package com.huazi.project.hse.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.huazi.project.hse.R;
import com.huazi.project.hse.db.HseDB;
import com.huazi.project.hse.entity.Risk;
import com.huazi.project.hse.entity.UploadFile;
import com.huazi.project.hse.util.AsyncHttpUtil;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.KsoapUtil;
import com.huazi.project.hse.util.Utility;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.callback.RequestCallBackHandler;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 隐患预览与上传
 * @author wfl
 *
 */

@ContentView(R.layout.preview_layout)
public class PreviewActivity extends Activity {
	
	private ProgressDialog progressDialog;

	private Risk risk; //上传隐患对象
	
	@ViewInject(R.id.risk_type_tv)
	private TextView riskTypeTV;
	
	@ViewInject(R.id.prof_type_tv)
	private TextView profTypeTV;
	
	@ViewInject(R.id.dept_tv)
	private TextView deptTV;
	
	@ViewInject(R.id.risk_rank_tv)
	private TextView riskRankTV;
	
	@ViewInject(R.id.risk_content_tv)
	private TextView contentTV;
	
	@ViewInject(R.id.preview_img)
	private ImageView imgView;
	
	private HseDB db;
	
	private boolean isUpload = false;
	private String fileName;
	//private String filePath;
	
	//private Bitmap uploadfile;
	
	private Bundle riskBundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ViewUtils.inject(this);
		db = HseDB.getInstance(this);
		riskBundle = getIntent().getExtras().getBundle("risk_bundle");
		
		//预览
		preview();
		
		//ready();
	}
	
	/*private void ready() {
		//risk = new Risk();
		//Bundle bundle = getIntent().getExtras().getBundle("risk_bundle");
		int riskTypeId = bundle.getInt("risk_type_id");
		String riskTypeName = bundle.getString("risk_type_name");
		int profTypeId = bundle.getInt("prof_type_id");
		String profTypeName = bundle.getString("prof_type_name");
		int riskRank = bundle.getInt("risk_rank");
		String createDate = bundle.getString("risk_create_date");
		fileName = bundle.getString("risk_filename");
		String photoPath = bundle.getString("photo_path");
		filePath = photoPath;
		Integer departmentId = null ; // 待定部门ID
		String creater = "";
		
		risk = new Risk(riskTypeId,profTypeId,departmentId,riskRank,content,creater,createDate,fileName);
		db.saveRisk(risk);  //insert into local database
		
		
	}*/
	
	private void preview() { //预览信息
		String riskTypeName = riskBundle.getString("risk_type_name");
		String profTypeName = riskBundle.getString("prof_type_name");
		String departmentName = "待定";
		String rankName = riskBundle.getString("risk_rank_name");
		String content = riskBundle.getString("risk_content");
		
		riskTypeTV.setText(riskTypeName);
		profTypeTV.setText(profTypeName);
		deptTV.setText(departmentName);
		riskRankTV.setText(rankName);
		contentTV.setText(content);
		
		String photoPath = riskBundle.getString("photo_path");
		Bitmap bm = BitmapFactory.decodeFile(photoPath);
		imgView.setImageBitmap(bm);
	}
	
	@OnClick(R.id.preview_upload_btn)
	public void upload(View v) {
		
		if (isUpload) {
			Toast.makeText(this, "请勿重复上传！", Toast.LENGTH_SHORT).show();
		} else {
			//上传JSON数据
			uploadJson();
			
			//上传图片文件
			httpUploadFile();
			
			isUpload = true;
		}		
	}
	
	/*@OnClick(R.id.upload_btn)
	public void upload(View v) {
		
		if (isUpload) {
			Toast.makeText(this, "请勿重复上传！", Toast.LENGTH_SHORT).show();
		} else {
			//上传JSON数据
			uploadJson();
			
			//上传图片文件
			httpUploadFile();
			
			isUpload = true;
		}		
	}*/
	
	/**
	 * 上传隐患数据
	 */
	private void uploadJson() {
		//准备提交信息
		risk = new Risk();
		int riskTypeId = riskBundle.getInt("risk_type_id");
		int profTypeId = riskBundle.getInt("prof_type_id");
		int riskRank = riskBundle.getInt("risk_rank");
		fileName = riskBundle.getString("risk_filename");
		String content = riskBundle.getString("risk_content");
		risk.setRiskTypeId(riskTypeId);
		risk.setProfTypeId(profTypeId);
		risk.setRank(riskRank);
		risk.setContent(content);
		risk.setFileName(fileName);
		risk.setCreater("");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("json", risk.toJson());
		//显示上传对话框
		showProgressDialog("正在上传数据...");
		KsoapUtil.connectWebService(params, "saveRiskInfo", new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				//Log.i("feilin", response);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// 关闭对话框
						closeProgressDialog();
					}
				});
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * 上传隐患图片
	 */
	private void httpUploadFile() {
		String url = "http://192.168.0.49:8080/baseFrame/upload.action";
		String filePath = riskBundle.getString("photo_path");
		File file = new File(filePath);
		RequestParams params = new RequestParams();

		try {
			showProgressDialog("正在上传图片...");
			params.put("uploadFile", file);
			AsyncHttpUtil.upload(url, params, new AsyncHttpResponseHandler() {
				
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					String response = arg2.toString();
					Log.i("feilin", response);
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// 关闭对话框
							closeProgressDialog();
						}
					});
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					String response = arg2.toString();
					Log.i("feilin", response);
				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 显示加载数据对话框
	 */
	private void showProgressDialog(String msg) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(msg);
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	
	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	
	@ViewInject(R.id.preview_back_btn)
	private Button backBtn;
	
	@OnClick(R.id.preview_back_btn)
	public void onBack(View v) {
		super.onBackPressed();
		//backBtn.setBackgroundResource(R.drawable.back_pressed1);
	}

}

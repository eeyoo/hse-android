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

import android.app.Activity;
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
	
	@ViewInject(R.id.photo_imgview)
	private ImageView imgView;
	
	private HseDB db;
	
	private String uploadStatus;
	private String fileName;
	private String filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		db = HseDB.getInstance(this);
		
		ready();
	}
	
	private void ready() {
		//risk = new Risk();
		Bundle bundle = getIntent().getExtras().getBundle("risk_bundle");
		int riskTypeId = bundle.getInt("risk_type_id");
		String riskTypeName = bundle.getString("risk_type_name");
		int profTypeId = bundle.getInt("prof_type_id");
		String profTypeName = bundle.getString("prof_type_name");
		int riskRank = bundle.getInt("risk_rank");
		String rankName = bundle.getString("risk_rank_name");
		String content = bundle.getString("risk_content");
		String createDate = bundle.getString("risk_create_date");
		fileName = bundle.getString("risk_filename");
		String photoPath = bundle.getString("photo_path");
		filePath = photoPath;
		Integer departmentId = null ; // 待定部门ID
		String creater = "";
		
		risk = new Risk(riskTypeId,profTypeId,departmentId,riskRank,content,creater,createDate,fileName);
		db.saveRisk(risk);  //insert into local database
		
		
		riskTypeTV.setText(riskTypeName);
		profTypeTV.setText(profTypeName);
		deptTV.setText("待定");
		riskRankTV.setText(rankName);
		contentTV.setText(content);
		
		Bitmap bm = BitmapFactory.decodeFile(photoPath);
		//bm = Bitmap.createBitmap(300, 450, null);
		//bm.setDensity(8);
		imgView.setImageBitmap(bm);
	}
	
	@OnClick(R.id.upload_btn)
	public void upload(View v) {
		//上传JSON数据
		uploadJson();
		Log.i("feilin", "risk json ");
		//上传图片文件
		uploadFile();
		Log.i("feilin", "photo json ");
		//Toast.makeText(PreviewActivity.this, uploadStatus, Toast.LENGTH_SHORT).show();
	}
	
	private void uploadJson() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("riskJson", risk.toJson());
		
		KsoapUtil.connectWebService(params, "saveRiskInfo", new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				//Log.i("feilin", response);
				uploadStatus = response;
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				e.printStackTrace();
			}
		});
	}
	
	private void uploadFile() {
		UploadFile file = new UploadFile();
		file.setFileName(fileName);
		file.setFileType("JPG");
		try {
			String content = Utility.fileToBase64(filePath);
			file.setContent(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("fileJson", file.toJson());
		KsoapUtil.connectWebService(params, "uploadFile", new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/*private class UploadTask extends AsyncTask<String, Integer, String> {

		String result = null;
		@Override
		protected String doInBackground(String... args) {
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("riskJson", risk.toJson());
			
			KsoapUtil.connectWebService(params, "saveRiskInfo", new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					result = response;
				}
				
				@Override
				public void onError(Exception e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			Toast.makeText(PreviewActivity.this, result, Toast.LENGTH_SHORT).show();
		}
		
	}*/

}

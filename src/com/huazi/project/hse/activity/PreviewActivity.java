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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.callback.RequestCallBackHandler;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * @author Administrator
 *
 */

@ContentView(R.layout.preview_layout)
public class PreviewActivity extends Activity {

	/*private Button upload;
	
	private TextView tvUnit;
	private TextView tvArea;
	private TextView tvEquipment;
	private TextView tvDemo;
	private String photoName;
	private String photoAddress;
	private UploadContent data;
	private SQLiteDao dao;
	
	private Context context;
	private boolean isUpload; //
	private boolean isFirstPreview; //
*/	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}
	
	@OnClick(R.id.btn_upload)
	public void upload(View v) {
		//Toast.makeText(this, POST, Toast.LENGTH_LONG).show();
		String url = "http://192.168.0.58:8080/hse/yh/riskRecord&test4Android.action";
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, 
				url, 
				new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(PreviewActivity.this, "GET failed", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(PreviewActivity.this, arg0.result, Toast.LENGTH_LONG).show();
			}
			
		});						
		
	}

}

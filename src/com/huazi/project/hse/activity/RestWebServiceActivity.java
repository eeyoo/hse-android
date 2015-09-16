package com.huazi.project.hse.activity;

import org.apache.http.Header;

import com.huazi.project.hse.R;
import com.huazi.project.hse.util.AsyncHttpUtil;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.HttpUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 用于测试REST Web Service
 */
public class RestWebServiceActivity extends Activity implements OnClickListener {

	private TextView requestText;
	private TextView responseText;
	private Button getRequestBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rest_layout);
		
		requestText = (TextView) findViewById(R.id.request_tv);
		responseText = (TextView) findViewById(R.id.response_tv);
		getRequestBtn = (Button) findViewById(R.id.get_btn);
		getRequestBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.get_btn: 
			queryFromServer1();
			break;
		}
		
	}

	private void queryFromServer() {
		String address = "http://192.168.0.66:8080/greeting";
		//String address = "http://192.168.149.1:8080/greeting";
		//String address = "http://www.weather.com.cn/adat/sk/101110101.html";
		requestText.setText(address);
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Log.i("feilin", "response -> " + response);
				responseText.setText(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				Log.i("feilin","Http request failed.");
				//responseText.setText(e.toString());
				e.printStackTrace();
			}
		});
	}
	
	private void queryFromServer1() {
		//String address = "http://192.168.0.66:8080/greeting";
		String address = "http://192.168.0.66:9999/Service/TestWebService?wsdl";
		requestText.setText(address);
		AsyncHttpUtil.get(address, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// TODO Auto-generated method stub
				String response = arg2.toString();
				Log.i("feilin", "response-> " + response);
				//responseText.setText(text);
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				Log.i("feilin", "http connect failed...");
			}
		});
	}
	
}

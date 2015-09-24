package com.huazi.project.hse.activity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.huazi.project.hse.R;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.HttpUtil;
import com.huazi.project.hse.util.KSOAPHttpUtil;
import com.huazi.project.hse.util.Utility;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SoapWebServiceActivity extends Activity implements OnClickListener {
	
	private TextView url;
	private EditText txt1;
	private EditText txt2;
	private Button connection;
	
	private String result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soap_layout);
		
		url = (TextView) findViewById(R.id.textView1);
		txt1 = (EditText) findViewById(R.id.editText1);
		txt2 = (EditText) findViewById(R.id.editText2);
		connection = (Button) findViewById(R.id.button1);
		connection.setOnClickListener(this);
		
		url.setText(KSOAPHttpUtil.SERVICE_URL);
		//txt1.setText((CharSequence) new MyTask().execute("Lily"));
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			//soapaction();
			//axis2soap();
			httpRequest("name=World!");
			break;

		default:
			break;
		}
	}

	private void soapaction() {
		KSOAPHttpUtil.getSoapInfo(new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Log.i("feilin", response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				Log.i("feilin", "connection failed...");
			}
		});
	}
	
	private void axis2soap() {
		final String param = "World!";
		KSOAPHttpUtil.connectWebService(param, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Log.i("feilin", response);
				result = response;
				//txt1.setText(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				Log.i("feilin", "connection failed...");
			}
		});
		txt1.setText(result);
	}
	
	private void httpRequest(String param) {
		String address = "http://192.168.0.66:8080/hse-server/services/HelloWorld/hello?response=application/json";
		address += "&" + param;
		url.setText(address);
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				//result = response;
				result = Utility.handleJsonResponse(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				result = "request failed.";
			}
		});
		txt1.setText(result);
	}
	
}

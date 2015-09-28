package com.huazi.project.hse.activity;



import com.huazi.project.hse.R;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.HttpUtil;
import com.huazi.project.hse.util.KsoapUtil;
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
		
		url.setText(KsoapUtil.SERVICE_URL);
		//txt1.setText((CharSequence) new MyTask().execute("Lily"));
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			soapParser();
			break;

		default:
			break;
		}
	}
	
	private void soapParser() {
		KsoapUtil.getSoapInfo(new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				//Log.i("feilin", response);
				result = response;
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				result = "soap get error";
			}
		});
		txt1.setText(result);
	}
	
}

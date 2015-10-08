package com.huazi.project.hse.activity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huazi.project.hse.R;
import com.huazi.project.hse.util.AsyncHttpUtil;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.HttpUtil;
import com.huazi.project.hse.util.KsoapUtil;
import com.huazi.project.hse.util.MyHttp;
import com.huazi.project.hse.util.Utility;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 用于测试REST Web Service
 */
public class RestWebServiceActivity extends Activity implements OnClickListener {
	
	private String address = "http://192.168.0.49:8080/getDeptByName";

	private TextView requestText;
	private TextView responseText;
	private Button getRequestBtn;
	private EditText paramEdit;
	
	private String result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rest_layout);
		
		requestText = (TextView) findViewById(R.id.request_tv);
		requestText.setText(address);
		
		responseText = (TextView) findViewById(R.id.response_tv);
		paramEdit = (EditText) findViewById(R.id.param_et);
		
		getRequestBtn = (Button) findViewById(R.id.get_btn);
		getRequestBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.get_btn: 
			queryFromServer("");
			break;
		}
		
	}

	private void queryFromServer(String param) {
		//String address = "http://192.168.149.1:8080/greeting";
		//String address = "http://192.168.0.49:8080/greeting";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Log.i("feilin", "response -> " + response);
				showJsonData(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				Log.i("feilin", "http connect failed...");
				e.printStackTrace();
			}
		});
		responseText.setText(result);
	}
	
	private void showJsonData(String response) {
		try {
			//JSONObject object = new JSONObject(response);
			//int id = object.getInt("id");
			//String content = object.getString("content");
			//result = "id: " + id + "; content: " + content;
			
			JSONArray array = new JSONArray(response);
			for (int i=0; i<array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				int no = obj.getInt("id");
				String name = obj.getString("name");
				String loc = obj.getString("loc");
				result = "id: " + no + "; name: " + name + "; location: " + loc;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			result = "json get error";
		}
	}
	
	private void showInfor() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		StringBuilder sb = new StringBuilder();
		int id = preferences.getInt("id", 0);
		String content = preferences.getString("content", "");
		sb.append("id: "+id);
		sb.append("; content: "+content);
		responseText.setText(sb);
	}
}

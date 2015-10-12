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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
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
@ContentView(R.layout.rest_layout)
public class RestWebServiceActivity extends Activity {
	
	//private String address = "http://192.168.0.49:8080/getDepartById";

	@ViewInject(R.id.request_tv)
	private TextView urlText;
	
	@ViewInject(R.id.param_et)
	private EditText paramEdit;
	
	@ViewInject(R.id.response_et)
	private EditText response;
	
	private String address;
	private String result;
	private String param;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		//urlText.setText(address);
	}
	
	@OnClick(R.id.rest_get_btn)
	public void find(View v) {
		param = paramEdit.getText().toString();
		//StringBuilder sb = null;
		//sb.append(address).append("?id=").append(param);
		if (param != "") {
			address = "http://192.168.0.49:8080/getDepartById?id=" + param;
		} else {
			address = "http://192.168.0.49:8080/getDepartById";
		}
		urlText.setText(address);
		
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				parserJsonData(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
		response.setText(result);
	}


	private void queryFromServer(String param) {
		//String address = "http://192.168.149.1:8080/greeting";
		//String address = "http://192.168.0.49:8080/greeting";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Log.i("feilin", "response -> " + response);
				//showJsonData(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				Log.i("feilin", "http connect failed...");
				e.printStackTrace();
			}
		});
		response.setText(result);
	}
	
	private void parserJsonData(String response) {
		try {
			JSONObject object = new JSONObject(response);
			int id = object.getInt("id");
			String name = object.getString("name");
			String loc = object.getString("loc");
			result = "id: " + id + "; name: " + name + "; location: " + loc;
			
			/*JSONArray array = new JSONArray(response);
			for (int i=0; i<array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				int no = obj.getInt("id");
				String name = obj.getString("name");
				String loc = obj.getString("loc");
				result = "id: " + no + "; name: " + name + "; location: " + loc;
			}*/
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
		response.setText(sb);
	}
}

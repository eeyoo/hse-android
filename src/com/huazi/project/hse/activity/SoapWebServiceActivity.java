package com.huazi.project.hse.activity;



import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huazi.project.hse.R;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.KsoapUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

@ContentView(R.layout.soap_layout)
public class SoapWebServiceActivity extends Activity {
	
	@ViewInject(R.id.url_tv)
	private TextView url;
	
	@ViewInject(R.id.method_tv)
	private TextView method;
	
	@ViewInject(R.id.multiline_ret)
	private EditText responseET;
	
	private String result;
	private String method_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		url.setText(KsoapUtil.SERVICE_URL);
		
		//method_name = "saveDeptInfo";
		//method.setText(method_name);
	}
	
	@OnClick(R.id.get_btn)
	public void getData(View v) {
		//method_name = "getDictEntryByType";
		method_name = "getRiskById";
		method.setText(method_name);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		//params.put("type", "YHFL");
		params.put("id", 1042);
		
		KsoapUtil.connectWebService(params, method_name, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				//result = jsonParser(response);
				//result = getDictEntry(response);
				result = getRiskInfo(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				result = e.toString();
			}
		});
		responseET.setText(result);
	}
	
	private String getDictEntry(String response) {
		return response;
	}
	
	private String getRiskInfo(String response) {
		return response;
	}
	
	@OnClick(R.id.insert_btn)
	public void insertData(View v) {
		method_name = "saveDeptInfo";
		method.setText(method_name);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		long id = 70;
		params.put("id", id);
		params.put("name", "销售部");
		params.put("loc", "越南");
		
		KsoapUtil.connectWebService(params, method_name, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				result = response;
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				result = e.toString();
			}
		});
		responseET.setText(result);
	}
	
	private String jsonParser(String response) {
		String res = null;
		try {
			//JSONObject object = new JSONObject(response);
			JSONArray array = new JSONArray(response);
			//JSONObject object = (JSONObject) array.get(0);
			
			for (int i=0; i<array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				long id = object.getLong("deptNo");
				String name = object.getString("deptName");
				String loc = object.getString("location");
				res = "id: " + id + ", deptName: " + name + ", location: " + loc;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
}

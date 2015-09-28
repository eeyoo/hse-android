package com.huazi.project.hse.activity;



import java.util.HashMap;

import org.json.JSONArray;

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
	
	@ViewInject(R.id.service_ret)
	private EditText response;
	
	@ViewInject(R.id.multiline_ret)
	private EditText jsonResponse;
	
	private String result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		url.setText(KsoapUtil.SERVICE_URL);
		//txt1.setText((CharSequence) new MyTask().execute("Lily"));
				
	}
	
	/*@OnClick(R.id.web_service)
	public void testWebService(View v) {
		Log.i("feilin", "test web service");
		soapParser();
	}*/
	
	@OnClick(R.id.web_service)
	public void webService(View v) {
		//Log.i("feilin", "Web Service");
		//soapParser();
		getWebResponse();
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
		response.setText(result);
	}
	
	private void getWebResponse() {
		final String url = "http://192.168.0.49:8080/temp/ws/planAndReport?wsdl";
		final String methodName = "getConfirmPlanInfoJson";
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("yearStr", response.getText().toString());
		
		KsoapUtil.connectWebService(params, url, methodName, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				result = response;
				//jsonParser(response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				result = e.toString();
			}
		});
		
		jsonResponse.setText(result);
	}
	
	private String jsonParser(String response) {
		StringBuilder sb = null;
		
		try {
			JSONArray array = new JSONArray(response);
			
			for(int i=0;i<array.length();i++){
				org.json.JSONObject obj = (org.json.JSONObject)array.get(i);
				JSONArray arr = obj.getJSONArray("mainInfo");
				
				for(int j=0;j<arr.length();j++){
					org.json.JSONObject obj1 = (org.json.JSONObject)arr.get(j);
					
					String code = obj1.getString("code");
					String exp = obj1.getString("explication");
					String value = obj1.getString("value");
					sb.append("code: " + code);
					sb.append("value: " + value);
					sb.append("explication: " + exp);			
				}
	    	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}

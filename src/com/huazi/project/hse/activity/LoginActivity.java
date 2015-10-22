/**
 * 
 */
package com.huazi.project.hse.activity;

import java.util.HashMap;

import com.huazi.project.hse.R;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.KsoapUtil;
import com.huazi.project.hse.util.Utility;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author wfl
 * 用户管理
 */
@ContentView(R.layout.login_layout)
public class LoginActivity extends Activity {

	@ViewInject(R.id.login_username_et)
	private EditText usernameEt;
	
	@ViewInject(R.id.login_password_et)
	private EditText passwordEt;
	
	private String username;
	private String password;
	
	private String result;
	
	//private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ViewUtils.inject(this);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean login = preferences.getBoolean("login", false);
		if (login) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		
	}
	
	@OnClick(R.id.login_btn)
	public void login(View v) {
		username = usernameEt.getText().toString();
		password = passwordEt.getText().toString();
		
		Log.i("feilin", username);
		Log.i("feilin", password);
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userName", username);
		params.put("userPwd", password);
		String method_name = "checkUser";
		
		KsoapUtil.connectWebService(params, method_name, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Log.i("feilin", response);
				result = response;
				if (result.equals("true")) {
					//saveLoginStatus(true);
					Utility.handleUserLoginResponse(LoginActivity.this, true);
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish(); // kill itself
				} else {
					//saveLoginStatus(false);
					Log.i("feilin", "登陆失败");
					usernameEt.setText("");
					passwordEt.setText("");
				}
				
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//new MyTask().execute();
		//Log.i("feilin", result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

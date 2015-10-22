/**
 * 
 */
package com.huazi.project.hse.activity;

import com.huazi.project.hse.R;
import com.huazi.project.hse.util.Utility;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * @author wfl
 * 用户管理
 */
@ContentView(R.layout.usermange_layout)
public class UserManageActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ViewUtils.inject(this);
	}
	
	@OnClick(R.id.user_logout_btn)
	public void logout(View v) {
		Utility.handleUserLoginResponse(UserManageActivity.this, false);
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
		// 发通知销毁所有Acitivity
	}

	@OnClick(R.id.user_back_btn)
	public void onBack(View v) {
		super.onBackPressed();
	}
}

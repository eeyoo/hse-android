package com.huazi.project.hse.activity;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huazi.project.hse.R;
import com.huazi.project.hse.db.HseDB;
import com.huazi.project.hse.entity.DictEntry;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.KsoapUtil;
import com.huazi.project.hse.util.Utility;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

@ContentView(R.layout.main_layout)
public class MainActivity extends Activity {

	private HseDB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		ViewUtils.inject(this);
		
		db = HseDB.getInstance(this);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean dictEntryLoad = preferences.getBoolean("DictEntryLoad", false);
		
		if (!dictEntryLoad) {
			//服务端获取全局字典数据
			queryFromServer("getDictEntries");
		} 
	}

	@OnClick(R.id.risk_btn)
	public void risk(View v) {
		//隐患上报
		Intent intent = new Intent(this, RiskActivity.class);
		startActivity(intent);
	}

	private void queryFromServer(String method_name) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		KsoapUtil.connectWebService(params, method_name, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				Utility.handleDictEntryJsonResponse(MainActivity.this, db, response);
			}

			@Override
			public void onError(Exception e) {
			}
		});
	}
	
	@OnClick(R.id.user_manager_btn)
	public void userManager(View v) {
		//Dialog dialog = new Dialog(this);
		//dialog.show();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_layout, null))
			.setPositiveButton(R.string.signin, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNegativeButton(R.string.cancel, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
		builder.create().show();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

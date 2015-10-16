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
import android.preference.PreferenceManager;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

@ContentView(R.layout.main_layout)
public class MainActivity extends Activity {

	private HseDB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

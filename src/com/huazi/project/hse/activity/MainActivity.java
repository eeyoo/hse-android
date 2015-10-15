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
	}

	@OnClick(R.id.risk_btn)
	public void danger(View v) {
		// Toast.makeText(this, "OK", duration)
		Intent intent = new Intent(this, RiskActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.webservice_btn)
	public void services(View v) {
		// Intent intent = new Intent(this, SoapWebServiceActivity.class);
		// startActivity(intent);
		// 获取DictEntry字典数据
		//queryFromServer("getDictEntries");
		//Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean dictEntryLoad = preferences.getBoolean("DictEntryLoad", false);
		
		if (!dictEntryLoad) {
			new LoadData().execute("getDictEntries");
		} else {
			Toast.makeText(this, "已经载入数据", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private class LoadData extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			queryFromServer(params[0]);
			return "载入数据成功";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
		}
	}

	private void queryFromServer(String method_name) {
		//method_name = "getDictEntries";
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


	@OnClick(R.id.gridview_btn)
	public void grid(View v) {
		// Intent intent = new Intent(this, ListViewActivity.class);
		// startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

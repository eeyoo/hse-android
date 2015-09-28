package com.huazi.project.hse.activity;

import com.huazi.project.hse.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

@ContentView(R.layout.main_layout)
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		ViewUtils.inject(this);
	}
	
	@OnClick(R.id.risk_btn)
	public void danger(View v) {
		//Toast.makeText(this, "OK", duration)
		Intent intent = new Intent(this, RiskActivity.class);
		startActivity(intent);
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);		
		return true;
	}

}

package com.huazi.project.hse.activity;

import java.io.File;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.huazi.project.hse.R;

@ContentView(R.layout.upload_layout)
public class UploadActivity extends Activity {

	@ViewInject(R.id.img_view)
	private ImageView img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ViewUtils.inject(this);
		
		
		img.setAdjustViewBounds(true);
		img.setMaxHeight(600);
		img.setMaxWidth(400);
		
		Intent intent = getIntent();
		//Bitmap bm = (Bitmap) intent.getExtras().get("photo");
		String path = intent.getStringExtra("path");
		String name = intent.getStringExtra("name");
		String time = intent.getStringExtra("time");
		Log.i("feilin", path);
		Log.i("feilin", name);
		Log.i("feilin", time);
		Bitmap bm = BitmapFactory.decodeFile(path);
		//bm = Bitmap.createBitmap(300, 450, null);
		//bm.setDensity(8);
		img.setImageBitmap(bm);
	}
	

	@OnClick(R.id.upload_btn)
	public void upload(View v) {
		Toast.makeText(this, "上传成功", Toast.LENGTH_LONG).show();
	}

}

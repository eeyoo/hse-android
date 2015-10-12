package com.huazi.project.hse.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.R.anim;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.huazi.project.hse.R;
import com.huazi.project.hse.db.HseDB;
import com.huazi.project.hse.entity.DictEntry;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.KsoapUtil;

@ContentView(R.layout.risk_layout)
public class RiskActivity extends Activity {

	// private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	// private Uri fileUri;

	private String photoName;
	private String photoPath;
	private String photoTime;
	private Uri mUri;
	private Bitmap photo;
	private boolean isPhoto = false;

	private ArrayList<String> dataList;
	private List<DictEntry> dictEntryList;
	private HseDB hseDB;
	
	private String method_name;
	private String result;

	@ViewInject(R.id.risk_type_spinner)
	private Spinner riskTypeSpinner;

	@ViewInject(R.id.prof_type_spinner)
	private Spinner profTypeSpinner;

	@ViewInject(R.id.department_id_spinner)
	private Spinner departmentSpinner;

	@ViewInject(R.id.rank_spinner)
	private Spinner rankSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);
		hseDB = HseDB.getInstance(this);
		// hseDB.loadDictEntry()

		queryData();
		setSpinner();
	}

	private void queryData() {
		dictEntryList = hseDB.loadDictEntryByType("YHFL");
		if (dictEntryList.size() > 0) {
			dataList.clear();
			for (DictEntry dictEntry : dictEntryList) {
				dataList.add(dictEntry.getName());
			}
		} else { // 获取服务器数据
			queryFromServer();
		}
	}

	private void queryFromServer() {
		method_name = "getDictEntries";
		//method_name = "getDictEntryByType";

		HashMap<String, Object> params = new HashMap<String, Object>();
		//params.put("type", "YHFL");

		KsoapUtil.connectWebService(params, method_name, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				result = jsonParser(response);
				// result = getDictEntry(response);
			}

			@Override
			public void onError(Exception e) {
			}
		});
	}
	
	private String jsonParser(String response) {
		try {
			JSONArray array = new JSONArray(response);
			for (int i=0; i < array.length(); i++) {
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private void setSpinner() {
		// load risk type
		ArrayList<String> data = new ArrayList<String>();
		data.add("北京");
		data.add("上海");
		data.add("广州");
		ArrayAdapter<CharSequence> riskAdapter = ArrayAdapter.createFromResource(this, R.array.risk_type_array,
				android.R.layout.simple_spinner_item);
		riskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		riskTypeSpinner.setAdapter(riskAdapter);
		// load prof type
		ArrayAdapter<String> profAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
		profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		profTypeSpinner.setAdapter(profAdapter);

		// load department

		// load rank
	}

	// 获取隐患类型数据，本地数据库读取，若没有数据通过Web服务读取

	// 获取专业类型数据

	// 获取所属部门数据

	// 获取隐患等级数据

	@OnClick(R.id.camera_btn)
	public void photo(View v) {// 调用系统相机

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String dirPath = Environment.getExternalStorageDirectory() + "/images/";
		File file = new File(dirPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		new DateFormat();
		photoTime = (String) DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA));

		photoName = "img_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		photoPath = dirPath + photoName;
		mUri = Uri.fromFile(new File(photoPath));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, 0);
	}

	@OnClick(R.id.preview_btn)
	public void preview(View v) { // 发送预览信息
		if (!isPhoto) {
			Toast.makeText(this, "请对隐患拍照！", Toast.LENGTH_LONG).show();
			return;
		}
		Intent intent = new Intent(this, UploadActivity.class);

		intent.putExtra("time", photoTime);
		intent.putExtra("name", photoName);
		intent.putExtra("path", photoPath);
		// intent.putExtra("photo", photo); //图片已经保存至SD卡
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {// 保存相机拍照图片
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			isPhoto = true;
			ContentResolver cr = this.getContentResolver();
			try {
				photo = BitmapFactory.decodeStream(cr.openInputStream(mUri));
				if (photo != null) {
					photo.recycle();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}

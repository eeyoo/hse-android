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
import org.json.JSONObject;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.R.anim;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.huazi.project.hse.R;
import com.huazi.project.hse.db.HseDB;
import com.huazi.project.hse.entity.DictEntry;
import com.huazi.project.hse.entity.Risk;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.KsoapUtil;

@ContentView(R.layout.risk_layout)
public class RiskActivity extends Activity {

	private String photoName;
	private String photoPath;
	private String photoTime;
	private Uri mUri;
	private Bitmap photo;
	private boolean isPhoto = false;

	private List<String> riskdataList = new ArrayList<String>();
	private List<String> profdataList = new ArrayList<String>();
	private List<String> rankdataList = new ArrayList<String>();
	private List<DictEntry> riskDictList;
	private List<DictEntry> profDictList;
	
	private DictEntry riskSelected;
	private DictEntry profSelected;
	
	private HseDB hseDB;

	@ViewInject(R.id.risk_type_spinner)
	private Spinner riskTypeSpinner;

	@ViewInject(R.id.prof_type_spinner)
	private Spinner profTypeSpinner;

	@ViewInject(R.id.department_id_spinner)
	private Spinner departmentSpinner;

	@ViewInject(R.id.rank_spinner)
	private Spinner rankSpinner;
	
	@ViewInject(R.id.content_et)
	private EditText content_et;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	private Bundle riskBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ViewUtils.inject(this);
		
		hseDB = HseDB.getInstance(this);
		
		riskBundle = new Bundle();

		setYHFLSpinner();
		setZYLXSpinner();
		setRankSpinner();		
	}
	
	/*private void refresh() {
		riskDictList = hseDB.loadDictEntryByType("YHFL");
		if (riskDictList.size() > 0) {
			//dataList.clear();
			for (DictEntry dictEntry : riskDictList) {
				String name = dictEntry.getName();
				riskdataList.add(name);
			}
		} else { // 获取服务器数据
			Log.i("feilin", "DictEntry not data");
		}
		
		profDictList = hseDB.loadDictEntryByType("ZYLX");
		if (profDictList.size() > 0) {
			//dataList.clear();
			for (DictEntry dictEntry : profDictList) {
				String name = dictEntry.getName();
				profdataList.add(name);
			}
		} else { // 获取服务器数据
			Log.i("feilin", "DictEntry not data");
		}
	}*/

	// 获取隐患类型数据，本地数据库读取，若没有数据通过Web服务读取
	private void setYHFLSpinner() {
		riskDictList = hseDB.loadDictEntryByType("YHFL");
		if (riskDictList.size() > 0) {
			//dataList.clear();
			for (DictEntry dictEntry : riskDictList) {
				String name = dictEntry.getName();
				riskdataList.add(name);
			}
		} else { // 获取服务器数据
			Log.i("feilin", "DictEntry not data");
		}

		// load risk type
		ArrayAdapter<String> riskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				riskdataList);
		riskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		riskTypeSpinner.setAdapter(riskAdapter);		
		
		riskTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {								
				riskSelected = riskDictList.get(position);

				riskBundle.putInt("risk_type_id", riskSelected.getServerId());
				riskBundle.putString("risk_type_name", riskSelected.getName());				
			} 

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	// 获取专业类型数据
	private void setZYLXSpinner() {
		profDictList = hseDB.loadDictEntryByType("ZYLX");
		if (profDictList.size() > 0) {
			//dataList.clear();
			for (DictEntry dictEntry : profDictList) {
				String name = dictEntry.getName();
				profdataList.add(name);
			}
		} else { // 获取服务器数据
			Log.i("feilin", "DictEntry not data");
		}

		// load risk type
		ArrayAdapter<String> profAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				profdataList);
		profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		profTypeSpinner.setAdapter(profAdapter);
		
		profTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				profSelected = profDictList.get(position);
				
				riskBundle.putInt("prof_type_id", profSelected.getServerId());
				riskBundle.putString("prof_type_name", profSelected.getName());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	// 获取所属部门数据

	// 获取隐患等级数据
	private void setRankSpinner() {
		rankdataList = new ArrayList<String>();
		rankdataList.add("一般事故隐患");
		rankdataList.add("重大事故隐患");
		
		ArrayAdapter<String> rankAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rankdataList);
		rankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rankSpinner.setAdapter(rankAdapter);
		
		rankSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				riskBundle.putInt("risk_rank", position+1);
				riskBundle.putString("risk_rank_name", rankdataList.get(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

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

		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {// 保存相机拍照图片
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				isPhoto = true;
				ContentResolver cr = this.getContentResolver();
				try {
					photo = BitmapFactory.decodeStream(cr.openInputStream(mUri));
					if (photo != null) {
						photo.recycle();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@OnClick(R.id.preview_btn)
	public void preview(View v) { // 发送预览信息
		if (!isPhoto) {
			Toast.makeText(this, "请对隐患拍照！", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(this, PreviewActivity.class); //To preview
		String content = content_et.getText().toString();
		
		riskBundle.putString("risk_content", content);
		riskBundle.putString("risk_create_date", photoTime);
		riskBundle.putString("risk_filename", photoName);
		riskBundle.putString("photo_path", photoPath);
		intent.putExtra("risk_bundle", riskBundle);
		startActivity(intent);
	}

	@ViewInject(R.id.risk_back_btn)
	private Button backBtn;
	
	@OnClick(R.id.risk_back_btn)
	public void onBack(View v) {
		super.onBackPressed();
		backBtn.setBackgroundResource(R.drawable.back_pressed1);
	}
}

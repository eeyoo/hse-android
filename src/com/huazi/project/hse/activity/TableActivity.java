package com.huazi.project.hse.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.huazi.project.hse.R;
import com.huazi.project.hse.adapter.ImageAdapter;
import com.huazi.project.hse.db.HseDB;
import com.huazi.project.hse.entity.InterfaceData;
import com.huazi.project.hse.util.HttpCallbackListener;
import com.huazi.project.hse.util.KsoapUtil;
import com.huazi.project.hse.util.Utility;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@ContentView(R.layout.table_layout)
public class TableActivity extends Activity {

	//@ViewInject(R.id.table_gridview)
	//private GridView gridview;
	private Dialog dialog;
	private ProgressDialog progressDialog;
	private ArrayAdapter<String> adapter;
	private List<String> dataList = new ArrayList<String>();
	private HseDB hsedb;
	private List<InterfaceData> interfaceDataList;
	
	@ViewInject(R.id.data_list)
	private ListView listview;
	
	@ViewInject(R.id.table_title_tv)
	private TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉自带title
		ViewUtils.inject(this);
		
		dialog = new Dialog(this);
		
		hsedb = HseDB.getInstance(this);
		
		title.setText("数据表显示");
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//AlertDialog.Builder builder = new AlertDialog.Builder(this);
				showDialog();
			}
		});
		
		query("2015"); //query and view data
	}
	
	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Hello World");
		//builder.create();
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private void query(String year) {
		interfaceDataList = hsedb.loadInterface(year);
		if (interfaceDataList.size() > 0) {//query data from sqlite
			dataList.clear();
			for (InterfaceData data : interfaceDataList) {
				String code = data.getCode();
				String value = data.getValue();
				String explication = data.getExplication();
				String bundle = code + " -- " + value + " -- " + explication;
				dataList.add(bundle);
			}
			adapter.notifyDataSetChanged();
			listview.setSelection(0);
		} else { // else query from server
			queryFromServer(year);
		}
	}
	
	/**
	 * 向服务器请求数据
	 * @param year
	 */
	private void queryFromServer(final String year) {
		
		final String url = "http://192.168.0.49:8080/temp/ws/planAndReport?wsdl";
		final String methodName = "getConfirmPlanInfoJson";
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("yearStr", year);
		
		showProgressDialog();
		
	}

	/**
	 * 显示加载数据对话框
	 */
	private void showProgressDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("loading...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	
	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
}

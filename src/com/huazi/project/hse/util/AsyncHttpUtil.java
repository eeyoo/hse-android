package com.huazi.project.hse.util;

import java.io.FileNotFoundException;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncHttpUtil {
	
	//private static final String URL = "http://192.168.0.49:8080/baseFrame/upload.action";

	private static AsyncHttpClient client = new AsyncHttpClient();
	
	static {
		client.setTimeout(10000); //默认10s
	}
	
	public synchronized static void get(String url, AsyncHttpResponseHandler responseHandler) {
		client.get(url, responseHandler);
	}
	
	public synchronized static void post(String url, RequestParams params, 
			AsyncHttpResponseHandler handler) {
		client.post(url, params, handler);
	}
	
	public synchronized static void upload(String url, RequestParams params, 
			AsyncHttpResponseHandler handler) throws FileNotFoundException {
		client.post(url, params, handler);
	}
}

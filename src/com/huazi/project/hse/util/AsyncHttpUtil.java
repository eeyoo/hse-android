package com.huazi.project.hse.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class AsyncHttpUtil {

	private static AsyncHttpClient client = new AsyncHttpClient();
	
	static {
		client.setTimeout(11000); //默认10s
	}
	
	public synchronized static void get(String url, AsyncHttpResponseHandler responseHandler) {
		client.get(url, responseHandler);
	}
}

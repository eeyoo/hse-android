package com.huazi.project.hse.util;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class MyHttp {

	public synchronized static void httpGet(final String url, final HttpCallbackListener listener) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String response = null;
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				//HttpRequest httpRequest;
				HttpResponse httpResponse;
				try {
					httpResponse = httpClient.execute(httpGet);
					int statusCode = httpResponse.getStatusLine().getStatusCode();
					if (statusCode == HttpStatus.SC_OK) {
						response = EntityUtils.toString(httpResponse.getEntity());
						if (listener != null) {
							listener.onFinish(response);
						}
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onError(e);
					}
				}
			}
		}).start();
		
	}
}

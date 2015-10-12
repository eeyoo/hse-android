package com.huazi.project.hse.util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class KsoapUtil {

	public final static String SERVICE_URL = "http://192.168.0.49:8080/baseFrame/ws/userService?wsdl";
	final static String SERVICE_NS = "http://business.services.projects.huazi.com/";
	
	public static void connectWebService(final HashMap<String, Object> params, 
			final String method_name, final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				SoapObject request = new SoapObject(SERVICE_NS, method_name);
				
				if (params != null && params.size() > 0) {
					for (Entry<String, Object> entry : params.entrySet()) {
						request.addProperty(entry.getKey(), entry.getValue());
					}
				}

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.bodyOut = request;

				HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
				ht.debug = true;

				try {
					ht.call(null, envelope);

					// 获取返回数据
					SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

					if (listener != null) {
						listener.onFinish(response.toString());
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
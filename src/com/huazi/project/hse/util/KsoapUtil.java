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

	public final static String SERVICE_URL = "http://192.168.0.49:8080/temp/ws/userService?wsdl";
	final static String SERVICE_NS = "http://business.services.projects.esse.com/";
	final static String METHOD_NAME = "checkUser";

	public static void getSoapInfo(final HttpCallbackListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				SoapObject request = new SoapObject(SERVICE_NS, METHOD_NAME);
				// request.addProperty("text", "Lily");
				request.addProperty("userName", "abc");
				request.addProperty("userPwd", "123");

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.bodyOut = request;

				HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
				ht.debug = true;

				try {
					ht.call(null, envelope);

					// 获取返回数据
					// SoapObject result = (SoapObject) envelope.bodyIn;
					SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
					// result = response.toString();
					if (listener != null) {
						listener.onFinish(response.toString());
					}

				} catch (Exception e) {
					// e.printStackTrace();
					if (listener != null) {
						listener.onError(e);
					}
				}

			}
		}).start();

	}

	public static void connectWebService(final HttpCallbackListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String url = " http://192.168.0.66:8080/axis2/services/Version?wsdl";
				String namespace = "http://axisversion.sample";
				String methodname = "getVersion";
				SoapObject request = new SoapObject(namespace, methodname);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(url);

				try {
					ht.call(null, envelope);
					SoapObject so = (SoapObject) envelope.bodyIn;
					String response = so.toString();
					if (listener != null) {
						listener.onFinish(response);
					}
				} catch (Exception e) {
					// TODO: handle exception
					if (listener != null) {
						listener.onError(e);
					}
				}
			}
		}).start();
	}

	public static void connectWebService(final HashMap<String, Object> params, final String url, 
			final String methodName,
			final HttpCallbackListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				SoapObject request = new SoapObject(SERVICE_NS, methodName);
				
				if (params != null && params.size() > 0) {
					for (Entry<String, Object> entry : params.entrySet()) {
						request.addProperty(entry.getKey(), entry.getValue());
					}
				}

				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = false; // dotNet - true, java - false
				envelope.setOutputSoapObject(request);

				HttpTransportSE ht = new HttpTransportSE(url);
				ht.debug = true;

				try {
					// Involve web service
					ht.call(null, envelope);

					// SoapObject soap = (SoapObject) envelope.bodyIn;
					SoapPrimitive soapPrimitive = (SoapPrimitive) envelope.getResponse();
					// String result = response.toString();
					String response = soapPrimitive.toString();

					if (listener != null) {
						listener.onFinish(response);
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

package com.huazi.project.hse.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;

public class KSOAPHttpUtil {

	//public static final String endPoint = "http://localhost:9999/Service/TestWebService?wsdl";
	final static String SERVICE_NS = "http://server.webservice.rpc.local.demo.g4studio.org/";
	//http://server.webservice.rpc.local.demo.g4studio.org/
	public final static String SERVICE_URL = "http://192.168.0.66:169/g4studio/rpc/webservice/HelloWorldService";
	//http://localhost:169/g4studio/rpc/webservice/HelloWorldService?wsdl=HelloWorldService.wsdl
	final static String METHOD_NAME = "sayHello";
	
	public static void getSoapInfo(final HttpCallbackListener listener) {

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//命名空间
				//String nameSpace = "http://hse.project.huazi.com/";
				//方法名
				//String methodName = "sayHello";
				// EndPoint
				//String endPoint = "http://localhost:9999/Service/TestWebService?wsdl";
				//SaopAction
				//String soapAction = null;
				
				//SoapObject soapObject = new SoapObject(nameSpace, methodName);
				
				//envelope.bodyOut = soapObject;
				//envelope.dotNet = false;
				//envelope.setOutputSoapObject(soapObject);
				
				HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
				ht.debug = true;
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				SoapObject request = new SoapObject(SERVICE_NS, METHOD_NAME);
				//request.addProperty("text", "Lily");
				envelope.bodyOut = request;
				
				try {
					ht.call(null, envelope);
					
					//获取返回数据
					SoapObject result = (SoapObject) envelope.bodyIn;
					//String response = result.getProperty(0).toString();
					String response = result.toString();
					if (listener != null) {
						listener.onFinish(response);
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					//e.printStackTrace();
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
	
	public static void connectWebService(final String param, final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//String url = "http://192.168.0.66:8080/hse-server/services/HelloWorld/hello?response=application/json";
				String url = "http://192.168.0.66:8080/hse-server/services/HelloWorld/hello";
				String namespace = "http://impl.service.example.com";
				String methodname = "hello";
				SoapObject request = new SoapObject(namespace, methodname);
				//request.addProperty("response", "application/json");
				//request.addAttribute("response", "application/json");
				request.addProperty("name", param);
				//request.add
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				//InputStream is = new ByteArrayInputStream(request);
				
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE ht = new HttpTransportSE(url);
				ht.debug = true;
				
				try {
					ht.call(null, envelope);
					//SoapObject so = (SoapObject) envelope.bodyIn;
					SoapObject so = (SoapObject) envelope.getResponse();
					String response = so.getProperty("return").toString();
					//String response = so.getPropertyAsString(0).toString();
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
}

package com.huazi.project.hse.util;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class KSOAPHttpUtil {

	public static final String endPoint = "http://localhost:9999/Service/TestWebService?wsdl";
	
	public static void getSoapInfo(final HttpCallbackListener listener) {

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//命名空间
				String nameSpace = "http://hse.project.huazi.com/";
				//方法名
				String methodName = "sayHello";
				// EndPoint
				//String endPoint = "http://localhost:9999/Service/TestWebService?wsdl";
				//SaopAction
				String soapAction = null;
				
				SoapObject soapObject = new SoapObject(nameSpace, methodName);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
				
				envelope.bodyOut = soapObject;
				envelope.dotNet = false;
				envelope.setOutputSoapObject(soapObject);
				
				HttpTransportSE transportSE = new HttpTransportSE(endPoint);
				try {
					transportSE.call(soapAction, envelope);
					
					//获取返回数据
					SoapObject result = (SoapObject) envelope.bodyIn;
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
}

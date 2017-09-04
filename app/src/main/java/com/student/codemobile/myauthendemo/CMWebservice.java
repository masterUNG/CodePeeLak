package com.student.codemobile.myauthendemo;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CMWebservice {

    // sudo /Applications/XAMPP/xamppfiles/xampp enablessl


    private static final String URL = "http://www.pttplc.com/webservice/pttinfo.asmx";
    private static final String SOAP_ACTION = "http://www.pttplc.com/ptt_webservice/CurrentOilPrice";
    private static final String OPERATION_NAME = "CurrentOilPrice";
    private static final String NAMESPACE = "http://www.pttplc.com/ptt_webservice/";
    private static Object resultRequestSOAP = null;
    private static final int TIMEOUT = 600000;


    public static String getCurrentOilPrice() {

        SoapObject request = new SoapObject(NAMESPACE, OPERATION_NAME);
        request.addProperty("Language", "TH");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true; //webservice เขียนมาจาก .net ต้องเป็น True
        envelope.bodyOut = request;
        CMHttpsUtil.allowAllSSL();
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, TIMEOUT);
        androidHttpTransport.debug = true;

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);
            resultRequestSOAP = envelope.getResponse();
            //ข้อมูลดิบ เนื่องจาก .net จะมาทั้งหมดที่เป้น Array จะทำการ Dump ทั้ง request และ response
            Log.i("codemobiles", "soap request " + androidHttpTransport.requestDump);
            Log.i("codemobiles", "soap response" + androidHttpTransport.responseDump);

            return resultRequestSOAP.toString();
        } catch (Exception aE) {
        }

        return null;
    }



}

package com.student.codemobile.myauthendemo;


import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import static com.student.codemobile.myauthendemo.CMWebservice.getCurrentOilPrice;
import static com.student.codemobile.myauthendemo.WeatherWebservice.getCitiesByCountry;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebserviceFragment extends Fragment {
    private TextView mDieselTextView;
    private TextView mE85TextView;
    private TextView mE20TextView;
    private TextView mGas91TextView;
    private TextView mGas95TextView;

    public WebserviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_webservice, container, false);

        mDieselTextView = (TextView) v.findViewById(R.id.dieselTextView);
        mE20TextView = (TextView) v.findViewById(R.id.e20TextView);
        mGas91TextView = (TextView) v.findViewById(R.id.gas91TextView);
        mGas95TextView = (TextView) v.findViewById(R.id.gas95TextView);
        mE85TextView = (TextView) v.findViewById(R.id.e85TextView);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "ds_digital.TTF");

        mDieselTextView.setTypeface(font);
        mE20TextView.setTypeface(font);
        mGas91TextView.setTypeface(font);
        mGas95TextView.setTypeface(font);
        mE85TextView.setTypeface(font);

        new WebserviceTask().execute();

        return v;
    }

    public class WebserviceTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String cities = WeatherWebservice.getCitiesByCountry("Thailand");
            Log.i("weather_cities",cities);
            String result = CMWebservice.getCurrentOilPrice();

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            CMXMLParser parser = new CMXMLParser();
            Document doc = parser.getDomElement(s);
            NodeList nl = doc.getElementsByTagName("DataAccess"); //Root ของ ข้อมูล Webservice PTT
            //ดึงข้อมูลแบบ single ทดสอบดึงดูก่อน
            //Element item = (Element) nl.item(0);
            //String product = parser.getValue(item, "PRODUCT"); //โครงสร้าง Field ชื่อ Product
            //Toast.makeText(getAc*/tivity(), product, Toast.LENGTH_SHORT).show();

            for (int j=0; j < nl.getLength(); j++){
                Element item = (Element) nl.item(j);
                String product = parser.getValue(item, "PRODUCT");
                String price = parser.getValue(item, "PRICE");
                if (product.equals("Blue Diesel")) {
                    mDieselTextView.setText(price);
                } else if (product.equals("Blue Gasohol E85")) {
                    mE85TextView.setText(price);
                } else if (product.equals("Blue Gasohol E20")) {
                    mE20TextView.setText(price);
                } else if (product.equals("Blue Gasohol 91")) {
                    mGas91TextView.setText(price);
                } else if (product.equals("Blue Gasohol 95")) {
                    mGas95TextView.setText(price);
                }

            }
        }
    }
}

package com.example.webgeoapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public void some_function(View view) {
        // stuff...
        downloadJsonData downhandler = new downloadJsonData();
        EditText edt1 = (EditText)findViewById(R.id.editText);
        String city =  edt1.getText().toString();//textView8

        TextView txt11 = (TextView) findViewById(R.id.textView11);
        TextView txt12 = (TextView) findViewById(R.id.textView12);

        txt11.setText("WEather in  -"+city);
        txt12.setText("tempurature in - "+city);

        TextView txt9 = (TextView) findViewById(R.id.textView9);
        TextView txt10 = (TextView) findViewById(R.id.textView10);
        TextView txt1 = (TextView)findViewById(R.id.textView3);

        TextView txt2 = (TextView)findViewById(R.id.textView4);
        txt2.setText("fetching.....");
        txt1.setText("fetching.....");
        txt9.setText("fetching.....");
        txt10.setText("fetching....");


        TextView shity = (TextView)findViewById(R.id.textView8);
        shity.setText(city);

        downhandler.execute("https://geocoder.api.here.com/6.2/geocode.json?app_id=lmuiUmhDK2JQwBnORnRB&app_code=W3YBbdzlsKOER9SvMF_V1g&searchtext="+city);





    }


    public  class downloadweather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String jsonDAta = "";
            URL url;
            HttpURLConnection conn = null;



            try {
                url = new URL(urls[0]);
                conn =  (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int buffer = reader.read();

                while (buffer != -1){

                    jsonDAta += (char) buffer;
                    buffer = reader.read();
                }

                return jsonDAta;

            } catch (IOException e) {
                e.printStackTrace();
                return "error network";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("weather - ",s);

            try {
                JSONObject obj1 = new JSONObject(s);
                String currently = obj1.getString("currently");
                JSONObject obj2 = new JSONObject(currently);
                String summry = obj2.getString("summary");
                String temp = obj2.getString("temperature");

                Log.i("summary ",summry);
                Log.i("temp",temp);

                TextView txt9 = (TextView) findViewById(R.id.textView9);
                TextView txt10 = (TextView) findViewById(R.id.textView10);

                txt9.setText(summry);

                double cel = ( Double.parseDouble(temp) -32)/9*5;

                txt10.setText(Double.toString((Math.round(cel))));





            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }




    public class downloadJsonData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            String jsonDAta = "";
            URL url;
            HttpURLConnection conn = null;



            try {
                url = new URL(urls[0]);
                conn =  (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int buffer = reader.read();

                while (buffer != -1){

                    jsonDAta += (char) buffer;
                    buffer = reader.read();
                }

                return jsonDAta;

            } catch (IOException e) {
                e.printStackTrace();
                return "error network";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Json data - location - -- wowoow- o -o ",s);
            try {
                JSONObject obj = new JSONObject(s);
                String location = obj.getString("Response");
                JSONArray obj2 = new JSONObject(location).getJSONArray("View");
                String obj3 = obj2.getString(0);
                JSONArray obj4 = new JSONObject(obj3).getJSONArray("Result");
                String obj5 =  obj4.getString(0);
                String obj6 = new JSONObject(obj5).getString("Location");
                String obj7 = new JSONObject(obj6).getString("DisplayPosition");
                String latitude = new JSONObject(obj7).getString("Latitude");
                String longitude = new JSONObject(obj7).getString("Longitude");


                Log.i("Json data - location - -- wowoow- o -o ",obj5);

                Log.i("latitiude - longitude -",latitude);
                Log.i("longitude - ",longitude);
                TextView txt1 = (TextView)findViewById(R.id.textView3);
                txt1.setText(latitude);
                TextView txt2 = (TextView)findViewById(R.id.textView4);
                txt2.setText(longitude);

                downloadweather downweatherhand = new downloadweather();

                downweatherhand.execute("https://api.darksky.net/forecast/8f03e2f020812fb014e7541fb9a37720/"+latitude+","+longitude);





            } catch (JSONException e) {
                e.printStackTrace();
            }





        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







    }




}

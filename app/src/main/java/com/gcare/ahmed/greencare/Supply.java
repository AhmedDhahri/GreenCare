package com.gcare.ahmed.greencare;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static java.net.Proxy.Type.HTTP;

public class Supply extends AppCompatActivity {


    FrameLayout f;
    EditText e;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply);
        f = findViewById(R.id.wait2);
        e = findViewById(R.id.input);
        t = findViewById(R.id.t);
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                    ;
                else if(Double.parseDouble(s.toString()) > 5)
                    e.setText("5");
                else if(Double.parseDouble(s.toString()) < 0)
                    e.setText("0");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void b_ok(View view) {
        new wait().execute();
    }

    public void b_no(View view) {
        this.finish();
    }

    public void update_txt(View view) {
        new wait1().execute();
    }
    class wait extends AsyncTask<Void, Void, Void>{

        URL url;
        HttpURLConnection cnx;
        @Override
        protected void onPreExecute(){
            f.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            String req = "http://greencare10.000webhostapp.com/write1.php?V=" + e.getText().toString();
            Log.i("abacad","http://greencare10.000webhostapp.com/write1.php?V=" + e.getText().toString());
            try {
                url = new URL(req);
                cnx = (HttpURLConnection) url.openConnection();
                cnx.setRequestMethod("GET");
                int responseCode = cnx.getResponseCode();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v){
            f.setVisibility(View.INVISIBLE);
            cnx.disconnect();
        }
    }
    class wait1 extends AsyncTask<Void, Void, Void>{


        String date;
        @Override
        protected void onPreExecute(){
            f.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String str = "";
            try {
                URL url = new URL("https://greencare10.000webhostapp.com/store.json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(60000);
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String ln;
                while((ln = in.readLine()) != null){
                    str = ln;
                }
                in.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                JSONObject o = new JSONObject(str);
                date = o.getString("dte");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v){
            f.setVisibility(View.INVISIBLE);
            t.setText("Last Irrigation:" + date);
        }
    }
}
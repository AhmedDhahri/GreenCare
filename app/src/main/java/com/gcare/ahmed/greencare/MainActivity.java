package com.gcare.ahmed.greencare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView T;
    TextView M;
    TextView H;
    TextView D;
    GButton refresh;
    FrameLayout f;
    ProgressBar p;
    String str="";
    static Status result = new Status();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        T = findViewById(R.id.T);
        M = findViewById(R.id.M);
        f = findViewById(R.id.wait);
        H = findViewById(R.id.H);
        D = findViewById(R.id.D);
        p = findViewById(R.id.progressBar);
        refresh = findViewById(R.id.refresh);
        f.setVisibility(View.GONE);
        Intent startIntent = new Intent(getApplicationContext(), Alerte.class);
        startService(startIntent);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        LinearLayout top = findViewById(R.id.top);
        View.OnTouchListener L = new View.OnTouchListener() {
            float t1;
            float t2;
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                Log.i("Action","Touch");
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    t1 = event.getX();
                    Log.i("Action","Down");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    t2 = event.getX();
                    Log.i("event",""+t1+"--"+t2);
                    if((t2-t1) < 200)
                        startActivity(new Intent(MainActivity.this, Graph.class));
                    else{
                        t1 = 0;
                        t2 = 0;
                    }
                }
                return true;
            }

        };
        top.setOnTouchListener(L);
        top.performClick();


    }

    public void refresh() {
        new update().execute();
    }

    public void settings(View view) {
        startActivity(new Intent(MainActivity.this,Settings.class));
    }

    public void supply(View v){
        startActivity(new Intent(this, Supply.class));
    }

    public void Graph(View view) {


    }

    class update extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){
            f.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... voids) {
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
                //to change
                JSONArray t = o.getJSONArray("temp");
                JSONArray h = o.getJSONArray("hum");
                JSONArray m = o.getJSONArray("soilm");
                JSONArray d = o.getJSONArray("dte");

                int length = Math.min(t.length(),Math.min(h.length(),m.length()));

                Graph.temp = new int[length];
                Graph.hum = new int[length];
                Graph.soilm = new int[length];
                Graph.dte = new String[length];

                for(int i = 0;i<length;i++){
                    Graph.temp [i] = t.getInt(i);
                    Graph.hum[i] = h.getInt(i);
                    Graph.soilm[i] = m.getInt(i);
                    Graph.dte[i] = d.getString(i);
                }

                result = new com.gcare.ahmed.greencare.Status(Graph.temp[length-1],Graph.hum[length-1],Graph.soilm[length-1],Graph.dte[length-1]);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void v){
            T.setText(" " + result.temperature + " °C");
            H.setText(" "+result.humidity+" %");
            M.setText(" "+result.soilm+" %");
            D.setText("Last Update: "+result.date);
            f.setVisibility(View.INVISIBLE);
        }
    }

}
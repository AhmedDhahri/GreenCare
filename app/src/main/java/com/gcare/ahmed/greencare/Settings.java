package com.gcare.ahmed.greencare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.net.SocketException;
import java.net.UnknownHostException;


public class Settings extends AppCompatActivity {

    SeekBar sleep;
    SeekBar set;
    int  sleep_time = 30;
    int  set_time = 30;
    TextView Sbar_show;
    EditText ssid;
    EditText pass;
    Settings_info info;
    FrameLayout f;
    Network n;
    ConnectivityManager connManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        try {
            n = new Network(getApplicationContext());
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        f = findViewById(R.id.wait1);
        ssid = findViewById(R.id.ssid);
        pass = findViewById(R.id.pass);
        sleep = findViewById(R.id.seekBar);
        set = findViewById(R.id.seekBar2);
        Sbar_show = findViewById(R.id.sbar_show);
        sleep.setProgress(sleep_time);
        set.setProgress(set_time);
        String _ssid = n.getWifiSSID();
        ssid.setText(_ssid.substring(1,_ssid.length()-1));
        sleep.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 10)
                    sleep.setProgress(10);
                sleep_time = progress;
                Sbar_show.setVisibility(View.VISIBLE);
                Sbar_show.setText(progress+" minutes");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Sbar_show_v().execute();
            }
        });
        set.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 10) {
                    set.setProgress(10);
                }
                set_time = progress;
                Sbar_show.setVisibility(View.VISIBLE);
                Sbar_show.setText(progress+" seconds");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Sbar_show_v().execute();
            }
        });
    }
    void b_ok(View v){
        info =  new Settings_info(ssid.getText().toString(), pass.getText().toString(), set_time, sleep_time);
        new _wait().execute();
        n.Connect();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    n.Connect();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(n.isConnected(connManager))
                        break;
                }
                n.Listen();
                n.Send(info);
            }
        }).start();
    }

    void b_no(View v){
        this.finish();
    }


    class Sbar_show_v extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v){
            Sbar_show.setVisibility(View.INVISIBLE);
        }
    }

    class _wait extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){
            f.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            while(!n.done);
            return null;
        }
        @Override
        protected void onPostExecute(Void v){
            f.setVisibility(View.INVISIBLE);
        }
    }
}

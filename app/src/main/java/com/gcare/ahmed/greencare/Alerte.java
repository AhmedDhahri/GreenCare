package com.gcare.ahmed.greencare;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Alerte extends Service {
    int n = 10;
    MediaPlayer p;
    Thread get = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    URL url = new URL("http://adtun96.000webhostapp.com/store.json");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(60000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String ln;
                    while ((ln = in.readLine()) != null) {
                        str = ln;
                    }
                    in.close();
                    conn.disconnect();
                } catch (Exception e) {
                    Log.d("MyTag", e.toString());
                }
                try {
                    JSONObject o = new JSONObject(str);
                    JSONArray tArray = o.getJSONArray("temp");
                    JSONArray hArray = o.getJSONArray("hum");
                    JSONArray mArray = o.getJSONArray("soilm");

                    t = tArray.getInt(tArray.length()-1);
                    h = hArray.getInt(hArray.length()-1);
                    m = mArray.getInt(mArray.length()-1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if ((t < 25) || (h < 30) || (m < 60) || (m > 90))
                    send_notification("Green Care Alerte", "Check your plant's condition!");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
    });
    String str = "";
    int t,h,m;
    public Alerte() {
    }


    @Override
    public  void onCreate(){
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServiceWithNotification();
        Notification notification = new Notification();
        startForeground(1, notification);
        if(!get.isAlive())
            get.start();
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
        this.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void send_notification(String title,String msg){
        Log.i("starting","hey I 'm here");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"GCare")
                .setSmallIcon(R.drawable.ic_icon)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        notificationManager.notify(1, mBuilder.build());
    }

    void startServiceWithNotification() {

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setAction("kh");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);


        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker("")
                .setContentText("")
                .setContentIntent(contentPendingIntent)
                .setOngoing(true)
                .build();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;
        startForeground(500, notification);
    }
}

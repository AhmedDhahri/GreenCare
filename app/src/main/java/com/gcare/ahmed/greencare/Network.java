package com.gcare.ahmed.greencare;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import static android.content.Context.WIFI_SERVICE;

public class Network {
    byte[] buf_tx;
    byte[] buf_rx;
    int port = 4000;
    InetAddress server;
    DatagramSocket udp = new DatagramSocket(null);

    static boolean done = false;
    WifiConfiguration conf;
    WifiManager mngr;
    Context context;
    public Network(Context APPcontext) throws SocketException, UnknownHostException {
        context = APPcontext;
        buf_rx = new byte[]{'N', 'N'};
        udp.setReuseAddress(true);
        udp.setBroadcast(true);
        udp.bind(new InetSocketAddress(port));
        //server = InetAddress.getByName(getApIpAddr(MainActivity.this));
        server = InetAddress.getByName("192.168.1.22");

    }

    void Send(Settings_info i){
        buf_tx = i.getData().getBytes();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramPacket p_tx = new DatagramPacket(buf_tx,buf_tx.length,server,port);
                try {
                    udp.send(p_tx);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    void Listen(){
        done = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = "NN";
                DatagramPacket p_rx = new DatagramPacket(buf_rx,2);
                while(!s.equals("OK")){
                    try {
                        udp.receive(p_rx);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    s = new String(buf_rx,0,2);
                    Log.i("ooooo",s);
                }

                Log.i("ooooo","dggggg");
                done = true;
            }
        }).start();

    }

    void Connect(){
        conf = new WifiConfiguration();
        mngr = (WifiManager) context.getSystemService(WIFI_SERVICE);
        conf.SSID = "\"GreenCare\"";
        conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        int netID = mngr.addNetwork(conf);
        mngr.disconnect();
        mngr.enableNetwork(netID, true);
        mngr.reconnect();
    }

    boolean isConnected(ConnectivityManager cnxmgr){
        NetworkInfo mWifi = cnxmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mngr = (WifiManager) context.getSystemService(WIFI_SERVICE);
        while(true){
            if((mWifi.isConnected()) && (mngr.getConnectionInfo().getSSID().equals("\"GreenCare\"")))
                return true;
        }
    }
    String getWifiSSID(){
        mngr = (WifiManager) context.getSystemService(WIFI_SERVICE);
        return mngr.getConnectionInfo().getSSID();
    }
}

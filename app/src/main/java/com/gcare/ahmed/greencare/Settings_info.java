package com.gcare.ahmed.greencare;

public class Settings_info {
    String SSID;
    String PASS;
    int sleep;
    int set;
    public Settings_info(String SSID, String PASS, int  set, int  sleep){
        this.SSID = SSID;
        this.PASS = PASS;
        this.sleep = sleep;
        this.set = set;
    }
    String getData(){
        char Bell = (char) 7;
        return (SSID + Bell + PASS + Bell + ((char)sleep) + Bell + ((char)set));
    }
}
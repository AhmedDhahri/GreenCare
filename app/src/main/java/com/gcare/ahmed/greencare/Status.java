package com.gcare.ahmed.greencare;

public class Status {
    int temperature = 0;
    int humidity = 0;
    int soilm = 0;
    String date = "";
    public Status(int temperature,int humidity,int soilm, String date){
        this.date = date;
        this.humidity = humidity;
        this.soilm = soilm;
        this.temperature = temperature;
    }
    public Status(){}
}

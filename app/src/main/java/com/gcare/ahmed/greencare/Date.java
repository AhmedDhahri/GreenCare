package com.gcare.ahmed.greencare;


public class Date {
    int year = 2018;
    int month = 1;
    int day = 1;
    float hours = 0f;

    Date(String s){
        String p[] = s.split("-");
        year = Integer.parseInt(p[0]);
        month = Integer.parseInt(p[1]);
        day = Integer.parseInt(p[2]);
        if(p[7].equals("pm"))
            hours = 12 + Integer.parseInt(p[4])+((float)Integer.parseInt(p[5])/60)+((float)Integer.parseInt(p[6])/3600);
        else
            hours = Integer.parseInt(p[4])+((float)Integer.parseInt(p[5])/60)+((float)Integer.parseInt(p[6])/3600);
    }
    float xCord(){
        float f = 0f;
        f = (year-2018) * ydays(year) + (month-1) * mdays(month-1,year) + day + (hours/24);
        return f;
    }

    int mdays(int m,int y) {
        int[] Marray = {31,0,31,30,31,30,31,31,30,31,30,31};
        if((y%4) == 0)
            Marray[1] = 29;
        else
            Marray[1] = 28;
        return Marray[m];
    }

    int ydays(int y) {
        if((y%4) == 0)
            return 366;
        else
            return  365;
    }

}

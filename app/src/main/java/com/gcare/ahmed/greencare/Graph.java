package com.gcare.ahmed.greencare;


import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM;

public class Graph extends AppCompatActivity{

    LineChart mChart;
    static int[] temp;
    static int[] hum;
    static int[] soilm;
    static String[] dte;
    int choice = 3;
    Bundle s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s = savedInstanceState;
        setContentView(R.layout.graph);
        ((RelativeLayout)findViewById(R.id.container)).setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.back,null));
        mChart = findViewById(R.id.chart1);
        mChart.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.back,null));
        mChart.setDrawGridBackground(true);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(BOTTOM);
        YAxis y = mChart.getAxisRight();
        y.setEnabled(false);
        draw(graph(0));
    }
    List<Entry> chart(int[] array){
        List<Entry> e = new ArrayList<Entry>();
        Date d0 = new Date(dte[dte.length-1]);
        float x0 = d0.xCord();
        for(int i = 0;i < dte.length; i++){
            e.add(new Entry(new Date(dte[i]).xCord()-x0,array[i]));
        }
        return e;
    }
    List<ILineDataSet> graph(int choice){
        List<ILineDataSet> l1 = new ArrayList<ILineDataSet>();
        if(choice == 0){
            l1 = new ArrayList<ILineDataSet>();
            List<Entry> e1 = chart(temp);
            ILineDataSet ld1 = new LineDataSet(e1,"T");
            ld1.setColor(255,40,40);
            ld1.setLineColor(255,40,40);
            l1.add(ld1);
        }
        if(choice == 1){
            l1 = new ArrayList<ILineDataSet>();
            List<Entry> e2 = chart(hum);
            ILineDataSet ld2 = new LineDataSet(e2,"H");
            ld2.setColor(0,26,255);
            ld2.setLineColor(64,160,255);
            l1.add(ld2);
        }
        if(choice == 2){
            l1 = new ArrayList<ILineDataSet>();
            List<Entry> e3 = chart(soilm);
            ILineDataSet ld3 = new LineDataSet(e3,"M");
            ld3.setColor(128,255,0);
            ld3.setLineColor(128,255,128);
            l1.add(ld3);
        }
        if(choice == 3){
            l1 = new ArrayList<ILineDataSet>();

            List<Entry> e1 = chart(temp);
            ILineDataSet ld1 = new LineDataSet(e1,"T");
            ld1.setColor(255,40,40);
            ld1.setLineColor(255,40,40);
            l1.add(ld1);

            List<Entry> e2 = chart(hum);
            ILineDataSet ld2 = new LineDataSet(e2,"H");
            ld2.setColor(0,26,255);
            ld2.setLineColor(64,160,255);
            l1.add(ld2);

            List<Entry> e3 = chart(soilm);
            ILineDataSet ld3 = new LineDataSet(e3,"M");
            ld3.setColor(128,255,0);
            ld3.setLineColor(128,255,128);
            l1.add(ld3);
        }
        return l1;
    }
    void draw(List<ILineDataSet> l1){
        mChart.setData(new LineData(l1));
        Description d = new Description();
        d.setText("des");
        mChart.setDescription(d);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        mChart.invalidate();
        mChart.getData().setValueTextSize(20);
    }

    public void refSw(View view) {
        if(choice != 3)
            choice++;
        else
            choice = 0;
        draw(graph(choice));
    }
}

package com.gcare.ahmed.greencare;


import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import android.widget.TextView;

import com.github.mikephil.charting.data.PieDataSet;


public class GButton extends android.support.v7.widget.AppCompatImageView{

    int dark = ResourcesCompat.getColor(getResources(),R.color.white3,null);
    int dark1 = ResourcesCompat.getColor(getResources(),R.color.dark1,null);
    int white = ResourcesCompat.getColor(getResources(),R.color.white5,null);
    int white1 = ResourcesCompat.getColor(getResources(),R.color.white6,null);
    int back = ResourcesCompat.getColor(getResources(),R.color.back,null);
    int green = ResourcesCompat.getColor(getResources(),R.color.green,null);
    int blue = ResourcesCompat.getColor(getResources(),R.color.blue1,null);

    int offset = 0;
    Bitmap bmap;
    Canvas cvas;
    Canvas cvas2;
    int w;
    int h;
    int left;

    Paint mPaint = new Paint();
    Paint mPaint2 = new Paint();
    RectF mRectF = new RectF();
    LinearGradient g1;
    LinearGradient g2;

    OnTouchListener l;
    ValueAnimator vA;
    ValueAnimator nvA;
    String Text = "";
    int textSize = 30;

    XmlResourceParser x = getResources().getLayout(R.layout.graph);


    public GButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        //String s = attrs.getAttributeValue(null,"text");

        for(int i =0;i<attrs.getAttributeCount();i++){
            Log.i("attr",""+attrs.getAttributeName(i));
            if(attrs.getAttributeName(i).equals("text"))
                Text = attrs.getAttributeValue(i);
            if(attrs.getAttributeName(i).equals("textSize"))
                textSize = attrs.getAttributeIntValue(i,20);
        }
        vA = ValueAnimator.ofObject(new ArgbEvaluator(),blue,white);
        vA.setDuration(250);
        vA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawButton((int) animation.getAnimatedValue(),white);
            }
        });
        nvA = ValueAnimator.ofObject(new ArgbEvaluator(),white,blue);
        nvA.setDuration(1000);
        nvA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawButton((int) animation.getAnimatedValue(),white);
            }
        });
        l = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN){
                    vA.start();
                    return false;
                }
                if(e.getAction() == MotionEvent.ACTION_UP) {
                    nvA.start();
                    return false;
                }
                return true;
            }
        };
        setOnTouchListener(l);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
        w = getWidth();
        h = getHeight();

        drawButton(blue,white);
        GButton g = findViewById(R.id.button);
    }
    public void drawButton(int color,int back){

        bmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        this.setImageBitmap(bmap);
        cvas = new Canvas(bmap);
        cvas2 = new Canvas(bmap);
        //cvas.drawColor(back);
        g1 = new LinearGradient(w/2,0f,49*w/50,h,color,back,LinearGradient.TileMode.CLAMP);
        g2 = new LinearGradient(w/50,0f,w/2,h,back,color,LinearGradient.TileMode.CLAMP);

        mPaint2.setColor(dark1);
        mPaint2.setStrokeWidth(0);
        mPaint2.setStrokeCap(Paint.Cap.ROUND);
        mRectF.set(1, 1, w-1,h-1);
        cvas2.drawRoundRect(mRectF,w/20, w/20, mPaint2);

        mPaint.setShader(g2);
        mPaint.setStrokeWidth(0);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mRectF.set(w/50+2, 2, 3*w/4,h-2);
        cvas.drawRoundRect(mRectF,w/20, w/20, mPaint);

        mPaint.setShader(g1);
        mPaint.setStrokeWidth(0);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mRectF.set(w/4, 2, 49*w/50-2,h-2);
        cvas.drawRoundRect(mRectF,w/20, w/20, mPaint);

        mPaint2.setColor(Color.WHITE);
        mPaint2.setTextSize(textSize);
        cvas2.drawText(Text,(float) (w/2-Text.length()*textSize/4.1),textSize/5 + h/2,mPaint2);

        mPaint2.setColor(white1);
        mRectF.set(w/50+2,2,49*w/50-2,h-2);
        cvas2.drawRoundRect(mRectF,w/20,w/20,mPaint2);

        this.invalidate();
    }
}

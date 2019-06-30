package com.gcare.ahmed.greencare;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;



public class Image extends android.support.v7.widget.AppCompatImageView {
    int c1 = ResourcesCompat.getColor(getResources(),R.color.c1,null);
    int c2 = ResourcesCompat.getColor(getResources(),R.color.c2,null);
    int c3 = ResourcesCompat.getColor(getResources(),R.color.c3,null);
    int c4 = ResourcesCompat.getColor(getResources(),R.color.c4,null);

    int l = 0; int t = 0; int r = 0; int b = 0;
    //static float x = 0f;
    static float y = 0f;
    static float x = 0f;
    ValueAnimator animatorx;
    ValueAnimator animatorxr;
    ValueAnimator animatory;
    ValueAnimator animatoryr;

    AnimatorSet set = new AnimatorSet();
    public Image(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void onLayout(boolean changed ,int l, int t, int r, int b){
        this.l = l; this.t = t; this.r = r; this.b = b;
        animatorx = ValueAnimator.ofFloat(0f,(r-l));
        animatorxr = ValueAnimator.ofFloat((r-l),0f);
        animatory = ValueAnimator.ofFloat(0f,(b-t));
        animatoryr = ValueAnimator.ofFloat((b-t),0f);
        animatorx.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x = (Float) animation.getAnimatedValue();
                draw_backg((Float) animation.getAnimatedValue());
            }
        });
        animatorxr.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x = (Float) animation.getAnimatedValue();
                draw_backg((Float) animation.getAnimatedValue());
            }
        });
        animatory.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y = (Float) animation.getAnimatedValue();
                draw_backg(x);
            }
        });
        animatoryr.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y = (Float) animation.getAnimatedValue();
                draw_backg(x);
            }
        });
        animatorx.setDuration(1000);
        animatorxr.setDuration(750);
        animatory.setDuration(100);
        animatoryr.setDuration(2000);
        ValueAnimator.setFrameDelay(1);
        set.playSequentially(animatorx,animatory,animatorxr,animatoryr);
        set.addListener(new AnimatorListenerAdapter() {
            private boolean mCanceled;

            @Override
            public void onAnimationStart(Animator animation) {
                mCanceled = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mCanceled) {
                    animation.start();
                }
            }
        });
        set.start();
    }
    void draw_backg(float _x) {
        x = _x;
        Paint mp = new Paint();
        Bitmap bm = Bitmap.createBitmap(r-l,b-t,Bitmap.Config.ARGB_8888);
        this.setImageBitmap(bm);
        Canvas cvs = new Canvas(bm);
        RadialGradient rg = new RadialGradient(x,y,(float)(r-l), c1, c2, Shader.TileMode.MIRROR);
        mp.setShader(rg);
        RectF rect = new RectF();
        rect.set(l,t,r,b);
        cvs.drawRect(rect,mp);
        LinearGradient lg = new LinearGradient(l,t,r,b, c3, c4, Shader.TileMode.MIRROR);
        mp.setShader(lg);
        cvs.drawRect(rect,mp);
        this.invalidate();
    }
}

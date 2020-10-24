package com.github.abdalimran.tourmate.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.abdalimran.tourmate.R;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreenActivity extends Activity {
    final static int SPLASH_TIME_OUT = 5000;
    private boolean scheduled = false;
    private Timer timer;
    private TextView splash_app_title;
    private Animation anim;
    private LinearLayout linlay;
    private ImageView splashimg;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        linlay=(LinearLayout) findViewById(R.id.lin_lay);
        splash_app_title= (TextView) findViewById(R.id.splash_app_title);
        splashimg = (ImageView) findViewById(R.id.splashview);

        Typeface appTitleFont = Typeface.createFromAsset(getAssets(),"fonts/Impregnable.ttf");
        splash_app_title.setTypeface(appTitleFont);
        StartAnimations();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    private void StartAnimations() {
//           Set R.anim.fade_out below to give fade out effect
        anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        anim.reset();
        linlay.clearAnimation();
        linlay.startAnimation(anim);
        splashimg.clearAnimation();
        splashimg.startAnimation(anim);

        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                Intent home_page = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(home_page);
                finish();
            }}, SPLASH_TIME_OUT);
        this.scheduled=true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        overridePendingTransition(0,0);
        if (this.scheduled) {
            this.timer.cancel();
        }
        this.timer.purge();
    }
}
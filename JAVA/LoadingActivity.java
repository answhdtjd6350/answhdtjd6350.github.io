package com.example.jongseong3;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

public class LoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView splashgif = (ImageView)findViewById(R.id.loading);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.wave);
        splashgif.setAnimation(anim);
        TextView one = (TextView)findViewById(R.id.onecare);
        Animation animm = AnimationUtils.loadAnimation(this,R.anim.splash);
        one.setAnimation(animm);
        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                finish();
            }
        }, 3000);
    }
}

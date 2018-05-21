package khantique.organisation.com.khantique;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    ImageView mc9img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mc9img = (ImageView) findViewById(R.id.img_second);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.myanim);
        mc9img.startAnimation(hyperspaceJumpAnimation);


        Thread splashthread = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    });
                    finish();
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        splashthread.start();

    }
}

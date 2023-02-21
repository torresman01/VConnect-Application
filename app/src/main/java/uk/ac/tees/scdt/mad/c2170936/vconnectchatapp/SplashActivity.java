package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appName = findViewById(R.id.app_Name);

        //change font
        Typeface typeface = ResourcesCompat.getFont(this,R.font.blacklist);
        appName.setTypeface(typeface);

        //add animation
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        appName.setAnimation(anim);

        new Thread() {

            @Override
            public void run()
            {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }.start();
    }
}
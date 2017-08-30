package mmustapha.g_hub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import mmustapha.g_hub.Index.IndexActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), IndexActivity.class));
            }
        };
        new Handler().postDelayed(r, 2500);
    }
}

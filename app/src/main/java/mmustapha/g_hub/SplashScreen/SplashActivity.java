package mmustapha.g_hub.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import mmustapha.g_hub.Index.IndexActivity;
import mmustapha.g_hub.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                new CreateSQLiteDB(getApplicationContext()).execute();
            }
        };
        new Handler().postDelayed(r, 1500);
    }
}

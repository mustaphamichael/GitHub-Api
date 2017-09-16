package mmustapha.g_hub.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;


import mmustapha.g_hub.Index.IndexActivity;
import mmustapha.g_hub.Models.SQLite.DBHelper;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mmustapha on 9/14/17.
 * Create Local SQLite DB while displaying Splash screen for the first time
 */

public class CreateSQLiteDB extends AsyncTask<Void, Void, Void> {

    private Context mCtx;
    private DBHelper mDBHelper;
    private SharedPreferences mPreference;
    private final String PREF_NAME = "com.dev.mmustapha.ghub.SplashScreen.SharedPreference";
    private final String IS_FIRST = "com.dev.mmustapha.ghub.SplashScreen.IsFirst";

    public CreateSQLiteDB(Context context){
        mCtx = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mPreference = mCtx.getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Check for App's First Launch
        if (mPreference.getBoolean(IS_FIRST, true)){
            Log.e("MIKE'S TAG", "First App Launch");
            mDBHelper = new DBHelper(mCtx);
            mDBHelper.getWritableDatabase(); // Creates the DB on First Launch
            mPreference.edit().putBoolean(IS_FIRST, false).apply(); // Change the instance as false
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        Intent intent = new Intent(mCtx, IndexActivity.class);
            if (intent.resolveActivity(mCtx.getPackageManager()) != null){
            mCtx.startActivity(intent);
        }
    }
}

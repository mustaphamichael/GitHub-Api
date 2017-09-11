package mmustapha.g_hub.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by mmustapha on 8/30/17.
 */

public class InternetCheck extends AsyncTask<Void, Void, Boolean>{

    private IPListener mListener;
    private Context mCtx;
    private int mStatus;

    public InternetCheck(Context context, IPListener listener){
        mCtx = context;
        mListener = listener;
    }

    /**
     * Run Internet check in background
     * @param voids
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        final Runnable checkIt = new Runnable() {
            public void run() {
                isConnected();
            }
        };

        // Scheduler for Internet Connectivity Check
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // Executes the run task in background with 0s delay at 10s interval
        final ScheduledFuture internetHandle =
                scheduler.scheduleAtFixedRate(checkIt, 0, 15, SECONDS);
        internetHandle.cancel(testConnection());
        return isConnected();
    }

    /**
     * Check if device is connected to a network and has access to the internet
     * @return
     */
    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            Log.e("INCC_1", "Connected to a Network");
                if (testConnection()){
                    Log.e("INCC_2", "Granted Network Access");
                    return true;
                } else {
                    Log.e("INCC_2", "No Granted Access");
                    return false;
                }
        }
        else return false;
    }

    /**
     * Make a connection request to the internet
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Boolean testConnection() {
        String testURL = "https://google.com"; // Used "https://api.github.com" but got API Limitation Call Error. Google won't fail :)
        try {
            URL url = new URL(testURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            mStatus = con.getResponseCode();
            con.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mStatus == 200;
    }

    /**
     * Retrieve result of internet check
     * @param checker
     */
    @Override
    protected void onPostExecute(final Boolean checker) {
        super.onPostExecute(checker);
        mListener.getResponse(checker);
    }
}

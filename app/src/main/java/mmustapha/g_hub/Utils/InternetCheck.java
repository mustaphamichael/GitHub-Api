package mmustapha.g_hub.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by mmustapha on 8/30/17.
 */

public class InternetCheck extends AsyncTask<Void, Void, Boolean>{

    private IPListener mListener;
    private Activity mActivity;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); // Scheduler for Internet Connectivity Check

    public InternetCheck(Activity activity, IPListener listener){
        mActivity = activity;
        mListener = listener;
    }

    /**
     * Run Internet check in background
     * @param voids
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        return isConnected();
    }

    /**
     * Check if device is connected to a network and has access to the internet
     * @return
     */
    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager)mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()){
            Log.e("INCC_1", "Connected to a Network");
            try {
                if (testConnection()){
                    Log.e("INCC_2", "Granted Network Access");
                    return true;
                } else {
                    Log.e("INCC_2", "No Granted Access");
                    return false;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
//        return true;
    }

    /**
     * Make a connection request to the internet
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Boolean testConnection() throws IOException, InterruptedException {
        String testURL = "https://google.com"; // Used "https://api.github.com" but got API Limitation Call Error. Google won't fail :)
        URL url = new URL(testURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        int status = con.getResponseCode();
        con.disconnect();
        if (status == 200){
            return true;
        }
        else return false;
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

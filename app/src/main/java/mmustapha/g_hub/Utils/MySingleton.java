package mmustapha.g_hub.Utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mmustapha on 8/29/17.
 */

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private MySingleton(Context context){
        mCtx = context;
        mRequestQueue = getmRequestQueue();
    }

    public static synchronized MySingleton getmInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getmRequestQueue(){
        if (mRequestQueue == null){
            if (mCtx.getApplicationContext() != null) {
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
        }
        return mRequestQueue;
    }

    public <T> RequestQueue addToRequestQueue(Request<T> request){
        getmRequestQueue().add(request);
        return null;
    }
}

package mmustapha.g_hub.Models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import mmustapha.g_hub.Utils.CustomListener;
import mmustapha.g_hub.Utils.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mmustapha on 8/29/17.
 */

public class ServerResponse {
    Context mCtx;
    String listURL = "https://api.github.com/search/users?q=language:java+location:lagos";
    String profileURL = "https://api.github.com/users/";

    public ServerResponse(Context context){
        this.mCtx = context;
    }

    /**
     * Method to fetch list of Java Developers via API (in JSON format)
     * The listener is an Utils interface allows the JSON Response to be accessible to other classes
     * @param listener
     */
    public void fetchList(final CustomListener<Object> listener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, listURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                // The 'items' array is parsed from the JSON Response
                                JSONArray array = (JSONArray) response.get("items");
                                listener.getResult(array);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // MySingleton is a custom Utils class for creating an instance of Volley RequestQueue
        MySingleton.getmInstance(mCtx.getApplicationContext()).addToRequestQueue(request);
    }

    public void fetchDevProfile(String userName, final CustomListener<Object> listener){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, profileURL+userName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.getResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // MySingleton is a custom Utils class for creating an instance of Volley RequestQueue
        MySingleton.getmInstance(mCtx.getApplicationContext()).addToRequestQueue(request);
    }
}

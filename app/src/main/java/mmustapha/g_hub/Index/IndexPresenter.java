package mmustapha.g_hub.Index;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mmustapha.g_hub.Index.Adapters.Developer;
import mmustapha.g_hub.Models.ServerResponse;
import mmustapha.g_hub.Utils.CustomListener;

/**
 * Created by mmustapha on 8/29/17.
 */

public class IndexPresenter implements IndexContract.Presenter {
    private IndexFragment mView;
    private JSONObject mJSONObject;
    private Developer mDeveloper;


    public IndexPresenter(IndexFragment view){
        mView = view;

    }


    @Override
    public void getDeveloperList() {
        ServerResponse response = new ServerResponse(mView.getContext());
        response.fetchList(new CustomListener<Object>() {
            @Override
            public void getResult(Object object) {
                JSONArray itemsArray = (JSONArray)object;
                for (int i=0; i<=itemsArray.length(); i++){
                    try {
                        mJSONObject = (JSONObject)itemsArray.get(i);
                        String userName = mJSONObject.getString("login");
                        String imageURL = mJSONObject.getString("avatar_url");
//                        populateSQLiteDB(userName, imageURL, false); // Populate SQLite DB
                        Log.e("RESPONSE", userName);
                        mDeveloper = new Developer();
                        mDeveloper.setUserName(userName);
                        mDeveloper.setImageURL(imageURL);
                        mDeveloper.setIsFavourite(false);
                        mView.onSuccess(mDeveloper);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

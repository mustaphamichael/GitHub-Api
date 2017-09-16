package mmustapha.g_hub.Index;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import mmustapha.g_hub.Index.Adapters.Developer;
import mmustapha.g_hub.Models.SQLite.DBContract;
import mmustapha.g_hub.Models.SQLite.DBController;
import mmustapha.g_hub.Models.ServerResponse;
import mmustapha.g_hub.Utils.CustomListener;
import mmustapha.g_hub.Utils.IPListener;
import mmustapha.g_hub.Utils.InternetCheck;

/**
 * Created by mmustapha on 8/29/17.
 */

public class IndexPresenter implements IndexContract.Presenter {
    private IndexFragment mView;
    private JSONObject mJSONObject;
    private Developer mDeveloper;
    private DBController mDBController;
    private InternetCheck check;

    public IndexPresenter(IndexFragment view) {
        mView = view;
        mDBController = new DBController(view.getContext());
    }

    /**
     * Pass Developer details to the View
     */
    @Override
    public void getDeveloperList() {
        this.getServerResponse(mView.GET_LIST);
        int arrayLength = mDBController.fetchList().size();
        for (int i=0; i< arrayLength; i++){
            Developer dev = mDBController.fetchList().get(i);
            mView.onSuccess(dev);
        }
    }

    /**
     * Insert Data into Local SQLite DB
     * @param username
     * @param imageURL
     * @param isFav
     */
    private void populateLocalDB(String username, String imageURL, String isFav){
        HashMap<String, String> map = new HashMap<>();
        map.put(DBContract.GHub.USERNAME, username);
        map.put(DBContract.GHub.IMAGE_URL, imageURL);
        map.put(DBContract.GHub.IS_FAV, isFav);
        mDBController.insertPosts(map);
    }

    /**
     * Get Response from API
     */
    @Override
    public void getServerResponse(final String taskId){
        // Check for internet connectivity
        if (mView.getActivity() != null) {
            check = new InternetCheck(mView.getContext(), new IPListener() {
                @Override
                public void getResponse(boolean isConnected) {
                    // If available, populate recycler view
                    if (isConnected) {
                        ServerResponse response = new ServerResponse(mView.getContext());
                        response.fetchList(new CustomListener<Object>() {
                            @Override
                            public void getResult(Object object) {
                                JSONArray itemsArray = (JSONArray) object;
                                for (int i = 0; i <= itemsArray.length(); i++) {
                                    try {
                                        mJSONObject = (JSONObject) itemsArray.get(i);
                                        String userName = mJSONObject.getString("login");
                                        String imageURL = mJSONObject.getString("avatar_url");
                                        if (mDBController.fetchList().size() <= i) {
                                            // Check if Database Rows are available
                                            mDeveloper = new Developer();
                                            mDeveloper.setUserName(userName);
                                            mDeveloper.setImageURL(imageURL);
                                            mDeveloper.setIsFavourite(false);
                                            mView.onSuccess(mDeveloper);
                                        }
                                        mView.hideSwipeRefreshLayout();
                                        populateLocalDB(userName, imageURL, String.valueOf(false));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    // Else re-run the internet check
                    else {
                        switch (taskId){
                            case "REFRESH_LIST":
                                // Check for Internet once
                                mView.hideSwipeRefreshLayout();
                                mView.onFailure("No Internet Connectivity");
                                break;
                            case "GET_LIST":
                                // Continuous Internet Check
                                mView.onFailure("No Internet Connectivity");
                                getServerResponse(mView.GET_LIST);
                                break;

                            default:
                                // Do Nothing
                        }
                    }
                }
            });
            check.execute();
        }
        Log.e("TASK: ", taskId);
    }

    @Override
    public void stopServerResponse() {
        check.cancel(true);
        mView.hideSwipeRefreshLayout();
        System.out.println("CANCELLED!!!!!");
    }
}

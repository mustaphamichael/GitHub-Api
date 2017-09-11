package mmustapha.g_hub.Index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mmustapha.g_hub.Index.Adapters.Developer;
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

    public IndexPresenter(IndexFragment view) {
        mView = view;
    }

    @Override
    public void getDeveloperList() {
        // Check for internet connectivity
        new InternetCheck(mView.getContext(), new IPListener() {
            @Override
            public void getResponse(boolean isConnected) {
                // If available, populate recycler view
                if (isConnected){
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
                // Else re-run the internet check
                else {
                    mView.showToastMessage("No Internet Connectivity");
                    getDeveloperList();
                }
            }
        }).execute();
    }
}

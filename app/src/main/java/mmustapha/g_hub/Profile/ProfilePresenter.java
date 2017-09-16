package mmustapha.g_hub.Profile;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import mmustapha.g_hub.Models.ServerResponse;
import mmustapha.g_hub.Profile.Adapter.DevProfile;
import mmustapha.g_hub.Utils.CustomListener;
import mmustapha.g_hub.Utils.IPListener;
import mmustapha.g_hub.Utils.InternetCheck;

/**
 * Created by mmustapha on 8/29/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileFragment mView;
    private JSONObject mJSONObject;
    private InternetCheck check;

    public ProfilePresenter(ProfileFragment view){
        mView = view;
    }

    @Override
    public void getProfile(final String taskId, final String username) {
        // Check for internet connectivity
        if (mView.getContext() != null) {
            check = new InternetCheck(mView.getContext(), new IPListener() {
                @Override
                public void getResponse(boolean isConnected) {
                    // If available, populate profile screen
                    if (isConnected) {
                        ServerResponse response = new ServerResponse(mView.getContext());
                        response.fetchDevProfile(username, new CustomListener<Object>() {
                            @Override
                            public void getResult(Object object) {
                                mJSONObject = (JSONObject) object;
                                try {
                                    String fullname = mJSONObject.getString("name");
                                    String location = mJSONObject.getString("location");
                                    String userName = mJSONObject.getString("login");
                                    String profileURL = mJSONObject.getString("html_url");
                                    String imageURL = mJSONObject.getString("avatar_url");
                                    String repo = String.valueOf(mJSONObject.getInt("public_repos"));
                                    String followers = String.valueOf(mJSONObject.getInt("followers"));
                                    String following = String.valueOf(mJSONObject.getInt("following"));

                                    DevProfile d = new DevProfile();
                                    d.setFullName(fullname);
                                    d.setLocation(location);
                                    d.setUserName(userName);
                                    d.setProfileURL(profileURL);
                                    d.setImageURL(imageURL);
                                    d.setRepo(repo);
                                    d.setFollowers(followers);
                                    d.setFollowing(following);
                                    mView.onSuccess(d);
                                    mView.hideSwipeRefreshLayout();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    else {
                        switch (taskId){
                            case "GET_DEVELOPER_DETAILS":
                                mView.hideSwipeRefreshLayout();
                                mView.onFailure("No Internet Connectivity");
                                break;
                            default:
                                // Do Nothing
                        }
                    }
                }
            });
            check.execute();
        }
        Log.e("TASK: ", taskId); // Viewing which Task is been ran
    }

    @Override
    public void stopServerResponse() {
        if (check != null){
            check.cancel(true);
            mView.hideSwipeRefreshLayout();
        }
        System.out.println("CANCELLED!!!!!");
    }
}

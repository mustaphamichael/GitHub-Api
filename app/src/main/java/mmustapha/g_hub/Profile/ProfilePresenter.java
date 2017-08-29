package mmustapha.g_hub.Profile;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import mmustapha.g_hub.Models.ServerResponse;
import mmustapha.g_hub.Profile.Adapter.DevProfile;
import mmustapha.g_hub.Utils.CustomListener;

/**
 * Created by mmustapha on 8/29/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileFragment mView;
    private JSONObject mJSONObject;

    public ProfilePresenter(ProfileFragment view){
        mView = view;
    }
    @Override
    public void getProfile(String username) {
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
                    mView.showProfile(d);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

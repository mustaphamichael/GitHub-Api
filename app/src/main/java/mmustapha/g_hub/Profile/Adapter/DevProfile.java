package mmustapha.g_hub.Profile.Adapter;

/**
 * Created by mmustapha on 8/29/17.
 */

public class DevProfile {

    private String mFullName, mLocation, mUserName, mProfileURL, mImageURL, mRepo, mFollowers, mFollowing;


    public void setFullName(String fullname) {
        this.mFullName = fullname;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setProfileURL(String profileURL) {
        this.mProfileURL = profileURL;
    }

    public String getProfileURL() {
        return mProfileURL;
    }

    public void setImageURL(String imageURL) {
        this.mImageURL = imageURL;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setRepo(String repo) {
        this.mRepo = repo;
    }

    public String getRepo() {
        return mRepo;
    }

    public void setFollowers(String followers) {
        this.mFollowers = followers;
    }

    public String getFollowers() {
        return mFollowers;
    }

    public void setFollowing(String following) {
        this.mFollowing = following;
    }

    public String getFollowing() {
        return mFollowing;
    }
}


package mmustapha.g_hub.Profile.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mmustapha on 8/29/17.
 */

public class DevProfile implements Parcelable {

    private String mFullName, mLocation, mUserName, mProfileURL, mImageURL, mRepo, mFollowers, mFollowing;

    public DevProfile() {

    }

    public DevProfile(Parcel in) {
        mFullName = in.readString();
        mLocation = in.readString();
        mUserName = in.readString();
        mProfileURL = in.readString();
        mImageURL = in.readString();
        mRepo = in.readString();
        mFollowers = in.readString();
        mFollowing = in.readString();
    }

    public static final Creator<DevProfile> CREATOR = new Creator<DevProfile>() {
        @Override
        public DevProfile createFromParcel(Parcel in) {
            return new DevProfile(in);
        }

        @Override
        public DevProfile[] newArray(int size) {
            return new DevProfile[size];
        }
    };


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFullName);
        parcel.writeString(mLocation);
        parcel.writeString(mUserName);
        parcel.writeString(mProfileURL);
        parcel.writeString(mImageURL);
        parcel.writeString(mRepo);
        parcel.writeString(mFollowers);
        parcel.writeString(mFollowing);
    }
}


package mmustapha.g_hub.Index.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mmustapha on 8/29/17.
 */

public class Developer implements Parcelable {
    private String mUserName, mImageURL;
    private boolean mIsFavourite;

    public Developer(){
        // Constructor
    }

    protected Developer(Parcel in) {
        mUserName = in.readString();
        mImageURL = in.readString();
        mIsFavourite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserName);
        dest.writeString(mImageURL);
        dest.writeByte((byte) (mIsFavourite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Developer> CREATOR = new Creator<Developer>() {
        @Override
        public Developer createFromParcel(Parcel in) {
            return new Developer(in);
        }

        @Override
        public Developer[] newArray(int size) {
            return new Developer[size];
        }
    };

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setImageURL(String imageURL) {
        this.mImageURL = imageURL;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.mIsFavourite = isFavourite;
    }

    public boolean isFavourite() {
        return mIsFavourite;
    }
}


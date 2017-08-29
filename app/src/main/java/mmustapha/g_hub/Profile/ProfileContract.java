package mmustapha.g_hub.Profile;

import android.content.Context;

import mmustapha.g_hub.BaseView;
import mmustapha.g_hub.Profile.Adapter.DevProfile;

/**
 * Created by mmustapha on 8/29/17.
 */

public class ProfileContract {
    interface View extends BaseView<Presenter>{
        Context getContext();
        void showProfile(DevProfile profile);
        void onShareBtnPress();
    }

    interface Presenter{
        void getProfile(String username);
    }
}

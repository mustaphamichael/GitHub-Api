package mmustapha.g_hub.Index;

import android.content.Context;

import mmustapha.g_hub.BaseView;
import mmustapha.g_hub.Index.Adapters.Developer;

/**
 * Created by mmustapha on 8/29/17.
 */

public class IndexContract {

    interface View extends BaseView<Presenter>{
        Context getContext();
        void onSuccess(Developer dev);
        void onFailure(String errorMessage);
        void showToastMessage(String message);
    }

    interface Presenter{
        void getDeveloperList();
    }
}

package mmustapha.g_hub.Index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import mmustapha.g_hub.Index.Adapters.Developer;
import mmustapha.g_hub.Models.ServerResponse;
import mmustapha.g_hub.Utils.CustomListener;
import mmustapha.g_hub.Utils.IPListener;
import mmustapha.g_hub.Utils.InternetCheck;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by mmustapha on 8/29/17.
 */

public class IndexPresenter implements IndexContract.Presenter {
    private IndexFragment mView;
    private JSONObject mJSONObject;
    private Developer mDeveloper;
    // Scheduler for Internet Connectivity Check
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private InternetCheck internetCheck;
    private boolean isCon;

    public IndexPresenter(IndexFragment view) {
        mView = view;
        internetCheck = new InternetCheck(mView.getActivity(), new IPListener() {
            @Override
            public void getResponse(boolean response) {
                isCon = response;
            }
        });
 }


    @Override
    public void getDeveloperList() {
        checkInternet();
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

    // Checks Internet Availability in Background
    void checkInternet() {
        final Runnable checkIt = new Runnable() {
            public void run() {
             internetCheck.execute(); // Executes the check
            }
        };
        // Executes the run task in background with 0s delay at 5s interval
        final ScheduledFuture<?> internetHandle =
                scheduler.scheduleAtFixedRate(checkIt, 0, 5, SECONDS);
            if (isCon) {
                internetHandle.cancel(true);
            }
            else {
                mView.toastMessage("Check Internet Connectivity");
                internetHandle.cancel(false);
            }
    }
}

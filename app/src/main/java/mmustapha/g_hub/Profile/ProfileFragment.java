package mmustapha.g_hub.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import mmustapha.g_hub.Index.Adapters.Developer;
import mmustapha.g_hub.Profile.Adapter.DevProfile;
import mmustapha.g_hub.R;
import mmustapha.g_hub.Utils.RoundedCornerImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter mPresenter;
    private RoundedCornerImage mProfileImage;
    private TextView mFullName, mLocation, mUserName, mProfileURL, mRepos, mFollowers, mFollowing;
    private ProgressBar mProgressBar;
    String mFullNameStr, mProfileURLStr; // Dev's FullName And ProfileURL in String
    private ProfileFragmentListener mListener;
    private DevProfile mDeveloper;
    private ConstraintLayout mConstraint;
    private SwipeRefreshLayout mSwipeRefresh;

    public final String GET_DEVELOPER_DETAILS = "GET_DEVELOPER_DETAILS";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true); // Enables Options Menu to be displayed on the Toolbar
        // initialize Presenter
        mPresenter = new ProfilePresenter(this);
        setPresenter(mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        mConstraint = view.findViewById(R.id.profile_body);
        mProfileImage = view.findViewById(R.id.dev_image);
        mFullName = view.findViewById(R.id.dev_name);
        mLocation = view.findViewById(R.id.dev_location);
        mUserName = view.findViewById(R.id.username);
        mProfileURL = view.findViewById(R.id.profileURL);
        mRepos = view.findViewById(R.id.repo);
        mFollowers = view.findViewById(R.id.followers);
        mFollowing = view.findViewById(R.id.following);
        mProgressBar = view.findViewById(R.id.progressbar);
        mSwipeRefresh = view.findViewById(R.id.swipeRefresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent,
                R.color.colorPrimary, R.color.colorPrimaryDark);
        return view;
    }

    /**
     * A SwipeRefreshLayout for getting updates on Developer's Profile
     */
    private void onSwipeRefresh(){
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getProfile(GET_DEVELOPER_DETAILS, mListener.getDevName());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("DEVELOPER", mDeveloper); // Save an instance of the Developer's Profile
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getContext() instanceof ProfileFragment.ProfileFragmentListener){
            mListener = (ProfileFragment.ProfileFragmentListener)getContext();
        }
        if (savedInstanceState == null){
            mPresenter.getProfile(GET_DEVELOPER_DETAILS, mListener.getDevName());
        }
        else{
            mDeveloper = savedInstanceState.getParcelable("DEVELOPER");
            onSuccess(mDeveloper);
        }
        onSwipeRefresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) onShareBtnPress(); // Java 8
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * Display Developer's Details on the Screen
     * @param profile
     */
    @Override
    public void showProfile(DevProfile profile) {
        mFullName.setText(profile.getFullName());
        mLocation.setText(profile.getLocation());
        mUserName.setText(profile.getUserName());
        mProfileURL.setText(profile.getProfileURL());
        mRepos.setText(profile.getRepo());
        mFollowers.setText(profile.getFollowers());
        mFollowing.setText(profile.getFollowing());
        // Load Developer's Image using Glide library
        if (getView() != null){
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.git_no_data);
            Glide.with(this)
                    .setDefaultRequestOptions(options)
                    .load(mListener.getImageURL()) // Pass the intent value as Image URL
                    .into(mProfileImage);
        }
        mFullNameStr = profile.getFullName();
        mProfileURLStr = profile.getProfileURL();
    }

    /**
     * Enable Android Share Intent
     */
    @Override
    public void onShareBtnPress() {
        final String shareMessage = getResources().getString(R.string.share_message, mFullNameStr, mProfileURLStr);
            final String shareChooserHeader = getResources().getString(R.string.share_chooser_header);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                intent.setType("text/plain"); // Message as a plain text
                if (intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(Intent.createChooser(intent, shareChooserHeader));
                }
            }

    @Override
    public void onSuccess(DevProfile profile) {
        mDeveloper = profile;
        showProfile(profile);
        mProgressBar.setVisibility(View.GONE); // Hide Progress Bar
        mConstraint.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String errorMessage) {
        if (getView() != null){
            Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void hideSwipeRefreshLayout() {
        mSwipeRefresh.setRefreshing(false);
    }

    /**
     * Force a new call to the server for every profile check
     */
    @Override
    public void onPause() {
        super.onPause();
        mPresenter.stopServerResponse();
    }

    // Listener to get Developer's Name from Parent Activity
    public interface ProfileFragmentListener{
        String getDevName();
        String getImageURL();
    }
}

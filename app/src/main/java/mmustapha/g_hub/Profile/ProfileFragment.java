package mmustapha.g_hub.Profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import mmustapha.g_hub.Profile.Adapter.DevProfile;
import mmustapha.g_hub.R;
import mmustapha.g_hub.Utils.RoundedCornerImage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter mPresenter;
    private RoundedCornerImage mProfileImage;
    private TextView mFullName, mLocation, mUserName, mProfileURL, mRepos, mFollwers, mFollowing;
    private ProfileFragmentListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment IndexFragment.
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true); // Enables Options Menu to be displayed on the Toolbar
        // initialize Presenter
        mPresenter = new ProfilePresenter(this);
        this.setPresenter(mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        mProfileImage = view.findViewById(R.id.dev_image);
        mFullName = view.findViewById(R.id.dev_name);
        mLocation = view.findViewById(R.id.dev_location);
        mUserName = view.findViewById(R.id.username);
        mProfileURL = view.findViewById(R.id.profileURL);
        mRepos = view.findViewById(R.id.repo);
        mFollwers = view.findViewById(R.id.followers);
        mFollowing = view.findViewById(R.id.following);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getContext() instanceof ProfileFragment.ProfileFragmentListener){
            mListener = (ProfileFragment.ProfileFragmentListener)getContext();
        }
        mPresenter.getProfile(mListener.getDevName());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share)
            Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProfile(DevProfile profile) {
        mFullName.setText(profile.getFullName());
        mLocation.setText(profile.getLocation());
        mUserName.setText(profile.getUserName());
        mProfileURL.setText(profile.getProfileURL());
        mRepos.setText(profile.getRepo());
        mFollwers.setText(profile.getFollowers());
        mFollowing.setText(profile.getFollowing());
        // Load Developer's Image using Glide library
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.git_no_data);
        Glide.with(this)
                .setDefaultRequestOptions(options)
                .load(profile.getImageURL())
                .into(mProfileImage);

    }

    // Listener to get Developer's Name from Parent Activity
    public interface ProfileFragmentListener{
        String getDevName();
    }
}

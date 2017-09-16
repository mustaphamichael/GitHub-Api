package mmustapha.g_hub.Index;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import mmustapha.g_hub.Index.Adapters.DevListAdapter;
import mmustapha.g_hub.Index.Adapters.Developer;
import mmustapha.g_hub.R;
import mmustapha.g_hub.SplashScreen.CreateSQLiteDB;

public class IndexFragment extends Fragment implements IndexContract.View {

    private RecyclerView mRecyclerView;
    private DevListAdapter mDevAdapter;
    private ArrayList<Developer> mDevelopers;
    private IndexContract.Presenter mPresenter;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefresh;

    public final String REFRESH_LIST = "REFRESH_LIST";
    public final String GET_LIST = "GET_LIST";

    public IndexFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment IndexFragment.
     */
    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        // Initialize the Presenter
        mPresenter = new IndexPresenter(this);
        this.setPresenter(mPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        mRecyclerView = view.findViewById(R.id.dev_recyclerview);
        mProgressBar = view.findViewById(R.id.progressbar);
        mSwipeRefresh = view.findViewById(R.id.swipeRefresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent,
                R.color.colorPrimary, R.color.colorPrimaryDark);
        return view;
    }

    /**
     * Creates an object of Recycler Layout Manager
     * @param adapter
     */
    public void createRecyclerView(DevListAdapter adapter){
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * A SwipeRefreshLayout for getting updates on Developers' List
     */
    private void onSwipeRefresh(){
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getServerResponse(REFRESH_LIST);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("DEVELOPER_ARRAY", mDevelopers); // Saving Developers ArrayList on the LifeCycle
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState ==  null){
            mDevelopers = new ArrayList<>();
            mPresenter.getDeveloperList();
        }else{
            mDevelopers = savedInstanceState.getParcelableArrayList("DEVELOPER_ARRAY");
            mProgressBar.setVisibility(View.GONE);
            System.out.println("It's not NEW");
        }
        mDevAdapter = new DevListAdapter(this, mDevelopers);
        createRecyclerView(mDevAdapter);
        onSwipeRefresh();
    }

    @Override
    public void setPresenter(IndexContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * Display list of developers on Screen if Data is available
     * @param dev
     */
    @Override
    public void onSuccess(Developer dev) {
        mDevelopers.add(dev);
        if (mDevAdapter != null)
        mDevAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE); // Hide Progress bar
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
     * Stop Internet Check when paused
     */
    @Override
    public void onPause() {
        super.onPause();
        mPresenter.stopServerResponse();
        System.out.println("PAUSED!!!!!!!!!!");
    }

    /**
     * Do a quick refresh of list
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getServerResponse(REFRESH_LIST);
    }
}

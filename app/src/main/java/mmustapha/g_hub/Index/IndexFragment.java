package mmustapha.g_hub.Index;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import mmustapha.g_hub.Index.Adapters.DevListAdapter;
import mmustapha.g_hub.Index.Adapters.Developer;
import mmustapha.g_hub.R;
import mmustapha.g_hub.Utils.IPListener;
import mmustapha.g_hub.Utils.InternetCheck;

public class IndexFragment extends Fragment implements IndexContract.View {

    private RecyclerView mRecyclerView;
    private DevListAdapter mDevAdapter;
    private ArrayList<Developer> mDevelopers;
    private IndexContract.Presenter mPresenter;
    private ProgressBar mProgressBar;

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
//        mDevelopers = new ArrayList();
//        mDevAdapter = new DevListAdapter(this, mDevelopers);
//        createRecyclerView(mDevAdapter);
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
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int position = mLayoutManager.getPosition(recyclerView);
                super.onScrolled(recyclerView, dx, dy);
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
        }
        mDevAdapter = new DevListAdapter(this, mDevelopers);
        // Create Recycler View
        createRecyclerView(mDevAdapter);
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
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void toastMessage(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}

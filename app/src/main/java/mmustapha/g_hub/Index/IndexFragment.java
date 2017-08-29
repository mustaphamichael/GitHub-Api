package mmustapha.g_hub.Index;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mmustapha.g_hub.Index.Adapters.DevListAdapter;
import mmustapha.g_hub.Index.Adapters.Developer;
import mmustapha.g_hub.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndexFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DevListAdapter mDevAdapter;
    private ArrayList<Developer> mDevelopers;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        mRecyclerView = view.findViewById(R.id.dev_recyclerview);
        mDevelopers = new ArrayList();
        mDevAdapter = new DevListAdapter(this, mDevelopers);

        // DEMO DATA
        Developer dev1 = new Developer("Mike", "", false);
        mDevelopers.add(dev1);
        Developer dev2 = new Developer("Mustapha", "", false);
        mDevelopers.add(dev2);
        createRecyclerView(mDevAdapter);
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

}

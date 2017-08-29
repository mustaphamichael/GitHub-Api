package mmustapha.g_hub.Index.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mmustapha.g_hub.Index.IndexFragment;
import mmustapha.g_hub.R;

/**
 * Created by mmustapha on 8/29/17.
 */

public class DevListAdapter extends RecyclerView.Adapter<DevListAdapter.DevListViewHolder> {

    private Context mCtx;
    private Fragment mFragment;
    private List<Developer> mDeveloper;
//    private IndexFragment.IndexListener mListener;
    boolean mIsFav;


    public static class DevListViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        ImageView profileImage;
        CheckBox isFavorite;
        public DevListViewHolder(View view) {
            super(view);
            username = (TextView)view.findViewById(R.id.userName);
            profileImage = (ImageView) view.findViewById(R.id.profileImage);
            isFavorite = (CheckBox) view.findViewById(R.id.isFav);
        }
    }

    /**
     * Constructor
     * @param fragment
     * @param entries
     */
    public DevListAdapter(Fragment fragment, List<Developer> entries){
        this.mFragment = fragment;
        this.mDeveloper = entries;
    }

    @Override
    public DevListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dev_listview, parent, false);
        return new DevListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DevListViewHolder holder, int position) {
        final Developer Developer = mDeveloper.get(position);
        final String userName = Developer.getUserName(); // Retrieve Username from Developer
        String url = Developer.getImageURL();    // Retrieve the ImageURL from Developer
        holder.username.setText(Developer.getUserName());

        // Load Developer's Image using Glide library
//        RequestOptions options = new RequestOptions();
//        options.placeholder(R.drawable.git_no_data);
//        Glide.with(mFragment)
//                .setDefaultRequestOptions(options)
//                .load(url)
//                .into(holder.profileImage);

        // Add Developer to Favourites Group
        // TODO: Create a SQLite DB instance to store Favourites List
        holder.isFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    mIsFav = true;
                    Toast.makeText(mFragment.getContext(),userName+" has been added to Favourites", Toast.LENGTH_SHORT).show();
                }
                else {
                    mIsFav = false;
                    Toast.makeText(mFragment.getContext(),userName+" has been removed from Favourites", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (holder.isFavorite.isChecked()){
            mIsFav = true;
        }
        else {
            mIsFav = false;
        }
        // itemView is an in-built variable of View class
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx = mFragment.getContext();
//                if (mCtx instanceof IndexFragment.IndexListener){
//                    mListener = (IndexFragment.IndexListener)mCtx;
//                }
//                mListener.createInteraction(userName, mIsFav);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeveloper.size();
    }

}

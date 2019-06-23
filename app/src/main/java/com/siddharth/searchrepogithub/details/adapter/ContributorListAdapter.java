package com.siddharth.searchrepogithub.details.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.siddharth.searchrepogithub.R;
import com.siddharth.searchrepogithub.databinding.GridViewItemBinding;
import com.siddharth.searchrepogithub.databinding.RepoListItemBinding;
import com.siddharth.searchrepogithub.details.model.Contributor;
import com.siddharth.searchrepogithub.home.model.Item;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siddharth on 8/3/19.
 */
public class ContributorListAdapter extends RecyclerView.Adapter<ContributorListAdapter.GroupViewHolder> {

    //https://abhiandroid.com/ui/gridview
    //https://api.github.com/repos/ericoporto/fgmk/contributors
    //https://stackoverflow.com/questions/44917324/android-gridview-set-not-scrollable
    //https://drive.google.com/file/d/1dg9anWvVjwaSVAbPSPfDj3bToen-fRcW/view

    private Context context;
    private ArrayList<Contributor> mArrayListContributors;

    public ContributorListAdapter(Context context) {
        this.mArrayListContributors = new ArrayList<>();
        this.context = context;

    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GridViewItemBinding mGridViewItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.grid_view_item, parent, false);

        return new GroupViewHolder(mGridViewItemBinding);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mArrayListContributors.size();
    }

    public void setData(List<Contributor> data) {
        mArrayListContributors.clear();
        mArrayListContributors.addAll(data);

        this.notifyDataSetChanged();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        GridViewItemBinding mGridViewItemBinding;

        public GroupViewHolder(GridViewItemBinding mGridViewItemBinding) {
            super(mGridViewItemBinding.getRoot());
            this.mGridViewItemBinding = mGridViewItemBinding;
        }

        void bind(final int pos) {

            final Contributor contributor = mArrayListContributors.get(pos);

            mGridViewItemBinding.textViewName.setText(contributor.getLogin());
            Picasso.with(context).load(contributor.getAvatarUrl()).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(mGridViewItemBinding.imageViewImage);
        }

    }

}
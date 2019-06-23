package com.siddharth.searchrepogithub.home.adapter;

import android.arch.persistence.room.Database;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.siddharth.searchrepogithub.R;
import com.siddharth.searchrepogithub.database.MyDatabase;
import com.siddharth.searchrepogithub.database.entitymodels.RepoDetails;
import com.siddharth.searchrepogithub.databinding.RepoListItemBinding;
import com.siddharth.searchrepogithub.home.model.Item;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Siddharth on 8/3/19.
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.GroupViewHolder> {

    private Context context;
    private ArrayList<Item> mArrayListRepo;
    private IListener mListener;

    public RepoListAdapter(Context context, IListener listener) {
        this.mArrayListRepo = new ArrayList<>();
        this.mListener = listener;
        this.context = context;

    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RepoListItemBinding repoListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.repo_list_item, parent, false);

        return new GroupViewHolder(repoListItemBinding);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mArrayListRepo.size();
    }

    public void setData(List<Item> data) {
        mArrayListRepo.clear();
        mArrayListRepo.addAll(data);

        this.notifyDataSetChanged();
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {

        RepoListItemBinding mRepoListItemBinding;

        public GroupViewHolder(RepoListItemBinding mRepoListItemBinding) {
            super(mRepoListItemBinding.getRoot());
            this.mRepoListItemBinding = mRepoListItemBinding;
        }

        void bind(final int pos) {

            final Item repo = mArrayListRepo.get(pos);

            mRepoListItemBinding.textViewName.setText("NAME: "+repo.getName());
            mRepoListItemBinding.textViewFullname.setText("FULL NAME: "+repo.getFullName());
            mRepoListItemBinding.textViewWatcher.setText("WATCHER COUNT: "+ repo.getWatchersCount());
            mRepoListItemBinding.textViewCommit.setText("FORKS COUNT: "+ repo.getForksCount());
            Picasso.with(context).load(repo.getOwner().getAvatarUrl()).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(mRepoListItemBinding.imageViewAvatar);
        }

    }
    public interface IListener {
        void onItemClick(Item repo);
    }

}
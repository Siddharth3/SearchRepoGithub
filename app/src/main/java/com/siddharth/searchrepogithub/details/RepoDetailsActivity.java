package com.siddharth.searchrepogithub.details;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.siddharth.searchrepogithub.BaseActivity;
import com.siddharth.searchrepogithub.Constants;
import com.siddharth.searchrepogithub.R;
import com.siddharth.searchrepogithub.Utils;
import com.siddharth.searchrepogithub.databinding.ActivityRepoDetailsBinding;
import com.siddharth.searchrepogithub.details.adapter.ContributorListAdapter;
import com.siddharth.searchrepogithub.details.model.Contributor;
import com.siddharth.searchrepogithub.home.MainActivity;
import com.siddharth.searchrepogithub.home.model.Item;
import com.siddharth.searchrepogithub.project_web_view.WebViewActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RepoDetailsActivity extends BaseActivity implements RepoDetailsContractor{

    private ActivityRepoDetailsBinding mActivityRepoDetailsBinding;
    private Item repoData;
    private RepoDetailsPresenterImpl presenter;
    private ContributorListAdapter contributorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRepoDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_repo_details);

        repoData = (Item) getIntent().getSerializableExtra(Constants.REPO_DATA);
        if (repoData!=null)
            setData(repoData);

        presenter = new RepoDetailsPresenterImpl(this, this);

        if (Utils.isInternetConnected(RepoDetailsActivity.this)) {
            presenter.callGitContributors(repoData.getContributorsUrl());
        } else {
            toast(getResources().getString(R.string.no_internet_error));
        }

        initListeners();

        contributorListAdapter = new ContributorListAdapter(this);

    }

    private void setData(Item repoData) {
        Picasso.with(this).load(repoData.getOwner().getAvatarUrl()).memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(mActivityRepoDetailsBinding.imageViewAvatar);
        mActivityRepoDetailsBinding.textViewName.setText(repoData.getFullName());
        mActivityRepoDetailsBinding.textViewLink.setText(repoData.getCloneUrl());
        mActivityRepoDetailsBinding.textViewDescription.setText(repoData.getDescription());
        mActivityRepoDetailsBinding.toolbar.textViewTitle.setText(getResources().getText(R.string.project_details));
        mActivityRepoDetailsBinding.toolbar.textViewSubTitle.setVisibility(View.VISIBLE);
        mActivityRepoDetailsBinding.toolbar.textViewSubTitle.setText(repoData.getName());
    }

    private void initListeners() {
        mActivityRepoDetailsBinding.toolbar.imageViewBack.setOnClickListener(mOnClickListener);
        mActivityRepoDetailsBinding.textViewLink.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = v -> {
        switch(v.getId()){
            case R.id.image_view_back:
                onBackPressed();
                break;

            case R.id.textView_link:
                Intent i = new Intent(this, WebViewActivity.class);
                i.putExtra(Constants.PROJECT_LINK,mActivityRepoDetailsBinding.textViewLink.getText().toString());
                startActivity(i);
                break;
        }
    };

    @Override
    public void showErrorToast() {
        toast(getResources().getString(R.string.network_error_message));
    }

    @Override
    public void showLoading() {
        startLoading(getResources().getString(R.string.please_wait));
    }

    @Override
    public void cancelLoading() {
        dismissLoading();
    }

    @Override
    public void setResultListView(List<Contributor> datumList) {

        if (datumList != null) {
            mActivityRepoDetailsBinding.recyclerViewContributors.setLayoutManager(new GridLayoutManager(this, 3));
            contributorListAdapter.setData(datumList);
            mActivityRepoDetailsBinding.recyclerViewContributors.setAdapter(contributorListAdapter);
        }
    }
}

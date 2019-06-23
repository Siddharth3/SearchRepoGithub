package com.siddharth.searchrepogithub.details;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.siddharth.searchrepogithub.Network.DataService;
import com.siddharth.searchrepogithub.Network.RetrofitInstance;
import com.siddharth.searchrepogithub.R;
import com.siddharth.searchrepogithub.details.model.Contributor;
import com.siddharth.searchrepogithub.home.MainContractor;
import com.siddharth.searchrepogithub.home.model.Item;
import com.siddharth.searchrepogithub.home.model.Repo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Siddharth on 21/6/19.
 */
public class RepoDetailsPresenterImpl implements RepoDetailsPresenter {

    private DataService apiInterface;
    private RepoDetailsContractor repoDetailsContractor;
    private Context mContext;

    public RepoDetailsPresenterImpl(RepoDetailsContractor repoDetailsContractor, Context context)
    {
        this.repoDetailsContractor = repoDetailsContractor;
        apiInterface = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        this.mContext = context;
    }

    @Override
    public void callGitContributors(String url) {

        repoDetailsContractor.showLoading();
        /**
         GET List Resources
         **/
        Call<List<Contributor>> call = apiInterface.getContributors(url);
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call <List<Contributor>> call, Response<List<Contributor>> response) {
                repoDetailsContractor.cancelLoading();
                List<Contributor> datumList = response.body();
                repoDetailsContractor.setResultListView(datumList);
            }

            @Override
            public void onFailure(Call <List<Contributor>> call, Throwable t) {
                call.cancel();
                repoDetailsContractor.cancelLoading();
                repoDetailsContractor.showErrorToast();
            }

        });

    }
}

package com.siddharth.searchrepogithub.home;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.siddharth.searchrepogithub.Network.DataService;
import com.siddharth.searchrepogithub.Network.RetrofitInstance;
import com.siddharth.searchrepogithub.database.MyDatabase;
import com.siddharth.searchrepogithub.database.daos.RepoDao;
import com.siddharth.searchrepogithub.database.entitymodels.RepoDetails;
import com.siddharth.searchrepogithub.home.model.Item;
import com.siddharth.searchrepogithub.home.model.Repo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenterImpl implements MainPresenter {

    private DataService apiInterface;
    private MainContractor mainContractor;
    private Context mContext;

    public MainPresenterImpl(Context context, MainContractor mainContractor)
    {
        this.mContext = context;
        this.mainContractor = mainContractor;
        apiInterface = RetrofitInstance.getRetrofitInstance().create(DataService.class);
    }

    @Override
    public void callGitSearchApi(String key) {

        mainContractor.showLoading();
        /**
         GET List Resources
         **/
        Call<Repo> call = apiInterface.getRepo(key);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                mainContractor.cancelLoading();
                List<Item> datumList = response.body().getItems();
                mainContractor.setResultListView(datumList);

                MyDatabase.getApplicationDatabase(mContext).resetShifuDatabase(mContext);

                for (int i=0; i<=15; i++) {
                    insertInDatabase(datumList.get(i));
                }
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                call.cancel();
                mainContractor.cancelLoading();
                mainContractor.showErrorToast();
            }

        });
    }

    private void insertInDatabase(Item repo) {

        RepoDetails repoDetails = new RepoDetails();
        repoDetails.setName(repo.getName());
        repoDetails.setFullName(repo.getFullName());
        repoDetails.setAvatarUrl(repo.getOwner().getAvatarUrl());
        repoDetails.setCloneUrl(repo.getCloneUrl());
        repoDetails.setDescription(repo.getDescription());
        repoDetails.setForksCount(repo.getForksCount());
        repoDetails.setWatchersCount(repo.getWatchersCount());

        Completable.fromAction(() ->
                MyDatabase.getApplicationDatabase(mContext).repoDao().insert(repoDetails))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDBPopulationSuccess, this::onDBPopulationFailure);

    }

    private void onDBPopulationFailure(Throwable throwable) {
        Log.d("Sssss","Error");
    }

    private void onDBPopulationSuccess() {
        Log.d("Sssss","completed inserting");
    }
}

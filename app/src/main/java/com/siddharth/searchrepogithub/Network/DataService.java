package com.siddharth.searchrepogithub.Network;

import com.siddharth.searchrepogithub.details.model.Contributor;
import com.siddharth.searchrepogithub.home.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface DataService {

    @GET("/search/repositories")
    Call<Repo> getRepo(@Query("q") String param);

//    @GET("/repos/ericoporto/fgmk/contributors")
//    Call<Repo> getRepo(@Query("q") String param);

    @GET
    Call<List<Contributor>> getContributors(@Url String url);

}
package com.siddharth.searchrepogithub.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.siddharth.searchrepogithub.DatabaseConstants;
import com.siddharth.searchrepogithub.database.entitymodels.RepoDetails;

import java.util.List;

@Dao
public abstract class RepoDao {

//    @Query("SELECT * FROM "+ DatabaseConstants.RepoDetail.TABLENAME)
//    public abstract List<RepoDetails> getAll();

    @Insert
    public abstract void insert(RepoDetails repoDetails);

//    @Delete
//    public abstract void delete(RepoDetails repoDetails);


    @Query("DELETE FROM " + DatabaseConstants.RepoDetail.TABLENAME)
    public abstract void deleteAll();
}

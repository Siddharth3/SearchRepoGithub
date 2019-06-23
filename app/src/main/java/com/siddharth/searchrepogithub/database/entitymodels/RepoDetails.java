
package com.siddharth.searchrepogithub.database.entitymodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.siddharth.searchrepogithub.Constants;
import com.siddharth.searchrepogithub.DatabaseConstants;
import com.siddharth.searchrepogithub.home.model.License;
import com.siddharth.searchrepogithub.home.model.Owner;

import java.io.Serializable;

@Entity(tableName = DatabaseConstants.RepoDetail.TABLENAME)
public class RepoDetails implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = DatabaseConstants.RepoDetail.Columns.NAME)
    private String name;

    @ColumnInfo(name = DatabaseConstants.RepoDetail.Columns.FULL_NAME)
    private String fullName;

    @ColumnInfo(name = DatabaseConstants.RepoDetail.Columns.DESCRIPTION)
    private String description;

    @ColumnInfo(name = DatabaseConstants.RepoDetail.Columns.CLONE_URL)
    private String cloneUrl;

    @ColumnInfo(name = DatabaseConstants.RepoDetail.Columns.WATCHER_COUNT)
    private Integer watchersCount;

    @ColumnInfo(name = DatabaseConstants.RepoDetail.Columns.FORKS_COUNT)
    private Integer forksCount;

    @ColumnInfo(name = DatabaseConstants.RepoDetail.Columns.AVATAR_URL)
    private String avatarUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

}

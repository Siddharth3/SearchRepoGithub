package com.siddharth.searchrepogithub.details;

import com.siddharth.searchrepogithub.details.model.Contributor;

import java.util.List;

/**
 * Created by Siddharth on 21/6/19.
 */
public interface RepoDetailsContractor {

    void showErrorToast();

    void showLoading();

    void cancelLoading();

    void setResultListView(List<Contributor> datumList);
}

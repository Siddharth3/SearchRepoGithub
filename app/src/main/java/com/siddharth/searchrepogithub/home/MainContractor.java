package com.siddharth.searchrepogithub.home;

import com.siddharth.searchrepogithub.home.model.Item;

import java.util.List;

public interface MainContractor {

    void setResultListView(List<Item> repoData);

    void showErrorToast();

    void showLoading();

    void cancelLoading();
}

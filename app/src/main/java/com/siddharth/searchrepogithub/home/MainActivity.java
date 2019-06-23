package com.siddharth.searchrepogithub.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.siddharth.searchrepogithub.BaseActivity;
import com.siddharth.searchrepogithub.Constants;
import com.siddharth.searchrepogithub.R;
import com.siddharth.searchrepogithub.Utils;
import com.siddharth.searchrepogithub.databinding.ActivityMainBinding;
import com.siddharth.searchrepogithub.details.RepoDetailsActivity;
import com.siddharth.searchrepogithub.home.adapter.RepoListAdapter;
import com.siddharth.searchrepogithub.home.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.RECORD_AUDIO;

public class MainActivity extends BaseActivity implements RepoListAdapter.IListener, MainContractor {

    private ActivityMainBinding mActivityMainBinding;
    private MainPresenter presenter;
    private String result;
    private RepoListAdapter repoListAdapter;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        checkPermission();
        intializations();
        initListeners();

        mActivityMainBinding.editTextInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                result = cs.toString().trim();
                if (!result.equals("")) {
                    if (Utils.isInternetConnected(MainActivity.this)) {
                        presenter.callGitSearchApi(result);
                    } else {
                        toast(getResources().getString(R.string.no_internet_error));
                    }
                } else {
                    toast(getResources().getString(R.string.null_error));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

    }

    private void initListeners() {
        mActivityMainBinding.imageVoiceSearch.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = v -> {
        switch(v.getId()){
            case R.id.image_voice_search:

                if (!checkPermission()) {
                    requestPermission();
                } else {
                    promptSpeechInput();
                }

                break;
        }
    };

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, PERMISSION_REQUEST_CODE);
    }

    private void intializations() {

        setSupportActionBar(mActivityMainBinding.toolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        mActivityMainBinding.toolbar.imageViewBack.setVisibility(View.GONE);
        mActivityMainBinding.toolbar.textViewTitle.setText(getResources().getText(R.string.app_name));
        repoListAdapter = new RepoListAdapter(this,this);
        presenter = new MainPresenterImpl(this, this);

    }

    @Override
    public void onItemClick(Item repo) {
        Intent intent = new Intent(MainActivity.this, RepoDetailsActivity.class);
        intent.putExtra(Constants.REPO_DATA, repo);
        startActivity(intent);
    }

    @Override
    public void setResultListView(List<Item> repoData) {
        repoListAdapter.setData(repoData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mActivityMainBinding.recyclerView.setLayoutManager(layoutManager);
        mActivityMainBinding.recyclerView.setAdapter(repoListAdapter);
    }

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

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            toast(getResources().getString(R.string.speech_not_supported));
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mActivityMainBinding.editTextInput.setText(result.get(0));
                }
                break;
            }

        }
    }

}

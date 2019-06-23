package com.siddharth.searchrepogithub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private SearchApp searchApp;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgress();

        searchApp = (SearchApp)this.getApplicationContext();

    }


    /**
     * Initializing the progress
     */
    private void initProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * Dismiss the progress
     */
    protected void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /***
     *Start progress dialog with custom message
     * @param message
     */
    protected void startLoading(String message) {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    /***
     *Start progress dialog with custom message
     * @param message
     */
    protected void startLoading(String title, String message) {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
            mProgressDialog.setTitle(title);
            mProgressDialog.show();
        }
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

}

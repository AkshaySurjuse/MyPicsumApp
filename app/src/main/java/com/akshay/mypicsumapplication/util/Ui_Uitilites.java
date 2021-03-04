package com.akshay.mypicsumapplication.util;

import androidx.appcompat.app.AppCompatActivity;

import com.akshay.mypicsumapplication.R;


public class Ui_Uitilites {

    private AppCompatActivity mCurrentActivityWindow;
    private CustomProgressDialog customProgressDialog;

    private Runnable mProgressDelay = null;


    public Ui_Uitilites(AppCompatActivity currentActivityWindow) {
        this.mCurrentActivityWindow = currentActivityWindow;
    }


    public void showCustomProgressDialog(boolean delay) {

        if (customProgressDialog == null) {
            customProgressDialog = new CustomProgressDialog(mCurrentActivityWindow, R.mipmap.progress_loader);
            customProgressDialog.setCancelable(false);
            mCurrentActivityWindow.getWindow().getDecorView().postDelayed(mProgressDelay = new Runnable() {
                @Override
                public void run() {
                    customProgressDialog.show();
                    mProgressDelay = null;
                }
            }, delay ? 1000 : 0);
        }
    }


    public void hideCustomDialog() {
        if (mProgressDelay != null) {
            mCurrentActivityWindow.getWindow().getDecorView().removeCallbacks(mProgressDelay);
            customProgressDialog.dismiss();
        }
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }
    }


}

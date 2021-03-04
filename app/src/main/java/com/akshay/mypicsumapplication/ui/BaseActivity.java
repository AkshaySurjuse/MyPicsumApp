package com.akshay.mypicsumapplication.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.BuildConfig;

import com.akshay.mypicsumapplication.R;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    private Window mWindow;
    private T mViewDataBinding;
    private Location mLocation;
    private ClipboardManager mClipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!BuildConfig.BUILD_TYPE.contains("debug"))
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mWindow = getWindow();
        WindowManager.LayoutParams winParams = mWindow.getAttributes();
        mWindow.setAttributes(winParams);
        mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        lightStatusBarColor();
        setSoftInputAdjustPan();
        super.onCreate(savedInstanceState);
        performDataBinding();

        mClipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.setText("");

        //getCurrentLocation();

    }


    public abstract int getLayoutId();

    public abstract void setNavDrawerModeAndBottomNavVisibility(boolean bottomVisible,boolean drawerEnable);

    public abstract void setToolbarTitle(String title);


    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        /*mViewDataBinding.setVariable(getViewDataBinding());
        mViewDataBinding.executePendingBindings();*/
    }

    public T getViewDataBinding() {
        if(mViewDataBinding==null)
            performDataBinding();
        return mViewDataBinding;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mClipboardManager.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mClipboardManager.setText("");
    }

    @Override
    protected void onDestroy() {
        Runtime.getRuntime().gc();
        super.onDestroy();
    }

    /*public static Location getCurrentLatLon(AppCompatActivity context) {
        Location location = null;
        try {
            GetCurrentLatLong getCurrentAddressLatLong = new GetCurrentLatLong(context);
            if (getCurrentAddressLatLong != null) {
                location = getCurrentAddressLatLong.getLatLong();
                //String[] words = latLong.split("-");
                return location;
            } else {
                return location;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return location;
        }
    }*/

    public Location getCurrentLocation() {
        return mLocation;
    }

    public void setCurrentLocation(Location location){
        mLocation=location;
    }

    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = getCurrentFocus();
        if (currentFocusedView != null && inputManager != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
        }
    }

    public void setSoftInputAdjustPan(){
        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void lightStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black));
            }
        }
    }

    public void setFullScreen() {
        // Call before calling setContentView();
        mWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void hideActionBar() {
        // Call before calling setContentView();
            mWindow.requestFeature(Window.FEATURE_ACTION_BAR);
            if (getActionBar() != null) {
                getActionBar().hide();
            }
    }

    private void setupUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard();
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void disableScreenshotFunctionality() {
        mWindow.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    public void setScreenTitle(String title) {
        setToolbarTitle(title);
    }

}

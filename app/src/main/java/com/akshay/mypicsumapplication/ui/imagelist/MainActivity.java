package com.akshay.mypicsumapplication.ui.imagelist;

import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.akshay.mypicsumapplication.R;
import com.akshay.mypicsumapplication.databinding.MainActivityBinding;
import com.akshay.mypicsumapplication.model.responses.ImageListResponse;
import com.akshay.mypicsumapplication.ui.BaseActivity;
import com.akshay.mypicsumapplication.util.Ui_Uitilites;

import java.util.List;

public class MainActivity extends BaseActivity<MainActivityBinding> {
    MainActivityBinding mainActivityBinding;
    MainActivityViewModel mainActivityViewModel;
    Ui_Uitilites utilities;
    ImageListAdapter imageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = getViewDataBinding();
        utilities = new Ui_Uitilites(this);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        initUI();
        loadImageListData();

    }

    private void initUI() {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mainActivityBinding.rvImageList.setLayoutManager(mGridLayoutManager);
        imageListAdapter = new ImageListAdapter(MainActivity.this, null);
        mainActivityBinding.rvImageList.setAdapter(imageListAdapter);
    }

    private void loadImageListData() {
        mainActivityViewModel.getImageList();
        utilities.showCustomProgressDialog(false);

        mainActivityViewModel.getImageListResponseMutableLiveData().observe(this, new Observer<List<ImageListResponse>>() {
            @Override
            public void onChanged(List<ImageListResponse> imageListResponses) {
                if (imageListResponses != null) {
                    imageListAdapter.updateList(imageListResponses);
                    utilities.hideCustomDialog();
                }
            }
        });
        mainActivityViewModel.getErrorResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                utilities.hideCustomDialog();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setNavDrawerModeAndBottomNavVisibility(boolean bottomVisible, boolean drawerEnable) {

    }

    @Override
    public void setToolbarTitle(String title) {

    }


}
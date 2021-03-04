package com.akshay.mypicsumapplication.ui.imagelist;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.akshay.mypicsumapplication.helper.ApiResponse;
import com.akshay.mypicsumapplication.manager.ApiManager;
import com.akshay.mypicsumapplication.model.ResponseToObjectMapper;
import com.akshay.mypicsumapplication.model.responses.ImageListResponse;
import com.android.volley.VolleyError;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private final ApiManager apiManager;

    private final MutableLiveData<List<ImageListResponse>> imageListResponseMutableLiveData=new MutableLiveData<>();
    private final MutableLiveData<String> errorResponse=new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        apiManager = new ApiManager();
    }

    public MutableLiveData<List<ImageListResponse>> getImageListResponseMutableLiveData() {
        return imageListResponseMutableLiveData;
    }

    public MutableLiveData<String> getErrorResponse() {
        return errorResponse;
    }

    public void getImageList(){
        apiManager.getImageList(new ApiResponse<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    List<ImageListResponse> imageListResponse = ResponseToObjectMapper.mapImageListResponse(response);
                    imageListResponseMutableLiveData.setValue(imageListResponse);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    error.printStackTrace();
                    Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_LONG).show();
                    errorResponse.postValue(error.getMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_LONG).show();
                    errorResponse.postValue(error.getMessage());
                }
            }
        });
    }
}

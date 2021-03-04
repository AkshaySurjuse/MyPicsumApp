package com.akshay.mypicsumapplication.helper;

import com.android.volley.VolleyError;

public interface ApiResponse<T>{
        void onResponse(T response);

        void onErrorResponse(VolleyError error);
}

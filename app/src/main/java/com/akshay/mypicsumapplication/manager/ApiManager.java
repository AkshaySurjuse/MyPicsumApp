package com.akshay.mypicsumapplication.manager;

import com.akshay.mypicsumapplication.constants.AppConstants;
import com.akshay.mypicsumapplication.core.PicsumApplication;
import com.akshay.mypicsumapplication.helper.ApiResponse;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class ApiManager {
    private final static int TIME_OUT = 120000;
    private final static int RETRIES = 0;

    private void makeCallForArray(String sURL, final JSONArray param, int method, final Request.Priority pro, final ApiResponse apiResponse,
                                  String requestTag, final boolean isWithAuthToken) {

        JsonArrayRequest request = new JsonArrayRequest(method, sURL, param,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        apiResponse.onResponse(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    // filed request for network timeout
                }
                apiResponse.onErrorResponse(error);

                try {
                    if (error.networkResponse != null) {
                        if (error.networkResponse.statusCode == 401) {
                            stopRequest();
                            CancelAll();
                            startRequest();
                        }
                    }
                } catch (Exception e) {
                    apiResponse.onErrorResponse(error);
                    e.printStackTrace();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {

                return getHeaderMap(isWithAuthToken);
            }

            @Override
            public Priority getPriority() {
                return pro;
            }
        };
        request.setRetryPolicy(
                new DefaultRetryPolicy(TIME_OUT, RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(true);
        PicsumApplication.addToRequestQueue(request, requestTag);
    }

    public void stopRequest() {
        PicsumApplication.getRequestQueue().stop();
    }

    public void startRequest() {
        PicsumApplication.getRequestQueue().start();
    }

    public void CancelAll() {
        PicsumApplication.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    private Map<String, String> getHeaderMap(boolean isWithAuthToken) {
        Map<String, String> params = new HashMap<String, String>();
//        params.put(AppConstants.Content_Type, "application/json");
        return params;
    }

    public void Cancel(String tag) {
        PicsumApplication.getRequestQueue().cancelAll(tag);
    }

    public void getImageList( ApiResponse<String> apiRespone) {
        makeCallForArray(AppConstants.AppUrls.BASEURL, null, Request.Method.GET, Request.Priority.IMMEDIATE, apiRespone, AppConstants.Tags.PHOTOList, false);
    }

}





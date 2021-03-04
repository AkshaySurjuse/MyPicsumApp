package com.akshay.mypicsumapplication.model;

import com.akshay.mypicsumapplication.model.responses.ImageListResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ResponseToObjectMapper {
    public static Gson gson;


    public static List<ImageListResponse> mapImageListResponse(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        try {
            List<ImageListResponse> imageListResponse =gson.fromJson(response, new TypeToken<List<ImageListResponse>>(){}.getType());
            return imageListResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

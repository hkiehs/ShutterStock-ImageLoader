package com.sample.service;

import com.sample.model.SearchModel;

import retrofit.Callback;
import retrofit.http.GET;

public interface ApiService {
    @GET("/v2/images/search?image_type=photo&&license=commercial&sort=popular&view=minimal")
    void getMovieList(Callback<SearchModel> cb);
}
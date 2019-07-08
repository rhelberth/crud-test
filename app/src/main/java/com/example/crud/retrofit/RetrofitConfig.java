package com.example.crud.retrofit;

import com.example.crud.service.AlbumService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public AlbumService getAlbumService() {
        return this.retrofit.create(AlbumService.class);
    }

}

package com.example.crud.retrofit;

import com.example.crud.service.AlbumService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public AlbumService getAlbumService() {
        return this.retrofit.create(AlbumService.class);
    }

}

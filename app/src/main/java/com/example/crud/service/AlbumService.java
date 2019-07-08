package com.example.crud.service;

import com.example.crud.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumService {

    @GET("photos")
    Call<List<Album>> listarAlbum();

}

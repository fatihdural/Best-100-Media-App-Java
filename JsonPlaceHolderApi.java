package com.example.myretrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("api/v1/us/movies/top-movies/all/100/explicit.json")
    Call<Post> getMovies();

    @GET("api/v1/tr/itunes-music/hot-tracks/all/100/explicit.json")
    Call<Post> getMusics();

    @GET("api/v1/tr/ios-apps/new-apps-we-love/all/100/explicit.json")
    Call<Post> getApps();

    @GET("api/v1/tr/books/top-free/all/100/explicit.json")
    Call<Post> getBooks();

    @GET("api/v1/tr/music-videos/top-music-videos/all/100/explicit.json")
    Call<Post> getClips();
}

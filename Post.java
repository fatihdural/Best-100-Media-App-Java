package com.example.myretrofit;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Post {
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("feed")
    private Post feed;
    @SerializedName("artworkUrl100")
    private String img;
    @SerializedName("artistName")
    private String director;
    @SerializedName("url")
    private String information;
    @SerializedName("results")
    private ArrayList<Post> results;

    ArrayList<Post> getResults(){
        return results;
    }
    Post getFeed(){
        return feed;
    }
    String getInformation(){
        return information;
    }
    String getDirector(){
        return director;
    }
    String getImg(){
        return img;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
}

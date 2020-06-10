package com.assignment.daggerpractisewithmtich.network.main;

import com.assignment.daggerpractisewithmtich.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    //posts?userId=1
    @GET("posts")
    Flowable<List<Post>> getPostFromUser(
            @Query("userId") int id
    );
}

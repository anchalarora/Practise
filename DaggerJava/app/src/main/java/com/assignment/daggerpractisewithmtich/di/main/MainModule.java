package com.assignment.daggerpractisewithmtich.di.main;

import com.assignment.daggerpractisewithmtich.network.main.MainApi;
import com.assignment.daggerpractisewithmtich.ui.main.posts.PostRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static PostRecyclerAdapter providesAdpater(){
        return new PostRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainApi providesMainApi(Retrofit retrofit)
    {
        return retrofit.create(MainApi.class);
    }
}

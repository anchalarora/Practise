package com.assignment.daggerpractisewithmtich.di.auth;


import com.assignment.daggerpractisewithmtich.network.auth.AuthAPI;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

//here will add dependencies for auth subcompoments
@Module
public class AuthModule {

    @AuthScope
    @Provides
    static AuthAPI providesAuthAPI(Retrofit retrofit)
    {
        return retrofit.create(AuthAPI.class);
    }

}

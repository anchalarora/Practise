package com.assignment.daggerpractisewithmtich.di;


import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.assignment.daggerpractisewithmtich.R;
import com.assignment.daggerpractisewithmtich.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    static Retrofit providesREtrofitInstance()

    {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    static RequestOptions provideRequestOptions()
    {
        return RequestOptions
                .placeholderOf(R.drawable.white_background)
                .error(R.drawable.white_background);
    }

    @Provides
    @Singleton
    static RequestManager providesRequestManager(Application application,RequestOptions requestOptions)
    {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Provides
    @Singleton
    static Drawable provideAppDrawable(Application application)
    {
        return ContextCompat.getDrawable(application,R.drawable.logo);
    }


    /*@Provides
    static String someString()
    {
        return "Hello Test";
    }


    //i can use here Application object as it is @BindInstance in AppComponent class
    //so it(Application) can be used in all the modules of this AppComponent.
    //returns true if app object doesnt exist
    //returns false if app object exist
    @Provides
    boolean getApplication(Application app)
    {
        return app == null;
    }*/
}

package com.assignment.daggerpractisewithmtich.di;


import android.app.Application;

import com.assignment.daggerpractisewithmtich.BaseApplication;
import com.assignment.daggerpractisewithmtich.SessionManager;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component( modules = {AndroidSupportInjectionModule.class,AppModule.class,
        AcitivityBuilderModule.class,ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    SessionManager sessionMgr();

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application app);

        AppComponent build();
    }
}

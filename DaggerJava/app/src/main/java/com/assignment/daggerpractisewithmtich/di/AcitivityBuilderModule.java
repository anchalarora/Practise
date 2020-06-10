package com.assignment.daggerpractisewithmtich.di;


import com.assignment.daggerpractisewithmtich.di.auth.AuthModule;
import com.assignment.daggerpractisewithmtich.di.auth.AuthScope;
import com.assignment.daggerpractisewithmtich.di.auth.AuthViewModelModule;
import com.assignment.daggerpractisewithmtich.di.main.MainFragmentBuildersModule;
import com.assignment.daggerpractisewithmtich.di.main.MainModule;
import com.assignment.daggerpractisewithmtich.di.main.MainScope;
import com.assignment.daggerpractisewithmtich.di.main.MainViewModelModule;
import com.assignment.daggerpractisewithmtich.network.auth.AuthAPI;
import com.assignment.daggerpractisewithmtich.ui.auth.AuthActivity;
import com.assignment.daggerpractisewithmtich.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AcitivityBuilderModule {

    //authmodule exist inside authactiivty subcomponent
    //authactivity is subcomponent of app component
    //inside app component we have retrofit dependency so we can access it(retrofit) in AuthModule
    @AuthScope
    @ContributesAndroidInjector(  modules = {AuthViewModelModule.class, AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    //main activity subcomponent created(check in generated folder)
    //ProfileFragment is going to exist within the scope of Main Activity subcomponent
    @MainScope
    @ContributesAndroidInjector(modules = {MainFragmentBuildersModule.class, MainViewModelModule.class, MainModule.class})
    abstract MainActivity contributeMAinActivity();

}

package com.assignment.daggerpractisewithmtich.di;
import androidx.lifecycle.ViewModelProvider;

import com.assignment.daggerpractisewithmtich.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
public class ViewModelFactoryModule {

    /*@Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);
    */
    @Provides
    public ViewModelProvider.Factory providesViewModelFactory(ViewModelProviderFactory viewModelFactory)
    {
        return viewModelFactory;
    }


}

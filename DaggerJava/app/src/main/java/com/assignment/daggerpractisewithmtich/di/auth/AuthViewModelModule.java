package com.assignment.daggerpractisewithmtich.di.auth;

import androidx.lifecycle.ViewModel;

import com.assignment.daggerpractisewithmtich.di.ViewModelKey;
import com.assignment.daggerpractisewithmtich.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class AuthViewModelModule {

    /*@Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);*/

    @Provides
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public ViewModel providesAuthViewModel(AuthViewModel viewModel)
    {
        return viewModel;
    }
}

package com.assignment.daggerpractisewithmtich.di.main;

import androidx.lifecycle.ViewModel;

import com.assignment.daggerpractisewithmtich.di.ViewModelKey;
import com.assignment.daggerpractisewithmtich.ui.main.posts.PostVIewModel;
import com.assignment.daggerpractisewithmtich.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostVIewModel.class)
    public abstract ViewModel bindPostsViewModel(PostVIewModel postsViewModel);


}

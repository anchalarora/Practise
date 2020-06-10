package com.assignment.daggerpractisewithmtich.di.main;


import com.assignment.daggerpractisewithmtich.ui.main.posts.PostsFragment;
import com.assignment.daggerpractisewithmtich.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//here we put all injectors for fragments (fragments only inside Main SubComponent ==>ProfileFRagment and PostFrag)
@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();


}

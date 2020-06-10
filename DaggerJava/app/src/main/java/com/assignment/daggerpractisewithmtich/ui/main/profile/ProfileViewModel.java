package com.assignment.daggerpractisewithmtich.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.assignment.daggerpractisewithmtich.SessionManager;
import com.assignment.daggerpractisewithmtich.models.User;
import com.assignment.daggerpractisewithmtich.ui.auth.AuthResource;
import com.assignment.daggerpractisewithmtich.viewmodels.ViewModelProviderFactory;
import com.bumptech.glide.util.LogTime;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";

    private SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager){
        this.sessionManager = sessionManager;
        Log.d(TAG,"ProfileViewmodel : viewmodel is ready");
    }

    public LiveData<AuthResource<User>> getAuthentciatedUSer(){
        return sessionManager.getAuthUser();
    }



}

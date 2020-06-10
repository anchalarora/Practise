package com.assignment.daggerpractisewithmtich;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.assignment.daggerpractisewithmtich.models.User;
import com.assignment.daggerpractisewithmtich.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private MediatorLiveData<AuthResource<User>> cachedUSer = new MediatorLiveData<>();


    @Inject
    public SessionManager(){

    }


    public void authenticateWIthId(final LiveData<AuthResource<User>> source){
        if(cachedUSer!= null){
            cachedUSer.setValue(AuthResource.loading((User) null));
            cachedUSer.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cachedUSer.setValue(userAuthResource);
                    cachedUSer.removeSource(source);
                }
            });
        }
        else
        {
            Log.d("TAG","authenticateId:previous session detected .Retreiving user from cache");
        }
    }


    public void logout(){
        Log.d("TAG","logout : logging out...");
        cachedUSer.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser(){
        return cachedUSer;
    }
}

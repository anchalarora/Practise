package com.assignment.daggerpractisewithmtich.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.assignment.daggerpractisewithmtich.R;
import com.assignment.daggerpractisewithmtich.models.User;
import com.assignment.daggerpractisewithmtich.ui.auth.AuthResource;
import com.assignment.daggerpractisewithmtich.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    private ProfileViewModel profileViewModel;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private TextView email,username,website;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Toast.makeText(getActivity(),"Profile Fragment",Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ProfileFragment was created...");
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);

        profileViewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers()
    {
        profileViewModel.getAuthentciatedUSer().removeObservers(getViewLifecycleOwner());
        profileViewModel.getAuthentciatedUSer().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource !=null){

                    switch(userAuthResource.status){
                        case AUTHENTICATED:
                        {
                            setUserDetails(userAuthResource.data);
                            break;
                        }

                        case ERROR:{
                            setErrorDetails(userAuthResource.message);
                            break;

                        }
                    }
                }

            }
        });
    }

    private void setErrorDetails(String message) {
        email.setText(message);
        username.setText("error");
        website.setText("error");

    }

    private void setUserDetails(User data) {
        email.setText(data.getEmail());
        username.setText(data.getUsername());
        website.setText(data.getWebsite());


    }
}

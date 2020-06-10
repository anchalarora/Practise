package com.assignment.daggerpractisewithmtich.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.assignment.daggerpractisewithmtich.R;
import com.assignment.daggerpractisewithmtich.models.User;
import com.assignment.daggerpractisewithmtich.ui.main.MainActivity;
import com.assignment.daggerpractisewithmtich.viewmodels.ViewModelProviderFactory;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    /*@Inject
    String str;*/

    /*@Inject
    boolean isAppNull;*/

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private AuthViewModel viewModel;

    private EditText userId;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userId = findViewById(R.id.user_id_input);

        progressBar = findViewById(R.id.progress_bar);

        findViewById(R.id.login_button).setOnClickListener(this);

        setLogo();

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(AuthViewModel.class);

        /*String hello = viewModel.hello();
        Log.d("Auth","on create  " + hello);*/

        subscribeObservers();


        //Log.d("Auth","on create" + str);
        //Log.d("Auth","on create : is app null?" + isAppNull);

    }


    private void onLoginSuccess(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLogo() {

        requestManager.load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

    /*private void subscribeObservers(){
        viewModel.observeUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null)
                {
                   Log.d("AuthAcitivity","onchanged  "+user.getEmail());
                }
            }
        });
    }*/

    private void subscribeObservers(){
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource !=  null)
                {
                    switch (userAuthResource.status)
                    {
                        case LOADING: {

                            showProgressBar(true);
                            break;
                        }

                        case AUTHENTICATED: {
                            Log.d("AuthActivity","onChanged "+userAuthResource.data.getEmail());
                            showProgressBar(false);
                            onLoginSuccess();
                            break;
                        }

                        case ERROR: {
                            Toast.makeText(AuthActivity.this, userAuthResource.message+ "\nDid you emter userId between 1 to 10?",Toast.LENGTH_LONG).show();
                            showProgressBar(false);
                            break;
                        }

                        case NOT_AUTHENTICATED: {
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });

    }

    private void showProgressBar(boolean isVisible){

        if(isVisible)
        {
            progressBar.setVisibility(View.VISIBLE);

        }
        else
        {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.login_button:
            {
                attemptLogin();

            }
        }

    }

    private void attemptLogin() {

        if(TextUtils.isEmpty(userId.getText().toString())){
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }
}

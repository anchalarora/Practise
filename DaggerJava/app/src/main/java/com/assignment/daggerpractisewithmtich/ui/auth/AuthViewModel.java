package com.assignment.daggerpractisewithmtich.ui.auth;

import android.util.Log;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.assignment.daggerpractisewithmtich.SessionManager;
import com.assignment.daggerpractisewithmtich.models.User;
import com.assignment.daggerpractisewithmtich.network.auth.AuthAPI;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private AuthAPI authAPI;


    //private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();
    private SessionManager sessionManager;

    
    // inject authapi into constructor
    @Inject
    public AuthViewModel(AuthAPI authAPI,SessionManager sessionManager) {
        this.authAPI = authAPI;
        this.sessionManager= sessionManager;
        Log.d(TAG, "AuthViewModel: viewmodel is working...");



        /*if(authAPI == null){

            Log.d(TAG, "AuthViewModel: auth api is NULL");
        }else{
            Log.d(TAG, "AuthViewModel: auth api is NOT NULL");
        }*/


        /*//convert flowable object to observable using toObservable
        authAPI.getUsers(1)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, "onNext"+user.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError "+e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/


    }

    private  LiveData<AuthResource<User>> queryUSerID(int userID){
        return LiveDataReactiveStreams.fromPublisher(
                authAPI.getUsers(userID)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if(user.getId() == -1){
                                    return AuthResource.error("Could not authenticate",null);
                                }
                                return AuthResource.authenticated(user);
                            }

                        })


                        .subscribeOn(Schedulers.io()));


    }

    public void authenticateWithId(int userID)
    {
       Log.d("","attempting to login");
       sessionManager.authenticateWIthId(queryUSerID(userID));
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();

    }

    //for practise
    public String hello(){
        return "Hello Anchal";
    }
}
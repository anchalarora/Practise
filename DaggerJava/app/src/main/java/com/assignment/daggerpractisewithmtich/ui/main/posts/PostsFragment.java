package com.assignment.daggerpractisewithmtich.ui.main.posts;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.daggerpractisewithmtich.R;
import com.assignment.daggerpractisewithmtich.models.Post;
import com.assignment.daggerpractisewithmtich.ui.VerticalSpacingItemDecoration;
import com.assignment.daggerpractisewithmtich.ui.main.Resource;
import com.assignment.daggerpractisewithmtich.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";

    private PostVIewModel postVIewModel;
    private RecyclerView recyclerView;

    @Inject
    PostRecyclerAdapter adapter;


    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        postVIewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(PostVIewModel.class);
        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers(){
        postVIewModel.observePosts().removeObservers(getViewLifecycleOwner());
        postVIewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if(listResource != null)
                Log.d(TAG, "onChanged: "+listResource.data);
                switch (listResource.status){

                    case LOADING:{
                        Log.d(TAG, "onChanged: LOADING...");
                        break;
                    }

                    case SUCCESS:{
                        Log.d(TAG, "onChanged: get posts...");
                        adapter.setPosts(listResource.data);

                        break;
                    }

                    case ERROR:{
                        Log.d(TAG, "onChanged: Error..."+listResource.message);
                        break;
                    }


                }



            }
        });
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);

        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);

    }
}

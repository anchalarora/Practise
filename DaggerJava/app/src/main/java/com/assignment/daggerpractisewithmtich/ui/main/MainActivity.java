package com.assignment.daggerpractisewithmtich.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.assignment.daggerpractisewithmtich.BaseActivity;
import com.assignment.daggerpractisewithmtich.R;
import com.assignment.daggerpractisewithmtich.ui.main.posts.PostsFragment;
import com.assignment.daggerpractisewithmtich.ui.main.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        //testFragment();
        init();


    }

    private void init(){
        //tie navigation controller object to the fragment in our view
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout);
        NavigationUI.setupWithNavController(navigationView,navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /*private void testFragment()
    {

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                new PostsFragment()).commit();
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();

        inflater.inflate(R.menu.main_menu,menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                sessionManager.logout();
                return true;

                //it references the back sarrow button
            case android.R.id.home : {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }else
                {
                    return false;
                }
            }

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.nav_profile:{
                //clear the backstack if user goes to ProfileScreen(home location)
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.main,true).build();
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(
                        R.id.profilescreen,
                        null,
                        navOptions
                );
                break;
            }
            case R.id.nav_posts:{
                if(isValidDestination(R.id.postsscreen)){
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.postsscreen);
                }

                break;
            }
        }
        //hightlight the ione which is selcted.
        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    //create a check that will prevent the fragment
    //from being added to back stack if its already in view
    //int destination is the id of the fragmentthats it trying to navigate to.
    //compare these ids and if its the one currently on then do the transaction.
    private boolean isValidDestination(int destination)
    {
        return destination != Navigation.findNavController(this,R.id.nav_host_fragment).getCurrentDestination().getId();
    }

    //it enables to open the navscxdrawer (but still doesnt close the navdrawer)and also toolbar's
    // back arrow(back button)
    // works when inside a fragment
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.nav_host_fragment),drawerLayout);
    }
}

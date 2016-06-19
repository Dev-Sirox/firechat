package com.davidhoeck.firechat.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davidhoeck.firechat.R;
import com.davidhoeck.firechat.fragments.ChatFragment;
import com.davidhoeck.firechat.fragments.ProfileFragment;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    //Logging
    public static final String TAG = "Firechat";

    //UI
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    //Auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);

        //Init Auth
        mAuth = FirebaseAuth.getInstance();

        setupToolbar();
        setupNavDrawer();


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAuthState();
    }

    private void setupToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("Firechat");

    }

    private void setupNavDrawer(){
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);

        //Default Fragment "Chat"
        selectDrawerItem(nvDrawer.getMenu().getItem(0));

        View headerLayout = nvDrawer.inflateHeaderView(R.layout.drawer_header);

        ImageView headerImage = (ImageView) headerLayout.findViewById(R.id.header_image);
        TextView headerName = (TextView) headerLayout.findViewById(R.id.header_name);

        headerName.setText("Hi User");

        if(mAuth.getCurrentUser() != null){

            headerName.setText(mAuth.getCurrentUser().getDisplayName());
            Picasso.with(getApplicationContext()).load(mAuth.getCurrentUser().getPhotoUrl().toString()).into(headerImage);
        }
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_chat:
                fragmentClass = ChatFragment.class;
                break;
            case R.id.nav_profile:
                fragmentClass = ProfileFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = ChatFragment.class;
                break;
            default:
                fragmentClass = ChatFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void checkAuthState(){
       if(mAuth.getCurrentUser() == null){
           startActivity(new Intent(this,AuthActivity.class));
       }

    }

}

package com.example.yajmana;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NavigateActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        Log.d("loaded","NavigationActivity");
        loadFragment(new HomeFragment(), getApplicationContext().getResources().getString(R.string.vanshawal_list));
        BottomNavigationView navView = findViewById(R.id.nav_view);//getting bottom navigation view and attaching the listener
        Log.d("loaded","navView");
        navView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("loaded","onNavigationItemReselected");
        Fragment fragment = null;
        String title=null;
        Log.i("MenuItem:", String.valueOf(item.getItemId()));
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                title=getApplicationContext().getResources().getString(R.string.vanshawal_list);
                break;

            case R.id.app_bar_search:
                fragment = new SearchFragment();
                title = getApplicationContext().getResources().getString(R.string.search_fragment);
                break;

            case R.id.navigation_dashboard:
                //fragment = new FeedbackFragment();
                fragment = new FeedbackFragment();
                title = getApplicationContext().getResources().getString(R.string.feedback_heading);
                break;

            case R.id.navigation_notifications:
                fragment = new NotificationsFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                title = getApplicationContext().getResources().getString(R.string.profile_heading);
                break;
        }
        return loadFragment(fragment, title);
    }

    public boolean loadFragment(Fragment fragment, String title) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            getSupportActionBar().setTitle(title);
            return true;
        }
        return false;
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear();
    }

}
package com.example.yajmana;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.yajmana.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NavigateActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView navView;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        loadFragment(new HomeFragment(), getApplicationContext().getResources().getString(R.string.vanshawal_list));
        navView = findViewById(R.id.nav_view);//getting bottom navigation view and attaching the listener
        navView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String title=null;

        Log.i("MenuItem:", String.valueOf(item.getItemId()));
        switch (item.getItemId()) {
            case R.id.navigation_home:
                item.setChecked(true);
                fragment = new HomeFragment();
                title = getApplicationContext().getResources().getString(R.string.vanshawal_list);
                return loadFragment(fragment, title);

            case R.id.app_bar_search:
                item.setChecked(true);
                fragment = new SearchFragment();
                title = getApplicationContext().getResources().getString(R.string.search_fragment);
                return loadFragment(fragment, title);

            case R.id.navigation_dashboard:
                item.setChecked(true);
                fragment = new FeedbackFragment();
                title = getApplicationContext().getResources().getString(R.string.feedback_heading);
                return loadFragment(fragment, title);

            case R.id.navigation_notifications:
                item.setChecked(true);
                fragment = new NotificationsFragment();
                return loadFragment(fragment, title);

            case R.id.navigation_profile:
                item.setChecked(true);
                fragment = new ProfileFragment();
                title = getApplicationContext().getResources().getString(R.string.profile_heading);
                return loadFragment(fragment, title);

            case R.id.navigation_logout:
                return logout();

            default:return false;
        }
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
            if(fragment instanceof HomeFragment){
//                Log.d("fragmentname", "Home");
                navView = findViewById(R.id.nav_view);
                menu = navView.getMenu();
                menu.getItem(0).setChecked(true);
            } else if(fragment instanceof AddFeedbackFragment) {
//                Log.d("fragmentname", "AddFeedbackFragment");
                navView = findViewById(R.id.nav_view);
                menu = navView.getMenu();
                menu.getItem(1).setChecked(true);
            }
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

    public boolean logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do do my action here
                Toast.makeText(NavigateActivity.this.getApplicationContext(), "Logging Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( NavigateActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return false;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.d("backPress", "Backpress");
    }

    public void disableUserInteraction(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void enableUserInteraction(){
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
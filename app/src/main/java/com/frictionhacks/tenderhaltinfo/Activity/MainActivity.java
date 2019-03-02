package com.frictionhacks.tenderhaltinfo.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.Fragments.DashboardFragment;
import com.frictionhacks.tenderhaltinfo.Fragments.NotificationFragment;
import com.frictionhacks.tenderhaltinfo.Fragments.ProfileFragment;
import com.frictionhacks.tenderhaltinfo.R;
import com.frictionhacks.tenderhaltinfo.Util.Preferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction ftdash,ftpro,ftnoti;
    ProfileFragment profileFragment;
    DashboardFragment dashboardFragment;
    NotificationFragment notificationFragment;


    FrameLayout fragmentContainer;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    ftpro=fragmentManager.beginTransaction();
                    ftpro.replace(R.id.fragment_container,profileFragment);
                    ftpro.commit();
                    return true;
                case R.id.navigation_dashboard:

                    ftdash=fragmentManager.beginTransaction();
                    ftdash.replace(R.id.fragment_container,dashboardFragment);
                    ftdash.commit();

                    return true;
                case R.id.navigation_notifications:
                ftnoti=fragmentManager.beginTransaction();
                ftnoti.replace(R.id.fragment_container,notificationFragment);
                ftnoti.commit();

                    return true;
            }
            return false;
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_navigation, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.top_navigation_about:
                // search action
                return true;
            case R.id.top_navigation_feedback:
                // location found

                return true;
            case R.id.top_navigation_logout:
                // refresh
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Preferences.getFirstRun(this)) {
            startActivity(new Intent(this, IntroActivity.class));
            finish();
        } else {
            getLocationPermission();

            dashboardFragment = new DashboardFragment();
            profileFragment = new ProfileFragment();
            fragmentContainer = findViewById(R.id.fragment_container);
            fragmentManager = getSupportFragmentManager();
            notificationFragment = new NotificationFragment();
            FragmentTransaction fmt = fragmentManager.beginTransaction();
            fmt.add(R.id.fragment_container, profileFragment);
            fmt.commit();

            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }


    }
    private void getLocationPermission(){

        Dexter.withActivity(MainActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Log.d("GRANTED",response.toString());

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Log.d("DENIED1",response.toString());
                        showSettingsDialog();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

        ContractorTenderDetailsDashboardModel.init();
        dashboardFragment = new DashboardFragment();
profileFragment=new ProfileFragment();
        fragmentContainer = findViewById(R.id.fragment_container);
        fragmentManager=getSupportFragmentManager();
        notificationFragment=new NotificationFragment();
        FragmentTransaction fmt=fragmentManager.beginTransaction();
        fmt.add(R.id.fragment_container,profileFragment);
        fmt.commit();


    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}

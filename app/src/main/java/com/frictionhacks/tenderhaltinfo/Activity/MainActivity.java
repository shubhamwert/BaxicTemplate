package com.frictionhacks.tenderhaltinfo.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.Fragments.DashboardFragment;
import com.frictionhacks.tenderhaltinfo.Fragments.NotificationFragment;
import com.frictionhacks.tenderhaltinfo.Fragments.ProfileFragment;
import com.frictionhacks.tenderhaltinfo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContractorTenderDetailsDashboardModel.init();
        dashboardFragment = new DashboardFragment();
profileFragment=new ProfileFragment();
        fragmentContainer = findViewById(R.id.fragment_container);
        fragmentManager=getSupportFragmentManager();
        notificationFragment=new NotificationFragment();
        FragmentTransaction fmt=fragmentManager.beginTransaction();
        fmt.add(R.id.fragment_container,profileFragment);
        fmt.commit();

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}

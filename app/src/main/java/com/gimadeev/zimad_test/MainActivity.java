package com.gimadeev.zimad_test;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;

import com.gimadeev.zimad_test.fragments.cats.CatListFragment;
import com.gimadeev.zimad_test.fragments.dogs.DogListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.gimadeev.zimad_test.NavigationUtils.setupActionBarWithNavController;
import static com.gimadeev.zimad_test.NavigationUtils.setupWithNavController;

public class MainActivity extends AppCompatActivity {

    private LiveData<NavController> navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabBottom);
        tabLayout.addTab(tabLayout.newTab().setText("Cats"));
        tabLayout.addTab(tabLayout.newTab().setText("Dogs"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.navHostContainer, new CatListFragment()).commit();
                    case 2:
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dog");
                        if (fragment == null) fragment = new DogListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.navHostContainer, fragment, "dog").commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (savedInstanceState == null) {
            //setupBottomNavigationBar();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //setupBottomNavigationBar();
    }

    //private void setupBottomNavigationBar() {
    //    BottomNavigationView navigationView = findViewById(R.id.bottomNavigation);
    //    List<Integer> navGraphIds = new ArrayList<Integer>(2) {{
    //        add(R.navigation.cat);
    //        add(R.navigation.dog);
    //    }};
//
    //    navController = setupWithNavController(navigationView, navGraphIds, getSupportFragmentManager(), R.id.navHostContainer, getIntent());
    //    navController.observe(this, new Observer<NavController>() {
    //        @Override
    //        public void onChanged(NavController controller) {
    //            setupActionBarWithNavController(controller, MainActivity.this);
    //        }
    //    });
    //}

    @Override
    public boolean onSupportNavigateUp() {
        return navController.getValue().navigateUp();
    }

    @Override
    public void onBackPressed() {
        if (!navController.getValue().popBackStack()) {
            super.onBackPressed();
        }
    }
}

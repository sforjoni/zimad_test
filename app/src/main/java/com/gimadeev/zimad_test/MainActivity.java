package com.gimadeev.zimad_test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LiveData<NavController> navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            setupBottomNavigationBar();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setupBottomNavigationBar();
    }

    private void setupBottomNavigationBar() {
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigation);
        List<Integer> navGraphIds = new ArrayList<Integer>(2) {{
            add(R.navigation.cat);
            add(R.navigation.dog);
        }};

        navController = NavigationUtils.setupWithNavController(navigationView, navGraphIds, getSupportFragmentManager(), R.id.navHostContainer, getIntent());
        navController.observe(this, new Observer<NavController>() {
            @Override
            public void onChanged(NavController controller) {

            }
        });
    }
}

package com.gimadeev.zimad_test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.gimadeev.zimad_test.NavigationUtils.setupActionBarWithNavController;
import static com.gimadeev.zimad_test.NavigationUtils.setupWithNavController;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_TAB = "selected_tab";

    private LiveData<NavController> navController;
    private TabLayout tabs;
    private List<Integer> rootFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            setupTabLayout(0);
        }

        rootFragments = new ArrayList<Integer>(2) {{
            add(R.id.catListFragment);
            add(R.id.dogListFragment);
        }};
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAB, tabs.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            setupTabLayout(savedInstanceState.getInt(SELECTED_TAB, 0));
        }

    }

    private void setupTabLayout(int selectedPosition) {
        tabs = findViewById(R.id.bottomNavigation);

        tabs.addTab(tabs.newTab().setText(R.string.title_cats));
        tabs.addTab(tabs.newTab().setText(R.string.title_dogs));

        tabs.getTabAt(selectedPosition).select();

        List<Integer> navGraphIds = new ArrayList<Integer>(2) {{
            add(R.navigation.cat);
            add(R.navigation.dog);
        }};

        navController = setupWithNavController(tabs, navGraphIds, getSupportFragmentManager(), R.id.navHostContainer, getIntent());
        navController.observe(this, new Observer<NavController>() {
            @Override
            public void onChanged(NavController controller) {
                setupActionBarWithNavController(controller, MainActivity.this);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.getValue().navigateUp();
    }

    @Override
    public void onBackPressed() {
        if (rootFragments.contains(navController.getValue().getCurrentDestination().getId())) {
            finish();
            return;
        }

        if (!navController.getValue().popBackStack()) {
            super.onBackPressed();
        }
    }
}

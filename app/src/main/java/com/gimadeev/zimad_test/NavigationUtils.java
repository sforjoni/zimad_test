package com.gimadeev.zimad_test;

import android.content.Intent;
import android.util.SparseArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class NavigationUtils {

    public static LiveData<NavController> setupWithNavController(final AppCompatActivity activity, final TabLayout navigationView, final List<Integer> navGraphIds,
                                                                 final FragmentManager fragmentManager, final Integer containerId, Intent intent) {

        final SparseArray<String> graphIdToTagMap = new SparseArray<>();
        final MutableLiveData<NavController> selectedNavController = new MutableLiveData<>();

        final Outer<Integer> firstFragmentGraphId = new Outer<>(0);

        final SparseArray<Integer> tabPositionToGraphId = new SparseArray<>();

        for (int i = 0; i < navGraphIds.size(); ++i) {
            String fragmentTag = getFragmentTag(i);
            Integer id = navGraphIds.get(i);

            NavHostFragment navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, id, containerId);
            int graphId = navHostFragment.getNavController().getGraph().getId();
            if (i == 0) {
                firstFragmentGraphId.setValue(graphId);
            }
            graphIdToTagMap.put(graphId, fragmentTag);
            tabPositionToGraphId.put(i, graphId);

            if (Integer.valueOf(graphId).equals(tabPositionToGraphId.get(navigationView.getSelectedTabPosition()))) {
                selectedNavController.setValue(navHostFragment.getNavController());
                attachNavHostFragment(fragmentManager, navHostFragment, i == 0);
            } else {
                detachNavHostFragment(fragmentManager, navHostFragment);
            }
        }

        final Outer<String> selectedItemTag = new Outer<>(graphIdToTagMap.get(tabPositionToGraphId.get(navigationView.getSelectedTabPosition())));
        final Outer<String> firstFragmentTag = new Outer<>(graphIdToTagMap.get(firstFragmentGraphId.getValue()));
        final Outer<Boolean> isOnFirstFragment = new Outer<>(selectedItemTag.getValue() == firstFragmentTag.getValue());

        navigationView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (fragmentManager.isStateSaved()) {
                    return;
                } else {
                    String newlySelectedItemTag = graphIdToTagMap.get(tabPositionToGraphId.get(tab.getPosition()));
                    if (selectedItemTag.getValue() != newlySelectedItemTag) {
                        fragmentManager.popBackStack(firstFragmentTag.getValue(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        NavHostFragment selectedFragment = (NavHostFragment) fragmentManager.findFragmentByTag(newlySelectedItemTag);

                        if (firstFragmentTag.getValue() != newlySelectedItemTag) {
                            FragmentTransaction transaction = fragmentManager.beginTransaction()
                                    .attach(selectedFragment)
                                    .setPrimaryNavigationFragment(selectedFragment);

                            for (int i = 0; i < graphIdToTagMap.size(); ++i) {
                                String tag = graphIdToTagMap.get(i);
                                if (tag != newlySelectedItemTag) {
                                    transaction.detach(fragmentManager.findFragmentByTag(firstFragmentTag.getValue()));
                                }
                            }

                            transaction
                                    .addToBackStack(firstFragmentTag.getValue())
                                    .setCustomAnimations(R.anim.nav_default_enter_anim,
                                            R.anim.nav_default_exit_anim,
                                            R.anim.nav_default_pop_enter_anim,
                                            R.anim.nav_default_pop_exit_anim)
                                    .setReorderingAllowed(true)
                                    .commit();

                        }
                        selectedItemTag.setValue(newlySelectedItemTag);

                        isOnFirstFragment.setValue(selectedItemTag.getValue() == firstFragmentTag.getValue());
                        selectedNavController.setValue(selectedFragment.getNavController());
                        return;
                    } else {
                        return;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                String newlySelectedItemTag = graphIdToTagMap.get(tabPositionToGraphId.get(tab.getPosition()));
                NavHostFragment selectedFragment = (NavHostFragment) fragmentManager.findFragmentByTag(newlySelectedItemTag);
                NavController controller = selectedFragment.getNavController();
                controller.popBackStack(controller.getGraph().getStartDestination(), false);
            }
        });

        setupDeepLinks(navigationView, navGraphIds, tabPositionToGraphId, fragmentManager, containerId, intent);

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                NavController controller = selectedNavController.getValue();

                if (controller != null) {
                    if (controller.getCurrentDestination() == null) {
                        controller.navigate(controller.getGraph().getId());
                    }
                }
            }
        });

        return selectedNavController;
    }

    public static void setupActionBarWithNavController(NavController navController, AppCompatActivity activity) {
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(activity, navController, configuration);
    }

    private static void setupDeepLinks(TabLayout navigationView, List<Integer> navGraphIds, final SparseArray<Integer> tabPositionToGraphId, FragmentManager fragmentManager, Integer containerId, Intent intent ) {
        for (int i = 0; i < navGraphIds.size(); ++i) {
            String fragmentTag = getFragmentTag(i);
            Integer id = navGraphIds.get(i);

            NavHostFragment navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, id, containerId);
            if (navHostFragment.getNavController().handleDeepLink(intent)) {
                for (int t = 0; t < tabPositionToGraphId.size(); ++t) {
                    if (tabPositionToGraphId.get(t).equals(navHostFragment.getNavController().getGraph().getId())) {
                        navigationView.getTabAt(t).select();
                        break;
                    }
                }
            }
        }
    }

    private static void detachNavHostFragment(FragmentManager fragmentManager, NavHostFragment navHostFragment) {
        fragmentManager.beginTransaction()
                .detach(navHostFragment)
                .commitNow();
    }

    private static void attachNavHostFragment(FragmentManager fragmentManager, NavHostFragment navHostFragment, Boolean isPrimaryNavFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction().attach(navHostFragment);
        if (isPrimaryNavFragment) {
            transaction.setPrimaryNavigationFragment(navHostFragment);
        }
        transaction.commitNow();
    }

    private static NavHostFragment obtainNavHostFragment(FragmentManager fragmentManager, String fragmentTag, Integer navGraphId, Integer containerId) {
        NavHostFragment fragment = (NavHostFragment) fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment != null) return fragment;

        fragment = NavHostFragment.create(navGraphId);
        fragmentManager.beginTransaction()
                .add(containerId, fragment, fragmentTag)
                .commitNow();

        return fragment;
    }

    private static Boolean isOnBackStack(FragmentManager fragmentManager, String backStackName) {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; ++i) {
            if (fragmentManager.getBackStackEntryAt(i).getName().equals(backStackName)) {
                return true;
            }
        }
        return false;
    }

    private static String getFragmentTag(Integer index) {
        return "bottomNavigation#" + index;
    }

    private static class Outer<T> {
        private T value;

        public Outer(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}

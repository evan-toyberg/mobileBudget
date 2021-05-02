package edu.moravian.csci299.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements IncomeFragment.Callbacks, ExpensesFragment.Callbacks {
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    FloatingActionButton addActon;
    public int[] tabIcons = {R.drawable.schedule_icon, R.drawable.overview,
            R.drawable.expenses_icon, R.drawable.income_icon};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        for (int tabIcon : tabIcons) {
            tabLayout.addTab(tabLayout.newTab().setIcon(tabIcon));
        }

        // Open scheduleFragment on app launch
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, new ScheduleFragment()).commit();



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new ScheduleFragment();
                        break;
                    case 1:
                        fragment = new OverViewFragment();
                        break;
                    case 2:
                        fragment = new ExpensesFragment();
                        break;
                    case 3:
                        fragment = new IncomeFragment();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                assert fragment != null;
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onActionSelected(Action action) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.simpleFrameLayout, ActionFragment.newInstance(action))
                .addToBackStack(null)
                .commit();
    }
}
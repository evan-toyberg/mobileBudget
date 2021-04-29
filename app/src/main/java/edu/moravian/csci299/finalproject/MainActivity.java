package edu.moravian.csci299.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
// Create a new Tab named "First"
        TabLayout.Tab overViewTab = tabLayout.newTab();
        overViewTab.setText(R.string.tab_1); // set the Text for the first Tab
        overViewTab.setIcon(R.drawable.ic_launcher_foreground); // set an icon for the
// first tab
        tabLayout.addTab(overViewTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        TabLayout.Tab expensesTab = tabLayout.newTab();
        expensesTab.setText(R.string.tab_2); // set the Text for the second Tab
        expensesTab.setIcon(R.drawable.ic_launcher_foreground); // set an icon for the second tab
        tabLayout.addTab(expensesTab); // add  the tab  in the TabLayout
// Create a new Tab named "Third"
        TabLayout.Tab incomeTab = tabLayout.newTab();
        incomeTab.setText(R.string.tab_3); // set the Text for the first Tab
        incomeTab.setIcon(R.mipmap.ic_income_icon); // set an icon for the first tab
        tabLayout.addTab(incomeTab); // add  the tab at in the TabLayout


// perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new OverViewFragment();
                        break;
                    case 1:
                        fragment = new ExpensesFragment();
                        break;
                    case 2:
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
}
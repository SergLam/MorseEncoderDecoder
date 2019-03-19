package com.samurai.morseencoder;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;

import android.support.v4.view.ViewPager;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements TabListener {
    private ViewPager viewPager;
    private TabPagerAdapter tabPagerAdapter;
    private ActionBar actionBar;
    protected String[] tabNames = {"","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabNames[0] = getResources().getString(R.string.tab_main);
        tabNames[1] = getResources().getString(R.string.tab_settings);
        tabNames[2] = getResources().getString(R.string.tab_rules);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(actionBar.newTab().setText(tabNames[i])
                    .setTabListener(this));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int postion) {
                actionBar.setSelectedNavigationItem(postion);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
// TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
// TODO Auto-generated method stub

    }

}

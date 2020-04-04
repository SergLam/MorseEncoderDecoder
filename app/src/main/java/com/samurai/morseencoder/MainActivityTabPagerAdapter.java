package com.samurai.morseencoder;

/**
 * Created by Sergey on 24.01.2017.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.samurai.morseencoder.fragments.MainFrame;
import com.samurai.morseencoder.fragments.Rules;
import com.samurai.morseencoder.fragments.Settings;

public class MainActivityTabPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private final int PAGE_COUNT=3;

    public MainActivityTabPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
            case 0:
                return new MainFrame();
            case 1:
                return new Settings();
            case 2:
                return new Rules();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override public CharSequence getPageTitle(int position) {
        String[] tabTitles = context.getResources().getStringArray(R.array.tab_names);
        return tabTitles[position];
    }
}

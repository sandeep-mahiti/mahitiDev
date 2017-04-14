package com.parentof.mai.views.adapters.reports_history_adapters;

/**
 * Created by sandeep HR on 30/01/17.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.parentof.mai.views.fragments.reports_chathistory_fragments.ChildHistoryFragment;
import com.parentof.mai.views.fragments.reports_chathistory_fragments.ChildReportsFragment;


/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class ChildReportsHistoryPagerAdater extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ChildReportsHistoryPagerAdater(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                ChildReportsFragment tab1 = new  ChildReportsFragment();;//.newInstance();
                return tab1;
            case 1:
                ChildHistoryFragment tab2 =new ChildHistoryFragment(); //.newInstance();
                return tab2;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
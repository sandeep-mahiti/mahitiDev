package com.parentof.mai.views.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.parentof.mai.R;
import com.parentof.mai.views.fragments.FiveFragment;
import com.parentof.mai.views.fragments.FragmentFour;
import com.parentof.mai.views.fragments.OneFragment;
import com.parentof.mai.views.fragments.ThreeFragment;
import com.parentof.mai.views.fragments.TwoFragment;


import java.util.ArrayList;
import java.util.List;

;

public class IntroductionActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        // Instantiate a ViewPager and a PagerAdapter
        ViewPager vPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(vPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment());
        adapter.addFragment(new TwoFragment());
        adapter.addFragment(new ThreeFragment());
        adapter.addFragment(new FragmentFour());
        adapter.addFragment(new FiveFragment());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }
}
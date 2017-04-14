package com.parentof.mai.views.activity.ipTabs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.ImprovementPlanModel.HomeTaskBean;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.views.adapters.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p/>
 * to handle interaction events.
 * Use the {@link IpsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IpsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String[] tabArray;
    Child childBean;
    List<HomeTaskBean> homeTaskBeanList;
    int noOfRecords=0;

    public IpsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * IpsFragmant.
     */
    // TODO: Rename and change types and number of parameters
    public static IpsFragment newInstance(Child childBean) {
        IpsFragment fragment = new IpsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, childBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            childBean = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ips_fragmant, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        tabArray = getResources().getStringArray(R.array.ip_tab_names); // names of the tabs

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        if (childBean.getChild().getId() != null)
            homeTaskBeanList = databaseHelper.getCcompletedIPsForActivity(childBean.getChild().getId());
        if(homeTaskBeanList!=null && !homeTaskBeanList.isEmpty() && homeTaskBeanList.size()>0 )
            noOfRecords=homeTaskBeanList.size();
        setupViewPager(viewPager);
        //viewPager.setCurrentItem(4);
        //set the view pager

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                setTabIcons();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                    int position = tab.getPosition();
                    tabLayout.setTabTextColors(CommonClass.getColor(getActivity(), R.color.gray), CommonClass.getColor(getActivity(), R.color.colorPrimaryDark));
                } catch (Exception e) {
                    Logger.logE("on tab", "tab--", e);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {
                    int position = tab.getPosition();
                    tabLayout.setTabTextColors(CommonClass.getColor(getActivity(), R.color.gray), CommonClass.getColor(getActivity(), R.color.colorPrimaryDark));
                } catch (Exception e) {
                    Logger.logE("on tab", "tab--", e);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //   Logger.logD("onPageScrolled", "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Logger.logD("onPageSelected", "onPageSelected" + position);
                viewPager.getCurrentItem();
                try {
                    tabLayout.setTabTextColors(CommonClass.getColor(getActivity(), R.color.tab_text_color), CommonClass.getColor(getActivity(), R.color.white));
                } catch (Exception e) {
                    Logger.logE("on tab", "tab--", e);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Logger.logD("onPageScrollStateChanged", "onPageScrollStateChanged" + state);
            }
        });


        return view;

    }

    //set icons for the view pager
    private void setTabIcons() {
        try {
            for (int i = 0; i < tabArray.length; i++) {
                tabLayout.getTabAt(i).setText(tabArray[i]);
            }
        } catch (Exception e) {
            Logger.logE("on tab", "tab--", e);
        }
    }


    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(CurrentFragment.newInstance(childBean), getResources().getString(R.string.current));
        viewPagerAdapter.addFragment(CompleteFragment.newInstance(childBean, (ArrayList<HomeTaskBean>) homeTaskBeanList), getResources().getString(R.string.complete).concat("("+noOfRecords+")"));
        viewPager.setAdapter(viewPagerAdapter);
    }


}

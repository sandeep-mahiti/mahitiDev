package com.parentof.mai.views.fragments.childTabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.db.DbConstants;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.views.activity.growthCharts.GraphListActivity;
import com.parentof.mai.views.activity.healthReport.HealthReportActivity;
import com.parentof.mai.views.activity.immuniazation.ImmunizationActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link HealthReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthReportFragment extends Fragment {

    @Bind(R.id.img_height)
    LinearLayout imgHeight;

    @Bind(R.id.img_weight)
    LinearLayout imgWeight;

    @Bind(R.id.img_circumfarence)
    LinearLayout imgCircumfarence;

    @Bind(R.id.rl_healthReport)
    LinearLayout rlHealthReport;

    @Bind(R.id.rl_immunization)
    LinearLayout rlImmunization;

    DatabaseHelper databaseHelper;
    Child childBean;
    String childId;
    private String TAG = "GrowthChartOptionActivity";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HealthReportFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment HealthReport.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthReportFragment newInstance(Child childBean) {
        HealthReportFragment fragment = new HealthReportFragment();
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
        View view = inflater.inflate(R.layout.fragment_health_report, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        return view;
    }
    @OnClick(R.id.img_height)
    void imgHeight() {
        navigateNextActivity(DbConstants.Height);
    }

    @OnClick(R.id.img_weight)
    void imgWeight() {
        navigateNextActivity(DbConstants.Weight);
    }

    @OnClick(R.id.img_circumfarence)
    void imgCircumfarence() {
        navigateNextActivity(DbConstants.Head_Circumfarence);

    }

    @OnClick(R.id.rl_healthReport)
    void healthReport() {
        navigateToNextActivity(HealthReportActivity.class);
    }

    @OnClick(R.id.rl_immunization)
    void immunization() {
        navigateToNextActivity(ImmunizationActivity.class);

    }

    void navigateNextActivity(String option) {
        Intent i = new Intent(getActivity(), GraphListActivity.class);
        i.putExtra(DbConstants.SELECTED_OPTION, option);
        i.putExtra(Constants.CHILD_LIST_BEAN, childBean);
        startActivity(i);
    }

    void navigateToNextActivity(Class activty) {
        try {
            Bundle b = new Bundle();
            if (childBean != null && childBean.getId() == null) {
                Logger.logD(TAG, "Child data not available");
            } else {
                b.putParcelable(Constants.SELECTED_CHILD, childBean);
                Intent i = new Intent(getActivity(), activty);
                i.putExtras(b);
                startActivity(i);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "onBndvwHldr :", e);
        }

    }


}

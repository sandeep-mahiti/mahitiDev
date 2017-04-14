package com.parentof.mai.views.activity.growthCharts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.db.DbConstants;
import com.parentof.mai.model.ChildBean;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.utils.CircularImageView;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.activity.growthCharts.adapters.ChildAgeHeightAdapter;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GraphListActivity extends AppCompatActivity implements View.OnClickListener, UpdateResponse {

    @Bind(R.id.rcv_child_age_height)
    RecyclerView rcvChildAgeHeight;
    DatabaseHelper databaseHelper;
    @Bind(R.id.save_child_age)
    TextView addAgeHeight;
    AlertDialog alertDialog1;
    ChildAgeHeightAdapter childAgeHeightAdapter;

    @Bind(R.id.rl_graph_banner)
    LinearLayout graphBanner;

    @Bind(R.id.ll_list_layout)
    LinearLayout listLayout;


    @Bind(R.id.rl_list_banner)
    RelativeLayout listBanner;

    @Bind(R.id.ll_child_profile)
    LinearLayout llChildProfile;

    @Bind(R.id.graph)
    GraphView graphView;
    List<ChildBean> childList;
    String childOption = "";
    String cId;
    @Bind(R.id.child_slected_option)
    TextView textViewOption;
    Child childBean;

    @Bind(R.id.img_Childgraph)
    CircularImageView imageView;
    @Bind(R.id.tv_GraphChildName)
    TextView tv_GraphChildName;
    double maxHeight;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_list);
        ButterKnife.bind(this);
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        toolbarInit();

        ui();
        init();
    }

    void ui() {
        addAgeHeight.setOnClickListener(this);
        graphBanner.setOnClickListener(this);
        listBanner.setOnClickListener(this);
        if (getIntent() != null) {
            childBean = (Child) getIntent().getParcelableExtra(Constants.CHILD_LIST_BEAN);
            childOption = getIntent().getStringExtra(DbConstants.SELECTED_OPTION);
            cId = childBean.getId();
            if (childOption != null)
                textViewOption.setText(childOption);
        }
    }

    void init() {
        databaseHelper = new DatabaseHelper(this);
        if (cId != null && childOption != null) {
            childList = databaseHelper.getChildAgeHeight(cId, childOption);
            maxHeight = databaseHelper.getChildMaxHeight(cId, childOption);
            Type collectionType = new TypeToken<List<ChildBean>>() {
            } // end new
                    .getType();

            String gsonString = new Gson().toJson(childList, collectionType);
            // gsonString = gsonString.replace("[", "").replace("]", "");
            Logger.logD(Constants.PROJECT, "Graph Conv to String--" + gsonString);

        }

        childAgeHeightAdapter = new ChildAgeHeightAdapter(this, childList, this, childOption, childBean.getChild().getFirstName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvChildAgeHeight.setLayoutManager(linearLayoutManager);
        rcvChildAgeHeight.setAdapter(childAgeHeightAdapter);
    }

    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbar != null) {
                toolbar.setTitle(getResources().getString(R.string.title_activity_chart_option));
                toolbar.setTitleTextColor(CommonClass.getColor(this, R.color.white));
                ImageView imageView = (ImageView) toolbar.findViewById(R.id.parentIcon);
                imageView.setImageResource(R.drawable.ic_action_overflow);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass.loadSettingsActivity(GraphListActivity.this);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_child_age:
                loadAddAgePopup();
                break;
            case R.id.rl_graph_banner:
                clickGraphView();
                break;
            case R.id.rl_list_banner:
                listLayout.setVisibility(View.VISIBLE);
                graphBanner.setVisibility(View.VISIBLE);

                listBanner.setVisibility(View.GONE);
                llChildProfile.setVisibility(View.GONE);
                graphView.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }

    void clickGraphView() {
        listLayout.setVisibility(View.GONE);
        graphBanner.setVisibility(View.GONE);

        listBanner.setVisibility(View.VISIBLE);
        llChildProfile.setVisibility(View.VISIBLE);
        graphView.setVisibility(View.VISIBLE);
        setGraphView();
        if (childBean != null && childBean.getChild().getId() != null) {
            boolean imgFlag = CommonClass.getImageFromDirectory(imageView, childBean.getChild().getId());
            if (!imgFlag) {
                imageView.setImageBitmap(CommonClass.StringToBitMap(prefs.getString("child_image", "")));
            }
        }
        if (childBean != null && childBean.getChild().getFirstName() != null)
            tv_GraphChildName.setText(childBean.getChild().getFirstName());
    }

    void setGraphView() {
        LineGraphSeries<DataPoint> seriesDummy = null;
        DataPoint[] dataPointArrayDummy = new DataPoint[9];

        DataPoint point1 = new DataPoint(0.5, 25);
        DataPoint point2 = new DataPoint(1, 38);
        DataPoint point3 = new DataPoint(1.5, 48);
        DataPoint point4 = new DataPoint(2, 55);
        DataPoint point5 = new DataPoint(3, 65);
        DataPoint point6 = new DataPoint(4.5, 70);
        DataPoint point7 = new DataPoint(6, 71);
        DataPoint point8 = new DataPoint(10, 73);
        DataPoint point9 = new DataPoint(16, 74);

        dataPointArrayDummy[0] = point1;
        dataPointArrayDummy[1] = point2;
        dataPointArrayDummy[2] = point3;
        dataPointArrayDummy[3] = point4;
        dataPointArrayDummy[4] = point5;
        dataPointArrayDummy[5] = point6;
        dataPointArrayDummy[6] = point7;
        dataPointArrayDummy[7] = point8;
        dataPointArrayDummy[8] = point9;

        seriesDummy = new LineGraphSeries<>(dataPointArrayDummy);
        seriesDummy.setColor(Color.RED);
        graphView.addSeries(seriesDummy);

        DataPoint point11 = new DataPoint(0.5, 15);
        DataPoint point21 = new DataPoint(1, 28);
        DataPoint point31 = new DataPoint(1.5, 38);
        DataPoint point41 = new DataPoint(2, 45);
        DataPoint point51 = new DataPoint(3, 55);
        DataPoint point61 = new DataPoint(4.5, 60);
        DataPoint point71 = new DataPoint(6, 61);
        DataPoint point81 = new DataPoint(10, 63);
        DataPoint point91 = new DataPoint(16, 64);

        dataPointArrayDummy[0] = point11;
        dataPointArrayDummy[1] = point21;
        dataPointArrayDummy[2] = point31;
        dataPointArrayDummy[3] = point41;
        dataPointArrayDummy[4] = point51;
        dataPointArrayDummy[5] = point61;
        dataPointArrayDummy[6] = point71;
        dataPointArrayDummy[7] = point81;
        dataPointArrayDummy[8] = point91;

        seriesDummy = new LineGraphSeries<>(dataPointArrayDummy);
        seriesDummy.setColor(Color.RED);
        graphView.addSeries(seriesDummy);

        DataPoint point111 = new DataPoint(0.5, 05);
        DataPoint point211 = new DataPoint(1, 18);
        DataPoint point311 = new DataPoint(1.5, 28);
        DataPoint point411 = new DataPoint(2, 35);
        DataPoint point511 = new DataPoint(3, 45);
        DataPoint point611 = new DataPoint(4.5, 50);
        DataPoint point711 = new DataPoint(6, 51);
        DataPoint point811 = new DataPoint(10, 53);
        DataPoint point911 = new DataPoint(16, 54);

        dataPointArrayDummy[0] = point111;
        dataPointArrayDummy[1] = point211;
        dataPointArrayDummy[2] = point311;
        dataPointArrayDummy[3] = point411;
        dataPointArrayDummy[4] = point511;
        dataPointArrayDummy[5] = point611;
        dataPointArrayDummy[6] = point711;
        dataPointArrayDummy[7] = point811;
        dataPointArrayDummy[8] = point911;

        seriesDummy = new LineGraphSeries<>(dataPointArrayDummy);
        seriesDummy.setColor(Color.RED);
        graphView.addSeries(seriesDummy);

        if (childList.isEmpty())
            return;
        LineGraphSeries<DataPoint> series = null;
        DataPoint[] dataPointArray = new DataPoint[childList.size()];
        for (int i = 0; i < childList.size(); i++) {
            ChildBean childBean = childList.get(i);
            Logger.logD(Constants.PROJECT, "Child Bean --" + childBean.getChildAge());

            Logger.logD(Constants.PROJECT, "Child Bean Height--" + childBean.getChildHeight());
            int years = 0;
            if (childBean.getAgeMode().equalsIgnoreCase("months")) {
                double yearsTemp = childBean.getChildAge() / 12;
                years = (int) Math.round(yearsTemp);
            } else {
                years = childBean.getChildAge();
            }
            dataPointArray[i] = new DataPoint(years, childBean.getChildHeight());
        }
        series = new LineGraphSeries<>(dataPointArray);
        graphView.addSeries(series);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);

        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Age(years)");
        if (DbConstants.Weight.equalsIgnoreCase(childOption))
            gridLabel.setVerticalAxisTitle(childOption + " (Kgs)");
        else
            gridLabel.setVerticalAxisTitle(childOption + " (cms)");
    }

    void loadAddAgePopup() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_child_age_popup, null);
        alertDialog.setView(dialogView);
        alertDialog1 = alertDialog.create();
        alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog1.show();
        final EditText ageEdit = (EditText) dialogView.findViewById(R.id.edt_child_age);
        final EditText heightEdit = (EditText) dialogView.findViewById(R.id.edt_child_height);
        ImageView closeImage = (ImageView) dialogView.findViewById(R.id.close);
        ImageView childImage_graph = (ImageView) dialogView.findViewById(R.id.childImage_graph);
        TextView saveChildAge = (TextView) dialogView.findViewById(R.id.save_child_age);
        TextView cms = (TextView) dialogView.findViewById(R.id.cms);
        TextView childName = (TextView) dialogView.findViewById(R.id.childName);
        if (childBean != null && childBean.getChild().getId() != null) {
            boolean imgFlag = CommonClass.getImageFromDirectory(childImage_graph, childBean.getChild().getId());
            if (!imgFlag) {
                childImage_graph.setImageBitmap(CommonClass.StringToBitMap(prefs.getString("child_image", "")));
            }
        }
        if (childBean != null && childBean.getChild().getFirstName() != null)
            childName.setText(childBean.getChild().getFirstName());
        if (DbConstants.Weight.equalsIgnoreCase(childOption))
            cms.setText("kgs");
        else
            cms.setText("cms");

        TextView childOptionTv = (TextView) dialogView.findViewById(R.id.child_option);
        if (childOption != null)
            childOptionTv.setText(childOption);
        final Spinner ageMode = (Spinner) dialogView.findViewById(R.id.ageMode);
        saveChildAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ageModeStr = ageMode.getSelectedItem().toString().trim();
                String ageStr = ageEdit.getText().toString().trim();
                callInsertQuery(ageStr, heightEdit.getText().toString().trim(), ageModeStr);
            }
        });
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

    }

    void callInsertQuery(String age, String height, String ageMode) {
        try {
            if (age.isEmpty()) {
                ToastUtils.displayToast("Please enter your child Age", this);
            } else if ("Years".equalsIgnoreCase(ageMode) && "Height".equalsIgnoreCase(childOption) && Integer.valueOf(age) > 16) {
                ToastUtils.displayToast("Child age max 16Years ", this);
            } else if ("Years".equalsIgnoreCase(ageMode) && "Weight".equalsIgnoreCase(childOption) && Integer.valueOf(age) > 16) {
                ToastUtils.displayToast("Child age max 16Years", this);
            } else if ("Years".equalsIgnoreCase(ageMode) && "Head Circumference".equalsIgnoreCase(childOption) && Integer.valueOf(age) > 5) {
                ToastUtils.displayToast("Child age max 5Years", this);
            } else if (height.isEmpty()) {
                ToastUtils.displayToast("Please enter your child " + childOption, this);
            } else if (Double.parseDouble(height) < maxHeight) {
                ToastUtils.displayToast(childOption + " does not enter lower", this);
            } else {
                int ageChild = Integer.valueOf(age);
                double heightChild = Double.parseDouble(height);
                ChildBean childBeanTmp = new ChildBean();
                childBeanTmp.setChildName(childBean.getChild().getFirstName());
                childBeanTmp.setChildAge(ageChild);
                childBeanTmp.setChildHeight(heightChild);
                childBeanTmp.setAgeMode(ageMode);
                childBeanTmp.setChildId(cId);
                childBeanTmp.setChildOptionFlag(childOption);
                databaseHelper.insertChildAge(childBeanTmp);
                // databaseHelper.updateChildAgeHeight(childBean, 1);
                alertDialog1.dismiss();
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alertDialog1.dismiss();
        }
    }


    void callUpdateQuery(String age, String height, String ageMode, int pId, AlertDialog alertDialog1) {
        try {
            if (age.isEmpty()) {
                ToastUtils.displayToast("Please enter your child Age", this);
            } else if ("Years".equalsIgnoreCase(ageMode) && "Height".equalsIgnoreCase(childOption) && Integer.valueOf(age) > 16) {
                ToastUtils.displayToast("Child age max 16Years ", this);
            } else if ("Years".equalsIgnoreCase(ageMode) && "Weight".equalsIgnoreCase(childOption) && Integer.valueOf(age) > 16) {
                ToastUtils.displayToast("Child age max 16Years", this);
            } else if ("Years".equalsIgnoreCase(ageMode) && "Head Circumference".equalsIgnoreCase(childOption) && Integer.valueOf(age) > 5) {
                ToastUtils.displayToast("Child age max 5Years", this);
            } else if (height.isEmpty()) {
                ToastUtils.displayToast("Please enter your child " + childOption, this);
            } /*else if (Double.parseDouble(height) < maxHeight) {
                ToastUtils.displayToast(childOption + " does not enter lower", this);
            } */ else {
                int ageChild = Integer.valueOf(age);
                double heightChild = Double.parseDouble(height);
                ChildBean childBeanTmp = new ChildBean();
                childBeanTmp.setChildName(childBean.getChild().getFirstName());
                childBeanTmp.setChildAge(ageChild);
                childBeanTmp.setChildHeight(heightChild);
                childBeanTmp.setAgeMode(ageMode);
                childBeanTmp.setChildOptionFlag(childOption);
                childBeanTmp.setChildId(cId);
                databaseHelper.updateChildAgeHeight(childBeanTmp, pId);
                alertDialog1.dismiss();
                ToastUtils.displayToast("Updated Successfully", this);
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateChildData(String age, String height, String mode, int id, AlertDialog alertDialog1) {
        callUpdateQuery(age, height, mode, id, alertDialog1);
        graphView.removeAllSeries();
        // childList = databaseHelper.getChildAgeHeight(cId, childOption);
        setGraphView();
    }
}

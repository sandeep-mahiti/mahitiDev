package com.parentof.mai.views.activity.immuniazation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.parentof.mai.R;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImmunizationActivity extends AppCompatActivity {


    @Bind(R.id.rcv_immunization)
    RecyclerView rcvImmunization;
   /* String[] ageGroups = {"Birth", *//*"Birth", "Birth",*//*
            "6 weeks", "6 weeks", "6 weeks", "6 weeks", "6 weeks", "6 weeks",
            "10 weeks", "10 weeks", "10 weeks", "10 weeks", "10 weeks",
            "14 weeks", "14 weeks", "14 weeks", "14 weeks", "14 weeks",
            "6 months", "6 months",
            "9 months", "9 months",
            "9-12 months", "12 months",
            "15 months", "15 months", "15 months",
            "16 to 18 months", "16 to 18 months", "16 to 18 months",
            "18 months",
            "2 years", "4 to 6 years",
            "4 to 6 years", "4 to 6 years", "4 to 6 years",
            "10 to 12 years", "10 to 12 years"};*/

    String[] ageGroups = {"BirthDay", "6 weeks", "10 weeks", "14 weeks",
            "6 months", "9 months", "9-12 months", "12 months", "15 months",
            "16 to 18 months", "18 months", "2 years", "4 to 6 years", "10 to 12 years"};

    String[] vaccinationGroups = {"BCG", "OPV 0", "Hep-B 1",

            "DTwP 1", "IPV 1", "Hep-B 2", "Hib 1", "Rotavirus 1", "PCV 1",

            "DTwP 2", "IPV 2", "Hib 2", "Rotavirus 2", "PCV 2",
            "DTwP 3", "IPV 3", "Hib 3", "Rotavirus 3", "PCV 3",
            "OPV 1", "Hep-B 3",
            "OPV 2", "MMR-1",
            "Typhoid Conjugate Vaccine",
            "Hep-A 1",
            "MMR 2", "Varicella 1", "PCV booster",
            "DTwPB1/DTaPB1", "IPV B1", "Hib B1",
            "Hep-A 2",
            "Booster of Typhoid Conjugate Vaccine",
            "DTwP B2/DTaP B2", "OPV 3", "Varicella 2", "MMR 3",
            "Tdap/Td", "HPV"};
    String[] vaccineId = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
            "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39"};
    ImmunizationBean immunizationBean;
    List<ImmunizationBean> listImmunization = new ArrayList<>();

    Child childBean;
    String childId;
    @Bind(R.id.imgImmunization)
    CircularImageView imgChild;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immunization);
        ButterKnife.bind(this);
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        childBean = (Child) getIntent().getParcelableExtra(Constants.SELECTED_CHILD);
        if (childBean != null && childBean.getId() != null) {
            childId = childBean.getChild().getId();
            TextView name = (TextView) findViewById(R.id.tv_name);
            name.setText(childBean.getChild().getFirstName());
        }
        fillImmunizationBean();
        toolbarInit();
        if (childId != null) {
            boolean imgFlag = CommonClass.getImageFromDirectory(imgChild, childId);
            if (!imgFlag) {
                imgChild.setImageBitmap(CommonClass.StringToBitMap(prefs.getString("child_image", "")));
            }
        }

    }

    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbar != null) {
                toolbar.setTitle("Immunization Record for".concat(" " + childBean.getChild().getFirstName()));
                toolbar.setTitleTextColor(CommonClass.getColor(this, R.color.white));
                ImageView imageView = (ImageView) toolbar.findViewById(R.id.parentIcon);
                imageView.setImageResource(R.drawable.ic_action_overflow);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonClass.loadSettingsActivity(ImmunizationActivity.this);
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


    void fillImmunizationBean() {

        immunizationBean = new ImmunizationBean(ageGroups[0], vaccineId[0], vaccinationGroups[0], vaccineId[0], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[0], vaccineId[1], vaccinationGroups[1], vaccineId[1], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[0], vaccineId[2], vaccinationGroups[2], vaccineId[2], false);
        listImmunization.add(immunizationBean);

        immunizationBean = new ImmunizationBean(ageGroups[1], vaccineId[3], vaccinationGroups[3], vaccineId[3], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[1], vaccineId[4], vaccinationGroups[4], vaccineId[4], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[1], vaccineId[5], vaccinationGroups[5], vaccineId[5], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[1], vaccineId[6], vaccinationGroups[6], vaccineId[6], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[1], vaccineId[7], vaccinationGroups[7], vaccineId[7], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[1], vaccineId[8], vaccinationGroups[8], vaccineId[8], false);
        listImmunization.add(immunizationBean);

        immunizationBean = new ImmunizationBean(ageGroups[2], vaccineId[9], vaccinationGroups[9], vaccineId[9], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[2], vaccineId[10], vaccinationGroups[10], vaccineId[10], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[2], vaccineId[11], vaccinationGroups[11], vaccineId[11], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[2], vaccineId[12], vaccinationGroups[12], vaccineId[12], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[2], vaccineId[13], vaccinationGroups[13], vaccineId[13], false);
        listImmunization.add(immunizationBean);

        immunizationBean = new ImmunizationBean(ageGroups[3], vaccineId[14], vaccinationGroups[14], vaccineId[15], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[3], vaccineId[15], vaccinationGroups[15], vaccineId[15], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[3], vaccineId[16], vaccinationGroups[16], vaccineId[16], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[3], vaccineId[17], vaccinationGroups[17], vaccineId[17], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[3], vaccineId[18], vaccinationGroups[18], vaccineId[18], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[4], vaccineId[19], vaccinationGroups[19], vaccineId[19], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[4], vaccineId[20], vaccinationGroups[20], vaccineId[20], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[5], vaccineId[21], vaccinationGroups[21], vaccineId[21], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[5], vaccineId[22], vaccinationGroups[22], vaccineId[22], false);

        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[6], vaccineId[23], vaccinationGroups[23], vaccineId[23], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[7], vaccineId[24], vaccinationGroups[24], vaccineId[24], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[8], vaccineId[25], vaccinationGroups[25], vaccineId[25], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[8], vaccineId[26], vaccinationGroups[26], vaccineId[26], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[8], vaccineId[27], vaccinationGroups[27], vaccineId[27], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[9], vaccineId[28], vaccinationGroups[28], vaccineId[28], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[9], vaccineId[29], vaccinationGroups[29], vaccineId[29], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[9], vaccineId[30], vaccinationGroups[30], vaccineId[30], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[10], vaccineId[31], vaccinationGroups[31], vaccineId[31], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[11], vaccineId[32], vaccinationGroups[32], vaccineId[32], true);
        listImmunization.add(immunizationBean);

        immunizationBean = new ImmunizationBean(ageGroups[12], vaccineId[33], vaccinationGroups[33], vaccineId[33], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[12], vaccineId[34], vaccinationGroups[34], vaccineId[34], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[12], vaccineId[35], vaccinationGroups[35], vaccineId[35], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[12], vaccineId[36], vaccinationGroups[36], vaccineId[36], false);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[13], vaccineId[37], vaccinationGroups[37], vaccineId[37], true);
        listImmunization.add(immunizationBean);
        immunizationBean = new ImmunizationBean(ageGroups[13], vaccineId[38], vaccinationGroups[38], vaccineId[38], false);
        listImmunization.add(immunizationBean);

        ImmunizationAdapter immunizationAdapter = new ImmunizationAdapter(this, listImmunization, childId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvImmunization.setLayoutManager(linearLayoutManager);
        rcvImmunization.setAdapter(immunizationAdapter);


    }


}


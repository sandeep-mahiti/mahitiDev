package com.parentof.mai.views.activity.healthReport;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parentof.mai.R;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.healthReportModel.AnaemiaModel;
import com.parentof.mai.model.healthReportModel.BioMetricBean;
import com.parentof.mai.model.healthReportModel.ConditionModel;
import com.parentof.mai.model.healthReportModel.DentalModel;
import com.parentof.mai.model.healthReportModel.EyeModel;
import com.parentof.mai.model.healthReportModel.PhysicalModel;
import com.parentof.mai.model.healthReportModel.SystematicModel;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ToastUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HealthReportActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.ll_bio)
    LinearLayout llBio;

    @Bind(R.id.hp_childName)
    TextView childNameTV;


    @Bind(R.id.ll_condition)
    LinearLayout llCondition;

    @Bind(R.id.ll_eye)
    LinearLayout llEye;

    @Bind(R.id.ll_dental)
    LinearLayout llDental;

    @Bind(R.id.ll_physical)
    LinearLayout llPhysical;

    @Bind(R.id.ll_systematic)
    LinearLayout llSystematic;

    @Bind(R.id.ll_anaemia)
    LinearLayout llAnaemia;

    @Bind(R.id.ll_summary)
    LinearLayout llSummary;

    @Bind(R.id.img_bio)
    ImageView imgBio;

    @Bind(R.id.img_condition_img)
    ImageView imgCondition;

    @Bind(R.id.img_eye)
    ImageView imgEye;

    @Bind(R.id.img_dental)
    ImageView imgDental;

    @Bind(R.id.img_physical)
    ImageView imgPhysical;

    @Bind(R.id.img_sys)
    ImageView imgSys;

    @Bind(R.id.img_anaemia)
    ImageView imgAnaemia;

    @Bind(R.id.img_summary)
    ImageView imgSummary;

    @Bind(R.id.tv_bio)
    TextView tvBio;

    @Bind(R.id.tv_condition)
    TextView tvCondition;

    @Bind(R.id.tv_eye)
    TextView tvEye;

    @Bind(R.id.tv_dental)
    TextView tvDental;

    @Bind(R.id.tv_physical)
    TextView tvPhysical;

    @Bind(R.id.tv_systematic)
    TextView tvSystematic;

    @Bind(R.id.tv_anaemia)
    TextView tvAnaemia;

    @Bind(R.id.tv_summary)
    TextView tvSummary;

    @Bind(R.id.img_bio_row)
    ImageView bioArrow;

    @Bind(R.id.img_con_arrow)
    ImageView conArrow;
    @Bind(R.id.img_eye_arrow)
    ImageView eyeArrow;

    @Bind(R.id.img_dental_arrow)
    ImageView dentalArrow;

    @Bind(R.id.img_physical_arrow)
    ImageView phyArrow;

    @Bind(R.id.img_sys_arrow)
    ImageView sysArrow;

    @Bind(R.id.img_anaemia_arrow)
    ImageView anaemiaArrow;

    @Bind(R.id.img_summary_arrow)
    ImageView summaryArrow;
    @Bind(R.id.bio_include)
    LinearLayout bioIncludeLayout;

    int flag = 1;
    int flagCon = 1;
    @Bind(R.id.edt_pulse)
    EditText edtPulse;
    @Bind(R.id.edt_bp)
    EditText edtBp;
    @Bind(R.id.edt_height_bio)
    EditText edtHeightBio;
    @Bind(R.id.edt_weight_bio)
    EditText edtWeightBio;
    @Bind(R.id.edt_bmi)
    EditText edtBmi;
    @Bind(R.id.bio_save)
    TextView bioSave;


    DatabaseHelper databaseHelper;
    BioMetricBean bioMetric;
    double bmi = 0;
    String resultObservation;

    @Bind(R.id.tv_observation)
    TextView tvObservation;

    @Bind(R.id.condition_include)
    LinearLayout conditionInclude;

    @Bind(R.id.dd_hair)
    Spinner ddConHair;

    @Bind(R.id.dd_skin)
    Spinner ddConSkin;

    @Bind(R.id.dd_nail)
    Spinner ddConNail;

    @Bind(R.id.dd_nose)
    Spinner ddConNose;

    @Bind(R.id.dd_ear)
    Spinner ddConEar;
    @Bind(R.id.dd_pure_tone)
    Spinner ddConPureTone;

    @Bind(R.id.dd_throat)
    Spinner ddThroat;


    @Bind(R.id.tv_dd_hair)
    TextView tvHair;
    @Bind(R.id.tv_dd_skin)
    TextView tvSkin;
    @Bind(R.id.tv_dd_nail)
    TextView tvNail;
    @Bind(R.id.tv_dd_nose)
    TextView tvNose;
    @Bind(R.id.tv_dd_ear)
    TextView tvEar;
    @Bind(R.id.tv_dd_pure_tone)
    TextView tvPureTone;

    @Bind(R.id.tv_dd_throat)
    TextView tvDdThroat;

    @Bind(R.id.condition_save)
    TextView conditionSave;
    ConditionModel conditionModelMain;

    //Eye...
    @Bind(R.id.eye_include)
    LinearLayout eyeInclude;
    int eyeFlag = 1;

    @Bind(R.id.edt_vis_left)
    EditText editVisualLeft;
    @Bind(R.id.edt_vis_right)
    EditText editVisualRight;

    @Bind(R.id.edt_ref_left)
    EditText editRefLeft;
    @Bind(R.id.edt_ref_right)
    EditText editRefRight;

    @Bind(R.id.sp_color_vision)
    Spinner spColorVision;

    @Bind(R.id.sp_squint)
    Spinner spSquint;

    @Bind(R.id.sp_allergy)
    Spinner spAllergy;

    @Bind(R.id.tv_color_vision)
    TextView tvColorvision;

    @Bind(R.id.tv_squint)
    TextView tvSquint;

    @Bind(R.id.tv_allergy)
    TextView tvAllergy;

    List<EditText> eyeLeftList = new ArrayList<>();
    List<EditText> eyeRightList = new ArrayList<>();
    @Bind(R.id.eye_save)
    TextView eyeSave;
    EyeModel eyeModelMain = new EyeModel();

    //Dental
    @Bind(R.id.dental_include)
    LinearLayout dentalInclude;
    int dentalFlag = 1;
    @Bind(R.id.d_normal)
    CheckBox dNormal;

    @Bind(R.id.d_satins)
    CheckBox dStains;

    @Bind(R.id.d_tooth_cavity)
    CheckBox dToothCavity;

    @Bind(R.id.d_gum_bleeding)
    CheckBox dGumBleeding;

    @Bind(R.id.d_tartae)
    CheckBox dTarte;

    @Bind(R.id.d_plaque)
    CheckBox dPlaque;

    @Bind(R.id.d_soft_tissue)
    CheckBox dSoftTissue;

    @Bind(R.id.d_gum_inflamma)
    CheckBox dGumInflamma;

    @Bind(R.id.d_bad_breath)
    CheckBox dBadBreath;
    @Bind(R.id.dental_save)
    TextView dentalSave;
    DentalModel dentalModelMain;

    @Bind(R.id.ll_dental_checked_tv)
    LinearLayout llDentalCheckedTv;

    @Bind(R.id.ll_dental_checkboxs)
    LinearLayout llDentalCheckBoxs;

    //Systematic
    @Bind(R.id.systematic_include)
    LinearLayout systematicInclude;
    int systematicFlag = 1;
    @Bind(R.id.systematic_save)
    TextView saveSystematic;
    @Bind(R.id.systematic_textview)
    LinearLayout systematicTextview;
    @Bind(R.id.systematic_spinners)
    LinearLayout systematicSpinner;

    @Bind(R.id.sp_cardiov)
    Spinner spCardiov;

    @Bind(R.id.sp_resp)
    Spinner spResp;

    @Bind(R.id.sp_abdominal)
    Spinner spAbdominal;

    @Bind(R.id.sp_musculoskeletal)
    Spinner spMusculoskeletal;

    @Bind(R.id.tv_card)
    TextView tvCardiov;

    @Bind(R.id.tv_resp)
    TextView tvResp;

    @Bind(R.id.tv_abdominal)
    TextView tvAbdominal;

    @Bind(R.id.tv_musculoskeletal)
    TextView tvMusculoskeletal;

    SystematicModel systematicModelMain;

    //Physical
    @Bind(R.id.physical_include)
    LinearLayout physicalInclude;
    int physicalFlag = 1;
    @Bind(R.id.physical_save)
    TextView physicalSave;

    @Bind(R.id.sp_icterus)
    Spinner spIcterus;
    @Bind(R.id.sp_cyanosis)
    Spinner spCyanosis;

    @Bind(R.id.sp_clubbing)
    Spinner spClubbing;
    @Bind(R.id.sp_oedema)
    Spinner spOedema;

    @Bind(R.id.sp_sacrat)
    Spinner spSacrat;
    @Bind(R.id.sp_peri)
    Spinner spPeri;

    @Bind(R.id.sp_lymphad)
    Spinner spLymphad;
    @Bind(R.id.sp_cerAnterior)
    Spinner spCerAnterior;

    @Bind(R.id.sp_cerPosterior)
    Spinner spCerPosterior;
    @Bind(R.id.sp_axillary)
    Spinner spAllillary;

    @Bind(R.id.sp_occipital)
    Spinner spoccipital;
    @Bind(R.id.sp_fpitrochlear)
    Spinner spFitrochlear;

    /*TextViews*/
    @Bind(R.id.tv_icterus)
    TextView tvIcterus;
    @Bind(R.id.tv_cyanosis)
    TextView tvCyanosis;

    @Bind(R.id.tv_clubbing)
    TextView tvClubbing;
    @Bind(R.id.tv_oedema)
    TextView tvOedema;

    @Bind(R.id.tv_sacrat)
    TextView tvSacrat;
    @Bind(R.id.tv_peri)
    TextView tvPeri;

    @Bind(R.id.tv_lymphad)
    TextView tvLymphad;
    @Bind(R.id.tv_cerAnterior)
    TextView tvCerAnterior;

    @Bind(R.id.tv_cerPosterior)
    TextView tvCerPosterior;
    @Bind(R.id.tv_axillary)
    TextView tvAllillary;

    @Bind(R.id.tv_occipital)
    TextView tvOccipital;
    @Bind(R.id.tv_fpitrochlear)
    TextView tvFitrochlear;

    @Bind(R.id.ll_physical_spinner)
    LinearLayout phySpinners;
    @Bind(R.id.ll_physical_tv)
    LinearLayout phyTextviews;
    PhysicalModel physicalModelMain;

    /*Anaemia..*/
    @Bind(R.id.anaemia_include)
    LinearLayout anaemiaInclude;

    int anaemiaFla = 1;

    @Bind(R.id.a_skin)
    CheckBox aSkin;
    @Bind(R.id.a_tongue)
    CheckBox aTongue;
    @Bind(R.id.a_sclera)
    CheckBox aSclera;
    @Bind(R.id.a_lips)
    CheckBox aLips;
    @Bind(R.id.a_nailBed)
    CheckBox aNailBed;

    @Bind(R.id.anaemia_save)
    TextView anaemiaSave;

    @Bind(R.id.ll_anaemia_checkbox)
    LinearLayout llAnaemiaCheckbox;

    @Bind(R.id.ll_anaemia_checked_tv)
    LinearLayout llAnaemiaTv;
    AnaemiaModel anaemiaModelMain;

    //Summary
    @Bind(R.id.summary_include)
    LinearLayout includeSummary;
    int summaryFlag = 1;
    Child childBean;
    String childId;
    int ibp1;
    int ibp2;
    @Bind(R.id.hrp_childBloodGroup)
    TextView childBloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_report);
        ButterKnife.bind(this);

        databaseHelper = new DatabaseHelper(this);
        childBean = (Child) getIntent().getParcelableExtra(Constants.SELECTED_CHILD);
        if (childBean != null && childBean.getId() != null)
            childId = childBean.getId();

        toolbarInit();
        if (childBean.getChild().getFirstName() != null)
            childNameTV.setText(childBean.getChild().getFirstName());
        if (childBean.getChild().getBloodGroup() != null)
            childBloodGroup.setText(childBean.getChild().getBloodGroup());

        llAnaemia.setOnClickListener(this);
        llBio.setOnClickListener(this);
        llCondition.setOnClickListener(this);
        llDental.setOnClickListener(this);

        llEye.setOnClickListener(this);
        llPhysical.setOnClickListener(this);
        llSystematic.setOnClickListener(this);
        llSummary.setOnClickListener(this);
        inputTypeText();
        getBioMetricData();
        getConditionDataFromDb();
        edtHeightBio.addTextChangedListener(mTextEditorWatcher);
        edtWeightBio.addTextChangedListener(mTextEditorWatcher);
        edtBmi.setInputType(InputType.TYPE_NULL);

        /*editRefLeft.setInputType(InputType.TYPE_CLASS_NUMBER);
        editRefRight.setInputType(InputType.TYPE_CLASS_NUMBER);

        editRefLeft.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        editRefRight.setRawInputType(InputType.TYPE_CLASS_NUMBER);

        editRefRight.setRawInputType(Configuration.KEYBOARD_QWERTY);
        editRefLeft.setRawInputType(Configuration.KEYBOARD_QWERTY);*/

        addEyeEditList();
        getEyeFromDb();
        getDentalStringFromDb();
        getPhysicalDataFromDb();
        getSystematicDataFromDb();
        getAnaemiaStringFromDb();


    }

    @OnClick(R.id.anaemia_save)
    void anaemiaSave() {
        try {
            List<String> checkedAnaemiaList = new ArrayList<>();
            if (aSkin.isChecked())
                checkedAnaemiaList.add(aSkin.getText().toString());
            if (aLips.isChecked())
                checkedAnaemiaList.add(aLips.getText().toString());
            if (aTongue.isChecked())
                checkedAnaemiaList.add(aTongue.getText().toString());
            if (aNailBed.isChecked())
                checkedAnaemiaList.add(aNailBed.getText().toString());
            if (aSclera.isChecked())
                checkedAnaemiaList.add(aSclera.getText().toString());

            String listString = "";
            for (int i = 0; i < checkedAnaemiaList.size(); i++) {
                if (i != checkedAnaemiaList.size() - 1)
                    listString += checkedAnaemiaList.get(i) + ",";
                else
                    listString += checkedAnaemiaList.get(i);
            }
            Logger.logD(Constants.PROJECT, "listString size-->" + listString);


            if ("Save".equalsIgnoreCase(anaemiaSave.getText().toString())) {
                AnaemiaModel anaemiaModel = new AnaemiaModel();
                anaemiaModel.setChildId(childId);
                anaemiaModel.setAnaemiaCheckedString(listString);
                databaseHelper.insertAnaemiaData(anaemiaModel);
                getAnaemiaStringFromDb();
                llAnaemiaCheckbox.setVisibility(View.GONE);
                llAnaemiaTv.setVisibility(View.VISIBLE);
                anaemiaSave.setText("Edit");
                ToastUtils.displayToast("Saved Successfully", this);
            } else if ("Edit".equalsIgnoreCase(anaemiaSave.getText().toString())) {
                llAnaemiaTv.setVisibility(View.GONE);
                llAnaemiaCheckbox.setVisibility(View.VISIBLE);
                callMethodAnaemiACheck();
                anaemiaSave.setText("Edit and Save");
            } else if ("Edit and Save".equalsIgnoreCase(anaemiaSave.getText().toString())) {
                AnaemiaModel anaemiaModel = new AnaemiaModel();
                anaemiaModel.setChildId(childId);
                anaemiaModel.setAnaemiaCheckedString(listString);
                databaseHelper.updateAnaemiaData(anaemiaModel, anaemiaModelMain.getpId());
                anaemiaSave.setText("Edit");
                ToastUtils.displayToast("Updated Successfully", this);
                getAnaemiaStringFromDb();
                llAnaemiaTv.setVisibility(View.VISIBLE);
                llAnaemiaCheckbox.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getAnaemiaStringFromDb() {
        try {

            llAnaemiaTv.removeAllViews();
            anaemiaModelMain = databaseHelper.getAnaemiaData(childId);
            if (anaemiaModelMain != null && anaemiaModelMain.getAnaemiaCheckedString() != null) {
                List<String> myAnaList = new ArrayList<>(Arrays.asList(anaemiaModelMain.getAnaemiaCheckedString().split(",")));

                Type collectionType = new TypeToken<List<SystematicModel>>() {
                } // end new
                        .getType();

                String gsonString = new Gson().toJson(myAnaList, collectionType);
                gsonString = gsonString.replace("[", "{").replace("]", "}");
                Logger.logD(Constants.PROJECT, "Anaemia Conv to String--" + gsonString);

                Logger.logD(Constants.PROJECT, "getAnaemiaStringFromDb--Anamia--" + anaemiaModelMain.getAnaemiaCheckedString());
                if (myAnaList.size() > 0) {
                    llAnaemiaCheckbox.setVisibility(View.GONE);
                    anaemiaSave.setText("Edit");
                    final TextView[] myTextViews = new TextView[myAnaList.size()]; // create an empty array;
                    for (int i = 0; i < myAnaList.size(); i++) {

                        // create a new textview
                        final TextView rowTextViewAna = new TextView(this);
                        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        //  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this);
                        rowTextViewAna.setLayoutParams(LLParams);
                        // set some properties of rowTextView or something
                        rowTextViewAna.setText(myAnaList.get(i));
                        rowTextViewAna.setTextColor(CommonClass.getColor(this, R.color.black));
                        rowTextViewAna.setTypeface(rowTextViewAna.getTypeface(), Typeface.BOLD);
                        rowTextViewAna.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.eighteensp));
                        // add the textview to the linearlayout
                        llAnaemiaTv.addView(rowTextViewAna);
                        // save a reference to the textview for later
                        myTextViews[i] = rowTextViewAna;
                    }
                } else {
                    llAnaemiaCheckbox.setVisibility(View.VISIBLE);
                    Logger.logD(Constants.PROJECT, "String to list -->");
                }
            } else {
                Logger.logD(Constants.PROJECT, "String to list -->");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callMethodAnaemiACheck() {
        setAniamiaCheckedItem(aSkin);
        setAniamiaCheckedItem(aTongue);
        setAniamiaCheckedItem(aSclera);
        setAniamiaCheckedItem(aLips);
        setAniamiaCheckedItem(aNailBed);
    }

    void setAniamiaCheckedItem(CheckBox checkBox) {
        try {
            if (anaemiaModelMain != null && anaemiaModelMain.getAnaemiaCheckedString() != null) {
                Logger.logD(Constants.PROJECT, "String--Anamia--" + anaemiaModelMain.getAnaemiaCheckedString());
                List<String> myAnaList = new ArrayList<>(Arrays.asList(anaemiaModelMain.getAnaemiaCheckedString().split(",")));
                for (int i = 0; i < myAnaList.size(); i++) {
                    if (checkBox.getText().toString().equalsIgnoreCase(myAnaList.get(i))) {
                        checkBox.setChecked(true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick(R.id.systematic_save)
    void systematicSave() {
        try {
            if ("Save".equalsIgnoreCase(saveSystematic.getText().toString())) {
                SystematicModel systematicModel = new SystematicModel();
                fillSysBean(systematicModel);
                databaseHelper.insertSystematicData(systematicModel);
                showSysTv();
                getSystematicDataFromDb();
                setSysmatciTextViews();
                ToastUtils.displayToast("Saved Successfully", this);
                saveSystematic.setText("Edit");
            } else if ("Edit".equalsIgnoreCase(saveSystematic.getText().toString())) {
                saveSystematic.setText("Edit and Save");
                setSysSpinnerText();
                showSysSpinners();

            } else if ("Edit and Save".equalsIgnoreCase(saveSystematic.getText().toString())) {
                SystematicModel systematicModel = new SystematicModel();
                fillSysBean(systematicModel);
                databaseHelper.updateSysData(systematicModel, systematicModelMain.getpId());
                ToastUtils.displayToast("Updated Successfully", this);
                getSystematicDataFromDb();
                showSysTv();
                saveSystematic.setText("Edit");


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showSysSpinners() {
        systematicSpinner.setVisibility(View.VISIBLE);
        systematicTextview.setVisibility(View.GONE);
    }

    void showSysTv() {
        systematicSpinner.setVisibility(View.GONE);
        systematicTextview.setVisibility(View.VISIBLE);
    }

    void setSysmatciTextViews() {
        tvCardiov.setText(systematicModelMain.getCardDiovascular());
        tvResp.setText(systematicModelMain.getRespiratory());
        tvAbdominal.setText(systematicModelMain.getAbdominal());
        tvMusculoskeletal.setText(systematicModelMain.getMusculoskeletal());
    }

    void fillSysBean(SystematicModel systematicModel) {
        systematicModel.setChildId(childId);
        systematicModel.setCardDiovascular(spCardiov.getSelectedItem().toString());
        systematicModel.setRespiratory(spResp.getSelectedItem().toString());
        systematicModel.setAbdominal(spAbdominal.getSelectedItem().toString());
        systematicModel.setMusculoskeletal(spMusculoskeletal.getSelectedItem().toString());

    }

    void getSystematicDataFromDb() {
        List<SystematicModel> list = databaseHelper.getSyData(childId);
        Type collectionType = new TypeToken<List<SystematicModel>>() {
        } // end new
                .getType();

        String gsonString = new Gson().toJson(list, collectionType);
        gsonString = gsonString.replace("[", "").replace("]", "");
        Logger.logD(Constants.PROJECT, "Con Conv to String--" + gsonString);

        if (list.size() > 0) {
            saveSystematic.setText("Edit");
            for (int i = 0; i < list.size(); i++) {
                systematicModelMain = list.get(i);
                showSysTv();
                setSysmatciTextViews();
                //setPhyTextViews();
            }

        } else {
            showSysSpinners();
        }
    }


    void setSysSpinnerText() {
        selectSpinnerValue(spCardiov, systematicModelMain.getCardDiovascular());
        selectSpinnerValue(spResp, systematicModelMain.getRespiratory());
        selectSpinnerValue(spAbdominal, systematicModelMain.getAbdominal());
        selectSpinnerValue(spMusculoskeletal, systematicModelMain.getMusculoskeletal());
    }


    void visiablePhysicalSpinners() {
        phySpinners.setVisibility(View.VISIBLE);
        phyTextviews.setVisibility(View.GONE);

    }

    void gonePhySpinners() {
        phySpinners.setVisibility(View.GONE);
        phyTextviews.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.physical_save)
    void physicalSave() {
        try {
            if ("Save".equalsIgnoreCase(physicalSave.getText().toString())) {
                physicalSave.setText("Edit");
                PhysicalModel physicalModel = new PhysicalModel();
                fillPhyBean(physicalModel);
                databaseHelper.insertPhysical(physicalModel);
                gonePhySpinners();
                ToastUtils.displayToast("Saved Successfully", this);
                getPhysicalDataFromDb();
            } else if ("Edit".equalsIgnoreCase(physicalSave.getText().toString())) {
                physicalSave.setText("Edit and Save");
                visiablePhysicalSpinners();
                setPhySpinners();
            } else if ("Edit and Save".equalsIgnoreCase(physicalSave.getText().toString())) {
                PhysicalModel physicalModel = new PhysicalModel();
                fillPhyBean(physicalModel);
                databaseHelper.updatePhyData(physicalModel, physicalModelMain.getpId());
                physicalSave.setText("Edit");
                getPhysicalDataFromDb();
                ToastUtils.displayToast("Updated Successfully", this);
                gonePhySpinners();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillPhyBean(PhysicalModel physicalModel) {
        physicalModel.setChildId(childId);

        physicalModel.setIcterus(spIcterus.getSelectedItem().toString());
        physicalModel.setCyanosis(spCyanosis.getSelectedItem().toString());

        physicalModel.setClubbing(spClubbing.getSelectedItem().toString());
        physicalModel.setOedema(spOedema.getSelectedItem().toString());

        physicalModel.setSacrat(spSacrat.getSelectedItem().toString());
        physicalModel.setPeriOrbital(spPeri.getSelectedItem().toString());

        physicalModel.setLymphadenopathy(spLymphad.getSelectedItem().toString());
        physicalModel.setCervicalAnterior(spCerAnterior.getSelectedItem().toString());

        physicalModel.setCervicalPosterior(spCerPosterior.getSelectedItem().toString());
        physicalModel.setAxillary(spAllillary.getSelectedItem().toString());

        physicalModel.setOccipital(spoccipital.getSelectedItem().toString());
        physicalModel.setFpitrochlear(spFitrochlear.getSelectedItem().toString());

    }

    void setPhyTextViews() {
        tvIcterus.setText(physicalModelMain.getIcterus());
        tvCyanosis.setText(physicalModelMain.getCyanosis());

        tvClubbing.setText(physicalModelMain.getClubbing());
        tvOedema.setText(physicalModelMain.getOedema());

        tvSacrat.setText(physicalModelMain.getSacrat());
        tvPeri.setText(physicalModelMain.getPeriOrbital());

        tvLymphad.setText(physicalModelMain.getLymphadenopathy());
        tvCerAnterior.setText(physicalModelMain.getCervicalAnterior());

        tvCerPosterior.setText(physicalModelMain.getCervicalPosterior());
        tvAllillary.setText(physicalModelMain.getAxillary());

        tvOccipital.setText(physicalModelMain.getOccipital());
        tvFitrochlear.setText(physicalModelMain.getFpitrochlear());
    }

    void setPhySpinners() {
        selectSpinnerValue(spIcterus, physicalModelMain.getIcterus());
        selectSpinnerValue(spCyanosis, physicalModelMain.getCyanosis());

        selectSpinnerValue(spClubbing, physicalModelMain.getClubbing());
        selectSpinnerValue(spOedema, physicalModelMain.getOedema());

        selectSpinnerValue(spPeri, physicalModelMain.getPeriOrbital());
        selectSpinnerValue(spSacrat, physicalModelMain.getSacrat());

        selectSpinnerValue(spLymphad, physicalModelMain.getLymphadenopathy());
        selectSpinnerValue(spCerAnterior, physicalModelMain.getCervicalAnterior());

        selectSpinnerValue(spCerPosterior, physicalModelMain.getCervicalPosterior());
        selectSpinnerValue(spAllillary, physicalModelMain.getAxillary());

        selectSpinnerValue(spoccipital, physicalModelMain.getOccipital());
        selectSpinnerValue(spFitrochlear, physicalModelMain.getFpitrochlear());


    }

    void getPhysicalDataFromDb() {
        List<PhysicalModel> list = databaseHelper.getPhysicalData(childId);
        Type collectionType = new TypeToken<List<PhysicalModel>>() {
        } // end new
                .getType();

        String gsonString = new Gson().toJson(list, collectionType);
        gsonString = gsonString.replace("[", "").replace("]", "");
        Logger.logD(Constants.PROJECT, "Physical Conv to String--" + gsonString);
        if (list.size() > 0) {
            physicalSave.setText("Edit");
            for (int i = 0; i < list.size(); i++) {
                physicalModelMain = list.get(i);
                gonePhySpinners();
                setPhyTextViews();
            }

        } else {
            visiablePhysicalSpinners();
        }
    }

    @OnClick(R.id.dental_save)
    void dentalSave() {
        List<String> checkedDentalList = new ArrayList<>();
        if (dNormal.isChecked())
            checkedDentalList.add(dNormal.getText().toString());
        if (dStains.isChecked())
            checkedDentalList.add(dStains.getText().toString());
        if (dBadBreath.isChecked())
            checkedDentalList.add(dBadBreath.getText().toString());
        if (dGumBleeding.isChecked())
            checkedDentalList.add(dGumBleeding.getText().toString());
        if (dToothCavity.isChecked())
            checkedDentalList.add(dToothCavity.getText().toString());
        if (dTarte.isChecked())
            checkedDentalList.add(dTarte.getText().toString());
        if (dPlaque.isChecked())
            checkedDentalList.add(dPlaque.getText().toString());
        if (dSoftTissue.isChecked())
            checkedDentalList.add(dSoftTissue.getText().toString());
        if (dGumInflamma.isChecked())
            checkedDentalList.add(dGumInflamma.getText().toString());

        String listString = "";
        for (int i = 0; i < checkedDentalList.size(); i++) {
            if (i != checkedDentalList.size() - 1)
                listString += checkedDentalList.get(i) + ",";
            else
                listString += checkedDentalList.get(i);
        }
        Logger.logD(Constants.PROJECT, "listString size-->" + listString);

        if ("Save".equalsIgnoreCase(dentalSave.getText().toString())) {
            DentalModel dentalModel = new DentalModel();
            dentalSave.setText("Edit");
            dentalModel.setChildId(childId);
            dentalModel.setCheckedListString(listString);
            databaseHelper.insertDentalData(dentalModel);
            ToastUtils.displayToast("Saved Successfully", this);
            llDentalCheckBoxs.setVisibility(View.GONE);
            llDentalCheckedTv.setVisibility(View.VISIBLE);
            getDentalStringFromDb();
        } else if ("Edit".equalsIgnoreCase(dentalSave.getText().toString())) {
            dentalSave.setText("Edit and Save");
            llDentalCheckBoxs.setVisibility(View.VISIBLE);
            llDentalCheckedTv.setVisibility(View.GONE);
            callMethodToDenatlCheck();

        } else if ("Edit and Save".equalsIgnoreCase(dentalSave.getText().toString())) {
            DentalModel dentalModel = new DentalModel();
            dentalModel.setChildId(childId);
            dentalModel.setCheckedListString(listString);
            databaseHelper.updateDentalData(dentalModel, dentalModelMain.getpId());
            ToastUtils.displayToast("Updated Successfully", this);
            dentalSave.setText("Edit");
            getDentalStringFromDb();
            llDentalCheckedTv.setVisibility(View.VISIBLE);
            llDentalCheckBoxs.setVisibility(View.GONE);
        }
    }


    void getDentalStringFromDb() {
        try {
            llDentalCheckedTv.removeAllViews();
            dentalModelMain = databaseHelper.getDentalData(childId);
            if (dentalModelMain != null && dentalModelMain.getCheckedListString() != null) {
                List<String> myList = new ArrayList<>(Arrays.asList(dentalModelMain.getCheckedListString().split(",")));
                Type collectionType = new TypeToken<List<DentalModel>>() {
                } // end new
                        .getType();

                String gsonString = new Gson().toJson(myList, collectionType);
                //JSONObject jsonObject = new Gson().toJson(myList,collectionType);
                gsonString = gsonString.replace("[", "{").replace("]", "}");
                Logger.logD(Constants.PROJECT, "Dental Conv to String--" + gsonString);

                if (myList.size() > 0) {
                    llDentalCheckBoxs.setVisibility(View.GONE);
                    dentalSave.setText("Edit");
                    //llDentalCheckedTv.setPadding(R.dimen.twentydp, R.dimen.twentydp, 0, R.dimen.twentydp);
                    final TextView[] myTextViews = new TextView[myList.size()]; // create an empty array;
                    for (int i = 0; i < myList.size(); i++) {
                        // create a new textview
                        final TextView rowTextView = new TextView(this);
                        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        //  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this);
                        rowTextView.setLayoutParams(LLParams);
                        // set some properties of rowTextView or something
                        rowTextView.setText(myList.get(i));
                        rowTextView.setTextColor(CommonClass.getColor(this, R.color.black));
                        rowTextView.setTypeface(rowTextView.getTypeface(), Typeface.BOLD);
                        // add the textview to the linearlayout
                        rowTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                getResources().getDimension(R.dimen.eighteensp));


                       /* if (i % 2 == 1) {
                            rowTextView.setBackgroundColor(CommonClass.getColor(this, R.color.light_blue));
                        } else {
                            rowTextView.setBackgroundColor(CommonClass.getColor(this, R.color.layout_bg));
                        }*/
                        llDentalCheckedTv.addView(rowTextView);
                        // save a reference to the textview for later
                        myTextViews[i] = rowTextView;
                    }
                } else {
                    llDentalCheckBoxs.setVisibility(View.VISIBLE);
                    Logger.logD(Constants.PROJECT, "String to list -->");
                }
            } else {
                Logger.logD(Constants.PROJECT, "String to list -->");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    void callMethodToDenatlCheck() {
        setDentalCheckedItem(dNormal);
        setDentalCheckedItem(dBadBreath);
        setDentalCheckedItem(dStains);
        setDentalCheckedItem(dToothCavity);
        setDentalCheckedItem(dGumBleeding);
        setDentalCheckedItem(dTarte);
        setDentalCheckedItem(dPlaque);
        setDentalCheckedItem(dSoftTissue);
        setDentalCheckedItem(dGumInflamma);
    }

    void setDentalCheckedItem(CheckBox checkBox) {
        try {
            if (dentalModelMain != null && dentalModelMain.getCheckedListString() != null) {
                List<String> myList = new ArrayList<>(Arrays.asList(dentalModelMain.getCheckedListString().split(",")));
                for (int i = 0; i < myList.size(); i++) {
                    if (checkBox.getText().toString().equalsIgnoreCase(myList.get(i))) {
                        checkBox.setChecked(true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void addEyeEditList() {
        eyeLeftList.add(editVisualLeft);
        eyeLeftList.add(editRefLeft);
        /*eyeLeftList.add(editColorLeft);
        eyeLeftList.add(editSquintLeft);
        eyeLeftList.add(editAllLeft);*/

        eyeRightList.add(editVisualRight);
        eyeRightList.add(editRefRight);
        /*eyeRightList.add(editColorRight);
        eyeRightList.add(editSquintRight);
        eyeRightList.add(editAllRight);*/

    }

    void eyeEditNull(List<EditText> tempEditList) {
        for (int i = 0; i < tempEditList.size(); i++) {
            tempEditList.get(i).setInputType(InputType.TYPE_NULL);
            tempEditList.get(i).setTextColor(CommonClass.getColor(this, R.color.black));
            tempEditList.get(i).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
        spColorVision.setVisibility(View.GONE);
        spSquint.setVisibility(View.GONE);
        spAllergy.setVisibility(View.GONE);

        tvColorvision.setVisibility(View.VISIBLE);
        tvSquint.setVisibility(View.VISIBLE);
        tvAllergy.setVisibility(View.VISIBLE);


    }

    void eyeEditText(List<EditText> tempEditList) {
        for (int i = 0; i < tempEditList.size(); i++) {
            tempEditList.get(i).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
            tempEditList.get(i).setBackground(getResources().getDrawable(R.drawable.add_age_edittext));
        }
        spColorVision.setVisibility(View.VISIBLE);
        spSquint.setVisibility(View.VISIBLE);
        spAllergy.setVisibility(View.VISIBLE);

        if (eyeModelMain.getColorLeft() != null)
            selectSpinnerValue(spColorVision, eyeModelMain.getColorLeft());

        if (eyeModelMain.getSquintLeft() != null)
            selectSpinnerValue(spSquint, eyeModelMain.getSquintLeft());

        if (eyeModelMain.getAllLeft() != null)
            selectSpinnerValue(spAllergy, eyeModelMain.getAllLeft());

        tvColorvision.setVisibility(View.GONE);
        tvSquint.setVisibility(View.GONE);
        tvAllergy.setVisibility(View.GONE);


    }

    void eyeEditTextValidation(List<EditText> tempEditList) {
        for (int i = 0; i < tempEditList.size(); i++) {
            if (tempEditList.get(i).getText().toString().isEmpty())
                ToastUtils.displayToast("Please fill all your eye details", this);
        }
    }

    @OnClick(R.id.eye_save)
    void saveEyeData() {
        try {
            if (editVisualLeft.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Please enter Visual Acuity for left eye", this);
            } else if (!editVisualLeft.getText().toString().contains("/")) {
                ToastUtils.displayToast("Please enter valid format(6/36)", this);
            } else if (Character.toString(editVisualLeft.getText().toString().charAt(2)).contains("/") || Character.toString(editVisualLeft.getText().toString().charAt(2)).contains("*") || Character.toString(editVisualLeft.getText().toString().charAt(2)).contains(",")) {
                ToastUtils.displayToast("Please enter valid format(6/36) for Eye", this);
            } else if (Character.toString(editVisualLeft.getText().toString().charAt(2)).contains(".") || Character.toString(editVisualLeft.getText().toString().charAt(2)).contains("+") || Character.toString(editVisualLeft.getText().toString().charAt(2)).contains("#")) {
                ToastUtils.displayToast("Please enter valid format(6/36) for Eye", this);
            } else if (Character.toString(editVisualLeft.getText().toString().charAt(2)).contains("-") || Character.toString(editVisualLeft.getText().toString().charAt(2)).contains("(") || Character.toString(editVisualLeft.getText().toString().charAt(2)).contains(")")) {
                ToastUtils.displayToast("Please enter valid format(6/36) for Eye", this);
            } else if (!editVisualLeft.getText().toString().isEmpty() && !eyeEditColorValidation(editVisualLeft)) {
                ToastUtils.displayToast("Visual Acuity Min 6/6 - Max 6/36", this);
            } else if (editVisualRight.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Please enter Visual Acuity for right eye", this);
            } else if (!editVisualRight.getText().toString().contains("/")) {
                ToastUtils.displayToast("Please enter valid format(6/36)", this);
            } else if (Character.toString(editVisualRight.getText().toString().charAt(2)).contains("/") || Character.toString(editVisualRight.getText().toString().charAt(2)).contains("*") || Character.toString(editVisualRight.getText().toString().charAt(2)).contains(",")) {
                ToastUtils.displayToast("Please enter valid format(6/36) for Eye", this);
            } else if (Character.toString(editVisualRight.getText().toString().charAt(2)).contains(".") || Character.toString(editVisualRight.getText().toString().charAt(2)).contains("+") || Character.toString(editVisualRight.getText().toString().charAt(2)).contains("#")) {
                ToastUtils.displayToast("Please enter valid format(6/36) for Eye", this);
            } else if (Character.toString(editVisualRight.getText().toString().charAt(2)).contains("-") || Character.toString(editVisualRight.getText().toString().charAt(2)).contains("(") || Character.toString(editVisualRight.getText().toString().charAt(2)).contains(")")) {
                ToastUtils.displayToast("Please enter valid format(6/36) for Eye", this);
            } else if (!editVisualRight.getText().toString().isEmpty() && !eyeEditColorValidation(editVisualRight)) {
                ToastUtils.displayToast("Visual Acuity Min 6/6 - Max 6/36", this);
            } else if (editRefLeft.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Please enter Refractive index for left eye", this);
            } else if (Integer.parseInt(editRefLeft.getText().toString().trim()) < 40) {
                ToastUtils.displayToast("Refractive index range 40 to 48", this);
            } else if (Integer.parseInt(editRefLeft.getText().toString().trim()) > 48) {
                ToastUtils.displayToast("Refractive index range 40 to 48", this);
            } else if (editRefRight.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Please enter Refractive index for Right eye", this);
            } else if (Integer.parseInt(editRefRight.getText().toString().trim()) < 40) {
                ToastUtils.displayToast("Refractive index range 40 to 48", this);
            } else if (Integer.parseInt(editRefRight.getText().toString().trim()) > 48) {
                ToastUtils.displayToast("Refractive index range 40 to 48", this);
            } else if ("Save".equalsIgnoreCase(eyeSave.getText().toString())) {
                eyeSave.setText("Edit");
                EyeModel eyeModel = new EyeModel();
                fillEyeBean(eyeModel);
                databaseHelper.insertEye(eyeModel);
                ToastUtils.displayToast("Saved Successfully", this);
                getEyeFromDb();
                eyeEditNull(eyeLeftList);
                eyeEditNull(eyeRightList);
            } else if ("Edit".equalsIgnoreCase(eyeSave.getText().toString())) {
                eyeSave.setText("Edit and Save");
                eyeEditText(eyeLeftList);
                eyeEditText(eyeRightList);

            } else if ("Edit and Save".equalsIgnoreCase(eyeSave.getText().toString())) {
                ToastUtils.displayToast("Updated Successfully", this);
                eyeSave.setText("Edit");
                eyeEditNull(eyeLeftList);
                eyeEditNull(eyeRightList);
                EyeModel eyeModel = new EyeModel();
                fillEyeBean(eyeModel);
                databaseHelper.updateEyeData(eyeModel, eyeModelMain.getpId());
                getEyeFromDb();
            }
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "Exce", e);
            ToastUtils.displayToast("Please enter valid eye data", this);
        }
    }


    boolean eyeEditColorValidation(EditText editText) {

        String[] separatedBp = editText.getText().toString().trim().split("/");
        ibp1 = Integer.parseInt(separatedBp[0]);
        ibp2 = Integer.parseInt(separatedBp[1]);
        Logger.logD(Constants.PROJECT, "ibp1-->" + ibp1 + "ibp2-->" + ibp2);
        if ((ibp1 >= 6 && ibp2 >= 6) && (ibp1 <= 6 && ibp2 <= 36))
            return true;
        else
            return false;
    }

    void getEyeFromDb() {
        List<EyeModel> list = databaseHelper.getEyeData(childId);
        Type collectionType = new TypeToken<List<EyeModel>>() {
        } // end new
                .getType();

        String gsonString = new Gson().toJson(list, collectionType);
        gsonString = gsonString.replace("[", "").replace("]", "");
        Logger.logD(Constants.PROJECT, "Eye Conv to String--" + gsonString);

        if (list.size() > 0) {
            eyeSave.setText("Edit");
            for (int i = 0; i < list.size(); i++) {
                eyeModelMain = list.get(i);
                eyeEditNull(eyeLeftList);
                eyeEditNull(eyeRightList);
                setEyeValues();
            }

        } else {
            eyeEditText(eyeRightList);
            eyeEditText(eyeLeftList);
            //eyeEditNull(eyeLeftList);
        }
    }


    void fillEyeBean(EyeModel eyeModel) {

        eyeModel.setVisLeft(eyeLeftList.get(0).getText().toString());
        eyeModel.setRefLeft(eyeLeftList.get(1).getText().toString());

        eyeModel.setColorLeft(spColorVision.getSelectedItem().toString());
        eyeModel.setSquintLeft(spSquint.getSelectedItem().toString());
        eyeModel.setAllLeft(spAllergy.getSelectedItem().toString());

        eyeModel.setVisRight(eyeRightList.get(0).getText().toString());
        eyeModel.setRefRight(eyeRightList.get(1).getText().toString());
       /* eyeModel.setColorRight(eyeRightList.get(2).getText().toString());
        eyeModel.setSquintRight(eyeRightList.get(3).getText().toString());
        eyeModel.setAllRight(eyeRightList.get(4).getText().toString());*/
        eyeModel.setChildId(childId);

    }

    @OnClick(R.id.condition_save)
    void saveCondition() {
        if ("Save".equalsIgnoreCase(conditionSave.getText().toString())) {
            ConditionModel conditionModel = new ConditionModel();
            saveAndEditCondition(conditionModel);
            databaseHelper.insertCondition(conditionModel);
            ToastUtils.displayToast("Saved Successfully", this);
            conditionSave.setText("Edit");
            getConditionDataFromDb();
            showConditionTextViews();
            goneConditionSpinners();
        } else if ("Edit".equalsIgnoreCase(conditionSave.getText().toString())) {
            showConditionSpinners();
            goneConditionTextViews();
            conditionSave.setText("Edit and Save");
            setSpinnerValues();
        } else if ("Edit and Save".equalsIgnoreCase(conditionSave.getText().toString())) {
            conditionSave.setText("Edit");
            ConditionModel conditionModel = new ConditionModel();
            saveAndEditCondition(conditionModel);
            databaseHelper.updateConditionalData(conditionModel, conditionModelMain.getConPid());
            ToastUtils.displayToast("Updated Successfully", this);
            getConditionDataFromDb();
            showConditionTextViews();
            goneConditionSpinners();

        }
    }


    void setEyeValues() {
        if (eyeModelMain.getVisLeft() != null)
            editVisualLeft.setText(eyeModelMain.getVisLeft());

        if (eyeModelMain.getVisRight() != null)
            editVisualRight.setText(eyeModelMain.getVisRight());

        if (eyeModelMain.getRefLeft() != null)
            editRefLeft.setText(eyeModelMain.getRefLeft());

        if (eyeModelMain.getRefRight() != null)
            editRefRight.setText(eyeModelMain.getRefRight());

        if (eyeModelMain.getColorLeft() != null)
            tvColorvision.setText(eyeModelMain.getColorLeft());
        if (eyeModelMain.getSquintLeft() != null)
            tvSquint.setText(eyeModelMain.getSquintLeft());
        if (eyeModelMain.getAllLeft() != null)
            tvAllergy.setText(eyeModelMain.getAllLeft());

       /* selectSpinnerValue(spColorVision, eyeModelMain.getColorLeft());

        if (eyeModelMain.getSquintLeft() != null)
            selectSpinnerValue(spSquint, eyeModelMain.getSquintLeft());

        if (eyeModelMain.getAllLeft() != null)
            selectSpinnerValue(spAllergy, eyeModelMain.getAllLeft());*/


    }


    private void selectSpinnerValue(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    void setSpinnerValues() {
        if (conditionModelMain.getHair() != null)
            selectSpinnerValue(ddConHair, conditionModelMain.getHair());
        if (conditionModelMain.getSkin() != null)
            selectSpinnerValue(ddConSkin, conditionModelMain.getSkin());
        if (conditionModelMain.getNail() != null)
            selectSpinnerValue(ddConNail, conditionModelMain.getNail());
        if (conditionModelMain.getNose() != null)
            selectSpinnerValue(ddConNose, conditionModelMain.getNose());
        if (conditionModelMain.getEar() != null)
            selectSpinnerValue(ddConEar, conditionModelMain.getEar());
        if (conditionModelMain.getPureTone() != null)
            selectSpinnerValue(ddConPureTone, conditionModelMain.getPureTone());
        if (conditionModelMain.getThroat() != null)
            selectSpinnerValue(ddThroat, conditionModelMain.getThroat());
    }

    void saveAndEditCondition(ConditionModel conditionModel) {
        conditionModel.setConChildId(childId);
        conditionModel.setHair(ddConHair.getSelectedItem().toString());
        conditionModel.setSkin(ddConSkin.getSelectedItem().toString());
        conditionModel.setNose(ddConNose.getSelectedItem().toString());
        conditionModel.setNail(ddConNail.getSelectedItem().toString());
        conditionModel.setEar(ddConEar.getSelectedItem().toString());
        conditionModel.setPureTone(ddConPureTone.getSelectedItem().toString());
        conditionModel.setThroat(ddThroat.getSelectedItem().toString());
    }

    void getConditionDataFromDb() {
        List<ConditionModel> list = databaseHelper.getConditionalData(childId);
        Type collectionType = new TypeToken<List<ConditionModel>>() {
        } // end new
                .getType();

        String gsonString = new Gson().toJson(list, collectionType);
        gsonString = gsonString.replace("[", "").replace("]", "");
        Logger.logD(Constants.PROJECT, "Con Conv to String--" + gsonString);

        if (list.size() > 0) {
            showConditionTextViews();
            goneConditionSpinners();
            conditionSave.setText("Edit");
            for (int i = 0; i < list.size(); i++) {
                conditionModelMain = list.get(i);
                setConditionTextViews();
            }
        } else {
            showConditionSpinners();
            goneConditionTextViews();

        }

    }

    void setConditionTextViews() {
        if (conditionModelMain.getHair() != null)
            tvHair.setText(conditionModelMain.getHair());
        if (conditionModelMain.getSkin() != null)
            tvSkin.setText(conditionModelMain.getSkin());
        if (conditionModelMain.getNail() != null)
            tvNail.setText(conditionModelMain.getNail());
        if (conditionModelMain.getNose() != null)
            tvNose.setText(conditionModelMain.getNose());
        if (conditionModelMain.getEar() != null)
            tvEar.setText(conditionModelMain.getEar());
        if (conditionModelMain.getPureTone() != null)
            tvPureTone.setText(conditionModelMain.getPureTone());
        if (conditionModelMain.getThroat() != null)
            tvDdThroat.setText(conditionModelMain.getThroat());
    }


    void showConditionTextViews() {
        tvHair.setVisibility(View.VISIBLE);
        tvSkin.setVisibility(View.VISIBLE);
        tvNail.setVisibility(View.VISIBLE);
        tvNose.setVisibility(View.VISIBLE);
        tvEar.setVisibility(View.VISIBLE);
        tvPureTone.setVisibility(View.VISIBLE);
        tvDdThroat.setVisibility(View.VISIBLE);
    }

    void goneConditionTextViews() {
        tvHair.setVisibility(View.GONE);
        tvSkin.setVisibility(View.GONE);
        tvNail.setVisibility(View.GONE);
        tvNose.setVisibility(View.GONE);
        tvEar.setVisibility(View.GONE);
        tvPureTone.setVisibility(View.GONE);
        tvDdThroat.setVisibility(View.GONE);
    }

    void showConditionSpinners() {
        ddConHair.setVisibility(View.VISIBLE);
        ddConSkin.setVisibility(View.VISIBLE);
        ddConNail.setVisibility(View.VISIBLE);
        ddConNose.setVisibility(View.VISIBLE);
        ddConEar.setVisibility(View.VISIBLE);
        ddConPureTone.setVisibility(View.VISIBLE);
        ddThroat.setVisibility(View.VISIBLE);
    }

    void goneConditionSpinners() {
        ddConHair.setVisibility(View.GONE);
        ddConSkin.setVisibility(View.GONE);
        ddConNail.setVisibility(View.GONE);
        ddConNose.setVisibility(View.GONE);
        ddConEar.setVisibility(View.GONE);
        ddConPureTone.setVisibility(View.GONE);
        ddThroat.setVisibility(View.GONE);
    }

    void getBioMetricData() {
        List<BioMetricBean> list = databaseHelper.getBioMetric(childId); // Child ==1
        Logger.logD(Constants.PROJECT, "Bio size--" + list.size());
        Type collectionType = new TypeToken<List<BioMetricBean>>() {
        } // end new
                .getType();

        String gsonString = new Gson().toJson(list, collectionType);
        gsonString = gsonString.replace("[", "").replace("]", "");
        Logger.logD(Constants.PROJECT, "Bi Conv to String--" + gsonString);

        if (list.size() > 0) {
            bioSave.setText("Edit");
            disableEditBio();
            for (int i = 0; i < list.size(); i++) {
                bioMetric = list.get(i);
                if (bioMetric.getPulse() != null)
                    edtPulse.setText(bioMetric.getPulse());
                if (bioMetric.getBp() != null)
                    edtBp.setText(bioMetric.getBp());
                if (bioMetric.getHeight() != null)
                    edtHeightBio.setText(bioMetric.getHeight());
                if (bioMetric.getWeight() != null)
                    edtWeightBio.setText(bioMetric.getWeight());
                if (bioMetric.getBmi() != null)
                    edtBmi.setText(bioMetric.getBmi());
                if (bioMetric.getObservation() != null)
                    tvObservation.setText(bioMetric.getObservation());

            }

        } else {
            inputTypeText();

        }

    }

    void disableEditBio() {
        edtPulse.setBackground(getResources().getDrawable(R.drawable.transperent_edt_bg));
        edtBp.setBackground(getResources().getDrawable(R.drawable.transperent_edt_bg));
        edtHeightBio.setBackground(getResources().getDrawable(R.drawable.transperent_edt_bg));
        edtWeightBio.setBackground(getResources().getDrawable(R.drawable.transperent_edt_bg));
        edtBmi.setBackground(getResources().getDrawable(R.drawable.transperent_edt_bg));
        bioEditTypeNUll();

    }

    void enableEditBio() {
        edtPulse.setBackground(getResources().getDrawable(R.drawable.add_age_edittext));
        edtBp.setBackground(getResources().getDrawable(R.drawable.add_age_edittext));
        edtHeightBio.setBackground(getResources().getDrawable(R.drawable.add_age_edittext));
        edtWeightBio.setBackground(getResources().getDrawable(R.drawable.add_age_edittext));
        edtBmi.setBackground(getResources().getDrawable(R.drawable.add_age_edittext));
    }

    void inputTypeText() {
        edtPulse.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtBp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        edtHeightBio.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtWeightBio.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @OnClick(R.id.bio_save)
    void saveBio() {
        try {
            if (edtPulse.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Please enter pulse", this);
            } else if (Integer.parseInt(edtPulse.getText().toString().trim()) < 60) {
                ToastUtils.displayToast("Pulse range between 60 to 100", this);
            } else if (Integer.parseInt(edtPulse.getText().toString().trim()) > 100) {
                ToastUtils.displayToast("Pulse range between 60 to 100", this);
            } else if (edtBp.getText().toString().isEmpty()) {
                Logger.logD(Constants.PROJECT, "Pulse" + Integer.parseInt(edtPulse.getText().toString().trim()));
                ToastUtils.displayToast("Please enter BP", this);
            } else if (!edtBp.getText().toString().contains("/")) {
                ToastUtils.displayToast("Please enter valid format(90/120)", this);
            } else if (Character.toString(edtBp.getText().toString().charAt(3)).contains("/") || Character.toString(edtBp.getText().toString().charAt(3)).contains("*") || Character.toString(edtBp.getText().toString().charAt(3)).contains(",")) {
                ToastUtils.displayToast("Please enter valid format(90/120) for BP", this);
            } else if (Character.toString(edtBp.getText().toString().charAt(3)).contains(".") || Character.toString(edtBp.getText().toString().charAt(3)).contains("+") || Character.toString(edtBp.getText().toString().charAt(3)).contains("#")) {
                ToastUtils.displayToast("Please enter valid format(90/120) for BP", this);
            } else if (Character.toString(edtBp.getText().toString().charAt(3)).contains("-") || Character.toString(edtBp.getText().toString().charAt(3)).contains("(") || Character.toString(edtBp.getText().toString().charAt(3)).contains(")")) {
                ToastUtils.displayToast("Please enter valid format(90/120) for BP", this);
            } else if (!edtBp.getText().toString().isEmpty() && !bpValidation(edtBp.getText().toString().trim())) {
                ToastUtils.displayToast("Bp range is 75/110 to 90/120 ", this);
            } else if (edtHeightBio.getText().toString().isEmpty()) {
                Logger.logD(Constants.PROJECT, "ibp1-->" + ibp1 + "ibp2-->" + ibp2);
                ToastUtils.displayToast("Please enter Height", this);
            } else if (Integer.parseInt(edtHeightBio.getText().toString().trim()) < 1) {
                ToastUtils.displayToast("Enter valid height", this);
            } else if (Integer.parseInt(edtHeightBio.getText().toString().trim()) > 199) {
                ToastUtils.displayToast("Height max 199cms", this);
            } else if (edtWeightBio.getText().toString().isEmpty()) {
                ToastUtils.displayToast("Please enter Weight", this);
            } else if (Integer.parseInt(edtWeightBio.getText().toString().trim()) < 1) {
                ToastUtils.displayToast("Enter valid Weight", this);
            } else if (Integer.parseInt(edtWeightBio.getText().toString().trim()) > 99) {
                ToastUtils.displayToast("Weight max 99Kgs", this);
            } else {
                Logger.logD(Constants.PROJECT, "BIO Save--");
                saveEditBio();
            }
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "Exce", e);
            ToastUtils.displayToast("Please enter valid Bio-metric data", this);
        }
    }

    boolean bpValidation(String bp) {
        String[] separatedBp = bp.split("/");
        ibp1 = Integer.parseInt(separatedBp[0]);
        ibp2 = Integer.parseInt(separatedBp[1]);
        Logger.logD(Constants.PROJECT, "ibp1-->" + ibp1 + "ibp2-->" + ibp2);
        if ((ibp1 >= 75 && ibp2 >= 110) && (ibp1 <= 90 && ibp2 <= 120))
            return true;
        else
            return false;
    }

    void saveEditBio() {
        if ("Save".equalsIgnoreCase(bioSave.getText().toString())) {
            Logger.logD(Constants.PROJECT, "BIO Save--");
            BioMetricBean bioMetricBean = new BioMetricBean();
            filBioBean(bioMetricBean);
            databaseHelper.insertBioMetricData(bioMetricBean);
            ToastUtils.displayToast("Saved Successfully", this);
            bioSave.setText("Edit");
            disableEditBio();
            bioEditTypeNUll();
        } else if ("Edit".equalsIgnoreCase(bioSave.getText().toString())) {
            edtPulse.setCursorVisible(true);
            edtPulse.setFocusable(true);
            bioSave.setText("Edit and Save");
            enableEditBio();
            inputTypeText();
        } else if ("Edit and Save".equalsIgnoreCase(bioSave.getText().toString())) {
            BioMetricBean bioMetricBean = new BioMetricBean();
            filBioBean(bioMetricBean);
            databaseHelper.updateBioMetricBean(bioMetricBean, bioMetric.getPid());
            ToastUtils.displayToast("Updated Successfully", this);
            bioSave.setText("Edit");
            disableEditBio();
            bioEditTypeNUll();
        }
    }

    void bioEditTypeNUll() {
        edtPulse.setInputType(InputType.TYPE_NULL);
        edtBp.setInputType(InputType.TYPE_NULL);
        edtHeightBio.setInputType(InputType.TYPE_NULL);
        edtWeightBio.setInputType(InputType.TYPE_NULL);
        edtBmi.setInputType(InputType.TYPE_NULL);
    }

    void filBioBean(BioMetricBean bioMetricBean) {
        bioMetricBean.setPulse(edtPulse.getText().toString().trim());
        bioMetricBean.setBp(edtBp.getText().toString().trim());
        bioMetricBean.setHeight(edtHeightBio.getText().toString().trim());
        bioMetricBean.setWeight(edtWeightBio.getText().toString().trim());
        bioMetricBean.setBmi(edtBmi.getText().toString().trim());
        bioMetricBean.setObservation(tvObservation.getText().toString());
        bioMetricBean.setChildId(childId);
    }

    private void calculateBmi() {
        try {
            if (!edtHeightBio.getText().toString().isEmpty() && !edtWeightBio.getText().toString().isEmpty()) {
                double valueHeight = Double.parseDouble(edtHeightBio.getText().toString());
                double valueWeight = Double.parseDouble(edtWeightBio.getText().toString());
                Double valueheightmeters;

                valueheightmeters = valueHeight / 100; // Converting to meters.
                bmi = (valueWeight / (valueheightmeters * valueheightmeters));
                double roundOffBmi = Math.round(bmi * 100.0) / 100.0;
                edtBmi.setText(String.valueOf((roundOffBmi)));
                if (bmi > 30) {
                    resultObservation = " OBESE ";
                    tvObservation.setText(resultObservation);
                } else if (bmi >= 18.5 && bmi <= 25) {
                    resultObservation = " NORMAL";
                    tvObservation.setText(resultObservation);
                } else if (bmi >= 25 && bmi <= 30) {
                    resultObservation = " OVERWEIGHT";
                    tvObservation.setText(resultObservation);
                } else if (bmi >= 18.5) {
                    resultObservation = " IDEAL";
                    tvObservation.setText(resultObservation);
                } else if (bmi < 18.5) {
                    resultObservation = " UNDERWEIGHT";
                    tvObservation.setText(resultObservation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub


            if (count == 0) {
                //sentText.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                // sentText.setText(getString(R.string.otpSentString));
                //subtext.setText(getString(R.string.otpSentSubString));
                // ortextV.setVisibility(View.INVISIBLE);

            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            calculateBmi();
        }
    };

    void bioElse() {
        flag = 1;
        bioIncludeLayout.setVisibility(View.GONE);
        imgBio.setImageResource(R.drawable.bio);
        bioArrow.setImageResource(R.drawable.tab_arrow);
        llBio.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvBio.setTextColor(CommonClass.getColor(this, R.color.black));
    }

    void conditionElse() {
        flagCon = 1;
        conditionInclude.setVisibility(View.GONE);
        imgCondition.setImageResource(R.drawable.condition);
        conArrow.setImageResource(R.drawable.tab_arrow);
        llCondition.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvCondition.setTextColor(CommonClass.getColor(this, R.color.black));
    }

    void eyeElse() {
        eyeFlag = 1;
        imgEye.setImageResource(R.drawable.eye);
        eyeInclude.setVisibility(View.GONE);
        eyeArrow.setImageResource(R.drawable.tab_arrow);
        llEye.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvEye.setTextColor(CommonClass.getColor(this, R.color.black));
    }

    void dentalElse() {
        dentalFlag = 1;
        imgDental.setImageResource(R.drawable.dental);
        dentalInclude.setVisibility(View.GONE);

        dentalArrow.setImageResource(R.drawable.tab_arrow);
        llDental.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvDental.setTextColor(CommonClass.getColor(this, R.color.black));
    }

    void systematicElse() {
        systematicFlag = 1;
        imgSys.setImageResource(R.drawable.systematic);
        systematicInclude.setVisibility(View.GONE);
        sysArrow.setImageResource(R.drawable.tab_arrow);
        llSystematic.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvSystematic.setTextColor(CommonClass.getColor(this, R.color.black));

    }

    void physicalElse() {
        physicalFlag = 1;
        imgPhysical.setImageResource(R.drawable.physical);
        physicalInclude.setVisibility(View.GONE);
        phyArrow.setImageResource(R.drawable.tab_arrow);
        llPhysical.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvPhysical.setTextColor(CommonClass.getColor(this, R.color.black));

    }

    void anaemiaElse() {
        anaemiaFla = 1;
        imgAnaemia.setImageResource(R.drawable.anaemia);
        anaemiaInclude.setVisibility(View.GONE);

        anaemiaArrow.setImageResource(R.drawable.tab_arrow);
        llAnaemia.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvAnaemia.setTextColor(CommonClass.getColor(this, R.color.black));
    }

    void summaryElse() {
        summaryFlag = 1;
        imgSummary.setImageResource(R.drawable.summary);
        includeSummary.setVisibility(View.GONE);

        summaryArrow.setImageResource(R.drawable.tab_arrow);
        llSummary.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        tvSummary.setTextColor(CommonClass.getColor(this, R.color.black));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bio:
                if (flag == 1) {
                    flag = 2;
                    selectReport(llBio, llCondition, llEye, llDental, llPhysical, llSystematic, llAnaemia, llSummary,
                            bioArrow, conArrow, eyeArrow, dentalArrow, phyArrow, sysArrow, anaemiaArrow, summaryArrow,
                            tvBio, tvCondition, tvEye, tvDental, tvPhysical, tvSystematic, tvAnaemia, tvSummary);
                    bioIncludeLayout.setVisibility(View.VISIBLE);
                    imgBio.setImageResource(R.drawable.biometrics_s);
                    conditionElse();
                    eyeElse();
                    systematicElse();
                    physicalElse();
                    anaemiaElse();
                    dentalElse();
                    summaryElse();
                    //tvBio.setTextColor(CommonClass.getColor(this, R.color.white));
                } else if (flag == 2) {
                    bioElse();

                }
                break;
            case R.id.ll_condition:
                if (flagCon == 1) {
                    flagCon = 2;
                    selectReport(llCondition, llBio, llEye, llDental, llPhysical, llSystematic, llAnaemia, llSummary,
                            conArrow, bioArrow, eyeArrow, dentalArrow, phyArrow, sysArrow, anaemiaArrow, summaryArrow,
                            tvCondition, tvBio, tvEye, tvDental, tvPhysical, tvSystematic, tvAnaemia, tvSummary);
                    conditionInclude.setVisibility(View.VISIBLE);
                    imgCondition.setImageResource(R.drawable.condition_s);
                    bioElse();
                    systematicElse();
                    eyeElse();
                    anaemiaElse();
                    physicalElse();
                    dentalElse();
                    summaryElse();
                } else if (flagCon == 2) {
                    conditionElse();
                }
                break;
            case R.id.ll_eye:
                if (eyeFlag == 1) {
                    eyeFlag = 2;
                    selectReport(llEye, llCondition, llBio, llDental, llPhysical, llSystematic, llAnaemia, llSummary,
                            eyeArrow, conArrow, bioArrow, dentalArrow, phyArrow, sysArrow, anaemiaArrow, summaryArrow,
                            tvEye, tvCondition, tvBio, tvDental, tvPhysical, tvSystematic, tvAnaemia, tvSummary);
                    imgEye.setImageResource(R.drawable.eye_s);
                    eyeInclude.setVisibility(View.VISIBLE);
                    bioElse();
                    conditionElse();
                    systematicElse();
                    physicalElse();
                    dentalElse();
                    anaemiaElse();
                    summaryElse();
                } else if (eyeFlag == 2) {
                    eyeElse();
                }
                break;
            case R.id.ll_dental:
                if (dentalFlag == 1) {
                    dentalFlag = 2;
                    selectReport(llDental, llCondition, llEye, llBio, llPhysical, llSystematic, llAnaemia, llSummary,
                            dentalArrow, conArrow, eyeArrow, bioArrow, phyArrow, sysArrow, anaemiaArrow, summaryArrow,
                            tvDental, tvCondition, tvEye, tvBio, tvPhysical, tvSystematic, tvAnaemia, tvSummary);
                    dentalInclude.setVisibility(View.VISIBLE);
                    imgDental.setImageResource(R.drawable.dental_s);
                    bioElse();
                    conditionElse();
                    physicalElse();
                    systematicElse();
                    anaemiaElse();
                    eyeElse();
                    summaryElse();

                } else if (dentalFlag == 2) {
                    dentalElse();
                }
                break;
            case R.id.ll_physical:
                if (physicalFlag == 1) {
                    physicalFlag = 2;
                    selectReport(llPhysical, llCondition, llEye, llDental, llBio, llSystematic, llAnaemia, llSummary,
                            phyArrow, conArrow, bioArrow, dentalArrow, eyeArrow, sysArrow, anaemiaArrow, summaryArrow,
                            tvPhysical, tvCondition, tvEye, tvDental, tvBio, tvSystematic, tvAnaemia, tvSummary);
                    imgPhysical.setImageResource(R.drawable.physical_s);
                    physicalInclude.setVisibility(View.VISIBLE);
                    bioElse();
                    conditionElse();
                    systematicElse();
                    eyeElse();
                    anaemiaElse();
                    dentalElse();
                    summaryElse();
                } else if (physicalFlag == 2) {
                    physicalElse();
                }
                break;
            case R.id.ll_systematic:
                if (systematicFlag == 1) {
                    systematicFlag = 2;
                    selectReport(llSystematic, llCondition, llEye, llDental, llPhysical, llBio, llAnaemia, llSummary,
                            sysArrow, conArrow, bioArrow, dentalArrow, phyArrow, eyeArrow, anaemiaArrow, summaryArrow,
                            tvSystematic, tvCondition, tvEye, tvDental, tvPhysical, tvBio, tvAnaemia, tvSummary);

                    systematicInclude.setVisibility(View.VISIBLE);
                    imgSys.setImageResource(R.drawable.systematic_s);
                    bioElse();
                    conditionElse();
                    physicalElse();
                    eyeElse();
                    anaemiaElse();
                    dentalElse();
                    summaryElse();
                } else if (systematicFlag == 2) {
                    systematicElse();
                }
                break;
            case R.id.ll_anaemia:
                if (anaemiaFla == 1) {
                    anaemiaFla = 2;
                    selectReport(llAnaemia, llCondition, llEye, llDental, llPhysical, llSystematic, llBio, llSummary,
                            anaemiaArrow, conArrow, bioArrow, dentalArrow, phyArrow, sysArrow, eyeArrow, summaryArrow,
                            tvAnaemia, tvCondition, tvEye, tvDental, tvPhysical, tvSystematic, tvBio, tvSummary);
                    imgAnaemia.setImageResource(R.drawable.anaemia_s);
                    anaemiaInclude.setVisibility(View.VISIBLE);
                    bioElse();
                    conditionElse();
                    physicalElse();
                    systematicElse();
                    eyeElse();
                    dentalElse();
                    summaryElse();
                } else if (anaemiaFla == 2) {
                    anaemiaElse();
                }
                break;
            case R.id.ll_summary:
                if (summaryFlag == 1) {
                    summaryFlag = 2;
                    selectReport(llSummary, llCondition, llEye, llDental, llPhysical, llSystematic, llAnaemia, llBio,
                            summaryArrow, conArrow, bioArrow, dentalArrow, phyArrow, sysArrow, anaemiaArrow, eyeArrow,
                            tvSummary, tvCondition, tvEye, tvDental, tvPhysical, tvSystematic, tvAnaemia, tvBio);
                    imgSummary.setImageResource(R.drawable.summary_s);
                    includeSummary.setVisibility(View.VISIBLE);
                    bioElse();
                    conditionElse();
                    physicalElse();
                    systematicElse();
                    eyeElse();
                    dentalElse();
                    anaemiaElse();
                } else if (summaryFlag == 2) {
                    summaryElse();
                }
                break;
            default:
                break;
        }
    }

    void selectReport(LinearLayout linearLayout1, LinearLayout linearLayout2, LinearLayout linearLayout3,
                      LinearLayout linearLayout4, LinearLayout linearLayout5, LinearLayout linearLayout6, LinearLayout linearLayout7,
                      LinearLayout linearLayout8, ImageView params1, ImageView params2, ImageView params3, ImageView params4,
                      ImageView params5, ImageView params6, ImageView params7, ImageView params8,
                      TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5, TextView tv6, TextView tv7, TextView tv8) {
        linearLayout1.setBackgroundColor(CommonClass.getColor(this, R.color.tab_color));
        linearLayout2.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        linearLayout3.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        linearLayout4.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        linearLayout5.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        linearLayout6.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        linearLayout7.setBackgroundColor(CommonClass.getColor(this, R.color.white));
        linearLayout8.setBackgroundColor(CommonClass.getColor(this, R.color.white));

        params1.setImageResource(R.drawable.topwhitearrow);
        params2.setImageResource(R.drawable.tab_arrow);
        params3.setImageResource(R.drawable.tab_arrow);
        params4.setImageResource(R.drawable.tab_arrow);
        params5.setImageResource(R.drawable.tab_arrow);
        params6.setImageResource(R.drawable.tab_arrow);
        params7.setImageResource(R.drawable.tab_arrow);
        params8.setImageResource(R.drawable.tab_arrow);

        tv1.setTextColor(CommonClass.getColor(this, R.color.white));
        tv2.setTextColor(CommonClass.getColor(this, R.color.black));
        tv3.setTextColor(CommonClass.getColor(this, R.color.black));
        tv4.setTextColor(CommonClass.getColor(this, R.color.black));
        tv5.setTextColor(CommonClass.getColor(this, R.color.black));
        tv6.setTextColor(CommonClass.getColor(this, R.color.black));
        tv7.setTextColor(CommonClass.getColor(this, R.color.black));
        tv8.setTextColor(CommonClass.getColor(this, R.color.black));
    }

    void toolbarInit() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (toolbar != null) {
                if (childBean.getChild().getFirstName() != null)
                    toolbar.setTitle(getResources().getString(R.string.title_activity_health_report).concat(" " + childBean.getChild().getFirstName()));
                toolbar.setTitleTextColor(CommonClass.getColor(this, R.color.white));
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


}

package com.parentof.mai.views.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.ChildUpdateInterfaceCallback;
import com.parentof.mai.activityinterfaces.GetChildRespCallBack;
import com.parentof.mai.activityinterfaces.PassChildToActivityCallBack;
import com.parentof.mai.api.apicalls.GetChildrenAPI;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.decisionpointsmodel.DPRespModel;
import com.parentof.mai.model.getchildrenmodel.AdditionalInfo;
import com.parentof.mai.model.getchildrenmodel.AdditionalInfo_;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.getchildrenmodel.Child_;
import com.parentof.mai.model.getchildrenmodel.GetChildrenRespModel;
import com.parentof.mai.model.getchildrenmodel.HealthDetails;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PermissionClass;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;
import com.parentof.mai.views.activity.childProfile.ChildProfileActivity;
import com.parentof.mai.views.activity.userProfile.UserProfileActivity;
import com.parentof.mai.views.adapters.ChildrenAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaiDashboardActivity extends AppCompatActivity implements GetChildRespCallBack,
        ChildUpdateInterfaceCallback, PassChildToActivityCallBack, NavigationView.OnNavigationItemSelectedListener {


    private String TAG = "DshBrdActi ";

    AlertDialog alertDialog1;


    LinearLayout children;
    RecyclerView recyclerView;
    private GetChildrenRespModel getChildrenRespModel;
    int childrenNos = 0;
    private RecyclerView.Adapter adapter;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private DatabaseHelper databaseHelper;
    private DPRespModel decisionPointsRespModel;
    private String childId;
    private Child childBean;
    private boolean gotoChat = false;
    Toolbar toolbar;
    String userId;

    @Bind(R.id.ll_discovery)
    LinearLayout llDiscovery;
    @Bind(R.id.ll_dairy)
    LinearLayout llDairy;
    @Bind(R.id.ll_Intervention)
    LinearLayout llIntervention;
    @Bind(R.id.ll_health)
    LinearLayout llHealth;
    int selectedValues = 0;
    CircularImageView navUserImageView;
    TextView navUserName;
    Dialog dialog;
    @Bind(R.id.fab)
    ImageView imageViewFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mai_main_dashboard);
        ButterKnife.bind(this);
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putBoolean(PreferencesConstants.CALL_CHILD_API_FLAG, false);
        editor.apply();
        mediaPermissions();
        try {
            userId = String.valueOf(prefs.getInt(Constants._ID, 0));
            Logger.logD(Constants.PROJECT, "UserId-->" + userId);
            toolbarInit();
            openNavDrawer();
            children = (LinearLayout) findViewById(R.id.profile_img_ep);
            callChildApi();
            //showChildprofile();
            recyclerView = (RecyclerView) findViewById(R.id.childrenIconsRV);
            children.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MaiDashboardActivity.this, ChildProfileActivity.class);
                    startActivity(i);
                }
            });


        } catch (Exception e) {
            Logger.logE(TAG, "oncrt :", e);
        }
      //  hashMapDemo();


    }



    void openNavDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        navUserImageView = (CircularImageView) hView.findViewById(R.id.nav_userimageView);
        navUserName = (TextView) hView.findViewById(R.id.nav_userName);
        navUserName.setText(prefs.getString(PreferencesConstants.FIRST_NAME, ""));
        navUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserProfileActivity();
            }
        });
        setUserImageName();
    }

    void setUserImageName() {
        boolean userImgFlag = CommonClass.getUserImageFromDirectory(navUserImageView, userId);
        if (!userImgFlag) {
            CommonClass.setGravatarUserImage(prefs.getString(PreferencesConstants.RELATION, ""), navUserImageView);
        }
        navUserName.setText(prefs.getString(PreferencesConstants.FIRST_NAME, ""));
    }


    void loadChildListPopup() {
        try {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dashboard_child_popup);
            RecyclerView childsListRcv = (RecyclerView) dialog.findViewById(R.id.rcv_dbChildPopup);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            boolean childAvailable = setChildren(childsListRcv);
            Logger.logD(Constants.PROJECT, "Child List--" + childAvailable);
            Logger.logD(Constants.PROJECT, "ChildList-->" + childrenNos);
            if (childAvailable && childrenNos > 0)
                dialog.show();
            else
                ToastUtils.displayToast("Please add Child to access", this);

            // alertDialog1.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            Logger.logE(TAG, "Load popup--", e);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    void callChildApi() {
        if (CheckNetwork.checkNet(this)) {
            GetChildrenAPI.getChildrenInfo(this, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
        } else {
            ToastUtils.displayToast("Please check your internet connection and try again!", this);
            Logger.logD(TAG, " Please check your internet ");
        }
    }

    void mediaPermissions() {
        if (PermissionClass.checkPermission(this)) {

        } else {
            PermissionClass.requestPermission(this);

        }
    }


    @OnClick(R.id.ll_discovery)
    public void clickDiscovery() {
        loadChildListPopup();
        selectedValues = 1;
    }

    @OnClick(R.id.ll_dairy)
    public void clickDairy() {
        loadChildListPopup();
        selectedValues = 2;
    }

    @OnClick(R.id.ll_Intervention)
    public void clickIntervention() {
        loadChildListPopup();
        selectedValues = 3;
    }

    @OnClick(R.id.ll_health)
    public void clickHealth() {
        loadChildListPopup();
        selectedValues = 4;
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApiForOnBackPress();
        selectedValues = 0;
        setUserImageName();
        Logger.logD(Constants.PROJECT, "OnResume");
    }

    @OnClick(R.id.fab)
    public void clickFab() {
        if (childrenNos > 0) {
            loadChildListPopup();
          //  selectedValues = 5; // if health report is the we have give 5
            selectedValues = 4;
        } else
            ToastUtils.displayToast("Please add Child to access", this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (alertDialog1 != null)
            alertDialog1.dismiss();
        if (dialog != null)
            dialog.dismiss();
    }

    void callApiForOnBackPress() {
        if (prefs.getBoolean(PreferencesConstants.CALL_CHILD_API_FLAG, false))
            callChildApi();
    }

    void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Dashboard");
            toolbar.setTitleTextColor(CommonClass.getColor(this, R.color.white));
           /* ImageView imageView = (ImageView) toolbar.findViewById(R.id.parentIcon);
            imageView.setVisibility(View.VISIBLE);
            toolbar.findViewById(R.id.parentIcon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendUserProfileActivity();
                }
            });*/
        }
    }

    void sendUserProfileActivity() {
        try {
           /* Bundle b = new Bundle();
            b.putParcelable(Constants.GET_USER_ADDITIONAL, getChildrenRespModel.getData().getAdditionalInfo());*/
            Intent i = new Intent(MaiDashboardActivity.this, UserProfileActivity.class);
            // i.putExtras(b);
            startActivity(i);
        } catch (Exception e) {
            Logger.logE(TAG, " sndusrprfactvi ", e);
        }
    }

    void saveToPreference(AdditionalInfo additionalInfo) {
        if (additionalInfo != null) {
            editor.putString(PreferencesConstants.TYPE_OF_PROFESSION, additionalInfo.getProfession());
            editor.putString(PreferencesConstants.OFFICE_DAYS, additionalInfo.getOfficeDays());
            editor.putString(PreferencesConstants.OFFICE_TIMINGS, additionalInfo.getOfficeTiming());
            editor.putString(PreferencesConstants.AVERAGE_INCOME, additionalInfo.getAverageIncome());
            editor.putString(PreferencesConstants.DOB, additionalInfo.getDob());
            if (additionalInfo.getSeperateChildRoom() != null && additionalInfo.getSeperateChildRoom().equalsIgnoreCase("yes"))
                editor.putBoolean(PreferencesConstants.SEPARATE_ROOM, true);

            editor.putString(PreferencesConstants.RELATION, additionalInfo.getRelation());
           /* if(chSeparateRoom.isChecked()){
                editor.putBoolean(PreferencesConstants.SEPARATE_ROOM, true);
            }else{
                editor.putBoolean(PreferencesConstants.SEPARATE_ROOM, false);
            }*/
            editor.apply();
        }
    }


    @Override
    public void childrenDetails(GetChildrenRespModel getChildrenRespModel) {
        try {
            if (getChildrenRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE) && getChildrenRespModel.getData() != null) {
                this.getChildrenRespModel = getChildrenRespModel;
                saveToPreference(getChildrenRespModel.getData().getAdditionalInfo());
                setChildren(recyclerView);
                editor.putBoolean(PreferencesConstants.CALL_CHILD_API_FLAG, false);
                editor.apply();
                callDPAPIprefs();
                //showChildprofile();
            }
        } catch (Exception e) {
            Logger.logE(TAG, " exception + ", e);
        }
    }

    private boolean setChildren(RecyclerView recyclerView) {
        try {
            if (getChildrenRespModel != null && getChildrenRespModel.getData() != null && getChildrenRespModel.getData().getChilds() != null) {
                childrenNos = getChildrenRespModel.getData().getChilds().size();
                Logger.logD(Constants.PROJECT, "ChildList-->" + childrenNos);
                //Finally initializing our adapter
                List<Child> list = sortChildList();
                if (list.isEmpty())
                    return false;
                // Collections.reverse(list);
                adapter = new ChildrenAdapter(this, getChildrenRespModel, this, list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                //Adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "set Children", e);
            return false;
        }
        return true;
        // sortChildList();
    }

    List<Child> sortChildList() {
        List<Child> mainList = null;
        try {
            mainList = getChildrenRespModel.getData().getChilds();
            Logger.logD(Constants.PROJECT, "Child List--" + mainList.size());
            for (int i = 0; i < getChildrenRespModel.getData().getChilds().size(); i++) {
                //  Logger.logD(Constants.PROJECT, "Child Date List--" + getChildrenRespModel.getData().getChilds().get(i).getChild().getDob());
                Logger.logD(Constants.PROJECT, "Child After convert List--" + CommonClass.getDate(getChildrenRespModel.getData().getChilds().get(i).getChild().getDob()));
            }
            final List<Child> finalMainList = mainList;
            Collections.sort(mainList, new Comparator<Child>() {
                @Override
                public int compare(Child lhs, Child rhs) {
                    Child o1 = (Child) lhs;
                    Child o2 = (Child) rhs;
                    Logger.logD(Constants.PROJECT, "Child one" + o1.getChild().getDob());
                    Logger.logD(Constants.PROJECT, "Child two" + o2.getChild().getDob());
                    for (int i = 0; i < finalMainList.size(); i++) {
                        //  Logger.logD(Constants.PROJECT, "Child Date sort List--" + mainList.get(i).getChild().getDob());
                        Logger.logD(Constants.PROJECT, "Child After convert sort List--" + CommonClass.getDate(finalMainList.get(i).getChild().getDob()));
                    }

                    if (o1.getChild().getDob() != null && o2.getChild().getDob() != null)
                        return o1.getChild().getDob().compareTo(o2.getChild().getDob());
                    else
                        return 0;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainList;
    }


    @Override
    public void getUpdatedGenData(Child_ updatedChild) {


    }

    @Override
    public void getUpdatedHealthData(HealthDetails healthDetails) {

    }

    @Override
    public void getUpdatedAddiData(AdditionalInfo_ additionalInfo_) {

    }

    @Override
    public void passSelectedChildToActivity(Child childBean, String selBitmap) {
        try {
            this.childBean = new Child();
            this.childBean = childBean;
            childId = childBean.getChild().getId();
            editor.putString("child_image", selBitmap);
            editor.apply();

            Bundle b = new Bundle();
            if (childBean.getChild().getId() == null) {
                Logger.logD(TAG, "Child data not available");
            } else {
                b.putString(Constants.UPDATE_CHILD_ID, childId);
                b.putParcelable(Constants.SELECTED_CHILD, childBean);
                // b.putParcelable(Constants.DECISION_POINTS, decisionPointsRespModel);
                b.putInt(Constants.SELECTED_CATEGORY, selectedValues);
                b.putParcelable(Constants.CHILD_LIST_BEAN, getChildrenRespModel);
                Intent i = new Intent(this, ChildDashboardActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
            //   DecisionPointsAPI.getDPs(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, this);

        } catch (Exception e) {
            Logger.logE(TAG, "onBndvwHldr :", e);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accounts) {
            // Handle the camera action
            ToastUtils.displayToast("Coming soon...", this);
        } else if (id == R.id.nav_notification) {
            Intent i = new Intent(MaiDashboardActivity.this, NotificationActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_privacy) {
            ToastUtils.displayToast("Coming soon...", this);

        } else if (id == R.id.nav_logout) {
            loadLogoutPopup();
        } else if (id == R.id.nav_freq) {
            ToastUtils.displayToast("Coming soon...", this);

        } else if (id == R.id.nav_abt) {
            ToastUtils.displayToast("Coming soon...", this);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    void loadLogoutPopup() {
        final AlertDialog alertDialog1;
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout_popup, null);
        alertDialog.setView(dialogView);
        TextView logoutYes = (TextView) dialogView.findViewById(R.id.logout_yes);
        TextView logoutNO = (TextView) dialogView.findViewById(R.id.logout_no);
        alertDialog1 = alertDialog.create();
        alertDialog1.show();
        alertDialog1.setCanceledOnTouchOutside(false);
        logoutNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
        logoutYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                finish();
                Intent i = new Intent(MaiDashboardActivity.this, MobileNumberActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });



    }

    void callDPAPIprefs() {
        try {
            for (Child child : getChildrenRespModel.getData().getChilds()) {
                prefs.edit().putBoolean(child.getChild().getId(), true).apply();
            }

        } catch (Exception e) {

        }
    }


}



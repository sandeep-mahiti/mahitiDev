package com.parentof.mai.views.activity.childProfile;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.AddChildAInfoRespCallBack;
import com.parentof.mai.activityinterfaces.ChildUpdateInterfaceCallback;
import com.parentof.mai.api.apicalls.AddChildAInfoAPI;
import com.parentof.mai.model.addchildmodel.AddChildAIRespModel;
import com.parentof.mai.model.getchildrenmodel.AdditionalInfo_;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.PreferencesConstants;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChildAdditionalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildAdditionalFragment extends Fragment implements AddChildAInfoRespCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = " chldAddInf ";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.child_additional_saveBtn)
    Button childAInfoBtn;

    @Bind(R.id.child_add_likesET)
    EditText likesEt;
    @Bind(R.id.child_add_dislikesEt)
    EditText dislikesEt;
    @Bind(R.id.child_add_hobbiesEt)
    EditText hobbiesEt;
    @Bind(R.id.child_add_skillsEt)
    EditText skillsEt;
    @Bind(R.id.child_add_allergiesEt)
    EditText allergiesEt;
    String childId;

    static Child childBean;
    AdditionalInfo_ childAdditionalBean;

    HashMap<String, String> childDetails;
    private SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private String cIdToUpdate;
    Bundle bundl;
    ChildUpdateInterfaceCallback callbackObj;
    Context context;

    AdditionalInfo_ additionalInfo_;

    public ChildAdditionalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1         Parameter 1.
     * @param childLocalBean Parameter 2.
     * @return A new instance of fragment ChildAdditionalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildAdditionalFragment newInstance(String param1, Child childLocalBean) {
        ChildAdditionalFragment fragment = new ChildAdditionalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        childBean = childLocalBean;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        childDetails = new HashMap<>();
        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        //childId=prefs.getString("childId", "");
        View view = inflater.inflate(R.layout.fragment_child_additional, container, false);
        childAInfoBtn = (Button) view.findViewById(R.id.child_additional_saveBtn);


        likesEt = (EditText) view.findViewById(R.id.child_add_likesET);

        dislikesEt = (EditText) view.findViewById(R.id.child_add_dislikesEt);

        hobbiesEt = (EditText) view.findViewById(R.id.child_add_hobbiesEt);
        skillsEt = (EditText) view.findViewById(R.id.child_add_skillsEt);

        allergiesEt = (EditText) view.findViewById(R.id.child_add_allergiesEt);

        childAInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getChildAddInfo())
                    saveAdditionalChildInfo();
            }
        });
        prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
        childId = childBean.getChild().getId();
        setAdditionalData(childBean.getChild().getAdditionalInfo());
        //childAddDetailsFromBundle();
        callbackObj = (ChildUpdateInterfaceCallback) getActivity();
        return view;


    }

    public void callInterface(ChildUpdateInterfaceCallback childUpdateInterfaceCallback) {
        callbackObj = childUpdateInterfaceCallback;
    }

    /*void childAddDetailsFromBundle() {
        try {
            if(getActivity().getIntent().getExtras()!=null) {
               // childId=getActivity().getIntent().getStringExtra(Constants.UPDATE_CHILD_ID);
                 bundl = getActivity().getIntent().getExtras();
                if (bundl!=null && bundl.getParcelable(Constants.SELECTED_CHILD)!=null)
                 fillBeanFromBundle(bundl);
            }
            setAdditionalData(childAdditionalBean);
        }catch(Exception e){
            Logger.logE(TAG, " gtChldADDinfo ", e);
        }

    }

    private void fillBeanFromBundle(Bundle b) {
        try {
            childBean = b.getParcelable(Constants.SELECTED_CHILD);
            childId=b.getString(Constants.UPDATE_CHILD_ID);
            if (childBean != null) {
                childAdditionalBean = childBean.getChild().getAdditionalInfo();
            } else {
                Logger.logD(TAG, "Unable to retrieve Additional Information");
            }
        }catch(Exception e){
            Logger.logE(TAG, " filBnFrmBndl ", e);
        }
    }*/

    private void setAdditionalData(AdditionalInfo_ childBean) {
        try {
            if (childBean != null) {
                if (childBean.getLikes() != null)
                    likesEt.setText(childBean.getLikes());
                if (childBean.getDislikes() != null)
                    dislikesEt.setText(childBean.getDislikes());
                if (childBean.getHobbies() != null)
                    hobbiesEt.setText(childBean.getHobbies());
                if (childBean.getSkills() != null)
                    skillsEt.setText(childBean.getSkills());
                if (childBean.getAllergies() != null)
                    allergiesEt.setText(childBean.getAllergies());
            }
        } catch (Exception e) {
            Logger.logE(TAG, " gtChldADDinfo ", e);
        }
    }

    void saveAdditionalChildInfo() {
        try {
            //getChildAddInfo();
            SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

            if (childDetails != null && !childDetails.isEmpty()) {
                if (CheckNetwork.checkNet(getActivity())) {
                    AddChildAInfoAPI.addChildAdditionalInfo(context, childDetails, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
                    //sendMobNumtoServer();
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", context);
                }
            } else {
                ToastUtils.displayToast("Missing Child Additional Information, please check!", context);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "svAddiChldInf ", e);
        }
    }

    void saveAdditionalChildInfoInstantSave() {
        try {
            //getChildAddInfo();
            SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

            if (childDetails != null && !childDetails.isEmpty()) {
                if (CheckNetwork.checkNet(getActivity())) {
                    AddChildAInfoAPI.addChildAdditionalInfoSave(context, childDetails, String.valueOf(prefs.getInt(Constants._ID, -1)), this);
                    //sendMobNumtoServer();
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", context);
                }
            } else {
                ToastUtils.displayToast("Missing Child Additional Information, please check!", context);
            }
        } catch (Exception e) {
            Logger.logE(TAG, "svAddiChldInf ", e);
        }
    }

    public boolean isChanged(String[] additionalInfo) {
        for (int i = 0; i < additionalInfo.length; i++) {
            if (!additionalInfo[i].isEmpty())
                return true;
        }
        return false;
    }

    private boolean getChildAddInfo() {
        boolean change = false;
        try {

            String likesValue = likesEt.getText().toString().trim();
            String disLikesValue = dislikesEt.getText().toString().trim();
            String hobbiesValue = hobbiesEt.getText().toString().trim();
            String skillsValue = skillsEt.getText().toString().trim();
            String allergiesValue = allergiesEt.getText().toString().trim();
            String[] additionalInfo = {likesValue, disLikesValue, hobbiesValue, skillsValue, allergiesValue};
            childDetails.put("childId", childId);
            childDetails.put("likes", likesValue);
            childDetails.put("dislikes", disLikesValue);
            childDetails.put("hobbies", hobbiesValue);
            childDetails.put("skills", skillsValue);
            childDetails.put("allergies", allergiesValue);

            AdditionalInfo_ additionalChildBeen = childBean.getChild().getAdditionalInfo();

            if (additionalChildBeen == null)
                return isChanged(additionalInfo);
            if ((additionalChildBeen.getLikes() == null && !likesValue.isEmpty()) || !likesValue.equals(additionalChildBeen.getLikes()))
                return true;
            if ((additionalChildBeen.getDislikes() == null && !disLikesValue.isEmpty()) || !disLikesValue.equals(additionalChildBeen.getDislikes()))
                return true;
            if ((additionalChildBeen.getHobbies() == null && !hobbiesValue.isEmpty()) || !hobbiesValue.equals(additionalChildBeen.getHobbies()))
                return true;
            if ((additionalChildBeen.getSkills() == null && !skillsValue.isEmpty()) || !skillsValue.equals(additionalChildBeen.getSkills()))
                return true;
            if ((additionalChildBeen.getAllergies() == null && !allergiesValue.isEmpty()) || !allergiesValue.equals(additionalChildBeen.getAllergies()))
                return true;

        } catch (Exception e) {
            Logger.logE(TAG, "getChldAddInfo ", e);
        }

        return change;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.logD(TAG, "nDestroyView()");
        if (getChildAddInfo())
            saveAdditionalChildInfoInstantSave();

    }

    @Override
    public void childAddInfoResp(AddChildAIRespModel addChildAIRespModel) {
        try {
            if (addChildAIRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast(addChildAIRespModel.getData(), context);
                Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + addChildAIRespModel.getData());
                if (childAdditionalBean == null)
                    childAdditionalBean = new AdditionalInfo_();
                childAdditionalBean.setLikes(childDetails.get("likes"));
                childAdditionalBean.setDislikes(childDetails.get("dislikes"));
                childAdditionalBean.setHobbies(childDetails.get("hobbies"));
                childAdditionalBean.setSkills(childDetails.get("skills"));
                childAdditionalBean.setAllergies(childDetails.get("allergies"));
                callbackObj.getUpdatedAddiData(childAdditionalBean);
                editor.putBoolean(PreferencesConstants.CALL_CHILD_API_FLAG, true);
                editor.apply();
            } else {
                ToastUtils.displayToast(addChildAIRespModel.getData(), context);
                Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + addChildAIRespModel.getData());
            }
        } catch (Exception e) {
            Logger.logE(TAG, "getChldDetails ", e);
        }
    }

    void setTempAddData() {
        additionalInfo_ = new AdditionalInfo_();
        additionalInfo_.setAllergies(allergiesEt.getText().toString().trim());
        additionalInfo_.setDislikes(dislikesEt.getText().toString().trim());
        additionalInfo_.setHobbies(hobbiesEt.getText().toString().trim());
        additionalInfo_.setLikes(likesEt.getText().toString().trim());
        additionalInfo_.setSkills(skillsEt.getText().toString().trim());
    }

}

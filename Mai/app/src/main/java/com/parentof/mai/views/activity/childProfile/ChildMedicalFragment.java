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
import com.parentof.mai.activityinterfaces.AddChildHinfoRespCallBack;
import com.parentof.mai.activityinterfaces.ChildUpdateInterfaceCallback;
import com.parentof.mai.api.apicalls.AddChildHealthInfoAPI;
import com.parentof.mai.model.addchildmodel.AddChildHIRespModel;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.getchildrenmodel.HealthDetails;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChildMedicalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChildMedicalFragment extends Fragment implements AddChildHinfoRespCallBack {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.child_health_saveBtn)
    Button childHealthSaveBtn;
    @Bind(R.id.child_health_docName)
    EditText docName;
    @Bind(R.id.child_health_docNum)
    EditText docNum;
    @Bind(R.id.child_health_hospName)
    EditText hospName;
    @Bind(R.id.child_health_hospAddress)
    EditText hospAddress;
    @Bind(R.id.child_health_hospAddress2)
    EditText hospAddress2;
    @Bind(R.id.child_health_hospNum)
    EditText hospNum;
    private HashMap<String, String> childDetails;
    private String TAG = "ChldHealthFrag";
    private String childId = "";
    private SharedPreferences prefs;
    SharedPreferences.Editor editor;
    static Child childBean;
    HealthDetails childMediBean;
    private String cIdToUpdate;
    ChildUpdateInterfaceCallback callbackObj;
    Context context;


    public ChildMedicalFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1         Parameter 1.
     * @param childLocalBean Parameter 2.
     * @return A new instance of fragment ChildMedicalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChildMedicalFragment newInstance(String param1, Child childLocalBean) {
        ChildMedicalFragment fragment = new ChildMedicalFragment();
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
        View view = inflater.inflate(R.layout.fragment_child_medical, container, false);
        childHealthSaveBtn = (Button) view.findViewById(R.id.child_health_saveBtn);
        docName = (EditText) view.findViewById(R.id.child_health_docName);

        docNum = (EditText) view.findViewById(R.id.child_health_docNum);

        hospName = (EditText) view.findViewById(R.id.child_health_hospName);
        hospAddress = (EditText) view.findViewById(R.id.child_health_hospAddress);
        hospAddress2 = (EditText) view.findViewById(R.id.child_health_hospAddress2);
        hospNum = (EditText) view.findViewById(R.id.child_health_hospNum);
        childHealthSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( getChildAddInfo())
                    childHealthSaveBtn();
            }
        });
        prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
        childId = childBean.getChild().getId();
        if (childBean.getChild().getHealthDetails() != null)
            setmedicalData(childBean.getChild().getHealthDetails());
        //childAddDetailsFromBundle();
        callbackObj = (ChildUpdateInterfaceCallback) getActivity();
        return view;
    }

    public void callInterface(ChildUpdateInterfaceCallback childUpdateInterfaceCallback) {
        callbackObj = childUpdateInterfaceCallback;
    }


    void childAddDetailsFromBundle() {
        try {
            if (getActivity().getIntent().getExtras() != null) {
                //childId=getActivity().getIntent().getStringExtra(Constants.UPDATE_CHILD_ID);
                Bundle b = getActivity().getIntent().getExtras();
                //if(b!=null &&  b.getParcelable(Constants.SELECTED_CHILD)!=null)
                // fillMedBeanFromBundle(b);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " gtChldADDinfo ", e);
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.logD(TAG, "nDestroyView()");
        if( getChildAddInfo())
            childHealthSaveBtnInstantSave();

    }
    private void setmedicalData(HealthDetails childMediBean) {

        //childDetails.put("childId", childId);
        try {
            if (childMediBean.getDoctorName() != null)
                docName.setText(childMediBean.getDoctorName());
            if (childMediBean.getDoctorContactNumber() != null)
                docNum.setText(childMediBean.getDoctorContactNumber());
            if (childMediBean.getHospitalName() != null)
                hospName.setText(childMediBean.getHospitalName());
            if (childMediBean.getHospitalAddress() != null)
                hospAddress.setText(childMediBean.getHospitalAddress());
            if (childMediBean.getHospitalContactNumber() != null)
                hospNum.setText(childMediBean.getHospitalContactNumber());
            //hospNum.setText(childMediBean.get);

        } catch (Exception e) {
            Logger.logE(TAG, " setmedicalData ", e);
        }
    }

    void childHealthSaveBtn() {
        try {
            //getChildAddInfo();
            SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
            if (childDetails != null && !childDetails.isEmpty()) {
                if (CheckNetwork.checkNet(getActivity())) {
                    AddChildHealthInfoAPI.addChildMedicalInfo(context, childDetails,  String.valueOf(prefs.getInt(Constants._ID, -1)), this);

                    //sendMobNumtoServer();
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", context);
                }
            } else {
                ToastUtils.displayToast("Missing Child Medical Information, please check!",context);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " childHealthSaveBtn ", e);
        }
    }
    void childHealthSaveBtnInstantSave() {
        try {
            //getChildAddInfo();
            SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
            if (childDetails != null && !childDetails.isEmpty()) {
                if (CheckNetwork.checkNet(getActivity())) {
                    AddChildHealthInfoAPI.addChildMedicalInfoSave(context, childDetails, String.valueOf(prefs.getInt(Constants._ID, -1)), this);

                    //sendMobNumtoServer();
                } else {
                    ToastUtils.displayToast("Please check your internet connection and try again!", context);
                }
            } else {
                ToastUtils.displayToast("Missing Child Medical Information, please check!",context);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " childHealthSaveBtn ", e);
        }
    }


    public boolean isChangedAnyFields(String[] medicalInfo){
        for(int i=0;i<medicalInfo.length;i++){
            if(!medicalInfo[i].isEmpty())
                return true;
        }
        return false;
    }
    private boolean getChildAddInfo() {
        boolean change=false;
        try {
            String doctorNameValue = docName.getText().toString().trim();
            String doctorNumberValue = docNum.getText().toString().trim();
            String hospitalNameValue = hospName.getText().toString().trim();
            String hospAddressValue = hospAddress.getText().toString().trim();
            String hospitalAdd2 = hospAddress2.getText().toString().trim();
            String hospNumberValue = hospNum.getText().toString().trim();
            String[] medicalInfoStrings = {doctorNameValue, doctorNumberValue, hospitalNameValue, hospAddressValue, hospitalAdd2,hospNumberValue};
            HealthDetails child = childBean.getChild().getHealthDetails();
            boolean flag = isChangedAnyFields(medicalInfoStrings);
            if(!flag)
                return false;
            if(!doctorNumberValue.isEmpty()) {
                if (doctorNumberValue.length() != 10) {
                    ToastUtils.displayToast("Please enter 10 digit doctor mobile number", getActivity());
                    return false;
                }
            }

            if (!hospNumberValue.isEmpty()){
                if(hospNumberValue.length()<6 || hospNumberValue.length() >12) {
                    ToastUtils.displayToast("Please enter valid hospital number(6-12 digits)", getActivity());
                    return false;
                }
            }
            if(!hospitalAdd2.isEmpty())
                hospAddressValue = hospAddressValue +" "+hospitalAdd2;
            childDetails.put("childId", childId);
            childDetails.put("doctorName", doctorNameValue);
            childDetails.put("doctorContactNumber", doctorNumberValue);
            childDetails.put("HospitalName", hospitalNameValue);
            childDetails.put("HospitalAddress", hospAddressValue);
            //No hospital Num params for API but only in UI.
            childDetails.put("HospitalContactNumber", hospNumberValue);
            if(child==null)
                return true;
            if((child.getDoctorName()==null && !doctorNameValue.isEmpty()) || !doctorNameValue.equals(child.getDoctorName()))
                return true;
            if((child.getDoctorContactNumber()==null && !doctorNumberValue.isEmpty()) || !doctorNumberValue.equals(child.getDoctorContactNumber()))
                return true;
            if((child.getHospitalName()==null && !hospitalNameValue.isEmpty()) || !hospitalNameValue.equals(child.getHospitalName()))
                return true;
            if((child.getHospitalAddress()==null && !hospAddressValue.isEmpty()) || !hospAddressValue.equals(child.getHospitalAddress()))
                return true;
            if((child.getHospitalContactNumber()==null && hospNumberValue.isEmpty()) || !hospNum.getText().toString().equals(child.getHospitalContactNumber()))
                return true;
        } catch (Exception e) {
            Logger.logE(TAG, "getChldMedInfo ", e);
        }


        return  false;
    }


    @Override
    public void childHealthInfo(AddChildHIRespModel addChildHIRespModel) {
        try {
            if (addChildHIRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast(addChildHIRespModel.getData(), getActivity());
                Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + addChildHIRespModel.getData());
                if (childMediBean == null)
                    childMediBean = new HealthDetails();
                childMediBean.setDoctorName(String.valueOf(docName.getText()));
                childMediBean.setDoctorContactNumber(String.valueOf(docNum.getText()));
                childMediBean.setHospitalName(String.valueOf(hospName.getText()));
                childMediBean.setHospitalAddress(String.valueOf(hospAddress.getText()).concat(" " + hospAddress2.getText()));
                childMediBean.setHospitalContactNumber(String.valueOf(hospNum.getText()));
                callbackObj.getUpdatedHealthData(childMediBean);
                editor.putBoolean(PreferencesConstants.CALL_CHILD_API_FLAG, true);
                editor.apply();
            } else {
                ToastUtils.displayToast(addChildHIRespModel.getData(), getActivity());
                Logger.logD(TAG, " OTPSubmit, getMobileNumResp : " + addChildHIRespModel.getData());
            }
        } catch (Exception e) {
            Logger.logE(TAG, "childHealthInfo ", e);
        }
    }
}

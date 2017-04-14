
package com.parentof.mai.model.getchildrenmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HealthDetails implements Parcelable
{

    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("doctorContactNumber")
    @Expose
    private String doctorContactNumber;
    @SerializedName("HospitalName")
    @Expose
    private String hospitalName;
    @SerializedName("HospitalAddress")
    @Expose
    private String hospitalAddress;
    @SerializedName("HospitalContactNumber")
    @Expose
    private String hospitalContactNumber;

    public final static Creator<HealthDetails> CREATOR = new Creator<HealthDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public HealthDetails createFromParcel(Parcel in) {
            HealthDetails instance = new HealthDetails();
            instance.doctorName = ((String) in.readValue((String.class.getClassLoader())));
            instance.doctorContactNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.hospitalName = ((String) in.readValue((String.class.getClassLoader())));
            instance.hospitalAddress = ((String) in.readValue((String.class.getClassLoader())));
            instance.hospitalContactNumber=((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public HealthDetails[] newArray(int size) {
            return (new HealthDetails[size]);
        }

    }
    ;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorContactNumber() {
        return doctorContactNumber;
    }

    public void setDoctorContactNumber(String doctorContactNumber) {
        this.doctorContactNumber = doctorContactNumber;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalContactNumber() {
        return hospitalContactNumber;
    }

    public void setHospitalContactNumber(String hospitalContactNumber) {
        this.hospitalContactNumber = hospitalContactNumber;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(doctorName);
        dest.writeValue(doctorContactNumber);
        dest.writeValue(hospitalName);
        dest.writeValue(hospitalAddress);
        dest.writeValue(hospitalContactNumber);

    }

    public int describeContents() {
        return  0;
    }

}

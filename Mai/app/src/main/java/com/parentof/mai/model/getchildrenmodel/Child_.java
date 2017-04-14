
package com.parentof.mai.model.getchildrenmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child_ implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("nickName")
    @Expose
    private String nickName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("rollNumber")
    @Expose
    private String rollNumber;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("additionalInfo")
    @Expose
    private AdditionalInfo_ additionalInfo;
    @SerializedName("healthDetails")
    @Expose
    private HealthDetails healthDetails;
    public final static Creator<Child_> CREATOR = new Creator<Child_>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Child_ createFromParcel(Parcel in) {
            Child_ instance = new Child_();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.firstName = ((String) in.readValue((String.class.getClassLoader())));
            instance.nickName = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastName = ((String) in.readValue((String.class.getClassLoader())));
            instance.dob = ((String) in.readValue((String.class.getClassLoader())));
            instance.gender = ((String) in.readValue((String.class.getClassLoader())));
            instance.bloodGroup = ((String) in.readValue((String.class.getClassLoader())));
            instance.school = ((String) in.readValue((String.class.getClassLoader())));
            instance._class = ((String) in.readValue((String.class.getClassLoader())));
            instance.section = ((String) in.readValue((String.class.getClassLoader())));
            instance.rollNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.v = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.createdDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.additionalInfo = ((AdditionalInfo_) in.readValue((AdditionalInfo_.class.getClassLoader())));
            instance.healthDetails = ((HealthDetails) in.readValue((HealthDetails.class.getClassLoader())));
            return instance;
        }

        public Child_[] newArray(int size) {
            return (new Child_[size]);
        }

    }
    ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public AdditionalInfo_ getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo_ additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public HealthDetails getHealthDetails() {
        return healthDetails;
    }

    public void setHealthDetails(HealthDetails healthDetails) {
        this.healthDetails = healthDetails;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(firstName);
        dest.writeValue(nickName);
        dest.writeValue(lastName);
        dest.writeValue(dob);
        dest.writeValue(gender);
        dest.writeValue(bloodGroup);
        dest.writeValue(school);
        dest.writeValue(_class);
        dest.writeValue(section);
        dest.writeValue(rollNumber);
        dest.writeValue(v);
        dest.writeValue(createdDate);
        dest.writeValue(additionalInfo);
        dest.writeValue(healthDetails);
    }

    public int describeContents() {
        return  0;
    }

}

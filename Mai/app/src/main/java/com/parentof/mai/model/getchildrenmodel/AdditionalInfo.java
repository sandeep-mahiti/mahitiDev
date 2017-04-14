
package com.parentof.mai.model.getchildrenmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalInfo implements Parcelable
{

    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("officeDays")
    @Expose
    private String officeDays;
    @SerializedName("officeTiming")
    @Expose
    private String officeTiming;
    @SerializedName("averageIncome")
    @Expose
    private String averageIncome;
    @SerializedName("seperateChildRoom")
    @Expose
    private String seperateChildRoom;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("relation")
    @Expose
    private String relation;
    public final static Creator<AdditionalInfo> CREATOR = new Creator<AdditionalInfo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AdditionalInfo createFromParcel(Parcel in) {
            AdditionalInfo instance = new AdditionalInfo();
            instance.profession = ((String) in.readValue((String.class.getClassLoader())));
            instance.officeDays = ((String) in.readValue((String.class.getClassLoader())));
            instance.officeTiming = ((String) in.readValue((String.class.getClassLoader())));
            instance.averageIncome = ((String) in.readValue((String.class.getClassLoader())));
            instance.seperateChildRoom = ((String) in.readValue((String.class.getClassLoader())));
            instance.dob = ((String) in.readValue((String.class.getClassLoader())));
            instance.relation = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public AdditionalInfo[] newArray(int size) {
            return (new AdditionalInfo[size]);
        }

    }
    ;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getOfficeDays() {
        return officeDays;
    }

    public void setOfficeDays(String officeDays) {
        this.officeDays = officeDays;
    }

    public String getOfficeTiming() {
        return officeTiming;
    }

    public void setOfficeTiming(String officeTiming) {
        this.officeTiming = officeTiming;
    }

    public String getAverageIncome() {
        return averageIncome;
    }

    public void setAverageIncome(String averageIncome) {
        this.averageIncome = averageIncome;
    }

    public String getSeperateChildRoom() {
        return seperateChildRoom;
    }

    public void setSeperateChildRoom(String seperateChildRoom) {
        this.seperateChildRoom = seperateChildRoom;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(profession);
        dest.writeValue(officeDays);
        dest.writeValue(officeTiming);
        dest.writeValue(averageIncome);
        dest.writeValue(seperateChildRoom);
        dest.writeValue(dob);
        dest.writeValue(relation);
    }

    public int describeContents() {
        return  0;
    }

}

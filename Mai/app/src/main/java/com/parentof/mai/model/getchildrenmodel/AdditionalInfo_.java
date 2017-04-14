
package com.parentof.mai.model.getchildrenmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalInfo_ implements Parcelable
{

    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("dislikes")
    @Expose
    private String dislikes;
    @SerializedName("hobbies")
    @Expose
    private String hobbies;
    @SerializedName("skills")
    @Expose
    private String skills;
    @SerializedName("allergies")
    @Expose
    private String allergies;
    public final static Creator<AdditionalInfo_> CREATOR = new Creator<AdditionalInfo_>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AdditionalInfo_ createFromParcel(Parcel in) {
            AdditionalInfo_ instance = new AdditionalInfo_();
            instance.likes = ((String) in.readValue((String.class.getClassLoader())));
            instance.dislikes = ((String) in.readValue((String.class.getClassLoader())));
            instance.hobbies = ((String) in.readValue((String.class.getClassLoader())));
            instance.skills = ((String) in.readValue((String.class.getClassLoader())));
            instance.allergies = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public AdditionalInfo_[] newArray(int size) {
            return (new AdditionalInfo_[size]);
        }

    }
    ;

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(likes);
        dest.writeValue(dislikes);
        dest.writeValue(hobbies);
        dest.writeValue(skills);
        dest.writeValue(allergies);
    }

    public int describeContents() {
        return  0;
    }

}

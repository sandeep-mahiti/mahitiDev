
package com.parentof.mai.model.getchildrenmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child implements Parcelable
{

    @SerializedName("child")
    @Expose
    private Child_ child;
    @SerializedName("isPrimary")
    @Expose
    private Boolean isPrimary;
    @SerializedName("_id")
    @Expose
    private String id;
    public final static Creator<Child> CREATOR = new Creator<Child>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Child createFromParcel(Parcel in) {
            Child instance = new Child();
            instance.child = ((Child_) in.readValue((Child_.class.getClassLoader())));
            instance.isPrimary = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Child[] newArray(int size) {
            return (new Child[size]);
        }

    }
    ;

    public Child_ getChild() {
        return child;
    }

    public void setChild(Child_ child) {
        this.child = child;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(child);
        dest.writeValue(isPrimary);
        dest.writeValue(id);
    }

    public int describeContents() {
        return  0;
    }

}

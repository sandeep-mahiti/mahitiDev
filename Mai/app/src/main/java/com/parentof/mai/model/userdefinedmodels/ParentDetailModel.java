package com.parentof.mai.model.userdefinedmodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mahiti on 5/1/17.
 */
public class ParentDetailModel implements Parcelable {

    public ParentDetailModel() {
    }

    public ParentDetailModel(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
    }

    public static final Creator<ParentDetailModel> CREATOR = new Creator<ParentDetailModel>() {
        @Override
        public ParentDetailModel createFromParcel(Parcel in) {
            return new ParentDetailModel(in);
        }

        @Override
        public ParentDetailModel[] newArray(int size) {
            return new ParentDetailModel[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String firstName;
    String lastName;
    String email;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
    }
}

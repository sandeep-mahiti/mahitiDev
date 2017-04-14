package com.parentof.mai.model.activateskillmodel.chatmodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sandeep HR on 28/01/17.
 */


public class CustomQstAnsModel implements Parcelable {
    private String qId;
    private String question;
    private String answer;
    private String indicator;

    public CustomQstAnsModel() {
    }

    protected CustomQstAnsModel(Parcel in) {
        qId = in.readString();
        question = in.readString();
        answer = in.readString();
        indicator = in.readString();
    }

    public static final Creator<CustomQstAnsModel> CREATOR = new Creator<CustomQstAnsModel>() {
        @Override
        public CustomQstAnsModel createFromParcel(Parcel in) {
            return new CustomQstAnsModel(in);
        }

        @Override
        public CustomQstAnsModel[] newArray(int size) {
            return new CustomQstAnsModel[size];
        }
    };

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(qId);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(indicator);
    }
}

package com.parentof.mai.model.verifyuser.succesModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parentof.mai.model.getchildrenmodel.Child;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
{

    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = new ArrayList<>();
    public final static Parcelable.Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            Data instance = new Data();
            instance.min = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.questions, (com.parentof.mai.model.verifyuser.succesModel.Question.class.getClassLoader()));
            return instance;
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
            ;

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(min);
        dest.writeList(questions);
    }

    public int describeContents() {
        return 0;
    }

}



/*public class Message implements Parcelable {

    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    protected Message(Parcel in) {


    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}*/

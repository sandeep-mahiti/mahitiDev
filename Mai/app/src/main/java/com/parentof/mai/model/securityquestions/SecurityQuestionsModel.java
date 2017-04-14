
package com.parentof.mai.model.securityquestions;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecurityQuestionsModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("minCorrect")
    @Expose
    private Integer minCorrect;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMinCorrect() {
        return minCorrect;
    }

    public void setMinCorrect(Integer minCorrect) {
        this.minCorrect = minCorrect;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}

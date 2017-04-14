package com.parentof.mai.model.healthReportModel;

/**
 * Created by mahiti on 8/2/17.
 */
public class SystematicModel {
    String cardDiovascular;
    String respiratory;
    String abdominal;
    String musculoskeletal;
    String childId;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    int pId;

    public String getAbdominal() {
        return abdominal;
    }

    public void setAbdominal(String abdominal) {
        this.abdominal = abdominal;
    }

    public String getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(String respiratory) {
        this.respiratory = respiratory;
    }

    public String getCardDiovascular() {
        return cardDiovascular;
    }

    public void setCardDiovascular(String cardDiovascular) {
        this.cardDiovascular = cardDiovascular;
    }

    public String getMusculoskeletal() {
        return musculoskeletal;
    }

    public void setMusculoskeletal(String musculoskeletal) {
        this.musculoskeletal = musculoskeletal;
    }


}

package com.parentof.mai.views.activity.immuniazation;

/**
 * Created by mahiti on 11/2/17.
 */
public class ImmunizationBean {


   private String vaccineId;
    private String vaccineCategoryName;
    private String vaccineDate;
    private  String vaccineHelpText;
    private  boolean primaryFlag;
    private  int pId;
    private String childId;
    private String vaccineName;
    public  ImmunizationBean(){

    }

    public ImmunizationBean(
            String vaccineCategoryName,
            String vaccineId,
            String vaccineName,
            String id,
            boolean primaryFlag) {
        this.vaccineId = vaccineId;
        this.vaccineCategoryName = vaccineCategoryName;
        this.vaccineName = vaccineName;
        this.id = id;
        this.primaryFlag = primaryFlag;
    }



    public String getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(String vaccineId) {
        this.vaccineId = vaccineId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getVaccineCategoryName() {
        return vaccineCategoryName;
    }

    public void setVaccineCategoryName(String vaccineCategoryName) {
        this.vaccineCategoryName = vaccineCategoryName;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(String vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public String getVaccineHelpText() {
        return vaccineHelpText;
    }

    public void setVaccineHelpText(String vaccineHelpText) {
        this.vaccineHelpText = vaccineHelpText;
    }

    public boolean isPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(boolean primaryFlag) {
        this.primaryFlag = primaryFlag;
    }

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


}

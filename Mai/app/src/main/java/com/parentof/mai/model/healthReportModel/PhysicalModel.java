package com.parentof.mai.model.healthReportModel;

/**
 * Created by mahiti on 8/2/17.
 */
public class PhysicalModel {


   /* <string name="oedema">Oedema Pedal</string>
    <string name="sacrat">Sacrat</string>
    <string name="peri_orbital">Peri Orbital</string>
    <string name="lymphadenopathy">Lymphadenopathy</string>
    <string name="cervical_anterior">Cervical Anterior</string>
    <string name="cervical_posterior">Cervical Posterior</string>
    <string name="axillary">Axillary</string>
    <string name="occipital">Occipital</string>
    <string name="fpitrochlear">Fpitrochlear</string>*/

    String icterus;
    String cyanosis;

    String clubbing;
    String oedema;

    String sacrat;
    String periOrbital;

    String lymphadenopathy;
    String cervicalPosterior;

    String axillary;
    String occipital;

    String cervicalAnterior;
    String fpitrochlear;
    int pId;

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

    String childId;

    public String getCyanosis() {
        return cyanosis;
    }

    public void setCyanosis(String cyanosis) {
        this.cyanosis = cyanosis;
    }

    public String getIcterus() {
        return icterus;
    }

    public void setIcterus(String icterus) {
        this.icterus = icterus;
    }

    public String getClubbing() {
        return clubbing;
    }

    public void setClubbing(String clubbing) {
        this.clubbing = clubbing;
    }

    public String getOedema() {
        return oedema;
    }

    public void setOedema(String oedema) {
        this.oedema = oedema;
    }

    public String getSacrat() {
        return sacrat;
    }

    public void setSacrat(String sacrat) {
        this.sacrat = sacrat;
    }

    public String getPeriOrbital() {
        return periOrbital;
    }

    public void setPeriOrbital(String periOrbital) {
        this.periOrbital = periOrbital;
    }

    public String getLymphadenopathy() {
        return lymphadenopathy;
    }

    public void setLymphadenopathy(String lymphadenopathy) {
        this.lymphadenopathy = lymphadenopathy;
    }

    public String getCervicalPosterior() {
        return cervicalPosterior;
    }

    public void setCervicalPosterior(String cervicalPosterior) {
        this.cervicalPosterior = cervicalPosterior;
    }

    public String getAxillary() {
        return axillary;
    }

    public void setAxillary(String axillary) {
        this.axillary = axillary;
    }

    public String getOccipital() {
        return occipital;
    }

    public void setOccipital(String occipital) {
        this.occipital = occipital;
    }

    public String getCervicalAnterior() {
        return cervicalAnterior;
    }

    public void setCervicalAnterior(String cervicalAnterior) {
        this.cervicalAnterior = cervicalAnterior;
    }

    public String getFpitrochlear() {
        return fpitrochlear;
    }

    public void setFpitrochlear(String fpitrochlear) {
        this.fpitrochlear = fpitrochlear;
    }


}

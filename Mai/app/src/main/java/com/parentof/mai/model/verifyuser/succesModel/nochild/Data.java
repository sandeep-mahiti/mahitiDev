
package com.parentof.mai.model.verifyuser.succesModel.nochild;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Serializable
{

    @SerializedName("haveChild")
    @Expose
    private Boolean haveChild;
    private final static long serialVersionUID = 3672984311926767199L;

    public Boolean getHaveChild() {
        return haveChild;
    }

    public void setHaveChild(Boolean haveChild) {
        this.haveChild = haveChild;
    }

}

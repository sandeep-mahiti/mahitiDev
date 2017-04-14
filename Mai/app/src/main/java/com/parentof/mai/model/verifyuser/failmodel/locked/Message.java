
package com.parentof.mai.model.verifyuser.failmodel.locked;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Serializable
{

    @SerializedName("isLocked")
    @Expose
    private Boolean isLocked;
    private final static long serialVersionUID = -4237836833238693284L;

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

}

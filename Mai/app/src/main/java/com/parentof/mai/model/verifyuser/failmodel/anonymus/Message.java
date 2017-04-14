
package com.parentof.mai.model.verifyuser.failmodel.anonymus;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Serializable
{

    @SerializedName("anonymous")
    @Expose
    private Boolean anonymous;
    private final static long serialVersionUID = -4070590487795896660L;

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

}

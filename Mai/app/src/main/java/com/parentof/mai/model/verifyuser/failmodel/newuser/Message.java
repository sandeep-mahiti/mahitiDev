
package com.parentof.mai.model.verifyuser.failmodel.newuser;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message implements Serializable
{

    @SerializedName("newuser")
    @Expose
    private Boolean newuser;
    private final static long serialVersionUID = 2702682470166841214L;

    public Boolean getNewuser() {
        return newuser;
    }

    public void setNewuser(Boolean newuser) {
        this.newuser = newuser;
    }

}

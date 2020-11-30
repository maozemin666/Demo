package com.example.myapplication.haikang.bean;

import com.google.gson.annotations.SerializedName;

public class ControlResp {

    /**
     * code : 0
     * msg : success
     * data : null
     */

    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

package com.example.myapplication.haikang.bean;

import com.google.gson.annotations.SerializedName;

public class PreviewResp {
    /**
     * code : 0
     * msg : success
     * data : {"url":"rtsp://10.2.145.66:655/EUrl/CLJ52BW"}
     */

    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public static class DataDTO {
        /**
         * url : rtsp://10.2.145.66:655/EUrl/CLJ52BW
         */

        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }
}

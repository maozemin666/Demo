package com.example.myapplication.haikang.bean;

import com.google.gson.annotations.SerializedName;

public class PreviewRequest {

    /**
     * cameraIndexCode : 748d84750e3a4a5bbad3cd4af9ed5101
     * streamType : 0
     * protocol : rtsp
     * transmode : 1
     * expand : transcode=0
     * streamform : ps
     */

    @SerializedName("cameraIndexCode")
    private String cameraIndexCode;
    @SerializedName("streamType")
    private int streamType;
    @SerializedName("protocol")
    private String protocol;
    @SerializedName("transmode")
    private int transmode;
    @SerializedName("expand")
    private String expand;
    @SerializedName("streamform")
    private String streamform;

    public String getCameraIndexCode() {
        return cameraIndexCode;
    }

    public void setCameraIndexCode(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getTransmode() {
        return transmode;
    }

    public void setTransmode(int transmode) {
        this.transmode = transmode;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public String getStreamform() {
        return streamform;
    }

    public void setStreamform(String streamform) {
        this.streamform = streamform;
    }
}

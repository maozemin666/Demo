package com.example.myapplication.haikang.bean;

import com.google.gson.annotations.SerializedName;

public class ControlRequest {

    /**
     * cameraIndexCode : 748d84750e3a4a5bbad3cd4af9ed5101
     * action : 1
     * command : GOTO_PRESET
     * speed : 4
     * presetIndex : 20
     */

    @SerializedName("cameraIndexCode")
    private String cameraIndexCode;
    @SerializedName("action")
    private int action;
    @SerializedName("command")
    private String command;
    @SerializedName("speed")
    private int speed;
    @SerializedName("presetIndex")
    private int presetIndex;

    public String getCameraIndexCode() {
        return cameraIndexCode;
    }

    public void setCameraIndexCode(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPresetIndex() {
        return presetIndex;
    }

    public void setPresetIndex(int presetIndex) {
        this.presetIndex = presetIndex;
    }
}

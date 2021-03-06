package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.haikang.log.SuperLog;
import com.example.myapplication.haikang.log.UtilBase;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        UtilBase.init(this);
        SuperLog.setEnable(true, false);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}

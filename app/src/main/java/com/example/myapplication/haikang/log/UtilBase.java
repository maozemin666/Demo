package com.example.myapplication.haikang.log;

import android.content.Context;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.retrofit.utils.UtilBase.java
 * @author: yh
 * @date: 2016-10-26 14:44
 */

public class UtilBase {

    private static final String TAG = "UtilBase";
    /**
     * the context of application
     */
    private static Context applicationContext;

    private static boolean debugVersion = false;

    public static final int TIME_LENGTH = 60;

    /**
     * init the sdk utils
     * @param context
     */
    public static void init(Context context)
    {
        applicationContext = context.getApplicationContext();
    }

    /**
     * set current version type,debug or release
     * @param isDebugVersion true -- debug,false -- release
     */
    public static void setDebugVersion(boolean isDebugVersion)
    {
        debugVersion = isDebugVersion;
    }

    /**
     * Get the current version is a debug
     * @return F/T
     */
    public static boolean isDebugVersion()
    {
        return debugVersion;
    }

    public static Context getApplicationContext()
    {
        if(null == applicationContext)
        {
            SuperLog.error(TAG,"The OTT SDK application context is null, you need to init OTTSDK by OTTSDK.init(context) before you use it");
        }
        return applicationContext;
    }

    public static void setApplicationContext(Context context)
    {
        UtilBase.applicationContext = context;
    }
}
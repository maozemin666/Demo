package com.example.myapplication.haikang.LogUtil;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * App统一路径管理
 * 负责debug、release版本的区分
 * 含data/data、外部sdcard区分
 * app中不同功能组件目录管理，日志、升级、白标、缓存等
 */
public class PathManager
{
    private static final String TAG = PathManager.class.getSimpleName();
    /**
     * 文件路径管理中的基础路径
     */
    private static String APP_ROOT_PATH = "";

    static
    {
        APP_ROOT_PATH = UtilBase.getApplicationContext().getFilesDir().getPath
                () + File.separator;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            APP_ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
        }
        APP_ROOT_PATH = APP_ROOT_PATH + File.separator + "HiKangDemo" + File.separator;
    }


    /**
     * 获取sdcard路径
     * as:/mnt/sdcard/
     *
     * @return String
     */
    public static String getAppRootPath()
    {
        String absolutePath = APP_ROOT_PATH;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    /**
     * 获取升级功能相关路径
     * 如apk升级包保存路径
     * as:/mnt/sdcard/OTT/Upgrade/
     *
     * @return String
     */
    public static String getUpgradePath()
    {
        String absolutePath = APP_ROOT_PATH + "Upgrade" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    /**
     * 获取日志存储路径
     * as:/mnt/sdcard/OTT/LOG/
     *
     * @return String
     */
    public static String getLogPath()
    {
        String absolutePath = APP_ROOT_PATH + "LOG" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    /**
     * file zip save path
     * as:/mnt/sdcard/OTT/Mail/
     *
     * @return String
     */
    public static String getMailZipPath()
    {
        String absolutePath = APP_ROOT_PATH + "Mail" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    /**
     * video download path
     * as:/mnt/sdcard/OTT/DownLoad/
     *
     * @return String absolute path
     */
    public static String getDownLoadPath()
    {
        String absolutePath = APP_ROOT_PATH + "DownLoad" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    public static String getGIFPath()
    {
        String absolutePath = APP_ROOT_PATH + "GIF" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    public static String getUserPicPath()
    {
        String absolutePath = APP_ROOT_PATH + "userPic" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    /**
     * Resource Customized path
     * as:/mnt/sdcard/OTT/ResourceCustomized/
     *
     * @return String absolute path
     */
    public static String getResourceCustomizedPath()
    {
        String absolutePath = APP_ROOT_PATH + "ResourceCustomized" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    /**
     * peel resource
     * @return
     */
    public static String getPeelResourcePath()
    {
        String absolutePath = APP_ROOT_PATH + "PeelResource" ;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    /**
     * 获取语音存储路径
     * as:/mnt/sdcard/OTT/AUDIO/
     *
     * @return String
     */
    public static String getAudioPath()
    {
        String absolutePath = APP_ROOT_PATH + "AUDIO" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }

    /**
     * auto create the file's path
     */
    private static void autoCreatePath(String path)
    {
        if(TextUtils.isEmpty(path))
        {
            return;
        }
        File file = new File(path);
        if(file.exists())
        {
            return;
        }
        boolean result = file.mkdir();
        SuperLog.debug(TAG,"mkdir "+path+" "+result);
    }

    /**
     * 获取日志存储路径
     * as:/mnt/sdcard/OTT/LOG/Player/
     *
     * @return String
     */
    public static String getPlayerLogPath() {
        String absolutePath = APP_ROOT_PATH + "LOG" + File.separator + "Player" + File.separator;
        autoCreatePath(absolutePath);
        return absolutePath;
    }
}

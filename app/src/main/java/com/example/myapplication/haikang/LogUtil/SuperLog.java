package com.example.myapplication.haikang.LogUtil;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.example.myapplication.MyApplication;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SuperLog {

    private static final String TAG = "SuperLog|";

    public static final String NEW_LINE = System.getProperty("line.separator");

    private static final String LOG_PATH = PathManager.getLogPath();

    private static final String MAIL_LOG_PATH = PathManager.getMailZipPath();

    public static final int JSON_INDENT = 4;

    public static final String NULL_TIPS = "Log with null object";

    /**
     * 文件路径管理中的基础路径
     */
    private static String APP_ROOT_PATH;

    private static final String FILE_TYPE = ".log";

    private static boolean WRITE_SDCARD_LOG = false;

    private static volatile File logfile;

    private static volatile File httpLogFile;

    private static final long ERR_LOG_SIZE = 1024 * 1024 * 4;

    public static final int V = 0x1;

    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    private static long startTimestamp;

    public static final String DEBUG = "debug";
    public static final String INFO = "info";
    public static final String WARN = "warn";
    public static final String ERROR = "error";
    public static final String ASSERT = "assert";

    private static String appInfo = null;

    //单线程线程池
    private static ExecutorService logExecutorService;

    private static String mLogFileTime = "";//sd卡 写日志的名称

    public static void setLofFileTime(String logFileTime) {
        mLogFileTime = logFileTime;
    }

    private static ConcurrentLinkedQueue<WriteLogBean> linkedQueue = new ConcurrentLinkedQueue<>();

    private SuperLog() {
    }

    public static void setEnable(boolean isEnableLog, boolean isWrite2SD) {
        needDebug = isEnableLog;
        WRITE_SDCARD_LOG = isWrite2SD;

        if (logExecutorService == null) {
            logExecutorService = Executors.newSingleThreadExecutor();
        }
    }

    public static void setWriteSdcardLog(boolean writeSdcardLog) {
        WRITE_SDCARD_LOG = writeSdcardLog;
    }

    private static boolean needDebug = true;

    public static boolean enabled() {
        return needDebug;
    }

    public static String getLogPath() {
        return LOG_PATH;
    }


    public static String getMailLogPath() {
        return MAIL_LOG_PATH;
    }

    private static synchronized String buildMsg(String msg, String level) {

        if (TextUtils.isEmpty(msg)) {
            return "log is null";
        }

        StringBuilder buffer = new StringBuilder();

//        final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        buffer.append("[" + level + "]" + "|");
        buffer.append("[");
//        buffer.append(Thread.currentThread().getName());
//        buffer.append(" |");
//        buffer.append(stackTraceElement.getFileName());
//        buffer.append(" |");
//        buffer.append(stackTraceElement.getMethodName());
//        buffer.append("() |Line:");
//        buffer.append(stackTraceElement.getLineNumber());
//        buffer.append("] |");
        buffer.append(msg);
        buffer.append(NEW_LINE);
        return buffer.toString();
    }

    public static void verbose(String tag, String msg) {
        if (enabled()) {
            Log.v(TAG + tag, msg);
        }
    }

    private static String getTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ms");
        return format.format(date);

    }

    public static void debug(String tag, String msg) {
        if (enabled()) {
            printDefault(D, TAG + tag, buildMsg(msg, DEBUG));
            if (WRITE_SDCARD_LOG) {

                writeFileToSD(getTime() + buildMsg(msg, DEBUG));
            }
        }
    }

    public static void info(String tag, String msg) {
        if (enabled()) {
            printDefault(I, TAG + tag, buildMsg(msg, INFO));
        }
    }

    public static void info2SD(String tag, String msg) {

        printDefault(I, TAG + tag, buildMsg(msg, INFO));
        writeFileToSD(getTime() + buildMsg(msg, INFO));

    }

    public static void warn(String tag, String msg) {
        if (enabled()) {
            String warn = buildMsg(msg, WARN);
            printDefault(W, TAG + tag, warn);
            if (WRITE_SDCARD_LOG) {
                writeFileToSD(getTime() + warn);
            }
        }
    }

    public static void error(String tag, String msg) {
        String error = buildMsg(msg, ERROR);
        printDefault(E, TAG + tag, error);
        writeFileToSD(getTime() + error);
    }

    public static void error(String tag, Throwable e) {
        printDefault(E, TAG + tag, getStackString(e));
        writeFileToSD(getStackString(e));
    }

    public static void jsonPrint(String tag, String msg) {
        if (WRITE_SDCARD_LOG) {
            JsonLog.printJson(TAG + tag, msg, "This json log");
            if (WRITE_SDCARD_LOG) {
                writeFileToSD(getTime() + buildMsg(msg, "json"));
            }
        }
    }

    public static void xmlPrint(String tag, String msg) {
        if (WRITE_SDCARD_LOG) {

            XmlLog.printXml(TAG + tag, msg, "This xml log");

            if (WRITE_SDCARD_LOG) {
                writeFileToSD(getTime() + buildMsg(msg, "xml"));
            }
        }
    }

    private static String printStackTraceAsCause(StackTraceElement[] causedTrace, Throwable e) {
        StackTraceElement[] trace = e.getStackTrace();
        int m = trace.length - 1, n = causedTrace.length - 1;
        while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n])) {
            m--;
            n--;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(e.toString());
        builder.append(NEW_LINE);
        for (int i = 0; i <= m; i++) {
            builder.append(trace[i].toString());
            builder.append(NEW_LINE);
        }
        String ret = builder.toString();

        Throwable ourCause = e.getCause();
        if (ourCause != null) {
            String temp = printStackTraceAsCause(trace, ourCause);
            ret = temp + ret;
        }
        return ret;
    }

    public static String getStackString(Throwable e) {
        StackTraceElement[] trace = e.getStackTrace();
        StringBuilder builder = new StringBuilder();
        builder.append(e.toString());
        builder.append(NEW_LINE);
        for (StackTraceElement temp : trace) {
            builder.append(temp.toString());
            builder.append(NEW_LINE);
        }

        String ret = builder.toString();
        Throwable ourCause = e.getCause();
        if (ourCause != null) {
            String child = printStackTraceAsCause(trace, ourCause);
            ret = child + ret;
        }
        return ret;

    }

    public static void addLog(String value, int endLines) {
        if (!isSdcardCanWrite()) {
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ms");
        String timeString = format.format(new Date(System.currentTimeMillis()));
        StringBuilder builder = new StringBuilder(timeString);
        builder.append(" ");
        builder.append(value);
        builder.append(NEW_LINE);
        for (int iLoop = 0; iLoop < endLines; iLoop++) {
            builder.append(NEW_LINE);
        }

        linkedQueue.add(new WriteLogBean(builder.toString(), chooseFileName("_HeartBeat")));
        if (logExecutorService != null) {
            AychWriter aychWriter = new AychWriter("HeartBeat");
            logExecutorService.execute(aychWriter);
        }
    }

    private static void writeFileToSD(String context) {
        if (!isSdcardCanWrite()) {
            return;
        }

        linkedQueue.add(new WriteLogBean(context, chooseFileName("_APP_LOG")));
        if (logExecutorService != null) {
            AychWriter aychWriter = new AychWriter("APP_LOG");
            logExecutorService.execute(aychWriter);
        }
    }


    /**
     * 异步写文件
     */
    private static class AychWriter extends Thread {

        public AychWriter(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (linkedQueue != null && !linkedQueue.isEmpty()) {
                WriteLogBean writeLogBean = linkedQueue.poll();
                if (writeLogBean != null) {
                    appendToFile(writeLogBean.getText(), writeLogBean.getFile());
                }
            }
        }
    }

    private static void appendToFile(String text, File file) {
        if (file == null) {
            return;
        }
        if (makeDirs(LOG_PATH)) {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file.getAbsolutePath(), "rw");
                raf.seek(file.length());
                if (file.length() == 0L) {
                    String hearderInfo = getHearderInfo();
                    text = hearderInfo + text;
                }

                raf.write(text.getBytes());
            } catch (Exception e) {
                printExecption(e);
            } finally {
                if (null != raf) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        printExecption(e);
                    }
                }
            }
        }
    }

    private static void printExecption(Exception exception) {
        String ss = getStackString(exception);
        Log.e(TAG, ss);
    }

    private static boolean makeDirs(String path) {
        boolean isComplete = true;
        if (!isFileExists(path)) {
            File file = new File(path);
            isComplete = file.mkdirs();
        }
        return isComplete;
    }

    private static String getHearderInfo() {

        // 拼装文件内容
        StringBuilder builder = new StringBuilder();
        builder.append(getAppInfo());
        builder.append(NEW_LINE);
        builder.append(getTelModel());
        builder.append(NEW_LINE);
        builder.append("SDKVerion:" + Build.VERSION.SDK_INT);
        builder.append(NEW_LINE);

        return builder.toString();
    }

    private static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return new File(path).exists();
    }

    private static boolean isSdcardCanWrite() {
        boolean ret = true;
        String sdStatus = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(sdStatus)) {
            Log.d(TAG, "SD card is not avaiable right now.");
            ret = false;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(sdStatus)) {
            Log.d(TAG, "Not allow write SD card!");
            ret = false;
        }
        return ret;
    }

    private static File chooseFileName(String type) {

        if (TextUtils.isEmpty(mLogFileTime)) {
            mLogFileTime = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date());
        }
        String fileName = mLogFileTime +
                type + FILE_TYPE;
        if (logfile == null) {
            deleteUnusedFile(type + FILE_TYPE);
            logfile = new File(LOG_PATH + fileName);
        } else if (!logfile.exists()) {
            logfile = new File(LOG_PATH + fileName);
        } else {
            if (logfile.length() > ERR_LOG_SIZE) {
                logfile = new File(LOG_PATH + fileName);
            }
        }

        return logfile;
    }

    private static void deleteUnusedFile(String simpleName) {
        List<String> rawList = new ArrayList<String>();
        File file = new File(LOG_PATH);
        String[] files = file.list();
        if (files != null) {
            rawList = Arrays.asList(files);
        }

        List<String> names = new ArrayList<String>();
        for (String fileTemp : rawList) {
            if (fileTemp.matches("[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}.*" + simpleName)) {
                names.add(fileTemp);
            }
        }
        if (names.size() > 10) {
            Comparator<String> comparator = new Comparator<String>() {
                @Override
                public int compare(String arg1, String arg2) { //逆排序
                    String str1 = arg1.substring(0, 13);
                    String str2 = arg2.substring(0, 13);
                    return str2.compareTo(str1);
                }

            };
            Collections.sort(names, comparator);

            for (int i = 0; i < names.size(); i++) {
                if (i >= 10) {
                    File deletedFile = new File(LOG_PATH + names.get(i));
                    if (!deletedFile.delete()) {
                        SuperLog.error(TAG, "[deleteUnusedFile] , deletedFile delete failed");
                    }
                }
            }
        }
    }

    private static void printDefault(int type, String tag, String msg) {

        int index = 0;
        int maxLength = 3800;
        int countOfSub = msg.length() / maxLength;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                printSub(type, tag, sub);
                index += maxLength;
            }
            printSub(type, tag, msg.substring(index, msg.length()));
        } else {
            printSub(type, tag, msg);
        }
    }

    /**
     * 是否超过log的限制
     *
     * @param msg
     * @return
     */
    private static boolean isTooMore(String msg) {
        int maxLength = 4000;
        int countOfSub = msg.length() / maxLength;

        if (countOfSub > 0) {
            return true;
        } else {
            return false;
        }
    }

    private static void printSub(int type, String tag, String sub) {
        switch (type) {
            case V:
                Log.v(tag, sub);
                break;
            case D:
                Log.d(tag, sub);
                break;
            case I:
                Log.i(tag, sub);
                break;
            case W:
                Log.w(tag, sub);
                break;
            case E:
                Log.e(tag, sub);
                break;
            case A:
                Log.wtf(tag, sub);
                break;
        }
    }

    private static String getAppInfo() {

        if (appInfo != null && !appInfo.equals("")) {
            return appInfo;
        }

        try {
            String pkName = MyApplication.getInstance().getPackageName();
            String versionName = MyApplication.getInstance().getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = MyApplication.getInstance().getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            appInfo = "versionName:" + versionName + NEW_LINE + "versionCode:" + versionCode;
            return appInfo;
        } catch (Exception e) {
        }
        return "";
    }

    private static String getTelModel() {
        return Build.MODEL;
    }

    /**
     * 记录时间戳。
     */
    public static void timestamp() {
        startTimestamp = System.currentTimeMillis();
    }

    /**
     * 打印时间戳
     *
     * @param tag <p>You should call {@link #timestamp()} method before call this method</p>
     */
    public static void debugInterval(String tag) {
        if (startTimestamp == -1) {
            throw new IllegalStateException("startTimestamp is -1,you should call timestamp() method first");
        }
        long cur = System.currentTimeMillis();
        debug(tag, "start:" + startTimestamp + ",end:" + cur + ",consume:" + (cur - startTimestamp));
        startTimestamp = -1;
    }
}

package com.example.myapplication.haikang.LogUtil;

import java.io.File;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.fmcc.video.common.utils.LogUtil.WriteLogBean.java
 * @author: yh
 * @date: 2018-02-26 11:53
 */

public class WriteLogBean {

    //需要打印的日志
    private String text;
    //打印到哪个文件
    private File file;

    public WriteLogBean(String text, File file) {
        this.text = text;
        this.file = file;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

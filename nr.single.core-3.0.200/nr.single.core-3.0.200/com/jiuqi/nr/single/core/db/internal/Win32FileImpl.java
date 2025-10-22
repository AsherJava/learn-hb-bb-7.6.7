/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.db.internal;

import com.jiuqi.nr.single.core.db.DbConsts;
import com.jiuqi.nr.single.core.db.Win32File;
import java.io.RandomAccessFile;

public class Win32FileImpl
implements Win32File {
    protected String fileName;
    protected RandomAccessFile fileHandle;
    protected int fileAttributes;

    protected void fileReadError() throws Exception {
        DbConsts.lastWin32CallCheck(String.format("\u8bfb\u6587\u4ef6 %s \u9519\u8bef", this.fileName));
    }

    protected void fileWriteError() throws Exception {
        DbConsts.lastWin32CallCheck(String.format("\u5199\u6587\u4ef6 %s \u9519\u8bef", this.fileName));
    }

    protected void fileSeekError() throws Exception {
        DbConsts.lastWin32CallCheck(String.format("\u79fb\u52a8\u6587\u4ef6 %s \u6307\u9488\u9519\u8bef", this.fileName));
    }

    protected void fheckFileOpened() throws Exception {
        DbConsts.lastWin32CallCheck(String.format("\u6587\u4ef6 %s \u6ca1\u6709\u6253\u5f00", this.fileName));
    }

    protected void checkFileWritable() throws Exception {
        DbConsts.lastWin32CallCheck(String.format("\u6587\u4ef6 %s \u4ee5\u53ea\u8bfb\u65b9\u5f0f\u6253\u5f00\uff0c\u4e0d\u80fd\u5199", this.fileName));
    }

    protected void beforeOpen() {
    }

    protected void afterOpen() {
    }

    protected void beforeClose() {
    }

    protected void afterClose() {
    }

    protected RandomAccessFile getFileHandle() {
        return this.fileHandle;
    }

    @Override
    public void assignFileProp(Win32File source) {
        if (source != null) {
            // empty if block
        }
    }

    @Override
    public void openFile(String fileName, Boolean readOnly, Boolean exclusive) {
    }
}


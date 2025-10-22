/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.nr.file.FileInfo;

public class FileData {
    private FileInfo fileInfo;
    private byte[] data;

    public FileInfo getFileInfo() {
        return this.fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}


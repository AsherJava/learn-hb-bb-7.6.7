/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.nr.dataentry.attachment.message.FileCountInfo;
import com.jiuqi.nr.dataentry.attachment.message.GridDataInfo;

public class FileDetails {
    private FileCountInfo fileCountInfo;
    private GridDataInfo gridData;

    public FileCountInfo getFileCountInfo() {
        return this.fileCountInfo;
    }

    public void setFileCountInfo(FileCountInfo fileCountInfo) {
        this.fileCountInfo = fileCountInfo;
    }

    public GridDataInfo getGridData() {
        return this.gridData;
    }

    public void setGridData(GridDataInfo gridData) {
        this.gridData = gridData;
    }
}


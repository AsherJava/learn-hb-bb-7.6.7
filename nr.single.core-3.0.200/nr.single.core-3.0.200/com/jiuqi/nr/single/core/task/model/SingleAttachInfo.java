/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.model;

import com.jiuqi.nr.single.core.task.model.SingleAttachFileInfo;
import java.util.ArrayList;
import java.util.List;

public class SingleAttachInfo {
    private String name;
    private String groupKey;
    private String zipFilePath;
    private String iniFilePath;
    private String unzipTempPath;
    private List<SingleAttachFileInfo> files;

    public List<SingleAttachFileInfo> getFiles() {
        if (this.files == null) {
            this.files = new ArrayList<SingleAttachFileInfo>();
        }
        return this.files;
    }

    public void setFiles(List<SingleAttachFileInfo> files) {
        this.files = files;
    }

    public String getZipFilePath() {
        return this.zipFilePath;
    }

    public void setZipFilePath(String zipFilePath) {
        this.zipFilePath = zipFilePath;
    }

    public String getIniFilePath() {
        return this.iniFilePath;
    }

    public void setIniFilePath(String iniFilePath) {
        this.iniFilePath = iniFilePath;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnzipTempPath() {
        return this.unzipTempPath;
    }

    public void setUnzipTempPath(String unzipTempPath) {
        this.unzipTempPath = unzipTempPath;
    }
}


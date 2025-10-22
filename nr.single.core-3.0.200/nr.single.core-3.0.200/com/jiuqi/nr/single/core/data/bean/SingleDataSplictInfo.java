/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.data.bean;

import java.io.Serializable;
import java.util.List;

public class SingleDataSplictInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int splictType = 0;
    private List<String> periodList;
    private List<String> unitList;
    private int unitSize;
    private boolean splictAttachment;
    private String soureFilePath;
    private String destParamFile;
    private String destDataFile;
    private String workPath;

    public int getSplictType() {
        return this.splictType;
    }

    public void setSplictType(int splictType) {
        this.splictType = splictType;
    }

    public List<String> getPeriodList() {
        return this.periodList;
    }

    public void setPeriodList(List<String> periodList) {
        this.periodList = periodList;
    }

    public List<String> getUnitList() {
        return this.unitList;
    }

    public void setUnitList(List<String> unitList) {
        this.unitList = unitList;
    }

    public int getUnitSize() {
        return this.unitSize;
    }

    public void setUnitSize(int unitSize) {
        this.unitSize = unitSize;
    }

    public String getSoureFilePath() {
        return this.soureFilePath;
    }

    public void setSoureFilePath(String soureFilePath) {
        this.soureFilePath = soureFilePath;
    }

    public String getDestParamFile() {
        return this.destParamFile;
    }

    public void setDestParamFile(String destParamFile) {
        this.destParamFile = destParamFile;
    }

    public String getDestDataFile() {
        return this.destDataFile;
    }

    public void setDestDataFile(String destDataFile) {
        this.destDataFile = destDataFile;
    }

    public String getWorkPath() {
        return this.workPath;
    }

    public void setWorkPath(String workPath) {
        this.workPath = workPath;
    }

    public boolean isSplictAttachment() {
        return this.splictAttachment;
    }

    public void setSplictAttachment(boolean splictAttachment) {
        this.splictAttachment = splictAttachment;
    }
}


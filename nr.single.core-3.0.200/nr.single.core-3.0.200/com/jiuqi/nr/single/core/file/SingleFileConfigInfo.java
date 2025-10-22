/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.file;

import com.jiuqi.nr.single.core.common.InOutDataType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SingleFileConfigInfo
implements Serializable {
    private static final long serialVersionUID = -6777784226880572826L;
    private String taskName;
    private String taskFlag;
    private String fileFlag;
    private String taskYear;
    private String taskPeriod;
    private String taskTime;
    private String taskVersion;
    private String taskGroup;
    private boolean inputClien;
    private String NetPeriodT;
    private String dataSystem;
    private Set<InOutDataType> inOutData;
    private String dataSource;
    private String fileVersion;
    private String subDataDir;
    private List<String> subDataDirs;

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskFlag() {
        return this.taskFlag;
    }

    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag;
    }

    public String getFileFlag() {
        return this.fileFlag;
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag;
    }

    public String getTaskYear() {
        return this.taskYear;
    }

    public void setTaskYear(String taskYear) {
        this.taskYear = taskYear;
    }

    public String getTaskPeriod() {
        return this.taskPeriod;
    }

    public void setTaskPeriod(String taskPeriod) {
        this.taskPeriod = taskPeriod;
    }

    public String getTaskTime() {
        return this.taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskVersion() {
        return this.taskVersion;
    }

    public void setTaskVersion(String taskVersion) {
        this.taskVersion = taskVersion;
    }

    public String getTaskGroup() {
        return this.taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public boolean isInputClien() {
        return this.inputClien;
    }

    public void setInputClien(boolean inputClien) {
        this.inputClien = inputClien;
    }

    public String getNetPeriodT() {
        return this.NetPeriodT;
    }

    public void setNetPeriodT(String netPeriodT) {
        this.NetPeriodT = netPeriodT;
    }

    public String getDataSystem() {
        return this.dataSystem;
    }

    public void setDataSystem(String dataSystem) {
        this.dataSystem = dataSystem;
    }

    public Set<InOutDataType> getInOutData() {
        if (this.inOutData == null) {
            this.inOutData = new HashSet<InOutDataType>();
        }
        return this.inOutData;
    }

    public void setInOutData(Set<InOutDataType> inOutData) {
        this.inOutData = inOutData;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getFileVersion() {
        return this.fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public List<String> getSubDataDirs() {
        if (this.subDataDirs == null) {
            this.subDataDirs = new ArrayList<String>();
        }
        return this.subDataDirs;
    }

    public void setSubDataDirs(List<String> subDataDirs) {
        this.subDataDirs = subDataDirs;
    }

    public String getSubDataDir() {
        return this.subDataDir;
    }

    public void setSubDataDir(String subDataDir) {
        this.subDataDir = subDataDir;
    }
}


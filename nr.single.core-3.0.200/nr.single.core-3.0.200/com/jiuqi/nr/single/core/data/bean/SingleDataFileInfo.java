/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.data.bean;

import com.jiuqi.nr.single.core.data.bean.SingleUnitInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SingleDataFileInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskFlag;
    private String fileFlag;
    private String taskTitle;
    private String taskYear;
    private String periodType;
    private List<String> periodCodes;
    private List<SingleUnitInfo> unitList;
    private boolean hasParam = false;
    private boolean hasData = false;

    public List<String> getPeriodCodes() {
        if (this.periodCodes == null) {
            this.periodCodes = new ArrayList<String>();
        }
        return this.periodCodes;
    }

    public void setPeriodCodes(List<String> periodCodes) {
        this.periodCodes = periodCodes;
    }

    public List<SingleUnitInfo> getUnitList() {
        if (this.unitList == null) {
            this.unitList = new ArrayList<SingleUnitInfo>();
        }
        return this.unitList;
    }

    public void setUnitList(List<SingleUnitInfo> unitList) {
        this.unitList = unitList;
    }

    public boolean isHasParam() {
        return this.hasParam;
    }

    public void setHasParam(boolean hasParam) {
        this.hasParam = hasParam;
    }

    public boolean isHasData() {
        return this.hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
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

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskYear() {
        return this.taskYear;
    }

    public void setTaskYear(String taskYear) {
        this.taskYear = taskYear;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}


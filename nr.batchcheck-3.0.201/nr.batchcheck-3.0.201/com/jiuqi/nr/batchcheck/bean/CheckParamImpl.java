/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 */
package com.jiuqi.nr.batchcheck.bean;

import com.jiuqi.nr.batchcheck.bean.DimUnitInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import java.io.Serializable;
import java.time.Instant;

public class CheckParamImpl
implements Serializable {
    private static final long serialVersionUID = -3114706727666214471L;
    private String taskName;
    private String formSchemeTitle;
    private String formulaSchemeTitle;
    private String asyncTaskId;
    private BatchCheckInfo checkInfo;
    private DimUnitInfo[] entityNames;
    private String totalUnit;
    private Instant currentTime;
    private String periodName;

    public String getTotalUnit() {
        return this.totalUnit;
    }

    public void setTotalUnit(String totalUnit) {
        this.totalUnit = totalUnit;
    }

    public DimUnitInfo[] getEntityNames() {
        return this.entityNames;
    }

    public void setEntityNames(DimUnitInfo[] entityNames) {
        this.entityNames = entityNames;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formTitle) {
        this.formSchemeTitle = formTitle;
    }

    public String getFormulaSchemeTitle() {
        return this.formulaSchemeTitle;
    }

    public void setFormulaSchemeTitle(String formulaSchemeTitle) {
        this.formulaSchemeTitle = formulaSchemeTitle;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public BatchCheckInfo getCheckInfo() {
        return this.checkInfo;
    }

    public void setCheckInfo(BatchCheckInfo checkInfo) {
        this.checkInfo = checkInfo;
    }

    public Instant getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(Instant currentTime) {
        this.currentTime = currentTime;
    }

    public String getPeriodName() {
        return this.periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }
}


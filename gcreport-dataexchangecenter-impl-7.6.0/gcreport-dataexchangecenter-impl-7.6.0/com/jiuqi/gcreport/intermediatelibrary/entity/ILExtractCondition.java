/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ILExtractCondition {
    private Set<String> libraryMessages = new CopyOnWriteArraySet<String>();
    private String programmeId;
    private List<String> orgIdList;
    private List<String> formIdList;
    private String isAllOrgChoose;
    private String isAllFormChoose;
    private String orgVersionType;
    private String orgTypeTitle;
    private DataEntryContext envContext;
    private String sn;
    private double currentProgress;
    private double stepProgress;
    private AsyncTaskMonitor asyncTaskMonitor;
    private String taskPoolType;
    private boolean pushType;
    private Map<String, Object> extInfo;
    private Map<String, DimensionValue> dimensionSet;

    public boolean getPushType() {
        return this.pushType;
    }

    public void setPushType(boolean pushType) {
        this.pushType = pushType;
    }

    public String getTaskPoolType() {
        return this.taskPoolType;
    }

    public void setTaskPoolType(String taskPoolType) {
        this.taskPoolType = taskPoolType;
    }

    public String getIsAllFormChoose() {
        return this.isAllFormChoose;
    }

    public void setIsAllFormChoose(String isAllFormChoose) {
        this.isAllFormChoose = isAllFormChoose;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getCurrentProgress() {
        return this.currentProgress;
    }

    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = currentProgress;
    }

    public double getStepProgress() {
        return this.stepProgress;
    }

    public void setStepProgress(double stepProgress) {
        this.stepProgress = stepProgress;
    }

    public Set<String> getLibraryMessages() {
        return this.libraryMessages;
    }

    public void setLibraryMessages(Set<String> libraryMessages) {
        this.libraryMessages = libraryMessages;
    }

    public DataEntryContext getEnvContext() {
        return this.envContext;
    }

    public void setEnvContext(DataEntryContext envContext) {
        this.envContext = envContext;
    }

    public String getIsAllOrgChoose() {
        return this.isAllOrgChoose;
    }

    public void setIsAllOrgChoose(String isAllOrgChoose) {
        this.isAllOrgChoose = isAllOrgChoose;
    }

    public List<String> getOrgIdList() {
        return this.orgIdList;
    }

    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }

    public List<String> getFormIdList() {
        return this.formIdList;
    }

    public void setFormIdList(List<String> formIdList) {
        this.formIdList = formIdList;
    }

    public String getOrgVersionType() {
        return this.orgVersionType;
    }

    public void setOrgVersionType(String orgVersionType) {
        this.orgVersionType = orgVersionType;
    }

    public String getOrgTypeTitle() {
        return this.orgTypeTitle;
    }

    public void setOrgTypeTitle(String orgTypeTitle) {
        this.orgTypeTitle = orgTypeTitle;
    }

    public String getProgrammeId() {
        return this.programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public Map<String, Object> getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }
}


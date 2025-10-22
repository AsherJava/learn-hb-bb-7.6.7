/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;

public class BatchWorkFlowInfo {
    private Map<String, DimensionValue> dimensionValue;
    private boolean forceCommit;
    private boolean needOptDesc = false;
    private List<Integer> ignoreErrorStatus;
    private List<Integer> needCommentErrorStatus;
    private String checkFilter;
    private String taskId;
    private String formKey;
    private String formGroupKey;

    public Map<String, DimensionValue> getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(Map<String, DimensionValue> dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public boolean isForceCommit() {
        return this.forceCommit;
    }

    public void setForceCommit(boolean forceCommit) {
        this.forceCommit = forceCommit;
    }

    public boolean isNeedOptDesc() {
        return this.needOptDesc;
    }

    public void setNeedOptDesc(boolean needOptDesc) {
        this.needOptDesc = needOptDesc;
    }

    public List<Integer> getIgnoreErrorStatus() {
        return this.ignoreErrorStatus;
    }

    public void setIgnoreErrorStatus(List<Integer> ignoreErrorStatus) {
        this.ignoreErrorStatus = ignoreErrorStatus;
    }

    public List<Integer> getNeedCommentErrorStatus() {
        return this.needCommentErrorStatus;
    }

    public void setNeedCommentErrorStatus(List<Integer> needCommentErrorStatus) {
        this.needCommentErrorStatus = needCommentErrorStatus;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getCheckFilter() {
        return this.checkFilter;
    }

    public void setCheckFilter(String checkFilter) {
        this.checkFilter = checkFilter;
    }
}


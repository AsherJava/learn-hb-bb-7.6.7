/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.bean;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import java.io.Serializable;
import java.util.Map;

public class SubProcessBean
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String formSchemeKey;
    private WorkflowStatus flowType;
    private Map<BusinessKey, String> businessKeyMap;
    private boolean bindFlag;
    private boolean selectAll;
    private boolean selectReportAll;
    private String adjust;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public WorkflowStatus getFlowType() {
        return this.flowType;
    }

    public void setFlowType(WorkflowStatus flowType) {
        this.flowType = flowType;
    }

    public Map<BusinessKey, String> getBusinessKeyMap() {
        return this.businessKeyMap;
    }

    public void setBusinessKeyMap(Map<BusinessKey, String> businessKeyMap) {
        this.businessKeyMap = businessKeyMap;
    }

    public boolean isBindFlag() {
        return this.bindFlag;
    }

    public void setBindFlag(boolean bindFlag) {
        this.bindFlag = bindFlag;
    }

    public boolean isSelectAll() {
        return this.selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public boolean isSelectReportAll() {
        return this.selectReportAll;
    }

    public void setSelectReportAll(boolean selectReportAll) {
        this.selectReportAll = selectReportAll;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.bpm.setting.pojo;

import com.jiuqi.nr.context.infc.impl.NRContext;
import java.io.Serializable;
import java.util.Set;

public class StateChangeObj
extends NRContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<String> dataObj;
    private String formSchemeId;
    private String period;
    private String settingId;
    private boolean isStart;
    private boolean selectAll;
    private boolean reportAll;
    private Set<String> reportList;
    private String adjust;

    public Set<String> getDataObj() {
        return this.dataObj;
    }

    public void setDataObj(Set<String> dataObj) {
        this.dataObj = dataObj;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public boolean isStart() {
        return this.isStart;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSettingId() {
        return this.settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public boolean isSelectAll() {
        return this.selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public boolean isReportAll() {
        return this.reportAll;
    }

    public void setReportAll(boolean reportAll) {
        this.reportAll = reportAll;
    }

    public Set<String> getReportList() {
        return this.reportList;
    }

    public void setReportList(Set<String> reportList) {
        this.reportList = reportList;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }
}


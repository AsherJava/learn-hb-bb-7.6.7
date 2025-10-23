/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.multcheck2.provider;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;

public class MultcheckContext
implements Serializable {
    private String checkSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private List<String> orgList;
    private DimensionCollection dims;
    private String org;

    public String getCheckSchemeKey() {
        return this.checkSchemeKey;
    }

    public void setCheckSchemeKey(String checkSchemeKey) {
        this.checkSchemeKey = checkSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getOrgList() {
        return this.orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }

    public DimensionCollection getDims() {
        return this.dims;
    }

    public void setDims(DimensionCollection dims) {
        this.dims = dims;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}


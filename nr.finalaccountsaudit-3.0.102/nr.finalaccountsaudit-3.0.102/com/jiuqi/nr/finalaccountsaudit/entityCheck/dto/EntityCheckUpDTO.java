/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.dto;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public class EntityCheckUpDTO {
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private List<String> scope;
    private String webTabName;
    private boolean isDetailed;
    private String associatedTaskKey;
    private String associatedFormSchemeKey;
    private String associatedPeriod;
    private JtableContext jtableContext;
    private String filterValue;

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

    public List<String> getScope() {
        return this.scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public String getWebTabName() {
        return this.webTabName;
    }

    public void setWebTabName(String webTabName) {
        this.webTabName = webTabName;
    }

    public boolean isDetailed() {
        return this.isDetailed;
    }

    public void setDetailed(boolean detailed) {
        this.isDetailed = detailed;
    }

    public String getAssociatedTaskKey() {
        return this.associatedTaskKey;
    }

    public void setAssociatedTaskKey(String associatedTaskKey) {
        this.associatedTaskKey = associatedTaskKey;
    }

    public String getAssociatedFormSchemeKey() {
        return this.associatedFormSchemeKey;
    }

    public void setAssociatedFormSchemeKey(String associatedFormSchemeKey) {
        this.associatedFormSchemeKey = associatedFormSchemeKey;
    }

    public String getAssociatedPeriod() {
        return this.associatedPeriod;
    }

    public void setAssociatedPeriod(String associatedPeriod) {
        this.associatedPeriod = associatedPeriod;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public String getFilterValue() {
        return this.filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
}


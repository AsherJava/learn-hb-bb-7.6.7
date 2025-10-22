/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public class EntityCheckInfo
extends JtableLog {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private String scop;
    private String WebTabName;
    private String isDetailed;
    private String associatedTaskKey;
    private String associatedFormSchemeKey;
    private String associatedperiod;
    private String checkScope;
    private JtableContext context;
    private String filterValue;

    public String getFilterValue() {
        return this.filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getCheckScope() {
        return this.checkScope;
    }

    public void setCheckScope(String checkScope) {
        this.checkScope = checkScope;
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

    public String getScop() {
        return this.scop;
    }

    public void setScop(String scop) {
        this.scop = scop;
    }

    public String getWebTabName() {
        return this.WebTabName;
    }

    public void setWebTabName(String webTabName) {
        this.WebTabName = webTabName;
    }

    public String getIsDetailed() {
        return this.isDetailed;
    }

    public void setIsDetailed(String isDetailed) {
        this.isDetailed = isDetailed;
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

    public String getAssociatedperiod() {
        return this.associatedperiod;
    }

    public void setAssociatedperiod(String associatedperiod) {
        this.associatedperiod = associatedperiod;
    }
}


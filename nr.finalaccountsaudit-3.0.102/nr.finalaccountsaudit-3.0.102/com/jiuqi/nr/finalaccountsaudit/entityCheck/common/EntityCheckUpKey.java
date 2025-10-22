/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpType;

public class EntityCheckUpKey {
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private String dwzdm;
    private EntityCheckUpType checkType;
    private String fmdmid;

    public String GetTaskKey() {
        return this.taskKey;
    }

    public void SetTaskKey(String _TaskKey) {
        this.taskKey = _TaskKey;
    }

    public String GetFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void SetFormSchemeKey(String _FormSchemeKey) {
        this.formSchemeKey = _FormSchemeKey;
    }

    public String GetPeriod() {
        return this.period;
    }

    public void SetPeriod(String _Period) {
        this.period = _Period;
    }

    public String GetDWZDM() {
        return this.dwzdm;
    }

    public void SetDWZDM(String _DWZDM) {
        this.dwzdm = _DWZDM;
    }

    public EntityCheckUpType GetCheckType() {
        return this.checkType;
    }

    public void SetCheckType(EntityCheckUpType _CheckType) {
        this.checkType = _CheckType;
    }

    public String GetFmdmId() {
        return this.fmdmid;
    }

    public void SetFmdmId(String _fmdmid) {
        this.fmdmid = _fmdmid;
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

    public String getDwzdm() {
        return this.dwzdm;
    }

    public void setDwzdm(String dwzdm) {
        this.dwzdm = dwzdm;
    }

    public EntityCheckUpType getCheckType() {
        return this.checkType;
    }

    public void setCheckType(EntityCheckUpType checkType) {
        this.checkType = checkType;
    }

    public String getFmdmid() {
        return this.fmdmid;
    }

    public void setFmdmid(String fmdmid) {
        this.fmdmid = fmdmid;
    }
}


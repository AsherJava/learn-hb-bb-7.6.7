/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.reject;

import java.util.List;
import java.util.Map;

public class RejectParamDTO {
    private String user;
    private List<String> unitIds;
    private String task;
    private String formSchemeKey;
    private String period;
    private String userName;
    private List<String> fromGroupKeys;
    private List<String> formKeys;
    private Map<String, List<String>> unitToflow;

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getFromGroupKeys() {
        return this.fromGroupKeys;
    }

    public void setFromGroupKeys(List<String> fromGroupKeys) {
        this.fromGroupKeys = fromGroupKeys;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public Map<String, List<String>> getUnitToflow() {
        return this.unitToflow;
    }

    public void setUnitToflow(Map<String, List<String>> unitToflow) {
        this.unitToflow = unitToflow;
    }
}


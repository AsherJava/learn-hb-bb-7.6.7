/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.nr.data.logic.facade.param.output.CheckRecordData;
import java.util.List;

public class CheckRecord {
    private String userKey;
    private String userName;
    private String actionName;
    private String actionID;
    private long checkTime;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private List<CheckRecordData> checkRecordData;

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }

    public List<CheckRecordData> getCheckRecordData() {
        return this.checkRecordData;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public void setCheckRecordData(List<CheckRecordData> checkRecordData) {
        this.checkRecordData = checkRecordData;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionID() {
        return this.actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }
}


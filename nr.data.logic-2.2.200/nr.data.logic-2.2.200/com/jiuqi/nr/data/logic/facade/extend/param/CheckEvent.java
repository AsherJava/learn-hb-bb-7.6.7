/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;

public class CheckEvent {
    private String formSchemeKey;
    private ActionEnum actionType;
    private String actionId;
    private CheckParam checkParam;
    private String checkResultTableName;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public ActionEnum getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionEnum actionType) {
        this.actionType = actionType;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public CheckParam getCheckParam() {
        return this.checkParam;
    }

    public void setCheckParam(CheckParam checkParam) {
        this.checkParam = checkParam;
    }

    public String getCheckResultTableName() {
        return this.checkResultTableName;
    }

    public void setCheckResultTableName(String checkResultTableName) {
        this.checkResultTableName = checkResultTableName;
    }
}


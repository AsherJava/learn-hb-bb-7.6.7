/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;

public class GetTableContext {
    private String batchId;
    private String originalTableName;
    private String formSchemeKey;
    private ActionEnum actionEnum = ActionEnum.BATCH_CHECK;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getOriginalTableName() {
        return this.originalTableName;
    }

    public void setOriginalTableName(String originalTableName) {
        this.originalTableName = originalTableName;
    }

    public ActionEnum getActionEnum() {
        return this.actionEnum;
    }

    public void setActionEnum(ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }
}


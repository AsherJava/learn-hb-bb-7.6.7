/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.data.logic.facade.extend.ICKDQueryCKRFilter;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;

public class BatchSaveCheckDesParam {
    private ActionEnum actionEnum;
    private String actionId;
    private CheckResultQueryParam checkResultQueryParam;
    private String description;
    private ICKDQueryCKRFilter filter;
    private boolean coverOriginalDes;

    public ICKDQueryCKRFilter getFilter() {
        return this.filter;
    }

    public void setFilter(ICKDQueryCKRFilter filter) {
        this.filter = filter;
    }

    public ActionEnum getActionEnum() {
        return this.actionEnum;
    }

    public void setActionEnum(ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    public CheckResultQueryParam getCheckResultQueryParam() {
        return this.checkResultQueryParam;
    }

    public void setCheckResultQueryParam(CheckResultQueryParam checkResultQueryParam) {
        this.checkResultQueryParam = checkResultQueryParam;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public boolean isCoverOriginalDes() {
        return this.coverOriginalDes;
    }

    public void setCoverOriginalDes(boolean coverOriginalDes) {
        this.coverOriginalDes = coverOriginalDes;
    }
}


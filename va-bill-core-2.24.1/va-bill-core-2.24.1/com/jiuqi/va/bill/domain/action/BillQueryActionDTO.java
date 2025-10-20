/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.ActionCategory
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain.action;

import com.jiuqi.va.biz.intf.ActionCategory;
import com.jiuqi.va.mapper.domain.TenantDO;

public class BillQueryActionDTO
extends TenantDO {
    private String modelName;
    private ActionCategory actionCategory;

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public ActionCategory getActionCategory() {
        return this.actionCategory;
    }

    public void setActionCategory(ActionCategory actionCategory) {
        this.actionCategory = actionCategory;
    }
}


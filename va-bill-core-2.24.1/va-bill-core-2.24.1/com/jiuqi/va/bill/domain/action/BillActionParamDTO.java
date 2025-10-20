/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain.action;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Map;

public class BillActionParamDTO
extends TenantDO {
    private String billDefineCode;
    private String billCode;
    private String actionName;
    private String actionType;
    private Map<String, Object> actionParams;

    public String getBillDefineCode() {
        return this.billDefineCode;
    }

    public void setBillDefineCode(String billDefineCode) {
        this.billDefineCode = billDefineCode;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Map<String, Object> getActionParams() {
        return this.actionParams;
    }

    public void setActionParams(Map<String, Object> actionParams) {
        this.actionParams = actionParams;
    }

    public String toString() {
        return "BillActionParamDTO{billDefineCode='" + this.billDefineCode + '\'' + ", billCode='" + this.billCode + '\'' + ", actionName='" + this.actionName + '\'' + ", actionType='" + this.actionType + '\'' + ", actionParams=" + this.actionParams + '}';
    }
}


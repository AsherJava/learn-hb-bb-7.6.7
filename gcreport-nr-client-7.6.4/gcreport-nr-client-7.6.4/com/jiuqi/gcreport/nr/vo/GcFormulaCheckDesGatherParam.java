/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.nr.vo;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.List;

public class GcFormulaCheckDesGatherParam
implements Serializable {
    private String targetUnitCode;
    private List<String> orgIds;
    private JtableContext jtableContext;
    private List<String> formIds;
    private boolean allChildOrgFlag;

    public String getTargetUnitCode() {
        return this.targetUnitCode;
    }

    public void setTargetUnitCode(String targetUnitCode) {
        this.targetUnitCode = targetUnitCode;
    }

    public List<String> getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public List<String> getFormIds() {
        return this.formIds;
    }

    public void setFormIds(List<String> formIds) {
        this.formIds = formIds;
    }

    public boolean isAllChildOrgFlag() {
        return this.allChildOrgFlag;
    }

    public void setAllChildOrgFlag(boolean allChildOrgFlag) {
        this.allChildOrgFlag = allChildOrgFlag;
    }
}


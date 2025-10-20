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

public class GcFormulaCheckDesCopyInfoParam
implements Serializable {
    private List<String> orgIds;
    private List<String> currncyCodes;
    private JtableContext jtableContext;

    public List<String> getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public List<String> getCurrncyCodes() {
        return this.currncyCodes;
    }

    public void setCurrncyCodes(List<String> currncyCodes) {
        this.currncyCodes = currncyCodes;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }
}


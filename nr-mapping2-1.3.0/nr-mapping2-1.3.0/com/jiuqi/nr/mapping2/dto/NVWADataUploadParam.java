/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.dto;

import com.jiuqi.nr.mapping2.util.NvMappingInsertRule;
import com.jiuqi.nr.mapping2.util.NvMappingMatchRule;

public class NVWADataUploadParam {
    private NvMappingMatchRule matchRule;
    private NvMappingInsertRule insertRule;
    private boolean org;
    private boolean basedata;
    private boolean zb;
    private boolean period;
    private boolean formula;

    public NvMappingMatchRule getMatchRule() {
        return this.matchRule;
    }

    public void setMatchRule(NvMappingMatchRule matchRule) {
        this.matchRule = matchRule;
    }

    public NvMappingInsertRule getInsertRule() {
        return this.insertRule;
    }

    public void setInsertRule(NvMappingInsertRule insertRule) {
        this.insertRule = insertRule;
    }

    public boolean isOrg() {
        return this.org;
    }

    public void setOrg(boolean org) {
        this.org = org;
    }

    public boolean isBasedata() {
        return this.basedata;
    }

    public void setBasedata(boolean basedata) {
        this.basedata = basedata;
    }

    public boolean isZb() {
        return this.zb;
    }

    public void setZb(boolean zb) {
        this.zb = zb;
    }

    public boolean isPeriod() {
        return this.period;
    }

    public void setPeriod(boolean period) {
        this.period = period;
    }

    public boolean isFormula() {
        return this.formula;
    }

    public void setFormula(boolean formula) {
        this.formula = formula;
    }
}


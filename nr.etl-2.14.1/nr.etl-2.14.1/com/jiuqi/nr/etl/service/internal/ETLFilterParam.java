/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.service.internal;

import java.util.Set;

public class ETLFilterParam {
    private String period;
    private Set<String> unitid;
    private Set<String> unidCode;
    private Set<String> formCode;
    private Set<String> formKeySet;

    public ETLFilterParam() {
    }

    public ETLFilterParam(String period) {
        this.period = period;
    }

    public ETLFilterParam(String period, Set<String> unitid, Set<String> unidCode, Set<String> formCode, Set<String> formKeySet) {
        this.period = period;
        this.unitid = unitid;
        this.unidCode = unidCode;
        this.formCode = formCode;
        this.formKeySet = formKeySet;
    }

    public void setUnitid(Set<String> unitid) {
        this.unitid = unitid;
    }

    public void setUnidCode(Set<String> unidCode) {
        this.unidCode = unidCode;
    }

    public void setFormCode(Set<String> formCode) {
        this.formCode = formCode;
    }

    public void setFormKeySet(Set<String> formKeySet) {
        this.formKeySet = formKeySet;
    }

    public String getPeriod() {
        return this.period;
    }

    public Set<String> getUnitid() {
        return this.unitid;
    }

    public String getUnitidByString() {
        return String.join((CharSequence)",", this.unitid);
    }

    public Set<String> getUnidCode() {
        return this.unidCode;
    }

    public String getUnidCodeByString() {
        return String.join((CharSequence)",", this.unidCode);
    }

    public Set<String> getFormCode() {
        return this.formCode;
    }

    public String getFormCodeByString() {
        return String.join((CharSequence)",", this.formCode);
    }

    public Set<String> getFormKeySet() {
        return this.formKeySet;
    }

    public String getFormKeySetByString() {
        return String.join((CharSequence)",", this.formKeySet);
    }
}


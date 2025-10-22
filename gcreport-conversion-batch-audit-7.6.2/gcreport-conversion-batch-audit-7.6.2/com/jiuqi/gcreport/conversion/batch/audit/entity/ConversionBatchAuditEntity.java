/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 */
package com.jiuqi.gcreport.conversion.batch.audit.entity;

import com.jiuqi.gcreport.common.task.vo.Scheme;

public class ConversionBatchAuditEntity {
    private Scheme scheme;
    private String periodStr;

    public Scheme getScheme() {
        return this.scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }
}


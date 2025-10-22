/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.SchemaPeriodInfo
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.SchemaPeriodInfo;
import java.io.Serializable;

public class SchemaPeriodDTO
implements SchemaPeriodInfo,
Serializable {
    private static final long serialVersionUID = 1L;
    private String startPeriod;
    private String endPeriod;

    public String getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }
}


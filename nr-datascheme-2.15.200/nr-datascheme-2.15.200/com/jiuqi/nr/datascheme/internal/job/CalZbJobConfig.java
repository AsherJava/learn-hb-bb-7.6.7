/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.nr.datascheme.internal.job;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CalZbJobConfig {
    private String dataSchemeKey;
    private ExecuteType executeType;
    private PeriodType periodType;
    private String period;

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public void setExecuteType(ExecuteType executeType) {
        this.executeType = executeType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public ExecuteType getExecuteType() {
        return this.executeType;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public String getPeriod() {
        return this.period;
    }

    public String toString() {
        return new ToStringBuilder((Object)this, ToStringStyle.JSON_STYLE).append("dataSchemeKey", (Object)this.dataSchemeKey).append("executeType", (Object)this.executeType).append("periodType", (Object)this.periodType).append("period", (Object)this.period).toString();
    }

    static enum PeriodType {
        CURRENT(0),
        LAST_TWO_ISSUE(1),
        LAST_ISSUE(2),
        SPECIFIC(3);

        final int value;

        private PeriodType(int value) {
            this.value = value;
        }
    }

    static enum ExecuteType {
        ALL,
        RANGE;

    }
}


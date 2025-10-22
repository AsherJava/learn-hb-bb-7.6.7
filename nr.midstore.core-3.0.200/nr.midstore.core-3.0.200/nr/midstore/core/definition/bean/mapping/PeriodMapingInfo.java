/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.mapping.bean.PeriodMapping
 */
package nr.midstore.core.definition.bean.mapping;

import com.jiuqi.nr.mapping.bean.PeriodMapping;

public class PeriodMapingInfo {
    private PeriodMapping periodMapping;
    private String periodCode;
    private String periodMapCode;

    public PeriodMapingInfo() {
    }

    public PeriodMapingInfo(PeriodMapping periodMapping) {
        this.periodMapping = periodMapping;
        this.periodCode = periodMapping.getPeriod();
        this.periodMapCode = periodMapping.getMapping();
    }

    public PeriodMapping getPeriodMapping() {
        return this.periodMapping;
    }

    public void setPeriodMapping(PeriodMapping periodMapping) {
        this.periodMapping = periodMapping;
    }

    public String getPeriodCode() {
        return this.periodCode;
    }

    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }

    public String getPeriodMapCode() {
        return this.periodMapCode;
    }

    public void setPeriodMapCode(String periodMapCode) {
        this.periodMapCode = periodMapCode;
    }
}


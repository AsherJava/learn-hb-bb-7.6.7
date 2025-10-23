/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.web.vo;

import com.jiuqi.nr.mapping.bean.PeriodMapping;
import java.util.List;

public class PeriodMappingVO {
    private List<PeriodMapping> mappings;
    private List<String> periods;
    private String defaultDate;

    public List<PeriodMapping> getMappings() {
        return this.mappings;
    }

    public void setMappings(List<PeriodMapping> mappings) {
        this.mappings = mappings;
    }

    public List<String> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }

    public String getDefaultDate() {
        return this.defaultDate;
    }

    public void setDefaultDate(String defaultDate) {
        this.defaultDate = defaultDate;
    }
}


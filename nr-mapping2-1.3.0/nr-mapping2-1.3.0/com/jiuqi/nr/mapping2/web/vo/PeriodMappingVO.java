/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.web.vo;

import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.web.vo.Result;
import java.util.List;
import java.util.Map;

public class PeriodMappingVO
extends Result {
    private List<PeriodMapping> mappings;
    private List<String> periods;
    private String defaultDate;
    private List<Map<String, String>> customArr;

    public PeriodMappingVO() {
        super(true, null, null);
    }

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

    public List<Map<String, String>> getCustomArr() {
        return this.customArr;
    }

    public void setCustomArr(List<Map<String, String>> customArr) {
        this.customArr = customArr;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.period.select.vo;

import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import java.util.List;

public class AdjustData {
    private String code;
    private List<AdjustPeriod> adjustList;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<AdjustPeriod> getAdjustList() {
        return this.adjustList;
    }

    public void setAdjustList(List<AdjustPeriod> adjustList) {
        this.adjustList = adjustList;
    }
}


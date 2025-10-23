/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;

public class DataDimPeriodVO
extends BaseDataVO {
    private int periodType;

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public DataDimPeriodVO(int periodType) {
        this.periodType = periodType;
    }

    public DataDimPeriodVO() {
    }

    public DataDimPeriodVO(String key, String code, String title, String desc, int periodType) {
        super(key, code, title, desc);
        this.periodType = periodType;
    }
}


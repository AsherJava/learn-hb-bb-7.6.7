/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.web.param;

import com.jiuqi.nr.period.select.common.Mode;
import com.jiuqi.nr.period.select.vo.ParamObj;

public class LoadYearData {
    private ParamObj paramObj;
    private Mode mode = Mode.S;
    private Integer year;

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public ParamObj getParamObj() {
        return this.paramObj;
    }

    public void setParamObj(ParamObj paramObj) {
        this.paramObj = paramObj;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.web.param;

import com.jiuqi.nr.period.select.common.Mode;
import com.jiuqi.nr.period.select.vo.ParamObj;
import java.util.List;

public class LoadYearsData {
    private ParamObj paramObj;
    private Mode mode = Mode.S;
    private List<Integer> years;

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

    public List<Integer> getYears() {
        return this.years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }
}


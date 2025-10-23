/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.web.param;

import com.jiuqi.nr.period.select.common.Mode;
import com.jiuqi.nr.period.select.vo.ParamObj;
import java.util.List;

public class ModeSelectData {
    private ParamObj paramObj;
    private Mode mode = Mode.S;
    private List<String> range;

    public List<String> getRange() {
        return this.range;
    }

    public void setRange(List<String> range) {
        this.range = range;
    }

    public ParamObj getParamObj() {
        return this.paramObj;
    }

    public void setParamObj(ParamObj paramObj) {
        this.paramObj = paramObj;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.CondParam;
import com.jiuqi.nr.summary.vo.ResultParam;
import com.jiuqi.nr.summary.vo.SiderParam;

public class PreviewInitParam {
    private CondParam condParam;
    private SiderParam siderParam;
    private ResultParam resultParam;

    public CondParam getCondParam() {
        return this.condParam;
    }

    public void setCondParam(CondParam condParam) {
        this.condParam = condParam;
    }

    public SiderParam getSiderParam() {
        return this.siderParam;
    }

    public void setSiderParam(SiderParam siderParam) {
        this.siderParam = siderParam;
    }

    public ResultParam getResultParam() {
        return this.resultParam;
    }

    public void setResultParam(ResultParam resultParam) {
        this.resultParam = resultParam;
    }
}


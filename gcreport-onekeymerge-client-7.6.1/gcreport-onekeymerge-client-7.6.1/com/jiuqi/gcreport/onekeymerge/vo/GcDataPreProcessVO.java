/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;

public class GcDataPreProcessVO
extends GcBaseTaskStateVO {
    private String rule;
    private String result;

    public String getRule() {
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String toString() {
        return "DataPreProcessDTO{rule='" + this.rule + '\'' + ", result='" + this.result + '\'' + '}';
    }
}


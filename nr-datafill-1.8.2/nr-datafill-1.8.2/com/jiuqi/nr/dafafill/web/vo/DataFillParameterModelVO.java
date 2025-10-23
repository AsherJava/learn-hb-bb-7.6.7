/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import java.io.Serializable;

public class DataFillParameterModelVO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String json;
    private boolean quickCondition;

    public String getJson() {
        return this.json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public boolean isQuickCondition() {
        return this.quickCondition;
    }

    public void setQuickCondition(boolean quickCondition) {
        this.quickCondition = quickCondition;
    }
}


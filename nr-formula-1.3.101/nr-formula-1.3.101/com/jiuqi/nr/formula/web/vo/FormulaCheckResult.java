/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

import java.util.Map;

public class FormulaCheckResult {
    private int total;
    private Map<String, String> error;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map<String, String> getError() {
        return this.error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.common;

import com.jiuqi.nr.finalaccountsaudit.multcheck.common.DataAnalysisItem;
import java.io.Serializable;
import java.util.List;

public class AnalysisResultInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<DataAnalysisItem> analysisItems;
    private boolean result;

    public List<DataAnalysisItem> getAnalysisItems() {
        return this.analysisItems;
    }

    public void setAnalysisItems(List<DataAnalysisItem> analysisItems) {
        this.analysisItems = analysisItems;
    }

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}


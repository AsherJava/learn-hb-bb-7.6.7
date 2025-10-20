/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.parser.dataset;

public final class DSFormulaInfo {
    private String curDataSet;
    private boolean rawFields;

    public DSFormulaInfo(String curDataSet) {
        this(curDataSet, false);
    }

    public DSFormulaInfo(String curDataSet, boolean rawFields) {
        this.curDataSet = curDataSet;
    }

    public String getCurDataSet() {
        return this.curDataSet;
    }

    public boolean isRawFields() {
        return this.rawFields;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;

public class ExportHandleCurrParam {
    private int currentIndex;
    private List<Map<String, DimensionValue>> currentDimension;
    private List<String> splitAllForms;

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Map<String, DimensionValue>> getCurrentDimension() {
        return this.currentDimension;
    }

    public void setCurrentDimension(List<Map<String, DimensionValue>> currentDimension) {
        this.currentDimension = currentDimension;
    }

    public List<String> getSplitAllForms() {
        return this.splitAllForms;
    }

    public void setSplitAllForms(List<String> splitAllForms) {
        this.splitAllForms = splitAllForms;
    }
}


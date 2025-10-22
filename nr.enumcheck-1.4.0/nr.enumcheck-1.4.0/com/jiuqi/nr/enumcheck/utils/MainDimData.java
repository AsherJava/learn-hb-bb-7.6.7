/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.spire.ms.System.Collections.ArrayList
 */
package com.jiuqi.nr.enumcheck.utils;

import com.spire.ms.System.Collections.ArrayList;
import java.util.List;

public class MainDimData {
    private List<List<String>> extFdValues = new ArrayList();
    private int fdSize = 0;
    private List<String> mainDimValue = new ArrayList();

    public List<List<String>> getExtFdValues() {
        return this.extFdValues;
    }

    public void setExtFdValues(List<List<String>> extFdValues) {
        this.extFdValues = extFdValues;
    }

    public List<String> getMainDimValue() {
        return this.mainDimValue;
    }

    public void setMainDimValue(List<String> mainDimValue) {
        this.mainDimValue = mainDimValue;
    }

    public void addMainValue(String value) {
        this.mainDimValue.add(value);
    }

    public void setExTFdSize(int fdSize) {
        this.fdSize = fdSize;
        this.extFdValues.clear();
        for (int idx = 0; idx < fdSize; ++idx) {
            this.extFdValues.add((List<String>)new ArrayList());
        }
    }

    public void addExtValue(int idx, String value) {
        this.extFdValues.get(idx).add(value);
    }

    public String getExtValue(int idx, int fdIdx) {
        return this.extFdValues.get(idx).get(fdIdx);
    }

    public void setExtValues(int idx, List<String> fdValues) {
        this.extFdValues.set(idx, fdValues);
    }

    public List<String> getExtValues(int idx) {
        return this.extFdValues.get(idx);
    }

    public boolean hasExtData() {
        return this.fdSize > 0;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.response;

import com.jiuqi.nr.efdc.extract.impl.response.FixExpResult;
import java.util.ArrayList;
import java.util.List;

public class FloatExpResult {
    private String flag;
    private int rowCount;
    private List<FixExpResult> colResults = new ArrayList<FixExpResult>();
    private boolean valid = true;

    public void setColResults(List<FixExpResult> colResults) {
        this.colResults = colResults;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<FixExpResult> getColResults() {
        return this.colResults;
    }

    public boolean addColResult(FixExpResult result) {
        if (result == null) {
            return false;
        }
        return this.colResults.add(result);
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return this.valid;
    }
}


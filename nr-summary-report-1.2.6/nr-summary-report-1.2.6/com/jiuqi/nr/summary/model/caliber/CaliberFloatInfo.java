/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.caliber;

import com.jiuqi.nr.summary.model.caliber.CaliberExpandMode;
import com.jiuqi.nr.summary.model.caliber.SumPosition;
import java.io.Serializable;
import java.util.List;

public class CaliberFloatInfo
implements Serializable {
    private String displayField;
    private CaliberExpandMode expandMode = CaliberExpandMode.ALL;
    private List<String> values;
    private int level;
    private String filter;
    private boolean showSum;
    private SumPosition sumPosition = SumPosition.BOTTOM;

    public String getDisplayField() {
        return this.displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }

    public CaliberExpandMode getExpandMode() {
        return this.expandMode;
    }

    public void setExpandMode(CaliberExpandMode expandMode) {
        this.expandMode = expandMode;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isShowSum() {
        return this.showSum;
    }

    public void setShowSum(boolean showSum) {
        this.showSum = showSum;
    }

    public SumPosition getSumPosition() {
        return this.sumPosition;
    }

    public void setSumPosition(SumPosition sumPosition) {
        this.sumPosition = sumPosition;
    }
}


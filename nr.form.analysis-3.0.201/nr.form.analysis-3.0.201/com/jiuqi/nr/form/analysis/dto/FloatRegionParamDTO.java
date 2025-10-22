/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

import java.util.List;

public class FloatRegionParamDTO {
    private String dataRegionKey;
    private String listCondition;
    private String listFilter;
    private String sortFields;
    private String sortFlags;
    private int maxRowCount;
    private String classifyFields;
    private String classifyWidths;
    private boolean classifySumOnly;
    private List<String> sumExpressions;

    public String getDataRegionKey() {
        return this.dataRegionKey;
    }

    public void setDataRegionKey(String dataRegionKey) {
        this.dataRegionKey = dataRegionKey;
    }

    public String getListCondition() {
        return this.listCondition;
    }

    public void setListCondition(String listCondition) {
        this.listCondition = listCondition;
    }

    public String getListFilter() {
        return this.listFilter;
    }

    public void setListFilter(String listFilter) {
        this.listFilter = listFilter;
    }

    public String getSortFields() {
        return this.sortFields;
    }

    public void setSortFields(String sortFields) {
        this.sortFields = sortFields;
    }

    public String getSortFlags() {
        return this.sortFlags;
    }

    public void setSortFlags(String sortFlags) {
        this.sortFlags = sortFlags;
    }

    public int getMaxRowCount() {
        return this.maxRowCount;
    }

    public void setMaxRowCount(int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    public String getClassifyFields() {
        return this.classifyFields;
    }

    public void setClassifyFields(String classifyFields) {
        this.classifyFields = classifyFields;
    }

    public String getClassifyWidths() {
        return this.classifyWidths;
    }

    public void setClassifyWidths(String classifyWidths) {
        this.classifyWidths = classifyWidths;
    }

    public boolean isClassifySumOnly() {
        return this.classifySumOnly;
    }

    public void setClassifySumOnly(boolean classifySumOnly) {
        this.classifySumOnly = classifySumOnly;
    }

    public List<String> getSumExpressions() {
        return this.sumExpressions;
    }

    public void setSumExpressions(List<String> sumExpressions) {
        this.sumExpressions = sumExpressions;
    }
}


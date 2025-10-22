/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.anslysis;

import com.jiuqi.nr.definition.facade.analysis.FloatListConfig;
import java.util.List;

public class FloatListConfigImpl
implements FloatListConfig {
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

    @Override
    public String getDataRegionKey() {
        return this.dataRegionKey;
    }

    @Override
    public void setDataRegionKey(String dataRegionKey) {
        this.dataRegionKey = dataRegionKey;
    }

    @Override
    public String getListCondition() {
        return this.listCondition;
    }

    @Override
    public void setListCondition(String listCondition) {
        this.listCondition = listCondition;
    }

    @Override
    public String getListFilter() {
        return this.listFilter;
    }

    @Override
    public void setListFilter(String listFilter) {
        this.listFilter = listFilter;
    }

    @Override
    public String getSortFields() {
        return this.sortFields;
    }

    @Override
    public void setSortFields(String sortFields) {
        this.sortFields = sortFields;
    }

    @Override
    public String getSortFlags() {
        return this.sortFlags;
    }

    @Override
    public void setSortFlags(String sortFlags) {
        this.sortFlags = sortFlags;
    }

    @Override
    public int getMaxRowCount() {
        return this.maxRowCount;
    }

    @Override
    public void setMaxRowCount(int maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    @Override
    public String getClassifyFields() {
        return this.classifyFields;
    }

    @Override
    public void setClassifyFields(String classifyFields) {
        this.classifyFields = classifyFields;
    }

    @Override
    public String getClassifyWidths() {
        return this.classifyWidths;
    }

    @Override
    public void setClassifyWidths(String classifyWidths) {
        this.classifyWidths = classifyWidths;
    }

    @Override
    public boolean getClassifySumOnly() {
        return this.classifySumOnly;
    }

    @Override
    public void setClassifySumOnly(boolean classifySumOnly) {
        this.classifySumOnly = classifySumOnly;
    }

    @Override
    public List<String> getSumExpressions() {
        return this.sumExpressions;
    }

    @Override
    public void setSumExpressions(List<String> sumExpressions) {
        this.sumExpressions = sumExpressions;
    }

    public String toString() {
        return "FloatListObj [dataRegionKey=" + this.dataRegionKey + ", listCondition=" + this.listCondition + ", listFilter=" + this.listFilter + ", sortFields=" + this.sortFields + ", sortFlags=" + this.sortFlags + ", maxRowCount=" + this.maxRowCount + ", classifyFields=" + this.classifyFields + ", classifyWidths=" + this.classifyWidths + ", classifySumOnly=" + this.classifySumOnly + ", sumExpressions=" + this.sumExpressions + "]";
    }
}


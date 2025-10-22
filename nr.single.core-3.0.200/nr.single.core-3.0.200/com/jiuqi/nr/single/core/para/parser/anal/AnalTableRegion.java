/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.anal;

import com.jiuqi.nr.single.core.para.parser.anal.AnalCellFormulaItem;
import java.util.ArrayList;
import java.util.List;

public class AnalTableRegion {
    private int floatingRow;
    private List<AnalCellFormulaItem> statFormulas;
    private String listCondition;
    private String listFilter;
    private String sortFields;
    private String sortFlags;
    private int maxRowCount;
    private String classifyFields;
    private String classifyWidths;
    private boolean classifySumOnly;
    private String keyFields;
    private boolean isEmpty;

    public int getFloatingRow() {
        return this.floatingRow;
    }

    public void setFloatingRow(int floatingRow) {
        this.floatingRow = floatingRow;
    }

    public List<AnalCellFormulaItem> getStatFormulas() {
        if (this.statFormulas == null) {
            this.statFormulas = new ArrayList<AnalCellFormulaItem>();
        }
        return this.statFormulas;
    }

    public void setStatFormulas(List<AnalCellFormulaItem> statFormulas) {
        this.statFormulas = statFormulas;
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

    public String getKeyFields() {
        return this.keyFields;
    }

    public void setKeyFields(String keyFields) {
        this.keyFields = keyFields;
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}


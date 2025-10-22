/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.collector;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.ArrayList;
import java.util.List;

public class GlobalInfo {
    public long totalCost;
    public int fieldCount;
    public int queryRecordCount;
    public int formulaCount;
    public int updateRecordCount = 0;
    public int insertRecordCount = 0;
    public int execRegionCount = 0;
    public List<IParsedExpression> advanceExpressions = new ArrayList<IParsedExpression>();

    public long getTotalCost() {
        return this.totalCost;
    }

    public int getFieldCount() {
        return this.fieldCount;
    }

    public int getQueryRecordCount() {
        return this.queryRecordCount;
    }

    public int getFormulaCount() {
        return this.formulaCount;
    }

    public int getUpdateRecordCount() {
        return this.updateRecordCount;
    }

    public int getInsertRecordCount() {
        return this.insertRecordCount;
    }

    public int getExecRegionCount() {
        return this.execRegionCount;
    }

    public List<IParsedExpression> getAdvanceExpressions() {
        return this.advanceExpressions;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public void setQueryRecordCount(int queryRecordCount) {
        this.queryRecordCount = queryRecordCount;
    }

    public void setFormulaCount(int formulaCount) {
        this.formulaCount = formulaCount;
    }

    public void setUpdateRecordCount(int updateRecordCount) {
        this.updateRecordCount = updateRecordCount;
    }

    public void addUpdateRecordCount() {
        ++this.updateRecordCount;
    }

    public void addInsertRecordCount() {
        ++this.insertRecordCount;
    }

    public void addExecRegionCount(int execRegionCount) {
        this.execRegionCount += execRegionCount;
    }
}


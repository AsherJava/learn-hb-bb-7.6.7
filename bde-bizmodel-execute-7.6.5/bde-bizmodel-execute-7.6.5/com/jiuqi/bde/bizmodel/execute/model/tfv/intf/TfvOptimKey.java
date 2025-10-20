/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.intf;

import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.OptimizeOperator;

public class TfvOptimKey {
    private String selectColumn;
    private String selectAlias;
    private boolean selectContainsSum = false;
    private String mainDimName = "";
    private String mainDimValue = "";
    private OptimizeOperator mainDimOperator = OptimizeOperator.EQ;
    private String secondDimName = "";
    private String secondDimValue = "";
    private OptimizeOperator secondDimOperator = OptimizeOperator.EQ;
    private String otherCondition = "";
    private String resultOptimizeFlag;

    public String getSelectColumn() {
        return this.selectColumn;
    }

    public void setSelectColumn(String selectColumn) {
        this.selectColumn = selectColumn;
    }

    public String getSelectAlias() {
        return this.selectAlias;
    }

    public void setSelectAlias(String selectAlias) {
        this.selectAlias = selectAlias;
    }

    public boolean getSelectContainsSum() {
        return this.selectContainsSum;
    }

    public void setSelectContainsSum(boolean selectContainsSum) {
        this.selectContainsSum = selectContainsSum;
    }

    public String getMainDimName() {
        return this.mainDimName;
    }

    public void setMainDimName(String mainDimName) {
        this.mainDimName = mainDimName;
    }

    public String getMainDimValue() {
        return this.mainDimValue;
    }

    public void setMainDimValue(String mainDimValue) {
        this.mainDimValue = mainDimValue;
    }

    public OptimizeOperator getMainDimOperator() {
        return this.mainDimOperator;
    }

    public void setMainDimOperator(OptimizeOperator mainDimOperator) {
        this.mainDimOperator = mainDimOperator;
    }

    public String getSecondDimName() {
        return this.secondDimName;
    }

    public void setSecondDimName(String secondDimName) {
        this.secondDimName = secondDimName;
    }

    public String getSecondDimValue() {
        return this.secondDimValue;
    }

    public void setSecondDimValue(String secondDimValue) {
        this.secondDimValue = secondDimValue;
    }

    public OptimizeOperator getSecondDimOperator() {
        return this.secondDimOperator;
    }

    public void setSecondDimOperator(OptimizeOperator secondDimOperator) {
        this.secondDimOperator = secondDimOperator;
    }

    public String getOtherCondition() {
        return this.otherCondition;
    }

    public void setOtherCondition(String otherCondition) {
        this.otherCondition = otherCondition;
    }

    public String getOptimizeRule() {
        StringBuffer optimizeRule = new StringBuffer();
        optimizeRule.append(",").append(this.selectContainsSum);
        optimizeRule.append(",").append(this.mainDimName.toLowerCase());
        optimizeRule.append(",").append(this.secondDimName.toLowerCase());
        optimizeRule.append(",").append(this.otherCondition.toLowerCase());
        return optimizeRule.toString();
    }

    public String getResultOptimizeFlag() {
        if (this.resultOptimizeFlag == null) {
            this.resultOptimizeFlag = TfvOptimKey.buildResultOptimizeFlag(this.mainDimValue, this.secondDimValue);
        }
        return this.resultOptimizeFlag;
    }

    public static String buildResultOptimizeFlag(String mainDimValue, String secondDimValue) {
        StringBuffer optimizeRule = new StringBuffer();
        optimizeRule.append(mainDimValue == null ? "" : mainDimValue);
        optimizeRule.append("-").append(secondDimValue == null ? "" : secondDimValue);
        return optimizeRule.toString();
    }

    public String toString() {
        return "TfvOptimizeTicket [mainDimName=" + this.mainDimName + ", mainDimValue=" + this.mainDimValue + ", mainDimOperator=" + (Object)((Object)this.mainDimOperator) + ", secondDimName=" + this.secondDimName + ", secondDimValue=" + this.secondDimValue + ", secondDimOperator=" + (Object)((Object)this.secondDimOperator) + ", otherCondition=" + this.otherCondition + "]";
    }
}


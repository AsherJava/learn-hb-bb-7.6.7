/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.node.DynamicDataNode;

public class SqlCalcItem {
    private DynamicDataNode leftNode;
    private String assignSqlPart;
    private String conditionSqlPart;

    public SqlCalcItem(DynamicDataNode leftNode) {
        this.leftNode = leftNode;
    }

    public String getAssignSqlPart() {
        return this.assignSqlPart;
    }

    public void setAssignSqlPart(String assignSqlPart) {
        this.assignSqlPart = assignSqlPart;
    }

    public String getConditionSqlPart() {
        return this.conditionSqlPart;
    }

    public void setConditionSqlPart(String conditionSqlPart) {
        this.conditionSqlPart = conditionSqlPart;
    }

    public DynamicDataNode getLeftNode() {
        return this.leftNode;
    }
}


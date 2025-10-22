/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy;

public class GcSimpleCopyOperand {
    private String tableName = "GC_INPUTDATA";
    private String fieldName;

    public GcSimpleCopyOperand(String exp) {
        int leftBracketIndex = exp.indexOf("[");
        int rightBracketIndex = exp.indexOf("]");
        if (leftBracketIndex > -1 && rightBracketIndex > -1) {
            this.tableName = exp.substring(0, leftBracketIndex);
            this.fieldName = exp.substring(leftBracketIndex + 1, rightBracketIndex);
        } else {
            this.fieldName = exp;
        }
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String formatField() {
        return this.tableName + "[" + this.fieldName + "]";
    }
}


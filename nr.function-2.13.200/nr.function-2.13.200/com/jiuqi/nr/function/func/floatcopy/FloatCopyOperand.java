/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.function.func.floatcopy;

public class FloatCopyOperand {
    private int rowNum;
    private int colNum;
    private String reportName;
    private String linkAlias;

    public FloatCopyOperand(String exp) {
        int leftBracketIndex = exp.indexOf("[");
        int rightBracketIndex = exp.indexOf("]");
        int atIndex = exp.indexOf("@");
        this.reportName = exp.substring(0, leftBracketIndex);
        String cellXY = exp.substring(leftBracketIndex + 1, rightBracketIndex);
        String[] xy = cellXY.split(",");
        this.rowNum = Integer.parseInt(xy[0]);
        this.colNum = Integer.parseInt(xy[1]);
        if (atIndex > 0) {
            this.linkAlias = exp.substring(atIndex + 1);
        }
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public int getColNum() {
        return this.colNum;
    }

    public String getReportName() {
        return this.reportName;
    }

    public String getLinkAlias() {
        return this.linkAlias;
    }
}


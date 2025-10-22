/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.node;

import java.io.Serializable;

public class NodeShowInfo
implements Serializable {
    private static final long serialVersionUID = 7152285609901491953L;
    private boolean isZBExpression;
    private String tableName;
    private String sheetName;
    private String innerAppend;
    private String endAppend;
    private boolean hasBracket = true;

    public boolean isZBExpression() {
        return this.isZBExpression;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public String getInnerAppend() {
        return this.innerAppend;
    }

    public String getEndAppend() {
        return this.endAppend;
    }

    public void setZBExpression(boolean isZBExpression) {
        this.isZBExpression = isZBExpression;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void addInnerAppend(String innerAppend) {
        this.innerAppend = this.innerAppend == null ? innerAppend : this.innerAppend + "," + innerAppend;
    }

    public void addEndAppend(String endAppend) {
        this.endAppend = this.endAppend == null ? endAppend : this.endAppend + "," + endAppend;
    }

    public boolean isHasBracket() {
        return this.hasBracket;
    }

    public void setHasBracket(boolean hasBracket) {
        this.hasBracket = hasBracket;
    }

    public void setInnerAppend(String innerAppend) {
        this.innerAppend = innerAppend;
    }

    public void setEndAppend(String endAppend) {
        this.endAppend = endAppend;
    }

    public String toString() {
        return "NodeShowInfo [isZBExpression=" + this.isZBExpression + ", tableName=" + this.tableName + ", sheetName=" + this.sheetName + ", innerAppend=" + this.innerAppend + ", endAppend=" + this.endAppend + ", hasBracket=" + this.hasBracket + "]";
    }
}


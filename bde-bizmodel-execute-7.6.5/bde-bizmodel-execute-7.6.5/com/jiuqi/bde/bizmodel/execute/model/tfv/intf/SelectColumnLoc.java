/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.intf;

public class SelectColumnLoc {
    private String column;
    private String alias;
    private Integer loc;
    private boolean containsSum = false;

    public SelectColumnLoc() {
    }

    public SelectColumnLoc(String column, String alias, Integer loc, boolean containsSum) {
        this.column = column;
        this.alias = alias;
        this.loc = loc;
        this.containsSum = containsSum;
    }

    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getLoc() {
        return this.loc;
    }

    public void setLoc(Integer loc) {
        this.loc = loc;
    }

    public boolean getContainsSum() {
        return this.containsSum;
    }

    public void setContainsSum(boolean containsSum) {
        this.containsSum = containsSum;
    }

    public String toString() {
        return "SelectColumnLoc [column=" + this.column + ", alias=" + this.alias + ", loc=" + this.loc + ", containsSum=" + this.containsSum + "]";
    }
}


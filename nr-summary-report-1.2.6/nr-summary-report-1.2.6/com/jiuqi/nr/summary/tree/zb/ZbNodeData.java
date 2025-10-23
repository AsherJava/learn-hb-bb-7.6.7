/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.summary.tree.zb;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;

public class ZbNodeData {
    private DataFieldType dataType;
    private int precision;
    private int decimal;
    private DataFieldGatherType gatherType;
    private String exp;

    public DataFieldType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataFieldType dataType) {
        this.dataType = dataType;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public DataFieldGatherType getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(DataFieldGatherType gatherType) {
        this.gatherType = gatherType;
    }

    public String getExp() {
        return this.exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}


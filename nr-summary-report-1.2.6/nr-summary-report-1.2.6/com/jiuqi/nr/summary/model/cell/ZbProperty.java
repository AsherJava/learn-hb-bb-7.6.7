/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.summary.model.cell;

import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.io.Serializable;

public class ZbProperty
implements Serializable {
    private DataFieldType dataType;
    private int precision;
    private int decimal;

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
}


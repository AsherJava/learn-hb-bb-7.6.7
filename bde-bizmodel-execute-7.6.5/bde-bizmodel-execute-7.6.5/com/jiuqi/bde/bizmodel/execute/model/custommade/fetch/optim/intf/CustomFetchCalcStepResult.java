/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import java.math.BigDecimal;

public class CustomFetchCalcStepResult {
    private BigDecimal decimalVal;
    private BigDecimal intVal;
    private String strVal;
    private BigDecimal stepCt;

    public CustomFetchCalcStepResult setVal(ResultColumnType colType, Object value) {
        switch (colType) {
            case NUMBER: {
                if (value == null) {
                    this.decimalVal = BigDecimal.ZERO;
                    return this;
                }
                if (value instanceof BigDecimal) {
                    this.decimalVal = (BigDecimal)value;
                    return this;
                }
                this.decimalVal = new BigDecimal(value.toString());
                return this;
            }
            case INT: {
                if (value == null) {
                    this.intVal = BigDecimal.ZERO;
                    return this;
                }
                if (value instanceof BigDecimal) {
                    this.intVal = (BigDecimal)value;
                    return this;
                }
                this.intVal = new BigDecimal(value.toString());
                return this;
            }
        }
        this.strVal = value instanceof String ? (String)value : String.valueOf(value);
        return this;
    }

    public BigDecimal getDecimalVal() {
        return this.decimalVal;
    }

    public BigDecimal getIntVal() {
        return this.intVal;
    }

    public String getStrVal() {
        return this.strVal;
    }

    public BigDecimal getStepCt() {
        return this.stepCt;
    }

    public void setStepCt(BigDecimal stepCt) {
        this.stepCt = stepCt;
    }
}


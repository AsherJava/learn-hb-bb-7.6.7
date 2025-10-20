/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.aging;

import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import java.math.BigDecimal;

public class AgingBalanceData
extends AssBalanceData {
    private BigDecimal hxnc;
    private BigDecimal hxye;
    private BigDecimal hxzsc;
    private BigDecimal whxnc;
    private BigDecimal whxye;

    public BigDecimal getHxnc() {
        return this.hxnc;
    }

    public void setHxnc(BigDecimal hxnc) {
        this.hxnc = hxnc;
    }

    public BigDecimal getHxye() {
        return this.hxye;
    }

    public void setHxye(BigDecimal hxye) {
        this.hxye = hxye;
    }

    public BigDecimal getHxzsc() {
        return this.hxzsc;
    }

    public void setHxzsc(BigDecimal hxzsc) {
        this.hxzsc = hxzsc;
    }

    public BigDecimal getWhxnc() {
        return this.whxnc;
    }

    public void setWhxnc(BigDecimal whxnc) {
        this.whxnc = whxnc;
    }

    public BigDecimal getWhxye() {
        return this.whxye;
    }

    public void setWhxye(BigDecimal whxye) {
        this.whxye = whxye;
    }

    @Override
    public String toString() {
        return "ReclassifyBalanceData [hxnc=" + this.hxnc + ", hxye=" + this.hxye + ", hxzsc=" + this.hxzsc + ", whxnc=" + this.whxnc + ", whxye=" + this.whxye + ", toString()=" + super.toString() + "]";
    }
}


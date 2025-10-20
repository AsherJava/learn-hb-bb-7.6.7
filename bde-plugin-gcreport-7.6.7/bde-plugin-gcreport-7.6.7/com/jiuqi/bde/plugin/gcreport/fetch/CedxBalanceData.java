/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData
 */
package com.jiuqi.bde.plugin.gcreport.fetch;

import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import java.math.BigDecimal;

public class CedxBalanceData
extends AssBalanceData {
    private Integer orient;
    private BigDecimal dxnc;
    private BigDecimal dxjnc;
    private BigDecimal dxdnc;
    private BigDecimal dxye;
    private BigDecimal dxjyh;
    private BigDecimal dxdyh;

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public BigDecimal getDxnc() {
        return this.dxnc;
    }

    public void setDxnc(BigDecimal dxnc) {
        this.dxnc = dxnc;
    }

    public BigDecimal getDxjnc() {
        return this.dxjnc;
    }

    public void setDxjnc(BigDecimal dxjnc) {
        this.dxjnc = dxjnc;
    }

    public BigDecimal getDxdnc() {
        return this.dxdnc;
    }

    public void setDxdnc(BigDecimal dxdnc) {
        this.dxdnc = dxdnc;
    }

    public BigDecimal getDxye() {
        return this.dxye;
    }

    public void setDxye(BigDecimal dxye) {
        this.dxye = dxye;
    }

    public BigDecimal getDxjyh() {
        return this.dxjyh;
    }

    public void setDxjyh(BigDecimal dxjyh) {
        this.dxjyh = dxjyh;
    }

    public BigDecimal getDxdyh() {
        return this.dxdyh;
    }

    public void setDxdyh(BigDecimal dxdyh) {
        this.dxdyh = dxdyh;
    }
}


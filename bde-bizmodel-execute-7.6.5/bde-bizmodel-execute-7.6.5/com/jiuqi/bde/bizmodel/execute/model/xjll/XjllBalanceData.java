/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.model.xjll;

import com.jiuqi.bde.bizmodel.execute.intf.AssBalanceData;
import java.math.BigDecimal;

public class XjllBalanceData
extends AssBalanceData {
    private String cfItemCode;
    private BigDecimal bqnum;
    private BigDecimal ljnum;
    private BigDecimal wbqnum;
    private BigDecimal wljnum;

    public String getCfItemCode() {
        return this.cfItemCode;
    }

    public void setCfItemCode(String cfItemCode) {
        this.cfItemCode = cfItemCode;
    }

    @Override
    public BigDecimal getBqnum() {
        return this.bqnum;
    }

    @Override
    public void setBqnum(BigDecimal bqnum) {
        this.bqnum = bqnum;
    }

    @Override
    public BigDecimal getLjnum() {
        return this.ljnum;
    }

    @Override
    public void setLjnum(BigDecimal ljnum) {
        this.ljnum = ljnum;
    }

    @Override
    public BigDecimal getWbqnum() {
        return this.wbqnum;
    }

    @Override
    public void setWbqnum(BigDecimal wbqnum) {
        this.wbqnum = wbqnum;
    }

    @Override
    public BigDecimal getWljnum() {
        return this.wljnum;
    }

    @Override
    public void setWljnum(BigDecimal wljnum) {
        this.wljnum = wljnum;
    }

    @Override
    public String toString() {
        return "CfBalanceData [cfItemCode=" + this.cfItemCode + ", toString()=" + super.toString() + "]";
    }
}


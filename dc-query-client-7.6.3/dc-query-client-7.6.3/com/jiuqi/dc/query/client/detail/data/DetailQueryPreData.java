/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.detail.data;

import java.math.BigDecimal;

public class DetailQueryPreData {
    private BigDecimal nc;
    private BigDecimal wnc;
    private BigDecimal cj;
    private BigDecimal wjc;
    private BigDecimal cd;
    private BigDecimal wdc;

    public DetailQueryPreData() {
    }

    public DetailQueryPreData(BigDecimal nc, BigDecimal wnc, BigDecimal cj, BigDecimal wjc, BigDecimal cd, BigDecimal wdc) {
        this.nc = nc;
        this.wnc = wnc;
        this.cj = cj;
        this.wjc = wjc;
        this.cd = cd;
        this.wdc = wdc;
    }

    public BigDecimal getNc() {
        return this.nc;
    }

    public void setNc(BigDecimal nc) {
        this.nc = nc;
    }

    public BigDecimal getWnc() {
        return this.wnc;
    }

    public void setWnc(BigDecimal wnc) {
        this.wnc = wnc;
    }

    public BigDecimal getCj() {
        return this.cj;
    }

    public void setCj(BigDecimal cj) {
        this.cj = cj;
    }

    public BigDecimal getWjc() {
        return this.wjc;
    }

    public void setWjc(BigDecimal wjc) {
        this.wjc = wjc;
    }

    public BigDecimal getCd() {
        return this.cd;
    }

    public void setCd(BigDecimal cd) {
        this.cd = cd;
    }

    public BigDecimal getWdc() {
        return this.wdc;
    }

    public void setWdc(BigDecimal wdc) {
        this.wdc = wdc;
    }
}


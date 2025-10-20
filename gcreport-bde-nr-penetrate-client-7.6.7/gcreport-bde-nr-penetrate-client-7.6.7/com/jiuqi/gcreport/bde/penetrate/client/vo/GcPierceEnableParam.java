/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.penetrate.client.vo;

public class GcPierceEnableParam {
    private String bblx;
    private String prodLine;
    private Boolean hasFormula;

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getProdLine() {
        return this.prodLine;
    }

    public void setProdLine(String prodLine) {
        this.prodLine = prodLine;
    }

    public Boolean getHasFormula() {
        return this.hasFormula;
    }

    public void setHasFormula(Boolean hasFormula) {
        this.hasFormula = hasFormula;
    }

    public GcPierceEnableParam(String bblx, String prodLine) {
        this.bblx = bblx;
        this.prodLine = prodLine;
    }

    public GcPierceEnableParam() {
    }

    public GcPierceEnableParam(String bblx, String prodLine, Boolean hasFormula) {
        this.bblx = bblx;
        this.prodLine = prodLine;
        this.hasFormula = hasFormula;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.archive.api.scheme.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendArchiveWjxxVO {
    private String F_PDFMC;
    private String F_PDFPATH;
    private String F_EXCELMC;
    private String F_EXCELPATH;

    @JsonProperty(value="F_PDFMC")
    public String getF_PDFMC() {
        return this.F_PDFMC;
    }

    public void setF_PDFMC(String f_PDFMC) {
        this.F_PDFMC = f_PDFMC;
    }

    @JsonProperty(value="F_PDFPATH")
    public String getF_PDFPATH() {
        return this.F_PDFPATH;
    }

    public void setF_PDFPATH(String f_PDFPATH) {
        this.F_PDFPATH = f_PDFPATH;
    }

    @JsonProperty(value="F_EXCELMC")
    public String getF_EXCELMC() {
        return this.F_EXCELMC;
    }

    public void setF_EXCELMC(String f_EXCELMC) {
        this.F_EXCELMC = f_EXCELMC;
    }

    @JsonProperty(value="F_EXCELPATH")
    public String getF_EXCELPATH() {
        return this.F_EXCELPATH;
    }

    public void setF_EXCELPATH(String f_EXCELPATH) {
        this.F_EXCELPATH = f_EXCELPATH;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.archive.api.scheme.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EFSResponseData {
    @JsonProperty(value="CODE")
    private String code;
    @JsonProperty(value="ERRCODE")
    private String errCode;
    @JsonProperty(value="MESSAGE")
    private String message;
    @JsonProperty(value="F_KJQJ")
    private String f_jkqj;
    @JsonProperty(value="F_MDMCODE")
    private String f_mdmCode;
    @JsonProperty(value="F_BBLX")
    private String f_bblx;
    @JsonProperty(value="F_UUID")
    private String f_uuid;
    @JsonProperty(value="F_EXCELPATH")
    private String f_excelPath;
    @JsonProperty(value="F_PDFPATH")
    private String f_pdfPath;

    public EFSResponseData() {
    }

    public EFSResponseData(String code, String f_uuid, String message) {
        this.code = code;
        this.f_uuid = f_uuid;
        this.message = message;
    }

    public String getCODE() {
        return this.code;
    }

    @JsonProperty(value="CODE")
    public void setCODE(String code) {
        this.code = code;
    }

    public String getERRCODE() {
        return this.errCode;
    }

    @JsonProperty(value="ERRCODE")
    public void setERRCODE(String errCode) {
        this.errCode = errCode;
    }

    public String getMESSAGE() {
        return this.message;
    }

    @JsonProperty(value="MESSAGE")
    public void setMESSAGE(String message) {
        this.message = message;
    }

    public String getF_KJQJ() {
        return this.f_jkqj;
    }

    @JsonProperty(value="F_KJQJ")
    public void setF_KJQJ(String f_jkqj) {
        this.f_jkqj = f_jkqj;
    }

    public String getF_MDMCODE() {
        return this.f_mdmCode;
    }

    @JsonProperty(value="F_MDMCODE")
    public void setF_MDMCODE(String f_mdmCode) {
        this.f_mdmCode = f_mdmCode;
    }

    public String getF_BBLX() {
        return this.f_bblx;
    }

    @JsonProperty(value="F_BBLX")
    public void setF_BBLX(String f_bblx) {
        this.f_bblx = f_bblx;
    }

    public String getF_UUID() {
        return this.f_uuid;
    }

    @JsonProperty(value="F_UUID")
    public void setF_UUID(String f_uuid) {
        this.f_uuid = f_uuid;
    }

    public String getF_EXCELPATH() {
        return this.f_excelPath;
    }

    @JsonProperty(value="F_EXCELPATH")
    public void setF_EXCELPATH(String f_excelPatch) {
        this.f_excelPath = f_excelPatch;
    }

    public String getF_PDFPATH() {
        return this.f_pdfPath;
    }

    @JsonProperty(value="F_PDFPATH")
    public void setF_PDFPATH(String f_pdfPath) {
        this.f_pdfPath = f_pdfPath;
    }
}


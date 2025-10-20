/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.dc.datamapping.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataRefMappingCacheDTO {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="CODE")
    private String code;
    @JsonProperty(value="ODS_CODE")
    private String odsCode;
    @JsonProperty(value="ODS_NAME")
    private String odsName;
    @JsonProperty(value="ODS_ASSISTCODE")
    private String odsAssistcode;

    public DataRefMappingCacheDTO(String id, String code, String odsCode, String odsName) {
        this.id = id;
        this.code = code;
        this.odsCode = odsCode;
        this.odsName = odsName;
    }

    public DataRefMappingCacheDTO() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOdsCode() {
        return this.odsCode;
    }

    public void setOdsCode(String odsCode) {
        this.odsCode = odsCode;
    }

    public String getOdsName() {
        return this.odsName;
    }

    public void setOdsName(String odsName) {
        this.odsName = odsName;
    }

    public String getOdsAssistcode() {
        return this.odsAssistcode;
    }

    public void setOdsAssistcode(String odsAssistcode) {
        this.odsAssistcode = odsAssistcode;
    }
}


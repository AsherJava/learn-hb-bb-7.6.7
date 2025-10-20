/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.ent.bamboocloud.bim.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GeneralDTO {
    private String bimRequestId;
    private String resultCode;
    private String message;

    public GeneralDTO() {
    }

    public GeneralDTO(String bimRequestId, String resultCode, String message) {
        this.bimRequestId = bimRequestId;
        this.resultCode = resultCode;
        this.message = message;
    }

    public String getBimRequestId() {
        return this.bimRequestId;
    }

    public GeneralDTO setBimRequestId(String bimRequestId) {
        this.bimRequestId = bimRequestId;
        return this;
    }

    public String getResultCode() {
        return this.resultCode;
    }

    public GeneralDTO setResultCode(String resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public GeneralDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


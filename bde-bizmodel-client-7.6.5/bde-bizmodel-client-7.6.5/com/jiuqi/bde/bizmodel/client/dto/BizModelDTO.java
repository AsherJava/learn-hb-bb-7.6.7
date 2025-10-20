/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.bizmodel.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BizModelDTO {
    private String id;
    private String code;
    private String name;
    private String computationModelCode;
    private String computationModelName;
    private Integer stopFlag;
    private BigDecimal ordinal;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComputationModelCode() {
        return this.computationModelCode;
    }

    public void setComputationModelCode(String computationModelCode) {
        this.computationModelCode = computationModelCode;
    }

    public String getComputationModelName() {
        return this.computationModelName;
    }

    public void setComputationModelName(String computationModelName) {
        this.computationModelName = computationModelName;
    }

    public Integer getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }
}


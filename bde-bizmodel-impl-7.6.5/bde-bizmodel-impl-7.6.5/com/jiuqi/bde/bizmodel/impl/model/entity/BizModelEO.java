/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class BizModelEO
implements Serializable {
    private static final long serialVersionUID = -7940379955365756669L;
    private String id;
    private String code;
    private String name;
    private String computationModelCode;
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

    public String toString() {
        return "BizModelEO [id=" + this.id + ", code=" + this.code + ", name=" + this.name + ", computationModelCode=" + this.computationModelCode + ", stopFlag=" + this.stopFlag + ", ordinal=" + this.ordinal + "]";
    }
}


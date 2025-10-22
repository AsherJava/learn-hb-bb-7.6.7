/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.treebean;

import java.math.BigDecimal;
import java.util.UUID;

public class FieldTableObj {
    private UUID id;
    private String code;
    private String title;
    private UUID parentId;
    private Integer baseUnit;
    private BigDecimal ratio;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getParentId() {
        return this.parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public Integer getBaseUnit() {
        return this.baseUnit;
    }

    public void setBaseUnit(Integer baseUnit) {
        this.baseUnit = baseUnit;
    }

    public BigDecimal getRatio() {
        return this.ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }
}


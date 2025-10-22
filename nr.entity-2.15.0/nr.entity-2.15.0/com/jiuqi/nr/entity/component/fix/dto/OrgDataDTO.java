/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.component.fix.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrgDataDTO {
    private String id;
    private String code;
    private String orgCode;
    private String parentCode;
    private String name;
    private String parents;
    private Date validtime;
    private Date invalidtime;
    private BigDecimal ordinal;
    private BigDecimal newOrdinal;

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

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public Date getValidtime() {
        return this.validtime;
    }

    public void setValidtime(Date validtime) {
        this.validtime = validtime;
    }

    public Date getInvalidtime() {
        return this.invalidtime;
    }

    public void setInvalidtime(Date invalidtime) {
        this.invalidtime = invalidtime;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public BigDecimal getNewOrdinal() {
        return this.newOrdinal;
    }

    public void setNewOrdinal(BigDecimal newOrdinal) {
        this.newOrdinal = newOrdinal;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  tk.mybatis.mapper.annotation.Order
 */
package com.jiuqi.va.domain.datamodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Id;
import tk.mybatis.mapper.annotation.Order;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataModelGroupExternalDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private String name;
    private String title;
    private String biztype;
    private String parentname;
    @Order(value="asc", priority=1)
    private BigDecimal ordernum;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentname() {
        return this.parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getBiztype() {
        return this.biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public BigDecimal getOrdernum() {
        return this.ordernum;
    }

    public void setOrdernum(BigDecimal ordernum) {
        this.ordernum = ordernum;
    }
}


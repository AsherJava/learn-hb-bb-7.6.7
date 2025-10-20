/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 *  tk.mybatis.mapper.annotation.Order
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;
import tk.mybatis.mapper.annotation.Order;

@Table(name="ORG_IMPORT_TEMPLATE")
public class OrgImportTemplateDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private String code;
    private String name;
    @Order(value="asc", priority=1)
    private BigDecimal ordernum;
    private Object templatedata;
    private List<Object> importFields;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getOrdernum() {
        return this.ordernum;
    }

    public void setOrdernum(BigDecimal ordernum) {
        this.ordernum = ordernum;
    }

    public Object getTemplatedata() {
        return this.templatedata;
    }

    public void setTemplatedata(Object templatedata) {
        this.templatedata = templatedata;
    }

    public List<Object> getImportFields() {
        return this.importFields;
    }

    public void setImportFields(List<Object> importFields) {
        this.importFields = importFields;
    }
}


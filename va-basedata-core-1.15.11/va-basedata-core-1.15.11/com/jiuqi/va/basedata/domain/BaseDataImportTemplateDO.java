/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  tk.mybatis.mapper.annotation.Order
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.Id;
import tk.mybatis.mapper.annotation.Order;

public class BaseDataImportTemplateDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private String code;
    private String name;
    private Integer fixed;
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

    public Integer getFixed() {
        return this.fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public List<Object> getImportFields() {
        return this.importFields;
    }

    public void setImportFields(List<Object> importFields) {
        this.importFields = importFields;
    }
}


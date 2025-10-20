/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;

public class SublistImportTemplateDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String id;
    private BigDecimal ver;
    private String billType;
    private String tableName;
    private String templateData;

    public String getBillType() {
        return this.billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTemplateData() {
        return this.templateData;
    }

    public void setTemplateData(String templateData) {
        this.templateData = templateData;
    }

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public String getId() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }
}


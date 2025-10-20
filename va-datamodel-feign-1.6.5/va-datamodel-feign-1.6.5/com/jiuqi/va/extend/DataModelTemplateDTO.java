/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.extend;

import com.jiuqi.va.mapper.domain.TenantDO;

public class DataModelTemplateDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String templateName;
    private String tableName;

    public DataModelTemplateDTO() {
    }

    public DataModelTemplateDTO(String templateName) {
        this.templateName = templateName;
    }

    public DataModelTemplateDTO(String templateName, String tableName) {
        this.templateName = templateName;
        this.tableName = tableName;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}


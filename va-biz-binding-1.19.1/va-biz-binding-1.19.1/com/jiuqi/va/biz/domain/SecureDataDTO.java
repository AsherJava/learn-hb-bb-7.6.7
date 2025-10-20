/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.biz.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class SecureDataDTO
extends TenantDO {
    private String bizCode;
    private String id;
    private String tableName;
    private String fieldName;

    public String getBizCode() {
        return this.bizCode;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getId() {
        return this.id;
    }

    public String getTableName() {
        return this.tableName;
    }
}


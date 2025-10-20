/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.billcode.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class BillCodeCheckExistDO
extends TenantDO {
    private static final long serialVersionUID = -2158450925918848719L;
    private String tableName;
    private String defineCode;

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}


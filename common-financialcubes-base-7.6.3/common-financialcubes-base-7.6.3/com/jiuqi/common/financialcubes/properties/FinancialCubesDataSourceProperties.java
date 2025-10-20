/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesDataSourceProperties {
    @Value(value="${spring.datasource.dbType}")
    private String currentDbType;
    @Value(value="${jiuqi.nvwa.datasources.gcreport.dbType:}")
    private String gcDbType;

    public String getCurrentDbType() {
        return this.currentDbType;
    }

    public void setCurrentDbType(String currentDbType) {
        this.currentDbType = currentDbType;
    }

    public String getGcDbType() {
        return this.gcDbType;
    }

    public void setGcDbType(String gcDbType) {
        this.gcDbType = gcDbType;
    }
}


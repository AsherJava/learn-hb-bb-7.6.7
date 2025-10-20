/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datasource.database;

import com.jiuqi.va.query.datasource.database.QueryDataBaseHandler;

public abstract class AbstractQueryDataBaseHandler
implements QueryDataBaseHandler {
    private String typeName;
    private String description;
    private String productName;
    private String driver;

    protected AbstractQueryDataBaseHandler(String typeName, String description, String productName, String driver) {
        this.typeName = typeName;
        this.description = description;
        this.productName = productName;
        this.driver = driver;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getProductName() {
        return this.productName;
    }

    @Override
    public String getDriverName() {
        return this.driver;
    }
}


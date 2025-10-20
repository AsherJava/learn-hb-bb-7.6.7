/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datasource.enumerate;

public enum DataBaseTypeEnum {
    ORACLE("ORACLE", "oracle"),
    MYSQL("MYSQL", "mysql"),
    HANA("HANA", "hdb"),
    SQL_SERVER("SQL_SERVER", "microsoft sql server");

    private String typeName;
    private String productName;

    private DataBaseTypeEnum(String typeName, String productName) {
        this.typeName = typeName;
        this.productName = productName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getProductName() {
        return this.productName;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum DataBaseTypeEnum {
    ORACLE("ORACLE", "oracle"),
    MYSQL("MYSQL", "mysql"),
    HANA("HANA", "hdb"),
    SQL_SERVER("SQL_SERVER", "microsoft sql server"),
    POSTGRESQL("POSTGRESQL", "postgresql"),
    UXDB("UXDB", "uxdb"),
    POLARDB("POLARDB", "polardb"),
    GAUSS("GAUSS", "gauss"),
    KINGBASE("KINGBASE", "kingbase"),
    HIGHGO("HIGHGO", "highgo");

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

    public static DataBaseTypeEnum getDataBaseTypeEnumBydbType(String dbType) {
        for (DataBaseTypeEnum dataBaseTypeEnum : DataBaseTypeEnum.values()) {
            if (!dataBaseTypeEnum.getTypeName().equals(dbType.toUpperCase())) continue;
            return dataBaseTypeEnum;
        }
        return null;
    }
}


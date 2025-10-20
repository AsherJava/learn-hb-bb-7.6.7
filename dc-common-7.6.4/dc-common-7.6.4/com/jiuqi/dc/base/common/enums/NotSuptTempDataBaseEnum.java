/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

public enum NotSuptTempDataBaseEnum {
    MYSQL("MYSQL"),
    POSTGRESQL("POSTGRESQL"),
    GAUSSDB100("'GAUSSDB100");

    private String dbTypeName;

    private NotSuptTempDataBaseEnum(String dbTypeName) {
        this.dbTypeName = dbTypeName;
    }

    public static Boolean isNotSuptTempDataBase(String typeName) {
        for (NotSuptTempDataBaseEnum notSuptTempDataBaseEnum : NotSuptTempDataBaseEnum.values()) {
            if (!notSuptTempDataBaseEnum.dbTypeName.equalsIgnoreCase(typeName)) continue;
            return true;
        }
        return false;
    }
}


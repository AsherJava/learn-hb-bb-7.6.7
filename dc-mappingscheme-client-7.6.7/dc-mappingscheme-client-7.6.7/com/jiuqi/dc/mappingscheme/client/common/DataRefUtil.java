/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.common;

import java.util.Map;

public class DataRefUtil {
    public static String getRefTableName(String tableName) {
        return String.format("REF_%1$s", tableName);
    }

    public static String getOdsPrefixName(String str) {
        return String.format("ODS_%1$s", str);
    }

    public static String getOdsTableName(String dataSchemeCode, String tableName) {
        return String.format("ODS_%1$s_%2$s", dataSchemeCode, tableName);
    }

    public static String getNodeLabel(String code, String name) {
        return String.format("%1$s %2$s", code, name);
    }

    public static String getCode(Map<String, Object> refData) {
        return DataRefUtil.getValueStr(refData, "CODE");
    }

    public static String getOdsCode(Map<String, Object> refData) {
        return DataRefUtil.getValueStr(refData, "ODS_CODE");
    }

    public static String getValueStr(Map<String, Object> refData, String fieldName) {
        if (refData.get(fieldName) == null) {
            return "#";
        }
        return String.valueOf(refData.get(fieldName));
    }

    public static String getTableName(String itemFieldName) {
        String tableName;
        switch (itemFieldName) {
            case "UNITCODE": {
                tableName = "MD_ORG";
                break;
            }
            case "SUBJECTCODE": {
                tableName = "MD_ACCTSUBJECT";
                break;
            }
            case "CURRENCYCODE": {
                tableName = "MD_CURRENCY";
                break;
            }
            case "CFITEMCODE": {
                tableName = "MD_CFITEM";
                break;
            }
            default: {
                tableName = itemFieldName;
            }
        }
        return tableName;
    }

    public static String getFieldName(String tableName) {
        String fieldName;
        switch (tableName) {
            case "MD_ACCTSUBJECT": {
                fieldName = "SUBJECTCODE";
                break;
            }
            case "MD_CURRENCY": {
                fieldName = "CURRENCYCODE";
                break;
            }
            case "MD_CFITEM": {
                fieldName = "CFITEMCODE";
                break;
            }
            default: {
                fieldName = tableName;
            }
        }
        return fieldName;
    }
}


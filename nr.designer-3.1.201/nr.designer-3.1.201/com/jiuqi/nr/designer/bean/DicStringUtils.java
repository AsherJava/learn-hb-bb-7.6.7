/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.bean;

import org.springframework.stereotype.Component;

@Component
public class DicStringUtils {
    public static String[] getRefs(Object refs) {
        return refs == null ? null : refs.toString().split(";");
    }

    public static String getSortZBString(String[] sortList) {
        String sort = "";
        if (sortList == null) {
            return sort;
        }
        for (int i = 0; i < sortList.length; ++i) {
            sort = sort + sortList[i].toUpperCase();
            sort = sort + "+";
            if (i == sortList.length - 1) continue;
            sort = sort + ";";
        }
        return sort;
    }

    public static String buildExcelIndex(Integer col, Integer row) {
        return row + "_" + col;
    }

    public static String buildExcelValue(String value, String code) {
        return value + "_" + (code != null ? code.toLowerCase() : code);
    }

    public static Integer getExcelRowIndex(String index) {
        String substring = index.substring(0, index.indexOf("_"));
        return Integer.valueOf(substring);
    }

    public static Integer getExcelColIndex(String index) {
        String substring = index.substring(index.indexOf("_") + 1);
        return Integer.valueOf(substring);
    }
}


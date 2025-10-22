/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gc.financialcubes.query.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import java.util.ArrayList;
import java.util.Map;

public class PenetrationValueUtil {
    public static String removeObjectCodeSuffix(String jsonKey) {
        if (jsonKey.endsWith(".OBJECTCODE")) {
            jsonKey = jsonKey.replaceFirst("\\.OBJECTCODE$", "");
        }
        return jsonKey;
    }

    public static void checkMutilValue(String key, String value) {
        if (value != null && value.contains(",")) {
            String[] splitResult = value.split(",");
            if (key.equals("MD_ACCTSUBJECT.OBJECTCODE") || key.endsWith(".CFITEMCODE") || key.endsWith(".SUBJECTCODE")) {
                return;
            }
            if (key.equals("P_TIMEKEY") && splitResult.length == 2 && splitResult[0].equals(splitResult[1]) || splitResult.length == 3) {
                return;
            }
            throw new BusinessRuntimeException("\u6b64\u5b57\u6bb5\u4e0d\u652f\u6301\u591a\u503c:" + key);
        }
    }

    public static String extractOrgType(String sqlCondition) {
        String orgType;
        String keyWord = "md_gcorgtype = '";
        int startIndex = sqlCondition.indexOf(keyWord) + keyWord.length();
        int endIndex = sqlCondition.indexOf("'", startIndex);
        if (startIndex > keyWord.length() - 1 && endIndex != -1 && !"NONE".equals(orgType = sqlCondition.substring(startIndex, endIndex))) {
            return orgType;
        }
        return null;
    }

    public static int convertStr2Type(String strType) {
        int periodType = -1;
        switch (strType) {
            case "N": {
                periodType = 1;
                break;
            }
            case "H": {
                periodType = 2;
                break;
            }
            case "J": {
                periodType = 3;
                break;
            }
            case "Y": {
                periodType = 4;
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u65f6\u671f\u7c7b\u578b\u5f02\u5e38: " + strType);
            }
        }
        return periodType;
    }

    public static int convertMonthToType(int type, int month) {
        int result = -1;
        switch (type) {
            case 1: {
                result = 1;
                break;
            }
            case 2: {
                result = month <= 6 ? 1 : 2;
                break;
            }
            case 3: {
                if (month >= 1 && month <= 3) {
                    result = 1;
                    break;
                }
                if (month >= 4 && month <= 6) {
                    result = 2;
                    break;
                }
                if (month >= 7 && month <= 9) {
                    result = 3;
                    break;
                }
                if (month < 10 || month > 12) break;
                result = 4;
                break;
            }
            case 4: {
                result = month;
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u6b64\u65f6\u671f\u7c7b\u578b: " + type);
            }
        }
        return result;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getTableName(Map<String, Object> linkmsgMap) {
        String tableName = "";
        if (linkmsgMap.containsKey("ZBFULLNAMES")) {
            ArrayList fullNames = (ArrayList)linkmsgMap.get("ZBFULLNAMES");
            if (fullNames == null) throw new BusinessRuntimeException("ZBFULLNAMES \u5b57\u6bb5\u5185\u5bb9\u4e3a\u7a7a");
            if (fullNames.isEmpty()) throw new BusinessRuntimeException("ZBFULLNAMES \u5b57\u6bb5\u5185\u5bb9\u4e3a\u7a7a");
            if (fullNames.get(0) == null) throw new BusinessRuntimeException("ZBFULLNAMES \u5b57\u6bb5\u5185\u5bb9\u4e3a\u7a7a");
            return ((String)fullNames.get(0)).split("\\.")[0];
        }
        if (!linkmsgMap.containsKey("ZBNAME")) throw new BusinessRuntimeException("\u7f3a\u5c11 ZBFULLNAMES \u6216 ZBNAME \u5b57\u6bb5");
        ArrayList fullNames = (ArrayList)linkmsgMap.get("ZBNAME");
        if (fullNames == null) throw new BusinessRuntimeException("ZBNAME \u5b57\u6bb5\u5185\u5bb9\u4e3a\u7a7a");
        if (fullNames.isEmpty()) throw new BusinessRuntimeException("ZBNAME \u5b57\u6bb5\u5185\u5bb9\u4e3a\u7a7a");
        if (fullNames.get(0) == null) throw new BusinessRuntimeException("ZBNAME \u5b57\u6bb5\u5185\u5bb9\u4e3a\u7a7a");
        return ((String)fullNames.get(0)).split("\\.")[0];
    }

    private PenetrationValueUtil() {
        throw new BusinessRuntimeException("\u5de5\u5177\u7c7b\u4e0d\u80fd\u88ab\u521d\u59cb\u5316");
    }
}


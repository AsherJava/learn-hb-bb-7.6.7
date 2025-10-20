/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.exception.BudgetException
 */
package com.jiuqi.budget.dimexp;

import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.dimexp.DimCellExp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DimExpUtil {
    private static final String TRAINF_REGEX = "[A-Za-z0-9_]+\\[((maxRow|\\d+|\\*)([+|-]\\d+)?),((maxCol|\\d+|\\*)([+|-]\\d+)?)(,\\'(.)*\\')?(,(.)*)?(,''|,sum|,count|,avg|,min|,max|,first|,last)?\\]";
    private static final Pattern TRAINF_PATTERN = Pattern.compile("[A-Za-z0-9_]+\\[((maxRow|\\d+|\\*)([+|-]\\d+)?),((maxCol|\\d+|\\*)([+|-]\\d+)?)(,\\'(.)*\\')?(,(.)*)?(,''|,sum|,count|,avg|,min|,max|,first|,last)?\\]");

    private DimExpUtil() {
        throw new BudgetException("Utility class");
    }

    public static String getTableName(String dimExp) {
        int index = dimExp.indexOf(91);
        if (index > -1) {
            return dimExp.substring(0, index);
        }
        throw new BudgetException("\u7ef4\u5ea6\u8868\u8fbe\u5f0f\u683c\u5f0f\u6709\u8bef");
    }

    public static String getMeasCode(String dimExp) {
        String restStr = dimExp.substring(dimExp.indexOf(91) + 1, dimExp.indexOf(93));
        int index = restStr.indexOf(44);
        if (index > -1) {
            return restStr.substring(0, index);
        }
        return restStr;
    }

    public static Map<String, String> getDimValMap(String dimExp) {
        String restStr = dimExp.substring(dimExp.indexOf(91) + 1, dimExp.indexOf(93));
        int index = restStr.indexOf(44);
        if (index > -1) {
            LinkedHashMap<String, String> dimValMap = new LinkedHashMap<String, String>();
            String dimStr = restStr.substring(index + 1);
            String[] dimArr = dimStr.split(",");
            for (int i = 0; i < dimArr.length; ++i) {
                if (i <= 0 || dimArr[i].contains("=")) continue;
                int n = i - 1;
                dimArr[n] = dimArr[n] + "," + dimArr[i];
                dimArr[i] = "";
            }
            ArrayList<String> dimList = new ArrayList<String>(Arrays.asList(dimArr));
            for (int i = 0; i < dimList.size(); ++i) {
                if (!dimList.get(i).isEmpty()) continue;
                dimList.remove(i);
                --i;
            }
            for (String dim : dimList) {
                if (!dim.contains("=")) continue;
                String str = dim.split("=")[1];
                str = str.substring(1, str.length() - 1);
                dimValMap.put(dim.split("=")[0], str);
            }
            return dimValMap;
        }
        return Collections.emptyMap();
    }

    public static DimCellExp getDimCellInfo(String dimExp) {
        DimCellExp dimCellExp = new DimCellExp();
        dimCellExp.setTableName(DimExpUtil.getTableName(dimExp));
        dimCellExp.setMeasCode(DimExpUtil.getMeasCode(dimExp));
        dimCellExp.setDimValMap(DimExpUtil.getDimValMap(dimExp));
        return dimCellExp;
    }

    public static String getOffSetStr(String dimExp) {
        if (DimExpUtil.checkIsXY(dimExp)) {
            return null;
        }
        String restStr = dimExp.substring(dimExp.indexOf(91) + 1, dimExp.indexOf(93));
        int index = restStr.indexOf(44);
        if (index > -1) {
            String dimStr = restStr.substring(index + 1);
            String[] dimArr = dimStr.split(",");
            String offSetStr = "";
            for (int i = 0; i < dimArr.length; ++i) {
                if (dimArr[i].contains("=") || dimArr[i].equals("sum")) continue;
                offSetStr = offSetStr + dimArr[i] + ",";
            }
            if (!offSetStr.isEmpty()) {
                offSetStr = offSetStr.substring(0, offSetStr.lastIndexOf(","));
            }
            if (offSetStr.contains(",")) {
                String[] split;
                for (String s : split = offSetStr.split(",")) {
                    if (s.matches("(\\+|\\-)?\\d+[A-Z]$")) continue;
                    return "";
                }
            } else if (!offSetStr.matches("(\\+|\\-)?\\d+[A-Z]$")) {
                return "";
            }
            return offSetStr;
        }
        return "";
    }

    private static boolean checkIsXY(String formulaExpression) {
        Matcher matcher = TRAINF_PATTERN.matcher(formulaExpression);
        return matcher.find();
    }

    public static String buildDimCellExp(DimCellExp cellInfo) {
        String tableName = cellInfo.getTableName();
        String measCode = cellInfo.getMeasCode();
        Map<String, String> dimValMap = cellInfo.getDimValMap();
        StringBuilder sb = new StringBuilder(tableName).append("[").append(measCode);
        if (dimValMap != null && !dimValMap.isEmpty()) {
            ArrayList<String> keys = new ArrayList<String>(dimValMap.keySet());
            Collections.sort(keys);
            for (String key : keys) {
                sb.append(",").append(key).append("=\"").append(dimValMap.get(key) + "\"");
            }
        }
        return sb.append("]").toString();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.gcreport.formsetting.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.nr.definition.common.DataRegionKind;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;

public class CodeGenerator {
    private static char[] abcStr = new char[]{'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static String codeStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateFieldCode(String tableName, List<String> fieldCodes, int colIndex) {
        String fieldCode;
        while (fieldCodes.contains(fieldCode = tableName + "_" + CodeGenerator.nextCode(colIndex))) {
            ++colIndex;
        }
        return fieldCode;
    }

    private static String nextCode(int colIndex) {
        if (colIndex < 0) {
            return "NEG";
        }
        String colTitle = "";
        if (colIndex <= 26) {
            colTitle = String.valueOf(abcStr[colIndex]);
        } else {
            int ss;
            int ys = -1;
            for (ss = colIndex; ss > 26; ss /= 26) {
                if (ys == 0) {
                    ys = ss % 26;
                    --ys;
                } else {
                    ys = ss % 26;
                }
                colTitle = abcStr[ys] + colTitle;
            }
            if (ys == 0) {
                --ss;
            }
            if (ss != 0) {
                colTitle = abcStr[ss] + colTitle;
            }
        }
        return colTitle;
    }

    public static String nextTableCode(DataRegionKind regionKind, int left, int top) {
        String floatRegionCode = "";
        if (regionKind == DataRegionKind.DATA_REGION_COLUMN_LIST) {
            floatRegionCode = "F" + left;
        } else if (regionKind == DataRegionKind.DATA_REGION_ROW_LIST) {
            floatRegionCode = "F" + top;
        }
        String tableCode = CodeGenerator.newUniqueCode() + (floatRegionCode != "" ? "_" + floatRegionCode : "");
        while (CodeGenerator.isExistTableCode(tableCode)) {
            tableCode = CodeGenerator.generateNewStringByTemplet(tableCode);
        }
        return tableCode;
    }

    private static String newUniqueCode() {
        char[] re = new char[8];
        long timestamp = new Date().getTime();
        int i = 0;
        while (timestamp > 0L) {
            re[7 - i] = codeStr.charAt((int)(timestamp % 36L));
            timestamp /= 36L;
            ++i;
        }
        return new String(re);
    }

    private static boolean isExistTableCode(String tableCode) {
        IDataDefinitionDesignTimeController dataDefinitionDesignTimeController = (IDataDefinitionDesignTimeController)SpringContextUtils.getBean(IDataDefinitionDesignTimeController.class);
        try {
            return dataDefinitionDesignTimeController.queryTableDefinesByCode(tableCode) != null;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u8868\u540d\u4e3a\u201c" + tableCode + "\u201d\u7684\u8868\u5b9a\u4e49\u5931\u8d25\u3002", (Throwable)e);
        }
    }

    private static String generateNewStringByTemplet(String tableCode) {
        if (StringUtils.isEmpty(tableCode)) {
            return "1";
        }
        if (tableCode.length() == 1) {
            return tableCode + "_1";
        }
        int lastIndexOfSplitter = tableCode.lastIndexOf(95);
        if (lastIndexOfSplitter >= 0) {
            if (lastIndexOfSplitter == tableCode.length() - 1) {
                return tableCode + "1";
            }
            String rearStr = tableCode.substring(lastIndexOfSplitter + 1, tableCode.length());
            boolean isNumber = true;
            try {
                Integer.valueOf(rearStr);
            }
            catch (NumberFormatException e) {
                isNumber = false;
            }
            if (isNumber) {
                return tableCode.substring(0, lastIndexOfSplitter + 1) + (Integer.valueOf(rearStr) + 1);
            }
        }
        return tableCode + "_1";
    }
}


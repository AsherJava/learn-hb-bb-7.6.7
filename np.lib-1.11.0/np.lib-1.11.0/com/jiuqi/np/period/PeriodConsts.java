/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.IntValue
 */
package com.jiuqi.np.period;

import com.jiuqi.bi.util.IntValue;
import java.util.HashMap;

public class PeriodConsts {
    public static final String PERIOD_DIMENSION = "DATATIME";
    public static final String PERIOD_FIELD_NAME = "DATATIME";
    public static final String DEFAULT_PERIOD = "1900N0001";
    public static final int PERIOD_TYPE_DEFAULT = 0;
    public static final int PERIOD_TYPE_YEAR = 1;
    public static final int PERIOD_TYPE_HALFYEAR = 2;
    public static final int PERIOD_TYPE_SEASON = 3;
    public static final int PERIOD_TYPE_MONTH = 4;
    public static final int PERIOD_TYPE_TENDAY = 5;
    public static final int PERIOD_TYPE_DAY = 6;
    public static final int PERIOD_TYPE_WEEK = 7;
    public static final int PERIOD_TYPE_CUSTOM = 8;
    public static final int PERIOD_TYPE_MIN = 1;
    public static final int PERIOD_TYPE_MAX = 8;
    public static final String IRREGULAR_YEAR = "0000";
    static int[] codeToTypeMap;
    static HashMap titleToTypeMap;
    static final int[] PERIOD_TYPE_CODEs;
    static final String[] PERIOD_TYPE_TITLEs;
    static final String[] PERIOD_TYPE_TITLE1s;
    private static final int maxPeriodTypeLength;
    static final int[] PERIOD_TYPE_HIGH_BOUNDs;

    private PeriodConsts() {
    }

    public static int typeToCode(int type) {
        return PERIOD_TYPE_CODEs[type];
    }

    public static int codeToType(int code) {
        return codeToTypeMap[code];
    }

    public static String typeToTitle(int type) {
        return PERIOD_TYPE_TITLEs[type];
    }

    public static int titleToType(String title) {
        Integer type = (Integer)titleToTypeMap.get(title);
        return type != null ? type : 0;
    }

    public static boolean parsePeriodEndingTail(String periodData, IntValue tailStart, IntValue periodType) {
        int maxLen = periodData.length();
        int tail = maxLen - maxPeriodTypeLength;
        if (tail < 0) {
            tail = 0;
        }
        while (tail < maxLen) {
            int type = PeriodConsts.titleToType(periodData.substring(tail));
            if (type != 0) {
                periodType.value = type;
                tailStart.value = tail;
                return true;
            }
            ++tail;
        }
        return false;
    }

    static {
        int i;
        codeToTypeMap = null;
        titleToTypeMap = new HashMap();
        PERIOD_TYPE_CODEs = new int[]{32, 78, 72, 74, 89, 88, 82, 90, 66};
        PERIOD_TYPE_TITLEs = new String[]{"\u65e0", "\u5e74", "\u534a\u5e74", "\u5b63", "\u6708", "\u65ec", "\u65e5", "\u5468", "\u671f"};
        PERIOD_TYPE_TITLE1s = new String[]{null, "\u5e74\u5ea6", null, "\u5b63\u5ea6", "\u6708\u4efd", null, "\u5929", "\u5468\u6b21", "\u671f\u95f4"};
        codeToTypeMap = new int[Math.max(122, 90) + 1];
        for (i = 0; i < PERIOD_TYPE_CODEs.length; ++i) {
            PeriodConsts.codeToTypeMap[Character.toUpperCase((int)PeriodConsts.PERIOD_TYPE_CODEs[i])] = i;
            PeriodConsts.codeToTypeMap[Character.toLowerCase((int)PeriodConsts.PERIOD_TYPE_CODEs[i])] = i;
        }
        for (i = 0; i < PERIOD_TYPE_TITLEs.length; ++i) {
            Integer type = new Integer(i);
            titleToTypeMap.put(PERIOD_TYPE_TITLEs[i], type);
            if (PERIOD_TYPE_TITLE1s[i] != null) {
                titleToTypeMap.put(PERIOD_TYPE_TITLE1s[i], type);
            }
            titleToTypeMap.put(String.valueOf((char)Character.toUpperCase(PERIOD_TYPE_CODEs[i])), type);
            titleToTypeMap.put(String.valueOf((char)Character.toLowerCase(PERIOD_TYPE_CODEs[i])), type);
        }
        int max = 0;
        for (String key : titleToTypeMap.keySet()) {
            if (max >= key.length()) continue;
            max = key.length();
        }
        maxPeriodTypeLength = max;
        PERIOD_TYPE_HIGH_BOUNDs = new int[]{0, 1, 2, 4, 12, 36, 366, 53, 9999};
    }
}


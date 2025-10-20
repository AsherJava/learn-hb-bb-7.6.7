/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.carryover.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;

public class CarryOverUtil {
    public static String convertPeriod(String periodStr, int acctYear, int periodType) {
        String acctPeriod;
        if (StringUtils.isEmpty((String)periodStr) || periodStr.length() != 9) {
            throw new BusinessRuntimeException("\u65f6\u671f\u5f02\u5e38\uff01");
        }
        if (periodType == 1) {
            acctPeriod = "01";
        } else if (periodType == 2) {
            acctPeriod = "02";
        } else if (periodType == 3) {
            acctPeriod = "04";
        } else if (periodType == 4) {
            acctPeriod = "12";
        } else {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + periodType);
        }
        return acctYear - 1 + periodStr.substring(4, 7) + acctPeriod;
    }

    public static String getQueryOrgPeriod(String defaultPeriod) {
        if (org.springframework.util.StringUtils.isEmpty(defaultPeriod)) {
            return "";
        }
        if (defaultPeriod.endsWith("00")) {
            if (defaultPeriod.contains("N")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "1";
            }
            if (defaultPeriod.contains("H")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "2";
            }
            if (defaultPeriod.contains("J")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 1) + "4";
            }
            if (defaultPeriod.contains("Y")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + "12";
            }
            if (defaultPeriod.contains("X")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + "36";
            }
            if (defaultPeriod.contains("Z")) {
                return defaultPeriod.substring(0, defaultPeriod.length() - 2) + "53";
            }
        }
        return defaultPeriod;
    }
}


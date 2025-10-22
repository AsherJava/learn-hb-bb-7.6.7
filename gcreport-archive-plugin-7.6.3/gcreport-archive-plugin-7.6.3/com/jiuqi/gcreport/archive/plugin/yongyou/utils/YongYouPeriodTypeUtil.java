/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.utils;

import com.jiuqi.common.base.BusinessRuntimeException;

public class YongYouPeriodTypeUtil {
    public static String getPeriodTypeName(int periodType) {
        switch (periodType) {
            case 1: {
                return "year";
            }
            case 2: {
                return "halfyear";
            }
            case 3: {
                return "season";
            }
            case 4: {
                return "month";
            }
        }
        return "unknown";
    }

    public static String getPeriodTitleName(int periodType) {
        switch (periodType) {
            case 1: {
                return "\u5e74\u62a5";
            }
            case 2: {
                return "\u534a\u5e74\u62a5";
            }
            case 3: {
                return "\u5b63\u62a5";
            }
            case 4: {
                return "\u6708\u62a5";
            }
        }
        return "\u672a\u77e5";
    }

    public static String getMonth(int period, int type) {
        int month;
        switch (type) {
            case 1: {
                month = 12;
                break;
            }
            case 2: {
                month = period * 6;
                break;
            }
            case 3: {
                month = period * 3;
                break;
            }
            case 4: {
                month = period;
                break;
            }
            default: {
                throw new IllegalArgumentException("\u65e0\u6548\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + type);
            }
        }
        return String.format("%02d", month);
    }

    public static String getArchiveType(String dateTime) {
        if (dateTime == null || dateTime.length() < 5) {
            throw new BusinessRuntimeException("\u65f6\u671f\u683c\u5f0f\u6709\u95ee\u9898");
        }
        char typeChar = dateTime.charAt(4);
        if (typeChar == 'N' || typeChar == 'Y' || typeChar == 'J' || typeChar == 'H') {
            return typeChar + "B";
        }
        throw new BusinessRuntimeException("\u65e5\u671f\u7c7b\u578b\u6709\u95ee\u9898: " + dateTime);
    }
}


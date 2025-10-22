/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.gcreport.aidocaudit.util;

import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class AidocauditPeriodUtil {
    private AidocauditPeriodUtil() {
        throw new UnsupportedOperationException("\u8be5\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316");
    }

    public static String formatDate(YearPeriodObject yp) {
        String dateStr = yp.getYtm();
        if (yp.getType() == 1) {
            dateStr = yp.getYear() + "\u5e74";
        } else if (yp.getType() == 4) {
            dateStr = yp.getYear() + "\u5e74" + yp.getPeriod() + "\u6708";
        }
        return dateStr;
    }

    public static String parseData(String dateStr, FormSchemeDefine formScheme) {
        int periodType = formScheme.getPeriodType().type();
        int year = 0;
        int period = 0;
        if (periodType == 1) {
            year = Integer.parseInt(dateStr.replace("\u5e74", ""));
            period = 1;
        } else if (periodType == 4) {
            String[] parts = dateStr.split("\u5e74");
            year = Integer.parseInt(parts[0]);
            period = Integer.parseInt(parts[1].replace("\u6708", ""));
        } else {
            return dateStr;
        }
        YearPeriodObject yearPeriodObject = new YearPeriodObject(formScheme.getKey(), year, periodType, period);
        return yearPeriodObject.getYtm();
    }
}


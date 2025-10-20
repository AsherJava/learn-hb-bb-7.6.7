/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.dataset.manager.TimeKeyHelper
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.html.message;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.manager.TimeKeyHelper;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PeriodFormatProcessor {
    private PeriodFormatProcessor() {
    }

    public static String processGlobalPeriod(String globalPeriod, DSField fieldInfo) {
        return PeriodFormatProcessor.processGlobalPeriod(globalPeriod, fieldInfo.getTimegranularity(), DataType.valueOf((int)fieldInfo.getValType()), fieldInfo.getDataPattern());
    }

    public static String processGlobalPeriod(String globalPeriod, TimeGranularity timeGranularity, DataType dataType, String dataPattern) {
        if (dataType == DataType.INTEGER) {
            return PeriodFormatProcessor.processGlobalPeriod_intType(globalPeriod, timeGranularity);
        }
        if (StringUtils.isEmpty((String)dataPattern)) {
            dataPattern = TimeKeyHelper.getDefaultDatePattern((TimeGranularity)timeGranularity);
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf1.parse(globalPeriod);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormatEx format = new DateFormatEx(dataPattern);
        return format.format(date);
    }

    private static String processGlobalPeriod_intType(String globalPeriod, TimeGranularity time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(format.parse(globalPeriod));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        if (time == TimeGranularity.YEAR) {
            return Integer.toString(calendar.get(1));
        }
        if (time == TimeGranularity.HALFYEAR) {
            int month = calendar.get(2) + 1;
            if (1 <= month && month <= 6) {
                return "1";
            }
            if (7 <= month && month <= 12) {
                return "2";
            }
            return null;
        }
        if (time == TimeGranularity.QUARTER) {
            int month = calendar.get(2) + 1;
            if (1 <= month && month <= 3) {
                return "1";
            }
            if (4 <= month && month <= 6) {
                return "2";
            }
            if (7 <= month && month <= 9) {
                return "3";
            }
            if (10 <= month && month <= 12) {
                return "4";
            }
            return null;
        }
        if (time == TimeGranularity.MONTH) {
            return calendar.get(2) + 1 + "";
        }
        if (time == TimeGranularity.XUN) {
            int day = calendar.get(5);
            if (1 <= day && day <= 10) {
                return "1";
            }
            if (11 <= day && day <= 20) {
                return "2";
            }
            if (21 <= day) {
                return "3";
            }
            return null;
        }
        if (time == TimeGranularity.DAY) {
            return Integer.toString(calendar.get(5));
        }
        return null;
    }
}


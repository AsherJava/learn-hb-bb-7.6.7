/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.manager.timekey;

import com.jiuqi.bi.dataset.DataType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class AbstractTimeKeyField {
    public abstract String generateTimekey(Object[] var1);

    static int parseValueToYear(Object value, int valType, SimpleDateFormat sdf) {
        int yearValue;
        if (value == null) {
            return -1;
        }
        if (valType == DataType.INTEGER.value()) {
            yearValue = ((Number)value).intValue();
        } else if (valType == DataType.DATETIME.value()) {
            Calendar c = (Calendar)value;
            yearValue = c.get(1);
        } else if (valType == DataType.STRING.value()) {
            String yearString = (String)value;
            if (sdf == null) {
                try {
                    if (yearString.length() > 4) {
                        yearValue = Integer.parseInt(yearString.substring(0, 4));
                    }
                    yearValue = Integer.parseInt(yearString);
                }
                catch (Exception e) {
                    return -1;
                }
            } else {
                try {
                    Date date = sdf.parse(yearString);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    yearValue = c.get(1);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        } else {
            return -1;
        }
        return yearValue;
    }

    static int parseValueToHalfyear(Object value, int valType, SimpleDateFormat sdf) {
        int halfyearValue;
        if (value == null) {
            return -1;
        }
        if (valType == DataType.INTEGER.value()) {
            halfyearValue = ((Number)value).intValue();
        } else if (valType == DataType.DATETIME.value()) {
            Calendar c = (Calendar)value;
            int month = c.get(2);
            halfyearValue = month <= 6 ? 1 : 7;
        } else if (valType == DataType.STRING.value()) {
            String yearString = (String)value;
            if (sdf == null) {
                try {
                    halfyearValue = Integer.parseInt(yearString);
                }
                catch (Exception e) {
                    return -1;
                }
            } else {
                try {
                    Date date = sdf.parse(yearString);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int month = c.get(2);
                    halfyearValue = month <= 6 ? 1 : 7;
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        } else {
            return -1;
        }
        return halfyearValue;
    }

    static int parseValueToQuarter(Object value, int valType, SimpleDateFormat sdf) {
        int quarterValue;
        if (value == null) {
            return -1;
        }
        if (valType == DataType.INTEGER.value()) {
            quarterValue = ((Number)value).intValue();
        } else if (valType == DataType.DATETIME.value()) {
            Calendar c = (Calendar)value;
            int month = c.get(2);
            quarterValue = month <= 3 ? 1 : (month <= 6 ? 4 : (month <= 9 ? 7 : 10));
        } else if (valType == DataType.STRING.value()) {
            String yearString = (String)value;
            if (sdf == null) {
                try {
                    quarterValue = Integer.parseInt(yearString);
                }
                catch (Exception e) {
                    return -1;
                }
            } else {
                try {
                    Date date = sdf.parse(yearString);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int month = c.get(2);
                    if (month <= 3) {
                        quarterValue = 1;
                    }
                    if (month <= 6) {
                        quarterValue = 4;
                    }
                    if (month <= 9) {
                        quarterValue = 7;
                    }
                    quarterValue = 10;
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        } else {
            return -1;
        }
        return quarterValue;
    }

    static int parseValueToMonth(Object value, int valType, SimpleDateFormat sdf) {
        int monthValue;
        if (value == null) {
            return -1;
        }
        if (valType == DataType.INTEGER.value()) {
            monthValue = ((Number)value).intValue();
        } else if (valType == DataType.DATETIME.value()) {
            Calendar c = (Calendar)value;
            monthValue = c.get(2) + 1;
        } else if (valType == DataType.STRING.value()) {
            String yearString = (String)value;
            if (sdf == null) {
                try {
                    monthValue = Integer.parseInt(yearString);
                }
                catch (Exception e) {
                    return -1;
                }
            } else {
                try {
                    Date date = sdf.parse(yearString);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    monthValue = c.get(2) + 1;
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        } else {
            return -1;
        }
        return monthValue;
    }

    static int parseValueToXun(Object value, int valType, SimpleDateFormat sdf) {
        int xunValue;
        if (value == null) {
            return -1;
        }
        if (valType == DataType.INTEGER.value()) {
            xunValue = ((Number)value).intValue();
        } else if (valType == DataType.DATETIME.value()) {
            Calendar c = (Calendar)value;
            int day = c.get(5);
            xunValue = day <= 10 ? 1 : (day <= 20 ? 11 : 21);
        } else if (valType == DataType.STRING.value()) {
            String yearString = (String)value;
            if (sdf == null) {
                try {
                    xunValue = Integer.parseInt(yearString);
                }
                catch (Exception e) {
                    return -1;
                }
            } else {
                try {
                    Date date = sdf.parse(yearString);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int day = c.get(5);
                    if (day <= 10) {
                        xunValue = 1;
                    }
                    if (day <= 20) {
                        xunValue = 11;
                    }
                    xunValue = 21;
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        } else {
            return -1;
        }
        return xunValue;
    }

    static int parseValueToDay(Object value, int valType, SimpleDateFormat sdf) {
        int dayValue;
        if (value == null) {
            return -1;
        }
        if (valType == DataType.INTEGER.value()) {
            dayValue = ((Number)value).intValue();
        } else if (valType == DataType.DATETIME.value()) {
            Calendar c = (Calendar)value;
            dayValue = c.get(5);
        } else if (valType == DataType.STRING.value()) {
            String yearString = (String)value;
            if (sdf == null) {
                try {
                    dayValue = Integer.parseInt(yearString);
                }
                catch (Exception e) {
                    return -1;
                }
            } else {
                try {
                    Date date = sdf.parse(yearString);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    dayValue = c.get(5);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        } else {
            return -1;
        }
        return dayValue;
    }
}


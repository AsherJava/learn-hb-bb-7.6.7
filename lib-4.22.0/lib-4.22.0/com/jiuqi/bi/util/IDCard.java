/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class IDCard {
    private static final int[] WEIGHS = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    private static final char[] IDCS = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    private static boolean isNumber(String s) {
        if (s != null && s.length() > 0) {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean isIDCardChar(String s) {
        if (s != null && (s.length() == 15 || s.length() == 18)) {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9' || c == 'X') continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private static Calendar IdCard15Date(String s) {
        String birthday;
        if (s != null && s.length() == 15 && IDCard.isNumber(birthday = s.substring(6, 12))) {
            int year = 1900 + Integer.parseInt(birthday.substring(0, 2));
            int month = Integer.parseInt(birthday.substring(2, 4));
            int day = Integer.parseInt(birthday.substring(4, 6));
            if (year < 1900 || year >= 2000 || month < 1 || month > 12 || day < 1 || day > 31) {
                return null;
            }
            GregorianCalendar calendar = new GregorianCalendar();
            switch (month) {
                case 4: 
                case 6: 
                case 9: 
                case 11: {
                    if (day <= 30) break;
                    return null;
                }
                case 2: {
                    if (!(calendar.isLeapYear(year) ? day > 29 : day > 28)) break;
                    return null;
                }
            }
            calendar.set(1, year);
            calendar.set(2, month - 1);
            calendar.set(5, day);
            return calendar;
        }
        return null;
    }

    private static Calendar IdCard18Date(String s) {
        String birthday;
        if (s != null && s.length() == 18 && IDCard.isNumber(birthday = s.substring(6, 14))) {
            int year = Integer.parseInt(birthday.substring(0, 4));
            int month = Integer.parseInt(birthday.substring(4, 6));
            int day = Integer.parseInt(birthday.substring(6, 8));
            if (year < 1900 || year >= 3000 || month < 1 || month > 12 || day < 1 || day > 31) {
                return null;
            }
            GregorianCalendar calendar = new GregorianCalendar();
            long currTime = calendar.getTimeInMillis();
            switch (month) {
                case 4: 
                case 6: 
                case 9: 
                case 11: {
                    if (day <= 30) break;
                    return null;
                }
                case 2: {
                    if (!(calendar.isLeapYear(year) ? day > 29 : day > 28)) break;
                    return null;
                }
            }
            calendar.set(1, year);
            calendar.set(2, month - 1);
            calendar.set(5, day);
            if (calendar.getTimeInMillis() > currTime) {
                return null;
            }
            return calendar;
        }
        return null;
    }

    public static char IDCardC(String s) {
        int index = 0;
        char result = '\u0000';
        if (s == null || s.length() != 17) {
            return result;
        }
        for (int i = 0; i < s.length(); ++i) {
            index += (s.charAt(i) - 48) * WEIGHS[i];
        }
        result = IDCS[index %= 11];
        return result;
    }

    public static boolean isIDCard(String s) {
        boolean result = false;
        if (s != null && IDCard.isIDCardChar(s)) {
            if (s.length() == 18) {
                result = IDCard.IdCard18Date(s) != null;
            } else if (s.length() == 15) {
                result = IDCard.IdCard15Date(s) != null;
            }
        }
        return result;
    }

    public static String IDCard18(String s) {
        if (IDCard.isIDCard(s)) {
            if (s.length() == 15) {
                StringBuffer result = new StringBuffer(s);
                result.insert(6, "19");
                result.append(IDCard.IDCardC(result.toString()));
                return result.toString();
            }
            return s;
        }
        return null;
    }

    public static Calendar getIDCardDate(String s) {
        Calendar result = null;
        if (s != null && IDCard.isIDCardChar(s)) {
            if (s.length() == 18) {
                result = IDCard.IdCard18Date(s);
            } else if (s.length() == 15) {
                result = IDCard.IdCard15Date(s);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(IDCard.getIDCardDate("420802750708151"));
        System.out.println(IDCard.getIDCardDate("430506200605091512"));
    }
}


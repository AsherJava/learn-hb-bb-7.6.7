/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StringUtils {
    public static String toViewString(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    public static boolean equalsAny(String str, String ... matchStrs) {
        if (str != null) {
            for (String matchStr : matchStrs) {
                if (!str.equals(matchStr)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean equalsIgnoreCaseAny(String str, String ... matchStrs) {
        if (str != null) {
            for (String matchStr : matchStrs) {
                if (!str.equalsIgnoreCase(matchStr)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean convertToBoolean(String str) {
        return StringUtils.equalsIgnoreCaseAny(str, "\u662f", "Y", "Yes", "T", "True") || StringUtils.convertToInteger(str, 0) != 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNull(String str) {
        return StringUtils.isEmpty(str) || "null".equalsIgnoreCase(str);
    }

    public static String parseEmpty(String str) {
        return StringUtils.parseEmpty(str, null);
    }

    public static String parseEmpty(String str, String def) {
        return StringUtils.isEmpty(str) ? def : str;
    }

    public static int convertToInteger(String str, int def) {
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            return def;
        }
    }

    public static Double convertToDouble(String str, Double def) {
        try {
            NumberFormat nf = NumberFormat.getInstance(Locale.SIMPLIFIED_CHINESE);
            if (nf instanceof DecimalFormat) {
                ((DecimalFormat)nf).setDecimalSeparatorAlwaysShown(true);
            }
            return nf.parse(str).doubleValue();
        }
        catch (Exception e) {
            return def;
        }
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch (Exception e) {
            return s;
        }
    }

    public static List<String> split(String source, String splitChar) {
        ArrayList<String> list = new ArrayList<String>();
        int index = source.indexOf(splitChar);
        while (index != -1) {
            String sub = source.substring(0, index);
            list.add(sub);
            source = source.substring(index + 1, source.length());
            index = source.indexOf(splitChar);
        }
        list.add(source);
        return list;
    }

    public static String join(Object[] array, String separator) {
        int arraySize;
        if (separator == null) {
            separator = "";
        }
        int bufSize = (arraySize = array.length) == 0 ? 0 : (array[0].toString().length() + separator.length()) * arraySize;
        StringBuffer buf = new StringBuffer(bufSize);
        for (int i = 0; i < arraySize; ++i) {
            if (i > 0) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    public static int compareStr(String value1, String value2) {
        if (value1 == null) {
            return -1;
        }
        if (value2 == null) {
            return 1;
        }
        return value1.compareTo(value2);
    }
}


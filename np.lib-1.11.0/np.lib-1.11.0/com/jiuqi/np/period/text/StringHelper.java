/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.text;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StringHelper {
    public static final char SPACE = ' ';
    public static final String nullStr = "";
    public static final String comma = ",";
    public static final String semicolon = ";";
    public static final String leftParenthesis = "(";
    public static final String rightParenthesis = ")";
    public static final String interrogation = "?";
    public static final String space = " ";
    public static final String bias = "\\";
    public static final String breakstr = "\b";
    public static final String returnstr = "\u000b";
    public static final String select = "select ";
    public static final String from = " from ";
    public static final String where = " where ";
    public static final String set = " set ";
    public static final String and = " and ";
    public static final String or = " or ";
    public static final String not = " not ";
    public static final String equal = "=";
    public static final String insert = "insert into ";
    public static final String update = "update ";
    public static final String delete = "delete from ";
    public static final String create = "create table ";
    public static final String drop = "drop table ";
    public static final String alter = "alter table ";
    private static final String[] SpaceStrings = new String[]{"", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          ", "           ", "            ", "             ", "              ", "               ", "                ", "                 ", "                  ", "                   ", "                    ", "                     ", "                      ", "                       ", "                        ", "                         ", "                          ", "                           ", "                            ", "                             "};

    private StringHelper() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.equals(nullStr);
    }

    public static boolean isNull(String value) {
        return value == null || value.equals(nullStr) || value.equalsIgnoreCase("null");
    }

    public static boolean isBoolean(String value) {
        if (value.equalsIgnoreCase("true")) {
            return true;
        }
        if (value.equalsIgnoreCase("yes")) {
            return true;
        }
        if (value.equalsIgnoreCase("T") || value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("01")) {
            return true;
        }
        return value.equalsIgnoreCase("\u662f") || value.equalsIgnoreCase("\u5bf9") || value.indexOf("\u662f") >= 0;
    }

    public static String getTrueValue(String value) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return "true";
        }
        if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("no")) {
            return "yes";
        }
        if (value.equalsIgnoreCase("T") || value.equalsIgnoreCase("F")) {
            return "T";
        }
        if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("N")) {
            return "Y";
        }
        if (value.equalsIgnoreCase("\u662f") || value.equalsIgnoreCase("\u5426")) {
            return "\u662f";
        }
        if (value.equalsIgnoreCase("1") || value.equalsIgnoreCase("0")) {
            return "1";
        }
        if (value.equalsIgnoreCase("01") || value.equalsIgnoreCase("00")) {
            return "01";
        }
        return "1";
    }

    public static String getFalseValue(String value) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return "false";
        }
        if (value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("no")) {
            return "no";
        }
        if (value.equalsIgnoreCase("T") || value.equalsIgnoreCase("F")) {
            return "F";
        }
        if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("N")) {
            return "N";
        }
        if (value.equalsIgnoreCase("\u662f") || value.equalsIgnoreCase("\u5426")) {
            return "\u5426";
        }
        if (value.equalsIgnoreCase("1") || value.equalsIgnoreCase("0")) {
            return "0";
        }
        if (value.equalsIgnoreCase("01") || value.equalsIgnoreCase("00")) {
            return "00";
        }
        return "0";
    }

    public static boolean notNull(String value) {
        return value != null && !value.equals(nullStr);
    }

    public static boolean readBool(String str, int index) {
        boolean result = false;
        if (str != null && str.length() > index && index >= 0) {
            result = str.charAt(index) == '1';
        }
        return result;
    }

    public static String writeBool(String str, int index, boolean value) {
        StringBuffer result;
        StringBuffer stringBuffer = result = str == null ? new StringBuffer() : new StringBuffer(str);
        if (index >= 0) {
            int nullcount = index - result.length();
            for (int i = 0; i <= nullcount; ++i) {
                result.append('#');
            }
            result.setCharAt(index, value ? (char)'1' : '0');
        }
        return result.toString();
    }

    public static String readString(String str, int beginIndex, int length) {
        String result = null;
        int endIndex = beginIndex + length;
        if (str != null && str.length() > beginIndex && beginIndex >= 0 && endIndex > beginIndex) {
            endIndex = str.length() >= endIndex ? endIndex : str.length();
            return str.substring(beginIndex, endIndex);
        }
        return result;
    }

    public static String writeString(String str, int beginIndex, int length, String value) {
        StringBuffer result;
        StringBuffer stringBuffer = result = str == null ? new StringBuffer() : new StringBuffer(str);
        if (beginIndex >= 0) {
            int nullcount = beginIndex + length - result.length();
            for (int i = 0; i < nullcount; ++i) {
                result.append(' ');
            }
            int len = value == null ? 0 : value.length();
            for (int i = 0; i < length; ++i) {
                char chr = i < len ? (char)value.charAt(i) : (char)' ';
                result.setCharAt(beginIndex + i, chr);
            }
        }
        return result.toString();
    }

    public static String[] StringToStrArray(String str, String split) {
        String[] result = null;
        if (StringHelper.isNull(str)) {
            return result;
        }
        if (StringHelper.isNull(split)) {
            result = new String[]{str};
            return result;
        }
        ArrayList<Object> list = new ArrayList<Object>();
        StringTokenizer st = new StringTokenizer(str, split);
        while (st.hasMoreElements()) {
            list.add(st.nextElement());
        }
        if (list.size() > 0) {
            result = new String[list.size()];
            for (int i = 0; i < result.length; ++i) {
                result[i] = (String)list.get(i);
            }
        } else {
            result = new String[]{str};
            return result;
        }
        return result;
    }

    public static boolean equalStringArray(String str1, String str2) {
        boolean result = false;
        if (StringHelper.isNull(str1) && StringHelper.isNull(str2)) {
            return true;
        }
        String[] list1 = StringHelper.StringToStrArray(str1);
        String[] list2 = StringHelper.StringToStrArray(str2);
        if (list1 == null && list2 == null) {
            return str1.equals(str2);
        }
        if (list1 == null && list2 != null || list1 != null && list2 == null) {
            return str1.equals(str2);
        }
        if (list1.length != list2.length) {
            return false;
        }
        for (int i = 0; i < list1.length; ++i) {
            result = false;
            for (int k = 0; k < list2.length; ++k) {
                if (!list1[i].equals(list2[k])) continue;
                result = true;
                list2[k] = "_$#@_*&^#%^";
                break;
            }
            if (!result) break;
        }
        return result;
    }

    public static String[] StringToStrArray(String str, char split) {
        int i;
        String[] result = null;
        int p = 0;
        if (str == null || str.equals(nullStr)) {
            return null;
        }
        if (str.indexOf(split) < 0) {
            result = new String[]{str};
            return result;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c != split) continue;
            if (i - p > 0) {
                String s = str.substring(p, i);
                list.add(s);
            } else {
                list.add(nullStr);
            }
            p = i + 1;
        }
        if (p > 0 && p < str.length()) {
            String s = str.substring(p, str.length());
            list.add(s);
        }
        if (list.size() > 0) {
            result = new String[list.size()];
            for (i = 0; i < list.size(); ++i) {
                result[i] = (String)list.get(i);
            }
        }
        return result;
    }

    public static String[] StringToStrArray(String str) {
        int i;
        String[] result = null;
        int p = 0;
        if (str == null || str.equals(nullStr)) {
            return null;
        }
        if (str.indexOf(59) < 0 && str.indexOf(44) < 0) {
            result = new String[]{str};
            return result;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c != ';' && c != ',') continue;
            if (i - p > 0) {
                String s = str.substring(p, i);
                list.add(s);
            } else {
                list.add(nullStr);
            }
            p = i + 1;
        }
        if (p > 0 && p < str.length()) {
            String s = str.substring(p, str.length());
            list.add(s);
        }
        if (list.size() > 0) {
            result = new String[list.size()];
            for (i = 0; i < list.size(); ++i) {
                result[i] = (String)list.get(i);
            }
        }
        return result;
    }

    public static String stringOfChar(char c, int length) {
        StringBuffer result = new StringBuffer(length);
        for (int i = 0; i < length; ++i) {
            result.append(c);
        }
        return result.toString();
    }

    public static String trimAll(String str, char c) {
        if (StringHelper.isNull(str)) {
            return null;
        }
        StringBuffer result = new StringBuffer(str.length());
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == c) continue;
            result.append(str.charAt(i));
        }
        return result.toString();
    }

    public static String trimAll(String str) {
        return StringHelper.trimAll(str, ' ');
    }

    public static String trimL(String str) {
        int pos;
        if (StringHelper.isNull(str)) {
            return null;
        }
        if (str.length() == 0 || str.charAt(0) != ' ') {
            return str;
        }
        for (int i = pos = 1; i < str.length(); ++i) {
            if (str.charAt(i) == ' ') continue;
            pos = i;
            break;
        }
        return str.substring(pos);
    }

    public static String trimR(String str) {
        int pos;
        if (StringHelper.isNull(str)) {
            return null;
        }
        int len = str.length();
        if (len == 0 || str.charAt(len - 1) != ' ') {
            return str;
        }
        for (int i = pos = len - 2; i >= 0; --i) {
            if (str.charAt(i) == ' ') continue;
            pos = i;
            break;
        }
        return str.substring(0, pos + 1);
    }

    public static String trimLR(String str) {
        if (StringHelper.isNull(str)) {
            return null;
        }
        return str.trim();
    }

    public static boolean safeEquals(String str1, String str2) {
        str1 = StringHelper.isNull(str1) ? nullStr : str1;
        str2 = StringHelper.isNull(str2) ? nullStr : str2;
        return str1.equals(str2);
    }

    public static boolean safeEqualsIgnoreCase(String str1, String str2) {
        str1 = StringHelper.isNull(str1) ? nullStr : str1;
        str2 = StringHelper.isNull(str2) ? nullStr : str2;
        return str1.equalsIgnoreCase(str2);
    }

    public static int safeCompare(String str1, String str2) {
        str1 = StringHelper.isNull(str1) ? nullStr : str1;
        str2 = StringHelper.isNull(str2) ? nullStr : str2;
        return str1.compareTo(str2);
    }

    public static int safeCompareIgnoreCase(String str1, String str2) {
        str1 = StringHelper.isNull(str1) ? nullStr : str1;
        str2 = StringHelper.isNull(str2) ? nullStr : str2;
        return str1.compareToIgnoreCase(str2);
    }

    public static int safeIndexOf(String str, String subStr) {
        str = StringHelper.isNull(str) ? nullStr : str;
        subStr = StringHelper.isNull(subStr) ? nullStr : subStr;
        return str.indexOf(subStr);
    }

    public static int safeIndexOfIgnoreCase(String str, String subStr) {
        str = StringHelper.isNull(str) ? nullStr : str.toLowerCase();
        subStr = StringHelper.isNull(subStr) ? nullStr : subStr.toLowerCase();
        return str.indexOf(subStr);
    }

    public static String quote(String s, char quote) {
        if (StringHelper.isEmpty(s)) {
            return s;
        }
        StringBuffer retStr = new StringBuffer();
        retStr.append(quote);
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == quote) {
                retStr.append(quote);
                retStr.append(quote);
                continue;
            }
            retStr.append(s.charAt(i));
        }
        retStr.append(quote);
        return retStr.toString();
    }

    public static boolean isSymbol(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c) || '_' == c || '$' == c) continue;
            return false;
        }
        return true;
    }

    public static boolean isIdentifier(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isLetterOrDigit(s.charAt(i)) || '_' == s.charAt(i)) continue;
            return false;
        }
        return Character.isLetter(s.charAt(0)) || '_' == s.charAt(0);
    }

    public static boolean isNumberString(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') continue;
            return false;
        }
        return true;
    }

    public static boolean isRegularString(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (!Character.isSpaceChar(ch) && (ch >= '\u00ff' || Character.isLetterOrDigit(ch))) continue;
            return false;
        }
        return true;
    }

    public static boolean hasSpaceChar(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isSpaceChar(s.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static String strExclude(String str, char ch) {
        if (str == null) {
            return null;
        }
        char[] chars = new char[str.length()];
        int index = 0;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c == ch) continue;
            chars[index++] = c;
        }
        return new String(chars, 0, index);
    }

    public static boolean charIsInCharSet(char value, char[] chrSet) {
        for (int i = 0; i < chrSet.length; ++i) {
            if (value != chrSet[i]) continue;
            return true;
        }
        return false;
    }

    public static String strExclude(String str, char[] chs) {
        if (str == null) {
            return null;
        }
        if (chs == null) {
            return str;
        }
        char[] chars = new char[str.length()];
        int index = 0;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (StringHelper.charIsInCharSet(c, chs)) continue;
            chars[index++] = c;
        }
        return new String(chars, 0, index);
    }

    public static final String getSpaceString(int length) {
        if (length < SpaceStrings.length) {
            return SpaceStrings[length];
        }
        char[] data = new char[length];
        for (int i = 0; i < data.length; ++i) {
            data[i] = 32;
        }
        return new String(data);
    }
}


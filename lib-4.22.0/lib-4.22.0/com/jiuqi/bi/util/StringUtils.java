/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\r\n");

    private StringUtils() {
    }

    public static String clean(String str) {
        return str == null ? "" : str.trim();
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String deleteWhitespace(String str) {
        StringBuffer buffer = new StringBuffer();
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isWhitespace(str.charAt(i))) continue;
            buffer.append(str.charAt(i));
        }
        return buffer.toString();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    public static int indexOfAny(String str, String[] searchStrs) {
        if (str == null || searchStrs == null) {
            return -1;
        }
        int sz = searchStrs.length;
        int ret = Integer.MAX_VALUE;
        int tmp = 0;
        for (int i = 0; i < sz; ++i) {
            tmp = str.indexOf(searchStrs[i]);
            if (tmp == -1 || tmp >= ret) continue;
            ret = tmp;
        }
        return ret == Integer.MAX_VALUE ? -1 : ret;
    }

    public static int lastIndexOfAny(String str, String[] searchStrs) {
        if (str == null || searchStrs == null) {
            return -1;
        }
        int sz = searchStrs.length;
        int ret = -1;
        int tmp = 0;
        for (int i = 0; i < sz; ++i) {
            tmp = str.lastIndexOf(searchStrs[i]);
            if (tmp <= ret) continue;
            ret = tmp;
        }
        return ret;
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return "";
        }
        return str.substring(start);
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }
        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return "";
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        return str.substring(start, end);
    }

    public static String left(String str, int len) {
        if (len < 0) {
            throw new IllegalArgumentException("Requested String length " + len + " is less than zero");
        }
        if (str == null || str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static String right(String str, int len) {
        if (len < 0) {
            throw new IllegalArgumentException("Requested String length " + len + " is less than zero");
        }
        if (str == null || str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    public static String mid(String str, int pos, int len) {
        if (pos < 0 || str != null && pos > str.length()) {
            throw new StringIndexOutOfBoundsException("String index " + pos + " is out of bounds");
        }
        if (len < 0) {
            throw new IllegalArgumentException("Requested String length " + len + " is less than zero");
        }
        if (str == null) {
            return null;
        }
        if (str.length() <= pos + len) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    public static String[] split(String str) {
        return StringUtils.split(str, null, -1);
    }

    public static String[] splitWithSpaceElement(String text, String separator) {
        int pos = text.indexOf(separator);
        int count = 0;
        while (pos != -1) {
            ++count;
            pos = text.indexOf(separator, pos + 1);
        }
        String[] sReturn = new String[count + 1];
        pos = text.indexOf(separator);
        count = 0;
        int prepos = -1;
        while (pos != -1) {
            sReturn[count] = text.substring(prepos + 1, pos);
            prepos = pos;
            pos = text.indexOf(separator, pos + 1);
            ++count;
        }
        sReturn[sReturn.length - 1] = prepos + 1 == text.length() ? "" : text.substring(prepos + 1);
        return sReturn;
    }

    public static String[] split(String text, String separator) {
        return StringUtils.split(text, separator, -1);
    }

    public static String[] split(String str, String separator, int max) {
        StringTokenizer tok = null;
        tok = separator == null ? new StringTokenizer(str) : new StringTokenizer(str, separator);
        int listSize = tok.countTokens();
        if (max > 0 && listSize > max) {
            listSize = max;
        }
        String[] list = new String[listSize];
        int i = 0;
        int lastTokenBegin = 0;
        int lastTokenEnd = 0;
        while (tok.hasMoreTokens()) {
            if (max > 0 && i == listSize - 1) {
                String endToken = tok.nextToken();
                lastTokenBegin = str.indexOf(endToken, lastTokenEnd);
                list[i] = str.substring(lastTokenBegin);
                break;
            }
            list[i] = tok.nextToken();
            lastTokenBegin = str.indexOf(list[i], lastTokenEnd);
            lastTokenEnd = lastTokenBegin + list[i].length();
            ++i;
        }
        return list;
    }

    public static String concatenate(Object[] array) {
        return StringUtils.join(array, "");
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

    public static String join(Iterator<?> iterator, String separator) {
        if (separator == null) {
            separator = "";
        }
        StringBuffer buf = new StringBuffer(256);
        while (iterator.hasNext()) {
            buf.append(iterator.next());
            if (!iterator.hasNext()) continue;
            buf.append(separator);
        }
        return buf.toString();
    }

    public static String replaceOnce(String text, String repl, String with) {
        return StringUtils.replace(text, repl, with, 1);
    }

    public static String replace(String text, String repl, String with) {
        return StringUtils.replace(text, repl, with, -1);
    }

    public static String replace(String text, String repl, String with, int max) {
        if (text == null) {
            return null;
        }
        if (repl == null || repl.length() == 0) {
            return text;
        }
        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        int end = 0;
        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();
            if (--max != 0) continue;
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String overlayString(String text, String overlay, int start, int end) {
        return new StringBuffer(start + overlay.length() + text.length() - end + 1).append(text.substring(0, start)).append(overlay).append(text.substring(end)).toString();
    }

    public static String center(String str, int size) {
        return StringUtils.center(str, size, " ");
    }

    public static String center(String str, int size, String delim) {
        int sz = str.length();
        int p = size - sz;
        if (p < 1) {
            return str;
        }
        str = StringUtils.leftPad(str, sz + p / 2, delim);
        str = StringUtils.rightPad(str, size, delim);
        return str;
    }

    public static String chomp(String str) {
        return StringUtils.chomp(str, "\n");
    }

    public static String chomp(String str, String sep) {
        int idx = str.lastIndexOf(sep);
        if (idx != -1) {
            return str.substring(0, idx);
        }
        return str;
    }

    public static String chompLast(String str) {
        return StringUtils.chompLast(str, "\n");
    }

    public static String chompLast(String str, String sep) {
        if (str.length() == 0) {
            return str;
        }
        String sub = str.substring(str.length() - sep.length());
        if (sep.equals(sub)) {
            return str.substring(0, str.length() - sep.length());
        }
        return str;
    }

    public static String getChomp(String str, String sep) {
        int idx = str.lastIndexOf(sep);
        if (idx == str.length() - sep.length()) {
            return sep;
        }
        if (idx != -1) {
            return str.substring(idx);
        }
        return "";
    }

    public static String prechomp(String str, String sep) {
        int idx = str.indexOf(sep);
        if (idx != -1) {
            return str.substring(idx + sep.length());
        }
        return str;
    }

    public static String getPrechomp(String str, String sep) {
        int idx = str.indexOf(sep);
        if (idx != -1) {
            return str.substring(0, idx + sep.length());
        }
        return "";
    }

    public static String chop(String str) {
        if ("".equals(str)) {
            return "";
        }
        if (str.length() == 1) {
            return "";
        }
        int lastIdx = str.length() - 1;
        String ret = str.substring(0, lastIdx);
        char last = str.charAt(lastIdx);
        if (last == '\n' && ret.charAt(lastIdx - 1) == '\r') {
            return ret.substring(0, lastIdx - 1);
        }
        return ret;
    }

    public static String chopNewline(String str) {
        int lastIdx = str.length() - 1;
        char last = str.charAt(lastIdx);
        if (last == '\n') {
            if (str.charAt(lastIdx - 1) == '\r') {
                --lastIdx;
            }
        } else {
            ++lastIdx;
        }
        return str.substring(0, lastIdx);
    }

    public static String escape(String str) {
        int sz = str.length();
        StringBuffer buffer = new StringBuffer(2 * sz);
        block12: for (int i = 0; i < sz; ++i) {
            char ch = str.charAt(i);
            if (ch > '\u0fff') {
                buffer.append("\\u" + Integer.toHexString(ch));
                continue;
            }
            if (ch > '\u00ff') {
                buffer.append("\\u0" + Integer.toHexString(ch));
                continue;
            }
            if (ch > '\u007f') {
                buffer.append("\\u00" + Integer.toHexString(ch));
                continue;
            }
            if (ch < ' ') {
                switch (ch) {
                    case '\b': {
                        buffer.append('\\');
                        buffer.append('b');
                        break;
                    }
                    case '\n': {
                        buffer.append('\\');
                        buffer.append('n');
                        break;
                    }
                    case '\t': {
                        buffer.append('\\');
                        buffer.append('t');
                        break;
                    }
                    case '\f': {
                        buffer.append('\\');
                        buffer.append('f');
                        break;
                    }
                    case '\r': {
                        buffer.append('\\');
                        buffer.append('r');
                        break;
                    }
                    default: {
                        if (ch > '\u000f') {
                            buffer.append("\\u00" + Integer.toHexString(ch));
                            break;
                        }
                        buffer.append("\\u000" + Integer.toHexString(ch));
                        break;
                    }
                }
                continue;
            }
            switch (ch) {
                case '\'': {
                    buffer.append('\\');
                    buffer.append('\'');
                    continue block12;
                }
                case '\"': {
                    buffer.append('\\');
                    buffer.append('\"');
                    continue block12;
                }
                case '\\': {
                    buffer.append('\\');
                    buffer.append('\\');
                    continue block12;
                }
                default: {
                    buffer.append(ch);
                }
            }
        }
        return buffer.toString();
    }

    public static String repeat(String str, int repeat) {
        StringBuffer buffer = new StringBuffer(repeat * str.length());
        for (int i = 0; i < repeat; ++i) {
            buffer.append(str);
        }
        return buffer.toString();
    }

    public static String rightPad(String str, int size) {
        return StringUtils.rightPad(str, size, " ");
    }

    public static String rightPad(String str, int size, String delim) {
        if ((size = (size - str.length()) / delim.length()) > 0) {
            str = str + StringUtils.repeat(delim, size);
        }
        return str;
    }

    public static String leftPad(String str, int size) {
        return StringUtils.leftPad(str, size, " ");
    }

    public static String leftPad(String str, int size, String delim) {
        if ((size = (size - str.length()) / delim.length()) > 0) {
            str = StringUtils.repeat(delim, size) + str;
        }
        return str;
    }

    public static String strip(String str) {
        return StringUtils.strip(str, null);
    }

    public static String strip(String str, String delim) {
        str = StringUtils.stripStart(str, delim);
        return StringUtils.stripEnd(str, delim);
    }

    public static String[] stripAll(String[] strs) {
        return StringUtils.stripAll(strs, null);
    }

    public static String[] stripAll(String[] strs, String delimiter) {
        if (strs == null || strs.length == 0) {
            return strs;
        }
        int sz = strs.length;
        String[] newArr = new String[sz];
        for (int i = 0; i < sz; ++i) {
            newArr[i] = StringUtils.strip(strs[i], delimiter);
        }
        return newArr;
    }

    public static String stripEnd(String str, String strip) {
        int end;
        if (str == null) {
            return null;
        }
        if (strip == null) {
            for (end = str.length(); end != 0 && Character.isWhitespace(str.charAt(end - 1)); --end) {
            }
        } else {
            while (end != 0 && strip.indexOf(str.charAt(end - 1)) != -1) {
                --end;
            }
        }
        return str.substring(0, end);
    }

    public static String stripStart(String str, String strip) {
        int start;
        if (str == null) {
            return null;
        }
        int sz = str.length();
        if (strip == null) {
            for (start = 0; start != sz && Character.isWhitespace(str.charAt(start)); ++start) {
            }
        } else {
            while (start != sz && strip.indexOf(str.charAt(start)) != -1) {
                ++start;
            }
        }
        return str.substring(start);
    }

    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    public static String uncapitalise(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "";
        }
        return new StringBuffer(str.length()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String capitalise(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "";
        }
        return new StringBuffer(str.length()).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String swapCase(String str) {
        if (str == null) {
            return null;
        }
        int sz = str.length();
        StringBuffer buffer = new StringBuffer(sz);
        boolean whitespace = false;
        char ch = '\u0000';
        char tmp = '\u0000';
        for (int i = 0; i < sz; ++i) {
            ch = str.charAt(i);
            tmp = Character.isUpperCase(ch) ? Character.toLowerCase(ch) : (Character.isTitleCase(ch) ? Character.toLowerCase(ch) : (Character.isLowerCase(ch) ? (whitespace ? Character.toTitleCase(ch) : Character.toUpperCase(ch)) : ch));
            buffer.append(tmp);
            whitespace = Character.isWhitespace(ch);
        }
        return buffer.toString();
    }

    public static String capitaliseAllWords(String str) {
        if (str == null) {
            return null;
        }
        int sz = str.length();
        StringBuffer buffer = new StringBuffer(sz);
        boolean space = true;
        for (int i = 0; i < sz; ++i) {
            char ch = str.charAt(i);
            if (Character.isWhitespace(ch)) {
                buffer.append(ch);
                space = true;
                continue;
            }
            if (space) {
                buffer.append(Character.toTitleCase(ch));
                space = false;
                continue;
            }
            buffer.append(ch);
        }
        return buffer.toString();
    }

    public static String getNestedString(String str, String tag) {
        return StringUtils.getNestedString(str, tag, tag);
    }

    public static String getNestedString(String str, String open, String close) {
        int end;
        if (str == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1 && (end = str.indexOf(close, start + open.length())) != -1) {
            return str.substring(start + open.length(), end);
        }
        return null;
    }

    public static int countMatches(String str, String sub) {
        if (str == null) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            ++count;
            idx += sub.length();
        }
        return count;
    }

    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isLetter(str.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isAlphaSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isLetter(str.charAt(i)) || str.charAt(i) == ' ') continue;
            return false;
        }
        return true;
    }

    public static boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isLetterOrDigit(str.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isLetterOrDigit(str.charAt(i)) || str.charAt(i) == ' ') continue;
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isDigit(str.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == ' ') continue;
            return false;
        }
        return true;
    }

    public static String defaultString(String str) {
        return StringUtils.defaultString(str, "");
    }

    public static String defaultString(String str, String defaultString) {
        return str == null ? defaultString : str;
    }

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }

    public static String reverseDelimitedString(String str, String delimiter) {
        Object[] strs = StringUtils.split(str, delimiter);
        StringUtils.reverseArray(strs);
        return StringUtils.join(strs, delimiter);
    }

    private static void reverseArray(Object[] array) {
        int i = 0;
        for (int j = array.length - 1; j > i; --j, ++i) {
            Object tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }

    public static boolean containsOnly(String str, char[] valid) {
        if (str == null || valid == null) {
            return false;
        }
        int strSize = str.length();
        int validSize = valid.length;
        for (int i = 0; i < strSize; ++i) {
            boolean contains = false;
            for (int j = 0; j < validSize; ++j) {
                if (valid[j] != str.charAt(i)) continue;
                contains = true;
                break;
            }
            if (contains) continue;
            return false;
        }
        return true;
    }

    public static String encodeStringArray(String[] strs) {
        if (strs != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < strs.length; ++i) {
                sb.append(strs[i]);
                sb.append(";");
            }
            return sb.toString();
        }
        return "";
    }

    public static String[] decodeStringArray(String str) {
        if (str == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, ";");
        ArrayList<String> l = new ArrayList<String>();
        while (st.hasMoreElements()) {
            l.add(st.nextToken());
        }
        String[] ret = new String[l.size()];
        for (int i = 0; i < l.size(); ++i) {
            ret[i] = (String)l.get(i);
        }
        return ret;
    }

    public static int lengthOf(String s) {
        if (null == s) {
            return -1;
        }
        int size = s.length();
        int ret = 0;
        for (int i = 0; i < size; ++i) {
            ret += s.charAt(i) > '\u00ff' ? 2 : 1;
        }
        return ret;
    }

    public static int getMaxLenOfStrList(String[] strArr) {
        List<String> list = Arrays.asList(strArr);
        int ret = StringUtils.getMaxLenOfStrList(list);
        return ret;
    }

    public static int getMaxLenOfStrList(List<?> strList) {
        class StrComparator
        implements Comparator<Object> {
            StrComparator() {
            }

            @Override
            public boolean equals(Object object) {
                return false;
            }

            @Override
            public int compare(Object obj1, Object obj2) {
                if (null == obj1 || null == obj2) {
                    return obj1 == obj2 ? 0 : (null == obj2 ? 1 : -1);
                }
                return StringUtils.lengthOf((String)obj1) - StringUtils.lengthOf((String)obj2);
            }
        }
        StrComparator c = new StrComparator();
        String retStr = (String)Collections.max(strList, c);
        c = null;
        return StringUtils.lengthOf(retStr);
    }

    public static int indexOf(char c, String src) {
        int len = src.length();
        for (int i = 0; i < len; ++i) {
            if (src.charAt(i) != c) continue;
            return i;
        }
        return -1;
    }

    public static String quote(String s, char quote) {
        if (StringUtils.isEmpty(s)) {
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

    public static String dequote(String s, char quote) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        if (s.charAt(0) != quote) {
            return null;
        }
        StringBuffer retStr = new StringBuffer(s.length());
        int i = 1;
        while (i < s.length()) {
            char curCh;
            if ((curCh = s.charAt(i++)) == quote) {
                char nextCh;
                if (i >= s.length() || (nextCh = s.charAt(i++)) != quote) break;
                retStr.append(quote);
                continue;
            }
            retStr.append(s.charAt(i));
        }
        return retStr.toString();
    }

    public static String eliminate(String s, char ch) {
        if (s == null) {
            return null;
        }
        int next = s.indexOf(ch);
        if (next == -1) {
            return s;
        }
        StringBuffer buffer = new StringBuffer(s.length());
        int current = 0;
        while (current < s.length()) {
            buffer.append(s.substring(current, next));
            current = next + 1;
            if ((next = s.indexOf(ch, current)) != -1) continue;
            next = s.length();
        }
        return buffer.toString();
    }

    public static int compare(String s1, String s2) {
        if (s1 == s2) {
            return 0;
        }
        if (s1 == null) {
            return -1;
        }
        if (s2 == null) {
            return 1;
        }
        return s1.compareTo(s2);
    }

    public static int compareIgnoreCase(String s1, String s2) {
        if (s1 == s2) {
            return 0;
        }
        if (s1 == null) {
            return -1;
        }
        if (s2 == null) {
            return 1;
        }
        return s1.compareToIgnoreCase(s2);
    }

    public static boolean isIdentifier(String id) {
        if (id == null || id.length() == 0) {
            return false;
        }
        char ch = id.charAt(0);
        if (!StringUtils.isLetter(ch) && ch != '_') {
            return false;
        }
        for (int i = 1; i < id.length(); ++i) {
            ch = id.charAt(i);
            if (StringUtils.isLetter(ch) || ch == '_' || StringUtils.isNumber(ch)) continue;
            return false;
        }
        return true;
    }

    private static boolean isLetter(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    private static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }
}


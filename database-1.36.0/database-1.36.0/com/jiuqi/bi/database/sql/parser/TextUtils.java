/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.parser;

import com.jiuqi.bi.database.sql.parser.SQLParserException;
import com.jiuqi.bi.database.sql.parser.Term;

class TextUtils {
    TextUtils() {
    }

    public static Term getTerm(String str, int startpos) throws SQLParserException {
        boolean isString;
        char startchar = str.charAt(startpos);
        while (TextUtils.isBlank(startchar)) {
            if (++startpos == str.length()) {
                return null;
            }
            startchar = str.charAt(startpos);
        }
        if (TextUtils.isSeparator(startchar)) {
            return new Term(startpos, String.valueOf(startchar));
        }
        boolean bl = isString = startchar == '\'' || startchar == '\"';
        if (isString) {
            for (int i = startpos + 1; i < str.length(); ++i) {
                if (str.charAt(i) == '\\') {
                    ++i;
                    continue;
                }
                if (str.charAt(i) != startchar) continue;
                return new Term(startpos, str.substring(startpos, i + 1));
            }
            throw new SQLParserException("\u8bed\u6cd5\u683c\u5f0f\u6709\u8bef");
        }
        for (int i = startpos; i < str.length(); ++i) {
            int pos = TextUtils.skipComment(str, i);
            if (pos > i) {
                startpos = pos;
                i = pos - 1;
                continue;
            }
            char c = str.charAt(i);
            if (TextUtils.isBlank(c) && startpos == i) {
                ++startpos;
                continue;
            }
            if (c == '\\') {
                ++i;
                continue;
            }
            if (!TextUtils.isHalt(c)) continue;
            String txt = startpos == i ? str.substring(startpos, i + 1) : str.substring(startpos, i);
            return new Term(startpos, txt);
        }
        if (startpos == str.length()) {
            return null;
        }
        return new Term(startpos, str.substring(startpos));
    }

    public static int indexOf(String str, int startpos, Term dest) throws SQLParserException {
        int start = startpos;
        while (start <= str.length() - 1) {
            Term term = TextUtils.getTerm(str, start);
            if (term.text().equalsIgnoreCase(dest.text())) {
                return term.pos();
            }
            start = term.end() + 1;
        }
        return -1;
    }

    public static int getRownumByPosition(String str, int pos) {
        int rownum = 0;
        for (int i = 0; i <= pos; ++i) {
            if (str.charAt(i) != '\n') continue;
            ++rownum;
        }
        return rownum;
    }

    public static String tryRemoveUtf8BOM(String str) {
        byte[] bytes = str.getBytes();
        if (bytes.length >= 3 && bytes[0] + 256 == 239 && bytes[1] + 256 == 187 && bytes[2] + 256 == 191) {
            byte[] nb = new byte[bytes.length - 3];
            System.arraycopy(bytes, 3, nb, 0, nb.length);
            return new String(nb);
        }
        return str;
    }

    public static int skipComment(String str, int startpos) {
        if (str.charAt(startpos) != '-' || startpos >= str.length() - 1) {
            return startpos;
        }
        if (str.charAt(startpos + 1) != '-') {
            return startpos;
        }
        for (int i = startpos + 2; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c != '\n') continue;
            return i + 1;
        }
        return str.length();
    }

    public static boolean isHalt(char c) {
        return TextUtils.isBlank(c) || TextUtils.isBracket(c) || TextUtils.isOperSign(c) || TextUtils.isSeparator(c);
    }

    public static boolean isBlank(char c) {
        return c == ' ' || c == '\n' || c == '\t' || c == '\r';
    }

    public static boolean isBracket(char c) {
        return c == '(' || c == ')';
    }

    public static boolean isOperSign(char c) {
        return c == '+' || c == '-' || c == '=';
    }

    private static boolean isSeparator(char c) {
        return c == ',' || c == ';';
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.patterns;

public final class Grammar {
    private final String quoteChars;
    private final String lineCommentSign;
    private final String blockCommentStart;
    private final String blockCommentEnd;
    public static final Grammar SQL = new Grammar("'", "--", "/*", "*/");
    public static final int UNMATACHED = Integer.MIN_VALUE;

    public Grammar(String quoteChars, String lineCommentSign, String blockCommentStart, String blockCommentEnd) {
        this.quoteChars = quoteChars;
        this.lineCommentSign = lineCommentSign;
        this.blockCommentStart = blockCommentStart;
        this.blockCommentEnd = blockCommentEnd;
    }

    public String getQuoteChar() {
        return this.quoteChars;
    }

    public String getLineCommentSign() {
        return this.lineCommentSign;
    }

    public String getBlockCommentStart() {
        return this.blockCommentStart;
    }

    public String getBlockCommentEnd() {
        return this.blockCommentEnd;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(this.quoteChars).append(this.lineCommentSign).append(' ').append(this.blockCommentStart).append(' ').append(this.blockCommentEnd).append(']');
        return buffer.toString();
    }

    public int match(String s, int start) {
        int next = this.tryString(s, start);
        if (next != -1) {
            return -next;
        }
        next = this.tryLineComment(s, start);
        if (next != -1) {
            return next;
        }
        next = this.tryBlockComment(s, start);
        if (next != -1) {
            return next;
        }
        return Integer.MIN_VALUE;
    }

    private int tryString(String s, int start) {
        if (this.quoteChars == null) {
            return -1;
        }
        char quoteChar = s.charAt(start);
        if (this.quoteChars.indexOf(quoteChar) < 0) {
            return -1;
        }
        int cur = start + 1;
        while (cur < s.length()) {
            char ch = this.charAt(s, cur);
            if (ch == quoteChar) {
                if (this.charAt(s, cur + 1) == quoteChar) {
                    cur += 2;
                    continue;
                }
                return cur + 1;
            }
            if (ch == '$' && this.charAt(s, cur + 1) == '{') {
                cur = this.skipVariable(s, cur);
                continue;
            }
            ++cur;
        }
        return -1;
    }

    private int skipVariable(String s, int cur) {
        int nextRight;
        int nextLeft;
        int left = cur;
        int right = s.indexOf(125, cur + 2);
        while (left < right && (nextLeft = s.indexOf("${", left + 2)) >= 0 && nextLeft <= right && (nextRight = s.indexOf(125, right + 1)) >= 0) {
            left = nextLeft;
            right = nextRight;
        }
        return right < 0 ? cur + 1 : right + 1;
    }

    private int tryLineComment(String s, int start) {
        int cur;
        if (this.lineCommentSign == null) {
            return -1;
        }
        if (!s.startsWith(this.lineCommentSign, start)) {
            return -1;
        }
        for (cur = start + this.lineCommentSign.length(); cur < s.length(); ++cur) {
            char ch = this.charAt(s, cur);
            switch (ch) {
                case '\r': {
                    if (this.charAt(s, cur) == '\n') {
                        ++cur;
                    }
                    return cur;
                }
                case '\n': {
                    return cur;
                }
            }
        }
        return cur;
    }

    private int tryBlockComment(String s, int start) {
        if (this.blockCommentStart == null || this.blockCommentEnd == null) {
            return -1;
        }
        if (!s.startsWith(this.blockCommentStart, start)) {
            return -1;
        }
        int next = s.indexOf(this.blockCommentEnd, start + this.blockCommentStart.length());
        return next == -1 ? s.length() : next + this.blockCommentEnd.length();
    }

    private char charAt(String s, int pos) {
        return pos < s.length() ? s.charAt(pos) : (char)'\u0000';
    }
}


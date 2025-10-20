/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DBException
 */
package com.jiuqi.bi.sql;

import com.jiuqi.bi.sql.DBException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SQLPretreatment {
    private String originSQL;
    private boolean toUpperCase = false;
    private boolean ignoreComment = false;
    private boolean queryOnly = false;
    private boolean multiLineable = true;
    private String sql;
    private List<String> params;
    private static final String BLANKS = " \t\r\n";
    private static final String DELIMITERS = " \t\r\n{}(),;+-*/<>?=!%.|`\"'";
    private static final Set<String> DANGER_VERBS = new HashSet<String>(Arrays.asList("UPDATE", "INSERT", "MERGE", "DELETE", "DROP", "CREATE", "ALTER", "CALL", "EXEC", "INTO", "TRUNCATE", "REPLACE", "SHOW", "RENAME", "USE", "FLASHBACK"));

    public static String toUpperSQL(String sql) throws DBException {
        SQLPretreatment prt = new SQLPretreatment(sql);
        prt.setToUpperCase(true);
        prt.execute();
        return prt.getSQL();
    }

    public SQLPretreatment(String sql) {
        this.originSQL = sql;
        sql = null;
        this.params = new ArrayList<String>();
    }

    public void execute() throws DBException {
        this.reset();
        StringBuffer sqlBuffer = new StringBuffer();
        this.build(sqlBuffer);
        this.sql = sqlBuffer.toString().trim();
    }

    public String getSQL() {
        return this.sql;
    }

    public String[] getParamNames() {
        return this.params.toArray(new String[0]);
    }

    public void setToUpperCase(boolean toUpperCase) {
        this.toUpperCase = toUpperCase;
    }

    public boolean getToUpperCase() {
        return this.toUpperCase;
    }

    public void setIgnoreComment(boolean ignoreComment) {
        this.ignoreComment = ignoreComment;
    }

    public boolean getIgnoreComment() {
        return this.ignoreComment;
    }

    public boolean isQueryOnly() {
        return this.queryOnly;
    }

    public void setQueryOnly(boolean queryOnly) {
        this.queryOnly = queryOnly;
    }

    public boolean isMultiLineable() {
        return this.multiLineable;
    }

    public void setMultiLineable(boolean multiLineable) {
        this.multiLineable = multiLineable;
    }

    private void reset() {
        this.sql = null;
        this.params.clear();
    }

    private void build(StringBuffer buffer) throws DBException {
        CharReader reader = new CharReader(this.originSQL);
        block8: while (!reader.isEnd()) {
            char ch = reader.read();
            switch (ch) {
                case '\u0000': 
                case ';': {
                    return;
                }
                case '-': {
                    if (reader.peek() == '-') {
                        reader.read();
                        if (!this.ignoreComment) {
                            buffer.append("--");
                        }
                        this.gotoNextLine(reader, buffer);
                        continue block8;
                    }
                    buffer.append(ch);
                    continue block8;
                }
                case '/': {
                    if (reader.peek() == '*') {
                        reader.read();
                        if (!this.ignoreComment) {
                            buffer.append("/*");
                        }
                        this.gotoCommentEnd(reader, buffer);
                        continue block8;
                    }
                    buffer.append(ch);
                    continue block8;
                }
                case '\'': {
                    buffer.append(ch);
                    if (this.readToStringEnd(reader, buffer)) continue block8;
                    throw new DBException("SQL\u8bed\u6cd5\u9519\u8bef\uff1a\u542b\u6709\u672a\u7ed3\u675f\u7684\u5b57\u7b26\u4e32\uff01");
                }
                case '?': {
                    String paramName = this.readParamName(reader);
                    String newExpr = this.repaceParameter(paramName);
                    if (newExpr == null) {
                        buffer.append(ch);
                        this.params.add(paramName);
                        continue block8;
                    }
                    buffer.append(newExpr);
                    continue block8;
                }
                case '\"': 
                case '`': {
                    buffer.append(ch);
                    if (this.readToQuotingEnd(reader, buffer, ch)) continue block8;
                    throw new DBException("SQL\u8bed\u6cd5\u9519\u8bef\uff0c\u5f15\u7528\u7b26\u672a\u6b63\u786e\u7ed3\u675f\uff1a" + ch);
                }
            }
            if (DELIMITERS.indexOf(ch) >= 0) {
                buffer.append(this.convertChar(ch));
                continue;
            }
            String token = this.readToken(reader);
            if ("GO".equalsIgnoreCase(token)) {
                return;
            }
            buffer.append(this.convertToken(token, reader));
        }
    }

    private char convertChar(char ch) {
        return this.toUpperCase ? Character.toUpperCase(ch) : ch;
    }

    private String convertToken(String token, CharReader reader) throws DBException {
        String retToken;
        if (token == null) {
            return token;
        }
        String string = retToken = this.toUpperCase ? token.toUpperCase() : token;
        if (this.queryOnly && DANGER_VERBS.contains(retToken.toUpperCase()) && reader.peekNext(BLANKS) != '(') {
            throw new DBException("\u67e5\u8be2\u6a21\u5f0f\u4e0b\u7981\u6b62\u4f7f\u7528\u64cd\u4f5c\uff1a" + token);
        }
        return retToken;
    }

    protected String repaceParameter(String paramName) throws DBException {
        return null;
    }

    private void gotoNextLine(CharReader reader, StringBuffer buffer) {
        while (!reader.isEnd()) {
            char ch = reader.read();
            if (ch == '\r' || ch == '\n') {
                buffer.append(ch);
                break;
            }
            if (this.ignoreComment) continue;
            buffer.append(ch);
        }
    }

    private void gotoCommentEnd(CharReader reader, StringBuffer buffer) {
        while (!reader.isEnd()) {
            char ch = reader.read();
            if (ch == '*' && reader.peek() == '/') {
                reader.read();
                buffer.append(this.ignoreComment ? " " : "*/");
                break;
            }
            if (this.ignoreComment) continue;
            buffer.append(ch);
        }
    }

    private boolean readToStringEnd(CharReader reader, StringBuffer buffer) {
        while (!reader.isEnd()) {
            char ch = reader.read();
            if (!(this.multiLineable || ch != '\r' && ch != '\n')) {
                return false;
            }
            buffer.append(ch);
            if (ch != '\'') continue;
            if (reader.peek() == '\'') {
                buffer.append(reader.read());
                continue;
            }
            return true;
        }
        return false;
    }

    private boolean readToQuotingEnd(CharReader reader, StringBuffer buffer, char quoteChar) {
        while (!reader.isEnd()) {
            char ch = reader.read();
            if (ch == '\r' || ch == '\n') {
                return false;
            }
            buffer.append(ch);
            if (ch != quoteChar) continue;
            return true;
        }
        return false;
    }

    private String readParamName(CharReader reader) {
        char ch;
        StringBuffer paramName = new StringBuffer();
        while (!reader.isEnd() && ((ch = reader.peek()) >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == '_' || ch == '@' || ch == '.')) {
            paramName.append(ch);
            reader.read();
        }
        return paramName.toString();
    }

    private String readToken(CharReader reader) {
        reader.mark(-1);
        while (!reader.isEnd() && !reader.check(0, DELIMITERS)) {
            reader.read();
        }
        return reader.getMarkToken();
    }

    private static class CharReader {
        private final String text;
        private int curPos = 0;
        private int mark = -1;
        public static final char EOF = '\u0000';

        public CharReader(String text) {
            this.text = text;
        }

        public boolean isEnd() {
            return this.text == null || this.curPos >= this.text.length();
        }

        public char read() {
            if (this.isEnd()) {
                return '\u0000';
            }
            return this.text.charAt(this.curPos++);
        }

        public char peek() {
            if (this.isEnd()) {
                return '\u0000';
            }
            return this.text.charAt(this.curPos);
        }

        public char peek(int offset) {
            if (this.text == null) {
                return '\u0000';
            }
            int newPos = this.curPos + offset;
            return newPos < 0 || newPos >= this.text.length() ? (char)'\u0000' : this.text.charAt(newPos);
        }

        public boolean check(int offset, String signs) {
            if (signs == null) {
                return false;
            }
            char ch = this.peek(offset);
            return signs.indexOf(ch) >= 0;
        }

        public char peekNext(String ignores) {
            for (int p = this.curPos; p < this.text.length(); ++p) {
                char ch = this.text.charAt(p);
                if (ignores != null && ignores.indexOf(ch) >= 0) continue;
                return ch;
            }
            return '\u0000';
        }

        public void mark(int offset) {
            this.mark = this.curPos + offset;
        }

        public String getMarkToken() {
            return this.text.substring(this.mark, this.curPos);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DBException
 */
package com.jiuqi.bi.sql;

import com.jiuqi.bi.sql.DBException;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.Reader;

public abstract class SQLScriptExcutor {
    private Reader reader;
    private boolean isIgnoreComment;
    private boolean isString;
    private boolean isBlockComment;
    private boolean isLineComment;
    private boolean isBlockScript;
    private StringBuffer sql;
    private StringBuffer line;

    public SQLScriptExcutor(Reader sqlReader, boolean isIgnoreComment) {
        this.reader = sqlReader;
        this.isIgnoreComment = isIgnoreComment;
        this.sql = new StringBuffer();
        this.line = new StringBuffer();
    }

    protected void open() throws DBException {
    }

    protected void close() throws DBException {
    }

    public final void parse() throws IOException, DBException {
        int i;
        this.open();
        block9: while ((i = this.reader.read()) != -1) {
            char c = (char)i;
            if (!(c != '\r' && c != '\n' || this.isBlockScript)) {
                if (this.line.length() != 0) {
                    this.sql.append(this.line.toString());
                    this.sql.append("\r\n");
                    this.line.setLength(0);
                }
                this.isLineComment = false;
                continue;
            }
            if (this.isLineComment) continue;
            if (this.isBlockComment && this.isIgnoreComment) {
                if (c != '*' || (c = (char)(i = this.reader.read())) == '\uffffffff' || c != '/') continue;
                this.isBlockComment = false;
                continue;
            }
            if (this.isBlockScript) {
                if (c == '#') {
                    char[] end = new char[3];
                    this.reader.read(end);
                    String str = new String(end);
                    if (str.equalsIgnoreCase("end")) {
                        this.isBlockScript = false;
                        this.excute();
                        continue;
                    }
                    this.line.append('#').append(str);
                    continue;
                }
                if (c == '\r' || c == '\n') {
                    this.line.append(' ');
                    continue;
                }
                this.line.append(c);
                continue;
            }
            switch (c) {
                case '\'': {
                    this.line.append(c);
                    if (this.isBlockComment) continue block9;
                    this.isString = !this.isString;
                    continue block9;
                }
                case '-': {
                    if (!this.isBlockComment && !this.isString) {
                        i = this.reader.read();
                        c = (char)i;
                        if (i == -1) continue block9;
                        if (c == '-') {
                            this.isLineComment = true;
                            continue block9;
                        }
                        this.line.append('-').append(c);
                        continue block9;
                    }
                    this.line.append('-');
                    continue block9;
                }
                case '/': {
                    if (!this.isBlockComment && !this.isString) {
                        i = this.reader.read();
                        c = (char)i;
                        if (i == -1) continue block9;
                        if (c == '*') {
                            this.isBlockComment = true;
                            if (this.isIgnoreComment) continue block9;
                            this.line.append('/').append(c);
                            continue block9;
                        }
                        this.line.append('/').append(c);
                        continue block9;
                    }
                    this.line.append('/');
                    continue block9;
                }
                case '*': {
                    if (this.isBlockComment) {
                        c = (char)this.reader.read();
                        if (c == '\uffffffff') continue block9;
                        if (c == '/') {
                            this.isBlockComment = false;
                        }
                        if (this.isIgnoreComment) continue block9;
                        this.line.append('*').append(c);
                        continue block9;
                    }
                    this.line.append('*');
                    continue block9;
                }
                case ';': {
                    if (!this.isBlockComment && !this.isString) {
                        this.excute();
                        continue block9;
                    }
                    this.line.append(';');
                    continue block9;
                }
                case 'G': 
                case 'g': {
                    char prev = c;
                    if (!this.isBlockComment && !this.isString) {
                        i = this.reader.read();
                        c = (char)i;
                        if (c == 'O' || c == 'o') {
                            i = this.reader.read();
                            char nextChar = (char)i;
                            if (i == -1 || nextChar == '\r' || nextChar == '\n') {
                                this.excute();
                                continue block9;
                            }
                            this.line.append(prev).append(c).append(nextChar);
                            continue block9;
                        }
                        this.line.append(prev);
                        if (c == ';') {
                            this.excute();
                            continue block9;
                        }
                        if (i == -1) continue block9;
                        this.line.append(c);
                        continue block9;
                    }
                    this.line.append(prev);
                    continue block9;
                }
                case '#': {
                    if (this.line.length() == 0) {
                        char[] begin = new char[5];
                        this.reader.read(begin);
                        String str = new String(begin);
                        if (str.equalsIgnoreCase("begin")) {
                            this.isBlockScript = true;
                            continue block9;
                        }
                        this.line.append('#').append(str);
                        continue block9;
                    }
                    this.line.append('#');
                    continue block9;
                }
            }
            this.line.append(c);
        }
        this.excute();
        this.close();
    }

    public boolean isIgnoreComment() {
        return this.isIgnoreComment;
    }

    private void excute() throws DBException {
        this.sql.append(this.line.toString());
        String sqlStr = this.sql.toString();
        if (!StringUtils.isEmpty(sqlStr)) {
            this.excuteSingleSql(sqlStr.trim());
        }
        this.line.setLength(0);
        this.sql.setLength(0);
    }

    protected abstract void excuteSingleSql(String var1) throws DBException;
}


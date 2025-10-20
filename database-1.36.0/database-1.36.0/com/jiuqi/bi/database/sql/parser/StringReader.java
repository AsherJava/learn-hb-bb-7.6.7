/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.parser;

import java.util.ArrayList;
import java.util.List;

class StringReader {
    private String str;
    private int nextpos;
    private int total;

    public StringReader(String str) {
        this.str = str;
        this.nextpos = 0;
        this.total = str.length();
    }

    public int read() {
        if (this.nextpos >= this.total) {
            throw new StringIndexOutOfBoundsException(this.nextpos);
        }
        char c = this.str.charAt(this.nextpos);
        ++this.nextpos;
        return c;
    }

    public void skip(int n) {
        if (this.nextpos + n > this.total) {
            throw new StringIndexOutOfBoundsException(this.nextpos + n);
        }
        this.nextpos += n;
    }

    public String readLine() {
        return this.readLine(true);
    }

    public String readLine(boolean ignoreLT) {
        int len;
        String line = null;
        if (this.isEnd()) {
            return null;
        }
        int pos = this.nextpos;
        block0: while (pos < this.total) {
            char c;
            if ((c = this.str.charAt(++pos - 1)) == '\'' || c == '\"') {
                for (int i = pos; i < this.total; ++i) {
                    if (this.str.charAt(i) == '\\') {
                        ++i;
                        continue;
                    }
                    if (this.str.charAt(i) != c) continue;
                    pos = i + 1;
                    continue block0;
                }
                continue;
            }
            if (c != '\n') continue;
            line = this.str.substring(this.nextpos, pos);
            this.nextpos = pos;
            return line;
        }
        line = this.str.substring(this.nextpos);
        this.nextpos = this.total;
        if (ignoreLT && (len = line.length()) > 0 && line.charAt(len - 1) == '\n') {
            line = line.substring(0, len - 1);
            if (--len > 0 && line.charAt(len - 1) == '\r') {
                line = line.substring(0, len - 1);
            }
        }
        return line;
    }

    public List<String> lines() {
        ArrayList<String> lines = new ArrayList<String>();
        String line = this.readLine();
        while (line != null) {
            if (!line.trim().isEmpty()) {
                lines.add(line);
            }
            line = this.readLine();
        }
        return lines;
    }

    public int getPos() {
        return this.nextpos;
    }

    public boolean isEnd() {
        return this.nextpos >= this.total;
    }

    public void reset() {
        this.nextpos = 0;
    }
}


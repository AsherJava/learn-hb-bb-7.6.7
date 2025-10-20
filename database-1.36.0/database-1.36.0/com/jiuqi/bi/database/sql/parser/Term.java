/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.parser;

public final class Term {
    private int pos;
    private String text;

    public Term(int pos, String text) {
        this.pos = pos;
        this.text = text;
    }

    public int pos() {
        return this.pos;
    }

    public int end() {
        return this.pos + this.text.length() - 1;
    }

    public String text() {
        return this.text;
    }

    public boolean isChar() {
        return this.text.length() == 1;
    }

    public char toChar() {
        return this.text.charAt(0);
    }

    public boolean isSQLEndSign() {
        return this.isChar() && this.toChar() == ';';
    }

    public String toString() {
        return "{ text: " + this.text + "; pos:" + this.pos + " }";
    }
}


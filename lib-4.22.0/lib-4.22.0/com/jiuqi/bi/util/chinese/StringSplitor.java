/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.chinese;

import com.jiuqi.bi.util.chinese.NoValidCharException;

public class StringSplitor {
    private final char[] CHARS;
    private int position;
    private int nextPosition;
    private final char[] EXCLUDE_SET;

    public StringSplitor(String value, char[] excludSet) {
        if (value == null) {
            throw new IllegalArgumentException("\u6307\u5411\u7a7a\u7684\u5b57\u7b26\u4e32\u6307\u9488\uff01");
        }
        this.CHARS = value.toCharArray();
        this.position = -1;
        this.nextPosition = -1;
        this.EXCLUDE_SET = excludSet;
    }

    public boolean isValidChar(char theChar) {
        boolean found = false;
        for (int i = 0; i < this.EXCLUDE_SET.length; ++i) {
            if (this.EXCLUDE_SET[i] != theChar) continue;
            found = true;
            break;
        }
        return !found;
    }

    public boolean hasValidChar() {
        if (this.position == this.CHARS.length - 1) {
            return false;
        }
        if (this.nextPosition > this.position) {
            return true;
        }
        for (int i = this.position + 1; i < this.CHARS.length; ++i) {
            char thisChar = this.CHARS[i];
            if (!this.isValidChar(thisChar)) continue;
            this.nextPosition = i;
            return true;
        }
        return false;
    }

    public char getNextValidChar() throws NoValidCharException {
        if (this.hasValidChar()) {
            this.position = this.nextPosition;
            return this.CHARS[this.position];
        }
        throw new NoValidCharException("\u8fd9\u4e2a\u5b57\u7b26\u4e32\u5df2\u7ecf\u6ca1\u6709\u975e\u7a7a\u683c\u7684\u5b57\u7b26\uff01");
    }

    public void rollBack() {
        int prePosition;
        boolean found = false;
        for (prePosition = this.position - 1; prePosition >= 0; --prePosition) {
            if (!this.isValidChar(this.CHARS[prePosition])) continue;
            found = true;
            break;
        }
        this.position = found ? prePosition : -1;
        this.nextPosition = this.position;
    }

    public String getLeft() {
        return new String(this.CHARS, this.position + 1, this.CHARS.length - this.position - 1);
    }

    public int validLength() {
        int length = 0;
        for (int i = 0; i < this.CHARS.length; ++i) {
            if (!this.isValidChar(this.CHARS[i])) continue;
            ++length;
        }
        return length;
    }
}


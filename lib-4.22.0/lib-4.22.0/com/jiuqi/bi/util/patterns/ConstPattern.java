/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.patterns;

import com.jiuqi.bi.util.patterns.IPattern;
import com.jiuqi.bi.util.patterns.ITranslator;
import java.util.Map;

final class ConstPattern
implements IPattern {
    private String expression;

    public ConstPattern() {
    }

    public ConstPattern(String expression) {
        this.expression = expression;
    }

    public String toString() {
        return this.expression;
    }

    @Override
    public void add(IPattern pattern) {
    }

    @Override
    public void add(int index, IPattern pattern) {
    }

    @Override
    public void clear() {
    }

    @Override
    public IPattern get(int index) {
        return null;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Override
    public int getLength() {
        return this.expression == null ? 0 : this.expression.length();
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int indexOf(IPattern pattern) {
        return -1;
    }

    @Override
    public void remove(int index) {
    }

    @Override
    public void setExpression(String value) {
        this.expression = value;
    }

    @Override
    public void setLength(int value) {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int match(String s, int start, Map<String, String> values) {
        if (s == null) {
            return -1;
        }
        if (this.expression == null) {
            return start;
        }
        for (int i = 0; i < this.expression.length(); ++i) {
            if (start + i >= s.length()) {
                return -1;
            }
            if (this.expression.charAt(i) == s.charAt(start + i)) continue;
            return -1;
        }
        return start + this.expression.length();
    }

    @Override
    public String replace(ITranslator translator, String prevText) {
        return this.expression;
    }
}


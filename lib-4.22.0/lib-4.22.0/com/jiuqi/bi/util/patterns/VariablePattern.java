/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.patterns;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.patterns.IPattern;
import com.jiuqi.bi.util.patterns.ITranslator;
import com.jiuqi.bi.util.patterns.PatternException;
import java.util.Map;

final class VariablePattern
implements IPattern {
    private String name;
    private int length;

    VariablePattern() {
    }

    public void parse(String expression) throws PatternException {
        int p;
        this.clear();
        if (expression == null) {
            return;
        }
        int n = p = expression.startsWith("=") ? -1 : expression.lastIndexOf(58);
        if (p == -1) {
            this.name = expression;
            this.length = 0;
        } else {
            this.name = expression.substring(0, p);
            String valStr = expression.substring(p + 1);
            if (StringUtils.isEmpty(valStr)) {
                this.length = 0;
            } else {
                try {
                    this.length = Integer.parseInt(valStr);
                }
                catch (NumberFormatException e) {
                    throw new PatternException(e);
                }
            }
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("${");
        if (this.length == 0) {
            buffer.append(this.name);
        } else {
            buffer.append(this.name).append(':').append(this.length);
        }
        buffer.append('}');
        return buffer.toString();
    }

    @Override
    public void add(IPattern pattern) {
    }

    @Override
    public void add(int index, IPattern pattern) {
    }

    @Override
    public void clear() {
        this.name = null;
        this.length = 0;
    }

    @Override
    public IPattern get(int index) {
        return null;
    }

    @Override
    public String getExpression() {
        return this.name;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public int getType() {
        return 1;
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
        try {
            this.parse(value);
        }
        catch (PatternException e) {
            this.clear();
        }
    }

    @Override
    public void setLength(int value) {
        this.length = value;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int match(String s, int start, Map<String, String> values) {
        if (this.length == 0 || start + this.length > s.length()) {
            return -1;
        }
        return start + this.length;
    }

    @Override
    public String replace(ITranslator translator, String prevText) {
        String[] ret = translator.translate(this.name, this.length, prevText);
        if (ret == null) {
            return this.toString();
        }
        return ret.length == 0 ? "" : ret[0];
    }
}


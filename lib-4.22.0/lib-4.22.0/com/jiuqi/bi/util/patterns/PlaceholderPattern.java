/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.patterns;

import com.jiuqi.bi.util.patterns.ConstPattern;
import com.jiuqi.bi.util.patterns.Grammar;
import com.jiuqi.bi.util.patterns.IPattern;
import com.jiuqi.bi.util.patterns.ITranslator;
import com.jiuqi.bi.util.patterns.PatternException;
import com.jiuqi.bi.util.patterns.VariablePattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class PlaceholderPattern
implements IPattern {
    private List<IPattern> items = new ArrayList<IPattern>();
    private String expression;
    private boolean isVariable;

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (this.isVariable) {
            buffer.append("${");
        }
        for (int i = 0; i < this.size(); ++i) {
            buffer.append(this.get(i).toString());
        }
        if (this.isVariable) {
            buffer.append("}");
        }
        return buffer.toString();
    }

    public boolean isVariable() {
        return this.isVariable;
    }

    public void setIsVariable(boolean value) {
        this.isVariable = value;
    }

    public void parse(String expression, Grammar grammar) throws PatternException {
        this.clear();
        this.expression = expression;
        if (expression == null) {
            return;
        }
        int cur = 0;
        int start = 0;
        int stackSize = 0;
        boolean startVar = false;
        block4: while (cur < expression.length()) {
            int next;
            switch (this.charOf(cur)) {
                case '$': {
                    if (this.charOf(cur + 1) == '{') {
                        if (startVar) {
                            ++stackSize;
                        } else {
                            this.addConst(start, cur);
                            start = cur;
                            startVar = true;
                            stackSize = 0;
                        }
                        cur += 2;
                        continue block4;
                    }
                    ++cur;
                    continue block4;
                }
                case '}': {
                    if (startVar) {
                        if (stackSize == 0) {
                            this.addVariable(start + 2, cur);
                            startVar = false;
                            start = cur + 1;
                        } else {
                            --stackSize;
                        }
                    }
                    ++cur;
                    continue block4;
                }
            }
            int n = next = grammar == null || startVar ? Integer.MIN_VALUE : grammar.match(expression, cur);
            if (next == Integer.MIN_VALUE) {
                ++cur;
                continue;
            }
            if (next < 0) {
                String strExpr = expression.substring(cur, next = -next);
                if (strExpr.indexOf("${") >= 0) {
                    this.addConst(start, cur);
                    PlaceholderPattern placeHolder = new PlaceholderPattern();
                    placeHolder.setExpression(strExpr);
                    this.add(placeHolder);
                    start = next;
                    cur = next;
                    continue;
                }
                cur = next;
                continue;
            }
            cur = next;
        }
        if (cur == expression.length() && cur > start) {
            this.addConst(start, cur);
        }
    }

    private char charOf(int index) {
        return index < 0 || index >= this.expression.length() ? (char)'\u0000' : this.expression.charAt(index);
    }

    private void addConst(int start, int cur) {
        if (start >= cur) {
            return;
        }
        String s = this.expression.substring(start, cur);
        ConstPattern p = new ConstPattern(s);
        this.add(p);
    }

    private void addVariable(int start, int cur) throws PatternException {
        if (start >= cur) {
            return;
        }
        String s = this.expression.substring(start, cur);
        if (this.isInlineVariable(s)) {
            PlaceholderPattern p = new PlaceholderPattern();
            p.parse(s, null);
            p.setIsVariable(true);
            this.add(p);
        } else {
            VariablePattern p = new VariablePattern();
            p.parse(s);
            this.add(p);
        }
    }

    private boolean isInlineVariable(String var) {
        int p1 = var.indexOf("${");
        int p2 = var.lastIndexOf(125);
        return p1 >= 0 && p1 < p2;
    }

    @Override
    public void add(IPattern pattern) {
        this.items.add(pattern);
    }

    @Override
    public void add(int index, IPattern pattern) {
        this.items.add(index, pattern);
    }

    @Override
    public void clear() {
        this.items.clear();
        this.expression = null;
        this.isVariable = false;
    }

    @Override
    public IPattern get(int index) {
        return this.items.get(index);
    }

    @Override
    public String getExpression() {
        return this.toString();
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public int indexOf(IPattern pattern) {
        return this.items.indexOf(pattern);
    }

    @Override
    public void remove(int index) {
        this.items.remove(index);
    }

    @Override
    public void setExpression(String value) {
        try {
            this.parse(value, null);
        }
        catch (PatternException e) {
            this.clear();
        }
    }

    @Override
    public void setLength(int value) {
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Deprecated
    public int match(String s, int start, Map values) {
        return this.matchFrom(s, start, values);
    }

    public int matchFrom(String s, int start, Map<String, String> values) {
        if (this.isVariable) {
            return -1;
        }
        int next = start;
        for (int cur = 0; cur < this.size(); ++cur) {
            switch (this.get(cur).getType()) {
                case 0: {
                    next = this.get(cur).match(s, start, values);
                    if (next != -1) break;
                    return -1;
                }
                case 1: {
                    if (this.get(cur).getLength() > 0) {
                        next = this.get(cur).match(s, start, values);
                        if (next == -1) {
                            return -1;
                        }
                        String value = s.substring(start, next);
                        values.put(this.get(cur).getExpression(), value);
                        break;
                    }
                    if (cur >= this.size() - 1) {
                        String value = s.substring(start);
                        values.put(this.get(cur).getExpression(), value);
                        next = s.length();
                        break;
                    }
                    if (this.get(cur + 1).getType() == 0) {
                        next = s.indexOf(this.get(cur + 1).getExpression(), start + 1);
                        if (next == -1) {
                            return -1;
                        }
                        String value = s.substring(start, next);
                        values.put(this.get(cur).getExpression(), value);
                        break;
                    }
                    int backLen = this.statNextLength(cur);
                    if (backLen == -1 || start >= s.length() - backLen) {
                        return -1;
                    }
                    next = s.length() - backLen;
                    String value = s.substring(start, next);
                    values.put(this.get(cur).getExpression(), value);
                }
            }
            start = next;
        }
        return start;
    }

    private int statNextLength(int cur) {
        int len = 0;
        block4: for (int i = cur + 1; i < this.size(); ++i) {
            switch (this.get(i).getType()) {
                case 0: {
                    len += this.get(i).getLength();
                    continue block4;
                }
                case 1: {
                    if (this.get(i).getLength() > 0) {
                        len += this.get(i).getLength();
                        continue block4;
                    }
                    return -1;
                }
                default: {
                    return -1;
                }
            }
        }
        return len;
    }

    @Override
    public String replace(ITranslator translator, String prevText) {
        StringBuffer buffer = new StringBuffer();
        String text = prevText;
        for (int i = 0; i < this.size(); ++i) {
            text = this.get(i).replace(translator, text);
            buffer.append(text);
        }
        String expr = buffer.toString();
        if (this.isVariable) {
            String[] ret = translator.translate(expr, 0, prevText);
            if (ret == null) {
                return expr;
            }
            return ret.length == 0 ? "" : ret[0];
        }
        return expr;
    }
}


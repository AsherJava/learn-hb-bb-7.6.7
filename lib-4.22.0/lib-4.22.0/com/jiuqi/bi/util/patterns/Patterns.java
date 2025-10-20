/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.patterns;

import com.jiuqi.bi.util.patterns.Grammar;
import com.jiuqi.bi.util.patterns.IPattern;
import com.jiuqi.bi.util.patterns.ITranslator;
import com.jiuqi.bi.util.patterns.PatternException;
import com.jiuqi.bi.util.patterns.PlaceholderPattern;
import java.util.ArrayList;
import java.util.Map;

public class Patterns {
    PlaceholderPattern root = new PlaceholderPattern();

    public static Patterns getInstance() {
        return new Patterns();
    }

    protected Patterns() {
    }

    public void parse(String patternStr) throws PatternException {
        this.root.parse(patternStr, null);
    }

    public void parse(String patternStr, Grammar grammar) throws PatternException {
        this.root.parse(patternStr, grammar);
    }

    public String[] getAllVariable() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < this.root.size(); ++i) {
            IPattern p = this.root.get(i);
            if (p.getType() != 1) continue;
            list.add(p.getExpression());
        }
        return list.toArray(new String[list.size()]);
    }

    @Deprecated
    public boolean match(String s, Map values) {
        return this.matchText(s, values);
    }

    public boolean matchText(String s, Map<String, String> values) {
        if (s == null) {
            return false;
        }
        int p = this.root.matchFrom(s, 0, values);
        if (p == -1) {
            return false;
        }
        return p == s.length();
    }

    public String repace(ITranslator translator) {
        return this.root.replace(translator, null);
    }

    protected IPattern getRoot() {
        return this.root;
    }

    public String toString() {
        if (this.root.size() == 0) {
            return "(null)";
        }
        return this.root.toString();
    }
}


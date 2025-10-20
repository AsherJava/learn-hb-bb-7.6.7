/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.patterns;

import com.jiuqi.bi.util.patterns.ITranslator;
import java.util.Map;

interface IPattern {
    public static final int CONST = 0;
    public static final int VARIABLE = 1;
    public static final int PLACEHODER = 2;

    public int getType();

    public int size();

    public IPattern get(int var1);

    public void add(IPattern var1);

    public void add(int var1, IPattern var2);

    public void remove(int var1);

    public int indexOf(IPattern var1);

    public void clear();

    public String getExpression();

    public void setExpression(String var1);

    public int getLength();

    public void setLength(int var1);

    public int match(String var1, int var2, Map<String, String> var3);

    public String replace(ITranslator var1, String var2);
}


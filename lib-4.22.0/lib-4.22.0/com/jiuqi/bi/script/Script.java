/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script;

import com.jiuqi.bi.script.CompileException;
import com.jiuqi.bi.script.EvalException;

public interface Script {
    public Object evaluate(String var1, String var2) throws EvalException, CompileException;

    public void compile(String var1, String var2) throws CompileException;

    public Object call(String var1, Object[] var2) throws NoSuchMethodException, EvalException, CompileException;

    public void put(String var1, Object var2);

    public Object get(String var1);

    public Object toScript(Object var1);

    public Object toJava(Object var1);
}


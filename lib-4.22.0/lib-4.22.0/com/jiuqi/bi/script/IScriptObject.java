/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script;

public interface IScriptObject {
    public String[] getPropertyNames();

    public Object getProperty(String var1);

    public void setProperty(String var1, Object var2);

    public void delProperty(String var1);

    public boolean hasProperty(String var1);

    public String[] getFunctionNames();

    public boolean hasFunction(String var1);

    public Object invoke(String var1, Object[] var2);
}


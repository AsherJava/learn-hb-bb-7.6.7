/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script;

import com.jiuqi.bi.script.Script;

public interface ScriptFactory {
    public String[] getNames();

    public Script createScript();

    public String getFactoryName();

    public String getVersion();
}


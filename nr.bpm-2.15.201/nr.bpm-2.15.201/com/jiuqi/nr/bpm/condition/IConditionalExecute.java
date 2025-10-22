/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.condition;

import com.jiuqi.nr.bpm.common.Task;

public interface IConditionalExecute {
    public String getTitle();

    public boolean execute(Task var1, String var2, String var3, String var4, String var5);

    public boolean execute(String var1, String var2);

    public String className();
}


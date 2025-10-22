/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.List;
import java.util.Map;

public interface IUnitLeafFinder {
    public Map<String, List<String>> getAllSubLeafUnits(ExecutorContext var1, String var2, String var3, List<String> var4);

    public List<String> getSubLeafUnits(ExecutorContext var1, String var2, String var3, String var4);
}


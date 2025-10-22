/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.copydes;

import com.jiuqi.nr.dataentry.copydes.IUnsupportedDesHandler;
import java.util.Map;

public interface IExtensionFactory {
    public IUnsupportedDesHandler getUnsupportedDesHandler(Map<String, String> var1);
}


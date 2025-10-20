/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import java.util.HashSet;
import java.util.Set;

public class FrontFunctionManager {
    private Set<String> functions = new HashSet<String>();
    private static FrontFunctionManager instance = new FrontFunctionManager();

    private FrontFunctionManager() {
    }

    public static boolean isFrontFunction(String name) {
        return FrontFunctionManager.instance.functions.contains(name);
    }

    static {
        FrontFunctionManager.instance.functions.add("TestRegular");
        FrontFunctionManager.instance.functions.add("IsNotNull");
        FrontFunctionManager.instance.functions.add("IsNull");
        FrontFunctionManager.instance.functions.add("Len");
    }
}


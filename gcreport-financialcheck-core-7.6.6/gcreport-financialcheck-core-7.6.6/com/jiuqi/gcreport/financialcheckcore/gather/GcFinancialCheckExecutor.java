/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.gather;

import java.util.List;
import java.util.Map;

public interface GcFinancialCheckExecutor {
    public List<Map<String, String>> listShowTypeForPage();

    public Object execute(Object var1, String var2, String var3);
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.impl.dao;

import java.util.List;
import java.util.Map;

public interface GcTaskDataCopyDAO {
    public List<Map<String, Object>> queryMapping(String var1, String var2, String var3);

    public List<Map<String, Object>> queryFloatMapping(String var1, String var2, String var3);

    public List<Map<String, Object>> querySrcData(String var1, String var2, String var3, List<String> var4);

    public void saveData(String var1, List<String> var2, List<Object[]> var3);

    public void deleteData(String var1, String var2, String var3);

    public void deleteFloatData(String var1, List<String> var2, String var3, String var4);

    public void saveFloatData(String var1, List<Object[]> var2);
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ICheckFilter {
    public boolean check(Map<String, Object> var1, String var2, Date var3);

    public List<Boolean> check(List<Map<String, Object>> var1, String var2, Date var3);
}


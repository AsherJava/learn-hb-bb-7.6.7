/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.impl.cache;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;

public interface BaseDataCache {
    public void clearCache();

    public GcBaseData queryBaseDataByCode(String var1, String var2);
}


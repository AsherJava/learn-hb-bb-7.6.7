/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.dao;

import com.jiuqi.gcreport.bde.fetch.impl.entity.GcFetchTaskLogClobEO;
import java.util.List;

public interface GcFetchTaskLogClobDao {
    public List<GcFetchTaskLogClobEO> listById(List<String> var1);

    public void save(GcFetchTaskLogClobEO var1);

    public GcFetchTaskLogClobEO get(String var1);
}


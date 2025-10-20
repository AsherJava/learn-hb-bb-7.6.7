/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.service;

import com.jiuqi.gcreport.inputdata.inputdata.entity.OffsetQueueEO;
import java.util.Set;

public interface OffsetQueueService {
    public boolean joinQueue(OffsetQueueEO var1);

    public void removeQueue(String var1);

    public Set<String> queryUnitByCondition(String var1, String var2, String var3, long var4, String var6);
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.item.service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface GcDbLockService {
    public String tryLock(Collection<String> var1, String var2, String var3);

    public String tryLock(Collection<String> var1, long var2, TimeUnit var4, String var5, String var6);

    public void unlock(String var1);

    public void deleteExpiredData();

    public String queryUserNameByInputItemId(Collection<String> var1, String var2);
}


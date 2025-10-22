/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.service;

import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface InputDataLockService {
    public String tryLock(Collection<String> var1, InputWriteNecLimitCondition var2, String var3);

    public String tryLock(Collection<String> var1, InputWriteNecLimitCondition var2, long var3, TimeUnit var5, String var6);

    public void unlock(String var1);

    public void deleteExpiredData();

    public String queryUserNameByInputItemId(Collection<String> var1);
}


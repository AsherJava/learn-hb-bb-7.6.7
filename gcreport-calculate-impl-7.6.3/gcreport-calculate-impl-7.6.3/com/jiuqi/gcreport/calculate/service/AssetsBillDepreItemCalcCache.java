/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.calculate.service;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.List;
import java.util.concurrent.Callable;

public interface AssetsBillDepreItemCalcCache {
    public List<DefaultTableEntity> getCacheData(String var1, String var2, Callable<List<DefaultTableEntity>> var3);

    public void removeCacheBySn(String var1);
}


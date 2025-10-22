/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batchcheck.dao;

import com.jiuqi.nr.batchcheck.bean.CheckParamImpl;

public interface IBatchCheckInfoDao {
    public CheckParamImpl queryBatchCheckInfoByUserId(String var1);

    public boolean addBatchCheckInfo(CheckParamImpl var1, String var2, String var3);

    public boolean updataBatchCheckInfo(CheckParamImpl var1, String var2, String var3);
}


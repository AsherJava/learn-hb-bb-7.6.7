/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batchcheck.service;

import com.jiuqi.nr.batchcheck.bean.CheckFromInfo;
import com.jiuqi.nr.batchcheck.bean.CheckParamImpl;
import java.time.Instant;
import java.util.List;

public interface IBatchCheckParamService {
    public List<CheckFromInfo> getAllFormsByScheme(String var1);

    public CheckParamImpl getBatchCheckParam();

    public Instant updataBatchCheckParam(CheckParamImpl var1) throws Exception;
}


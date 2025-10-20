/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.DeployDTO
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.domain.biz.DeployDTO;
import java.util.Map;

public interface MetaInfoService {
    public void syncMetaInfo(DeployDTO var1);

    public Map<String, Object> getTablesName(String var1);
}


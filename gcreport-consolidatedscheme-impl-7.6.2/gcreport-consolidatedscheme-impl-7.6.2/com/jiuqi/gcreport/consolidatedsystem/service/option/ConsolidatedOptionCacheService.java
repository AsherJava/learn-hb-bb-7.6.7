/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 */
package com.jiuqi.gcreport.consolidatedsystem.service.option;

import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;

public interface ConsolidatedOptionCacheService {
    public ConsolidatedOptionVO getConOptionBySystemId(String var1);

    public ConsolidatedOptionVO getConOptionBySchemeId(String var1, String var2);
}


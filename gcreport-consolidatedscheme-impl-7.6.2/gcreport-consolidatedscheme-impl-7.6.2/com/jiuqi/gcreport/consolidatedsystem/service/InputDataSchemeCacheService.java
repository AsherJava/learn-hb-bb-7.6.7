/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 */
package com.jiuqi.gcreport.consolidatedsystem.service;

import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;

public interface InputDataSchemeCacheService {
    public InputDataSchemeVO getInputDataSchemeByDataSchemeKey(String var1);

    public void clearAllCache();
}


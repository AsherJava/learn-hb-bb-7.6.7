/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.EfdcFormulaResultVO
 */
package com.jiuqi.gcreport.bde.penetrate.impl.pierce.service;

import com.jiuqi.gcreport.bde.penetrate.client.dto.GcFetchPierceDTO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.EfdcFormulaResultVO;

public interface GcEfdcFetchPierceService {
    public EfdcFormulaResultVO efdcFormulaAnalysis(GcFetchPierceDTO var1);

    public Boolean judgeEfdcFormulaIsNotEmpty(GcFetchPierceDTO var1);
}


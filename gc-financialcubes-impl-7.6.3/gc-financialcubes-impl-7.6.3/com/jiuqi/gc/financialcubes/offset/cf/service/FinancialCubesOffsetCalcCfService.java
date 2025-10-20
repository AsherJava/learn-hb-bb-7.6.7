/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 */
package com.jiuqi.gc.financialcubes.offset.cf.service;

import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;

public interface FinancialCubesOffsetCalcCfService {
    public String calcFinancialCubesCf(FinancialCubesOffsetTaskDto var1);

    public String rebuildFinancialCubesCf(FinancialCubesRebuildDTO var1, int var2);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 */
package com.jiuqi.gc.financialcubes.offset.dim.service;

import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;

public interface FinancialCubesOffsetCalcDimService {
    public String calcFinancialCubesDim(FinancialCubesOffsetTaskDto var1);

    public String rebuildFinancialCubesDim(FinancialCubesRebuildDTO var1, int var2);
}


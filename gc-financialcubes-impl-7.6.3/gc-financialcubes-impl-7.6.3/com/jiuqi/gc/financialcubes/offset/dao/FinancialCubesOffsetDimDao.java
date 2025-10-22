/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 */
package com.jiuqi.gc.financialcubes.offset.dao;

import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FinancialCubesOffsetDimDao {
    public List<Map<String, Object>> queryOffsetData(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public List<Map<String, Object>> queryOffsetDataByRebuild(FinancialCubesRebuildDTO var1, Set<String> var2, String var3);
}


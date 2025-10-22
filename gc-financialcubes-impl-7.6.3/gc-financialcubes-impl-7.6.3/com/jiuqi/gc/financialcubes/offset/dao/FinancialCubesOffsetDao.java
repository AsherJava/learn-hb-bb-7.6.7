/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.offset.dao;

import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import java.util.Collection;
import java.util.List;

public interface FinancialCubesOffsetDao {
    public List<FinancialCubesOffsetTaskDto> listFinancialCubesOffsetTaskDtoByIdList(Collection<String> var1, boolean var2);

    public List<FinancialCubesOffsetTaskDto> listFinancialCubesOffsetTaskDtoAgingByIdList(Collection<String> var1, List<String> var2);
}


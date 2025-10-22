/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.offset.dao;

import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import java.util.List;
import java.util.Set;

public interface FinancialCubesOffsetAgingBizDao {
    public int insertNotExistsDataByTemp(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public int deleteOffsetByTemp(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public int updateFinancialCubes(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public int insertTempTable(List<Object[]> var1, Set<String> var2);

    public void cleanTempTable();

    public int insertDataToUpdateTempTable(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public void deleteTempDataByUpdateTempTable();

    public void cleanUpdateTempTable();
}


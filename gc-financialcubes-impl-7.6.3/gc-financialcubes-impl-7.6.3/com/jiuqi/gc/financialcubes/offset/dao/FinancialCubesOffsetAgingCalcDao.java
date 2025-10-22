/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 */
package com.jiuqi.gc.financialcubes.offset.dao;

import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import java.util.Set;

public interface FinancialCubesOffsetAgingCalcDao {
    public int insertTempTable(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public int insertNotExistsDataByTemp(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public int deleteOffsetByTemp(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public int updateFinancialCubes(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public int insertTempTableByRebuild(FinancialCubesRebuildDTO var1, Set<String> var2, String var3);

    public void cleanTempTable();

    public int insertDataToUpdateTempTable(FinancialCubesOffsetTaskDto var1, Set<String> var2);

    public void deleteTempDataByUpdateTempTable();

    public void cleanUpdateTempTable();
}


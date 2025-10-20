/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gc.financialcubes.offset.utils;

import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;

public class FinancialCubesOffsetCalcUtils {
    public static String getPriorPeriod(String periodStr) {
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        defaultPeriodAdapter.priorPeriod(periodWrapper);
        return periodWrapper.toString();
    }

    public static FinancialCubesOffsetTaskDto initOffsetTaskDto(FinancialCubesRebuildDTO rebuildDTO, int sn) {
        FinancialCubesOffsetTaskDto offsetTaskDto = new FinancialCubesOffsetTaskDto();
        offsetTaskDto.setOrgType(rebuildDTO.getOrgType());
        offsetTaskDto.setDataTime(rebuildDTO.getDataTime());
        offsetTaskDto.setDiffUnitId(rebuildDTO.getUnitCode());
        offsetTaskDto.setRebuildFlag(true);
        offsetTaskDto.setBatchNum(sn);
        offsetTaskDto.setSubjectCodes(rebuildDTO.getSubjectCodeList());
        offsetTaskDto.setPeriodTypeEnum(FinancialCubesPeriodTypeEnum.valueOf((String)rebuildDTO.getPeriodType()));
        return offsetTaskDto;
    }
}


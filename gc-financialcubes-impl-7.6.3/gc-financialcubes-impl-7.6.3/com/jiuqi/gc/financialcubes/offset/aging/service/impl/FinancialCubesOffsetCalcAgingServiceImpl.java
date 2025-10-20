/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 */
package com.jiuqi.gc.financialcubes.offset.aging.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.gc.financialcubes.common.dao.TempCodeDao;
import com.jiuqi.gc.financialcubes.offset.aging.service.FinancialCubesOffsetCalcAgingService;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetAgingBizDao;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetAgingSysDao;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.gc.financialcubes.offset.utils.FinancialCubesOffsetCalcUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialCubesOffsetCalcAgingServiceImpl
implements FinancialCubesOffsetCalcAgingService {
    @Autowired
    private FinancialCubesOffsetAgingBizDao offsetAgingBizDao;
    @Autowired
    private FinancialCubesOffsetAgingSysDao offsetAgingSysDao;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private TempCodeDao tempCodeDao;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public String calcFinancialCubesAging(FinancialCubesOffsetTaskDto offsetTaskDto) {
        StringBuilder log = new StringBuilder();
        Set<String> dimensionCodeSet = this.dimensionService.findDimFieldsByTableName("GC_FINCUBES_AGING").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        Set adjustAssistDimSet = this.dimensionService.findDimFieldsByTableName("GC_OFFSETVCHRITEM").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        dimensionCodeSet.retainAll(adjustAssistDimSet);
        log.append(String.format("\u8d26\u9f84\u5408\u5e76\u5e95\u7a3f\u8868" + offsetTaskDto.getPeriodTypeEnum().getName() + "\u7c7b\u578b\u8ba1\u7b97\u5904\u7406\u53c2\u6570\u3010%1$s\u3011\n", offsetTaskDto));
        try {
            List<Object[]> offsetData = this.offsetAgingSysDao.queryOffsetData(offsetTaskDto, dimensionCodeSet);
            int datas = this.offsetAgingBizDao.insertTempTable(offsetData, dimensionCodeSet);
            int count = this.calcFinancialCubesAgingByTemp(offsetTaskDto, dimensionCodeSet, datas);
            log.append(String.format("\u591a\u7ef4\u5408\u5e76\u5e95\u7a3f\u8868%1$s\u7c7b\u578b\u91c7\u96c6\u5904\u7406\u5b8c\u6210\uff0c\u5171\u66f4\u65b0\u4e86%2$s\u6761\u6570\u636e\u3002\n", offsetTaskDto.getPeriodTypeEnum().getName(), count));
        }
        finally {
            this.offsetAgingBizDao.cleanTempTable();
            this.offsetAgingBizDao.cleanUpdateTempTable();
        }
        return log.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public String rebuildFinancialCubesAging(FinancialCubesRebuildDTO rebuildDTO, int sn) {
        StringBuilder log = new StringBuilder();
        FinancialCubesPeriodTypeEnum periodTypeEnum = FinancialCubesPeriodTypeEnum.getByCode((String)rebuildDTO.getPeriodType());
        log.append(String.format("\u8d26\u9f84\u5408\u5e76\u5e95\u7a3f\u8868" + periodTypeEnum.getName() + "\u7c7b\u578b\u91cd\u7b97\u5904\u7406\u53c2\u6570\u3010%1$s\u3011\n", rebuildDTO));
        Set<String> dimensionCodeSet = this.dimensionService.findDimFieldsByTableName("GC_FINCUBES_AGING").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        Set adjustAssistDimSet = this.dimensionService.findDimFieldsByTableName("GC_OFFSETVCHRITEM").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        dimensionCodeSet.retainAll(adjustAssistDimSet);
        FinancialCubesOffsetTaskDto offsetTaskDto = FinancialCubesOffsetCalcUtils.initOffsetTaskDto(rebuildDTO, sn);
        String subjectTempGroupId = UUIDUtils.newUUIDStr();
        try {
            IdTemporaryTableUtils.insertTempStr((String)subjectTempGroupId, (Collection)rebuildDTO.getSubjectCodeList());
            this.tempCodeDao.batchInsert(rebuildDTO.getSubjectCodeList());
            List<Object[]> offsetData = this.offsetAgingSysDao.queryOffsetDataByRebuild(rebuildDTO, dimensionCodeSet);
            int datas = this.offsetAgingBizDao.insertTempTable(offsetData, dimensionCodeSet);
            int count = this.calcFinancialCubesAgingByTemp(offsetTaskDto, dimensionCodeSet, datas);
            log.append(String.format("\u8d26\u9f84\u5408\u5e76\u5e95\u7a3f\u8868%1$s\u7c7b\u578b\u91c7\u96c6\u5904\u7406\u5b8c\u6210\uff0c\u5171\u66f4\u65b0\u4e86%2$s\u6761\u6570\u636e\u3002\n", periodTypeEnum.getName(), count));
        }
        finally {
            this.offsetAgingBizDao.cleanTempTable();
            this.offsetAgingBizDao.cleanUpdateTempTable();
            IdTemporaryTableUtils.deteteByGroupId((String)subjectTempGroupId);
            this.tempCodeDao.cleanTemp();
        }
        return log.toString();
    }

    private int calcFinancialCubesAgingByTemp(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet, int datas) {
        int count = 0;
        int addData = this.offsetAgingBizDao.insertDataToUpdateTempTable(offsetTaskDto, dimensionCodeSet);
        if (addData > 0) {
            this.offsetAgingBizDao.deleteTempDataByUpdateTempTable();
        }
        count += this.offsetAgingBizDao.deleteOffsetByTemp(offsetTaskDto, dimensionCodeSet);
        if (datas > addData) {
            count += this.offsetAgingBizDao.updateFinancialCubes(offsetTaskDto, dimensionCodeSet);
        }
        if (addData > 0) {
            count += this.offsetAgingBizDao.insertNotExistsDataByTemp(offsetTaskDto, dimensionCodeSet);
        }
        return count;
    }
}


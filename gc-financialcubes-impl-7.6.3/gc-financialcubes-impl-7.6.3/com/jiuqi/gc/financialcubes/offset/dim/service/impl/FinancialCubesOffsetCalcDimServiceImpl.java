/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO
 *  com.jiuqi.common.financialcubes.util.BooleanConsumerUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  javax.annotation.Resource
 */
package com.jiuqi.gc.financialcubes.offset.dim.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesRebuildDTO;
import com.jiuqi.common.financialcubes.util.BooleanConsumerUtils;
import com.jiuqi.gc.financialcubes.common.dao.TempCodeDao;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetBizDao;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetDimDao;
import com.jiuqi.gc.financialcubes.offset.dim.service.FinancialCubesOffsetCalcDimService;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.gc.financialcubes.offset.utils.FinancialCubesOffsetCalcUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialCubesOffsetCalcDimServiceImpl
implements FinancialCubesOffsetCalcDimService {
    @Autowired
    private FinancialCubesOffsetBizDao offsetBizDao;
    @Autowired
    private FinancialCubesOffsetDimDao offsetDimDao;
    @Autowired
    private DimensionService dimensionService;
    @Resource
    private TempCodeDao tempCodeDao;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public String calcFinancialCubesDim(FinancialCubesOffsetTaskDto offsetTaskDto) {
        StringBuilder log = new StringBuilder();
        Set<String> dimensionCodeSet = this.dimensionService.findDimFieldsByTableName("GC_FINCUBES_DIM").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        Set adjustAssistDimSet = this.dimensionService.findDimFieldsByTableName("GC_OFFSETVCHRITEM").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        dimensionCodeSet.retainAll(adjustAssistDimSet);
        log.append(String.format("\u591a\u7ef4\u5408\u5e76\u5e95\u7a3f\u8868" + offsetTaskDto.getPeriodTypeEnum().getName() + "\u7c7b\u578b\u8ba1\u7b97\u5904\u7406\u53c2\u6570\u3010%1$s\u3011\n", offsetTaskDto));
        List<Map<String, Object>> resultList = this.offsetDimDao.queryOffsetData(offsetTaskDto, dimensionCodeSet);
        try {
            int datas = this.offsetBizDao.insertTempTable(resultList, dimensionCodeSet);
            int count = this.calcFinancialCubesDimByTemp(offsetTaskDto, dimensionCodeSet, datas);
            log.append(String.format("\u591a\u7ef4\u5408\u5e76\u5e95\u7a3f\u8868%1$s\u7c7b\u578b\u91c7\u96c6\u5904\u7406\u5b8c\u6210\uff0c\u5171\u66f4\u65b0\u4e86%2$s\u6761\u6570\u636e\u3002\n", offsetTaskDto.getPeriodTypeEnum().getName(), count));
        }
        finally {
            this.offsetBizDao.cleanTempTable();
            this.offsetBizDao.cleanUpdateTempTable();
        }
        return log.toString();
    }

    private int calcFinancialCubesDimByTemp(FinancialCubesOffsetTaskDto offsetTaskDto, Set<String> dimensionCodeSet, Integer datas) {
        int addData;
        int count = 0;
        if (!offsetTaskDto.getDataTime().endsWith("01")) {
            String priorPeriod = FinancialCubesOffsetCalcUtils.getPriorPeriod(offsetTaskDto.getDataTime());
            this.offsetBizDao.updateTempTableByPriorPeriod(offsetTaskDto, priorPeriod, dimensionCodeSet);
        }
        if ((addData = this.offsetBizDao.insertDataToUpdateTempTable(offsetTaskDto, dimensionCodeSet)) > 0) {
            this.offsetBizDao.deleteTempDataByUpdateTempTable();
        }
        count += this.offsetBizDao.deleteOffsetByTemp(offsetTaskDto, dimensionCodeSet);
        if (datas > addData) {
            count += this.offsetBizDao.updateFinancialCubes(offsetTaskDto, dimensionCodeSet);
        }
        if (addData > 0) {
            count += this.offsetBizDao.insertNotExistsDataByTemp(offsetTaskDto, dimensionCodeSet);
        }
        return count;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String rebuildFinancialCubesDim(FinancialCubesRebuildDTO rebuildDTO, int sn) {
        StringBuilder log = new StringBuilder();
        FinancialCubesPeriodTypeEnum periodTypeEnum = FinancialCubesPeriodTypeEnum.getByCode((String)rebuildDTO.getPeriodType());
        log.append(String.format("\u591a\u7ef4\u5408\u5e76\u5e95\u7a3f\u8868" + periodTypeEnum.getName() + "\u7c7b\u578b\u91cd\u7b97\u5904\u7406\u53c2\u6570\u3010%1$s\u3011\n", rebuildDTO));
        Set<String> dimensionCodeSet = this.dimensionService.findDimFieldsByTableName("GC_FINCUBES_DIM").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        Set adjustAssistDimSet = this.dimensionService.findDimFieldsByTableName("GC_OFFSETVCHRITEM").stream().map(DimensionEO::getCode).collect(Collectors.toSet());
        dimensionCodeSet.retainAll(adjustAssistDimSet);
        FinancialCubesOffsetTaskDto offsetTaskDto = FinancialCubesOffsetCalcUtils.initOffsetTaskDto(rebuildDTO, sn);
        String subjectTempGroupId = UUIDUtils.newUUIDStr();
        try {
            IdTemporaryTableUtils.insertTempStr((String)subjectTempGroupId, (Collection)rebuildDTO.getSubjectCodeList());
            BooleanConsumerUtils.falseFunction((Boolean)CollectionUtils.isEmpty((Collection)rebuildDTO.getSubjectCodeList()), () -> this.tempCodeDao.batchInsert(rebuildDTO.getSubjectCodeList()));
            List<Map<String, Object>> resultList = this.offsetDimDao.queryOffsetDataByRebuild(rebuildDTO, dimensionCodeSet, subjectTempGroupId);
            int datas = this.offsetBizDao.insertTempTable(resultList, dimensionCodeSet);
            int count = this.calcFinancialCubesDimByTemp(offsetTaskDto, dimensionCodeSet, datas);
            log.append(String.format("\u591a\u7ef4\u5408\u5e76\u5e95\u7a3f\u8868%1$s\u7c7b\u578b\u91cd\u7b97\u5904\u7406\u5b8c\u6210\uff0c\u5171\u66f4\u65b0\u4e86%2$s\u6761\u6570\u636e\u3002\n", periodTypeEnum.getName(), count));
        }
        finally {
            this.offsetBizDao.cleanTempTable();
            this.offsetBizDao.cleanUpdateTempTable();
            this.tempCodeDao.cleanTemp();
            IdTemporaryTableUtils.deteteByGroupId((String)subjectTempGroupId);
        }
        return log.toString();
    }
}


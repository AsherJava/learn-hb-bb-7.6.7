/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.intf.FloatDefineEO
 *  com.jiuqi.bde.bizmodel.execute.dto.FetchFloatRowDTO
 *  com.jiuqi.bde.bizmodel.execute.dto.FloatRowResultEO
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.dto.FloatQueryFieldVO
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.floatmodel.impl.gather.FloatRegionHandlerGather
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 */
package com.jiuqi.bde.fetch.impl.request.service.impl;

import com.jiuqi.bde.base.intf.FloatDefineEO;
import com.jiuqi.bde.bizmodel.execute.dto.FetchFloatRowDTO;
import com.jiuqi.bde.bizmodel.execute.dto.FloatRowResultEO;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataRequestDTO;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.dto.FloatQueryFieldVO;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.fetch.impl.request.service.FetchDataAnalyzeFloatRegionService;
import com.jiuqi.bde.floatmodel.impl.gather.FloatRegionHandlerGather;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FetchDataAnalyzeFloatRegionServiceImpl
implements FetchDataAnalyzeFloatRegionService {
    @Autowired
    private FloatRegionHandlerGather floatRegionHandlerGather;

    @Override
    public FetchFloatRowDTO analyzeFloatRegion(FetchDataRequestDTO fetchRequestDTO) {
        FetchTaskContext fetchTaskContext = (FetchTaskContext)BeanConvertUtil.convert((Object)fetchRequestDTO.getFetchContext(), FetchTaskContext.class, (String[])new String[0]);
        fetchTaskContext.setOrgMapping(fetchRequestDTO.getOrgMapping());
        fetchTaskContext.setRequestTaskId(fetchRequestDTO.getRequestTaskId());
        FetchFloatRowResult fetchFloatRowResult = this.floatRegionHandlerGather.getFloatRegionHandlerByQueryType(fetchRequestDTO.getFloatSetting().getQueryType()).queryFloatRowDatas(fetchTaskContext, fetchRequestDTO.getFloatSetting().getQueryConfigInfo());
        if (fetchFloatRowResult == null || CollectionUtils.isEmpty((Collection)fetchFloatRowResult.getRowDatas())) {
            return null;
        }
        if (!CollectionUtils.isEmpty((Collection)fetchRequestDTO.getFilters())) {
            fetchFloatRowResult = this.filterFloatRowResult(fetchRequestDTO.getFloatSetting(), fetchRequestDTO.getFilters(), fetchFloatRowResult);
        }
        FetchFloatRowDTO fetchFloatRowDTO = this.convertFloatRowDatas(fetchRequestDTO.getFloatSetting().getQueryConfigInfo(), fetchFloatRowResult);
        return fetchFloatRowDTO;
    }

    private FetchFloatRowResult filterFloatRowResult(FetchRequestFloatSettingDTO floatRegionConfigVO, List<Map<String, Object>> filters, FetchFloatRowResult fetchFloatRowResult) {
        if (CollectionUtils.isEmpty(filters)) {
            return fetchFloatRowResult;
        }
        QueryConfigInfo queryConfigInfo = floatRegionConfigVO.getQueryConfigInfo();
        List zbMapping = queryConfigInfo.getZbMapping();
        Map fieldNameToZbMappingMap = zbMapping.stream().collect(Collectors.toMap(FloatZbMappingVO::getQueryName, Function.identity(), (o1, o2) -> o1));
        Map floatColumns = fetchFloatRowResult.getFloatColumns();
        HashMap<String, Integer> floatColumnZbIdToIndexMap = new HashMap<String, Integer>();
        for (String fieldName : floatColumns.keySet()) {
            if (!fieldNameToZbMappingMap.containsKey("${" + fieldName + "}")) continue;
            floatColumnZbIdToIndexMap.put(((FloatZbMappingVO)fieldNameToZbMappingMap.get("${" + fieldName + "}")).getFieldDefineId(), (Integer)floatColumns.get(fieldName));
        }
        List rowDataList = fetchFloatRowResult.getRowDatas();
        ArrayList<String[]> filterResultRowDataList = new ArrayList<String[]>();
        for (String[] rowData : rowDataList) {
            if (!this.matchFilterCondition(filters, floatColumnZbIdToIndexMap, rowData)) continue;
            filterResultRowDataList.add(rowData);
        }
        fetchFloatRowResult.setRowDatas(filterResultRowDataList);
        return fetchFloatRowResult;
    }

    private boolean matchFilterCondition(List<Map<String, Object>> filters, HashMap<String, Integer> floatColumnZbIdToIndexMap, String[] rowData) {
        for (Map<String, Object> filterMap : filters) {
            boolean matchFlag = true;
            for (String filedId : filterMap.keySet()) {
                String filedValueStr;
                Object filedValue;
                if (!floatColumnZbIdToIndexMap.containsKey(filedId) || (filedValue = filterMap.get(filedId)) == null || StringUtils.isEmpty((String)filedValue.toString()) || this.filedValueIsEquals(filedValueStr = filedValue.toString(), rowData[floatColumnZbIdToIndexMap.get(filedId)])) continue;
                matchFlag = false;
                break;
            }
            if (!matchFlag) continue;
            return true;
        }
        return false;
    }

    private boolean filedValueIsEquals(String filedValue, String rowDataValue) {
        if (filedValue.equals(rowDataValue)) {
            return true;
        }
        Double filedValueDouble = ConverterUtils.getAsDouble((Object)filedValue);
        if (filedValueDouble == null) {
            return false;
        }
        Double rowDataValueDouble = ConverterUtils.getAsDouble((Object)rowDataValue);
        if (rowDataValueDouble == null) {
            return false;
        }
        return filedValueDouble.equals(rowDataValueDouble);
    }

    public FetchFloatRowDTO convertFloatRowDatas(QueryConfigInfo queryConfigInfo, FetchFloatRowResult fetchFloatRowResult) {
        if (CollectionUtils.isEmpty((Collection)fetchFloatRowResult.getRowDatas())) {
            return new FetchFloatRowDTO();
        }
        List zbMapping = queryConfigInfo.getZbMapping();
        Map fieldNameToZbMappingMap = zbMapping.stream().collect(Collectors.toMap(FloatZbMappingVO::getQueryName, Function.identity(), (o1, o2) -> o1));
        List queryFieldVOList = queryConfigInfo.getQueryFields();
        HashSet usedFieldSet = new HashSet(queryConfigInfo.getUsedFields());
        ArrayList fieldIndexList = new ArrayList();
        ArrayList<FloatDefineEO> floatDefineEOList = new ArrayList<FloatDefineEO>();
        int fieldDefineOrder = 0;
        for (FloatQueryFieldVO floatQueryFieldVO : queryFieldVOList) {
            String fieldName = floatQueryFieldVO.getName().trim();
            if (!usedFieldSet.contains(fieldName)) continue;
            FloatDefineEO floatDefineEO = new FloatDefineEO();
            if (fieldNameToZbMappingMap.containsKey("${" + fieldName + "}")) {
                floatDefineEO.setFieldDefineId(((FloatZbMappingVO)fieldNameToZbMappingMap.get("${" + fieldName + "}")).getFieldDefineId());
            }
            floatDefineEO.setFieldDefineOrder(Integer.valueOf(fieldDefineOrder));
            floatDefineEO.setFieldDefineName(fieldName);
            floatDefineEO.setFieldDefineType(((ColumnTypeEnum)fetchFloatRowResult.getFloatColumnsType().get(fieldName)).getCode());
            floatDefineEOList.add(floatDefineEO);
            fieldIndexList.add(fetchFloatRowResult.getFloatColumns().get(fieldName));
            ++fieldDefineOrder;
        }
        ArrayList<FloatRowResultEO> floatRowResultEOList = new ArrayList<FloatRowResultEO>();
        int rowSize = fetchFloatRowResult.getRowDatas().size();
        for (int rowNum = 0; rowNum < rowSize; ++rowNum) {
            String[] rowdata = (String[])fetchFloatRowResult.getRowDatas().get(rowNum);
            FloatRowResultEO floatRowResultEO = new FloatRowResultEO();
            ArrayList<String> zbValues = new ArrayList<String>();
            for (Integer index : fieldIndexList) {
                zbValues.add(rowdata[index]);
            }
            floatRowResultEO.setFloatOrder(Integer.valueOf(rowNum));
            floatRowResultEO.setZbValues(zbValues);
            floatRowResultEOList.add(floatRowResultEO);
        }
        FetchFloatRowDTO fetchFloatRowDTO = new FetchFloatRowDTO();
        fetchFloatRowDTO.setFloatRowResultEOList(floatRowResultEOList);
        fetchFloatRowDTO.setFloatDefineEOList(floatDefineEOList);
        return fetchFloatRowDTO;
    }
}


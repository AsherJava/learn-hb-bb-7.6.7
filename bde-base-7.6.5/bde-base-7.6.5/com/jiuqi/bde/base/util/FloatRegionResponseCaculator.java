/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.dto.FloatZbMappingVO
 *  com.jiuqi.bde.common.dto.QueryConfigInfo
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO
 *  com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO
 *  com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.bde.base.util;

import com.jiuqi.bde.base.formula.FetchFormulaContext;
import com.jiuqi.bde.base.formula.FetchFormulaPaser;
import com.jiuqi.bde.base.formula.FetchFormulaUtil;
import com.jiuqi.bde.base.intf.FetchResultDim;
import com.jiuqi.bde.base.intf.FloatColResultVO;
import com.jiuqi.bde.base.intf.FloatFieldDefineValPojo;
import com.jiuqi.bde.base.result.dao.FetchResultDao;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.dto.FloatZbMappingVO;
import com.jiuqi.bde.common.dto.QueryConfigInfo;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFixedSettingDTO;
import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloatRegionResponseCaculator {
    private FetchRequestDTO fetchRequestDTO;
    private Integer routeNum;
    private final Logger logger = LoggerFactory.getLogger(FloatRegionResponseCaculator.class);

    public FloatRegionResponseCaculator(FetchRequestDTO fetchRequestDTO, Integer routeNum) {
        this.fetchRequestDTO = fetchRequestDTO;
        this.routeNum = routeNum;
    }

    public FloatRegionResultDTO doCaculate() {
        QueryConfigInfo queryConfigInfo = this.fetchRequestDTO.getFloatSetting().getQueryConfigInfo();
        FloatRegionResultDTO result = new FloatRegionResultDTO();
        FetchResultDao fetchResultDao = (FetchResultDao)ApplicationContextRegister.getBean(FetchResultDao.class);
        List zbMappingList = queryConfigInfo.getZbMapping();
        FetchResultDim fetchResultDim = new FetchResultDim(this.fetchRequestDTO.getRequestTaskId(), this.fetchRequestDTO.getFetchContext().getFormId(), this.fetchRequestDTO.getFetchContext().getRegionId(), this.routeNum);
        FetchFloatRowResult fetchFloatRowResult = fetchResultDao.getFloatRowDatasMap(fetchResultDim);
        if (fetchFloatRowResult == null || CollectionUtils.isEmpty((Collection)fetchFloatRowResult.getRowDatas())) {
            return new FloatRegionResultDTO(new HashMap(), new ArrayList());
        }
        HashMap<String, String> logicFormulaMap = new HashMap<String, String>();
        for (FetchRequestFixedSettingDTO fetchRequestFixedSettingDTO : this.fetchRequestDTO.getFixedSetting()) {
            if (StringUtils.isEmpty((String)fetchRequestFixedSettingDTO.getLogicFormula())) continue;
            logicFormulaMap.put(fetchRequestFixedSettingDTO.getFieldDefineId(), fetchRequestFixedSettingDTO.getLogicFormula());
        }
        Map<String, Object> envParam = FetchFormulaUtil.convtEnvByFetchContext(this.fetchRequestDTO);
        FetchFormulaPaser formulaPaser = new FetchFormulaPaser();
        FloatColResultVO floatColDataVo = fetchResultDao.getFloatColData(fetchResultDim);
        ArrayList<Object[]> rowDataList = new ArrayList<Object[]>(fetchFloatRowResult.getRowDatas().size());
        Object[] rowData = null;
        String[] floatRow = null;
        HashMap<String, Object> floatRowMap = null;
        for (int rowIdx = 0; rowIdx < fetchFloatRowResult.getRowDatas().size(); ++rowIdx) {
            floatRow = (String[])fetchFloatRowResult.getRowDatas().get(rowIdx);
            floatRowMap = new HashMap<String, Object>(fetchFloatRowResult.getFloatColumns().size());
            for (Map.Entry floatRowDataEntry : fetchFloatRowResult.getFloatColumns().entrySet()) {
                floatRowMap.put((String)floatRowDataEntry.getKey(), this.parseFloatRowVal(floatRow[(Integer)floatRowDataEntry.getValue()], (ColumnTypeEnum)fetchFloatRowResult.getFloatColumnsType().get(floatRowDataEntry.getKey())));
            }
            rowData = new Object[zbMappingList.size()];
            rowDataList.add(rowData);
            for (int colIdx = 0; colIdx < zbMappingList.size(); ++colIdx) {
                boolean strZbMapping;
                FloatZbMappingVO zbMapping = (FloatZbMappingVO)zbMappingList.get(colIdx);
                String fieldDefineId = zbMapping.getFieldDefineId();
                boolean bl = strZbMapping = !BdeCommonUtil.fieldDefineTypeIsNum((Integer)zbMapping.getFieldDefineType());
                if (zbMapping.getQueryName().startsWith("$")) {
                    String realQueryName = zbMapping.getQueryName().replace("${", "").replace("}", "");
                    if (fetchFloatRowResult.getFloatColumns() == null) {
                        this.logger.error("RequestTaskId:\u3010{}\u3011,floatRowDatasPair.getFirst()\u3010{}\u3011\u4e3a\u7a7a", (Object)this.fetchRequestDTO.getRequestTaskId(), (Object)floatColDataVo.toString());
                        continue;
                    }
                    if (fetchFloatRowResult.getFloatColumns().get(realQueryName) == null) {
                        throw new BusinessRuntimeException("\u53d6\u6570\u65b9\u6848\u5df2\u88ab \u4fee\u6539\u83b7\u53d6\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5\u3002");
                    }
                    rowData[colIdx] = floatRow[(Integer)fetchFloatRowResult.getFloatColumns().get(realQueryName)];
                    ColumnTypeEnum columnTypeEnum = (ColumnTypeEnum)fetchFloatRowResult.getFloatColumnsType().get(realQueryName);
                    switch (columnTypeEnum) {
                        case INT: {
                            rowData[colIdx] = Integer.valueOf(floatRow[(Integer)fetchFloatRowResult.getFloatColumns().get(realQueryName)]);
                            break;
                        }
                        case NUMBER: {
                            try {
                                rowData[colIdx] = Double.valueOf(floatRow[(Integer)fetchFloatRowResult.getFloatColumns().get(realQueryName)]);
                                break;
                            }
                            catch (Exception e) {
                                throw new BusinessRuntimeException("\u91d1\u989d\u7c7b\u578b\u8f6c\u6362\u5931\u8d25", (Throwable)e);
                            }
                        }
                        default: {
                            rowData[colIdx] = floatRow[(Integer)fetchFloatRowResult.getFloatColumns().get(realQueryName)];
                            break;
                        }
                    }
                    continue;
                }
                if (zbMapping.getQueryName().equals("=")) {
                    FetchFormulaContext context;
                    Map<String, Object> fetchResult = this.parseFieldDefineResult(fieldDefineId, floatColDataVo, rowIdx);
                    String formula = (String)logicFormulaMap.get(fieldDefineId);
                    if (fetchResult.isEmpty() && StringUtils.isEmpty((String)formula)) {
                        rowData[colIdx] = strZbMapping ? null : Double.valueOf(0.0);
                        continue;
                    }
                    if (fetchResult.isEmpty() && !StringUtils.isEmpty((String)formula)) {
                        context = new FetchFormulaContext(envParam);
                        context.setFloatRowMap(floatRowMap);
                        rowData[colIdx] = formulaPaser.evaluate(formula, context);
                        continue;
                    }
                    if (!fetchResult.isEmpty() && StringUtils.isEmpty((String)formula)) {
                        if (strZbMapping) {
                            if (fetchResult.size() > 1) {
                                throw new BusinessRuntimeException(String.format("\u6307\u6807\u3010%1$s\u3011\u4e3a\u5b57\u7b26\u578b\uff0c\u4ec5\u652f\u6301\u914d\u7f6e\u5355\u6761\u53d6\u6570\u8bbe\u7f6e", fieldDefineId));
                            }
                            rowData[colIdx] = fetchResult.values().toArray()[0];
                            continue;
                        }
                        Double sumVal = 0.0;
                        for (Object val : fetchResult.values()) {
                            sumVal = NumberUtils.sum((Double)sumVal, (Double)((Double)val));
                        }
                        rowData[colIdx] = sumVal;
                        continue;
                    }
                    context = new FetchFormulaContext(envParam);
                    context.setFloatRowMap(floatRowMap);
                    context.setFetchResultMap(fetchResult);
                    rowData[colIdx] = formulaPaser.evaluate(formula, context);
                    continue;
                }
                rowData[colIdx] = zbMapping.getQueryName();
            }
        }
        result.setFloatColumns(this.buildFloatColumns(zbMappingList));
        result.setRowDatas(rowDataList);
        return result;
    }

    private Object parseFloatRowVal(String valStr, ColumnTypeEnum columnTypeEnum) {
        if (valStr == null) {
            return valStr;
        }
        switch (columnTypeEnum) {
            case NUMBER: {
                return Double.valueOf(valStr);
            }
            case INT: {
                return Integer.valueOf(valStr);
            }
        }
        return String.valueOf(valStr);
    }

    private Map<String, Object> parseFieldDefineResult(String fieldDefineId, FloatColResultVO floatColDataVo, int rowIdx) {
        if (floatColDataVo.getColInfoMap().get(fieldDefineId) == null || CollectionUtils.isEmpty(floatColDataVo.getColDataList())) {
            return CollectionUtils.newHashMap();
        }
        FloatFieldDefineValPojo fieldDefineValPojo = floatColDataVo.getColInfoMap().get(fieldDefineId);
        HashMap<String, Object> fetchResult = new HashMap<String, Object>(fieldDefineValPojo.getFieldSettingMap().size());
        Integer fieldDefineIdx = fieldDefineValPojo.getFieldDefineIdx();
        for (Map.Entry<String, Integer> fetchSettingEntry : fieldDefineValPojo.getFieldSettingMap().entrySet()) {
            if (floatColDataVo.getColDataList() == null) {
                this.logger.error("RequestTaskId:\u3010{}\u3011,floatColDataVo.getColDataList()\u3010{}\u3011\u4e3a\u7a7a", (Object)this.fetchRequestDTO.getRequestTaskId(), (Object)floatColDataVo.toString());
                continue;
            }
            fetchResult.put(fetchSettingEntry.getKey(), floatColDataVo.getColDataList().get(rowIdx)[fieldDefineIdx][fetchSettingEntry.getValue()]);
        }
        return fetchResult;
    }

    private Map<String, Integer> buildFloatColumns(List<FloatZbMappingVO> zbMappingList) {
        HashMap<String, Integer> floatColumns = new HashMap<String, Integer>(zbMappingList.size());
        Integer floatColumnIdx = 0;
        for (FloatZbMappingVO zbMapping : zbMappingList) {
            Integer n = floatColumnIdx;
            Integer n2 = floatColumnIdx = Integer.valueOf(floatColumnIdx + 1);
            floatColumns.put(zbMapping.getFieldDefineId(), n);
        }
        return floatColumns;
    }
}


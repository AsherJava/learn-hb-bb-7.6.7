/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.intf.FetchSettingCacheKey
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelMaintainClient
 */
package com.jiuqi.bde.bizmodel.execute.model.basedata.fetch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.AbstractGenericModelExecute;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.model.basedata.BaseDataDataCondi;
import com.jiuqi.bde.bizmodel.execute.model.basedata.BaseDataLoader;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.FetchSettingCacheKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelMaintainClient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataModelExecute
extends AbstractGenericModelExecute {
    @Autowired
    private BaseDataLoader loader;
    @Autowired
    private DataModelMaintainClient dataModelMaintainClient;

    public String getComputationModelCode() {
        return "BASEDATA";
    }

    @Override
    protected ModelExecuteContext rewriteContext(FetchTaskContext fetchTaskContext, FetchSettingCacheKey fetchSettingCacheKey) {
        ModelExecuteContext modelExecuteContext = ModelExecuteUtil.convert2ExecuteContext(fetchTaskContext);
        modelExecuteContext.setOptimizeRuleGroup(fetchSettingCacheKey.getOptimizeRuleGroup());
        return modelExecuteContext;
    }

    @Override
    protected ExecuteSettingVO rewriteFetchSetting(ExecuteSettingVO orignSetting, Map<String, String> rowData) {
        ExecuteSettingVO fetchSetting = (ExecuteSettingVO)BeanConvertUtil.convert((Object)orignSetting, ExecuteSettingVO.class, (String[])new String[0]);
        fetchSetting.setSubjectCode(VariableParseUtil.parse((String)orignSetting.getSubjectCode(), rowData));
        return fetchSetting;
    }

    @Override
    protected List<FetchResult> doExecute(ModelExecuteContext executeContext, List<ExecuteSettingVO> executeSettingList, StringBuilder logContent) {
        if (executeSettingList.isEmpty() || Objects.isNull((Object)executeContext)) {
            ArrayList<FetchResult> resultList = new ArrayList<FetchResult>(executeSettingList.size());
            for (ExecuteSettingVO executeSettingItem : executeSettingList) {
                FetchResult result = new FetchResult(executeSettingItem);
                result.setZbValue("");
                result.setZbValueType(ColumnTypeEnum.STRING);
                resultList.add(result);
            }
            logContent.append("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u30100\u3011\u6761\u6570\u636e%n");
            return resultList;
        }
        String optimizeRuleGroup = executeContext.getOptimizeRuleGroup();
        Map optimzeRuleItem = (Map)JsonUtils.readValue((String)optimizeRuleGroup, (TypeReference)new TypeReference<Map<String, String>>(){});
        String baseCodeDefine = (String)optimzeRuleItem.get("baseCodeDefine");
        DataModelDTO modelCondi = new DataModelDTO();
        modelCondi.setName(baseCodeDefine);
        Map<String, DataModelType.ColumnType> columnTypeMap = this.dataModelMaintainClient.get(modelCondi).getColumns().stream().collect(Collectors.toMap(DataModelColumn::getColumnName, DataModelColumn::getColumnType, (k1, k2) -> k2));
        List<String> baseDataCodeList = executeSettingList.stream().map(item -> item.getSubjectCode().toUpperCase()).collect(Collectors.toList());
        BaseDataDataCondi condi = new BaseDataDataCondi(executeContext, executeSettingList, baseCodeDefine, baseDataCodeList);
        logContent.append(String.format("\u5f00\u59cb\u52a0\u8f7d\u6570\u636e\uff0c\u52a0\u8f7d\u6570\u636e\u914d\u7f6e\uff1a\u3010%1$s\u3011%n", condi));
        Map<String, BaseDataDO> baseDataDoMap = this.loader.loadData(condi);
        ColumnTypeEnum columnType = null;
        Object value = null;
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>(executeSettingList.size());
        for (ExecuteSettingVO executeSettingItem : executeSettingList) {
            FetchResult result = new FetchResult(executeSettingItem);
            columnType = this.getColumnType(columnTypeMap, baseCodeDefine);
            BaseDataDO baseDataDO = baseDataDoMap.get(executeSettingItem.getSubjectCode().toUpperCase());
            value = baseDataDO == null ? null : baseDataDO.getValueOf(executeSettingItem.getFetchType());
            Pair<ColumnTypeEnum, String> val = this.getVal(executeSettingItem.getFieldDefineType(), columnType, value);
            result.setZbValueType((ColumnTypeEnum)val.getFirst());
            if (ColumnTypeEnum.NUMBER.equals(val.getFirst())) {
                result.setZbValue(new BigDecimal((String)val.getSecond()));
            } else {
                result.setZbValue((String)val.getSecond());
            }
            resultList.add(result);
        }
        logContent.append(String.format("\u7ed3\u675f\u52a0\u8f7d\u6570\u636e\uff0c\u5171\u52a0\u8f7d\u3010%1$s\u3011\u6761\u6570\u636e%n", resultList.size()));
        return resultList;
    }

    private Pair<ColumnTypeEnum, String> getVal(Integer fieldDefineType, ColumnTypeEnum columnType, Object value) {
        if (value == null) {
            if (fieldDefineType == null) {
                return new Pair((Object)ColumnTypeEnum.NUMBER, (Object)BigDecimal.ZERO.toString());
            }
            if (fieldDefineType == 5) {
                return new Pair((Object)ColumnTypeEnum.STRING, null);
            }
            if (fieldDefineType == 6) {
                return new Pair((Object)ColumnTypeEnum.STRING, (Object)"");
            }
            return new Pair((Object)ColumnTypeEnum.NUMBER, (Object)BigDecimal.ZERO.toString());
        }
        if (columnType == null && fieldDefineType == null) {
            if (value instanceof BigDecimal || value instanceof Double || value instanceof Integer || value instanceof Float) {
                return new Pair((Object)ColumnTypeEnum.NUMBER, (Object)value.toString());
            }
            if (value instanceof Date) {
                return new Pair((Object)ColumnTypeEnum.STRING, (Object)DateUtils.format((Date)((Date)value)));
            }
            return new Pair((Object)ColumnTypeEnum.STRING, (Object)value.toString());
        }
        if (columnType != null) {
            if (columnType == ColumnTypeEnum.NUMBER || columnType == ColumnTypeEnum.INT) {
                try {
                    return new Pair((Object)ColumnTypeEnum.NUMBER, (Object)new BigDecimal(value.toString()).toString());
                }
                catch (Exception e) {
                    if (value instanceof Date) {
                        return new Pair((Object)ColumnTypeEnum.STRING, (Object)DateUtils.format((Date)((Date)value)));
                    }
                    return new Pair((Object)ColumnTypeEnum.STRING, (Object)value.toString());
                }
            }
            if (value instanceof Date) {
                return new Pair((Object)ColumnTypeEnum.STRING, (Object)DateUtils.format((Date)((Date)value)));
            }
            return new Pair((Object)ColumnTypeEnum.STRING, (Object)value.toString());
        }
        if (BdeCommonUtil.fieldDefineTypeIsNum((Integer)fieldDefineType)) {
            try {
                return new Pair((Object)ColumnTypeEnum.NUMBER, (Object)new BigDecimal(value.toString()).toString());
            }
            catch (Exception e) {
                if (value instanceof Date) {
                    return new Pair((Object)ColumnTypeEnum.STRING, (Object)DateUtils.format((Date)((Date)value)));
                }
                return new Pair((Object)ColumnTypeEnum.STRING, (Object)value.toString());
            }
        }
        if (value instanceof Date) {
            return new Pair((Object)ColumnTypeEnum.STRING, (Object)DateUtils.format((Date)((Date)value)));
        }
        return new Pair((Object)ColumnTypeEnum.STRING, (Object)value.toString());
    }

    private ColumnTypeEnum getColumnType(Map<String, DataModelType.ColumnType> columnTypeMap, String columnName) {
        DataModelType.ColumnType columnType = columnTypeMap.get(columnName);
        if (columnType == null) {
            return null;
        }
        if (columnType == DataModelType.ColumnType.NUMERIC) {
            return ColumnTypeEnum.NUMBER;
        }
        if (columnType == DataModelType.ColumnType.INTEGER) {
            return ColumnTypeEnum.INT;
        }
        return ColumnTypeEnum.STRING;
    }
}


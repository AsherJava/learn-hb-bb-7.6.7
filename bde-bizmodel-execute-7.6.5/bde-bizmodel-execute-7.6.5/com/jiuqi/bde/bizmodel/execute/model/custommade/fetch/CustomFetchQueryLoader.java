/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.dto.SimpleComposeDateDTO
 *  com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchCondi;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchExecuteSettingVO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchSqlBuilder;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomQueryResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.FinCustomFetchLoader;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.SimpleComposeDateDTO;
import com.jiuqi.bde.common.dto.SimpleCustomComposePluginDataVO;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class CustomFetchQueryLoader
extends FinCustomFetchLoader {
    @Override
    public List<FetchResult> loadData(CustomFetchCondi condi) {
        SelectField selectField;
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        CustomFetchExecuteSettingVO executeSettingVO = condi.getFetchSettingList().get(0);
        CustomBizModelDTO bizModelDTO = (CustomBizModelDTO)this.bizModelService.get(executeSettingVO.getFetchSourceCode());
        Map customFetchConditionMap = (Map)JsonUtils.readValue((String)executeSettingVO.getFormula(), (TypeReference)new TypeReference<Map<String, String>>(){});
        CustomFetchSqlBuilder customFetchSqlBuilder = new CustomFetchSqlBuilder();
        for (Map.Entry entity : customFetchConditionMap.entrySet()) {
            if (StringUtils.isEmpty((String)((String)entity.getValue()))) continue;
            CustomCondition customCondition = executeSettingVO.getCustomConditionMap().get(entity.getKey());
            if (customCondition == null) {
                throw new BusinessRuntimeException(String.format("\u6839\u636e\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u67e5\u8be2\u6761\u4ef6", entity.getKey()));
            }
            MatchingRuleEnum matchingRuleEnum = MatchingRuleEnum.getEnumByCode((String)customCondition.getRuleCode());
            customFetchSqlBuilder.addConditionSql(matchingRuleEnum.buildSqlByRuleAndValue((String)entity.getKey(), (String)entity.getValue()));
        }
        if (!StringUtils.isEmpty((String)bizModelDTO.getFixedCondition())) {
            customFetchSqlBuilder.addConditionSql(" AND " + bizModelDTO.getFixedCondition());
        }
        if ((selectField = (SelectField)bizModelDTO.getSelectFieldMap().get(executeSettingVO.getFetchType())) == null) {
            throw new BusinessRuntimeException("\u672a\u68c0\u6d4b\u5230\u6240\u9009\u7684\u53d6\u6570\u5b57\u6bb5");
        }
        customFetchSqlBuilder.setTableName(bizModelDTO.getFetchTable());
        customFetchSqlBuilder.addSelectField(AggregateFuncEnum.buildSelectFieldSql((SelectField)selectField));
        String lastSql = ContextVariableParseUtil.parse((String)customFetchSqlBuilder.build(), (FetchTaskContext)condi.getFetchTaskContext());
        BdeLogUtil.recordLog((String)condi.getFetchTaskContext().getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u81ea\u5b9a\u4e49\u6a21\u578b\u53d6\u6570", (Object)new Object[]{condi}, (String)lastSql);
        List<Object> queryList = this.getQueryList(condi, lastSql, executeSettingVO);
        FetchResult result = new FetchResult((ExecuteSettingVO)executeSettingVO);
        if (executeSettingVO.getFieldDefineType() == null) {
            if (queryList.get(0) == null) {
                result.setZbValue(BigDecimal.ZERO.toString());
                result.setZbValueType(ColumnTypeEnum.NUMBER);
            } else if (queryList.get(0) instanceof BigDecimal) {
                result.setZbValue(new BigDecimal(queryList.get(0).toString()));
                result.setZbValueType(ColumnTypeEnum.NUMBER);
            } else {
                result.setZbValue(queryList.get(0).toString());
                result.setZbValueType(ColumnTypeEnum.STRING);
            }
        } else if (BdeCommonUtil.fieldDefineTypeIsNum((Integer)executeSettingVO.getFieldDefineType())) {
            if (queryList.get(0) != null && StringUtils.isEmpty((String)String.valueOf(queryList.get(0)))) {
                throw new BusinessRuntimeException("\u6570\u503c\u7c7b\u578b\u6307\u6807\u53d6\u6570\u7ed3\u679c\u4e0d\u80fd\u4e3a\u7a7a\u5b57\u7b26\u4e32\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u8c03\u6574\u53d6\u6570\u8bbe\u7f6e");
            }
            result.setZbValue(new BigDecimal(String.valueOf(queryList.get(0))));
            result.setZbValueType(ColumnTypeEnum.NUMBER);
        } else {
            result.setZbValue(String.valueOf(queryList.get(0)));
            result.setZbValueType(ColumnTypeEnum.STRING);
        }
        resultList.add(result);
        return resultList;
    }

    protected List<Object> getQueryList(CustomFetchCondi condi, String lastSql, ExecuteSettingVO executeSettingVO) {
        return (List)this.dataSourceService.pageQuery(condi.getFetchTaskContext().getOrgMapping().getDataSourceCode(), lastSql, 1, 1, new Object[0], (ResultSetExtractor)new CustomQueryResultSetExtractor(executeSettingVO));
    }

    public FetchFloatRowResult simpleFloatQuery(FetchTaskContext fetchTaskContext, BalanceCondition condi, SimpleComposeDateDTO simpleComposeDateDTO) {
        CustomBizModelDTO bizModelDTO = (CustomBizModelDTO)this.bizModelService.get(simpleComposeDateDTO.getSimpleCustomComposePluginDataVO().getFetchSourceCode());
        List usedFields = simpleComposeDateDTO.getUsedFields();
        SimpleCustomComposePluginDataVO simpleCustomComposePluginDataVO = simpleComposeDateDTO.getSimpleCustomComposePluginDataVO();
        CustomFetchSqlBuilder customFetchSqlBuilder = new CustomFetchSqlBuilder();
        for (Object dimension : simpleCustomComposePluginDataVO.getDimensionMapping()) {
            if (StringUtils.isEmpty((String)dimension.getDimValue())) continue;
            CustomCondition customCondition = (CustomCondition)bizModelDTO.getCustomConditionMap().get(dimension.getDimCode());
            if (customCondition == null) {
                throw new BusinessRuntimeException(String.format("\u81ea\u5b9a\u4e49\u6a21\u578b\u3010%1$s\u3011\u4e2d\u6ca1\u6709\u83b7\u53d6\u5230\u53c2\u6570\u6807\u8bc6\u4e3a\u3010%2$s\u3011\u7684\u81ea\u5b9a\u4e49\u6761\u4ef6\uff0c\u8bf7\u68c0\u67e5\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u914d\u7f6e", bizModelDTO.getName(), dimension.getDimCode()));
            }
            MatchingRuleEnum matchingRuleEnum = MatchingRuleEnum.getEnumByCode((String)customCondition.getRuleCode());
            customFetchSqlBuilder.addConditionSql(matchingRuleEnum.buildSqlByRuleAndValue(dimension.getDimCode(), dimension.getDimValue()));
        }
        if (!StringUtils.isEmpty((String)bizModelDTO.getFixedCondition())) {
            customFetchSqlBuilder.addConditionSql(" AND " + bizModelDTO.getFixedCondition());
        }
        customFetchSqlBuilder.setTableName(bizModelDTO.getFetchTable());
        HashSet<String> aggregateFuncSet = new HashSet<String>();
        for (String usedField : usedFields) {
            SelectField selectField = (SelectField)bizModelDTO.getSelectFieldMap().get(usedField);
            if (selectField == null) {
                throw new BusinessRuntimeException(String.format("\u81ea\u5b9a\u4e49\u6a21\u578b\u3010%1$s\u3011\u4e2d\u6ca1\u6709\u83b7\u53d6\u5230\u5b57\u6bb5\u6807\u8bc6\u4e3a\u3010%2$s\u3011\u7684\u53d6\u6570\u5b57\u6bb5\uff0c\u8bf7\u68c0\u67e5\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u914d\u7f6e", bizModelDTO.getName(), usedField));
            }
            aggregateFuncSet.add(selectField.getAggregateFuncCode());
            customFetchSqlBuilder.addSelectField(AggregateFuncEnum.buildSelectFieldSql((SelectField)selectField));
            if (!AggregateFuncEnum.ORIGINAL.getFuncCode().equals(selectField.getAggregateFuncCode())) continue;
            customFetchSqlBuilder.addGroupFieldList(selectField.getFieldCode());
        }
        boolean needGroup = aggregateFuncSet.size() > 1 && aggregateFuncSet.contains(AggregateFuncEnum.ORIGINAL.getFuncCode());
        customFetchSqlBuilder.setNeedGroup(needGroup);
        String lastSql = ContextVariableParseUtil.parse((String)customFetchSqlBuilder.build(), (FetchTaskContext)fetchTaskContext);
        BdeLogUtil.recordLog((String)fetchTaskContext.getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u81ea\u5b9a\u4e49\u6a21\u578b\u53d6\u6570", (Object)new Object[]{condi}, (String)lastSql);
        FetchFloatRowResult fetchFloatRowResult = new FetchFloatRowResult();
        HashMap<String, Integer> fieldTypeNoMap = new HashMap<String, Integer>();
        List<String[]> rowDataList = this.getResult(lastSql, usedFields, condi.getOrgMapping().getDataSourceCode(), fieldTypeNoMap);
        HashMap fieldTypeMap = new HashMap();
        block6: for (Map.Entry entry : fieldTypeNoMap.entrySet()) {
            switch ((Integer)entry.getValue()) {
                case 4: {
                    fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.INT);
                    continue block6;
                }
                case 2: 
                case 3: 
                case 6: 
                case 8: {
                    fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.NUMBER);
                    continue block6;
                }
            }
            fieldTypeMap.put(entry.getKey(), ColumnTypeEnum.STRING);
        }
        fetchFloatRowResult.setFloatColumnsType(fieldTypeMap);
        fetchFloatRowResult.setRowDatas(rowDataList);
        LinkedHashMap floatColumns = new LinkedHashMap();
        for (int i = 0; i < usedFields.size(); ++i) {
            floatColumns.put(usedFields.get(i), i);
        }
        fetchFloatRowResult.setFloatColumns(floatColumns);
        return fetchFloatRowResult;
    }

    protected List<String[]> getResult(String definedSql, List<String> usedFields, String dataSourceCode, Map<String, Integer> fieldTypeNoMap) {
        List rowDataList = this.dataSourceService.query(dataSourceCode, definedSql, null, (rs, row) -> {
            String[] rowData = new String[usedFields.size()];
            for (int i = 0; i < usedFields.size(); ++i) {
                rowData[i] = rs.getString(i + 1);
                fieldTypeNoMap.put((String)usedFields.get(i), rs.getMetaData().getColumnType(i + 1));
            }
            return rowData;
        });
        return rowDataList;
    }
}


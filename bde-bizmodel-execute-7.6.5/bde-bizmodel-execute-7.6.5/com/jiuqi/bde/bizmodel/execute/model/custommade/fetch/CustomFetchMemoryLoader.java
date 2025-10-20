/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum
 *  com.jiuqi.bde.bizmodel.client.vo.CustomCondition
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.intf.ConditionMatchRule
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.enums.MatchingRuleEnum;
import com.jiuqi.bde.bizmodel.client.vo.CustomCondition;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.intf.ModelExecuteContext;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchCondi;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchExecuteSettingVO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomFetchSqlBuilder;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.CustomMemoryResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.FinCustomFetchLoader;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.intf.ConditionMatchRule;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.ResultSetExtractor;

@Deprecated
class CustomFetchMemoryLoader
extends FinCustomFetchLoader {
    CustomFetchMemoryLoader() {
    }

    @Override
    public List<FetchResult> loadData(CustomFetchCondi condi) {
        CustomFetchResult customFetchResult = new CustomFetchResult();
        HashMap<String, Integer> columnsMap = new HashMap<String, Integer>();
        HashMap<String, ColumnTypeEnum> fieldType = new HashMap<String, ColumnTypeEnum>();
        CustomFetchSqlBuilder customFetchSqlBuilder = CustomFetchSqlBuilder.initSqlBuilderByRuleGroup(CollectionUtils.isEmpty((Collection)condi.getFetchTaskContext().getFetchSettingList()) ? ((ModelExecuteContext)condi.getFetchTaskContext()).getOptimizeRuleGroup() : ((FetchSettingVO)condi.getFetchTaskContext().getFetchSettingList().get(0)).getOptimizeRuleGroup(), condi.getFetchSettingList().get(0).getBizModel());
        HashMap<String, ConditionMatchRule> matchRuleConditionMap = new HashMap<String, ConditionMatchRule>();
        Integer queryNum = 1;
        for (CustomFetchExecuteSettingVO customFetchExecuteSettingVO : condi.getFetchSettingList()) {
            Map<String, CustomCondition> customFetchConditionMap = customFetchExecuteSettingVO.getCustomConditionMap();
            SelectField selectField = customFetchExecuteSettingVO.getSelectField();
            if (selectField == null) {
                throw new BusinessRuntimeException("\u672a\u83b7\u53d6\u5230\u81ea\u5b9a\u4e49\u6a21\u578b\u7684\u53d6\u6570\u5b57\u6bb5");
            }
            customFetchSqlBuilder.addSelectField(AggregateFuncEnum.buildSelectFieldSql((SelectField)selectField));
            if (columnsMap.get(selectField.getFieldCode()) == null) {
                Integer n = queryNum;
                Integer n2 = queryNum = Integer.valueOf(queryNum + 1);
                columnsMap.put(selectField.getFieldCode(), n);
            }
            this.buildConditionMatchRuleMap(matchRuleConditionMap, customFetchExecuteSettingVO.getCustomConditionMap());
            for (Map.Entry entry : customFetchConditionMap.entrySet()) {
                customFetchSqlBuilder.addSelectField(String.format("%1$s.%2$s", "MAINTABLE", entry.getKey()));
                if (columnsMap.get(entry.getKey()) == null) {
                    Integer n = queryNum;
                    Integer n3 = queryNum = Integer.valueOf(queryNum + 1);
                    columnsMap.put((String)entry.getKey(), n);
                }
                customFetchSqlBuilder.addGroupFieldList(String.format("%1$s.%2$s", "MAINTABLE", entry.getKey()));
            }
        }
        this.getCustomCondition(matchRuleConditionMap);
        for (Map.Entry entry : matchRuleConditionMap.entrySet()) {
            ConditionMatchRule conditionMatchRule = (ConditionMatchRule)entry.getValue();
            if (conditionMatchRule.getMatchRule() != MatchRuleEnum.RANGE) {
                if (conditionMatchRule.getSubjectCodes().size() == 1) {
                    customFetchSqlBuilder.addConditionSql(String.format(" AND %1$s LIKE '%2$s' ", entry.getKey(), conditionMatchRule.getSubjectCodes().get(0)));
                    continue;
                }
                ArrayList conditionList = new ArrayList();
                conditionMatchRule.getSubjectCodes().forEach(item -> conditionList.add(String.format("%1$s LIKE '%2$s'", entry.getKey(), item)));
                customFetchSqlBuilder.addConditionSql(SqlUtil.concatCondi(conditionList, (boolean)false));
                continue;
            }
            customFetchSqlBuilder.addConditionSql(String.format(" AND MAINTABLE.%1$s >= '%2$s' AND MAINTABLE.%1$s <= '%3$s' ", entry.getKey(), conditionMatchRule.getSubjectCodes().get(0), conditionMatchRule.getSubjectCodes().get(1)));
        }
        String lastSql = ContextVariableParseUtil.parse((String)customFetchSqlBuilder.build(), (FetchTaskContext)condi.getFetchTaskContext());
        BdeLogUtil.recordLog((String)condi.getFetchTaskContext().getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u81ea\u5b9a\u4e49\u6a21\u578b\u53d6\u6570", (Object)new Object[]{condi}, (String)lastSql);
        List<Object[]> list = this.getRowDatas(condi, lastSql, columnsMap, fieldType);
        customFetchResult.setDatas(list);
        customFetchResult.setColumnsMap(columnsMap);
        customFetchResult.setFieldType(fieldType);
        return this.computedResult(condi.getFetchSettingList(), customFetchResult);
    }

    protected List<Object[]> getRowDatas(CustomFetchCondi condi, String lastSql, Map<String, Integer> columnsMap, Map<String, ColumnTypeEnum> fieldType) {
        return (List)this.dataSourceService.query(condi.getFetchTaskContext().getOrgMapping().getDataSourceCode(), lastSql, new Object[0], (ResultSetExtractor)new CustomMemoryResultSetExtractor(columnsMap, fieldType));
    }

    private List<FetchResult> computedResult(List<CustomFetchExecuteSettingVO> customFetchExecuteSettingVOS, CustomFetchResult customFetchResult) {
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>();
        for (CustomFetchExecuteSettingVO executeSettingVO : customFetchExecuteSettingVOS) {
            FetchResult fetchResultEO = new FetchResult((ExecuteSettingVO)executeSettingVO);
            Integer index = customFetchResult.getColumnsMap().get(executeSettingVO.getSelectField().getFieldCode());
            ColumnTypeEnum columnTypeEnum = customFetchResult.getFieldType().get(executeSettingVO.getSelectField().getFieldCode());
            Map<String, CustomCondition> customConditionMap = executeSettingVO.getCustomConditionMap();
            String strValue = "";
            BigDecimal bigDecimal = BigDecimal.ZERO;
            boolean isNum = this.fieldIsNum(executeSettingVO, columnTypeEnum);
            for (Object[] rowData : customFetchResult.getDatas()) {
                Object data = rowData[index - 1];
                if (!this.validatedData(rowData, customConditionMap, customFetchResult.getColumnsMap())) continue;
                if (!isNum) {
                    strValue = data == null ? "" : data.toString();
                    break;
                }
                if (AggregateFuncEnum.ORIGINAL.getFuncCode().equals(executeSettingVO.getSelectField().getAggregateFuncCode())) {
                    bigDecimal = data == null ? bigDecimal : new BigDecimal(data.toString());
                    break;
                }
                if (data == null) continue;
                bigDecimal = bigDecimal.add(new BigDecimal(data.toString()));
            }
            if (isNum) {
                fetchResultEO.setZbValue(bigDecimal);
                fetchResultEO.setZbValueType(ColumnTypeEnum.NUMBER);
            } else {
                fetchResultEO.setZbValue(strValue);
                fetchResultEO.setZbValueType(ColumnTypeEnum.STRING);
            }
            resultList.add(fetchResultEO);
        }
        return resultList;
    }

    private void getCustomCondition(Map<String, ConditionMatchRule> matchRuleMap) {
        for (Map.Entry<String, ConditionMatchRule> entry : matchRuleMap.entrySet()) {
            ConditionMatchRule conditionMatchRule = entry.getValue();
            if (conditionMatchRule.getMatchRule() == null) {
                conditionMatchRule.setMatchRule(MatchRuleEnum.LIKE);
            }
            conditionMatchRule.getSubjectCodes().sort(String::compareTo);
            ArrayList<String> optimizationList = new ArrayList<String>();
            List<Object> resultList = new ArrayList();
            String markSubject = (String)conditionMatchRule.getSubjectCodes().get(0);
            optimizationList.add(markSubject);
            for (String result : conditionMatchRule.getSubjectCodes()) {
                if (result.startsWith(markSubject)) continue;
                markSubject = result;
                optimizationList.add(result);
            }
            if (conditionMatchRule.getMatchRule() != MatchRuleEnum.RANGE) {
                resultList = optimizationList.stream().map(item -> item + "%").collect(Collectors.toList());
            } else {
                resultList.add(optimizationList.get(0));
                resultList.add((String)optimizationList.get(optimizationList.size() - 1) + "ZZ");
            }
            conditionMatchRule.setSubjectCodes(resultList);
        }
    }

    private boolean validatedData(Object[] rowData, Map<String, CustomCondition> customConditionMap, Map<String, Integer> columnsMap) {
        block3: for (Map.Entry<String, CustomCondition> entry : customConditionMap.entrySet()) {
            CustomCondition customValue = entry.getValue();
            Object rowDatum = rowData[columnsMap.get(entry.getKey()) - 1];
            String result = rowDatum != null ? rowDatum.toString() : "";
            ConditionMatchRule conditionMatchRule = this.splitParam(customValue);
            switch (conditionMatchRule.getMatchRule()) {
                case RANGE: {
                    if (result.compareTo((String)conditionMatchRule.getSubjectCodes().get(0)) >= 0 && result.compareTo((String)conditionMatchRule.getSubjectCodes().get(1) + "ZZZ") <= 0) continue block3;
                    return false;
                }
            }
            boolean matched = false;
            for (String value : conditionMatchRule.getSubjectCodes()) {
                if (MatchingRuleEnum.LIKE.getRuleCode().equals(customValue.getRuleCode())) {
                    if (!result.startsWith(value)) continue;
                    matched = true;
                    break;
                }
                if (!value.equals(result)) continue;
                matched = true;
                break;
            }
            if (matched) continue;
            return false;
        }
        return true;
    }

    private ConditionMatchRule splitParam(CustomCondition customCondition) {
        ConditionMatchRule conditionMatchRule = new ConditionMatchRule(MatchRuleEnum.EQ, new ArrayList());
        if (customCondition.getValue().contains(",")) {
            conditionMatchRule.getSubjectCodes().addAll(Arrays.asList(customCondition.getValue().split(",")));
        } else if (customCondition.getValue().contains(":")) {
            conditionMatchRule.setMatchRule(MatchRuleEnum.RANGE);
            conditionMatchRule.getSubjectCodes().addAll(Arrays.asList(customCondition.getValue().split(":")));
        } else {
            conditionMatchRule.getSubjectCodes().add(customCondition.getValue());
        }
        return conditionMatchRule;
    }

    private boolean fieldIsNum(ExecuteSettingVO executeSettingVO, ColumnTypeEnum columnTypeEnum) {
        boolean isNum = false;
        if (executeSettingVO.getFieldDefineType() != null) {
            isNum = BdeCommonUtil.fieldDefineTypeIsNum((Integer)executeSettingVO.getFieldDefineType());
        } else if (!columnTypeEnum.equals((Object)ColumnTypeEnum.STRING)) {
            isNum = true;
        }
        return isNum;
    }

    private void buildConditionMatchRuleMap(Map<String, ConditionMatchRule> matchRuleMap, Map<String, CustomCondition> customFetchConditionMap) {
        for (Map.Entry<String, CustomCondition> entry : customFetchConditionMap.entrySet()) {
            ConditionMatchRule conditionMatchRule = matchRuleMap.get(entry.getKey());
            if (conditionMatchRule == null) {
                conditionMatchRule = new ConditionMatchRule(null, new ArrayList());
                matchRuleMap.put(entry.getKey(), conditionMatchRule);
            }
            if (entry.getValue().getValue().contains(",")) {
                conditionMatchRule.getSubjectCodes().addAll(Arrays.asList(entry.getValue().getValue().split(",")));
                continue;
            }
            if (entry.getValue().getValue().contains(":")) {
                conditionMatchRule.setMatchRule(MatchRuleEnum.RANGE);
                conditionMatchRule.getSubjectCodes().addAll(Arrays.asList(entry.getValue().getValue().split(":")));
                continue;
            }
            conditionMatchRule.getSubjectCodes().add(entry.getValue().getValue());
        }
    }
}


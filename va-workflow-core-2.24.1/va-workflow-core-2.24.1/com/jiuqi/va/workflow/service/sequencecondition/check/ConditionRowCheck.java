/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.domain.workflow.ValueType
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.sequencecondition.ConditionRow
 */
package com.jiuqi.va.workflow.service.sequencecondition.check;

import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.domain.workflow.ValueType;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.sequencecondition.ConditionRow;
import com.jiuqi.va.workflow.domain.OperatorEnum;
import com.jiuqi.va.workflow.service.sequencecondition.check.Condition;
import com.jiuqi.va.workflow.service.sequencecondition.operator.ComparisonOperator;
import com.jiuqi.va.workflow.utils.SequenceConditionUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class ConditionRowCheck
implements Condition {
    private static final Logger logger = LoggerFactory.getLogger(ConditionRowCheck.class);
    private final ConditionRow conditionRow;

    public ConditionRowCheck(ConditionRow conditionRow) {
        this.conditionRow = conditionRow;
    }

    @Override
    public boolean check(Map<String, Object> data) {
        List<Object> paramValue;
        if (this.conditionRow == null) {
            return true;
        }
        ProcessParam paramInfo = this.conditionRow.getParamInfo();
        if (paramInfo == null) {
            return true;
        }
        String paramName = paramInfo.getParamName();
        if (!StringUtils.hasText(paramName)) {
            return true;
        }
        paramName = paramName.substring(1, paramName.length() - 1);
        String operator = this.conditionRow.getCompareSymbol();
        if (!StringUtils.hasText(operator)) {
            return true;
        }
        Object bizValue = data.get(paramName);
        Object compareValueObj = this.conditionRow.getCompareValue();
        if (ObjectUtils.isEmpty(bizValue)) {
            return OperatorEnum.ISNULL.getName().equals(operator);
        }
        if (OperatorEnum.ISNULL.getName().equals(operator)) {
            return false;
        }
        if (OperatorEnum.IS_NOTNULL.getName().equals(operator)) {
            return true;
        }
        ComparisonOperator comparisonOperator = SequenceConditionUtils.getComparisonOperator(operator);
        if (comparisonOperator == null) {
            logger.error("\u6d41\u8f6c\u7ebf\u6761\u4ef6\u7ec4\u4e0d\u652f\u6301\u6bd4\u8f83\u7b26\uff1a{}", (Object)operator);
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notsupportseqcompareoperator") + operator);
        }
        Object bizValueObj = bizValue;
        ValueType paramType = paramInfo.getParamType();
        if (ValueType.STRING.equals((Object)paramType)) {
            if (paramInfo.getMappingType() != null) {
                if (compareValueObj instanceof List) {
                    List compareValueList = (List)compareValueObj;
                    paramValue = compareValueList.stream().map(value -> (String)value.get("code")).collect(Collectors.toList());
                } else {
                    paramValue = new ArrayList();
                }
            } else {
                paramValue = String.valueOf(compareValueObj);
            }
        } else {
            bizValueObj = SequenceConditionUtils.handleBizParamType(paramType, bizValueObj);
            paramValue = SequenceConditionUtils.handleParamType(paramType, compareValueObj);
        }
        return comparisonOperator.compare(bizValueObj, paramValue);
    }
}


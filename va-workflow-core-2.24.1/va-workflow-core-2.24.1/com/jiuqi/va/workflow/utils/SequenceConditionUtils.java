/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.workflow.VaContext
 *  com.jiuqi.va.domain.workflow.VaWorkflowContext
 *  com.jiuqi.va.domain.workflow.ValueType
 *  com.jiuqi.va.domain.workflow.sequencecondition.ConditionGroupInfo
 *  com.jiuqi.va.domain.workflow.sequencecondition.ConditionRow
 *  com.jiuqi.va.domain.workflow.sequencecondition.SequenceConditionView
 */
package com.jiuqi.va.workflow.utils;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.workflow.VaContext;
import com.jiuqi.va.domain.workflow.VaWorkflowContext;
import com.jiuqi.va.domain.workflow.ValueType;
import com.jiuqi.va.domain.workflow.sequencecondition.ConditionGroupInfo;
import com.jiuqi.va.domain.workflow.sequencecondition.ConditionRow;
import com.jiuqi.va.domain.workflow.sequencecondition.SequenceConditionView;
import com.jiuqi.va.workflow.service.sequencecondition.check.ConditionCombinationCheck;
import com.jiuqi.va.workflow.service.sequencecondition.check.ConditionGroupCheck;
import com.jiuqi.va.workflow.service.sequencecondition.check.ConditionRowCheck;
import com.jiuqi.va.workflow.service.sequencecondition.operator.ComparisonOperator;
import com.jiuqi.va.workflow.service.sequencecondition.operator.ComparisonOperatorCollection;
import com.jiuqi.va.workflow.service.sequencecondition.relation.Relation;
import com.jiuqi.va.workflow.service.sequencecondition.relation.RelationCollection;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class SequenceConditionUtils {
    private static final Logger log = LoggerFactory.getLogger(SequenceConditionUtils.class);
    private static volatile boolean comparisonInitialed = false;
    private static Map<String, ComparisonOperator> comparisonOperatorMap;
    private static volatile boolean relationInitialed;
    private static Map<String, Relation> relationMap;

    private SequenceConditionUtils() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void initOperator() {
        if (comparisonInitialed) return;
        Class<SequenceConditionUtils> clazz = SequenceConditionUtils.class;
        synchronized (SequenceConditionUtils.class) {
            if (comparisonInitialed) return;
            comparisonOperatorMap = new HashMap<String, ComparisonOperator>();
            comparisonOperatorMap.put(ComparisonOperatorCollection.contains().getName(), ComparisonOperatorCollection.contains());
            comparisonOperatorMap.put(ComparisonOperatorCollection.notContains().getName(), ComparisonOperatorCollection.notContains());
            comparisonOperatorMap.put(ComparisonOperatorCollection.equals().getName(), ComparisonOperatorCollection.equals());
            comparisonOperatorMap.put(ComparisonOperatorCollection.notEquals().getName(), ComparisonOperatorCollection.notEquals());
            comparisonOperatorMap.put(ComparisonOperatorCollection.greaterThan().getName(), ComparisonOperatorCollection.greaterThan());
            comparisonOperatorMap.put(ComparisonOperatorCollection.greaterThanOrEquals().getName(), ComparisonOperatorCollection.greaterThanOrEquals());
            comparisonOperatorMap.put(ComparisonOperatorCollection.lessThan().getName(), ComparisonOperatorCollection.lessThan());
            comparisonOperatorMap.put(ComparisonOperatorCollection.lessThanOrEquals().getName(), ComparisonOperatorCollection.lessThanOrEquals());
            comparisonInitialed = true;
            // ** MonitorExit[var0] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void initRelation() {
        if (relationInitialed) return;
        Class<SequenceConditionUtils> clazz = SequenceConditionUtils.class;
        synchronized (SequenceConditionUtils.class) {
            if (relationInitialed) return;
            relationMap = new HashMap<String, Relation>();
            relationMap.put(RelationCollection.andRelation().getName(), RelationCollection.andRelation());
            relationMap.put(RelationCollection.orRelation().getName(), RelationCollection.orRelation());
            relationInitialed = true;
            // ** MonitorExit[var0] (shouldn't be in output)
            return;
        }
    }

    public static ComparisonOperator getComparisonOperator(String operator) {
        if (!StringUtils.hasText(operator)) {
            return null;
        }
        SequenceConditionUtils.initOperator();
        return comparisonOperatorMap.get(operator);
    }

    public static Relation getRelation(String relation) {
        if (!StringUtils.hasText(relation)) {
            return null;
        }
        SequenceConditionUtils.initRelation();
        return relationMap.get(relation);
    }

    public static Object handleParamType(ValueType paramType, Object paramValueObj) {
        if (paramValueObj == null || paramType == null) {
            return paramValueObj;
        }
        Object result = paramValueObj;
        try {
            switch (paramType) {
                case STRING: {
                    result = String.valueOf(paramValueObj);
                    break;
                }
                case DATE: {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    result = dateFormat.parse(String.valueOf(paramValueObj));
                    break;
                }
                case INTEGER: {
                    result = Integer.parseInt(String.valueOf(paramValueObj));
                    break;
                }
                case DECIMAL: {
                    result = new BigDecimal(String.valueOf(paramValueObj));
                    break;
                }
                case DATETIME: {
                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    result = dateTimeFormat.parse(String.valueOf(paramValueObj));
                    break;
                }
                case DOUBLE: {
                    result = Double.parseDouble(String.valueOf(paramValueObj));
                    break;
                }
                case BOOLEAN: {
                    result = Boolean.parseBoolean(String.valueOf(paramValueObj));
                    break;
                }
                case LONG: {
                    result = Long.parseLong(String.valueOf(paramValueObj));
                    break;
                }
                case IDENTIFY: {
                    result = UUID.fromString(String.valueOf(paramValueObj));
                    break;
                }
            }
        }
        catch (Exception e) {
            log.error("\u53c2\u6570\u8f6c\u6362\u9519\u8bef\uff0c\u53c2\u6570\u7c7b\u578b\uff1a{}\uff0c\u53c2\u6570\u503c\uff1a{}\uff0c\u9519\u8bef\u539f\u56e0\uff1a{}", paramType.name(), paramValueObj, e.getMessage(), e);
        }
        return result;
    }

    public static Object handleBizParamType(ValueType paramType, Object bizValueObj) {
        try {
            if (ValueType.DATE.equals((Object)paramType) || ValueType.DATETIME.equals((Object)paramType)) {
                if (bizValueObj instanceof Calendar) {
                    Calendar calendar = (Calendar)bizValueObj;
                    bizValueObj = calendar.getTime();
                } else if (bizValueObj instanceof String) {
                    SimpleDateFormat format = ValueType.DATE.equals((Object)paramType) ? new SimpleDateFormat("yyyy-MM-dd") : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    bizValueObj = format.parse((String)bizValueObj);
                }
            } else {
                bizValueObj = SequenceConditionUtils.handleParamType(paramType, bizValueObj);
            }
        }
        catch (Exception e) {
            log.error("\u53c2\u6570\u8f6c\u6362\u9519\u8bef\uff0c\u53c2\u6570\u7c7b\u578b\uff1a{}\uff0c\u53c2\u6570\u503c\uff1a{}\uff0c\u9519\u8bef\u539f\u56e0\uff1a{}", paramType.name(), bizValueObj, e.getMessage(), e);
        }
        return bizValueObj;
    }

    public static boolean executeConditionView(String conditionViewStr) {
        if (!StringUtils.hasText(conditionViewStr) || "\"\"".equals(conditionViewStr)) {
            return true;
        }
        SequenceConditionView conditionView = (SequenceConditionView)JSONUtil.parseObject((String)conditionViewStr, SequenceConditionView.class);
        return SequenceConditionUtils.executeConditionView(conditionView);
    }

    public static boolean executeConditionView(SequenceConditionView conditionView) {
        if (conditionView == null) {
            return true;
        }
        String groupRelation = conditionView.getGroupRelation();
        List groupInfo = conditionView.getGroupInfo();
        if (CollectionUtils.isEmpty(groupInfo)) {
            return true;
        }
        ArrayList<ConditionGroupCheck> groupChecks = new ArrayList<ConditionGroupCheck>();
        for (ConditionGroupInfo conditionGroupInfo : groupInfo) {
            String internalRelation = conditionGroupInfo.getInternalRelation();
            List infos = conditionGroupInfo.getInfo();
            if (CollectionUtils.isEmpty(infos)) continue;
            ArrayList<ConditionRowCheck> rowChecks = new ArrayList<ConditionRowCheck>();
            for (ConditionRow info : infos) {
                ConditionRowCheck rowCheck = new ConditionRowCheck(info);
                rowChecks.add(rowCheck);
            }
            ConditionGroupCheck groupCheck = new ConditionGroupCheck(internalRelation, rowChecks);
            groupChecks.add(groupCheck);
        }
        ConditionCombinationCheck combinationCheck = new ConditionCombinationCheck(groupRelation, groupChecks);
        VaWorkflowContext vaWorkflowContext = VaContext.getVaWorkflowContext();
        Map variables = vaWorkflowContext.getVariables();
        if (CollectionUtils.isEmpty(variables)) {
            variables = vaWorkflowContext.getWorkflowDTO().getWorkflowVariables();
        }
        return combinationCheck.check(variables);
    }

    static {
        relationInitialed = false;
    }
}


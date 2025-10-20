/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.FlexibleRuleExecutorImpl;
import com.jiuqi.gcreport.inputdata.flexible.utils.RuleMappingImplUtils;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

public class AutoOffsetHelper {
    private InputDataService inputDataService = (InputDataService)SpringContextUtils.getBean(InputDataService.class);
    protected GcOffsetAppInputDataService gcOffsetAppInputDataService = (GcOffsetAppInputDataService)SpringContextUtils.getBean(GcOffsetAppInputDataService.class);
    protected IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);

    public void mappingRuleByInputDataItems(List<InputDataEO> inputItems) {
        ArrayList recordKeys = new ArrayList();
        inputItems.forEach(inputItem -> {
            String recordKey = this.getRecordKeys((InputDataEO)((Object)inputItem));
            if (recordKeys.contains(recordKey)) {
                return;
            }
            this.againMappingRule((InputDataEO)((Object)inputItem));
            recordKeys.add(recordKey);
        });
    }

    public void cancelInputOffset(List<InputDataEO> inputItems) {
        Map<String, InputRuleFilterCondition> conditions = this.getInputRuleFilterCondition(inputItems);
        HashMap ruleGroup = new HashMap(16);
        conditions.forEach((relGroupKey, condition) -> {
            FlexibleRuleExecutorImpl flexibleRuleExecutor;
            AbstractUnionRule rule = (AbstractUnionRule)ruleGroup.get(condition.getRuleId());
            if (rule == null && !StringUtils.isEmpty((String)(rule = UnionRuleUtils.getAbstractUnionRuleById((String)condition.getRuleId())).getId())) {
                ruleGroup.put(rule.getId(), rule);
            }
            if ((flexibleRuleExecutor = this.getFlexibleRuleExecutorImpl(rule)) == null) {
                return;
            }
            boolean isBalance = flexibleRuleExecutor.hasBalanceFormulaSetting(rule);
            if (isBalance || rule.getEnableToleranceFlag().booleanValue()) {
                condition.setRuleId(rule.getId());
                List<String> offsetInputItemIds = this.inputDataService.queryOffsetedByInputRuleFilterCondition((InputRuleFilterCondition)condition).stream().map(InputDataEO::getId).collect(Collectors.toList());
                this.gcOffsetAppInputDataService.cancelInputOffset(offsetInputItemIds, InputWriteNecLimitCondition.newMergeOrgLimit(condition.getTaskId(), condition.getNrPeriod(), condition.getCurrency()));
            }
        });
    }

    private void againMappingRule(InputDataEO inputItem) {
        InputRuleFilterCondition condition = this.createInputRuleFilterConditionByItem(inputItem);
        List<InputDataEO> inputDataRuleEmptyItems = this.inputDataService.queryByCondition(condition).stream().filter(inputData -> StringUtils.isEmpty((String)inputData.getUnionRuleId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(inputDataRuleEmptyItems)) {
            RuleMappingImplUtils.remappingRule(inputDataRuleEmptyItems, inputItem.getReportSystemId());
        }
    }

    private String getRecordKeys(InputDataEO inputItem) {
        String convertedUnitId = inputItem.getConvertOffsetOrgFlag() != false ? inputItem.getOffsetOrgCode() : inputItem.getUnitId();
        String convertedOppUnitId = inputItem.getConvertOffsetOrgFlag() != false ? inputItem.getOffsetOppUnitId() : inputItem.getOppUnitId();
        return DigestUtils.md5DigestAsHex((this.getUnitComb(convertedUnitId, convertedOppUnitId) + inputItem.getReportSystemId() + inputItem.getCurrency() + inputItem.getPeriod() + inputItem.getUnionRuleId()).getBytes(Charset.defaultCharset()));
    }

    private String getUnitComb(String unitId1, String unitId2) {
        StringBuilder buf = new StringBuilder();
        if (unitId1.compareTo(unitId2) <= 0) {
            return buf.append(unitId1).append(",").append(unitId2).toString();
        }
        return buf.append(unitId2).append(",").append(unitId1).toString();
    }

    private Map<String, InputRuleFilterCondition> getInputRuleFilterCondition(List<InputDataEO> inputItems) {
        HashMap<String, InputRuleFilterCondition> conditionGroup = new HashMap<String, InputRuleFilterCondition>(16);
        inputItems.forEach(inputItem -> {
            String recordKey = this.getRecordKeys((InputDataEO)((Object)inputItem));
            if (conditionGroup.containsKey(recordKey)) {
                return;
            }
            InputRuleFilterCondition condition = this.createInputRuleFilterConditionByItem((InputDataEO)((Object)inputItem));
            conditionGroup.put(recordKey, condition);
        });
        if (CollectionUtils.isEmpty(conditionGroup)) {
            return Collections.emptyMap();
        }
        return conditionGroup;
    }

    private FlexibleRuleExecutorImpl getFlexibleRuleExecutorImpl(AbstractUnionRule rule) {
        if (rule instanceof FlexibleRuleDTO) {
            return new FlexibleRuleExecutorImpl();
        }
        return null;
    }

    private InputRuleFilterCondition createInputRuleFilterConditionByItem(InputDataEO inputItem) {
        InputRuleFilterCondition condition = new InputRuleFilterCondition();
        condition.setCurrency(inputItem.getCurrency());
        condition.setOrgType(inputItem.getOrgType());
        condition.setNrPeriod(inputItem.getPeriod());
        condition.setOffsetState(inputItem.getOffsetState());
        condition.setTaskId(inputItem.getTaskId());
        String schemeKey = null;
        try {
            schemeKey = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(inputItem.getPeriod(), inputItem.getTaskId()).getSchemeKey();
        }
        catch (Exception exception) {
            // empty catch block
        }
        condition.setSchemeId(schemeKey);
        condition.setRelUnitId1(inputItem.getUnitId());
        condition.setRelUnitId2(inputItem.getOppUnitId());
        condition.setRuleId(inputItem.getUnionRuleId());
        condition.setSelectAdjustCode((String)inputItem.getFieldValue("ADJUST"));
        return condition;
    }
}


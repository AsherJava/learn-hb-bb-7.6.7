/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.flexible.utils;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.formula.GcInputDataFormulaEvalService;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class RuleMappingImplUtils {
    private static List<RuleTypeEnum> ruleTypes = new ArrayList<RuleTypeEnum>();
    private static ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
    private static Logger logger = LoggerFactory.getLogger(RuleMappingImplUtils.class);

    public static Collection<InputDataEO> mappingRule(Collection<InputDataEO> inputItems, String reportSystemId) {
        ArrayList<InputDataEO> matchedItems = new ArrayList<InputDataEO>();
        if (CollectionUtils.isEmpty(inputItems)) {
            return matchedItems;
        }
        RuleMappingImplUtils.setDc(inputItems);
        List ruleTypeCodes = ruleTypes.stream().map(RuleTypeEnum::getCode).collect(Collectors.toList());
        List rules = UnionRuleUtils.selectRuleListByReportSystemAndRuleTypes((String)reportSystemId, ruleTypeCodes);
        if (CollectionUtils.isEmpty(rules)) {
            return matchedItems;
        }
        List<String> inputDataNumberFields = RuleMappingImplUtils.getInputDataNumberFields(inputItems.stream().findFirst().get().getTaskId());
        inputItems.forEach(inputItem -> {
            inputItem.setReportSystemId(reportSystemId);
            boolean matchRuleSuccess = rules.stream().anyMatch(rule -> RuleMappingImplUtils.mappingRule(inputItem, rule, inputDataNumberFields));
            if (matchRuleSuccess) {
                matchedItems.add((InputDataEO)((Object)inputItem));
            }
        });
        return matchedItems;
    }

    public static Collection<InputDataEO> mappingFinancialCheckRule(Collection<InputDataEO> inputItems, String reportSystemId) {
        ArrayList<InputDataEO> matchedItems = new ArrayList<InputDataEO>();
        if (CollectionUtils.isEmpty(inputItems)) {
            return matchedItems;
        }
        RuleMappingImplUtils.setDc(inputItems);
        ArrayList<RuleTypeEnum> ruleTypes = new ArrayList<RuleTypeEnum>();
        ruleTypes.add(RuleTypeEnum.FINANCIAL_CHECK);
        List ruleTypeCodes = ruleTypes.stream().map(RuleTypeEnum::getCode).collect(Collectors.toList());
        List rules = UnionRuleUtils.selectRuleListByReportSystemAndRuleTypes((String)reportSystemId, ruleTypeCodes);
        if (CollectionUtils.isEmpty(rules)) {
            return matchedItems;
        }
        List<String> inputDataNumberFields = RuleMappingImplUtils.getInputDataNumberFields(inputItems.stream().findFirst().get().getTaskId());
        inputItems.forEach(inputItem -> {
            inputItem.setReportSystemId(reportSystemId);
            boolean matchRuleSuccess = rules.stream().anyMatch(rule -> RuleMappingImplUtils.mappingRule(inputItem, rule, inputDataNumberFields));
            if (matchRuleSuccess) {
                matchedItems.add((InputDataEO)((Object)inputItem));
            }
        });
        return matchedItems;
    }

    private static void setDc(Collection<InputDataEO> inputItems) {
        inputItems.forEach(inputItem -> {
            ConsolidatedSubjectEO subject = consolidatedSubjectService.getSubjectByCode(inputItem.getReportSystemId(), inputItem.getSubjectCode());
            if (subject == null) {
                inputItem.setDc(OrientEnum.D.getValue());
                return;
            }
            Integer orient = subject.getOrient() == null ? null : Integer.valueOf(String.valueOf(subject.getOrient()));
            inputItem.setDc(orient == null || orient == OrientEnum.C.getValue() ? OrientEnum.D.getValue() : OrientEnum.C.getValue());
        });
    }

    public static AbstractUnionRule mappingRule(String systemId, List<AbstractUnionRule> flexibleRules, InputDataEO inputData, List<String> inputDataNumberFields) {
        String reportSystemId = inputData.getReportSystemId();
        if (reportSystemId == null) {
            if (StringUtils.isEmpty(systemId)) {
                return null;
            }
            inputData.setReportSystemId(systemId);
        }
        if (CollectionUtils.isEmpty(flexibleRules)) {
            return null;
        }
        for (AbstractUnionRule rule : flexibleRules) {
            if (!RuleMappingImplUtils.mappingRule(inputData, rule, inputDataNumberFields)) continue;
            return rule;
        }
        inputData.setUnionRuleId(null);
        String subjectCode = inputData.getSubjectCode();
        if (StringUtils.isEmpty(subjectCode)) {
            inputData.setDc(OrientEnum.D.getValue());
            return null;
        }
        ConsolidatedSubjectEO subject = consolidatedSubjectService.getSubjectByCode(reportSystemId, subjectCode);
        if (subject == null) {
            inputData.setDc(OrientEnum.D.getValue());
            return null;
        }
        Integer orient = subject.getOrient();
        inputData.setDc(orient == null || orient == OrientEnum.C.getValue() ? OrientEnum.D.getValue() : OrientEnum.C.getValue());
        return null;
    }

    private static boolean mappingRule(InputDataEO inputData, AbstractUnionRule rule, List<String> inputDataNumberFields) {
        FlexibleRuleDTO flexibleRuleDTO;
        inputData.setUnionRuleId(null);
        if (inputData.getOppUnitId().equals(inputData.getUnitId())) {
            return false;
        }
        OrientEnum dc = RuleMappingImplUtils.filterRuleBySubject(inputData, rule);
        if (dc == null) {
            return false;
        }
        List applyGcUnits = rule.getApplyGcUnits();
        if (!CollectionUtils.isEmpty(applyGcUnits)) {
            GcOrgCacheVO oppUnitOrg;
            GcOrgCacheVO unitOrg;
            String cateGory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)inputData.getTaskId());
            YearPeriodObject yp = new YearPeriodObject(null, String.valueOf(inputData.getPeriod()));
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(cateGory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO commonUnit = orgTool.getCommonUnit(unitOrg = orgTool.getOrgByCode(inputData.getOrgCode()), oppUnitOrg = orgTool.getOrgByCode(inputData.getOppUnitId()));
            if (Objects.isNull(commonUnit)) {
                return false;
            }
            if (!applyGcUnits.contains(commonUnit.getCode())) {
                return false;
            }
        }
        if (!RuleMappingImplUtils.checkApplicableCondition(inputData, rule)) {
            return false;
        }
        inputData.setDc(dc.getValue());
        inputData.setUnionRuleId(rule.getId());
        if (rule instanceof FlexibleRuleDTO && (flexibleRuleDTO = (FlexibleRuleDTO)rule).getCheckOffsetFlag().booleanValue()) {
            List offsetFields = flexibleRuleDTO.getOffsetGroupingField();
            boolean isNumberField = false;
            double unCheckAmt = 0.0;
            if (!CollectionUtils.isEmpty(offsetFields)) {
                for (String fieldName : offsetFields) {
                    if (!inputDataNumberFields.contains(fieldName)) continue;
                    isNumberField = true;
                    unCheckAmt += ((Double)inputData.getFieldValue(fieldName)).doubleValue();
                }
            }
            if (isNumberField) {
                inputData.setUnCheckAmt(unCheckAmt);
            } else {
                inputData.setUnCheckAmt(inputData.getAmt());
            }
            inputData.setCheckState(InputDataCheckStateEnum.NOTCHECK.getValue());
        }
        return true;
    }

    private static OrientEnum filterRuleBySubject(InputDataEO inputData, AbstractUnionRule rule) {
        String subjectCode = inputData.getSubjectCode();
        FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)rule;
        boolean match = flexibleRuleDTO.getSrcDebitAllChildrenCodes().stream().anyMatch(debitSubjectCode -> debitSubjectCode.equals(subjectCode));
        if (match) {
            return OrientEnum.D;
        }
        match = flexibleRuleDTO.getSrcCreditAllChildrenCodes().stream().anyMatch(creditSubjectCode -> creditSubjectCode.equals(subjectCode));
        if (match) {
            return OrientEnum.C;
        }
        return null;
    }

    private static boolean checkApplicableCondition(InputDataEO inputData, AbstractUnionRule rule) {
        String ruleCondition = rule.getRuleCondition();
        if (StringUtils.isEmpty(ruleCondition)) {
            return true;
        }
        Map fields = inputData.getFields();
        logger.info("\u9002\u7528\u6761\u4ef6\uff1a" + ruleCondition);
        logger.info("\u5185\u90e8\u8868\u6570\u636e\uff1a" + fields.toString());
        GcInputDataFormulaEvalService inputDataFormulaEvalService = (GcInputDataFormulaEvalService)SpringContextUtils.getBean(GcInputDataFormulaEvalService.class);
        try {
            return inputDataFormulaEvalService.checkMxInputData(DimensionUtils.generateDimSet(fields.get("MDCODE"), fields.get("DATATIME"), fields.get("MD_CURRENCY"), fields.get("MD_GCORGTYPE"), (String)((String)fields.get("ADJUST")), (String)inputData.getTaskId()), ruleCondition, inputData);
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u516c\u5f0f\u53d1\u751f\u5f02\u5e38", e);
            return false;
        }
    }

    public static boolean checkRelTxnApplicableCondition(List<InputDataEO> inputItems, AbstractUnionRule rule, GcCalcArgmentsDTO arg) {
        String ruleCondition = rule.getRuleCondition();
        if (StringUtils.isEmpty(ruleCondition)) {
            return true;
        }
        logger.info("\u9002\u7528\u6761\u4ef6\uff1a" + ruleCondition);
        GcInputDataFormulaEvalService inputDataFormulaEvalService = (GcInputDataFormulaEvalService)SpringContextUtils.getBean(GcInputDataFormulaEvalService.class);
        try {
            for (InputDataEO inputData : inputItems) {
                Map fields = inputData.getFields();
                boolean success = inputDataFormulaEvalService.checkMxInputData(DimensionUtils.generateDimSet(fields.get("MDCODE"), (Object)arg.getPeriodStr(), (Object)arg.getCurrency(), (Object)arg.getOrgType(), (String)((String)inputData.getFieldValue("ADJUST")), (String)inputData.getTaskId()), ruleCondition, inputData);
                if (success) continue;
                return false;
            }
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u516c\u5f0f\u53d1\u751f\u5f02\u5e38", e);
            return false;
        }
        return true;
    }

    public static void remappingRule(Collection<InputDataEO> inputItems, String reportSystemId) {
        Map<String, RuleInfo> ruleInfoMapping = inputItems.stream().collect(Collectors.toMap(DefaultTableEntity::getId, inputItem -> new RuleInfo(inputItem.getUnionRuleId(), inputItem.getDc())));
        RuleMappingImplUtils.mappingRule(inputItems, reportSystemId);
        Iterator<InputDataEO> it = inputItems.iterator();
        ArrayList<InputDataEO> needUpdateRuleInfoItems = new ArrayList<InputDataEO>();
        while (it.hasNext()) {
            InputDataEO inputItem2 = it.next();
            if (inputItem2.getUnionRuleId() == null || !inputItem2.getUnionRuleId().equals(ruleInfoMapping.get(inputItem2.getId()).getRuleId())) {
                needUpdateRuleInfoItems.add(inputItem2);
                continue;
            }
            if (inputItem2.getDc().equals(ruleInfoMapping.get(inputItem2.getId()).getDc())) continue;
            needUpdateRuleInfoItems.add(inputItem2);
        }
        ((InputDataService)SpringContextUtils.getBean(InputDataService.class)).updateRuleInfo(needUpdateRuleInfoItems);
    }

    private static List<String> getInputDataNumberFields(String taskId) {
        try {
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
            String tableName = inputDataNameProvider.getTableNameByTaskId(taskId);
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByName(tableName);
            List<String> numberFieldCodes = dataModelService.getColumnModelDefinesByTable(tableDefine.getID()).stream().filter(columnModelDefine -> ColumnModelType.BIGDECIMAL.equals((Object)columnModelDefine.getColumnType()) || ColumnModelType.DOUBLE.equals((Object)columnModelDefine.getColumnType())).map(ColumnModelDefine::getName).collect(Collectors.toList());
            return numberFieldCodes;
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5185\u90e8\u8868\u91d1\u989d\u5b57\u6bb5\u5f02\u5e38\u3002", e);
            return null;
        }
    }

    static {
        ruleTypes.add(RuleTypeEnum.FLEXIBLE);
    }

    private static class RuleInfo {
        private final String ruleId;
        private final Integer dc;

        RuleInfo(String ruleId, Integer dc) {
            this.ruleId = ruleId;
            this.dc = dc;
        }

        private String getRuleId() {
            return this.ruleId;
        }

        private Integer getDc() {
            return this.dc;
        }
    }
}


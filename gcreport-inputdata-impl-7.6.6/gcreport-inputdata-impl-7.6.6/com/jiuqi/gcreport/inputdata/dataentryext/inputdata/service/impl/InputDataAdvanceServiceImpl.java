/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataAdvanceService;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.InputDataRuleExecutor;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.FlexibleRuleExecutorImpl;
import com.jiuqi.gcreport.inputdata.flexible.utils.RuleMappingImplUtils;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class InputDataAdvanceServiceImpl
implements InputDataAdvanceService {
    private final InputDataDao dao;
    private final IDataAccessProvider dataAccessProvider;
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private final IFormulaRunTimeController formulaRunTimeController;
    private final IRunTimeViewController runTimeViewController;
    private final IEntityViewRunTimeController entityViewRunTimeController;
    private final InputDataNameProvider inputDataNameProvider;
    private final ConsolidatedTaskService consolidatedTaskCacheService;
    private Logger logger = LoggerFactory.getLogger(FlexibleRuleExecutorImpl.class);

    public InputDataAdvanceServiceImpl(InputDataDao dao, IDataAccessProvider dataAccessProvider, IDataDefinitionRuntimeController dataDefinitionRuntimeController, IFormulaRunTimeController formulaRunTimeController, IRunTimeViewController runTimeViewController, IEntityViewRunTimeController entityViewRunTimeController, InputDataNameProvider inputDataNameProvider, ConsolidatedTaskService consolidatedTaskCacheService) {
        this.dao = dao;
        this.dataAccessProvider = dataAccessProvider;
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
        this.formulaRunTimeController = formulaRunTimeController;
        this.runTimeViewController = runTimeViewController;
        this.entityViewRunTimeController = entityViewRunTimeController;
        this.inputDataNameProvider = inputDataNameProvider;
        this.consolidatedTaskCacheService = consolidatedTaskCacheService;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateCustomInfoAfterSave(Map<String, String> dimFieldAndValueMapping, List<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputItems.get(0).getTaskId());
        this.dao.updateCustomInfoByKeys(inputItems, dimFieldAndValueMapping, tableName);
    }

    @Override
    public Map<String, Set<String>> realTimeOffset(List<InputDataEO> inputItems, DataEntryContext dataEntryContext, boolean checkOffsetFlag) {
        return this.doRealTimeOffset(inputItems, dataEntryContext, Integer.MAX_VALUE, true, checkOffsetFlag);
    }

    @Override
    public Map<String, Set<String>> realTimeOffsetLimit(List<InputDataEO> inputItems, DataEntryContext dataEntryContext, int maxOffsetAmount) {
        return this.doRealTimeOffset(inputItems, dataEntryContext, maxOffsetAmount, true, true);
    }

    @Override
    public Map<String, Set<String>> doCheckAfterOffset(List<InputDataEO> inputItems) {
        return this.doRealTimeOffset(inputItems, null, Integer.MAX_VALUE, true, true);
    }

    private Map<String, Set<String>> doRealTimeOffset(List<InputDataEO> inputItems, DataEntryContext dataEntryContext, int maxOffsetAmount, boolean realTimeOffsetOptionFlag, boolean checkOffsetFlag) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return Collections.emptyMap();
        }
        HashMap<String, AbstractUnionRule> ruleGroup = new HashMap<String, AbstractUnionRule>(16);
        HashMap<String, List<InputDataEO>> inputItemGroup = new HashMap<String, List<InputDataEO>>(16);
        if (dataEntryContext != null) {
            List<String> itemIds = inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
            ((InputDataAdvanceService)SpringContextUtils.getBean(InputDataAdvanceService.class)).formulaCalc(dataEntryContext, itemIds);
            inputItems = ((InputDataAdvanceService)SpringContextUtils.getBean(InputDataAdvanceService.class)).inputDataCalAfterMappingRule(itemIds, dataEntryContext);
        }
        boolean isRealTimeOffset = maxOffsetAmount > 0;
        this.groupInputItems(inputItems, ruleGroup, inputItemGroup, checkOffsetFlag, isRealTimeOffset);
        HashMap<String, Set<String>> allOffsetedOrgAndItemIdMapping = new HashMap<String, Set<String>>();
        inputItemGroup.forEach((ruleId, sameGroupItems) -> {
            AbstractUnionRule rule = (AbstractUnionRule)ruleGroup.get(ruleId);
            InputDataRuleExecutor ruleProcessor = this.getRuleProcessor(rule);
            if (ruleProcessor == null) {
                return;
            }
            Map<String, Set<String>> offsetedOrgAndItemIdMapping = ruleProcessor.realTimeOffset(rule, (List<InputDataEO>)sameGroupItems, realTimeOffsetOptionFlag, checkOffsetFlag);
            if (CollectionUtils.isEmpty(offsetedOrgAndItemIdMapping)) {
                return;
            }
            offsetedOrgAndItemIdMapping.forEach((key, value) -> {
                if (allOffsetedOrgAndItemIdMapping.containsKey(key)) {
                    Set itemIds = (Set)allOffsetedOrgAndItemIdMapping.get(key);
                    itemIds.addAll(value);
                    allOffsetedOrgAndItemIdMapping.put((String)key, itemIds);
                } else {
                    allOffsetedOrgAndItemIdMapping.put((String)key, (Set<String>)value);
                }
            });
        });
        return allOffsetedOrgAndItemIdMapping;
    }

    private void groupInputItems(List<InputDataEO> inputItems, Map<String, AbstractUnionRule> ruleGroup, Map<String, List<InputDataEO>> inputItemGroup, boolean checkOffsetFlag, boolean isRealTimeOffset) {
        inputItems.forEach(inputItem -> {
            String ruleId = inputItem.getUnionRuleId();
            if (ruleId == null) {
                return;
            }
            AbstractUnionRule rule = (AbstractUnionRule)ruleGroup.get(ruleId);
            if (rule == null) {
                rule = UnionRuleUtils.getAbstractUnionRuleById((String)ruleId);
                ruleGroup.put(ruleId, rule);
            }
            if (rule == null) {
                return;
            }
            FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)rule;
            if (flexibleRuleDTO.getCheckOffsetFlag() != false && checkOffsetFlag ? InputDataCheckStateEnum.CHECK.getValue().equals(inputItem.getCheckState()) : !isRealTimeOffset || ReportOffsetStateEnum.OFFSET.getValue().equals(inputItem.getOffsetState())) {
                return;
            }
            List sameGroupItems = inputItemGroup.computeIfAbsent(ruleId, k -> new ArrayList());
            sameGroupItems.add(inputItem);
        });
    }

    private InputDataRuleExecutor getRuleProcessor(AbstractUnionRule rule) {
        if (rule instanceof FlexibleRuleDTO) {
            return new FlexibleRuleExecutorImpl();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void formulaCalc(DataEntryContext envContext, List<String> keys) {
        if (StringUtils.isEmpty(envContext.getFormulaSchemeKey()) || StringUtils.isEmpty(envContext.getFormKey())) {
            return;
        }
        List parsedExpressions = this.formulaRunTimeController.getParsedExpressionByForm(envContext.getFormulaSchemeKey(), envContext.getFormKey(), DataEngineConsts.FormulaType.CALCULATE);
        if (CollectionUtils.isEmpty(parsedExpressions)) {
            return;
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(envContext.getTaskKey());
        Collection allFields = SqlUtils.getColumnNamesByTableDefine((String)tableName);
        if (CollectionUtils.isEmpty(parsedExpressions = parsedExpressions.stream().filter(expression -> expression != null && !expression.toString().toLowerCase().contains("gcsimplecopy") && !expression.toString().toLowerCase().contains("gcfloatcopy") && !expression.toString().toLowerCase().contains("floatcopy") && !expression.toString().toLowerCase().contains("sumhb") && !expression.toString().toLowerCase().contains("sumhbzb") && expression.getAssignNode() != null && expression.getAssignNode().getQueryField() != null && allFields.contains(expression.getAssignNode().getQueryField().getFieldName())).collect(Collectors.toList()))) {
            return;
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, envContext.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        FormulaCallBack callback = new FormulaCallBack();
        callback.getParsedExpressions().addAll(parsedExpressions);
        IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)envContext.getDimensionSet());
        AbstractMonitor monitor = new AbstractMonitor();
        try {
            runner.prepareCalc(executorContext, dimensionValueSet, (IMonitor)monitor);
            runner.run((IMonitor)monitor);
        }
        catch (Exception e) {
            this.logger.debug("\u589e\u91cf\u8fd0\u7b97\u8fd0\u884c\u65f6\u53d1\u751f\u5f02\u5e38\uff0c\u516c\u5f0f\u65b9\u6848ID\u3010" + envContext.getFormulaSchemeKey() + "\u3011\uff0c\u8868\u5355ID\u3010" + envContext.getFormKey() + "\u3011\uff0c\u62a5\u8868\u65b9\u6848ID\u3010" + envContext.getFormSchemeKey() + "\u3011\u3002", e);
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.inputdataadvance.formulacalcexceptionmsg"), e);
        }
    }

    @Override
    public Map<String, Set<String>> autoBatchOffset(List<InputDataEO> inputItems, DataEntryContext dataEntryContext) {
        return this.doRealTimeOffset(inputItems, dataEntryContext, Integer.MAX_VALUE, false, false);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<InputDataEO> inputDataCalAfterMappingRule(Collection<String> inputItemIds, DataEntryContext envContext) {
        if (CollectionUtils.isEmpty(inputItemIds)) {
            return Collections.emptyList();
        }
        String period = envContext.getDimensionSet().getOrDefault("DATATIME", new DimensionValue()).getValue();
        String systemId = this.consolidatedTaskCacheService.getSystemIdBySchemeId(envContext.getFormSchemeKey(), period);
        if (StringUtils.isEmpty(systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notsystemerrormsg"));
        }
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(envContext.getTaskKey());
        List<InputDataEO> inputDataCalAfterItems = this.dao.queryByIds(inputItemIds, tableName);
        if (CollectionUtils.isEmpty(inputDataCalAfterItems)) {
            return Collections.emptyList();
        }
        RuleMappingImplUtils.mappingRule(inputDataCalAfterItems, systemId);
        this.dao.updateRuleAndDcById(inputDataCalAfterItems, tableName);
        return inputDataCalAfterItems;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.JsonNodeFactory
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.CrossTaskCalcVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition
 *  com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition
 *  com.jiuqi.gcreport.nr.impl.function.GcFormulaThreadContext
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.CrossTaskCalcVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.service.InputDataCheckService;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.service.InputDataService;
import com.jiuqi.gcreport.inputdata.dto.InputRuleFilterCondition;
import com.jiuqi.gcreport.inputdata.dto.MergeCalcFilterCondition;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.InputDataRuleExecutor;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.RuleChecker;
import com.jiuqi.gcreport.inputdata.flexible.processor.executor.impl.RealTimeOffsetMonitor;
import com.jiuqi.gcreport.inputdata.flexible.utils.CorporateConvertUtils;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputWriteNecLimitCondition;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.OffsetQueueEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.ReportOffsetStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.OffsetQueueService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.nr.impl.function.GcFormulaThreadContext;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractInputDataRuleExecutorImpl
implements InputDataRuleExecutor,
RuleChecker {
    GcCalcEnvContext processEnv;
    protected String reportSystemId;
    protected boolean realTimeOffsetOptionFlag;
    InputDataService inputService = (InputDataService)SpringContextUtils.getBean(InputDataService.class);
    protected GcOffsetAppInputDataService gcOffsetAppInputDataService = (GcOffsetAppInputDataService)SpringContextUtils.getBean(GcOffsetAppInputDataService.class);
    protected IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
    private ConsolidatedOptionService optionCacheService = (ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class);
    ConsolidatedTaskService taskCacheService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
    private OffsetQueueService offsetQueueService = (OffsetQueueService)SpringContextUtils.getBean(OffsetQueueService.class);
    private OffsetQueueEO offsetQueue;
    int maxOffsetNum = 3;
    Map<String, Set<String>> offsetedOrgAndItemIdMapping = new HashMap<String, Set<String>>();
    RealTimeOffsetMonitor realTimeOffsetMonitor = null;
    String curRelGroupKey = null;
    boolean isManualBatchOffset = false;
    boolean isCheckOffset = false;
    AbstractUnionRule manualBatchOffsetRule;
    InputDataCheckService inputDataCheckService = (InputDataCheckService)SpringContextUtils.getBean(InputDataCheckService.class);
    protected ConsolidatedOptionService optionService = (ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class);
    Integer offsetedNum = 0;

    @Override
    public Map<String, Set<String>> realTimeOffset(AbstractUnionRule rule, List<InputDataEO> inputItems, boolean realTimeOffsetOptionFlag, boolean checkOffsetFlag) {
        this.realTimeOffsetOptionFlag = realTimeOffsetOptionFlag;
        if (!this.canRealTimeOffset(rule) || !this.mustRelUnit()) {
            return this.offsetedOrgAndItemIdMapping;
        }
        Map<String, InputRuleFilterCondition> conditions = this.getFilter(inputItems);
        if (CollectionUtils.isEmpty(conditions)) {
            return this.offsetedOrgAndItemIdMapping;
        }
        this.realTimeOffsetMonitor = RealTimeOffsetMonitor.newInstance(rule.getId(), inputItems.size(), conditions.size());
        GcFormulaThreadContext.enableCache();
        this.processEnv = new GcCalcEnvContextImpl(UUIDUtils.newUUIDStr());
        List inputDataIds = inputItems.stream().map(InputDataEO::getId).collect(Collectors.toList());
        FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)rule;
        conditions.forEach((relGroupKey, condition) -> {
            this.curRelGroupKey = relGroupKey;
            this.realTimeOffsetMonitor.beginRelOffset((String)relGroupKey);
            GcCalcArgmentsDTO arg = this.createGcCalcArgments((InputRuleFilterCondition)condition);
            if (this.joinOffsetQueue(arg, this.getUnitComb(condition.getRelUnitId1(), condition.getRelUnitId2()))) {
                this.realTimeOffsetMonitor.skipRelOffset((String)relGroupKey, "\u52a0\u5165\u62b5\u9500\u961f\u5217\u5931\u8d25");
                return;
            }
            try {
                ((GcCalcEnvContextImpl)this.processEnv).setCalcArgments(arg);
                String ccyCheckResult = this.checkCurrencyCanOffset(this.processEnv.getCalcArgments().getCurrency());
                if (!StringUtils.isEmpty(ccyCheckResult)) {
                    this.realTimeOffsetMonitor.skipRelOffset((String)relGroupKey, ccyCheckResult);
                    return;
                }
                this.reportSystemId = this.taskCacheService.getSystemIdBySchemeId(arg.getSchemeId(), arg.getPeriodStr());
                if (this.reportSystemId == null) {
                    this.realTimeOffsetMonitor.skipRelOffset((String)relGroupKey, arg.getSchemeId() + "\u6ca1\u6709\u5173\u8054\u5408\u5e76\u4f53\u7cfb");
                    return;
                }
                Integer maxOffsetNumObj = this.optionCacheService.getOptionData(this.reportSystemId).getMaxOffsetNum();
                this.maxOffsetNum = maxOffsetNumObj == null ? 1 : maxOffsetNumObj;
                List<Object> list = new ArrayList();
                this.realTimeOffsetMonitor.setRelOffsetNum((String)relGroupKey, list == null ? 0 : list.size());
                if (flexibleRuleDTO.getCheckOffsetFlag().booleanValue() && checkOffsetFlag) {
                    condition.setOffsetState(null);
                    condition.setCheckState(null);
                    list = this.inputService.queryByCondition((InputRuleFilterCondition)condition);
                    list.forEach(CorporateConvertUtils::convertToOffsetUnit);
                    this.doCheckOffset(list);
                } else {
                    condition.setOffsetState(ReportOffsetStateEnum.NOTOFFSET.getValue());
                    condition.setCheckState(null);
                    list = this.inputService.queryByCondition((InputRuleFilterCondition)condition);
                    if (this.isManualBatchOffset && !CollectionUtils.isEmpty(list)) {
                        list = list.stream().filter(inputData -> inputDataIds.contains(inputData.getId())).collect(Collectors.toList());
                    }
                    this.doOffset(list);
                }
                this.realTimeOffsetMonitor.finishRelOffset((String)relGroupKey);
            }
            catch (Exception e) {
                this.realTimeOffsetMonitor.finish();
                throw new BusinessRuntimeException((Throwable)e);
            }
            finally {
                this.offsetQueueService.removeQueue(this.offsetQueue.getId());
            }
        });
        GcFormulaThreadContext.releaseCache();
        this.realTimeOffsetMonitor.finish();
        return this.offsetedOrgAndItemIdMapping;
    }

    @Override
    public Map<String, Set<String>> manualBatchOffset(AbstractUnionRule rule, AbstractUnionRule manualBatchOffsetRule, List<InputDataEO> inputData) {
        this.isManualBatchOffset = true;
        this.manualBatchOffsetRule = manualBatchOffsetRule;
        return this.realTimeOffset(rule, inputData, false, false);
    }

    protected abstract boolean canRealTimeOffset(AbstractUnionRule var1);

    protected abstract boolean mustRelUnit();

    protected Map<String, InputRuleFilterCondition> getFilter(List<InputDataEO> inputItems) {
        HashMap<String, InputRuleFilterCondition> conditionGroup = new HashMap<String, InputRuleFilterCondition>(16);
        AbstractUnionRule rule = this.getRule();
        boolean convert = rule instanceof FlexibleRuleDTO && ((FlexibleRuleDTO)rule).getCorporateOffsetFlag() != false;
        inputItems.forEach(inputItem -> {
            if (convert) {
                String recordKey = this.getRealTimeQueryGroupKey((InputDataEO)((Object)inputItem), true);
                if (conditionGroup.containsKey(recordKey)) {
                    Set checkOrgCodes = ((InputRuleFilterCondition)conditionGroup.get(recordKey)).getCheckOrgCodes();
                    Set checkOppUnitIds = ((InputRuleFilterCondition)conditionGroup.get(recordKey)).getCheckOppUnitIds();
                    checkOrgCodes.add(inputItem.getUnitId());
                    checkOrgCodes.add(inputItem.getOppUnitId());
                    checkOppUnitIds.add(inputItem.getUnitId());
                    checkOppUnitIds.add(inputItem.getOppUnitId());
                    CorporateConvertUtils.convertToOffsetUnit(inputItem);
                    String corporateUnitId = inputItem.getOffsetOrgCode();
                    String corporateOppUnitId = inputItem.getOffsetOppUnitId();
                    if (corporateUnitId != null) {
                        checkOrgCodes.add(corporateUnitId);
                        checkOppUnitIds.add(corporateUnitId);
                    }
                    if (corporateOppUnitId != null) {
                        checkOrgCodes.add(corporateOppUnitId);
                        checkOppUnitIds.add(corporateOppUnitId);
                    }
                    return;
                }
                InputRuleFilterCondition condition = this.createRealTimeFilterConditionByItem((InputDataEO)((Object)inputItem), this.realTimeOffsetOptionFlag, true);
                conditionGroup.put(recordKey, condition);
                return;
            }
            String recordKey = this.getRealTimeQueryGroupKey((InputDataEO)((Object)inputItem), false);
            if (conditionGroup.containsKey(recordKey)) {
                return;
            }
            InputRuleFilterCondition condition = this.createRealTimeFilterConditionByItem((InputDataEO)((Object)inputItem), this.realTimeOffsetOptionFlag, false);
            conditionGroup.put(recordKey, condition);
        });
        if (CollectionUtils.isEmpty(conditionGroup)) {
            return Collections.emptyMap();
        }
        return conditionGroup;
    }

    private String getRealTimeQueryGroupKey(InputDataEO inputItem, boolean convert) {
        String unitId = inputItem.getUnitId();
        String oppUnitId = inputItem.getOppUnitId();
        if (convert) {
            CorporateConvertUtils.convertToOffsetUnit(inputItem);
            unitId = inputItem.getOffsetOrgCode();
            oppUnitId = inputItem.getOffsetOppUnitId();
        }
        return DigestUtils.md5DigestAsHex((this.getUnitComb(unitId, oppUnitId) + inputItem.getReportSystemId() + inputItem.getCurrency() + inputItem.getPeriod()).getBytes(Charset.defaultCharset()));
    }

    private InputRuleFilterCondition createRealTimeFilterConditionByItem(InputDataEO inputItem, boolean realTimeOffsetOptionFlag, boolean convert) {
        String nrPeriod = String.valueOf(inputItem.getFieldValue("DATATIME"));
        String orgType = String.valueOf(inputItem.getFieldValue("MD_GCORGTYPE"));
        YearPeriodObject yp = new YearPeriodObject(null, nrPeriod);
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)inputItem.getTaskId());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgCategory, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO commonUnit = orgTool.getCommonUnit(orgTool.getOrgByCode(inputItem.getUnitId()), orgTool.getOrgByCode(inputItem.getOppUnitId()));
        InputRuleFilterCondition condition = new InputRuleFilterCondition();
        condition.setCurrency(inputItem.getCurrency());
        condition.setOrgType(orgType);
        condition.setNrPeriod(nrPeriod);
        condition.setOffsetState(ReportOffsetStateEnum.NOTOFFSET.getValue());
        condition.setRuleId(inputItem.getUnionRuleId());
        condition.setTaskId(inputItem.getTaskId());
        String schemeKey = null;
        try {
            schemeKey = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(nrPeriod, inputItem.getTaskId()).getSchemeKey();
        }
        catch (Exception exception) {
            // empty catch block
        }
        condition.setSchemeId(schemeKey);
        condition.setOrgId(commonUnit == null ? inputItem.getUnitId() : commonUnit.getId());
        condition.setRelUnitId1(inputItem.getUnitId());
        condition.setRelUnitId2(inputItem.getOppUnitId());
        condition.setSelectAdjustCode((String)inputItem.getFieldValue("ADJUST"));
        if (convert) {
            CorporateConvertUtils.convertToOffsetUnit(inputItem);
            condition.setConvertCheckOrgFlag(inputItem.getConvertOffsetOrgFlag());
            HashSet<String> corporateUnitIds = new HashSet<String>();
            corporateUnitIds.add(inputItem.getUnitId());
            corporateUnitIds.add(inputItem.getOffsetOrgCode());
            condition.setCheckOrgCodes(corporateUnitIds);
            HashSet<String> corporateOppUnitIds = new HashSet<String>();
            corporateOppUnitIds.add(inputItem.getOppUnitId());
            corporateOppUnitIds.add(inputItem.getOffsetOppUnitId());
            condition.setCheckOppUnitIds(corporateOppUnitIds);
        } else {
            condition.setConvertCheckOrgFlag(Boolean.valueOf(false));
        }
        return condition;
    }

    private GcCalcArgmentsDTO createGcCalcArgments(InputRuleFilterCondition condition) {
        GcCalcArgmentsDTO arg = new GcCalcArgmentsDTO();
        PeriodWrapper periodType = new PeriodWrapper(condition.getNrPeriod());
        arg.setAcctYear(Integer.valueOf(periodType.getYear()));
        arg.setPeriodType(Integer.valueOf(periodType.getType()));
        arg.setAcctPeriod(Integer.valueOf(periodType.getPeriod()));
        arg.setPeriodStr(periodType.toString());
        arg.setCurrency(condition.getCurrency());
        arg.setOrgId(condition.getOrgId());
        arg.setOrgType(condition.getOrgType());
        arg.setTaskId(condition.getTaskId());
        arg.setSchemeId(condition.getSchemeId());
        arg.setSelectAdjustCode(condition.getSelectAdjustCode());
        return arg;
    }

    protected void doOffset(List<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        this.recalcAmt(inputItems);
    }

    protected void doCheckOffset(List<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return;
        }
        this.recalcAmt(inputItems);
    }

    Map<String, List<InputDataEO>> groupByOffsetCondition(List<InputDataEO> list) {
        HashMap<String, List<InputDataEO>> group = new HashMap<String, List<InputDataEO>>(16);
        if (CollectionUtils.isEmpty(list)) {
            return group;
        }
        list.forEach(inputItem -> {
            String key = this.getRecordKey((InputDataEO)((Object)inputItem));
            List sameGroupList = group.computeIfAbsent(key, k -> new ArrayList());
            sameGroupList.add(inputItem);
        });
        return group;
    }

    protected abstract String getRecordKey(InputDataEO var1);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void calMerge(AbstractUnionRule rule, GcCalcEnvContext env) {
        this.processEnv = env;
        if (!(rule instanceof FlexibleRuleDTO)) {
            return;
        }
        if (!this.checkCurrencyCanOffset()) {
            return;
        }
        if (!this.checkAndSetRule(rule)) {
            return;
        }
        GcCalcArgmentsDTO arg = env.getCalcArgments();
        this.reportSystemId = this.taskCacheService.getSystemIdBySchemeId(arg.getSchemeId(), arg.getPeriodStr());
        if (StringUtils.isEmpty(this.reportSystemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.notsystemerrormsg"));
        }
        if (!this.taskCacheService.isEntryScheme(arg.getSchemeId(), arg.getPeriodStr()) && !this.taskCacheService.managementCanCalc(arg.getSchemeId(), arg.getPeriodStr(), arg.getOrgId())) {
            return;
        }
        if (this.joinOffsetQueue(arg, null)) {
            return;
        }
        this.realTimeOffsetOptionFlag = false;
        try {
            boolean isBalance;
            GcFormulaThreadContext.enableCache();
            Integer maxOffsetNum = this.optionCacheService.getOptionData(this.reportSystemId).getMaxOffsetNum();
            this.maxOffsetNum = maxOffsetNum == null ? 1 : maxOffsetNum;
            List crossTaskCalcs = this.optionCacheService.getOptionData(this.reportSystemId).getCrossTaskCalcSets();
            String crossTaskId = null;
            for (CrossTaskCalcVO crossTaskCalc : crossTaskCalcs) {
                if (CollectionUtils.isEmpty(crossTaskCalc.getRuleIds()) || !crossTaskCalc.getRuleIds().contains(this.getRule().getId())) continue;
                crossTaskId = crossTaskCalc.getTaskId();
                break;
            }
            MergeCalcFilterCondition condition = new MergeCalcFilterCondition();
            BeanUtils.copyProperties(arg, condition);
            if (StringUtils.hasText(crossTaskId)) {
                condition.setTaskId(crossTaskId);
            }
            if ((isBalance = this.hasBalanceFormulaSetting(rule)) || rule.getEnableToleranceFlag().booleanValue()) {
                List<String> offsetInputItemIds = this.inputService.queryOffsetedByRuleAndMergeCondition(rule.getId(), condition).stream().map(InputDataEO::getId).collect(Collectors.toList());
                this.gcOffsetAppInputDataService.cancelInputOffset(offsetInputItemIds, InputWriteNecLimitCondition.newMergeOrgLimit(condition.getTaskId(), arg.getPeriodStr(), arg.getCurrency()));
            }
            ArrayList<InputDataEO> list = new ArrayList();
            FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)rule;
            if (flexibleRuleDTO.getCheckOffsetFlag().booleanValue()) {
                list = this.inputService.queryUnoffsetedByRuleAndMergeCondition(rule.getId(), condition, null);
                this.doCheckOffset(list);
            } else {
                list = this.inputService.queryUnoffsetedByRuleAndMergeCondition(rule.getId(), condition, ReportOffsetStateEnum.NOTOFFSET.getValue());
                this.doOffset(list);
            }
            Integer offsetedNum = this.offsetedNum;
            if (list.size() > 0) {
                String resultMsg = this.generateMessage(list.size(), offsetedNum, rule, env);
                ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).addResultMsg(resultMsg);
            }
        }
        finally {
            this.offsetQueueService.removeQueue(this.offsetQueue.getId());
            GcFormulaThreadContext.releaseCache();
        }
    }

    public String generateMessage(Integer total, Integer offset, AbstractUnionRule rule, GcCalcEnvContext env) {
        ObjectNode root = JsonNodeFactory.instance.objectNode();
        root.put("template", "[" + rule.getLocalizedName() + "]\u89c4\u5219\u5e94\u62b5\u9500\u6570\u636e" + total + "\u6761\uff0c\u5df2\u62b5\u9500\u6570\u636e" + offset + "\u6761\uff0c${unoffset}");
        ObjectNode data = root.putObject("data");
        data.put("unoffset", "\u672a\u62b5\u9500\u6570\u636e" + (total - offset) + "\u6761");
        ObjectNode events = root.putObject("events");
        ObjectNode totalEvent = events.putObject("unoffset");
        totalEvent.put("type", "offset").put("ruleId", rule.getId());
        return root.toString();
    }

    protected abstract boolean checkAndSetRule(AbstractUnionRule var1);

    protected boolean hasBalanceFormulaSetting(AbstractUnionRule rule) {
        return false;
    }

    void recalcAmt(Collection<InputDataEO> inputItems) {
        inputItems.forEach(inputItem -> {
            inputItem.setOffsetAmt(0.0);
            inputItem.setDiffAmt(0.0);
        });
    }

    private boolean checkCurrencyCanOffset() {
        return StringUtils.isEmpty(this.checkCurrencyCanOffset(this.processEnv.getCalcArgments().getCurrency()));
    }

    private String checkCurrencyCanOffset(String currenctCode) {
        return null;
    }

    String getUnitComb(String unitId1, String unitId2) {
        StringBuilder buf = new StringBuilder();
        if (unitId1.compareTo(unitId2) <= 0) {
            return buf.append(unitId1).append(",").append(unitId2).toString();
        }
        return buf.append(unitId2).append(",").append(unitId1).toString();
    }

    private boolean isSameOrient(Collection<InputDataEO> list) {
        if (CollectionUtils.isEmpty(list) || list.size() == 1) {
            return true;
        }
        Iterator<InputDataEO> it = list.iterator();
        int firstItemDc = it.next().getDc();
        while (it.hasNext()) {
            InputDataEO inputItem = it.next();
            if (firstItemDc == inputItem.getDc()) continue;
            return false;
        }
        return true;
    }

    private boolean isSameUnit(Collection<InputDataEO> list) {
        if (CollectionUtils.isEmpty(list) || list.size() == 1) {
            return true;
        }
        Iterator<InputDataEO> it = list.iterator();
        String firstItemUnitId = it.next().getUnitId();
        while (it.hasNext()) {
            InputDataEO inputItem = it.next();
            if (firstItemUnitId.equals(inputItem.getUnitId())) continue;
            return false;
        }
        return true;
    }

    abstract AbstractUnionRule getRule();

    private boolean joinOffsetQueue(GcCalcArgmentsDTO arg, String unitComb) {
        this.offsetQueue = new OffsetQueueEO();
        if (!StringUtils.isEmpty(unitComb)) {
            this.offsetQueue.setUnitComb(unitComb);
        } else {
            this.offsetQueue.setUnitComb(arg.getOrgId());
        }
        this.offsetQueue.setNrPeriod(arg.getPeriodStr());
        this.offsetQueue.setCurrencyCode(arg.getCurrency());
        this.offsetQueue.setSchemeId(arg.getSchemeId());
        this.offsetQueue.setUnionRuleId(this.getRule().getId());
        return !this.offsetQueueService.joinQueue(this.offsetQueue);
    }

    List<List<GcOffSetVchrItemDTO>> groupByAmtSign(List<GcOffSetVchrItemDTO> offsetItems) {
        ArrayList<List<GcOffSetVchrItemDTO>> group = new ArrayList<List<GcOffSetVchrItemDTO>>();
        ArrayList positiveItems = new ArrayList();
        ArrayList negativeItems = new ArrayList();
        group.add(positiveItems);
        group.add(negativeItems);
        offsetItems.forEach(offsetItem -> {
            double amt = offsetItem.getDebit() + offsetItem.getCredit();
            if (amt > 0.0) {
                positiveItems.add(offsetItem);
            } else {
                negativeItems.add(offsetItem);
            }
        });
        return group;
    }

    List<List<InputDataEO>> groupByAmtSignInputdata(List<InputDataEO> inputItems) {
        ArrayList<List<InputDataEO>> group = new ArrayList<List<InputDataEO>>();
        ArrayList positiveItems = new ArrayList();
        ArrayList negativeItems = new ArrayList();
        group.add(positiveItems);
        group.add(negativeItems);
        inputItems.forEach(inputData -> {
            double amt = inputData.getAmt();
            if (amt > 0.0) {
                positiveItems.add(inputData);
            } else {
                negativeItems.add(inputData);
            }
        });
        return group;
    }

    @Override
    public String canOffset(List<InputDataEO> inputItems, boolean realTimeOffset, AbstractUnionRule rule, String orgType) {
        this.realTimeOffsetOptionFlag = realTimeOffset;
        this.processEnv = new GcCalcEnvContextImpl(UUIDUtils.newUUIDStr());
        if (CollectionUtils.isEmpty(inputItems)) {
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.notoffsetdatamsg");
        }
        String sameGroup = this.checkSameGroup(inputItems);
        if (!StringUtils.isEmpty(sameGroup)) {
            return sameGroup;
        }
        if (!this.realTimeOffsetOptionFlag) {
            inputItems.get(0).setOrgType(orgType);
            inputItems.get(0).addFieldValue("MD_GCORGTYPE", orgType);
        }
        InputRuleFilterCondition condition = this.createRealTimeFilterConditionByItem(inputItems.get(0), realTimeOffset, false);
        GcCalcArgmentsDTO calcArgments = this.createGcCalcArgments(condition);
        if (!(this.realTimeOffsetOptionFlag || this.taskCacheService.isEntryScheme(calcArgments.getSchemeId(), calcArgments.getPeriodStr()) || this.taskCacheService.managementCanCalc(calcArgments.getSchemeId(), calcArgments.getPeriodStr(), calcArgments.getOrgId()))) {
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.notcalunitmsg") + calcArgments.getOrgId();
        }
        ((GcCalcEnvContextImpl)this.processEnv).setCalcArgments(calcArgments);
        String currencyCanOffset = this.checkCurrencyCanOffset(inputItems.get(0).getCurrency());
        if (!StringUtils.isEmpty(currencyCanOffset)) {
            return currencyCanOffset;
        }
        String fitOptions = this.checkByOptions(inputItems);
        if (!StringUtils.isEmpty(fitOptions)) {
            return fitOptions;
        }
        return this.checkAmt(inputItems);
    }

    private String checkSameGroup(List<InputDataEO> inputItems) {
        if (inputItems.size() == 1) {
            this.getRecordKey(inputItems.get(0));
            return "";
        }
        InputDataEO cmpInputItem = inputItems.get(0);
        for (int index = 1; index < inputItems.size(); ++index) {
            InputDataEO curInputItem = inputItems.get(index);
            if (!curInputItem.getReportSystemId().equals(cmpInputItem.getReportSystemId())) {
                return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.systemkeycheckmsg");
            }
            if (!curInputItem.getCurrency().equals(cmpInputItem.getCurrency())) {
                return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.currncycheckmsg");
            }
            if (!curInputItem.getPeriod().equals(cmpInputItem.getPeriod())) {
                return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.periodcheckmsg");
            }
            String deepCheck = this.checkSameGroup(curInputItem, cmpInputItem);
            if (!StringUtils.isEmpty(deepCheck)) {
                return deepCheck;
            }
            if (!this.realTimeOffsetOptionFlag || this.getUnitComb(curInputItem.getUnitId(), curInputItem.getOppUnitId()).equals(this.getUnitComb(cmpInputItem.getUnitId(), cmpInputItem.getOppUnitId()))) continue;
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.unitandoppunitcheckmsg");
        }
        return "";
    }

    private String checkSameGroup(InputDataEO inputItem1, InputDataEO inputItem2) {
        return this.getRecordKey(inputItem1).equals(this.getRecordKey(inputItem2)) ? "" : GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.nogroupcheckmsg");
    }

    protected abstract String checkAmt(List<InputDataEO> var1);

    protected String checkByOptions(Collection<InputDataEO> inputItems) {
        if (CollectionUtils.isEmpty(inputItems)) {
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.notoffsetdatamsg");
        }
        if (!CollectionUtils.isEmpty(this.getRule().getSrcCreditSubjectCodeList()) && !CollectionUtils.isEmpty(this.getRule().getSrcDebitSubjectCodeList()) && inputItems.size() == 1 && this.realTimeOffsetOptionFlag) {
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.onenotoffsetmsg");
        }
        boolean sameOrient = this.isSameOrient(inputItems);
        boolean sameUnit = this.isSameUnit(inputItems);
        if (sameOrient && !this.canUnilateralOffset()) {
            Object[] args = new String[]{this.getRule().getLocalizedName()};
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.notsupportedunilateraloffset", (Object[])args);
        }
        if (sameUnit && !sameOrient && !this.canBothDc()) {
            return GcI18nUtil.getMessage((String)"gc.calculate.inputdataruleexecutor.notsupportdcmsg");
        }
        return "";
    }

    protected abstract boolean canUnilateralOffset();

    protected abstract boolean canBothDc();
}


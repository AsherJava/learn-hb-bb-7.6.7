/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.GcCalcRuleProcessorService
 *  com.jiuqi.gcreport.calculate.task.GcCalcRuleProcessorForkJoinTask
 *  com.jiuqi.gcreport.calculate.task.callback.GcCalcRuleProcessorForkJoinTaskCallBack
 *  com.jiuqi.gcreport.calculate.util.GcCalcRuleUtils
 *  com.jiuqi.gcreport.calculate.util.PeriodUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.event.GcCalcExecuteSameOffsetItemEvent
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO
 *  com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.invest.calculate.rule.dispatcher.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.GcCalcRuleProcessorService;
import com.jiuqi.gcreport.calculate.task.GcCalcRuleProcessorForkJoinTask;
import com.jiuqi.gcreport.calculate.task.callback.GcCalcRuleProcessorForkJoinTaskCallBack;
import com.jiuqi.gcreport.calculate.util.GcCalcRuleUtils;
import com.jiuqi.gcreport.calculate.util.PeriodUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.calculate.rule.processor.AbstractGcCalcInvestBillRuleProcessor;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.invest.investbill.executor.CompreEquityRatioTask;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.event.GcCalcExecuteSameOffsetItemEvent;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcCalcInvestBillRuleDispatcherExecutor {
    private Logger logger = LoggerFactory.getLogger(GcCalcInvestBillRuleDispatcherExecutor.class);
    @Autowired
    private GcCalcRuleProcessorService ruleProcessorService;
    @Autowired
    private CompreEquityRatioTask compreEquityRatioTask;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    private static final String SQL_GET_INVESTMENT_ITEM_DATAEOS = " select %1$s \n from GC_INVESTBILLITEM ei   where %2$s \n";

    @Transactional(rollbackFor={Exception.class})
    public void execute(List<AbstractUnionRule> rules, GcCalcEnvContext env) throws Exception {
        boolean isMonthlyIncrement = this.monthlyIncrementHandle(env.getCalcArgments());
        String hbOrgId = env.getCalcArgments().getOrgId();
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = orgTool.getOrgByCode(hbOrgId);
        String baseUnitId = hbOrg.getBaseUnitId();
        if (baseUnitId == null) {
            String msg = GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.no.base.warn", (Object[])new Object[]{hbOrg.getTitle()});
            env.addResultItem(msg);
            rules.forEach(rule -> ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).setSuccessFlag(Boolean.TRUE.booleanValue()));
            return;
        }
        ArrayList<AbstractInvestmentRule> equityLawAdjustRules = new ArrayList<AbstractInvestmentRule>();
        ArrayList<AbstractInvestmentRule> notEquityLawAdjustRules = new ArrayList<AbstractInvestmentRule>();
        for (AbstractUnionRule rule2 : rules) {
            AbstractInvestmentRule investmentRule = (AbstractInvestmentRule)rule2;
            if (investmentRule.getEquityLawAdjustFlag().booleanValue()) {
                equityLawAdjustRules.add(investmentRule);
                continue;
            }
            notEquityLawAdjustRules.add(investmentRule);
        }
        List childOrgs = orgTool.getOrgChildrenTree(hbOrgId);
        if (childOrgs == null || childOrgs.size() == 0) {
            rules.stream().forEach(rule -> {
                ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).setSuccessFlag(Boolean.TRUE.booleanValue());
                String msg = String.format(GcCalcRuleUtils.getSuccessLogStr(), rule.getLocalizedName(), GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.no.direct.subordinate.warn", (Object[])new Object[]{hbOrg.getTitle()}));
                env.addResultItem(msg);
            });
            return;
        }
        Map<Boolean, List<GcInvestBillGroupDTO>> investmentDatasGroup = this.getInvestmentDatasGroupMap(env.getCalcArgments(), orgTool, hbOrg, baseUnitId, childOrgs);
        List<GcInvestBillGroupDTO> directInvestmentGroups = investmentDatasGroup.get(Boolean.TRUE);
        List<GcInvestBillGroupDTO> inDirectInvestmentGroups = investmentDatasGroup.get(Boolean.FALSE);
        try {
            this.compreEquityRatioTask.writeCompreEquityRatioValue(env.getCalcArgments(), directInvestmentGroups);
            this.compreEquityRatioTask.writeCompreEquityRatioValue(env.getCalcArgments(), inDirectInvestmentGroups);
        }
        catch (Exception e) {
            env.addResultItem(GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.compre.equity.ratio.error") + e.getMessage());
        }
        this.executeRules(env, equityLawAdjustRules, directInvestmentGroups, inDirectInvestmentGroups, isMonthlyIncrement);
        this.executeRules(env, notEquityLawAdjustRules, directInvestmentGroups, inDirectInvestmentGroups, isMonthlyIncrement);
        this.executeInvest(env, rules);
    }

    private void executeInvest(GcCalcEnvContext env, List<AbstractUnionRule> rules) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        queryParamsDTO.setTaskId(env.getCalcArgments().getTaskId());
        queryParamsDTO.setCurrency(env.getCalcArgments().getCurrency());
        queryParamsDTO.setSchemeId(env.getCalcArgments().getSchemeId());
        queryParamsDTO.setPeriodStr(env.getCalcArgments().getPeriodStr());
        queryParamsDTO.setOrgId(env.getCalcArgments().getOrgId());
        queryParamsDTO.setOrgType(env.getCalcArgments().getOrgType());
        List directInvestRuleList = rules.stream().filter(rule -> rule.getRuleType().equals(RuleTypeEnum.DIRECT_INVESTMENT.getCode())).map(AbstractUnionRule::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(directInvestRuleList)) {
            return;
        }
        queryParamsDTO.setRules(directInvestRuleList);
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcCalcExecuteSameOffsetItemEvent((Object)this, queryParamsDTO));
    }

    public Map<Boolean, List<GcInvestBillGroupDTO>> getInvestmentDatasGroupMap(GcCalcArgmentsDTO calcArgments, GcOrgCenterService instance, GcOrgCacheVO hbOrg, String baseUnitId, List<GcOrgCacheVO> childOrgs) {
        GcCalcArgmentsDTO argmentsDTO = new GcCalcArgmentsDTO();
        BeanUtils.copyProperties(calcArgments, argmentsDTO);
        Set<String> baseUnitAllSubContainsSelf = this.listAllOrgByParentIdContainsSelf(instance, baseUnitId);
        List<String> directUnitIds = childOrgs.stream().map(childOrg -> {
            List childs = childOrg.getChildren();
            if (!CollectionUtils.isEmpty((Collection)childs)) {
                String childBaseUnitid = instance.getDeepestBaseUnitId(childOrg.getId());
                if (childBaseUnitid == null) {
                    return childOrg.getId();
                }
                return childBaseUnitid;
            }
            return childOrg.getId();
        }).filter(childOrgid -> childOrgid != null).collect(Collectors.toList());
        List<GcInvestBillGroupDTO> investmentGroups = this.getAllInvestmentGroups(argmentsDTO, hbOrg, baseUnitAllSubContainsSelf);
        Function<GcInvestBillGroupDTO, Boolean> groupFunction = this.getGroupFunction(baseUnitAllSubContainsSelf, directUnitIds);
        return investmentGroups.stream().collect(Collectors.groupingBy(groupFunction));
    }

    private List<GcInvestBillGroupDTO> getAllInvestmentGroups(GcCalcArgmentsDTO argmentsDTO, GcOrgCacheVO hbOrg, Set<String> baseUnitAllSubContainsSelf) {
        List sameCtrlChgOrgEOList;
        String priorPeriodStr = this.getPriorPeriod(argmentsDTO.getPeriodStr());
        YearPeriodObject yp = new YearPeriodObject(null, argmentsDTO.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)argmentsDTO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        List<GcInvestBillGroupDTO> investmentGroups = this.getInvestmentGroups(argmentsDTO, hbOrg, true);
        if (CollectionUtils.isEmpty(investmentGroups)) {
            investmentGroups = new ArrayList<GcInvestBillGroupDTO>();
        }
        if (CollectionUtils.isEmpty(sameCtrlChgOrgEOList = Collections.emptyList())) {
            return investmentGroups;
        }
        argmentsDTO.setPeriodStr(priorPeriodStr);
        List<GcInvestBillGroupDTO> priorInvestmentGroups = this.getInvestmentGroups(argmentsDTO, hbOrg, true);
        Map mastId2InvestBillMap = investmentGroups.stream().collect(Collectors.toMap(item -> item.getMaster().getId(), Function.identity()));
        Map<String, SameCtrlChgOrgEO> unit2SamCtrlChgOrgMap = sameCtrlChgOrgEOList.stream().collect(Collectors.toMap(SameCtrlChgOrgEO::getChangedCode, Function.identity(), (v1, v2) -> v2));
        for (GcInvestBillGroupDTO dto : priorInvestmentGroups) {
            int currentMonth;
            GcOrgCacheVO investedHbOrg;
            GcOrgCacheVO investHbOrg;
            GcOrgCacheVO firstSameParentOrg;
            SameCtrlChgOrgEO sameCtrlChgOrgEO;
            String investUnit = String.valueOf(dto.getMaster().getFieldValue("UNITCODE"));
            String investedUnit = String.valueOf(dto.getMaster().getFieldValue("INVESTEDUNIT"));
            if (!baseUnitAllSubContainsSelf.contains(investUnit) || (sameCtrlChgOrgEO = this.getVirtualUnit(unit2SamCtrlChgOrgMap, investedUnit)) == null || (firstSameParentOrg = orgTool.getCommonUnit(investHbOrg = orgTool.getOrgByCode(investUnit), investedHbOrg = orgTool.getOrgByCode(sameCtrlChgOrgEO.getVirtualCode()))) == null || !firstSameParentOrg.getCode().equals(hbOrg.getCode())) continue;
            dto.getMaster().addFieldValue("VIRTUALUNIT", (Object)sameCtrlChgOrgEO);
            if (!mastId2InvestBillMap.containsKey(dto.getMaster().getId())) {
                mastId2InvestBillMap.put(dto.getMaster().getId(), dto);
                continue;
            }
            int changeMonth = DateUtils.getDateFieldValue((Date)sameCtrlChgOrgEO.getChangeDate(), (int)2);
            if (changeMonth > (currentMonth = DateUtils.getDateFieldValue((Date)yp.formatYP().getEndDate(), (int)2))) continue;
            mastId2InvestBillMap.put(dto.getMaster().getId(), dto);
        }
        return new ArrayList<GcInvestBillGroupDTO>(mastId2InvestBillMap.values());
    }

    private SameCtrlChgOrgEO getVirtualUnit(Map<String, SameCtrlChgOrgEO> unit2SamCtrlChgOrgMap, String investedUnit) {
        if (StringUtils.isEmpty((String)investedUnit)) {
            return null;
        }
        return unit2SamCtrlChgOrgMap.get(investedUnit);
    }

    private String getPriorPeriod(String periodStr) {
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        defaultPeriodAdapter.priorPeriod(periodWrapper);
        return periodWrapper.toString();
    }

    private boolean monthlyIncrementHandle(GcCalcArgmentsDTO paramsVO) {
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        if (reportSystemId == null) {
            return false;
        }
        ConsolidatedOptionVO conOptionBySystemId = this.optionService.getOptionData(reportSystemId);
        return conOptionBySystemId != null && conOptionBySystemId.getMonthlyIncrement() != false;
    }

    private Set<String> listAllOrgByParentIdContainsSelf(GcOrgCenterService orgCenterService, String orgId) {
        HashSet<String> baseUnitSubAllContainSelf = new HashSet<String>();
        baseUnitSubAllContainSelf.add(orgId);
        GcOrgCacheVO hbOrg = orgCenterService.getOrgByCode(orgId);
        if (null == hbOrg || hbOrg.isLeaf()) {
            return baseUnitSubAllContainSelf;
        }
        List baseUnitAllSubContainSelfList = orgCenterService.listAllOrgByParentIdContainsSelf(hbOrg.getId());
        if (!CollectionUtils.isEmpty((Collection)baseUnitAllSubContainSelfList)) {
            for (GcOrgCacheVO gcOrgCacheVO : baseUnitAllSubContainSelfList) {
                baseUnitSubAllContainSelf.add(gcOrgCacheVO.getId());
            }
        }
        return baseUnitSubAllContainSelf;
    }

    private Function<GcInvestBillGroupDTO, Boolean> getGroupFunction(final Set<String> baseUnitAllSubContainsSelf, final List<String> directChildBaseUnitIds) {
        Function<GcInvestBillGroupDTO, Boolean> groupFunction = new Function<GcInvestBillGroupDTO, Boolean>(){

            @Override
            public Boolean apply(GcInvestBillGroupDTO investmentEO) {
                String investUnit = String.valueOf(investmentEO.getMaster().getFieldValue("UNITCODE"));
                String investedUnit = String.valueOf(investmentEO.getMaster().getFieldValue("INVESTEDUNIT"));
                Object sameCtrlChgOrgEO = investmentEO.getMaster().getFieldValue("VIRTUALUNIT");
                if (sameCtrlChgOrgEO != null) {
                    SameCtrlChgOrgEO sameCtrlChgOrg = (SameCtrlChgOrgEO)sameCtrlChgOrgEO;
                    investedUnit = sameCtrlChgOrg.getVirtualCode();
                }
                if (!baseUnitAllSubContainsSelf.contains(investUnit)) {
                    return Boolean.FALSE;
                }
                String finalInvestedUnit = investedUnit;
                boolean isMatch = directChildBaseUnitIds.stream().anyMatch(directUnitId -> directUnitId.equals(finalInvestedUnit));
                return isMatch;
            }
        };
        return groupFunction;
    }

    private void executeRules(final GcCalcEnvContext env, List<AbstractInvestmentRule> rules, final List<GcInvestBillGroupDTO> directInvestmentGroups, final List<GcInvestBillGroupDTO> inDirectInvestmentGroups, final boolean isMonthlyIncrement) throws Exception {
        if (rules == null || rules.size() == 0) {
            return;
        }
        GcCalcRuleProcessorForkJoinTask forkJoinTask = new GcCalcRuleProcessorForkJoinTask(env, rules, (GcCalcRuleProcessorForkJoinTaskCallBack)new GcCalcRuleProcessorForkJoinTaskCallBack<AbstractInvestmentRule>(){

            public void run(AbstractInvestmentRule unionRule) {
                GcCalcInvestBillRuleDispatcherExecutor.this.executeRuleProcessor(env, directInvestmentGroups, inDirectInvestmentGroups, unionRule, isMonthlyIncrement);
            }
        });
        ForkJoinTask submit = GcCalcRuleUtils.getRuleProcessorForkJoinPool().submit(forkJoinTask);
        this.logger.debug("\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u5408\u5e76\u89c4\u5219\u6807\u9898\u4fe1\u606f\uff1a" + (String)submit.get());
    }

    private void executeRuleProcessor(GcCalcEnvContext env, List<GcInvestBillGroupDTO> directInvestmentGroups, List<GcInvestBillGroupDTO> inDirectInvestmentGroups, AbstractInvestmentRule rule, boolean isMonthlyIncrement) {
        this.processorRule(env, rule, directInvestmentGroups, inDirectInvestmentGroups, isMonthlyIncrement);
    }

    private void processorRule(GcCalcEnvContext env, AbstractInvestmentRule rule, List<GcInvestBillGroupDTO> directInvestmentGroups, List<GcInvestBillGroupDTO> inDirectInvestmentGroups, boolean isMonthlyIncrement) {
        String ruleType;
        AbstractGcCalcInvestBillRuleProcessor calcRuleProcessor = (AbstractGcCalcInvestBillRuleProcessor)this.ruleProcessorService.findRuleProcessorByRule((AbstractUnionRule)rule, env);
        List<GcInvestBillGroupDTO> datas = null;
        RuleTypeEnum ruleTypeEnum = RuleTypeEnum.codeOf((String)rule.getRuleType());
        switch (ruleTypeEnum) {
            case DIRECT_INVESTMENT: {
                datas = directInvestmentGroups;
                ruleType = GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.direct.investment");
                break;
            }
            case INDIRECT_INVESTMENT: {
                datas = inDirectInvestmentGroups;
                ruleType = GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.indirect.investment");
                break;
            }
            default: {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.not.exist.type", (Object[])new Object[]{rule.getRuleType()}));
            }
        }
        if (CollectionUtils.isEmpty(datas)) {
            ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).setSuccessFlag(Boolean.TRUE.booleanValue());
            String msg = String.format(GcCalcRuleUtils.getSuccessLogStr(), rule.getLocalizedName(), GcI18nUtil.getMessage((String)"gc.calculate.bill.invest.rule.no.data.info", (Object[])new Object[]{ruleType}));
            env.addResultItem(msg);
        }
        calcRuleProcessor.processor(rule, env, datas, isMonthlyIncrement);
    }

    private List<GcInvestBillGroupDTO> getInvestmentGroups(GcCalcArgmentsDTO calcArgments, GcOrgCacheVO hbOrg, boolean isExistSegmentRule) {
        List<DefaultTableEntity> investmentItemDatas;
        List<DefaultTableEntity> investmentDatas = this.getInvestmentDatas(calcArgments, hbOrg);
        if (investmentDatas == null || investmentDatas.size() == 0) {
            return Collections.emptyList();
        }
        Map<String, DefaultTableEntity> investmentMap = investmentDatas.stream().collect(Collectors.toMap(DefaultTableEntity::getId, GcInvestBillEO -> GcInvestBillEO));
        Map<String, List<DefaultTableEntity>> investmentItemMap = null;
        if (isExistSegmentRule && (investmentItemDatas = this.getInvestmentItemDatas(investmentMap.keySet())) != null && investmentItemDatas.size() > 0) {
            investmentItemMap = investmentItemDatas.stream().collect(Collectors.groupingBy(o -> String.valueOf(o.getFieldValue("MASTERID"))));
        }
        Map<String, List<DefaultTableEntity>> finalInvestmentItemMap = investmentItemMap;
        ArrayList<GcInvestBillGroupDTO> groups = new ArrayList<GcInvestBillGroupDTO>();
        investmentMap.entrySet().stream().forEach(entry -> {
            String id = (String)entry.getKey();
            DefaultTableEntity master = (DefaultTableEntity)entry.getValue();
            List items = null;
            if (finalInvestmentItemMap != null) {
                items = (List)finalInvestmentItemMap.get(id);
            }
            GcInvestBillGroupDTO gcInvestmentGroup = new GcInvestBillGroupDTO(master, items, true);
            groups.add(gcInvestmentGroup);
        });
        return groups;
    }

    private List<DefaultTableEntity> getInvestmentDatas(GcCalcArgmentsDTO calcArgments, GcOrgCacheVO hbOrg) {
        if (calcArgments.getPreCalcFlag().get()) {
            String billCode = (String)calcArgments.getExtendInfo().get("BILLCODE");
            DefaultTableEntity investBillEntity = InvestBillTool.getEntityByBillCode((String)billCode, (String)"GC_INVESTBILL");
            return Arrays.asList(investBillEntity);
        }
        int period = PeriodUtils.getMonth((int)calcArgments.getAcctYear(), (int)calcArgments.getPeriodType(), (int)calcArgments.getAcctPeriod());
        int len = GcOrgPublicTool.getInstance().getOrgCodeLength();
        String SQL_GET_INVESTMENT_DATAEOS = " select %1$s \n from GC_INVESTBILL e \n join %5$s investunit on e.unitCode = investunit.code \n join %5$s investedunit on e.investedunit = investedunit.code \n where substr(investunit.gcparents, %2$s, " + len + ") <> substr(investedunit.gcparents, %2$s, " + len + ") \n and substr(investunit.parents, 1, %3$s)  = '%4$s'\n and substr(investedunit.parents, 1, %3$s)  = '%4$s' \n and investedunit.validtime <= ? and investedunit.invalidtime > ?  \n and investunit.validtime <= ? and investunit.invalidtime > ?  \n and %6$s";
        String hbUnitParents = hbOrg.getParentStr();
        int gcUnitParentsStartIndex = hbOrg.getGcParentStr().length() + 2;
        int gcUnitParentsEndIndex = hbUnitParents.length();
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILL", (String)"e");
        String orgTypeCond = "e.acctYear=" + calcArgments.getAcctYear() + " and e.PERIOD=" + period;
        String formatSQL = String.format(SQL_GET_INVESTMENT_DATAEOS, columns, gcUnitParentsStartIndex, gcUnitParentsEndIndex, hbUnitParents, calcArgments.getOrgType(), orgTypeCond);
        YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
        Date orgvalidate = yp.formatYP().getEndDate();
        List inputDataEOs = InvestBillTool.queryBySql((String)formatSQL, (Object[])new Object[]{orgvalidate, orgvalidate, orgvalidate, orgvalidate});
        return inputDataEOs;
    }

    private List<DefaultTableEntity> getInvestmentItemDatas(Set<String> investmentIds) {
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)"GC_INVESTBILLITEM", (String)"ei");
        String condiSql = SqlUtils.getConditionOfIdsUseOr(investmentIds, (String)"ei.masterId");
        String formatSQL = String.format(SQL_GET_INVESTMENT_ITEM_DATAEOS, columns, condiSql);
        return InvestBillTool.queryBySql((String)formatSQL, (Object[])new Object[0]);
    }
}


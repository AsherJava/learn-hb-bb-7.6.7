/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.event.GcCalcCheckBeforeExecuteInitOffsetItemEvent
 *  com.jiuqi.gcreport.calculate.event.GcCalcExecuteInitOffsetItemEvent
 *  com.jiuqi.gcreport.calculate.event.GcCalcPrepareDatasEvent
 *  com.jiuqi.gcreport.calculate.vo.GcCalcArgmentsVO
 *  com.jiuqi.gcreport.calculate.vo.GcCalcLogVO
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.common.tool.web.DebugParam
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.util.UnionRuleUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  javax.validation.Valid
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.common.GcCalcSortAndRegroupRuleMap;
import com.jiuqi.gcreport.calculate.common.GcConcurrentLogRuntimeException;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.dto.GcCalcRuleExecuteStateDTO;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.event.GcCalcCheckBeforeExecuteInitOffsetItemEvent;
import com.jiuqi.gcreport.calculate.event.GcCalcExecuteInitOffsetItemEvent;
import com.jiuqi.gcreport.calculate.event.GcCalcPrepareDatasEvent;
import com.jiuqi.gcreport.calculate.processor.CalcManagementProcessor;
import com.jiuqi.gcreport.calculate.rule.dispatcher.GcCalcRuleDispatcher;
import com.jiuqi.gcreport.calculate.rule.dispatcher.enums.GcCalcRuleDispatcherPriorityEnum;
import com.jiuqi.gcreport.calculate.service.AssetsBillDepreItemCalcCache;
import com.jiuqi.gcreport.calculate.service.GcCalcLogService;
import com.jiuqi.gcreport.calculate.service.GcCalcRuleDispatcherService;
import com.jiuqi.gcreport.calculate.service.GcCalcService;
import com.jiuqi.gcreport.calculate.service.InvestBillInitialMergeCalcCache;
import com.jiuqi.gcreport.calculate.task.GcCalcRuleDispatcherForkJoinTask;
import com.jiuqi.gcreport.calculate.util.GcCalcRuleUtils;
import com.jiuqi.gcreport.calculate.vo.GcCalcArgmentsVO;
import com.jiuqi.gcreport.calculate.vo.GcCalcLogVO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.common.tool.web.DebugParam;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class GcCalcServiceImpl
implements GcCalcService {
    private Logger logger = LoggerFactory.getLogger(GcCalcServiceImpl.class);
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private GcCalcRuleDispatcherService ruleDispatcherService;
    @Autowired
    private ProgressService<GcCalcEnvContextImpl, List<String>> progressService;
    @Autowired
    private GcCalcLogService calcLogService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired(required=false)
    private InvestBillInitialMergeCalcCache investBillInitialMergeCalcCache;
    @Autowired(required=false)
    private AssetsBillDepreItemCalcCache assetsBillDepreItemCalcCache;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public GcCalcEnvContext calc(GcCalcEnvContextImpl env) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        env.setProgressValueAndRefresh(0.05);
        GcCalcLogEO beginCalcLogEO = null;
        try {
            beginCalcLogEO = this.insertCalcLog(calcArgments);
            boolean isValid = this.checkCalcValid(env);
            if (!isValid) {
                GcCalcEnvContextImpl gcCalcEnvContextImpl = env;
                return gcCalcEnvContextImpl;
            }
            GcCalcEnvContext gcCalcEnvContext = this.executeCalc(env);
            return gcCalcEnvContext;
        }
        catch (GcConcurrentLogRuntimeException e) {
            this.logger.error(e.getMessage(), e);
            env.addResultItem(e.getMessage());
            env.setSuccessFlagAndRefresh(false);
            GcOrgCacheVO org = this.getOrg(env);
            ContextUser user = NpContextHolder.getContext().getUser();
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.calc.service.lock.msg", (Object[])new Object[]{org.getId(), org.getTitle(), StringUtils.isEmpty((String)user.getFullname()) ? user.getName() : user.getFullname()}), (Throwable)e);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            env.addResultItem(e.getMessage());
            env.setSuccessFlagAndRefresh(false);
            String errorMsg = e.getMessage();
            if (StringUtils.isEmpty((String)errorMsg)) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter, true);
                e.printStackTrace(printWriter);
                printWriter.flush();
                stringWriter.flush();
                errorMsg = GcI18nUtil.getMessage((String)"gc.calculate.calc.service.exception") + stringWriter.toString();
            }
            throw new BusinessRuntimeException(errorMsg, (Throwable)e);
        }
        finally {
            env.addResultItem(GcI18nUtil.getMessage((String)"gc.calculate.calc.service.endTime") + DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
            env.setProgressValueAndRefresh(1.0);
            this.updateCalcLog(env, beginCalcLogEO);
        }
    }

    private void updateCalcLog(GcCalcEnvContextImpl env, GcCalcLogEO beginCalcLogEO) {
        if (env.getCalcArgments().getPreCalcFlag().get()) {
            return;
        }
        if (beginCalcLogEO != null) {
            String loginfo = ((List)env.getResult()).stream().map(Object::toString).collect(Collectors.joining("\n"));
            this.calcLogService.updateCalcLogEO(beginCalcLogEO.getId(), loginfo, env.isSuccessFlag() ? TaskStateEnum.SUCCESS : TaskStateEnum.ERROR);
        }
        this.calcLogService.startCalcLog(env);
    }

    private GcCalcLogEO insertCalcLog(GcCalcArgmentsDTO calcArgments) {
        if (calcArgments.getPreCalcFlag().get()) {
            return null;
        }
        GcCalcLogEO beginCalcLogEO = this.calcLogService.insertCalcLogEO(300000L, GcCalcLogOperateEnum.STATR_CALC, calcArgments.getTaskId(), calcArgments.getCurrency(), calcArgments.getPeriodStr(), calcArgments.getOrgType(), calcArgments.getOrgId(), calcArgments.getSelectAdjustCode());
        return beginCalcLogEO;
    }

    @Override
    public GcCalcEnvContext calc(GcCalcArgmentsDTO calcArgments) {
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl(calcArgments.getSn());
        env.setCalcArgments(calcArgments);
        GcCalcEnvContext envContext = this.calc(env);
        return envContext;
    }

    private boolean checkCalcValid(GcCalcEnvContextImpl env) {
        boolean isCheckValid = this.preCalc(env);
        return isCheckValid;
    }

    private CheckResult checkUpload(GcCalcEnvContextImpl env) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        ReadWriteAccessDesc writeAbleInfo = new UploadStateTool().writeable((Object)calcArgments, calcArgments.getOrgId());
        if (!Boolean.TRUE.equals(writeAbleInfo.getAble())) {
            env.addResultItem(writeAbleInfo.getDesc());
            return CheckResult.newInstance(false, true);
        }
        return CheckResult.newSucessInstance();
    }

    private CheckResult checkOrg(GcCalcEnvContextImpl env) {
        GcOrgCacheVO org = this.getOrg(env);
        if (!GcOrgKindEnum.UNIONORG.equals((Object)org.getOrgKind())) {
            env.addResultItem(GcI18nUtil.getMessage((String)"gc.calculate.calc.service.checkOrg.exception"));
            return CheckResult.newInstance(false, true);
        }
        return CheckResult.newSucessInstance();
    }

    private GcOrgCacheVO getOrg(GcCalcEnvContextImpl env) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(calcArgments.getOrgId());
        if (org == null) {
            this.logger.error("\u83b7\u53d6\u4e0d\u5230{}\u5408\u5e76\u5355\u4f4d, \u5355\u4f4d\u7c7b\u578b\uff1a{}, \u65f6\u671f\uff1a{}", calcArgments.getOrgId(), calcArgments.getOrgType(), calcArgments.getPeriodStr());
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5408\u5e76\u5355\u4f4d[" + calcArgments.getOrgId() + "]\u3002");
        }
        return org;
    }

    private boolean preCalc(GcCalcEnvContextImpl env) {
        CheckResult checkResult = this.checkArgments(env);
        if (!checkResult.isSuccess()) {
            this.progressService.createProgressData((ProgressData)env);
            env.setSuccessFlag(checkResult.isProcessState());
            env.setProgressValueAndRefresh(1.0);
            return false;
        }
        this.progressService.createProgressData((ProgressData)env);
        env.setProgressValueAndRefresh(0.05);
        return true;
    }

    private GcCalcEnvContext executeCalc(GcCalcEnvContextImpl env) {
        try {
            this.executeCorporateCalc(env);
        }
        finally {
            env.addResultItem(GcI18nUtil.getMessage((String)"gc.calculate.calc.service.finish"));
            env.setProgressValueAndRefresh(1.0);
        }
        return env;
    }

    private GcCalcEnvContext executeManagementCalc(GcCalcEnvContextImpl env) {
        CalcManagementProcessor managementProcessor = new CalcManagementProcessor(env);
        return managementProcessor.executeCalc();
    }

    private GcCalcEnvContext executeCorporateCalc(GcCalcEnvContextImpl env) {
        List<AbstractUnionRule> rules = this.getAllMatchRules((GcCalcEnvContext)env);
        if (rules == null || rules.size() == 0) {
            return env;
        }
        this.initCalcContextExpandVariableCenter(env);
        env.setProgressValue(0.1);
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcCalcPrepareDatasEvent((Object)this, (GcCalcEnvContext)env, rules));
        env.setProgressValueAndRefresh(0.2);
        this.preGenerateRuleId2MRecids(rules, (GcCalcEnvContext)env);
        this.executeInitToAdjustOffSetItem(env);
        env.setProgressValue(0.5);
        this.executeRuleCalc(env, rules);
        boolean successFlag = !env.getRuleStateMap().values().stream().anyMatch(ruleState -> {
            boolean isFail;
            if (ruleState.getSuccessFlag() == null) {
                AbstractUnionRule rule = ruleState.getRule();
                ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).setSuccessFlag(Boolean.FALSE.booleanValue());
                String errMsg = String.format(GcCalcRuleUtils.getExceptionLogStr(), rule.getLocalizedName(), GcI18nUtil.getMessage((String)"gc.calculate.calc.service.rule.exception"));
                env.addResultItem(errMsg);
            }
            boolean bl = isFail = ruleState.getSuccessFlag() == null || ruleState.getSuccessFlag() == false;
            if (isFail) {
                this.logger.warn("\u89c4\u5219[" + ruleState.getRule().getLocalizedName() + "]\u6267\u884c\u5931\u8d25\u3002");
            }
            return isFail;
        });
        env.setSuccessFlagAndRefresh(successFlag);
        return env;
    }

    private void preGenerateRuleId2MRecids(@NotNull List<AbstractUnionRule> rules, GcCalcEnvContext env) {
        ArrayList ruleIds = new ArrayList();
        rules.stream().forEach(rule -> {
            RuleTypeEnum ruleType = RuleTypeEnum.codeOf((String)rule.getRuleType());
            if (!(RuleTypeEnum.DIRECT_INVESTMENT.equals((Object)ruleType) || RuleTypeEnum.INDIRECT_INVESTMENT.equals((Object)ruleType) || RuleTypeEnum.INDIRECT_INVESTMENT_SEGMENT.equals((Object)ruleType) || RuleTypeEnum.DIRECT_INVESTMENT_SEGMENT.equals((Object)ruleType) || RuleTypeEnum.FIXED_ASSETS.equals((Object)ruleType))) {
                return;
            }
            ruleIds.add(rule.getId());
        });
        Collections.reverse(ruleIds);
        ruleIds.stream().forEach(ruleId -> env.getCalcContextExpandVariableCenter().preGenerateRuleId2MRecids(env, ruleId, 500L));
    }

    private void executeInitToAdjustOffSetItem(GcCalcEnvContextImpl env) {
        if (!DebugParam.isEnableCopyInitOffset() || env.getCalcArgments().getDisabledCopyInitOffset().get()) {
            return;
        }
        try {
            this.checkBeforeExecuteInitOffsetItem(env);
        }
        catch (Exception e) {
            this.logger.info(e.getMessage());
            return;
        }
        this.executeInitOffsetItem(env);
    }

    private void checkBeforeExecuteInitOffsetItem(GcCalcEnvContextImpl env) {
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcCalcCheckBeforeExecuteInitOffsetItemEvent((Object)this, (GcCalcEnvContext)env));
    }

    private void executeInitOffsetItem(GcCalcEnvContextImpl env) {
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcCalcExecuteInitOffsetItemEvent((Object)this, (GcCalcEnvContext)env));
        if (env.getCalcContextExpandVariableCenter().getExistsDisposedLedgers().get()) {
            ((List)env.getResult()).add(GcI18nUtil.getMessage((String)"gc.calculate.calc.log.copy.init.to.curr.info"));
        }
    }

    private Set<String> getUnits(String comUnitId, GcOrgCenterService tool, Set<String> filterUnitSet) {
        HashSet<String> unitIds = new HashSet<String>();
        GcOrgCacheVO orgByCode = tool.getOrgByCode(comUnitId);
        if (orgByCode == null) {
            return unitIds;
        }
        List childrenOrg = tool.getOrgChildrenTree(comUnitId);
        if (CollectionUtils.isEmpty((Collection)childrenOrg)) {
            return unitIds;
        }
        for (GcOrgCacheVO org : childrenOrg) {
            String needAddUnitId;
            String baseUnitId = org.isLeaf() ? null : tool.getDeepestBaseUnitId(org.getId());
            String string = needAddUnitId = baseUnitId != null ? baseUnitId : org.getId();
            if (filterUnitSet != null && filterUnitSet.contains(needAddUnitId)) continue;
            unitIds.add(needAddUnitId);
        }
        return unitIds;
    }

    private Set<String> getChangeUnitSet(GcCalcArgmentsDTO calcArgments, GcOrgCenterService tool) {
        PeriodWrapper periodWrapper = new PeriodWrapper(calcArgments.getPeriodStr());
        int period = periodWrapper.getPeriod();
        Set<String> currentUnitSet = this.getUnits(calcArgments.getOrgId(), tool, null);
        HashSet<String> changeUnitSet = new HashSet<String>();
        for (int i = period - 1; i > 0; --i) {
            String tempPeriod = this.getPriorPeriod(calcArgments.getPeriodStr());
            YearPeriodObject yp = new YearPeriodObject(null, tempPeriod);
            GcOrgCenterService priorTool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            Set<String> units = this.getUnits(calcArgments.getOrgId(), priorTool, currentUnitSet);
            changeUnitSet.addAll(units);
            currentUnitSet.addAll(units);
        }
        if (changeUnitSet.isEmpty()) {
            return null;
        }
        return changeUnitSet;
    }

    private String getPriorPeriod(String periodStr) {
        PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        defaultPeriodAdapter.priorPeriod(periodWrapper);
        return periodWrapper.toString();
    }

    private void initCalcContextExpandVariableCenter(GcCalcEnvContextImpl env) {
        String systemId = this.taskService.getConsolidatedSystemIdBySchemeId(env.getCalcArgments().getSchemeId(), env.getCalcArgments().getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.calc.service.systemId.isnull.error"));
        }
        env.getCalcContextExpandVariableCenter().setNpContext(NpContextHolder.getContext());
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        env.getCalcContextExpandVariableCenter().setOrgCenterService(orgCenterService);
    }

    private void executeRuleCalc(GcCalcEnvContextImpl env, List<AbstractUnionRule> rules) {
        GcCalcSortAndRegroupRuleMap sortAndRegroupRuleMap = this.ruleDispatcherService.sortAndRegroupRuleDispatchers((GcCalcEnvContext)env, rules);
        Map<GcCalcRuleDispatcherPriorityEnum, HashSet<GcCalcRuleDispatcher>> sortDispatcherPriorityMap = sortAndRegroupRuleMap.getSortDispatcherPriorityMap();
        env.setProgressValueAndRefresh(0.4);
        if (sortDispatcherPriorityMap.size() == 0) {
            env.setProgressValueAndRefresh(1.0);
            return;
        }
        env.addResultItem(GcI18nUtil.getMessage((String)"gc.calculate.calc.service.start"));
        double ruleStepProgress = new BigDecimal(((double)0.95f - env.getProgressValue()) / (double)rules.size()).setScale(6, 1).doubleValue();
        env.setRuleStepProgress(ruleStepProgress);
        NpContext context = NpContextHolder.getContext();
        sortDispatcherPriorityMap.forEach((dispatcherPriority, ruleDispatchers) -> {
            List calcRuleDispatchers = ruleDispatchers.stream().collect(Collectors.toList());
            GcCalcRuleDispatcherForkJoinTask<GcCalcRuleDispatcher> forkJoinTask = new GcCalcRuleDispatcherForkJoinTask<GcCalcRuleDispatcher>((GcCalcEnvContext)env, calcRuleDispatchers, ruleDispatcher -> {
                try {
                    NpContextHolder.setContext((NpContext)context);
                    env.setProgressValueAndRefresh(env.getProgressValue());
                    this.executeDispatch(env, sortAndRegroupRuleMap, ruleDispatcher);
                }
                finally {
                    NpContextHolder.clearContext();
                }
            });
            try {
                ForkJoinTask<Integer> submit = GcCalcRuleUtils.getRuleDispatcherForkJoinPool().submit(forkJoinTask);
                this.logger.debug("\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u5206\u53d1\u5668\u6570\u91cf\uff1a" + submit.get().toString());
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private CheckResult checkArgments(GcCalcEnvContextImpl env) {
        CheckResult checkResult = this.checkAndFillCalcArgments(env);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        checkResult = this.checkOrg(env);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        checkResult = this.checkUpload(env);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        return checkResult;
    }

    private CheckResult checkAndFillCalcArgments(GcCalcEnvContextImpl env) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        if (calcArgments.getSn() == null) {
            calcArgments.setSn(UUID.randomUUID().toString());
        }
        calcArgments.setSelectAdjustCode(calcArgments.getSelectAdjustCode());
        try {
            Objects.requireNonNull(calcArgments.getOrgType(), GcI18nUtil.getMessage((String)"gc.calculate.calc.service.orgType.isnull.error"));
        }
        catch (Exception e) {
            return CheckResult.newInstance(false, false);
        }
        try {
            GcBaseData currencyData;
            if (calcArgments.getCurrency() != null && (currencyData = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", calcArgments.getCurrency())) != null) {
                calcArgments.setCurrency(currencyData.getCode());
            }
        }
        catch (Exception e) {
            StringBuilder builder = new StringBuilder();
            builder.append(GcI18nUtil.getMessage((String)"gc.calculate.calc.service.currency.error", (Object[])new Object[]{calcArgments.getCurrency()}));
            env.addResultItem(builder.toString());
            return CheckResult.newInstance(false, false);
        }
        ConsolidatedTaskVO taskOption = this.taskService.getTaskBySchemeId(calcArgments.getSchemeId(), calcArgments.getPeriodStr());
        Boolean enableCalc = taskOption.getEnableCalc();
        if (!enableCalc.booleanValue()) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.calc.service.enableCalc.error"));
        }
        return CheckResult.newSucessInstance();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeDispatch(GcCalcEnvContextImpl env, GcCalcSortAndRegroupRuleMap sortAndRegroupRuleMap, GcCalcRuleDispatcher ruleDispatcher) {
        List<AbstractUnionRule> rules = sortAndRegroupRuleMap.getRulesByDispatcher(ruleDispatcher);
        try {
            ruleDispatcher.dispatch(rules, (GcCalcEnvContext)env);
        }
        catch (Exception e) {
            this.logger.error("\u5408\u5e76\u8ba1\u7b97\u5206\u53d1\u5668\u6267\u884c\u53d1\u751f\u5f02\u5e38", e);
            rules.stream().forEach(rule -> {
                Boolean successFlag = ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).getSuccessFlag();
                if (successFlag == null) {
                    String errMsg = String.format(GcCalcRuleUtils.getExceptionLogStr(), rule.getLocalizedName(), e.getMessage());
                    env.addResultItem(errMsg);
                    ((GcCalcRuleExecuteStateDTO)env.getRuleStateMap().get(rule.getId())).setSuccessFlag(Boolean.FALSE.booleanValue());
                }
            });
            env.addResultItem(e.getMessage());
        }
        finally {
            if (this.investBillInitialMergeCalcCache != null) {
                this.investBillInitialMergeCalcCache.removeCacheBySn(env.getCalcArgments().getSn());
            }
            if (this.assetsBillDepreItemCalcCache != null) {
                this.assetsBillDepreItemCalcCache.removeCacheBySn(env.getCalcArgments().getSn());
            }
        }
    }

    public List<AbstractUnionRule> getAllMatchRules(GcCalcEnvContext env) {
        List ruleIds = env.getCalcArgments().getRuleIds();
        List rules = UnionRuleUtils.getAbstractUnionRuleByIdList((List)ruleIds);
        if (CollectionUtils.isEmpty((Collection)rules)) {
            return null;
        }
        rules.sort(Comparator.comparing(o -> ruleIds.indexOf(o.getId())));
        int size = rules.size();
        StringBuilder ruleLog = new StringBuilder(String.format("\u7cfb\u7edf\u5f00\u59cb\u6267\u884c\u5408\u5e76\u8ba1\u7b97\u6279\u6b21\u53f7[%s]\uff0c\u5171[%s]\u4e2a\u5408\u5e76\u89c4\u5219\uff0c\u5206\u522b\u4e3a\uff1a", env.getCalcArgments().getSn(), size));
        List<AbstractUnionRule> finalRules = Collections.unmodifiableList(Collections.synchronizedList(rules));
        for (int i = 0; i < finalRules.size(); ++i) {
            AbstractUnionRule finalRule = (AbstractUnionRule)finalRules.get(i);
            env.getRuleStateMap().put(finalRule.getId(), new GcCalcRuleExecuteStateDTO(finalRule));
            ruleLog.append(String.format(" \n %s. [\u4ee3\u7801\uff1a%s\uff0c\u540d\u79f0\uff1a%s\uff0c\u7c7b\u578b\uff1a%s]", i + 1, finalRule.getRuleCode(), finalRule.getLocalizedName(), finalRule.getRuleTypeDescription()));
        }
        this.logger.info(ruleLog.toString());
        return finalRules;
    }

    @Override
    public GcCalcArgmentsDTO convertVO2DTO(@Valid GcCalcArgmentsVO calcVo) {
        GcCalcArgmentsDTO gcCalcDTO = new GcCalcArgmentsDTO();
        BeanUtils.copyProperties(calcVo, gcCalcDTO);
        return gcCalcDTO;
    }

    @Override
    public GcCalcLogVO findCalcLogVo(GcCalcArgmentsVO calcArgmentsVO) {
        GcCalcLogEO calcLogEO = this.calcLogService.queryCurrOrgLatestCalcLogEO(GcCalcLogOperateEnum.STATR_CALC, calcArgmentsVO.getTaskId(), calcArgmentsVO.getOrgId(), calcArgmentsVO.getCurrency(), calcArgmentsVO.getPeriodStr(), calcArgmentsVO.getSelectAdjustCode());
        GcCalcLogEO completeLogEO = this.calcLogService.queryCurrOrgLatestCalcLogEO(GcCalcLogOperateEnum.COMPLETE_CALC, calcArgmentsVO.getTaskId(), calcArgmentsVO.getOrgId(), calcArgmentsVO.getCurrency(), calcArgmentsVO.getPeriodStr(), calcArgmentsVO.getSelectAdjustCode());
        GcCalcLogVO calcLogVO = new GcCalcLogVO();
        if (calcLogEO != null) {
            if (calcLogEO.getBegintime() != null) {
                calcLogVO.setCalcStartDatetime(DateUtils.format((Date)new Date(calcLogEO.getBegintime()), (String)"yyyy-MM-dd HH:mm:ss"));
            }
            if (calcLogEO.getEndtime() != null) {
                calcLogVO.setCalcEndDatetime(DateUtils.format((Date)new Date(calcLogEO.getEndtime()), (String)"yyyy-MM-dd HH:mm:ss"));
            }
            calcLogVO.setCalcLog(calcLogEO.getInfo());
            calcLogVO.setUsername(this.getUserNickNameByName(calcLogEO.getUsername()));
            if (!StringUtils.isEmpty((String)calcLogEO.getTaskId())) {
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(calcLogEO.getTaskId());
                if (taskDefine != null) {
                    calcLogVO.setCalcTaskTitle(taskDefine.getTitle());
                }
                try {
                    FormSchemeDefine schemeDefine;
                    SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(calcArgmentsVO.getPeriodStr(), calcArgmentsVO.getTaskId());
                    if (schemePeriodLinkDefine != null && (schemeDefine = this.iRunTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey())) != null) {
                        calcLogVO.setCalcSchemeTitle(schemeDefine.getTitle());
                    }
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
        }
        if (completeLogEO != null) {
            TaskDefine taskDefine;
            if (completeLogEO.getBegintime() != null) {
                calcLogVO.setCompleteStartDatetime(DateUtils.format((Date)new Date(completeLogEO.getBegintime()), (String)"yyyy-MM-dd HH:mm:ss"));
            }
            if (completeLogEO.getEndtime() != null) {
                calcLogVO.setCompleteEndDatetime(DateUtils.format((Date)new Date(completeLogEO.getEndtime()), (String)"yyyy-MM-dd HH:mm:ss"));
            }
            calcLogVO.setCompleteUsername(this.getUserNickNameByName(completeLogEO.getUsername()));
            String info = completeLogEO.getInfo();
            List messages = (List)JsonUtils.readValue((String)info, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
            messages.remove(1);
            messages.remove(1);
            calcLogVO.setCompleteLog(JsonUtils.writeValueAsString((Object)messages));
            if (StringUtils.isEmpty((String)calcLogVO.getCalcSchemeTitle())) {
                try {
                    FormSchemeDefine schemeDefine;
                    SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(calcArgmentsVO.getPeriodStr(), calcArgmentsVO.getTaskId());
                    if (schemePeriodLinkDefine != null && (schemeDefine = this.iRunTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey())) != null) {
                        calcLogVO.setCalcSchemeTitle(schemeDefine.getTitle());
                    }
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            if (StringUtils.isEmpty((String)calcLogVO.getCalcTaskTitle()) && (taskDefine = this.iRunTimeViewController.queryTaskDefine(completeLogEO.getTaskId())) != null) {
                calcLogVO.setCalcTaskTitle(taskDefine.getTitle());
            }
        }
        return calcLogVO;
    }

    private String getUserNickNameByName(String username) {
        User user = this.userService.getByUsername(username);
        String userTitile = user == null ? username : username.concat("|").concat(user.getNickname());
        return userTitile;
    }

    @Override
    public Set<String> deleteAutoOffsetEntrysByRule(String ruleId, GcCalcArgmentsDTO arg) {
        if (null == ruleId) {
            return null;
        }
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setTaskId(arg.getTaskId());
        queryParamsVO.setAcctYear(arg.getAcctYear());
        queryParamsVO.setAcctPeriod(arg.getAcctPeriod());
        queryParamsVO.setPeriodStr(arg.getPeriodStr());
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.CONSOLIDATE.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.PHS.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.EQUITY_METHOD_ADJ.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.FAIRVALUE_ADJ.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.DEFERRED_INCOME_TAX_RULE.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.COPY_OFFSET.getSrcTypeValue());
        queryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
        ArrayList<String> rules = new ArrayList<String>();
        rules.add(ruleId);
        queryParamsVO.setRules(rules);
        queryParamsVO.setOrgId(arg.getOrgId());
        ArrayList<Integer> elmModes = new ArrayList<Integer>();
        elmModes.add(OffsetElmModeEnum.AUTO_ITEM.getValue());
        queryParamsVO.setElmModes(elmModes);
        queryParamsVO.setOrgType(arg.getOrgType());
        queryParamsVO.setCurrency(arg.getCurrency());
        Assert.isNotNull((Object)queryParamsVO.getOrgId(), (String)"\u5408\u5e76\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)queryParamsVO.getTaskId(), (String)"\u4efb\u52a1\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)queryParamsVO.getAcctYear(), (String)"\u5e74\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        CalcLogUtil.getInstance().log(this.getClass(), "deleteAutoOffsetEntrysByRule-\u5408\u5e76\u8ba1\u7b97\u7b49\u53d6\u6d88\u89c4\u5219", (Object)arg);
        queryParamsVO.setSelectAdjustCode(arg.getSelectAdjustCode());
        QueryParamsDTO queryDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryDTO);
        return this.offsetCoreService.delete(queryDTO);
    }

    private static class CheckResult {
        final boolean success;
        final boolean processState;

        public static CheckResult newInstance(boolean success, boolean processState) {
            return new CheckResult(success, processState);
        }

        public static CheckResult newSucessInstance() {
            return new CheckResult(true, true);
        }

        public boolean isSuccess() {
            return this.success;
        }

        public boolean isProcessState() {
            return this.processState;
        }

        private CheckResult(boolean success, boolean processState) {
            this.success = success;
            this.processState = processState;
        }
    }
}


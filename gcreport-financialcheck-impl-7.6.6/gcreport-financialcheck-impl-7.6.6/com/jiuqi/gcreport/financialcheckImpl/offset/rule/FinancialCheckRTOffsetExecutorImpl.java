/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.impl.FinancialCheckOffsetServiceImpl;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dto.RelatedItemGcOffsetRelDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl.RelatedItemOffsetRelAgent;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckRuleConverter;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.helper.FinancialCheckBalanceExecutorHelper;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.processor.executor.impl.inputdata.FinancialCheckRuleExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.offset.util.FinancialCheckOffsetUtils;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCheckRTOffsetExecutorImpl {
    @Autowired
    private FinancialCheckOffsetServiceImpl financialCheckOffsetService;
    @Autowired
    private ConsolidatedSystemService systemService;
    @Autowired
    private ConsolidatedTaskService taskCacheService;
    @Autowired
    private UnionRuleService ruleService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private FinancialCheckRuleConverter ruleConverter;
    private static Logger logger = LoggerFactory.getLogger(FinancialCheckRTOffsetExecutorImpl.class);

    public void realTimeOffset(List<GcRelatedItemEO> items, String periodStr) {
        Set<String> relatedItemIds = items.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
        this.deleteOffsetAndRel(relatedItemIds);
        List<GcOffsetRelatedItemEO> offsetItems = this.financialCheckOffsetService.listByRelatedId(relatedItemIds);
        Set<String> allSystemIds = this.listAllSystemIds();
        HashSet<String> ruleTypes = new HashSet<String>();
        ruleTypes.add(RuleTypeEnum.FINANCIAL_CHECK.getCode());
        GcCalcArgmentsDTO calcArgs = new GcCalcArgmentsDTO();
        calcArgs.setAcctYear(ConverterUtils.getAsInteger((Object)periodStr.substring(0, 4)));
        calcArgs.setAcctPeriod(ConverterUtils.getAsInteger((Object)periodStr.substring(7)));
        calcArgs.setSelectAdjustCode("0");
        for (String systemId : allSystemIds) {
            AbstractUnionRule unionRuleVO;
            List ruleIds;
            String periodBySystemId = FinancialCheckOffsetUtils.convertPeriodBySystemId(systemId, periodStr);
            if (StringUtils.isEmpty((String)periodBySystemId)) continue;
            calcArgs.setPeriodStr(periodBySystemId);
            List<GcOffsetRelatedItemEO> offsetItemBySyStem = offsetItems.stream().filter(item -> systemId.equals(item.getSystemId())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(offsetItemBySyStem) || offsetItemBySyStem.stream().anyMatch(item -> !StringUtils.isEmpty((String)item.getOffsetGroupId())) || (ruleIds = offsetItemBySyStem.stream().map(item -> item.getUnionRuleId()).distinct().collect(Collectors.toList())).size() != 1 || StringUtils.isEmpty((String)((String)ruleIds.get(0))) || Objects.isNull(unionRuleVO = this.ruleService.selectUnionRuleDTOById((String)ruleIds.get(0))) || !((FinancialCheckRuleDTO)unionRuleVO).isChecked() || !this.appendArg(calcArgs, systemId) || !this.setMergeOrgAndCurrency(calcArgs, (GcOffsetRelatedItemEO)offsetItemBySyStem.get(0))) continue;
            try {
                FinancialCheckBalanceExecutorHelper financialCheckExecutor = new FinancialCheckBalanceExecutorHelper(unionRuleVO, calcArgs);
                List<FinancialCheckRuleExecutorImpl.OffsetResult> offsetResults = financialCheckExecutor.autoCanOffsetItems(offsetItemBySyStem);
                this.saveOffset(offsetResults, relatedItemIds);
            }
            catch (Exception e) {
                this.log(calcArgs, unionRuleVO, e);
            }
        }
    }

    private void saveOffset(List<FinancialCheckRuleExecutorImpl.OffsetResult> offsetResults, Set<String> relatedItemIds) {
        if (CollectionUtils.isEmpty(offsetResults)) {
            return;
        }
        FinancialCheckRuleExecutorImpl.OffsetResult oneOffsetResult = null;
        for (FinancialCheckRuleExecutorImpl.OffsetResult offsetResult : offsetResults) {
            if (null == oneOffsetResult) {
                oneOffsetResult = offsetResult;
                continue;
            }
            oneOffsetResult.getOffsetVchr().getItems().addAll(offsetResult.getOffsetVchr().getItems());
            oneOffsetResult.getOffsetedInputItems().addAll(offsetResult.getOffsetedInputItems());
        }
        Map<String, RelatedItemGcOffsetRelDTO> relatedItemId2DTOMap = RelatedItemOffsetRelAgent.listRelatedItemId2DTOMap(oneOffsetResult);
        Set<String> ids = relatedItemId2DTOMap.keySet();
        if (ids.size() != relatedItemIds.size() || !relatedItemIds.containsAll(ids)) {
            return;
        }
        this.financialCheckOffsetService.saveOffsetData(oneOffsetResult, true);
    }

    private String log(GcCalcArgmentsDTO calcArgs, AbstractUnionRule unionRuleVO, Exception e) {
        ConsolidatedSystemEO systemEO;
        logger.info(e.getMessage(), e);
        String msg = e.getMessage() + " ";
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(calcArgs.getTaskId());
        if (null != taskDefine) {
            msg = msg + "\u4efb\u52a1\uff1a" + taskDefine.getTitle() + "\u3002";
        }
        if (null != (systemEO = this.systemService.getConsolidatedSystemEO(unionRuleVO.getReportSystem()))) {
            msg = msg + "\u4f53\u7cfb\uff1a" + systemEO.getSystemName() + "\u3002";
        }
        if (null != unionRuleVO) {
            msg = msg + "\u89c4\u5219\uff1a" + unionRuleVO.getTitle() + "\u3002";
        }
        return msg;
    }

    private boolean appendArg(GcCalcArgmentsDTO calcArgs, String systemId) {
        List consolidatedTasks = this.taskCacheService.listConsolidatedTaskBySystemIdAndPeriod(systemId, calcArgs.getPeriodStr());
        if (CollectionUtils.isEmpty((Collection)consolidatedTasks)) {
            return false;
        }
        ConsolidatedTaskVO firstTaskVo = (ConsolidatedTaskVO)consolidatedTasks.get(0);
        calcArgs.setTaskId(firstTaskVo.getTaskKey());
        String orgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)firstTaskVo.getTaskKey());
        calcArgs.setOrgType(orgType);
        try {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(calcArgs.getPeriodStr(), firstTaskVo.getTaskKey());
            if (Objects.isNull(schemePeriodLinkDefine)) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u4e0d\u5230\u62a5\u8868\u65b9\u6848");
            }
            calcArgs.setSchemeId(schemePeriodLinkDefine.getSchemeKey());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public void deleteOffsetAndRel(Collection<String> relatedItemIds) {
        List<Object> items = this.financialCheckOffsetService.listByRelatedId(relatedItemIds);
        items = items.stream().filter(item -> !StringUtils.isEmpty((String)item.getOffsetGroupId())).collect(Collectors.toList());
        HashSet<String> lockIds = new HashSet<String>();
        HashSet<String> delOffsetGroupIdSet = new HashSet<String>();
        items.forEach(item -> {
            delOffsetGroupIdSet.add(item.getOffsetGroupId());
            lockIds.add(FinancialCheckOffsetUtils.getItemKey(item));
        });
        this.financialCheckOffsetService.batchDeleteByOffsetGroupIdAndLock(delOffsetGroupIdSet, lockIds, null);
    }

    private boolean setMergeOrgAndCurrency(GcCalcArgmentsDTO calcArgs, GcOffsetRelatedItemEO item) {
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)calcArgs.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, calcArgs.getPeriodStr()));
        GcOrgCacheVO mergeOrg = tool.getCommonUnit(tool.getOrgByCode(item.getGcUnitId()), tool.getOrgByCode(item.getGcOppUnitId()));
        if (null == mergeOrg) {
            return false;
        }
        calcArgs.setOrgId(mergeOrg.getCode());
        calcArgs.setCurrency(ConverterUtils.getAsString((Object)mergeOrg.getTypeFieldValue("CURRENCYID")));
        return true;
    }

    private Set<String> listAllSystemIds() {
        List consolidatedSystemEOS = this.systemService.getConsolidatedSystemEOS();
        if (null == consolidatedSystemEOS) {
            return Collections.EMPTY_SET;
        }
        return consolidatedSystemEOS.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
    }
}


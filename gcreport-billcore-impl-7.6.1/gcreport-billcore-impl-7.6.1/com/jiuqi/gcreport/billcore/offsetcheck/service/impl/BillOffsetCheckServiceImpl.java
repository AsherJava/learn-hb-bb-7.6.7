/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.calculate.event.GcCalcTaskEvent
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.datatrace.service.OffsetAmtTraceService
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl
 *  com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dao.UnionRuleDao
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 */
package com.jiuqi.gcreport.billcore.offsetcheck.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO;
import com.jiuqi.gcreport.billcore.offsetcheck.dto.OffsetCheckResultDTO;
import com.jiuqi.gcreport.billcore.offsetcheck.enums.CheckStatusEnum;
import com.jiuqi.gcreport.billcore.offsetcheck.handle.BuildBaseOffsetCheckInfoHandler;
import com.jiuqi.gcreport.billcore.offsetcheck.offsetitemcheck.OffsetItemCheck;
import com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheck;
import com.jiuqi.gcreport.billcore.offsetcheck.ruleconditoncheck.RuleCondtionCheckGather;
import com.jiuqi.gcreport.billcore.offsetcheck.service.BillOffsetCheckService;
import com.jiuqi.gcreport.billcore.offsetcheck.service.GcBillTracerService;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.event.GcCalcTaskEvent;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.datatrace.service.OffsetAmtTraceService;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl;
import com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BillOffsetCheckServiceImpl
implements BillOffsetCheckService {
    private final Logger logger = LoggerFactory.getLogger(BillOffsetCheckServiceImpl.class);
    public static final String RULEID = "RULEID";
    public static final String SUBJECTCODE = "SUBJECTCODE";
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private IRuntimeTaskService taskService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private GcOffSetItemAdjustCoreServiceImpl offSetItemAdjustService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private GcBillTracerService gcBillTracerService;
    @Autowired
    private OffsetAmtTraceService offsetAmtTraceService;
    @Autowired
    private RuleCondtionCheckGather ruleCondtionCheckGather;
    @Autowired(required=false)
    private List<OffsetItemCheck> offsetItemCheckList;
    @Autowired
    private UnionRuleDao unionRuleDao;
    @Autowired(required=false)
    private List<BuildBaseOffsetCheckInfoHandler> buildBaseOffsetCheckInfoHandlerList;

    @Override
    public Pagination<BillOffsetCheckInfoDTO> dataTraceOffsetCheckList(GcDataTraceCondi condi) {
        QueryParamsVO queryParamsVO = this.buildQueryParamsByDataTraceCondi(condi);
        if (CollectionUtils.isEmpty(queryParamsVO.getRules())) {
            throw new BusinessRuntimeException("\u8be5\u4efb\u52a1\u6240\u5c5e\u4f53\u7cfb\u4e0b\u65e0\u89c4\u5219");
        }
        List doCheckUnionRules = this.unionRuleService.selectUnionRuleDTOByIdList((Collection)queryParamsVO.getRules());
        if (CollectionUtils.isEmpty(doCheckUnionRules)) {
            return new Pagination(Collections.emptyList(), Integer.valueOf(0), condi.getPageNum(), condi.getPageSize());
        }
        List allRuleListByReportSystem = this.unionRuleDao.findAllRuleListByReportSystem(queryParamsVO.getSystemId());
        Map ruleId2RuleEOMap = allRuleListByReportSystem.stream().collect(Collectors.toMap(item -> item.getId(), item -> item, (v1, v2) -> v1, LinkedHashMap::new));
        LinkedHashMap<String, BillOffsetCheckInfoDTO> ruleId2DataTraceCheckInfoDTOMap = new LinkedHashMap<String, BillOffsetCheckInfoDTO>();
        this.gcBillTracerService.resetBillCode(condi);
        List<Map<String, Object>> originOffsetItems = this.getOriginOffsetItems(condi, queryParamsVO);
        List subjectEOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(queryParamsVO.getSystemId());
        Map subjectCode2DataMap = subjectEOS.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, eo -> eo, (v1, v2) -> v1, LinkedHashMap::new));
        List<BillOffsetCheckInfoDTO> billOffsetCheckInfoDTOS = this.ruleConditonCheck(condi, doCheckUnionRules, originOffsetItems, subjectCode2DataMap);
        List<GcOffSetVchrItemDTO> preCalcOffSetItems = this.getPreCalcOffSetItems(queryParamsVO, condi.getBillCode());
        billOffsetCheckInfoDTOS.addAll(this.offsetItemInfoCheck(doCheckUnionRules, originOffsetItems, preCalcOffSetItems, subjectCode2DataMap, condi.getGcDataTraceType()));
        List<BillOffsetCheckInfoDTO> dataTraceCheckInfoDTOs = this.assemblyBillOffsetCheckInfoDTOS(ruleId2RuleEOMap, ruleId2DataTraceCheckInfoDTOMap, billOffsetCheckInfoDTOS);
        return new Pagination(dataTraceCheckInfoDTOs, Integer.valueOf(dataTraceCheckInfoDTOs.size()), condi.getPageNum(), condi.getPageSize());
    }

    @Override
    public List<OffsetTraceResultVO> offsetCheckAmtTrace(GcDataTraceCondi condi) {
        QueryParamsVO queryParamsVO = this.buildQueryParamsByDataTraceCondi(condi);
        List<Object> preCalcOffSetItems = this.getPreCalcOffSetItems(queryParamsVO, condi.getBillCode());
        preCalcOffSetItems = preCalcOffSetItems.stream().filter(item -> item.getOffSetDebit() != null && item.getOffSetDebit() != 0.0 || item.getOffSetCredit() != null && item.getOffSetCredit() != 0.0).collect(Collectors.toList());
        List offSetVchrItemAdjustEOS = preCalcOffSetItems.stream().map(offSetVchrItemDTO -> OffsetConvertUtil.getInstance().convertDTO2EO(offSetVchrItemDTO)).collect(Collectors.toList());
        return this.offsetAmtTraceService.listOffsetTraceResultVOS((GcTaskBaseArguments)queryParamsVO, offSetVchrItemAdjustEOS);
    }

    private List<BillOffsetCheckInfoDTO> assemblyBillOffsetCheckInfoDTOS(Map<String, UnionRuleEO> ruleId2RuleEOMap, Map<String, BillOffsetCheckInfoDTO> ruleId2DataTraceCheckInfoDTOMap, List<BillOffsetCheckInfoDTO> billOffsetCheckInfoDTOS) {
        for (BillOffsetCheckInfoDTO billOffsetCheckInfoDTO : billOffsetCheckInfoDTOS) {
            this.setParentRuleCheckInfoDTO(billOffsetCheckInfoDTO, ruleId2RuleEOMap, ruleId2DataTraceCheckInfoDTOMap);
        }
        List<Object> dataTraceCheckInfoDTOs = new ArrayList<BillOffsetCheckInfoDTO>();
        dataTraceCheckInfoDTOs.addAll(ruleId2DataTraceCheckInfoDTOMap.values());
        dataTraceCheckInfoDTOs = dataTraceCheckInfoDTOs.stream().filter(dataTraceCheckInfoDTO -> {
            UnionRuleEO unionRuleEO = (UnionRuleEO)ruleId2RuleEOMap.get(dataTraceCheckInfoDTO.getRuleId());
            return UUIDUtils.emptyUUIDStr().equals(unionRuleEO.getParentId());
        }).collect(Collectors.toList());
        dataTraceCheckInfoDTOs = ((BillOffsetCheckInfoDTO)dataTraceCheckInfoDTOs.get(0)).getChildren();
        this.setChildLevels(dataTraceCheckInfoDTOs, 1);
        return dataTraceCheckInfoDTOs;
    }

    private void setChildLevels(List<BillOffsetCheckInfoDTO> children, int parentLevel) {
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        for (BillOffsetCheckInfoDTO child : children) {
            List grandchildren = child.getChildren();
            if (grandchildren == null) continue;
            child.setParentLevel(parentLevel);
            this.setChildLevels(grandchildren, parentLevel + 1);
        }
    }

    private List<BillOffsetCheckInfoDTO> ruleConditonCheck(GcDataTraceCondi condi, List<AbstractUnionRule> doCheckUnionRules, List<Map<String, Object>> originOffsetItems, Map<String, ConsolidatedSubjectEO> subjectCode2DataMap) {
        List<RuleCondtionCheck> ruleCondtionCheckList = this.ruleCondtionCheckGather.getRuleCondtionCheckList(condi.getGcDataTraceType());
        Map<String, List<Map>> ruleId2OriginOffsetItemsMap = this.buildRuleId2OffsetItemsMap(originOffsetItems, item -> item.get(RULEID).toString());
        ArrayList<String> hasCheckResultRuleIds = new ArrayList<String>();
        ArrayList<BillOffsetCheckInfoDTO> billOffsetCheckInfoDTOS = new ArrayList<BillOffsetCheckInfoDTO>();
        block0: for (AbstractUnionRule rule2 : doCheckUnionRules) {
            List<Map<String, Object>> originOffsetItemList = ruleId2OriginOffsetItemsMap.get(rule2.getId());
            for (RuleCondtionCheck ruleCondtionCheck : ruleCondtionCheckList) {
                OffsetCheckResultDTO offsetCheckResultDTO = ruleCondtionCheck.check(rule2, condi, !CollectionUtils.isEmpty(originOffsetItemList));
                if (offsetCheckResultDTO == null) continue;
                hasCheckResultRuleIds.add(rule2.getId());
                if (!CollectionUtils.isEmpty(originOffsetItemList)) {
                    this.addCheckInfoContainOrignOffset(subjectCode2DataMap, hasCheckResultRuleIds, billOffsetCheckInfoDTOS, rule2, originOffsetItemList, offsetCheckResultDTO);
                    continue block0;
                }
                billOffsetCheckInfoDTOS.add(this.addDataTraceCheckInfo(rule2, offsetCheckResultDTO));
                continue block0;
            }
        }
        doCheckUnionRules.removeAll(doCheckUnionRules.stream().filter(rule -> hasCheckResultRuleIds.contains(rule.getId())).collect(Collectors.toList()));
        return billOffsetCheckInfoDTOS;
    }

    private void addCheckInfoContainOrignOffset(Map<String, ConsolidatedSubjectEO> subjectCode2DataMap, List<String> hasCheckResultRuleIds, List<BillOffsetCheckInfoDTO> billOffsetCheckInfoDTOS, AbstractUnionRule rule, List<Map<String, Object>> originOffsetItemList, OffsetCheckResultDTO offsetCheckResultDTO) {
        hasCheckResultRuleIds.add(rule.getId());
        BuildBaseOffsetCheckInfoHandler buildBaseOffsetCheckInfoHandler = this.buildBaseOffsetCheckInfoHandlerList.stream().filter(handler -> handler.isMatch(rule.getRuleType())).findFirst().orElse(null);
        if (buildBaseOffsetCheckInfoHandler == null) {
            throw new BusinessRuntimeException(rule.getTitle() + " \u89c4\u5219\u672a\u627e\u5230\u5bf9\u5e94\u7684\u68c0\u67e5\u4fe1\u606f\u6267\u884c\u5668");
        }
        List<BillOffsetCheckInfoDTO> billOffsetCheckInfoDTOOfRule = buildBaseOffsetCheckInfoHandler.buildBaseOffsetCheckInfo(rule, originOffsetItemList, subjectCode2DataMap);
        String mrecid = CollectionUtils.isEmpty(originOffsetItemList) ? "" : (String)originOffsetItemList.get(0).get("MRECID");
        billOffsetCheckInfoDTOOfRule.forEach(billOffsetCheckInfoDTO -> {
            billOffsetCheckInfoDTO.setCheckInfo(offsetCheckResultDTO.getCheckInfo());
            billOffsetCheckInfoDTO.setCheckStatus(offsetCheckResultDTO.getCheckStatus());
            billOffsetCheckInfoDTO.setOriginOffsetMrecid(mrecid);
        });
        billOffsetCheckInfoDTOS.addAll(billOffsetCheckInfoDTOOfRule);
    }

    private BillOffsetCheckInfoDTO addDataTraceCheckInfo(AbstractUnionRule rule, OffsetCheckResultDTO offsetCheckResultDTO) {
        BillOffsetCheckInfoDTO dataTraceCheckInfoDTO = new BillOffsetCheckInfoDTO(rule.getId(), "--", "--");
        dataTraceCheckInfoDTO.setRuleTitle(rule.getLocalizedName());
        dataTraceCheckInfoDTO.setRuleType(rule.getRuleType());
        dataTraceCheckInfoDTO.setCheckInfo(offsetCheckResultDTO.getCheckInfo());
        dataTraceCheckInfoDTO.setCheckStatus(offsetCheckResultDTO.getCheckStatus());
        dataTraceCheckInfoDTO.setRuleApplyConditon(rule.getRuleCondition());
        dataTraceCheckInfoDTO.setRuleTypeTitle(rule.getRuleTypeDescription());
        dataTraceCheckInfoDTO.setSubjectTitle("--");
        dataTraceCheckInfoDTO.setFormula("--");
        dataTraceCheckInfoDTO.setCheckOffsetDebitInfo("--");
        dataTraceCheckInfoDTO.setCheckOffsetCreditInfo("--");
        dataTraceCheckInfoDTO.setOffsetCreditInfo("--");
        dataTraceCheckInfoDTO.setOffsetDebitInfo("--");
        dataTraceCheckInfoDTO.setCheckCreditDiffInfo("--");
        dataTraceCheckInfoDTO.setCheckDebitDiffInfo("--");
        return dataTraceCheckInfoDTO;
    }

    private List<Map<String, Object>> getOriginOffsetItems(GcDataTraceCondi condi, QueryParamsVO queryParamsVO) {
        ArrayList<String> srcGroupIds = new ArrayList<String>();
        srcGroupIds.add(condi.getSrcId());
        List<String> associatedDataSrcGroupIds = this.gcBillTracerService.getAssociatedDataSrcGroupIds(condi);
        srcGroupIds.addAll(associatedDataSrcGroupIds);
        Pagination<Map<String, Object>> offsetPage = this.getOffsetEntryBySrcOffsetGroupIds(queryParamsVO, new HashSet<String>(srcGroupIds));
        return offsetPage.getContent();
    }

    private List<BillOffsetCheckInfoDTO> offsetItemInfoCheck(List<AbstractUnionRule> doCheckUnionRules, List<Map<String, Object>> originOffsetItems, List<GcOffSetVchrItemDTO> preCalcOffSetItems, Map<String, ConsolidatedSubjectEO> subjectCode2DataMap, String gcDataTraceType) {
        Map<String, List<Map>> ruleId2OriginOffsetItemsMap = this.buildRuleId2OffsetItemsMap(originOffsetItems, item -> item.get(RULEID).toString());
        Map<String, List<GcOffSetVchrItemDTO>> ruleId2PreCalcOffSetItemsMap = this.buildRuleId2OffsetItemsMap(preCalcOffSetItems, GcOffSetVchrItemDTO::getRuleId);
        ArrayList<BillOffsetCheckInfoDTO> billOffsetCheckInfoDTOS = new ArrayList<BillOffsetCheckInfoDTO>();
        for (AbstractUnionRule rule : doCheckUnionRules) {
            List<Map<String, Object>> originOffsetItemList = ruleId2OriginOffsetItemsMap.get(rule.getId());
            List<GcOffSetVchrItemDTO> preCalcOffSetItemList = ruleId2PreCalcOffSetItemsMap.get(rule.getId());
            List<BillOffsetCheckInfoDTO> dataTraceCheckInfoDTOsOfOneRule = this.buildCheckInfoDTOsOfRule(subjectCode2DataMap, rule, originOffsetItemList, preCalcOffSetItemList);
            for (OffsetItemCheck offsetItemCheck : this.offsetItemCheckList) {
                OffsetCheckResultDTO offsetCheckResultDTO = offsetItemCheck.check(originOffsetItemList, preCalcOffSetItemList, gcDataTraceType);
                if (offsetCheckResultDTO == null) continue;
                String mrecid = CollectionUtils.isEmpty(originOffsetItemList) ? "" : (String)originOffsetItemList.get(0).get("MRECID");
                dataTraceCheckInfoDTOsOfOneRule.forEach(item -> {
                    item.setCheckInfo(offsetCheckResultDTO.getCheckInfo());
                    item.setCheckStatus(offsetCheckResultDTO.getCheckStatus());
                    item.setOriginOffsetMrecid(mrecid);
                });
                break;
            }
            if (CollectionUtils.isEmpty(dataTraceCheckInfoDTOsOfOneRule)) continue;
            List existOffsetAmtIsNotNullDatas = dataTraceCheckInfoDTOsOfOneRule.stream().filter(dataTraceCheckInfoDTO -> !dataTraceCheckInfoDTO.isRowAmtAllNull()).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(existOffsetAmtIsNotNullDatas)) {
                BillOffsetCheckInfoDTO firstDataTraceCheckInfoDTO = dataTraceCheckInfoDTOsOfOneRule.get(0);
                firstDataTraceCheckInfoDTO.setCheckStatus(CheckStatusEnum.UNGENERATED.getCode());
                firstDataTraceCheckInfoDTO.setSubjectTitle("--");
                firstDataTraceCheckInfoDTO.setFormula("--");
                firstDataTraceCheckInfoDTO.setCheckOffsetDebitInfo("--");
                firstDataTraceCheckInfoDTO.setCheckOffsetCreditInfo("--");
                firstDataTraceCheckInfoDTO.setOffsetCreditInfo("--");
                firstDataTraceCheckInfoDTO.setOffsetDebitInfo("--");
                firstDataTraceCheckInfoDTO.setCheckCreditDiffInfo("--");
                firstDataTraceCheckInfoDTO.setCheckDebitDiffInfo("--");
                firstDataTraceCheckInfoDTO.setRowAmtAllNull(false);
                billOffsetCheckInfoDTOS.add(firstDataTraceCheckInfoDTO);
                continue;
            }
            billOffsetCheckInfoDTOS.addAll(dataTraceCheckInfoDTOsOfOneRule);
        }
        return billOffsetCheckInfoDTOS;
    }

    private List<BillOffsetCheckInfoDTO> buildCheckInfoDTOsOfRule(Map<String, ConsolidatedSubjectEO> subjectCode2DataMap, AbstractUnionRule rule, List<Map<String, Object>> originOffsetItemList, List<GcOffSetVchrItemDTO> preCalcOffSetItemList) {
        BuildBaseOffsetCheckInfoHandler buildBaseOffsetCheckInfoHandler;
        LinkedHashMap<String, Map<String, Object>> subjectCode2OriginOffsetItem = new LinkedHashMap();
        if (!CollectionUtils.isEmpty(originOffsetItemList)) {
            subjectCode2OriginOffsetItem = originOffsetItemList.stream().collect(Collectors.toMap(item -> (String)item.get(SUBJECTCODE), item -> item, (v1, v2) -> v1, LinkedHashMap::new));
        }
        LinkedHashMap<String, GcOffSetVchrItemDTO> subjectCode2PreCalcOffSetItem = new LinkedHashMap();
        if (!CollectionUtils.isEmpty(preCalcOffSetItemList)) {
            subjectCode2PreCalcOffSetItem = preCalcOffSetItemList.stream().collect(Collectors.toMap(item -> item.getSubjectCode(), item -> item, (v1, v2) -> v1, LinkedHashMap::new));
        }
        if ((buildBaseOffsetCheckInfoHandler = (BuildBaseOffsetCheckInfoHandler)this.buildBaseOffsetCheckInfoHandlerList.stream().filter(handler -> handler.isMatch(rule.getRuleType())).findFirst().orElse(null)) == null) {
            throw new BusinessRuntimeException(rule.getTitle() + " \u89c4\u5219\u672a\u627e\u5230\u5bf9\u5e94\u7684\u68c0\u67e5\u4fe1\u606f\u6267\u884c\u5668");
        }
        return buildBaseOffsetCheckInfoHandler.buildBaseOffsetCheckInfo(rule, subjectCode2DataMap, subjectCode2OriginOffsetItem, subjectCode2PreCalcOffSetItem);
    }

    private void setParentRuleCheckInfoDTO(BillOffsetCheckInfoDTO childrenDataTraceCheckInfo, Map<String, UnionRuleEO> ruleId2RuleEOMap, Map<String, BillOffsetCheckInfoDTO> ruleId2DataTraceCheckInfoDTOMap) {
        UnionRuleEO unionRuleEO = ruleId2RuleEOMap.get(childrenDataTraceCheckInfo.getRuleId());
        String parentId = unionRuleEO.getParentId();
        BillOffsetCheckInfoDTO parentCheckInfoDTO = ruleId2DataTraceCheckInfoDTOMap.get(parentId);
        if (parentCheckInfoDTO == null) {
            UnionRuleEO parentRuleEO = ruleId2RuleEOMap.get(parentId);
            if (parentRuleEO == null) {
                return;
            }
            parentCheckInfoDTO = new BillOffsetCheckInfoDTO(parentId, null, null);
            parentCheckInfoDTO.setRuleTypeTitle("\u5206\u7ec4");
            parentCheckInfoDTO.setRuleTitle(parentRuleEO.getTitle());
            ruleId2DataTraceCheckInfoDTOMap.put(parentId, parentCheckInfoDTO);
            if (childrenDataTraceCheckInfo != null) {
                parentCheckInfoDTO.getChildren().add(childrenDataTraceCheckInfo);
            }
            this.setParentRuleCheckInfoDTO(parentCheckInfoDTO, ruleId2RuleEOMap, ruleId2DataTraceCheckInfoDTOMap);
        } else if (childrenDataTraceCheckInfo != null) {
            parentCheckInfoDTO.getChildren().add(childrenDataTraceCheckInfo);
        }
    }

    private QueryParamsVO buildQueryParamsByDataTraceCondi(GcDataTraceCondi condi) {
        ConsolidatedTaskVO consolidatedTaskVO;
        TaskDefine taskDefine;
        PeriodType periodType;
        if (CollectionUtils.isEmpty(condi.getRuleIds())) {
            throw new BusinessRuntimeException("\u5408\u5e76\u89c4\u5219\u6761\u4ef6\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        QueryParamsVO paramsVO = new QueryParamsVO();
        paramsVO.setOrgType(condi.getOrgType());
        YearPeriodObject yp = new YearPeriodObject(null, condi.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condi.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO inputUnit = tool.getOrgByCode(condi.getInputUnitId());
        if (inputUnit != null && GcOrgKindEnum.UNIONORG.equals((Object)inputUnit.getOrgKind())) {
            paramsVO.setOrgId(condi.getInputUnitId());
        } else {
            GcOrgCacheVO oppUnit;
            GcOrgCacheVO unit = tool.getOrgByCode(condi.getUnitId());
            GcOrgCacheVO commonUnit = tool.getCommonUnit(unit, oppUnit = tool.getOrgByCode(condi.getOppUnitId()));
            if (commonUnit != null) {
                paramsVO.setOrgId(commonUnit.getId());
            }
        }
        paramsVO.setUnitIdList(Arrays.asList(condi.getUnitId()));
        paramsVO.setOppUnitIdList(Arrays.asList(condi.getOppUnitId()));
        paramsVO.setAcctYear(condi.getAcctYear());
        paramsVO.setAcctPeriod(condi.getAcctPeriod());
        paramsVO.setPeriodStr(condi.getPeriodStr());
        paramsVO.setSelectAdjustCode(condi.getSelectAdjustCode());
        paramsVO.setTaskId(condi.getTaskId());
        try {
            FormSchemeDefine formSchemeDefine;
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(condi.getPeriodStr(), condi.getTaskId());
            if (schemePeriodLinkDefine != null && (formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey())) != null) {
                paramsVO.setSchemeId(formSchemeDefine.getKey());
            }
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u6eaf\u6e90\u53c2\u6570\u83b7\u53d6\u4e0d\u5230\u65b9\u6848ID:" + e.getMessage(), e);
        }
        paramsVO.setCurrency(condi.getCurrency());
        paramsVO.setFilterDisableItem(true);
        if (!StringUtils.isEmpty((String)condi.getSrcId())) {
            paramsVO.setSrcOffsetGroupIds(Arrays.asList(condi.getSrcId()));
        }
        if ((periodType = (taskDefine = this.taskService.queryTaskDefine(condi.getTaskId())).getPeriodType()) != null) {
            paramsVO.setPeriodType(Integer.valueOf(periodType.type()));
        }
        if ((consolidatedTaskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(condi.getTaskId(), condi.getPeriodStr())) == null) {
            this.logger.error("\u62b5\u9500\u5206\u5f55\u6570\u636e\u6eaf\u6e90-\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5408\u5e76\u4f53\u7cfb\uff0ctaskId:{}, periodStr:{}", (Object)condi.getTaskId(), (Object)condi.getPeriodStr());
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5408\u5e76\u4f53\u7cfb");
        }
        paramsVO.setSystemId(consolidatedTaskVO.getSystemId());
        paramsVO.setRules(condi.getRuleIds());
        List otherShowColumnNames = condi.getOtherShowColumnNames();
        if (!CollectionUtils.isEmpty(otherShowColumnNames)) {
            paramsVO.setOtherShowColumns(otherShowColumnNames);
        }
        return paramsVO;
    }

    public Pagination<Map<String, Object>> getOffsetEntryBySrcOffsetGroupIds(QueryParamsVO queryParamsVO, Set<String> srcOffsetGroupIds) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        Pagination pagination = new Pagination(this.offsetCoreService.listWithFullGroupBySrcOffsetGroupIdsAndSystemId(queryParamsDTO, srcOffsetGroupIds), Integer.valueOf(0), Integer.valueOf(queryParamsVO.getPageNum()), Integer.valueOf(queryParamsVO.getPageSize()));
        return this.offSetItemAdjustService.assembleOffsetEntry(pagination, queryParamsVO);
    }

    private <T> Map<String, List<T>> buildRuleId2OffsetItemsMap(List<T> items, Function<T, String> keyExtractor) {
        if (items == null) {
            return Collections.emptyMap();
        }
        return items.stream().collect(Collectors.groupingBy(keyExtractor, LinkedHashMap::new, Collectors.toList()));
    }

    private BillOffsetCheckInfoDTO buildBlankDataTraceDTO(Map<String, ConsolidatedSubjectEO> subjectCode2DataMap, AbstractUnionRule rule, String subjectCode, String fetchFormula) {
        if (StringUtils.isEmpty((String)subjectCode) || rule == null) {
            return null;
        }
        BillOffsetCheckInfoDTO dataTraceCheckInfoDTO = new BillOffsetCheckInfoDTO(rule.getId(), subjectCode, fetchFormula);
        dataTraceCheckInfoDTO.setRuleTitle(rule.getLocalizedName());
        ConsolidatedSubjectEO consolidatedSubjectEO = subjectCode2DataMap.get(subjectCode);
        if (consolidatedSubjectEO != null) {
            dataTraceCheckInfoDTO.setSubjectTitle(consolidatedSubjectEO.getTitle());
        }
        return dataTraceCheckInfoDTO;
    }

    public List<GcOffSetVchrItemDTO> getPreCalcOffSetItems(QueryParamsVO queryParamsVO, String billCode) {
        GcCalcArgmentsDTO calcArgments = new GcCalcArgmentsDTO();
        calcArgments.setOrgType(queryParamsVO.getOrgType());
        calcArgments.setRuleIds(queryParamsVO.getRules());
        calcArgments.setAcctPeriod(queryParamsVO.getAcctPeriod());
        calcArgments.setTaskId(queryParamsVO.getTaskId());
        calcArgments.setAcctYear(queryParamsVO.getAcctYear());
        calcArgments.setPeriodType(queryParamsVO.getPeriodType());
        calcArgments.setPeriodStr(queryParamsVO.getPeriodStr());
        calcArgments.setCurrency(queryParamsVO.getCurrency());
        calcArgments.setOrgId(queryParamsVO.getOrgId());
        calcArgments.setSchemeId(queryParamsVO.getSchemeId());
        calcArgments.getPreCalcFlag().set(true);
        calcArgments.getDisabledCopyInitOffset().set(true);
        calcArgments.setSn(UUIDOrderUtils.newUUIDStr());
        calcArgments.setSelectAdjustCode(queryParamsVO.getSelectAdjustCode());
        calcArgments.getExtendInfo().put("BILLCODE", billCode);
        GcCalcEnvContextImpl env = new GcCalcEnvContextImpl(calcArgments.getSn());
        env.setCalcArgments(calcArgments);
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcCalcTaskEvent((Object)this, env));
        return env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems();
    }
}


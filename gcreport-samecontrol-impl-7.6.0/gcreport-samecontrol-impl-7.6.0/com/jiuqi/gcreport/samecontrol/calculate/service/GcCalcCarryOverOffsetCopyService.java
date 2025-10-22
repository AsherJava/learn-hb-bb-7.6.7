/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.service.IGcCalcInitOffSetItemCopyFilter
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.interceptor.TransactionAspectSupport
 */
package com.jiuqi.gcreport.samecontrol.calculate.service;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.service.IGcCalcInitOffSetItemCopyFilter;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

@Component
public class GcCalcCarryOverOffsetCopyService {
    private Logger logger = LoggerFactory.getLogger(GcCalcCarryOverOffsetCopyService.class);
    @Autowired
    private GcOffSetAppOffsetService adjustingEntryService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcOffSetInitService gcOffSetInitService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private SameCtrlOffSetItemDao sameCtrlOffSetItemDao;

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void executeInitToAdjustOffSetItem(GcCalcEnvContext env) {
        OffsetItemInitQueryParamsVO currTermQueryParamsVO = this.queryOffsetItemInitQueryParamsVO(env.getCalcArgments());
        try {
            String FIELD_SRCOFFSETGROUPID = "SRCOFFSETGROUPID";
            String FIELD_MODIFYTIME = "MODIFYTIME";
            String FIELD_CREATETIME = "CREATETIME";
            List initOffSetPartFieldDatas = this.gcOffSetInitService.getPartFieldOffsetEntry(currTermQueryParamsVO);
            Map<String, List<Map>> initSrcOffsetGroupId2DataMap = initOffSetPartFieldDatas.stream().filter(initItem -> !ObjectUtils.isEmpty(initItem.get(FIELD_SRCOFFSETGROUPID))).collect(Collectors.groupingBy(map -> ConverterUtils.getAsString(map.get(FIELD_SRCOFFSETGROUPID))));
            QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
            BeanUtils.copyProperties(currTermQueryParamsVO, queryParamsDTO);
            List partFieldOffsetEntrys = this.adjustingEntryService.getPartFieldOffsetEntrys(queryParamsDTO);
            Map<String, List<Map>> srcOffsetGroupId2DataMap = partFieldOffsetEntrys.stream().filter(srcItem -> !ObjectUtils.isEmpty(srcItem.get(FIELD_SRCOFFSETGROUPID))).collect(Collectors.groupingBy(map -> ConverterUtils.getAsString(map.get(FIELD_SRCOFFSETGROUPID))));
            HashSet needDeleteSrcOffsetGroupIds = new HashSet();
            HashSet needCopyInitSrcOffsetGroupIds = new HashSet();
            srcOffsetGroupId2DataMap.forEach((srcOffsetGroupId, srcItems) -> {
                List initItems = (List)initSrcOffsetGroupId2DataMap.get(srcOffsetGroupId);
                if (CollectionUtils.isEmpty((Collection)initItems)) {
                    needDeleteSrcOffsetGroupIds.add(srcOffsetGroupId);
                    return;
                }
                OptionalLong initMaxModifyTime = initItems.stream().map(initItem -> (Date)(initItem.get(FIELD_MODIFYTIME) != null ? initItem.get(FIELD_MODIFYTIME) : initItem.get(FIELD_CREATETIME))).mapToLong(Date::getTime).max();
                long initMaxTimestamp = 0L;
                if (initMaxModifyTime.isPresent()) {
                    initMaxTimestamp = initMaxModifyTime.getAsLong();
                }
                OptionalLong srcMinModifyTime = srcItems.stream().map(item -> (Date)(item.get(FIELD_MODIFYTIME) != null ? item.get(FIELD_MODIFYTIME) : item.get(FIELD_CREATETIME))).mapToLong(Date::getTime).min();
                long srcMinTimestamp = 0L;
                if (srcMinModifyTime.isPresent()) {
                    srcMinTimestamp = srcMinModifyTime.getAsLong();
                }
                if (initMaxTimestamp >= srcMinTimestamp) {
                    needDeleteSrcOffsetGroupIds.add(srcOffsetGroupId);
                    return;
                }
            });
            Set<String> srcOffsetGroupIds = srcOffsetGroupId2DataMap.keySet();
            initSrcOffsetGroupId2DataMap.forEach((initSrcOffsetGroupId, initFeildMap) -> {
                if (needDeleteSrcOffsetGroupIds.contains(initSrcOffsetGroupId)) {
                    needCopyInitSrcOffsetGroupIds.add(initSrcOffsetGroupId);
                    return;
                }
                if (!srcOffsetGroupIds.contains(initSrcOffsetGroupId)) {
                    needCopyInitSrcOffsetGroupIds.add(initSrcOffsetGroupId);
                    return;
                }
            });
            HashSet deleteMrecids = new HashSet();
            needDeleteSrcOffsetGroupIds.stream().forEach(needDeleteSrcOffsetGroupId -> {
                List mrecids = ((List)srcOffsetGroupId2DataMap.get(needDeleteSrcOffsetGroupId)).stream().map(srcItem -> ConverterUtils.getAsString(srcItem.get("MRECID"))).filter(mrecid -> !StringUtils.isEmpty((String)mrecid)).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(mrecids)) {
                    return;
                }
                deleteMrecids.addAll(mrecids);
            });
            if (!CollectionUtils.isEmpty(deleteMrecids)) {
                this.adjustingEntryService.cancelInputOffsetByOffsetGroupId(needDeleteSrcOffsetGroupIds, queryParamsDTO.getTaskId());
                this.adjustingEntryService.batchDelete(deleteMrecids, queryParamsDTO.getTaskId(), queryParamsDTO.getAcctYear(), queryParamsDTO.getAcctPeriod(), queryParamsDTO.getOrgType(), queryParamsDTO.getCurrency());
            }
            CalcLogUtil.getInstance().log(this.getClass(), "\u5408\u5e76\u8ba1\u7b97-\u5e74\u521d\u521d\u59cb\u5316\u589e\u91cf\u5220\u9664-\u8c03\u6574\u62b5\u9500\u5206\u5f55mrecid\u6570\u91cf" + deleteMrecids.size(), (Object)queryParamsDTO);
            if (CollectionUtils.isEmpty(needCopyInitSrcOffsetGroupIds)) {
                return;
            }
            QueryParamsVO queryParamsVO = new QueryParamsVO();
            BeanUtils.copyProperties(currTermQueryParamsVO, queryParamsVO);
            queryParamsVO.setSrcOffsetGroupIds(new ArrayList(needCopyInitSrcOffsetGroupIds));
            List<GcOffSetVchrDTO> groups = this.queryOffSetGroupDTOs(env, queryParamsVO);
            Collections.reverse(groups);
            for (GcOffSetVchrDTO group : groups) {
                group.setAllowMoreUnit(true, env.getCalcContextExpandVariableCenter().getOrgCenterService().getCurrOrgType().getName());
                this.save(env, group, currTermQueryParamsVO);
            }
            CalcLogUtil.getInstance().log(this.getClass(), "\u5408\u5e76\u8ba1\u7b97-\u5e74\u521d\u521d\u59cb\u5316\u589e\u91cf\u590d\u5236-\u521d\u59cb\u5316\u5206\u5f55\u589e\u91cf\u6761\u6570" + groups.size(), (Object)queryParamsDTO);
            if (env.getCalcArgments().getPreCalcFlag().get()) {
                groups.forEach(gcOffSetVchrDTO -> env.getCalcContextExpandVariableCenter().getPreCalcOffSetItems().addAll(gcOffSetVchrDTO.getItems()));
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.carry.over.copy.error") + e.getMessage());
        }
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void executeDisposeInitToAdjustOffSetItem(GcCalcEnvContext env, String comBaseUnit, Map<String, SameCtrlChgOrgEO> sameCtrlChangedCode2EOMap) {
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        SameCtrlOffsetCond cond = this.getSameCtrlOffsetCond(calcArgments);
        QueryParamsVO queryParamsVO = this.getQueryParamsVO(calcArgments, comBaseUnit, sameCtrlChangedCode2EOMap.keySet());
        List<GcOffSetVchrDTO> carryOverGroups = this.getCarryOverGcOffsetGroupDTOs(queryParamsVO);
        if (CollectionUtils.isEmpty(carryOverGroups)) {
            return;
        }
        HashMap<String, String> ruleId2TitleMap = new HashMap<String, String>();
        List rules = this.unionRuleService.selectRuleListBySchemeIdAndRuleTypes(calcArgments.getSchemeId(), calcArgments.getPeriodStr(), null);
        rules.forEach(item -> ruleId2TitleMap.put(item.getId(), item.getTitle()));
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemList = new ArrayList<SameCtrlOffSetItemEO>();
        Collections.reverse(carryOverGroups);
        for (GcOffSetVchrDTO group : carryOverGroups) {
            if (CollectionUtils.isEmpty((Collection)group.getItems())) continue;
            SameCtrlOffsetCond copyCond = this.initSameCtrlOffsetCondBySameCtrlChgOrgEO(comBaseUnit, sameCtrlChangedCode2EOMap, cond, group);
            sameCtrlOffSetItemList.addAll(this.convertOffsetItemToSameCtrlList(env, group, copyCond, ruleId2TitleMap));
        }
        if (CollectionUtils.isEmpty(sameCtrlOffSetItemList)) {
            return;
        }
        this.sameCtrlOffSetItemDao.saveAll(sameCtrlOffSetItemList);
    }

    private SameCtrlOffsetCond initSameCtrlOffsetCondBySameCtrlChgOrgEO(String comBaseUnit, Map<String, SameCtrlChgOrgEO> sameCtrlChangedCode2EOMap, SameCtrlOffsetCond cond, GcOffSetVchrDTO group) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = (GcOffSetVchrItemDTO)group.getItems().get(0);
        String changeUnitId = comBaseUnit.equals(gcOffSetVchrItemDTO.getUnitId()) ? gcOffSetVchrItemDTO.getOppUnitId() : gcOffSetVchrItemDTO.getUnitId();
        SameCtrlChgOrgEO sameCtrlChgOrgEO = sameCtrlChangedCode2EOMap.get(changeUnitId);
        SameCtrlOffsetCond copyCond = new SameCtrlOffsetCond();
        BeanUtils.copyProperties(cond, copyCond);
        copyCond.setSameParentCode(sameCtrlChgOrgEO.getSameParentCode());
        copyCond.setChangeDate(sameCtrlChgOrgEO.getChangeDate());
        copyCond.setSameCtrlChgId(sameCtrlChgOrgEO.getId());
        return copyCond;
    }

    private SameCtrlOffsetCond getSameCtrlOffsetCond(GcCalcArgmentsDTO calcArgments) {
        SameCtrlOffsetCond cond = new SameCtrlOffsetCond();
        BeanUtils.copyProperties(calcArgments, cond);
        cond.setMergeUnitCode(calcArgments.getOrgId());
        cond.setPeriodStr(calcArgments.getPeriodStr());
        cond.setOrgType(calcArgments.getOrgType());
        cond.setTaskId(calcArgments.getTaskId());
        cond.setSchemeId(calcArgments.getSchemeId());
        return cond;
    }

    private List<SameCtrlOffSetItemEO> convertOffsetItemToSameCtrlList(GcCalcEnvContext env, GcOffSetVchrDTO group, SameCtrlOffsetCond cond, Map<String, String> ruleId2TitleMap) {
        List items = group.getItems();
        String ruleId = CollectionUtils.isEmpty((Collection)items) ? null : ((GcOffSetVchrItemDTO)items.get(0)).getRuleId();
        String mRecid = env.getCalcContextExpandVariableCenter().getPreGernerateOffsetItemMRecid(ruleId);
        ArrayList<SameCtrlOffSetItemEO> sameCtrlOffSetItemList = new ArrayList<SameCtrlOffSetItemEO>();
        double sumOffsetValue = 0.0;
        for (GcOffSetVchrItemDTO item : group.getItems()) {
            if (item.getDisableFlag().booleanValue()) {
                return Collections.emptyList();
            }
            SameCtrlOffSetItemEO sameCtrlOffSetItemEO = new SameCtrlOffSetItemEO();
            BeanUtils.copyProperties(item, (Object)sameCtrlOffSetItemEO);
            sameCtrlOffSetItemEO.setId(UUIDOrderUtils.newUUIDStr());
            sameCtrlOffSetItemEO.setSrcId(item.getId());
            sameCtrlOffSetItemEO.setmRecid(mRecid);
            sameCtrlOffSetItemEO.setCreateTime(Calendar.getInstance().getTime());
            sameCtrlOffSetItemEO.setDefaultPeriod(cond.getPeriodStr());
            sameCtrlOffSetItemEO.setTaskId(cond.getTaskId());
            sameCtrlOffSetItemEO.setSchemeId(cond.getSchemeId());
            String addMemo = cond.getPeriodStr().substring(0, 4) + OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeName();
            String memo = StringUtils.isEmpty((String)item.getMemo()) ? addMemo : item.getMemo() + "\uff0c" + addMemo;
            sameCtrlOffSetItemEO.setMemo(memo);
            sameCtrlOffSetItemEO.setUnitCode(item.getUnitId());
            sameCtrlOffSetItemEO.setOppUnitCode(item.getOppUnitId());
            YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
            GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO mergeUnitVo = orgCenterTool.getOrgByCode(cond.getMergeUnitCode());
            sameCtrlOffSetItemEO.setInputUnitParents(mergeUnitVo.getParentStr());
            sameCtrlOffSetItemEO.setSameParentCode(cond.getSameParentCode());
            sameCtrlOffSetItemEO.setUnitChangeYear(DateUtils.getYearOfDate((Date)cond.getChangeDate()));
            sameCtrlOffSetItemEO.setSameCtrlChgId(cond.getSameCtrlChgId());
            sameCtrlOffSetItemEO.setSameCtrlSrcType(SameCtrlSrcTypeEnum.END_INVEST_CALC_INIT.getCode());
            sameCtrlOffSetItemEO.setOrgType(cond.getOrgType());
            sameCtrlOffSetItemEO.setRuleTitle(ruleId2TitleMap.get(item.getRuleId()));
            sameCtrlOffSetItemEO.setOrient(item.getOrient().getValue());
            sameCtrlOffSetItemEO.setInputUnitCode(cond.getMergeUnitCode());
            int orient = sameCtrlOffSetItemEO.getOrient();
            if (orient == OrientEnum.D.getValue()) {
                sameCtrlOffSetItemEO.setOffSetDebit(item.getOffSetDebit());
            }
            if (orient == OrientEnum.C.getValue()) {
                sameCtrlOffSetItemEO.setOffSetCredit(item.getOffSetCredit());
            }
            this.sameCtrlOffSetItemDao.checkItemDTO(sameCtrlOffSetItemEO);
            sumOffsetValue = NumberUtils.sum((double)sumOffsetValue, (double)NumberUtils.sub((Double)sameCtrlOffSetItemEO.getOffSetDebit(), (Double)sameCtrlOffSetItemEO.getOffSetCredit()));
            sameCtrlOffSetItemList.add(sameCtrlOffSetItemEO);
        }
        if (!NumberUtils.isZreo((Double)sumOffsetValue)) {
            this.logger.error("\u672c\u5bf9\u65b9\u5355\u4f4d\u3010{}\u3011\u548c\u3010{}\u3011\u590d\u5236\u7b2c0\u671f\u501f\u8d37\u62b5\u9500\u91d1\u989d\u4e0d\u7b49\uff0c\u4e0d\u5141\u8bb8\u62b5\u9500", (Object)((GcOffSetVchrItemDTO)group.getItems().get(0)).getUnitId(), (Object)((GcOffSetVchrItemDTO)group.getItems().get(0)).getOppUnitId());
        }
        return sameCtrlOffSetItemList;
    }

    private boolean checkMergeLevel(GcCalcArgmentsDTO arg, GcOrgCenterService tool, String unitCode, String oppUnitCode) {
        GcOrgCacheVO unit = tool.getOrgByCode(unitCode);
        GcOrgCacheVO oppUnit = tool.getOrgByCode(oppUnitCode);
        if (unit == null || oppUnit == null) {
            return false;
        }
        GcOrgCacheVO commonUnit = tool.getCommonUnit(unit, oppUnit);
        return commonUnit != null && Objects.equals(commonUnit.getId(), arg.getOrgId());
    }

    private List<GcOffSetVchrDTO> getCarryOverGcOffsetGroupDTOs(QueryParamsVO queryParamsVO) {
        OffsetItemInitQueryParamsVO copyQueryParamsVO = new OffsetItemInitQueryParamsVO();
        BeanUtils.copyProperties(queryParamsVO, copyQueryParamsVO);
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.CARRY_OVER_FAIRVALUE.getSrcTypeValue());
        copyQueryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
        return this.gcOffSetInitService.queryOffSetGroupDTOs(copyQueryParamsVO);
    }

    private String getVirtualUnit(Map<String, String> unit2virtualUnitMapCache, GcOrgCenterService currentMangerInstance, String unitId) {
        if (unit2virtualUnitMapCache.containsKey(unitId)) {
            return unit2virtualUnitMapCache.get(unitId);
        }
        GcOrgCacheVO vritualUnit = currentMangerInstance.getOrgByCode(unitId);
        if (vritualUnit == null || vritualUnit.getTypeFieldValue("DISPOSALVIRTUALUNITID") == null) {
            unit2virtualUnitMapCache.put(unitId, null);
            return null;
        }
        unit2virtualUnitMapCache.put(unitId, vritualUnit.getTypeFieldValue("DISPOSALVIRTUALUNITID").toString());
        return unit2virtualUnitMapCache.get(unitId);
    }

    private String getCurrentMergeLLevelVirtualUnit(String comBaseUnit, GcOffSetVchrItemDTO gcOffSetVchrItemDTO, Map<String, String> unit2virtualUnitMapCache, GcOrgCenterService tool, GcCalcArgmentsDTO calcArgments) {
        String investedUnitId = comBaseUnit.equals(gcOffSetVchrItemDTO.getUnitId()) ? gcOffSetVchrItemDTO.getOppUnitId() : gcOffSetVchrItemDTO.getUnitId();
        String virtualUnit = this.getVirtualUnit(unit2virtualUnitMapCache, tool, investedUnitId);
        if (!this.checkMergeLevel(calcArgments, tool, comBaseUnit, virtualUnit)) {
            return null;
        }
        return virtualUnit;
    }

    private QueryParamsVO getQueryParamsVO(GcCalcArgmentsDTO calcArgments, String comBaseUnit, Set<String> changeUnitSet) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(calcArgments, queryParamsVO);
        ArrayList<String> unitList = new ArrayList<String>();
        unitList.add(comBaseUnit);
        ArrayList<String> oppUnitList = new ArrayList<String>(changeUnitSet);
        queryParamsVO.setUnitIdList(unitList);
        queryParamsVO.setOppUnitIdList(oppUnitList);
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        if (consolidatedTaskVO != null) {
            queryParamsVO.setSystemId(consolidatedTaskVO.getSystemId());
        }
        String reportSystemId = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getSystemIdBySchemeId(calcArgments.getSchemeId(), calcArgments.getPeriodStr());
        List<String> ruleTypes = Arrays.asList(RuleTypeEnum.DIRECT_INVESTMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT.getCode(), RuleTypeEnum.DIRECT_INVESTMENT_SEGMENT.getCode(), RuleTypeEnum.INDIRECT_INVESTMENT_SEGMENT.getCode(), RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode());
        List ruleIds = ((UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class)).findAllRuleIdsBySystemIdAndRuleTypes(reportSystemId, ruleTypes);
        queryParamsVO.setRules(ruleIds);
        return queryParamsVO;
    }

    private OffsetItemInitQueryParamsVO queryParamsVO(QueryParamsVO paramsVO) {
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = new OffsetItemInitQueryParamsVO();
        BeanUtils.copyProperties(paramsVO, offsetItemInitQueryParamsVO);
        offsetItemInitQueryParamsVO.setOrgId(paramsVO.getOrgId());
        this.initOffSetSrcTypes(offsetItemInitQueryParamsVO);
        return offsetItemInitQueryParamsVO;
    }

    private OffsetItemInitQueryParamsVO queryOffsetItemInitQueryParamsVO(GcCalcArgmentsDTO paramsVO) {
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = new OffsetItemInitQueryParamsVO();
        BeanUtils.copyProperties(paramsVO, offsetItemInitQueryParamsVO);
        offsetItemInitQueryParamsVO.setOrgId(paramsVO.getOrgId());
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(paramsVO.getSchemeId(), paramsVO.getPeriodStr());
        if (consolidatedTaskVO != null) {
            offsetItemInitQueryParamsVO.setSystemId(consolidatedTaskVO.getSystemId());
        }
        this.initOffSetSrcTypes(offsetItemInitQueryParamsVO);
        return offsetItemInitQueryParamsVO;
    }

    private void initOffSetSrcTypes(OffsetItemInitQueryParamsVO queryParamsVO) {
        ArrayList<Integer> offSetSrcTypes = new ArrayList<Integer>();
        offSetSrcTypes.add(OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue());
        offSetSrcTypes.add(OffSetSrcTypeEnum.CARRY_OVER_FAIRVALUE.getSrcTypeValue());
        queryParamsVO.setOffSetSrcTypes(offSetSrcTypes);
    }

    private Set<String> deleteHistory(QueryParamsVO queryParamsVO) {
        CalcLogUtil.getInstance().log(this.getClass(), "deleteHistory-" + OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue() + "\u6279\u91cf\u5220\u9664", (Object)queryParamsVO);
        return this.adjustingEntryService.deleteAllOffsetEntrys(queryParamsVO);
    }

    private List<GcOffSetVchrDTO> queryOffSetGroupDTOs(GcCalcEnvContext env, QueryParamsVO paramsVO) {
        OffsetItemInitQueryParamsVO queryParamsVO = this.queryParamsVO(paramsVO);
        ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        if (consolidatedTaskVO != null) {
            queryParamsVO.setSystemId(consolidatedTaskVO.getSystemId());
        }
        List gcOffSetVchrDTOS = this.gcOffSetInitService.queryOffSetGroupDTOs(queryParamsVO);
        IGcCalcInitOffSetItemCopyFilter initOffSetItemCopyFilter = (IGcCalcInitOffSetItemCopyFilter)SpringContextUtils.getBean(IGcCalcInitOffSetItemCopyFilter.class);
        if (null != initOffSetItemCopyFilter) {
            gcOffSetVchrDTOS = initOffSetItemCopyFilter.filter(env, gcOffSetVchrDTOS, paramsVO);
        }
        return gcOffSetVchrDTOS;
    }

    private void save(GcCalcEnvContext env, GcOffSetVchrDTO group, OffsetItemInitQueryParamsVO queryParamsVO) {
        List items = group.getItems();
        String ruleId = CollectionUtils.isEmpty((Collection)items) ? null : ((GcOffSetVchrItemDTO)items.get(0)).getRuleId();
        String preGernerateOffsetItemMRecid = env.getCalcContextExpandVariableCenter().getPreGernerateOffsetItemMRecid(ruleId);
        group.onlySetMrecid(preGernerateOffsetItemMRecid);
        Date time = Calendar.getInstance().getTime();
        for (GcOffSetVchrItemDTO item : group.getItems()) {
            if (item.getDisableFlag().booleanValue()) {
                return;
            }
            item.setSrcId(item.getId());
            item.setId(UUID.randomUUID().toString());
            item.setmRecid(group.getMrecid());
            item.setCreateTime(time);
            item.setModifyTime(time);
            item.setTaskId(queryParamsVO.getTaskId());
            item.setSchemeId(queryParamsVO.getSchemeId());
            item.setAcctYear(queryParamsVO.getAcctYear());
            item.setAcctPeriod(queryParamsVO.getAcctPeriod());
            item.setDefaultPeriod(queryParamsVO.getPeriodStr());
            String addMemo = queryParamsVO.getAcctYear() + GcI18nUtil.getMessage((String)"gc.calculate.init.copy.memo");
            String memo = StringUtils.isEmpty((String)item.getMemo()) ? addMemo : item.getMemo() + "\uff0c" + addMemo;
            item.setMemo(memo);
        }
        this.adjustingEntryService.save(group);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig$Item
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO$Item
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.gcreport.unionrule.vo.BaseRuleVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.gcreport.workingpaper.querytask;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FlexibleFetchConfig;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractWorkingPaperQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public abstract class AbstractWorkingPaperRuleQueryTask
extends AbstractWorkingPaperQueryTask {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractWorkingPaperRuleQueryTask.class);
    protected static final String GROUP_PHS = "phs";
    protected static final String GROUP_NOT_PHS = "notphs";

    @Override
    protected List<WorkingPaperDxsItemDTO> getWorkingPaperOffsetItemDTOs(WorkingPaperQueryCondition condition) {
        long time1 = System.currentTimeMillis();
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.covertQueryParamsVO(condition);
        if (!CollectionUtils.isEmpty((Collection)condition.getBaseRuleProp())) {
            List ruleIds = condition.getBaseRuleProp().stream().map(rule -> rule.getId()).collect(Collectors.toList());
            queryParamsVO.setRules(ruleIds);
        }
        GcOffSetItemAdjustCoreService adjustCoreService = (GcOffSetItemAdjustCoreService)SpringContextUtils.getBean(GcOffSetItemAdjustCoreService.class);
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        List offsetList = adjustCoreService.listWithOnlyItems(queryParamsDTO);
        if (CollectionUtils.isEmpty((Collection)offsetList)) {
            return Collections.emptyList();
        }
        GcOffSetAppOffsetService offsetVchrItemService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
        List<WorkingPaperDxsItemDTO> workingPaperDxsItemDTOS = offsetList.stream().filter(eo -> !StringUtils.isEmpty((String)eo.getSubjectCode())).map(eo -> offsetVchrItemService.convertEO2DTO(eo)).map(dto -> {
            WorkingPaperDxsItemDTO workingPaperDxsItemDTO = WorkingPaperDxsItemDTO.empty();
            workingPaperDxsItemDTO.setRuleId(dto.getRuleId());
            workingPaperDxsItemDTO.setOffSetCredit(ConverterUtils.getAsBigDecimal((Object)dto.getOffSetCredit(), (BigDecimal)BigDecimal.ZERO));
            workingPaperDxsItemDTO.setOffSetDebit(ConverterUtils.getAsBigDecimal((Object)dto.getOffSetDebit(), (BigDecimal)BigDecimal.ZERO));
            workingPaperDxsItemDTO.setCredit(ConverterUtils.getAsBigDecimal((Object)dto.getCredit(), (BigDecimal)BigDecimal.ZERO));
            workingPaperDxsItemDTO.setDebit(ConverterUtils.getAsBigDecimal((Object)dto.getDebit(), (BigDecimal)BigDecimal.ZERO));
            workingPaperDxsItemDTO.setSubjectCode(dto.getSubjectCode());
            workingPaperDxsItemDTO.setOrgCode(dto.getUnitId());
            workingPaperDxsItemDTO.setElmMode(ConverterUtils.getAsString((Object)dto.getElmMode()));
            workingPaperDxsItemDTO.setOffSetSrcType(dto.getOffSetSrcType());
            workingPaperDxsItemDTO.setSubjectOrient(dto.getOrient());
            workingPaperDxsItemDTO.setDiffc(ConverterUtils.getAsBigDecimal((Object)dto.getDiffc(), (BigDecimal)BigDecimal.ZERO));
            workingPaperDxsItemDTO.setDiffd(ConverterUtils.getAsBigDecimal((Object)dto.getDiffd(), (BigDecimal)BigDecimal.ZERO));
            workingPaperDxsItemDTO.setFetchSetGroupId(dto.getFetchSetGroupId());
            return workingPaperDxsItemDTO;
        }).collect(Collectors.toList());
        long time2 = System.currentTimeMillis();
        LOGGER.debug("\u5de5\u4f5c\u5e95\u7a3f\u83b7\u53d6\u62b5\u9500\u8bb0\u5f55\u8017\u65f6\uff1a{}", (Object)(time2 - time1));
        return workingPaperDxsItemDTOS;
    }

    protected Map<String, List<WorkingPaperDxsItemDTO>> getSubject2DxsItemsMap(WorkingPaperQueryCondition condition, String systemId, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap) {
        Map<String, Set<String>> subjectCode2ChildSubjectCodesContainsSelfMap = WorkingPaperQueryUtils.getSubjectCode2AllChildCodeSetMap(subjectCode2DataMap.keySet(), systemId, true);
        LinkedHashMap<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMap = new LinkedHashMap<String, List<WorkingPaperDxsItemDTO>>();
        List<WorkingPaperDxsItemDTO> workingPaperOffsetItemDTOs = this.getWorkingPaperOffsetItemDTOs(condition);
        subjectCode2DataMap.keySet().stream().forEach(subjectCode -> {
            Set subjectCode2ChildSubjectCodesContainsSelf = (Set)subjectCode2ChildSubjectCodesContainsSelfMap.get(subjectCode);
            List currSubjectOffsetItems = CollectionUtils.isEmpty((Collection)subjectCode2ChildSubjectCodesContainsSelf) ? Collections.emptyList() : workingPaperOffsetItemDTOs.stream().filter(offset -> subjectCode2ChildSubjectCodesContainsSelf.contains(offset.getSubjectCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(currSubjectOffsetItems)) {
                subject2DxsItemsMap.put((String)subjectCode, currSubjectOffsetItems);
            }
        });
        return subject2DxsItemsMap;
    }

    protected Map<String, AbstractUnionRule> getRuleId2DataMap(String systemId) {
        UnionRuleService unionRuleService = (UnionRuleService)SpringContextUtils.getBean(UnionRuleService.class);
        List unionRules = unionRuleService.selectAllRuleListByReportSystemAndRuleTypes(systemId, null);
        if (CollectionUtils.isEmpty((Collection)unionRules)) {
            return Collections.emptyMap();
        }
        Map ruleId2DataMap = unionRules.stream().collect(Collectors.toMap(AbstractUnionRule::getId, rule -> rule, (e1, e2) -> e1, LinkedHashMap::new));
        return ruleId2DataMap;
    }

    protected List<WorkingPaperTableDataVO> buildWorkingPaperTableDataByRuleAndSubject(WorkingPaperQueryCondition condition) {
        String systemId = this.getSystemId(condition.getSchemeID(), condition.getPeriodStr());
        Map<String, AbstractUnionRule> ruleId2DataMap = this.getRuleId2DataMap(systemId);
        Map<String, ConsolidatedSubjectVO> subjectCode2DataMap = this.getSubjectCode2DataMap(condition);
        Map<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMap = this.getSubject2DxsItemsMap(condition, systemId, subjectCode2DataMap);
        TreeMap<AbstractUnionRule, TreeMap<AbstractUnionRule, List<AbstractUnionRule>>> ruleFirstAndLeafGroupId2DataMap = this.getRuleFirstAndLeafGroupId2DataMap(condition, ruleId2DataMap);
        ArrayList<WorkingPaperTableDataVO> workingPaperTableDataVOS = new ArrayList<WorkingPaperTableDataVO>();
        ruleFirstAndLeafGroupId2DataMap.forEach((firstGroup, firstGroup2LeafGroupMap) -> firstGroup2LeafGroupMap.forEach((leafGroup, ruleDatas) -> {
            WorkingPaperTableDataVO workingPaperTableDataVOByRuleGroup = this.buildBlankWorkingPaperTableDataByRuleGroup((AbstractUnionRule)firstGroup, (AbstractUnionRule)leafGroup);
            workingPaperTableDataVOS.add(workingPaperTableDataVOByRuleGroup);
            Collections.sort(ruleDatas, (o1, o2) -> {
                Integer ruleOrder1 = ConverterUtils.getAsInteger((Object)o1.getSortOrder(), (Integer)0);
                Integer ruleOrder2 = ConverterUtils.getAsInteger((Object)o2.getSortOrder(), (Integer)0);
                return ruleOrder1.compareTo(ruleOrder2);
            });
            ruleDatas.forEach(rule -> {
                Map<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMapByRuleId = this.filterByRuleId(subject2DxsItemsMap, rule.getId());
                List<WorkingPaperTableDataVO> workingPaperTableDataVOByRule = this.buildBlankWorkingPaperTableDataByRule(subjectCode2DataMap, (AbstractUnionRule)rule, subject2DxsItemsMapByRuleId);
                workingPaperTableDataVOS.addAll(workingPaperTableDataVOByRule);
            });
        }));
        if (CollectionUtils.isEmpty((Collection)condition.getBaseRuleProp())) {
            WorkingPaperTableDataVO workingPaperTableDataVOByRuleGroup = this.buildBlankWorkingPaperTableDataByRuleGroup(null, null);
            workingPaperTableDataVOS.add(workingPaperTableDataVOByRuleGroup);
            List<WorkingPaperTableDataVO> workingPaperTableDataVOByNullRule = this.buildBlankWorkingPaperTableDataByNullRule(subjectCode2DataMap, subject2DxsItemsMap);
            workingPaperTableDataVOS.addAll(workingPaperTableDataVOByNullRule);
        }
        return workingPaperTableDataVOS;
    }

    private Map<String, List<WorkingPaperDxsItemDTO>> filterByRuleId(Map<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMap, String ruleId) {
        LinkedHashMap<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMapByRuleId = new LinkedHashMap<String, List<WorkingPaperDxsItemDTO>>();
        for (Map.Entry<String, List<WorkingPaperDxsItemDTO>> entry : subject2DxsItemsMap.entrySet()) {
            String subjectCode = entry.getKey();
            List<WorkingPaperDxsItemDTO> items = entry.getValue();
            List filteredItems = items.stream().filter(item -> ruleId.equals(item.getRuleId())).collect(Collectors.toList());
            if (filteredItems.isEmpty()) continue;
            subject2DxsItemsMapByRuleId.put(subjectCode, filteredItems);
        }
        return subject2DxsItemsMapByRuleId;
    }

    public static Map<String, List<WorkingPaperDxsItemDTO>> groupByRuleIdAndFetchSetGroupId(Map<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMapByRuleId, String fetchSetGroupId) {
        if (MapUtils.isEmpty(subject2DxsItemsMapByRuleId)) {
            return Collections.emptyMap();
        }
        if (fetchSetGroupId == null) {
            fetchSetGroupId = "";
        }
        String currFetchSetGroupId = fetchSetGroupId;
        LinkedHashMap<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMapByRuleIdAndFetchSetGroupId = new LinkedHashMap<String, List<WorkingPaperDxsItemDTO>>();
        subject2DxsItemsMapByRuleId.forEach((subjectCode, dxsItems) -> {
            if (CollectionUtils.isEmpty((Collection)dxsItems)) {
                return;
            }
            dxsItems.stream().forEach(dxsItem -> {
                String itemFetchSetGroupId = ConverterUtils.getAsString((Object)dxsItem.getFetchSetGroupId(), (String)"");
                if (currFetchSetGroupId.equals(itemFetchSetGroupId)) {
                    subject2DxsItemsMapByRuleIdAndFetchSetGroupId.computeIfAbsent((String)subjectCode, k -> new ArrayList()).add(dxsItem);
                }
            });
        });
        return subject2DxsItemsMapByRuleIdAndFetchSetGroupId;
    }

    private static List<String> reorderSubjectCodesBySubjectOrient(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2DxsItemsByRuleIdAndFetchSetGroupId, List<String> subjectCodesInRule, OrientEnum subjectOrient) {
        LinkedHashSet<String> orderSubjectCodes = new LinkedHashSet<String>();
        for (String string : subjectCodesInRule) {
            orderSubjectCodes.add(string);
        }
        for (Map.Entry entry : subjectCode2DxsItemsByRuleIdAndFetchSetGroupId.entrySet()) {
            List itemDTOS;
            ConsolidatedSubjectVO consolidatedSubjectVO;
            String subjectCode = (String)entry.getKey();
            if (orderSubjectCodes.contains(subjectCode) || (consolidatedSubjectVO = subjectCode2DataMap.get(subjectCode)) == null || !Boolean.TRUE.equals(consolidatedSubjectVO.getLeafFlag()) || CollectionUtils.isEmpty((Collection)(itemDTOS = (List)entry.getValue()))) continue;
            orderSubjectCodes.add(subjectCode);
        }
        List<String> orderSubjectCodes2 = orderSubjectCodes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return orderSubjectCodes2;
    }

    private Map<String, List<WorkingPaperDxsItemDTO>> filterByRuleIdAndFetchSetGroupIdAndSubjectOrientAndAmtOrient(Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2DxsItemsByRuleIdAndFetchSetGroupId, @NotNull String filterBySubjectCode, OrientEnum filterByAmtOrient) {
        List filterItems;
        LinkedHashMap<String, List<WorkingPaperDxsItemDTO>> subjectCode2DxsItemsByRuleIdAndFetchSetGroupIdAndOrient = new LinkedHashMap<String, List<WorkingPaperDxsItemDTO>>();
        List<WorkingPaperDxsItemDTO> items = subjectCode2DxsItemsByRuleIdAndFetchSetGroupId.get(filterBySubjectCode);
        if (items == null) {
            items = Collections.emptyList();
        }
        if (!CollectionUtils.isEmpty(filterItems = items.stream().filter(item -> filterByAmtOrient == null || filterByAmtOrient.equals((Object)(BigDecimal.ZERO.compareTo(item.getOffSetDebit()) != 0 ? OrientEnum.D : OrientEnum.C))).collect(Collectors.toList()))) {
            subjectCode2DxsItemsByRuleIdAndFetchSetGroupIdAndOrient.put(filterBySubjectCode, filterItems);
        }
        return subjectCode2DxsItemsByRuleIdAndFetchSetGroupIdAndOrient;
    }

    protected Set<String> getConditionUnionRuleIds(WorkingPaperQueryCondition condition, Map<String, AbstractUnionRule> ruleId2DataMap) {
        Collection conditionRules;
        List baseRuleProps = condition.getBaseRuleProp();
        if (CollectionUtils.isEmpty((Collection)baseRuleProps)) {
            conditionRules = ruleId2DataMap.values();
        } else {
            Set conditionRuleIds = baseRuleProps.stream().map(BaseRuleVO::getId).filter(Objects::nonNull).collect(Collectors.toSet());
            conditionRules = ruleId2DataMap.values().stream().filter(rule -> conditionRuleIds.contains(rule.getId())).collect(Collectors.toList());
        }
        Set<String> ruleIds = conditionRules.stream().map(AbstractUnionRule::getId).filter(Objects::nonNull).collect(Collectors.toSet());
        return ruleIds;
    }

    private TreeMap<AbstractUnionRule, TreeMap<AbstractUnionRule, List<AbstractUnionRule>>> getRuleFirstAndLeafGroupId2DataMap(WorkingPaperQueryCondition condition, Map<String, AbstractUnionRule> ruleId2DataMap) {
        Set<String> conditionRuleIds = this.getConditionUnionRuleIds(condition, ruleId2DataMap);
        Map<String, List<AbstractUnionRule>> ruleId2ParentRulesMap = this.getRuleId2ParentRulesMap(ruleId2DataMap);
        TreeMap<AbstractUnionRule, TreeMap<AbstractUnionRule, List<AbstractUnionRule>>> ruleGroup2LeafGroup2DatasMap = new TreeMap<AbstractUnionRule, TreeMap<AbstractUnionRule, List<AbstractUnionRule>>>((o1, o2) -> {
            Integer ruleGroupOrder1 = ConverterUtils.getAsInteger((Object)o1.getSortOrder(), (Integer)0);
            Integer ruleGroupOrder2 = ConverterUtils.getAsInteger((Object)o2.getSortOrder(), (Integer)0);
            return ruleGroupOrder1.compareTo(ruleGroupOrder2);
        });
        ruleId2ParentRulesMap.forEach((ruleId, parentRules) -> {
            if (!conditionRuleIds.contains(ruleId)) {
                return;
            }
            if (CollectionUtils.isEmpty((Collection)parentRules)) {
                return;
            }
            AbstractUnionRule ruleData = (AbstractUnionRule)ruleId2DataMap.get(ruleId);
            if (ruleData == null) {
                return;
            }
            AbstractUnionRule firstRuleGroup = (AbstractUnionRule)parentRules.get(0);
            AbstractUnionRule leafRuleGroup = (AbstractUnionRule)parentRules.get(parentRules.size() - 1);
            if (ruleGroup2LeafGroup2DatasMap.get(firstRuleGroup) == null) {
                ruleGroup2LeafGroup2DatasMap.put(firstRuleGroup, new TreeMap((o1, o2) -> {
                    Integer ruleGroupOrder1 = ConverterUtils.getAsInteger((Object)o1.getSortOrder(), (Integer)0);
                    Integer ruleGroupOrder2 = ConverterUtils.getAsInteger((Object)o2.getSortOrder(), (Integer)0);
                    return ruleGroupOrder1.compareTo(ruleGroupOrder2);
                }));
            }
            if (((TreeMap)ruleGroup2LeafGroup2DatasMap.get(firstRuleGroup)).get(leafRuleGroup) == null) {
                ((TreeMap)ruleGroup2LeafGroup2DatasMap.get(firstRuleGroup)).put(leafRuleGroup, new ArrayList());
            }
            if (ruleData != null) {
                ((List)((TreeMap)ruleGroup2LeafGroup2DatasMap.get(firstRuleGroup)).get(leafRuleGroup)).add(ruleData);
            }
        });
        return ruleGroup2LeafGroup2DatasMap;
    }

    private Map<String, List<AbstractUnionRule>> getRuleId2ParentRulesMap(Map<String, AbstractUnionRule> ruleId2DataMap) {
        HashMap<String, List<AbstractUnionRule>> ruleId2LinkedParentsMap = new HashMap<String, List<AbstractUnionRule>>();
        ruleId2DataMap.values().stream().filter(AbstractUnionRule::getLeafFlag).forEach(rule -> {
            AbstractUnionRule parentRule;
            ruleId2LinkedParentsMap.put(rule.getId(), new ArrayList());
            String tempParentRuleId = rule.getParentId();
            while (!StringUtils.isEmpty((String)tempParentRuleId) && (parentRule = (AbstractUnionRule)ruleId2DataMap.get(tempParentRuleId)) != null && !"\u5408\u5e76\u89c4\u5219".equals(parentRule.getTitle())) {
                ((List)ruleId2LinkedParentsMap.get(rule.getId())).add(parentRule);
                tempParentRuleId = parentRule.getParentId();
            }
            Collections.reverse((List)ruleId2LinkedParentsMap.get(rule.getId()));
        });
        return ruleId2LinkedParentsMap;
    }

    private WorkingPaperTableDataVO buildBlankWorkingPaperTableDataByRuleGroup(AbstractUnionRule firstGroup, AbstractUnionRule leafGroup) {
        String title = firstGroup == null ? GcI18nUtil.getMessage((String)"gc.workingpaper.code.other") : (firstGroup.getLocalizedName().equals(leafGroup.getLocalizedName()) ? firstGroup.getLocalizedName() : firstGroup.getLocalizedName().concat("-").concat(leafGroup.getLocalizedName()));
        WorkingPaperTableDataVO parentVo = new WorkingPaperTableDataVO();
        parentVo.setGzmc(title);
        parentVo.setKmcode(title);
        HashMap<String, Object> groupZbValuemap = new HashMap<String, Object>();
        groupZbValuemap.put("DXS_DEBIT_SHOWVALUE", null);
        groupZbValuemap.put("DXS_CREDIT_SHOWVALUE", null);
        parentVo.setZbvalue(groupZbValuemap);
        parentVo.setRuleid("");
        parentVo.setLeafNodeFlag(false);
        return parentVo;
    }

    protected List<WorkingPaperTableDataVO> buildBlankWorkingPaperTableDataByNullRule(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMap) {
        ArrayList<WorkingPaperTableDataVO> workingPaperTableDataVOsByRule = new ArrayList<WorkingPaperTableDataVO>();
        HashSet<WorkingPaperDxsItemDTO> nullRuleDxsItems = new HashSet<WorkingPaperDxsItemDTO>();
        subject2DxsItemsMap.forEach((subjectCode, subjectDxsItems) -> {
            if (CollectionUtils.isEmpty((Collection)subjectDxsItems)) {
                return;
            }
            List nullRuleItems = subjectDxsItems.stream().filter(subjectDxsItem -> StringUtils.isEmpty((String)subjectDxsItem.getRuleId())).collect(Collectors.toList());
            nullRuleDxsItems.addAll(nullRuleItems);
        });
        Map<String, WorkingPaperDxsItemDTO> map = this.mergeDxsItemsByRuleAndFetchSetGroupAndOffSetSrcTypeAndSubject(subjectCode2DataMap, nullRuleDxsItems);
        LinkedHashMap mapD = new LinkedHashMap();
        LinkedHashMap mapC = new LinkedHashMap();
        map.forEach((mergeKey, workingPaperDxsItemDTO) -> {
            ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(workingPaperDxsItemDTO.getSubjectCode());
            if (consolidatedSubjectVO == null) {
                return;
            }
            if (OrientEnum.D.equals((Object)OrientEnum.valueOf((Integer)consolidatedSubjectVO.getOrient()))) {
                mapD.put(mergeKey, workingPaperDxsItemDTO);
            }
            if (OrientEnum.C.equals((Object)OrientEnum.valueOf((Integer)consolidatedSubjectVO.getOrient()))) {
                mapC.put(mergeKey, workingPaperDxsItemDTO);
            }
        });
        mapD.values().forEach(workingPaperDxsItemDTO -> {
            WorkingPaperTableDataVO dataVO = this.buildBlankWorkingPaperTableDataByItem(subjectCode2DataMap, null, (WorkingPaperDxsItemDTO)workingPaperDxsItemDTO, true, this.isPhs(workingPaperDxsItemDTO.getOffSetSrcType()));
            workingPaperTableDataVOsByRule.add(dataVO);
        });
        mapC.values().forEach(workingPaperDxsItemDTO -> {
            WorkingPaperTableDataVO dataVO = this.buildBlankWorkingPaperTableDataByItem(subjectCode2DataMap, null, (WorkingPaperDxsItemDTO)workingPaperDxsItemDTO, false, this.isPhs(workingPaperDxsItemDTO.getOffSetSrcType()));
            workingPaperTableDataVOsByRule.add(dataVO);
        });
        return workingPaperTableDataVOsByRule;
    }

    protected List<WorkingPaperTableDataVO> buildBlankWorkingPaperTableDataByRule(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, AbstractUnionRule rule, Map<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMapByRuleId) {
        ArrayList<WorkingPaperTableDataVO> workingPaperTableDataVOsByRule = new ArrayList<WorkingPaperTableDataVO>();
        RuleTypeEnum ruleTypeEnum = RuleTypeEnum.codeOf((String)rule.getRuleType());
        switch (ruleTypeEnum) {
            case FIXED_TABLE: 
            case FIXED_ASSETS: 
            case INVENTORY: 
            case DIRECT_INVESTMENT: 
            case DIRECT_INVESTMENT_SEGMENT: 
            case INDIRECT_INVESTMENT: 
            case INDIRECT_INVESTMENT_SEGMENT: 
            case PUBLIC_VALUE_ADJUSTMENT: 
            case FLOAT_LINE: {
                List srcDebitSubjectCodeList = rule.getSrcDebitSubjectCodeList();
                List srcCreditSubjectCodeList = rule.getSrcCreditSubjectCodeList();
                List<WorkingPaperTableDataVO> workingPaperTableDataVOS = this.buildRuleSingleSubjectWorkPaperItemByFetchGroupId(subjectCode2DataMap, rule, subject2DxsItemsMapByRuleId, srcDebitSubjectCodeList, srcCreditSubjectCodeList, null, null);
                workingPaperTableDataVOsByRule.addAll(workingPaperTableDataVOS);
                break;
            }
            case LEASE: {
                LeaseRuleDTO leaseRuleDTO = (LeaseRuleDTO)rule;
                List debitItemList = leaseRuleDTO.getDebitItemList() == null ? Collections.emptyList() : leaseRuleDTO.getDebitItemList();
                List creditItemList = leaseRuleDTO.getCreditItemList() == null ? Collections.emptyList() : leaseRuleDTO.getCreditItemList();
                List<String> srcDebitSubjectCodeList2 = debitItemList.stream().map(LeaseRuleDTO.Item::getSubjectCode).collect(Collectors.toList());
                List<String> srcCreditSubjectCodeList2 = creditItemList.stream().map(LeaseRuleDTO.Item::getSubjectCode).collect(Collectors.toList());
                List<WorkingPaperTableDataVO> workingPaperTableDataVOS2 = this.buildRuleSingleSubjectWorkPaperItemByFetchGroupId(subjectCode2DataMap, rule, subject2DxsItemsMapByRuleId, srcDebitSubjectCodeList2, srcCreditSubjectCodeList2, null, null);
                workingPaperTableDataVOsByRule.addAll(workingPaperTableDataVOS2);
                break;
            }
            case FLEXIBLE: 
            case RELATE_TRANSACTIONS: {
                FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)rule;
                List fetchConfigList = flexibleRuleDTO.getFetchConfigList() == null ? Collections.emptyList() : flexibleRuleDTO.getFetchConfigList();
                fetchConfigList.stream().forEach(fetchConfig -> {
                    String fetchConfigTitle = fetchConfig.getDescription();
                    String fetchSetGroupId = fetchConfig.getFetchSetGroupId();
                    List debitConfigList = fetchConfig.getDebitConfigList() == null ? Collections.emptyList() : fetchConfig.getDebitConfigList();
                    List crebitConfigList = fetchConfig.getCreditConfigList() == null ? Collections.emptyList() : fetchConfig.getCreditConfigList();
                    List<String> srcDebitSubjectCodeList3 = debitConfigList.stream().map(FlexibleFetchConfig.Item::getSubjectCode).collect(Collectors.toList());
                    List<String> srcCreditSubjectCodeList3 = crebitConfigList.stream().map(FlexibleFetchConfig.Item::getSubjectCode).collect(Collectors.toList());
                    List<WorkingPaperTableDataVO> workingPaperTableDataVOS3 = this.buildRuleSingleSubjectWorkPaperItemByFetchGroupId(subjectCode2DataMap, rule, subject2DxsItemsMapByRuleId, srcDebitSubjectCodeList3, srcCreditSubjectCodeList3, fetchSetGroupId, fetchConfigTitle);
                    workingPaperTableDataVOsByRule.addAll(workingPaperTableDataVOS3);
                });
                List<WorkingPaperTableDataVO> workingPaperTableDataVOS4 = this.buildRuleSingleSubjectWorkPaperItemByFetchGroupId(subjectCode2DataMap, rule, subject2DxsItemsMapByRuleId, null, null, null, null);
                workingPaperTableDataVOsByRule.addAll(workingPaperTableDataVOS4);
                break;
            }
            case FINANCIAL_CHECK: {
                FinancialCheckRuleDTO financialCheckRuleDTO = (FinancialCheckRuleDTO)rule;
                List fetchConfigList1 = financialCheckRuleDTO.getFetchConfigList() == null ? Collections.emptyList() : financialCheckRuleDTO.getFetchConfigList();
                fetchConfigList1.stream().forEach(fetchConfig -> {
                    String fetchConfigTitle = fetchConfig.getDescription();
                    String fetchSetGroupId = ConverterUtils.getAsString((Object)fetchConfig.getFetchSetGroupId(), (String)"OTHER");
                    List debitConfigList = fetchConfig.getDebitConfigList();
                    List crebitConfigList = fetchConfig.getCreditConfigList();
                    List<String> srcDebitSubjectCodeList5 = debitConfigList.stream().map(FinancialCheckFetchConfig.Item::getSubjectCode).collect(Collectors.toList());
                    List<String> srcCreditSubjectCodeList5 = crebitConfigList.stream().map(FinancialCheckFetchConfig.Item::getSubjectCode).collect(Collectors.toList());
                    List<WorkingPaperTableDataVO> workingPaperTableDataVOS5 = this.buildRuleSingleSubjectWorkPaperItemByFetchGroupId(subjectCode2DataMap, rule, subject2DxsItemsMapByRuleId, srcDebitSubjectCodeList5, srcCreditSubjectCodeList5, fetchSetGroupId, fetchConfigTitle);
                    workingPaperTableDataVOsByRule.addAll(workingPaperTableDataVOS5);
                });
                List<WorkingPaperTableDataVO> workingPaperTableDataVOS6 = this.buildRuleSingleSubjectWorkPaperItemByFetchGroupId(subjectCode2DataMap, rule, subject2DxsItemsMapByRuleId, null, null, null, null);
                workingPaperTableDataVOsByRule.addAll(workingPaperTableDataVOS6);
                break;
            }
        }
        return workingPaperTableDataVOsByRule;
    }

    private List<WorkingPaperTableDataVO> buildRuleSingleSubjectWorkPaperItemByFetchGroupId(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, AbstractUnionRule rule, Map<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMapByRuleId, List<String> debitSubjectCodesInRule, List<String> creditSubjectCodesInRule, String fetchSetGroupId, String fetchConfigTitle) {
        List<String> finalDebitSubjectCodesInRule = debitSubjectCodesInRule == null ? Collections.emptyList() : debitSubjectCodesInRule;
        List<String> finalCreditSubjectCodesInRule = creditSubjectCodesInRule == null ? Collections.emptyList() : creditSubjectCodesInRule;
        ArrayList<WorkingPaperTableDataVO> workingPaperTableDataVOsByRule = new ArrayList<WorkingPaperTableDataVO>();
        Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2DxsItemsByRuleIdAndFetchSetGroupId = AbstractWorkingPaperRuleQueryTask.groupByRuleIdAndFetchSetGroupId(subject2DxsItemsMapByRuleId, fetchSetGroupId);
        List<String> finalReorderSubjectCodesByOrientD = AbstractWorkingPaperRuleQueryTask.reorderSubjectCodesBySubjectOrient(subjectCode2DataMap, subjectCode2DxsItemsByRuleIdAndFetchSetGroupId, finalDebitSubjectCodesInRule, OrientEnum.D);
        for (String subjectCode : finalReorderSubjectCodesByOrientD) {
            Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2NeedMergeAmtDxsItemMap;
            ConsolidatedSubjectVO consolidatedSubjectVO = subjectCode2DataMap.get(subjectCode);
            if (consolidatedSubjectVO == null || !consolidatedSubjectVO.getLeafFlag().booleanValue() || MapUtils.isEmpty(subjectCode2NeedMergeAmtDxsItemMap = this.filterByRuleIdAndFetchSetGroupIdAndSubjectOrientAndAmtOrient(subjectCode2DxsItemsByRuleIdAndFetchSetGroupId, subjectCode, OrientEnum.D)) && !finalDebitSubjectCodesInRule.contains(subjectCode)) continue;
            List<WorkingPaperTableDataVO> workingPaperTableDataVOs = this.buildRuleSingleSubjectWorkPaperItem(subjectCode2DataMap, rule, subjectCode2NeedMergeAmtDxsItemMap, true, subjectCode, fetchSetGroupId, fetchConfigTitle);
            workingPaperTableDataVOsByRule.addAll(workingPaperTableDataVOs);
        }
        List<String> finalReorderSubjectCodesByOrientC = AbstractWorkingPaperRuleQueryTask.reorderSubjectCodesBySubjectOrient(subjectCode2DataMap, subjectCode2DxsItemsByRuleIdAndFetchSetGroupId, finalCreditSubjectCodesInRule, OrientEnum.C);
        for (String subjectCode : finalReorderSubjectCodesByOrientC) {
            Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2NeedMergeAmtDxsItemMap;
            ConsolidatedSubjectVO consolidatedSubjectVO = subjectCode2DataMap.get(subjectCode);
            if (consolidatedSubjectVO == null || !consolidatedSubjectVO.getLeafFlag().booleanValue() || MapUtils.isEmpty(subjectCode2NeedMergeAmtDxsItemMap = this.filterByRuleIdAndFetchSetGroupIdAndSubjectOrientAndAmtOrient(subjectCode2DxsItemsByRuleIdAndFetchSetGroupId, subjectCode, OrientEnum.C)) && !finalCreditSubjectCodesInRule.contains(subjectCode)) continue;
            List<WorkingPaperTableDataVO> workingPaperTableDataVOs = this.buildRuleSingleSubjectWorkPaperItem(subjectCode2DataMap, rule, subjectCode2NeedMergeAmtDxsItemMap, false, subjectCode, fetchSetGroupId, fetchConfigTitle);
            workingPaperTableDataVOsByRule.addAll(workingPaperTableDataVOs);
        }
        return workingPaperTableDataVOsByRule;
    }

    private List<WorkingPaperTableDataVO> buildRuleSingleSubjectWorkPaperItem(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, AbstractUnionRule rule, Map<String, List<WorkingPaperDxsItemDTO>> subject2DxsItemsMap, Boolean isRuleDebitItem, String ruleSubjectCode, String fetchSetGroupId, String fetchConfigTitle) {
        WorkingPaperTableDataVO dataVO;
        WorkingPaperDxsItemDTO dxsItemDTO;
        ArrayList<WorkingPaperTableDataVO> dataVOs = new ArrayList<WorkingPaperTableDataVO>();
        if (StringUtils.isEmpty((String)ruleSubjectCode)) {
            return dataVOs;
        }
        ConsolidatedSubjectVO consolidatedSubjectVO = subjectCode2DataMap.get(ruleSubjectCode);
        if (consolidatedSubjectVO == null) {
            return dataVOs;
        }
        ArrayList<WorkingPaperDxsItemDTO> phsItems = new ArrayList<WorkingPaperDxsItemDTO>();
        ArrayList<WorkingPaperDxsItemDTO> notPhsItems = new ArrayList<WorkingPaperDxsItemDTO>();
        List<WorkingPaperDxsItemDTO> subjectDxsItems = subject2DxsItemsMap.get(ruleSubjectCode);
        if (!CollectionUtils.isEmpty(subjectDxsItems)) {
            subjectDxsItems.stream().filter(subjectDxsItem -> rule.getId().equals(subjectDxsItem.getRuleId()) && String.valueOf(fetchSetGroupId).equals(String.valueOf(subjectDxsItem.getFetchSetGroupId()))).forEach(subjectDxsItem -> {
                OffSetSrcTypeEnum offSetSrcType = subjectDxsItem.getOffSetSrcType();
                if (this.isPhs(offSetSrcType)) {
                    phsItems.add((WorkingPaperDxsItemDTO)subjectDxsItem);
                } else {
                    notPhsItems.add((WorkingPaperDxsItemDTO)subjectDxsItem);
                }
            });
        }
        if (!CollectionUtils.isEmpty(notPhsItems)) {
            dxsItemDTO = this.getMergeOffSetVchrItem(consolidatedSubjectVO, notPhsItems);
            dxsItemDTO.setSubjectCode(ruleSubjectCode);
            dxsItemDTO.setRuleId(rule.getId());
            dxsItemDTO.setFetchSetGroupId(fetchSetGroupId);
            dxsItemDTO.setFetchConfigTitle(fetchConfigTitle);
            dataVO = this.buildBlankWorkingPaperTableDataByItem(subjectCode2DataMap, rule, dxsItemDTO, isRuleDebitItem, false);
            dataVOs.add(dataVO);
        }
        if (!CollectionUtils.isEmpty(phsItems)) {
            dxsItemDTO = this.getMergeOffSetVchrItem(consolidatedSubjectVO, phsItems);
            dxsItemDTO.setSubjectCode(ruleSubjectCode);
            dxsItemDTO.setRuleId(rule.getId());
            dxsItemDTO.setFetchSetGroupId(fetchSetGroupId);
            dxsItemDTO.setFetchConfigTitle(fetchConfigTitle);
            dataVO = this.buildBlankWorkingPaperTableDataByItem(subjectCode2DataMap, rule, dxsItemDTO, isRuleDebitItem, true);
            dataVOs.add(dataVO);
        }
        if (CollectionUtils.isEmpty(phsItems) && CollectionUtils.isEmpty(notPhsItems)) {
            dxsItemDTO = WorkingPaperDxsItemDTO.empty();
            dxsItemDTO.setSubjectCode(ruleSubjectCode);
            dxsItemDTO.setRuleId(rule.getId());
            dxsItemDTO.setFetchSetGroupId(fetchSetGroupId);
            dxsItemDTO.setFetchConfigTitle(fetchConfigTitle);
            dataVO = this.buildBlankWorkingPaperTableDataByItem(subjectCode2DataMap, rule, dxsItemDTO, isRuleDebitItem, false);
            dataVOs.add(dataVO);
        }
        return dataVOs;
    }

    protected String getOffSetSrcTypeGroupKey(OffSetSrcTypeEnum offSetSrcType) {
        String offSetSrcTypeValueGroupKey = this.isPhs(offSetSrcType) ? GROUP_PHS : GROUP_NOT_PHS;
        return offSetSrcTypeValueGroupKey;
    }

    protected List<OffSetSrcTypeEnum> getPhsOffSetSrcTypes() {
        return Arrays.asList(OffSetSrcTypeEnum.PHS, OffSetSrcTypeEnum.MANUAL_OFFSET_INPUT);
    }

    protected boolean isPhs(OffSetSrcTypeEnum offSetSrcType) {
        if (offSetSrcType == null) {
            return false;
        }
        List<OffSetSrcTypeEnum> phsOffSetSrcTypes = this.getPhsOffSetSrcTypes();
        boolean isPhs = phsOffSetSrcTypes.contains(offSetSrcType);
        return isPhs;
    }

    protected WorkingPaperTableDataVO buildBlankWorkingPaperTableDataByItem(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, AbstractUnionRule rule, WorkingPaperDxsItemDTO dxsItemDTO, Boolean isRuleDebitItem, boolean isPhs) {
        String fetchConfigTitle;
        WorkingPaperTableDataVO vo = new WorkingPaperTableDataVO();
        String subjectCode = dxsItemDTO.getSubjectCode();
        OffSetSrcTypeEnum offSetSrcType = dxsItemDTO.getOffSetSrcType();
        String fetchSetGroupId = dxsItemDTO.getFetchSetGroupId();
        String string = fetchConfigTitle = StringUtils.isEmpty((String)dxsItemDTO.getFetchConfigTitle()) ? "--" : dxsItemDTO.getFetchConfigTitle();
        if (rule != null) {
            vo.setGzmc("    " + rule.getLocalizedName());
            vo.setRuleid(rule.getId());
            vo.setOrder(ConverterUtils.getAsString((Object)rule.getSortOrder(), (String)"0"));
        }
        vo.setOffsetSrcType(this.getOffSetSrcTypeGroupKey(offSetSrcType));
        vo.setZbvalue(new HashMap(16));
        vo.setZbvalueStr(new HashMap(16));
        vo.setFetchSetGroupId(fetchSetGroupId);
        vo.setFetchConfigTitle(fetchConfigTitle);
        if (StringUtils.isEmpty((String)subjectCode)) {
            return vo;
        }
        ConsolidatedSubjectVO consolidatedSubjectVO = subjectCode2DataMap.get(subjectCode);
        vo.setKmcode(subjectCode);
        BigDecimal offSetDebit = BigDecimal.ZERO;
        BigDecimal offSetCredit = BigDecimal.ZERO;
        Map<String, String> orientCode2Title = ((GcBaseDataService)SpringContextUtils.getBean(GcBaseDataService.class)).queryBasedataItems("MD_ORIENT").stream().collect(Collectors.toMap(GcBaseData::getCode, GcBaseData::getTitle));
        if (consolidatedSubjectVO != null) {
            if (Boolean.TRUE.equals(isRuleDebitItem)) {
                offSetDebit = NumberUtils.sub((BigDecimal)dxsItemDTO.getOffSetDebit(), (BigDecimal)dxsItemDTO.getOffSetCredit());
                offSetCredit = BigDecimal.ZERO;
            } else if (Boolean.FALSE.equals(isRuleDebitItem)) {
                offSetCredit = NumberUtils.sub((BigDecimal)dxsItemDTO.getOffSetCredit(), (BigDecimal)dxsItemDTO.getOffSetDebit());
                offSetDebit = BigDecimal.ZERO;
            } else {
                offSetCredit = dxsItemDTO.getOffSetCredit();
                offSetDebit = dxsItemDTO.getOffSetDebit();
            }
            vo.setKmorient(orientCode2Title.get(ConverterUtils.getAsString((Object)consolidatedSubjectVO.getOrient(), (String)"-1")));
            vo.setOrient(consolidatedSubjectVO.getOrient());
            vo.setKmid(consolidatedSubjectVO.getId());
            if (isPhs) {
                vo.setKmname("\u3010".concat(consolidatedSubjectVO.getCode()).concat("\u3011").concat(consolidatedSubjectVO.getTitle()).concat("\uff08").concat(OffSetSrcTypeEnum.PHS.getSrcTypeName()).concat("\uff09"));
            } else {
                vo.setKmname("\u3010".concat(consolidatedSubjectVO.getCode()).concat("\u3011").concat(consolidatedSubjectVO.getTitle()));
            }
        }
        vo.getZbvalue().put("DXS_DEBIT", offSetDebit);
        vo.getZbvalueStr().put("DXS_DEBIT_SHOWVALUE", AbstractWorkingPaperRuleQueryTask.formatBigDecimal(offSetDebit));
        vo.getZbvalue().put("DXS_CREDIT", offSetCredit);
        vo.getZbvalueStr().put("DXS_CREDIT_SHOWVALUE", AbstractWorkingPaperRuleQueryTask.formatBigDecimal(offSetCredit));
        return vo;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeDxsItemsByRuleAndFetchSetGroupAndOffSetSrcTypeAndSubject(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Set<WorkingPaperDxsItemDTO> dxsItemDTOS) {
        TreeMap<String, WorkingPaperDxsItemDTO> mergeDxsItemsByRuleAndSubjectAndFetchSetGroupAndOffSetSrcTypeMap = new TreeMap<String, WorkingPaperDxsItemDTO>(Comparator.naturalOrder());
        dxsItemDTOS.stream().forEach(dxsItemDTO -> {
            String subjectCode = dxsItemDTO.getSubjectCode();
            String ruleId = dxsItemDTO.getRuleId();
            String fetchSetGroupId = dxsItemDTO.getFetchSetGroupId();
            String fetchConfigTitle = dxsItemDTO.getFetchConfigTitle();
            if (StringUtils.isEmpty((String)subjectCode)) {
                return;
            }
            ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(subjectCode);
            if (consolidatedSubjectVO == null) {
                return;
            }
            String mergeKey = ruleId + "_" + fetchSetGroupId + "_" + this.getOffSetSrcTypeGroupKey(dxsItemDTO.getOffSetSrcType()) + "_" + subjectCode;
            if (mergeDxsItemsByRuleAndSubjectAndFetchSetGroupAndOffSetSrcTypeMap.get(mergeKey) == null) {
                WorkingPaperDxsItemDTO empty = WorkingPaperDxsItemDTO.empty();
                empty.setSubjectCode(subjectCode);
                empty.setSubjectOrient(OrientEnum.valueOf((Integer)consolidatedSubjectVO.getOrient()));
                empty.setRuleId(ruleId);
                empty.setFetchSetGroupId(fetchSetGroupId);
                empty.setFetchConfigTitle(fetchConfigTitle);
                empty.setOffSetSrcType(dxsItemDTO.getOffSetSrcType());
                mergeDxsItemsByRuleAndSubjectAndFetchSetGroupAndOffSetSrcTypeMap.put(mergeKey, empty);
            }
            WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeDxsItemsByRuleAndSubjectAndFetchSetGroupAndOffSetSrcTypeMap.get(mergeKey);
            this.mergeOffSetVchrItem(consolidatedSubjectVO, (WorkingPaperDxsItemDTO)dxsItemDTO, mergeWorkingPaperOffsetItemDTO);
        });
        return mergeDxsItemsByRuleAndSubjectAndFetchSetGroupAndOffSetSrcTypeMap;
    }
}


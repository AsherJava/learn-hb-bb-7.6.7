/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.PrimaryWorkpaperSettingDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 */
package com.jiuqi.gcreport.workingpaper.querytask.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.PrimaryWorkpaperSettingDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;

public class WorkingPaperQueryUtils {
    public static QueryParamsVO covertQueryParamsVO(WorkingPaperQueryCondition condition) {
        List oppUnitIds;
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setAcctPeriod(condition.getAcctPeriod());
        queryParamsVO.setAcctYear(condition.getAcctYear());
        queryParamsVO.setPeriodStr(condition.getPeriodStr());
        queryParamsVO.setPeriodType(condition.getPeriodType());
        queryParamsVO.setOrgId(condition.getOrgid());
        queryParamsVO.setCurrency(condition.getCurrencyCode());
        queryParamsVO.setElmModes(condition.getElmModes());
        queryParamsVO.setFilterCondition(condition.getFilterCondition());
        queryParamsVO.setSelectAdjustCode(condition.getSelectAdjustCode());
        List unitIds = condition.getUnitIds();
        if (!StringUtils.isEmpty((String)condition.getSchemeID())) {
            queryParamsVO.setSchemeId(condition.getSchemeID());
        }
        if (!CollectionUtils.isEmpty((Collection)unitIds)) {
            queryParamsVO.setUnitIdList(unitIds);
        }
        if (!CollectionUtils.isEmpty((Collection)(oppUnitIds = condition.getOppUnitIds()))) {
            queryParamsVO.setOppUnitIdList(oppUnitIds);
        }
        queryParamsVO.setOrgType(condition.getOrg_type());
        queryParamsVO.setPageSize(-1);
        queryParamsVO.setPageNum(-1);
        queryParamsVO.setTaskId(condition.getTaskID());
        ArrayList<String> showColumns = new ArrayList<String>();
        showColumns.add("OFFSETSRCTYPE");
        List otherColumnKeys = condition.getOtherShowColumnKeys();
        if (otherColumnKeys != null && otherColumnKeys.size() > 0) {
            showColumns.addAll(otherColumnKeys);
        }
        queryParamsVO.setOtherShowColumns(showColumns);
        queryParamsVO.setArbitrarilyMerge(condition.getArbitrarilyMerge());
        queryParamsVO.setOrgBatchId(condition.getOrgBatchId());
        queryParamsVO.setOrgComSupLength(condition.getOrgComSupLength());
        queryParamsVO.setSystemId(WorkingPaperQueryUtils.getSystemID(condition));
        queryParamsVO.setQueryAllColumns(true);
        queryParamsVO.setFilterDisableItem(true);
        return queryParamsVO;
    }

    public static List<ConsolidatedSubjectVO> getAllSubject(WorkingPaperQueryCondition condition) {
        ArrayList<ConsolidatedSubjectVO> retList = new ArrayList<ConsolidatedSubjectVO>();
        String systemid = WorkingPaperQueryUtils.getSystemID(condition);
        if (null == systemid) {
            return retList;
        }
        ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        List allSubject = consolidatedSubjectService.listSubjectsBySystemIdWithSortOrder(systemid);
        for (ConsolidatedSubjectEO eo : allSubject) {
            ConsolidatedSubjectVO vo = new ConsolidatedSubjectVO();
            BeanUtils.copyProperties(eo, vo);
            retList.add(vo);
        }
        return retList;
    }

    public static String getSystemID(WorkingPaperQueryCondition condition) {
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        String systemId = consolidatedTaskService.getConsolidatedSystemIdBySchemeId(condition.getSchemeID(), condition.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5408\u5e76\u4f53\u7cfb\uff0c\u8bf7\u5148\u5728[\u5408\u5e76\u4f53\u7cfb\u7ba1\u7406] \u4e2d\u6dfb\u52a0\u4efb\u52a1");
        }
        return systemId;
    }

    public static QueryParamsVO convertPenInfoToOffsetParams(WorkingPaperPentrationQueryCondtion penInfo, boolean isPage) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(penInfo, queryParamsVO);
        queryParamsVO.setTaskId(penInfo.getTaskID());
        queryParamsVO.setSchemeId(penInfo.getSchemeID());
        queryParamsVO.setOrgType(penInfo.getOrg_type());
        queryParamsVO.setPeriodStr(penInfo.getPeriodStr());
        queryParamsVO.setOrgId(penInfo.getOrgid());
        queryParamsVO.setCurrency(penInfo.getCurrencyCode());
        queryParamsVO.setSelectAdjustCode(penInfo.getSelectAdjustCode());
        queryParamsVO.setOtherShowColumns(penInfo.getOtherShowColumnKeys());
        if (isPage) {
            queryParamsVO.setPageSize(penInfo.getPageSize());
            queryParamsVO.setPageNum(penInfo.getPageNum());
        } else {
            queryParamsVO.setPageSize(-1);
            queryParamsVO.setPageNum(-1);
        }
        queryParamsVO.setFilterDisableItem(true);
        ConsolidatedTaskService taskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        String systemId = taskService.getConsolidatedSystemIdBySchemeId(penInfo.getSchemeID(), penInfo.getPeriodStr());
        queryParamsVO.setSystemId(systemId);
        return queryParamsVO;
    }

    public static List<String> listSubjectCodes(WorkingPaperPentrationQueryCondtion penInfo, WorkingPaperTableDataVO workingPaperTableDataVO) {
        PrimaryWorkpaperSettingDao primaryWorkpaperSettingDao = (PrimaryWorkpaperSettingDao)SpringContextUtils.getBean(PrimaryWorkpaperSettingDao.class);
        HashSet<String> subjectCodes = new HashSet<String>();
        if (penInfo.isCurPrimarySubjectOffsetEntry()) {
            List primaryWorkPaperSettings = primaryWorkpaperSettingDao.querySetRecordsByTypeId(penInfo.getPrimaryTableType());
            primaryWorkPaperSettings.stream().map(PrimaryWorkPaperSettingEO::getBoundSubjectCodes).map(boundSubjectCodes -> Arrays.asList(boundSubjectCodes.split(";"))).forEach(subjectCodes::addAll);
        } else {
            PrimaryWorkPaperSettingEO primaryWorkPaperSettingEO = (PrimaryWorkPaperSettingEO)primaryWorkpaperSettingDao.get((Serializable)((Object)workingPaperTableDataVO.getPrimarySettingId()));
            String boundSubjectCodes2 = primaryWorkPaperSettingEO.getBoundSubjectCodes();
            subjectCodes.addAll(Arrays.asList(boundSubjectCodes2.split(";")));
        }
        ConsolidatedTaskService taskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        String systemId = taskService.getConsolidatedSystemIdBySchemeId(penInfo.getSchemeID(), penInfo.getPeriodStr());
        ArrayList<String> allChildrenSubjectCodes = new ArrayList<String>();
        if (!StringUtils.isEmpty((String)systemId)) {
            Map<String, Set<String>> subjectCode2AllChildCodeSetMap = WorkingPaperQueryUtils.getSubjectCode2AllChildCodeSetMap(subjectCodes, systemId, true);
            subjectCode2AllChildCodeSetMap.entrySet().forEach(entry -> allChildrenSubjectCodes.addAll((Collection)entry.getValue()));
        }
        return allChildrenSubjectCodes;
    }

    public static Set<String> listAllChildCodesContainsSelf(String systemId, String subjectCode) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        List allChildrenSubjects = subjectService.listAllChildrenSubjects(systemId, subjectCode);
        if (allChildrenSubjects == null) {
            return Collections.emptySet();
        }
        return Stream.concat(allChildrenSubjects.stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toList()).stream(), Collections.singletonList(subjectCode).stream()).collect(Collectors.toSet());
    }

    public static Map<String, Set<String>> getSubjectCode2AllChildCodeSetMap(Set<String> subjectCodes, String reportSystemId, boolean containsSelf) {
        LinkedHashMap<String, Set<String>> parentCode2AllChildrenCodesMap = new LinkedHashMap<String, Set<String>>();
        subjectCodes.remove(null);
        subjectCodes.remove("");
        List allSubjectEos = ((ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).listAllSubjectsBySystemId(reportSystemId);
        Map parentCode2DirectChildrenCodesMap = allSubjectEos.stream().collect(Collectors.groupingBy(ConsolidatedSubjectEO::getParentCode, Collectors.mapping(subject -> subject.getCode(), Collectors.toList())));
        for (String subjectCode : subjectCodes) {
            HashSet<String> allChildrenSubjectCodes = new HashSet<String>(MapUtils.listAllChildrens((String)subjectCode, parentCode2DirectChildrenCodesMap));
            if (containsSelf) {
                allChildrenSubjectCodes.add(subjectCode);
            }
            parentCode2AllChildrenCodesMap.put(subjectCode, allChildrenSubjectCodes);
        }
        return parentCode2AllChildrenCodesMap;
    }

    public static List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords) {
        if (org.springframework.util.CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<Map<String, Object>> sortedRecords = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> oneEntryRecords = new ArrayList<Map<String, Object>>();
        String mrecid = null;
        int entryIndex = 1;
        for (Map<String, Object> record : unSortedRecords) {
            String tempMrecid = (String)record.get("MRECID");
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    oneEntryRecords.sort(OffsetItemComparatorUtil.mapUniversalComparator());
                    sortedRecords.addAll(oneEntryRecords);
                    ((Map)oneEntryRecords.get(0)).put("rowspan", size);
                    ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
                    oneEntryRecords.clear();
                }
                mrecid = tempMrecid;
            }
            oneEntryRecords.add(record);
        }
        int size = oneEntryRecords.size();
        if (size > 0) {
            oneEntryRecords.sort(OffsetItemComparatorUtil.mapUniversalComparator());
            sortedRecords.addAll(oneEntryRecords);
            ((Map)oneEntryRecords.get(0)).put("rowspan", size);
            ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
    }

    public static List<GcOrgCacheVO> getDirectChilds(WorkingPaperQueryCondition condi) {
        YearPeriodObject yp = new YearPeriodObject(null, condi.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condi.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO orgByID = tool.getOrgByCode(condi.getOrgid());
        if (null == orgByID || orgByID.getChildren() == null || orgByID.getChildren().size() == 0) {
            return Collections.emptyList();
        }
        List childrens = orgByID.getChildren();
        String diffUnitOrgid = orgByID.getDiffUnitId();
        return childrens.stream().filter(org -> {
            if (org.isRecoveryFlag()) {
                return false;
            }
            return !org.getCode().equals(diffUnitOrgid);
        }).collect(Collectors.toList());
    }

    public static List<GcOrgCacheVO> getOrgDirectChildsContainMergeOrgAndDiffOrg(WorkingPaperQueryCondition queryCondition) {
        YearPeriodObject yp = new YearPeriodObject(null, queryCondition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryCondition.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO mergeOrg = tool.getOrgByCode(queryCondition.getOrgid());
        List orgDrectList = mergeOrg == null || mergeOrg.getChildren() == null ? new ArrayList() : mergeOrg.getChildren();
        String diffUnitId = mergeOrg == null ? null : mergeOrg.getDiffUnitId();
        GcOrgCacheVO diffOrg = null;
        if (!StringUtils.isEmpty((String)diffUnitId)) {
            diffOrg = tool.getOrgByCode(diffUnitId);
        }
        boolean isContainDiffOrg = false;
        boolean isContainMergeOrg = false;
        ConsolidatedOptionCacheService optionCacheService = (ConsolidatedOptionCacheService)SpringContextUtils.getBean(ConsolidatedOptionCacheService.class);
        ConsolidatedOptionVO consolidatedOptionVO = optionCacheService.getConOptionBySystemId(WorkingPaperQueryUtils.getSystemID(queryCondition));
        List offsetSpecialUnitTypes = consolidatedOptionVO.getOffsetSpecialUnitType();
        if (!CollectionUtils.isEmpty((Collection)offsetSpecialUnitTypes)) {
            if (offsetSpecialUnitTypes.contains("UNION")) {
                isContainMergeOrg = true;
            }
            if (offsetSpecialUnitTypes.contains("DIFFERENCE")) {
                isContainDiffOrg = true;
            }
        }
        List<GcOrgCacheVO> orgs = orgDrectList.stream().filter(orgDrect -> {
            if (orgDrect.isRecoveryFlag()) {
                return false;
            }
            return !orgDrect.getCode().equals(diffUnitId);
        }).collect(Collectors.toList());
        if (isContainMergeOrg && mergeOrg != null) {
            orgs.add(mergeOrg);
        }
        if (isContainDiffOrg && diffOrg != null) {
            orgs.add(diffOrg);
        }
        return orgs;
    }

    public static List<GcOrgCacheVO> getOrgDirectChildsContainSelf(WorkingPaperQueryCondition queryCondition) {
        YearPeriodObject yp = new YearPeriodObject(null, queryCondition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryCondition.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO mergeOrg = tool.getOrgByCode(queryCondition.getOrgid());
        List orgDrectList = mergeOrg == null || mergeOrg.getChildren() == null ? new ArrayList() : mergeOrg.getChildren();
        List<GcOrgCacheVO> orgs = orgDrectList.stream().filter(orgDrect -> !orgDrect.isRecoveryFlag()).collect(Collectors.toList());
        if (mergeOrg != null) {
            orgs.add(mergeOrg);
        }
        return orgs;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.PrimaryWorkpaperSettingDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 */
package com.jiuqi.gcreport.workingpaper.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.PrimaryWorkpaperSettingDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class WorkingPaperUtil {
    public static final Logger logger = LoggerFactory.getLogger(WorkingPaperUtil.class);
    private static ConcurrentHashMap<String, BaseDataVO> baseDataVOHashMap = new ConcurrentHashMap();

    public static void mergeGcOffsetDTO(GcOffSetVchrItemDTO target, GcOffSetVchrItemDTO src) {
        WorkingPaperUtil.initGcOffSetVchrItemDTO(target);
        WorkingPaperUtil.initGcOffSetVchrItemDTO(src);
        target.setOffSetDebit(Double.valueOf(target.getOffSetDebit() + src.getOffSetDebit()));
        target.setOffSetCredit(Double.valueOf(target.getOffSetCredit() + src.getOffSetCredit()));
        target.setBfOffSetDebit(Double.valueOf(target.getBfOffSetDebit() + src.getBfOffSetDebit()));
        target.setBfOffSetCredit(Double.valueOf(target.getBfOffSetCredit() + src.getBfOffSetCredit()));
        target.setCredit(Double.valueOf(target.getCredit() + src.getCredit()));
        target.setDebit(Double.valueOf(target.getDebit() + src.getDebit()));
        target.setDiffd(Double.valueOf(target.getDiffd() + src.getDiffd()));
        target.setDiffc(Double.valueOf(target.getDiffc() + src.getDiffc()));
    }

    public static void initGcOffSetVchrItemDTO(GcOffSetVchrItemDTO target) {
        if (null == target.getOffSetCredit()) {
            target.setOffSetCredit(Double.valueOf(0.0));
        }
        if (null == target.getOffSetDebit()) {
            target.setOffSetDebit(Double.valueOf(0.0));
        }
        if (null == target.getBfOffSetCredit()) {
            target.setBfOffSetCredit(Double.valueOf(0.0));
        }
        if (null == target.getBfOffSetDebit()) {
            target.setBfOffSetDebit(Double.valueOf(0.0));
        }
        if (null == target.getCredit()) {
            target.setCredit(Double.valueOf(0.0));
        }
        if (null == target.getDebit()) {
            target.setDebit(Double.valueOf(0.0));
        }
        if (null == target.getDiffd()) {
            target.setDiffd(Double.valueOf(0.0));
        }
        if (null == target.getDiffc()) {
            target.setDiffc(Double.valueOf(0.0));
        }
    }

    public static QueryParamsVO covertQueryParamsVO(WorkingPaperQueryCondition condition) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        queryParamsVO.setAcctPeriod(condition.getAcctPeriod());
        queryParamsVO.setAcctYear(condition.getAcctYear());
        queryParamsVO.setOrgId(condition.getOrgid());
        queryParamsVO.setCurrency(condition.getCurrencyCode());
        queryParamsVO.setElmModes(condition.getElmModes());
        queryParamsVO.setFilterCondition(condition.getFilterCondition());
        if (!queryParamsVO.getOrgId().equals(condition.getOppUnitId())) {
            LinkedList<String> uuids = new LinkedList<String>();
            uuids.add(condition.getOppUnitId());
            queryParamsVO.setOppUnitIdList(uuids);
        }
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (int)condition.getAcctYear(), (int)condition.getPeriodType(), (int)condition.getAcctPeriod());
        condition.setPeriodStr(yearPeriodUtil.toString());
        queryParamsVO.setOrgType(condition.getOrg_type());
        queryParamsVO.setPageSize(-1);
        queryParamsVO.setPageNum(-1);
        queryParamsVO.setTaskId(condition.getTaskID());
        if (!queryParamsVO.getOrgId().equals(condition.getUnitId())) {
            LinkedList<String> unitIdList = new LinkedList<String>();
            unitIdList.add(condition.getUnitId());
            queryParamsVO.setUnitIdList(unitIdList);
        }
        ArrayList<String> showColumns = new ArrayList<String>();
        showColumns.add("OFFSETSRCTYPE");
        List otherColumnKeys = condition.getOtherShowColumnKeys();
        if (otherColumnKeys != null && otherColumnKeys.size() > 0) {
            showColumns.addAll(otherColumnKeys);
        }
        queryParamsVO.setOtherShowColumns(showColumns);
        queryParamsVO.setCurrency(condition.getCurrencyCode());
        queryParamsVO.setPeriodStr(condition.getPeriodStr());
        queryParamsVO.setArbitrarilyMerge(condition.getArbitrarilyMerge());
        queryParamsVO.setOrgBatchId(condition.getOrgBatchId());
        queryParamsVO.setOrgComSupLength(condition.getOrgComSupLength());
        return queryParamsVO;
    }

    public static GcOffSetVchrItemDTO buildOffsetVchrItemBYMap(WorkingPaperQueryCondition queryCondition, HashMap map) {
        GcOffSetVchrItemDTO dto = new GcOffSetVchrItemDTO();
        dto.setUnitId((String)map.get("UNITID"));
        dto.setGcBusinessTypeCode((String)map.get("GCBUSINESSTYPECODE"));
        dto.setOppUnitId((String)map.get("OPPUNITID"));
        dto.setSubjectCode((String)map.get("SUBJECTCODE"));
        dto.setMemo((String)map.get("MEMO"));
        dto.setId((String)map.get("ID"));
        String offsetDebit = map.get("OFFSETDEBIT").toString();
        dto.setOffSetDebit(Double.valueOf(null == offsetDebit || offsetDebit.equalsIgnoreCase("null") || offsetDebit.equals("") ? "0" : offsetDebit.replace(",", "")));
        String offsetCredit = map.get("OFFSETCREDIT").toString();
        dto.setOffSetCredit(Double.valueOf(null == offsetCredit || offsetCredit.equalsIgnoreCase("null") || offsetCredit.equals("") ? "0" : offsetCredit.replace(",", "")));
        dto.setElmMode((Integer)map.get("ELMMODE"));
        dto.setmRecid((String)map.get("MRECID"));
        dto.setRuleId((String)map.get("RULEID"));
        Double diffC = map.get("DIFFC") == null ? 0.0 : new BigDecimal(map.get("DIFFC").toString()).doubleValue();
        dto.setDiffc(diffC);
        Double diffD = map.get("DIFFD") == null ? 0.0 : new BigDecimal(map.get("DIFFD").toString()).doubleValue();
        dto.setDiffd(diffD);
        dto.setTaskId(queryCondition.getTaskID());
        dto.setSchemeId(queryCondition.getSchemeID());
        dto.setOffSetSrcType(OffSetSrcTypeEnum.getEnumByValue((int)((Integer)map.get("OFFSETSRCTYPE"))));
        HashMap<String, String> otherColumnValueMap = new HashMap<String, String>();
        List otherColumnKeys = queryCondition.getOtherShowColumnKeys();
        Iterator iterator = otherColumnKeys.iterator();
        while (iterator.hasNext()) {
            String columnKey;
            otherColumnValueMap.put(columnKey, map.get(columnKey = (String)iterator.next()) == null ? "" : String.valueOf(map.get(columnKey)));
        }
        dto.setOtherColumnsValueMap(otherColumnValueMap);
        dto.setOrient(OrientEnum.valueOf((Integer)((Integer)map.get("ORIENT"))));
        return dto;
    }

    public static Map<String, Set<String>> getSubjectCode2AllChildCodeSetMap(Set<String> subjectCodes, String reportSystemId, boolean containsSelf) {
        HashMap<String, Set<String>> parentCode2AllChildrenCodesMap = new HashMap<String, Set<String>>();
        subjectCodes.remove(null);
        subjectCodes.remove("");
        List allSubjectEos = ((ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class)).listAllSubjectsBySystemId(reportSystemId);
        Map parentCode2DirectChildrenCodesMap = allSubjectEos.stream().collect(Collectors.groupingBy(ConsolidatedSubjectEO::getParentCode, Collectors.mapping(subject -> subject.getCode(), Collectors.toList())));
        for (String subjectCode : subjectCodes) {
            HashSet<String> allChildrenSubjectCodes = new HashSet<String>(MapUtils.listAllChildrens((String)subjectCode, parentCode2DirectChildrenCodesMap));
            if (containsSelf) {
                allChildrenSubjectCodes.add(subjectCode);
            }
            if (CollectionUtils.isEmpty(allChildrenSubjectCodes)) continue;
            parentCode2AllChildrenCodesMap.put(subjectCode, allChildrenSubjectCodes);
        }
        return parentCode2AllChildrenCodesMap;
    }

    public static Map<String, Set<String>> getSubjectCode2AllChildCodeSetMap(Set<String> subjectCodes, String reportSystemId) {
        return WorkingPaperUtil.getSubjectCode2AllChildCodeSetMap(subjectCodes, reportSystemId, false);
    }

    public static List<ConsolidatedSubjectVO> getParentSubjectCode(Set<String> subjectCodeList, HashMap<String, ConsolidatedSubjectVO> cacheKM) {
        ArrayList<ConsolidatedSubjectVO> retList = new ArrayList<ConsolidatedSubjectVO>();
        ConcurrentHashMap<String, ConsolidatedSubjectVO> allParentKM = new ConcurrentHashMap<String, ConsolidatedSubjectVO>();
        int maxSize = cacheKM.size();
        for (String subjectCode : subjectCodeList) {
            ConsolidatedSubjectVO vo = cacheKM.get(subjectCode);
            if (null == vo) continue;
            retList.add(vo);
            boolean isEnd = false;
            for (int p = 0; !isEnd && p <= maxSize; ++p) {
                String parentCode = vo.getParentCode();
                ConsolidatedSubjectVO parentVo = cacheKM.get(parentCode);
                if (parentCode != null && !parentCode.equalsIgnoreCase("") && parentVo.getParentCode() != null) {
                    allParentKM.put(parentCode, parentVo);
                } else {
                    isEnd = true;
                }
                vo = cacheKM.get(parentCode);
            }
        }
        retList.addAll(allParentKM.values().stream().collect(Collectors.toList()));
        return retList;
    }

    public static HSSFCellStyle getExelCellStyle(HSSFWorkbook workbook, String cellType) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        switch (cellType) {
            case "head": {
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                break;
            }
            case "text": {
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                break;
            }
            case "amount": {
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                break;
            }
            case "merge": {
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
            }
        }
        return cellStyle;
    }

    public static void clearBaseDataCache() {
        baseDataVOHashMap.clear();
    }

    public static void initKMCache(Map<String, ConsolidatedSubjectVO> cacheKM, List<ConsolidatedSubjectVO> subjectVoList, WorkingPaperQueryCondition condition) {
        List<ConsolidatedSubjectVO> voList = subjectVoList == null ? WorkingPaperUtil.getAllSubject(condition) : subjectVoList;
        for (ConsolidatedSubjectVO vo : voList) {
            cacheKM.put(vo.getCode(), vo);
        }
    }

    public static List<ConsolidatedSubjectVO> getAllSubject(WorkingPaperQueryCondition condition) {
        ArrayList<ConsolidatedSubjectVO> retList = new ArrayList<ConsolidatedSubjectVO>();
        String systemid = WorkingPaperUtil.getSystemID(condition);
        if (null == systemid) {
            return retList;
        }
        ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        List allSubject = consolidatedSubjectService.listAllSubjectsBySystemId(systemid);
        for (ConsolidatedSubjectEO eo : allSubject) {
            ConsolidatedSubjectVO vo = new ConsolidatedSubjectVO();
            BeanUtils.copyProperties(eo, vo);
            retList.add(vo);
        }
        return retList;
    }

    public static String getSystemID(WorkingPaperQueryCondition condition) {
        ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        return consolidatedTaskService.getConsolidatedSystemIdBySchemeId(condition.getSchemeID(), condition.getPeriodStr());
    }

    private static WorkingPaperTableDataVO getParentWorking(Map<String, WorkingPaperTableDataVO> parentWorking, String parentId, Map<String, ConsolidatedSubjectVO> subjectVOHashMap) {
        if (parentId == null) {
            return null;
        }
        WorkingPaperTableDataVO vo = parentWorking.get(parentId);
        if (vo != null) {
            return vo;
        }
        ConsolidatedSubjectVO subjectVO = subjectVOHashMap.get(parentId);
        vo = WorkingPaperUtil.getParentWorking(parentWorking, subjectVO.getParentCode(), subjectVOHashMap);
        return vo;
    }

    public static QueryParamsVO convertPenInfoToOffsetParams(WorkingPaperPentrationQueryCondtion penInfo) {
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(penInfo, queryParamsVO);
        queryParamsVO.setTaskId(penInfo.getTaskID());
        queryParamsVO.setSchemeId(penInfo.getSchemeID());
        queryParamsVO.setOrgType(penInfo.getOrg_type());
        queryParamsVO.setPeriodStr(penInfo.getPeriodStr());
        queryParamsVO.setOrgId(penInfo.getOrgid());
        queryParamsVO.setCurrency(penInfo.getCurrency());
        queryParamsVO.setOtherShowColumns(penInfo.getOtherShowColumnKeys());
        queryParamsVO.setPageSize(-1);
        queryParamsVO.setPageNum(-1);
        return queryParamsVO;
    }

    public static List<String> listSubjectCodes(WorkingPaperPentrationQueryCondtion penInfo) {
        PrimaryWorkpaperSettingDao primaryWorkpaperSettingDao = (PrimaryWorkpaperSettingDao)SpringContextUtils.getBean(PrimaryWorkpaperSettingDao.class);
        HashSet<String> subjectCodes = new HashSet<String>();
        if (penInfo.isCurPrimarySubjectOffsetEntry()) {
            List primaryWorkPaperSettings = primaryWorkpaperSettingDao.querySetRecordsByTypeId(penInfo.getPrimaryTableType());
            primaryWorkPaperSettings.stream().map(PrimaryWorkPaperSettingEO::getBoundSubjectCodes).map(boundSubjectCodes -> Arrays.asList(boundSubjectCodes.split(";"))).forEach(subjectCodes::addAll);
        } else {
            PrimaryWorkPaperSettingEO primaryWorkPaperSettingEO = (PrimaryWorkPaperSettingEO)primaryWorkpaperSettingDao.get((Serializable)((Object)penInfo.getPrimarySettingId()));
            String boundSubjectCodes2 = primaryWorkPaperSettingEO.getBoundSubjectCodes();
            subjectCodes.addAll(Arrays.asList(boundSubjectCodes2.split(";")));
        }
        ConsolidatedTaskService taskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        String systemId = taskService.getConsolidatedSystemIdBySchemeId(penInfo.getSchemeID(), penInfo.getPeriodStr());
        ArrayList<String> allChildrenSubjectCodes = new ArrayList<String>();
        if (!StringUtils.isEmpty((String)systemId)) {
            Map<String, Set<String>> subjectCode2AllChildCodeSetMap = WorkingPaperUtil.getSubjectCode2AllChildCodeSetMap(subjectCodes, systemId, true);
            subjectCode2AllChildCodeSetMap.entrySet().forEach(entry -> allChildrenSubjectCodes.addAll((Collection)entry.getValue()));
        }
        return allChildrenSubjectCodes;
    }

    public static Set<String> listAllChildCodesContainsSelf(WorkingPaperPentrationQueryCondtion pentrationQueryCondtion) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        String currPentrationKMDM = pentrationQueryCondtion.getCurrPentrationKMDM();
        String[] subjectCodes = currPentrationKMDM.split(";");
        HashSet<String> allChildrenSubjectCodeSet = new HashSet<String>();
        for (String subjectCode : subjectCodes) {
            List allChildrenSubjects = subjectService.listAllChildrenSubjects(WorkingPaperUtil.getSystemID((WorkingPaperQueryCondition)pentrationQueryCondtion), currPentrationKMDM);
            allChildrenSubjects.stream().forEach(item -> allChildrenSubjectCodeSet.add(item.getCode()));
            allChildrenSubjectCodeSet.add(subjectCode);
        }
        return allChildrenSubjectCodeSet;
    }

    public static List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords) {
        if (org.springframework.util.CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<Map<String, Object>> sortedRecords = new ArrayList<Map<String, Object>>();
        ArrayList<Map<String, Object>> oneEntryRecords = new ArrayList<Map<String, Object>>();
        String mrecid = null;
        Comparator comparator = (record1, record2) -> {
            int result = MapUtils.compareInt((Map)record2, (Map)record1, (Object)"ORIENT");
            if (result == 0) {
                result = MapUtils.compareStr((Map)record1, (Map)record2, (Object)"SUBJECTCODE");
            }
            return result;
        };
        int entryIndex = 1;
        for (Map<String, Object> record : unSortedRecords) {
            String tempMrecid = (String)record.get("MRECID");
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    oneEntryRecords.sort(comparator);
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
            oneEntryRecords.sort(comparator);
            sortedRecords.addAll(oneEntryRecords);
            ((Map)oneEntryRecords.get(0)).put("rowspan", size);
            ((Map)oneEntryRecords.get(0)).put("index", entryIndex++);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
    }

    public static void pageOffsetByMrecids(int pageNum, int pageSize, Set<String> mRecids) {
        if (pageNum > 0 && pageSize > 0) {
            List tempmRecids = mRecids.stream().sorted(Comparator.comparing(mRecid -> mRecid)).collect(Collectors.toList());
            int mRecidsSize = tempmRecids.size();
            int begin = (pageNum - 1) * pageSize <= mRecidsSize ? (pageNum - 1) * pageSize : 0;
            int range = pageNum * pageSize > mRecidsSize ? mRecidsSize : pageNum * pageSize;
            tempmRecids = tempmRecids.subList(begin, range);
            mRecids.clear();
            mRecids.addAll(tempmRecids);
        }
    }
}


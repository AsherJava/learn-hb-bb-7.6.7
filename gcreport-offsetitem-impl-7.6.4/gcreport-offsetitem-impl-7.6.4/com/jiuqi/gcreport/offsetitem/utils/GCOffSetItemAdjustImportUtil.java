/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.ImportUtils
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dao.UnionRuleDao
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  org.json.JSONException
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.offsetitem.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.ImportUtils;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.utils.CommonImportUtil;
import com.jiuqi.gcreport.offsetitem.utils.GcOffSetItemImportTempCache;
import com.jiuqi.gcreport.offsetitem.vo.GcInputAdjustVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

public class GCOffSetItemAdjustImportUtil {
    private static Map<String, Map<String, String>> tableName2MapDictTitle2Id;

    public static List<List<GcInputAdjustVO>> parse(MultipartFile importData, QueryParamsVO queryParamsVO) throws JSONException {
        Workbook workbook = null;
        try {
            try {
                workbook = new XSSFWorkbook(importData.getInputStream());
            }
            catch (Exception ex) {
                workbook = new HSSFWorkbook(importData.getInputStream());
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("Excel\u89e3\u6790\u5931\u8d25", (Throwable)e);
        }
        int numberOfSheets = workbook.getNumberOfSheets();
        if (numberOfSheets == 0) {
            return null;
        }
        if (numberOfSheets == 1) {
            return GCOffSetItemAdjustImportUtil.offSetImport(workbook.getSheetAt(0), queryParamsVO);
        }
        for (int i = 0; i < numberOfSheets; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            if (!GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.offset").equals(sheet.getSheetName())) continue;
            return GCOffSetItemAdjustImportUtil.offSetImport(sheet, queryParamsVO);
        }
        throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u4e2d\u6709\u591a\u4e2a\u9875\u7b7e\uff0c\u4f46\u662f\u672a\u627e\u5230\u53ef\u4ee5\u5bfc\u5165\u7684\u5df2\u62b5\u9500\u9875\u7b7e\uff0c\u8bf7\u68c0\u67e5\u6587\u4ef6\uff01");
    }

    private static List<List<GcInputAdjustVO>> offSetImport(Sheet sheet, QueryParamsVO queryParamsVO) {
        GcOffSetItemImportTempCache tempCache = CommonImportUtil.initGcOffSetItemImportTempCache(queryParamsVO);
        int lastRowNum = sheet.getLastRowNum();
        String[] importCvsColumns = null;
        String srcId = null;
        StringBuilder errorLog = new StringBuilder("");
        ArrayList<List<GcInputAdjustVO>> allList = new ArrayList<List<GcInputAdjustVO>>();
        for (int excelRowNum = 0; excelRowNum <= lastRowNum; ++excelRowNum) {
            Row row = sheet.getRow(excelRowNum);
            if (excelRowNum == 0) {
                importCvsColumns = GCOffSetItemAdjustImportUtil.parseTitle(row);
                HashSet<String> titleSet = new HashSet<String>(Arrays.asList(importCvsColumns));
                HashSet<String> requiredTitleSet = new HashSet<String>();
                requiredTitleSet.add("\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b");
                requiredTitleSet.add("\u5408\u5e76\u89c4\u5219");
                requiredTitleSet.add("\u672c\u65b9\u5355\u4f4d");
                requiredTitleSet.add("\u5bf9\u65b9\u5355\u4f4d");
                requiredTitleSet.add("\u79d1\u76ee");
                requiredTitleSet.add("\u501f\u65b9\u91d1\u989d");
                requiredTitleSet.add("\u8d37\u65b9\u91d1\u989d");
                if (titleSet.containsAll(requiredTitleSet)) continue;
                throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u6a21\u677f\u4e0d\u6b63\u786e\uff0c\u8bf7\u68c0\u67e5\u6587\u4ef6\uff01");
            }
            if (GCOffSetItemAdjustImportUtil.isRowEmpty(row)) continue;
            ArrayList<GcInputAdjustVO> listOfGroup = new ArrayList<GcInputAdjustVO>();
            int mergeNum = 0;
            if (row.getCell(0) == null) {
                errorLog.append("\u7b2c" + (row.getRowNum() + 1) + "\u884c\u6570\u636e\u4e0d\u7b26\u5408\u89c4\u8303\n");
                continue;
            }
            Map<String, Object> regionRange = GCOffSetItemAdjustImportUtil.isMergedRegion(sheet, row.getRowNum(), row.getCell(0).getColumnIndex());
            if (!regionRange.isEmpty()) {
                Integer firstRow = (Integer)regionRange.get("firstRow");
                Integer lastRow = (Integer)regionRange.get("lastRow");
                mergeNum = lastRow - firstRow;
                for (int i = 0; i <= mergeNum; ++i) {
                    row = sheet.getRow(excelRowNum + i);
                    String errorMsg = GCOffSetItemAdjustImportUtil.oneRowOffSet(row, importCvsColumns, listOfGroup, tempCache, queryParamsVO);
                    if (StringUtils.isEmpty((String)errorMsg)) continue;
                    errorLog.append("\u7b2c").append(row.getRowNum() + 1).append("\u884c\u6570\u636e\uff1a").append(errorMsg).append("\n");
                }
                excelRowNum = lastRow;
            }
            srcId = UUIDOrderUtils.newUUIDStr();
            boolean isCorporate = "MD_ORG_CORPORATE".equals(queryParamsVO.getOrgType());
            for (GcInputAdjustVO journalVO : listOfGroup) {
                journalVO.setSrcID(srcId);
                if (isCorporate) {
                    journalVO.setElmMode(Integer.valueOf(OffsetElmModeEnum.INPUT_ITEM.getValue()));
                    continue;
                }
                journalVO.setElmMode(Integer.valueOf(OffsetElmModeEnum.MANAGE_INPUT_ITEM.getValue()));
            }
            allList.add(listOfGroup);
        }
        if (errorLog.length() > 0) {
            errorLog.append("\u5bfc\u5165\u5931\u8d25");
            throw new RuntimeException(errorLog.toString());
        }
        tempCache.clear();
        return allList;
    }

    private static String oneRowOffSet(Row row, String[] importCvsColumns, List<GcInputAdjustVO> listOfGroup, GcOffSetItemImportTempCache tempCache, QueryParamsVO queryParamsVO) {
        Map<String, DimensionVO> allDimMap = tempCache.getAllDimMap();
        Map<String, UnionRuleEO> ruleCode2RuleVoMap = tempCache.getRuleCode2RuleVoMap();
        Map<String, UnionRuleEO> ruleTitle2RuleVoMap = tempCache.getRuleTitle2RuleVoMap();
        Map<String, String> subjectCode2Title = tempCache.getSubjectCode2Title();
        Map<String, String> subjectTitle2Code = tempCache.getSubjectTitle2Code();
        Map<String, String> unitCode2UnitTitleMap = tempCache.getUnitCode2UnitTitleMap();
        Map<String, String> unitTitle2unitCodeMap = tempCache.getUnitTitle2unitCodeMap();
        Map<String, Map<String, String>> tableName2MapDictCode2Title = tempCache.getTableName2MapDictCode2Title();
        Map<String, Map<String, String>> tableName2MapDictTitle2Code = tempCache.getTableName2MapDictTitle2Code();
        Map<String, List<ConsolidatedSubjectEO>> parentCodeGroupMap = tempCache.getParentCodeGroupMap();
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(null, queryParamsVO.getPeriodStr()));
        String systemId = queryParamsVO.getSystemId();
        int lastCellNum = row.getLastCellNum();
        GcInputAdjustVO journalVO = new GcInputAdjustVO();
        for (int k = 0; k < lastCellNum; ++k) {
            String dimCode;
            Cell cell;
            if (k >= importCvsColumns.length || (cell = row.getCell(k)) == null) continue;
            importCvsColumns[k] = importCvsColumns[k].trim();
            switch (importCvsColumns[k]) {
                case "\u5e8f\u53f7": {
                    break;
                }
                case "\u62b5\u9500\u65b9\u5f0f": {
                    break;
                }
                case "\u5408\u5e76\u89c4\u5219": {
                    UnionRuleEO unionRuleEO;
                    String cellValue = ImportUtils.getCellValue((Cell)cell);
                    if (StringUtils.isEmpty((String)cellValue)) break;
                    UnionRuleEO unionRuleEO2 = unionRuleEO = ruleCode2RuleVoMap.containsKey(cellValue) ? ruleCode2RuleVoMap.get(cellValue) : ruleTitle2RuleVoMap.get(cellValue.trim());
                    if (unionRuleEO == null) break;
                    journalVO.setUnionRuleId(unionRuleEO.getId());
                    journalVO.setRuleTitle(unionRuleEO.getTitle());
                    journalVO.setGcBusinessTypeCode(unionRuleEO.getBusinessTypeCode());
                    journalVO.setRuleParentId(unionRuleEO.getParentId());
                    break;
                }
                case "\u672c\u65b9\u5355\u4f4d": {
                    String unitCode;
                    String unitCellValue = ImportUtils.getCellValue((Cell)cell);
                    String string = unitCode = unitCellValue.contains("|") ? unitCellValue.substring(0, unitCellValue.indexOf("|")) : unitCellValue;
                    if (StringUtils.isEmpty((String)unitCode)) break;
                    String unitId = unitCode2UnitTitleMap.containsKey(unitCode) ? unitCode : unitTitle2unitCodeMap.get(unitCode);
                    GcOrgCacheVO unitVo = tool.getOrgByCode(unitId);
                    journalVO.setUnitVo(unitVo);
                    break;
                }
                case "\u5bf9\u65b9\u5355\u4f4d": {
                    String oppUnitCode;
                    String oppUnitCellValue = ImportUtils.getCellValue((Cell)cell);
                    String string = oppUnitCode = oppUnitCellValue.contains("|") ? oppUnitCellValue.substring(0, oppUnitCellValue.indexOf("|")) : oppUnitCellValue;
                    if (StringUtils.isEmpty((String)oppUnitCode)) break;
                    String oppUnitId = unitCode2UnitTitleMap.containsKey(oppUnitCode) ? oppUnitCode : unitTitle2unitCodeMap.get(oppUnitCode);
                    GcOrgCacheVO oppUnitVo = tool.getOrgByCode(oppUnitId);
                    journalVO.setOppUnitVo(oppUnitVo);
                    break;
                }
                case "\u79d1\u76ee": {
                    String subjectCode;
                    String subject = ImportUtils.getCellValue((Cell)cell);
                    String string = subjectCode = subject.contains("|") ? subject.substring(0, subject.indexOf("|")) : subject;
                    if (StringUtils.isEmpty((String)subjectCode)) break;
                    String string2 = subjectCode = subjectCode2Title.containsKey(subjectCode) ? subjectCode : subjectTitle2Code.get(subjectCode);
                    if (StringUtils.isEmpty((String)subjectCode) || !CollectionUtils.isEmpty((Collection)parentCodeGroupMap.get(subjectCode))) break;
                    GcBaseDataVO subjectVO = GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)subjectCode, (String[])new String[]{systemId})));
                    journalVO.setSubjectCode(subjectCode);
                    journalVO.setSubjectVo(subjectVO);
                    break;
                }
                case "\u501f\u65b9\u91d1\u989d": {
                    try {
                        String debit = ImportUtils.getCellValue((Cell)cell);
                        if (StringUtils.isEmpty((String)debit)) break;
                        double debitValue = GCOffSetItemAdjustImportUtil.getDoubleValue(cell, importCvsColumns[k]);
                        journalVO.setDebit(Double.valueOf(debitValue));
                        break;
                    }
                    catch (Exception e) {
                        return e.getMessage();
                    }
                }
                case "\u8d37\u65b9\u91d1\u989d": {
                    try {
                        String credit = ImportUtils.getCellValue((Cell)cell);
                        if (StringUtils.isEmpty((String)credit)) break;
                        double creditValue = GCOffSetItemAdjustImportUtil.getDoubleValue(cell, importCvsColumns[k]);
                        journalVO.setCredit(Double.valueOf(creditValue));
                        break;
                    }
                    catch (Exception e) {
                        return e.getMessage();
                    }
                }
                case "\u63cf\u8ff0": {
                    String memo = ImportUtils.getCellValue((Cell)cell);
                    if (StringUtils.isEmpty((String)memo)) break;
                    journalVO.setMemo(memo);
                    break;
                }
                case "\u9879\u76ee\u540d\u79f0": {
                    String projectTitle = ImportUtils.getCellValue((Cell)cell);
                    if (StringUtils.isEmpty((String)projectTitle)) break;
                    journalVO.addUnSysFieldValue("PROJECTTITLE", (Object)projectTitle);
                    break;
                }
                case "\u5f71\u54cd\u671f\u95f4": {
                    String effectTypeTitle = ImportUtils.getCellValue((Cell)cell);
                    EFFECTTYPE effectType = EFFECTTYPE.getEnumByTitle((String)effectTypeTitle);
                    if (effectType == null) break;
                    journalVO.setEffectType(effectType.getCode());
                    break;
                }
            }
            if (!allDimMap.containsKey(importCvsColumns[k])) continue;
            DimensionVO dimensionVO = allDimMap.get(importCvsColumns[k]);
            String dimCellString = ImportUtils.getCellValue((Cell)cell);
            String string = dimCode = dimCellString.contains("|") ? dimCellString.substring(0, dimCellString.indexOf("|")) : dimCellString;
            if (org.springframework.util.StringUtils.isEmpty(dimCode)) continue;
            dimCode = tableName2MapDictCode2Title.get(dimensionVO.getDictTableName()).containsKey(dimCode) ? dimCode : tableName2MapDictTitle2Code.get(dimensionVO.getDictTableName()).get(dimCode);
            journalVO.addUnSysFieldValue(dimensionVO.getCode(), (Object)(StringUtils.isEmpty((String)dimCode) ? dimCellString : GcBaseDataCenterTool.getInstance().queryBasedataByCode(dimensionVO.getDictTableName(), dimCode)));
        }
        String errorMsg = GCOffSetItemAdjustImportUtil.handleDebitCredit(journalVO, listOfGroup);
        if (errorMsg != null) {
            return errorMsg;
        }
        listOfGroup.add(journalVO);
        return null;
    }

    private static String handleDebitCredit(GcInputAdjustVO journalVO, List<GcInputAdjustVO> listOfGroup) {
        if (journalVO.getCredit() == null || journalVO.getDebit() == null) {
            return null;
        }
        if (journalVO.getCredit() == 0.0) {
            journalVO.setCredit(null);
        } else if (journalVO.getDebit() == 0.0) {
            journalVO.setDebit(null);
        } else {
            return "\u4e0d\u5141\u8bb8\u4e00\u65b9\u65e2\u6709\u501f\u53c8\u6709\u8d37";
        }
        return null;
    }

    public static GcBaseData getBaseData(String title, String tableName) {
        String code;
        GcBaseData baseData = null;
        Map<String, String> dicTitleTOCode = tableName2MapDictTitle2Id.get(tableName);
        if (dicTitleTOCode != null && dicTitleTOCode.size() > 0 && !StringUtils.isEmpty((String)(code = tableName2MapDictTitle2Id.get(tableName).get(title)))) {
            baseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode(tableName, code);
        }
        return baseData;
    }

    protected static Map<String, String> leafUnitTitle2OrgIdMap(GcOrgCenterService orgTool, String parentId) {
        HashMap<String, String> unitTitle2OrgIdMap = new HashMap<String, String>(64);
        List orgCacheVOs = orgTool.listAllOrgByParentIdContainsSelf(parentId);
        if (orgCacheVOs == null) {
            return unitTitle2OrgIdMap;
        }
        for (GcOrgCacheVO org : orgCacheVOs) {
            if (null == org || !org.isLeaf()) continue;
            unitTitle2OrgIdMap.put(org.getTitle(), org.getId());
        }
        return unitTitle2OrgIdMap;
    }

    protected static String[] parseTitle(Row row) {
        int physicalNumberOfCells = row.getLastCellNum();
        String[] importCvsColumns = new String[physicalNumberOfCells];
        for (int k = 0; k < physicalNumberOfCells; ++k) {
            Cell cell = row.getCell(k);
            importCvsColumns[k] = cell.getStringCellValue();
        }
        return importCvsColumns;
    }

    protected static String getDictCodeValue(Cell cell, String title, Map<String, Map<String, String>> tableName2MapDictTitle2Id, String dictTableName) {
        String cellValue = ImportUtils.getCellValue((Cell)cell);
        if (!StringUtils.isEmpty((String)cellValue)) {
            String dictCode = tableName2MapDictTitle2Id.get(dictTableName).get(cellValue);
            if (dictCode == null) {
                throw new BusinessRuntimeException("'" + title + "'\u89e3\u6790\u5931\u8d25:" + cellValue);
            }
            return dictCode;
        }
        return cellValue;
    }

    protected static String getUnitValue(Cell cell, Map<String, String> unitTitle2OrgIdMap) {
        String cellValue = ImportUtils.getCellValue((Cell)cell);
        String unitId = unitTitle2OrgIdMap.get(cellValue);
        if (null != unitId) {
            return unitId;
        }
        Object[] values = cellValue.split("\\|");
        if (CollectionUtils.isEmpty((Object[])values) || values.length < 2) {
            return null;
        }
        return unitTitle2OrgIdMap.get(values[1]);
    }

    private static double getDoubleValue(Cell cell, String title) {
        String cellValue = ImportUtils.getCellValue((Cell)cell);
        if (StringUtils.isEmpty((String)cellValue)) {
            return 0.0;
        }
        try {
            Double cellDoubleValue = Double.valueOf(cellValue);
            return OffsetVchrItemNumberUtils.round((Double)cellDoubleValue);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("'" + title + "'\u91d1\u989d\u9519\u8bef:" + cellValue);
        }
    }

    protected static Map<String, Map<String, String>> tableName2MapDictTitle2Id(QueryParamsVO queryParamsVO) {
        HashMap<String, Map<String, String>> tableName2MapDictTitle2Id = new HashMap<String, Map<String, String>>(8);
        tableName2MapDictTitle2Id.put("MD_GCBUSINESSTYPE", GCOffSetItemAdjustImportUtil.dictTitle2Code("MD_GCBUSINESSTYPE"));
        tableName2MapDictTitle2Id.put("MD_TZYZMS", GCOffSetItemAdjustImportUtil.dictTitle2Code("MD_TZYZMS"));
        tableName2MapDictTitle2Id.put("MD_AREA", GCOffSetItemAdjustImportUtil.dictTitle2Code("MD_AREA"));
        tableName2MapDictTitle2Id.put("MD_GCYWLX", GCOffSetItemAdjustImportUtil.dictTitle2Code("MD_GCYWLX"));
        tableName2MapDictTitle2Id.put("MD_YWBK", GCOffSetItemAdjustImportUtil.dictTitle2Code("MD_YWBK"));
        ConsolidatedTaskService consolidatedTaskCacheService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        String systemId = consolidatedTaskCacheService.getSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        if (StringUtils.isEmpty((String)systemId)) {
            tableName2MapDictTitle2Id.put("MD_GCSUBJECT", GCOffSetItemAdjustImportUtil.dictTitle2Code("MD_GCSUBJECT"));
            return tableName2MapDictTitle2Id;
        }
        ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        List subjects = consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        Map<String, String> subjectTitle2Code = subjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getTitle, ConsolidatedSubjectEO::getCode, (title1, title2) -> title1));
        tableName2MapDictTitle2Id.put("MD_GCSUBJECT", subjectTitle2Code);
        return tableName2MapDictTitle2Id;
    }

    protected static Map<String, String> dictTitle2Code(String tableCode) {
        HashMap<String, String> dictTitle2Code = new HashMap<String, String>(64);
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems(tableCode);
        if (null == baseData) {
            return dictTitle2Code;
        }
        for (GcBaseData iBaseData : baseData) {
            dictTitle2Code.put(iBaseData.getTitle(), iBaseData.getCode());
        }
        return dictTitle2Code;
    }

    private static Map<String, UnionRuleEO> ruleTitleTORuleVoMap() {
        HashMap<String, UnionRuleEO> ruleTitleTORuleVoMap = new HashMap<String, UnionRuleEO>();
        UnionRuleDao unionRuleDao = (UnionRuleDao)SpringContextUtils.getBean(UnionRuleDao.class);
        List ruleEOList = unionRuleDao.loadAll();
        if (ruleEOList != null && ruleEOList.size() > 0) {
            for (UnionRuleEO unionRuleEO : ruleEOList) {
                if (ruleTitleTORuleVoMap.get(unionRuleEO.getTitle()) != null) continue;
                ruleTitleTORuleVoMap.put(unionRuleEO.getTitle(), unionRuleEO);
            }
        }
        return ruleTitleTORuleVoMap;
    }

    private static Map<String, Object> isMergedRegion(Sheet sheet, int row, int column) {
        HashMap<String, Object> regionRange = new HashMap<String, Object>();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; ++i) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row < firstRow || row > lastRow || column < firstColumn || column > lastColumn) continue;
            regionRange.put("result", true);
            regionRange.put("firstColumn", firstColumn);
            regionRange.put("lastColumn", lastColumn);
            regionRange.put("firstRow", firstRow);
            regionRange.put("lastRow", lastRow);
            return regionRange;
        }
        return regionRange;
    }

    public static boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); ++c) {
            Cell cell = row.getCell(c);
            if (cell == null || cell.getCellType() == CellType.BLANK) continue;
            return false;
        }
        return true;
    }
}


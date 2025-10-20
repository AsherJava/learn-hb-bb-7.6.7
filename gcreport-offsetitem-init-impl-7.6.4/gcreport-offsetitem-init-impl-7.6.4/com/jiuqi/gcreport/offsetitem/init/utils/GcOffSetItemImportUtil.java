/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.utils.GcOffSetItemImportTempCache
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dao.UnionRuleDao
 *  com.jiuqi.gcreport.unionrule.entity.UnionRuleEO
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.DateUtil
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.ss.util.NumberToTextConverter
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.offsetitem.init.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.EFFECTTYPE;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitAssistService;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService;
import com.jiuqi.gcreport.offsetitem.init.service.impl.GcCalcOffsetInitLogServiceImpl;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.utils.GcOffSetItemImportTempCache;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class GcOffSetItemImportUtil {
    public static Workbook parse(MultipartFile importData, QueryParamsVO queryParamsVO, StringBuffer log) throws Exception {
        Workbook workbook = null;
        try {
            InputStream inputStream = importData.getInputStream();
            workbook = GcOffSetItemImportUtil.getWorkbook(inputStream, importData.getOriginalFilename());
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("Excel\u89e3\u6790\u5931\u8d25", (Throwable)e);
        }
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgToJsonVO = tool.getOrgByCode(queryParamsVO.getOrgId());
        String parents = orgToJsonVO.getParentStr();
        GcOffSetItemImportTempCache tempCache = GcOffSetItemImportUtil.initGcOffSetItemImportTempCache(queryParamsVO);
        HashMap<Integer, List<Integer>> errorCellIndexMap = new HashMap<Integer, List<Integer>>();
        ConcurrentLinkedDeque<String> preGenerateSortedMRecids = GcOffSetItemImportUtil.preGenerateSortedMRecids(workbook);
        int successCount = 0;
        int failedCount = 0;
        ArrayList<Object> itemDTOs = new ArrayList<GcOffSetVchrItemDTO>();
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; ++i) {
            if (i > 0) continue;
            Sheet sheet = workbook.getSheetAt(i);
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            int currMergedRegionIndex = 0;
            List mergedRegions = sheet.getMergedRegions();
            if (CollectionUtils.isEmpty((Collection)mergedRegions)) {
                throw new BusinessRuntimeException("\u6ca1\u6709\u627e\u5230\u6210\u7ec4\u7684\u5206\u5f55\u4fe1\u606f\u3002");
            }
            ArrayList<Integer> lastRowList = new ArrayList<Integer>();
            for (CellRangeAddress rangeAddress : mergedRegions) {
                if (rangeAddress.getFirstColumn() > 0) continue;
                lastRowList.add(rangeAddress.getLastRow());
            }
            lastRowList.sort(Comparator.comparing(Integer::intValue));
            int numMergedRegions = lastRowList.size();
            if (numMergedRegions <= 0) {
                throw new BusinessRuntimeException("\u6ca1\u6709\u627e\u5230\u6210\u7ec4\u7684\u5206\u5f55\u4fe1\u606f\u3002");
            }
            int lastRow = (Integer)lastRowList.get(currMergedRegionIndex++);
            String[] titleList = null;
            boolean itemGroupHasError = false;
            for (int excelRowNum = 0; excelRowNum < physicalNumberOfRows; ++excelRowNum) {
                GcOffSetVchrItemDTO itemDTO;
                if (excelRowNum == 0) {
                    Row titleRow = sheet.getRow(excelRowNum);
                    titleList = GcOffSetItemImportUtil.initImportTitleList(titleRow);
                    continue;
                }
                Row row = sheet.getRow(excelRowNum);
                String failReason = GcOffSetItemImportUtil.oneRow(row, itemDTO = GcOffSetItemImportUtil.gcOffSetVchrItemDTO(queryParamsVO), itemDTOs, titleList, errorCellIndexMap, tempCache);
                if (null == failReason) {
                    failReason = GcOffSetItemImportUtil.checkAndHandle(itemDTO);
                }
                if (null != failReason) {
                    log.append("excel\u884c\u53f7\u7b2c").append(excelRowNum + 1).append("\u884c\u51fa\u9519\uff1a").append(failReason).append(" \n");
                    itemGroupHasError = true;
                }
                itemDTOs.add(itemDTO);
                if (excelRowNum < lastRow) continue;
                if (itemGroupHasError) {
                    itemGroupHasError = false;
                    itemDTOs.clear();
                    ++failedCount;
                }
                if (currMergedRegionIndex >= lastRowList.size()) break;
                lastRow = (Integer)lastRowList.get(currMergedRegionIndex++);
                if (CollectionUtils.isEmpty(itemDTOs)) continue;
                failReason = GcOffSetItemImportUtil.checkUnit(itemDTOs, tool, queryParamsVO.getOrgId());
                if (null != failReason) {
                    log.append("excel\u884c\u53f7\u7b2c").append(excelRowNum + 1).append("\u884c\u5bf9\u5e94\u5206\u5f55\u51fa\u9519\uff1a").append(failReason).append("\n");
                    itemDTOs = new ArrayList();
                    ++failedCount;
                    continue;
                }
                String mrecid = preGenerateSortedMRecids.pollFirst();
                failReason = GcOffSetItemImportUtil.save(mrecid, itemDTOs, tool, parents, queryParamsVO.getOrgId());
                if (null != failReason) {
                    ++failedCount;
                    log.append("excel\u884c\u53f7\u7b2c").append(excelRowNum + 1).append("\u884c\u5bf9\u5e94\u5206\u5f55\u51fa\u9519\uff1a").append(failReason).append("\n");
                } else {
                    ++successCount;
                }
                itemDTOs = new ArrayList();
            }
            if (itemDTOs.isEmpty()) continue;
            String failReason = GcOffSetItemImportUtil.checkUnit(itemDTOs, tool, queryParamsVO.getOrgId());
            if (failReason == null) {
                String mrecid = preGenerateSortedMRecids.pollFirst();
                failReason = GcOffSetItemImportUtil.save(mrecid, itemDTOs, tool, parents, queryParamsVO.getOrgId());
            }
            if (null != failReason) {
                ++failedCount;
                log.append("\u6700\u540e\u4e00\u7ec4\u8bb0\u5f55\u51fa\u9519\uff1a").append(failReason).append("\n");
                continue;
            }
            ++successCount;
        }
        tempCache.clear();
        log.insert(0, "\u672c\u6b21\u5bfc\u5165\u6210\u529f" + successCount + "\u7ec4\uff0c\u5931\u8d25" + failedCount + "\u7ec4\u3002 \n");
        return null;
    }

    private static ConcurrentLinkedDeque<String> preGenerateSortedMRecids(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        ConcurrentLinkedDeque<String> preMRecidQueue = new ConcurrentLinkedDeque<String>();
        for (int i = 0; i < numberOfSheets; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            for (int excelRowNum = 0; excelRowNum < physicalNumberOfRows; ++excelRowNum) {
                preMRecidQueue.offerFirst(UUIDOrderSnowUtils.newUUIDStr());
            }
        }
        return preMRecidQueue;
    }

    public static GcOffSetItemImportTempCache initGcOffSetItemImportTempCache(QueryParamsVO queryParamsVO) {
        GcOffSetItemImportTempCache tempCache = new GcOffSetItemImportTempCache();
        GcOffSetItemImportUtil.initRuleTempCache(tempCache, queryParamsVO, queryParamsVO.getPeriodStr());
        GcOffSetItemImportUtil.initUnitTempCache(tempCache, queryParamsVO.getOrgType(), queryParamsVO.getPeriodStr());
        GcOffSetItemImportUtil.initSubjectTempCache(tempCache, queryParamsVO);
        List dimensionVOS = ((ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class)).getAllDimensionsByTableName("GC_OFFSETVCHRITEM_INIT", queryParamsVO.getSystemId());
        Map<Object, Object> allDimMap = new HashMap();
        if (!CollectionUtils.isEmpty((Collection)dimensionVOS)) {
            allDimMap = dimensionVOS.stream().collect(Collectors.toMap(DimensionVO::getTitle, Function.identity(), (key1, key2) -> key2));
        }
        tempCache.setAllDimMap(allDimMap);
        GcOffSetItemImportUtil.initDimensionsTempCache(tempCache, dimensionVOS);
        return tempCache;
    }

    private static void initSubjectTempCache(GcOffSetItemImportTempCache tempCache, QueryParamsVO queryParamsVO) {
        List<ConsolidatedSubjectEO> subjectEOs = GcOffSetItemImportUtil.getConsolidatedSubjectEOS(queryParamsVO);
        if (subjectEOs == null) {
            tempCache.setSubjectCode2Title(Collections.EMPTY_MAP);
            tempCache.setSubjectTitle2Code(Collections.EMPTY_MAP);
            tempCache.setParentCodeGroupMap(Collections.EMPTY_MAP);
            return;
        }
        Map<String, List<ConsolidatedSubjectEO>> parentCodeGroupMap = subjectEOs.stream().filter(t -> t.getParentCode() != null).collect(Collectors.groupingBy(ConsolidatedSubjectEO::getParentCode));
        tempCache.setParentCodeGroupMap(parentCodeGroupMap);
        HashMap<String, String> subjectTitle2Code = new HashMap<String, String>();
        HashMap<String, String> subjectCode2Title = new HashMap<String, String>();
        for (ConsolidatedSubjectEO subjectEO : subjectEOs) {
            subjectTitle2Code.put(subjectEO.getTitle(), subjectEO.getCode());
            subjectCode2Title.put(subjectEO.getCode(), subjectEO.getTitle());
        }
        tempCache.setSubjectTitle2Code(subjectTitle2Code);
        tempCache.setSubjectCode2Title(subjectCode2Title);
    }

    private static void updateErrorColStyle(Workbook workbook, Map<Integer, List<Integer>> errorCellIndexMap) {
        Sheet sheet = workbook.getSheetAt(0);
        for (Integer rowIndex : errorCellIndexMap.keySet()) {
            Row row = sheet.getRow(rowIndex.intValue());
            for (Integer col : errorCellIndexMap.get(rowIndex)) {
                GcOffSetItemImportUtil.setCellErrorStyle(row.getCell(col.intValue()), workbook);
            }
        }
    }

    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        HSSFWorkbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            wb = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            wb = new XSSFWorkbook(inStr);
        } else {
            throw new BusinessRuntimeException("\u89e3\u6790\u7684\u6587\u4ef6\u683c\u5f0f\u6709\u8bef\uff01");
        }
        return wb;
    }

    private static String[] initImportTitleList(Row titleRow) {
        int physicalNumberOfCells = titleRow.getLastCellNum();
        String[] titleList = new String[physicalNumberOfCells];
        for (int i = 0; i < physicalNumberOfCells; ++i) {
            titleList[i] = titleRow.getCell(i).getStringCellValue().trim();
        }
        return titleList;
    }

    private static String checkUnit(List<GcOffSetVchrItemDTO> itemDTOs, GcOrgCenterService tool, String comUnitId) {
        HashSet<String> unitIdSet = new HashSet<String>();
        for (GcOffSetVchrItemDTO item : itemDTOs) {
            unitIdSet.add(item.getUnitId());
            unitIdSet.add(item.getOppUnitId());
        }
        unitIdSet.remove("");
        unitIdSet.remove(null);
        if (unitIdSet.size() > 2) {
            return GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.moreThanTwo");
        }
        if (unitIdSet.size() < 2) {
            return GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.disAllowSame");
        }
        Iterator iterator = unitIdSet.iterator();
        String firstUnitCode = (String)iterator.next();
        String secondUnitCode = (String)iterator.next();
        GcOrgCacheVO commonUnit = tool.getCommonUnit(tool.getOrgByCode(firstUnitCode), tool.getOrgByCode(secondUnitCode));
        if (commonUnit == null || !comUnitId.equals(commonUnit.getCode())) {
            return GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.notCurrSameParent");
        }
        return null;
    }

    private static GcOffSetVchrItemDTO gcOffSetVchrItemDTO(QueryParamsVO queryParamsVO) {
        GcOffSetVchrItemDTO itemDTO = new GcOffSetVchrItemDTO();
        itemDTO.setSystemId(queryParamsVO.getSystemId());
        itemDTO.setTaskId(queryParamsVO.getTaskId());
        itemDTO.setSchemeId(queryParamsVO.getSchemeId());
        itemDTO.setAcctYear(queryParamsVO.getAcctYear());
        itemDTO.setAcctPeriod(queryParamsVO.getAcctPeriod());
        itemDTO.setDefaultPeriod(queryParamsVO.getPeriodStr());
        itemDTO.setOffSetCurr(queryParamsVO.getCurrencyUpperCase());
        itemDTO.setElmMode(Integer.valueOf(OffsetElmModeEnum.INPUT_ITEM.getValue()));
        itemDTO.setOrgType(queryParamsVO.getOrgType());
        itemDTO.setUnitVersion(queryParamsVO.getOrgType());
        itemDTO.setOffSetSrcType(OffSetSrcTypeEnum.OFFSET_ITEM_INIT);
        if (queryParamsVO.isCarryOver()) {
            itemDTO.setOffSetSrcType(OffSetSrcTypeEnum.CARRY_OVER);
        }
        return itemDTO;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String save(String mrecid, List<GcOffSetVchrItemDTO> itemDTOs, GcOrgCenterService tool, String parents, String inputUnitId) {
        String failReason = GcOffSetItemImportUtil.check(itemDTOs, tool, parents, inputUnitId);
        if (null != failReason) {
            return failReason;
        }
        GcOffSetVchrDTO dto = GcOffSetItemImportUtil.repairNullUnit(mrecid, itemDTOs);
        GcOffSetInitService offSetInitService = (GcOffSetInitService)SpringContextUtils.getBean(GcOffSetInitService.class);
        GcOffSetInitAssistService offSetInitAssistService = (GcOffSetInitAssistService)SpringContextUtils.getBean(GcOffSetInitAssistService.class);
        GcOffSetAppOffsetService offSetItemAdjustService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
        try {
            List<GcOffSetVchrItemVO> offSetVchrItemVOList = dto.getItems().stream().map(item -> offSetItemAdjustService.convertDTO2VO(item)).collect(Collectors.toList());
            offSetInitAssistService.changeInvestmentOffsetStatus(offSetVchrItemVOList, true, true, true);
            dto.getItems().stream().forEach(item -> item.setSrcOffsetGroupId(((GcOffSetVchrItemVO)offSetVchrItemVOList.get(0)).getSrcOffsetGroupId()));
            offSetInitService.save(dto);
            String srcTypeName = itemDTOs.get(0).getOffSetSrcType().getSrcTypeName();
            GcCalcOffsetInitLogServiceImpl initLogService = (GcCalcOffsetInitLogServiceImpl)SpringContextUtils.getBean(GcCalcOffsetInitLogServiceImpl.class);
            initLogService.insertOrUpdateCalcLogEO(inputUnitId, itemDTOs.get(0).getAcctYear().toString(), srcTypeName + "\u6279\u91cf\u5bfc\u5165\u6570\u636e");
        }
        catch (Exception e) {
            e.printStackTrace();
            String string = e.getMessage();
            return string;
        }
        finally {
            itemDTOs.clear();
        }
        return null;
    }

    private static GcOffSetVchrDTO repairNullUnit(String mrecid, List<GcOffSetVchrItemDTO> itemDTOs) {
        Objects.requireNonNull(mrecid, "mrecid\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        GcOffSetVchrDTO dto = new GcOffSetVchrDTO(mrecid);
        dto.setItems(itemDTOs);
        Map<String, String> oppUnitMap = GcOffSetItemImportUtil.getOppUnitMap(itemDTOs);
        String srcOffsetGroupId = itemDTOs.get(0).getSrcOffsetGroupId();
        if (srcOffsetGroupId == null) {
            srcOffsetGroupId = UUIDUtils.newUUIDStr();
        }
        for (GcOffSetVchrItemDTO itemDTO : itemDTOs) {
            itemDTO.setOppUnitId(oppUnitMap.get(itemDTO.getUnitId()));
            itemDTO.setSrcOffsetGroupId(srcOffsetGroupId);
        }
        for (int i = 0; i < itemDTOs.size(); ++i) {
            itemDTOs.get(i).setSortOrder(Double.valueOf(i + 1));
        }
        return dto;
    }

    private static Map<String, String> getOppUnitMap(List<GcOffSetVchrItemDTO> itemDTOs) {
        HashMap<String, String> oppUnitMap = new HashMap<String, String>();
        HashSet<String> unitIdSet = new HashSet<String>();
        for (GcOffSetVchrItemDTO itemDTO : itemDTOs) {
            unitIdSet.add(itemDTO.getUnitId());
            unitIdSet.add(itemDTO.getOppUnitId());
            if (unitIdSet.size() < 2) continue;
            Iterator iterator = unitIdSet.iterator();
            String unit1 = (String)iterator.next();
            String unit2 = (String)iterator.next();
            oppUnitMap.put(unit1, unit2);
            oppUnitMap.put(unit2, unit1);
            return oppUnitMap;
        }
        return oppUnitMap;
    }

    private static String check(List<GcOffSetVchrItemDTO> itemDTOs, GcOrgCenterService tool, String parents, String inputUnitId) {
        if (null == parents) {
            return "parents\u4e3a\u7a7a";
        }
        if (itemDTOs.size() < 2) {
            return "\u81f3\u5c11\u4e24\u6761\u8bb0\u5f55";
        }
        HashSet assetTitles = new HashSet();
        HashSet<String> unitIds = new HashSet<String>();
        String ruleId = itemDTOs.get(0).getRuleId();
        if (StringUtils.isEmpty((String)ruleId)) {
            return "\u5408\u5e76\u89c4\u5219\u4e0d\u80fd\u4e3a\u7a7a";
        }
        String gcBusinessTypeCode = itemDTOs.get(0).getGcBusinessTypeCode();
        for (GcOffSetVchrItemDTO itemDTO : itemDTOs) {
            unitIds.add(itemDTO.getUnitId());
            unitIds.add(itemDTO.getOppUnitId());
            itemDTO.setRuleId(ruleId);
            itemDTO.setGcBusinessTypeCode(gcBusinessTypeCode);
        }
        unitIds.remove(null);
        if (assetTitles.size() > 1) {
            return "\u8d44\u4ea7\u540d\u79f0\u4e0d\u4e00\u81f4";
        }
        if (unitIds.size() > 2) {
            return GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.moreThanTwo");
        }
        assetTitles.remove(null);
        assetTitles.remove("");
        if (assetTitles.size() == 1) {
            if (unitIds.size() < 2) {
                return GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.disAllowSame");
            }
            GcOrgCacheVO jsonVO = tool.getOrgByID(inputUnitId);
            String bbUnit = jsonVO.getBaseUnitId();
            boolean success = unitIds.remove(bbUnit);
            if (!success) {
                return "\u5fc5\u987b\u6709\u4e00\u5bb6\u4e3a\u672c\u90e8\u5355\u4f4d";
            }
            String otherUnitId = (String)unitIds.iterator().next();
            GcOrgCacheVO otherOrg = tool.getOrgByID(otherUnitId);
            if (!inputUnitId.equals(otherOrg.getParentId())) {
                return "\u5b58\u5728\u975e\u76f4\u63a5\u4e0b\u7ea7\u7684\u5355\u4f4d";
            }
        }
        return null;
    }

    private static String oneRow(Row row, GcOffSetVchrItemDTO itemDTO, List<GcOffSetVchrItemDTO> itemDTOs, String[] titleList, Map<Integer, List<Integer>> errorCellIndexMap, GcOffSetItemImportTempCache tempCache) {
        Map allDimMap = tempCache.getAllDimMap();
        Map parentCodeGroupMap = tempCache.getParentCodeGroupMap();
        Map ruleCode2RuleVoMap = tempCache.getRuleCode2RuleVoMap();
        Map ruleTitle2RuleVoMap = tempCache.getRuleTitle2RuleVoMap();
        Map subjectCode2Title = tempCache.getSubjectCode2Title();
        Map subjectTitle2Code = tempCache.getSubjectTitle2Code();
        Map unitCode2UnitTitleMap = tempCache.getUnitCode2UnitTitleMap();
        Map unitTitle2unitCodeMap = tempCache.getUnitTitle2unitCodeMap();
        Map tableName2MapDictCode2Title = tempCache.getTableName2MapDictCode2Title();
        Map tableName2MapDictTitle2Code = tempCache.getTableName2MapDictTitle2Code();
        StringBuffer failReason = new StringBuffer();
        int physicalNumberOfCells = row.getLastCellNum();
        UnionRuleEO ruleVO = null;
        for (int k = 0; k < physicalNumberOfCells; ++k) {
            String dimCode;
            Cell cell;
            if (k >= titleList.length || (cell = row.getCell(k)) == null) continue;
            switch (titleList[k]) {
                case "\u5e8f\u53f7": {
                    break;
                }
                case "\u5408\u5e76\u89c4\u5219": {
                    String ruleCode;
                    if (!CollectionUtils.isEmpty(itemDTOs)) break;
                    String cellValue = GcOffSetItemImportUtil.getCellValue(cell);
                    String string = ruleCode = cellValue.contains("|") ? cellValue.substring(0, cellValue.indexOf("|")) : cellValue;
                    if (StringUtils.isEmpty((String)ruleCode)) break;
                    UnionRuleEO unionRuleEO = ruleVO = ruleCode2RuleVoMap.containsKey(ruleCode) ? (UnionRuleEO)ruleCode2RuleVoMap.get(ruleCode) : (UnionRuleEO)ruleTitle2RuleVoMap.get(ruleCode);
                    if (ruleVO == null) {
                        GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                        failReason.append("\u672a\u627e\u89c1\u89c4\u5219:" + cellValue + " ");
                        break;
                    }
                    itemDTO.setRuleId(ruleVO.getId());
                    itemDTO.setGcBusinessTypeCode(ruleVO.getBusinessTypeCode());
                    break;
                }
                case "\u672c\u65b9\u5355\u4f4d": {
                    String debitUnitId;
                    String debitUnitCode;
                    String debitUnit = GcOffSetItemImportUtil.getCellValue(cell);
                    String string = debitUnitCode = debitUnit.contains("|") ? debitUnit.substring(0, debitUnit.indexOf("|")) : debitUnit;
                    if (StringUtils.isEmpty((String)debitUnitCode)) break;
                    String string2 = debitUnitId = unitCode2UnitTitleMap.containsKey(debitUnitCode) ? debitUnitCode : (String)unitTitle2unitCodeMap.get(debitUnitCode);
                    if (debitUnitId == null) {
                        GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                        failReason.append("\u672a\u627e\u89c1\u672c\u65b9\u5355\u4f4d:" + debitUnit + " ");
                        break;
                    }
                    itemDTO.setUnitId(debitUnitId);
                    break;
                }
                case "\u5bf9\u65b9\u5355\u4f4d": {
                    String creditUnitId;
                    String creditUnitCode;
                    String creditUnit = GcOffSetItemImportUtil.getCellValue(cell);
                    String string = creditUnitCode = creditUnit.contains("|") ? creditUnit.substring(0, creditUnit.indexOf("|")) : creditUnit;
                    if (StringUtils.isEmpty((String)creditUnitCode)) break;
                    String string3 = creditUnitId = unitCode2UnitTitleMap.containsKey(creditUnitCode) ? creditUnitCode : (String)unitTitle2unitCodeMap.get(creditUnitCode);
                    if (creditUnitId == null) {
                        GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                        failReason.append("\u672a\u627e\u89c1\u5bf9\u65b9\u5355\u4f4d:" + creditUnit + " ");
                        break;
                    }
                    itemDTO.setOppUnitId(creditUnitId);
                    break;
                }
                case "\u79d1\u76ee": {
                    String subjectCode;
                    String subject = GcOffSetItemImportUtil.getCellValue(cell);
                    String string = subjectCode = subject.contains("|") ? subject.substring(0, subject.indexOf("|")) : subject;
                    if (!StringUtils.isEmpty((String)subjectCode)) {
                        String string4 = subjectCode = subjectCode2Title.containsKey(subjectCode) ? subjectCode : (String)subjectTitle2Code.get(subjectCode);
                        if (subjectCode == null) {
                            GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                            failReason.append("\u672a\u627e\u89c1\u79d1\u76ee:" + subject + " ");
                            break;
                        }
                        if (!CollectionUtils.isEmpty((Collection)((Collection)parentCodeGroupMap.get(subjectCode)))) {
                            GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                            failReason.append("\u79d1\u76ee:" + subject + "\u4e3a\u975e\u672b\u7ea7\u79d1\u76ee ");
                            break;
                        }
                        itemDTO.setSubjectCode(subjectCode);
                        break;
                    }
                    GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                    break;
                }
                case "\u501f\u65b9\u91d1\u989d": {
                    String offSetDebit = GcOffSetItemImportUtil.getCellValue(cell);
                    if (StringUtils.isEmpty((String)offSetDebit)) break;
                    try {
                        itemDTO.setOffSetDebit(GcOffSetItemImportUtil.getCellDoubleValue(offSetDebit));
                    }
                    catch (Exception e) {
                        GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                        failReason.append("\u501f\u65b9\u62b5\u9500\u91d1\u989d\u9519\u8bef:" + offSetDebit + " ");
                    }
                    break;
                }
                case "\u8d37\u65b9\u91d1\u989d": {
                    String offSetCredit = GcOffSetItemImportUtil.getCellValue(cell);
                    if (StringUtils.isEmpty((String)offSetCredit)) break;
                    try {
                        itemDTO.setOffSetCredit(GcOffSetItemImportUtil.getCellDoubleValue(offSetCredit));
                    }
                    catch (Exception e) {
                        GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                        failReason.append("\u8d37\u65b9\u62b5\u9500\u91d1\u989d\u9519\u8bef:" + offSetCredit + " ");
                    }
                    break;
                }
                case "\u63cf\u8ff0": {
                    String memo = GcOffSetItemImportUtil.getCellValue(cell);
                    itemDTO.setMemo(memo);
                    break;
                }
                case "\u5f71\u54cd\u4e0b\u5e74": {
                    String effectType = GcOffSetItemImportUtil.getCellValue(cell);
                    if (StringUtils.isEmpty((String)effectType)) {
                        itemDTO.setEffectType(EFFECTTYPE.MONTH.getCode());
                        break;
                    }
                    if ("\u662f".equals(effectType)) {
                        itemDTO.setEffectType(EFFECTTYPE.LONGTERM.getCode());
                        break;
                    }
                    if ("\u5426".equals(effectType)) {
                        itemDTO.setEffectType(EFFECTTYPE.MONTH.getCode());
                        break;
                    }
                    failReason.append("\u5f71\u54cd\u4e0b\u5e74\u8bf7\u586b\u5199\u201c\u662f\u201d\u6216\u201c\u5426\u201d");
                    break;
                }
            }
            if (!GcOffSetItemImportUtil.hasEffectTypeHeaderTitle(titleList)) {
                itemDTO.setEffectType(EFFECTTYPE.LONGTERM.getCode());
            }
            if (!allDimMap.containsKey(titleList[k])) continue;
            DimensionVO dimensionVO = (DimensionVO)allDimMap.get(titleList[k]);
            String dimCellString = GcOffSetItemImportUtil.getCellValue(cell);
            String string = dimCode = dimCellString.contains("|") ? dimCellString.substring(0, dimCellString.indexOf("|")) : dimCellString;
            if (!StringUtils.isEmpty((String)dimCode)) {
                if (!StringUtils.isEmpty((String)dimensionVO.getDictTableName())) {
                    String string5 = dimCode = ((Map)tableName2MapDictCode2Title.get(dimensionVO.getDictTableName())).containsKey(dimCode) ? dimCode : (String)((Map)tableName2MapDictTitle2Code.get(dimensionVO.getDictTableName())).get(dimCode);
                }
                if (dimCode == null) {
                    GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
                    failReason.append("\u672a\u627e\u89c1").append(titleList[k]).append(":").append(dimCellString).append(" ");
                }
                itemDTO.addUnSysFieldValue(dimensionVO.getCode(), (Object)dimCode);
                itemDTO.addFieldValue(dimensionVO.getCode(), (Object)dimCode);
                continue;
            }
            if (null == dimensionVO.getNullAble() || dimensionVO.getNullAble().booleanValue()) continue;
            GcOffSetItemImportUtil.mapAddValue(errorCellIndexMap, cell.getRowIndex(), cell.getColumnIndex());
            failReason.append("\u5fc5\u586b\u53c2\u6570").append(dimensionVO.getTitle()).append("\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)itemDTO.getUnitId())) {
            failReason.append("\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        failReason.append(GcOffSetItemImportUtil.handleDebitCredit(itemDTO, itemDTOs));
        return failReason.toString().isEmpty() ? null : failReason.toString();
    }

    private static boolean hasEffectTypeHeaderTitle(String[] titleList) {
        boolean hasTitle = false;
        for (String title : titleList) {
            if (!title.contains("\u5f71\u54cd\u4e0b\u5e74")) continue;
            hasTitle = true;
            break;
        }
        return hasTitle;
    }

    private static String handleDebitCredit(GcOffSetVchrItemDTO itemDTO, List<GcOffSetVchrItemDTO> itemDTOs) {
        if (itemDTO.getOffSetCredit() == null && itemDTO.getOffSetDebit() == null) {
            return "\u4e0d\u5141\u8bb8\u501f\u8d37\u65b9\u90fd\u4e3a\u7a7a";
        }
        if (itemDTO.getOffSetCredit() != null && itemDTO.getOffSetDebit() != null && itemDTO.getOffSetCredit() == 0.0 && itemDTO.getOffSetDebit() == 0.0) {
            return "\u4e0d\u5141\u8bb8\u501f\u8d37\u65b9\u90fd\u4e3a0";
        }
        if (itemDTO.getOffSetCredit() == null) {
            itemDTO.setOrient(OrientEnum.D);
            itemDTO.setOffSetCredit(null);
        } else if (itemDTO.getOffSetDebit() == null) {
            itemDTO.setOrient(OrientEnum.C);
            itemDTO.setOffSetDebit(null);
        } else if (itemDTO.getOffSetCredit() == 0.0) {
            itemDTO.setOrient(OrientEnum.D);
            itemDTO.setOffSetCredit(null);
        } else if (itemDTO.getOffSetDebit() == 0.0) {
            itemDTO.setOrient(OrientEnum.C);
            itemDTO.setOffSetDebit(null);
        } else {
            return "\u4e0d\u5141\u8bb8\u4e00\u65b9\u65e2\u6709\u501f\u53c8\u6709\u8d37";
        }
        return "";
    }

    private static void mapAddValue(Map<Integer, List<Integer>> errorCellIndexMap, int rowIndex, int columnIndex) {
        List<Integer> columnList = errorCellIndexMap.get(rowIndex);
        if (columnList == null) {
            columnList = new ArrayList<Integer>();
        }
        columnList.add(columnIndex);
        errorCellIndexMap.put(rowIndex, columnList);
    }

    private static void setCellErrorStyle(Cell cell, Workbook workbook) {
        CellStyle cStyle = workbook.createCellStyle();
        cStyle.cloneStyleFrom(cell.getCellStyle());
        cStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        cStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(cStyle);
    }

    private static Double getCellDoubleValue(String valueOf) {
        if (StringUtils.isEmpty((String)valueOf)) {
            return 0.0;
        }
        try {
            Double cellValue = Double.valueOf(valueOf.replace(",", ""));
            return OffsetVchrItemNumberUtils.round((Double)cellValue);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u91d1\u989d\u9519\u8bef:" + valueOf);
        }
    }

    private static void initDimensionsTempCache(GcOffSetItemImportTempCache tempCache, List<DimensionVO> allDimMap) {
        HashMap<String, Map<String, String>> tableName2MapDictTitle2Code = new HashMap<String, Map<String, String>>();
        HashMap<String, Map<String, String>> tableName2MapDictCode2Title = new HashMap<String, Map<String, String>>();
        tempCache.setTableName2MapDictTitle2Code(tableName2MapDictTitle2Code);
        tempCache.setTableName2MapDictCode2Title(tableName2MapDictCode2Title);
        for (DimensionVO dimensionVO : allDimMap) {
            GcOffSetItemImportUtil.initDictTitleAndCodeMap(dimensionVO.getDictTableName(), tableName2MapDictTitle2Code, tableName2MapDictCode2Title);
        }
    }

    private static void initDictTitleAndCodeMap(String tableCode, Map<String, Map<String, String>> tableName2MapDictTitle2Code, Map<String, Map<String, String>> tableName2MapDictCode2Title) {
        if (StringUtils.isEmpty((String)tableCode)) {
            tableName2MapDictTitle2Code.put(tableCode, Collections.EMPTY_MAP);
            tableName2MapDictCode2Title.put(tableCode, Collections.EMPTY_MAP);
            return;
        }
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems(tableCode);
        if (null == baseData) {
            tableName2MapDictTitle2Code.put(tableCode, Collections.EMPTY_MAP);
            tableName2MapDictCode2Title.put(tableCode, Collections.EMPTY_MAP);
            return;
        }
        HashMap<String, String> dictTitle2Code = new HashMap<String, String>();
        HashMap<String, String> dictCode2Title = new HashMap<String, String>();
        for (GcBaseData iBaseData : baseData) {
            dictTitle2Code.put(iBaseData.getTitle(), iBaseData.getCode());
            dictCode2Title.put(iBaseData.getCode(), iBaseData.getTitle());
        }
        tableName2MapDictTitle2Code.put(tableCode, dictTitle2Code);
        tableName2MapDictCode2Title.put(tableCode, dictCode2Title);
    }

    private static String checkAndHandle(GcOffSetVchrItemDTO itemDTO) {
        String failReason = itemDTO.check();
        if (null != failReason) {
            return failReason;
        }
        return null;
    }

    private static String getCellValue(Cell cell) {
        Object cellValue;
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
            if (format == 14 || format == 31 || format == 57 || format == 58) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                double value = cell.getNumericCellValue();
                Date date = DateUtil.getJavaDate((double)value);
                cellValue = sdf.format(date);
            } else if (DateUtil.isCellDateFormatted((Cell)cell)) {
                Date date = cell.getDateCellValue();
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                cellValue = formater.format(date);
            } else {
                cellValue = NumberToTextConverter.toText((double)cell.getNumericCellValue());
            }
        } else {
            cellValue = cell.getCellType() == CellType.STRING ? cell.getStringCellValue().trim() : (cell.getCellType() == CellType.BOOLEAN ? String.valueOf(cell.getBooleanCellValue()).trim() : (cell.getCellType() == CellType.FORMULA ? cell.getCellFormula() : (cell.getCellType() == CellType.BLANK ? null : "")));
        }
        return StringUtils.isEmpty((String)cellValue) ? "" : cellValue;
    }

    private static void initRuleTempCache(GcOffSetItemImportTempCache tempCache, QueryParamsVO queryParamsVO, String periodStr) {
        HashMap<String, UnionRuleEO> ruleTitle2EOMap = new HashMap<String, UnionRuleEO>();
        HashMap<String, UnionRuleEO> ruleId2EOMap = new HashMap<String, UnionRuleEO>();
        tempCache.setRuleTitle2RuleVoMap(ruleTitle2EOMap);
        tempCache.setRuleCode2RuleVoMap(ruleId2EOMap);
        String systemId = queryParamsVO.getSystemId();
        if (StringUtils.isEmpty((String)systemId)) {
            ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
            systemId = consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), periodStr);
        }
        if (StringUtils.isEmpty((String)systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.systemNotExist"));
        }
        UnionRuleDao unionRuleDao = (UnionRuleDao)SpringContextUtils.getBean(UnionRuleDao.class);
        List ruleEOList = unionRuleDao.findRuleListByReportSystem(systemId);
        if (ruleEOList == null) {
            return;
        }
        for (UnionRuleEO unionRuleVO : ruleEOList) {
            ruleTitle2EOMap.put(unionRuleVO.getTitle().trim(), unionRuleVO);
            ruleId2EOMap.put(unionRuleVO.getId(), unionRuleVO);
        }
    }

    private static void initUnitTempCache(GcOffSetItemImportTempCache tempCache, String orgType, String periodStr) {
        HashMap<String, String> unitTitle2unitCodeMap = new HashMap<String, String>();
        HashMap<String, String> unitCode2unitTitleMap = new HashMap<String, String>();
        tempCache.setUnitTitle2unitCodeMap(unitTitle2unitCodeMap);
        tempCache.setUnitCode2UnitTitleMap(unitCode2unitTitleMap);
        List orgs = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)new YearPeriodObject(null, periodStr)).listAllOrgByParentIdContainsSelf(null);
        if (orgs == null) {
            return;
        }
        for (GcOrgCacheVO org : orgs) {
            unitTitle2unitCodeMap.put(org.getTitle(), org.getCode());
            unitCode2unitTitleMap.put(org.getCode(), org.getTitle());
        }
    }

    private static List<ConsolidatedSubjectEO> getConsolidatedSubjectEOS(QueryParamsVO queryParamsVO) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        String systemId = queryParamsVO.getSystemId();
        if (StringUtils.isEmpty((String)systemId)) {
            ConsolidatedTaskService consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
            systemId = consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        }
        if (StringUtils.isEmpty((String)systemId)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.showoffset.systemNotExist"));
        }
        return subjectService.listAllSubjectsBySystemId(systemId);
    }
}


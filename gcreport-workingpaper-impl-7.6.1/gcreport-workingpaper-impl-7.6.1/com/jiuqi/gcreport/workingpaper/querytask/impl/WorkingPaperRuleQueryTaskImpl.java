/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService
 *  com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package com.jiuqi.gcreport.workingpaper.querytask.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractWorkingPaperRuleQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableVO;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class WorkingPaperRuleQueryTaskImpl
extends AbstractWorkingPaperRuleQueryTask {
    @Override
    public List<WorkingPaperTableDataVO> getDataVO(WorkingPaperQueryCondition condition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        List<WorkingPaperTableDataVO> workingPaperTableDataVOs = this.buildWorkingPaperTableDataByRuleAndSubject(condition);
        if (condition.getIsFilterZero().booleanValue()) {
            int size;
            WorkingPaperTableDataVO preVO;
            ArrayList<WorkingPaperTableDataVO> filterWorkingPaperTableDataVOs = new ArrayList<WorkingPaperTableDataVO>();
            workingPaperTableDataVOs.forEach(dataVO -> {
                if (!dataVO.isLeafNodeFlag()) {
                    int size;
                    WorkingPaperTableDataVO preVO;
                    if (filterWorkingPaperTableDataVOs.size() > 0 && !(preVO = (WorkingPaperTableDataVO)filterWorkingPaperTableDataVOs.get((size = filterWorkingPaperTableDataVOs.size()) - 1)).isLeafNodeFlag()) {
                        filterWorkingPaperTableDataVOs.remove(size - 1);
                    }
                } else {
                    Map zbvalueMap = dataVO.getZbvalue();
                    BigDecimal dxsDebitValue = ConverterUtils.getAsBigDecimal(zbvalueMap.get("DXS_DEBIT"), (BigDecimal)BigDecimal.ZERO);
                    BigDecimal dxsCreditValue = ConverterUtils.getAsBigDecimal(zbvalueMap.get("DXS_CREDIT"), (BigDecimal)BigDecimal.ZERO);
                    if (BigDecimal.ZERO.compareTo(dxsDebitValue) == 0 && BigDecimal.ZERO.compareTo(dxsCreditValue) == 0) {
                        return;
                    }
                }
                filterWorkingPaperTableDataVOs.add((WorkingPaperTableDataVO)dataVO);
            });
            if (filterWorkingPaperTableDataVOs.size() > 0 && !(preVO = (WorkingPaperTableDataVO)filterWorkingPaperTableDataVOs.get((size = filterWorkingPaperTableDataVOs.size()) - 1)).isLeafNodeFlag()) {
                filterWorkingPaperTableDataVOs.remove(size - 1);
            }
            workingPaperTableDataVOs = filterWorkingPaperTableDataVOs;
        }
        return workingPaperTableDataVOs;
    }

    @Override
    public List<WorkingPaperTableHeaderVO> getHeaderVO(WorkingPaperQueryCondition queryCondition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        ArrayList<WorkingPaperTableHeaderVO> titles = new ArrayList<WorkingPaperTableHeaderVO>();
        titles.add(new WorkingPaperTableHeaderVO("gzmc", GcI18nUtil.getMessage((String)"gc.workingpaper.header.rulename"), "left", (Object)"", 300));
        titles.add(new WorkingPaperTableHeaderVO("fetchConfigTitle", GcI18nUtil.getMessage((String)"gc.workingpaper.header.takenumber"), "left", (Object)"", 200));
        titles.add(new WorkingPaperTableHeaderVO("kmname", GcI18nUtil.getMessage((String)"gc.workingpaper.header.combiningsubject"), "left", (Object)"", 400));
        titles.add(new WorkingPaperTableHeaderVO("DXS_DEBIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.borrowingamount"), "right", (Object)"", 200));
        titles.add(new WorkingPaperTableHeaderVO("DXS_CREDIT_SHOWVALUE", GcI18nUtil.getMessage((String)"gc.workingpaper.header.lenderamount"), "right", (Object)"", 200));
        return titles;
    }

    @Override
    public WorkingPaperTableVO queryAllData(WorkingPaperQueryCondition queryCondition) {
        List<WorkingPaperTableHeaderVO> titles = this.getHeaderVO(queryCondition, null, null);
        List<WorkingPaperTableDataVO> datas = this.getDataVO(queryCondition, null, null);
        WorkingPaperTableVO workPagerMLDVO = new WorkingPaperTableVO();
        workPagerMLDVO.setTitles(titles);
        workPagerMLDVO.setDatas(datas);
        return workPagerMLDVO;
    }

    @Override
    public ExportExcelSheet getExcelVO(ExportContext context, Workbook workbook, WorkingPaperQueryCondition queryCondition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        queryCondition.setPageNum(-1);
        queryCondition.setPageSize(-1);
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List<WorkingPaperTableHeaderVO> header = this.getHeaderVO(queryCondition, null, null);
        ArrayList<String> headerCols = new ArrayList<String>();
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), GcI18nUtil.getMessage((String)"gc.workingpaper.sheet.ruledetailworkingpaper"), Integer.valueOf(1));
        for (int i = 0; i < header.size(); ++i) {
            WorkingPaperTableHeaderVO head = header.get(i);
            headerCols.add(head.getLabel());
        }
        rowDatas.add(headerCols.toArray());
        List<WorkingPaperTableDataVO> voList = this.getDataVO(queryCondition, null, null);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        if (CollectionUtils.isEmpty(voList)) {
            return exportExcelSheet;
        }
        int size = voList.size();
        for (int i = 0; i < size; ++i) {
            String kmmc;
            WorkingPaperTableDataVO workingPaperTableDataVO = voList.get(i);
            Object[] dataRow = new Object[5];
            String gzmc = ConverterUtils.getAsString((Object)workingPaperTableDataVO.getGzmc(), (String)"");
            if (gzmc.equals(kmmc = ConverterUtils.getAsString((Object)workingPaperTableDataVO.getKmname(), (String)""))) {
                dataRow[0] = gzmc;
            } else {
                dataRow[0] = gzmc;
                dataRow[1] = workingPaperTableDataVO.getFetchConfigTitle();
                dataRow[2] = kmmc;
                BigDecimal jfje = (BigDecimal)workingPaperTableDataVO.getZbvalue().get("DXS_DEBIT");
                dataRow[3] = null == jfje ? "" : df.format(jfje);
                BigDecimal dfje = (BigDecimal)workingPaperTableDataVO.getZbvalue().get("DXS_CREDIT");
                dataRow[4] = null == dfje ? "" : df.format(dfje);
            }
            rowDatas.add(dataRow);
        }
        CellStyle cellStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
        exportExcelSheet.getContentCellStyleCache().put(3, cellStyle);
        exportExcelSheet.getContentCellStyleCache().put(4, cellStyle);
        exportExcelSheet.getContentCellTypeCache().put(3, CellType.NUMERIC);
        exportExcelSheet.getContentCellTypeCache().put(4, CellType.NUMERIC);
        exportExcelSheet.getColumnWidthCache().put(0, 12800);
        exportExcelSheet.getColumnWidthCache().put(1, 7680);
        exportExcelSheet.getColumnWidthCache().put(2, 10240);
        exportExcelSheet.getColumnWidthCache().put(3, 5120);
        exportExcelSheet.getColumnWidthCache().put(4, 5120);
        List<CellRangeAddress> cellRangeAddresses = this.generateCellRanges(voList);
        exportExcelSheet.getCellRangeAddresses().addAll(cellRangeAddresses);
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    public List<CellRangeAddress> generateCellRanges(List<WorkingPaperTableDataVO> voList) {
        ArrayList<CellRangeAddress> mergeRegions = new ArrayList<CellRangeAddress>();
        int startRowIndexForRuleID = -1;
        int startRowIndexForGZMC = -1;
        int startRowIndexForFetchSetGroup = -1;
        for (int i = 0; i < voList.size(); ++i) {
            String prevRuleId;
            String prevGZMC;
            WorkingPaperTableDataVO currentRow = voList.get(i);
            int rowIndex = i + 1;
            String currentRuleId = currentRow.getRuleid();
            String currentFetchSetGroupId = currentRow.getFetchSetGroupId();
            String currentGZMC = currentRow.getGzmc();
            String currentKMName = currentRow.getKmname();
            if (StringUtils.isEmpty((CharSequence)currentRuleId) && StringUtils.isEmpty((CharSequence)currentKMName)) {
                if (startRowIndexForRuleID == -1) {
                    startRowIndexForRuleID = rowIndex;
                }
                mergeRegions.add(new CellRangeAddress(startRowIndexForRuleID, rowIndex, 0, 4));
                startRowIndexForRuleID = -1;
                continue;
            }
            if (startRowIndexForGZMC == -1) {
                startRowIndexForGZMC = rowIndex;
            }
            if (i > 0 && (StringUtils.isEmpty((CharSequence)(prevGZMC = voList.get(i - 1).getGzmc())) ? !StringUtils.isEmpty((CharSequence)currentGZMC) : !prevGZMC.equals(currentGZMC))) {
                if (startRowIndexForGZMC != rowIndex) {
                    mergeRegions.add(new CellRangeAddress(startRowIndexForGZMC, rowIndex - 1, 0, 0));
                }
                startRowIndexForGZMC = rowIndex;
            }
            if (startRowIndexForRuleID == -1) {
                startRowIndexForRuleID = rowIndex;
            }
            if (i > 0 && (StringUtils.isEmpty((CharSequence)(prevRuleId = voList.get(i - 1).getRuleid())) ? !StringUtils.isEmpty((CharSequence)currentRuleId) : !prevRuleId.equals(currentRuleId))) {
                if (startRowIndexForRuleID != rowIndex) {
                    mergeRegions.add(new CellRangeAddress(startRowIndexForRuleID, rowIndex - 1, 0, 0));
                }
                startRowIndexForRuleID = rowIndex;
            }
            if (startRowIndexForFetchSetGroup == -1) {
                startRowIndexForFetchSetGroup = rowIndex;
            }
            if (i > 0) {
                prevRuleId = voList.get(i - 1).getRuleid();
                String prevFetchSetGroupId = voList.get(i - 1).getFetchSetGroupId();
                if (!StringUtils.isEmpty((CharSequence)currentRuleId) && !StringUtils.isEmpty((CharSequence)prevRuleId) && prevRuleId.equals(currentRuleId) && (StringUtils.isEmpty((CharSequence)prevFetchSetGroupId) ? !StringUtils.isEmpty((CharSequence)currentFetchSetGroupId) : !prevFetchSetGroupId.equals(currentFetchSetGroupId))) {
                    if (startRowIndexForFetchSetGroup != rowIndex) {
                        mergeRegions.add(new CellRangeAddress(startRowIndexForFetchSetGroup, rowIndex - 1, 1, 1));
                    }
                    startRowIndexForFetchSetGroup = rowIndex;
                }
            }
            if (i != voList.size() - 1 && (currentRuleId == null || currentGZMC == null || currentGZMC.equals(voList.get(i + 1).getGzmc()) || currentRuleId.equals(voList.get(i + 1).getRuleid()))) continue;
            if (startRowIndexForRuleID != -1) {
                mergeRegions.add(new CellRangeAddress(startRowIndexForRuleID, rowIndex, 0, 0));
            }
            if (startRowIndexForFetchSetGroup != -1) {
                mergeRegions.add(new CellRangeAddress(startRowIndexForFetchSetGroup, rowIndex, 1, 1));
            }
            startRowIndexForRuleID = -1;
            startRowIndexForFetchSetGroup = -1;
        }
        return mergeRegions;
    }

    public Pagination<Map<String, Object>> getWorkPaperPentrationDatas(HttpServletRequest request, HttpServletResponse response, WorkingPaperPentrationQueryCondtion pentrationQueryCondtion, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum) {
        Pagination offsetPageByMrecid;
        String colunmName = pentrationQueryCondtion.getPentraColumnName();
        WorkingPaperTableDataVO workingPaperTableDataVO = (WorkingPaperTableDataVO)JsonUtils.readValue((String)pentrationQueryCondtion.getWorkPaperVoJson(), WorkingPaperTableDataVO.class);
        QueryParamsVO queryParamsVO = this.getOffsetQueryParams(pentrationQueryCondtion, workingPaperTableDataVO);
        GcOffSetAppOffsetService offSetItemAdjustService = (GcOffSetAppOffsetService)SpringContextUtils.getBean(GcOffSetAppOffsetService.class);
        Pagination offsetPage = offSetItemAdjustService.listOffsetEntrys(queryParamsVO, false);
        List offsetDatas = offsetPage.getContent();
        String systemID = WorkingPaperQueryUtils.getSystemID((WorkingPaperQueryCondition)pentrationQueryCondtion);
        String subjectCode = workingPaperTableDataVO.getKmcode();
        Set<String> subjectCodes = WorkingPaperQueryUtils.listAllChildCodesContainsSelf(systemID, subjectCode);
        String fetchSetGroupIdCondi = ConverterUtils.getAsString((Object)workingPaperTableDataVO.getFetchSetGroupId(), (String)"");
        Set mRecids = offsetDatas.stream().filter(offset -> {
            String fetchsetgroupid = ConverterUtils.getAsString(offset.get("FETCHSETGROUPID"), (String)"");
            if (!fetchSetGroupIdCondi.equals(fetchsetgroupid)) {
                return false;
            }
            return subjectCodes.contains(offset.get("SUBJECTCODE"));
        }).map(offset -> ConverterUtils.getAsString(offset.get("MRECID"))).filter(Objects::nonNull).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(mRecids)) {
            offsetPageByMrecid = new Pagination();
        } else {
            QueryParamsVO queryByMRercidParamsVO = this.getOffsetQueryByMRecidParams(pentrationQueryCondtion, workingPaperTableDataVO, new ArrayList<String>(mRecids));
            offsetPageByMrecid = offSetItemAdjustService.listOffsetEntrys(queryByMRercidParamsVO, false);
        }
        offsetPageByMrecid.setContent(this.setRowSpanAndSort(offsetPageByMrecid.getContent()));
        return offsetPageByMrecid;
    }

    private QueryParamsVO getOffsetQueryParams(WorkingPaperPentrationQueryCondtion penInfo, WorkingPaperTableDataVO workingPaperTableDataVO) {
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(penInfo, true);
        if (StringUtils.isEmpty((CharSequence)workingPaperTableDataVO.getRuleid())) {
            queryParamsVO.setOnlyQueryNullRule(true);
        } else {
            queryParamsVO.setRules(Collections.singletonList(workingPaperTableDataVO.getRuleid()));
        }
        String offsetSrcType = workingPaperTableDataVO.getOffsetSrcType();
        if (ObjectUtils.isEmpty(offsetSrcType)) {
            queryParamsVO.setOffSetSrcTypes(new ArrayList());
        } else {
            List srcTypeValues;
            List<OffSetSrcTypeEnum> phsOffSetSrcTypes = this.getPhsOffSetSrcTypes();
            List phsSrcTypeValues = phsOffSetSrcTypes.stream().map(OffSetSrcTypeEnum::getSrcTypeValue).collect(Collectors.toList());
            boolean isPhs = "phs".equals(offsetSrcType);
            if (isPhs) {
                srcTypeValues = phsSrcTypeValues;
            } else {
                List allSrcTypeValues = Arrays.asList(OffSetSrcTypeEnum.values()).stream().map(OffSetSrcTypeEnum::getSrcTypeValue).collect(Collectors.toList());
                allSrcTypeValues.removeAll(phsSrcTypeValues);
                srcTypeValues = allSrcTypeValues;
            }
            queryParamsVO.setOffSetSrcTypes(srcTypeValues);
        }
        queryParamsVO.setFilterDisableItem(true);
        return queryParamsVO;
    }

    private QueryParamsVO getOffsetQueryByMRecidParams(WorkingPaperPentrationQueryCondtion penInfo, WorkingPaperTableDataVO workingPaperTableDataVO, List<String> mRecids) {
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(penInfo, true);
        if (StringUtils.isEmpty((CharSequence)workingPaperTableDataVO.getRuleid())) {
            queryParamsVO.setOnlyQueryNullRule(true);
        } else {
            queryParamsVO.setRules(Collections.singletonList(workingPaperTableDataVO.getRuleid()));
        }
        queryParamsVO.setMrecids(mRecids);
        return queryParamsVO;
    }

    private List<Map<String, Object>> setRowSpanAndSort(List<Map<String, Object>> unSortedRecords) {
        if (CollectionUtils.isEmpty(unSortedRecords)) {
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
}


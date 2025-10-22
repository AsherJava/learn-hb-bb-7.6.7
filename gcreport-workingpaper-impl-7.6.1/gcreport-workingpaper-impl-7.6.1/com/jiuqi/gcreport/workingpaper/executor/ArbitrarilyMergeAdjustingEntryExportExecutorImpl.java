/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.workingpaper.executor;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeOffSetItemAdjustService;
import com.jiuqi.gcreport.workingpaper.service.ArbitrarilyMergeService;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class ArbitrarilyMergeAdjustingEntryExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private ArbitrarilyMergeOffSetItemAdjustService arbitrarilyMergeOffSetItemAdjustService;
    @Autowired
    private ArbitrarilyMergeService arbitrarilyMergeService;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    ArbitrarilyMergeAdjustingEntryExportExecutorImpl() {
    }

    public String getName() {
        return "ArbitrarilyMergeAdjustingEntryExportExecutor";
    }

    @Transactional(rollbackFor={Exception.class})
    public List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        WorkingPaperPentrationQueryCondtion ryPenInfo = (WorkingPaperPentrationQueryCondtion)JsonUtils.readValue((String)context.getParam(), WorkingPaperPentrationQueryCondtion.class);
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(ryPenInfo, false);
        queryParamsVO.setPageNum(-1);
        queryParamsVO.setPageSize(-1);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        Pagination<Map<String, Object>> offsetMap = this.arbitrarilyMergeService.listRyPentrationDatasOther(ryPenInfo);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        exportExcelSheets.add(this.arbitrarilyMergeOffSetItemAdjustService.exportRySheet("offsetSheet", queryParamsVO, offsetMap, cellStyleMap, context.isTemplateExportFlag()));
        context.getVarMap().put("offsetIntervalRows", this.getOffSetIntervalRowSet(offsetMap));
        return exportExcelSheets;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        Set rowSet;
        int rowNum = row.getRowNum();
        if (rowNum == 0) {
            return;
        }
        Object intervalColorRows = null;
        switch (excelSheet.getSheetNo()) {
            case 0: {
                intervalColorRows = context.getVarMap().get("offsetIntervalRows");
                break;
            }
            case 1: {
                intervalColorRows = context.getVarMap().get("notOffsetIntervalRows");
                break;
            }
            case 2: {
                intervalColorRows = context.getVarMap().get("notOffsetParentIntervalRows");
                break;
            }
        }
        if (intervalColorRows != null && (rowSet = (Set)intervalColorRows).contains(rowNum)) {
            this.setIntervalColor(workbook, cell);
        }
    }

    private void getIntervalRowByUnitGroup(List<Map<String, Object>> content, Set<Integer> rowNumber, Set<String> temp) {
        for (int i = 0; i < content.size(); ++i) {
            Map<String, Object> item = content.get(i);
            Object unitIdObj = item.get("UNITID");
            if (unitIdObj != null && !StringUtils.isEmpty((String)unitIdObj.toString())) {
                String oppUnitId = item.get("OPPUNITID").toString();
                if (unitIdObj.toString().compareTo(oppUnitId) > 0) {
                    temp.add(unitIdObj.toString() + oppUnitId);
                } else {
                    temp.add(oppUnitId + unitIdObj.toString());
                }
                if (temp.size() % 2 == 0) continue;
                rowNumber.add(i + 1);
                continue;
            }
            temp.clear();
        }
    }

    private void getIntervalRowByUnit(List<Map<String, Object>> content, Set<Integer> rowNumber, Set<String> temp) {
        for (int i = 0; i < content.size(); ++i) {
            String oppUnitId;
            Map<String, Object> item = content.get(i);
            if (item.get("UNITID") == null) continue;
            String unitId = item.get("UNITID").toString();
            if (unitId.compareTo(oppUnitId = item.get("OPPUNITID").toString()) > 0) {
                temp.add(unitId + oppUnitId);
            } else {
                temp.add(oppUnitId + unitId);
            }
            if (temp.size() % 2 == 0) continue;
            rowNumber.add(i + 1);
        }
    }

    private void getIntervalRowByRule(List<Map<String, Object>> content, Set<Integer> rowNumber, Set<String> temp) {
        for (int i = 0; i < content.size(); ++i) {
            Map<String, Object> item = content.get(i);
            if (item.get("UNIONRULEID") != null) {
                temp.add(item.get("UNIONRULEID").toString());
            }
            if (temp.size() % 2 == 0) continue;
            rowNumber.add(i + 1);
        }
    }

    private Set<Integer> getOffSetIntervalRowSet(Pagination<Map<String, Object>> offsetMap) {
        List content = offsetMap.getContent();
        if (CollectionUtils.isEmpty((Collection)content)) {
            return null;
        }
        HashSet<Integer> rowNumber = new HashSet<Integer>();
        HashSet<String> mrecids = new HashSet<String>();
        for (int i = 0; i < content.size(); ++i) {
            Map item = (Map)content.get(i);
            if (item.get("MRECID") != null) {
                mrecids.add(item.get("MRECID").toString());
            }
            if (mrecids.size() % 2 != 1) continue;
            rowNumber.add(i + 1);
        }
        return rowNumber;
    }

    private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headStringStyle = this.buildDefaultHeadCellStyle(workbook);
            headStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("headString", headStringStyle);
            CellStyle headAmtStyle = this.buildDefaultHeadCellStyle(workbook);
            headAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            headAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("headAmt", headAmtStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentString", contentStringStyle);
            CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
            contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("contentAmt", contentAmtStyle);
            XSSFCellStyle intervalColorString = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorString.setAlignment(HorizontalAlignment.LEFT);
            intervalColorString.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleMap.put("intervalColorString", intervalColorString);
            XSSFCellStyle intervalColorAmt = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorAmt.setAlignment(HorizontalAlignment.RIGHT);
            intervalColorAmt.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorAmt.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            intervalColorAmt.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("intervalColorAmt", intervalColorAmt);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    private void setIntervalColor(Workbook workbook, Cell cell) {
        if (cell.getCellStyle() != null && cell.getCellStyle().getAlignment() == HorizontalAlignment.RIGHT) {
            cell.setCellStyle(this.getCellStyleMap(workbook).get("intervalColorAmt"));
            return;
        }
        cell.setCellStyle(this.getCellStyleMap(workbook).get("intervalColorString"));
    }
}


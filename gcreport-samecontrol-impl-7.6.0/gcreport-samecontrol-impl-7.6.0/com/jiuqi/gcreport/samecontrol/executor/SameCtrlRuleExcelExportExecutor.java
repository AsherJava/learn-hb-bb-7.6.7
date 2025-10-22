/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.ColumnVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlExportExcelVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO
 */
package com.jiuqi.gcreport.samecontrol.executor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlRuleService;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlRuleExportProcessor;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.ColumnVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlExportExcelVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.stereotype.Component;

@Component
public class SameCtrlRuleExcelExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();
    private SameCtrlRuleService sameCtrlRuleService;

    public SameCtrlRuleExcelExportExecutor(SameCtrlRuleService sameCtrlRuleService) {
        this.sameCtrlRuleService = sameCtrlRuleService;
    }

    public String getName() {
        return "SameCtrlRuleExcelExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        assert (params != null);
        String reportSystemId = (String)params.get("reportSystemId");
        String taskId = (String)params.get("taskId");
        boolean isTemplateExportFlag = context.isTemplateExportFlag();
        List<SameCtrlRuleVO> ruleTree = this.sameCtrlRuleService.listRuleTree(reportSystemId, taskId);
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        for (int i = 0; i < ruleTree.get(0).getChildren().size(); ++i) {
            String sheetName = ((SameCtrlRuleVO)ruleTree.get(0).getChildren().get(i)).getTitle();
            ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(i), sheetName, Integer.valueOf(1));
            List<ColumnVO> colums = this.getSheetTitles();
            ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
            this.setSameCtrlRuleExportHead(colums, (SameCtrlRuleVO)ruleTree.get(0).getChildren().get(i), rowDatas, isTemplateExportFlag, cellStyleMap, exportExcelSheet);
            exportExcelSheet.getRowDatas().addAll(rowDatas);
            exportExcelSheets.add(exportExcelSheet);
        }
        return exportExcelSheets;
    }

    private void setSameCtrlRuleExportHead(List<ColumnVO> colums, SameCtrlRuleVO sameCtrlRuleVO, List<Object[]> rowDatas, boolean isTemplateExportFlag, Map<String, CellStyle> cellStyleMap, ExportExcelSheet exportExcelSheet) {
        int i;
        Object[] firstHead = new Object[colums.size()];
        String[] firstHeadKeys = new String[colums.size()];
        CellStyle[] headStyles = new CellStyle[colums.size()];
        CellStyle[] contentStyles = new CellStyle[colums.size()];
        CellType[] cellTypes = new CellType[colums.size()];
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle contentString = cellStyleMap.get("contentString");
        for (i = 0; i < colums.size(); ++i) {
            firstHead[i] = colums.get(i).getTitle();
            firstHeadKeys[i] = colums.get(i).getCode();
            headStyles[i] = headString;
            contentStyles[i] = contentString;
            cellTypes[i] = CellType.STRING;
        }
        for (i = 0; i < headStyles.length; ++i) {
            exportExcelSheet.getHeadCellStyleCache().put(i, headStyles[i]);
            exportExcelSheet.getContentCellStyleCache().put(i, contentStyles[i]);
            exportExcelSheet.getContentCellTypeCache().put(i, cellTypes[i]);
        }
        rowDatas.add(firstHead);
        this.getSameCtrlRuleExportData(sameCtrlRuleVO, firstHeadKeys, isTemplateExportFlag, rowDatas);
    }

    private void getSameCtrlRuleExportData(SameCtrlRuleVO sameCtrlRuleVO, String[] secondHeadKeys, boolean isTemplateExportFlag, List<Object[]> rowDatas) {
        if (!isTemplateExportFlag) {
            List<SameCtrlExportExcelVO> sheetDatas = new SameCtrlRuleExportProcessor(sameCtrlRuleVO, sameCtrlRuleVO.getReportSystem()).getSheetData();
            for (SameCtrlExportExcelVO sheetData : sheetDatas) {
                Object[] rowData = new Object[secondHeadKeys.length];
                Map<String, Object> dataMap = this.getSameCtrlRuleDataMap(sheetData, secondHeadKeys);
                for (int j = 0; j < secondHeadKeys.length; ++j) {
                    rowData[j] = dataMap.get(secondHeadKeys[j]);
                }
                rowDatas.add(rowData);
            }
        }
    }

    private Map<String, Object> getSameCtrlRuleDataMap(SameCtrlExportExcelVO sameCtrlExportExcelVO, String[] secondHeadKeys) {
        HashMap<String, Object> value = new HashMap<String, Object>(16);
        value.put(secondHeadKeys[0], sameCtrlExportExcelVO.getIndex());
        value.put(secondHeadKeys[1], sameCtrlExportExcelVO.getRuleTitle());
        value.put(secondHeadKeys[2], sameCtrlExportExcelVO.getDebitOrCredit());
        value.put(secondHeadKeys[3], sameCtrlExportExcelVO.getSubjectCode());
        value.put(secondHeadKeys[4], sameCtrlExportExcelVO.getSubjectTitle());
        value.put(secondHeadKeys[5], sameCtrlExportExcelVO.getFormula());
        value.put(secondHeadKeys[6], sameCtrlExportExcelVO.getRuleCondition());
        value.put(secondHeadKeys[7], sameCtrlExportExcelVO.getRuleType());
        value.put(secondHeadKeys[8], sameCtrlExportExcelVO.getBusinessType());
        value.put(secondHeadKeys[9], sameCtrlExportExcelVO.getStartFlag());
        value.put(secondHeadKeys[10], sameCtrlExportExcelVO.getUnit());
        return value;
    }

    private List<ColumnVO> getSheetTitles() {
        ArrayList<ColumnVO> colums = new ArrayList<ColumnVO>();
        colums.add(new ColumnVO("index", "\u5e8f\u53f7"));
        colums.add(new ColumnVO("ruleTitle", "\u89c4\u5219\u540d\u79f0"));
        colums.add(new ColumnVO("debitOrCredit", "\u501f/\u8d37"));
        colums.add(new ColumnVO("subjectCode", "\u79d1\u76ee\u4ee3\u7801"));
        colums.add(new ColumnVO("subjectTitle", "\u79d1\u76ee\u540d\u79f0"));
        colums.add(new ColumnVO("formula", "\u516c\u5f0f"));
        colums.add(new ColumnVO("ruleCondition", "\u9002\u7528\u6761\u4ef6"));
        colums.add(new ColumnVO("ruleType", "\u89c4\u5219\u7c7b\u578b"));
        colums.add(new ColumnVO("businessType", "\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b"));
        colums.add(new ColumnVO("startFlag", "\u662f\u5426\u505c\u7528"));
        colums.add(new ColumnVO("unit", "\u5355\u4f4d"));
        return colums;
    }

    private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headStringStyle = this.buildDefaultHeadCellStyle(workbook);
            headStringStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyleMap.put("headString", headStringStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentString", contentStringStyle);
            CellStyle contentNumberStyle = this.buildDefaultContentCellStyle(workbook);
            contentNumberStyle.setAlignment(HorizontalAlignment.RIGHT);
            cellStyleMap.put("contentNumber", contentNumberStyle);
            XSSFCellStyle intervalColorString = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorString.setAlignment(HorizontalAlignment.LEFT);
            intervalColorString.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleMap.put("intervalColorString", intervalColorString);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        List<ColumnVO> colums = this.getSheetTitles();
        boolean flag = false;
        int index = 0;
        int rownum = -1;
        for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
            String afterRow;
            Cell cell2 = sheet.getRow(i).getCell(1);
            Cell cell3 = i == sheet.getLastRowNum() ? null : sheet.getRow(i + 1).getCell(1);
            String frontRow = cell2.getStringCellValue();
            String string = afterRow = cell3 == null ? "" : cell3.getStringCellValue();
            if (frontRow.equals(afterRow)) {
                flag = true;
                ++index;
                if (rownum != -1) continue;
                rownum = i;
                continue;
            }
            if (!flag) continue;
            int col = 0;
            for (ColumnVO column : colums) {
                if ("index".equals(column.getCode()) || "ruleTitle".equals(column.getCode()) || "ruleType".equals(column.getCode()) || "businessType".equals(column.getCode()) || "ruleCondition".equals(column.getCode()) || "startFlag".equals(column.getCode())) {
                    sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + index, col, col));
                }
                ++col;
            }
            flag = false;
            index = 0;
            rownum = -1;
        }
    }
}


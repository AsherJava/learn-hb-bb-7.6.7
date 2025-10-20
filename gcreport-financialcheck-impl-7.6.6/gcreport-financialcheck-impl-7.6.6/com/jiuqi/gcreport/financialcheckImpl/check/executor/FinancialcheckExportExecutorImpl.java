/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package com.jiuqi.gcreport.financialcheckImpl.check.executor;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.financialcheckImpl.check.service.FinancialCheckService;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialcheckExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    FinancialCheckService service;

    public String getName() {
        return "FinancialcheckExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        ExportExcelSheet exportExcelSheet;
        FinancialcheckExportExecutorParam exportParam = (FinancialcheckExportExecutorParam)JsonUtils.readValue((String)context.getParam(), FinancialcheckExportExecutorParam.class);
        FinancialCheckQueryConditionVO condition = exportParam.getCondition();
        condition.setPageNum(Integer.valueOf(-1));
        String showType = null;
        ArrayList<ExportExcelSheet> exportExcelSheetList = new ArrayList<ExportExcelSheet>();
        Map<Object, Object> titleCheckedMap = new HashMap();
        Map<Object, Object> titleUnCheckedMap = new HashMap();
        if (exportParam.getExportOption().equals("allTab")) {
            titleCheckedMap = this.service.getExcelColumnTitleMap("checked", exportParam.checkedSelectColumns);
            titleUnCheckedMap = this.service.getExcelColumnTitleMap("unchecked", exportParam.unCheckedSelectColumns);
            showType = "SCHEME";
        } else if (exportParam.getTabSelect().equals("checked")) {
            titleCheckedMap = this.service.getExcelColumnTitleMap(exportParam.getTabSelect(), exportParam.checkedSelectColumns);
        } else {
            titleUnCheckedMap = this.service.getExcelColumnTitleMap(exportParam.getTabSelect(), exportParam.unCheckedSelectColumns);
            showType = condition.getShowType();
        }
        int tab = 0;
        if (!titleCheckedMap.isEmpty()) {
            exportExcelSheet = this.getExcelSheet("checked", tab, showType, condition, titleCheckedMap, workbook);
            ++tab;
            exportExcelSheetList.add(exportExcelSheet);
        }
        if (!titleUnCheckedMap.isEmpty()) {
            exportExcelSheet = this.getExcelSheet("unchecked", tab, showType, condition, titleUnCheckedMap, workbook);
            exportExcelSheetList.add(exportExcelSheet);
        }
        return exportExcelSheetList;
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        for (int i = 0; i < ((Object[])excelSheet.getRowDatas().get(0)).length; ++i) {
            sheet.setColumnWidth(i, 6144);
        }
    }

    protected void callBackRow(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row) {
        if (row.getLastCellNum() == 1) {
            CellStyle cellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short)14);
            cellStyle.setFont(font);
            row.getCell(0).setCellStyle(cellStyle);
        }
    }

    private ExportExcelSheet getExcelSheet(String type, int tab, String showType, FinancialCheckQueryConditionVO condition, Map<String, String> titleMap, Workbook workbook) {
        String tabName;
        List<FinancialCheckQueryVO> dataList = new ArrayList();
        List<FinancialCheckQueryVO> dataByOppUnitList = new ArrayList();
        if (type.equals("checked")) {
            tabName = "\u5df2\u5bf9\u8d26";
            condition.setShowType(null);
            dataList = this.service.queryChecked(condition).getList();
        } else {
            tabName = "\u672a\u5bf9\u8d26";
            if (null != showType && showType.equals("UNIT")) {
                dataList = this.service.queryUncheckedGroupByUnit(condition).getList();
                dataByOppUnitList = this.service.queryUncheckedGroupByOppUnit(condition).getList();
            } else {
                dataList = this.service.queryUncheckedGroupByScheme(condition).getList();
            }
        }
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(tab), tabName);
        ArrayList<String> columnKeys = new ArrayList<String>(titleMap.keySet());
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        Object[] headDatas = new Object[columnKeys.size()];
        for (int i = 0; i < columnKeys.size(); ++i) {
            String key = (String)columnKeys.get(i);
            headDatas[i] = titleMap.get(key);
        }
        rowDatas.add(headDatas);
        if (!dataList.isEmpty()) {
            int checkTypeColumn = -1;
            if (type.equals("checked")) {
                for (int i = 0; i < columnKeys.size(); ++i) {
                    if (!((String)columnKeys.get(i)).equals("checkType")) continue;
                    checkTypeColumn = i;
                }
            }
            List<Map<String, Object>> exportDatas = this.service.exportData(dataList);
            int rowNumber = rowDatas.size();
            if (type.equals("unchecked") && showType.equals("UNIT")) {
                Object[] rowData = new Object[]{"\u672c\u65b9\u5355\u4f4d\u672a\u5bf9\u8d26"};
                rowDatas.add(rowData);
                this.addMergedRegion(exportExcelSheet, rowNumber, rowNumber, 0, 1);
                ++rowNumber;
            }
            String currentCheckId = null;
            for (Map<String, Object> exportData2 : exportDatas) {
                Object[] rowData = new Object[columnKeys.size()];
                if (checkTypeColumn > -1) {
                    String checkId = exportData2.get("checkId").toString();
                    if (currentCheckId == null) {
                        currentCheckId = checkId;
                    } else if (currentCheckId.equals(checkId)) {
                        this.addMergedRegion(exportExcelSheet, rowNumber - 1, rowNumber, checkTypeColumn, checkTypeColumn);
                    } else {
                        currentCheckId = checkId;
                    }
                }
                for (int i = 0; i < columnKeys.size(); ++i) {
                    String key = (String)columnKeys.get(i);
                    Object valueObj = exportData2.get(key);
                    if (valueObj != null) {
                        if (valueObj instanceof Double) {
                            String value;
                            CellType cellType = (CellType)exportExcelSheet.getContentCellTypeCache().get(i);
                            if (!CellType.NUMERIC.equals((Object)cellType)) {
                                exportExcelSheet.getContentCellTypeCache().put(i, CellType.NUMERIC);
                                CellStyle cellStyle = (CellStyle)exportExcelSheet.getContentCellStyleCache().get(i);
                                if (cellStyle == null) {
                                    CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
                                    contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
                                    contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
                                    exportExcelSheet.getContentCellStyleCache().put(i, contentAmtStyle);
                                }
                            }
                            if ((value = valueObj.toString()).length() == 0) continue;
                            rowData[i] = ConverterUtils.getAsBigDecimal((Object)value.replace(",", ""), (BigDecimal)BigDecimal.ZERO);
                            continue;
                        }
                        rowData[i] = valueObj.toString();
                        continue;
                    }
                    rowData[i] = null;
                }
                rowDatas.add(rowData);
                ++rowNumber;
            }
        }
        if (!dataByOppUnitList.isEmpty()) {
            if (type.equals("unchecked") && showType.equals("UNIT")) {
                int rowNumber = rowDatas.size();
                Object[] rowData = new Object[]{"\u5bf9\u65b9\u5355\u4f4d\u672a\u5bf9\u8d26"};
                rowDatas.add(rowData);
                this.addMergedRegion(exportExcelSheet, rowNumber, rowNumber, 0, 1);
            }
            List<Map<String, Object>> exportDatas = this.service.exportData(dataByOppUnitList);
            exportDatas.stream().forEach(exportData -> {
                Object[] rowData = new Object[columnKeys.size()];
                for (int i = 0; i < columnKeys.size(); ++i) {
                    String key = (String)columnKeys.get(i);
                    Object valueObj = exportData.get(key);
                    if (valueObj != null) {
                        if (valueObj instanceof Double) {
                            String value = valueObj.toString();
                            if (value.length() == 0) continue;
                            rowData[i] = ConverterUtils.getAsBigDecimal((Object)value.replace(",", ""), (BigDecimal)BigDecimal.ZERO);
                            continue;
                        }
                        rowData[i] = valueObj.toString();
                        continue;
                    }
                    rowData[i] = null;
                }
                rowDatas.add(rowData);
            });
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    private void addMergedRegion(ExportExcelSheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
        if (rowStart == rowEnd && colStart == colEnd) {
            return;
        }
        CellRangeAddress region = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
        sheet.getCellRangeAddresses().add(region);
    }

    public static class FinancialcheckExportExecutorParam {
        String exportOption;
        FinancialCheckQueryConditionVO condition;
        String tabSelect;
        private TableColumnVO[] checkedSelectColumns;
        private TableColumnVO[] unCheckedSelectColumns;

        public String getExportOption() {
            return this.exportOption;
        }

        public void setExportOption(String exportOption) {
            this.exportOption = exportOption;
        }

        public FinancialCheckQueryConditionVO getCondition() {
            return this.condition;
        }

        public void setCondition(FinancialCheckQueryConditionVO condition) {
            this.condition = condition;
        }

        public String getTabSelect() {
            return this.tabSelect;
        }

        public void setTabSelect(String tabSelect) {
            this.tabSelect = tabSelect;
        }

        public TableColumnVO[] getCheckedSelectColumns() {
            return this.checkedSelectColumns;
        }

        public void setCheckedSelectColumns(TableColumnVO[] checkedSelectColumns) {
            this.checkedSelectColumns = checkedSelectColumns;
        }

        public TableColumnVO[] getUnCheckedSelectColumns() {
            return this.unCheckedSelectColumns;
        }

        public void setUnCheckedSelectColumns(TableColumnVO[] unCheckedSelectColumns) {
            this.unCheckedSelectColumns = unCheckedSelectColumns;
        }
    }
}


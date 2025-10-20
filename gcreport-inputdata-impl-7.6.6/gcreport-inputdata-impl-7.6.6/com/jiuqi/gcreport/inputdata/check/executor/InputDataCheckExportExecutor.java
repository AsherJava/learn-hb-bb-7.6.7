/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 */
package com.jiuqi.gcreport.inputdata.check.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.executor.sheet.InputDataCheckTabExportExcelSheet;
import com.jiuqi.gcreport.inputdata.check.executor.vo.InputDataExportExcelColumnVO;
import com.jiuqi.gcreport.inputdata.check.service.InputDataCheckService;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckTabEnum;
import com.jiuqi.gcreport.inputdata.util.I18nTableUtils;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InputDataCheckExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private InputDataCheckService inputDataCheckService;
    @Autowired
    private I18nTableUtils i18nTableUtils;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    private final DateFormat EXPORT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static String[] SELECTCHECKTABCOLUMNS = new String[]{"index", "CHECKTYPETITLE", "RULETITLE", "UNITTITLE", "OPPUNITTITLE", "SUBJECTTITLE", "CHECKDEBIT", "CHECKCREDIT", "CHECKAMT", "CHECKDIFF", "OFFSETSTATETITLE", "MEMO"};
    public static String[] SELECTUNCHECKTABCOLUMNS = new String[]{"index", "RULETITLE", "UNITTITLE", "OPPUNITTITLE", "SUBJECTTITLE", "CHECKDEBIT", "CHECKCREDIT", "CHECKDIFF", "OFFSETSTATETITLE", "MEMO"};
    public static String[] SELECTALLCHECKTABCOLUMNS = new String[]{"index", "CHECKSTATETITLE", "CHECKTYPETITLE", "RULETITLE", "UNITTITLE", "OPPUNITTITLE", "SUBJECTTITLE", "CHECKDEBIT", "CHECKCREDIT", "CHECKAMT", "CHECKDIFF", "OFFSETSTATETITLE", "MEMO"};

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        ExportExcelSheet exportExcelSheet;
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        InputDataCheckCondition inputDataCheckCondition = (InputDataCheckCondition)JsonUtils.readValue((String)context.getParam(), InputDataCheckCondition.class);
        List<String> exportTabs = this.getExportTabs(inputDataCheckCondition);
        inputDataCheckCondition.setPageNum(Integer.valueOf(-1));
        inputDataCheckCondition.setPageSize(Integer.valueOf(1));
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataCheckCondition.getTaskId());
        List<DesignFieldDefineVO> designFieldDefines = this.i18nTableUtils.getAllFieldsByTableName(tableName, new HashSet<String>());
        Map<String, DesignFieldDefineVO> i18nTitleGroupByCode = designFieldDefines.stream().collect(Collectors.toMap(DesignFieldDefineVO::getKey, vo -> vo));
        int sheetNo = -1;
        if (exportTabs.contains(InputDataCheckTabEnum.AllDATA.getTab())) {
            exportExcelSheet = this.createSheet(inputDataCheckCondition, context.isTemplateExportFlag(), ++sheetNo, InputDataCheckTabEnum.AllDATA, i18nTitleGroupByCode);
            exportExcelSheets.add(exportExcelSheet);
        }
        if (exportTabs.contains(InputDataCheckTabEnum.CHECKTAB.getTab())) {
            exportExcelSheet = this.createSheet(inputDataCheckCondition, context.isTemplateExportFlag(), ++sheetNo, InputDataCheckTabEnum.CHECKTAB, i18nTitleGroupByCode);
            exportExcelSheets.add(exportExcelSheet);
        }
        if (exportTabs.contains(InputDataCheckTabEnum.UNCHECKTAB.getTab())) {
            exportExcelSheet = this.createSheet(inputDataCheckCondition, context.isTemplateExportFlag(), ++sheetNo, InputDataCheckTabEnum.UNCHECKTAB, i18nTitleGroupByCode);
            exportExcelSheets.add(exportExcelSheet);
        }
        return exportExcelSheets;
    }

    private List<String> getExportTabs(InputDataCheckCondition inputDataCheckCondition) {
        String exportTabType = inputDataCheckCondition.getExportTabType();
        if (StringUtils.isEmpty((String)exportTabType)) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.exportmsg"));
        }
        ArrayList<String> exportTabs = new ArrayList<String>();
        if ("currentTab".equals(exportTabType)) {
            exportTabs.add(inputDataCheckCondition.getCurrentTab());
        } else if ("selectTab".equals(exportTabType)) {
            List selectExportTabs = inputDataCheckCondition.getSelectExportTabs();
            if (org.springframework.util.CollectionUtils.isEmpty(selectExportTabs)) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.exportmsg"));
            }
            exportTabs.addAll(selectExportTabs);
        } else {
            exportTabs.add(InputDataCheckTabEnum.AllDATA.getTab());
            exportTabs.add(InputDataCheckTabEnum.CHECKTAB.getTab());
            exportTabs.add(InputDataCheckTabEnum.UNCHECKTAB.getTab());
        }
        return exportTabs;
    }

    public String getName() {
        return "InputDataCheckExportExcelExecutor";
    }

    private ExportExcelSheet createSheet(InputDataCheckCondition inputDataCheckCondition, Boolean templateExportFlag, int sheetNo, InputDataCheckTabEnum inputDataCheckTabEnum, Map<String, DesignFieldDefineVO> i18nTitleGroupByCode) {
        Pagination<Map<String, Object>> pagination;
        InputDataCheckTabExportExcelSheet tabExportExcelSheet = null;
        if (InputDataCheckTabEnum.AllDATA.equals((Object)inputDataCheckTabEnum)) {
            tabExportExcelSheet = new InputDataCheckTabExportExcelSheet(sheetNo, GcI18nUtil.getMessage((String)"gc.inputdata.check.alltab"), 1);
            pagination = this.inputDataCheckService.allCheckTabDatas(inputDataCheckCondition);
            this.creataRowDatas(tabExportExcelSheet, inputDataCheckCondition, pagination.getContent(), SELECTALLCHECKTABCOLUMNS, i18nTitleGroupByCode, templateExportFlag, inputDataCheckTabEnum);
        }
        if (InputDataCheckTabEnum.CHECKTAB.equals((Object)inputDataCheckTabEnum)) {
            tabExportExcelSheet = new InputDataCheckTabExportExcelSheet(sheetNo, GcI18nUtil.getMessage((String)"gc.inputdata.check.checktab"), 1);
            pagination = this.inputDataCheckService.checkTabDatas(inputDataCheckCondition);
            this.creataRowDatas(tabExportExcelSheet, inputDataCheckCondition, pagination.getContent(), SELECTCHECKTABCOLUMNS, i18nTitleGroupByCode, templateExportFlag, inputDataCheckTabEnum);
        }
        if (InputDataCheckTabEnum.UNCHECKTAB.equals((Object)inputDataCheckTabEnum)) {
            tabExportExcelSheet = new InputDataCheckTabExportExcelSheet(sheetNo, GcI18nUtil.getMessage((String)"gc.inputdata.check.unchecktab"), 1);
            pagination = this.inputDataCheckService.unCheckTabDatas(inputDataCheckCondition);
            this.creataRowDatas(tabExportExcelSheet, inputDataCheckCondition, pagination.getContent(), SELECTUNCHECKTABCOLUMNS, i18nTitleGroupByCode, templateExportFlag, inputDataCheckTabEnum);
        }
        return tabExportExcelSheet;
    }

    private void creataRowDatas(InputDataCheckTabExportExcelSheet tabExportExcelSheet, InputDataCheckCondition inputDataCheckCondition, List<Map<String, Object>> dataItems, String[] columns, Map<String, DesignFieldDefineVO> i18nTitleGroupByCode, Boolean templateExportFlag, InputDataCheckTabEnum inputDataCheckTabEnum) {
        InputDataExportExcelColumnVO inputDataExportExcelColumn = this.getInputDataExportExcelColumn(columns, inputDataCheckCondition.getOtherShowColumns(), inputDataCheckCondition.getCheckGatherColumns(), i18nTitleGroupByCode);
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        List<String> titles = inputDataExportExcelColumn.getAllTitleColumns();
        List<String> titleKeys = inputDataExportExcelColumn.getTitleKeys();
        List<String> amtKeys = inputDataExportExcelColumn.getAmtColumns();
        rowDatas.add(titles.toArray(new String[0]));
        BitSet amtCellCol = new BitSet();
        if (!org.springframework.util.CollectionUtils.isEmpty(amtKeys)) {
            for (int i = 0; i < titleKeys.size(); ++i) {
                String key = titleKeys.get(i);
                if (!amtKeys.contains(key)) continue;
                amtCellCol.set(i);
            }
        }
        tabExportExcelSheet.setAmtCellCol(amtCellCol);
        if (templateExportFlag.booleanValue()) {
            tabExportExcelSheet.getRowDatas().addAll(rowDatas);
            return;
        }
        if (org.springframework.util.CollectionUtils.isEmpty(dataItems)) {
            tabExportExcelSheet.getRowDatas().addAll(rowDatas);
            return;
        }
        List<Map<String, Object>> subtotalRowDataItems = this.addSubtotalRowByRule(dataItems);
        int mergeStart = -1;
        int rowIndex = 1;
        String curMercid = null;
        for (Map<String, Object> item : subtotalRowDataItems) {
            Object[] rowData = new Object[inputDataExportExcelColumn.getAllTitleColumns().size()];
            for (int j = 0; j < inputDataExportExcelColumn.getTitleKeys().size(); ++j) {
                String key = titleKeys.get(j);
                Object valueObj = item.get(key);
                if (valueObj != null) {
                    if (amtKeys.contains(key)) {
                        String value = valueObj.toString();
                        if (value.length() == 0) continue;
                        rowData[j] = Double.valueOf(value.replace(",", ""));
                        continue;
                    }
                    if (valueObj instanceof Date) {
                        rowData[j] = this.EXPORT_DATE_FORMAT.format((Date)valueObj);
                        continue;
                    }
                    rowData[j] = valueObj.toString();
                    continue;
                }
                rowData[j] = null;
            }
            String recordKey = (String)item.get("RECORDKEY");
            boolean addMergeFlag = this.setTabMergedRegion(inputDataCheckTabEnum, tabExportExcelSheet, recordKey, curMercid, mergeStart, rowIndex, subtotalRowDataItems.size());
            if (addMergeFlag) {
                mergeStart = rowIndex;
                curMercid = recordKey;
            }
            rowDatas.add(rowData);
            ++rowIndex;
        }
        tabExportExcelSheet.getRowDatas().addAll(rowDatas);
    }

    private boolean setTabMergedRegion(InputDataCheckTabEnum inputDataCheckTabEnum, InputDataCheckTabExportExcelSheet tabExportExcelSheet, String recordKey, String curMercid, int mergeStart, int rowIndex, int totalCount) {
        boolean addMergeFlag = false;
        if (null != recordKey) {
            if (null == curMercid) {
                return true;
            }
            if (!curMercid.equals(recordKey)) {
                this.addMergedRegion(tabExportExcelSheet, mergeStart, rowIndex - 2, 0, 0);
                if (!InputDataCheckTabEnum.UNCHECKTAB.equals((Object)inputDataCheckTabEnum)) {
                    this.addMergedRegion(tabExportExcelSheet, mergeStart, rowIndex - 2, 1, 1);
                    this.addMergedRegion(tabExportExcelSheet, mergeStart, rowIndex - 2, 2, 2);
                    if (InputDataCheckTabEnum.AllDATA.equals((Object)inputDataCheckTabEnum)) {
                        this.addMergedRegion(tabExportExcelSheet, mergeStart, rowIndex - 2, 3, 3);
                    }
                }
                addMergeFlag = true;
            }
        }
        if (rowIndex == totalCount) {
            this.addMergedRegion(tabExportExcelSheet, mergeStart, rowIndex - 1, 0, 0);
            if (!InputDataCheckTabEnum.UNCHECKTAB.equals((Object)inputDataCheckTabEnum)) {
                this.addMergedRegion(tabExportExcelSheet, mergeStart, rowIndex - 1, 1, 1);
                this.addMergedRegion(tabExportExcelSheet, mergeStart, rowIndex - 1, 2, 2);
                if (InputDataCheckTabEnum.AllDATA.equals((Object)inputDataCheckTabEnum)) {
                    this.addMergedRegion(tabExportExcelSheet, mergeStart, rowIndex - 1, 3, 3);
                }
            }
            addMergeFlag = true;
        }
        return addMergeFlag;
    }

    private InputDataExportExcelColumnVO getInputDataExportExcelColumn(String[] columns, List<String> otherShowColumns, List<String> checkGatherColumns, Map<String, DesignFieldDefineVO> i18nTitleGroupByCode) {
        List allColumnKeys = CollectionUtils.newArrayList((Object[])columns).stream().collect(Collectors.toList());
        if (!org.springframework.util.CollectionUtils.isEmpty(otherShowColumns)) {
            allColumnKeys.addAll(otherShowColumns.stream().collect(Collectors.toList()));
        }
        if (!org.springframework.util.CollectionUtils.isEmpty(checkGatherColumns)) {
            allColumnKeys.addAll(checkGatherColumns.stream().collect(Collectors.toList()));
        }
        allColumnKeys.addAll(checkGatherColumns.stream().collect(Collectors.toList()));
        ArrayList<String> allTitleColumns = new ArrayList<String>();
        ArrayList<String> amtColumns = new ArrayList<String>();
        ArrayList<String> titleKeys = new ArrayList<String>();
        for (String column : allColumnKeys) {
            if ("index".equals(column) && !titleKeys.contains("index")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.sn"));
                titleKeys.add("index");
            }
            if (("ORGCODE".equals(column) || "MDCODE".equals(column) || "UNITTITLE".equals(column)) && !titleKeys.contains("UNITTITLE")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.unit"));
                titleKeys.add("UNITTITLE");
            }
            if (("OPPUNITTITLE".equals(column) || "OPPUNITID".equals(column)) && !titleKeys.contains("OPPUNITTITLE")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.oppunit"));
                titleKeys.add("OPPUNITTITLE");
            }
            if (("RULETITLE".equals(column) || "UNIONRULEID".equals(column)) && !titleKeys.contains("RULETITLE")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.rule"));
                titleKeys.add("RULETITLE");
            }
            if (("CHECKTYPETITLE".equals(column) || "CHECKTYPE".equals(column)) && !titleKeys.contains("CHECKTYPETITLE")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.checktype"));
                titleKeys.add("CHECKTYPETITLE");
            }
            if (("CHECKSTATETITLE".equals(column) || "CHECKSTATE".equals(column)) && !titleKeys.contains("CHECKSTATETITLE")) {
                allTitleColumns.add(i18nTitleGroupByCode.get("CHECKSTATE").getLabel());
                titleKeys.add("CHECKSTATETITLE");
            }
            if (("SUBJECTTITLE".equals(column) || "SUBJECTCODE".equals(column)) && !titleKeys.contains("SUBJECTTITLE")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.subject"));
                titleKeys.add("SUBJECTTITLE");
            }
            if ("CHECKDEBIT".equals(column) && !titleKeys.contains("CHECKDEBIT")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.checkdebit"));
                amtColumns.add("CHECKDEBIT");
                titleKeys.add("CHECKDEBIT");
            }
            if ("CHECKCREDIT".equals(column) && !titleKeys.contains("CHECKCREDIT")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.checkcredit"));
                amtColumns.add("CHECKCREDIT");
                titleKeys.add("CHECKCREDIT");
            }
            if (("OFFSETSTATETITLE".equals(column) || "OFFSETSTATE".equals(column)) && !titleKeys.contains("OFFSETSTATETITLE")) {
                allTitleColumns.add(i18nTitleGroupByCode.get("OFFSETSTATE").getLabel());
                titleKeys.add("OFFSETSTATETITLE");
            }
            if ("CHECKAMT".equals(column) && !titleKeys.contains("CHECKAMT")) {
                allTitleColumns.add(i18nTitleGroupByCode.get("CHECKAMT").getLabel());
                amtColumns.add("CHECKAMT");
                titleKeys.add("CHECKAMT");
            }
            if ("MEMO".equals(column) && !titleKeys.contains("MEMO")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.memo"));
                titleKeys.add("MEMO");
            }
            if ("CHECKDIFF".equals(column) && !titleKeys.contains("CHECKDIFF")) {
                allTitleColumns.add(GcI18nUtil.getMessage((String)"gc.inputdata.check.checkdiff"));
                amtColumns.add("CHECKDIFF");
                titleKeys.add("CHECKDIFF");
            }
            if (titleKeys.contains(column)) continue;
            DesignFieldDefineVO designFieldDefineVO = i18nTitleGroupByCode.get(column);
            allTitleColumns.add(designFieldDefineVO.getLabel());
            if (ColumnModelType.DOUBLE.equals((Object)designFieldDefineVO.getType()) || ColumnModelType.BIGDECIMAL.equals((Object)designFieldDefineVO.getType())) {
                amtColumns.add(column);
            }
            titleKeys.add(column);
        }
        InputDataExportExcelColumnVO inputDataExportExcelColumnVO = new InputDataExportExcelColumnVO();
        inputDataExportExcelColumnVO.setAmtColumns(amtColumns);
        inputDataExportExcelColumnVO.setAllTitleColumns(allTitleColumns);
        inputDataExportExcelColumnVO.setTitleKeys(titleKeys);
        return inputDataExportExcelColumnVO;
    }

    private void addMergedRegion(ExportExcelSheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
        if (rowStart == rowEnd && colStart == colEnd || rowEnd < rowStart || colEnd < colStart) {
            return;
        }
        CellRangeAddress region = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
        sheet.getCellRangeAddresses().add(region);
    }

    private List<Map<String, Object>> addSubtotalRowByRule(List<Map<String, Object>> content) {
        BigDecimal debitSum = new BigDecimal(0.0);
        BigDecimal creditSum = new BigDecimal(0.0);
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        HashSet<String> recordKeySet = new HashSet<String>();
        for (Map<String, Object> item : content) {
            String recordKey = ConverterUtils.getAsString((Object)item.get("RECORDKEY"));
            if (!recordKeySet.contains(recordKey)) {
                recordKeySet.add(recordKey);
                if (recordKeySet.size() > 1) {
                    result.add(this.createSubtotalRow(debitSum, creditSum));
                    debitSum = new BigDecimal(0.0);
                    creditSum = new BigDecimal(0.0);
                }
            }
            result.add(item);
            debitSum = debitSum.add(ConverterUtils.getAsBigDecimal((Object)item.get("CHECKDEBIT")));
            creditSum = creditSum.add(ConverterUtils.getAsBigDecimal((Object)item.get("CHECKCREDIT")));
        }
        result.add(this.createSubtotalRow(debitSum, creditSum));
        return result;
    }

    private Map<String, Object> createSubtotalRow(BigDecimal debitSum, BigDecimal creditSum) {
        HashMap<String, Object> subtotalRow = new HashMap<String, Object>();
        subtotalRow.put("index", "\u5c0f\u8ba1");
        subtotalRow.put("CHECKDEBIT", debitSum.doubleValue());
        subtotalRow.put("CHECKCREDIT", creditSum.doubleValue());
        subtotalRow.put("CHECKDIFF", debitSum.subtract(creditSum).doubleValue());
        return subtotalRow;
    }

    public Set<Integer> getSetIntervalRowSet(Pagination<Map<String, Object>> dataMap) {
        List content = dataMap.getContent();
        HashSet<Integer> rowNumber = new HashSet<Integer>();
        for (int i = 0; i < content.size(); ++i) {
            Map item = (Map)content.get(i);
            if (!"\u5c0f\u8ba1".equals(item.get("UNITTITLE"))) continue;
            rowNumber.add(i + 1);
        }
        return rowNumber;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO
 *  com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.FinancialCheckQueryTypeEnum
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.service.GcFinancialCheckDataEntryService;
import com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputConditionVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.DataInputVO;
import com.jiuqi.gcreport.financialcheckapi.dataentry.vo.GcRelatedItemVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.FinancialCheckQueryTypeEnum;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInputExportExecutor
extends AbstractExportExcelOneSheetExecutor {
    @Autowired
    GcFinancialCheckDataEntryService dataEntryService;
    @Autowired
    DimensionService dimensionService;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    protected ExportExcelSheet exportExcelSheet(ExportContext context, Workbook workbook) {
        DataInputConditionVO condition = (DataInputConditionVO)JsonUtils.readValue((String)context.getParam(), DataInputConditionVO.class);
        condition.setAllVchrPageNum(Integer.valueOf(1));
        condition.setAllVchrPageSize(Integer.valueOf(Integer.MAX_VALUE));
        condition.setCheckedPageNum(Integer.valueOf(1));
        condition.setCheckedPageSize(Integer.valueOf(Integer.MAX_VALUE));
        condition.setUncheckedPageNum(Integer.valueOf(1));
        condition.setUncheckedPageSize(Integer.valueOf(Integer.MAX_VALUE));
        condition.setOppUncheckedPageNum(Integer.valueOf(1));
        condition.setOppUncheckedPageSize(Integer.valueOf(Integer.MAX_VALUE));
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        boolean isTemplateExportFlag = context.isTemplateExportFlag();
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u51ed\u8bc1");
        ArrayList<Object[]> rowDataS = new ArrayList<Object[]>();
        this.setDataInputExportHead(this.getVoucherHeads(), rowDataS, isTemplateExportFlag, cellStyleMap, exportExcelSheet, condition);
        exportExcelSheet.getRowDatas().addAll(rowDataS);
        return exportExcelSheet;
    }

    public String getName() {
        return "DataInputExportExecutor";
    }

    private List<TableColumnVO> getVoucherHeads() {
        ArrayList<TableColumnVO> colums = new ArrayList<TableColumnVO>();
        colums.add(new TableColumnVO("subjectCode", "\u79d1\u76ee\u7f16\u7801*", "\u5fc5\u586b\uff0c\u4f1a\u8ba1\u79d1\u76ee\u7f16\u7801"));
        colums.add(new TableColumnVO("subjectName", "\u79d1\u76ee", "\u79d1\u76ee\u540d\u79f0\uff0c\u53ef\u4e0d\u586b\u5199"));
        colums.add(new TableColumnVO("unitId", "\u672c\u65b9\u5355\u4f4d\u7f16\u7801*", "\u5fc5\u586b\uff0c\u672c\u65b9\u5355\u4f4d\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801"));
        colums.add(new TableColumnVO("unitName", "\u672c\u65b9\u5355\u4f4d", "\u672c\u65b9\u5355\u4f4d\u540d\u79f0\uff0c\u53ef\u4e0d\u586b\u5199"));
        colums.add(new TableColumnVO("oppUnitId", "\u5bf9\u65b9\u5355\u4f4d\u7f16\u7801*", "\u5fc5\u586b\uff0c\u5bf9\u65b9\u5355\u4f4d\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801"));
        colums.add(new TableColumnVO("oppUnitName", "\u5bf9\u65b9\u5355\u4f4d", "\u5bf9\u65b9\u5355\u4f4d\u540d\u79f0\uff0c\u53ef\u4e0d\u586b\u5199"));
        colums.add(new TableColumnVO("acctYear", "\u5e74\u5ea6*", "\u5fc5\u586b"));
        colums.add(new TableColumnVO("acctPeriod", "\u671f\u95f4*", "\u5fc5\u586b\uff0c\u586b\u51990-12"));
        colums.add(new TableColumnVO("originalCurr", "\u539f\u5e01\u5e01\u79cd\u7f16\u7801*", "\u5fc5\u586b"));
        colums.add(new TableColumnVO("originalCurrName", "\u539f\u5e01\u5e01\u79cd", "\u5e01\u79cd\u540d\u79f0\uff0c\u53ef\u4e0d\u586b\u5199"));
        colums.add(new TableColumnVO("debitOrig", "\u501f\u65b9\u91d1\u989d(\u539f\u5e01)*", "\u5fc5\u586b\uff0c\u6709\u501f\u65b9\u91d1\u989d\u5219\u586b\u91d1\u989d\uff0c\u65e0\u501f\u65b9\u91d1\u989d\u5219\u586b0"));
        colums.add(new TableColumnVO("creditOrig", "\u8d37\u65b9\u91d1\u989d(\u539f\u5e01)*", "\u5fc5\u586b\uff0c\u6709\u8d37\u65b9\u91d1\u989d\u5219\u586b\u91d1\u989d\uff0c\u65e0\u8d37\u65b9\u91d1\u989d\u5219\u586b0"));
        colums.add(new TableColumnVO("currency", "\u672c\u4f4d\u5e01\u79cd\u7f16\u7801*", "\u5fc5\u586b"));
        colums.add(new TableColumnVO("currencyName", "\u672c\u4f4d\u5e01\u79cd", "\u5e01\u79cd\u540d\u79f0\uff0c\u53ef\u4e0d\u586b\u5199"));
        colums.add(new TableColumnVO("debit", "\u501f\u65b9\u91d1\u989d(\u672c\u4f4d\u5e01)*", "\u5fc5\u586b\uff0c\u6709\u501f\u65b9\u91d1\u989d\u5219\u586b\u91d1\u989d\uff0c\u65e0\u501f\u65b9\u91d1\u989d\u5219\u586b0"));
        colums.add(new TableColumnVO("credit", "\u8d37\u65b9\u91d1\u989d(\u672c\u4f4d\u5e01)*", "\u5fc5\u586b\uff0c\u6709\u8d37\u65b9\u91d1\u989d\u5219\u586b\u91d1\u989d\uff0c\u65e0\u8d37\u65b9\u91d1\u989d\u5219\u586b0"));
        colums.add(new TableColumnVO("gcNumber", "\u534f\u540c\u7801", "\u975e\u5fc5\u586b"));
        colums.add(new TableColumnVO("createDateStr", "\u51ed\u8bc1\u65e5\u671f", "\u975e\u5fc5\u586b\uff0c\u586b\u5199\u683c\u5f0f2023-01-01"));
        colums.add(new TableColumnVO("memo", "\u5206\u5f55\u5907\u6ce8", "\u975e\u5fc5\u586b"));
        colums.add(new TableColumnVO("itemOrder", "\u5206\u5f55\u5e8f\u53f7*", "\u5fc5\u586b"));
        colums.add(new TableColumnVO("vchrType", "\u51ed\u8bc1\u5b57*", "\u5fc5\u586b\uff0c\u82e5\u4e3a\u7a7a\uff0c\u53ef\u586b#"));
        colums.add(new TableColumnVO("vchrNum", "\u51ed\u8bc1\u53f7*", "\u5fc5\u586b\uff0c\u82e5\u4e3a\u7a7a\uff0c\u53ef\u586b#"));
        colums.add(new TableColumnVO("digest", "\u51ed\u8bc1\u6458\u8981", "\u975e\u5fc5\u586b"));
        colums.add(new TableColumnVO("vchrSourceType", "\u51ed\u8bc1\u7c7b\u578b", "\u975e\u5fc5\u586b"));
        colums.add(new TableColumnVO("vchrSourceTypeCode", "\u51ed\u8bc1\u7c7b\u578b\u7f16\u7801", "\u975e\u5fc5\u586b"));
        colums.add(new TableColumnVO("billCode", "\u5355\u636e\u7f16\u53f7", "\u975e\u5fc5\u586b"));
        List dimFields = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        dimFields.forEach(dim -> colums.add(new TableColumnVO(dim.getCode(), dim.getTitle(), "")));
        return colums;
    }

    private List<GcRelatedItemVO> getVoucherTemplateData() {
        ArrayList<GcRelatedItemVO> datas = new ArrayList<GcRelatedItemVO>();
        GcRelatedItemVO voucherItem = new GcRelatedItemVO();
        voucherItem.setSubjectCode("11220201");
        voucherItem.setUnitId("10001");
        voucherItem.setOppUnitId("123");
        voucherItem.setAcctYear(Integer.valueOf(2023));
        voucherItem.setAcctPeriod(Integer.valueOf(1));
        voucherItem.setOriginalCurr("CNY");
        voucherItem.setDebit(Double.valueOf(100.0));
        voucherItem.setCredit(Double.valueOf(0.0));
        voucherItem.setDebitOrig(Double.valueOf(100.0));
        voucherItem.setCreditOrig(Double.valueOf(0.0));
        voucherItem.setCreateDateStr("2023-01-01");
        voucherItem.setItemOrder("0");
        voucherItem.setVchrNum("#");
        voucherItem.setVchrType("#");
        datas.add(voucherItem);
        return datas;
    }

    private void setDataInputExportHead(List<TableColumnVO> colums, List<Object[]> rowDatas, boolean isTemplateExportFlag, Map<String, CellStyle> cellStyleMap, ExportExcelSheet exportExcelSheet, DataInputConditionVO condition) {
        int i;
        Object[] firstHead = new Object[colums.size()];
        Object[] SecondHead = new Object[colums.size() + 1];
        String[] firstHeadKeys = new String[colums.size()];
        CellStyle[] headStyles = new CellStyle[colums.size()];
        CellStyle[] contentStyles = new CellStyle[colums.size()];
        CellType[] cellTypes = new CellType[colums.size()];
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle headAmt = cellStyleMap.get("headAmt");
        CellStyle contentString = cellStyleMap.get("contentString");
        CellStyle contentAmt = cellStyleMap.get("contentAmt");
        for (i = 0; i < colums.size(); ++i) {
            firstHead[i] = colums.get(i).getLabel();
            if (isTemplateExportFlag) {
                SecondHead[i] = colums.get(i).getAlign();
            }
            firstHeadKeys[i] = colums.get(i).getKey();
            if (colums.get(i).getKey().startsWith("debit") || colums.get(i).getKey().startsWith("credit")) {
                headStyles[i] = headAmt;
                contentStyles[i] = contentAmt;
                cellTypes[i] = CellType.NUMERIC;
                continue;
            }
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
        if (isTemplateExportFlag) {
            exportExcelSheet.getHeadCellStyleCache().put(colums.size(), headString);
            exportExcelSheet.getContentCellStyleCache().put(colums.size(), contentString);
            exportExcelSheet.getContentCellTypeCache().put(colums.size(), CellType.STRING);
            SecondHead[colums.size()] = "\u5bfc\u5165\u65f6\u5220\u9664\u672c\u884c";
            rowDatas.add(SecondHead);
        }
        this.getDetailedItemExportData(firstHeadKeys, isTemplateExportFlag, rowDatas, condition);
    }

    private void getDetailedItemExportData(String[] firstHeadKeys, boolean isTemplateExportFlag, List<Object[]> rowDataS, DataInputConditionVO condition) {
        List<Object> datas = Collections.emptyList();
        if (!isTemplateExportFlag) {
            DataInputVO data = this.dataEntryService.query(condition);
            FinancialCheckQueryTypeEnum queryType = FinancialCheckQueryTypeEnum.fromName((String)condition.getQueryType());
            switch (queryType) {
                case DATAINPUT_ALL: {
                    datas = data.getAllVchrData();
                    break;
                }
                case DATAINPUT_CHECKED: {
                    datas = data.getCheckedVchrData();
                    break;
                }
                case DATAINPUT_UNCHECK: {
                    datas = data.getUncheckVchrData();
                    break;
                }
                case DATAINPUT_DFDATA: {
                    datas = data.getOppUncheckedData();
                    break;
                }
                default: {
                    throw new BusinessRuntimeException("\u6682\u4e0d\u652f\u6301\u6b64\u67e5\u8be2\u7c7b\u578b");
                }
            }
        } else {
            datas = this.getVoucherTemplateData();
        }
        for (int i = 0; i < datas.size(); ++i) {
            Object[] rowData = isTemplateExportFlag ? new Object[firstHeadKeys.length + 1] : new Object[firstHeadKeys.length];
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            this.bilateralDataMap(dataMap, (GcRelatedItemVO)datas.get(i));
            for (int j = 0; j < firstHeadKeys.length; ++j) {
                rowData[j] = dataMap.get(firstHeadKeys[j]) instanceof Double ? Double.valueOf(NumberUtils.round((double)((Double)dataMap.get(firstHeadKeys[j])), (int)2)) : dataMap.get(firstHeadKeys[j]);
            }
            if (isTemplateExportFlag) {
                rowData[firstHeadKeys.length] = "\u5bfc\u5165\u65f6\u5220\u9664\u672c\u884c";
            }
            rowDataS.add(rowData);
        }
    }

    private void bilateralDataMap(Map<String, Object> value, GcRelatedItemVO voucherItem) {
        Class<?> itemClass = voucherItem.getClass();
        for (Field declaredField : itemClass.getDeclaredFields()) {
            try {
                declaredField.setAccessible(true);
                value.put(declaredField.getName(), declaredField.get(voucherItem));
            }
            catch (IllegalAccessException e) {
                throw new BusinessRuntimeException("\u5904\u7406\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38");
            }
        }
        Map mapping = voucherItem.getDimensionCode();
        if (mapping != null && !mapping.isEmpty()) {
            value.putAll(mapping);
        }
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
            cellStyleMap.put("headAmt", headAmtStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentString", contentStringStyle);
            CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
            contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("contentAmt", contentAmtStyle);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        boolean isTemplateExportFlag = context.isTemplateExportFlag();
        if (row.getRowNum() == 0 || row.getRowNum() == 1 && isTemplateExportFlag && !"\u5bfc\u5165\u65f6\u5220\u9664\u672c\u884c".equals(cellValue)) {
            CellStyle titleHeadStringStyle = this.buildDefaultHeadCellStyle(workbook);
            titleHeadStringStyle.setAlignment(HorizontalAlignment.CENTER);
            titleHeadStringStyle.setWrapText(true);
            cell.setCellStyle(titleHeadStringStyle);
        }
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        for (int i = 0; i < this.getVoucherHeads().size(); ++i) {
            sheet.setColumnWidth(i, 10240);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor
 *  com.jiuqi.gcreport.common.elementtable.impl.ElementTableDataVO
 *  com.jiuqi.gcreport.common.elementtable.impl.ElementTableTitleVO
 *  com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitCondition
 *  com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitDataVO
 */
package com.jiuqi.gcreport.inputdata.gcoppunit.executor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor;
import com.jiuqi.gcreport.common.elementtable.impl.ElementTableDataVO;
import com.jiuqi.gcreport.common.elementtable.impl.ElementTableTitleVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitCondition;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitDataVO;
import com.jiuqi.gcreport.inputdata.gcoppunit.service.GcOppUnitQueryService;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
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
import org.springframework.util.CollectionUtils;

@Component
public class OppUnitDataExportExecutor
extends AbstractExportExcelOneSheetExecutor {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();
    @Autowired
    GcOppUnitQueryService gcOppUnitQueryService;

    protected ExportExcelSheet exportExcelSheet(ExportContext context, Workbook workbook) {
        int i;
        GcOppUnitCondition condition = (GcOppUnitCondition)JsonUtils.readValue((String)context.getParam(), GcOppUnitCondition.class);
        List columns = condition.getColumns();
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u5bf9\u65b9\u5355\u4f4d");
        Object[] firstHead = new Object[columns.size()];
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        CellStyle[] headStyles = new CellStyle[columns.size()];
        CellStyle[] contentStyles = new CellStyle[columns.size()];
        CellType[] cellTypes = new CellType[columns.size()];
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle headAmt = cellStyleMap.get("headAmt");
        CellStyle contentString = cellStyleMap.get("contentString");
        CellStyle contentAmt = cellStyleMap.get("contentAmt");
        for (i = 0; i < columns.size(); ++i) {
            firstHead[i] = ((ElementTableTitleVO)columns.get(i)).getTitle();
            if ("n".equals(((ElementTableTitleVO)columns.get(i)).getExportType())) {
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
        ArrayList<Object[]> rowDataS = new ArrayList<Object[]>();
        rowDataS.add(firstHead);
        condition.setPageNum(Integer.valueOf(1));
        condition.setPageSize(Integer.valueOf(Integer.MAX_VALUE));
        Object result = this.gcOppUnitQueryService.excute(condition);
        Map data = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)result), Map.class);
        if (data == null) {
            return exportExcelSheet;
        }
        List allData = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(data.get("datas")), (TypeReference)new TypeReference<List<GcOppUnitDataVO>>(){});
        if (!CollectionUtils.isEmpty(allData)) {
            if (!CollectionUtils.isEmpty(condition.getBatchSelectData())) {
                Set selectIds = condition.getBatchSelectData().stream().map(ElementTableDataVO::getId).collect(Collectors.toSet());
                allData = allData.stream().filter(unit -> selectIds.contains(unit.getId())).collect(Collectors.toList());
            }
            Class<GcOppUnitDataVO> clazz = GcOppUnitDataVO.class;
            for (int j = 0; j < allData.size(); ++j) {
                GcOppUnitDataVO unit2 = (GcOppUnitDataVO)allData.get(j);
                Object[] rowData = new Object[columns.size()];
                for (int i2 = 0; i2 < columns.size(); ++i2) {
                    Object o;
                    if (i2 == 0) {
                        rowData[0] = j + 1;
                        continue;
                    }
                    Map otherDataMap = unit2.getOtherDataMap();
                    if (otherDataMap != null && (o = otherDataMap.get(((ElementTableTitleVO)columns.get(i2)).getKey())) != null) {
                        rowData[i2] = o.toString();
                        continue;
                    }
                    String methodName = "get" + ((ElementTableTitleVO)columns.get(i2)).getKey().substring(0, 1).toUpperCase() + ((ElementTableTitleVO)columns.get(i2)).getKey().substring(1);
                    Object value = null;
                    try {
                        Method method = clazz.getMethod(methodName, new Class[0]);
                        value = method.invoke(unit2, new Object[0]);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                    }
                    catch (IllegalAccessException illegalAccessException) {
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        // empty catch block
                    }
                    rowData[i2] = Objects.isNull(value) ? "" : value.toString();
                }
                rowDataS.add(rowData);
            }
        }
        exportExcelSheet.getRowDatas().addAll(rowDataS);
        return exportExcelSheet;
    }

    private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headStringStyle = this.buildDefaultHeadCellStyle(workbook);
            headStringStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyleMap.put("headString", headStringStyle);
            CellStyle headAmtStyle = this.buildDefaultHeadCellStyle(workbook);
            headAmtStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyleMap.put("headAmt", headAmtStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentString", contentStringStyle);
            CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
            contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("contentAmt", contentAmtStyle);
            CellStyle contentNumberStyle = this.buildDefaultContentCellStyle(workbook);
            contentNumberStyle.setAlignment(HorizontalAlignment.RIGHT);
            cellStyleMap.put("contentNumber", contentNumberStyle);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    public String getName() {
        return "OppUnitDataExportExecutor";
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    protected void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        if (row.getRowNum() == 0) {
            CellStyle titleHeadStringStyle = this.buildDefaultHeadCellStyle(workbook);
            titleHeadStringStyle.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(titleHeadStringStyle);
        }
    }

    protected void callBackSheet(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet) {
        GcOppUnitCondition condition = (GcOppUnitCondition)JsonUtils.readValue((String)context.getParam(), GcOppUnitCondition.class);
        List columns = condition.getColumns();
        for (int i = 0; i < columns.size(); ++i) {
            ElementTableTitleVO column = (ElementTableTitleVO)columns.get(i);
            sheet.setColumnWidth(i, column.getWidth() * 25);
        }
    }
}


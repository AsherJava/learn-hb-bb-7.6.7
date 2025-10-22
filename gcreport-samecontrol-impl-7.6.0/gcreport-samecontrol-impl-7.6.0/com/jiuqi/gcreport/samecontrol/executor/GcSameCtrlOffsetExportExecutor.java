/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.unionrule.util.ExcelUtils$ExportColumnTypeEnum
 */
package com.jiuqi.gcreport.samecontrol.executor;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.samecontrol.service.GcSameCtrlOffsetExportService;
import com.jiuqi.gcreport.unionrule.util.ExcelUtils;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcSameCtrlOffsetExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();
    @Autowired
    private GcSameCtrlOffsetExportService gcSameCtrlOffsetExportService;

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        List<ExportExcelSheet> exportExcelSheetList = this.gcSameCtrlOffsetExportService.exportExcelSheets(context, workbook);
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle headAmt = cellStyleMap.get("headAmt");
        CellStyle contentString = cellStyleMap.get("contentString");
        CellStyle contentAmt = cellStyleMap.get("contentAmt");
        CellStyle[] headStyles = new CellStyle[]{headString, headString, headString, headString, headString, headString, headString, headAmt, headAmt, headAmt, headString};
        CellStyle[] contentStyles = new CellStyle[]{contentString, contentString, contentString, contentString, contentString, contentString, contentString, contentAmt, contentAmt, contentAmt, contentString};
        exportExcelSheetList.forEach(sheet -> {
            for (int i = 0; i < headStyles.length; ++i) {
                sheet.getHeadCellStyleCache().put(i, headStyles[i]);
                sheet.getContentCellStyleCache().put(i, contentStyles[i]);
            }
            sheet.getContentCellTypeCache().put(7, ExcelUtils.ExportColumnTypeEnum.NUMERIC.getCode());
            sheet.getContentCellTypeCache().put(8, ExcelUtils.ExportColumnTypeEnum.NUMERIC.getCode());
            sheet.getContentCellTypeCache().put(9, ExcelUtils.ExportColumnTypeEnum.NUMERIC.getCode());
        });
        return exportExcelSheetList;
    }

    public String getName() {
        return "SameCtrlOffsetExportExecutor";
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
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
            intervalColorString.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleMap.put("intervalColorString", intervalColorString);
            XSSFCellStyle intervalColorAmt = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorAmt.setAlignment(HorizontalAlignment.RIGHT);
            intervalColorAmt.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorAmt.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            intervalColorAmt.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("intervalColorAmt", intervalColorAmt);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }
}


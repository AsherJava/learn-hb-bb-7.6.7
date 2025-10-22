/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.executor.common.IntervalColorExportExcelSheet
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 */
package com.jiuqi.gcreport.invest.offsetitem.task.internal;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.invest.offsetitem.task.internal.MultiTableExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.executor.common.IntervalColorExportExcelSheet;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;

public abstract class AbstractLossGainExportTask {
    protected IntervalColorExportExcelSheet getExportExcelSheet(ExportContext context, Integer sheetNo, String sheetName) {
        IntervalColorExportExcelSheet exportExcelSheet = new IntervalColorExportExcelSheet(sheetNo, sheetName);
        this.init(context, (ExportExcelSheet)exportExcelSheet);
        return exportExcelSheet;
    }

    protected MultiTableExportExcelSheet getMultiTableExportExcelSheet(ExportContext context, Integer sheetNo, String sheetName) {
        MultiTableExportExcelSheet exportExcelSheet = new MultiTableExportExcelSheet(sheetNo, sheetName);
        this.init(context, exportExcelSheet);
        return exportExcelSheet;
    }

    protected void createHeader(List<Object[]> rowDatas, List<DesignFieldDefineVO> otherShowColumns) {
        ArrayList<String> header = new ArrayList<String>();
        header.add(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.debitUnit"));
        header.add(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.creditUnit"));
        header.add(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.creditSubject"));
        header.add(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.debitAmt"));
        header.add(GcI18nUtil.getMessage((String)"gc.calculate.lossGain.creditAmt"));
        for (DesignFieldDefineVO otherShowColumn : otherShowColumns) {
            header.add(otherShowColumn.getLabel());
        }
        Collections.addAll(rowDatas, new Object[][]{header.toArray(new Object[0])});
    }

    protected Object[] excelOneRow(Map<String, Object> record, List<DesignFieldDefineVO> otherShowColumns) {
        ArrayList<Object> oneRowData = new ArrayList<Object>();
        oneRowData.add(record.get("UNITTITLE"));
        oneRowData.add(record.get("OPPUNITTITLE"));
        oneRowData.add(record.get("SUBJECTTITLE"));
        oneRowData.add(record.get("OFFSETDEBIT"));
        oneRowData.add(record.get("OFFSETCREDIT"));
        for (DesignFieldDefineVO otherShowColumn : otherShowColumns) {
            oneRowData.add(record.get(otherShowColumn.getKey()));
        }
        return oneRowData.toArray(new Object[0]);
    }

    private ExportExcelSheet init(ExportContext context, ExportExcelSheet exportExcelSheet) {
        this.setColumnWidth(exportExcelSheet);
        this.setCellStyle(context, exportExcelSheet);
        return exportExcelSheet;
    }

    private void setCellStyle(ExportContext context, ExportExcelSheet exportExcelSheet) {
        CellStyle contentAmt = (CellStyle)context.getVarMap().get("contentAmt");
        CellStyle headAmt = (CellStyle)context.getVarMap().get("headAmt");
        exportExcelSheet.getContentCellStyleCache().put(4, contentAmt);
        exportExcelSheet.getHeadCellStyleCache().put(4, headAmt);
        exportExcelSheet.getContentCellTypeCache().put(4, CellType.NUMERIC);
        exportExcelSheet.getContentCellStyleCache().put(5, contentAmt);
        exportExcelSheet.getHeadCellStyleCache().put(5, headAmt);
        exportExcelSheet.getContentCellTypeCache().put(5, CellType.NUMERIC);
    }

    private void setColumnWidth(ExportExcelSheet exportExcelSheet) {
        Map columnWidthCache = exportExcelSheet.getColumnWidthCache();
        columnWidthCache.put(0, 7680);
        columnWidthCache.put(1, 7680);
        columnWidthCache.put(2, 7680);
        columnWidthCache.put(3, 7680);
        columnWidthCache.put(4, 5888);
        columnWidthCache.put(5, 5888);
    }
}


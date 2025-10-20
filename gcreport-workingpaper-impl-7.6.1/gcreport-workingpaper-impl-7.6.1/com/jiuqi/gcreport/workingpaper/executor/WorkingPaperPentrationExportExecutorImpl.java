/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.offsetitem.executor.tab.task.OffsetTabExportTask
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.DefaultIndexedColorMap
 *  org.apache.poi.xssf.usermodel.IndexedColorMap
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 */
package com.jiuqi.gcreport.workingpaper.executor;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.offsetitem.executor.tab.task.OffsetTabExportTask;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.workingpaper.querytask.utils.WorkingPaperQueryUtils;
import com.jiuqi.gcreport.workingpaper.service.WorkingPaperService;
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

@Component
public class WorkingPaperPentrationExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private OffsetTabExportTask offsetTabExportTask;
    @Autowired
    private WorkingPaperService workingPaperService;
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    public String getName() {
        return "WorkingPaperPentrationExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        WorkingPaperPentrationQueryCondtion pentrationQueryCondtion = (WorkingPaperPentrationQueryCondtion)JsonUtils.readValue((String)context.getParam(), WorkingPaperPentrationQueryCondtion.class);
        Pagination workPaperPentrationDatas = (Pagination)this.workingPaperService.getWorkPaperPentrationDatas(null, null, pentrationQueryCondtion);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        QueryParamsVO queryParamsVO = WorkingPaperQueryUtils.convertPenInfoToOffsetParams(pentrationQueryCondtion, false);
        ExportExcelSheet offsetSheet = this.offsetTabExportTask.createSheet(workbook, queryParamsVO, Boolean.valueOf(context.isTemplateExportFlag()), 0, workPaperPentrationDatas);
        exportExcelSheets.add(offsetSheet);
        context.getVarMap().put("offsetIntervalRows", this.getOffSetIntervalRowSet((Pagination<Map<String, Object>>)workPaperPentrationDatas));
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
        Object intervalColorRows = context.getVarMap().get("offsetIntervalRows");
        if (intervalColorRows != null && (rowSet = (Set)intervalColorRows).contains(rowNum)) {
            this.setIntervalColor(workbook, cell);
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
            headAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("headAmt", headAmtStyle);
            CellStyle contentStringStyle = this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentString", contentStringStyle);
            CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
            contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("contentAmt", contentAmtStyle);
            XSSFCellStyle intervalColorString = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorString.setAlignment(HorizontalAlignment.LEFT);
            intervalColorString.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleMap.put("intervalColorString", (CellStyle)intervalColorString);
            XSSFCellStyle intervalColorAmt = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorAmt.setAlignment(HorizontalAlignment.RIGHT);
            intervalColorAmt.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorAmt.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            intervalColorAmt.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("intervalColorAmt", (CellStyle)intervalColorAmt);
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


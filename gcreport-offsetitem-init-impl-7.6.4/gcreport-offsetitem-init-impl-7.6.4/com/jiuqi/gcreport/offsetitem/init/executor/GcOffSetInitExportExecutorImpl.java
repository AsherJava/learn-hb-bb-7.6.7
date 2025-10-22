/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine
 *  com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine
 *  com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.offsetitem.init.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine;
import com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcOffSetInitExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;
    @Autowired
    private GcOffSetInitService offSetInitService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    public String getName() {
        return "GcOffSetItemExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO = (OffsetItemInitQueryParamsVO)JsonUtils.readValue((String)context.getParam(), OffsetItemInitQueryParamsVO.class);
        offsetItemInitQueryParamsVO.setPageNum(-1);
        offsetItemInitQueryParamsVO.setPageSize(-1);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(offsetItemInitQueryParamsVO.getTaskId());
        LogHelper.info((String)"\u5408\u5e76-\u5206\u5f55\u521d\u59cb\u5316", (String)("\u6279\u91cf\u5bfc\u51fa-\u65f6\u671f" + offsetItemInitQueryParamsVO.getPeriodStr() + "-\u4efb\u52a1" + taskDefine.getTitle()), (String)"");
        List dimensionVOS = this.consolidatedOptionService.getDimensionsByTableName("GC_OFFSETVCHRITEM_INIT", offsetItemInitQueryParamsVO.getSystemId());
        List otherShowColumns = offsetItemInitQueryParamsVO.getOtherShowColumns();
        IEntTableDefineProvider entTableDefineProvider = (IEntTableDefineProvider)SpringContextUtils.getBean(IEntTableDefineProvider.class);
        EntTableDefine tableDefine = entTableDefineProvider.getTableDefine("GC_OFFSETVCHRITEM_INIT");
        Map<String, String> fieldCode2Title = tableDefine.getAllFields().stream().collect(Collectors.toMap(EntFieldDefine::getCode, EntFieldDefine::getTitle));
        Map<String, String> dimensionCode2Title = dimensionVOS.stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getTitle));
        otherShowColumns.forEach(code -> {
            if (dimensionCode2Title.containsKey(code)) {
                offsetItemInitQueryParamsVO.getOtherShowColumnTitles().add(dimensionCode2Title.get(code));
            }
            if (fieldCode2Title.containsKey(code) && !"EFFECTTYPE".equals(code) && !"OFFSETSRCTYPE".equals(code) && !offsetItemInitQueryParamsVO.getOtherShowColumnTitles().contains(fieldCode2Title.get(code))) {
                offsetItemInitQueryParamsVO.getOtherShowColumnTitles().add(fieldCode2Title.get(code));
            }
        });
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        List<GcBusinessTypeCountVO> businessTypeCountVOs = this.offSetInitService.rootBusinessTypes(offsetItemInitQueryParamsVO);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        for (int i = 0; i < businessTypeCountVOs.size(); ++i) {
            GcBusinessTypeCountVO gcBusinessTypeCountVO = businessTypeCountVOs.get(i);
            exportExcelSheets.add(this.offSetInitService.exportSheet(i, offsetItemInitQueryParamsVO, gcBusinessTypeCountVO, cellStyleMap, context));
            if (context.isTemplateExportFlag()) {
                return exportExcelSheets;
            }
            if (i == 0) {
                context.getProgressData().setProgressValueAndRefresh(0.4);
                continue;
            }
            context.getProgressData().setProgressValueAndRefresh(0.4 + (double)(i / businessTypeCountVOs.size()) * 0.5);
        }
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
        Object intervalColorRows = context.getVarMap().get(excelSheet.getSheetNo() + "");
        if (intervalColorRows != null && (rowSet = (Set)intervalColorRows).contains(rowNum)) {
            this.setIntervalColor(workbook, cell);
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
            headAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
            cellStyleMap.put("headAmt", headAmtStyle);
            CellStyle headDescribeStyle = this.buildHeadDescribeCellStyle(workbook);
            headDescribeStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("headDescribe", headDescribeStyle);
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

    private CellStyle buildHeadDescribeCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("\u5b8b\u4f53");
        font.setFontHeightInPoints((short)10);
        font.setItalic(true);
        font.setStrikeout(false);
        font.setColor(IndexedColors.RED.getIndex());
        font.setTypeOffset((short)0);
        font.setUnderline((byte)0);
        font.setCharSet(0);
        font.setBold(false);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private void setIntervalColor(Workbook workbook, Cell cell) {
        if (cell.getCellStyle() != null && workbook.getFontAt(cell.getCellStyle().getFontIndex()).getColor() == IndexedColors.RED.getIndex()) {
            cell.setCellStyle(this.getCellStyleMap(workbook).get("headDescribe"));
            return;
        }
        if (cell.getCellStyle() != null && cell.getCellStyle().getAlignment() == HorizontalAlignment.RIGHT) {
            cell.setCellStyle(this.getCellStyleMap(workbook).get("intervalColorAmt"));
            return;
        }
        cell.setCellStyle(this.getCellStyleMap(workbook).get("intervalColorString"));
    }
}


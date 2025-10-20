/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.xssf.usermodel.DefaultIndexedColorMap
 *  org.apache.poi.xssf.usermodel.IndexedColorMap
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFFont
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InputAdjustExportExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private GcI18nHelper gcI18nHelper;
    private final ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        Map<String, CellStyle> cellStyleMap = this.getCellStyleMap(workbook);
        ExportExcelSheet sheet = this.exportSheet(context, cellStyleMap);
        exportExcelSheets.add(sheet);
        return exportExcelSheets;
    }

    private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            XSSFFont fontStyle = (XSSFFont)workbook.createFont();
            fontStyle.setFontHeight(13.0);
            fontStyle.setColor((short)10);
            CellStyle emptyStyle = this.buildDefaultContentCellStyle(workbook);
            emptyStyle.setAlignment(HorizontalAlignment.LEFT);
            emptyStyle.setFont((Font)fontStyle);
            cellStyleMap.put("emptyStringStyle", emptyStyle);
            XSSFCellStyle headStringStyle = (XSSFCellStyle)this.buildDefaultHeadCellStyle(workbook);
            headStringStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("headStringStyle", (CellStyle)headStringStyle);
            XSSFCellStyle headAmtStyle = (XSSFCellStyle)this.buildDefaultHeadCellStyle(workbook);
            headAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            headAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("headAmtStyle", (CellStyle)headAmtStyle);
            XSSFCellStyle contentStringStyle = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
            contentStringStyle.setFont((Font)fontStyle);
            contentStringStyle.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            cellStyleMap.put("contentStringStyle", (CellStyle)contentStringStyle);
            XSSFCellStyle contentAmtStyle = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
            contentAmtStyle.setFont((Font)fontStyle);
            contentAmtStyle.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
            contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("contentAmtStyle", (CellStyle)contentAmtStyle);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    public String getName() {
        return "InputsAdjustExportExecutor";
    }

    protected void callBackWorkbook(ExportContext context, Workbook workbook) {
        this.threadLocal.remove();
    }

    public ExportExcelSheet exportSheet(ExportContext context, Map<String, CellStyle> cellStyleMap) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        String schemeId = (String)params.get("schemeId");
        String periodStr = (String)params.get("periodStr");
        Assert.isNotEmpty((String)schemeId, (String)"\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)periodStr, (String)"\u6570\u636e\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        List dimensionsByTableName = this.consolidatedOptionService.getDimensionsByTableName("GC_OFFSETVCHRITEM", schemeId, periodStr);
        TableModelDefine tableDefine = this.dataModelService.getTableModelDefineByCode("GC_OFFSETVCHRITEM");
        List defines = this.dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        for (DimensionVO dimensionVO : dimensionsByTableName) {
            for (ColumnModelDefine columnModelDefine : defines) {
                String title;
                String code = columnModelDefine.getCode().toUpperCase();
                if (!dimensionVO.getCode().equals(code)) continue;
                String localTitle = this.gcI18nHelper.getMessage(columnModelDefine.getID());
                String string = title = StringUtils.isEmpty(localTitle) ? columnModelDefine.getTitle() : localTitle;
                if (StringUtils.isEmpty(title)) {
                    title = code;
                }
                dimensionVO.setTitle(title);
            }
        }
        List<String> otherTitles = dimensionsByTableName.stream().map(DimensionVO::getTitle).collect(Collectors.toList());
        return this.exportTemplate(cellStyleMap, otherTitles);
    }

    private ExportExcelSheet exportTemplate(Map<String, CellStyle> cellStyleMap, List<String> otherTitles) {
        Object[] row2Data;
        Object[] row1Data;
        String[] titles;
        ArrayList<CellStyle> contentStyleList = new ArrayList<CellStyle>();
        ArrayList<CellStyle> headStyleList = new ArrayList<CellStyle>();
        ArrayList<String> rows1List = new ArrayList<String>();
        ArrayList<String> rows2List = new ArrayList<String>();
        CellStyle contentString = cellStyleMap.get("contentStringStyle");
        CellStyle contentAmt = cellStyleMap.get("contentAmtStyle");
        CellStyle headString = cellStyleMap.get("headStringStyle");
        CellStyle headAmt = cellStyleMap.get("headAmtStyle");
        CellStyle emptyString = cellStyleMap.get("emptyStringStyle");
        CellStyle[] contentStyles = new CellStyle[]{contentString, contentString, contentString, contentString, contentString, contentString, contentString, contentAmt, contentAmt, contentString, contentString};
        ArrayList<String> titleList = new ArrayList<String>();
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), GcI18nUtil.getMessage((String)"gc.calculate.adjustingentry.inputAdjust.importTemplate"), Integer.valueOf(1));
        if (!"1".equals(GcSystermOptionTool.getOptionValue((String)"FINANCIAL_CUBES_ENABLE"))) {
            titles = this.msg(new String[]{"gc.calculate.adjustingentry.showoffset.sn", "gc.calculate.adjustingentry.showoffset.effectPeriod", "gc.calculate.adjustingentry.showoffset.gcBusinessType", "gc.calculate.adjustingentry.showoffset.ruleTitle", "gc.calculate.adjustingentry.showoffset.thisUnit", "gc.calculate.adjustingentry.showoffset.oppUnit", "gc.calculate.adjustingentry.showoffset.subjectTitle", "gc.calculate.adjustingentry.showoffset.debitAmt", "gc.calculate.adjustingentry.showoffset.creditAmt", "gc.calculate.adjustingentry.showoffset.memo"});
            row1Data = new Object[]{"1", "\u5f53\u671f", "\u5f80\u6765\u7c7b", "\u5185\u90e8\u5f80\u6765-\u8d34\u73b0\u62b5\u9500", "1066778|\u6ea7\u9633\u4e45\u5176\u6c34\u52a1\u6709\u9650\u516c\u53f8", "2003640|\u5317\u4eac\u4e45\u5176\u96c6\u56e2\u6709\u9650\u516c\u53f8\u515a\u6821", "1021|\u884d\u751f\u91d1\u878d\u8d44\u4ea7", "1.00", "", ""};
            row2Data = new Object[]{"1", "\u5f53\u671f", "\u5f80\u6765\u7c7b", "\u5185\u90e8\u5f80\u6765-\u8d34\u73b0\u62b5\u9500", "1066778|\u6ea7\u9633\u4e45\u5176\u6c34\u52a1\u6709\u9650\u516c\u53f8", "2003640|\u5317\u4eac\u4e45\u5176\u96c6\u56e2\u6709\u9650\u516c\u53f8\u515a\u6821", "3021|\u8d44\u672c\u516c\u79ef", "", "1.00", "6498"};
        } else {
            titles = this.msg(new String[]{"gc.calculate.adjustingentry.showoffset.sn", "gc.calculate.adjustingentry.showoffset.effectPeriod", "gc.calculate.adjustingentry.showoffset.gcBusinessType", "gc.calculate.adjustingentry.showoffset.ruleTitle", "gc.calculate.adjustingentry.showoffset.thisUnit", "gc.calculate.adjustingentry.showoffset.oppUnit", "gc.calculate.adjustingentry.showoffset.subjectTitle", "gc.calculate.adjustingentry.showoffset.debitAmt", "gc.calculate.adjustingentry.showoffset.creditAmt", "gc.calculate.adjustingentry.showoffset.memo", "1-6\u6708"});
            row1Data = new Object[]{"1", "\u5f53\u671f", "\u5f80\u6765\u7c7b", "\u5185\u90e8\u5f80\u6765-\u8d34\u73b0\u62b5\u9500", "1066778|\u6ea7\u9633\u4e45\u5176\u6c34\u52a1\u6709\u9650\u516c\u53f8", "2003640|\u5317\u4eac\u4e45\u5176\u96c6\u56e2\u6709\u9650\u516c\u53f8\u515a\u6821", "1021|\u884d\u751f\u91d1\u878d\u8d44\u4ea7", "1.00", "", "", "21"};
            row2Data = new Object[]{"1", "\u5f53\u671f", "\u5f80\u6765\u7c7b", "\u5185\u90e8\u5f80\u6765-\u8d34\u73b0\u62b5\u9500", "1066778|\u6ea7\u9633\u4e45\u5176\u6c34\u52a1\u6709\u9650\u516c\u53f8", "2003640|\u5317\u4eac\u4e45\u5176\u96c6\u56e2\u6709\u9650\u516c\u53f8\u515a\u6821", "3021|\u8d44\u672c\u516c\u79ef", "", "1.00", "6498", "21"};
        }
        CellStyle[] headStyles = new CellStyle[]{headString, headString, headString, headString, headString, headString, headString, headAmt, headAmt, headString, headString};
        Collections.addAll(titleList, titles);
        if (!CollectionUtils.isEmpty(otherTitles)) {
            titleList.addAll(otherTitles);
        }
        titleList.add("");
        titles = new String[titleList.size()];
        titles = titleList.toArray(titles);
        Collections.addAll(headStyleList, headStyles);
        Collections.addAll(contentStyleList, contentStyles);
        Collections.addAll(rows1List, row1Data);
        Collections.addAll(rows2List, row2Data);
        for (int i = 0; i < otherTitles.size(); ++i) {
            headStyleList.add(headString);
            contentStyleList.add(contentString);
            rows1List.add("");
            rows2List.add("");
        }
        headStyleList.add(emptyString);
        contentStyleList.add(emptyString);
        rows1List.add("\u793a\u4f8b\uff1a\u5bfc\u5165\u65f6\u5220\u9664");
        rows2List.add("\u793a\u4f8b\uff1a\u5bfc\u5165\u65f6\u5220\u9664");
        headStyles = headStyleList.toArray(headStyles);
        contentStyles = contentStyleList.toArray(contentStyles);
        row1Data = rows1List.toArray(row1Data);
        row2Data = rows2List.toArray(row2Data);
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        for (int i = 0; i < contentStyles.length; ++i) {
            exportExcelSheet.getHeadCellStyleCache().put(i, headStyles[i]);
            exportExcelSheet.getContentCellStyleCache().put(i, contentStyles[i]);
        }
        rowDatas.add(titles);
        rowDatas.add(row1Data);
        rowDatas.add(row2Data);
        this.addMergedRegion(exportExcelSheet, 1, 2, 0, 0);
        this.addMergedRegion(exportExcelSheet, 1, 2, rows1List.size() - 1, rows1List.size() - 1);
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

    private String[] msg(String[] keys) {
        String[] msgs = new String[keys.length];
        for (int i = 0; i < keys.length; ++i) {
            String title = GcI18nUtil.getMessage((String)keys[i]);
            msgs[i] = StringUtils.isEmpty(title) ? keys[i] : title;
        }
        return msgs;
    }
}


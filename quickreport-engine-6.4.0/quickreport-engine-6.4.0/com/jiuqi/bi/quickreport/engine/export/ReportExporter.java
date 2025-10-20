/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.excel.watermark.IWatermarkInjector
 *  com.jiuqi.bi.syntax.excel.ExcelException
 *  com.jiuqi.bi.syntax.excel.ISheetDescriptor
 */
package com.jiuqi.bi.quickreport.engine.export;

import com.jiuqi.bi.office.excel.watermark.IWatermarkInjector;
import com.jiuqi.bi.quickreport.engine.IReportExporter;
import com.jiuqi.bi.quickreport.engine.ReportEngine;
import com.jiuqi.bi.quickreport.engine.ReportEngineException;
import com.jiuqi.bi.quickreport.engine.export.ExcelExporter;
import com.jiuqi.bi.quickreport.engine.export.SheetIterator;
import com.jiuqi.bi.quickreport.engine.result.SheetData;
import com.jiuqi.bi.syntax.excel.ExcelException;
import com.jiuqi.bi.syntax.excel.ISheetDescriptor;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReportExporter
implements IReportExporter {
    private final ReportEngine engine;
    private String format;
    private boolean allSheets;
    private final Map<String, Integer> pageNums;
    private IWatermarkInjector injector;

    public ReportExporter(ReportEngine engine) {
        this.engine = engine;
        this.format = engine.getConfig().getOrDefault("excel.format", "xlsx");
        this.pageNums = new HashMap<String, Integer>();
    }

    @Override
    public String getFormat() {
        return this.format;
    }

    @Override
    public void setFormat(String format) {
        this.format = Objects.requireNonNull(format);
    }

    @Override
    public Map<String, Integer> getPageNums() {
        return this.pageNums;
    }

    @Override
    public boolean isAllSheets() {
        return this.allSheets;
    }

    @Override
    public void setAllSheets(boolean allSheet) {
        this.allSheets = allSheet;
    }

    @Override
    public void setWatermarkInjector(IWatermarkInjector injector) {
        this.injector = injector;
    }

    @Override
    public void export(OutputStream output) throws ReportEngineException {
        this.export(output, 0);
    }

    @Override
    public void export(OutputStream output, int options) throws ReportEngineException {
        if (this.engine.getScriptRunner().has("exp")) {
            this.engine.getScriptRunner().exp(this.engine.getParameterEnv(), output);
            return;
        }
        Iterator<ISheetDescriptor> sheetItr = this.createSheetIterator();
        ExcelExporter xlsExporter = new ExcelExporter(sheetItr, this.format);
        try {
            xlsExporter.setWatermarkInjector(this.injector);
            xlsExporter.export(output, options);
        }
        catch (ExcelException e) {
            throw new ReportEngineException(e);
        }
    }

    private Iterator<ISheetDescriptor> createSheetIterator() throws ReportEngineException {
        if (this.allSheets) {
            List<SheetData> sheets = this.engine.getPagedAllSheets(this.pageNums);
            return new SheetIterator(sheets, this.engine.getReport().getExcelInfo());
        }
        SheetData sheet = this.engine.getPagedPrimarySheet(this.pageNums);
        return new SheetIterator(sheet, this.engine.getReport().getExcelInfo());
    }
}


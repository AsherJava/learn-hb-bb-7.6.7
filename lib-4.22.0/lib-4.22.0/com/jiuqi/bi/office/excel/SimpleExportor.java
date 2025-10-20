/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.ExcelException;
import com.jiuqi.bi.office.excel.WorkbookContext;
import com.jiuqi.bi.office.excel.WorksheetWriterEx;
import com.jiuqi.bi.office.excel.print.PrintSetting;
import com.jiuqi.bi.office.excel.watermark.IWatermarkInjector;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimpleExportor {
    private boolean autoAdjust;
    private boolean addTitle;
    private String title;
    private GridData reportData;
    private PrintSetting printSetting;
    private String[] pages;
    private GridData[] reportDatas;
    private PrintSetting[] printSettings;
    private boolean isXlsx;
    private IWatermarkInjector injector;

    public SimpleExportor(GridData gridData) {
        this(gridData, false);
    }

    public SimpleExportor(GridData gridData, boolean isXlsx) {
        this(null, gridData, isXlsx);
    }

    public SimpleExportor(String title, GridData gridData) {
        this(title, gridData, false);
    }

    public SimpleExportor(String title, GridData gridData, boolean isXlsx) {
        this(title, gridData, null, isXlsx);
    }

    public SimpleExportor(String title, GridData gridData, PrintSetting printSetting, boolean isXlsx) {
        this.reportData = gridData;
        this.printSetting = printSetting;
        this.title = title;
        this.isXlsx = isXlsx;
        this.addTitle = title != null;
    }

    public SimpleExportor(GridData[] gridData) {
        this(null, gridData, false);
    }

    public SimpleExportor(GridData[] gridData, boolean isXlsx) {
        this(null, gridData, isXlsx);
    }

    public SimpleExportor(String[] pages, GridData[] gridDatas) {
        this(pages, gridDatas, false);
    }

    public SimpleExportor(String[] pages, GridData[] gridDatas, boolean isXlsx) {
        this(pages, gridDatas, null, isXlsx);
    }

    public SimpleExportor(String[] pages, GridData[] gridDatas, PrintSetting[] printSettings, boolean isXlsx) {
        this.reportDatas = gridDatas;
        this.printSettings = printSettings;
        this.pages = pages;
        this.isXlsx = isXlsx;
    }

    public void setWatermarkInjector(IWatermarkInjector injector) {
        this.injector = injector;
    }

    public void setAutoAdjust(boolean autoAdjust) {
        this.autoAdjust = autoAdjust;
    }

    public void setAddTitle(boolean addTitle) {
        this.addTitle = addTitle;
    }

    public boolean isAutoAdjust() {
        return this.autoAdjust;
    }

    public boolean isAddTitle() {
        return this.addTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isXlsx() {
        return this.isXlsx;
    }

    public void setXlsx(boolean isXlsx) {
        this.isXlsx = isXlsx;
    }

    public void export(String fileName) throws ExcelException {
        try (BufferedOutputStream outStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(fileName, new String[0]), new OpenOption[0]));){
            this.export(outStream);
        }
        catch (IOException e) {
            throw new ExcelException(e);
        }
    }

    public void export(OutputStream outStream) throws ExcelException {
        try (Workbook wb = this.export();){
            if (this.injector != null) {
                this.injector.doInject(wb);
            }
            wb.write(outStream);
        }
        catch (IOException e) {
            throw new ExcelException(e);
        }
    }

    private Workbook export() {
        Workbook wb = this.createWorkbook();
        WorkbookContext context = new WorkbookContext(wb);
        if (this.reportData != null) {
            Sheet sheet = wb.createSheet();
            WorksheetWriterEx sheetWriter = new WorksheetWriterEx(context, sheet, this.reportData, this.printSetting);
            sheetWriter.setAddTitle(this.addTitle);
            if (this.addTitle) {
                sheetWriter.setTitle(this.title);
            }
            sheetWriter.setAutoAdjust(this.autoAdjust);
            sheetWriter.writeWorkSheet();
            return wb;
        }
        for (int i = 0; i < this.reportDatas.length; ++i) {
            Sheet sheet = this.pages != null ? wb.createSheet(this.pages[i]) : wb.createSheet();
            PrintSetting printSetting = this.printSettings != null ? this.printSettings[i] : null;
            WorksheetWriterEx sheetWriter = new WorksheetWriterEx(context, sheet, this.reportDatas[i], printSetting);
            sheetWriter.setAddTitle(this.addTitle);
            if (this.addTitle) {
                sheetWriter.setTitle(this.title);
            }
            sheetWriter.setAutoAdjust(this.autoAdjust);
            sheetWriter.writeWorkSheet();
        }
        return wb;
    }

    private Workbook createWorkbook() {
        if (!this.isXlsx) {
            return new HSSFWorkbook();
        }
        if (this.countRows() < 60000) {
            return new XSSFWorkbook();
        }
        return new SXSSFWorkbook();
    }

    private int countRows() {
        int count = 0;
        if (this.reportData != null) {
            count = this.reportData.getRowCount();
        } else {
            for (GridData data : this.reportDatas) {
                count += data.getRowCount();
            }
        }
        return count;
    }
}


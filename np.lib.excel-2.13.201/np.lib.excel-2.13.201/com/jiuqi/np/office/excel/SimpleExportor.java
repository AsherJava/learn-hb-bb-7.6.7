/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.GridData
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.office.excel.ExcelException;
import com.jiuqi.np.office.excel.WorksheetWriter;
import com.jiuqi.np.office.excel.WorksheetWriterExs;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimpleExportor {
    private boolean autoAdjust;
    private boolean addTitle;
    private String title;
    private GridData reportData;
    private HashMap cellStyleHashMap;
    private String[] pages;
    private GridData[] reportDatas;
    private boolean isXlsx;

    public SimpleExportor(GridData gridData) {
        this(null, gridData);
    }

    public SimpleExportor(GridData gridData, boolean isXlsx) {
        this(null, gridData, isXlsx);
    }

    public SimpleExportor(String title, GridData gridData) {
        this(title, gridData, false);
    }

    public SimpleExportor(String title, GridData gridData, boolean isXlsx) {
        this.reportData = gridData;
        this.title = title;
        this.cellStyleHashMap = new HashMap();
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
        this.reportDatas = gridDatas;
        this.pages = pages;
        this.isXlsx = isXlsx;
        this.cellStyleHashMap = new HashMap();
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

    public void export(OutputStream outStream) throws ExcelException {
        if (!this.isXlsx) {
            this.creatExcel03(outStream);
        } else {
            int maxRowCount = 0;
            if (this.reportData != null) {
                maxRowCount = this.reportData.getRowCount();
            } else {
                for (int i = 0; i < this.reportDatas.length; ++i) {
                    if (this.reportDatas[i].getRowCount() <= maxRowCount) continue;
                    maxRowCount = this.reportDatas[i].getRowCount();
                }
            }
            if (maxRowCount < 60000) {
                this.creatExcel07(outStream);
            } else {
                this.creatExcel07s(outStream);
            }
        }
    }

    private void creatExcel07(OutputStream outStream) throws ExcelException {
        XSSFSheet sheet;
        XSSFWorkbook wb = new XSSFWorkbook();
        if (this.reportData != null) {
            sheet = wb.createSheet();
            this.cellStyleHashMap.clear();
            WorksheetWriterExs sheetWriter = new WorksheetWriterExs(wb, sheet, this.reportData, this.cellStyleHashMap);
            sheetWriter.setAddTitle(this.addTitle);
            if (this.addTitle) {
                sheetWriter.setTitle(this.title);
            }
            sheetWriter.setAutoAdjust(this.autoAdjust);
            sheetWriter.writeWorkSheet();
        } else {
            for (int i = 0; i < this.reportDatas.length; ++i) {
                sheet = this.pages != null ? wb.createSheet(this.pages[i]) : wb.createSheet();
                this.cellStyleHashMap.clear();
                WorksheetWriterExs sheetWriter = new WorksheetWriterExs(wb, sheet, this.reportDatas[i], this.cellStyleHashMap);
                sheetWriter.setAddTitle(this.addTitle);
                if (this.addTitle) {
                    sheetWriter.setTitle(this.title);
                }
                sheetWriter.setAutoAdjust(this.autoAdjust);
                sheetWriter.writeWorkSheet();
            }
        }
        try {
            wb.write(outStream);
        }
        catch (IOException e) {
            throw new ExcelException(e);
        }
    }

    private void creatExcel07s(OutputStream outStream) throws ExcelException {
        Sheet sheet;
        SXSSFWorkbook wb = new SXSSFWorkbook();
        if (this.reportData != null) {
            sheet = wb.createSheet();
            this.cellStyleHashMap.clear();
            WorksheetWriterExs sheetWriter = new WorksheetWriterExs(wb, sheet, this.reportData, this.cellStyleHashMap);
            sheetWriter.setAddTitle(this.addTitle);
            if (this.addTitle) {
                sheetWriter.setTitle(this.title);
            }
            sheetWriter.setAutoAdjust(this.autoAdjust);
            sheetWriter.writeWorkSheet();
        } else {
            for (int i = 0; i < this.reportDatas.length; ++i) {
                sheet = this.pages != null ? wb.createSheet(this.pages[i]) : wb.createSheet();
                this.cellStyleHashMap.clear();
                WorksheetWriterExs sheetWriter = new WorksheetWriterExs(wb, sheet, this.reportDatas[i], this.cellStyleHashMap);
                sheetWriter.setAddTitle(this.addTitle);
                if (this.addTitle) {
                    sheetWriter.setTitle(this.title);
                }
                sheetWriter.setAutoAdjust(this.autoAdjust);
                sheetWriter.writeWorkSheet();
            }
        }
        try {
            wb.write(outStream);
        }
        catch (IOException e) {
            throw new ExcelException(e);
        }
    }

    private void creatExcel03(OutputStream outStream) throws ExcelException {
        HSSFSheet sheet;
        HSSFWorkbook wb = new HSSFWorkbook();
        if (this.reportData != null) {
            sheet = wb.createSheet();
            this.cellStyleHashMap.clear();
            WorksheetWriter sheetWriter = new WorksheetWriter(wb, sheet, this.reportData, this.cellStyleHashMap);
            sheetWriter.setAddTitle(this.addTitle);
            if (this.addTitle) {
                sheetWriter.setTitle(this.title);
            }
            sheetWriter.setAutoAdjust(this.autoAdjust);
            sheetWriter.writeWorkSheet();
        } else {
            for (int i = 0; i < this.reportDatas.length; ++i) {
                sheet = this.pages != null ? wb.createSheet(this.pages[i]) : wb.createSheet();
                this.cellStyleHashMap.clear();
                WorksheetWriter sheetWriter = new WorksheetWriter(wb, sheet, this.reportDatas[i], this.cellStyleHashMap);
                sheetWriter.setAddTitle(this.addTitle);
                if (this.addTitle) {
                    sheetWriter.setTitle(this.title);
                }
                sheetWriter.setAutoAdjust(this.autoAdjust);
                sheetWriter.writeWorkSheet();
            }
        }
        try {
            wb.write(outStream);
        }
        catch (IOException e) {
            throw new ExcelException(e);
        }
    }
}


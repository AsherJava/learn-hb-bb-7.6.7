/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.zip.ZipEntry
 *  com.jiuqi.bi.util.zip.ZipOutputStream
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.ExcelException;
import com.jiuqi.bi.office.excel.GridIterator;
import com.jiuqi.bi.office.excel.SimpleExportor;
import com.jiuqi.bi.office.excel.WorkbookContext;
import com.jiuqi.bi.office.excel.WorksheetWriterEx;
import com.jiuqi.bi.office.excel.watermark.IWatermarkInjector;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.zip.ZipEntry;
import com.jiuqi.bi.util.zip.ZipOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BatchExportor {
    private boolean autoAdjust;
    private boolean addTitle;
    private boolean toSingleFile;
    private final GridIterator iterator;
    private final Set<String> filenames;
    private String encoding;
    private IWatermarkInjector injector;

    public BatchExportor(GridIterator iterator) {
        this.iterator = iterator;
        this.filenames = new HashSet<String>();
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

    public void setToSingleFile(boolean toSingleFile) {
        this.toSingleFile = toSingleFile;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isAutoAdjust() {
        return this.autoAdjust;
    }

    public boolean isAddTitle() {
        return this.addTitle;
    }

    public boolean isToSingleFile() {
        return this.toSingleFile;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void Export(OutputStream outStream) throws Exception {
        this.export(outStream);
    }

    public void export(OutputStream outStream) throws Exception {
        if (this.toSingleFile) {
            this.exportToSingle(outStream);
        } else {
            this.exportToBatch(outStream);
        }
    }

    private void exportToSingle(OutputStream outStream) throws ExcelException {
        this.filenames.clear();
        XSSFWorkbook wb = new XSSFWorkbook();
        WorkbookContext context = new WorkbookContext((Workbook)wb);
        while (this.iterator.next()) {
            GridData reportData = this.iterator.getGridData();
            String rptTitle = this.validateFileName(this.iterator.getTitle());
            XSSFSheet sheet = wb.createSheet(rptTitle);
            WorksheetWriterEx sheetWriter = new WorksheetWriterEx(context, (Sheet)sheet, reportData);
            sheetWriter.setAddTitle(this.addTitle);
            sheetWriter.setTitle(this.addTitle ? this.iterator.getTitle() : "");
            sheetWriter.setAutoAdjust(this.autoAdjust);
            sheetWriter.writeWorkSheet();
        }
        if (this.injector != null) {
            this.injector.doInject((Workbook)wb);
        }
        try {
            wb.write(outStream);
        }
        catch (IOException e) {
            throw new ExcelException(e);
        }
    }

    private void exportToBatch(OutputStream outStream) throws ExcelException {
        this.filenames.clear();
        ZipOutputStream zipOutStream = new ZipOutputStream(outStream);
        byte[] buf = new byte[1024];
        while (this.iterator.next()) {
            SimpleExportor excelExportor = new SimpleExportor(this.iterator.getTitle(), this.iterator.getGridData(), true);
            excelExportor.setAddTitle(this.addTitle);
            excelExportor.setAutoAdjust(this.autoAdjust);
            excelExportor.setWatermarkInjector(this.injector);
            ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
            excelExportor.export(outBuffer);
            ZipEntry zipEntry = new ZipEntry(this.validateFileName(this.iterator.getTitle()) + ".xls");
            zipEntry.setSize((long)outBuffer.size());
            Date now = new Date();
            zipEntry.setTime(now.getTime());
            try {
                int readLen;
                zipOutStream.putNextEntry(zipEntry);
                ByteArrayInputStream is = new ByteArrayInputStream(outBuffer.toByteArray());
                while ((readLen = ((InputStream)is).read(buf, 0, 1024)) != -1) {
                    zipOutStream.write(buf, 0, readLen);
                }
                if (this.encoding == null || "".equals(this.encoding)) continue;
                zipOutStream.setEncoding(this.encoding);
            }
            catch (IOException e) {
                throw new ExcelException(e);
            }
        }
        try {
            zipOutStream.close();
        }
        catch (IOException e) {
            throw new ExcelException(e);
        }
    }

    private String validateFileName(String filename) {
        if (StringUtils.isEmpty(filename) || filename.length() >= 32) {
            filename = "report";
        }
        String newName = filename;
        int index = 0;
        while (this.filenames.contains(newName)) {
            newName = filename + "_" + ++index;
        }
        this.filenames.add(newName);
        return newName;
    }
}


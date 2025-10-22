/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.zip.ZipEntry
 *  com.jiuqi.bi.util.zip.ZipOutputStream
 *  com.jiuqi.np.grid.GridData
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.zip.ZipEntry;
import com.jiuqi.bi.util.zip.ZipOutputStream;
import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.office.excel.ExcelException;
import com.jiuqi.np.office.excel.GridIterator;
import com.jiuqi.np.office.excel.SimpleExportor;
import com.jiuqi.np.office.excel.WorksheetWriterExs;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BatchExportor {
    private boolean autoAdjust;
    private boolean addTitle;
    private boolean toSingleFile;
    private GridIterator iterator;
    private Set filenames;
    private HashMap cellStyleHashMap;
    private String encoding;

    public BatchExportor(GridIterator iterator) {
        this.iterator = iterator;
        this.filenames = new HashSet();
        this.cellStyleHashMap = new HashMap();
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
        if (this.toSingleFile) {
            this.exportToSingle(outStream);
        } else {
            this.exportToBatch(outStream);
        }
    }

    private void exportToSingle(OutputStream outStream) throws ExcelException {
        this.filenames.clear();
        GridData reportData = null;
        XSSFWorkbook wb = new XSSFWorkbook();
        while (this.iterator.next()) {
            reportData = this.iterator.getGridData();
            String rptTitle = this.validateFileName(this.iterator.getTitle());
            XSSFSheet sheet = wb.createSheet(rptTitle);
            WorksheetWriterExs sheetWriter = new WorksheetWriterExs(wb, sheet, reportData, this.cellStyleHashMap);
            sheetWriter.setAddTitle(this.addTitle);
            if (this.addTitle) {
                sheetWriter.setTitle(this.iterator.getTitle());
            } else {
                sheetWriter.setTitle("");
            }
            sheetWriter.setAutoAdjust(this.autoAdjust);
            sheetWriter.writeWorkSheet();
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
        ZipEntry zipEntry = null;
        byte[] buf = new byte[1024];
        int readLen = 0;
        while (this.iterator.next()) {
            SimpleExportor excelExportor = new SimpleExportor(this.iterator.getTitle(), this.iterator.getGridData(), true);
            excelExportor.setAddTitle(this.addTitle);
            excelExportor.setAutoAdjust(this.autoAdjust);
            ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
            excelExportor.export(outBuffer);
            zipEntry = new ZipEntry(this.validateFileName(this.iterator.getTitle()) + ".xls");
            zipEntry.setSize((long)outBuffer.size());
            Date now = new Date();
            zipEntry.setTime(now.getTime());
            try {
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
        if (StringUtils.isEmpty((String)filename) || filename.length() >= 32) {
            filename = "report";
        }
        String newName = filename;
        int index = 0;
        while (this.filenames.contains(newName)) {
            newName = filename + "_" + Integer.toString(++index);
        }
        this.filenames.add(newName);
        return newName;
    }
}


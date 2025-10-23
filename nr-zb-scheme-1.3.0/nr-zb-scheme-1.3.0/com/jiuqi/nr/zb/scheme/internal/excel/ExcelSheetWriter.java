/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel;

import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class ExcelSheetWriter {
    private static final Logger log = LoggerFactory.getLogger(ExcelSheetWriter.class);
    private final List<String> headers = new ArrayList<String>(25);
    private final List<String> extHeaders = new ArrayList<String>();
    private final Map<Class<?>, IExcelRowWriter> writerMap = new HashMap();
    private final List<IExcelRowWrapper> rows = new ArrayList<IExcelRowWrapper>(128);
    private final Sheet sheet;
    private final List<String> allHeaders = new ArrayList<String>(25);

    public ExcelSheetWriter(Sheet sheet) {
        Assert.notNull((Object)sheet, "sheet \u4e0d\u80fd\u4e3a\u7a7a");
        this.sheet = sheet;
    }

    public List<String> getHeaders() {
        return this.headers;
    }

    public List<String> getExtHeaders() {
        return this.extHeaders;
    }

    public List<String> getAllHeaders() {
        return this.allHeaders;
    }

    public Sheet getSheet() {
        return this.sheet;
    }

    public Map<Class<?>, IExcelRowWriter> getWriterMap() {
        return this.writerMap;
    }

    public List<IExcelRowWrapper> getRows() {
        return this.rows;
    }

    public void addHeader(List<String> headers) {
        if (!CollectionUtils.isEmpty(headers)) {
            this.headers.addAll(headers);
            this.allHeaders.addAll(headers);
        }
    }

    public void addExtHeader(List<String> headers) {
        if (!CollectionUtils.isEmpty(headers)) {
            this.extHeaders.addAll(headers);
            this.allHeaders.addAll(headers);
        }
    }

    public void registerWriter(Class<?> clazz, IExcelRowWriter writer) {
        Assert.notNull((Object)writer, "writer \u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull(clazz, "clazz \u4e0d\u80fd\u4e3a\u7a7a");
        this.writerMap.put(clazz, writer);
    }

    public void addRow(IExcelRowWrapper rowWrapper) {
        this.addRow(rowWrapper, -1);
    }

    public void addRow(IExcelRowWrapper rowWrapper, int rowIndex) {
        if (rowIndex > 0) {
            this.rows.add(rowIndex, rowWrapper);
        } else {
            this.rows.add(rowWrapper);
        }
    }

    public void write() {
        this.writeHeader(this.sheet);
        this.writeRows(this.sheet);
    }

    private void writeHeader(Sheet sheet) {
        Row row = sheet.createRow(0);
        List<String> allHeader = this.getAllHeaders();
        for (int i = 0; i < allHeader.size(); ++i) {
            row.createCell(i).setCellValue(allHeader.get(i));
        }
    }

    private void writeRows(Sheet sheet) {
        int rowNum = 1;
        for (IExcelRowWrapper rowWrapper : this.rows) {
            log.debug("\u6b63\u5728\u5199\u5165\u7b2c{}\u884c\u6570\u636e", (Object)rowNum);
            Row row = sheet.createRow(rowNum++);
            Object data = rowWrapper.getData();
            IExcelRowWriter excelRowWriter = this.writerMap.get(data.getClass());
            if (excelRowWriter == null) {
                throw new RuntimeException("\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u884c\u5199\u5165\u5668");
            }
            try {
                excelRowWriter.write(rowWrapper, this.getAllHeaders(), row);
            }
            catch (Exception e) {
                log.error("\u884c\u5199\u5165\u5668\u5199\u5165\u5931\u8d25\uff1a{}\uff1b\n\u6570\u636e\uff1a{}\uff1b\n\u9519\u8bef\u4fe1\u606f\uff1a{}", rowNum, rowWrapper, e);
            }
        }
    }
}


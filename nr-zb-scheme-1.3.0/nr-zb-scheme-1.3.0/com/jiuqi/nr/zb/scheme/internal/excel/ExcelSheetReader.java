/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel;

import com.jiuqi.nr.zb.scheme.exception.ZbSchemeException;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowReader;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowReaderProvider;
import com.jiuqi.nr.zb.scheme.internal.excel.IExcelRowWrapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

public class ExcelSheetReader {
    private static final Logger logger = LoggerFactory.getLogger(ExcelSheetReader.class);
    private final Sheet sheet;
    private final IExcelRowReaderProvider excelRowReaderProvider;
    private final List<String> headers;
    private final List<String> extHeaders;
    private Map<String, Short> readHeaders;
    private final List<IExcelRowWrapper> rows;
    private boolean success = true;

    public ExcelSheetReader(Sheet sheet, IExcelRowReaderProvider excelRowReaderProvider) {
        Assert.notNull((Object)sheet, "sheet is null");
        Assert.notNull((Object)excelRowReaderProvider, "excelRowReaderProvider is null");
        this.sheet = sheet;
        this.excelRowReaderProvider = excelRowReaderProvider;
        this.headers = new ArrayList<String>(25);
        this.extHeaders = new ArrayList<String>(25);
        this.rows = new ArrayList<IExcelRowWrapper>(sheet.getLastRowNum());
    }

    public boolean isSuccess() {
        return this.success;
    }

    public List<String> getHeaders() {
        return this.headers;
    }

    public List<String> getExtHeaders() {
        return this.extHeaders;
    }

    public List<String> getAllHeaders() {
        ArrayList<String> allHeaders = new ArrayList<String>(this.headers.size() + this.extHeaders.size());
        allHeaders.addAll(this.headers);
        allHeaders.addAll(this.extHeaders);
        return allHeaders;
    }

    public Map<String, Short> getReadHeaders() {
        return this.readHeaders;
    }

    public void addHeader(List<String> headers) {
        if (headers != null) {
            this.headers.addAll(headers);
        }
    }

    public void addExtHeader(List<String> extHeader) {
        if (extHeader != null) {
            this.extHeaders.addAll(extHeader);
        }
    }

    public List<IExcelRowWrapper> getRows() {
        return this.rows;
    }

    public void read() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int lastRowNum = this.sheet.getLastRowNum();
        if (lastRowNum >= 0) {
            this.readHead();
        }
        if (lastRowNum > 0) {
            for (int i = 1; i <= lastRowNum; ++i) {
                Row row = this.sheet.getRow(i);
                if (row == null || row.getFirstCellNum() > 0) {
                    logger.debug("\u7b2c{}\u884c\u6570\u636e\u4e3a\u7a7a\uff0c\u8df3\u8fc7", (Object)(i + 1));
                    continue;
                }
                IExcelRowReader excelRowReader = this.excelRowReaderProvider.getExcelRowReader(row, this.readHeaders);
                if (excelRowReader == null) {
                    throw new ZbSchemeException("\u7f3a\u5c11\u884c\u8bfb\u53d6\u5668");
                }
                IExcelRowWrapper rowWrapper = excelRowReader.read(i, this.readHeaders, row);
                if (rowWrapper == null) continue;
                if (!rowWrapper.isValid()) {
                    logger.error("\u8bfb\u53d6\u884c\u6570\u636e\u5931\u8d25\uff0c\u884c\u6570\uff1a{}\uff0c\u9519\u8bef\u4fe1\u606f\uff1a{}", (Object)(i + 1), (Object)rowWrapper.getErrors());
                    this.success = false;
                }
                this.rows.add(rowWrapper);
            }
            stopWatch.stop();
            logger.debug("\u603b\u5171\u8bfb\u53d6\u884c\uff1a{}\uff0c\u8bfb\u53d6\u64cd\u4f5c\u8017\u65f6\uff1a{} ms", (Object)this.rows.size(), (Object)stopWatch.getTotalTimeMillis());
        }
    }

    private void readHead() {
        Row row = this.sheet.getRow(0);
        short firstCellNum = row.getFirstCellNum();
        short lastCellNum = row.getLastCellNum();
        this.readHeaders = new LinkedHashMap<String, Short>(this.headers.size());
        HashSet<String> allHeaders = new HashSet<String>(this.headers.size() + this.extHeaders.size());
        allHeaders.addAll(this.headers);
        allHeaders.addAll(this.extHeaders);
        for (short i = firstCellNum; i < lastCellNum; i = (short)(i + 1)) {
            Cell cell = row.getCell(i);
            if (cell.getCellType() != CellType.STRING) {
                throw new ZbSchemeException("Excel\u5217\u6807\u9898\u4e0d\u662f\u5b57\u7b26");
            }
            String value = cell.getStringCellValue();
            if (!allHeaders.contains(value)) continue;
            this.readHeaders.put(value, i);
            allHeaders.remove(value);
        }
        if (!allHeaders.isEmpty()) {
            throw new ZbSchemeException("\u4e0d\u662f\u6b63\u786e\u7684\u6a21\u7248");
        }
    }
}


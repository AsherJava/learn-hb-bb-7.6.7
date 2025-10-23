/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.utils.excel.reader;

import com.jiuqi.nr.formula.exception.FormulaResourceException;
import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.core.ExcelTypeEnum;
import com.jiuqi.nr.formula.utils.excel.reader.ReadSheet;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

public class ReadWorkBook {
    private ExcelTypeEnum excelTypeEnum;
    private InputStream inputStream;
    private MultipartFile file;
    private Charset charset;
    private Workbook workbook;
    private Class<? extends ExcelEntity> headClass;
    private Class<?> rowClass;
    private List<ReadSheet> sheetList;

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Class<? extends ExcelEntity> getHeadClass() {
        if (this.headClass == null) {
            throw new FormulaResourceException("\u5bfc\u5165excel\u7f3a\u5c11\u6587\u4ef6head\u6570\u636e! ");
        }
        return this.headClass;
    }

    public void setHeadClass(Class<? extends ExcelEntity> headClass) {
        this.headClass = headClass;
    }

    public ExcelTypeEnum getExcelTypeEnum() {
        return this.excelTypeEnum;
    }

    public void setExcelTypeEnum(ExcelTypeEnum excelTypeEnum) {
        this.excelTypeEnum = excelTypeEnum;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public MultipartFile getFile() {
        return this.file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Class<?> getRowClass() {
        return this.rowClass;
    }

    public void setRowClass(Class<?> rowClass) {
        this.rowClass = rowClass;
    }

    public List<ReadSheet> getSheetList() {
        if (this.sheetList == null) {
            this.sheetList = new ArrayList<ReadSheet>();
        }
        return this.sheetList;
    }

    public void setSheetList(List<ReadSheet> sheetList) {
        this.sheetList = sheetList;
    }
}


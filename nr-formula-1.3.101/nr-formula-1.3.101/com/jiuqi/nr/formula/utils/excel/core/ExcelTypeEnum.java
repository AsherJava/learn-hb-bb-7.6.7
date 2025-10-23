/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.formula.utils.excel.core;

import com.jiuqi.nr.formula.utils.excel.reader.ReadWorkBook;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public enum ExcelTypeEnum {
    XLS(".xls", 2003),
    XLSX(".xlsx", 2007);

    private final String suffix;
    private final int version;

    private ExcelTypeEnum(String suffix, int version) {
        this.suffix = suffix;
        this.version = version;
    }

    public static ExcelTypeEnum valueOf(ReadWorkBook readWorkBook) {
        ExcelTypeEnum excelTypeEnum = readWorkBook.getExcelTypeEnum();
        if (excelTypeEnum != null) {
            return excelTypeEnum;
        }
        MultipartFile file = readWorkBook.getFile();
        InputStream inputStream = readWorkBook.getInputStream();
        if (file == null && inputStream == null) {
            throw new RuntimeException("File and inputStream must be a non-null.");
        }
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (fileName.endsWith(XLSX.getSuffix())) {
                    return XLSX;
                }
                if (fileName.endsWith(XLS.getSuffix())) {
                    return XLS;
                }
            }
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getSuffix() {
        return this.suffix;
    }

    public int getVersion() {
        return this.version;
    }
}


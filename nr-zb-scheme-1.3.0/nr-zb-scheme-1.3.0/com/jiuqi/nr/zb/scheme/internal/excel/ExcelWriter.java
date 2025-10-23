/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.excel;

import com.jiuqi.nr.zb.scheme.internal.excel.ExcelSheetWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;

public class ExcelWriter {
    private final Workbook workbook;
    private final List<ExcelSheetWriter> sheets = new ArrayList<ExcelSheetWriter>();

    public ExcelWriter(Workbook workbook) {
        Assert.notNull((Object)workbook, "Workbook must not be null");
        this.workbook = workbook;
    }

    public Workbook getWorkbook() {
        return this.workbook;
    }

    public ExcelSheetWriter createSheet() {
        ExcelSheetWriter sheetWriter = new ExcelSheetWriter(this.workbook.createSheet());
        this.sheets.add(sheetWriter);
        return sheetWriter;
    }

    public ExcelSheetWriter createSheet(String sheetName) {
        ExcelSheetWriter sheetWriter = new ExcelSheetWriter(this.workbook.createSheet(sheetName));
        this.sheets.add(sheetWriter);
        return sheetWriter;
    }

    public void write() {
        for (ExcelSheetWriter sheet : this.sheets) {
            sheet.write();
        }
    }
}


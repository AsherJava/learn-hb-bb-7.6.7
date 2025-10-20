/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.openxml4j.exceptions.InvalidFormatException
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.usermodel.WorkbookFactory
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.office.excel.WorksheetReader;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelInput {
    private GridData gridData;
    private Workbook workbook;
    private String[] name;
    private boolean isParseFormula;

    public ExcelInput(InputStream inStream) throws InvalidFormatException, IOException {
        this(inStream, false);
    }

    public ExcelInput(InputStream inStream, boolean isParseFormula) throws InvalidFormatException, IOException {
        this.workbook = WorkbookFactory.create((InputStream)inStream);
        inStream.close();
        int count = this.workbook.getNumberOfSheets();
        this.name = new String[count];
        for (int i = 0; i < count; ++i) {
            this.name[i] = this.workbook.getSheetName(i);
        }
        this.isParseFormula = isParseFormula;
    }

    public String[] getName() {
        return this.name;
    }

    public GridData getGridData(String name) {
        Sheet sheet = this.workbook.getSheet(name);
        WorksheetReader wsr = new WorksheetReader(this.workbook, sheet, this.isParseFormula);
        this.gridData = wsr.getGridData();
        return this.gridData;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.GridData
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.grid.GridData;
import com.jiuqi.np.office.excel.ExcelException;
import com.jiuqi.np.office.excel.FormulaConvertor;
import com.jiuqi.np.office.excel.WorksheetReader;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class SimpleImportor {
    final Workbook workbook;
    private FormulaConvertor fc;
    private boolean isParseFormula;

    public SimpleImportor(InputStream inStream) throws ExcelException {
        this(inStream, false);
    }

    public SimpleImportor(InputStream inStream, boolean isParseFormula) throws ExcelException {
        try {
            this.workbook = WorkbookFactory.create(inStream);
            this.isParseFormula = isParseFormula;
        }
        catch (Exception e) {
            throw new ExcelException(e);
        }
    }

    public int sheetSize() {
        return this.workbook.getNumberOfSheets();
    }

    public String getSheetName(int index) {
        return this.workbook.getSheetName(index);
    }

    public GridData read(int sheetIndex) throws ExcelException {
        Sheet sheet = this.workbook.getSheetAt(sheetIndex);
        if (sheet == null) {
            throw new ExcelException("Excel\u8bfb\u53d6\u9875\u7b7e\u7d22\u5f15\u8d8a\u754c\uff1a" + sheetIndex);
        }
        WorksheetReader reader = new WorksheetReader(this.workbook, sheet, this.isParseFormula);
        reader.setFormulaConvertor(this.fc);
        return reader.getGridData();
    }

    public GridData read(String sheetName) throws ExcelException {
        int index = this.workbook.getSheetIndex(sheetName);
        if (index < 0) {
            throw new ExcelException("\u9875\u7b7e[" + sheetName + "]\u4e0d\u5b58\u5728\uff01");
        }
        return this.read(index);
    }

    public void setFormulaConvertor(FormulaConvertor fc) {
        this.fc = fc;
    }
}


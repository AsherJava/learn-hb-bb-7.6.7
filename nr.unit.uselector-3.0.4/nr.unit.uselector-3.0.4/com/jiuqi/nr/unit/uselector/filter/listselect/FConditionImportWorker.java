/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.uselector.dataio.ExcelRowData;
import com.jiuqi.nr.unit.uselector.dataio.IExcelReaderWorker;
import com.jiuqi.nr.unit.uselector.dataio.IRowData;
import com.jiuqi.nr.unit.uselector.filter.listselect.FConditionTemplateExport;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

public class FConditionImportWorker
implements IExcelReaderWorker {
    private String selector;

    public FConditionImportWorker(String selector) {
        this.selector = selector;
    }

    @Override
    public List<IRowData> readRows(InputStream is) throws Exception {
        ArrayList<IRowData> dataRows = new ArrayList<IRowData>();
        XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook(OPCPackage.open(is));
        XSSFSheet sheet = workbook.getSheetAt(0);
        if (!this.isTemplateSheet(sheet)) {
            throw new UnitTreeRuntimeException("\u8bf7\u4f7f\u7528\u6807\u51c6\u6a21\u677f!");
        }
        this.readSheet(0, sheet, dataRows);
        return dataRows;
    }

    protected void readSheet(int sIdx, XSSFSheet sheet, List<IRowData> dataRows) {
        int minRowIdx = sheet.getFirstRowNum() + 1;
        int maxRowIdx = sheet.getLastRowNum();
        for (int rowIdx = minRowIdx; rowIdx <= maxRowIdx; ++rowIdx) {
            XSSFRow row = sheet.getRow(rowIdx);
            if (row == null) continue;
            this.readRow(row, dataRows);
        }
    }

    protected boolean isTemplateSheet(XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(sheet.getFirstRowNum());
        XSSFCell codeCell = row.getCell(0);
        XSSFCell titleCell = row.getCell(1);
        if (codeCell != null && titleCell != null) {
            codeCell.setCellType(CellType.STRING);
            String code = codeCell.getStringCellValue();
            titleCell.setCellType(CellType.STRING);
            String title = titleCell.getStringCellValue();
            if (StringUtils.isNotEmpty((String)code) && StringUtils.isNotEmpty((String)title)) {
                return code.equals(FConditionTemplateExport.columnNames[0]) && title.equals(FConditionTemplateExport.columnNames[1]);
            }
        }
        return false;
    }

    protected void readRow(XSSFRow row, List<IRowData> dataRows) {
        String code = null;
        String title = null;
        XSSFCell cell = row.getCell(0);
        if (cell != null) {
            cell.setCellType(CellType.STRING);
            code = cell.getStringCellValue();
        }
        if ((cell = row.getCell(1)) != null) {
            cell.setCellType(CellType.STRING);
            title = cell.getStringCellValue();
        }
        if (StringUtils.isNotEmpty((String)code) || StringUtils.isNotEmpty(title)) {
            ExcelRowData data = new ExcelRowData();
            dataRows.add(data);
            data.setCode(code);
            data.setTitle(title);
        }
    }
}


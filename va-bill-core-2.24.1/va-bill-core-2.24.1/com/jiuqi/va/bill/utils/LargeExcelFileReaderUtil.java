/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.openxml4j.opc.OPCPackage
 *  org.apache.poi.poifs.filesystem.FileMagic
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.DataFormatter
 *  org.apache.poi.ss.usermodel.DateUtil
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.usermodel.WorkbookFactory
 *  org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable
 *  org.apache.poi.xssf.eventusermodel.XSSFReader
 *  org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler
 *  org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler$SheetContentsHandler
 *  org.apache.poi.xssf.model.SharedStrings
 *  org.apache.poi.xssf.model.Styles
 *  org.apache.poi.xssf.model.StylesTable
 *  org.apache.poi.xssf.usermodel.XSSFComment
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.bill.intf.BillException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStrings;
import org.apache.poi.xssf.model.Styles;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.util.StringUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class LargeExcelFileReaderUtil {
    public List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    private int currentRow = 0;
    private Map<String, Object> rowData;
    List<Map<String, Object>> titles;
    private String numberPattern = "^(\uffe5|\\$|\u20ac)?-?[1-9][0-9]{0,2}(,[0-9]{3}){0,10}(\\.[0-9]{0,10})? ?$";
    private int startRow = 2;
    private int startColumn = 1;

    public LargeExcelFileReaderUtil(Integer startRow, Integer startColumn) {
        if (startRow != null) {
            this.startRow = startRow;
        }
        if (startColumn != null) {
            this.startColumn = startColumn;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void processOneSheet(byte[] data, List<Map<String, Object>> titles) throws Exception {
        this.titles = titles;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(data);
        ByteArrayInputStream input = new ByteArrayInputStream(bos.toByteArray());
        OPCPackage pkg = null;
        InputStream is = FileMagic.prepareToCheckMagic((InputStream)input);
        try {
            FileMagic fm = FileMagic.valueOf((InputStream)is);
            switch (fm) {
                case OLE2: {
                    this.processSheet2003(input);
                    return;
                }
                default: {
                    pkg = OPCPackage.open((InputStream)input);
                    this.processSheet2007(pkg);
                    return;
                }
            }
        }
        catch (Exception e) {
            throw new BillException("\u8868\u683c\u8bfb\u53d6\u5931\u8d25");
        }
        finally {
            bos.close();
            ((InputStream)input).close();
            if (pkg != null) {
                pkg.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    private void processSheet2003(InputStream input) throws Exception {
        Workbook excel = WorkbookFactory.create((InputStream)input);
        Sheet sheet = excel.getSheetAt(0);
        while (this.currentRow <= sheet.getLastRowNum()) {
            Row row = sheet.getRow(this.currentRow);
            if (this.currentRow >= this.startRow - 1 && row != null) {
                HashMap<String, Object> value = new HashMap<String, Object>();
                value.put("_state", 1);
                value.put("_stateMessage", "");
                value.put("_index", this.dataList.size() + 1);
                for (int index = 1; index <= row.getLastCellNum(); ++index) {
                    String cellValue;
                    if (index < this.startColumn || index - this.startColumn >= this.titles.size() || !StringUtils.hasText(cellValue = this.getCellValue(row.getCell(index - 1), this.titles.get(index - this.startColumn).get("valueType")))) continue;
                    value.put((String)this.titles.get(index - this.startColumn).get("fieldName"), cellValue);
                }
                if (value.size() > 3) {
                    this.dataList.add(value);
                }
            }
            ++this.currentRow;
        }
    }

    private void processSheet2007(OPCPackage pkg) throws Exception {
        XSSFReader r = new XSSFReader(pkg);
        StylesTable styles = r.getStylesTable();
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
        InputStream sheetInputStream = (InputStream)r.getSheetsData().next();
        XMLReader parser = XMLReaderFactory.createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
        DataFormatter dataFormatter = new DataFormatter(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public String formatRawCellContents(double value, int formatIndex, String formatString) {
                if (DateUtil.isADateFormat((int)formatIndex, (String)formatString) && DateUtil.isValidExcelDate((double)value)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d = DateUtil.getJavaDate((double)value, (boolean)false);
                    SimpleDateFormat simpleDateFormat = dateFormat;
                    synchronized (simpleDateFormat) {
                        return dateFormat.format((Object)d);
                    }
                }
                return super.formatRawCellContents(value, formatIndex, formatString);
            }
        };
        XSSFSheetXMLHandler handler = new XSSFSheetXMLHandler((Styles)styles, (SharedStrings)strings, new XSSFSheetXMLHandler.SheetContentsHandler(){

            public void startRow(int rowNum) {
                LargeExcelFileReaderUtil.this.rowData = new HashMap();
                LargeExcelFileReaderUtil.this.rowData.put("_state", 1);
                LargeExcelFileReaderUtil.this.rowData.put("_stateMessage", "");
                LargeExcelFileReaderUtil.this.rowData.put("_index", LargeExcelFileReaderUtil.this.dataList.size() + 1);
                LargeExcelFileReaderUtil.this.currentRow++;
            }

            public void endRow(int rowNum) {
                if (LargeExcelFileReaderUtil.this.rowData.size() > 3) {
                    LargeExcelFileReaderUtil.this.dataList.add(LargeExcelFileReaderUtil.this.rowData);
                }
            }

            public void cell(String cellReference, String formattedValue, XSSFComment comment) {
                if (LargeExcelFileReaderUtil.this.currentRow < LargeExcelFileReaderUtil.this.startRow || !StringUtils.hasText(formattedValue)) {
                    return;
                }
                if (!StringUtils.hasText(cellReference)) {
                    return;
                }
                int key = 0;
                for (int i = 0; i < cellReference.length(); ++i) {
                    if (!Character.isLetter(cellReference.charAt(i))) continue;
                    key = key * 26 + cellReference.charAt(i) - 65 + 1;
                }
                if (key < LargeExcelFileReaderUtil.this.startColumn) {
                    return;
                }
                if (key - LargeExcelFileReaderUtil.this.startColumn < LargeExcelFileReaderUtil.this.titles.size()) {
                    Map<String, Object> title = LargeExcelFileReaderUtil.this.titles.get(key - LargeExcelFileReaderUtil.this.startColumn);
                    if (title.get("valueType").equals("DATE")) {
                        formattedValue = formattedValue.split(" ")[0];
                    }
                    if ((title.get("valueType").equals("DECIMAL") || title.get("valueType").equals("INTEGER")) && formattedValue.matches(LargeExcelFileReaderUtil.this.numberPattern)) {
                        formattedValue = formattedValue.replaceAll("(\uffe5|,|\\$|\u20ac| )", "");
                    }
                    LargeExcelFileReaderUtil.this.rowData.put((String)title.get("fieldName"), formattedValue);
                }
            }
        }, dataFormatter, false);
        parser.setContentHandler((ContentHandler)handler);
        parser.parse(new InputSource(sheetInputStream));
    }

    private String getCellValue(Cell cell, Object valueType) {
        String cellValue = "";
        if (null != cell) {
            switch (cell.getCellType()) {
                case NUMERIC: {
                    if (DateUtil.isCellDateFormatted((Cell)cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        cellValue = formater.format(date);
                        if (!valueType.equals("DATE")) break;
                        cellValue = cellValue.split(" ")[0];
                        break;
                    }
                    cellValue = Double.toString(cell.getNumericCellValue());
                    break;
                }
                case STRING: {
                    cellValue = cell.getStringCellValue();
                    break;
                }
                case BOOLEAN: {
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                }
                case FORMULA: {
                    cellValue = cell.getCellFormula() + "";
                    break;
                }
                case BLANK: {
                    cellValue = "";
                    break;
                }
                case ERROR: {
                    cellValue = "\u975e\u6cd5\u5b57\u7b26";
                    break;
                }
                default: {
                    cellValue = "\u672a\u77e5\u7c7b\u578b";
                }
            }
        }
        return cellValue;
    }

    public List<Map<String, Object>> getMyDataList(List<Map<String, Object>> titles) throws Exception {
        return this.dataList;
    }
}


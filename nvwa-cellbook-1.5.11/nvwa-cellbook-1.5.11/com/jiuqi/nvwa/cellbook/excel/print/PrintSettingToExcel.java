/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Footer
 *  org.apache.poi.ss.usermodel.Header
 *  org.apache.poi.ss.usermodel.PrintSetup
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.apache.poi.xssf.usermodel.XSSFHeaderFooterProperties
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.excel.print.Font;
import com.jiuqi.nvwa.cellbook.excel.print.FontText;
import com.jiuqi.nvwa.cellbook.excel.print.HeaderFooter;
import com.jiuqi.nvwa.cellbook.excel.print.HeaderFooterSetting;
import com.jiuqi.nvwa.cellbook.excel.print.Margin;
import com.jiuqi.nvwa.cellbook.excel.print.PageStart;
import com.jiuqi.nvwa.cellbook.excel.print.PrintSetting;
import com.jiuqi.nvwa.cellbook.excel.print.Zoom;
import com.jiuqi.nvwa.cellbook.excel.print.ZoomMode;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFHeaderFooterProperties;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class PrintSettingToExcel {
    public static void loadPrintSetting(Workbook workbook, PrintSetting printSetting) {
        for (int i = 0; i < workbook.getNumberOfSheets(); ++i) {
            PrintSettingToExcel.loadPrintSetting(workbook.getSheetAt(i), printSetting);
        }
    }

    public static void loadPrintSetting(Sheet sheet, PrintSetting printSetting) {
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(printSetting.isLandscape());
        PrintSettingToExcel.setZoom(sheet, printSetup, printSetting.getZoom());
        printSetup.setPaperSize((short)printSetting.getPaperSize().value());
        PrintSettingToExcel.setStartPage(printSetup, printSetting.getPageStart());
        PrintSettingToExcel.setMargin(sheet, printSetting.getMargin());
        sheet.setHorizontallyCenter(printSetting.isHorzCenter());
        sheet.setVerticallyCenter(printSetting.isVertCenter());
        PrintSettingToExcel.setHeaderAndFooter(sheet, printSetting.getHeaderFooterSetting());
        PrintSettingToExcel.setRepeatRowsAndCols(sheet, printSetting);
        sheet.setPrintGridlines(printSetting.isPrintGridlines());
        sheet.setPrintRowAndColumnHeadings(printSetting.isPrintRowAndColumnHeadings());
        printSetup.setNoColor(printSetting.isNoColor());
        printSetup.setLeftToRight(printSetting.isLeftToRight());
        PrintSettingToExcel.setPrintRange(sheet, printSetting);
        PrintSettingToExcel.setBreaks(sheet, printSetting);
    }

    private static void setStartPage(PrintSetup printSetup, PageStart start) {
        if (start.isEnable()) {
            printSetup.setUsePage(true);
            printSetup.setPageStart((short)start.getPageNum());
        }
    }

    private static void setRepeatRowsAndCols(Sheet sheet, PrintSetting printSetting) {
        if (printSetting.getRepeatingRowStart() > 0 && printSetting.getRepeatingRowEnd() > 0) {
            sheet.setRepeatingRows(new CellRangeAddress(printSetting.getRepeatingRowStart() - 1, printSetting.getRepeatingRowEnd() - 1, printSetting.getRepeatingColStart() - 1, printSetting.getRepeatingColEnd() - 1));
        }
        if (printSetting.getRepeatingColStart() > 0 && printSetting.getRepeatingColEnd() > 0) {
            sheet.setRepeatingColumns(new CellRangeAddress(printSetting.getRepeatingRowStart() - 1, printSetting.getRepeatingRowEnd() - 1, printSetting.getRepeatingColStart() - 1, printSetting.getRepeatingColEnd() - 1));
        }
    }

    private static void setPrintRange(Sheet sheet, PrintSetting printSetting) {
        int totalRows = sheet.getPhysicalNumberOfRows();
        if (totalRows == 0) {
            return;
        }
        int totalCols = sheet.getRow(0).getPhysicalNumberOfCells();
        if (totalCols == 0) {
            return;
        }
        if (printSetting.getHideRows().isEmpty() && printSetting.getHideCols().isEmpty()) {
            return;
        }
        for (Integer row : printSetting.getHideRows()) {
            Row row1 = sheet.getRow(row - 1);
            if (row1 == null) continue;
            row1.getRowStyle().setHidden(true);
        }
        for (Integer col : printSetting.getHideCols()) {
            if (totalCols < col) continue;
            sheet.setColumnHidden(col - 1, true);
        }
    }

    private static void setBreaks(Sheet sheet, PrintSetting printSetting) {
        for (Integer col : printSetting.getBreakCols()) {
            sheet.setColumnBreak(col - 2);
        }
        for (Integer row : printSetting.getBreakRows()) {
            sheet.setRowBreak(row - 2);
        }
    }

    private static void setMargin(Sheet sheet, Margin margin) {
        double CM_TO_INCH = 0.3937008;
        sheet.setMargin((short)4, margin.getHeader() * CM_TO_INCH);
        sheet.setMargin((short)2, margin.getTop() * CM_TO_INCH);
        sheet.setMargin((short)0, margin.getLeft() * CM_TO_INCH);
        sheet.setMargin((short)1, margin.getRight() * CM_TO_INCH);
        sheet.setMargin((short)3, margin.getBottom() * CM_TO_INCH);
        sheet.setMargin((short)5, margin.getFooter() * CM_TO_INCH);
    }

    private static void setZoom(Sheet sheet, PrintSetup printSetup, Zoom zoom) {
        if (zoom.getId().equals("NONE")) {
            printSetup.setScale((short)100);
        } else if (zoom.getId().equals("FIT_ROWS")) {
            printSetup.setFitWidth((short)0);
            printSetup.setFitHeight((short)1);
            sheet.setAutobreaks(true);
            sheet.setFitToPage(true);
        } else if (zoom.getId().equals("FIT_COLS")) {
            printSetup.setFitWidth((short)1);
            printSetup.setFitHeight((short)0);
            sheet.setAutobreaks(true);
            sheet.setFitToPage(true);
        } else if (zoom.getId().equals("FIT_PAGE")) {
            printSetup.setFitWidth((short)1);
            printSetup.setFitHeight((short)1);
            sheet.setAutobreaks(true);
            sheet.setFitToPage(true);
        } else if (zoom.getId().equals("CUSTOM")) {
            if (zoom.getMode() == ZoomMode.FIT) {
                printSetup.setFitWidth((short)zoom.getFitWeight());
                printSetup.setFitHeight((short)zoom.getFitHeight());
                sheet.setAutobreaks(true);
                sheet.setFitToPage(true);
            } else if (zoom.getMode() == ZoomMode.SCALE) {
                printSetup.setScale((short)zoom.getScale());
            }
        } else if (zoom.getMode() == ZoomMode.FIT) {
            printSetup.setFitWidth((short)zoom.getFitWeight());
            printSetup.setFitHeight((short)zoom.getFitHeight());
            sheet.setAutobreaks(true);
            sheet.setFitToPage(true);
        } else if (zoom.getMode() == ZoomMode.SCALE) {
            printSetup.setScale((short)zoom.getScale());
        }
    }

    private static void setHeaderAndFooter(Sheet sheet, HeaderFooterSetting headerFooterSetting) {
        HeaderFooter headerFooter = headerFooterSetting.getHeaderFooter();
        if (headerFooter != null) {
            if (headerFooter.getHeader() != null) {
                Header header = sheet.getHeader();
                header.setLeft(PrintSettingToExcel.getFontTextForHeaderOrFooter(headerFooter.getHeader().getLeft()));
                header.setCenter(PrintSettingToExcel.getFontTextForHeaderOrFooter(headerFooter.getHeader().getCenter()));
                header.setRight(PrintSettingToExcel.getFontTextForHeaderOrFooter(headerFooter.getHeader().getRight()));
            }
            if (headerFooter.getFooter() != null) {
                Footer footer = sheet.getFooter();
                footer.setLeft(PrintSettingToExcel.getFontTextForHeaderOrFooter(headerFooter.getFooter().getLeft()));
                footer.setCenter(PrintSettingToExcel.getFontTextForHeaderOrFooter(headerFooter.getFooter().getCenter()));
                footer.setRight(PrintSettingToExcel.getFontTextForHeaderOrFooter(headerFooter.getFooter().getRight()));
            }
        }
        if (sheet instanceof XSSFSheet) {
            XSSFSheet xssfSheet = (XSSFSheet)sheet;
            XSSFHeaderFooterProperties properties = xssfSheet.getHeaderFooterProperties();
            properties.setAlignWithMargins(headerFooterSetting.isAlignWithMargins());
            properties.setScaleWithDoc(headerFooterSetting.isScaleWithDoc());
        }
    }

    private static String getFontTextForHeaderOrFooter(FontText fontText) {
        Font font = fontText.getFont();
        if (font == null) {
            return "&\"\u5b8b\u4f53\"" + fontText.getText();
        }
        String str = "";
        if (StringUtils.isNotEmpty(font.getFamily())) {
            str = str + "&\"" + font.getFamily() + "\"";
        }
        if (font.isItalic()) {
            str = str + "&I";
        }
        if (font.isBold()) {
            str = str + "&B";
        }
        if (font.getSize() != 0.0f) {
            str = str + "&" + (int)font.getSize();
        }
        if (font.isUnderline()) {
            str = str + "&U";
        }
        if (StringUtils.isNotEmpty(font.getColor())) {
            str = str + "&K" + font.getColor().replaceAll("#", "");
        }
        str = str + fontText.getText();
        return str;
    }
}


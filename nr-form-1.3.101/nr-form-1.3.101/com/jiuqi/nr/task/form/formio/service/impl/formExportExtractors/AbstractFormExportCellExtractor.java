/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.form.formio.service.IFormExportCellExtractor;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class AbstractFormExportCellExtractor
implements IFormExportCellExtractor {
    private final Map<String, IEntityModel> entityModelMap = new HashMap<String, IEntityModel>();

    protected IEntityModel getModel(String formKey, IDesignTimeViewController designTimeViewController, IEntityMetaService entityMetaService) {
        IEntityModel model = this.entityModelMap.get(formKey);
        if (model != null) {
            return model;
        }
        DesignFormDefine form = designTimeViewController.getForm(formKey);
        DesignFormSchemeDefine formScheme = designTimeViewController.getFormScheme(form.getFormScheme());
        DesignTaskDefine task = designTimeViewController.getTask(formScheme.getTaskKey());
        IEntityModel entityModel = entityMetaService.getEntityModel(task.getDw());
        this.entityModelMap.put(formKey, entityModel);
        return entityModel;
    }

    protected void setDefaultStyle(GridCellData cellData, XSSFCell cell, XSSFSheet sheet, XSSFWorkbook workbook) {
        this.setCellRange(cellData, sheet, cell.getRowIndex() + 1, cell.getColumnIndex() + 1);
        XSSFCellStyle style = new XSSFCellStyle(workbook.getStylesSource());
        this.setBorderStyle(style, cellData);
        XSSFFont font = workbook.createFont();
        this.setFontStyle(font, cellData);
        style.setFont(font);
        cell.setCellStyle(style);
    }

    protected void setCellRange(GridCellData cellData, XSSFSheet sheet, int row, int col) {
        int colSpan = cellData.getColSpan();
        int rowSpan = cellData.getRowSpan();
        CellRangeAddress region = null;
        if (colSpan > 1 && rowSpan == 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1 + colSpan - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        } else if (rowSpan > 1 && colSpan == 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1 + rowSpan - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        } else if (colSpan > 1 && rowSpan > 1) {
            int needRangeRow4Begin = row - 1;
            int needRangeRow4Last = row - 1 + rowSpan - 1;
            int needRangeCol4Begin = col - 1;
            int needRangeCol4Last = col - 1 + colSpan - 1;
            region = new CellRangeAddress(needRangeRow4Begin, needRangeRow4Last, needRangeCol4Begin, needRangeCol4Last);
        }
        if (region != null) {
            sheet.addMergedRegion(region);
        }
    }

    protected void setFontStyle(XSSFFont font, GridCellData cellData) {
        XSSFColor fontColor = new XSSFColor(AbstractFormExportCellExtractor.hex2Rgb(AbstractFormExportCellExtractor.intToHtmlColor(cellData.getForeGroundColor(), "#000000")), (IndexedColorMap)new DefaultIndexedColorMap());
        byte[] wRgb = new byte[]{-1, -1, -1};
        String savedRgbStr = new String(fontColor.getRGB());
        String wRgbStr = new String(wRgb);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        if (!savedRgbStr.equals(wRgbStr)) {
            font.setColor(fontColor);
        }
        int fontStyle = cellData.getFontStyle();
        font.setFontName(cellData.getFontName());
        font.setFontHeightInPoints((short)(cellData.getFontSize() * 72 / 96));
        if ((2 & fontStyle) == 2) {
            font.setBold(true);
        } else if ((4 & fontStyle) == 4) {
            font.setItalic(true);
        } else if ((8 & fontStyle) == 8) {
            font.setUnderline((byte)1);
        } else if ((0x10 & fontStyle) == 16) {
            font.setStrikeout(true);
        }
    }

    protected void setBorderStyle(XSSFCellStyle style, GridCellData cellData) {
        int vertAlign = cellData.getVertAlign();
        int horzAlign = cellData.getHorzAlign();
        int backGroundStyle = cellData.getBackGroundStyle();
        XSSFColor backStyleColor = new XSSFColor(AbstractFormExportCellExtractor.hex2Rgb("#C8CBCC"), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor backGroundColor = new XSSFColor(AbstractFormExportCellExtractor.hex2Rgb(AbstractFormExportCellExtractor.intToHtmlColor(cellData.getBackGroundColor(), "#ffffff")), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor bottomBorderColor = new XSSFColor(new Color(cellData.getBottomBorderColor()), (IndexedColorMap)new DefaultIndexedColorMap());
        XSSFColor rightBorderColor = new XSSFColor(new Color(cellData.getRightBorderColor()), (IndexedColorMap)new DefaultIndexedColorMap());
        BorderStyle excelBottomBorderStyle = this.formBorder2ExcelBorder(cellData.getBottomBorderStyle());
        BorderStyle excelRightBorderStyle = this.formBorder2ExcelBorder(cellData.getRightBorderStyle());
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(excelBottomBorderStyle);
        style.setBorderRight(excelRightBorderStyle);
        XSSFColor deaultBorderColor = new XSSFColor(new Color(217, 217, 217), (IndexedColorMap)new DefaultIndexedColorMap());
        if (cellData.getBottomBorderColor() < 0) {
            style.setBottomBorderColor(deaultBorderColor);
        } else {
            style.setBottomBorderColor(bottomBorderColor);
        }
        if (cellData.getRightBorderColor() < 0) {
            style.setRightBorderColor(deaultBorderColor);
        } else {
            style.setRightBorderColor(rightBorderColor);
        }
        if (cellData.isVertText()) {
            style.setRotation((short)255);
        }
        if (cellData.isWrapLine()) {
            style.setWrapText(true);
        }
        style.setIndention((short)cellData.getIndent());
        if (cellData.isEditable()) {
            style.setLocked(true);
        }
        if (horzAlign == 1) {
            style.setAlignment(HorizontalAlignment.forInt(1));
        } else if (horzAlign == 3) {
            style.setAlignment(HorizontalAlignment.forInt(2));
        } else if (horzAlign == 2) {
            style.setAlignment(HorizontalAlignment.forInt(3));
        }
        if (vertAlign == 1) {
            style.setVerticalAlignment(VerticalAlignment.forInt(0));
        } else if (vertAlign == 3) {
            style.setVerticalAlignment(VerticalAlignment.forInt(1));
        } else if (vertAlign == 2) {
            style.setVerticalAlignment(VerticalAlignment.forInt(2));
        }
        if (backGroundStyle == 0) {
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            XSSFColor silverBackGroundColor = new XSSFColor(new Color(232, 232, 232), (IndexedColorMap)new DefaultIndexedColorMap());
            byte[] wRgb = new byte[]{0, 0, 0};
            String savedRgbStr = new String(backGroundColor.getRGB());
            String wRgbStr = new String(wRgb);
            boolean silverHead = cellData.isSilverHead();
            if (savedRgbStr.equals(wRgbStr) && silverHead) {
                style.setFillForegroundColor(silverBackGroundColor);
            } else {
                style.setFillForegroundColor(backGroundColor);
            }
        } else if (backGroundStyle == 11) {
            style.setFillPattern(FillPatternType.THIN_HORZ_BANDS);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 8) {
            style.setFillPattern(FillPatternType.THIN_VERT_BANDS);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 10) {
            style.setFillPattern(FillPatternType.THIN_BACKWARD_DIAG);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 9) {
            style.setFillPattern(FillPatternType.THIN_FORWARD_DIAG);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 4) {
            style.setFillPattern(FillPatternType.SQUARES);
            style.setFillForegroundColor(backStyleColor);
        } else if (backGroundStyle == 5) {
            style.setFillPattern(FillPatternType.DIAMONDS);
            style.setFillForegroundColor(backStyleColor);
        }
    }

    private static Color hex2Rgb(String colorStr) {
        return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    private BorderStyle formBorder2ExcelBorder(int formBottomBorderStyle) {
        switch (formBottomBorderStyle) {
            case -1: {
                return BorderStyle.THIN;
            }
            case 0: {
                return BorderStyle.NONE;
            }
            case 1: {
                return BorderStyle.THIN;
            }
            case 2: {
                return BorderStyle.DOTTED;
            }
            case 4: {
                return BorderStyle.MEDIUM;
            }
            case 8: {
                return BorderStyle.DOUBLE;
            }
        }
        return BorderStyle.THIN;
    }

    private static String intToHtmlColor(int color, String defaultColor) {
        if (color < 0) {
            if (StringUtils.isNotEmpty((String)defaultColor)) {
                return defaultColor;
            }
            return "#000000";
        }
        return "#" + StringUtils.leftPad((String)Integer.toHexString(color), (int)6, (String)"0");
    }
}


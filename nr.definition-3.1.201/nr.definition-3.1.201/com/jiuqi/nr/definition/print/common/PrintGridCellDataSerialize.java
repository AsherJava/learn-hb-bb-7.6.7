/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBackGroundImageStyle
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 */
package com.jiuqi.nr.definition.print.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.io.IOException;

public class PrintGridCellDataSerialize
extends GridCellDataSerialize {
    protected static final String PROPERTY_SHOW_TEXT = "showText";
    protected static final String PROPERTY_EDIT_TEXT = "editText";
    protected static final String PROPERTY_COL_INDEX = "colIndex";
    protected static final String PROPERTY_ROW_INDEX = "rowIndex";
    protected static final String PROPERTY_HTML = "html";
    protected static final String PROPERTY_CHECKED = "checked";
    protected static final String PROPERTY_CHECKABLE = "checkable";
    protected static final String PROPERTY_EXPANDABLE = "expandable";
    protected static final String PROPERTY_EXPANDED = "expanded";
    protected static final String PROPERTY_DEPTH = "depth";
    protected static final String PROPERTY_ISTREEEND = "isTreeEnd";
    protected static final String PROPERTY_MERGED = "isMerged";
    protected static final String PROPERTY_CLIENT_DATA = "clientData";
    protected static final String PROPERTY_CELL_MODE = "cellMode";

    public void serialize(GridCellData cell, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(PROPERTY_COL_INDEX, cell.getColIndex());
        gen.writeNumberField(PROPERTY_ROW_INDEX, cell.getRowIndex());
        gen.writeStringField(PROPERTY_SHOW_TEXT, cell.getShowText());
        gen.writeStringField(PROPERTY_EDIT_TEXT, cell.getEditText());
        gen.writeBooleanField("selectable", cell.isSelectable());
        gen.writeBooleanField("editable", cell.isEditable());
        gen.writeNumberField(PROPERTY_CELL_MODE, cell.getCellMode());
        gen.writeNumberField("rowSpan", cell.getRowSpan());
        gen.writeNumberField("colSpan", cell.getColSpan());
        gen.writeNumberField("dataType", cell.getDataType());
        if (cell.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.HotLink)) {
            gen.writeStringField(PROPERTY_HTML, cell.getShowText());
        }
        gen.writeBooleanField(PROPERTY_CHECKED, cell.getChecked());
        gen.writeBooleanField(PROPERTY_CHECKABLE, cell.isCheckable());
        gen.writeBooleanField(PROPERTY_EXPANDABLE, cell.isExpandable());
        gen.writeBooleanField(PROPERTY_EXPANDED, cell.isExpended());
        gen.writeNumberField(PROPERTY_DEPTH, 0);
        if (cell.hasExData()) {
            gen.writeStringField(PROPERTY_CLIENT_DATA, cell.getDataExString());
        }
        if (cell.isTreeEnd()) {
            gen.writeBooleanField(PROPERTY_ISTREEEND, cell.isTreeEnd());
        }
        if (cell.isSilverHead()) {
            gen.writeBooleanField("silverHead", cell.isSilverHead());
            gen.writeStringField("backColor", "#f1f1f1");
        } else {
            gen.writeStringField("backColor", PrintGridCellDataSerialize.intToHtmlColor(cell.getBackGroundColor(), "#ffffff"));
        }
        gen.writeNumberField("backStyle", cell.getBackGroundStyle());
        gen.writeNumberField("backImageStyle", cell.getBackgroundImageStyle());
        if (GridEnums.GridBackGroundImageStyle.POSITION.getValue() == cell.getBackgroundImageStyle()) {
            gen.writeNumberField("backImageHorizion", cell.getHorizon().intValue());
            gen.writeNumberField("backImageVertical", cell.getVertAlign());
        } else if (GridEnums.GridBackGroundImageStyle.BOUNDS.getValue() == cell.getBackgroundImageStyle()) {
            gen.writeStringField("backImageBounds", cell.getCellStyleData().getBackImageBounds());
        }
        ImageDescriptor backImage = cell.getBackImage();
        if (null != backImage) {
            gen.writeStringField("backImageUri", backImage.getURI());
            ImageData imageDate = backImage.getImageData();
            if (null != imageDate) {
                gen.writeStringField("backImage", new String(imageDate.getRealBytes(), "UTF-8"));
            }
        }
        gen.writeBooleanField(PROPERTY_MERGED, cell.isMerged());
        gen.writeBooleanField("fitFontSize", cell.isFitFontSize());
        gen.writeFieldName("border");
        gen.writeStartArray();
        gen.writeNumber(cell.getRightBorderStyle());
        gen.writeNumber(cell.getBottomBorderStyle());
        gen.writeNumber(cell.getDiagonalBorderStyle());
        gen.writeNumber(cell.getInverseDiagonalBorderStyle());
        gen.writeEndArray();
        gen.writeFieldName("borderColor");
        gen.writeStartArray();
        gen.writeString(PrintGridCellDataSerialize.intToHtmlColor(cell.getRightBorderColor(), ""));
        gen.writeString(PrintGridCellDataSerialize.intToHtmlColor(cell.getBottomBorderColor(), ""));
        if (0 != cell.getDiagonalBorderStyle()) {
            gen.writeString(PrintGridCellDataSerialize.intToHtmlColor(cell.getDiagonalBorderColor(), ""));
        }
        if (0 != cell.getInverseDiagonalBorderStyle()) {
            gen.writeString(PrintGridCellDataSerialize.intToHtmlColor(cell.getInverseDiagonalBorderColor(), ""));
        }
        gen.writeEndArray();
        gen.writeStringField("fontName", cell.getFontName());
        gen.writeNumberField("fontSize", cell.getFontSize());
        int fontStyle = cell.getFontStyle();
        gen.writeBooleanField("fontBold", (fontStyle & 2) > 0);
        gen.writeBooleanField("fontItalic", (fontStyle & 4) > 0);
        if ((fontStyle & 8) > 0) {
            gen.writeNumberField("decoration", 1);
        }
        if ((fontStyle & 0x10) > 0) {
            gen.writeNumberField("decoration", 2);
        }
        gen.writeStringField("fontColor", PrintGridCellDataSerialize.intToHtmlColor(cell.getForeGroundColor(), ""));
        gen.writeNullField("textStroke");
        gen.writeBooleanField("wrapLine", cell.isWrapLine());
        gen.writeNumberField("indent", cell.getIndent());
        gen.writeNumberField("horzAlign", cell.getHorzAlign());
        gen.writeNumberField("vertAlign", cell.getVertAlign());
        gen.writeBooleanField("vertText", cell.isVertText());
        gen.writeBooleanField("multiLine", cell.isMultiLine());
        this.writePadding(gen);
        gen.writeEndObject();
    }

    private void writePadding(JsonGenerator gen) throws IOException {
        gen.writeFieldName("padding");
        gen.writeStartArray();
        gen.writeNumber(2);
        gen.writeNumber(2);
        gen.writeNumber(0);
        gen.writeNumber(2);
        gen.writeEndArray();
    }

    private static String intToHtmlColor(int color, String defaultColor) {
        if (color < 0) {
            if (color == -1) {
                return "";
            }
            if (PrintGridCellDataSerialize.isNotEmpty(defaultColor)) {
                return defaultColor;
            }
            return "#" + PrintGridCellDataSerialize.leftPad(Integer.toHexString(color & 0xFFFFFF), 6, "0");
        }
        return "#" + PrintGridCellDataSerialize.leftPad(Integer.toHexString(color), 6, "0");
    }

    private static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    private static String leftPad(String str, int size, String delim) {
        if ((size = (size - str.length()) / delim.length()) > 0) {
            str = PrintGridCellDataSerialize.repeat(delim, size) + str;
        }
        return str;
    }

    private static String repeat(String str, int repeat) {
        StringBuilder buffer = new StringBuilder(repeat * str.length());
        for (int i = 0; i < repeat; ++i) {
            buffer.append(str);
        }
        return buffer.toString();
    }
}


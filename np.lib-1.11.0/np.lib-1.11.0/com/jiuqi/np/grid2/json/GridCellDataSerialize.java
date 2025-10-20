/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 */
package com.jiuqi.np.grid2.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.grid2.GridEnums;
import java.io.IOException;

public class GridCellDataSerialize
extends JsonSerializer<GridCellData> {
    public void serialize(GridCellData cell, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("colIndex", cell.getColIndex());
        gen.writeNumberField("rowIndex", cell.getRowIndex());
        gen.writeStringField("editorId", "default");
        gen.writeStringField("showText", cell.getShowText());
        gen.writeStringField("editText", cell.getEditText());
        gen.writeNumberField("dataType", cell.getDataType());
        gen.writeBooleanField("selectable", cell.isSelectable());
        gen.writeBooleanField("editable", cell.isEditable());
        gen.writeNumberField("cellMode", cell.getCellMode());
        gen.writeStringField("html", "");
        gen.writeStringField("control", "");
        gen.writeStringField("treeImage", "");
        gen.writeBooleanField("checked", cell.getChecked());
        gen.writeBooleanField("checkable", cell.isCheckable());
        gen.writeBooleanField("expandable", cell.isExpandable());
        gen.writeBooleanField("expanded", cell.isExpended());
        gen.writeNumberField("depth", 0);
        gen.writeStringField("clientData", cell.getDataExString());
        gen.writeBooleanField("isTreeEnd", cell.isTreeEnd());
        gen.writeNumberField("rowSpan", cell.getRowSpan());
        gen.writeNumberField("colSpan", cell.getColSpan());
        if (cell.isSilverHead()) {
            gen.writeBooleanField("silverHead", cell.isSilverHead());
            gen.writeStringField("backColor", "#F1F1F1");
            gen.writeBooleanField("editable", false);
        } else {
            gen.writeStringField("backColor", GridCellDataSerialize.intToHtmlColor(cell.getBackGroundColor(), "#FFFFFF"));
        }
        gen.writeNumberField("backStyle", cell.getBackGroundStyle());
        gen.writeNumberField("backImage", cell.getBackgroundImageStyle());
        gen.writeBooleanField("isMerged", cell.isMerged());
        gen.writeBooleanField("fitFontSize", cell.isFitFontSize());
        gen.writeFieldName("border");
        gen.writeStartArray();
        if (cell.getRowIndex() == 0 || cell.getColIndex() == 0) {
            gen.writeNumber(GridEnums.GridBorderStyle.AUTO.getStyle());
            gen.writeNumber(GridEnums.GridBorderStyle.AUTO.getStyle());
        } else {
            gen.writeNumber(cell.getRightBorderStyle());
            gen.writeNumber(cell.getBottomBorderStyle());
            gen.writeNumber(cell.getDiagonalBorderStyle());
            gen.writeNumber(cell.getInverseDiagonalBorderStyle());
        }
        gen.writeEndArray();
        gen.writeFieldName("borderColor");
        gen.writeStartArray();
        gen.writeString(GridCellDataSerialize.intToHtmlColor(cell.getRightBorderColor(), ""));
        gen.writeString(GridCellDataSerialize.intToHtmlColor(cell.getBottomBorderColor(), ""));
        if (0 != cell.getDiagonalBorderStyle()) {
            gen.writeString(GridCellDataSerialize.intToHtmlColor(cell.getDiagonalBorderColor(), ""));
        }
        if (0 != cell.getInverseDiagonalBorderStyle()) {
            gen.writeString(GridCellDataSerialize.intToHtmlColor(cell.getInverseDiagonalBorderColor(), ""));
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
        gen.writeStringField("fontColor", GridCellDataSerialize.intToHtmlColor(cell.getForeGroundColor(), ""));
        gen.writeNullField("textStroke");
        gen.writeFieldName("textShadow");
        gen.writeStartObject();
        gen.writeNumberField("offsetX", 0);
        gen.writeNumberField("offsetY", 0);
        gen.writeNumberField("blur", 0);
        gen.writeStringField("color", "#00f");
        gen.writeEndObject();
        gen.writeBooleanField("wrapLine", cell.isWrapLine());
        gen.writeNumberField("indent", cell.getIndent());
        gen.writeNumberField("horzAlign", cell.getHorzAlign());
        gen.writeNumberField("vertAlign", cell.getVertAlign());
        gen.writeBooleanField("vertText", cell.isVertText());
        gen.writeBooleanField("multiLine", cell.isMultiLine());
        gen.writeFieldName("padding");
        gen.writeStartArray();
        gen.writeNumber(2);
        gen.writeNumber(2);
        gen.writeNumber(0);
        gen.writeNumber(2);
        gen.writeEndArray();
        gen.writeEndObject();
    }

    public static String intToHtmlColor(int color, String defaultColor) {
        if (color < 0) {
            if (GridCellDataSerialize.isNotEmpty(defaultColor)) {
                return defaultColor;
            }
            if (color == -1) {
                return "";
            }
            return "#" + GridCellDataSerialize.leftPad(Integer.toHexString(color & 0xFFFFFF), 6, "0");
        }
        return "#" + GridCellDataSerialize.leftPad(Integer.toHexString(color), 6, "0");
    }

    private static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    private static String leftPad(String str, int size, String delim) {
        if ((size = (size - str.length()) / delim.length()) > 0) {
            str = GridCellDataSerialize.repeat(delim, size) + str;
        }
        return str;
    }

    private static String repeat(String str, int repeat) {
        StringBuffer buffer = new StringBuffer(repeat * str.length());
        for (int i = 0; i < repeat; ++i) {
            buffer.append(str);
        }
        return buffer.toString();
    }
}


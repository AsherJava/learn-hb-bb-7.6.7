/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBackGroundImageStyle
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 */
package com.jiuqi.nr.splittable.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.splittable.other.LinkObj;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplitGridCellDataSerialize
extends JsonSerializer<GridCellData> {
    private List<LinkObj> realXYList;

    public SplitGridCellDataSerialize() {
        this(new ArrayList<LinkObj>());
    }

    public SplitGridCellDataSerialize(List<LinkObj> realXYList) {
        this.realXYList = realXYList;
    }

    public void setRealXYList(List<LinkObj> realXYList) {
        this.realXYList = realXYList;
    }

    public void serialize(GridCellData cell, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("colIndex", cell.getColIndex());
        gen.writeNumberField("rowIndex", cell.getRowIndex());
        gen.writeStringField("showText", cell.getShowText());
        gen.writeStringField("editText", cell.getEditText());
        gen.writeBooleanField("selectable", cell.isSelectable());
        gen.writeBooleanField("editable", cell.isEditable());
        gen.writeNumberField("cellMode", cell.getCellMode());
        gen.writeNumberField("rowSpan", cell.getRowSpan());
        gen.writeNumberField("colSpan", cell.getColSpan());
        gen.writeNumberField("dataType", cell.getDataType());
        if (cell.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.HotLink)) {
            gen.writeStringField("html", cell.getShowText());
        }
        gen.writeBooleanField("checked", cell.getChecked());
        gen.writeBooleanField("checkable", cell.isCheckable());
        gen.writeBooleanField("expandable", cell.isExpandable());
        gen.writeBooleanField("expanded", cell.isExpended());
        gen.writeNumberField("depth", 0);
        if (cell.hasExData()) {
            gen.writeStringField("clientData", cell.getDataExString());
        }
        if (cell.isTreeEnd()) {
            gen.writeBooleanField("isTreeEnd", cell.isTreeEnd());
        }
        if (cell.isSilverHead()) {
            gen.writeBooleanField("silverHead", cell.isSilverHead());
            gen.writeStringField("backColor", "#f1f1f1");
            gen.writeBooleanField("editable", false);
        } else {
            gen.writeStringField("backColor", this.intToHtmlColor(cell.getBackGroundColor(), "#ffffff"));
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
        gen.writeString(this.intToHtmlColor(cell.getRightBorderColor(), ""));
        gen.writeString(this.intToHtmlColor(cell.getBottomBorderColor(), ""));
        if (0 != cell.getDiagonalBorderStyle()) {
            gen.writeString(this.intToHtmlColor(cell.getDiagonalBorderColor(), ""));
        }
        if (0 != cell.getInverseDiagonalBorderStyle()) {
            gen.writeString(this.intToHtmlColor(cell.getInverseDiagonalBorderColor(), ""));
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
        gen.writeStringField("fontColor", this.intToHtmlColor(cell.getForeGroundColor(), ""));
        gen.writeNullField("textStroke");
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

    private String intToHtmlColor(int color, String defaultColor) {
        if (color < 0) {
            if (color == -1) {
                return "";
            }
            if (this.isNotEmpty(defaultColor)) {
                return defaultColor;
            }
            return "#" + this.leftPad(Integer.toHexString(color & 0xFFFFFF), 6, "0");
        }
        return "#" + this.leftPad(Integer.toHexString(color), 6, "0");
    }

    private String leftPad(String str, int size, String delim) {
        if ((size = (size - str.length()) / delim.length()) > 0) {
            str = this.repeat(delim, size) + str;
        }
        return str;
    }

    private String repeat(String delim, int size) {
        StringBuffer buffer = new StringBuffer(size * delim.length());
        for (int i = 0; i < size; ++i) {
            buffer.append(delim);
        }
        return buffer.toString();
    }

    private boolean isNotEmpty(String defaultColor) {
        return defaultColor != null && defaultColor.length() > 0;
    }

    public Class<GridCellData> handledType() {
        return GridCellData.class;
    }
}


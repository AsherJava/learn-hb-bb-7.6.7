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
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.gcreport.bde.fetchsetting.impl.config.Grid2DataConst;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import java.io.IOException;

public class BdeFetchSettingGridCellDataSerialize
extends JsonSerializer<GridCellData> {
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
        } else {
            gen.writeStringField("backColor", Grid2DataConst.intToHtmlColor(cell.getBackGroundColor(), "#ffffff"));
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
        gen.writeNumber(cell.getRightBorderStyle());
        gen.writeNumber(cell.getBottomBorderStyle());
        gen.writeNumber(cell.getDiagonalBorderStyle());
        gen.writeNumber(cell.getInverseDiagonalBorderStyle());
        gen.writeEndArray();
        gen.writeFieldName("borderColor");
        gen.writeStartArray();
        gen.writeString(Grid2DataConst.intToHtmlColor(cell.getRightBorderColor(), ""));
        gen.writeString(Grid2DataConst.intToHtmlColor(cell.getBottomBorderColor(), ""));
        if (0 != cell.getDiagonalBorderStyle()) {
            gen.writeString(Grid2DataConst.intToHtmlColor(cell.getDiagonalBorderColor(), ""));
        }
        if (0 != cell.getInverseDiagonalBorderStyle()) {
            gen.writeString(Grid2DataConst.intToHtmlColor(cell.getInverseDiagonalBorderColor(), ""));
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
        gen.writeStringField("fontColor", this.buildFontColor(cell.getForeGroundColor()));
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

    public Class<GridCellData> handledType() {
        return GridCellData.class;
    }

    private String buildFontColor(int foreGroundColor) {
        if (254 == foreGroundColor) {
            return "#2653F4";
        }
        if (253 == foreGroundColor) {
            return "#42424280";
        }
        if (255 == foreGroundColor) {
            return "#00ADE4";
        }
        return Grid2DataConst.intToHtmlColor(foreGroundColor, "");
    }
}


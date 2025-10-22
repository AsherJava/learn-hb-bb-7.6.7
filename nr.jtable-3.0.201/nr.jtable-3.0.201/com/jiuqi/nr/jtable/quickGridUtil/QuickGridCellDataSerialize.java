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
 *  com.jiuqi.nvwa.grid2.graphics.Font
 *  com.jiuqi.nvwa.grid2.json.DefaultGridCellOptions
 */
package com.jiuqi.nr.jtable.quickGridUtil;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.jtable.quickGridUtil.Grid2DataConst;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.Font;
import com.jiuqi.nvwa.grid2.json.DefaultGridCellOptions;
import java.io.IOException;
import java.util.Arrays;

public class QuickGridCellDataSerialize
extends JsonSerializer<GridCellData> {
    static final int[] BORDER_STYLE = new int[]{-1, -1};
    static final int[] BORDER_COLOR = new int[]{-1, -1};
    private DefaultGridCellOptions options;

    public QuickGridCellDataSerialize() {
        this.options = new DefaultGridCellOptions();
    }

    public QuickGridCellDataSerialize(DefaultGridCellOptions options) {
        this.options = options;
    }

    public void serialize(GridCellData cell, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String fontcolor;
        int fontStyle;
        Font font;
        gen.writeStartObject();
        gen.writeNumberField("colIndex", cell.getColIndex());
        gen.writeNumberField("rowIndex", cell.getRowIndex());
        gen.writeStringField("showText", cell.getShowText());
        gen.writeStringField("editText", cell.getEditText());
        gen.writeNumberField("cellMode", cell.getCellMode());
        gen.writeNumberField("rowSpan", cell.getRowSpan());
        gen.writeNumberField("colSpan", cell.getColSpan());
        if (0 != cell.getDataType()) {
            gen.writeNumberField("dataType", cell.getDataType());
        }
        if (cell.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.HotLink)) {
            gen.writeStringField("html", cell.getShowText());
        }
        if (cell.isMerged()) {
            gen.writeBooleanField("isMerged", cell.isMerged());
        }
        if (-1 != cell.getBackGroundColor()) {
            gen.writeStringField("backColor", Grid2DataConst.intToHtmlColor(cell.getBackGroundColor(), "#ffffff"));
        }
        if (cell.getBackGroundStyle() != 0 && cell.getBackGroundStyle() != 1) {
            gen.writeNumberField("backStyle", cell.getBackGroundStyle());
        }
        if (cell.getBackgroundImageStyle() != 0) {
            gen.writeNumberField("backImage", cell.getBackgroundImageStyle());
        }
        if (cell.getRowIndex() == 0 || cell.getColIndex() == 0) {
            gen.writeFieldName("border");
            gen.writeArray(this.options.getHeadBorderStyle(), 0, 2);
        } else {
            if (!Arrays.equals(cell.getBorderStyle(), this.options.getBorderStyle())) {
                gen.writeFieldName("border");
                gen.writeArray(cell.getBorderStyle(), 0, 2);
            }
            if (!Arrays.equals(cell.getBorderColor(), this.options.getBorderColor())) {
                gen.writeFieldName("borderColor");
                gen.writeStartArray();
                gen.writeString(Grid2DataConst.intToHtmlColor(cell.getRightBorderColor(), ""));
                gen.writeString(Grid2DataConst.intToHtmlColor(cell.getBottomBorderColor(), ""));
                gen.writeEndArray();
            }
        }
        if (cell.isFitFontSize()) {
            gen.writeBooleanField("fitFontSize", cell.isFitFontSize());
        }
        if (null != cell.getFontName() && !cell.getFontName().equals(this.options.getFont().getName())) {
            gen.writeStringField("fontName", cell.getFontName());
        }
        if (!(font = cell.getFont()).equals((Object)this.options.getFont())) {
            gen.writeNumberField("fontSize", font.getSize());
        }
        gen.writeBooleanField("fontItalic", ((fontStyle = cell.getFontStyle()) & 4) > 0);
        gen.writeBooleanField("fontBold", (fontStyle & 2) > 0);
        if ((fontStyle & 8) > 0) {
            gen.writeNumberField("decoration", 1);
        }
        if ((fontStyle & 0x10) > 0) {
            gen.writeNumberField("decoration", 2);
        }
        if (null != (fontcolor = Grid2DataConst.intToHtmlColor(cell.getForeGroundColor(), "")) && !fontcolor.isEmpty() && !fontcolor.equals("#000000")) {
            gen.writeStringField("fontColor", Grid2DataConst.intToHtmlColor(cell.getForeGroundColor(), ""));
        }
        if (cell.isWrapLine()) {
            gen.writeBooleanField("wrapLine", cell.isWrapLine());
        }
        if (cell.getIndent() != 0) {
            gen.writeNumberField("indent", cell.getIndent());
        }
        if (cell.getHorzAlign() != 0) {
            gen.writeNumberField("horzAlign", cell.getHorzAlign());
        }
        if (cell.getVertAlign() != 0) {
            gen.writeNumberField("vertAlign", cell.getVertAlign());
        }
        if (cell.isMultiLine()) {
            gen.writeBooleanField("multiLine", cell.isMultiLine());
        }
        gen.writeEndObject();
    }

    public Class<GridCellData> handledType() {
        return GridCellData.class;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBackGroundImageStyle
 *  com.jiuqi.nvwa.grid2.GridEnums$GridBorderStyle
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 *  com.jiuqi.nvwa.grid2.json.DefaultGridOptions
 */
package com.jiuqi.nr.splittable.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nr.splittable.bean.CellObj;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import com.jiuqi.nvwa.grid2.json.DefaultGridOptions;
import java.io.IOException;
import java.util.List;

public class SplitGrid2DataSerialize
extends JsonSerializer<Grid2Data> {
    private DefaultGridOptions options;
    private List<CellObj> realXYList;

    public SplitGrid2DataSerialize() {
        this.options = new DefaultGridOptions();
    }

    public SplitGrid2DataSerialize(DefaultGridOptions options) {
        this.options = options;
    }

    public SplitGrid2DataSerialize(List<CellObj> realXYList) {
        this.realXYList = realXYList;
        this.options = new DefaultGridOptions();
    }

    public void serialize(Grid2Data value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        int i;
        gen.writeStartObject();
        gen.writeFieldName("options");
        gen.writeStartObject();
        gen.writeNumberField("loadMode", this.options.getLoadMode());
        gen.writeBooleanField("loadByRow", this.options.isLoadByRow());
        gen.writeNumberField("selectionMode", this.options.getSelectionMode());
        gen.writeNumberField("editMode", this.options.getEditMode());
        gen.writeNumberField("enterNext", this.options.getEnterNext());
        gen.writeBooleanField("rowSelectable", this.options.isRowSelectable());
        gen.writeBooleanField("colSelectable", this.options.isColSelectable());
        gen.writeBooleanField("ignoreHidden", this.options.isIgnoreHidden());
        gen.writeBooleanField("colResizeable", this.options.isColResizeable());
        gen.writeBooleanField("rowResizeable", this.options.isRowResizeable());
        gen.writeBooleanField("colFreeResizeable", this.options.isColFreeResizeable());
        gen.writeBooleanField("rowFreeResizeable", this.options.isRowFreeResizeable());
        gen.writeBooleanField("colGrabable", this.options.isColGrabable());
        gen.writeBooleanField("showSelectionBorder", value.isShowSelectingBorder());
        gen.writeBooleanField("currentCellBorderHidden", this.options.isCurrentCellBorderHidden());
        gen.writeBooleanField("colExchangeable", this.options.isColExchangeable());
        gen.writeBooleanField("passReadOnly", this.options.isPassReadOnly());
        gen.writeBooleanField("showSelectionChange", this.options.isShowSelectionChange());
        gen.writeStringField("selectionColor", this.options.getSelectionColor());
        gen.writeStringField("selectionBorderColor", this.options.getSelectionBorderColor());
        gen.writeNumberField("selectionBorderWidth", this.options.getSelectionBorderWidth());
        gen.writeStringField("currentCellColor", this.options.getCurrentCellColor());
        gen.writeStringField("currentCellBorderColor", this.options.getCurrentCellBorderColor());
        gen.writeNumberField("mergeCellShowMode", this.options.getMergeCellShowMode());
        gen.writeBooleanField("showMergeChildBorder", this.options.isShowMergechildBorder());
        gen.writeEndObject();
        gen.writeFieldName("cells");
        gen.writeStartObject();
        gen.writeFieldName("rowList");
        gen.writeStartArray();
        for (int r = 0; r < value.getRowCount(); ++r) {
            gen.writeStartArray();
            for (int c = 0; c < value.getColumnCount(); ++c) {
                GridCellData cell = value.getGridCellData(c, r);
                gen.writeStartObject();
                if (this.realXYList.size() == 0) {
                    gen.writeNumberField("realX", cell.getRowIndex());
                    gen.writeNumberField("realY", cell.getColIndex());
                } else if (r == 0 || c == 0) {
                    gen.writeNumberField("realX", r);
                    gen.writeNumberField("realY", c);
                } else {
                    CellObj cellObj = this.realXYList.get((r - 1) * (value.getColumnCount() - 1) + c - 1);
                    gen.writeNumberField("realX", cellObj.getPosY());
                    gen.writeNumberField("realY", cellObj.getPosX());
                }
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
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeEndObject();
        gen.writeFieldName("mergeCells");
        gen.writeStartArray();
        for (i = 0; i < value.merges().count(); ++i) {
            Grid2CellField grid2CellField = value.merges().get(i);
            gen.writeStartObject();
            gen.writeNumberField("row", grid2CellField.top);
            gen.writeNumberField("col", grid2CellField.left);
            gen.writeNumberField("width", grid2CellField.right - grid2CellField.left + 1);
            gen.writeNumberField("height", grid2CellField.bottom - grid2CellField.top + 1);
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeFieldName("rows");
        gen.writeStartArray();
        for (i = 0; i < value.getRowCount(); ++i) {
            gen.writeStartObject();
            gen.writeNumberField("size", value.getRowHeight(i));
            gen.writeBooleanField("auto", value.isRowAutoHeight(i));
            gen.writeBooleanField("hidden", value.isRowHidden(i));
            gen.writeBooleanField("dirty", value.isDirty());
            gen.writeNumberField("minSize", 30);
            gen.writeNumberField("clientSize", value.getRowHeight(i));
            value.getRowBackgroundColor(i);
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeFieldName("cols");
        gen.writeStartArray();
        for (i = 0; i < value.getColumnCount(); ++i) {
            gen.writeStartObject();
            gen.writeNumberField("size", value.getColumnWidth(i));
            gen.writeBooleanField("auto", value.isColumnAutoWidth(i));
            gen.writeBooleanField("hidden", value.isColumnHidden(i));
            gen.writeBooleanField("dirty", value.isDirty());
            gen.writeNumberField("minSize", 30);
            gen.writeNumberField("clientSize", value.getColumnWidth(i));
            value.getColumnGrab(i);
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeNumberField("colHeaderCount", value.getHeaderColumnCount());
        gen.writeNumberField("rowHeaderCount", value.getHeaderRowCount());
        gen.writeNumberField("colFooterCount", value.getFooterColumnCount());
        gen.writeNumberField("rowFooterCount", value.getFooterRowCount());
        gen.writeNumberField("rowCount", value.getRowCount());
        gen.writeNumberField("colCount", value.getColumnCount());
        gen.writeNumberField("width", 0);
        gen.writeNumberField("height", 0);
        if (null != value.getScript() && !value.getScript().isEmpty()) {
            gen.writeStringField("script", value.getScript());
        }
        if (this.options.hasDefaultFont()) {
            gen.writeFieldName("defaultFont");
            gen.writeStartObject();
            gen.writeStringField("fontName", this.options.getFont().getName());
            gen.writeNumberField("fontSize", this.options.getFont().getSize());
            gen.writeNumberField("fontStyle", this.options.getFont().getStyle());
            gen.writeEndObject();
        }
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

    public Class<Grid2Data> handledType() {
        return Grid2Data.class;
    }
}


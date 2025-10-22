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
 *  com.jiuqi.nvwa.grid2.json.DefaultGridOptions
 */
package com.jiuqi.nr.jtable.quickGridUtil;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.DefaultGridOptions;
import java.io.IOException;

public class QuickGrid2DataSerialize
extends JsonSerializer<Grid2Data> {
    private DefaultGridOptions options;

    public QuickGrid2DataSerialize(DefaultGridOptions options) {
        this.options = options;
    }

    public void serialize(Grid2Data value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        int i;
        gen.writeStartObject();
        gen.writeFieldName("options");
        gen.writeStartObject();
        gen.writeNumberField("selectionMode", this.options.getSelectionMode());
        gen.writeBooleanField("colResizeable", this.options.isColResizeable());
        gen.writeBooleanField("rowResizeable", this.options.isRowResizeable());
        gen.writeBooleanField("colFreeResizeable", this.options.isColFreeResizeable());
        gen.writeBooleanField("rowFreeResizeable", this.options.isRowFreeResizeable());
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
                GridCellData gridCellData = value.getGridCellData(c, r);
                gen.writeObject((Object)gridCellData);
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
            if (value.isRowAutoHeight(i)) {
                gen.writeBooleanField("auto", value.isRowAutoHeight(i));
            }
            if (value.isRowHidden(i)) {
                gen.writeBooleanField("hidden", value.isRowHidden(i));
            }
            if (value.isDirty()) {
                gen.writeBooleanField("dirty", value.isDirty());
            }
            gen.writeNumberField("minSize", 30);
            gen.writeNumberField("clientSize", value.getRowHeight(i));
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeFieldName("cols");
        gen.writeStartArray();
        for (i = 0; i < value.getColumnCount(); ++i) {
            gen.writeStartObject();
            gen.writeNumberField("size", value.getColumnWidth(i));
            if (value.isColumnAutoWidth(i)) {
                gen.writeBooleanField("auto", value.isColumnAutoWidth(i));
            }
            if (value.isColumnHidden(i)) {
                gen.writeBooleanField("hidden", value.isColumnHidden(i));
            }
            if (value.isDirty()) {
                gen.writeBooleanField("dirty", value.isDirty());
            }
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

    public Class<Grid2Data> handledType() {
        return Grid2Data.class;
    }
}


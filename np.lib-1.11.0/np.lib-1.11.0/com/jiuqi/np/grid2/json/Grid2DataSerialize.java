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
import com.jiuqi.np.grid2.Grid2CellField;
import com.jiuqi.np.grid2.Grid2Data;
import com.jiuqi.np.grid2.GridCellData;
import com.jiuqi.np.grid2.json.Grid2DataConst;
import java.io.IOException;

public class Grid2DataSerialize
extends JsonSerializer<Grid2Data> {
    public void serialize(Grid2Data value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        int i;
        gen.writeStartObject();
        gen.writeFieldName("options");
        gen.writeStartObject();
        gen.writeNumberField("loadMode", Grid2DataConst.LOAD_MODE.LOAD_MODE_NORMAL.getIndex());
        gen.writeBooleanField("loadByRow", true);
        gen.writeNumberField("selectionMode", Grid2DataConst.SELECTION_MODE.SELECTION_MODE_MULTI.getIndex());
        gen.writeNumberField("editMode", Grid2DataConst.EDIT_MODE.EDIT_MODE_EDIT.getIndex());
        gen.writeNumberField("enterNext", Grid2DataConst.ENTER_NEXT.ENTER_NEXT_RIGHT.getIndex());
        gen.writeBooleanField("rowSelectable", true);
        gen.writeBooleanField("colSelectable", true);
        gen.writeBooleanField("ignoreHidden", true);
        gen.writeBooleanField("colResizeable", true);
        gen.writeBooleanField("rowResizeable", true);
        gen.writeBooleanField("colFreeResizeable", true);
        gen.writeBooleanField("rowFreeResizeable", false);
        gen.writeBooleanField("colGrabable", true);
        gen.writeBooleanField("showSelectionBorder", value.isShowSelectingBorder());
        gen.writeBooleanField("currentCellBorderHidden", false);
        gen.writeBooleanField("colExchangeable", false);
        gen.writeBooleanField("passReadOnly", true);
        gen.writeBooleanField("showSelectionChange", false);
        gen.writeStringField("selectionColor", "rgba(255, 0, 0, 0.2)");
        gen.writeStringField("selectionBorderColor", "#56932c");
        gen.writeNumberField("selectionBorderWidth", 3);
        gen.writeStringField("currentCellColor", "rgba(228, 247,214, 0.3)");
        gen.writeStringField("currentCellBorderColor", "#000");
        gen.writeNumberField("mergeCellShowMode", Grid2DataConst.MERGE_SHOW_MODE.MERGE_SHOW_MODE_NORMAL.getIndex());
        gen.writeBooleanField("showMergeChildBorder", true);
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
            gen.writeBooleanField("auto", value.isRowAutoHeight(i));
            gen.writeBooleanField("hidden", value.isRowHidden(i));
            gen.writeBooleanField("dirty", false);
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
            gen.writeBooleanField("dirty", false);
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
        gen.writeStringField("defaultEditorId", null);
        gen.writeNumberField("rowCount", value.getRowCount());
        gen.writeNumberField("colCount", value.getColumnCount());
        gen.writeNumberField("width", 0);
        gen.writeNumberField("height", 0);
        gen.writeStringField("script", value.getScript());
        gen.writeFieldName("defalutFont");
        gen.writeStartObject();
        gen.writeStringField("fontName", "\u5fae\u8f6f\u96c5\u9ed1");
        gen.writeNumberField("fontSize", 20);
        gen.writeStringField("fontStyle", "italic");
        gen.writeStringField("foregroundColor", "#f00");
        gen.writeBooleanField("textStroke", true);
        gen.writeFieldName("textShadow");
        gen.writeStartObject();
        gen.writeNumberField("offsetX", 4);
        gen.writeNumberField("offsetY", 4);
        gen.writeNumberField("blur", 2);
        gen.writeStringField("color", "#020");
        gen.writeEndObject();
        gen.writeEndObject();
        gen.writeEndObject();
    }
}


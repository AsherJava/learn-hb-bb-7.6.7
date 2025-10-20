/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.np.grid2.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.np.grid2.Grid2Data;
import com.jiuqi.np.grid2.GridCellData;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Grid2DataDeserialize
extends JsonDeserializer<Grid2Data> {
    public Grid2Data deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode;
        Grid2Data griddata = new Grid2Data();
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode node = (JsonNode)mapper.readTree(p);
        griddata.setHeaderColumnCount(node.get("colHeaderCount").asInt());
        griddata.setHeaderRowCount(node.get("rowHeaderCount").asInt());
        griddata.setFooterColumnCount(node.get("colFooterCount").asInt());
        griddata.setFooterRowCount(node.get("rowFooterCount").asInt());
        node.get("defaultEditorId").textValue();
        griddata.setRowCount(node.get("rowCount").asInt());
        griddata.setColumnCount(node.get("colCount").asInt());
        node.get("width").asInt();
        node.get("height").asInt();
        JsonNode options = node.get("options");
        options.get("loadMode").asInt();
        options.get("loadByRow").asBoolean();
        options.get("selectionMode").asInt();
        options.get("editMode").asInt();
        options.get("enterNext").asInt();
        options.get("rowSelectable").asBoolean();
        options.get("colSelectable").asBoolean();
        options.get("ignoreHidden").asBoolean();
        options.get("colResizeable").asBoolean();
        options.get("rowResizeable").asBoolean();
        options.get("colFreeResizeable").asBoolean();
        options.get("rowFreeResizeable").asBoolean();
        options.get("colGrabable").asBoolean();
        griddata.setShowSelectingBorder(options.get("showSelectionBorder").asBoolean());
        options.get("currentCellBorderHidden").asBoolean();
        options.get("colExchangeable").asBoolean();
        options.get("passReadOnly").asBoolean();
        options.get("showSelectionChange").asBoolean();
        options.get("selectionColor").textValue();
        options.get("selectionBorderColor").textValue();
        options.get("selectionBorderWidth").asInt();
        options.get("currentCellColor").textValue();
        options.get("currentCellBorderColor").textValue();
        options.get("mergeCellShowMode").asInt();
        JsonNode jsoncells = node.get("cells");
        JsonNode jsonrowlist = jsoncells.get("rowList");
        if (jsonrowlist.isArray()) {
            ArrayNode jsonrowarray = (ArrayNode)jsonrowlist;
            for (int r = 0; r < jsonrowarray.size(); ++r) {
                JsonNode jsonrow = jsonrowarray.get(r);
                if (!jsonrow.isArray()) continue;
                ArrayNode jsoncellarray = (ArrayNode)jsonrow;
                for (int c = 0; c < jsoncellarray.size(); ++c) {
                    JsonNode jsoncell = jsoncellarray.get(c);
                    StringWriter writer = new StringWriter();
                    mapper.writeValue((Writer)writer, (Object)jsoncell);
                    writer.close();
                    String json = writer.toString();
                    GridCellData gridCellData = (GridCellData)mapper.readValue(json, GridCellData.class);
                    griddata.setGridCellData(gridCellData, r, c);
                }
            }
        }
        if ((jsonNode = node.get("mergeCells")).isArray()) {
            ArrayNode mergeCells = (ArrayNode)jsonNode;
            for (int i = 0; i < mergeCells.size(); ++i) {
                JsonNode cell = mergeCells.get(i);
                int row = cell.get("row").asInt();
                int col = cell.get("col").asInt();
                int width = cell.get("width").asInt();
                int height = cell.get("height").asInt();
                griddata.mergeCells(col, row, width + col - 1, height + row - 1);
            }
        }
        if ((jsonNode = node.get("rows")).isArray()) {
            ArrayNode rows = (ArrayNode)jsonNode;
            for (int i = 0; i < rows.size(); ++i) {
                JsonNode row = rows.get(i);
                griddata.setRowHeight(i, row.get("size").asInt());
                griddata.setRowAutoHeight(i, row.get("auto").asBoolean());
                griddata.setRowHidden(i, row.get("hidden").asBoolean());
                row.get("clientSize").asInt();
            }
        }
        if ((jsonNode = node.get("cols")).isArray()) {
            ArrayNode cols = (ArrayNode)jsonNode;
            for (int i = 0; i < cols.size(); ++i) {
                JsonNode col = cols.get(i);
                griddata.setColumnWidth(i, col.get("size").asInt());
                griddata.setColumnAutoWidth(i, col.get("auto").asBoolean());
                griddata.setColumnHidden(i, col.get("hidden").asBoolean());
                col.get("clientSize").asInt();
            }
        }
        return griddata;
    }
}


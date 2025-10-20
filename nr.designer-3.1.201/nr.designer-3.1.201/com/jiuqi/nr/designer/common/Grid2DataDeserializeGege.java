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
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.designer.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nr.designer.common.GridCellDataDeserializeGege;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.io.IOException;
import java.util.ArrayList;

public class Grid2DataDeserializeGege
extends JsonDeserializer<Grid2Data> {
    public Grid2Data deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonCols;
        JsonNode jsonRows;
        JsonNode jsonMergeCells;
        JsonNode jsoncells;
        JsonNode jsonOption;
        Grid2Data griddata = new Grid2Data();
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode node = (JsonNode)mapper.readTree(p);
        ArrayList<Integer> italicList = new ArrayList<Integer>();
        ArrayList<Integer> boldList = new ArrayList<Integer>();
        ArrayList<Integer> editAbleList = new ArrayList<Integer>();
        ArrayList<Integer> selectAbleList = new ArrayList<Integer>();
        ArrayList<Integer> vertTextList = new ArrayList<Integer>();
        if (node.has("colHeaderCount")) {
            griddata.setHeaderColumnCount(node.get("colHeaderCount").asInt());
        }
        if (node.has("rowHeaderCount")) {
            griddata.setHeaderRowCount(node.get("rowHeaderCount").asInt());
        }
        if (node.has("colFooterCount")) {
            griddata.setFooterColumnCount(node.get("colFooterCount").asInt());
        }
        if (node.has("rowFooterCount")) {
            griddata.setFooterRowCount(node.get("rowFooterCount").asInt());
        }
        if (node.has("rowCount")) {
            griddata.setRowCount(node.get("rowCount").asInt());
        }
        if (node.has("colCount")) {
            griddata.setColumnCount(node.get("colCount").asInt());
        }
        if (node.has("options") && (jsonOption = node.get("options")).has("cellBoolAttrs")) {
            JsonNode jsonVertTextTrue;
            JsonNode jsonSelectAbleFalse;
            JsonNode jsonEditAbleFalse;
            JsonNode jsonFontBoldTure;
            JsonNode jsonItalic;
            int i;
            ArrayNode jsonFontItalic;
            JsonNode jsonFontItalicTure;
            JsonNode jsonCellBoolAttrs = jsonOption.get("cellBoolAttrs");
            if (jsonCellBoolAttrs.has("fontItalicTure") && (jsonFontItalicTure = jsonCellBoolAttrs.get("fontItalicTure")).isArray()) {
                jsonFontItalic = (ArrayNode)jsonFontItalicTure;
                for (i = 0; i < jsonFontItalic.size(); ++i) {
                    jsonItalic = jsonFontItalic.get(i);
                    italicList.add(jsonItalic.asInt());
                }
            }
            if (jsonCellBoolAttrs.has("fontBoldTure") && (jsonFontBoldTure = jsonCellBoolAttrs.get("fontBoldTure")).isArray()) {
                jsonFontItalic = (ArrayNode)jsonFontBoldTure;
                for (i = 0; i < jsonFontItalic.size(); ++i) {
                    jsonItalic = jsonFontItalic.get(i);
                    boldList.add(jsonItalic.asInt());
                }
            }
            if (jsonCellBoolAttrs.has("editableFalse") && (jsonEditAbleFalse = jsonCellBoolAttrs.get("editableFalse")).isArray()) {
                ArrayNode jsonEditAble = (ArrayNode)jsonEditAbleFalse;
                for (i = 0; i < jsonEditAble.size(); ++i) {
                    JsonNode editAble = jsonEditAble.get(i);
                    editAbleList.add(editAble.asInt());
                }
            }
            if (jsonCellBoolAttrs.has("selectableFalse") && (jsonSelectAbleFalse = jsonCellBoolAttrs.get("selectableFalse")).isArray()) {
                ArrayNode jsonSelectAble = (ArrayNode)jsonSelectAbleFalse;
                for (i = 0; i < jsonSelectAble.size(); ++i) {
                    JsonNode selectAble = jsonSelectAble.get(i);
                    selectAbleList.add(selectAble.asInt());
                }
            }
            if (jsonCellBoolAttrs.has("vertTextTure") && (jsonVertTextTrue = jsonCellBoolAttrs.get("vertTextTure")).isArray()) {
                ArrayNode jsonVerText = (ArrayNode)jsonVertTextTrue;
                for (i = 0; i < jsonVerText.size(); ++i) {
                    JsonNode vertText = jsonVerText.get(i);
                    vertTextList.add(vertText.asInt());
                }
            }
        }
        int curIdx = 0;
        if (node.has("cells") && (jsoncells = node.get("cells")).isArray()) {
            ArrayNode jsonrowlist = (ArrayNode)jsoncells;
            for (int i = 0; i < jsonrowlist.size(); ++i) {
                JsonNode jsonrow = jsonrowlist.get(i);
                if (!jsonrow.isArray()) continue;
                ArrayNode jsoncellarray = (ArrayNode)jsonrow;
                for (int j = 0; j < jsoncellarray.size(); ++j) {
                    JsonNode jsoncell = jsoncellarray.get(j);
                    GridCellDataDeserializeGege gridCellDataDeserialize = new GridCellDataDeserializeGege();
                    Boolean isVertText = false;
                    if (vertTextList != null && vertTextList.size() > 0) {
                        for (Integer idx : vertTextList) {
                            if (idx != curIdx) continue;
                            isVertText = true;
                        }
                    }
                    GridCellData gridCellData = gridCellDataDeserialize.deserialize(jsoncell, isVertText);
                    if (italicList != null && italicList.size() > 0) {
                        for (Integer idx : italicList) {
                            if (idx != curIdx) continue;
                            gridCellData.setFontStyle(gridCellData.getFontStyle() ^ 4);
                        }
                    }
                    if (boldList != null && boldList.size() > 0) {
                        for (Integer idx : boldList) {
                            if (idx != curIdx) continue;
                            gridCellData.setFontStyle(gridCellData.getFontStyle() ^ 2);
                        }
                    }
                    if (editAbleList != null && editAbleList.size() > 0) {
                        for (Integer idx : editAbleList) {
                            if (idx != curIdx) continue;
                            gridCellData.setEditable(false);
                        }
                    }
                    if (selectAbleList != null && selectAbleList.size() > 0) {
                        for (Integer idx : selectAbleList) {
                            if (idx != curIdx) continue;
                            gridCellData.setSelectable(false);
                        }
                    }
                    if (vertTextList != null && vertTextList.size() > 0) {
                        for (Integer idx : vertTextList) {
                            if (idx != curIdx) continue;
                            gridCellData.setVertText(true);
                        }
                    }
                    griddata.setGridCellData(gridCellData, i, j);
                    ++curIdx;
                }
            }
        }
        if (node.has("mergeCells") && (jsonMergeCells = node.get("mergeCells")).isArray()) {
            ArrayNode mergeCells = (ArrayNode)jsonMergeCells;
            for (int i = 0; i < mergeCells.size(); ++i) {
                JsonNode cell = mergeCells.get(i);
                int row = cell.get("row").asInt();
                int col = cell.get("col").asInt();
                int width = cell.get("width").asInt();
                int height = cell.get("height").asInt();
                griddata.mergeCells(col, row, width + col - 1, height + row - 1);
            }
        }
        if (node.has("rows") && (jsonRows = node.get("rows")).isArray()) {
            ArrayNode rows = (ArrayNode)jsonRows;
            for (int i = 0; i < rows.size(); ++i) {
                JsonNode row = rows.get(i);
                griddata.setRowHeight(i, row.get("size").asInt());
                griddata.setRowAutoHeight(i, row.get("auto").asBoolean());
                griddata.setRowHidden(i, row.get("hidden").asBoolean());
                griddata.setRowHeight(i, row.get("clientSize").asInt());
            }
        }
        if (node.has("cols") && (jsonCols = node.get("cols")).isArray()) {
            ArrayNode cols = (ArrayNode)jsonCols;
            for (int i = 0; i < cols.size(); ++i) {
                JsonNode col = cols.get(i);
                griddata.setColumnWidth(i, col.get("size").asInt());
                griddata.setColumnAutoWidth(i, col.get("auto").asBoolean());
                griddata.setColumnHidden(i, col.get("hidden").asBoolean());
                griddata.setColumnWidth(i, col.get("clientSize").asInt());
            }
        }
        return griddata;
    }
}


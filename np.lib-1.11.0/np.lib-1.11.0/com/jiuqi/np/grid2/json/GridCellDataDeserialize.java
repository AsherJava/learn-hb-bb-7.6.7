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
import com.jiuqi.np.grid2.GridCellData;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GridCellDataDeserialize
extends JsonDeserializer<GridCellData> {
    private static final String color16_regEx = "^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$";
    private static final Pattern pattern = Pattern.compile("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$");

    public GridCellData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode borderColor;
        JsonNode border;
        String bcolor;
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode node = (JsonNode)mapper.readTree(p);
        GridCellData gridCellData = new GridCellData(node.get("colIndex").asInt(), node.get("rowIndex").asInt());
        JsonNode jsonNode = node.get("showText");
        gridCellData.setShowText(jsonNode == null ? null : jsonNode.textValue());
        jsonNode = node.get("editText");
        gridCellData.setEditText(jsonNode == null ? null : jsonNode.textValue());
        jsonNode = node.get("dataType");
        gridCellData.setDataType(jsonNode == null ? -1 : jsonNode.asInt());
        jsonNode = node.get("selectable");
        gridCellData.setSelectable(jsonNode == null ? false : jsonNode.asBoolean());
        jsonNode = node.get("editable");
        gridCellData.setEditable(jsonNode == null ? false : jsonNode.asBoolean());
        jsonNode = node.get("cellMode");
        gridCellData.setCellMode(jsonNode == null ? 1 : jsonNode.asInt());
        jsonNode = node.get("clientData");
        if (null != jsonNode && null != jsonNode.textValue()) {
            gridCellData.setDataExFromString(jsonNode.textValue());
        }
        gridCellData.setRowSpan((jsonNode = node.get("rowSpan")) == null ? 0 : jsonNode.asInt());
        jsonNode = node.get("colSpan");
        gridCellData.setColSpan(jsonNode == null ? 0 : jsonNode.asInt());
        jsonNode = node.get("backStyle");
        gridCellData.setBackGroundStyle(jsonNode == null ? 0 : jsonNode.asInt());
        jsonNode = node.get("isMerged");
        gridCellData.setMerged(jsonNode == null ? false : jsonNode.asBoolean());
        if (node.has("backColor") && GridCellDataDeserialize.checkColorStr(bcolor = node.get("backColor").textValue())) {
            gridCellData.setBackGroundColor(GridCellDataDeserialize.htmlColorToInt(bcolor));
        }
        if ((border = node.get("border")).isArray()) {
            ArrayNode borderArray = (ArrayNode)border;
            gridCellData.setRightBorderStyle(borderArray.get(0).asInt());
            gridCellData.setBottomBorderStyle(borderArray.get(1).asInt());
        }
        if ((borderColor = node.get("borderColor")).isArray()) {
            String bbcolor;
            ArrayNode borderColorArray = (ArrayNode)borderColor;
            String rbColor = borderColorArray.get(0).textValue();
            if (GridCellDataDeserialize.checkColorStr(rbColor)) {
                gridCellData.setRightBorderColor(GridCellDataDeserialize.htmlColorToInt(rbColor));
            }
            if (GridCellDataDeserialize.checkColorStr(bbcolor = borderColorArray.get(1).textValue())) {
                gridCellData.setBottomBorderColor(GridCellDataDeserialize.htmlColorToInt(bbcolor));
            }
        }
        gridCellData.setFontName(node.get("fontName").textValue());
        gridCellData.setFontSize(node.get("fontSize").asInt());
        String gcolor = node.get("fontColor").textValue();
        if (GridCellDataDeserialize.checkColorStr(gcolor)) {
            gridCellData.setForeGroundColor(GridCellDataDeserialize.htmlColorToInt(gcolor));
        }
        node.get("textStroke");
        int fontStyle = 0;
        if (node.get("fontBold").asBoolean()) {
            fontStyle ^= 2;
        }
        if (node.get("fontItalic").asBoolean()) {
            fontStyle ^= 4;
        }
        if (node.has("decoration")) {
            int decoration = node.get("decoration").asInt();
            if (decoration == 1) {
                fontStyle ^= 8;
            }
            if (decoration == 2) {
                fontStyle ^= 0x10;
            }
        }
        gridCellData.setFontStyle(fontStyle);
        if (node.has("wrapLine")) {
            gridCellData.setWrapLine(node.get("wrapLine").asBoolean());
        }
        if (node.has("indent")) {
            gridCellData.setIndent(node.get("indent").asInt());
        }
        if (node.has("horzAlign")) {
            gridCellData.setHorzAlign(node.get("horzAlign").asInt());
        }
        if (node.has("vertAlign")) {
            gridCellData.setVertAlign(node.get("vertAlign").asInt());
        }
        if (node.has("vertText")) {
            gridCellData.setVertText(node.get("vertText").asBoolean());
        }
        if (node.has("multiLine")) {
            gridCellData.setMultiLine(node.get("multiLine").asBoolean());
        }
        if (node.has("fitFontSize")) {
            gridCellData.setFitFontSize(node.get("fitFontSize").asBoolean());
        }
        return gridCellData;
    }

    private static boolean checkColorStr(String colorStr) {
        Matcher matcher = pattern.matcher(colorStr);
        return matcher.matches();
    }

    private static int htmlColorToInt(String color) {
        return Integer.parseInt(color.substring(1), 16);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$Cell_MODE
 */
package com.jiuqi.nr.designer.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataConst;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GridCellDataDeserializeGege {
    public GridCellData deserialize(JsonNode node, Boolean isVertText) {
        String html;
        JsonNode borderColor;
        int colIndex = node.get("colIndex").asInt();
        int rowIndex = node.get("rowIndex").asInt();
        GridCellData gridCellData = new GridCellData(colIndex, rowIndex);
        if (node.has("title")) {
            gridCellData.setTitle(node.get("title").toString());
        }
        if (node.has("showText")) {
            String showText = node.get("showText").textValue();
            if (StringUtils.isEmpty((String)showText)) {
                showText = node.get("showText") == null ? "" : node.get("showText").toString();
            }
            gridCellData.setShowText(showText);
        }
        if (node.has("editText")) {
            String editText = node.get("editText").textValue();
            if (StringUtils.isEmpty((String)editText)) {
                editText = node.get("editText") == null ? "" : node.get("editText").asText();
            }
            gridCellData.setEditText(editText);
        }
        if (node.has("cellMode")) {
            gridCellData.setCellMode(node.get("cellMode").asInt());
        }
        if (node.has("selectable")) {
            gridCellData.setSelectable(node.get("selectable").asBoolean());
        }
        if (node.has("editable")) {
            gridCellData.setEditable(node.get("editable").asBoolean());
        }
        if (node.has("rowSpan")) {
            gridCellData.setRowSpan(node.get("rowSpan").asInt());
        }
        if (node.has("colSpan")) {
            gridCellData.setColSpan(node.get("colSpan").asInt());
        }
        if (node.has("isMerged")) {
            gridCellData.setMerged(node.get("isMerged").asBoolean());
        }
        if (node.has("backColor")) {
            gridCellData.setBackGroundColor(this.htmlColorToInt(node.get("backColor").textValue()));
        }
        if (node.has("backStyle")) {
            gridCellData.setBackGroundStyle(node.get("backStyle").asInt());
        }
        if (node.has("border")) {
            JsonNode border = node.get("border");
            if (border.isArray()) {
                ArrayNode borderArray = (ArrayNode)border;
                gridCellData.setRightBorderStyle(borderArray.get(0).asInt());
                gridCellData.setBottomBorderStyle(borderArray.get(1).asInt());
            }
        } else {
            gridCellData.setRightBorderStyle(-1);
            gridCellData.setBottomBorderStyle(-1);
        }
        if (node.has("cellDescription") && !node.get("cellDescription").asText().equals("null")) {
            gridCellData.setPersistenceData("cellDescription", node.get("cellDescription").asText());
        }
        if (node.has("borderColor") && (borderColor = node.get("borderColor")).isArray()) {
            ArrayNode borderColorArray = (ArrayNode)borderColor;
            gridCellData.setRightBorderColor(this.htmlColorToInt(borderColorArray.get(0).textValue()));
            gridCellData.setBottomBorderColor(this.htmlColorToInt(borderColorArray.get(1).textValue()));
        }
        if (node.has("fontName")) {
            gridCellData.setFontName(node.get("fontName").textValue());
        }
        if (node.has("fontSize")) {
            gridCellData.setFontSize(node.get("fontSize").asInt());
        } else {
            gridCellData.setFontSize(15);
        }
        if (node.has("fontColor")) {
            gridCellData.setForeGroundColor(this.htmlColorToInt(node.get("fontColor").textValue()));
        }
        if (node.has("fontColor")) {
            gridCellData.setForeGroundColor(this.htmlColorToInt(node.get("fontColor").textValue()));
        }
        int fontStyle = 0;
        if (node.has("fontBold") && node.get("fontBold").asBoolean()) {
            fontStyle ^= 2;
        }
        if (node.has("fontItalic") && node.get("fontItalic").asBoolean()) {
            fontStyle ^= 4;
        }
        if (node.has("decoration") && node.has("decoration")) {
            int decoration = node.get("decoration").asInt();
            if (decoration == 1) {
                fontStyle ^= 8;
            }
            if (decoration == 2) {
                fontStyle ^= 0x10;
            }
            if (decoration == 3) {
                fontStyle ^= 0x40;
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
        if (node.has("html") && !StringUtils.isEmpty((String)(html = node.get("html").textValue()))) {
            String trimText;
            html = node.get("html") == null ? "" : node.get("html").textValue();
            html = html.replace("<span style=\"\">", "span");
            html = html.replace("line-through;\">", "span");
            html = html.replace("underline;\">", "span");
            html = html.replace("</span>", "span");
            String regex = ">([^>]+)<";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()) {
                String oldValue = matcher.group();
                html = html.replace(oldValue, ">" + gridCellData.getEditText() + "<");
            }
            gridCellData.setCellMode(Grid2DataConst.Cell_MODE.Cell_MODE_HTML.getIndex());
            gridCellData.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.HotLink));
            StringBuffer addStyle = new StringBuffer();
            addStyle.append("style=\"");
            addStyle.append("font-family:");
            addStyle.append(gridCellData.getFontName());
            addStyle.append(";");
            addStyle.append("font-size:");
            addStyle.append(gridCellData.getFontSize());
            addStyle.append("px;");
            addStyle.append("color:");
            String colorRs = gridCellData.getForeGroundColor() + "";
            try {
                int color10 = gridCellData.getForeGroundColor();
                colorRs = Integer.toHexString(color10);
            }
            catch (Exception color10) {
                // empty catch block
            }
            addStyle.append("#" + colorRs);
            addStyle.append(";");
            if ((gridCellData.getFontStyle() & 2) > 0) {
                addStyle.append("font-weight:bold;");
            }
            if ((gridCellData.getFontStyle() & 4) > 0) {
                addStyle.append("font-style:italic;");
            }
            addStyle.append("position:absolute;");
            int horzAlign = gridCellData.getHorzAlign();
            int vertAlign = gridCellData.getVertAlign();
            if ((vertAlign == 0 || vertAlign == 3) && horzAlign == 3) {
                addStyle.append("top:50%;");
                addStyle.append("right:50%;");
                addStyle.append("transform:translateY(-50%)translateX(50%);");
            } else if (vertAlign == 2 && horzAlign == 3) {
                addStyle.append("top:100%;");
                addStyle.append("right:50%;");
                addStyle.append("transform:translateY(-100%)translateX(50%);");
            } else {
                if (vertAlign == 0 || vertAlign == 3) {
                    addStyle.append("top:50%;");
                    addStyle.append("transform:translateY(-50%);");
                }
                if (vertAlign == 2) {
                    addStyle.append("top:100%;");
                    addStyle.append("transform:translateY(-100%);");
                }
                if (horzAlign == 2) {
                    addStyle.append("right:0%;");
                }
                if (horzAlign == 3) {
                    addStyle.append("right:50%;");
                    addStyle.append("transform:translateX(50%);");
                }
            }
            addStyle.append("\"");
            String styleRegex = "style=\"\\S*\"";
            Pattern stylePattern = Pattern.compile(styleRegex);
            Matcher styleMatcher = stylePattern.matcher(html);
            if (styleMatcher.find()) {
                String oldStyle = styleMatcher.group();
                html = html.replace(oldStyle, addStyle.toString());
            } else {
                html = html.replace("href=\"javascript:void(0);\"", "href=\"javascript:void(0);\" " + addStyle.toString() + " ");
            }
            String editText = gridCellData.getEditText();
            if (editText.contains(" ")) {
                char[] charArray = editText.toCharArray();
                StringBuffer b = new StringBuffer();
                for (char c : charArray) {
                    if (' ' == c) {
                        b.append("&nbsp;");
                        continue;
                    }
                    b.append(c);
                }
                html = html.replace(editText, b.toString());
            }
            if ((trimText = editText.trim()).contains(" ")) {
                char[] charArray = trimText.toCharArray();
                StringBuffer b = new StringBuffer();
                for (char c : charArray) {
                    if (' ' == c) {
                        b.append("&nbsp;");
                        continue;
                    }
                    b.append(c);
                }
                trimText = b.toString();
            }
            String spanStyle = "><span style=\"";
            if ((gridCellData.getFontStyle() & 8) > 0) {
                spanStyle = spanStyle + "text-decoration:underline;";
            }
            if ((gridCellData.getFontStyle() & 0x10) > 0) {
                spanStyle = spanStyle + "text-decoration:line-through;";
            }
            if (isVertText.booleanValue()) {
                spanStyle = spanStyle + "writing-mode:tb-rl;";
                spanStyle = spanStyle + "text-orientation:upright;";
            }
            spanStyle = spanStyle + "\">" + trimText + "</span><";
            trimText = ">" + trimText + "<";
            html = html.replace(trimText, spanStyle);
            html = html.replace("&nbsp;&nbsp;", "&nbsp;");
            gridCellData.setEditText("");
            gridCellData.setShowText(html);
        }
        return gridCellData;
    }

    public int htmlColorToInt(String color) {
        return Integer.parseInt(color.substring(1), 16);
    }
}


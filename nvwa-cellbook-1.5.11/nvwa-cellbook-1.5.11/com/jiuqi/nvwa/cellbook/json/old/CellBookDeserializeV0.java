/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 */
package com.jiuqi.nvwa.cellbook.json.old;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.constant.CellStyleModel;
import com.jiuqi.nvwa.cellbook.constant.DocModel;
import com.jiuqi.nvwa.cellbook.constant.EnterNext;
import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.SelectionModel;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.constant.VerticalAlignment;
import com.jiuqi.nvwa.cellbook.datatype.CellBookDataTypeFactory;
import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import com.jiuqi.nvwa.cellbook.json.old.OldCellBookDeserialize;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellMerge;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.cellbook.model.CellStyle;
import com.jiuqi.nvwa.cellbook.model.Col;
import com.jiuqi.nvwa.cellbook.model.DocProps;
import com.jiuqi.nvwa.cellbook.model.Point;
import com.jiuqi.nvwa.cellbook.model.Row;
import com.jiuqi.nvwa.cellbook.model.SimpleCellSheet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellBookDeserializeV0
implements OldCellBookDeserialize {
    public static final Logger LOGGER = LoggerFactory.getLogger(CellBookDeserializeV0.class);
    public static final String DOC_PROPS = "docProps";
    public static final String DOC_VERSION = "version";
    public static final String DOC_ENCODING = "encoding";
    public static final String DOC_CREATER = "creator";
    public static final String DOC_CREATED = "created";
    public static final String DOC_MODIFIOR = "modifior";
    public static final String DOC_MODIFIED = "modified";
    public static final String DOC_COMPANY = "company";
    public static final String DOC_SECURITY = "docSecurity";
    public static final String DOC_MODEL = "model";
    public static final String GROUP_MANAGE = "groupManager";
    public static final String GROUP_MANAGE_GROUPS = "groups";
    public static final String GROUP_MANAGE_SHEETS = "sheets";
    public static final String BOOK_STYLE = "bookStyle";
    public static final String BOOK_STYLE_DEFAULT = "defaultStyle";
    public static final String PALETTE = "palette";
    public static final String PALETTE_TITLE = "title";
    public static final String PALETTE_VALUE = "value";
    public static final String GROUP_TITLE = "title";
    public static final String GROUP_CODE = "code";
    public static final String SHEET = "sheets";
    public static final String SHEET_TITLE = "title";
    public static final String SHEET_CODE = "code";
    public static final String SHEET_VERSION = "version";
    public static final String SHEET_GROUP_CODE = "groupCode";
    public static final String SHEET_OPTIONS = "options";
    public static final String SHEET_ROWS = "rows";
    public static final String SHEET_COLS = "columns";
    public static final String SHEET_DATA = "data";
    public static final String SHEET_STYLE = "styles";
    public static final String SHEET_MERGE = "mergeInfo";
    public static final String OPTION_SELECT = "selectMode";
    public static final String OPTION_ENTER = "enterNext";
    public static final String OPTION_HIDDEN_SERIAL_NUMBER = "hiddenSerialNumberHeader";
    public static final String OPTION_SERIAL_NUMBER = "serialNumberHeader";
    public static final String OPTION_HEADER = "header";
    public static final String OPTION_RESIZE = "resize";
    public static final String SERIAL_NUMBER_HEIGHT = "height";
    public static final String SERIAL_NUMBER_FONTNAME = "fontName";
    public static final String SERIAL_NUMBER_FONTCOLOR = "fontColor";
    public static final String SERIAL_NUMBER_FONTSIZE = "fontSize";
    public static final String SERIAL_NUMBER_FILLPATTERNTYPE = "fillPatternType";
    public static final String SERIAL_NUMBER_BACKGROUNDIMG = "backGroundImg";
    public static final String SERIAL_NUMBER_BACKGROUNDCOLOR = "backGroundColor";
    public static final String SERIAL_NUMBER_PADDING = "padding";
    public static final String HEADER_ROW = "rowHeader";
    public static final String HEADER_COL = "columnHeader";
    public static final String RESIZE_ROW = "row";
    public static final String RESIZE_COL = "column";
    public static final String ROW_SIZE = "size";
    public static final String ROW_AUTO = "auto";
    public static final String ROW_HIDDEN = "hidden";
    public static final String ROW_FOLD = "fold";
    public static final String COL_SIZE = "size";
    public static final String COL_AUTO = "auto";
    public static final String COL_HIDDEN = "hidden";
    public static final String COL_FOLD = "fold";
    public static final String DATA_ROW = "r";
    public static final String DATA_COL = "c";
    public static final String DATA_TYPE = "t";
    public static final String COMMON_DATA_TYPE = "b";
    public static final String DATA_VALUE = "v";
    public static final String DATA_FORMULA = "formula";
    public static final String DATA_STYLE = "s";
    public static final String DATA_MERGE = "m";
    public static final String DATA_PERSISTENCE = "exData";
    public static final String OLD_STYLE_RIGHT_BORDER_COLOR = "rightBorderColor";
    public static final String OLD_STYLE_RIGHT_BORDER_STYLE = "rightBorderStyle";
    public static final String OLD_STYLE_BOTTOM_BORDER_COLOR = "bottomBorderColor";
    public static final String OLD_STYLE_BOTTOM_BORDER_STYLE = "bottomBorderStyle";
    public static final String OLD_STYLE_LEFT_BORDER_COLOR = "leftBorderColor";
    public static final String OLD_STYLE_LEFT_BORDER_STYLE = "leftBorderStyle";
    public static final String OLD_STYLE_TOP_BORDER_COLOR = "topBorderColor";
    public static final String OLD_STYLE_TOP_BORDER_STYLE = "topBorderStyle";
    public static final String OLD_STYLE_DIAGONAL_UP_COLOR = "diagonalUpColor";
    public static final String OLD_STYLE_DIAGONAL_UP_STYLE = "diagonalUpStyle";
    public static final String OLD_STYLE_DIAGONAL_DOWN_COLOR = "diagonalDownColor";
    public static final String OLD_STYLE_DIAGONAL_DOWN_STYLE = "diagonalDownStyle";
    public static final String OLD_STYLE_FONT_NAME = "fontName";
    public static final String OLD_STYLE_FONT_SIZE = "fontSize";
    public static final String OLD_STYLE_FONT_COLOR = "fontColor";
    public static final String OLD_STYLE_FIT_FONT_SIZE = "fitFontSize";
    public static final String OLD_STYLE_BACKGROUND_COLOR = "backGroundColor";
    public static final String OLD_STYLE_FORMATTER = "formatter";
    public static final String STYLE_COUNT = "count";
    public static final String STYLE_MODEL = "model";
    public static final String STYLE_HORIZONTAL_ALIGNMENT = "horizontalAlignment";
    public static final String STYLE_VERTIACL_ALIGNMENT = "verticalAlignment";
    public static final String STYLE_FORMATTER = "formatter";
    public static final String STYLE_FORMATTER_FORMATCODE = "formatCode";
    public static final String STYLE_FONT = "font";
    public static final String STYLE_FONT_NAME = "name";
    public static final String STYLE_FONT_SIZE = "size";
    public static final String STYLE_FONT_COLOR = "color";
    public static final String STYLE_BOLD = "bold";
    public static final String STYLE_ITALIC = "italic";
    public static final String STYLE_UNDERLINE = "underline";
    public static final String STYLE_INLINE = "inline";
    public static final String STYLE_INDENT = "indent";
    public static final String STYLE_FIT_FONT_SIZE = "fitSize";
    public static final String STYLE_WRAPLINE = "wrapLine";
    public static final String STYLE_BORDERS = "borders";
    public static final String STYLE_BORDERS_RIGHT = "right";
    public static final String STYLE_BORDERS_BOTTOM = "bottom";
    public static final String STYLE_BORDERS_LEFT = "left";
    public static final String STYLE_BORDERS_TOP = "top";
    public static final String STYLE_BORDERS_DIAGONAL_UP = "diagonalUp";
    public static final String STYLE_BORDERS_DIAGONAL_DOWN = "diagonalDown";
    public static final String STYLE_BORDER_COLOR = "color";
    public static final String STYLE_BORDER_STYLE = "style";
    public static final String STYLE_BACKGROUND = "backGround";
    public static final String STYLE_FILL_PATTERN_TYPE = "fillPatternType";
    public static final String STYLE_BACKGROUND_COLOR = "color";
    public static final String STYLE_BACKGROUND_IMG = "backGroundImg";
    public static final String MERGE_ROW = "rowIndex";
    public static final String MERGE_COL = "columnIndex";
    public static final String MERGE_ROW_SPAN = "rowSpan";
    public static final String MERGE_COL_SPAN = "columnSpan";

    @Override
    public String getMinVersion() {
        return "";
    }

    @Override
    public String getMaxVersion() {
        return "";
    }

    @Override
    public CellBook deserialize(CellBook cellBook, JsonNode node) throws IOException, JsonProcessingException {
        JsonNode jsonNode;
        JsonNode docProps;
        if (node.has(DOC_PROPS) && null != (docProps = node.get(DOC_PROPS))) {
            DocProps docProp = new DocProps();
            cellBook.setDocProps(docProp);
            if (docProps.has("version")) {
                cellBook.getDocProps().setVersion(docProps.get("version").textValue());
            }
            if (docProps.has(DOC_ENCODING)) {
                cellBook.getDocProps().setEncoding(docProps.get(DOC_ENCODING).textValue());
            }
            if (docProps.has(DOC_CREATER)) {
                cellBook.getDocProps().setCreator(docProps.get(DOC_CREATER).textValue());
            }
            if (docProps.has(DOC_CREATED)) {
                cellBook.getDocProps().setCreated(docProps.get(DOC_CREATED).numberValue().longValue());
            }
            if (docProps.has(DOC_MODIFIOR)) {
                cellBook.getDocProps().setModifior(docProps.get(DOC_MODIFIOR).textValue());
            }
            if (docProps.has(DOC_MODIFIED)) {
                cellBook.getDocProps().setModified(docProps.get(DOC_MODIFIED).numberValue().longValue());
            }
            if (docProps.has(DOC_COMPANY)) {
                cellBook.getDocProps().setCompany(docProps.get(DOC_COMPANY).textValue());
            }
            if (docProps.has(DOC_SECURITY)) {
                cellBook.getDocProps().setDocSecurity(docProps.get(DOC_SECURITY).textValue());
            }
            if (docProps.has("model")) {
                int asInt = docProps.get("model").asInt();
                if (0 == asInt) {
                    cellBook.getDocProps().setModel(DocModel.READONLY);
                } else if (1 == asInt) {
                    cellBook.getDocProps().setModel(DocModel.DESIGN);
                } else if (2 == asInt) {
                    cellBook.getDocProps().setModel(DocModel.EDIT);
                }
            }
        }
        ArrayList<SimpleCellSheet> sheetGroups = new ArrayList<SimpleCellSheet>();
        if (node.has(GROUP_MANAGE) && null != (jsonNode = node.get(GROUP_MANAGE))) {
            String jsonCode;
            String jsonTitle;
            int i;
            ArrayNode jsonPalette;
            if (jsonNode.has(GROUP_MANAGE_GROUPS) && null != (jsonPalette = (ArrayNode)jsonNode.get(GROUP_MANAGE_GROUPS))) {
                for (i = 0; i < jsonPalette.size(); ++i) {
                    JsonNode group = jsonPalette.get(i);
                    jsonTitle = group.get("title").textValue();
                    jsonCode = group.get("code").textValue();
                    cellBook.createSheetGroup(jsonTitle, jsonCode);
                }
            }
            if (jsonNode.has("sheets") && null != (jsonPalette = (ArrayNode)jsonNode.get("sheets"))) {
                for (i = 0; i < jsonPalette.size(); ++i) {
                    JsonNode sheetBean = jsonPalette.get(i);
                    jsonTitle = sheetBean.get("title").textValue();
                    jsonCode = sheetBean.get("code").textValue();
                    String groupCode = sheetBean.get(SHEET_GROUP_CODE).textValue();
                    SimpleCellSheet sheetMain = new SimpleCellSheet(jsonCode, jsonTitle, groupCode);
                    sheetGroups.add(sheetMain);
                }
            }
        }
        if (node.has(BOOK_STYLE)) {
            jsonNode = node.get(BOOK_STYLE);
            if (jsonNode.has(BOOK_STYLE_DEFAULT)) {
                JsonNode jsonStyle = jsonNode.get(BOOK_STYLE_DEFAULT);
                CellStyle style = cellBook.getBookStyle().getDefaultStyle();
                this.readStyleOld(jsonStyle, style);
            }
            if (jsonNode.has(PALETTE)) {
                ArrayList<CellColor> palette = new ArrayList<CellColor>();
                ArrayNode jsonPalette = (ArrayNode)jsonNode.get(PALETTE);
                for (int i = 0; i < jsonPalette.size(); ++i) {
                    JsonNode cellColor = jsonPalette.get(i);
                    String title = "";
                    if (cellColor.has("title")) {
                        title = cellColor.get("title").textValue();
                    }
                    String textValue = cellColor.get(PALETTE_VALUE).textValue();
                    CellColor oneCellColor = new CellColor(this.handlerColor(textValue), title);
                    palette.add(oneCellColor);
                }
                cellBook.getBookStyle().setPalette(palette);
            }
        }
        if (node.has("sheets")) {
            ArrayNode sheets = (ArrayNode)node.get("sheets");
            for (int y = 0; y < sheets.size(); ++y) {
                JsonNode sheetNode = sheets.get(y);
                this.createSheet(cellBook, sheetGroups, sheetNode);
            }
        } else {
            this.createSheet(cellBook, null, node);
        }
        return cellBook;
    }

    private void createSheet(CellBook cellBook, List<SimpleCellSheet> sheetGroups, JsonNode sheetNode) {
        String code = null;
        String title = null;
        if (sheetNode.has("code")) {
            code = sheetNode.get("code").textValue();
        }
        if (sheetNode.has("title")) {
            title = sheetNode.get("title").textValue();
        }
        if (StringUtils.isEmpty(title)) {
            title = code;
        }
        if (StringUtils.isEmpty(code)) {
            code = title;
        }
        ArrayNode jsonRows = (ArrayNode)sheetNode.get(SHEET_ROWS);
        ArrayList<Row> rows = new ArrayList<Row>();
        for (int i = 0; i < jsonRows.size(); ++i) {
            JsonNode jsonNode = jsonRows.get(i);
            Row row = new Row();
            if (jsonNode.has("auto")) {
                row.setAuto(jsonNode.get("auto").asBoolean());
            }
            if (jsonNode.has("hidden")) {
                row.setHidden(jsonNode.get("hidden").asBoolean());
            }
            if (jsonNode.has("fold")) {
                row.setFold(jsonNode.get("fold").asBoolean());
            }
            row.setSize(jsonNode.get("size").asInt());
            rows.add(row);
        }
        ArrayNode jsonColumns = (ArrayNode)sheetNode.get(SHEET_COLS);
        ArrayList<Col> columns = new ArrayList<Col>();
        for (int i = 0; i < jsonColumns.size(); ++i) {
            JsonNode jsonNode = jsonColumns.get(i);
            Col col = new Col();
            if (jsonNode.has("auto")) {
                col.setAuto(jsonNode.get("auto").asBoolean());
            }
            if (jsonNode.has("hidden")) {
                col.setHidden(jsonNode.get("hidden").asBoolean());
            }
            if (jsonNode.has("fold")) {
                col.setFold(jsonNode.get("fold").asBoolean());
            }
            col.setSize(jsonNode.get("size").asInt());
            columns.add(col);
        }
        CellSheet cellSheet = cellBook.createSheet(title, code, rows.size(), columns.size());
        cellSheet.setRows(rows);
        cellSheet.setColumns(columns);
        if (sheetNode.has(SHEET_OPTIONS)) {
            JsonNode jsonOption = sheetNode.get(SHEET_OPTIONS);
            if (jsonOption.has(OPTION_SELECT)) {
                cellSheet.getOptions().setSelectMode(SelectionModel.forInt(jsonOption.get(OPTION_SELECT).asInt()));
            }
            if (jsonOption.has(OPTION_ENTER)) {
                cellSheet.getOptions().setEnterNext(EnterNext.forInt(jsonOption.get(OPTION_ENTER).asInt()));
            }
            if (jsonOption.has(OPTION_HIDDEN_SERIAL_NUMBER)) {
                boolean asBoolean = jsonOption.get(OPTION_HIDDEN_SERIAL_NUMBER).asBoolean();
                cellSheet.getOptions().setHiddenSerialNumberHeader(asBoolean);
            }
            if (jsonOption.has(OPTION_HEADER)) {
                JsonNode jsonHeader = jsonOption.get(OPTION_HEADER);
                if (jsonHeader.has(HEADER_COL)) {
                    cellSheet.getOptions().getHeader().setColumnHeader(jsonHeader.get(HEADER_COL).asInt());
                }
                if (jsonHeader.has(HEADER_ROW)) {
                    cellSheet.getOptions().getHeader().setRowHeader(jsonHeader.get(HEADER_ROW).asInt());
                }
            }
            if (jsonOption.has(OPTION_RESIZE)) {
                JsonNode jsonResize = jsonOption.get(OPTION_RESIZE);
                if (jsonResize.has(RESIZE_ROW)) {
                    cellSheet.getOptions().getResize().setRow(jsonResize.get(RESIZE_ROW).asBoolean());
                }
                if (jsonResize.has(RESIZE_COL)) {
                    cellSheet.getOptions().getResize().setColumn(jsonResize.get(RESIZE_COL).asBoolean());
                }
            }
        }
        HashMap<String, Point> mergeMap = new HashMap<String, Point>();
        if (sheetNode.has(SHEET_MERGE)) {
            ArrayNode jsonMerges = (ArrayNode)sheetNode.get(SHEET_MERGE);
            for (int i = 0; i < jsonMerges.size(); ++i) {
                JsonNode jsonMerge = jsonMerges.get(i);
                int rowIndex = jsonMerge.get(MERGE_ROW).asInt();
                int columnIndex = jsonMerge.get(MERGE_COL).asInt();
                int rowSpan = jsonMerge.get(MERGE_ROW_SPAN).asInt();
                int columnSpan = jsonMerge.get(MERGE_COL_SPAN).asInt();
                CellMerge cellMerge = new CellMerge(rowIndex, columnIndex, rowSpan, columnSpan);
                cellSheet.getMerges().add(cellMerge);
                Point point = new Point(rowIndex, columnIndex);
                for (int r = rowIndex; r < rowIndex + rowSpan; ++r) {
                    for (int c = columnIndex; c < columnIndex + columnSpan; ++c) {
                        mergeMap.put(r + "_" + c, point);
                    }
                }
            }
        }
        ArrayList<CellStyle> styles = new ArrayList<CellStyle>();
        ArrayNode jsonStyles = (ArrayNode)sheetNode.get(SHEET_STYLE);
        if (jsonStyles.size() != 0) {
            for (int i = 0; i < jsonStyles.size(); ++i) {
                JsonNode jsonStyle = jsonStyles.get(i);
                CellStyle style = new CellStyle(cellBook);
                this.readStyleOld(jsonStyle, style);
                styles.add(style);
            }
        }
        ArrayNode jsonDatas = (ArrayNode)sheetNode.get(SHEET_DATA);
        HashMap<Point, String> formatMap = new HashMap<Point, String>();
        for (int r = 0; r < jsonDatas.size(); ++r) {
            ArrayNode jsonRow = (ArrayNode)jsonDatas.get(r);
            for (int c = 0; c < jsonRow.size(); ++c) {
                String textValue;
                int styleIndex;
                JsonNode jsonCell = jsonRow.get(c);
                Cell cell = cellSheet.getCell(r, c);
                if (jsonCell.has(COMMON_DATA_TYPE)) {
                    String common = jsonCell.get(COMMON_DATA_TYPE).asText();
                    cell.setCommonDataType(CommonCellDataType.valueOf(common));
                }
                if (jsonCell.has(DATA_TYPE)) {
                    String dataType = jsonCell.get(DATA_TYPE).asText();
                    cell.setDataTypeId(dataType);
                    if (CellBookDataTypeFactory.isOldType(dataType)) {
                        cell.setCommonDataType(CommonCellDataType.STRING);
                        if ("4".equals(dataType) && jsonCell.has(DATA_VALUE)) {
                            cell.setFormula(jsonCell.get(DATA_VALUE).textValue());
                        }
                    }
                }
                if (jsonCell.has(DATA_FORMULA)) {
                    cell.setFormula(jsonCell.get(DATA_FORMULA).asText());
                }
                if (jsonCell.has(DATA_VALUE)) {
                    cell.setValue(jsonCell.get(DATA_VALUE).asText());
                }
                if (jsonCell.has(DATA_STYLE) && (styleIndex = jsonCell.get(DATA_STYLE).asInt()) >= 0) {
                    CellStyle cellStyle = (CellStyle)styles.get(styleIndex);
                    cell.setCellStyle((CellStyle)cellStyle.clone());
                }
                if (jsonCell.has(DATA_MERGE)) {
                    boolean merge = jsonCell.get(DATA_MERGE).asBoolean();
                    if (merge) {
                        cell.setMerged(merge);
                        Point point = (Point)((Point)mergeMap.get(r + "_" + c)).clone();
                        cell.setMergeInfo(point);
                    }
                } else {
                    Point point = (Point)mergeMap.get(r + "_" + c);
                    if (null != point) {
                        cell.setMerged(true);
                        cell.setMergeInfo((Point)point.clone());
                    }
                }
                if (jsonCell.has("formatter")) {
                    formatMap.put(new Point(r, c), jsonCell.get("formatter").textValue());
                }
                if (!jsonCell.has(DATA_PERSISTENCE) || StringUtils.isEmpty(textValue = jsonCell.get(DATA_PERSISTENCE).textValue())) continue;
                try {
                    cell.setPersistenceData(textValue);
                    continue;
                }
                catch (Exception e) {
                    LOGGER.error("\u5355\u5143\u683c\u6269\u5c55\u4fe1\u606f\u8bbe\u7f6e\u9519\u8bef", e);
                }
            }
        }
        Set entrySet = formatMap.entrySet();
        for (Map.Entry entry : entrySet) {
            Point key = (Point)entry.getKey();
            cellSheet.getCell(key.getX(), key.getY()).setFormatter((String)entry.getValue());
        }
    }

    private String handlerColor(String color) {
        if (null != color && (color = color.replaceAll("\\s*", "").toUpperCase()).length() > 0) {
            if (color.startsWith("RGB")) {
                int indexOf = color.indexOf(",");
                int indexOf2 = color.indexOf(",", indexOf + 1);
                int r = Integer.valueOf(color.substring(color.indexOf("(") + 1, indexOf));
                int g = Integer.valueOf(color.substring(indexOf + 1, indexOf2));
                int b = Integer.valueOf(color.substring(indexOf2 + 1, color.indexOf(")")));
                return new CellColor(r, g, b).getHexString();
            }
            return color.substring(1, color.length());
        }
        return "";
    }

    private void readStyleOld(JsonNode jsonStyle, CellStyle style) {
        if (null != jsonStyle) {
            String textValue;
            JsonNode backJson;
            JsonNode diagonalDownJson;
            JsonNode diagonalUpJson;
            JsonNode topJson;
            JsonNode leftJson;
            JsonNode bottomJson;
            JsonNode rightJson;
            JsonNode jsonNode2;
            JsonNode fontJson;
            if (jsonStyle.has("model")) {
                style.setModel(CellStyleModel.forInt(jsonStyle.get("model").asInt()));
            }
            if (jsonStyle.has(STYLE_HORIZONTAL_ALIGNMENT)) {
                style.setHorizontalAlignment(HorizontalAlignment.forInt(jsonStyle.get(STYLE_HORIZONTAL_ALIGNMENT).asInt()));
            }
            if (jsonStyle.has(STYLE_VERTIACL_ALIGNMENT)) {
                style.setVerticalAlignment(VerticalAlignment.forInt(jsonStyle.get(STYLE_VERTIACL_ALIGNMENT).asInt()));
            }
            if ((fontJson = jsonStyle).has("fontName")) {
                style.setFontName(fontJson.get("fontName").textValue());
            }
            if (fontJson.has("fontSize")) {
                style.setFontSize(fontJson.get("fontSize").asInt());
            }
            if (fontJson.has("fontColor")) {
                jsonNode2 = fontJson.get("fontColor");
                if (jsonNode2.canConvertToInt()) {
                    style.setFontColorIndex(jsonNode2.asInt());
                } else {
                    style.setFontColor(this.handlerColor(jsonNode2.textValue()));
                }
            }
            if (fontJson.has(STYLE_BOLD)) {
                style.setBold(fontJson.get(STYLE_BOLD).asBoolean());
            }
            if (fontJson.has(STYLE_ITALIC)) {
                style.setItalic(fontJson.get(STYLE_ITALIC).asBoolean());
            }
            if (fontJson.has(STYLE_UNDERLINE)) {
                style.setUnderline(fontJson.get(STYLE_UNDERLINE).asBoolean());
            }
            if (fontJson.has(STYLE_INLINE)) {
                style.setInline(fontJson.get(STYLE_INLINE).asBoolean());
            }
            if (fontJson.has(STYLE_INDENT)) {
                style.setIndent(fontJson.get(STYLE_INDENT).asInt());
            }
            if (fontJson.has(OLD_STYLE_FIT_FONT_SIZE)) {
                style.setFitFontSize(fontJson.get(OLD_STYLE_FIT_FONT_SIZE).asBoolean());
            }
            if (fontJson.has(STYLE_WRAPLINE)) {
                style.setWrapLine(fontJson.get(STYLE_WRAPLINE).asBoolean());
            }
            if ((rightJson = jsonStyle).has(OLD_STYLE_RIGHT_BORDER_COLOR)) {
                jsonNode2 = rightJson.get(OLD_STYLE_RIGHT_BORDER_COLOR);
                if (jsonNode2.canConvertToInt()) {
                    style.setRightBorderColorIndex(jsonNode2.asInt());
                } else {
                    style.setRightBorderColor(this.handlerColor(jsonNode2.textValue()));
                }
            }
            if (rightJson.has(OLD_STYLE_RIGHT_BORDER_STYLE)) {
                style.setRightBorderStyle(CellBorderStyle.forInt(rightJson.get(OLD_STYLE_RIGHT_BORDER_STYLE).asInt()));
            }
            if ((bottomJson = jsonStyle).has(OLD_STYLE_BOTTOM_BORDER_COLOR)) {
                JsonNode jsonNode22 = bottomJson.get(OLD_STYLE_BOTTOM_BORDER_COLOR);
                if (jsonNode22.canConvertToInt()) {
                    style.setBottomBorderColorIndex(jsonNode22.asInt());
                } else {
                    style.setBottomBorderColor(this.handlerColor(jsonNode22.textValue()));
                }
            }
            if (bottomJson.has(OLD_STYLE_BOTTOM_BORDER_STYLE)) {
                style.setBottomBorderStyle(CellBorderStyle.forInt(bottomJson.get(OLD_STYLE_BOTTOM_BORDER_STYLE).asInt()));
            }
            if ((leftJson = jsonStyle).has(OLD_STYLE_LEFT_BORDER_COLOR)) {
                JsonNode jsonNode23 = leftJson.get(OLD_STYLE_LEFT_BORDER_COLOR);
                if (jsonNode23.canConvertToInt()) {
                    style.setLeftBorderColorIndex(jsonNode23.asInt());
                } else {
                    style.setLeftBorderColor(this.handlerColor(jsonNode23.textValue()));
                }
            }
            if (leftJson.has(OLD_STYLE_LEFT_BORDER_STYLE)) {
                style.setLeftBorderStyle(CellBorderStyle.forInt(leftJson.get(OLD_STYLE_LEFT_BORDER_STYLE).asInt()));
            }
            if ((topJson = jsonStyle).has(OLD_STYLE_TOP_BORDER_COLOR)) {
                JsonNode jsonNode24 = topJson.get(OLD_STYLE_TOP_BORDER_COLOR);
                if (jsonNode24.canConvertToInt()) {
                    style.setTopBorderColorIndex(jsonNode24.asInt());
                } else {
                    style.setTopBorderColor(this.handlerColor(jsonNode24.textValue()));
                }
            }
            if (topJson.has(OLD_STYLE_TOP_BORDER_STYLE)) {
                style.setTopBorderStyle(CellBorderStyle.forInt(topJson.get(OLD_STYLE_TOP_BORDER_STYLE).asInt()));
            }
            if ((diagonalUpJson = jsonStyle).has(OLD_STYLE_DIAGONAL_UP_COLOR)) {
                JsonNode jsonNode25 = diagonalUpJson.get(OLD_STYLE_DIAGONAL_UP_COLOR);
                if (jsonNode25.canConvertToInt()) {
                    style.setDiagonalUpColorIndex(jsonNode25.asInt());
                } else {
                    style.setDiagonalUpColor(this.handlerColor(jsonNode25.textValue()));
                }
            }
            if (diagonalUpJson.has(OLD_STYLE_DIAGONAL_UP_STYLE)) {
                style.setDiagonalUpStyle(CellBorderStyle.forInt(diagonalUpJson.get(OLD_STYLE_DIAGONAL_UP_STYLE).asInt()));
            }
            if ((diagonalDownJson = jsonStyle).has(OLD_STYLE_DIAGONAL_DOWN_COLOR)) {
                JsonNode jsonNode26 = diagonalDownJson.get(OLD_STYLE_DIAGONAL_DOWN_COLOR);
                if (jsonNode26.canConvertToInt()) {
                    style.setDiagonalDownColorIndex(jsonNode26.asInt());
                } else {
                    style.setDiagonalDownColor(this.handlerColor(jsonNode26.textValue()));
                }
            }
            if (diagonalDownJson.has(OLD_STYLE_DIAGONAL_DOWN_STYLE)) {
                style.setDiagonalDownStyle(CellBorderStyle.forInt(diagonalDownJson.get(OLD_STYLE_DIAGONAL_DOWN_STYLE).asInt()));
            }
            if ((backJson = jsonStyle).has("fillPatternType")) {
                style.setFillPatternType(FillPatternType.forInt(backJson.get("fillPatternType").asInt()));
            }
            if (backJson.has("backGroundColor")) {
                jsonNode2 = backJson.get("backGroundColor");
                if (jsonNode2.canConvertToInt()) {
                    style.setBackGroundColorIndex(jsonNode2.asInt());
                } else {
                    style.setBackGroundColor(this.handlerColor(jsonNode2.textValue()));
                }
            }
            if (backJson.has("backGroundImg") && StringUtils.isNotEmpty(textValue = backJson.get("backGroundImg").textValue())) {
                style.setBackGroundImg(textValue);
            }
        }
    }
}


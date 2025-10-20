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
package com.jiuqi.nvwa.cellbook.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.jiuqi.nvwa.cellbook.json.old.CellBookDeserializeFactory;
import com.jiuqi.nvwa.cellbook.model.BookStyle;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBackGround;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellBorders;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellFont;
import com.jiuqi.nvwa.cellbook.model.CellFormatter;
import com.jiuqi.nvwa.cellbook.model.CellLayout;
import com.jiuqi.nvwa.cellbook.model.CellMerge;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.cellbook.model.CellStyle;
import com.jiuqi.nvwa.cellbook.model.CellTheme;
import com.jiuqi.nvwa.cellbook.model.CellThemes;
import com.jiuqi.nvwa.cellbook.model.Col;
import com.jiuqi.nvwa.cellbook.model.DocProps;
import com.jiuqi.nvwa.cellbook.model.Point;
import com.jiuqi.nvwa.cellbook.model.Row;
import com.jiuqi.nvwa.cellbook.model.SimpleCellSheet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellBookDeserialize
extends JsonDeserializer<CellBook> {
    public static final Logger LOGGER = LoggerFactory.getLogger(CellBookDeserialize.class);

    public CellBook deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        CellBook cellBook = new CellBook();
        ObjectMapper mapper = (ObjectMapper)p.getCodec();
        JsonNode node = (JsonNode)mapper.readTree(p);
        boolean oldVersion = CellBookDeserializeFactory.isOldVersion(node);
        if (oldVersion) {
            CellBookDeserializeFactory.deserialize(cellBook, node);
            cellBook.getDocProps().setVersion("2.0.0");
        } else {
            if (node.has("docProps")) {
                this.readDoc(cellBook, node);
                cellBook.getDocProps().setVersion("2.0.0");
            }
            ArrayList<SimpleCellSheet> sheetGroups = new ArrayList<SimpleCellSheet>();
            if (node.has("sheetGroups")) {
                this.readGroups(cellBook, sheetGroups, node);
            }
            if (node.has("theme")) {
                this.readTheme(cellBook, node);
            }
            if (node.has("styles")) {
                JsonNode jsonNode = node.get("styles");
                if (jsonNode.has("cellStyles")) {
                    JsonNode jsonStyle = jsonNode.get("cellStyles");
                    CellStyle style = cellBook.getBookStyle().getDefaultStyle();
                    this.readDefaultStytle(jsonStyle, style);
                }
                BookStyle bookStyle = cellBook.getBookStyle();
                if (jsonNode.has("selector")) {
                    bookStyle.setSelector(jsonNode.get("selector").toString());
                }
                if (jsonNode.has("serialNumberHeader")) {
                    bookStyle.setSerialNumberHeader(jsonNode.get("serialNumberHeader").toString());
                }
                if (jsonNode.has("splitLine")) {
                    bookStyle.setSplitLine(jsonNode.get("splitLine").toString());
                }
                if (jsonNode.has("padding")) {
                    bookStyle.setPadding(jsonNode.get("padding").toString());
                }
            }
            if (node.has("sheets")) {
                ArrayNode sheets = (ArrayNode)node.get("sheets");
                for (int y = 0; y < sheets.size(); ++y) {
                    JsonNode sheetNode = sheets.get(y);
                    this.readSheet(cellBook, sheetGroups, sheetNode);
                }
            }
        }
        return cellBook;
    }

    private void readDoc(CellBook cellBook, JsonNode node) {
        JsonNode docProps = node.get("docProps");
        if (null != docProps) {
            DocProps docProp = new DocProps();
            cellBook.setDocProps(docProp);
            if (docProps.has("version")) {
                cellBook.getDocProps().setVersion(docProps.get("version").textValue());
            }
            if (docProps.has("encoding")) {
                cellBook.getDocProps().setEncoding(docProps.get("encoding").textValue());
            }
            if (docProps.has("creator")) {
                cellBook.getDocProps().setCreator(docProps.get("creator").textValue());
            }
            if (docProps.has("created")) {
                cellBook.getDocProps().setCreated(docProps.get("created").numberValue().longValue());
            }
            if (docProps.has("modifior")) {
                cellBook.getDocProps().setModifior(docProps.get("modifior").textValue());
            }
            if (docProps.has("modified")) {
                cellBook.getDocProps().setModified(docProps.get("modified").numberValue().longValue());
            }
            if (docProps.has("company")) {
                cellBook.getDocProps().setCompany(docProps.get("company").textValue());
            }
            if (docProps.has("docSecurity")) {
                cellBook.getDocProps().setDocSecurity(docProps.get("docSecurity").textValue());
            }
            if (docProps.has("mode")) {
                boolean isInt = docProps.get("mode").isInt();
                if (isInt) {
                    cellBook.getDocProps().setModel(DocModel.forInt(docProps.get("mode").asInt()));
                } else {
                    cellBook.getDocProps().setModel(DocModel.valueOf(docProps.get("mode").textValue()));
                }
            }
        }
    }

    private void readGroups(CellBook cellBook, List<SimpleCellSheet> sheetGroups, JsonNode node) {
        JsonNode jsonNode = node.get("sheetGroups");
        if (null != jsonNode) {
            String jsonName;
            String jsonTitle;
            int i;
            ArrayNode jsonPalette;
            if (jsonNode.has("groups") && null != (jsonPalette = (ArrayNode)jsonNode.get("groups"))) {
                for (i = 0; i < jsonPalette.size(); ++i) {
                    JsonNode group = jsonPalette.get(i);
                    jsonTitle = group.get("title").textValue();
                    jsonName = group.get("name").textValue();
                    cellBook.createSheetGroup(jsonTitle, jsonName);
                }
            }
            if (jsonNode.has("sheets") && null != (jsonPalette = (ArrayNode)jsonNode.get("sheets"))) {
                for (i = 0; i < jsonPalette.size(); ++i) {
                    JsonNode sheetBean = jsonPalette.get(i);
                    jsonTitle = sheetBean.get("title").textValue();
                    jsonName = sheetBean.get("name").textValue();
                    String groupCode = null;
                    if (sheetBean.has("groupName")) {
                        groupCode = sheetBean.get("groupName").textValue();
                    }
                    SimpleCellSheet simpleCellSheet = new SimpleCellSheet(jsonName, jsonTitle, groupCode);
                    sheetGroups.add(simpleCellSheet);
                }
            }
        }
    }

    private void readTheme(CellBook cellBook, JsonNode node) {
        CellThemes theme = cellBook.getTheme();
        JsonNode jsonNode = node.get("theme");
        if (jsonNode.has("current")) {
            theme.setCurrent(jsonNode.get("current").textValue());
        }
        if (jsonNode.has("themes")) {
            JsonNode jsonThemes = jsonNode.get("themes");
            Iterator fields = jsonThemes.fields();
            while (fields.hasNext()) {
                CellTheme cellTheme = new CellTheme();
                Map.Entry next = (Map.Entry)fields.next();
                String themeName = (String)next.getKey();
                JsonNode themeJson = (JsonNode)next.getValue();
                if (themeJson.has("palette")) {
                    ArrayList<CellColor> palette = new ArrayList<CellColor>();
                    ArrayNode jsonPalette = (ArrayNode)themeJson.get("palette");
                    for (int i = 0; i < jsonPalette.size(); ++i) {
                        JsonNode cellColor = jsonPalette.get(i);
                        String title = "";
                        if (cellColor.has("title")) {
                            title = cellColor.get("title").textValue();
                        }
                        String textValue = cellColor.get("value").textValue();
                        CellColor oneCellColor = new CellColor(this.handlerColor(textValue), title);
                        palette.add(oneCellColor);
                    }
                    cellTheme.setPalette(palette);
                }
                theme.getThemes().put(themeName, cellTheme);
            }
        }
    }

    private void readSheet(CellBook cellBook, List<SimpleCellSheet> sheetGroups, JsonNode sheetNode) {
        ArrayNode jsonStyles;
        JsonNode jsonStyle;
        String name = sheetNode.get("name").textValue();
        Optional<SimpleCellSheet> findFirst = sheetGroups.stream().filter(sheet -> sheet.getName().equals(name)).findFirst();
        SimpleCellSheet simpleCellSheet = findFirst.get();
        ArrayNode jsonRows = (ArrayNode)sheetNode.get("rows");
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
        ArrayNode jsonColumns = (ArrayNode)sheetNode.get("columns");
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
        CellSheet cellSheet = cellBook.createSheet(simpleCellSheet.getTitle(), name, rows.size(), columns.size(), simpleCellSheet.getGroupCode());
        cellSheet.setRows(rows);
        cellSheet.setColumns(columns);
        if (sheetNode.has("options")) {
            JsonNode jsonOption = sheetNode.get("options");
            if (jsonOption.has("selectMode")) {
                cellSheet.getOptions().setSelectMode(SelectionModel.forInt(jsonOption.get("selectMode").asInt()));
            }
            if (jsonOption.has("enterNext")) {
                cellSheet.getOptions().setEnterNext(EnterNext.forInt(jsonOption.get("enterNext").asInt()));
            }
            if (jsonOption.has("hiddenSerialNumberHeader")) {
                boolean asBoolean = jsonOption.get("hiddenSerialNumberHeader").asBoolean();
                cellSheet.getOptions().setHiddenSerialNumberHeader(asBoolean);
            }
            if (jsonOption.has("header")) {
                JsonNode jsonHeader = jsonOption.get("header");
                if (jsonHeader.has("columnHeader")) {
                    cellSheet.getOptions().getHeader().setColumnHeader(jsonHeader.get("columnHeader").asInt());
                }
                if (jsonHeader.has("rowHeader")) {
                    cellSheet.getOptions().getHeader().setRowHeader(jsonHeader.get("rowHeader").asInt());
                }
            }
            if (jsonOption.has("resize")) {
                JsonNode jsonResize = jsonOption.get("resize");
                if (jsonResize.has("row")) {
                    cellSheet.getOptions().getResize().setRow(jsonResize.get("row").asBoolean());
                }
                if (jsonResize.has("column")) {
                    cellSheet.getOptions().getResize().setColumn(jsonResize.get("column").asBoolean());
                }
            }
        }
        HashMap<String, Point> mergeMap = new HashMap<String, Point>();
        if (sheetNode.has("mergeInfo")) {
            ArrayNode jsonMerges = (ArrayNode)sheetNode.get("mergeInfo");
            for (int i = 0; i < jsonMerges.size(); ++i) {
                ArrayNode jsonMerge = (ArrayNode)jsonMerges.get(i);
                int rowIndex = jsonMerge.get(0).asInt();
                int columnIndex = jsonMerge.get(1).asInt();
                int rowSpan = jsonMerge.get(2).asInt();
                int columnSpan = jsonMerge.get(3).asInt();
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
        if (sheetNode.has("style") && (jsonStyle = sheetNode.get("style")).has("style") && (jsonStyles = (ArrayNode)jsonStyle.get("style")).size() != 0) {
            ArrayList<CellFormatter> numFmts = new ArrayList<CellFormatter>();
            if (jsonStyle.has("numFmts")) {
                ArrayNode jsonNumFmts = (ArrayNode)jsonStyle.get("numFmts");
                for (int i = 0; i < jsonNumFmts.size(); ++i) {
                    JsonNode numFmtJson = jsonNumFmts.get(i);
                    numFmts.add(this.readFmt(numFmtJson));
                }
            }
            ArrayList<CellFont> fonts = new ArrayList<CellFont>();
            if (jsonStyle.has("fonts")) {
                ArrayNode jsonFonts = (ArrayNode)jsonStyle.get("fonts");
                for (int i = 0; i < jsonFonts.size(); ++i) {
                    JsonNode fontJson = jsonFonts.get(i);
                    fonts.add(this.readFont(fontJson, cellBook, cellBook.getBookStyle().getDefaultStyle().getFont()));
                }
            }
            ArrayList<CellBackGround> backGrounds = new ArrayList<CellBackGround>();
            if (jsonStyle.has("fills")) {
                ArrayNode jsonFonts = (ArrayNode)jsonStyle.get("fills");
                for (int i = 0; i < jsonFonts.size(); ++i) {
                    JsonNode backJson = jsonFonts.get(i);
                    backGrounds.add(this.readBackGround(cellBook, backJson));
                }
            }
            ArrayList<CellBorders> cellBorders = new ArrayList<CellBorders>();
            if (jsonStyle.has("borders")) {
                ArrayNode jsonFonts = (ArrayNode)jsonStyle.get("borders");
                for (int i = 0; i < jsonFonts.size(); ++i) {
                    JsonNode backJson = jsonFonts.get(i);
                    cellBorders.add(this.readBorder(backJson, cellBook));
                }
            }
            ArrayList<CellLayout> cellLayouts = new ArrayList<CellLayout>();
            if (jsonStyle.has("layouts")) {
                ArrayNode jsonFonts = (ArrayNode)jsonStyle.get("layouts");
                for (int i = 0; i < jsonFonts.size(); ++i) {
                    JsonNode backJson = jsonFonts.get(i);
                    cellLayouts.add(this.readLayout(backJson));
                }
            }
            for (int i = 0; i < jsonStyles.size(); ++i) {
                int asInt;
                JsonNode oneJson = jsonStyles.get(i);
                CellStyle style = new CellStyle(cellBook);
                if (oneJson.has("mode")) {
                    style.setModel(CellStyleModel.forInt(oneJson.get("mode").asInt()));
                }
                if (oneJson.has("numFmt") && (asInt = oneJson.get("numFmt").asInt()) >= 0) {
                    style.setFormatter((CellFormatter)numFmts.get(asInt));
                }
                if (oneJson.has("font") && (asInt = oneJson.get("font").asInt()) >= 0) {
                    style.setFont((CellFont)fonts.get(asInt));
                }
                if (oneJson.has("fill") && (asInt = oneJson.get("fill").asInt()) >= 0) {
                    style.setBackGround((CellBackGround)backGrounds.get(asInt));
                }
                if (oneJson.has("border") && (asInt = oneJson.get("border").asInt()) >= 0) {
                    style.setBorders((CellBorders)cellBorders.get(asInt));
                }
                if (oneJson.has("layout") && (asInt = oneJson.get("layout").asInt()) >= 0) {
                    style.setLayout((CellLayout)cellLayouts.get(asInt));
                }
                styles.add(style);
            }
        }
        ArrayNode jsonDatas = (ArrayNode)sheetNode.get("data");
        for (int r = 0; r < jsonDatas.size(); ++r) {
            ArrayNode jsonRow = (ArrayNode)jsonDatas.get(r);
            for (int c = 0; c < jsonRow.size(); ++c) {
                String textValue;
                Point point;
                int styleIndex;
                JsonNode jsonCell = jsonRow.get(c);
                Cell cell = cellSheet.getCell(r, c);
                if (jsonCell.has("b")) {
                    String common = jsonCell.get("b").asText();
                    cell.setCommonDataType(CommonCellDataType.getOpenMoreType(common));
                }
                if (jsonCell.has("t")) {
                    String dataType = jsonCell.get("t").asText();
                    cell.setDataTypeId(dataType);
                    if (CellBookDataTypeFactory.isOldType(dataType)) {
                        cell.setCommonDataType(CommonCellDataType.STRING);
                        if ("4".equals(dataType) && jsonCell.has("v")) {
                            cell.setFormula(jsonCell.get("v").textValue());
                        }
                    }
                }
                if (jsonCell.has("v")) {
                    cell.setValue(jsonCell.get("v").asText());
                }
                if (jsonCell.has("showText")) {
                    cell.setShowText(jsonCell.get("showText").textValue());
                }
                if (jsonCell.has("formula")) {
                    cell.setFormula(jsonCell.get("formula").asText());
                }
                if (jsonCell.has("s") && (styleIndex = jsonCell.get("s").asInt()) >= 0) {
                    CellStyle cellStyle = (CellStyle)styles.get(styleIndex);
                    cell.setCellStyle((CellStyle)cellStyle.clone());
                }
                if (null != (point = (Point)mergeMap.get(r + "_" + c))) {
                    cell.setMerged(true);
                    cell.setMergeInfo((Point)point.clone());
                }
                if (!jsonCell.has("exData") || StringUtils.isEmpty(textValue = jsonCell.get("exData").textValue())) continue;
                try {
                    cell.setPersistenceData(textValue);
                    continue;
                }
                catch (Exception e) {
                    LOGGER.error("\u5355\u5143\u683c\u6269\u5c55\u4fe1\u606f\u8bbe\u7f6e\u9519\u8bef", e);
                }
            }
        }
    }

    private void readDefaultStytle(JsonNode jsonStyle, CellStyle style) {
        JsonNode json;
        if (jsonStyle.has("mode")) {
            style.setModel(CellStyleModel.forInt(jsonStyle.get("mode").asInt()));
        }
        if (jsonStyle.has("font")) {
            JsonNode fontJson = jsonStyle.get("font");
            CellFont font = this.readFont(fontJson, style.get_cellBook(), null);
            style.setFont(font);
        }
        if (jsonStyle.has("layout")) {
            json = jsonStyle.get("layout");
            CellLayout layout = this.readLayout(json);
            style.setLayout(layout);
        }
        if (jsonStyle.has("border")) {
            json = jsonStyle.get("border");
            CellBorders readBorder = this.readBorder(json, style.get_cellBook());
            style.setBorders(readBorder);
        }
        if (jsonStyle.has("fill")) {
            JsonNode backJson = jsonStyle.get("fill");
            CellBackGround backGround = this.readBackGround(style.get_cellBook(), backJson);
            style.setBackGround(backGround);
        }
        if (jsonStyle.has("numFmt")) {
            json = jsonStyle.get("numFmt");
            CellFormatter readFmt = this.readFmt(json);
            style.setFormatter(readFmt);
        }
    }

    private CellFormatter readFmt(JsonNode json) {
        CellFormatter formatter = new CellFormatter();
        if (json.has("v")) {
            String textValue = json.get("v").textValue();
            formatter.setFormatCode(textValue);
        }
        return formatter;
    }

    private CellBackGround readBackGround(CellBook cellBook, JsonNode backJson) {
        String textValue;
        CellBackGround backGround = new CellBackGround(cellBook);
        if (backJson.has("t")) {
            backGround.setFillPatternType(FillPatternType.forInt(backJson.get("t").asInt()));
        }
        if (backJson.has("c")) {
            JsonNode jsonNode2 = backJson.get("c");
            if (jsonNode2.canConvertToInt()) {
                backGround.setColorIndex(jsonNode2.asInt());
            } else {
                backGround.setColor(this.handlerColor(jsonNode2.textValue()));
            }
        }
        if (backJson.has("i") && StringUtils.isNotEmpty(textValue = backJson.get("i").textValue())) {
            backGround.setBackGroundImg(textValue);
        }
        return backGround;
    }

    private CellBorders readBorder(JsonNode json, CellBook cellBook) {
        JsonNode jsonNode2;
        CellBorders borders = new CellBorders(cellBook);
        if (json.has("rbc")) {
            jsonNode2 = json.get("rbc");
            if (jsonNode2.canConvertToInt()) {
                borders.getRight().setColorIndex(jsonNode2.asInt());
            } else {
                borders.getRight().setColor(this.handlerColor(jsonNode2.textValue()));
            }
        }
        if (json.has("rbs")) {
            borders.getRight().setStyle(CellBorderStyle.forInt(json.get("rbs").asInt()));
        }
        if (json.has("bbc")) {
            jsonNode2 = json.get("bbc");
            if (jsonNode2.canConvertToInt()) {
                borders.getBottom().setColorIndex(jsonNode2.asInt());
            } else {
                borders.getBottom().setColor(this.handlerColor(jsonNode2.textValue()));
            }
        }
        if (json.has("bbs")) {
            borders.getBottom().setStyle(CellBorderStyle.forInt(json.get("bbs").asInt()));
        }
        if (json.has("lbc")) {
            jsonNode2 = json.get("lbc");
            if (jsonNode2.canConvertToInt()) {
                borders.getLeft().setColorIndex(jsonNode2.asInt());
            } else {
                borders.getLeft().setColor(this.handlerColor(jsonNode2.textValue()));
            }
        }
        if (json.has("lbs")) {
            borders.getLeft().setStyle(CellBorderStyle.forInt(json.get("lbs").asInt()));
        }
        if (json.has("tbc")) {
            jsonNode2 = json.get("tbc");
            if (jsonNode2.canConvertToInt()) {
                borders.getTop().setColorIndex(jsonNode2.asInt());
            } else {
                borders.getTop().setColor(this.handlerColor(jsonNode2.textValue()));
            }
        }
        if (json.has("tbs")) {
            borders.getTop().setStyle(CellBorderStyle.forInt(json.get("tbs").asInt()));
        }
        if (json.has("duc")) {
            jsonNode2 = json.get("duc");
            if (jsonNode2.canConvertToInt()) {
                borders.getDiagonalUp().setColorIndex(jsonNode2.asInt());
            } else {
                borders.getDiagonalUp().setColor(this.handlerColor(jsonNode2.textValue()));
            }
        }
        if (json.has("dus")) {
            borders.getDiagonalUp().setStyle(CellBorderStyle.forInt(json.get("dus").asInt()));
        }
        if (json.has("ddc")) {
            jsonNode2 = json.get("ddc");
            if (jsonNode2.canConvertToInt()) {
                borders.getDiagonalDown().setColorIndex(jsonNode2.asInt());
            } else {
                borders.getDiagonalDown().setColor(this.handlerColor(jsonNode2.textValue()));
            }
        }
        if (json.has("dds")) {
            borders.getDiagonalDown().setStyle(CellBorderStyle.forInt(json.get("dds").asInt()));
        }
        return borders;
    }

    private CellLayout readLayout(JsonNode json) {
        CellLayout layout = new CellLayout();
        if (json.has("i")) {
            layout.setIndent(json.get("i").asInt());
        }
        if (json.has("fs")) {
            layout.setFitSize(json.get("fs").asBoolean());
        }
        if (json.has("wl")) {
            layout.setWrapLine(json.get("wl").asBoolean());
        }
        if (json.has("h")) {
            layout.setHorizontalAlignment(HorizontalAlignment.forInt(json.get("h").asInt()));
        }
        if (json.has("v")) {
            layout.setVerticalAlignment(VerticalAlignment.forInt(json.get("v").asInt()));
        }
        if (json.has("r")) {
            layout.setTextRotation(json.get("r").asInt());
        }
        return layout;
    }

    private CellFont readFont(JsonNode fontJson, CellBook cellBook, CellFont defaultFont) {
        CellFont font = new CellFont(cellBook);
        if (fontJson.has("n")) {
            font.setName(fontJson.get("n").textValue());
        } else if (null != defaultFont && !font.getName().equals(defaultFont.getName())) {
            font.setName(defaultFont.getName());
        }
        if (fontJson.has("s")) {
            font.setSizeF((float)fontJson.get("s").asDouble());
        } else if (null != defaultFont && font.getSizeF() != defaultFont.getSizeF()) {
            font.setSizeF(defaultFont.getSizeF());
        }
        if (fontJson.has("c")) {
            JsonNode jsonNode2 = fontJson.get("c");
            if (jsonNode2.canConvertToInt()) {
                font.setColorIndex(jsonNode2.asInt());
            } else {
                font.setColor(this.handlerColor(jsonNode2.textValue()));
            }
        }
        if (fontJson.has("b")) {
            font.setBold(fontJson.get("b").asBoolean());
        }
        if (fontJson.has("i")) {
            font.setItalic(fontJson.get("i").asBoolean());
        }
        if (fontJson.has("ul")) {
            font.setUnderline(fontJson.get("ul").asBoolean());
        }
        if (fontJson.has("il")) {
            font.setInline(fontJson.get("il").asBoolean());
        }
        return font;
    }

    private String handlerColor(String color) {
        if (null != color && (color = color.replaceAll("\\s*", "").toUpperCase()).length() > 0) {
            if (color.startsWith("RGBA")) {
                int indexOf = color.indexOf(",");
                int indexOf2 = color.indexOf(",", indexOf + 1);
                int indexOf3 = color.indexOf(",", indexOf2 + 1);
                int r = Integer.parseInt(color.substring(color.indexOf("(") + 1, indexOf));
                int g = Integer.parseInt(color.substring(indexOf + 1, indexOf2));
                int b = Integer.parseInt(color.substring(indexOf2 + 1, indexOf3));
                int a = (int)Math.round(255.0 * Double.parseDouble(color.substring(indexOf3 + 1, color.indexOf(")"))));
                return new CellColor(r, g, b, a).getHexString();
            }
            if (color.startsWith("RGB")) {
                int indexOf = color.indexOf(",");
                int indexOf2 = color.indexOf(",", indexOf + 1);
                int r = Integer.parseInt(color.substring(color.indexOf("(") + 1, indexOf));
                int g = Integer.parseInt(color.substring(indexOf + 1, indexOf2));
                int b = Integer.parseInt(color.substring(indexOf2 + 1, color.indexOf(")")));
                return new CellColor(r, g, b).getHexString();
            }
            if (color.startsWith("#")) {
                return color.substring(1);
            }
            if (!"TRANSPARENT".equals(color)) {
                throw new RuntimeException("\u4e0d\u652f\u6301\u7684\u989c\u8272\u503c\uff01color:" + color);
            }
        }
        return "";
    }

    public Class<CellBook> handledType() {
        return CellBook.class;
    }
}


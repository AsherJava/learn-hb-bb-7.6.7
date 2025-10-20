/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.cellbook.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.nvwa.cellbook.constant.CellStyleModel;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import com.jiuqi.nvwa.cellbook.model.BookStyle;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBackGround;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellBorder;
import com.jiuqi.nvwa.cellbook.model.CellBorders;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellFont;
import com.jiuqi.nvwa.cellbook.model.CellFormatter;
import com.jiuqi.nvwa.cellbook.model.CellLayout;
import com.jiuqi.nvwa.cellbook.model.CellMerge;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.cellbook.model.CellSheetGroup;
import com.jiuqi.nvwa.cellbook.model.CellStyle;
import com.jiuqi.nvwa.cellbook.model.CellTheme;
import com.jiuqi.nvwa.cellbook.model.CellThemes;
import com.jiuqi.nvwa.cellbook.model.Col;
import com.jiuqi.nvwa.cellbook.model.DocProps;
import com.jiuqi.nvwa.cellbook.model.Header;
import com.jiuqi.nvwa.cellbook.model.Options;
import com.jiuqi.nvwa.cellbook.model.Resize;
import com.jiuqi.nvwa.cellbook.model.Row;
import com.jiuqi.nvwa.cellbook.model.SimpleCellStyle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class CellBookSerialize
extends JsonSerializer<CellBook> {
    public void serialize(CellBook cellBook, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        this.writeDoc(cellBook, gen);
        this.writeGroups(cellBook, gen);
        this.writeTheme(cellBook, gen);
        this.writeDefaultStyle(cellBook, gen);
        this.writeSheets(cellBook, gen);
        gen.writeEndObject();
    }

    private void writeSheets(CellBook cellBook, JsonGenerator gen) throws IOException {
        CellStyle defaultStyle = cellBook.getBookStyle().getDefaultStyle();
        List<CellSheet> sheets = cellBook.getSheets();
        gen.writeFieldName("sheets");
        gen.writeStartArray();
        for (CellSheet cellSheet : sheets) {
            gen.writeStartObject();
            this.writeSheet(gen, defaultStyle, cellSheet);
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }

    private void writeSheet(JsonGenerator gen, CellStyle bookStyle, CellSheet cellSheet) throws IOException {
        Resize resize;
        gen.writeStringField("name", cellSheet.getName());
        List<Row> rows = cellSheet.getRows();
        gen.writeFieldName("rows");
        gen.writeStartArray();
        for (Row row : rows) {
            gen.writeStartObject();
            gen.writeNumberField("size", row.getSize());
            if (row.isAuto()) {
                gen.writeBooleanField("auto", row.isAuto());
            }
            if (row.isHidden()) {
                gen.writeBooleanField("hidden", row.isHidden());
            }
            if (row.isFold()) {
                gen.writeBooleanField("fold", row.isFold());
            }
            gen.writeEndObject();
        }
        gen.writeEndArray();
        List<Col> columns = cellSheet.getColumns();
        gen.writeFieldName("columns");
        gen.writeStartArray();
        for (Col col : columns) {
            gen.writeStartObject();
            gen.writeNumberField("size", col.getSize());
            if (col.isAuto()) {
                gen.writeBooleanField("auto", col.isAuto());
            }
            if (col.isHidden()) {
                gen.writeBooleanField("hidden", col.isHidden());
            }
            if (col.isFold()) {
                gen.writeBooleanField("fold", col.isFold());
            }
            gen.writeEndObject();
        }
        gen.writeEndArray();
        Options options = cellSheet.getOptions();
        gen.writeFieldName("options");
        gen.writeStartObject();
        gen.writeNumberField("selectMode", options.getSelectMode().getCode());
        gen.writeNumberField("enterNext", options.getEnterNext().getCode());
        gen.writeBooleanField("hiddenSerialNumberHeader", options.isHiddenSerialNumberHeader());
        Header header = options.getHeader();
        if (null != header) {
            gen.writeFieldName("header");
            gen.writeStartObject();
            gen.writeNumberField("rowHeader", header.getRowHeader());
            gen.writeNumberField("columnHeader", header.getColumnHeader());
            gen.writeEndObject();
        }
        if (null != (resize = options.getResize())) {
            gen.writeFieldName("resize");
            gen.writeStartObject();
            gen.writeBooleanField("row", resize.isRow());
            gen.writeBooleanField("column", resize.isColumn());
            gen.writeEndObject();
        }
        gen.writeEndObject();
        ArrayList<CellFormatter> numFmts = new ArrayList<CellFormatter>();
        ArrayList<CellFont> fonts = new ArrayList<CellFont>();
        ArrayList<CellBackGround> backGrounds = new ArrayList<CellBackGround>();
        ArrayList<CellBorders> cellBorders = new ArrayList<CellBorders>();
        ArrayList<CellLayout> cellLayouts = new ArrayList<CellLayout>();
        ArrayList<SimpleCellStyle> simpleStyles = new ArrayList<SimpleCellStyle>();
        List<List<Cell>> data = cellSheet.getData();
        gen.writeFieldName("data");
        gen.writeStartArray();
        CellStyle defaultStyle = cellSheet.getCellBook().getBookStyle().getDefaultStyle();
        for (List<Cell> list : data) {
            gen.writeStartArray();
            for (Cell cell : list) {
                CellBorders borders;
                CellLayout layout;
                CellBackGround backGround;
                CellFont font;
                CellFormatter formatter;
                int styleIndex = -1;
                boolean isDefault = true;
                SimpleCellStyle simpleCellStyle = new SimpleCellStyle();
                CellStyle cellStyle = cell.getCellStyle();
                CellStyleModel model = cellStyle.getModel();
                simpleCellStyle.setModel(model);
                if (!defaultStyle.getModel().equals((Object)model)) {
                    isDefault = false;
                }
                if (null != (formatter = cellStyle.getFormatter())) {
                    int indexOf = numFmts.indexOf(formatter);
                    if (indexOf < 0) {
                        numFmts.add(formatter);
                        indexOf = numFmts.size() - 1;
                    }
                    simpleCellStyle.setNumFmt(indexOf);
                    isDefault = false;
                }
                if (null != (font = cellStyle.getFont())) {
                    int indexOf = fonts.indexOf(font);
                    if (indexOf < 0) {
                        fonts.add(font);
                        indexOf = fonts.size() - 1;
                    }
                    simpleCellStyle.setFont(indexOf);
                    isDefault = false;
                }
                if (null != (backGround = cellStyle.getBackGround())) {
                    int indexOf = backGrounds.indexOf(backGround);
                    if (indexOf < 0) {
                        backGrounds.add(backGround);
                        indexOf = backGrounds.size() - 1;
                    }
                    simpleCellStyle.setFill(indexOf);
                    isDefault = false;
                }
                if (null != (layout = cellStyle.getLayout())) {
                    int indexOf = cellLayouts.indexOf(layout);
                    if (indexOf < 0) {
                        cellLayouts.add(layout);
                        indexOf = cellLayouts.size() - 1;
                    }
                    simpleCellStyle.setLayout(indexOf);
                    isDefault = false;
                }
                if (null != (borders = cellStyle.getBorders())) {
                    int indexOf = cellBorders.indexOf(borders);
                    if (indexOf < 0) {
                        cellBorders.add(borders);
                        indexOf = cellBorders.size() - 1;
                    }
                    simpleCellStyle.setBorder(indexOf);
                    isDefault = false;
                }
                if (!isDefault && (styleIndex = simpleStyles.indexOf(simpleCellStyle)) < 0) {
                    simpleStyles.add(simpleCellStyle);
                    styleIndex = simpleStyles.size() - 1;
                }
                gen.writeStartObject();
                CommonCellDataType commonDataType = cell.getCommonDataType();
                String dataTypeId = cell.getDataTypeId();
                if (StringUtils.isNotEmpty(dataTypeId)) {
                    if (null == commonDataType) {
                        if (dataTypeId.equals("2")) {
                            commonDataType = CommonCellDataType.BOOLEAN;
                            dataTypeId = null;
                        } else if (dataTypeId.equals("3")) {
                            commonDataType = CommonCellDataType.DATE;
                            dataTypeId = null;
                        } else if (dataTypeId.equals("1")) {
                            commonDataType = CommonCellDataType.NUMBER;
                            dataTypeId = null;
                        } else if (dataTypeId.equals("0")) {
                            commonDataType = CommonCellDataType.STRING;
                            dataTypeId = null;
                        }
                    }
                    if (StringUtils.isNotEmpty(dataTypeId)) {
                        gen.writeStringField("t", dataTypeId);
                    }
                }
                if (null != commonDataType) {
                    gen.writeStringField("b", commonDataType.getCode());
                }
                gen.writeStringField("v", StringUtils.isNotEmpty(cell.getValue()) ? cell.getValue() : "");
                if (StringUtils.isNotEmpty(cell.getFormula())) {
                    gen.writeStringField("formula", cell.getFormula());
                }
                if (StringUtils.isNotEmpty(cell.getShowText())) {
                    gen.writeStringField("showText", cell.getShowText());
                }
                gen.writeNumberField("s", styleIndex);
                JSONObject persistenceData = cell.getPersistenceData();
                if (null != persistenceData && !persistenceData.isEmpty()) {
                    gen.writeStringField("exData", persistenceData.toString());
                }
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        defaultStyle = cellSheet.getCellBook().getBookStyle().getDefaultStyle();
        gen.writeFieldName("style");
        gen.writeStartObject();
        CellFormatter formatter = defaultStyle.getFormatter();
        if (!numFmts.isEmpty()) {
            gen.writeFieldName("numFmts");
            gen.writeStartArray();
            for (CellFormatter cellFormatter : numFmts) {
                this.writeFmt(gen, cellFormatter, formatter);
            }
            gen.writeEndArray();
        }
        CellFont cellFont = defaultStyle.getFont();
        if (!fonts.isEmpty()) {
            gen.writeFieldName("fonts");
            gen.writeStartArray();
            for (CellFont cellFont2 : fonts) {
                this.writeFont(gen, cellFont2, cellFont);
            }
            gen.writeEndArray();
        }
        CellBackGround cellBackGround = defaultStyle.getBackGround();
        if (!backGrounds.isEmpty()) {
            gen.writeFieldName("fills");
            gen.writeStartArray();
            for (CellBackGround backGround : backGrounds) {
                this.writeBackGround(gen, backGround, cellBackGround);
            }
            gen.writeEndArray();
        }
        CellBorders cellBorders2 = defaultStyle.getBorders();
        if (!cellBorders.isEmpty()) {
            gen.writeFieldName("borders");
            gen.writeStartArray();
            for (CellBorders borders : cellBorders) {
                this.writeBorder(gen, borders, cellBorders2);
            }
            gen.writeEndArray();
        }
        CellLayout deafultLayout = defaultStyle.getLayout();
        if (!cellLayouts.isEmpty()) {
            gen.writeFieldName("layouts");
            gen.writeStartArray();
            for (CellLayout layout : cellLayouts) {
                this.writeLayout(gen, layout, deafultLayout);
            }
            gen.writeEndArray();
        }
        if (!simpleStyles.isEmpty()) {
            gen.writeFieldName("style");
            gen.writeStartArray();
            for (SimpleCellStyle simpleCellStyle : simpleStyles) {
                gen.writeStartObject();
                gen.writeNumberField("mode", simpleCellStyle.getModel().getCode());
                if (simpleCellStyle.getNumFmt() >= 0) {
                    gen.writeNumberField("numFmt", simpleCellStyle.getNumFmt());
                }
                if (simpleCellStyle.getFont() >= 0) {
                    gen.writeNumberField("font", simpleCellStyle.getFont());
                }
                if (simpleCellStyle.getFill() >= 0) {
                    gen.writeNumberField("fill", simpleCellStyle.getFill());
                }
                if (simpleCellStyle.getBorder() >= 0) {
                    gen.writeNumberField("border", simpleCellStyle.getBorder());
                }
                if (simpleCellStyle.getLayout() >= 0) {
                    gen.writeNumberField("layout", simpleCellStyle.getLayout());
                }
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }
        gen.writeEndObject();
        List<CellMerge> merges = cellSheet.getMerges();
        if (null != merges) {
            gen.writeFieldName("mergeInfo");
            gen.writeStartArray();
            for (CellMerge cellMerge : merges) {
                gen.writeStartArray();
                gen.writeNumber(cellMerge.getRowIndex());
                gen.writeNumber(cellMerge.getColumnIndex());
                gen.writeNumber(cellMerge.getRowSpan());
                gen.writeNumber(cellMerge.getColumnSpan());
                gen.writeEndArray();
            }
            gen.writeEndArray();
        }
    }

    private void writeDefaultStyle(CellBook cellBook, JsonGenerator gen) throws IOException {
        BookStyle bookStyle = cellBook.getBookStyle();
        CellStyle defaultStyle = bookStyle.getDefaultStyle();
        gen.writeFieldName("styles");
        gen.writeStartObject();
        if (StringUtils.isNotEmpty(bookStyle.getSelector())) {
            gen.writeStringField("selector", bookStyle.getSelector());
        }
        if (StringUtils.isNotEmpty(bookStyle.getSerialNumberHeader())) {
            gen.writeStringField("serialNumberHeader", bookStyle.getSerialNumberHeader());
        }
        if (StringUtils.isNotEmpty(bookStyle.getSplitLine())) {
            gen.writeStringField("splitLine", bookStyle.getSplitLine());
        }
        if (StringUtils.isNotEmpty(bookStyle.getPadding())) {
            gen.writeStringField("padding", bookStyle.getPadding());
        }
        gen.writeFieldName("cellStyles");
        gen.writeStartObject();
        gen.writeNumberField("mode", defaultStyle.getModel().getCode());
        CellFont font = defaultStyle.getFont();
        gen.writeFieldName("font");
        this.writeFont(gen, font, null);
        CellLayout layout = defaultStyle.getLayout();
        gen.writeFieldName("layout");
        this.writeLayout(gen, layout, null);
        CellBorders borders = defaultStyle.getBorders();
        gen.writeFieldName("border");
        this.writeBorder(gen, borders, null);
        CellBackGround backGround = defaultStyle.getBackGround();
        gen.writeFieldName("fill");
        this.writeBackGround(gen, backGround, null);
        CellFormatter formatter = defaultStyle.getFormatter();
        gen.writeFieldName("numFmt");
        this.writeFmt(gen, formatter, null);
        gen.writeEndObject();
        gen.writeEndObject();
    }

    private void writeLayout(JsonGenerator gen, CellLayout layout, CellLayout deafultLayout) throws IOException {
        gen.writeStartObject();
        if (null == deafultLayout || deafultLayout.getIndent() != layout.getIndent()) {
            gen.writeNumberField("i", layout.getIndent());
        }
        if (null == deafultLayout || deafultLayout.isFitSize() != layout.isFitSize()) {
            gen.writeBooleanField("fs", layout.isFitSize());
        }
        if (null == deafultLayout || deafultLayout.isWrapLine() != layout.isWrapLine()) {
            gen.writeBooleanField("wl", layout.isWrapLine());
        }
        if (null == deafultLayout || deafultLayout.getHorizontalAlignment() != layout.getHorizontalAlignment()) {
            gen.writeNumberField("h", layout.getHorizontalAlignment().getCode());
        }
        if (null == deafultLayout || deafultLayout.getVerticalAlignment() != layout.getVerticalAlignment()) {
            gen.writeNumberField("v", layout.getVerticalAlignment().getCode());
        }
        if (null == deafultLayout || deafultLayout.getTextRotation() != layout.getTextRotation()) {
            gen.writeNumberField("r", layout.getTextRotation());
        }
        gen.writeEndObject();
    }

    private void writeBorder(JsonGenerator gen, CellBorders borders, CellBorders defaultBorders) throws IOException {
        gen.writeStartObject();
        CellBorder right = borders.getRight();
        if (null == defaultBorders || !defaultBorders.getRight().getColor().equals(right.getColor())) {
            int rightBorderColorIndex = right.getColorIndex();
            if (rightBorderColorIndex > -1) {
                gen.writeNumberField("rbc", rightBorderColorIndex);
            } else {
                gen.writeStringField("rbc", this.handlerColor(right.getColor()));
            }
        }
        if (null == defaultBorders || defaultBorders.getRight().getStyle() != right.getStyle()) {
            gen.writeNumberField("rbs", right.getStyle().getCode());
        }
        CellBorder bottom = borders.getBottom();
        if (null == defaultBorders || !defaultBorders.getBottom().getColor().equals(bottom.getColor())) {
            int bottomBorderColorIndex = bottom.getColorIndex();
            if (bottomBorderColorIndex > -1) {
                gen.writeNumberField("bbc", bottomBorderColorIndex);
            } else {
                gen.writeStringField("bbc", this.handlerColor(bottom.getColor()));
            }
        }
        if (null == defaultBorders || defaultBorders.getBottom().getStyle() != bottom.getStyle()) {
            gen.writeNumberField("bbs", bottom.getStyle().getCode());
        }
        CellBorder left = borders.getLeft();
        if (null == defaultBorders || !defaultBorders.getLeft().getColor().equals(left.getColor())) {
            int leftBorderColorIndex = left.getColorIndex();
            if (leftBorderColorIndex > -1) {
                gen.writeNumberField("lbc", leftBorderColorIndex);
            } else {
                gen.writeStringField("lbc", this.handlerColor(left.getColor()));
            }
        }
        if (null == defaultBorders || defaultBorders.getLeft().getStyle() != left.getStyle()) {
            gen.writeNumberField("lbs", left.getStyle().getCode());
        }
        CellBorder top = borders.getTop();
        if (null == defaultBorders || !defaultBorders.getTop().getColor().equals(top.getColor())) {
            int topBorderColorIndex = top.getColorIndex();
            if (topBorderColorIndex > -1) {
                gen.writeNumberField("tbc", topBorderColorIndex);
            } else {
                gen.writeStringField("tbc", this.handlerColor(top.getColor()));
            }
        }
        if (null == defaultBorders || defaultBorders.getTop().getStyle() != top.getStyle()) {
            gen.writeNumberField("tbs", top.getStyle().getCode());
        }
        CellBorder diagonalUp = borders.getDiagonalUp();
        if (null == defaultBorders || !defaultBorders.getDiagonalUp().getColor().equals(diagonalUp.getColor())) {
            int diagonalUpColorIndex = diagonalUp.getColorIndex();
            if (diagonalUpColorIndex > -1) {
                gen.writeNumberField("duc", diagonalUpColorIndex);
            } else {
                gen.writeStringField("duc", this.handlerColor(diagonalUp.getColor()));
            }
        }
        if (null == defaultBorders || defaultBorders.getDiagonalUp().getStyle() != diagonalUp.getStyle()) {
            gen.writeNumberField("dus", diagonalUp.getStyle().getCode());
        }
        CellBorder diagonalDown = borders.getDiagonalDown();
        if (null == defaultBorders || !defaultBorders.getDiagonalDown().getColor().equals(diagonalDown.getColor())) {
            int diagonalDownColorIndex = diagonalDown.getColorIndex();
            if (diagonalDownColorIndex > -1) {
                gen.writeNumberField("ddc", diagonalDownColorIndex);
            } else {
                gen.writeStringField("ddc", this.handlerColor(diagonalDown.getColor()));
            }
        }
        if (null == defaultBorders || defaultBorders.getDiagonalDown().getStyle() != diagonalDown.getStyle()) {
            gen.writeNumberField("dds", diagonalDown.getStyle().getCode());
        }
        gen.writeEndObject();
    }

    private void writeBackGround(JsonGenerator gen, CellBackGround backGround, CellBackGround defaultBackGround) throws IOException {
        gen.writeStartObject();
        if (null == defaultBackGround || defaultBackGround.getFillPatternType() != backGround.getFillPatternType()) {
            gen.writeNumberField("t", backGround.getFillPatternType().getCode());
        }
        if (null == defaultBackGround || !defaultBackGround.getColor().equals(backGround.getColor())) {
            int backGroundColorIndex = backGround.getColorIndex();
            if (backGroundColorIndex > -1) {
                gen.writeNumberField("c", backGroundColorIndex);
            } else {
                gen.writeStringField("c", this.handlerColor(backGround.getColor()));
            }
        }
        if (null == defaultBackGround || !defaultBackGround.getBackGroundImg().equals(backGround.getBackGroundImg())) {
            gen.writeStringField("i", backGround.getBackGroundImg());
        }
        gen.writeEndObject();
    }

    private void writeFont(JsonGenerator gen, CellFont font, CellFont defaultFont) throws IOException {
        gen.writeStartObject();
        if (null == defaultFont || !defaultFont.getName().equals(font.getName())) {
            gen.writeStringField("n", font.getName());
        }
        if (null == defaultFont || defaultFont.getSizeF() != font.getSizeF()) {
            gen.writeNumberField("s", font.getSizeF());
        }
        if (null == defaultFont || !defaultFont.getColor().equals(font.getColor())) {
            int fontColorIndex = font.getColorIndex();
            if (fontColorIndex > -1) {
                gen.writeNumberField("c", fontColorIndex);
            } else {
                gen.writeStringField("c", this.handlerColor(font.getColor()));
            }
        }
        if (null == defaultFont || defaultFont.isBold() != font.isBold()) {
            gen.writeBooleanField("b", font.isBold());
        }
        if (null == defaultFont || defaultFont.isItalic() != font.isItalic()) {
            gen.writeBooleanField("i", font.isItalic());
        }
        if (null == defaultFont || defaultFont.isUnderline() != font.isUnderline()) {
            gen.writeBooleanField("ul", font.isUnderline());
        }
        if (null == defaultFont || defaultFont.isInline() != font.isInline()) {
            gen.writeBooleanField("il", font.isInline());
        }
        gen.writeEndObject();
    }

    private void writeFmt(JsonGenerator gen, CellFormatter formatter, CellFormatter defaultFormatter) throws IOException {
        gen.writeStartObject();
        if (null == defaultFormatter || !defaultFormatter.getFormatCode().equals(formatter.getFormatCode())) {
            gen.writeStringField("v", formatter.getFormatCode());
        }
        gen.writeEndObject();
    }

    private void writeTheme(CellBook cellBook, JsonGenerator gen) throws IOException {
        CellThemes theme = cellBook.getTheme();
        gen.writeFieldName("theme");
        gen.writeStartObject();
        gen.writeStringField("current", theme.getCurrent());
        gen.writeFieldName("themes");
        gen.writeStartObject();
        Map<String, CellTheme> themes = theme.getThemes();
        for (Map.Entry<String, CellTheme> oneTheme : themes.entrySet()) {
            CellTheme value = oneTheme.getValue();
            gen.writeFieldName(oneTheme.getKey());
            gen.writeStartObject();
            List<CellColor> palette = value.getPalette();
            if (null != palette) {
                gen.writeFieldName("palette");
                gen.writeStartArray();
                for (CellColor cellColor : palette) {
                    gen.writeStartObject();
                    gen.writeStringField("title", cellColor.getTitle());
                    gen.writeStringField("value", this.handlerColor(cellColor.getHexString()));
                    gen.writeEndObject();
                }
                gen.writeEndArray();
            }
            gen.writeEndObject();
        }
        gen.writeEndObject();
        gen.writeEndObject();
    }

    private void writeGroups(CellBook cellBook, JsonGenerator gen) throws IOException {
        List<CellSheetGroup> groups = cellBook.getGroups();
        gen.writeFieldName("sheetGroups");
        gen.writeStartObject();
        if (null != groups && !groups.isEmpty()) {
            gen.writeFieldName("groups");
            gen.writeStartArray();
            for (CellSheetGroup cellSheetGroup : groups) {
                gen.writeStartObject();
                gen.writeStringField("title", cellSheetGroup.getTitle());
                gen.writeStringField("name", cellSheetGroup.getName());
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }
        List<CellSheet> sheets = cellBook.getSheets();
        gen.writeFieldName("sheets");
        gen.writeStartArray();
        for (CellSheet sheetBean : sheets) {
            gen.writeStartObject();
            gen.writeStringField("title", sheetBean.getTitle());
            gen.writeStringField("name", sheetBean.getName());
            gen.writeStringField("groupName", sheetBean.getGroupCode());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

    private void writeDoc(CellBook cellBook, JsonGenerator gen) throws IOException {
        DocProps docProps = cellBook.getDocProps();
        if (null != docProps) {
            gen.writeFieldName("docProps");
            gen.writeStartObject();
            gen.writeStringField("version", docProps.getVersion());
            gen.writeStringField("encoding", docProps.getEncoding());
            gen.writeStringField("creator", docProps.getCreator());
            gen.writeNumberField("created", docProps.getCreated());
            gen.writeStringField("modifior", docProps.getModifior());
            gen.writeNumberField("modified", docProps.getModified());
            gen.writeStringField("company", docProps.getCompany());
            gen.writeStringField("docSecurity", docProps.getDocSecurity());
            gen.writeStringField("mode", docProps.getModel().toString());
            gen.writeEndObject();
        }
    }

    private String handlerColor(String color) {
        return StringUtils.isNotEmpty(color) ? "#" + color : "";
    }

    public Class<CellBook> handledType() {
        return CellBook.class;
    }
}


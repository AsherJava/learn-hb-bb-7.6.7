/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.SerializerProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$Cell_MODE
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$EDIT_MODE
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$ENTER_NEXT
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$LOAD_MODE
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$MERGE_SHOW_MODE
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$SELECTION_MODE
 */
package nr.midstore2.design.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataConst;
import java.io.IOException;

public class MidstoreGrid2DataSeralizeToGeGe
extends JsonSerializer<Grid2Data> {
    public void serialize(Grid2Data value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
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
        gen.writeBooleanField("ignoreHidden", false);
        gen.writeBooleanField("colResizeable", true);
        gen.writeBooleanField("rowResizeable", true);
        gen.writeBooleanField("colFreeResizeable", false);
        gen.writeBooleanField("rowFreeResizeable", false);
        gen.writeBooleanField("colGrabable", true);
        gen.writeBooleanField("showSelectionBorder", true);
        gen.writeBooleanField("currentCellBorderHidden", false);
        gen.writeBooleanField("colExchangeable", false);
        gen.writeBooleanField("passReadOnly", true);
        gen.writeBooleanField("showSelectionChange", false);
        gen.writeStringField("selectionColor", "rgba(255, 0, 0, 0.2)");
        gen.writeStringField("selectionBorderColor", "rgba(0, 180, 0, 1");
        gen.writeNumberField("selectionBorderWidth", 3);
        gen.writeStringField("currentCellColor", "rgba(228, 247,214, 0.3)");
        gen.writeStringField("currentCellBorderColor", "rgba(0, 180, 0, 1)");
        gen.writeNumberField("mergeCellShowMode", Grid2DataConst.MERGE_SHOW_MODE.MERGE_SHOW_MODE_NORMAL.getIndex());
        gen.writeBooleanField("showMergeChildBorder", true);
        gen.writeBooleanField("hideSingleSelect", true);
        gen.writeBooleanField("tableCell", true);
        gen.writeNumberField("currentCellShowType", 1);
        gen.writeBooleanField("hideSelectionChangeable", false);
        gen.writeStringField("floatRegionBorderColor", "#50a0ff");
        gen.writeNumberField("floatRegionBorderWidth", 1);
        gen.writeBooleanField("advancedResize", false);
        gen.writeFieldName("DataRegions");
        gen.writeStartObject();
        gen.writeFieldName("0");
        gen.writeStartObject();
        gen.writeNumberField("RegionId", 0);
        gen.writeNumberField("RegionKind", 0);
        gen.writeNumberField("RegionTop", 0);
        gen.writeNumberField("RegionLeft", 0);
        gen.writeNumberField("RegionBottom", 0);
        gen.writeNumberField("RegionRight", 0);
        gen.writeNumberField("FloatDataCount", 0);
        gen.writeNumberField("DefaultCount", 0);
        gen.writeStringField("RecordRelations", null);
        gen.writeStringField("RegionTemplate", null);
        gen.writeStringField("RegionExtension", "");
        gen.writeBooleanField("FixedRecordCount", false);
        gen.writeNumberField("MaxRecordCount", -1);
        gen.writeEndObject();
        gen.writeEndObject();
        gen.writeFieldName("DataRowsAttr");
        gen.writeStartObject();
        gen.writeEndObject();
        gen.writeBooleanField("showGridTitle", true);
        gen.writeFieldName("extendData");
        gen.writeStartObject();
        gen.writeEndObject();
        gen.writeFieldName("cellBoolAttrs");
        gen.writeStartObject();
        gen.writeEndObject();
        gen.writeStringField("enumDataSourceUrl", " /DataInput/Home/LoadEnumData?_taskId_= 937ba267-0272-40c1-b8fb-45a195809f35");
        gen.writeStringField("selectionHeadColor", "rgb(180,228,178)");
        gen.writeStringField("enumDataBlobUrl", null);
        gen.writeNumberField("blankAfterRow", 54);
        gen.writeNumberField("blankAfterCol", 74);
        gen.writeBooleanField("hideFloatRegionBorder", false);
        gen.writeEndObject();
        gen.writeFieldName("cells");
        gen.writeStartArray();
        for (int i = 0; i < value.getRowCount(); ++i) {
            gen.writeStartArray();
            for (int j = 0; j < value.getColumnCount(); ++j) {
                gen.writeStartObject();
                GridCellData gridCellData = value.getGridCellData(j, i);
                gen.writeNumberField("horzAlign", gridCellData.getHorzAlign());
                gen.writeNumberField("vertAlign", gridCellData.getVertAlign());
                gen.writeNumberField("rowSpan", gridCellData.getRowSpan());
                gen.writeNumberField("colSpan", gridCellData.getColSpan());
                gen.writeNumberField("colIndex", gridCellData.getColIndex());
                gen.writeNumberField("rowIndex", gridCellData.getRowIndex());
                if (gridCellData.isSilverHead()) {
                    gen.writeStringField("backColor", "#F1F1F1");
                } else {
                    gen.writeStringField("backColor", MidstoreGrid2DataSeralizeToGeGe.intToHtmlColor(gridCellData.getBackGroundColor(), "#FFFFFF"));
                }
                gen.writeBooleanField("editable", gridCellData.isEditable());
                gen.writeBooleanField("selectable", gridCellData.isSelectable());
                gen.writeNumberField("backImageStyle", gridCellData.getBackgroundImageStyle());
                gen.writeStringField("fontName", gridCellData.getFontName().replaceAll("\"", ""));
                gen.writeNumberField("fontSize", gridCellData.getFontSize());
                int fontStyle = gridCellData.getFontStyle();
                gen.writeBooleanField("fontItalic", (fontStyle & 4) > 0);
                gen.writeBooleanField("fontBold", (fontStyle & 2) > 0);
                if ((fontStyle & 8) > 0) {
                    gen.writeNumberField("decoration", 1);
                }
                if ((fontStyle & 0x10) > 0) {
                    gen.writeNumberField("decoration", 2);
                }
                if ((fontStyle & 0x40) > 0) {
                    gen.writeNumberField("decoration", 3);
                }
                if (gridCellData.isVertText()) {
                    gen.writeBooleanField("vertText", true);
                }
                gen.writeStringField("fontColor", MidstoreGrid2DataSeralizeToGeGe.intToHtmlColor(gridCellData.getForeGroundColor(), "#000000"));
                gen.writeBooleanField("wrapLine", gridCellData.isWrapLine());
                gen.writeStringField("cellDescription", gridCellData.getPersistenceData("cellDescription"));
                gen.writeFieldName("border");
                gen.writeStartArray();
                gen.writeNumber(gridCellData.getRightBorderStyle());
                gen.writeNumber(gridCellData.getBottomBorderStyle());
                gen.writeEndArray();
                gen.writeFieldName("borderColor");
                gen.writeStartArray();
                gen.writeString(MidstoreGrid2DataSeralizeToGeGe.intToHtmlColor(gridCellData.getRightBorderColor(), "#D4D4D4"));
                gen.writeString(MidstoreGrid2DataSeralizeToGeGe.intToHtmlColor(gridCellData.getBottomBorderColor(), "#D4D4D4"));
                gen.writeEndArray();
                gen.writeStringField("showText", gridCellData.getShowText());
                gen.writeStringField("editText", gridCellData.getEditText());
                gen.writeNumberField("backStyle", gridCellData.getBackGroundStyle());
                int cellMode = gridCellData.getCellMode();
                if (cellMode == Grid2DataConst.Cell_MODE.Cell_MODE_HTML.getIndex()) {
                    gen.writeStringField("html", gridCellData.getShowText());
                    gen.writeStringField("showText", gridCellData.getEditText());
                } else {
                    gen.writeStringField("html", "");
                }
                gen.writeNumberField("cellMode", Grid2DataConst.Cell_MODE.Cell_MODE_NORMAL.getIndex());
                gen.writeBooleanField("fitFontSize", gridCellData.isFitFontSize());
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeFieldName("rows");
        gen.writeStartArray();
        for (int k = 0; k < value.getRowCount(); ++k) {
            gen.writeStartObject();
            gen.writeNumberField("size", value.getRowHeight(k));
            gen.writeNumberField("clientSize", value.getRowHeight(k));
            gen.writeBooleanField("hidden", value.isRowHidden(k));
            gen.writeBooleanField("auto", value.isRowAutoHeight(k));
            gen.writeBooleanField("colResizeable", true);
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeFieldName("cols");
        gen.writeStartArray();
        for (int t = 0; t < value.getColumnCount(); ++t) {
            gen.writeStartObject();
            gen.writeNumberField("size", value.getColumnWidth(t));
            gen.writeNumberField("clientSize", value.getColumnWidth(t));
            gen.writeBooleanField("hidden", value.isColumnHidden(t));
            gen.writeBooleanField("auto", value.isColumnAutoWidth(t));
            gen.writeBooleanField("rowResizeable", true);
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeNumberField("colHeaderCount", value.getHeaderColumnCount());
        gen.writeNumberField("rowHeaderCount", value.getHeaderRowCount());
        gen.writeNumberField("colFooterCount", value.getFooterColumnCount());
        gen.writeNumberField("rowFooterCount", value.getFooterRowCount());
        gen.writeNumberField("rowCount", value.getRowCount());
        gen.writeNumberField("colCount", value.getColumnCount());
        gen.writeStringField("guid", "");
        gen.writeEndObject();
    }

    public static String intToHtmlColor(int color, String defaultColor) {
        if (color < 0) {
            if (StringUtils.isNotEmpty((String)defaultColor)) {
                return defaultColor;
            }
            return "#000000";
        }
        return "#" + StringUtils.leftPad((String)Integer.toHexString(color), (int)6, (String)"0");
    }
}


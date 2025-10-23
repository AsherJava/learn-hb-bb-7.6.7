/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.converter.ICellBookGrid2DataConverterProvider
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataConst$Cell_MODE
 */
package com.jiuqi.nr.task.form.formstyle.cellbook.transfer;

import com.jiuqi.nvwa.cellbook.converter.ICellBookGrid2DataConverterProvider;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataConst;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SheetTransferProvider
implements ICellBookGrid2DataConverterProvider {
    Pattern pattern = Pattern.compile("title=\"([^\"]+)\"");

    public Grid2Data beforeGrid2DataToCellSheet(Grid2Data grid2Data, CellSheet cellSheet) {
        return grid2Data;
    }

    public CellSheet afterGrid2DataToCellSheet(Grid2Data grid2Data, CellSheet cellSheet) {
        return cellSheet;
    }

    public GridCellData beforeGridCellDataToCell(GridCellData gridCell, Cell cell) {
        return gridCell;
    }

    public Cell afterGridCellDataToCell(GridCellData gridCell, Cell cell) {
        Matcher matcher;
        if (gridCell.getCellMode() == Grid2DataConst.Cell_MODE.Cell_MODE_HTML.getIndex() && gridCell.getDataType() == GridEnums.getIntValue((Enum)GridEnums.DataType.HotLink) && (matcher = this.pattern.matcher(gridCell.getShowText())).find()) {
            cell.setShowText(matcher.group(1));
        }
        return cell;
    }

    public CellSheet beforeCellSheetToGrid2Data(Grid2Data grid2Data, CellSheet cellSheet) {
        return cellSheet;
    }

    public Grid2Data afterCellSheetToGrid2Data(Grid2Data grid2Data, CellSheet cellSheet) {
        if (cellSheet.getOptions().isHiddenSerialNumberHeader()) {
            grid2Data.setRowHidden(0, true);
            grid2Data.setColumnHidden(0, true);
        } else {
            grid2Data.setRowHidden(0, false);
            grid2Data.setColumnHidden(0, false);
        }
        if (grid2Data.getColumnWidth(0) != 36) {
            grid2Data.setColumnWidth(0, 36);
        }
        return grid2Data;
    }

    public Cell beforeCellToGridCellData(GridCellData gridCell, Cell cell) {
        return cell;
    }

    public GridCellData afterCellToGridCellData(GridCellData gridCell, Cell cell) {
        if ("hyperlink".equals(cell.getDataTypeId())) {
            this.transfer(gridCell);
            gridCell.setForeGroundColor(8431551);
        }
        return gridCell;
    }

    private void transfer(GridCellData gridCellData) {
        String trimText;
        String html = gridCellData.getShowText();
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
        if (gridCellData.isVertText()) {
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
}


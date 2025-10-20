/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.cellbook.model;

import com.jiuqi.nvwa.cellbook.constant.CellBorderStyle;
import com.jiuqi.nvwa.cellbook.constant.CellStyleModel;
import com.jiuqi.nvwa.cellbook.constant.FillPatternType;
import com.jiuqi.nvwa.cellbook.constant.HorizontalAlignment;
import com.jiuqi.nvwa.cellbook.constant.StringUtils;
import com.jiuqi.nvwa.cellbook.constant.VerticalAlignment;
import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import com.jiuqi.nvwa.cellbook.model.CellColor;
import com.jiuqi.nvwa.cellbook.model.CellMerge;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.cellbook.model.CellStyle;
import com.jiuqi.nvwa.cellbook.model.Point;
import java.io.Serializable;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cell
implements Serializable,
Cloneable {
    public static final Logger LOGGER = LoggerFactory.getLogger("CellBook");
    private static final long serialVersionUID = 1L;
    private int rowIndex;
    private int colIndex;
    private String dataTypeId;
    private CommonCellDataType commonDataTypeId;
    private String value;
    private String showText;
    private String formula;
    private boolean merged;
    private Point mergeInfo;
    private JSONObject persistenceData;
    private CellSheet cellSheet;
    private CellStyle cellStyle;

    protected Cell(int rowIndex, int colIndex, CellSheet cellSheet) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.cellSheet = cellSheet;
        this.cellStyle = new CellStyle(this.cellSheet.getCellBook());
    }

    protected CellStyle getTopCellStyle() {
        if (this.isNeedChangeCell()) {
            return this.getMergeTopCell().getCellStyle();
        }
        return this.getCellStyle();
    }

    public Object clone() {
        Cell cell = null;
        try {
            cell = (Cell)super.clone();
        }
        catch (CloneNotSupportedException e) {
            LOGGER.error("\u590d\u5236\u5bf9\u8c61\u62a5\u9519\uff01", e);
        }
        return cell;
    }

    public CellStyle getCellStyle() {
        return this.cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public CellSheet getCellSheet() {
        return this.cellSheet;
    }

    private Cell getMergeTopCell() {
        return this.cellSheet.getCell(this.mergeInfo.getX(), this.mergeInfo.getY());
    }

    private boolean isNeedChangeCell() {
        return this.merged && (this.mergeInfo.getX() != this.rowIndex || this.mergeInfo.getY() != this.colIndex);
    }

    public boolean isMerged() {
        return this.merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public Point getMergeInfo() {
        return this.mergeInfo;
    }

    public void setMergeInfo(Point mergeInfo) {
        this.mergeInfo = mergeInfo;
    }

    public int getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public String getDataTypeId() {
        return this.isNeedChangeCell() ? this.getMergeTopCell().getDataTypeId() : this.dataTypeId;
    }

    public CommonCellDataType getCommonDataType() {
        return this.isNeedChangeCell() ? this.getMergeTopCell().getCommonDataType() : this.commonDataTypeId;
    }

    public void setDataTypeId(String cellDataTypeId) {
        if (this.isNeedChangeCell()) {
            this.getMergeTopCell().setDataTypeId(cellDataTypeId);
        } else {
            this.dataTypeId = cellDataTypeId;
        }
    }

    public void setCommonDataType(CommonCellDataType commonDataType) {
        if (this.isNeedChangeCell()) {
            this.getMergeTopCell().setCommonDataType(commonDataType);
        } else {
            this.commonDataTypeId = commonDataType;
        }
    }

    public void setValue(String value) {
        if (this.isNeedChangeCell()) {
            this.getMergeTopCell().setValue(value);
        } else {
            this.value = value;
        }
    }

    public String getShowText() {
        if (this.isNeedChangeCell()) {
            return this.getMergeTopCell().getShowText();
        }
        return this.showText;
    }

    public void setShowText(String showText) {
        if (this.isNeedChangeCell()) {
            this.getMergeTopCell().setShowText(showText);
        } else {
            this.showText = showText;
        }
    }

    public String getValue() {
        if (this.isNeedChangeCell()) {
            return this.getMergeTopCell().getValue();
        }
        return this.value;
    }

    public String getFormula() {
        return this.isNeedChangeCell() ? this.getMergeTopCell().getFormula() : this.formula;
    }

    public void setFormula(String formula) {
        if (this.isNeedChangeCell()) {
            this.getMergeTopCell().setFormula(formula);
        } else {
            this.formula = formula;
        }
    }

    public CellStyleModel getModel() {
        return this.getTopCellStyle().getModel();
    }

    public void setModel(CellStyleModel model) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setModel(model);
    }

    public String getFormatter() {
        return this.getTopCellStyle().getFormatCode();
    }

    public void setFormatter(String formatter) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setFormatCode(formatter);
    }

    public String getFontName() {
        return this.getTopCellStyle().getFontName();
    }

    public void setFontName(String fontName) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setFontName(fontName);
    }

    @Deprecated
    public int getFontSize() {
        return (int)this.getTopCellStyle().getFontSizeF();
    }

    public float getFontSizeF() {
        return this.getTopCellStyle().getFontSizeF();
    }

    @Deprecated
    public void setFontSize(int fontSize) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setFontSize(fontSize);
    }

    public void setFontSize(float fontSize) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setFontSize(fontSize);
    }

    public boolean isBold() {
        return this.getTopCellStyle().isBold();
    }

    public void setBold(boolean bold) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setBold(bold);
    }

    public boolean isItalic() {
        return this.getTopCellStyle().isItalic();
    }

    public void setItalic(boolean italic) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setItalic(italic);
    }

    public boolean isUnderline() {
        return this.getTopCellStyle().isUnderline();
    }

    public void setUnderline(boolean underline) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setUnderline(underline);
    }

    public boolean isInline() {
        return this.getTopCellStyle().isInline();
    }

    public void setInline(boolean inline) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setInline(inline);
    }

    public int getIndent() {
        return this.getTopCellStyle().getIndent();
    }

    public void setIndent(int indent) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setIndent(indent);
    }

    public boolean isFitFontSize() {
        return this.getTopCellStyle().isFitFontSize();
    }

    public void setFitFontSize(boolean fitFontSize) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setFitFontSize(fitFontSize);
    }

    public boolean isWrapLine() {
        return this.getTopCellStyle().isWrapLine();
    }

    public void setWrapLine(boolean wrapLine) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setWrapLine(wrapLine);
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return this.getTopCellStyle().getHorizontalAlignment();
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setHorizontalAlignment(horizontalAlignment);
    }

    public VerticalAlignment getVerticalAlignment() {
        return this.getTopCellStyle().getVerticalAlignment();
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setVerticalAlignment(verticalAlignment);
    }

    public String getLeftBorderColorHex() {
        String rightBorderColor;
        if (this.colIndex > 0 && StringUtils.isNotEmpty(rightBorderColor = this.cellSheet.getCell(this.rowIndex, this.colIndex - 1).getCellStyle().getRightBorderColor())) {
            return rightBorderColor;
        }
        return this.getCellStyle().getLeftBorderColor();
    }

    public CellColor getLeftBorderColor() {
        String leftBorderColorIndex = this.getLeftBorderColorHex();
        return StringUtils.isNotEmpty(leftBorderColorIndex) ? new CellColor(leftBorderColorIndex) : null;
    }

    public void setLeftBorderColor(String leftBorderColor) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            if (cellMerge.getRowSpan() > 1) {
                for (int i = cellMerge.getRowIndex(); i < cellMerge.getRowIndex() + cellMerge.getRowSpan(); ++i) {
                    String rightBorderColor;
                    Cell cell = this.cellSheet.getCell(i, cellMerge.getColumnIndex());
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setLeftBorderColor(leftBorderColor);
                    if (this.colIndex <= 0 || (rightBorderColor = this.cellSheet.getCell(i, cellMerge.getColumnIndex() - 1).getRightBorderColorHex()).equals(leftBorderColor)) continue;
                    cell = this.cellSheet.getCell(i, cellMerge.getColumnIndex() - 1);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setRightBorderColor(leftBorderColor);
                }
            } else {
                String rightBorderColor;
                Cell cell = this.cellSheet.getCell(cellMerge.getRowIndex(), cellMerge.getColumnIndex());
                CellStyle canEditCellStyle = cell.getCellStyle();
                canEditCellStyle.setLeftBorderColor(leftBorderColor);
                if (this.colIndex > 0 && !(rightBorderColor = this.cellSheet.getCell(cellMerge.getRowIndex(), cellMerge.getColumnIndex() - 1).getRightBorderColorHex()).equals(leftBorderColor)) {
                    cell = this.cellSheet.getCell(cellMerge.getRowIndex(), cellMerge.getColumnIndex() - 1);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setRightBorderColor(leftBorderColor);
                }
            }
        } else {
            String rightBorderColorIndex;
            CellStyle canEditCellStyle = this.getTopCellStyle();
            canEditCellStyle.setLeftBorderColor(leftBorderColor);
            if (this.colIndex > 0 && !(rightBorderColorIndex = this.cellSheet.getCell(this.rowIndex, this.colIndex - 1).getRightBorderColorHex()).equals(leftBorderColor)) {
                Cell cell = this.cellSheet.getCell(this.rowIndex, this.colIndex - 1);
                canEditCellStyle = cell.getCellStyle();
                canEditCellStyle.setRightBorderColor(leftBorderColor);
            }
        }
    }

    public void setLeftBorderColor(CellColor leftBorderColor) {
        String hex = "";
        if (null != leftBorderColor) {
            hex = leftBorderColor.getHexString();
        }
        this.setLeftBorderColor(hex);
    }

    public void setSelfLeftBorderColor(String leftBorderColor) {
        this.cellStyle.setLeftBorderColor(leftBorderColor);
        if (this.colIndex > 0) {
            Cell leftCell = this.cellSheet.getCell(this.rowIndex, this.colIndex - 1);
            leftCell.getCellStyle().setRightBorderColor(leftBorderColor);
        }
    }

    public void setSelfLeftBorderColor(CellColor leftBorderColor) {
        this.setSelfLeftBorderColor(leftBorderColor == null ? "" : leftBorderColor.getHexString());
    }

    public String getRightBorderColorHex() {
        String rightBorderColor = this.getCellStyle().getRightBorderColor();
        if (StringUtils.isNotEmpty(rightBorderColor)) {
            return rightBorderColor;
        }
        int currCol = this.colIndex;
        int colSize = this.cellSheet.getColumns().size();
        if (currCol < colSize - 1) {
            return this.cellSheet.getCell(this.rowIndex, currCol + 1).getCellStyle().getLeftBorderColor();
        }
        return rightBorderColor;
    }

    public CellColor getRightBorderColor() {
        String rightBorderColor = this.getRightBorderColorHex();
        return StringUtils.isNotEmpty(rightBorderColor) ? new CellColor(rightBorderColor) : null;
    }

    public void setRightBorderColor(String rightBorderColor) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            int currCol = cellMerge.getColumnSpan() > 1 ? cellMerge.getColumnIndex() + cellMerge.getColumnSpan() - 1 : cellMerge.getColumnIndex();
            int colSize = this.cellSheet.getColumns().size();
            if (cellMerge.getRowSpan() > 1) {
                for (int i = cellMerge.getRowIndex(); i < cellMerge.getRowIndex() + cellMerge.getRowSpan(); ++i) {
                    String leftBorderColorIndex;
                    Cell cell = this.cellSheet.getCell(i, currCol);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setRightBorderColor(rightBorderColor);
                    if (currCol >= colSize - 1 || (leftBorderColorIndex = this.cellSheet.getCell(i, currCol + 1).getLeftBorderColorHex()).equals(rightBorderColor)) continue;
                    cell = this.cellSheet.getCell(i, currCol + 1);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setLeftBorderColor(rightBorderColor);
                }
                if (cellMerge.getColumnSpan() > 1) {
                    CellStyle canEditTopCellStyle = this.getTopCellStyle();
                    canEditTopCellStyle.setRightBorderColor(rightBorderColor);
                }
            } else {
                String leftBorderColorIndex;
                CellStyle canEditTopCellStyle = this.getTopCellStyle();
                canEditTopCellStyle.setRightBorderColor(rightBorderColor);
                Cell cell = this.cellSheet.getCell(cellMerge.getRowIndex(), currCol);
                canEditTopCellStyle = cell.getCellStyle();
                canEditTopCellStyle.setRightBorderColor(rightBorderColor);
                if (currCol < colSize - 1 && !(leftBorderColorIndex = this.cellSheet.getCell(cellMerge.getRowIndex(), currCol + 1).getLeftBorderColorHex()).equals(rightBorderColor)) {
                    cell = this.cellSheet.getCell(cellMerge.getRowIndex(), currCol + 1);
                    canEditTopCellStyle = cell.getCellStyle();
                    canEditTopCellStyle.setLeftBorderColor(rightBorderColor);
                }
            }
        } else {
            String leftBorderColorIndex;
            CellStyle canEditTopCellStyle = this.getTopCellStyle();
            canEditTopCellStyle.setRightBorderColor(rightBorderColor);
            int colSize = this.cellSheet.getColumns().size();
            if (this.colIndex < colSize - 1 && !(leftBorderColorIndex = this.cellSheet.getCell(this.rowIndex, this.colIndex + 1).getLeftBorderColorHex()).equals(rightBorderColor)) {
                Cell cell = this.cellSheet.getCell(this.rowIndex, this.colIndex + 1);
                canEditTopCellStyle = cell.getCellStyle();
                canEditTopCellStyle.setLeftBorderColor(rightBorderColor);
            }
        }
    }

    public void setRightBorderColor(CellColor rightBorderColor) {
        String indexOf = "";
        if (null != rightBorderColor) {
            indexOf = rightBorderColor.getHexString();
        }
        this.setRightBorderColor(indexOf);
    }

    public void setSelfRightBorderColor(String rightBorderColor) {
        this.cellStyle.setRightBorderColor(rightBorderColor);
        if (this.colIndex < this.cellSheet.getColumnCount() - 1) {
            Cell rightCell = this.cellSheet.getCell(this.rowIndex, this.colIndex + 1);
            rightCell.getCellStyle().setLeftBorderColor(rightBorderColor);
        }
    }

    public void setSelfRightBorderColor(CellColor rightBorderColor) {
        this.setSelfRightBorderColor(rightBorderColor == null ? "" : rightBorderColor.getHexString());
    }

    public CellBorderStyle getLeftBorderStyle() {
        CellBorderStyle rightBorderStyle;
        if (this.colIndex > 0 && CellBorderStyle.NONE != (rightBorderStyle = this.cellSheet.getCell(this.rowIndex, this.colIndex - 1).getCellStyle().getRightBorderStyle())) {
            return rightBorderStyle;
        }
        return this.getCellStyle().getLeftBorderStyle();
    }

    public void setLeftBorderStyle(CellBorderStyle leftBorderStyle) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            if (cellMerge.getRowSpan() > 1) {
                for (int i = cellMerge.getRowIndex(); i < cellMerge.getRowIndex() + cellMerge.getRowSpan(); ++i) {
                    CellBorderStyle rightBorderStyle;
                    Cell cell = this.cellSheet.getCell(i, cellMerge.getColumnIndex());
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setLeftBorderStyle(leftBorderStyle);
                    if (this.colIndex <= 0 || (rightBorderStyle = this.cellSheet.getCell(i, cellMerge.getColumnIndex() - 1).getRightBorderStyle()) == leftBorderStyle) continue;
                    cell = this.cellSheet.getCell(i, cellMerge.getColumnIndex() - 1);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setRightBorderStyle(leftBorderStyle);
                }
            } else {
                CellBorderStyle rightBorderStyle;
                Cell cell = this.cellSheet.getCell(cellMerge.getRowIndex(), cellMerge.getColumnIndex());
                CellStyle canEditCellStyle = cell.getCellStyle();
                canEditCellStyle.setLeftBorderStyle(leftBorderStyle);
                if (this.colIndex > 0 && (rightBorderStyle = this.cellSheet.getCell(cellMerge.getRowIndex(), cellMerge.getColumnIndex() - 1).getRightBorderStyle()) != leftBorderStyle && rightBorderStyle != CellBorderStyle.NONE) {
                    cell = this.cellSheet.getCell(cellMerge.getRowIndex(), cellMerge.getColumnIndex() - 1);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setRightBorderStyle(leftBorderStyle);
                }
            }
        } else {
            CellBorderStyle rightBorderStyle;
            CellStyle canEditCellStyle = this.getTopCellStyle();
            canEditCellStyle.setLeftBorderStyle(leftBorderStyle);
            if (this.colIndex > 0 && (rightBorderStyle = this.cellSheet.getCell(this.rowIndex, this.colIndex - 1).getRightBorderStyle()) != leftBorderStyle && rightBorderStyle != CellBorderStyle.NONE) {
                Cell cell = this.cellSheet.getCell(this.rowIndex, this.colIndex - 1);
                canEditCellStyle = cell.getCellStyle();
                canEditCellStyle.setRightBorderStyle(leftBorderStyle);
            }
        }
    }

    public void setSelfLeftBorderStyle(CellBorderStyle leftBorderStyle) {
        Cell leftCell;
        CellBorderStyle rightBorderStyle;
        this.cellStyle.setLeftBorderStyle(leftBorderStyle);
        if (this.colIndex > 0 && (rightBorderStyle = (leftCell = this.cellSheet.getCell(this.rowIndex, this.colIndex - 1)).getRightBorderStyle()) != leftBorderStyle && rightBorderStyle != CellBorderStyle.NONE) {
            leftCell.getCellStyle().setRightBorderStyle(leftBorderStyle);
        }
    }

    public CellBorderStyle getRightBorderStyle() {
        CellBorderStyle rightBorderStyle = this.getCellStyle().getRightBorderStyle();
        if (CellBorderStyle.NONE != rightBorderStyle) {
            return rightBorderStyle;
        }
        int currCol = this.colIndex;
        int colSize = this.cellSheet.getColumns().size();
        if (currCol < colSize - 1) {
            return this.cellSheet.getCell(this.rowIndex, currCol + 1).getLeftBorderStyle();
        }
        return rightBorderStyle;
    }

    public void setRightBorderStyle(CellBorderStyle rightBorderStyle) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            int currCol = cellMerge.getColumnSpan() > 1 ? cellMerge.getColumnIndex() + cellMerge.getColumnSpan() - 1 : cellMerge.getColumnIndex();
            int colSize = this.cellSheet.getColumns().size();
            if (cellMerge.getRowSpan() > 1) {
                for (int i = cellMerge.getRowIndex(); i < cellMerge.getRowIndex() + cellMerge.getRowSpan(); ++i) {
                    CellBorderStyle leftBorderStyle;
                    Cell cell = this.cellSheet.getCell(i, currCol);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setRightBorderStyle(rightBorderStyle);
                    if (currCol >= colSize - 1 || (leftBorderStyle = this.cellSheet.getCell(i, currCol + 1).getLeftBorderStyle()) == rightBorderStyle) continue;
                    cell = this.cellSheet.getCell(i, currCol + 1);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setLeftBorderStyle(rightBorderStyle);
                }
                if (cellMerge.getColumnSpan() > 1) {
                    CellStyle canEditTopCellStyle = this.getTopCellStyle();
                    canEditTopCellStyle.setRightBorderStyle(rightBorderStyle);
                }
            } else {
                CellBorderStyle leftBorderStyle;
                CellStyle canEditTopCellStyle = this.getTopCellStyle();
                canEditTopCellStyle.setRightBorderStyle(rightBorderStyle);
                Cell cell = this.cellSheet.getCell(cellMerge.getRowIndex(), currCol);
                canEditTopCellStyle = cell.getCellStyle();
                canEditTopCellStyle.setRightBorderStyle(rightBorderStyle);
                if (currCol < colSize - 1 && (leftBorderStyle = this.cellSheet.getCell(cellMerge.getRowIndex(), currCol + 1).getLeftBorderStyle()) != rightBorderStyle && leftBorderStyle != CellBorderStyle.NONE) {
                    cell = this.cellSheet.getCell(cellMerge.getRowIndex(), currCol + 1);
                    canEditTopCellStyle = cell.getCellStyle();
                    canEditTopCellStyle.setLeftBorderStyle(rightBorderStyle);
                }
            }
        } else {
            CellBorderStyle leftBorderStyle;
            CellStyle canEditTopCellStyle = this.getTopCellStyle();
            canEditTopCellStyle.setRightBorderStyle(rightBorderStyle);
            int colSize = this.cellSheet.getColumns().size();
            if (this.colIndex < colSize - 1 && (leftBorderStyle = this.cellSheet.getCell(this.rowIndex, this.colIndex + 1).getLeftBorderStyle()) != rightBorderStyle && leftBorderStyle != CellBorderStyle.NONE) {
                Cell cell = this.cellSheet.getCell(this.rowIndex, this.colIndex + 1);
                canEditTopCellStyle = cell.getCellStyle();
                canEditTopCellStyle.setLeftBorderStyle(rightBorderStyle);
            }
        }
    }

    public void setSelfRightBorderStyle(CellBorderStyle rightBorderStyle) {
        Cell rightCell;
        CellBorderStyle leftBorderStyle;
        this.cellStyle.setRightBorderStyle(rightBorderStyle);
        if (this.colIndex < this.cellSheet.getColumnCount() - 1 && (leftBorderStyle = (rightCell = this.cellSheet.getCell(this.rowIndex, this.colIndex + 1)).getLeftBorderStyle()) != rightBorderStyle && leftBorderStyle != CellBorderStyle.NONE) {
            rightCell.getCellStyle().setLeftBorderStyle(rightBorderStyle);
        }
    }

    public String getTopBorderColorHex() {
        String bottomBorderColor;
        if (this.rowIndex > 0 && StringUtils.isNotEmpty(bottomBorderColor = this.cellSheet.getCell(this.rowIndex - 1, this.colIndex).getCellStyle().getBottomBorderColor())) {
            return bottomBorderColor;
        }
        return this.getCellStyle().getTopBorderColor();
    }

    public String getBottomBorderColorHex() {
        String bottomBorderColor = this.getCellStyle().getBottomBorderColor();
        if (StringUtils.isNotEmpty(bottomBorderColor)) {
            return bottomBorderColor;
        }
        int rowSize = this.cellSheet.getRowCount();
        int currRow = this.rowIndex;
        if (currRow > 0 && currRow < rowSize - 1) {
            return this.cellSheet.getCell(currRow + 1, this.colIndex).getCellStyle().getTopBorderColor();
        }
        return bottomBorderColor;
    }

    public CellColor getTopBorderColor() {
        String topBorderColorIndex = this.getTopBorderColorHex();
        return StringUtils.isNotEmpty(topBorderColorIndex) ? new CellColor(topBorderColorIndex) : null;
    }

    public CellColor getBottomBorderColor() {
        String bottomBorderColorIndex = this.getBottomBorderColorHex();
        return StringUtils.isNotEmpty(bottomBorderColorIndex) ? new CellColor(bottomBorderColorIndex) : null;
    }

    public void setTopBorderColor(String topBorderColor) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            if (cellMerge.getColumnSpan() > 1) {
                for (int i = cellMerge.getColumnIndex(); i < cellMerge.getColumnIndex() + cellMerge.getColumnSpan(); ++i) {
                    String bottomBorderColor;
                    Cell cell = this.cellSheet.getCell(cellMerge.getRowIndex(), i);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setTopBorderColor(topBorderColor);
                    if (this.rowIndex <= 0 || (bottomBorderColor = this.cellSheet.getCell(cellMerge.getRowIndex() - 1, i).getBottomBorderColorHex()).equals(topBorderColor)) continue;
                    cell = this.cellSheet.getCell(cellMerge.getRowIndex() - 1, i);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setBottomBorderColor(topBorderColor);
                }
            } else {
                String bottomBorderColor;
                Cell cell = this.cellSheet.getCell(cellMerge.getRowIndex(), cellMerge.getColumnIndex());
                CellStyle canEditCellStyle = cell.getCellStyle();
                canEditCellStyle.setTopBorderColor(topBorderColor);
                if (this.rowIndex > 0 && !(bottomBorderColor = this.cellSheet.getCell(cellMerge.getRowIndex() - 1, cellMerge.getColumnIndex()).getBottomBorderColorHex()).equals(topBorderColor)) {
                    cell = this.cellSheet.getCell(cellMerge.getRowIndex() - 1, cellMerge.getColumnIndex());
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setBottomBorderColor(topBorderColor);
                }
            }
        } else {
            String bottomBorderColor;
            CellStyle canEditCellStyle = this.getTopCellStyle();
            canEditCellStyle.setTopBorderColor(topBorderColor);
            if (this.rowIndex > 0 && !(bottomBorderColor = this.cellSheet.getCell(this.rowIndex - 1, this.colIndex).getBottomBorderColorHex()).equals(topBorderColor)) {
                Cell cell = this.cellSheet.getCell(this.rowIndex - 1, this.colIndex);
                canEditCellStyle = cell.getCellStyle();
                canEditCellStyle.setBottomBorderColor(topBorderColor);
            }
        }
    }

    public void setBottomBorderColor(String bottomBorderColor) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            int currRow = cellMerge.getRowSpan() > 1 ? cellMerge.getRowIndex() + cellMerge.getRowSpan() - 1 : cellMerge.getRowIndex();
            int rowSize = this.cellSheet.getRowCount();
            if (cellMerge.getColumnSpan() > 1) {
                for (int i = cellMerge.getColumnIndex(); i < cellMerge.getColumnIndex() + cellMerge.getColumnSpan(); ++i) {
                    String topBorderColor;
                    Cell cell = this.cellSheet.getCell(currRow, i);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setBottomBorderColor(bottomBorderColor);
                    if (currRow >= rowSize - 1 || (topBorderColor = this.cellSheet.getCell(currRow + 1, i).getTopBorderColorHex()).equals(bottomBorderColor)) continue;
                    cell = this.cellSheet.getCell(currRow + 1, i);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setTopBorderColor(bottomBorderColor);
                }
                if (cellMerge.getRowSpan() > 1) {
                    CellStyle canEditTopCellStyle = this.getTopCellStyle();
                    canEditTopCellStyle.setBottomBorderColor(bottomBorderColor);
                }
            } else {
                String topBorderColor;
                CellStyle canEditTopCellStyle = this.getTopCellStyle();
                canEditTopCellStyle.setBottomBorderColor(bottomBorderColor);
                Cell cell = this.cellSheet.getCell(currRow, cellMerge.getColumnIndex());
                canEditTopCellStyle = cell.getCellStyle();
                canEditTopCellStyle.setBottomBorderColor(bottomBorderColor);
                if (currRow < rowSize - 1 && !(topBorderColor = this.cellSheet.getCell(currRow + 1, cellMerge.getColumnIndex()).getTopBorderColorHex()).equals(bottomBorderColor)) {
                    cell = this.cellSheet.getCell(currRow + 1, cellMerge.getColumnIndex());
                    canEditTopCellStyle = cell.getCellStyle();
                    canEditTopCellStyle.setTopBorderColor(bottomBorderColor);
                }
            }
        } else {
            String topBorderColor;
            CellStyle canEditTopCellStyle = this.getTopCellStyle();
            canEditTopCellStyle.setBottomBorderColor(bottomBorderColor);
            int rowSize = this.cellSheet.getRowCount();
            if (this.rowIndex < rowSize - 1 && !(topBorderColor = this.cellSheet.getCell(this.rowIndex + 1, this.colIndex).getTopBorderColorHex()).equals(bottomBorderColor)) {
                Cell cell = this.cellSheet.getCell(this.rowIndex + 1, this.colIndex);
                canEditTopCellStyle = cell.getCellStyle();
                canEditTopCellStyle.setTopBorderColor(bottomBorderColor);
            }
        }
    }

    public void setTopBorderColor(CellColor cellColor) {
        String indexOf = "";
        if (null != cellColor) {
            indexOf = cellColor.getHexString();
        }
        this.setTopBorderColor(indexOf);
    }

    public void setSelfTopBorderColor(String topBorderColor) {
        this.cellStyle.setTopBorderColor(topBorderColor);
        if (this.rowIndex > 0) {
            Cell topCell = this.cellSheet.getCell(this.rowIndex - 1, this.colIndex);
            topCell.getCellStyle().setBottomBorderColor(topBorderColor);
        }
    }

    public void setSelfTopBorderColor(CellColor topBorderColor) {
        this.setSelfTopBorderColor(topBorderColor == null ? "" : topBorderColor.getHexString());
    }

    public void setBottomBorderColor(CellColor cellColor) {
        String indexOf = "";
        if (null != cellColor) {
            indexOf = cellColor.getHexString();
        }
        this.setBottomBorderColor(indexOf);
    }

    public void setSelfBottomBorderColor(String bottomBorderColor) {
        this.cellStyle.setBottomBorderColor(bottomBorderColor);
        if (this.rowIndex < this.cellSheet.getRowCount() - 1) {
            Cell bottomCell = this.cellSheet.getCell(this.rowIndex + 1, this.colIndex);
            bottomCell.getCellStyle().setTopBorderColor(bottomBorderColor);
        }
    }

    public void setSelfBottomBorderColor(CellColor bottomBorderColor) {
        this.setSelfBottomBorderColor(bottomBorderColor == null ? "" : bottomBorderColor.getHexString());
    }

    public CellBorderStyle getTopBorderStyle() {
        CellBorderStyle bottomBorderStyle;
        if (this.rowIndex > 0 && CellBorderStyle.NONE != (bottomBorderStyle = this.cellSheet.getCell(this.rowIndex - 1, this.colIndex).getCellStyle().getBottomBorderStyle())) {
            return bottomBorderStyle;
        }
        return this.getCellStyle().getTopBorderStyle();
    }

    public CellBorderStyle getBottomBorderStyle() {
        CellBorderStyle bottomBorderStyle = this.getCellStyle().getBottomBorderStyle();
        if (CellBorderStyle.NONE != bottomBorderStyle) {
            return bottomBorderStyle;
        }
        int currRow = this.rowIndex;
        int rowSize = this.cellSheet.getRowCount();
        if (currRow < rowSize - 1) {
            return this.cellSheet.getCell(currRow + 1, this.colIndex).getCellStyle().getTopBorderStyle();
        }
        return bottomBorderStyle;
    }

    public void setTopBorderStyle(CellBorderStyle topBorderStyle) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            if (cellMerge.getColumnSpan() > 1) {
                for (int i = cellMerge.getColumnIndex(); i < cellMerge.getColumnIndex() + cellMerge.getColumnSpan(); ++i) {
                    CellBorderStyle bottomBorderStyle;
                    Cell cell = this.cellSheet.getCell(cellMerge.getRowIndex(), i);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setTopBorderStyle(topBorderStyle);
                    if (this.rowIndex <= 0 || (bottomBorderStyle = this.cellSheet.getCell(cellMerge.getRowIndex() - 1, i).getBottomBorderStyle()) == topBorderStyle) continue;
                    cell = this.cellSheet.getCell(cellMerge.getRowIndex() - 1, i);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setBottomBorderStyle(topBorderStyle);
                }
            } else {
                CellBorderStyle bottomBorderStyle;
                Cell cell = this.cellSheet.getCell(cellMerge.getRowIndex(), cellMerge.getColumnIndex());
                CellStyle canEditCellStyle = cell.getCellStyle();
                canEditCellStyle.setTopBorderStyle(topBorderStyle);
                if (this.rowIndex > 0 && (bottomBorderStyle = this.cellSheet.getCell(cellMerge.getRowIndex() - 1, cellMerge.getColumnIndex()).getBottomBorderStyle()) != topBorderStyle) {
                    cell = this.cellSheet.getCell(cellMerge.getRowIndex() - 1, cellMerge.getColumnIndex());
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setBottomBorderStyle(topBorderStyle);
                }
            }
        } else {
            CellBorderStyle bottomBorderStyle;
            CellStyle canEditCellStyle = this.getTopCellStyle();
            canEditCellStyle.setTopBorderStyle(topBorderStyle);
            if (this.rowIndex > 0 && (bottomBorderStyle = this.cellSheet.getCell(this.rowIndex - 1, this.colIndex).getBottomBorderStyle()) != topBorderStyle) {
                Cell cell = this.cellSheet.getCell(this.rowIndex - 1, this.colIndex);
                canEditCellStyle = cell.getCellStyle();
                canEditCellStyle.setBottomBorderStyle(topBorderStyle);
            }
        }
    }

    public void setSelfTopBorderStyle(CellBorderStyle topBorderStyle) {
        Cell topCell;
        CellBorderStyle bottomBorderStyle;
        this.cellStyle.setTopBorderStyle(topBorderStyle);
        if (this.rowIndex > 0 && (bottomBorderStyle = (topCell = this.cellSheet.getCell(this.rowIndex - 1, this.colIndex)).getBottomBorderStyle()) != topBorderStyle && bottomBorderStyle != CellBorderStyle.NONE) {
            topCell.getCellStyle().setBottomBorderStyle(topBorderStyle);
        }
    }

    public void setBottomBorderStyle(CellBorderStyle bottomBorderStyle) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            int currRow = cellMerge.getRowSpan() > 1 ? cellMerge.getRowIndex() + cellMerge.getRowSpan() - 1 : cellMerge.getRowIndex();
            int rowSize = this.cellSheet.getRowCount();
            if (cellMerge.getColumnSpan() > 1) {
                for (int i = cellMerge.getColumnIndex(); i < cellMerge.getColumnIndex() + cellMerge.getColumnSpan(); ++i) {
                    CellBorderStyle topBorderStyle;
                    Cell cell = this.cellSheet.getCell(currRow, i);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setBottomBorderStyle(bottomBorderStyle);
                    if (currRow >= rowSize - 1 || (topBorderStyle = this.cellSheet.getCell(currRow + 1, i).getTopBorderStyle()) == bottomBorderStyle) continue;
                    cell = this.cellSheet.getCell(currRow + 1, i);
                    canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setTopBorderStyle(bottomBorderStyle);
                }
                if (cellMerge.getRowSpan() > 1) {
                    CellStyle canEditTopCellStyle = this.getTopCellStyle();
                    canEditTopCellStyle.setBottomBorderStyle(bottomBorderStyle);
                }
            } else {
                CellBorderStyle topBorderStyle;
                CellStyle canEditTopCellStyle = this.getTopCellStyle();
                canEditTopCellStyle.setBottomBorderStyle(bottomBorderStyle);
                Cell cell = this.cellSheet.getCell(currRow, cellMerge.getColumnIndex());
                canEditTopCellStyle = cell.getCellStyle();
                canEditTopCellStyle.setBottomBorderStyle(bottomBorderStyle);
                if (currRow < rowSize - 1 && (topBorderStyle = this.cellSheet.getCell(currRow + 1, cellMerge.getColumnIndex()).getTopBorderStyle()) != bottomBorderStyle) {
                    cell = this.cellSheet.getCell(currRow + 1, cellMerge.getColumnIndex());
                    canEditTopCellStyle = cell.getCellStyle();
                    canEditTopCellStyle.setTopBorderStyle(bottomBorderStyle);
                }
            }
        } else {
            CellBorderStyle topBorderStyle;
            CellStyle canEditTopCellStyle = this.getTopCellStyle();
            canEditTopCellStyle.setBottomBorderStyle(bottomBorderStyle);
            int rowSize = this.cellSheet.getRowCount();
            if (this.rowIndex < rowSize - 1 && (topBorderStyle = this.cellSheet.getCell(this.rowIndex + 1, this.colIndex).getTopBorderStyle()) != bottomBorderStyle) {
                Cell cell = this.cellSheet.getCell(this.rowIndex + 1, this.colIndex);
                canEditTopCellStyle = cell.getCellStyle();
                canEditTopCellStyle.setTopBorderStyle(bottomBorderStyle);
            }
        }
    }

    public void setSelfBottomBorderStyle(CellBorderStyle bottomBorderStyle) {
        Cell bottomCell;
        CellBorderStyle topBorderStyle;
        this.cellStyle.setBottomBorderStyle(bottomBorderStyle);
        if (this.rowIndex < this.cellSheet.getRowCount() - 1 && (topBorderStyle = (bottomCell = this.cellSheet.getCell(this.rowIndex + 1, this.colIndex)).getTopBorderStyle()) != bottomBorderStyle && topBorderStyle != CellBorderStyle.NONE) {
            bottomCell.setTopBorderStyle(topBorderStyle);
        }
    }

    public CellBorderStyle getDiagonalUpStyle() {
        return this.getCellStyle().getDiagonalUpStyle();
    }

    public void setDiagonalUpStyle(CellBorderStyle diagonalUpStyle) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            for (int currRow = cellMerge.getRowIndex(); currRow < cellMerge.getRowIndex() + cellMerge.getRowSpan(); ++currRow) {
                for (int currColumn = cellMerge.getColumnIndex(); currColumn < cellMerge.getColumnIndex() + cellMerge.getColumnSpan(); ++currColumn) {
                    Cell cell = this.cellSheet.getCell(currRow, currColumn);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setDiagonalUpStyle(diagonalUpStyle);
                }
            }
        } else {
            CellStyle canEditCellStyle = this.cellSheet.getCell(this.rowIndex, this.colIndex).getCellStyle();
            canEditCellStyle.setDiagonalUpStyle(diagonalUpStyle);
        }
    }

    public CellBorderStyle getDiagonalDownStyle() {
        return this.getCellStyle().getDiagonalDownStyle();
    }

    public void setDiagonalDownStyle(CellBorderStyle diagonalDownStyle) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            for (int currRow = cellMerge.getRowIndex(); currRow < cellMerge.getRowIndex() + cellMerge.getRowSpan(); ++currRow) {
                for (int currColumn = cellMerge.getColumnIndex(); currColumn < cellMerge.getColumnIndex() + cellMerge.getColumnSpan(); ++currColumn) {
                    Cell cell = this.cellSheet.getCell(currRow, currColumn);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setDiagonalDownStyle(diagonalDownStyle);
                }
            }
        } else {
            CellStyle canEditCellStyle = this.cellSheet.getCell(this.rowIndex, this.colIndex).getCellStyle();
            canEditCellStyle.setDiagonalDownStyle(diagonalDownStyle);
        }
    }

    public String getDiagonalDownColorHex() {
        return this.getCellStyle().getDiagonalDownColor();
    }

    public CellColor getDiagonalDownColor() {
        String diagonalDownColor = this.getDiagonalDownColorHex();
        return StringUtils.isNotEmpty(diagonalDownColor) ? new CellColor(diagonalDownColor) : null;
    }

    public String getDiagonalUpColorHex() {
        return this.getCellStyle().getDiagonalUpColor();
    }

    public CellColor getDiagonalUpColor() {
        String diagonalUpColor = this.getDiagonalUpColorHex();
        return StringUtils.isNotEmpty(diagonalUpColor) ? new CellColor(diagonalUpColor) : null;
    }

    public void setDiagonalDownColor(String diagonalDownColor) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            for (int currRow = cellMerge.getRowIndex(); currRow < cellMerge.getRowIndex() + cellMerge.getRowSpan(); ++currRow) {
                for (int currColumn = cellMerge.getColumnIndex(); currColumn < cellMerge.getColumnIndex() + cellMerge.getColumnSpan(); ++currColumn) {
                    Cell cell = this.cellSheet.getCell(currRow, currColumn);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setDiagonalDownColor(diagonalDownColor);
                }
            }
        } else {
            CellStyle canEditTopCellStyle = this.getTopCellStyle();
            canEditTopCellStyle.setDiagonalDownColor(diagonalDownColor);
        }
    }

    public void setDiagonalUpColor(String diagonalUpColor) {
        if (this.merged) {
            CellMerge cellMerge = this.cellSheet.findMergeByPoint(this.mergeInfo);
            for (int currRow = cellMerge.getRowIndex(); currRow < cellMerge.getRowIndex() + cellMerge.getRowSpan(); ++currRow) {
                for (int currColumn = cellMerge.getColumnIndex(); currColumn < cellMerge.getColumnIndex() + cellMerge.getColumnSpan(); ++currColumn) {
                    Cell cell = this.cellSheet.getCell(currRow, currColumn);
                    CellStyle canEditCellStyle = cell.getCellStyle();
                    canEditCellStyle.setDiagonalUpColor(diagonalUpColor);
                }
            }
        } else {
            CellStyle canEditTopCellStyle = this.getTopCellStyle();
            canEditTopCellStyle.setDiagonalUpColor(diagonalUpColor);
        }
    }

    public void setDiagonalUpColor(CellColor cellColor) {
        this.setDiagonalUpColor(null != cellColor ? cellColor.getHexString() : "");
    }

    public void setDiagonalDownColor(CellColor cellColor) {
        this.setDiagonalDownColor(null != cellColor ? cellColor.getHexString() : "");
    }

    public FillPatternType getFillPatternType() {
        return this.getTopCellStyle().getFillPatternType();
    }

    public void setFillPatternType(FillPatternType fillPatternType) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setFillPatternType(fillPatternType);
    }

    public String getBackGroundColorHex() {
        return this.getTopCellStyle().getBackGroundColor();
    }

    public CellColor getBackGroundColor() {
        String backGroundColor = this.getBackGroundColorHex();
        return StringUtils.isNotEmpty(backGroundColor) ? new CellColor(backGroundColor) : null;
    }

    public void setBackGroundColor(String backGroundColor) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setBackGroundColor(backGroundColor);
    }

    public void setBackGroundColor(CellColor cellColor) {
        this.setBackGroundColor(null != cellColor ? cellColor.getHexString() : "");
    }

    public void setBackGroundImg(String backGroundImg) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setBackGroundImg(backGroundImg);
    }

    public String getBackGroundImg() {
        return this.getTopCellStyle().getBackGroundImg();
    }

    public String getFontColorHex() {
        return this.getTopCellStyle().getFontColor();
    }

    public CellColor getFontColor() {
        String fontColorHex = this.getFontColorHex();
        return StringUtils.isNotEmpty(fontColorHex) ? new CellColor(fontColorHex) : null;
    }

    public void setFontColor(String fontColor) {
        CellStyle canEditCellStyle = this.getTopCellStyle();
        canEditCellStyle.setFontColor(fontColor);
    }

    public void setFontColor(CellColor cellColor) {
        this.setFontColor(null != cellColor ? cellColor.getHexString() : "");
    }

    public void putPersistenceData(String key, String value) {
        if (null == this.persistenceData) {
            this.persistenceData = new JSONObject();
        }
        this.persistenceData.put(key, (Object)value);
    }

    public String getPersistenceData(String key) {
        if (null == this.persistenceData) {
            return null;
        }
        return this.persistenceData.getString(key);
    }

    public JSONObject getPersistenceData() {
        return this.persistenceData;
    }

    public void setPersistenceData(String json) {
        this.persistenceData = new JSONObject(json);
    }

    public String toString() {
        return "Cell [rowIndex=" + this.rowIndex + ", colIndex=" + this.colIndex + ", dataTypeId=" + this.dataTypeId + ", value=" + this.value + ", formula=" + this.formula + ", merged=" + this.merged + ", mergeInfo=" + this.mergeInfo + ", persistenceData=" + this.persistenceData + ", cellSheet=" + this.cellSheet + ", cellStyle=" + this.cellStyle + "]";
    }
}


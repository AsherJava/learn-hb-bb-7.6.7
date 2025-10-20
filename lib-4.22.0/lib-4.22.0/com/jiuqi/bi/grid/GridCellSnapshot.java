/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.GridCell;
import java.io.Serializable;

public final class GridCellSnapshot
implements Serializable {
    private static final long serialVersionUID = 3111211207981980086L;
    private byte[] data;
    private String cellData;
    private Object cellObj;
    private int col;
    private int row;
    private int leftEdgeColor;
    private int leftEdgeStyle;
    private int topEdgeColor;
    private int topEdgeStyle;

    public GridCellSnapshot(GridCell gridCell) {
        this.cellData = gridCell.getCellData();
        this.cellObj = gridCell.getCellObj();
        this.col = gridCell.getColNum();
        this.row = gridCell.getRowNum();
        this.initFieldSnapshot(gridCell);
    }

    public void restore(GridCell gridCell, boolean isCellDataNeedStore) {
        int rightEdgeColor = gridCell.getREdgeColor();
        int rightEdgeStyle = gridCell.getREdgeStyle();
        int bottomEdgeColor = gridCell.getBEdgeColor();
        int bottomEdgeStyle = gridCell.getBEdgeStyle();
        gridCell.setPropData(this.data);
        if (gridCell.getREdgeStyle() == 0 || gridCell.getREdgeStyle() == 1) {
            gridCell.setREdge(rightEdgeColor, rightEdgeStyle);
        }
        if (gridCell.getBEdgeStyle() == 0 || gridCell.getBEdgeStyle() == 1) {
            gridCell.setBEdge(bottomEdgeColor, bottomEdgeStyle);
        }
        if (this.leftEdgeStyle != 0 && this.leftEdgeStyle != 1) {
            gridCell.setLEdge(this.leftEdgeColor, this.leftEdgeStyle);
        }
        if (this.topEdgeStyle != 0 && this.topEdgeStyle != 1) {
            gridCell.setTEdge(this.topEdgeColor, this.topEdgeStyle);
        }
        if (isCellDataNeedStore) {
            gridCell.setCellData(this.cellData);
            gridCell.setCellObj(this.cellObj);
        }
    }

    public int col() {
        return this.col;
    }

    public int row() {
        return this.row;
    }

    private void initFieldSnapshot(GridCell gridCell) {
        CellField cellField = gridCell.getCellField();
        GridCell ltCell = gridCell.grid.getCell(cellField.left, cellField.top);
        this.leftEdgeColor = ltCell.getLEdgeColor();
        this.leftEdgeStyle = ltCell.getLEdgeStyle();
        this.topEdgeColor = ltCell.getTEdgeColor();
        this.topEdgeStyle = ltCell.getTEdgeStyle();
        if (cellField.getArea() == 1) {
            this.data = new byte[gridCell.getPropData().length];
            System.arraycopy(gridCell.getPropData(), 0, this.data, 0, gridCell.getPropData().length);
        } else {
            GridCell newCell = gridCell.grid.internalGetCell(ltCell.getColNum(), ltCell.getRowNum());
            GridCell lbCell = gridCell.grid.getCell(cellField.left, cellField.bottom);
            GridCell rtCell = gridCell.grid.getCell(cellField.right, cellField.top);
            newCell.setLEdge(ltCell.getLEdgeColor(), ltCell.getLEdgeStyle());
            newCell.setTEdge(ltCell.getTEdgeColor(), ltCell.getTEdgeStyle());
            newCell.setBEdge(lbCell.getBEdgeColor(), lbCell.getBEdgeStyle());
            newCell.setREdge(rtCell.getREdgeColor(), rtCell.getREdgeStyle());
            this.data = new byte[newCell.getPropData().length];
            System.arraycopy(newCell.getPropData(), 0, this.data, 0, newCell.getPropData().length);
        }
    }
}


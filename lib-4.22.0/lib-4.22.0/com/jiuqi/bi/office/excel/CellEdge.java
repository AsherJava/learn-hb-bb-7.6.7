/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.CellEdger;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import java.util.Objects;

public class CellEdge {
    private final GridData gridData;

    public CellEdge(GridData gridData) {
        this.gridData = gridData;
    }

    public EdgeInfo[] getBorders(int col, int row) {
        EdgeInfo[] edgeInfos = this.getSelfBorders(col, row);
        return this.fixBordersByAround(edgeInfos, col, row);
    }

    EdgeInfo[] fixBordersByAround(EdgeInfo[] edgeInfos, int col, int row) {
        CellEdger cellEdger;
        int idx;
        int row_leftCell;
        int col_leftCell;
        CellEdger cellEdger2;
        int idx2;
        int row_downCell;
        int col_downCell;
        CellEdger cellEdger3;
        int idx3;
        int row_rightCell;
        int col_rightCell;
        CellEdger cellEdger4;
        int idx4;
        int col_upCell = col;
        int row_upCell = row - 1;
        if (this.valid(col_upCell, row_upCell) && this.gridData.cellMerged(col_upCell, row_upCell) && (idx4 = col - (cellEdger4 = CellEdger.at(this.gridData, col_upCell, row_upCell)).getCol()) >= 0) {
            edgeInfos[0].style = cellEdger4.getBottomStyle(idx4);
            edgeInfos[0].color = cellEdger4.getBottomColor(idx4);
        }
        if (this.valid(col_rightCell = col + 1, row_rightCell = row) && this.gridData.cellMerged(col_rightCell, row_rightCell) && (idx3 = row - (cellEdger3 = CellEdger.at(this.gridData, col_rightCell, row_rightCell)).getRow()) >= 0) {
            edgeInfos[1].style = cellEdger3.getLeftStyle(idx3);
            edgeInfos[1].color = cellEdger3.getLeftColor(idx3);
        }
        if (this.valid(col_downCell = col, row_downCell = row + 1) && this.gridData.cellMerged(col_downCell, row_downCell) && (idx2 = col - (cellEdger2 = CellEdger.at(this.gridData, col_downCell, row_downCell)).getCol()) >= 0) {
            edgeInfos[2].style = cellEdger2.getTopStyle(idx2);
            edgeInfos[2].color = cellEdger2.getTopColor(idx2);
        }
        if (this.valid(col_leftCell = col - 1, row_leftCell = row) && this.gridData.cellMerged(col_leftCell, row_leftCell) && (idx = row - (cellEdger = CellEdger.at(this.gridData, col_leftCell, row_leftCell)).getRow()) >= 0) {
            edgeInfos[3].style = cellEdger.getRightStyle(idx);
            edgeInfos[3].color = cellEdger.getRightColor(idx);
        }
        return edgeInfos;
    }

    private boolean valid(int col, int row) {
        return col > 0 && col < this.gridData.getColCount() && row > 0 && row < this.gridData.getRowCount();
    }

    EdgeInfo[] getSelfBorders(int col, int row) {
        EdgeInfo[] edgeInfos = new EdgeInfo[]{new EdgeInfo(), new EdgeInfo(), new EdgeInfo(), new EdgeInfo()};
        GridCell gridCell = this.gridData.getCellEx(col, row);
        edgeInfos[0].style = gridCell.getTEdgeStyle();
        edgeInfos[0].color = gridCell.getTEdgeColor();
        edgeInfos[1].style = gridCell.getREdgeStyle();
        edgeInfos[1].color = gridCell.getREdgeColor();
        edgeInfos[2].style = gridCell.getBEdgeStyle();
        edgeInfos[2].color = gridCell.getBEdgeColor();
        edgeInfos[3].style = gridCell.getLEdgeStyle();
        edgeInfos[3].color = gridCell.getLEdgeColor();
        return edgeInfos;
    }

    public static class EdgeInfo {
        int style;
        int color;

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + Objects.hash(this.color, this.style);
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            EdgeInfo other = (EdgeInfo)obj;
            return this.color == other.color && this.style == other.style;
        }

        public int getStyle() {
            return this.style;
        }

        public int getColor() {
            return this.color;
        }
    }
}


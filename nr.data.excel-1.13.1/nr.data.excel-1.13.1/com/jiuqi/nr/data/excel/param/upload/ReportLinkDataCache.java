/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.Matrix
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 */
package com.jiuqi.nr.data.excel.param.upload;

import com.jiuqi.np.util.Matrix;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.util.List;

public class ReportLinkDataCache {
    private Matrix cellMatrix;
    private int minCol;
    private int maxCol;
    private int minRow;
    private int maxRow;

    public void init(List<DataLinkDefine> linkdatas) {
        this.cellMatrix = new Matrix();
        this.minCol = Integer.MAX_VALUE;
        this.minRow = Integer.MAX_VALUE;
        this.maxCol = Integer.MIN_VALUE;
        this.maxRow = Integer.MIN_VALUE;
        for (DataLinkDefine linkdata : linkdatas) {
            int row;
            int col = linkdata.getPosX();
            if (col < this.minCol) {
                this.minCol = col;
            }
            if (col > this.maxCol) {
                this.maxCol = col;
            }
            if ((row = linkdata.getPosY()) < this.minRow) {
                this.minRow = row;
            }
            if (row <= this.maxRow) continue;
            this.maxRow = row;
        }
        this.cellMatrix.setColCount(this.maxCol - this.minCol + 1);
        this.cellMatrix.setRowCount(this.maxRow - this.minRow + 1);
        for (DataLinkDefine linkdata : linkdatas) {
            this.cellMatrix.setItem(linkdata.getPosX() - this.minCol, linkdata.getPosY() - this.minRow, (Object)linkdata);
        }
    }

    public boolean hasLinkData(int row, int col) {
        if (row >= this.minRow && row <= this.maxRow && col >= this.minCol && col <= this.maxCol) {
            return this.cellMatrix.getItem(col - this.minCol, row - this.minRow) != null;
        }
        return false;
    }

    public DataLinkDefine getLinkData(int row, int col) {
        if (row >= this.minRow && row <= this.maxRow && col >= this.minCol && col <= this.maxCol) {
            return (DataLinkDefine)this.cellMatrix.getItem(col - this.minCol, row - this.minRow);
        }
        return null;
    }
}


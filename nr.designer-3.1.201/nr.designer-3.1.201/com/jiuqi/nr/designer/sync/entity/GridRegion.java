/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2CellField
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 */
package com.jiuqi.nr.designer.sync.entity;

import com.jiuqi.nvwa.grid2.Grid2CellField;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.HashSet;
import java.util.Set;

public class GridRegion {
    private int x;
    private int y;
    private int width;
    private int height;

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean contain(Grid2CellField rect) {
        return this.x <= rect.left && rect.left < this.x + this.width && this.y <= rect.top && rect.bottom < this.y + this.height;
    }

    public Set<GridCellData> getAllCells(Grid2Data grid) {
        HashSet<GridCellData> cells = new HashSet<GridCellData>();
        for (int i = 0; i < this.width; ++i) {
            for (int j = 0; j < this.height; ++j) {
                cells.add(grid.getGridCellData(this.x + i, this.y + j));
            }
        }
        return cells;
    }

    public GridRegion[] getRowRegions() {
        GridRegion[] regions = new GridRegion[this.height];
        for (int i = 0; i < this.height; ++i) {
            GridRegion copy = this.copy();
            copy.setY(this.y + i);
            copy.setHeight(1);
            regions[i] = copy;
        }
        return regions;
    }

    public GridRegion[] getColRegions() {
        GridRegion[] regions = new GridRegion[this.width];
        for (int i = 0; i < this.width; ++i) {
            GridRegion copy = this.copy();
            copy.setX(this.x + i);
            copy.setWidth(1);
            regions[i] = copy;
        }
        return regions;
    }

    private GridRegion copy() {
        GridRegion gridRegion = new GridRegion();
        gridRegion.setX(this.x);
        gridRegion.setY(this.y);
        gridRegion.setWidth(this.width);
        gridRegion.setHeight(this.height);
        return gridRegion;
    }

    public String toString() {
        return "GridRegion [x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + "]";
    }
}


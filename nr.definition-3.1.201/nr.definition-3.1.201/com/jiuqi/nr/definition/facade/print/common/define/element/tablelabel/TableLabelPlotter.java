/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridCell
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.draw2d.Color
 *  com.jiuqi.xg.draw2d.Pen
 *  com.jiuqi.xg.draw2d.XG
 *  com.jiuqi.xg.process.IDrawObject
 *  com.jiuqi.xg.process.IPaintContext
 *  com.jiuqi.xg.process.IProcessMonitor
 *  com.jiuqi.xg.process.impl.AbstractPlotter
 */
package com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel;

import com.jiuqi.grid.GridCell;
import com.jiuqi.grid.GridData;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelCellDrawer;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.TableLabelDrawObject;
import com.jiuqi.xg.draw2d.Color;
import com.jiuqi.xg.draw2d.Pen;
import com.jiuqi.xg.draw2d.XG;
import com.jiuqi.xg.process.IDrawObject;
import com.jiuqi.xg.process.IPaintContext;
import com.jiuqi.xg.process.IProcessMonitor;
import com.jiuqi.xg.process.impl.AbstractPlotter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableLabelPlotter
extends AbstractPlotter {
    private static final Logger logger = LoggerFactory.getLogger(TableLabelPlotter.class);
    private TableLabelCellDrawer cellDrawer;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void plot(IPaintContext context, IProcessMonitor monitor) {
        try {
            monitor.enter(((Object)((Object)this)).getClass(), ((Object)((Object)this)).getClass().getSimpleName(), "plot");
            monitor.beginTask("\u6267\u884c\u8868\u683c\u6807\u7b7e\u7ed8\u56fe\u5bf9\u8c61\u7684\u7ed8\u56fe\u5904\u7406\u8fc7\u7a0b", 100.0);
            TableLabelDrawObject tableLabel = (TableLabelDrawObject)this.element;
            if (this.interactor.adjustment((IDrawObject)tableLabel)) {
                this.initialize();
                this.plotBackground();
                this.performPlot(tableLabel);
                this.plotForeground();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            monitor.finishTask();
            monitor.exit(((Object)((Object)this)).getClass(), ((Object)((Object)this)).getClass().getSimpleName(), "plot");
        }
    }

    private void performPlot(TableLabelDrawObject table) {
        this.cellDrawer = new TableLabelCellDrawer();
        this.cellDrawer.init(table, this.graphics, this.x, this.y);
        GridData gridData = table.getGridData();
        int rowCount = gridData.getRowCount();
        int colCount = gridData.getColCount();
        double tempX = this.x;
        double tempY = this.y;
        for (int col = 1; col < colCount; ++col) {
            for (int row = 1; row < rowCount; ++row) {
                GridCell cell = gridData.getCell(col, row);
                this.performDrawCell(cell, tempX, tempY);
                tempY += XG.DEFAULT_LENGTH_UNIT.fromPixel((double)gridData.getRowHeights(row));
            }
            tempY = this.y;
            tempX += XG.DEFAULT_LENGTH_UNIT.fromPixel((double)gridData.getColWidths(col));
        }
        this.drawTableLine(gridData);
    }

    private void drawTableLine(GridData gridData) {
        List<double[]> coordinateList = this.getCoordinateList(gridData);
        if (coordinateList == null || coordinateList.isEmpty()) {
            return;
        }
        Pen pen = this.graphics.getPen();
        pen.setColor(Color.BLACK);
        pen.setWidth(0.37);
        pen.setDashOffset(0.0);
        for (double[] coordinate : coordinateList) {
            this.graphics.drawLine(coordinate[0], coordinate[1], coordinate[2], coordinate[3]);
        }
    }

    private List<double[]> getCoordinateList(GridData gridData) {
        int row;
        ArrayList<double[]> coordinateList = new ArrayList<double[]>();
        int colCount = gridData.getColCount();
        int rowCount = gridData.getRowCount();
        double tableWidth = 0.0;
        double tableHeight = 0.0;
        double[] Y = new double[rowCount];
        double[] X = new double[colCount];
        X[0] = this.x;
        Y[0] = this.y;
        for (row = 1; row < rowCount; ++row) {
            Y[row] = (tableHeight += XG.DEFAULT_LENGTH_UNIT.fromPixel((double)gridData.getRowHeights(row))) + this.y;
        }
        for (int col = 1; col < colCount; ++col) {
            X[col] = (tableWidth += XG.DEFAULT_LENGTH_UNIT.fromPixel((double)gridData.getColWidths(col))) + this.x;
        }
        for (row = 1; row < rowCount; ++row) {
            for (int col = 1; col < colCount; ++col) {
                GridCell cell = gridData.getCell(col, row);
                int rowSpan = cell.getRowSpan();
                int colSpan = cell.getColSpan();
                if (rowSpan == 0 || colSpan == 0) continue;
                if (rowSpan > 1 || colSpan > 1) {
                    if (row == 1 && cell.getTEdgeStyle() != 1) {
                        coordinateList.add(this.getcoordinate(col - 1, row - 1, col + colSpan - 1, row - 1, Y, X));
                    }
                    if (col == 1 && cell.getLEdgeStyle() != 1) {
                        coordinateList.add(this.getcoordinate(col - 1, row - 1, col - 1, row + rowSpan - 1, Y, X));
                    }
                    if (cell.getBEdgeStyle() != 1) {
                        coordinateList.add(this.getcoordinate(col - 1, row + rowSpan - 1, col + colSpan - 1, row + rowSpan - 1, Y, X));
                    }
                    if (cell.getREdgeStyle() == 1) continue;
                    coordinateList.add(this.getcoordinate(col + colSpan - 1, row - 1, col + colSpan - 1, row + rowSpan - 1, Y, X));
                    continue;
                }
                if (row == 1 && cell.getTEdgeStyle() != 1) {
                    coordinateList.add(this.getcoordinate(col - 1, row - 1, col, row - 1, Y, X));
                }
                if (col == 1 && cell.getLEdgeStyle() != 1) {
                    coordinateList.add(this.getcoordinate(col - 1, row - 1, col - 1, row, Y, X));
                }
                if (cell.getBEdgeStyle() != 1) {
                    coordinateList.add(this.getcoordinate(col - 1, row, col, row, Y, X));
                }
                if (cell.getREdgeStyle() == 1) continue;
                coordinateList.add(this.getcoordinate(col, row - 1, col, row, Y, X));
            }
        }
        return coordinateList;
    }

    private double[] getcoordinate(int x1, int y1, int x2, int y2, double[] Y, double[] X) {
        double[] coordinateB = new double[]{X[x1], Y[y1], X[x2], Y[y2]};
        return coordinateB;
    }

    private void performDrawCell(GridCell cell, double offsetX, double offsetY) {
        int rowSpan = cell.getRowSpan();
        int colSpan = cell.getColSpan();
        if (rowSpan > 0 && colSpan > 0) {
            this.cellDrawer.drawCell(cell, offsetX, offsetY);
        }
    }
}


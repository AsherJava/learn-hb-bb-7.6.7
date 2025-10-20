/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.CellBuffer;
import com.jiuqi.bi.grid.CellField;
import com.jiuqi.bi.grid.CellPostion;
import com.jiuqi.bi.grid.EdgePosition;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.util.StringUtils;
import java.util.Map;
import java.util.TreeMap;

public class CellEdger {
    private final GridData grid;
    private final CellField field;
    private CellBuffer[] buffers;

    public static CellEdger at(GridData grid, int col, int row) {
        return new CellEdger(grid, col, row);
    }

    public static CellEdger of(GridCell cell) {
        return new CellEdger(cell.grid, cell.getColNum(), cell.getRowNum());
    }

    public CellEdger(GridData grid, int col, int row) {
        if (col <= 0 || col >= grid.getColCount()) {
            throw new IndexOutOfBoundsException("\u5217\u53f7\u8d8a\u754c\uff1a" + col);
        }
        if (row <= 0 || row >= grid.getRowCount()) {
            throw new IndexOutOfBoundsException("\u884c\u53f7\u8d8a\u754c\uff1a" + row);
        }
        this.grid = grid;
        this.field = grid.expandCell(col, row);
        this.buffers = new CellBuffer[EdgePosition.AllEdges.length];
    }

    public GridData getGrid() {
        return this.grid;
    }

    public int getCol() {
        return this.field.left;
    }

    public int getRow() {
        return this.field.top;
    }

    public int getLeftStyle(int index) {
        CellBuffer buffer = this.getLeftBuffer();
        return this.getStyle(buffer, index);
    }

    public int getLeftColor(int index) {
        CellBuffer buffer = this.getLeftBuffer();
        return this.getColor(buffer, index);
    }

    public int[] getLeftStyles() {
        CellBuffer buffer = this.getLeftBuffer();
        return this.getStyles(buffer, this.field.getHeight());
    }

    public int[] getLeftColors() {
        CellBuffer buffer = this.getLeftBuffer();
        return this.getColors(buffer, this.field.getHeight());
    }

    public void setLeftStyle(int index, int style) {
        CellBuffer buffer = this.getLeftBuffer();
        this.setStyle(buffer, index, style);
        this.change(buffer);
    }

    public void setLeftColor(int index, int color) {
        CellBuffer buffer = this.getLeftBuffer();
        this.setColor(buffer, index, color);
        this.change(buffer);
    }

    public void setLeftStyles(int ... styles) {
        CellBuffer buffer = this.getLeftBuffer();
        this.setStyles(buffer, this.field.getHeight(), styles);
        this.change(buffer);
    }

    public void setLeftColors(int ... colors) {
        CellBuffer buffer = this.getLeftBuffer();
        this.setColors(buffer, this.field.getHeight(), colors);
        this.change(buffer);
    }

    private CellBuffer getLeftBuffer() {
        if (this.buffers[0] == null) {
            this.buffers[0] = this.loadLeftBuffer();
        }
        return this.buffers[0];
    }

    private CellBuffer loadLeftBuffer() {
        int len = this.field.getHeight();
        CellBuffer buffer = new CellBuffer(len * 3 + 1);
        for (int row = this.field.top; row <= this.field.bottom; ++row) {
            byte[] cellProp = this.grid.internalGetCellPropData(this.field.left - 1, row);
            buffer.fill(cellProp, 13, (row - this.field.top) * 3, 3);
        }
        return buffer;
    }

    public int getTopStyle(int index) {
        CellBuffer buffer = this.getTopBuffer();
        return this.getStyle(buffer, index);
    }

    public int getTopColor(int index) {
        CellBuffer buffer = this.getTopBuffer();
        return this.getColor(buffer, index);
    }

    public int[] getTopStyles() {
        CellBuffer buffer = this.getTopBuffer();
        return this.getStyles(buffer, this.field.getWidth());
    }

    public int[] getTopColors() {
        CellBuffer buffer = this.getTopBuffer();
        return this.getColors(buffer, this.field.getWidth());
    }

    public void setTopStyle(int index, int style) {
        CellBuffer buffer = this.getTopBuffer();
        this.setStyle(buffer, index, style);
        this.change(buffer);
    }

    public void setTopColor(int index, int color) {
        CellBuffer buffer = this.getTopBuffer();
        this.setColor(buffer, index, color);
        this.change(buffer);
    }

    public void setTopStyles(int ... styles) {
        CellBuffer buffer = this.getTopBuffer();
        this.setStyles(buffer, this.field.getWidth(), styles);
        this.change(buffer);
    }

    public void setTopColors(int ... colors) {
        CellBuffer buffer = this.getTopBuffer();
        this.setColors(buffer, this.field.getWidth(), colors);
        this.change(buffer);
    }

    private CellBuffer getTopBuffer() {
        if (this.buffers[1] == null) {
            this.buffers[1] = this.loadTopBuffer();
        }
        return this.buffers[1];
    }

    private CellBuffer loadTopBuffer() {
        int len = this.field.getWidth();
        CellBuffer buffer = new CellBuffer(len * 3 + 1);
        for (int col = this.field.left; col <= this.field.right; ++col) {
            byte[] cellProp = this.grid.internalGetCellPropData(col, this.field.top - 1);
            buffer.fill(cellProp, 16, (col - this.field.left) * 3, 3);
        }
        return buffer;
    }

    public int getRightStyle(int index) {
        CellBuffer buffer = this.getRightBuffer();
        return this.getStyle(buffer, index);
    }

    public int getRightColor(int index) {
        CellBuffer buffer = this.getRightBuffer();
        return this.getColor(buffer, index);
    }

    public int[] getRightStyles() {
        CellBuffer buffer = this.getRightBuffer();
        return this.getStyles(buffer, this.field.getHeight());
    }

    public int[] getRightColors() {
        CellBuffer buffer = this.getRightBuffer();
        return this.getColors(buffer, this.field.getHeight());
    }

    public void setRightStyle(int index, int style) {
        CellBuffer buffer = this.getRightBuffer();
        this.setStyle(buffer, index, style);
        this.change(buffer);
    }

    public void setRightColor(int index, int color) {
        CellBuffer buffer = this.getRightBuffer();
        this.setColor(buffer, index, color);
        this.change(buffer);
    }

    public void setRightStyles(int ... styles) {
        CellBuffer buffer = this.getRightBuffer();
        this.setStyles(buffer, this.field.getHeight(), styles);
        this.change(buffer);
    }

    public void setRightColors(int ... colors) {
        CellBuffer buffer = this.getRightBuffer();
        this.setColors(buffer, this.field.getHeight(), colors);
        this.change(buffer);
    }

    private CellBuffer getRightBuffer() {
        if (this.buffers[2] == null) {
            this.buffers[2] = this.loadRightBuffer();
        }
        return this.buffers[2];
    }

    private CellBuffer loadRightBuffer() {
        int len = this.field.getHeight();
        CellBuffer buffer = new CellBuffer(len * 3 + 1);
        for (int row = this.field.top; row <= this.field.bottom; ++row) {
            byte[] cellProp = this.grid.internalGetCellPropData(this.field.right, row);
            buffer.fill(cellProp, 13, (row - this.field.top) * 3, 3);
        }
        return buffer;
    }

    public int getBottomStyle(int index) {
        CellBuffer buffer = this.getBottomBuffer();
        return this.getStyle(buffer, index);
    }

    public int getBottomColor(int index) {
        CellBuffer buffer = this.getBottomBuffer();
        return this.getColor(buffer, index);
    }

    public int[] getBottomStyles() {
        CellBuffer buffer = this.getBottomBuffer();
        return this.getStyles(buffer, this.field.getWidth());
    }

    public int[] getBottomColors() {
        CellBuffer buffer = this.getBottomBuffer();
        return this.getColors(buffer, this.field.getWidth());
    }

    public void setBottomStyle(int index, int style) {
        CellBuffer buffer = this.getBottomBuffer();
        this.setStyle(buffer, index, style);
        this.change(buffer);
    }

    public void setBottomColor(int index, int color) {
        CellBuffer buffer = this.getBottomBuffer();
        this.setColor(buffer, index, color);
        this.change(buffer);
    }

    public void setBottomStyles(int ... styles) {
        CellBuffer buffer = this.getBottomBuffer();
        this.setStyles(buffer, this.field.getWidth(), styles);
        this.change(buffer);
    }

    public void setBottomColors(int ... colors) {
        CellBuffer buffer = this.getBottomBuffer();
        this.setColors(buffer, this.field.getWidth(), colors);
        this.change(buffer);
    }

    private CellBuffer getBottomBuffer() {
        if (this.buffers[3] == null) {
            this.buffers[3] = this.loadBottomBuffer();
        }
        return this.buffers[3];
    }

    private CellBuffer loadBottomBuffer() {
        int len = this.field.getWidth();
        CellBuffer buffer = new CellBuffer(len * 3 + 1);
        for (int col = this.field.left; col <= this.field.right; ++col) {
            byte[] cellProp = this.grid.internalGetCellPropData(col, this.field.bottom);
            buffer.fill(cellProp, 16, (col - this.field.left) * 3, 3);
        }
        return buffer;
    }

    private int getStyle(CellBuffer buffer, int index) {
        return buffer.getByte(index * 3);
    }

    private int[] getStyles(CellBuffer buffer, int len) {
        int[] styles = new int[len];
        for (int i = 0; i < len; ++i) {
            styles[i] = this.getStyle(buffer, i);
        }
        return styles;
    }

    private void setStyle(CellBuffer buffer, int index, int style) {
        buffer.setByte(index * 3, style);
    }

    private void setStyles(CellBuffer buffer, int len, int ... styles) {
        if (styles.length > len) {
            throw new IllegalArgumentException("\u8bbe\u7f6e\u8fb9\u6846\u6837\u5f0f\u6570\u91cf\u8d85\u51fa\u5f53\u524d\u533a\u57df");
        }
        for (int i = 0; i < Math.min(styles.length, len); ++i) {
            this.setStyle(buffer, i, styles[i]);
        }
    }

    private int getColor(CellBuffer buffer, int index) {
        int colorIndex = buffer.getUnsignedShort(index * 3 + 1);
        return this.grid.edgeColors().get(colorIndex);
    }

    private int[] getColors(CellBuffer buffer, int len) {
        int[] colors = new int[len];
        for (int i = 0; i < len; ++i) {
            colors[i] = this.getColor(buffer, i);
        }
        return colors;
    }

    private void setColor(CellBuffer buffer, int index, int color) {
        int colorIndex = GridCell.toColorIndex(color, this.grid.edgeColors());
        buffer.setUnsighedShort(index * 3 + 1, colorIndex);
    }

    private void setColors(CellBuffer buffer, int len, int ... colors) {
        if (colors.length > len) {
            throw new IllegalArgumentException("\u8bbe\u7f6e\u8fb9\u6846\u989c\u8272\u6570\u91cf\u8d85\u51fa\u5f53\u524d\u533a\u57df");
        }
        for (int i = 0; i < Math.min(colors.length, len); ++i) {
            this.setColor(buffer, i, colors[i]);
        }
    }

    private void change(CellBuffer buffer) {
        buffer.setByte(buffer.length() - 1, 1);
    }

    private boolean isChanged(int position) {
        CellBuffer buffer = this.buffers[position];
        if (buffer == null) {
            return false;
        }
        return buffer.getByte(buffer.length() - 1) != 0;
    }

    public void apply() {
        TreeMap<CellPostion, GridCell> cells = new TreeMap<CellPostion, GridCell>();
        this.scanLeft(cells);
        this.scanTop(cells);
        this.scanRight(cells);
        this.scanBottom(cells);
        this.apply(cells);
    }

    private void scanLeft(Map<CellPostion, GridCell> cells) {
        if (!this.isChanged(0)) {
            return;
        }
        CellBuffer buffer = this.buffers[0];
        for (int row = this.field.top; row <= this.field.bottom; ++row) {
            GridCell cell = this.openCell(cells, this.field.left - 1, row);
            cell.getPropBuffer().fill(buffer, (row - this.field.top) * 3, 13, 3);
        }
    }

    private void scanTop(Map<CellPostion, GridCell> cells) {
        if (!this.isChanged(1)) {
            return;
        }
        CellBuffer buffer = this.buffers[1];
        for (int col = this.field.left; col <= this.field.right; ++col) {
            GridCell cell = this.openCell(cells, col, this.field.top - 1);
            cell.getPropBuffer().fill(buffer, (col - this.field.left) * 3, 16, 3);
        }
    }

    private void scanRight(Map<CellPostion, GridCell> cells) {
        if (!this.isChanged(2)) {
            return;
        }
        CellBuffer buffer = this.buffers[2];
        for (int row = this.field.top; row <= this.field.bottom; ++row) {
            GridCell cell = this.openCell(cells, this.field.right, row);
            cell.getPropBuffer().fill(buffer, (row - this.field.top) * 3, 13, 3);
        }
    }

    private void scanBottom(Map<CellPostion, GridCell> cells) {
        if (!this.isChanged(3)) {
            return;
        }
        CellBuffer buffer = this.buffers[3];
        for (int col = this.field.left; col <= this.field.right; ++col) {
            GridCell cell = this.openCell(cells, col, this.field.bottom);
            cell.getPropBuffer().fill(buffer, (col - this.field.left) * 3, 16, 3);
        }
    }

    private GridCell openCell(Map<CellPostion, GridCell> cells, int col, int row) {
        CellPostion pos = new CellPostion(col, row);
        return cells.computeIfAbsent(pos, p -> this.grid.internalGetCell(p.col(), p.row()));
    }

    private void apply(Map<CellPostion, GridCell> cells) {
        cells.values().forEach(cell -> this.grid.internalSetCell((GridCell)cell));
        for (CellBuffer buffer : this.buffers) {
            if (buffer == null) continue;
            buffer.setByte(buffer.length() - 1, 0);
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < EdgePosition.AllEdges.length; ++i) {
            buffer.append(EdgePosition.AllEdgeNames[i]).append(": ");
            CellBuffer buf = this.buffers[EdgePosition.AllEdges[i]];
            if (buf == null) {
                buffer.append("NONE");
            } else {
                buffer.append(buf);
            }
            buffer.append(StringUtils.LINE_SEPARATOR);
        }
        return buffer.toString();
    }
}


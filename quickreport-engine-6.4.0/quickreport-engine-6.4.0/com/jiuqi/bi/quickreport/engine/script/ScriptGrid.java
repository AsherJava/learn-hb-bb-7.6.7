/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridColor
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.util.ColorUtil
 */
package com.jiuqi.bi.quickreport.engine.script;

import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.script.IScriptObj;
import com.jiuqi.bi.util.ColorUtil;
import java.util.HashMap;
import java.util.Map;

public final class ScriptGrid
implements IScriptObj {
    public static final String ID = "grid";
    private static final Map<String, GridColor> colorMap = new HashMap<String, GridColor>(64);
    private GridData grid;

    public ScriptGrid(GridData grid) {
        this.grid = grid;
    }

    public double getDouble(int col, int row) {
        return this.grid.getCellEx(col, row).getFloat();
    }

    public String getValue(int col, int row) {
        return this.grid.getCellData(col, row);
    }

    public boolean getBoolean(int col, int row) {
        return this.grid.getCellEx(col, row).getBoolean();
    }

    public void setColVisible(int col, boolean value) {
        this.grid.setColVisible(col, value);
    }

    public void setRowVisible(int row, boolean value) {
        this.grid.setRowVisible(row, value);
    }

    public void setBackground(int col, int row, int rgb) {
        this.setBackground(col, row, GridColor.valueOf((int)rgb));
    }

    public void setBackground(int col, int row, String color) {
        GridColor gridColor = colorMap.get(color.toUpperCase());
        if (gridColor == null) {
            int rgb = ColorUtil.parseHexRGBColor((String)color);
            this.setBackground(col, row, rgb);
        } else {
            this.setBackground(col, row, gridColor);
        }
    }

    private void setBackground(int col, int row, GridColor color) {
        GridCell gridCell = this.grid.getCellEx(col, row);
        gridCell.setBackColor(color);
        this.grid.setCell(col, row, gridCell);
    }

    @Override
    public Object getObj() {
        return this.grid;
    }

    static {
        for (GridColor gridColor : GridColor.PALLETTE) {
            colorMap.put(gridColor.toString(), gridColor);
        }
    }
}


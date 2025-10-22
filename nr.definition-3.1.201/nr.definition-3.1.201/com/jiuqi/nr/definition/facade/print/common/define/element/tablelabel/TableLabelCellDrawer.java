/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridCell
 */
package com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel;

import com.jiuqi.grid.GridCell;
import com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel.AbstractTableLabelFieldDrawer;

public class TableLabelCellDrawer
extends AbstractTableLabelFieldDrawer {
    public void drawCell(GridCell cell, double offsetX, double offsetY) {
        super.drawFieldContent(cell.getCellField(), offsetX, offsetY);
    }
}


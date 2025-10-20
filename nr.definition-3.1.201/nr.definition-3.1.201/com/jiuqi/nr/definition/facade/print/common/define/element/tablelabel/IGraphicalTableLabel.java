/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.xg.process.IGraphicalElement
 */
package com.jiuqi.nr.definition.facade.print.common.define.element.tablelabel;

import com.jiuqi.grid.GridData;
import com.jiuqi.xg.process.IGraphicalElement;

public interface IGraphicalTableLabel
extends IGraphicalElement {
    public static final String KIND = "element_tableLabel";
    public static final String PROPERTY_GRID_DATA = "gridData";

    public GridData getGridData();
}


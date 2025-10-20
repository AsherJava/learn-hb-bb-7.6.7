/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.sync;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.designer.sync.IAction;
import com.jiuqi.nr.designer.sync.entity.GridRegion;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.Arrays;

public abstract class AbstractAction
implements IAction {
    private String name;
    private GridRegion[] selections;
    private Object params;

    public AbstractAction(String name, GridRegion[] selections, Object params) {
        this.name = name;
        this.selections = selections;
        this.params = params;
    }

    protected boolean isAllRow(Grid2Data grid, GridRegion region) {
        return region.getWidth() == grid.getColumnCount() - 1;
    }

    protected boolean isAllCol(Grid2Data grid, GridRegion region) {
        return region.getHeight() == grid.getRowCount() - 1;
    }

    @JsonIgnore
    public String getName() {
        return this.name;
    }

    public GridRegion[] getSelections() {
        return this.selections;
    }

    public Object getParams() {
        return this.params;
    }

    public String toString() {
        return String.format("\u6267\u884c\u64cd\u4f5c\uff1a%s\uff0c\u9009\u4e2d\u533a\u57df\uff1a%s\uff0c\u53c2\u6570\uff1a%s\u3002", this.name, Arrays.toString(this.selections), this.params);
    }
}


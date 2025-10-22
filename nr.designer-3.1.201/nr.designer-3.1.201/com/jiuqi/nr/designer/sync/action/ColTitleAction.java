/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.sync.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.sync.action.HiddenAction;
import com.jiuqi.nr.designer.sync.entity.GridRegion;
import com.jiuqi.nvwa.grid2.Grid2Data;

public class ColTitleAction
extends HiddenAction {
    @JsonCreator
    public ColTitleAction(@JsonProperty(value="name") String name, @JsonProperty(value="selections") GridRegion[] selections, @JsonProperty(value="params") Object params) {
        super(name, selections, params);
    }

    @Override
    public void run(Grid2Data grid) {
        for (GridRegion region : this.getSelections()) {
            this.showHiddenColTitle(grid, region);
        }
    }
}


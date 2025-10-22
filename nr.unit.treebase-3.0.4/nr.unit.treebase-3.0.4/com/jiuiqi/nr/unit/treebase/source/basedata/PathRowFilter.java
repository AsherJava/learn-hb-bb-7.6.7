/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupRowFilter;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.ArrayList;
import java.util.List;

public class PathRowFilter
implements IGroupRowFilter {
    private final List<String> nodePath = new ArrayList<String>();

    public PathRowFilter(List<String> nodePath) {
        this.nodePath.addAll(nodePath);
    }

    @Override
    public boolean isValidRow(IEntityRow row) {
        return this.nodePath.contains(row.getEntityKeyData());
    }
}


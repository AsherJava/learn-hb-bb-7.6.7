/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface IGroupDataRow
extends IEntityRow {
    public static final String TYPE_OF_GROUP = "node@Group";
    public static final String TYPE_OF_DIM = "node@Dim";
    public static final String KEY_OF_UN_GROUPED = "Un-Grouped";
    public static final String TITLE_OF_UN_GROUPED = "\u672a\u5206\u7ec4";

    public String getReferEntityId();

    public String getRowType();

    public boolean isLeaf();

    public IEntityRow getData();

    public IGroupDataRow getParent();

    public List<IGroupDataRow> getChildren();
}


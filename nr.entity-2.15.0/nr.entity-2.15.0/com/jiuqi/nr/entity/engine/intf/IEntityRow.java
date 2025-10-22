/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.nr.entity.engine.intf.IDataRow;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;

public interface IEntityRow
extends IEntityItem,
IDataRow {
    public String[] getParentsEntityKeyDataPath();

    public boolean isLeaf();

    public boolean hasChildren();
}


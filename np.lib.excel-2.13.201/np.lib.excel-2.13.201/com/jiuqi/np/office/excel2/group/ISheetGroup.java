/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.group;

import com.jiuqi.np.office.excel2.group.GroupDirection;

public interface ISheetGroup {
    public int getStartIndex();

    public int getEndIndex();

    public GroupDirection getGroupDirection();

    public boolean isCollapsed();
}


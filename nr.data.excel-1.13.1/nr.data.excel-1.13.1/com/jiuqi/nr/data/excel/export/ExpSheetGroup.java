/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.office.excel2.group.GroupDirection
 *  com.jiuqi.np.office.excel2.group.ISheetGroup
 *  com.jiuqi.nr.definition.common.FormFoldingDirEnum
 */
package com.jiuqi.nr.data.excel.export;

import com.jiuqi.np.office.excel2.group.GroupDirection;
import com.jiuqi.np.office.excel2.group.ISheetGroup;
import com.jiuqi.nr.data.excel.obj.ExpFormFolding;
import com.jiuqi.nr.definition.common.FormFoldingDirEnum;

public class ExpSheetGroup
implements ISheetGroup {
    private final ExpFormFolding expFormFolding;
    private Boolean collapsed;
    private int startIndex = -1;
    private int endIndex = -1;

    public ExpSheetGroup(ExpFormFolding expFormFolding) {
        this.expFormFolding = expFormFolding;
    }

    public ExpSheetGroup(ExpFormFolding expFormFolding, Boolean collapsed) {
        this.expFormFolding = expFormFolding;
        this.collapsed = collapsed;
    }

    public ExpSheetGroup(ExpFormFolding expFormFolding, int startIndex, int endIndex) {
        this.expFormFolding = expFormFolding;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public ExpSheetGroup(ExpFormFolding expFormFolding, int startIndex, int endIndex, Boolean collapsed) {
        this.expFormFolding = expFormFolding;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.collapsed = collapsed;
    }

    public int getStartIndex() {
        if (this.startIndex > 0) {
            return this.startIndex - 1;
        }
        return this.expFormFolding.getStartIdx() - 1;
    }

    public int getEndIndex() {
        if (this.endIndex > 0) {
            return this.endIndex - 1;
        }
        return this.expFormFolding.getEndIdx() - 1;
    }

    public GroupDirection getGroupDirection() {
        FormFoldingDirEnum direction = this.expFormFolding.getDirection();
        if (direction == FormFoldingDirEnum.ROW_DIRECTION) {
            return GroupDirection.ROW;
        }
        return GroupDirection.COL;
    }

    public boolean isCollapsed() {
        if (this.collapsed != null) {
            return this.collapsed;
        }
        return this.expFormFolding.isCollapsed();
    }
}


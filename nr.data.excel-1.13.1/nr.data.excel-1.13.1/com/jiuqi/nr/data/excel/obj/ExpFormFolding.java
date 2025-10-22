/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormFoldingDirEnum
 *  com.jiuqi.nr.definition.facade.FormFoldingDefine
 */
package com.jiuqi.nr.data.excel.obj;

import com.jiuqi.nr.definition.common.FormFoldingDirEnum;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;

public class ExpFormFolding {
    private int startIdx;
    private int endIdx;
    private FormFoldingDirEnum direction;
    private boolean collapsed;

    public ExpFormFolding() {
    }

    public ExpFormFolding(FormFoldingDefine formFoldingDefine) {
        this.init(formFoldingDefine);
    }

    private void init(FormFoldingDefine formFoldingDefine) {
        Integer foldingDefineStartIdx = formFoldingDefine.getStartIdx();
        Integer foldingDefineEndIdx = formFoldingDefine.getEndIdx();
        this.startIdx = foldingDefineStartIdx == null ? -1 : foldingDefineStartIdx;
        this.endIdx = foldingDefineEndIdx == null ? -1 : foldingDefineEndIdx;
        this.direction = formFoldingDefine.getDirection();
        this.collapsed = formFoldingDefine.isFolding();
    }

    public ExpFormFolding(FormFoldingDefine formFoldingDefine, boolean collapsed) {
        this.init(formFoldingDefine);
        this.collapsed = collapsed;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public FormFoldingDirEnum getDirection() {
        return this.direction;
    }

    public void setDirection(FormFoldingDirEnum direction) {
        this.direction = direction;
    }

    public int getEndIdx() {
        return this.endIdx;
    }

    public void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }

    public int getStartIdx() {
        return this.startIdx;
    }

    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
    }
}


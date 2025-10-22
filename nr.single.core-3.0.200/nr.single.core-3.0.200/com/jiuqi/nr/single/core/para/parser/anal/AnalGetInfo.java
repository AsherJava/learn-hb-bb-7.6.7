/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.parser.anal;

public class AnalGetInfo {
    private String menuCaption;
    private int sourceTask;
    private String sourceUnitSelect;
    private boolean showTableSel;
    private boolean unitSelAll;

    public String getMenuCaption() {
        return this.menuCaption;
    }

    public void setMenuCaption(String menuCaption) {
        this.menuCaption = menuCaption;
    }

    public int getSourceTask() {
        return this.sourceTask;
    }

    public void setSourceTask(int sourceTask) {
        this.sourceTask = sourceTask;
    }

    public String getSourceUnitSelect() {
        return this.sourceUnitSelect;
    }

    public void setSourceUnitSelect(String sourceUnitSelect) {
        this.sourceUnitSelect = sourceUnitSelect;
    }

    public boolean isShowTableSel() {
        return this.showTableSel;
    }

    public void setShowTableSel(boolean showTableSel) {
        this.showTableSel = showTableSel;
    }

    public boolean isUnitSelAll() {
        return this.unitSelAll;
    }

    public void setUnitSelAll(boolean unitSelAll) {
        this.unitSelAll = unitSelAll;
    }
}


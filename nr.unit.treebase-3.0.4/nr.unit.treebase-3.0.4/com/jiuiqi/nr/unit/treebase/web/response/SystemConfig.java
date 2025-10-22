/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.web.response;

public class SystemConfig {
    private String nodeDisplay = "the-node-default";
    private Boolean canAddDimension;
    private Boolean showCode;
    private Boolean showContextMenu = true;
    private Boolean showFilterMenu = true;
    private String nameOfChildCount;
    private Boolean showCountOfAllChildrenQuantities;

    public String getNodeDisplay() {
        return this.nodeDisplay;
    }

    public void setNodeDisplay(String nodeDisplay) {
        this.nodeDisplay = nodeDisplay;
    }

    public Boolean getCanAddDimension() {
        return this.canAddDimension;
    }

    public void setCanAddDimension(Boolean canAddDimension) {
        this.canAddDimension = canAddDimension;
    }

    public Boolean getShowCode() {
        return this.showCode;
    }

    public void setShowCode(Boolean showCode) {
        this.showCode = showCode;
    }

    public Boolean getShowContextMenu() {
        return this.showContextMenu;
    }

    public void setShowContextMenu(Boolean showContextMenu) {
        this.showContextMenu = showContextMenu;
    }

    public Boolean getShowFilterMenu() {
        return this.showFilterMenu;
    }

    public void setShowFilterMenu(Boolean showFilterMenu) {
        this.showFilterMenu = showFilterMenu;
    }

    public String getNameOfChildCount() {
        return this.nameOfChildCount;
    }

    public void setNameOfChildCount(String nameOfChildCount) {
        this.nameOfChildCount = nameOfChildCount;
    }

    public Boolean getShowCountOfAllChildrenQuantities() {
        return this.showCountOfAllChildrenQuantities;
    }

    public void setShowCountOfAllChildrenQuantities(Boolean showCountOfAllChildrenQuantities) {
        this.showCountOfAllChildrenQuantities = showCountOfAllChildrenQuantities;
    }
}


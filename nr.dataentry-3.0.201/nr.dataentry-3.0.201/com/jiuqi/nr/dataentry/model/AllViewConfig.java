/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.dataentry.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.dataentry.model.DataEntryViewConfig;
import com.jiuqi.nr.dataentry.model.GridViewConfig;
import com.jiuqi.nr.dataentry.model.InfoViewConfig;
import com.jiuqi.nr.dataentry.model.StatusBarConfig;
import com.jiuqi.nr.dataentry.model.StatusViewConfig;
import com.jiuqi.nr.dataentry.model.TabViewConfig;
import com.jiuqi.nr.dataentry.model.ToolbarViewConfig;
import com.jiuqi.nr.dataentry.model.UnitViewConfig;

public class AllViewConfig {
    @JsonProperty(value="config")
    private DataEntryViewConfig dataEntryViewConfig;
    @JsonProperty(value="reportView")
    private GridViewConfig gridViewConfig;
    @JsonProperty(value="infoView")
    private InfoViewConfig infoViewConfig;
    @JsonProperty(value="statusView")
    private StatusViewConfig statusViewConfig;
    @JsonProperty(value="statusBar")
    private StatusBarConfig statusBarConfig;
    @JsonProperty(value="tabView")
    private TabViewConfig tabViewConfig;
    @JsonProperty(value="toolBarView")
    private ToolbarViewConfig toolbarViewConfig;
    @JsonProperty(value="unitView")
    private UnitViewConfig unitViewConfig;

    public DataEntryViewConfig getDataEntryViewConfig() {
        return this.dataEntryViewConfig;
    }

    public void setDataEntryViewConfig(DataEntryViewConfig dataEntryViewConfig) {
        this.dataEntryViewConfig = dataEntryViewConfig;
    }

    public GridViewConfig getGridViewConfig() {
        return this.gridViewConfig;
    }

    public void setGridViewConfig(GridViewConfig gridViewConfig) {
        this.gridViewConfig = gridViewConfig;
    }

    public InfoViewConfig getInfoViewConfig() {
        return this.infoViewConfig;
    }

    public void setInfoViewConfig(InfoViewConfig infoViewConfig) {
        this.infoViewConfig = infoViewConfig;
    }

    public StatusViewConfig getStatusViewConfig() {
        return this.statusViewConfig;
    }

    public void setStatusViewConfig(StatusViewConfig statusViewConfig) {
        this.statusViewConfig = statusViewConfig;
    }

    public TabViewConfig getTabViewConfig() {
        return this.tabViewConfig;
    }

    public void setTabViewConfig(TabViewConfig tabViewConfig) {
        this.tabViewConfig = tabViewConfig;
    }

    public ToolbarViewConfig getToolbarViewConfig() {
        return this.toolbarViewConfig;
    }

    public void setToolbarViewConfig(ToolbarViewConfig toolbarViewConfig) {
        this.toolbarViewConfig = toolbarViewConfig;
    }

    public UnitViewConfig getUnitViewConfig() {
        return this.unitViewConfig;
    }

    public void setUnitViewConfig(UnitViewConfig unitViewConfig) {
        this.unitViewConfig = unitViewConfig;
    }

    public StatusBarConfig getStatusBarConfig() {
        return this.statusBarConfig;
    }

    public void setStatusBarConfig(StatusBarConfig statusBarConfig) {
        this.statusBarConfig = statusBarConfig;
    }
}


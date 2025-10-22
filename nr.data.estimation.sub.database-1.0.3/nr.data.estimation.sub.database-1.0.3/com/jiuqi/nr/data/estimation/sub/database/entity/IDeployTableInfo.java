/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.data.estimation.sub.database.entity;

import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.List;

public class IDeployTableInfo {
    private String oriTableModelId;
    private String oriTableModelCode;
    private String oriTableModelTitle;
    private DesignTableModelDefine newTableModel;
    private List<DesignIndexModelDefine> newTableIndexes;
    private List<DesignColumnModelDefine> newTableColumns;

    public IDeployTableInfo(String oriTableModelId, String oriTableModelCode, String oriTableModelTitle) {
        this.oriTableModelId = oriTableModelId;
        this.oriTableModelCode = oriTableModelCode;
        this.oriTableModelTitle = oriTableModelTitle;
    }

    public String getOriTableModelId() {
        return this.oriTableModelId;
    }

    public void setOriTableModelId(String oriTableModelId) {
        this.oriTableModelId = oriTableModelId;
    }

    public String getOriTableModelCode() {
        return this.oriTableModelCode;
    }

    public void setOriTableModelCode(String oriTableModelCode) {
        this.oriTableModelCode = oriTableModelCode;
    }

    public String getOriTableModelTitle() {
        return this.oriTableModelTitle;
    }

    public void setOriTableModelTitle(String oriTableModelTitle) {
        this.oriTableModelTitle = oriTableModelTitle;
    }

    public DesignTableModelDefine getNewTableModel() {
        return this.newTableModel;
    }

    public void setNewTableModel(DesignTableModelDefine newTableModel) {
        this.newTableModel = newTableModel;
    }

    public List<DesignIndexModelDefine> getNewTableIndexes() {
        return this.newTableIndexes;
    }

    public void setNewTableIndexes(List<DesignIndexModelDefine> newTableIndexes) {
        this.newTableIndexes = newTableIndexes;
    }

    public List<DesignColumnModelDefine> getNewTableColumns() {
        return this.newTableColumns;
    }

    public void setNewTableColumns(List<DesignColumnModelDefine> newTableColumns) {
        this.newTableColumns = newTableColumns;
    }
}


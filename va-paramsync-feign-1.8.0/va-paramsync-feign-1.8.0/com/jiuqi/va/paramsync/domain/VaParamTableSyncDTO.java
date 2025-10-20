/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.mapper.common.JTableModel;
import com.jiuqi.va.paramsync.domain.VaParamTableModel;
import java.util.ArrayList;
import java.util.List;

public class VaParamTableSyncDTO {
    private String name;
    private String title;
    private List<VaParamTableModel> tables;
    private Long timestamp;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VaParamTableModel> getTables() {
        return this.tables;
    }

    public void setTables(List<VaParamTableModel> tables) {
        this.tables = tables;
    }

    public void addVPTableModel(VaParamTableModel vpTableModel) {
        if (this.tables == null) {
            this.tables = new ArrayList<VaParamTableModel>();
        }
        this.tables.add(vpTableModel);
    }

    public void addJTableModel(JTableModel jTableModel) {
        if (this.tables == null) {
            this.tables = new ArrayList<VaParamTableModel>();
        }
        this.tables.add(VaParamTableModel.newCopy(jTableModel));
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}


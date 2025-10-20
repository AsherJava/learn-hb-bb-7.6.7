/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.impl;

import com.jiuqi.va.datamodel.exchange.nvwa.base.DataModelConst;
import com.jiuqi.va.datamodel.exchange.nvwa.base.TableType;

public class DataModelGroup {
    private String id;
    private String code;
    private String title;
    private String parentid;

    public static DataModelGroup unGrouped() {
        DataModelGroup group = new DataModelGroup(DataModelConst.PACKAGE_VA_UNGROUPED, "VA_UNGROUPED", "\u672a\u5206\u7ec4", DataModelConst.PACKAGE_VA_DATAMODEL);
        return group;
    }

    public static DataModelGroup rootGroup() {
        DataModelGroup group = new DataModelGroup(DataModelConst.PACKAGE_VA_DATAMODEL, "VA_DATAMODEL", "VA\u6570\u636e\u5efa\u6a21", null);
        return group;
    }

    public DataModelGroup(TableType vaTableType) {
        this(vaTableType.getId(), vaTableType.getCode(), vaTableType.getTitle(), DataModelConst.PACKAGE_VA_DATAMODEL);
    }

    private DataModelGroup(String id, String code, String title, String parentid) {
        this.setId(id);
        this.setCode(code);
        this.setTitle(title);
        this.setParentid(parentid);
    }

    public DataModelGroup() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


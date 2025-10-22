/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bql.extend.model;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Date;

public class QueryTableModel
implements TableModelDefine {
    private static final long serialVersionUID = 747782702566147286L;
    private DataTable dataTable;
    private String tableName;

    public QueryTableModel(DataTable dataTable, String tableName) {
        this.dataTable = dataTable;
        this.tableName = tableName;
    }

    public String getID() {
        return this.dataTable.getKey();
    }

    public String getCode() {
        return this.dataTable.getCode();
    }

    public String getCatalogID() {
        return this.dataTable.getDataGroupKey();
    }

    public String getName() {
        return this.tableName;
    }

    public String getTitle() {
        return this.dataTable.getTitle();
    }

    public String getDesc() {
        return this.dataTable.getDesc();
    }

    public TableModelType getType() {
        return TableModelType.DATA;
    }

    public TableDictType getDictType() {
        return TableDictType.NORMAL;
    }

    public String getKeys() {
        return this.getBizKeys();
    }

    public String getBizKeys() {
        return this.dataTable.getBizKeyFieldsStr();
    }

    public String getOwner() {
        return null;
    }

    public String getOption() {
        return null;
    }

    public TableModelKind getKind() {
        return TableModelKind.DEFAULT;
    }

    public Date getCreateTime() {
        return null;
    }

    public Date getUpdateTime() {
        return null;
    }

    public boolean getSupportI18n() {
        return false;
    }

    public String getLocaleTitle() {
        return null;
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public boolean isSupportNrdb() {
        return true;
    }

    public String getStorageName() {
        return null;
    }

    public String getDataSource() {
        return null;
    }

    public void setID(String id) {
    }

    public void setCatalogID(String catalogID) {
    }

    public void setCode(String code) {
    }

    public void setName(String name) {
    }

    public void setTitle(String title) {
    }

    public void setDesc(String desc) {
    }

    public void setType(TableModelType type) {
    }

    public void setDictType(TableDictType dictType) {
    }

    public void setKeys(String keys) {
    }

    public void setBizKeys(String bizKeys) {
    }

    public void setOwner(String owner) {
    }

    public void setOption(String option) {
    }

    public void setKind(TableModelKind kind) {
    }

    public void setCreateTime(Date createTime) {
    }

    public void setUpdateTime(Date updateTime) {
    }

    public void setSupportI18n(boolean supportI18n) {
    }

    public void setSupportNrdb(boolean supportNrdb) {
    }

    public void setStorageName(String storageName) {
    }

    public void setDataSource(String dataSource) {
    }
}


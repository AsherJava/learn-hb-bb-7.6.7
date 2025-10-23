/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.web.facade.DataFieldVO;
import java.util.List;

public class DimSetVO {
    private String table;
    private List<String> keys;
    private boolean repeatCode;
    private List<DataFieldVO> insert;
    private List<DataFieldVO> update;
    private List<String> delete;
    private DataFieldVO dimField;
    private String defaultValue;

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public boolean isRepeatCode() {
        return this.repeatCode;
    }

    public void setRepeatCode(boolean repeatCode) {
        this.repeatCode = repeatCode;
    }

    public List<DataFieldVO> getInsert() {
        return this.insert;
    }

    public void setInsert(List<DataFieldVO> insert) {
        this.insert = insert;
    }

    public List<DataFieldVO> getUpdate() {
        return this.update;
    }

    public void setUpdate(List<DataFieldVO> update) {
        this.update = update;
    }

    public List<String> getDelete() {
        return this.delete;
    }

    public void setDelete(List<String> delete) {
        this.delete = delete;
    }

    public DataFieldVO getDimField() {
        return this.dimField;
    }

    public void setDimField(DataFieldVO dimField) {
        this.dimField = dimField;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}


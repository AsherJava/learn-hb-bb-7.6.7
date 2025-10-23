/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.param.transfer;

import com.jiuqi.nr.param.transfer.ChangeObj;
import java.util.ArrayList;
import java.util.List;

public class FieldChangeObj {
    String dataSchemeKey;
    String dataSchemeTitle;
    String dataTableKey;
    String dataTableTitle;
    List<ChangeObj> updateFields = new ArrayList<ChangeObj>();
    List<ChangeObj> addFields = new ArrayList<ChangeObj>();
    List<ChangeObj> deleteFields = new ArrayList<ChangeObj>();

    public FieldChangeObj() {
    }

    public FieldChangeObj(String dataSchemeKey, String dataSchemeTitle, String dataTableKey, String dataTableTitle, List<ChangeObj> updateFields, List<ChangeObj> addFields, List<ChangeObj> deleteFields) {
        this.dataSchemeKey = dataSchemeKey;
        this.dataSchemeTitle = dataSchemeTitle;
        this.dataTableKey = dataTableKey;
        this.dataTableTitle = dataTableTitle;
        this.updateFields = updateFields;
        this.addFields = addFields;
        this.deleteFields = deleteFields;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDataTableTitle() {
        return this.dataTableTitle;
    }

    public void setDataTableTitle(String dataTableTitle) {
        this.dataTableTitle = dataTableTitle;
    }

    public List<ChangeObj> getUpdateFields() {
        return this.updateFields;
    }

    public void setUpdateFields(List<ChangeObj> updateFields) {
        this.updateFields = updateFields;
    }

    public List<ChangeObj> getAddFields() {
        return this.addFields;
    }

    public void setAddFields(List<ChangeObj> addFields) {
        this.addFields = addFields;
    }

    public List<ChangeObj> getDeleteFields() {
        return this.deleteFields;
    }

    public void setDeleteFields(List<ChangeObj> deleteFields) {
        this.deleteFields = deleteFields;
    }
}


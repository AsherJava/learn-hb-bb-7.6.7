/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.fmdm.IFMDMAttribute;

public class ConfigItemStructure {
    private String key;
    private String title;
    private String code;

    public ConfigItemStructure() {
    }

    public ConfigItemStructure(DataField dataField) {
        this.key = dataField.getKey();
        this.title = dataField.getTitle();
        this.code = dataField.getCode();
    }

    public ConfigItemStructure(IFMDMAttribute fmdmattribute) {
        this.key = fmdmattribute.getID();
        this.title = fmdmattribute.getTitle();
        this.code = fmdmattribute.getCode();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


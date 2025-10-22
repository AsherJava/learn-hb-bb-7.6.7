/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.nr.definition.common;

import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.List;

public class DataLinkMappingCacheObj
implements IModelDefineItem {
    private static final long serialVersionUID = 1L;
    private String formKey;
    private List<DataLinkMappingDefine> value;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<DataLinkMappingDefine> getValue() {
        return this.value;
    }

    public void setValue(List<DataLinkMappingDefine> value) {
        this.value = value;
    }

    public String getID() {
        return this.formKey;
    }

    public String getCode() {
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.DataLinkMappingDefine;
import java.util.List;

public interface IRuntimeDataLinkMappingService {
    public List<DataLinkMappingDefine> queryDataLinkMappingByFormKey(String var1);

    default public List<DataLinkMappingDefine> listDataLinkMappingByForm(String formKey, String formSchemeKey) {
        return this.queryDataLinkMappingByFormKey(formKey);
    }
}


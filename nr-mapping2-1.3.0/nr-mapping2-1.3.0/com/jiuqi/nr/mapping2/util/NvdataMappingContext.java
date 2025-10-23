/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.util;

import com.jiuqi.nr.mapping2.util.NvMappingInsertRule;
import com.jiuqi.nr.mapping2.util.NvMappingMatchRule;

public class NvdataMappingContext {
    String mappingSchemeKey;
    String formSchemeSchemeKey;
    NvMappingMatchRule nvMappingMatchRule;
    NvMappingInsertRule nvMappingInsertRule;

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public String getFormSchemeSchemeKey() {
        return this.formSchemeSchemeKey;
    }

    public void setFormSchemeSchemeKey(String formSchemeSchemeKey) {
        this.formSchemeSchemeKey = formSchemeSchemeKey;
    }

    public NvMappingMatchRule getNvMappingMatchRule() {
        return this.nvMappingMatchRule;
    }

    public void setNvMappingMatchRule(NvMappingMatchRule nvMappingMatchRule) {
        this.nvMappingMatchRule = nvMappingMatchRule;
    }

    public NvMappingInsertRule getNvMappingInsertRule() {
        return this.nvMappingInsertRule;
    }

    public void setNvMappingInsertRule(NvMappingInsertRule nvMappingInsertRule) {
        this.nvMappingInsertRule = nvMappingInsertRule;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.web.dto;

import com.jiuqi.nr.mapping2.bean.ZBMapping;
import java.util.List;
import java.util.Map;

public class SaveMapping {
    private String mappingSchemeId;
    private String formCode;
    private List<ZBMapping> zbMappings;
    private Map<String, List<String>> invalidMapping;

    public String getMappingSchemeId() {
        return this.mappingSchemeId;
    }

    public void setMappingSchemeId(String mappingSchemeId) {
        this.mappingSchemeId = mappingSchemeId;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public List<ZBMapping> getZbMappings() {
        return this.zbMappings;
    }

    public void setZbMappings(List<ZBMapping> zbMappings) {
        this.zbMappings = zbMappings;
    }

    public Map<String, List<String>> getInvalidMapping() {
        return this.invalidMapping;
    }

    public void setInvalidMapping(Map<String, List<String>> invalidMapping) {
        this.invalidMapping = invalidMapping;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.web.vo;

import com.jiuqi.nr.mapping2.dto.FormMappingDTO;
import java.util.List;

public class FormMappingSaveVO {
    private List<FormMappingDTO> items;
    private String schemeKey;

    public List<FormMappingDTO> getItems() {
        return this.items;
    }

    public void setItems(List<FormMappingDTO> items) {
        this.items = items;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }
}


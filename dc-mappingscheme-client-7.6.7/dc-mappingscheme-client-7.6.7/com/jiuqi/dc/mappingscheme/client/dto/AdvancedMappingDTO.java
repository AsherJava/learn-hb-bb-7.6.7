/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.jiuqi.dc.mappingscheme.client.dto.DimMappingDTO;
import java.util.Map;

public class AdvancedMappingDTO
extends DimMappingDTO {
    private Map<String, String> bizMapping;
    private String storageType;

    public Map<String, String> getBizMapping() {
        return this.bizMapping;
    }

    public String getStorageType() {
        return this.storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public void setBizMapping(Map<String, String> bizMapping) {
        this.bizMapping = bizMapping;
    }
}


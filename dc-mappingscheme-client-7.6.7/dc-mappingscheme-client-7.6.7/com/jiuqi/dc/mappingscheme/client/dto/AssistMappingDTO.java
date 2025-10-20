/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.jiuqi.dc.mappingscheme.client.dto.DimMappingDTO;
import java.util.List;

public class AssistMappingDTO
extends DimMappingDTO {
    private List<String> effectScope;

    public List<String> getEffectScope() {
        return this.effectScope;
    }

    public void setEffectScope(List<String> effectScope) {
        this.effectScope = effectScope;
    }
}


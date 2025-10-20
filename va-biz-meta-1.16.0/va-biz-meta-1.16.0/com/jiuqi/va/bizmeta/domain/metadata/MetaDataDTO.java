/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.domain.metadata;

import java.util.UUID;

public class MetaDataDTO {
    private UUID id;
    private String designData;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDesignData() {
        return this.designData;
    }

    public void setDesignData(String designData) {
        this.designData = designData;
    }
}


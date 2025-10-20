/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.metadata;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="META_DESIGN")
public class MetaDataDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    @Column(name="DESIGNDATA")
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


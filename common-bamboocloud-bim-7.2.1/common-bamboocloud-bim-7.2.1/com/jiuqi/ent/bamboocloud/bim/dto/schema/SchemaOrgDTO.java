/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.ent.bamboocloud.bim.dto.schema;

import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaFieldDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaParentDTO;
import java.util.Set;

public class SchemaOrgDTO
extends SchemaParentDTO {
    private Set<SchemaFieldDTO> organization;

    public SchemaOrgDTO(String bimRequestId, Set<SchemaFieldDTO> organization) {
        super(bimRequestId);
        this.organization = organization;
    }

    public Set<SchemaFieldDTO> getOrganization() {
        return this.organization;
    }

    public void setOrganization(Set<SchemaFieldDTO> organization) {
        this.organization = organization;
    }
}


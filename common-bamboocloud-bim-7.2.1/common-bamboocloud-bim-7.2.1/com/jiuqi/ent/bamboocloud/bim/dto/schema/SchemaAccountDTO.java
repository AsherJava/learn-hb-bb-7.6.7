/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.ent.bamboocloud.bim.dto.schema;

import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaFieldDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaParentDTO;
import java.util.Set;

public class SchemaAccountDTO
extends SchemaParentDTO {
    private Set<SchemaFieldDTO> account;

    public SchemaAccountDTO(String bimRequestId, Set<SchemaFieldDTO> account) {
        super(bimRequestId);
        this.account = account;
    }

    public Set<SchemaFieldDTO> getAccount() {
        return this.account;
    }

    public void setAccount(Set<SchemaFieldDTO> account) {
        this.account = account;
    }
}


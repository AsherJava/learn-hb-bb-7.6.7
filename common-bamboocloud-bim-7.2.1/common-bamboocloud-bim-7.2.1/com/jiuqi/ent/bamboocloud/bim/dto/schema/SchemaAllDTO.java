/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.ent.bamboocloud.bim.dto.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaFieldDTO;
import com.jiuqi.ent.bamboocloud.bim.dto.schema.SchemaParentDTO;
import java.util.Set;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SchemaAllDTO
extends SchemaParentDTO {
    private Set<SchemaFieldDTO> account;
    private Set<SchemaFieldDTO> organization;

    public SchemaAllDTO(String bimRequestId, Set<SchemaFieldDTO> account, Set<SchemaFieldDTO> organization) {
        super(bimRequestId);
        this.account = account;
        this.organization = organization;
    }

    public Set<SchemaFieldDTO> getAccount() {
        return this.account;
    }

    public void setAccount(Set<SchemaFieldDTO> account) {
        this.account = account;
    }

    public Set<SchemaFieldDTO> getOrganization() {
        return this.organization;
    }

    public void setOrganization(Set<SchemaFieldDTO> organization) {
        this.organization = organization;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


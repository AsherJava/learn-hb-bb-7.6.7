/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apache.commons.lang3.builder.ToStringStyle
 */
package com.jiuqi.ent.bamboocloud.bim.dto.org;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.ent.bamboocloud.bim.dto.GeneralDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrgCreateDTO
extends GeneralDTO {
    private String orgId;

    public OrgCreateDTO(String bimRequestId, String resultCode, String message, String orgId) {
        super(bimRequestId, resultCode, message);
        this.orgId = orgId;
    }

    public OrgCreateDTO() {
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


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
import java.util.Map;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrgQueryDTO
extends GeneralDTO {
    private Map<String, Object> organization;

    public OrgQueryDTO(String bimRequestId, String resultCode, String message, Map<String, Object> json) {
        super(bimRequestId, resultCode, message);
        this.organization = json;
    }

    public OrgQueryDTO() {
    }

    public Map<String, Object> getOrganization() {
        return this.organization;
    }

    public void setOrganization(Map<String, Object> organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


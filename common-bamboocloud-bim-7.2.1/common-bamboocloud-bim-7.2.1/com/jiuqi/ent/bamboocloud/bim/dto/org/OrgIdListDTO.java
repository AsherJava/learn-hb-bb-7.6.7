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
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OrgIdListDTO
extends GeneralDTO {
    private List<String> orgIdList;

    public OrgIdListDTO(String bimRequestId, String resultCode, String message, List<String> orgIds) {
        super(bimRequestId, resultCode, message);
        this.orgIdList = orgIds;
    }

    public OrgIdListDTO() {
    }

    public List<String> getOrgIdList() {
        return this.orgIdList;
    }

    public void setOrgIdList(List<String> orgIdList) {
        this.orgIdList = orgIdList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this, (ToStringStyle)ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


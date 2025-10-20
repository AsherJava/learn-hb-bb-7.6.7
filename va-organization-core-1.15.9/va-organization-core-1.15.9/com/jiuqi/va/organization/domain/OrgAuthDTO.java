/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDTO
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import java.util.Map;

public class OrgAuthDTO
extends OrgAuthDO {
    private static final long serialVersionUID = 1L;

    @Override
    public String convertKey(String key) {
        return key;
    }

    public boolean isForShow() {
        return this.containsKey("forShow");
    }

    public void setForShow(boolean forShow) {
        this.put("forShow", (Object)forShow);
    }

    public OrgDTO getOrgDataDTO() {
        Object orgDataDTO = this.get("orgDataDTO");
        if (orgDataDTO == null) {
            return null;
        }
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.putAll((Map)orgDataDTO);
        return orgDTO;
    }

    public void setOrgDataDTO(OrgDTO orgDataDTO) {
        this.put("orgDataDTO", (Object)orgDataDTO);
    }

    public String getSqlCondition() {
        return (String)this.get("sqlCondition");
    }

    public void setSqlCondition(String sqlCondition) {
        this.put("sqlCondition", (Object)sqlCondition);
    }
}


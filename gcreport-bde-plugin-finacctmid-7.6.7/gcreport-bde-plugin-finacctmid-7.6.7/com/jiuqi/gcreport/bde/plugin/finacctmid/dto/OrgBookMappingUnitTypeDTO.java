/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.plugin.finacctmid.dto;

public class OrgBookMappingUnitTypeDTO {
    private String orgType;
    private String orgVerCode;

    public OrgBookMappingUnitTypeDTO() {
    }

    public OrgBookMappingUnitTypeDTO(String orgType, String orgVerCode) {
        this.orgType = orgType;
        this.orgVerCode = orgVerCode;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgVerCode() {
        return this.orgVerCode;
    }

    public void setOrgVerCode(String orgVerCode) {
        this.orgVerCode = orgVerCode;
    }

    public String toString() {
        return "OrgBookMappingUnitTypeDTO [orgType=" + this.orgType + ", orgVerCode=" + this.orgVerCode + "]";
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.client.orgcomb.vo;

public class OrgCombItemDefineVO {
    private String id;
    private Long ver;
    private String schemeId;
    private String orgCode;
    private String excludeOrgCodes;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getExcludeOrgCodes() {
        return this.excludeOrgCodes;
    }

    public void setExcludeOrgCodes(String excludeOrgCodes) {
        this.excludeOrgCodes = excludeOrgCodes;
    }
}


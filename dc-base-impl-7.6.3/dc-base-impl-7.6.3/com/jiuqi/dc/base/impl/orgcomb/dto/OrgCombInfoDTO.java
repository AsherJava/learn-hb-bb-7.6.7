/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.impl.orgcomb.dto;

public class OrgCombInfoDTO {
    private String code;
    private String name;
    private String remark;
    private String orgCode;
    private String excludeOrgCodes;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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


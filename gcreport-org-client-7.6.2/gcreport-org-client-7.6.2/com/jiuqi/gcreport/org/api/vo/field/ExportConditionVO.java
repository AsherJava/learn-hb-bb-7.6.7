/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.Pattern
 */
package com.jiuqi.gcreport.org.api.vo.field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ExportConditionVO {
    private String sn;
    private String orgType;
    private String orgVer;
    private Boolean executeOnDuplicate;
    @NotBlank(message="\u8868\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a")
    @Pattern(regexp="\\bORG\\S*\\b", message="\u53ea\u80fd\u5bfc\u5165\u7ec4\u7ec7\u673a\u6784\u76f8\u5173\u7684\u8868!")
    private @NotBlank(message="\u8868\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a") @Pattern(regexp="\\bORG\\S*\\b", message="\u53ea\u80fd\u5bfc\u5165\u7ec4\u7ec7\u673a\u6784\u76f8\u5173\u7684\u8868!") String tableName;
    private String parentId;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgVer() {
        return this.orgVer;
    }

    public void setOrgVer(String orgVer) {
        this.orgVer = orgVer;
    }

    public Boolean getExecuteOnDuplicate() {
        return this.executeOnDuplicate;
    }

    public void setExecuteOnDuplicate(Boolean executeOnDuplicate) {
        this.executeOnDuplicate = executeOnDuplicate;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}


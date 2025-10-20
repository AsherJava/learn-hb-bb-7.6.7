/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.dc.base.impl.orgcomb.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="DC_DEFINE_ORGCOMBITEM")
public class OrgCombItemDefineDO
extends TenantDO {
    public static final String TABLENAME = "DC_DEFINE_ORGCOMBITEM";
    private static final long serialVersionUID = 6767678804793076482L;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="VER")
    private Long ver;
    @Column(name="SCHEMEID")
    private String schemeId;
    @Column(name="ORGCODE")
    private String orgCode;
    @Column(name="EXCLUDEORGCODES")
    private String excludeOrgCodes;

    public OrgCombItemDefineDO() {
    }

    public OrgCombItemDefineDO(String schemeId) {
        this.schemeId = schemeId;
    }

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


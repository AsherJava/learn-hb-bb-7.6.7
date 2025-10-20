/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.dc.base.impl.onlinePeriod.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="DC_DEFINE_ONLINEPERIOD")
public class OnlinePeriodDefineDO
extends TenantDO {
    public static final String TABLENAME = "DC_DEFINE_ONLINEPERIOD";
    private static final long serialVersionUID = -7118518670158585176L;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="VER")
    private Long ver;
    @Column(name="ONLINEPERIOD")
    private String onlinePeriod;
    @Column(name="ORGCOMBCODES")
    private String orgCombCodes;
    @Column(name="moduleCODE")
    private String moduleCode;
    @Column(name="REMARK")
    private String remark;

    public OnlinePeriodDefineDO() {
    }

    public OnlinePeriodDefineDO(String moduleCode) {
        this.moduleCode = moduleCode;
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

    public String getOnlinePeriod() {
        return this.onlinePeriod;
    }

    public void setOnlinePeriod(String onlinePeriod) {
        this.onlinePeriod = onlinePeriod;
    }

    public String getOrgCombCodes() {
        return this.orgCombCodes;
    }

    public void setOrgCombCodes(String orgCombCodes) {
        this.orgCombCodes = orgCombCodes;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}


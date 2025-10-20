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

@Table(name="DC_DEFINE_ORGCOMB")
public class OrgCombDefineDO
extends TenantDO {
    public static final String TABLENAME = "DC_DEFINE_ORGCOMB";
    private static final long serialVersionUID = -2283194447892451904L;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="VER")
    private Long ver;
    @Column(name="CODE")
    private String code;
    @Column(name="name")
    private String name;
    @Column(name="REMARK")
    private String remark;
    @Column(name="GROUPID")
    private String groupId;
    @Column(name="SORTORDER")
    private Integer sortOrder;

    public OrgCombDefineDO() {
    }

    public OrgCombDefineDO(String id) {
        this.id = id;
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

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}


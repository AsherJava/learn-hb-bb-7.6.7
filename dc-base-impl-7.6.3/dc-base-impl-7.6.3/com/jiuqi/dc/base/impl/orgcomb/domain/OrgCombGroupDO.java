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

@Table(name="DC_DEFINE_ORGCOMBGROUP")
public class OrgCombGroupDO
extends TenantDO {
    public static final String TABLENAME = "DC_DEFINE_ORGCOMBGROUP";
    private static final long serialVersionUID = 3189046353762486395L;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="VER")
    private Long ver;
    @Column(name="TITLE")
    private String title;
    @Column(name="NODETYPE")
    private String nodeType;
    @Column(name="SORTNUM")
    private String sortNum;

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getSortNum() {
        return this.sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }
}


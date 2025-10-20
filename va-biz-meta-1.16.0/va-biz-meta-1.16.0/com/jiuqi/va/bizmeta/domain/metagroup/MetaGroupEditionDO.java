/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Column
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.metagroup;

import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="META_GROUP_USER")
public class MetaGroupEditionDO
extends MetaGroupDO {
    private static final long serialVersionUID = 1L;
    @Column(name="USERNAME")
    private String userName;
    @Column(name="METASTATE")
    private Integer metaState;
    @Column(name="ORGVERSION")
    private Long orgVersion;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getMetaState() {
        return this.metaState;
    }

    public void setMetaState(int metaState) {
        this.metaState = metaState;
    }

    public Long getOrgVersion() {
        return this.orgVersion;
    }

    public void setOrgVersion(long orgVersion) {
        this.orgVersion = orgVersion;
    }
}


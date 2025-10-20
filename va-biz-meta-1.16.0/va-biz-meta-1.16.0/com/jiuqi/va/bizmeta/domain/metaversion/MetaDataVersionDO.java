/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.metaversion;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="META_VERSION")
public class MetaDataVersionDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Column(name="VERSIONNO")
    private Long versionNo;
    @Column(name="USERNAME")
    private String userName;
    @Column(name="CREATETIME")
    private Date createTime;
    private String info;

    public Long getVersionNo() {
        return this.versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.biz.domain.commrule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name="BIZ_COMMRULE")
public class BizCommonRuleDO
extends TenantDO {
    @Id
    private UUID id;
    private Long ver;
    private String biztype;
    private String definecode;
    private String definetitle;
    private String rulename;
    private String objecttype;
    private String propertytype;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd  HH:mm:ss")
    private Date createtime;
    private String createuser;
    private String remark;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getBiztype() {
        return this.biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public String getDefinecode() {
        return this.definecode;
    }

    public void setDefinecode(String definecode) {
        this.definecode = definecode;
    }

    public String getDefinetitle() {
        return this.definetitle;
    }

    public void setDefinetitle(String definetitle) {
        this.definetitle = definetitle;
    }

    public String getRulename() {
        return this.rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    public String getObjecttype() {
        return this.objecttype;
    }

    public void setObjecttype(String objecttype) {
        this.objecttype = objecttype;
    }

    public String getPropertytype() {
        return this.propertytype;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return this.createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.helper;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="BIZ_VIEW_TEMPLATE")
public class BizViewTemplateDO
extends TenantDO {
    private static final long serialVersionUID = -666277228786146131L;
    @Id
    private String id;
    private Long ver;
    private String name;
    private String title;
    private String template;
    @Column(name="BIZTYPE")
    private String bizType;
    @Column(name="CREATETIME")
    private Date createTime;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        return "BizViewTemplateDO{id='" + this.id + '\'' + ", ver=" + this.ver + ", name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", template='" + this.template + '\'' + ", bizType='" + this.bizType + '\'' + ", createTime=" + this.createTime + '}';
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 *  tk.mybatis.mapper.annotation.Order
 */
package com.jiuqi.va.domain.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;
import tk.mybatis.mapper.annotation.Order;

@Table(name="AUTH_MENU")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MenuDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    @Order(value="asc", priority=2)
    private String name;
    private String title;
    private String parentname;
    private String url;
    private String perms;
    private Integer biztype;
    private Integer urltype;
    private String icon;
    private String bigicon;
    @Order(value="asc", priority=1)
    private BigDecimal ordernum;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getParentname() {
        return this.parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return this.perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getBiztype() {
        return this.biztype;
    }

    public void setBiztype(Integer biztype) {
        this.biztype = biztype;
    }

    public Integer getUrltype() {
        return this.urltype;
    }

    public void setUrltype(Integer urltype) {
        this.urltype = urltype;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBigicon() {
        return this.bigicon;
    }

    public void setBigicon(String bigicon) {
        this.bigicon = bigicon;
    }

    public BigDecimal getOrdernum() {
        return this.ordernum;
    }

    public void setOrdernum(BigDecimal ordernum) {
        this.ordernum = ordernum;
    }
}


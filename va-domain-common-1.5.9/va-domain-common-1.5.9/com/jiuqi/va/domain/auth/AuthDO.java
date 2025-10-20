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
package com.jiuqi.va.domain.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;
import tk.mybatis.mapper.annotation.Order;

@Table(name="AUTH_REGISTER")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class AuthDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    @Order(value="asc", priority=2)
    private String name;
    private String title;
    private String umdUrl;
    private String umdName;
    private String mainName;
    private String mainParam;
    @Order(value="asc", priority=1)
    private Integer ordinal;
    private Integer adaptflag;

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

    public String getUmdUrl() {
        return this.umdUrl;
    }

    public void setUmdUrl(String umdUrl) {
        this.umdUrl = umdUrl;
    }

    public String getUmdName() {
        return this.umdName;
    }

    public void setUmdName(String umdName) {
        this.umdName = umdName;
    }

    public String getMainName() {
        return this.mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public String getMainParam() {
        return this.mainParam;
    }

    public void setMainParam(String mainParam) {
        this.mainParam = mainParam;
    }

    public Integer getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getAdaptflag() {
        return this.adaptflag;
    }

    public void setAdaptflag(Integer adaptflag) {
        this.adaptflag = adaptflag;
    }
}


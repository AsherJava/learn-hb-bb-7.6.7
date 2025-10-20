/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  tk.mybatis.mapper.annotation.Order
 */
package com.jiuqi.va.bill.bd.core.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import javax.persistence.Id;
import tk.mybatis.mapper.annotation.Order;

public class ApplyRegMapConfigItemDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Order(value="asc", priority=1)
    private BigDecimal ver;
    private String name;
    private String title;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
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
}


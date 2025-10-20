/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.budget.init;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;

public abstract class BaseDO
extends TenantDO
implements Comparable<BaseDO> {
    public abstract String getId();

    public abstract String getCode();

    public abstract String getName();

    public abstract String getParentId();

    public abstract BigDecimal getOrderNum();

    public abstract String getRemark();

    @Override
    public int compareTo(BaseDO o) {
        return this.getOrderNum().compareTo(o.getOrderNum());
    }
}


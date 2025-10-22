/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataTableDesignDaoImpl
extends AbstractDataTableDao<DesignDataTableDO> {
    @Override
    public Class<DesignDataTableDO> getClz() {
        return DesignDataTableDO.class;
    }
}


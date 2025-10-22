/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataTableDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataTableDaoImpl
extends AbstractDataTableDao<DataTableDO> {
    @Override
    public Class<DataTableDO> getClz() {
        return DataTableDO.class;
    }
}


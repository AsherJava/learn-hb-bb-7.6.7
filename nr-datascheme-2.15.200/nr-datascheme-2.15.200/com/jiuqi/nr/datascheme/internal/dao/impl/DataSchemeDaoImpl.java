/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemeDaoImpl
extends AbstractDataSchemeDao<DataSchemeDO> {
    @Override
    public Class<DataSchemeDO> getClz() {
        return DataSchemeDO.class;
    }
}


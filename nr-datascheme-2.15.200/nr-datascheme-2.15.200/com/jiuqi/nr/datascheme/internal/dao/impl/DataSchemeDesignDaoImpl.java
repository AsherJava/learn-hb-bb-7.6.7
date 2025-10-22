/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataSchemeDesignDaoImpl
extends AbstractDataSchemeDao<DesignDataSchemeDO> {
    @Override
    public Class<DesignDataSchemeDO> getClz() {
        return DesignDataSchemeDO.class;
    }
}


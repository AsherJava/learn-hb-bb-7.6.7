/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataDimDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataDimDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataDimDesignDaoImpl
extends AbstractDataDimDao<DesignDataDimDO>
implements IDataDimDao<DesignDataDimDO> {
    @Override
    public Class<DesignDataDimDO> getClz() {
        return DesignDataDimDO.class;
    }
}


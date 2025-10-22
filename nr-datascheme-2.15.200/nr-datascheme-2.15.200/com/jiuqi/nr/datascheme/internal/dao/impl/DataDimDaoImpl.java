/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataDimDao;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataDimDaoImpl
extends AbstractDataDimDao<DataDimDO>
implements IDataDimDao<DataDimDO> {
    @Override
    public Class<DataDimDO> getClz() {
        return DataDimDO.class;
    }
}


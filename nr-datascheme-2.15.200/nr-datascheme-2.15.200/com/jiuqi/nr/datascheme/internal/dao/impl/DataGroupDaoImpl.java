/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataGroupDao;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataGroupDaoImpl
extends AbstractDataGroupDao<DataGroupDO> {
    @Override
    public Class<DataGroupDO> getClz() {
        return DataGroupDO.class;
    }
}


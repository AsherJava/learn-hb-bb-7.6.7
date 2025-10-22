/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataFieldDao;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataFieldDaoImpl
extends AbstractDataFieldDao<DataFieldDO> {
    @Override
    public Class<DataFieldDO> getClz() {
        return DataFieldDO.class;
    }
}


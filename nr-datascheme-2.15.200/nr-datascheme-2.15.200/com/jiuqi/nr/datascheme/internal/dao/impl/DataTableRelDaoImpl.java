/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableRelDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataTableRelDaoImpl
extends AbstractDataTableRelDao<DataTableRelDO>
implements IDataTableRelDao<DataTableRelDO> {
    @Override
    public Class<DataTableRelDO> getClz() {
        return DataTableRelDO.class;
    }
}


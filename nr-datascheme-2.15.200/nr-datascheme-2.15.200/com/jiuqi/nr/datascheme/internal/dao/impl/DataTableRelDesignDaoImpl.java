/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.IDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataTableRelDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableRelDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataTableRelDesignDaoImpl
extends AbstractDataTableRelDao<DesignDataTableRelDO>
implements IDataTableRelDao<DesignDataTableRelDO> {
    @Override
    public Class<DesignDataTableRelDO> getClz() {
        return DesignDataTableRelDO.class;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataGroupDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataGroupDesignDaoImpl
extends AbstractDataGroupDao<DesignDataGroupDO> {
    @Override
    public Class<DesignDataGroupDO> getClz() {
        return DesignDataGroupDO.class;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dao.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.AbstractDataFieldDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import org.springframework.stereotype.Repository;

@Repository
public class DataFieldDesignDaoImpl
extends AbstractDataFieldDao<DesignDataFieldDO> {
    @Override
    public Class<DesignDataFieldDO> getClz() {
        return DesignDataFieldDO.class;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.adjustment.dao;

import com.jiuqi.nr.datascheme.adjustment.dao.AbstractAdjustDao;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDO;
import org.springframework.stereotype.Repository;

@Repository
public class DesignAdjustPeriodDaoImpl
extends AbstractAdjustDao<DesignAdjustPeriodDO> {
    @Override
    public Class<DesignAdjustPeriodDO> getClz() {
        return DesignAdjustPeriodDO.class;
    }
}


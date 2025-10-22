/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.adjustment.dao;

import com.jiuqi.nr.datascheme.adjustment.dao.AbstractAdjustDao;
import com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO;
import org.springframework.stereotype.Repository;

@Repository
public class AdjustPeriodDaoImpl
extends AbstractAdjustDao<AdjustPeriodDO> {
    @Override
    public Class<AdjustPeriodDO> getClz() {
        return AdjustPeriodDO.class;
    }
}


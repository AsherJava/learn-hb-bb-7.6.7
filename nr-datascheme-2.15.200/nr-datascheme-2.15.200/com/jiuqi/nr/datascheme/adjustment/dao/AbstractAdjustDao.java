/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.adjustment.dao;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.datascheme.adjustment.dao.AdjustPeriodDao;
import com.jiuqi.nr.datascheme.adjustment.exception.AdjustPeriodException;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.exception.BeanParaException;
import com.jiuqi.nr.datascheme.exception.DBParaException;
import com.jiuqi.nr.datascheme.internal.dao.impl.BaseDao;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public abstract class AbstractAdjustDao<E extends AdjustPeriod>
extends BaseDao
implements AdjustPeriodDao<E> {
    private static final String DELETE_SQL = "DELETE FROM NR_ADJUST_PERIOD WHERE AP_DS_KEY =?  AND AP_PERIOD = ?";

    public Class<E> getClz() {
        return null;
    }

    @Override
    public List<E> list(String schemeKey) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null");
        return super.list(new String[]{"AP_DS_KEY"}, (Object[])new String[]{schemeKey}, this.getClz());
    }

    @Override
    public List<E> list(String schemeKey, String period) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null");
        Assert.notNull((Object)period, "period must not be null");
        return super.list(new String[]{"AP_DS_KEY", "AP_PERIOD"}, (Object[])new String[]{schemeKey, period}, this.getClz());
    }

    @Override
    public void insert(List<E> adjustDos) throws AdjustPeriodException {
        if (CollectionUtils.isEmpty(adjustDos)) {
            return;
        }
        for (AdjustPeriod adjustmentPeriod : adjustDos) {
            adjustmentPeriod.setOrder(OrderGenerator.newOrder());
        }
        try {
            super.insert(adjustDos.toArray());
        }
        catch (BeanParaException | DBParaException e) {
            throw new AdjustPeriodException("\u66f4\u65b0\u8c03\u6574\u671f\u6570\u636e\u5f02\u5e38");
        }
    }

    @Override
    public void delete(String schemeKey) throws AdjustPeriodException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null");
        super.deleteBy(new String[]{"AP_DS_KEY"}, new String[]{schemeKey});
    }

    @Override
    public void delete(String schemeKey, String period) throws AdjustPeriodException {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null");
        Assert.notNull((Object)period, "period must not be null");
        try {
            this.jdbcTemplate.update(DELETE_SQL, new Object[]{schemeKey, period});
        }
        catch (DataAccessException e) {
            throw new AdjustPeriodException("\u8c03\u6574\u671f\u6570\u636e\u5220\u9664\u5f02\u5e38");
        }
    }
}


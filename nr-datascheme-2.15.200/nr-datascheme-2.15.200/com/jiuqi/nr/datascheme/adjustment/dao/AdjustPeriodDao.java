/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.datascheme.adjustment.dao;

import com.jiuqi.nr.datascheme.adjustment.exception.AdjustPeriodException;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import java.util.List;

public interface AdjustPeriodDao<E extends AdjustPeriod> {
    public List<E> list(String var1);

    public List<E> list(String var1, String var2);

    public void insert(List<E> var1) throws AdjustPeriodException;

    public void delete(String var1) throws AdjustPeriodException;

    public void delete(String var1, String var2) throws AdjustPeriodException;
}


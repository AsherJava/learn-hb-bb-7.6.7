/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.spi;

import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.impl.dto.AccessDTO;

public interface TableUpdaterProvider {
    public TableUpdater getTableUpdater(FieldSaveInfo var1, FieldDataStrategyFactory var2, AccessDTO var3);
}


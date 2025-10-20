/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.dimension.internal.cache.converter;

import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public interface DimensionConverter<T> {
    public DimensionEO convert(T var1, TableModelDefine var2);
}


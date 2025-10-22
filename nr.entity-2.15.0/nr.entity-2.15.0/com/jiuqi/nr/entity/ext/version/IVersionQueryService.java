/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.ext.version;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Date;

public interface IVersionQueryService {
    public Date getVersionDate(DimensionValueSet var1, String var2, String var3);

    public Date getVersionDate(DimensionValueSet var1, String var2);
}


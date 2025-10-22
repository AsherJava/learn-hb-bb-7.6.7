/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.version;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Date;

public interface DataVersion {
    public String getVersionId();

    public String getTitle();

    public String getDescribe();

    public Date getCreatTime();

    public String getCreatUser();

    public boolean isAutoCreated();

    public DimensionValueSet getDimensionValueSet();
}


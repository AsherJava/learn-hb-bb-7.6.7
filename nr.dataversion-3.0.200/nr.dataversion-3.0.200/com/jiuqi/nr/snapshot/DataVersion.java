/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Date;

public interface DataVersion {
    public String getVersionId();

    public String getTitle();

    public String getDescribe();

    public Date getCreatTime();

    public String getCreatUser();

    public boolean isAutoCreated();

    public String getFormSchemeKey();

    public DimensionCombination getDimensionValueSet();
}


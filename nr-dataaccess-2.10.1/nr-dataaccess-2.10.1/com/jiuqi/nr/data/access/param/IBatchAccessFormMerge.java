/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;
import java.util.Set;

public interface IBatchAccessFormMerge {
    public DimensionCollection getMasterKeys();

    public DimensionCollection getMasterKeysByTaskKey(String var1);

    public List<IAccessFormMerge> getAccessFormMerges();

    public Set<String> getFormSchemeKeys();

    public Set<String> getTaskKeys();

    public Set<String> getFormSchemeKeysByTasKey(String var1);

    public Set<IAccessForm> getAccessFormsByTaskKey(String var1);

    public Set<IAccessForm> getAccessFormsByFormSchemeKey(String var1);

    public Set<String> getFormKeysByZbKey(String var1);
}


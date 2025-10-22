/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Set;

public interface IAccessFormMerge {
    public DimensionCombination getMasterKey();

    public Set<IAccessForm> getAccessForms();

    public Set<String> getFormSchemeKeys();

    public Set<String> getTaskKeys();

    public Set<String> getFormSchemeKeysByTasKey(String var1);

    public Set<IAccessForm> getAccessFormsByTaskKey(String var1);

    public Set<IAccessForm> getAccessFormsByFormSchemeKey(String var1);

    public IAccessForm getAccessFormsByFormKey(String var1);
}


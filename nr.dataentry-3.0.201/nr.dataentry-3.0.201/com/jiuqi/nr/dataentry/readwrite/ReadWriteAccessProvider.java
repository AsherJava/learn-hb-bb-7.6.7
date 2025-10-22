/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataentry.readwrite;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.paramInfo.FormReadWriteAccessData;
import com.jiuqi.nr.dataentry.readwrite.IFieldReadWriteAccessResult;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import java.util.List;

public interface ReadWriteAccessProvider {
    public List<String> getAllFormKeys(String var1);

    public FormReadWriteAccessData getAccessForms(FormReadWriteAccessData var1, ReadWriteAccessCacheManager var2);

    public FormReadWriteAccessData getAccessForms(FormReadWriteAccessData var1, ReadWriteAccessCacheManager var2, boolean var3);

    public IFieldReadWriteAccessResult getZBWriteAccess(List<DimensionValueSet> var1, List<String> var2, String var3);
}


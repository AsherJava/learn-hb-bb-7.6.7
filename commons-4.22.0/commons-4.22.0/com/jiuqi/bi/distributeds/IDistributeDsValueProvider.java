/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.distributeds;

import com.jiuqi.bi.distributeds.DistributeDimValueFindException;
import com.jiuqi.bi.distributeds.DistributeDsDimValue;
import java.util.List;

public interface IDistributeDsValueProvider {
    public List<DistributeDsDimValue> findDimValues(String var1) throws DistributeDimValueFindException;
}


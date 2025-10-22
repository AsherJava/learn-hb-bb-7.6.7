/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.QueryField;

public interface IFieldValueProcessor {
    public Object processValue(QueryField var1, Object var2);

    public double getMultiplier(QueryField var1);
}


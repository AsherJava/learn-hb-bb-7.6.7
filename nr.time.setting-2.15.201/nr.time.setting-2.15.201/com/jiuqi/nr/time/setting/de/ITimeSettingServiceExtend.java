/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.time.setting.de;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.time.setting.bean.MsgReturn;

public interface ITimeSettingServiceExtend {
    public boolean enable(String var1);

    public MsgReturn compareTime(String var1, DimensionValueSet var2);
}


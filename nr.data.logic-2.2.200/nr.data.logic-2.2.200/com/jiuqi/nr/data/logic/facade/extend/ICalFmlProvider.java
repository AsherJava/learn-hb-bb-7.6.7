/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.facade.extend.param.GetCalFmlEnv;
import java.util.List;

public interface ICalFmlProvider {
    public List<FmlExecInfo> getFml(GetCalFmlEnv var1);

    public String getType();

    public boolean isUse(GetCalFmlEnv var1);
}


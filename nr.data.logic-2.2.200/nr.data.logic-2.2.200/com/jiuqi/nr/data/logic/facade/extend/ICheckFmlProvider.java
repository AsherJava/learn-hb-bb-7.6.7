/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.facade.extend.param.GetCheckFmlEnv;
import java.util.List;

public interface ICheckFmlProvider {
    public List<FmlExecInfo> getFml(GetCheckFmlEnv var1);

    public String getType();

    public boolean isUse(GetCheckFmlEnv var1);
}


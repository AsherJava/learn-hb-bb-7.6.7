/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.module;

import com.jiuqi.dc.base.common.module.IModule;
import java.util.List;

public interface IModuleGather {
    public List<IModule> getModules();

    public IModule getModuleByCode(String var1);
}


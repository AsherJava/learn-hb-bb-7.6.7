/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather;

import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import java.util.List;

public interface IPluginTypeGather {
    public IPluginType getPluginType(String var1);

    public IPluginType getPluginTypeByPackageName(String var1);

    public List<IPluginType> list();
}


/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather;

import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import java.util.List;

public interface IFieldMappingProviderGather {
    public IFieldMappingProvider getProvider(IPluginType var1, String var2);

    public List<IFieldMappingProvider> listByPluginType(IPluginType var1);
}


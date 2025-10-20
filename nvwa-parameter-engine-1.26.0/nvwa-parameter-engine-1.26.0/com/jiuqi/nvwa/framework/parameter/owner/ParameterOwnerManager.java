/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nvwa.framework.parameter.owner;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.owner.IParameterOwnerProvider;
import java.util.HashMap;
import java.util.Map;

public class ParameterOwnerManager {
    private static ParameterOwnerManager instance = new ParameterOwnerManager();
    private Map<String, IParameterOwnerProvider> providerMap = new HashMap<String, IParameterOwnerProvider>();

    public static ParameterOwnerManager getInstance() {
        return instance;
    }

    public void registerOwnerProvider(IParameterOwnerProvider provider) {
        if (StringUtils.isNotEmpty((String)provider.getOwnerType()) && !this.providerMap.containsKey(provider.getOwnerType())) {
            this.providerMap.put(provider.getOwnerType(), provider);
        }
    }

    public void removeOwnerProvider(String ownerType) {
        if (this.providerMap.containsKey(ownerType)) {
            this.providerMap.remove(ownerType);
        }
    }

    public IParameterOwnerProvider getOwnerProvider(String ownerType) {
        return this.providerMap.get(ownerType);
    }
}


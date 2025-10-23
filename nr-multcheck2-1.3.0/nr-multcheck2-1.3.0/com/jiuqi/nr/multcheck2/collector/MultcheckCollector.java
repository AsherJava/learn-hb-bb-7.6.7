/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.collector;

import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class MultcheckCollector {
    private List<IMultcheckItemProvider> providerList = new ArrayList<IMultcheckItemProvider>();
    private Map<String, IMultcheckItemProvider> providerMap;

    public MultcheckCollector(@Autowired(required=false) List<IMultcheckItemProvider> providerList) {
        if (CollectionUtils.isEmpty(providerList)) {
            return;
        }
        this.providerList = providerList.stream().sorted(Comparator.comparingDouble(IMultcheckItemProvider::getOrder)).collect(Collectors.toList());
        this.providerMap = providerList.stream().collect(Collectors.toMap(IMultcheckItemProvider::getType, provider -> provider));
    }

    public List<IMultcheckItemProvider> getProviderList() {
        return this.providerList;
    }

    public IMultcheckItemProvider getProvider(String type) {
        if (this.providerMap == null || this.providerMap.isEmpty()) {
            return null;
        }
        return this.providerMap.get(type);
    }
}


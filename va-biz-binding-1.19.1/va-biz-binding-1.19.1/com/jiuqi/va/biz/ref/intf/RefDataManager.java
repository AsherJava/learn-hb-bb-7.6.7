/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.va.biz.ref.intf;

import com.jiuqi.va.biz.ref.intf.RefData;
import com.jiuqi.va.biz.ref.intf.RefDataProvider;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class RefDataManager
implements InitializingBean {
    @Autowired(required=false)
    private List<RefDataProvider> providers;
    @Autowired
    private BaseDataClient baseDataClient;
    private Map<Integer, RefDataProvider> providerMap;
    private boolean useBasedataClient;

    public RefDataProvider findProvider(int refDataType) {
        return this.providerMap.get(refDataType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.providerMap = this.providers != null ? this.buildProviderMap() : Collections.emptyMap();
        if (!this.providerMap.isEmpty()) {
            RefData.setInstance(this);
        }
        if (!this.baseDataClient.getClass().getName().startsWith("com.jiuqi.va.basedata.service.join.BaseDataClientImpl")) {
            this.useBasedataClient = true;
        }
    }

    private Map<Integer, RefDataProvider> buildProviderMap() {
        HashMap<Integer, RefDataProvider> providerMap = new HashMap<Integer, RefDataProvider>();
        for (RefDataProvider provider : this.providers) {
            providerMap.put(provider.getRefDataType(), provider);
        }
        return providerMap;
    }

    public void flushAll() {
        for (RefDataProvider provider : this.providerMap.values()) {
            provider.flush();
        }
    }

    public boolean isUseBasedataClient() {
        return this.useBasedataClient;
    }
}


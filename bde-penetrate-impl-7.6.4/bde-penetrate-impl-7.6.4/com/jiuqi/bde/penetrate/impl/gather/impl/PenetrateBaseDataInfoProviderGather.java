/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.penetrate.impl.gather.impl;

import com.jiuqi.bde.penetrate.impl.gather.IPenetrateBaseDataInfoProviderGather;
import com.jiuqi.bde.penetrate.impl.model.IPenetrateBaseDataInfoProvider;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PenetrateBaseDataInfoProviderGather
implements InitializingBean,
IPenetrateBaseDataInfoProviderGather {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired(required=false)
    private List<IPenetrateBaseDataInfoProvider> providerList;
    private final Map<String, IPenetrateBaseDataInfoProvider> providerMap = new ConcurrentHashMap<String, IPenetrateBaseDataInfoProvider>();

    private void init() {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getPluginType())) {
                this.logger.warn("\u900f\u89c6\u57fa\u7840\u6570\u636e\u4fe1\u606f\u63d0\u4f9b\u5668\u6ca1\u6709\u6570\u636e\u6a21\u578b\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.providerMap.containsKey(item.getPluginType())) {
                this.providerMap.put(item.getPluginType(), (IPenetrateBaseDataInfoProvider)item);
            }
        });
    }

    @Override
    public IPenetrateBaseDataInfoProvider getProvider(String pluginType) {
        Assert.isNotEmpty((String)pluginType);
        IPenetrateBaseDataInfoProvider provider = this.providerMap.get(pluginType);
        if (provider != null) {
            return provider;
        }
        provider = this.providerMap.get("DEFAULT_BASEDATAINFOPROVIDER");
        if (provider != null) {
            return provider;
        }
        throw new BusinessRuntimeException(String.format("\u63d2\u4ef6\u3010%1$s\u3011\u900f\u89c6\u6682\u672a\u652f\u6301\u63d0\u4f9b\u57fa\u7840\u4fe1\u606f", pluginType));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            this.init();
        }
        catch (Exception e) {
            this.logger.error("\u900f\u89c6\u57fa\u7840\u6570\u636e\u4fe1\u606f\u63d0\u4f9b\u5668\u6536\u96c6\u51fa\u73b0\u9519\u8bef\uff0c\u81ea\u52a8\u8df3\u8fc7", e);
        }
    }
}


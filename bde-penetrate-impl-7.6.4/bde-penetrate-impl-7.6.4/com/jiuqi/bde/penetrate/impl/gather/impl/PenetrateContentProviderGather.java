/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 */
package com.jiuqi.bde.penetrate.impl.gather.impl;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.gather.IPenetrateContentProviderGather;
import com.jiuqi.bde.penetrate.impl.model.IPenetrateContentProvider;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PenetrateContentProviderGather
implements InitializingBean,
IPenetrateContentProviderGather {
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    @Autowired(required=false)
    private List<IPenetrateContentProvider> providerList;
    private final Map<String, IPenetrateContentProvider> providerMap = new ConcurrentHashMap<String, IPenetrateContentProvider>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getPluginType())) {
                this.logger.warn("\u67e5\u8be2\u7ed3\u679c\u63d0\u4f9b\u5668\u6ca1\u6709\u6307\u5b9a\u8f6f\u4ef6\u7c7b\u578b\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (item.getBizModel() == null) {
                this.logger.warn("\u67e5\u8be2\u7ed3\u679c\u63d0\u4f9b\u5668\u6ca1\u6709\u6570\u636e\u6a21\u578b\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (item.getPenetrateType() == null) {
                this.logger.warn("\u67e5\u8be2\u5217\u63d0\u4f9b\u5668\u6ca1\u6709\u900f\u89c6\u7c7b\u578b\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            String key = this.getKey(item.getPluginType(), item.getBizModel(), item.getPenetrateType());
            if (!this.providerMap.containsKey(key)) {
                this.providerMap.put(key, (IPenetrateContentProvider)item);
            }
        });
    }

    @Override
    public IPenetrateContentProvider getProvider(String pluginType, String bizModelKey, PenetrateTypeEnum penetrateType) {
        Assert.isNotEmpty((String)pluginType);
        Assert.isNotNull((Object)bizModelKey);
        if (ComputationModelEnum.CUSTOMFETCH.getCode().equals(bizModelKey)) {
            pluginType = ComputationModelEnum.CUSTOMFETCH.getCode();
        }
        ComputationModelEnum computationModel = ComputationModelEnum.getEnumByCode((String)bizModelKey);
        IPenetrateContentProvider provider = this.providerMap.get(this.getKey(pluginType, bizModelKey, penetrateType));
        if (provider != null) {
            return provider;
        }
        provider = this.providerMap.get(this.getKey(pluginType, "DEFAULT_CONTENTPROVIDER", penetrateType));
        if (provider != null) {
            return provider;
        }
        throw new BusinessRuntimeException(String.format("\u63d2\u4ef6\u3010%1$s\u3011\u3001\u3010%2$s\u3011\u900f\u89c6\u3010%3$s\u3011\u6682\u672a\u652f\u6301", this.pluginTypeGather.getPluginType(pluginType) != null ? this.pluginTypeGather.getPluginType(pluginType).getTitle() : pluginType, computationModel.getName(), penetrateType.getName()));
    }

    private String getKey(String pluginType, String bizModelKey, PenetrateTypeEnum penetrateType) {
        return pluginType + "|" + bizModelKey + "|" + penetrateType.getCode();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


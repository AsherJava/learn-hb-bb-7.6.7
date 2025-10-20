/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class FieldMappingProviderGather
implements InitializingBean,
IFieldMappingProviderGather {
    @Autowired(required=false)
    private List<IFieldMappingProvider> providerList;
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    private final Map<String, IFieldMappingProvider> providerMap = new ConcurrentHashMap<String, IFieldMappingProvider>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            IPluginType pluginType = Optional.ofNullable(item.getPluginType()).orElse(this.pluginTypeGather.getPluginTypeByPackageName(item.getClass().getCanonicalName()));
            if (pluginType == null || StringUtils.isEmpty((String)pluginType.getSymbol())) {
                this.logger.warn("\u5b57\u6bb5\u6620\u5c04\u63d0\u4f9b\u5668\u63d2\u4ef6{}\u7684\u63d2\u4ef6\u7c7b\u578b\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u5b57\u6bb5\u6620\u5c04\u63d0\u4f9b\u5668\u63d2\u4ef6{}\u7684\u4e1a\u52a1\u8868\u540d\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            String key = this.getKey(pluginType, item.getCode());
            if (!this.providerMap.containsKey(key) || Objects.nonNull(item.getClass().getAnnotation(Primary.class))) {
                this.providerMap.put(key, (IFieldMappingProvider)item);
            }
        });
    }

    @Override
    public IFieldMappingProvider getProvider(IPluginType pluginType, String tableName) {
        Assert.isNotNull((Object)pluginType);
        Assert.isNotEmpty((String)tableName);
        return this.providerMap.get(this.getKey(pluginType, tableName));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    private String getKey(IPluginType pluginType, String code) {
        return pluginType.getSymbol() + "|" + code;
    }

    @Override
    public List<IFieldMappingProvider> listByPluginType(IPluginType pluginType) {
        Assert.isNotNull((Object)pluginType);
        Assert.isNotEmpty((String)pluginType.getSymbol());
        ArrayList<IFieldMappingProvider> list = new ArrayList<IFieldMappingProvider>(20);
        for (Map.Entry<String, IFieldMappingProvider> entry : this.providerMap.entrySet()) {
            if (!this.getPluginTypeByKey(entry.getKey()).equals(pluginType.getSymbol())) continue;
            list.add(entry.getValue());
        }
        return list.stream().sorted(Comparator.comparing(IFieldMappingProvider::showOrder)).collect(Collectors.toList());
    }

    private String getPluginTypeByKey(String key) {
        return key.substring(0, key.indexOf("|"));
    }
}


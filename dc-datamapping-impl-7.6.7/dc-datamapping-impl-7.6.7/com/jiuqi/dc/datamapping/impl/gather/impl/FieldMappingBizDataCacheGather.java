/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.datamapping.impl.gather.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.impl.gather.IFieldMappingBizDataCacheGather;
import com.jiuqi.dc.datamapping.impl.service.FieldMappingBizDataCacheProvider;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldMappingBizDataCacheGather
implements InitializingBean,
IFieldMappingBizDataCacheGather {
    @Autowired(required=false)
    private List<FieldMappingBizDataCacheProvider> providerList;
    private final Map<String, FieldMappingBizDataCacheProvider> providerMap = new ConcurrentHashMap<String, FieldMappingBizDataCacheProvider>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public FieldMappingBizDataCacheProvider getServiceByCode(String isolationStrategy) {
        Assert.isNotNull((Object)isolationStrategy);
        FieldMappingBizDataCacheProvider cache = this.providerMap.get(isolationStrategy);
        return cache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            String isolateTypeCode = Optional.ofNullable(item.getIsolateType().getCode()).orElse(null);
            if (StringUtils.isEmpty((String)isolateTypeCode)) {
                this.logger.warn("\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u7f13\u5b58\u63d0\u4f9b\u5668{}\u7c7b\u578b\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            this.providerMap.computeIfAbsent(isolateTypeCode, k -> item);
        });
    }
}


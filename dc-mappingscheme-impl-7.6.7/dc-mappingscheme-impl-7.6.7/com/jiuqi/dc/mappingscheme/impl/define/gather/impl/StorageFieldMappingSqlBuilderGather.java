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
import com.jiuqi.dc.mappingscheme.impl.define.IStorageFiledMappingSqlBuilder;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IStorageFieldMappingSqlBuilderGather;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
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
public class StorageFieldMappingSqlBuilderGather
implements InitializingBean,
IStorageFieldMappingSqlBuilderGather {
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired(required=false)
    private List<IStorageFiledMappingSqlBuilder> providerList;
    private final Map<String, IStorageFiledMappingSqlBuilder> providerMap = new ConcurrentHashMap<String, IStorageFiledMappingSqlBuilder>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public IStorageFiledMappingSqlBuilder getProvider(String storageType) {
        Assert.isNotNull((Object)storageType);
        IStorageFiledMappingSqlBuilder sqlBuilder = this.providerMap.get(storageType);
        return sqlBuilder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            String ruleTypeCode = Optional.ofNullable(item.getStorageType().getCode()).orElse(null);
            if (StringUtils.isEmpty((String)ruleTypeCode)) {
                this.logger.warn("\u5b57\u6bb5\u6620\u5c04Sql\u63d0\u4f9b\u5668\u63d2\u4ef6{}\u89c4\u5219\u7c7b\u578b\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            this.providerMap.computeIfAbsent(ruleTypeCode, k -> item);
        });
    }
}


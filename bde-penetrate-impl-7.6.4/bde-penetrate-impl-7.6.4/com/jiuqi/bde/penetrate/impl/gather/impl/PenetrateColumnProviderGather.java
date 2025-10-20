/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.penetrate.impl.gather.impl;

import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.gather.IPenetrateColumnBuilderGather;
import com.jiuqi.bde.penetrate.impl.model.IPenetrateColumnBuilder;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PenetrateColumnProviderGather
implements InitializingBean,
IPenetrateColumnBuilderGather {
    @Autowired(required=false)
    private List<IPenetrateColumnBuilder> providerList;
    private final Map<String, IPenetrateColumnBuilder> providerMap = new ConcurrentHashMap<String, IPenetrateColumnBuilder>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            if (item.getBizModel() == null) {
                this.logger.warn("\u67e5\u8be2\u5217\u63d0\u4f9b\u5668\u6ca1\u6709\u6570\u636e\u6a21\u578b\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (item.getPenetrateType() == null) {
                this.logger.warn("\u67e5\u8be2\u5217\u63d0\u4f9b\u5668\u6ca1\u6709\u900f\u89c6\u7c7b\u578b\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            String key = this.getKey(item.getBizModel(), item.getPenetrateType());
            if (!this.providerMap.containsKey(key)) {
                this.providerMap.put(key, (IPenetrateColumnBuilder)item);
            }
        });
    }

    private String getKey(String bizModelKey, PenetrateTypeEnum penetrateType) {
        return bizModelKey + "|" + penetrateType.getCode();
    }

    @Override
    public IPenetrateColumnBuilder getProvider(String bizModelKey, PenetrateTypeEnum penetrateType) {
        Assert.isNotEmpty((String)bizModelKey);
        ComputationModelEnum computationModel = ComputationModelEnum.getEnumByCode((String)bizModelKey);
        IPenetrateColumnBuilder provider = this.providerMap.get(this.getKey(bizModelKey, penetrateType));
        if (provider != null) {
            return provider;
        }
        provider = this.providerMap.get(this.getKey("DEFAULT_COLUMNBUILDER", penetrateType));
        if (provider != null) {
            return provider;
        }
        throw new BusinessRuntimeException(String.format("\u3010%1$s\u3011\u900f\u89c6\u3010%2$s\u3011\u6682\u672a\u652f\u6301", computationModel.getName(), penetrateType.getName()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.plugin.common.cache.memcache.gather.impl;

import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.plugin.common.cache.memcache.gather.IMemoryBalanceType;
import com.jiuqi.bde.plugin.common.cache.memcache.gather.IMemoryBalanceTypeGather;
import com.jiuqi.bde.plugin.common.cache.memcache.gather.impl.BuiltinMemoryBalanceType;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemoryBalanceTypeGather
implements InitializingBean,
IMemoryBalanceTypeGather {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryBalanceTypeGather.class);
    @Autowired(required=false)
    private List<IMemoryBalanceType> memoryBalanceTypeList;
    private final Map<String, IMemoryBalanceType> memoryBalanceTypeMap = new HashMap<String, IMemoryBalanceType>();

    @Override
    public IMemoryBalanceType getMemoryBalanceType(String symbol) {
        return this.memoryBalanceTypeMap.get(symbol);
    }

    @Override
    public List<IMemoryBalanceType> list() {
        return Collections.unmodifiableList(new ArrayList<IMemoryBalanceType>(this.memoryBalanceTypeMap.values()));
    }

    private void init() {
        this.memoryBalanceTypeMap.clear();
        MemoryBalanceTypeEnum[] builtInMemoryBalanceType = MemoryBalanceTypeEnum.values();
        for (MemoryBalanceTypeEnum memoryBalanceTypeEnum : builtInMemoryBalanceType) {
            BuiltinMemoryBalanceType memoryBalanceType = new BuiltinMemoryBalanceType(memoryBalanceTypeEnum.getCode(), memoryBalanceTypeEnum.getCode(), memoryBalanceTypeEnum.getName(), memoryBalanceTypeEnum.getBizModel(), memoryBalanceTypeEnum.getOrder(), memoryBalanceTypeEnum.getDesc());
            this.memoryBalanceTypeMap.put(memoryBalanceType.getSymbol(), memoryBalanceType);
        }
        if (CollectionUtils.isEmpty(this.memoryBalanceTypeList)) {
            return;
        }
        for (IMemoryBalanceType memoryBalanceType : this.memoryBalanceTypeList) {
            if (StringUtils.isEmpty((String)memoryBalanceType.getSymbol())) {
                LOGGER.warn("MemoryBalanceType[\u70ed\u70b9\u8868\u7c7b\u578b]: {} \u7684\u552f\u4e00\u6807\u8bc6[symbol]\u4e3a\u7a7a\uff0c\u8df3\u8fc7\u6ce8\u518c", (Object)memoryBalanceType.getClass());
                continue;
            }
            if (this.memoryBalanceTypeMap.containsKey(memoryBalanceType.getSymbol())) {
                LOGGER.warn("MemoryBalanceType[\u70ed\u70b9\u8868\u7c7b\u578b]: {} \u7684\u552f\u4e00\u6807\u8bc6[symbol]\u91cd\u590d\uff0c\u8df3\u8fc7\u6ce8\u518c", (Object)memoryBalanceType.getClass());
                continue;
            }
            this.memoryBalanceTypeMap.put(memoryBalanceType.getSymbol(), memoryBalanceType);
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.init();
    }
}


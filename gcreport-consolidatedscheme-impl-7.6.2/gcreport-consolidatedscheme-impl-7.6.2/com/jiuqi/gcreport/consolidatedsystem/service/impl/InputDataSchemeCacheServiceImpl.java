/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.event.InputDataSchemeChangedEvent
 *  com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.event.InputDataSchemeChangedEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.InputDataSchemeService;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import org.springframework.cache.Cache;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class InputDataSchemeCacheServiceImpl
implements InputDataSchemeCacheService {
    private NedisCacheManager cacheManger;
    private InputDataSchemeService inputDataSchemeService;

    public InputDataSchemeCacheServiceImpl(NedisCacheManager cacheManger, InputDataSchemeService inputDataSchemeService) {
        this.cacheManger = cacheManger;
        this.inputDataSchemeService = inputDataSchemeService;
    }

    @Override
    public InputDataSchemeVO getInputDataSchemeByDataSchemeKey(String dataSchemeKey) {
        InputDataSchemeVO inputDataSchemeVO;
        NedisCache cache = this.cacheManger.getCache("gcreport:inputDataScheme");
        Cache.ValueWrapper valueWrapper = cache.get(dataSchemeKey);
        if (null == valueWrapper) {
            inputDataSchemeVO = this.valueLoader(dataSchemeKey);
            if (ObjectUtils.isEmpty(inputDataSchemeVO)) {
                throw new BusinessRuntimeException("\u6570\u636e\u5173\u8054\u8868\u8bb0\u5f55\u4e0d\u5b58\u5728");
            }
            cache.put(dataSchemeKey, (Object)inputDataSchemeVO);
        } else {
            inputDataSchemeVO = (InputDataSchemeVO)valueWrapper.get();
        }
        return inputDataSchemeVO;
    }

    @EventListener
    public void onApplicationEvent(InputDataSchemeChangedEvent event) {
        String dataSchemeKey = event.getInputDataSchemeChangedInfo().getDataSchemeKey();
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            return;
        }
        this.cacheManger.getCache("gcreport:inputDataScheme").evict(dataSchemeKey);
    }

    private InputDataSchemeVO valueLoader(String dataSchemeKey) {
        return this.inputDataSchemeService.getInputDataSchemeByDataSchemeKey(dataSchemeKey);
    }

    @Override
    public void clearAllCache() {
        this.cacheManger.getCache("gcreport:inputDataScheme").clear();
    }
}


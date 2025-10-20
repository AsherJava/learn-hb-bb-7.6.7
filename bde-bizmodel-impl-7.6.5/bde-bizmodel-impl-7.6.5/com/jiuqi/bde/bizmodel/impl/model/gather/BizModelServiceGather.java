/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.bde.bizmodel.impl.model.gather;

import com.jiuqi.bde.bizmodel.impl.model.gather.IBizModelServiceGather;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelManageService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizModelServiceGather
implements IBizModelServiceGather {
    private List<BizModelManageService> registeredBizModelServiceList;
    private final Map<String, BizModelManageService> bizModelServiceMap = new ConcurrentHashMap<String, BizModelManageService>(16);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public BizModelServiceGather(@Autowired(required=false) List<BizModelManageService> registeredBizModelServiceList) {
        this.registeredBizModelServiceList = registeredBizModelServiceList;
        this.afterPropertiesSet();
    }

    private void init() {
        try {
            this.bizModelServiceMap.clear();
            this.registeredBizModelServiceList.forEach(item -> {
                if (item.getCategoryCode() == null) {
                    this.logger.warn("\u4e1a\u52a1\u6a21\u578b\u7c7b\u522bService{}\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                    return;
                }
                if (!this.bizModelServiceMap.containsKey(item.getCategoryCode())) {
                    this.bizModelServiceMap.put(item.getCategoryCode(), (BizModelManageService)item);
                }
            });
        }
        catch (Exception e) {
            this.logger.error("\u4e1a\u52a1\u6a21\u578b\u521d\u59cb\u51fa\u73b0\u5f02\u5e38", e);
        }
    }

    @Override
    public BizModelManageService getByCode(String categoryCode) {
        Assert.isNotEmpty((String)categoryCode);
        BizModelManageService bizModelService = this.bizModelServiceMap.get(categoryCode);
        if (bizModelService == null) {
            throw new BusinessRuntimeException(String.format("\u4e1a\u52a1\u6a21\u578b\u7c7b\u522b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684Service\u5b9e\u73b0", categoryCode));
        }
        return bizModelService;
    }

    @Override
    public List<BizModelManageService> list() {
        return this.bizModelServiceMap.values().stream().collect(Collectors.toList());
    }

    public void afterPropertiesSet() {
        this.init();
    }
}


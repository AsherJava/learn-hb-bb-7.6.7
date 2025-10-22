/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.checker.BizModelHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizModelHandlerGather
implements InitializingBean {
    @Autowired(required=false)
    private List<BizModelHandler> bizModelHandlerList;
    private final Map<String, BizModelHandler> bizModelCheckerMap = new ConcurrentHashMap<String, BizModelHandler>();

    @Override
    public void afterPropertiesSet() {
        this.init();
    }

    private void init() {
        this.bizModelCheckerMap.clear();
        if (CollectionUtils.isEmpty(this.bizModelHandlerList)) {
            this.bizModelHandlerList = new ArrayList<BizModelHandler>();
        }
        this.bizModelHandlerList.forEach(item -> this.bizModelCheckerMap.put(item.getBizModelCode(), (BizModelHandler)item));
    }

    public BizModelHandler getBizCheckerByBizModel(String bizModelCode) {
        Assert.isNotEmpty((String)bizModelCode);
        BizModelHandler modelLoader = this.bizModelCheckerMap.get(bizModelCode);
        if (modelLoader == null) {
            modelLoader = this.bizModelCheckerMap.get("DEFAULT");
        }
        return modelLoader;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.webserviceclient.gather.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.webserviceclient.gather.IWsDataHandlerGather;
import com.jiuqi.gcreport.webserviceclient.intf.IWsDataHandler;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class GcWsDataHandlerGather
implements IWsDataHandlerGather,
InitializingBean {
    @Autowired(required=false)
    private List<IWsDataHandler> wsDataHandlerList;
    private final Map<String, IWsDataHandler> providerMap = new ConcurrentHashMap<String, IWsDataHandler>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.wsDataHandlerList)) {
            this.providerMap.clear();
            return;
        }
        this.wsDataHandlerList.forEach(item -> {
            if (item.getKey() == null) {
                this.logger.warn("\u67e5\u8be2\u63d0\u4f9b\u5668\u6ca1\u6709\u63a5\u53e3\u7f16\u53f7\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.providerMap.containsKey(item.getKey())) {
                this.providerMap.put(item.getKey(), (IWsDataHandler)item);
            }
        });
    }

    @Override
    public IWsDataHandler getWsDataHandler(String zifno) {
        Assert.isNotNull((Object)zifno);
        IWsDataHandler provider = this.providerMap.get(zifno);
        if (provider == null) {
            throw new BusinessRuntimeException(String.format("\u63a5\u53e3\u7f16\u53f7\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6a21\u578b", zifno));
        }
        return provider;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


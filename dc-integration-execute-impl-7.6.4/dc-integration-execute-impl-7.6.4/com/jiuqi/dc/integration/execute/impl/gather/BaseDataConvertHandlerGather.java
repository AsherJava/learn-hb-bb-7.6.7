/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.integration.execute.impl.gather;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.integration.execute.impl.intf.IBaseDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.intf.IBaseDataConvertHandlerGather;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataConvertHandlerGather
implements InitializingBean,
IBaseDataConvertHandlerGather {
    @Autowired(required=false)
    private List<IBaseDataConvertHandler> handlerList;
    private final Map<String, IBaseDataConvertHandler> handlerMap = new ConcurrentHashMap<String, IBaseDataConvertHandler>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.handlerList)) {
            this.handlerMap.clear();
            return;
        }
        this.handlerList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u57fa\u7840\u6570\u636e\u8f6c\u6362\u5904\u7406\u5668{}\u6807\u8bc6\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            this.handlerMap.put(item.getCode(), (IBaseDataConvertHandler)item);
        });
    }

    @Override
    public IBaseDataConvertHandler getHandler(String code) {
        Assert.isNotEmpty((String)code);
        if (this.handlerMap.get(code) != null) {
            return this.handlerMap.get(code);
        }
        return this.handlerMap.get("DEFAULT");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


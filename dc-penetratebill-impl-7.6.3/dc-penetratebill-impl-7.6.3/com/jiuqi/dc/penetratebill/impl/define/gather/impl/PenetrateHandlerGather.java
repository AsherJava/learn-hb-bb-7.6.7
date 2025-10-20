/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.penetratebill.impl.define.gather.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.penetratebill.impl.define.IPenetrateHandler;
import com.jiuqi.dc.penetratebill.impl.define.gather.IPenetrateHandlerGather;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PenetrateHandlerGather
implements InitializingBean,
IPenetrateHandlerGather {
    @Autowired(required=false)
    private List<IPenetrateHandler> handlerList;
    private final Map<String, IPenetrateHandler> handlerMap = new ConcurrentHashMap<String, IPenetrateHandler>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.handlerMap.clear();
        if (CollectionUtils.isEmpty(this.handlerList)) {
            this.handlerList = new ArrayList<IPenetrateHandler>();
        }
        this.handlerList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u8054\u67e5\u5355\u636e\u65b9\u6848\u7a7f\u900f\u5904\u7406\u5668{}\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.handlerMap.containsKey(item.getCode())) {
                this.handlerMap.put(item.getCode(), (IPenetrateHandler)item);
            } else {
                this.logger.warn("\u8054\u67e5\u5355\u636e\u65b9\u6848\u7a7f\u900f\u5904\u7406\u5668{}\u91cd\u590d\u6ce8\u518c\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7", (Object)item.getClass());
            }
        });
    }

    @Override
    public IPenetrateHandler getPenetrateHandler(String code) {
        return this.handlerMap.get(code);
    }

    @Override
    public List<IPenetrateHandler> list() {
        return Collections.unmodifiableList(new ArrayList<IPenetrateHandler>(this.handlerMap.values()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


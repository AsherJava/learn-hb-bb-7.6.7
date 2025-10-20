/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.bde.floatmodel.impl.gather;

import com.jiuqi.bde.floatmodel.impl.gather.FloatConfigHandler;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FloatRegionHandlerGather
implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(FloatRegionHandlerGather.class);
    @Autowired(required=false)
    private List<FloatConfigHandler> registerFloatRegionHandlerList;
    private List<FloatConfigHandler> floatRegionHandlerList;
    private final Map<String, FloatConfigHandler> floatRegionHandlerMap = new ConcurrentHashMap<String, FloatConfigHandler>();

    @Override
    public void afterPropertiesSet() {
        this.init();
    }

    private void init() {
        try {
            this.floatRegionHandlerMap.clear();
            this.floatRegionHandlerList = new ArrayList<FloatConfigHandler>();
            Assert.isNotNull(this.registerFloatRegionHandlerList, (String)"\u6d6e\u52a8\u914d\u7f6e\u5904\u7406\u5668FloatConfigHandler\u672a\u83b7\u53d6\u5230\u5b9e\u73b0\u7c7b", (Object[])new Object[0]);
            this.registerFloatRegionHandlerList.forEach(item -> {
                if (this.floatRegionHandlerMap.containsKey(item.getCode())) {
                    this.logger.error("\u91cd\u590d\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e{}", (Object)JsonUtils.writeValueAsString((Object)item));
                } else {
                    this.floatRegionHandlerMap.put(item.getCode(), (FloatConfigHandler)item);
                    this.floatRegionHandlerList.add((FloatConfigHandler)item);
                }
            });
            this.floatRegionHandlerList = this.floatRegionHandlerList.stream().sorted(Comparator.comparing(FloatConfigHandler::getOrder)).collect(Collectors.toList());
        }
        catch (Exception e) {
            this.logger.error("\u6d6e\u52a8\u533a\u57df\u6536\u96c6\u5668FloatRegionHandlerGather\u521d\u59cb\u5316\u5931\u8d25", e);
        }
    }

    public Map<String, FloatConfigHandler> getFloatRegionHandlerMap() {
        return this.floatRegionHandlerMap;
    }

    public FloatConfigHandler getFloatRegionHandlerByQueryType(String queryType) {
        Assert.isNotEmpty((String)queryType, (String)"\u67e5\u8be2\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        FloatConfigHandler floatRegionHandler = this.floatRegionHandlerMap.get(queryType);
        Assert.isTrue((floatRegionHandler != null && floatRegionHandler.enable() ? 1 : 0) != 0, (String)"\u672a\u80fd\u627e\u5230\u67e5\u8be2\u7c7b\u578b\u3010%s\u3011\u5bf9\u5e94\u7684\u6d6e\u52a8\u533a\u57df\u6570\u636e\u5904\u7406\u5668", (Object[])new Object[]{queryType});
        return floatRegionHandler;
    }

    public List<FloatConfigHandler> getFloatRegionHandlerList() {
        if (CollectionUtils.isEmpty(this.floatRegionHandlerList)) {
            this.logger.error("\u6d6e\u52a8\u533a\u57df\u5904\u7406\u5668\u83b7\u53d6\u4e3a\u7a7a");
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.floatRegionHandlerList.stream().filter(FloatConfigHandler::enable).collect(Collectors.toList()));
    }
}


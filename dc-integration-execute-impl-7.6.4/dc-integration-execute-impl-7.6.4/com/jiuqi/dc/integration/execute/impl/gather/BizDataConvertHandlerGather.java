/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 */
package com.jiuqi.dc.integration.execute.impl.gather;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandlerGather;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class BizDataConvertHandlerGather
implements InitializingBean,
IBizDataConvertHandlerGather {
    @Autowired(required=false)
    private List<IBizDataConvertHandler> handlerList;
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    private final Map<String, IBizDataConvertHandler> handlerMap = new ConcurrentHashMap<String, IBizDataConvertHandler>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.handlerList)) {
            this.handlerMap.clear();
            return;
        }
        this.handlerList.forEach(item -> {
            IPluginType pluginType = Optional.ofNullable(item.getPluginType()).orElse(this.pluginTypeGather.getPluginTypeByPackageName(item.getClass().getCanonicalName()));
            if (pluginType == null) {
                this.logger.warn("\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u7c7b\u578b\u63d2\u4ef6{}\u63d2\u4ef6\u7c7b\u578b\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u7c7b\u578b\u63d2\u4ef6{}\u4ee3\u7801\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            String key = this.getKey(pluginType, item.getCode(), item.getFetchDataType());
            if (!this.handlerMap.containsKey(key) || Objects.nonNull(item.getClass().getAnnotation(Primary.class))) {
                this.handlerMap.put(key, (IBizDataConvertHandler)item);
            }
        });
    }

    @Override
    public IBizDataConvertHandler getHandler(IPluginType pluginType, String code, String fetchDataType) {
        Assert.isNotNull((Object)pluginType);
        Assert.isNotEmpty((String)code);
        Assert.isNotEmpty((String)fetchDataType);
        return this.handlerMap.get(this.getKey(pluginType, code, fetchDataType));
    }

    private String getKey(IPluginType pluginType, String code, String fetchDataType) {
        return pluginType.getSymbol() + "|" + code + "|" + fetchDataType;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


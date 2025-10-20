/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.define.IDataSchemeInitializer;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IDataSchemeInitializerGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class DataDataSchemeInitializerGather
implements IDataSchemeInitializerGather,
InitializingBean {
    @Autowired(required=false)
    private List<IDataSchemeInitializer> initializerList;
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    private final Map<String, IDataSchemeInitializer> initializerMap = new ConcurrentHashMap<String, IDataSchemeInitializer>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.initializerList)) {
            this.initializerMap.clear();
            return;
        }
        this.initializerList.forEach(item -> {
            IPluginType pluginType = Optional.ofNullable(item.getPluginType()).orElse(this.pluginTypeGather.getPluginTypeByPackageName(item.getClass().getCanonicalName()));
            if (StringUtils.isEmpty((String)pluginType.getSymbol())) {
                this.logger.warn("\u57fa\u7840\u6570\u636e\u8f6c\u6362\u5904\u7406\u5668{}\u6807\u8bc6\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.initializerMap.containsKey(pluginType.getSymbol()) || Objects.nonNull(item.getClass().getAnnotation(Primary.class))) {
                this.initializerMap.put(pluginType.getSymbol(), (IDataSchemeInitializer)item);
            }
        });
    }

    @Override
    public IDataSchemeInitializer getByPluginType(String pluginType) {
        IDataSchemeInitializer initializer = this.initializerMap.get(pluginType);
        Assert.notNull((Object)initializer, (String)String.format("\u63d2\u4ef6\u7c7b\u578b\u3010%s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u521d\u59cb\u5668", pluginType));
        return initializer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


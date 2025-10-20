/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontPluginDefineFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class FrontPluginDefineFactoryManager
implements InitializingBean {
    @Autowired
    private List<FrontPluginDefineFactory> list;
    private Map<String, FrontPluginDefineFactory> map;
    private static FrontPluginDefineFactoryManager instance;

    public static FrontPluginDefineFactory find(String name) {
        return FrontPluginDefineFactoryManager.instance.map.get(name);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.map = this.list.stream().collect(Collectors.toMap(o -> o.getName(), o -> o));
        instance = this;
    }
}


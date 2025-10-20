/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.gather.impl.BdeBizModelGather
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.execute.assist.impl;

import com.jiuqi.bde.bizmodel.define.gather.impl.BdeBizModelGather;
import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;
import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.bizmodel.execute.assist.IAssistProviderGather;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssistProviderGather
implements IAssistProviderGather {
    private List<IAssistProvider<?>> registeredAssistProviderList;
    private final Map<String, IAssistProvider<?>> assistProviderMap = new ConcurrentHashMap();
    private static final Logger logger = LoggerFactory.getLogger(BdeBizModelGather.class);

    public AssistProviderGather(@Autowired(required=false) List<IAssistProvider<?>> registeredAssistProviderList) {
        this.registeredAssistProviderList = registeredAssistProviderList;
        this.init();
    }

    @Override
    public <AcctAssist extends IAcctAssist> IAssistProvider<AcctAssist> getByPluginType(String pluginType) {
        Assert.isNotEmpty((String)pluginType);
        IAssistProvider<?> provider = this.assistProviderMap.get(pluginType);
        Assert.isNotNull(provider, (String)String.format("\u6ca1\u6709\u63d2\u4ef6\u4e3a\u3010%s\u3011\u7684\u8f85\u52a9\u9879\u63d0\u4f9b\u5668\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u7cfb\u7edf\u914d\u7f6e", pluginType), (Object[])new Object[0]);
        return provider;
    }

    private void init() {
        try {
            this.reload();
        }
        catch (Exception e) {
            logger.error("\u4e1a\u52a1\u6a21\u578b\u52a0\u8f7d\u5668\u521d\u59cb\u5316\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    public void reload() throws Exception {
        this.assistProviderMap.clear();
        if (CollectionUtils.isEmpty(this.registeredAssistProviderList)) {
            this.registeredAssistProviderList = new ArrayList();
        }
        this.registeredAssistProviderList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getPluginType())) {
                logger.warn("\u63d2\u4ef6\u8f85\u52a9\u9879\u63d0\u4f9b\u5668{}\uff0c\u63d2\u4ef6\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.assistProviderMap.containsKey(item.getPluginType())) {
                this.assistProviderMap.put(item.getPluginType(), (IAssistProvider<?>)item);
            }
        });
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class PluginTypeGather
implements InitializingBean,
IPluginTypeGather {
    @Autowired(required=false)
    private List<IPluginType> extPluginTypeList;
    private final Map<String, IPluginType> pluginTypeMap = new ConcurrentHashMap<String, IPluginType>();
    private final Map<String, IPluginType> referencePluginTypeMap = new ConcurrentHashMap<String, IPluginType>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.pluginTypeMap.clear();
        if (CollectionUtils.isEmpty(this.extPluginTypeList)) {
            this.extPluginTypeList = new ArrayList<IPluginType>();
        }
        this.extPluginTypeList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getSymbol())) {
                this.logger.warn("\u63d2\u4ef6\u7c7b\u578b{}\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.pluginTypeMap.containsKey(item.getSymbol())) {
                this.pluginTypeMap.put(item.getSymbol(), (IPluginType)item);
            } else if (Objects.nonNull(item.getClass().getAnnotation(Primary.class))) {
                this.pluginTypeMap.put(item.getSymbol(), (IPluginType)item);
            } else {
                this.logger.warn("\u63d2\u4ef6\u7c7b\u578b{}\u91cd\u590d\u6ce8\u518c\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7", (Object)item.getClass());
            }
            String packageName = item.getClass().getCanonicalName().substring(0, item.getClass().getCanonicalName().lastIndexOf("."));
            if (!this.referencePluginTypeMap.containsKey(packageName)) {
                this.referencePluginTypeMap.put(packageName, (IPluginType)item);
            } else if (Objects.nonNull(item.getClass().getAnnotation(Primary.class))) {
                this.referencePluginTypeMap.put(packageName, (IPluginType)item);
            } else {
                this.logger.warn(packageName + "\u5305\u8def\u5f84\u4e0b\u6709\u591a\u4e2a\u63d2\u4ef6\u7c7b\u578b{}\u91cd\u590d\u6ce8\u518c\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7", (Object)item.getClass());
            }
        });
    }

    @Override
    public IPluginType getPluginType(String code) {
        return this.pluginTypeMap.get(code);
    }

    @Override
    public IPluginType getPluginTypeByPackageName(String packageName) {
        for (Map.Entry<String, IPluginType> entry : this.referencePluginTypeMap.entrySet()) {
            if (!packageName.startsWith(entry.getKey() + ".")) continue;
            return entry.getValue();
        }
        return null;
    }

    @Override
    public List<IPluginType> list() {
        return Collections.unmodifiableList(new ArrayList<IPluginType>(this.pluginTypeMap.values()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}


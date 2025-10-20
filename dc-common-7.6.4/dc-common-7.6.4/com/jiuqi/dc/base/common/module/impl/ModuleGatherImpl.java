/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.dc.base.common.module.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.module.IModule;
import com.jiuqi.dc.base.common.module.IModuleGather;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModuleGatherImpl
implements IModuleGather,
InitializingBean {
    private final List<IModule> moduleCacheList = new ArrayList<IModule>();
    private final Map<String, IModule> moduleCacheMap = new ConcurrentHashMap<String, IModule>();
    @Autowired(required=false)
    private List<IModule> moduleList;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void registerModule() {
        Map<String, IModule> map = this.moduleCacheMap;
        synchronized (map) {
            if (this.moduleList == null || this.moduleList.isEmpty()) {
                return;
            }
            this.moduleCacheMap.clear();
            this.moduleCacheList.clear();
            Collections.sort(this.moduleList, this.getModuleComparator());
            for (IModule module : this.moduleList) {
                Assert.isNotEmpty((String)module.getCode());
                Assert.isNotEmpty((String)module.getName());
                if (this.moduleCacheMap.containsKey(module.getCode())) {
                    this.logger.error(String.format("\u91cd\u590d\u7684\u6a21\u5757\u6ce8\u518c\u9879 \u3010%1$s-%2$s\u3011", module.getCode(), module.getName()));
                    throw new IllegalArgumentException(String.format("\u91cd\u590d\u7684\u6a21\u5757\u6ce8\u518c\u9879 \u3010%1$s-%2$s\u3011", module.getCode(), module.getName()));
                }
                this.moduleCacheMap.put(module.getCode(), module);
                this.moduleCacheList.add(module);
            }
        }
    }

    private Comparator<IModule> getModuleComparator() {
        return new Comparator<IModule>(){

            @Override
            public int compare(IModule module1, IModule module2) {
                if (module1.getOrdinal() != module2.getOrdinal()) {
                    return module1.getOrdinal() - module2.getOrdinal();
                }
                return module1.getCode().compareTo(module2.getCode());
            }
        };
    }

    @Override
    public List<IModule> getModules() {
        return this.moduleCacheList;
    }

    @Override
    public IModule getModuleByCode(String code) {
        Assert.isNotEmpty((String)code);
        return this.moduleCacheMap.get(code);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.registerModule();
    }
}


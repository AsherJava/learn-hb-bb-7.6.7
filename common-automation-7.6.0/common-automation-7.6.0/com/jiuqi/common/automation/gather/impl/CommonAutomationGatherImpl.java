/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFolder
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 */
package com.jiuqi.common.automation.gather.impl;

import com.jiuqi.common.automation.gather.ICommonAutomationGather;
import com.jiuqi.common.automation.initializer.IAutomationInitializer;
import com.jiuqi.nvwa.framework.automation.api.AutomationFolder;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonAutomationGatherImpl
implements InitializingBean,
ICommonAutomationGather {
    private final Map<String, String> TYPE_CATEGORY_MAP = new ConcurrentHashMap<String, String>();
    private final Map<String, Map<String, AutomationFolder>> AUTOMATION_MAP = new ConcurrentHashMap<String, Map<String, AutomationFolder>>();
    private final Map<String, List<AutomationInstance>> AUTOMATION_INSTANCES_MAP = new ConcurrentHashMap<String, List<AutomationInstance>>();
    @Autowired(required=false)
    private List<IAutomationInitializer> automationInitializerList;

    private void init() {
        this.automationInitializerList.forEach(e -> e.init(this.TYPE_CATEGORY_MAP, this.AUTOMATION_MAP, this.AUTOMATION_INSTANCES_MAP));
    }

    @Override
    public List<AutomationFolder> getFolderList(String category) {
        return Optional.ofNullable(this.AUTOMATION_MAP.get(category)).map(e -> new ArrayList(e.values())).orElse(new ArrayList());
    }

    @Override
    public List<AutomationInstance> getAutomationInstanceList(String category) {
        return Optional.ofNullable(this.AUTOMATION_INSTANCES_MAP.get(category)).orElse(new ArrayList());
    }

    @Override
    public void afterPropertiesSet() {
        this.init();
    }
}


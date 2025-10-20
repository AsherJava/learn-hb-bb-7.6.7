/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gc.financial.status.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModuleQueryExecute;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IFinancialStatusModuleQueryExecuteGather
implements InitializingBean {
    private final Map<String, IFinancialStatusModuleQueryExecute> codeToExecuteMap = new ConcurrentHashMap<String, IFinancialStatusModuleQueryExecute>();
    @Autowired(required=false)
    private List<IFinancialStatusModuleQueryExecute> executeList;

    @Override
    public void afterPropertiesSet() {
        if (CollectionUtils.isEmpty(this.executeList)) {
            return;
        }
        this.codeToExecuteMap.clear();
        for (IFinancialStatusModuleQueryExecute plugin : this.executeList) {
            this.codeToExecuteMap.put(plugin.getExecuteName(), plugin);
        }
    }

    public IFinancialStatusModuleQueryExecute getPluginByExecuteName(String executeName) {
        return this.codeToExecuteMap.get(executeName);
    }
}


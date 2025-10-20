/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gc.financial.status.event.listening;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import com.jiuqi.gc.financial.status.event.FinancialStatusChangeEventData;
import com.jiuqi.gc.financial.status.event.FinancialStatusGroupChangeEvent;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModuleQueryExecute;
import com.jiuqi.gc.financial.status.service.impl.IFinancialStatusModuleGather;
import com.jiuqi.gc.financial.status.service.impl.IFinancialStatusModuleQueryExecuteGather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class FinancialStatusGroupChangedEventDispatcher
implements ApplicationListener<FinancialStatusGroupChangeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialStatusGroupChangeEvent.class);
    @Autowired
    private IFinancialStatusModuleQueryExecuteGather executeGather;
    @Autowired
    private IFinancialStatusModuleGather moduleGather;

    FinancialStatusGroupChangedEventDispatcher() {
    }

    @Override
    public void onApplicationEvent(FinancialStatusGroupChangeEvent event) {
        this.notifyChanged(event.getSource());
    }

    private void notifyChanged(FinancialStatusChangeEventData changeEventData) {
        if (StringUtils.isEmpty((String)changeEventData.getModuleCode())) {
            LOGGER.error("\u54cd\u5e94\u5f00\u5173\u8d26\u72b6\u6001\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\u3002\u4e8b\u4ef6\uff1a\u96c6\u56e2\u5f00\u5173\u8d26\u53d8\u66f4\uff0c\u53d8\u66f4\u53c2\u6570" + changeEventData);
            return;
        }
        IFinancialStatusModulePlugin pluginByModuleCode = this.moduleGather.getPluginByModuleCode(changeEventData.getModuleCode());
        if (pluginByModuleCode == null) {
            LOGGER.error("\u54cd\u5e94\u5f00\u5173\u8d26\u72b6\u6001\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\uff0c\u672a\u627e\u5230\u5bf9\u5e94\u7684\u6a21\u5757\u63d2\u4ef6\u3002\u4e8b\u4ef6\uff1a\u96c6\u56e2\u5f00\u5173\u8d26\u53d8\u66f4\uff0c\u53d8\u66f4\u53c2\u6570" + changeEventData);
            return;
        }
        IFinancialStatusModuleQueryExecute pluginByExecuteName = this.executeGather.getPluginByExecuteName(pluginByModuleCode.getFinancialStatusModuleQueryExecute());
        if (pluginByExecuteName == null) {
            LOGGER.error("\u54cd\u5e94\u5f00\u5173\u8d26\u72b6\u6001\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\uff0c\u672a\u627e\u5230\u5bf9\u5e94\u7684\u67e5\u8be2\u5904\u7406\u5668\u3002\u4e8b\u4ef6\uff1a\u96c6\u56e2\u5f00\u5173\u8d26\u53d8\u66f4\uff0c\u53d8\u66f4\u53c2\u6570" + changeEventData);
            return;
        }
        try {
            pluginByExecuteName.syncGroupCache(changeEventData);
        }
        catch (Exception e) {
            LOGGER.error("\u54cd\u5e94\u5f00\u5173\u8d26\u72b6\u6001\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\u3002\u4e8b\u4ef6\uff1a\u96c6\u56e2\u5f00\u5173\u8d26\u53d8\u66f4\uff0c\u53d8\u66f4\u53c2\u6570\uff1a" + changeEventData, e);
        }
        try {
            if (FinancialStatusEnum.OPEN.getCode().equals(changeEventData.getStatus())) {
                pluginByModuleCode.afterGroupOpen(changeEventData);
            }
        }
        catch (Exception e) {
            LOGGER.error("\u54cd\u5e94\u5f00\u5173\u8d26\u72b6\u6001\u53d8\u66f4\u4e8b\u4ef6\u5931\u8d25\u3002\u4e8b\u4ef6\uff1a\u96c6\u56e2\u5f00\u5173\u8d26\u53d8\u66f4\uff0c\u53d8\u66f4\u53c2\u6570\uff1a" + changeEventData, e);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nvwa.resourceview.category.IResourceCategoryApp
 *  com.jiuqi.nvwa.resourceview.plugin.GlobalCondition
 *  com.jiuqi.nvwa.resourceview.plugin.PluginInfo
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextHelper;
import com.jiuqi.nvwa.resourceview.category.IResourceCategoryApp;
import com.jiuqi.nvwa.resourceview.plugin.GlobalCondition;
import com.jiuqi.nvwa.resourceview.plugin.PluginInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class SummarySchemeManageApp
implements IResourceCategoryApp {
    public static final String APP_ID = "batch-summary";
    @Resource
    private BatchSummaryContextHelper contextHelper;

    public String getId() {
        return APP_ID;
    }

    public String getTitle() {
        return "\u6279\u91cf\u6c47\u603b\u65b9\u6848\u7ba1\u7406";
    }

    public String getIcon() {
        return null;
    }

    public double getOrder() {
        return 0.0;
    }

    public GlobalCondition getGlobalCondition() {
        GlobalCondition condition = new GlobalCondition();
        if (this.contextHelper.getContextData() == null) {
            condition.setDefaultValue(BatchSummaryUtils.toJSONStr((Object)this.contextHelper.initContextData()));
        }
        PluginInfo pluginInfo = new PluginInfo();
        pluginInfo.setProdLine("@nr");
        pluginInfo.setPluginName("batch-summary-plugin");
        pluginInfo.setPluginType("batch-summary-plugin");
        pluginInfo.setExpose("customCondition");
        condition.setPluginInfo(pluginInfo);
        return condition;
    }
}


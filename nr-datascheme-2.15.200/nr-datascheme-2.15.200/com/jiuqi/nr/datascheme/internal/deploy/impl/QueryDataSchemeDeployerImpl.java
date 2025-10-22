/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AbstractDataSchemeDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressUpdater;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.Set;
import java.util.function.Consumer;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class QueryDataSchemeDeployerImpl
extends AbstractDataSchemeDeployer {
    @Override
    public DataSchemeType[] getDataSchemeTypes() {
        return new DataSchemeType[]{DataSchemeType.QUERY};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void deployDataScheme(DeployContext context, DSProgressUpdater dsProgressUpdater) {
        try {
            String dataSchemeKey = context.getDataSchemeKey();
            this.runtimeDataSchemeManagerService.updateAllRuntimeDataScheme(dataSchemeKey);
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
            if (null == dataScheme) {
                this.runtimeDataSchemeManagerService.deleteDeployInfo(dataSchemeKey);
            }
        }
        catch (Exception e) {
            context.getDeployResult().setDeployState(DeployStatusEnum.FAIL);
            context.getDeployResult().setDeployMessage("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38\uff1a" + e.getMessage());
        }
        finally {
            LOGGER.info("\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff1a\u5237\u65b0\u6570\u636e\u65b9\u6848\u7f13\u5b58\uff01");
            RefreshCache refreshCache = new RefreshCache(true);
            this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
        }
    }

    @Override
    protected DSProgressUpdater getDSProgressUpdater(DeployContext context, Consumer<ProgressItem> progressConsumer) {
        ProgressItem progressItem = this.progressCacheService.getProgress(context.getDataSchemeKey());
        if (null == progressItem || progressItem.isFinished() || progressItem.isFailed()) {
            progressItem = new ProgressItem();
            progressItem.setProgressId(context.getDataSchemeKey());
            this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        }
        return new DSProgressUpdater(progressItem, progressConsumer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deployTables(DeployContext context, Set<String> keys, DSProgressUpdater progressUpdater) {
        try {
            for (String key : keys) {
                this.runtimeDataSchemeManagerService.updateAllRuntimeDataTable(key);
            }
            super.deployDataGroupByTables(context.getDataSchemeKey(), keys);
        }
        catch (Exception e) {
            context.getDeployResult().setDeployState(DeployStatusEnum.FAIL);
            context.getDeployResult().setDeployMessage("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38\uff1a" + e.getMessage());
        }
        finally {
            LOGGER.info("\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff1a\u5237\u65b0\u6570\u636e\u65b9\u6848\u7f13\u5b58\uff01");
            RefreshCache refreshCache = new RefreshCache(true);
            this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
        }
    }
}


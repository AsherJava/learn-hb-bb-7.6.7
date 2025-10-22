/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.datascheme.api.event.RefreshTable
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.datascheme.api.event.RefreshTable;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemePreDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.IDataSchemeDeployObjDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.IDataSchemeDeployObjGetter;
import com.jiuqi.nr.datascheme.internal.deploy.IDataTableDeployObjDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.IDataTableDeployObjGetter;
import com.jiuqi.nr.datascheme.internal.deploy.ITableModelDeployObjBuilder;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataSchemeDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AbstractDataSchemeDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressMessages;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressUpdater;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class NrDataSchemeDeployerImpl
extends AbstractDataSchemeDeployer {
    @Autowired
    private DataSchemePreDeployInfoDaoImpl dataSchemePreDeployInfoDao;
    @Autowired
    private IDataSchemeDeployObjGetter iSchemeInfoGetter;
    @Autowired
    private IDataTableDeployObjGetter iTableInfoGetter;
    @Autowired
    private IDataSchemeDeployObjDeployer iSchemeInfoDeployer;
    private final EnumMap<DataTableType, ITableModelDeployObjBuilder> iTableModelInfoBuilderMap = new EnumMap(DataTableType.class);
    private final EnumMap<DataTableType, IDataTableDeployObjDeployer> iTableInfoDeployerMap = new EnumMap(DataTableType.class);

    @Autowired
    public void setITableModelInfoBuilders(List<ITableModelDeployObjBuilder> iTableModelInfoBuilders) {
        for (ITableModelDeployObjBuilder iTableModelInfoBuilder : iTableModelInfoBuilders) {
            for (DataTableType tataTableType : iTableModelInfoBuilder.getDoForTableTypes()) {
                this.iTableModelInfoBuilderMap.put(tataTableType, iTableModelInfoBuilder);
            }
        }
    }

    @Autowired
    public void setITableInfoDeployer(List<IDataTableDeployObjDeployer> iTableInfoDeployers) {
        for (IDataTableDeployObjDeployer iTableInfoDeployer : iTableInfoDeployers) {
            for (DataTableType tataTableType : iTableInfoDeployer.getDoForTableTypes()) {
                this.iTableInfoDeployerMap.put(tataTableType, iTableInfoDeployer);
            }
        }
    }

    @Override
    public DataSchemeType[] getDataSchemeTypes() {
        return new DataSchemeType[]{DataSchemeType.NR};
    }

    @Override
    protected DSProgressUpdater getDSProgressUpdater(DeployContext context, Consumer<ProgressItem> progressConsumer) {
        ProgressItem progressItem = this.progressCacheService.getProgress(context.getDataSchemeKey());
        if (null == progressItem || progressItem.isFinished() || progressItem.isFailed()) {
            progressItem = new ProgressItem();
            progressItem.setProgressId(context.getDataSchemeKey());
            progressItem.addStepTitle("\u5206\u6790\u6570\u636e\u65b9\u6848");
            if (context.isCheck()) {
                progressItem.addStepTitle("\u6784\u5efa\u5b58\u50a8\u6a21\u578b\u5e76\u68c0\u67e5");
            } else {
                progressItem.addStepTitle("\u6784\u5efa\u5b58\u50a8\u6a21\u578b");
            }
            progressItem.addStepTitle("\u53d1\u5e03\u6570\u636e\u8868");
            progressItem.addStepTitle("\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        }
        return new DSProgressUpdater(progressItem, progressConsumer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void deployDataScheme(DeployContext context, DSProgressUpdater progressUpdater) {
        try {
            DataSchemeDeployObj dataCatalogDeployInfo = this.iSchemeInfoGetter.doGet(context.getDataSchemeKey());
            if (null == dataCatalogDeployInfo) {
                this.dataSchemeDeployStatusDao.delete(context.getDataSchemeKey());
                return;
            }
            if (context.isCheck() && DeployType.UPDATE == dataCatalogDeployInfo.getDimState() && this.runtimeDataSchemeService.dataSchemeCheckData(context.getDataSchemeKey())) {
                context.getDeployResult().setDeployState(DeployStatusEnum.FAIL);
                context.getDeployResult().setDeployMessage("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u516c\u5171\u7ef4\u5ea6\u53d1\u751f\u53d8\u5316\uff0c\u68c0\u6d4b\u5230\u6570\u636e\u65b9\u6848\u5df2\u7ecf\u5f55\u5165\u6570\u636e\uff0c\u8bf7\u6e05\u7a7a\u6570\u636e\u540e\u91cd\u8bd5\uff01");
                return;
            }
            this.deployTables(context, progressUpdater);
            if (!context.getDeployResult().isSuccess()) {
                return;
            }
            this.deployScheme(context, dataCatalogDeployInfo, progressUpdater);
        }
        catch (Exception e) {
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38", e);
            context.getDeployResult().setDeployState(DeployStatusEnum.FAIL);
            context.getDeployResult().setDeployMessage("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38\uff1a" + e.getMessage());
        }
        finally {
            this.refreshCache(context.getDataScheme(), true, context.getRefreshTableSet());
        }
    }

    private void deployScheme(DeployContext context, DataSchemeDeployObj dataCatalogDeployInfo, DSProgressUpdater progressUpdater) {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a \u53d1\u5e03\u6570\u636e\u65b9\u6848\u3002");
        progressUpdater.update(DSProgressMessages.DEPLOY_CATALOG.getMessage(), DSProgressMessages.DEPLOY_CATALOG.getCurrentProgress());
        this.iSchemeInfoDeployer.doDeploy(context, dataCatalogDeployInfo);
        if (context.getDeployResult().isSuccess()) {
            progressUpdater.nextStep();
        }
    }

    private void deployTables(DeployContext context, DSProgressUpdater progressUpdater) {
        progressUpdater.update(DSProgressMessages.ANALYSIS.getMessage(), DSProgressMessages.ANALYSIS.getCurrentProgress());
        Collection<DataTableDeployObj> dataTableInfos = this.iTableInfoGetter.doGet(context);
        if (!context.getDeployResult().getCheckState()) {
            return;
        }
        if (context.isPreDeploy()) {
            this.dataSchemePreDeployInfoDao.delete(context.getDataSchemeKey());
        }
        progressUpdater.nextStep();
        if (null == dataTableInfos || dataTableInfos.isEmpty()) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a \u6ca1\u6709\u68c0\u6d4b\u5230\u6570\u636e\u8868\u53d8\u5316\u3002");
            progressUpdater.nextStep();
        } else {
            Map<String, Collection<TableModelDeployObj>> tableModelDeployInfoMap = this.createTableModels(context, dataTableInfos, progressUpdater);
            if (!context.getDeployResult().getCheckState()) {
                return;
            }
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a \u53d1\u5e03\u6570\u636e\u8868\u3002");
            this.deployDataTable(context, dataTableInfos, tableModelDeployInfoMap, progressUpdater);
        }
        if (context.getDeployResult().isSuccess()) {
            progressUpdater.nextStep();
        }
    }

    private void deployDataTable(DeployContext context, Collection<DataTableDeployObj> dataTableInfos, Map<String, Collection<TableModelDeployObj>> tableModelDeployInfoMap, DSProgressUpdater progressUpdater) {
        for (DataTableDeployObj dataTableDeployInfo : dataTableInfos) {
            Collection<TableModelDeployObj> tableModelInfos = tableModelDeployInfoMap.get(dataTableDeployInfo.getDataTableKey());
            if (CollectionUtils.isEmpty(tableModelInfos)) continue;
            progressUpdater.update(String.format("%s:%s[%s]", DSProgressMessages.DEPLOY_DATATABLE.getMessage(), dataTableDeployInfo.getDataTable().getTitle(), dataTableDeployInfo.getDataTable().getCode()), DSProgressMessages.DEPLOY_DATATABLE.getCurrentProgress());
            this.deployDataTable(context, dataTableDeployInfo, tableModelInfos);
        }
    }

    private void deployDataTable(DeployContext context, DataTableDeployObj dataTableDeployInfo, Collection<TableModelDeployObj> tableModelInfos) {
        context.getRefreshTableSet().add(dataTableDeployInfo.getRefreshTable());
        DataTable dataTable = dataTableDeployInfo.getDataTable();
        IDataTableDeployObjDeployer iTableInfoDeployer = this.iTableInfoDeployerMap.get(dataTable.getDataTableType());
        if (null == iTableInfoDeployer) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u672a\u6ce8\u518c[{}]\u7684\u5b58\u50a8\u6a21\u578b\u53d1\u5e03\u5668\uff0c\u5c06\u4e0d\u4f1a\u53d1\u5e03\u8be5\u7c7b\u578b\u7684\u6570\u636e\u8868\u3002", (Object)dataTable.getDataTableType().getTitle());
            return;
        }
        if (null != tableModelInfos && !tableModelInfos.isEmpty()) {
            iTableInfoDeployer.doDeploy(context, dataTableDeployInfo, tableModelInfos);
        }
    }

    private Map<String, Collection<TableModelDeployObj>> createTableModels(DeployContext context, Collection<DataTableDeployObj> dataTableInfos, DSProgressUpdater progressUpdater) {
        if (context.isCheck()) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u6267\u884c\u53c2\u6570\u68c0\u67e5\u3002");
            progressUpdater.update(DSProgressMessages.INITNVWAMODEL_AND_CHECK.getMessage(), DSProgressMessages.INITNVWAMODEL_AND_CHECK.getCurrentProgress());
        } else {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a \u4e0d\u68c0\u67e5\u76f4\u63a5\u53d1\u5e03\u6570\u636e\u8868\u3002");
            progressUpdater.update(DSProgressMessages.INITNVWAMODEL.getMessage(), DSProgressMessages.INITNVWAMODEL.getCurrentProgress());
        }
        HashMap<String, Collection<TableModelDeployObj>> tableModelDeployInfoMap = new HashMap<String, Collection<TableModelDeployObj>>();
        for (DataTableDeployObj dataTableDeployInfo : dataTableInfos) {
            DataTable dataTable = dataTableDeployInfo.getDataTable();
            ITableModelDeployObjBuilder iTableModelInfoBuilder = this.iTableModelInfoBuilderMap.get(dataTable.getDataTableType());
            if (null == iTableModelInfoBuilder) {
                LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u672a\u6ce8\u518c[{}]\u7684\u5b58\u50a8\u6a21\u578b\u6784\u5efa\u5668\uff0c\u5c06\u4e0d\u4f1a\u53d1\u5e03\u8be5\u7c7b\u578b\u7684\u6570\u636e\u8868\u3002", (Object)dataTable.getDataTableType().getTitle());
                continue;
            }
            if (context.isCheck()) {
                progressUpdater.update(String.format("\u6b63\u5728\u6784\u5efa%s[%s]\u7684\u5b58\u50a8\u6a21\u578b\u5e76\u8fdb\u884c\u53d1\u5e03\u524d\u68c0\u67e5", dataTable.getTitle(), dataTable.getCode()), DSProgressMessages.INITNVWAMODEL_AND_CHECK.getCurrentProgress());
            } else {
                progressUpdater.update(String.format("\u6b63\u5728\u6784\u5efa%s[%s]\u7684\u5b58\u50a8\u6a21\u578b", dataTable.getTitle(), dataTable.getCode()), DSProgressMessages.INITNVWAMODEL.getCurrentProgress());
            }
            List<TableModelDeployObj> tableModelInfos = iTableModelInfoBuilder.doBuild(context, dataTableDeployInfo);
            tableModelDeployInfoMap.put(dataTableDeployInfo.getDataTableKey(), tableModelInfos);
        }
        if (context.getDeployResult().getCheckState()) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53c2\u6570\u68c0\u67e5\u901a\u8fc7\u3002");
            progressUpdater.nextStep();
        }
        return tableModelDeployInfoMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deployTables(DeployContext context, Set<String> dataTableKeys, DSProgressUpdater progressUpdater) {
        try {
            progressUpdater.update(DSProgressMessages.ANALYSIS.getMessage(), DSProgressMessages.ANALYSIS.getCurrentProgress());
            Collection<DataTableDeployObj> dataTableInfos = this.iTableInfoGetter.doGet(context, dataTableKeys);
            if (!context.getDeployResult().getCheckState()) {
                return;
            }
            progressUpdater.nextStep();
            if (null == dataTableInfos || dataTableInfos.isEmpty()) {
                LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a \u6ca1\u6709\u68c0\u6d4b\u5230\u6570\u636e\u8868\u53d8\u5316\u3002");
                progressUpdater.nextStep();
            } else {
                Map<String, Collection<TableModelDeployObj>> tableModelDeployInfoMap = this.createTableModels(context, dataTableInfos, progressUpdater);
                if (!context.getDeployResult().getCheckState()) {
                    return;
                }
                LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a \u53d1\u5e03\u6570\u636e\u8868\u3002");
                this.deployDataTable(context, dataTableInfos, tableModelDeployInfoMap, progressUpdater);
            }
            if (!context.getDeployResult().isSuccess()) {
                return;
            }
            progressUpdater.nextStep();
            progressUpdater.update(DSProgressMessages.DEPLOY_CATALOG.getMessage(), DSProgressMessages.DEPLOY_CATALOG.getCurrentProgress());
            for (String dataTableKey : dataTableKeys) {
                this.runtimeDataSchemeManagerService.updateRuntimeI18nByTable(dataTableKey);
            }
            super.deployDataGroupByTables(context.getDataSchemeKey(), dataTableKeys);
            progressUpdater.nextStep();
        }
        catch (Exception e) {
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38", e);
            context.getDeployResult().setDeployState(DeployStatusEnum.FAIL);
            context.getDeployResult().setDeployMessage("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38\uff1a" + e.getMessage());
        }
        finally {
            this.refreshCache(context.getDataScheme(), false, context.getRefreshTableSet());
        }
    }

    private void refreshCache(DataScheme dataScheme, boolean refreshAll, Set<RefreshTable> refreshTableSet) {
        RefreshCache refreshCache = new RefreshCache();
        HashMap<RefreshScheme, Set<RefreshTable>> refreshTable = new HashMap<RefreshScheme, Set<RefreshTable>>();
        refreshCache.setRefreshTable(refreshTable);
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u5237\u65b0\u6570\u636e\u65b9\u6848\u7f13\u5b58\uff01");
        RefreshScheme refreshScheme = new RefreshScheme(dataScheme.getKey(), dataScheme.getCode(), refreshAll);
        refreshTable.put(refreshScheme, refreshTableSet);
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
    }
}


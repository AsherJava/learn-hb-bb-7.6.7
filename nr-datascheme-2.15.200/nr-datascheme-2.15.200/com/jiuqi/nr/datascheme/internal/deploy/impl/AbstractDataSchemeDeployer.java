/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployResult$Result
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareEvent
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareSource
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.graph.IRWLockExecuterManager
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareEvent;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareSource;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeLoggerHelper;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.IDataSchemeDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressCacheService;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressUpdater;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.DataSchemeCacheService;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.StringUtils;

public abstract class AbstractDataSchemeDeployer
implements IDataSchemeDeployer {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataSchemeDeployer.class);
    @Autowired
    protected IDesignDataSchemeService designDataSchemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    protected RuntimeDataSchemeManagerService runtimeDataSchemeManagerService;
    @Autowired
    protected DataSchemeDeployStatusDaoImpl dataSchemeDeployStatusDao;
    @Autowired
    protected DSProgressCacheService progressCacheService;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    private IRWLockExecuterManager rwLockExecuterManager;

    @Override
    public void deployDataScheme(DeployContext context, Consumer<ProgressItem> progressConsumer) {
        DSProgressUpdater dsProgressUpdater = this.getDSProgressUpdater(context, progressConsumer);
        this.deployStart(context);
        if (!context.isPreDeploy()) {
            this.publishPrepareEvent(context.getDataScheme());
        }
        this.rwLockExecuterManager.getRWLockExecuter(DataSchemeCacheService.getLockName(context.getDataSchemeKey())).tryWrite(() -> {
            this.deployDataScheme(context, dsProgressUpdater);
            return null;
        });
        if (!context.getDeployResult().isSuccess()) {
            this.deployFail(context, dsProgressUpdater);
            return;
        }
        if (!context.isPreDeploy()) {
            try {
                this.publishSuccessEvent(context.getDataScheme());
            }
            catch (Exception e) {
                context.getDeployResult().setResult(DeployResult.Result.DEPLOY_WARN);
                context.getDeployResult().setMessage("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a" + e.getMessage());
                LOGGER.warn("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u8b66\u544a", e);
            }
        }
        this.deploySuccess(context, dsProgressUpdater);
    }

    protected abstract void deployDataScheme(DeployContext var1, DSProgressUpdater var2);

    protected abstract DSProgressUpdater getDSProgressUpdater(DeployContext var1, Consumer<ProgressItem> var2);

    private void deployStart(DeployContext context) {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u5f00\u59cb\uff0c\u68c0\u67e5\u6570\u636e\u65b9\u6848\u53d1\u5e03\u72b6\u6001\u3002");
        this.updateDeployStatus(context.getDataScheme(), DeployStatusEnum.DEPLOY, null);
    }

    private void deploySuccess(DeployContext context, DSProgressUpdater progressUpdater) {
        this.updateDeployStatus(context.getDataScheme(), DeployStatusEnum.SUCCESS, context.getDeployResult());
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6210\u529f\u3002");
        if (DeployResult.Result.DEPLOY_WARN == context.getDeployResult().getResult()) {
            progressUpdater.warn();
        } else {
            progressUpdater.end(true);
        }
    }

    private void deployFail(DeployContext context, DSProgressUpdater progressUpdater) {
        if (!context.getDeployResult().getCheckState()) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53c2\u6570\u68c0\u67e5\u672a\u901a\u8fc7\u3002");
        } else if (DeployStatusEnum.FAIL == context.getDeployResult().getDeployState()) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u6570\u636e\u8868\u53d1\u5e03\u8fc7\u7a0b\u5931\u8d25\u3002");
        } else {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u5931\u8d25\u3002");
        }
        this.updateDeployStatus(context.getDataScheme(), DeployStatusEnum.FAIL, context.getDeployResult());
        progressUpdater.end(false);
    }

    private void publishPrepareEvent(DataScheme dataScheme) {
        DataSchemeDeployPrepareSource source = new DataSchemeDeployPrepareSource(dataScheme);
        this.applicationContext.publishEvent((ApplicationEvent)new DataSchemeDeployPrepareEvent(source));
    }

    private void publishSuccessEvent(DataScheme dataScheme) {
        DataSchemeDeploySource source = new DataSchemeDeploySource(dataScheme);
        this.applicationContext.publishEvent((ApplicationEvent)new DataSchemeDeployEvent(source));
    }

    @Override
    public void deployDataTables(DeployContext context, Set<String> dataTableKeys, Consumer<ProgressItem> progressConsumer) {
        DSProgressUpdater progressUpdater = this.getDSProgressUpdater(context, progressConsumer);
        if (context.isCheck()) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(context.getDataSchemeKey());
            if (null == dataScheme) {
                context.getDeployResult().setResult(DeployResult.Result.CHECK_ERROR);
                context.getDeployResult().setMessage("\u6570\u636e\u65b9\u6848\u672a\u53d1\u5e03\uff0c\u8bf7\u53d1\u5e03\u6570\u636e\u65b9\u6848");
                return;
            }
            if (!Objects.equals(dataScheme.getPrefix(), context.getDataScheme().getPrefix())) {
                context.getDeployResult().setResult(DeployResult.Result.CHECK_ERROR);
                context.getDeployResult().setMessage("\u6570\u636e\u65b9\u6848\u524d\u7f00\u53d1\u751f\u53d8\u5316\uff0c\u8bf7\u53d1\u5e03\u6570\u636e\u65b9\u6848");
                return;
            }
            List run = this.runtimeDataSchemeService.getDataSchemeDimension(context.getDataSchemeKey()).stream().filter(d -> d.getDimensionType() != DimensionType.UNIT_SCOPE).map(DataDimension::getDimKey).collect(Collectors.toList());
            List des = this.designDataSchemeService.getDataSchemeDimension(context.getDataSchemeKey()).stream().filter(d -> d.getDimensionType() != DimensionType.UNIT_SCOPE).map(DataDimension::getDimKey).collect(Collectors.toList());
            run.removeAll(des);
            if (!run.isEmpty()) {
                context.getDeployResult().setResult(DeployResult.Result.CHECK_ERROR);
                context.getDeployResult().setMessage("\u6570\u636e\u65b9\u6848\u516c\u5171\u7ef4\u5ea6\u53d1\u751f\u53d8\u5316\uff0c\u8bf7\u53d1\u5e03\u6570\u636e\u65b9\u6848");
                return;
            }
        }
        this.rwLockExecuterManager.getRWLockExecuter(DataSchemeCacheService.getLockName(context.getDataSchemeKey())).doWrite(() -> {
            this.deployStart(context);
            this.deployTables(context, dataTableKeys, progressUpdater);
            if (!context.getDeployResult().isSuccess()) {
                this.deployFail(context, progressUpdater);
            } else {
                this.deploySuccess(context, progressUpdater);
            }
            return null;
        });
    }

    protected void deployDataGroupByTables(String dataSchemeKey, Set<String> dataTableKeys) {
        HashSet<String> keys = new HashSet<String>();
        Map groups = this.designDataSchemeService.getDataGroupByScheme(dataSchemeKey).stream().collect(Collectors.toMap(Grouped::getKey, Function.identity()));
        List dataTables = this.designDataSchemeService.getDataTables(new ArrayList<String>(dataTableKeys));
        LinkedList<DesignDataGroup> queue = new LinkedList<DesignDataGroup>();
        for (DesignDataTable dataTable : dataTables) {
            DesignDataGroup group = (DesignDataGroup)groups.get(dataTable.getDataGroupKey());
            if (null == group) continue;
            queue.add(group);
        }
        while (null != queue.peek()) {
            DesignDataGroup group = (DesignDataGroup)queue.poll();
            keys.add(group.getKey());
            if (null == (group = (DesignDataGroup)groups.get(group.getParentKey()))) continue;
            queue.add(group);
        }
        this.runtimeDataSchemeManagerService.updateRuntimeDataGroup(keys);
    }

    protected abstract void deployTables(DeployContext var1, Set<String> var2, DSProgressUpdater var3);

    private void updateDeployStatus(DataScheme dataScheme, DeployStatusEnum state, DeployResult deployResult) {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u66f4\u6539\u6570\u636e\u65b9\u6848\u53d1\u5e03\u72b6\u6001\u4e3a{}\u3002", (Object)state.getTitle());
        DataSchemeDeployStatusDO deployStatus = this.dataSchemeDeployStatusDao.getByDataSchemeKey(dataScheme.getKey());
        if (null == deployStatus) {
            deployStatus = new DataSchemeDeployStatusDO();
            deployStatus.setDataSchemeKey(dataScheme.getKey());
            deployStatus.setDeployStatus(state);
            deployStatus.setDeployResult(deployResult);
            deployStatus.setUpdateTime(Instant.now());
            this.dataSchemeDeployStatusDao.insert(deployStatus);
        } else {
            deployStatus.setDataSchemeKey(dataScheme.getKey());
            if (null != deployResult && DeployResult.Result.DEPLOY_WARN == deployResult.getResult()) {
                deployStatus.setDeployStatus(DeployStatusEnum.WARNING);
            } else {
                deployStatus.setDeployStatus(state);
            }
            deployStatus.setDeployResult(deployResult);
            deployStatus.setLastUpdateTime(deployStatus.getUpdateTime());
            deployStatus.setUpdateTime(Instant.now());
            this.dataSchemeDeployStatusDao.update(deployStatus);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("\u6570\u636e\u65b9\u6848\u4e3b\u952e\uff1a").append(dataScheme.getKey());
        builder.append("\uff0c\n\u6570\u636e\u65b9\u6848\u6807\u8bc6\uff1a").append(dataScheme.getCode());
        builder.append("\uff0c\n\u6570\u636e\u65b9\u6848\u6807\u9898\uff1a").append(dataScheme.getTitle());
        if (state == DeployStatusEnum.DEPLOY) {
            builder.append("\uff0c\n\u6570\u636e\u65b9\u6848\u53d1\u5e03\u72b6\u6001\uff1a\u53d1\u5e03\u5f00\u59cb\u3002");
            DataSchemeLoggerHelper.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f00\u59cb", builder.toString());
        } else {
            if (deployResult == null) {
                return;
            }
            String detail = deployResult.getDeployDetialMessage();
            detail = StringUtils.hasText(detail) ? "\n\u6570\u636e\u65b9\u6848\u53d1\u5e03\u8be6\u60c5\uff1a\n" + detail : "\n\u6570\u636e\u65b9\u6848\u53d1\u5e03\u8be6\u60c5\uff1a\u6ca1\u6709\u6570\u636e\u8868\u53d1\u5e03\u3002";
            DeployResult.Result result = deployResult.getResult();
            builder.append("\uff0c\n\u6570\u636e\u65b9\u6848\u53d1\u5e03\u72b6\u6001\uff1a");
            switch (result) {
                case SUCCESS: {
                    builder.append("\u53d1\u5e03\u6210\u529f\u3002").append(detail);
                    DataSchemeLoggerHelper.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u6210\u529f", builder.toString());
                    break;
                }
                case DEPLOY_ERROR: {
                    builder.append("\u53d1\u5e03\u5931\u8d25\u3002").append(detail);
                    DataSchemeLoggerHelper.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5931\u8d25", builder.toString());
                    break;
                }
                case DEPLOY_WARN: {
                    builder.append("\u53d1\u5e03\u8b66\u544a\u3002").append(detail);
                    DataSchemeLoggerHelper.warn("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u8b66\u544a", builder.toString());
                    break;
                }
                case CHECK_ERROR: {
                    builder.append("\u68c0\u67e5\u5931\u8d25\u3002").append(detail);
                    DataSchemeLoggerHelper.warn("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5931\u8d25", builder.toString());
                    break;
                }
                default: {
                    builder.append("\u53d1\u5e03\u7ed3\u675f\u3002").append(detail);
                    DataSchemeLoggerHelper.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u7ed3\u675f", builder.toString());
                }
            }
        }
    }
}


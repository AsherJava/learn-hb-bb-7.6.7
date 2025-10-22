/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.core.DeployResult$Result
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeListener
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDeployCallback
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  com.jiuqi.nr.graph.exception.RWLockExecuterException
 *  com.jiuqi.nr.graph.rwlock.executer.DatabaseLock
 *  com.jiuqi.nvwa.definition.common.ProgressItem
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.IndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.TableCheckDao
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.event.DataSchemeListener;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDeployCallback;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.common.DataSchemeDeployErrorEnum;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemePreDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.IDataSchemeDeployObjDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.IDataSchemeDeployObjGetter;
import com.jiuqi.nr.datascheme.internal.deploy.IDataSchemeDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataSchemeDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.progress.DSProgressCacheService;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDesignDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO;
import com.jiuqi.nr.datascheme.internal.entity.PreDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.DataSchemeCacheService;
import com.jiuqi.nr.graph.exception.RWLockExecuterException;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import com.jiuqi.nvwa.definition.common.ProgressItem;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.IndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.TableCheckDao;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DataSchemeDeployServiceImpl
implements IDataSchemeDeployService,
DataSchemeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSchemeDeployServiceImpl.class);
    @Autowired
    private RuntimeDataSchemeManagerService runtimeDataSchemeManagerService;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private TableCheckDao tableCheckDao;
    @Autowired
    private DataSchemePreDeployInfoDaoImpl dataSchemePreDeployInfoDao;
    private final EnumMap<DataSchemeType, IDataSchemeDeployer> iDataSchemeDeployerMap = new EnumMap(DataSchemeType.class);
    @Autowired
    protected DataSchemeDeployStatusDaoImpl dataSchemeDeployStatusDao;
    @Autowired
    protected DSProgressCacheService progressCacheService;
    @Autowired
    private IDataSchemeDeployObjDeployer iSchemeInfoDeployer;
    @Autowired
    private IDataSchemeDeployObjGetter iSchemeInfoGetter;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private DatabaseLock databaseLock;

    @Autowired
    public void setITableInfoDeployer(List<IDataSchemeDeployer> iDataSchemeDeployers) {
        for (IDataSchemeDeployer iDataSchemeDeployer : iDataSchemeDeployers) {
            for (DataSchemeType dataSchemeType : iDataSchemeDeployer.getDataSchemeTypes()) {
                this.iDataSchemeDeployerMap.put(dataSchemeType, iDataSchemeDeployer);
            }
        }
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public DeployResult unsafeDeployDataScheme(String dataSchemeKey, Consumer<ProgressItem> progressConsumer, IDeployCallback callback) {
        DeployContext context = this.createDeployContext(dataSchemeKey, false, false);
        this.deployDataScheme(context, progressConsumer, callback);
        return context.getDeployResult();
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public DeployResult deployDataScheme(String dataSchemeKey, Consumer<ProgressItem> progressConsumer, IDeployCallback callback) {
        DeployContext context = this.createDeployContext(dataSchemeKey, true, false);
        this.deployDataScheme(context, progressConsumer, callback);
        return context.getDeployResult();
    }

    private void deployDataScheme(DeployContext context, Consumer<ProgressItem> progressConsumer, IDeployCallback callback) {
        DataSchemeDeployStatusDO deployStatus = this.dataSchemeDeployStatusDao.getByDataSchemeKey(context.getDataSchemeKey());
        if (null != deployStatus && DeployStatusEnum.DEPLOY == deployStatus.getDeployStatus()) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u6570\u636e\u65b9\u6848\u6b63\u5728\u53d1\u5e03\u4e2d\uff0c\u65e0\u9700\u91cd\u590d\u53d1\u5e03\uff0c\u7ed3\u675f\u3002");
            context.getDeployResult().setDeployState(DeployStatusEnum.DEPLOY);
            context.getDeployResult().setDeployMessage("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u4e2d\uff01");
            return;
        }
        try {
            IDataSchemeDeployer iDataSchemeDeployer = this.iDataSchemeDeployerMap.get(context.getDataSchemeType());
            if (null == iDataSchemeDeployer) {
                LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u672a\u627e\u5230[{}]\u7684\u53d1\u5e03\u5668\uff0c\u672a\u80fd\u6b63\u5e38\u53d1\u5e03\u3002", (Object)context.getDataSchemeType());
                context.getDeployResult().setDeployState(DeployStatusEnum.FAIL);
                context.getDeployResult().setDeployMessage(String.format("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u672a\u627e\u5230[%s]\u7684\u53d1\u5e03\u5668\uff0c\u672a\u80fd\u6b63\u5e38\u53d1\u5e03\u3002", context.getDataSchemeType()));
                return;
            }
            iDataSchemeDeployer.deployDataScheme(context, progressConsumer);
        }
        catch (RWLockExecuterException ee) {
            throw ee;
        }
        catch (Exception e) {
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38", e);
            context.getDeployResult().setDeployState(DeployStatusEnum.FAIL);
            context.getDeployResult().setDeployMessage(e.getMessage());
        }
        if (null != callback) {
            try {
                callback.run(context.getDeployResult());
            }
            catch (Exception e) {
                LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u56de\u8c03\u4e8b\u4ef6\u6267\u884c\u5931\u8d25", e);
            }
        }
    }

    private DeployContext createDeployContext(String dataSchemeKey, boolean check, boolean preDeploy) {
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        if (null == dataScheme) {
            dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        }
        if (null == dataScheme) {
            DataSchemeDesignDTO scheme = new DataSchemeDesignDTO();
            scheme.setKey(dataSchemeKey);
            scheme.setType(DataSchemeType.NR);
            dataScheme = scheme;
        }
        return this.createDeployContext((DataScheme)dataScheme, check, preDeploy);
    }

    private DeployContext createDeployContext(DataScheme dataScheme, boolean check, boolean preDeploy) {
        DeployContext deployContext = new DeployContext(dataScheme, check, preDeploy);
        deployContext.setExistData(tableName -> {
            try {
                return this.tableCheckDao.checkTableExistData(tableName);
            }
            catch (Exception exception) {
                return false;
            }
        });
        return deployContext;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void deployDataTable(String dataTableKey, boolean doCheck) throws JQException {
        String dataSchemeKey;
        DeployContext context;
        IDataSchemeDeployer iDataSchemeDeployer;
        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(dataTableKey);
        if (null == dataTable) {
            dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
        }
        if (null == (iDataSchemeDeployer = this.iDataSchemeDeployerMap.get((context = this.createDeployContext(dataSchemeKey = dataTable.getDataSchemeKey(), doCheck, false)).getDataSchemeType()))) {
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u672a\u627e\u5230[{}]\u7684\u53d1\u5e03\u5668\uff0c\u672a\u80fd\u6b63\u5e38\u53d1\u5e03\u3002", (Object)context.getDataSchemeType());
            throw new JQException((ErrorEnum)DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_FAIL);
        }
        iDataSchemeDeployer.deployDataTables(context, Collections.singleton(dataTableKey), item -> {});
    }

    public DeployResult deployDataTable(String dataSchemeKey, Set<String> dataTableKeys, boolean check, Consumer<ProgressItem> progressConsumer) {
        DeployContext context = this.createDeployContext(dataSchemeKey, check, false);
        try {
            IDataSchemeDeployer iDataSchemeDeployer = this.iDataSchemeDeployerMap.get(context.getDataSchemeType());
            if (null == iDataSchemeDeployer) {
                LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u672a\u627e\u5230[{}]\u7684\u53d1\u5e03\u5668\uff0c\u672a\u80fd\u6b63\u5e38\u53d1\u5e03\u3002", (Object)context.getDataSchemeType());
                context.getDeployResult().setResult(DeployResult.Result.CHECK_ERROR);
                context.getDeployResult().setMessage(String.format("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u672a\u627e\u5230[%s]\u7684\u53d1\u5e03\u5668\uff0c\u672a\u80fd\u6b63\u5e38\u53d1\u5e03\u3002", context.getDataSchemeType()));
                return context.getDeployResult();
            }
            iDataSchemeDeployer.deployDataTables(context, dataTableKeys, progressConsumer);
        }
        catch (RWLockExecuterException ee) {
            throw ee;
        }
        catch (Exception e) {
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\u5f02\u5e38", e);
            context.getDeployResult().setResult(DeployResult.Result.DEPLOY_ERROR);
            context.getDeployResult().setMessage(e.getMessage());
        }
        return context.getDeployResult();
    }

    public void savePostProcess(DesignDataScheme dataScheme, List<DesignDataDimension> add) {
    }

    public void updatePostProcess(DesignDataScheme srcDataScheme, DesignDataScheme dataScheme, List<DesignDataDimension> add, List<DesignDataDimension> delete) {
    }

    public void deletePostProcess(DesignDataScheme dataScheme) {
        DeployContext context = this.createDeployContext((DataScheme)dataScheme, false, false);
        this.deployDataScheme(context, (ProgressItem item) -> {}, null);
    }

    public void fixDeployStatus() {
        try {
            List<DataSchemeDeployStatusDO> status = this.dataSchemeDeployStatusDao.getByStatus(DeployStatusEnum.DEPLOY);
            if (CollectionUtils.isEmpty(status)) {
                return;
            }
            new Thread(() -> {
                try {
                    Thread.sleep(60000L);
                }
                catch (InterruptedException e) {
                    LOGGER.error("\u4fee\u590d\u6570\u636e\u65b9\u6848\u53d1\u5e03\u72b6\u6001\u5931\u8d25", e);
                    Thread.currentThread().interrupt();
                }
                List<DataSchemeDeployStatusDO> deployStatus = this.dataSchemeDeployStatusDao.getByStatus(DeployStatusEnum.DEPLOY);
                for (int i = deployStatus.size() - 1; i == 0; --i) {
                    DataSchemeDeployStatusDO item = deployStatus.get(i);
                    boolean locked = this.databaseLock.isLocked(DataSchemeCacheService.getLockName(item.getDataSchemeKey()));
                    if (locked) {
                        deployStatus.remove(i);
                        continue;
                    }
                    item.setDeployStatus(DeployStatusEnum.FAIL);
                    this.progressCacheService.removeProgress(item.getDataSchemeKey());
                }
                if (CollectionUtils.isEmpty(deployStatus)) {
                    return;
                }
                this.dataSchemeDeployStatusDao.update(deployStatus.toArray());
            }).start();
        }
        catch (Exception e) {
            LOGGER.error("\u4fee\u590d\u6570\u636e\u65b9\u6848\u53d1\u5e03\u72b6\u6001\u5931\u8d25", e);
        }
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
    public List<String> preDeployDataScheme(String dataSchemeKey, boolean doCheck, Consumer<ProgressItem> progressConsumer) throws JQException {
        DeployContext context = this.createDeployContext(dataSchemeKey, true, true);
        this.deployDataScheme(context, progressConsumer, null);
        if (!context.getDeployResult().getCheckState() || DeployStatusEnum.FAIL == context.getDeployResult().getDeployState()) {
            throw new JQException((ErrorEnum)DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_FAIL, context.getDeployResult().getDeployErrorMessage());
        }
        return context.getDdlSqls();
    }

    public void deployTableModel(String dataSchemeKey) throws JQException {
        TransactionStatus transaction = null;
        List<PreDeployInfoDO> list = this.dataSchemePreDeployInfoDao.list(dataSchemeKey);
        try {
            transaction = this.getTransactionStatus();
            for (PreDeployInfoDO info : list) {
                Set<DataFieldDeployInfoDO> deleteDeployInfos;
                Set<DataFieldDeployInfoDO> updateDeployInfos;
                DesignColumnModelDefine[] array;
                PreDeployInfoDO.PreDeployDetails dataTableInfo = info.getDeployDetails();
                for (Map.Entry<String, DesignTableModelDefine> entry : dataTableInfo.getTableModels().entrySet()) {
                    if (null == entry.getValue()) {
                        this.designDataModelService.deleteTableModelDefine(entry.getKey());
                        this.designDataModelService.deleteColumnModelDefineByTable(entry.getKey());
                        this.designDataModelService.deleteIndexsByTable(entry.getKey());
                        continue;
                    }
                    DesignTableModelDefine table = this.designDataModelService.getTableModelDefine(entry.getKey());
                    if (null == table) {
                        this.designDataModelService.insertTableModelDefine(entry.getValue());
                        continue;
                    }
                    this.designDataModelService.updateTableModelDefine(entry.getValue());
                }
                if (!CollectionUtils.isEmpty(dataTableInfo.getAddColumns())) {
                    int size = dataTableInfo.getAddColumns().size();
                    array = dataTableInfo.getAddColumns().toArray(new DesignColumnModelDefine[size]);
                    this.designDataModelService.insertColumnModelDefines(array);
                }
                if (!CollectionUtils.isEmpty(dataTableInfo.getUpdateColumns())) {
                    int size = dataTableInfo.getUpdateColumns().size();
                    array = dataTableInfo.getUpdateColumns().toArray(new DesignColumnModelDefine[size]);
                    this.designDataModelService.updateColumnModelDefines(array);
                }
                if (!CollectionUtils.isEmpty(dataTableInfo.getDeleteColumns())) {
                    String[] array2 = (String[])dataTableInfo.getDeleteColumns().stream().map(IModelDefineItem::getID).toArray(String[]::new);
                    this.designDataModelService.deleteColumnModelDefines(array2);
                }
                if (!CollectionUtils.isEmpty(dataTableInfo.getAddIndexs())) {
                    for (DesignIndexModelDefine index : dataTableInfo.getAddIndexs()) {
                        String fieldIDs = index.getFieldIDs();
                        if (!StringUtils.hasText(fieldIDs)) continue;
                        this.designDataModelService.addIndexToTable(index.getTableID(), fieldIDs.split(";"), index.getName(), index.getType());
                    }
                }
                if (!CollectionUtils.isEmpty(dataTableInfo.getUpdateIndexs())) {
                    int size = dataTableInfo.getUpdateIndexs().size();
                    array = dataTableInfo.getUpdateIndexs().toArray(new DesignIndexModelDefine[size]);
                    this.designDataModelService.updateIndexModelDefines((DesignIndexModelDefine[])array);
                }
                if (!CollectionUtils.isEmpty(dataTableInfo.getDeleteIndexs())) {
                    String[] array3 = (String[])dataTableInfo.getDeleteIndexs().stream().map(IndexModelDefine::getID).toArray(String[]::new);
                    this.designDataModelService.deleteIndexModelDefines(array3);
                }
                for (Map.Entry<String, DesignTableModelDefine> entry : dataTableInfo.getTableModels().entrySet()) {
                    this.dataModelRegisterService.registerTable(entry.getKey());
                }
                if (PreDeployInfoDO.PreDeployType.DELETE == info.getType()) {
                    this.runtimeDataSchemeManagerService.deleteDeployInfoByTable(info.getDataTableKey());
                    this.runtimeDataSchemeManagerService.deleteRuntimeDataFieldByTable(info.getDataTableKey());
                    this.runtimeDataSchemeManagerService.deleteRuntimeDataTable(info.getDataTableKey());
                    this.runtimeDataSchemeManagerService.deleteRuntimeDataTableRel(info.getDataTableKey());
                    continue;
                }
                Set<DataFieldDeployInfoDO> addDeployInfos = dataTableInfo.getAddDeployInfos();
                if (!CollectionUtils.isEmpty(addDeployInfos)) {
                    this.dataFieldDeployInfoDao.insert(addDeployInfos.toArray(new DataFieldDeployInfoDO[0]));
                }
                if (!CollectionUtils.isEmpty(updateDeployInfos = dataTableInfo.getUpdateDeployInfos())) {
                    this.dataFieldDeployInfoDao.update(updateDeployInfos.toArray(new DataFieldDeployInfoDO[0]));
                }
                if (!CollectionUtils.isEmpty(deleteDeployInfos = dataTableInfo.getDeleteDeployInfos())) {
                    this.dataFieldDeployInfoDao.delete(deleteDeployInfos.toArray(new DataFieldDeployInfoDO[0]));
                }
                this.runtimeDataSchemeManagerService.updateRuntimeDataFieldByTable(info.getDataTableKey());
                this.runtimeDataSchemeManagerService.updateRuntimeDataTable(info.getDataTableKey());
                this.runtimeDataSchemeManagerService.updateRuntimeDataTableRel(info.getDataTableKey());
            }
            DataSchemeDeployObj dataCatalogDeployInfo = this.iSchemeInfoGetter.doGet(dataSchemeKey);
            this.iSchemeInfoDeployer.doDeploy(this.createDeployContext(dataSchemeKey, false, false), dataCatalogDeployInfo);
            this.dataSchemePreDeployInfoDao.delete(dataSchemeKey);
            this.platformTransactionManager.commit(transaction);
        }
        catch (Exception e) {
            if (null != transaction) {
                this.platformTransactionManager.rollback(transaction);
            }
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u540c\u6b65\u5973\u5a32\u53c2\u6570\u6a21\u578b\u5931\u8d25\uff01", e);
            throw new JQException((ErrorEnum)DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_FAIL, (Throwable)e);
        }
        this.refreshCache(dataSchemeKey);
    }

    private TransactionStatus getTransactionStatus() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(3);
        return this.platformTransactionManager.getTransaction((TransactionDefinition)def);
    }

    private void refreshCache(String dataSchemeKey) {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u5237\u65b0\u6570\u636e\u65b9\u6848\u7f13\u5b58\uff01");
        RefreshCache refreshCache = new RefreshCache();
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        if (null == dataScheme) {
            dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        }
        if (null != dataScheme) {
            HashMap refreshTable = new HashMap();
            RefreshScheme refreshScheme = new RefreshScheme(dataScheme.getKey(), dataScheme.getCode());
            refreshTable.put(refreshScheme, Collections.emptySet());
            refreshCache.setRefreshTable(refreshTable);
        } else {
            refreshCache.setRefreshAll(true);
        }
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeploySource
 *  com.jiuqi.nr.datascheme.api.event.DataTableDeploySource$Status
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nvwa.definition.common.exception.DeployTableException
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.exception.IgnoreRollBackException
 *  com.jiuqi.nvwa.definition.exception.IndexDeployException
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableType
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.event.DataTableDeployEvent;
import com.jiuqi.nr.datascheme.api.event.DataTableDeploySource;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemePreDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.IDataTableDeployObjDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.PreDeployInfoDO;
import com.jiuqi.nvwa.definition.common.exception.DeployTableException;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.exception.IgnoreRollBackException;
import com.jiuqi.nvwa.definition.exception.IndexDeployException;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableType;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

public abstract class AbstractDataTableDeployObjDeployer
implements IDataTableDeployObjDeployer {
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    private DataSchemePreDeployInfoDaoImpl dataSchemePreDeployInfoDao;
    @Autowired
    protected RuntimeDataSchemeManagerService runtimeDataSchemeManager;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    protected ApplicationContext applicationContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataTableDeployObjDeployer.class);

    @Override
    public void doDeploy(DeployContext context, DataTableDeployObj dataTableInfo, Collection<TableModelDeployObj> tableModelInfos) {
        if (context.isPreDeploy()) {
            this.preDeploy(context, dataTableInfo, tableModelInfos);
        } else {
            this.deploy(context, dataTableInfo, tableModelInfos);
        }
    }

    private void deploy(DeployContext context, DataTableDeployObj dataTableInfo, Collection<TableModelDeployObj> tableModelInfos) {
        DataTable dataTable = dataTableInfo.getDataTable();
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6570\u636e\u8868{}[{}], \u5305\u542b{}\u4e2a\u7269\u7406\u8868\u3002", dataTable.getTitle(), dataTable.getCode(), tableModelInfos.size());
        boolean needDeployTable = false;
        ArrayList<String> errorMsgs = new ArrayList<String>();
        ArrayList<String> warningMsgs = new ArrayList<String>();
        for (TableModelDeployObj tableObj : tableModelInfos) {
            try {
                this.saveTable(tableObj);
            }
            catch (Exception e) {
                this.saveTableError(dataTable, tableObj, e, errorMsgs);
                continue;
            }
            boolean executedDDL = false;
            try {
                executedDDL = this.deployTable(context, tableObj);
            }
            catch (IgnoreRollBackException e) {
                AbstractDataTableDeployObjDeployer.deployTableWarning(dataTable, tableObj, e, warningMsgs);
            }
            catch (Exception e) {
                this.deployTableError(dataTable, tableObj, e, errorMsgs);
                continue;
            }
            this.deployDataField(dataTableInfo, tableObj);
            this.publishSuccessEvent(dataTableInfo, tableObj, executedDDL);
            needDeployTable = true;
        }
        if (needDeployTable) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6570\u636e\u8868");
            this.deployDataTable(dataTableInfo);
        }
        context.getDeployResult().addDeployDetail(dataTable, errorMsgs, warningMsgs);
    }

    private static void deployTableWarning(DataTable dataTable, TableModelDeployObj tableObj, IgnoreRollBackException e, List<String> errorMsgs) {
        LOGGER.warn("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6570\u636e\u8868{}[{}]\u7684\u5b58\u50a8\u6a21\u578b[{}]\u5f02\u5e38\uff1a", new Object[]{dataTable.getTitle(), dataTable.getCode(), tableObj.getTableModelCode(), e});
        List indexExceptions = e.getIndexExceptions();
        for (IndexDeployException indexException : indexExceptions) {
            errorMsgs.add(String.format("\u53d1\u5e03\u6570\u636e\u8868%s[%s]\u7684\u5b58\u50a8\u6a21\u578b[%s]\u5f02\u5e38\uff1a%s\u3002", dataTable.getTitle(), dataTable.getCode(), tableObj.getTableModelCode(), indexException.getMessage()));
            LOGGER.warn("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6570\u636e\u8868{}[{}]\u7684\u5b58\u50a8\u6a21\u578b[{}]\u5f02\u5e38\uff1a", dataTable.getTitle(), dataTable.getCode(), tableObj.getTableModelCode(), indexException);
        }
    }

    private void deployTableError(DataTable dataTable, TableModelDeployObj tableObj, Exception e, List<String> errorMsgs) {
        String errorMsg = e instanceof DeployTableException ? e.getLocalizedMessage() : e.getMessage();
        errorMsgs.add(String.format("\u53d1\u5e03\u6570\u636e\u8868%s[%s]\u7684\u5b58\u50a8\u6a21\u578b[%s]\u5931\u8d25\uff1a%s\u3002", dataTable.getTitle(), dataTable.getCode(), tableObj.getTableModelCode(), errorMsg));
        LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6570\u636e\u8868{}[{}]\u7684\u5b58\u50a8\u6a21\u578b[{}]\u5931\u8d25\u3002", dataTable.getTitle(), dataTable.getCode(), tableObj.getTableModelCode(), e);
    }

    private void saveTableError(DataTable dataTable, TableModelDeployObj tableObj, Exception e, List<String> errorMsgs) {
        String errorMsg = e instanceof ModelValidateException ? e.getLocalizedMessage() : e.getMessage();
        errorMsgs.add(String.format("\u66f4\u65b0\u6570\u636e\u8868%s[%s]\u7684\u5b58\u50a8\u6a21\u578b[%s]\u5931\u8d25\uff1a%s\u3002", dataTable.getTitle(), dataTable.getCode(), tableObj.getTableModelCode(), errorMsg));
        LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u66f4\u65b0\u6570\u636e\u8868{}[{}]\u7684\u5b58\u50a8\u6a21\u578b[{}]\u5931\u8d25\u3002", dataTable.getTitle(), dataTable.getCode(), tableObj.getTableModelCode(), e);
    }

    private void saveTable(TableModelDeployObj tableObj) throws ModelValidateException {
        if (DeployTableType.DELETE == tableObj.getState()) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u5220\u9664NVWA\u5b58\u50a8\u6a21\u578b{}", (Object)tableObj.getTableModelCode());
            this.designDataModelService.deleteIndexsByTable(tableObj.getTableModelKey());
            this.designDataModelService.deleteColumnModelDefineByTable(tableObj.getTableModelKey());
            this.designDataModelService.deleteTableModelDefine(tableObj.getTableModelKey());
        } else {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u4fdd\u5b58NVWA\u5b58\u50a8\u6a21\u578b{}", (Object)tableObj.getTableModelCode());
            this.designDataModelService.saveTableModel(tableObj.getDesignTableModel());
        }
    }

    protected boolean deployTable(DeployContext context, TableModelDeployObj tableObj) throws Exception {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03NVWA\u5b58\u50a8\u6a21\u578b{}", (Object)tableObj.getTableModelCode());
        if (context.isCheck()) {
            return this.dataModelDeployService.deployTableModel(tableObj.getTableModelKey());
        }
        return this.dataModelDeployService.deployTableModelUnCheck(tableObj.getTableModelKey());
    }

    protected void deployDataTable(DataTableDeployObj dataTableInfo) {
        this.designDataSchemeService.refreshDataTableUpdateTime(dataTableInfo.getDataTableKey());
        this.runtimeDataSchemeManager.updateRuntimeDataTable(dataTableInfo.getDataTableKey());
        List<DataField> updateNodployDataFields = dataTableInfo.getUpdateNodployDataFields();
        Set<String> updateNodeployFieldKeys = null;
        if (null != updateNodployDataFields && !updateNodployDataFields.isEmpty()) {
            updateNodeployFieldKeys = updateNodployDataFields.stream().map(Basic::getKey).collect(Collectors.toSet());
            this.runtimeDataSchemeManager.updateRuntimeDataFields(updateNodeployFieldKeys);
        }
        ArrayList<DataFieldDeployInfoDO> updateDeployInfo = new ArrayList<DataFieldDeployInfoDO>();
        if (null != updateNodeployFieldKeys && !updateNodeployFieldKeys.isEmpty()) {
            for (String fieldKey : updateNodeployFieldKeys) {
                for (DataFieldDeployInfoDO deployInfo : dataTableInfo.getDeployInfoByFieldKey(fieldKey)) {
                    if (dataTableInfo.getDataTableKey().equals(deployInfo.getDataTableKey())) continue;
                    deployInfo.setDataTableKey(dataTableInfo.getDataTableKey());
                    updateDeployInfo.add(deployInfo);
                }
            }
        }
        if (!updateDeployInfo.isEmpty()) {
            this.dataFieldDeployInfoDao.update(updateDeployInfo.toArray(new DataFieldDeployInfoDO[0]));
        }
    }

    private void deployDataField(DataTableDeployObj dataTableInfo, TableModelDeployObj tableModelInfo) {
        Collection<DataFieldDeployInfoDO> addDeployInfos;
        Collection<DataFieldDeployInfoDO> updateDeployInfos;
        Collection<DataFieldDeployInfoDO> deleteDeployInfos;
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6307\u6807/\u5b57\u6bb5");
        Set<String> dirtyDataFieldKeys = tableModelInfo.getDirtyDataFieldKeys();
        if (!dirtyDataFieldKeys.isEmpty()) {
            this.runtimeDataSchemeManager.updateRuntimeDataFields(dirtyDataFieldKeys);
        }
        if (!(deleteDeployInfos = tableModelInfo.getDeleteDeployInfos()).isEmpty()) {
            this.dataFieldDeployInfoDao.delete(deleteDeployInfos.toArray(new DataFieldDeployInfoDO[0]));
        }
        if (!(updateDeployInfos = tableModelInfo.getUpdateDeployInfos()).isEmpty()) {
            this.dataFieldDeployInfoDao.update(updateDeployInfos.toArray(new DataFieldDeployInfoDO[0]));
        }
        if (!(addDeployInfos = tableModelInfo.getAddDeployInfos()).isEmpty()) {
            this.dataFieldDeployInfoDao.insert(addDeployInfos.toArray(new DataFieldDeployInfoDO[0]));
        }
        if (dataTableInfo.isCodeChanged()) {
            ArrayList<DataFieldDeployInfoDO> updateTableNameInfos = new ArrayList<DataFieldDeployInfoDO>();
            List<DataFieldDeployInfoDO> infos = dataTableInfo.getDeployInfoByTableModelKey(tableModelInfo.getTableModelKey());
            for (DataFieldDeployInfoDO info : infos) {
                if (dirtyDataFieldKeys.contains(info.getDataFieldKey())) continue;
                info.setTableName(tableModelInfo.getTableModelName());
                updateTableNameInfos.add(info);
            }
            if (!updateTableNameInfos.isEmpty()) {
                this.dataFieldDeployInfoDao.update(updateTableNameInfos.toArray(new DataFieldDeployInfoDO[0]));
            }
        }
    }

    private void publishSuccessEvent(DataTableDeployObj dataTable, TableModelDeployObj tableObj, boolean executedDDL) {
        DataTableDeploySource.Status status = DataTableDeploySource.Status.UPDATE_NO_DDL;
        if (DeployTableType.ADD == tableObj.getState()) {
            status = DataTableDeploySource.Status.ADD;
        } else if (DeployTableType.DELETE == tableObj.getState()) {
            status = DataTableDeploySource.Status.DELETE;
        } else if (executedDDL) {
            status = DataTableDeploySource.Status.UPDATE;
        }
        DataTableDeploySource source = new DataTableDeploySource(dataTable.getDataTable(), tableObj.getDesignTableModel(), status, dataTable.isCodeChanged());
        this.applicationContext.publishEvent((ApplicationEvent)new DataTableDeployEvent(source));
    }

    private void preDeploy(DeployContext context, DataTableDeployObj dataTableInfo, Collection<TableModelDeployObj> tableModelInfos) {
        DataTable dataTable = dataTableInfo.getDataTable();
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6570\u636e\u8868{}[{}], \u5305\u542b{}\u4e2a\u7269\u7406\u8868\u3002", dataTable.getTitle(), dataTable.getCode(), tableModelInfos.size());
        ArrayList errorMsgs = new ArrayList();
        for (TableModelDeployObj tableObj : tableModelInfos) {
            this.rollbackAfterRun(() -> {
                try {
                    this.saveTable(tableObj);
                }
                catch (Exception e) {
                    this.saveTableError(dataTable, tableObj, e, errorMsgs);
                    return;
                }
                try {
                    this.preDeployTable(context, tableObj);
                }
                catch (Exception e) {
                    this.deployTableError(dataTable, tableObj, e, errorMsgs);
                    return;
                }
            });
        }
        context.getDeployResult().addDeployDetail(dataTable, errorMsgs, Collections.emptyList());
        if (!errorMsgs.isEmpty()) {
            throw new RuntimeException();
        }
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03\u6570\u636e\u8868");
        this.preDeployDataTable(dataTableInfo, tableModelInfos);
    }

    protected boolean preDeployTable(DeployContext context, TableModelDeployObj tableObj) throws Exception {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u53d1\u5e03NVWA\u5b58\u50a8\u6a21\u578b{}", (Object)tableObj.getTableModelCode());
        List deployTableSqls = context.isCheck() ? this.dataModelDeployService.getDeployTableSqls(tableObj.getTableModelKey()) : this.dataModelDeployService.getDeployTableSqlsUnCheck(tableObj.getTableModelKey());
        if (!CollectionUtils.isEmpty(deployTableSqls)) {
            context.getDdlSqls().addAll(deployTableSqls);
            return true;
        }
        return false;
    }

    protected void preDeployDataTable(DataTableDeployObj dataTableInfo, Collection<TableModelDeployObj> tableModelInfos) {
        DataTable dataTable = dataTableInfo.getDataTable();
        PreDeployInfoDO info = new PreDeployInfoDO(dataTable.getDataSchemeKey(), dataTable.getKey(), PreDeployInfoDO.PreDeployType.valueOf(dataTableInfo.getState()));
        PreDeployInfoDO.PreDeployDetails tableInfo = new PreDeployInfoDO.PreDeployDetails();
        info.setDeployDetails(tableInfo);
        List<DataField> updateNodployDataFields = dataTableInfo.getUpdateNodployDataFields();
        if (!CollectionUtils.isEmpty(updateNodployDataFields)) {
            for (DataField dataField : updateNodployDataFields) {
                for (DataFieldDeployInfoDO deployInfo : dataTableInfo.getDeployInfoByFieldKey(dataField.getKey())) {
                    if (dataTableInfo.getDataTableKey().equals(deployInfo.getDataTableKey())) continue;
                    deployInfo.setDataTableKey(dataTableInfo.getDataTableKey());
                    tableInfo.getUpdateDeployInfos().add(deployInfo);
                }
            }
        }
        for (TableModelDeployObj tableModelInfo : tableModelInfos) {
            Collection<DataFieldDeployInfoDO> addDeployInfos;
            Collection<DataFieldDeployInfoDO> updateDeployInfos;
            if (DeployTableType.DELETE == tableModelInfo.getState()) {
                tableInfo.getTableModels().put(tableModelInfo.getTableModelKey(), null);
            } else {
                DesignTableModel designTableModel = tableModelInfo.getDesignTableModel();
                tableInfo.getTableModels().put(tableModelInfo.getTableModelKey(), designTableModel.getTableModelDefine());
                tableInfo.getAddColumns().addAll(designTableModel.getNewColumns());
                tableInfo.getUpdateColumns().addAll(designTableModel.getUpdateColumns());
                tableInfo.getDeleteColumns().addAll(designTableModel.getDeleteColumns());
                tableInfo.getAddIndexs().addAll(designTableModel.getNewIndexes());
                tableInfo.getUpdateIndexs().addAll(designTableModel.getUpdateIndexes());
                tableInfo.getDeleteIndexs().addAll(designTableModel.getDeleteIndexes());
            }
            Collection<DataFieldDeployInfoDO> deleteDeployInfos = tableModelInfo.getDeleteDeployInfos();
            if (!CollectionUtils.isEmpty(deleteDeployInfos)) {
                tableInfo.getDeleteDeployInfos().addAll(deleteDeployInfos);
            }
            if (!CollectionUtils.isEmpty(updateDeployInfos = tableModelInfo.getUpdateDeployInfos())) {
                tableInfo.getUpdateDeployInfos().addAll(updateDeployInfos);
            }
            if (CollectionUtils.isEmpty(addDeployInfos = tableModelInfo.getAddDeployInfos())) continue;
            tableInfo.getAddDeployInfos().addAll(addDeployInfos);
        }
        this.dataSchemePreDeployInfoDao.insert(info);
    }

    private void rollbackAfterRun(Runnable run) {
        TransactionStatus transaction = this.getTransactionStatus();
        run.run();
        this.platformTransactionManager.rollback(transaction);
    }

    private TransactionStatus getTransactionStatus() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(3);
        return this.platformTransactionManager.getTransaction((TransactionDefinition)def);
    }
}


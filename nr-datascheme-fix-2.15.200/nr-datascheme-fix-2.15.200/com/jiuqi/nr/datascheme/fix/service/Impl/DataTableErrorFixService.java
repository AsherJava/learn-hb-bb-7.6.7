/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl
 *  com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.datascheme.fix.service.Impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.common.DeployFixType;
import com.jiuqi.nr.datascheme.fix.common.FixError;
import com.jiuqi.nr.datascheme.fix.core.DeployFixDetailsDTO;
import com.jiuqi.nr.datascheme.fix.dao.DeployFailFixLogDao;
import com.jiuqi.nr.datascheme.fix.entity.DeployFailFixLogDO;
import com.jiuqi.nr.datascheme.fix.utils.DeployFixUtils;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataTableErrorFixService {
    @Autowired
    private DeployFixUtils deployFixUtils;
    @Autowired
    private DeployFailFixLogDao deployFailFixLogDao;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    private RuntimeDataSchemeManagerService dataSchemeManagerService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;
    private IHandler defaultHandler = null;

    private void backup(HandleContext context) throws Exception {
        List<String> currentTableModelIds = context.getCurrentTableModelIds();
        if (CollectionUtils.isEmpty(currentTableModelIds)) {
            return;
        }
        String[] ids = currentTableModelIds.toArray(new String[0]);
        List tableModelDefines = this.designDataModelService.getTableModelDefines(ids);
        for (DesignTableModelDefine tableModelDefine : tableModelDefines) {
            String newTableName = this.deployFixUtils.renameLogicTable(tableModelDefine.getName());
            this.dataModelDeployService.repairTableByRuntime(tableModelDefine.getID());
            context.getBackupTableNames().put(tableModelDefine.getName(), newTableName);
        }
    }

    private void logBackup(HandleContext context) {
        DeployFailFixLogDO deployFailFixLog = new DeployFailFixLogDO();
        deployFailFixLog.setExType(DeployExType.DEFAULT.getValue());
        deployFailFixLog.setFixScheme(DeployFixType.DEFAULT.getValue());
        deployFailFixLog.setFixResult("");
        deployFailFixLog.setDeployFailFixTime(Instant.now());
        deployFailFixLog.setTransfer(false);
        deployFailFixLog.setDataSchemeKey(context.dataSchemeKey);
        deployFailFixLog.setDataTableKey(context.currentDataTableKey);
        deployFailFixLog.setNewTableName(context.getBackupTableNames().values().toArray(new String[0]));
        this.deployFailFixLogDao.insert(deployFailFixLog);
    }

    private IHandler getDefaultHandler() {
        if (null == this.defaultHandler) {
            this.defaultHandler = new ReDeployHandler(new TableModelHandler(new IllegalModifyHandler(new ForcedReDeployHandler())));
        }
        return this.defaultHandler;
    }

    public DeployFixDetailsDTO fix(String dataSchemeKey, boolean checkSuccess, String dataTableKey) throws JQException {
        DeployFixDetailsDTO detail = new DeployFixDetailsDTO(dataTableKey);
        HandleContext context = new HandleContext(dataSchemeKey, checkSuccess);
        context.reset(dataTableKey);
        IHandler defaultHandler = this.getDefaultHandler();
        defaultHandler.handle(context);
        detail.getTableModelKeys().addAll(context.getCurrentTableModelIds());
        detail.getBackupTableNames().putAll(context.getBackupTableNames());
        return detail;
    }

    class ForcedReDeployHandler
    extends AbstractHandler {
        ForcedReDeployHandler() {
            super(null);
        }

        @Override
        public void handle(HandleContext context) throws JQException {
            String dataTableKey = context.getCurrentDataTableKey();
            List<String> tableModelIds = context.getCurrentTableModelIds();
            for (String tableModelId : tableModelIds) {
                DataTableErrorFixService.this.designDataModelService.deleteTableModelDefine(tableModelId);
                try {
                    DataTableErrorFixService.this.dataModelDeployService.deployTableUnCheck(tableModelId);
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)FixError.NVWA_FIX_ERROR, (Throwable)e);
                }
            }
            DataTableErrorFixService.this.dataSchemeManagerService.deleteRuntimeDataTable(dataTableKey);
            DataTableErrorFixService.this.dataSchemeManagerService.deleteRuntimeDataTableRel(dataTableKey);
            DataTableErrorFixService.this.dataSchemeManagerService.deleteRuntimeDataFieldByTable(dataTableKey);
            DataTableErrorFixService.this.dataSchemeManagerService.deleteDeployInfoByTable(dataTableKey);
            this.reDeploy(context);
        }
    }

    class IllegalModifyHandler
    extends AbstractHandler {
        IllegalModifyHandler(IHandler handler) {
            super(handler);
        }

        @Override
        public void handle(HandleContext context) throws JQException {
            try {
                DataTableErrorFixService.this.backup(context);
                DataTableErrorFixService.this.logBackup(context);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)FixError.NVWA_FIX_ERROR, (Throwable)e);
            }
            this.reDeploy(context);
        }
    }

    class TableModelHandler
    extends AbstractHandler {
        TableModelHandler(IHandler handler) {
            super(handler);
        }

        @Override
        public void handle(HandleContext context) throws JQException {
            if (!context.isCheckSuccess()) {
                this.handler.handle(context);
                return;
            }
            List<String> tableModelIds = context.getCurrentTableModelIds();
            for (String tableModelId : tableModelIds) {
                try {
                    DataTableErrorFixService.this.dataModelDeployService.repairTableByRuntime(tableModelId);
                }
                catch (Exception e) {
                    throw new JQException((ErrorEnum)FixError.NVWA_FIX_ERROR, (Throwable)e);
                }
            }
            this.reDeploy(context);
        }
    }

    class ReDeployHandler
    extends AbstractHandler {
        ReDeployHandler(IHandler handler) {
            super(handler);
        }

        @Override
        public void handle(HandleContext context) throws JQException {
            this.reDeploy(context);
        }
    }

    abstract class AbstractHandler
    implements IHandler {
        protected final IHandler handler;

        AbstractHandler(IHandler handler) {
            this.handler = handler;
        }

        protected void reDeploy(HandleContext context) throws JQException {
            block2: {
                try {
                    String dataTableKey = context.getCurrentDataTableKey();
                    DataTableErrorFixService.this.dataSchemeDeployService.deployDataTable(dataTableKey, false);
                }
                catch (Exception e) {
                    if (null == this.handler) break block2;
                    this.handler.handle(context);
                }
            }
        }
    }

    static interface IHandler {
        public void handle(HandleContext var1) throws JQException;
    }

    class HandleContext {
        private final String dataSchemeKey;
        private final boolean checkSuccess;
        private String currentDataTableKey;
        private List<String> currentTableModelIds;
        private Map<String, String> backupTableNames;

        public HandleContext(String dataSchemeKey, boolean checkSuccess) {
            this.dataSchemeKey = dataSchemeKey;
            this.checkSuccess = checkSuccess;
        }

        public void reset(String currentDataTableKey) {
            this.currentDataTableKey = currentDataTableKey;
            List infos = DataTableErrorFixService.this.dataFieldDeployInfoDao.getByDataTableKey(currentDataTableKey);
            this.currentTableModelIds = infos.stream().map(DataFieldDeployInfoDO::getTableModelKey).distinct().collect(Collectors.toList());
            this.backupTableNames = new HashMap<String, String>();
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }

        public boolean isCheckSuccess() {
            return this.checkSuccess;
        }

        public String getCurrentDataTableKey() {
            return this.currentDataTableKey;
        }

        public List<String> getCurrentTableModelIds() {
            return this.currentTableModelIds;
        }

        public Map<String, String> getBackupTableNames() {
            return this.backupTableNames;
        }
    }
}


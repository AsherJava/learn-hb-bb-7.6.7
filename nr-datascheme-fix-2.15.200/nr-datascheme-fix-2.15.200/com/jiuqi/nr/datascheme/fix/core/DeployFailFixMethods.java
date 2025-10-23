/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.datascheme.api.event.RefreshTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao
 *  com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl
 *  com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.DataModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.IndexModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.NvwaDeployDao
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.datascheme.fix.core;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.datascheme.api.event.RefreshTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.fix.utils.DeployFixUtils;
import com.jiuqi.nr.datascheme.fix.utils.Tools;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao;
import com.jiuqi.nvwa.definition.interval.dao.DataModelDao;
import com.jiuqi.nvwa.definition.interval.dao.IndexModelDao;
import com.jiuqi.nvwa.definition.interval.dao.NvwaDeployDao;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeployFailFixMethods {
    @Autowired
    private NvwaDeployDao nvwaDeployDao;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    private IDataFieldDao<DataFieldDO> dataFieldDao;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private RuntimeDataSchemeManagerService rtManageService;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected Tools tools;
    @Autowired
    private DeployFixUtils fixUtils;
    @Autowired
    private DataModelDao dataModelDao;
    @Autowired
    private ColumnModelDao columnModelDao;
    @Autowired
    private IndexModelDao indexDao;
    @Autowired
    private DesignDataModelService desDataModelService;

    public void deletePtMethod(String dataSchemeKey, String dataTableKey, Map<String, String> tmKeyAndLtNames, String dataTableCode) throws Exception {
        for (Map.Entry<String, String> entry : tmKeyAndLtNames.entrySet()) {
            if (entry.getValue() != null) {
                this.fixUtils.deleteLogicTable(entry.getValue());
            }
            this.deleteDesNvwaModel(entry.getKey());
            this.deleteRtNvwaModel(entry.getKey());
        }
        this.deleteDsRt(dataSchemeKey, dataTableKey, dataTableCode);
        this.deleteDeployInfo(dataTableKey);
    }

    public Map<String, String> renamePtMethod(String dataSchemeKey, String dataTableKey, Map<String, String> tmKeyAndLtNames, String dataTableCode) {
        HashMap<String, String> renameInfo = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : tmKeyAndLtNames.entrySet()) {
            if (entry.getValue() != null) {
                String newTableName = this.fixUtils.renameLogicTable(entry.getValue());
                renameInfo.put(newTableName, entry.getValue());
            }
            this.deleteDesNvwaModel(entry.getKey());
            this.deleteRtNvwaModel(entry.getKey());
        }
        this.deleteDsRt(dataSchemeKey, dataTableKey, dataTableCode);
        this.deleteDeployInfo(dataTableKey);
        return renameInfo;
    }

    public void deleteAllRuntimeParams(String dataSchemeKey, String dataTableKey, String dataTableCode, Map<String, String> tmKeyAndLtNames) {
        for (Map.Entry<String, String> entry : tmKeyAndLtNames.entrySet()) {
            this.deleteDesNvwaModel(entry.getKey());
            this.deleteRtNvwaModel(entry.getKey());
        }
        this.deleteDsRt(dataSchemeKey, dataTableKey, dataTableCode);
        this.deleteDeployInfo(dataTableKey);
    }

    public void deletePtAndNvwaRtParams(Map<String, String> tmKeyAndLtNames) throws Exception {
        for (Map.Entry<String, String> entry : tmKeyAndLtNames.entrySet()) {
            if (entry.getValue() != null) {
                this.fixUtils.deleteLogicTable(entry.getValue());
            }
            this.deleteRtNvwaModel(entry.getKey());
        }
    }

    public Map<String, String> renamePtAndDeleteNvwaRtParams(Map<String, String> tmKeyAndLtNames) {
        HashMap<String, String> renameInfo = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : tmKeyAndLtNames.entrySet()) {
            if (entry.getValue() != null) {
                String newTableName = this.fixUtils.renameLogicTable(entry.getValue());
                renameInfo.put(newTableName, entry.getValue());
            }
            this.deleteRtNvwaModel(entry.getKey());
        }
        return renameInfo;
    }

    public void deleteAllNvwaRtParams(Map<String, String> tmKeyAndLtNames) {
        for (String key : tmKeyAndLtNames.keySet()) {
            this.deleteRtNvwaModel(key);
        }
    }

    public boolean doTransferData(Map<String, String> renameInfo) {
        boolean isTransfer = false;
        int ret = 0;
        for (Map.Entry<String, String> entry : renameInfo.entrySet()) {
            String oldName = entry.getValue();
            try {
                this.fixUtils.transferData(oldName, entry.getKey());
            }
            catch (Exception e) {
                ++ret;
                isTransfer = false;
            }
            if (ret != 0) continue;
            isTransfer = true;
        }
        return isTransfer;
    }

    public void deleteDsRt(String dataSchemeKey, String dataTableKey, String dataTableCode) {
        this.rtManageService.deleteRuntimeDataTable(dataTableKey);
        this.dataFieldDao.deleteByTable(dataTableKey);
        RefreshTable refreshTable = new RefreshTable(dataTableKey, dataTableCode);
        HashSet<RefreshTable> refreshTableSet = new HashSet<RefreshTable>();
        refreshTableSet.add(refreshTable);
        this.refreshCache(dataSchemeKey, dataTableKey, dataTableCode);
    }

    public void deleteDeployInfo(String dataTableKey) {
        List dataFieldDeployInfos = this.dataFieldDeployInfoDao.getByDataTableKey(dataTableKey);
        if (dataFieldDeployInfos != null) {
            for (DataFieldDeployInfoDO dataFieldDeployInfo : dataFieldDeployInfos) {
                this.dataFieldDeployInfoDao.delete(dataFieldDeployInfo);
            }
        }
    }

    public void cleanGarbageData(String dataSchemeKey, String dataTableKey, Map<String, String> tmKeyAndLtNames, String dataTableCode) throws Exception {
        for (Map.Entry<String, String> entry : tmKeyAndLtNames.entrySet()) {
            HashMap<String, String> deleteMessage = new HashMap<String, String>();
            if (entry.getValue() != null) {
                String oldTableName = entry.getValue();
                deleteMessage.put(entry.getKey(), oldTableName);
                this.deleteDsRt(dataSchemeKey, dataTableKey, dataTableCode);
                this.deleteDeployInfo(dataTableKey);
                this.deleteDesNvwaModel(entry.getKey());
                this.deleteAllNvwaRtParams(deleteMessage);
                if (this.tools.getDesDataTableByTableCode(oldTableName) != null) continue;
                this.deletePtMethod(dataSchemeKey, dataTableKey, deleteMessage, dataTableCode);
                continue;
            }
            if (entry.getKey() == null || entry.getValue() != null) continue;
            deleteMessage.put(entry.getKey(), entry.getValue());
            this.deleteDsRt(dataSchemeKey, dataTableKey, dataTableCode);
            this.deleteDeployInfo(dataTableKey);
            this.deleteDesNvwaModel(entry.getKey());
            this.deleteAllNvwaRtParams(deleteMessage);
        }
    }

    protected void deleteDesNvwaModel(String tableModelKey) {
        this.desDataModelService.deleteTableModelDefine(tableModelKey);
        this.desDataModelService.deleteColumnModelDefineByTable(tableModelKey);
        List<DataFieldDeployInfoDO> dataFieldDeployInfos = this.tools.getDeployInfoByTableModelKey(tableModelKey);
        if (dataFieldDeployInfos != null) {
            ArrayList<String> tableModelCodes = new ArrayList<String>();
            for (DataFieldDeployInfoDO dataFieldDeployInfo : dataFieldDeployInfos) {
                String tableModelCode = dataFieldDeployInfo.getTableName();
                tableModelCodes.add(tableModelCode);
            }
            List tableModelCode = tableModelCodes.stream().distinct().collect(Collectors.toList());
            ArrayList<DesignTableModelDefine> tableModelDefines = new ArrayList<DesignTableModelDefine>();
            for (String code : tableModelCode) {
                DesignTableModelDefine desTableModelDefine = this.desDataModelService.getTableModelDefineByCode(code);
                if (desTableModelDefine == null) continue;
                tableModelDefines.add(desTableModelDefine);
            }
            for (DesignTableModelDefine tableModelDefine : tableModelDefines) {
                this.desDataModelService.deleteTableModelDefine(tableModelDefine.getID());
                this.desDataModelService.deleteColumnModelDefineByTable(tableModelDefine.getID());
            }
        }
    }

    protected void deleteRtNvwaModel(String tableModelKey) {
        HashSet<String> tableKeys = new HashSet<String>(1);
        tableKeys.add(tableModelKey);
        this.dataModelDao.deleteRunTableDefines(tableKeys.toArray(new String[1]));
        this.columnModelDao.deleteRunFieldDefines(tableKeys);
        this.indexDao.removeIndexsByTable(tableModelKey);
    }

    public void rtDfCoverDesDf(String dataTableKey) {
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        List desDataFields = this.designDataSchemeService.getDataFieldByTable(dataTableKey);
        if (null != desDataFields && !desDataFields.isEmpty()) {
            Set updateNodeployFieldKeys = desDataFields.stream().map(Basic::getKey).collect(Collectors.toSet());
            this.rtManageService.updateRuntimeDataFields(updateNodeployFieldKeys);
        }
    }

    public void refreshCache(String dataSchemeKey, String dataTableKey, String dataTableCode) {
        RefreshTable refreshTable = new RefreshTable(dataTableKey, dataTableCode);
        HashSet<RefreshTable> refreshTableSet = new HashSet<RefreshTable>();
        refreshTableSet.add(refreshTable);
        this.refreshCache(dataSchemeKey, refreshTableSet);
    }

    public void refreshCache(String dataSchemeKey, Set<RefreshTable> refreshTableSet) {
        RefreshCache refreshCache = new RefreshCache();
        HashMap<RefreshScheme, Set<RefreshTable>> refreshTable = new HashMap<RefreshScheme, Set<RefreshTable>>();
        refreshCache.setRefreshTable(refreshTable);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        if (null == dataScheme) {
            dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        }
        if (null != dataScheme) {
            RefreshScheme refreshScheme = new RefreshScheme(dataScheme.getKey(), dataScheme.getCode());
            refreshTable.put(refreshScheme, refreshTableSet);
            this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataUpdator
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.engine.dataquery;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataUpdator;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.data.engine.dataquery.DeleteDataService;
import com.jiuqi.nr.data.engine.util.DataEngineEntityUtil;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeleteDataServiceImpl
implements DeleteDataService {
    private static final Logger logger = LoggerFactory.getLogger(DeleteDataServiceImpl.class);
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private EntityResetCacheService entityResetCacheService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataEngineEntityUtil d2eUtil;

    @Override
    public void deleteDataByTaskKey(String taskKey) throws Exception {
        Set<String> tableNameList = this.queryAllTableName(taskKey);
        Set<String> entityTableNameList = this.queryEntityTableNameByTaskKey(taskKey);
        if (!tableNameList.isEmpty()) {
            for (String tableName : tableNameList) {
                if (entityTableNameList.contains(tableName)) continue;
                this.truncateTableByTableName(tableName);
            }
        }
    }

    @Override
    public void deleteDataByFormKey(String formKey) throws Exception {
        Set<String> tableNameSet = this.queryAllTableByFormKey(formKey);
        if (!tableNameSet.isEmpty()) {
            for (String tableName : tableNameSet) {
                this.truncateTableByTableName(tableName);
            }
        }
    }

    @Override
    public void deleteDataByTaskDim(String taskKey, DimensionValueSet dimensionValueSet) throws Exception {
        List formDefineList = this.iRunTimeViewController.queryAllFormDefinesByTask(taskKey);
        for (FormDefine formDefine : formDefineList) {
            this.deleteDataByFormDim(formDefine.getKey(), dimensionValueSet);
        }
    }

    @Override
    public void deleteDataByFormDim(String formKey, DimensionValueSet dimensionValueSet) throws Exception {
        Set<String> fieldListAll = this.queryAllTableNameByForm(formKey);
        for (String field : fieldListAll) {
            FieldDefine fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(field);
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
            dataQuery.addColumn(fieldDefine);
            dataQuery.setMasterKeys(dimensionValueSet);
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            IDataUpdator openForUpdate = dataQuery.openForUpdate(executorContext, true);
            openForUpdate.commitChanges();
        }
    }

    @Override
    public void deleteDataByTaskKey(String taskKey, boolean entityClean) throws Exception {
        this.deleteDataByTaskKey(taskKey);
        if (entityClean) {
            this.deleteEntityByTaskKey(taskKey);
        }
    }

    @Override
    public void deleteEntityByTaskKey(String taskKey) throws Exception {
        HashSet<String> entitiesSet = new HashSet<String>();
        entitiesSet.addAll(this.queryTaskEntityTableName(taskKey));
        List formSchemeDefineList = this.iRunTimeViewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine formSchemeDefine : formSchemeDefineList) {
            entitiesSet.addAll(this.querySchemeEntityTableName(formSchemeDefine));
        }
        if (!entitiesSet.isEmpty()) {
            for (String entityKey : entitiesSet) {
                TableModelDefine tableModel = this.entityMetaService.getTableModel(entityKey);
                String tableKey = tableModel.getID();
                String tableName = tableModel.getName();
                this.truncateTableByTableName(tableName);
                this.entityResetCacheService.resetCache(tableKey);
            }
        }
    }

    public Set<String> queryEntityTableNameByTaskKey(String taskKey) throws Exception {
        HashSet<String> entityTableNameList = new HashSet<String>();
        HashSet<String> entitiesSet = new HashSet<String>();
        entitiesSet.addAll(this.queryTaskEntityTableName(taskKey));
        List formSchemeDefineList = this.iRunTimeViewController.queryFormSchemeByTask(taskKey);
        for (FormSchemeDefine formSchemeDefine : formSchemeDefineList) {
            entitiesSet.addAll(this.querySchemeEntityTableName(formSchemeDefine));
        }
        if (!entitiesSet.isEmpty()) {
            for (String entityKey : entitiesSet) {
                TableModelDefine tableModel = this.entityMetaService.getTableModel(entityKey);
                String tableName = tableModel.getName();
                entityTableNameList.add(tableName);
            }
        }
        return entityTableNameList;
    }

    public void truncateTableByTableName(String tableName) {
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("truncate table ");
            sql.append(tableName);
            this.jdbcTemplate.execute(String.valueOf(sql));
        }
        catch (Exception e) {
            logger.error("\u5b58\u50a8\u8868" + tableName + "\u6570\u636e\u6e05\u7a7a\u6267\u884c\u5f02\u5e38\uff01" + e.getMessage());
        }
    }

    public Set<String> queryAllTableName(String taskKey) throws Exception {
        HashSet<String> tableNameSet = new HashSet<String>();
        List formDefineList = this.iRunTimeViewController.queryAllFormDefinesByTask(taskKey);
        for (FormDefine formDefine : formDefineList) {
            tableNameSet.addAll(this.queryAllTableByFormKey(formDefine.getKey()));
        }
        Set<String> sysTableNameSet = this.queryAllSysTableByTaskKey(taskKey);
        tableNameSet.addAll(sysTableNameSet);
        return tableNameSet;
    }

    public Set<String> queryAllTableNameByForm(String formKey) throws Exception {
        HashSet<String> fieldListAll = new HashSet<String>();
        HashSet<String> tableList = new HashSet<String>();
        List regionDefineList = this.iRunTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : regionDefineList) {
            List fieldList = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            List fieldDefineList = this.iDataDefinitionRuntimeController.queryFieldDefines((Collection)fieldList);
            for (FieldDefine fieldDefine : fieldDefineList) {
                DataFieldDeployInfo deployInfo;
                TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                if (tableDefine.getKind() == TableKind.TABLE_KIND_ENTITY || tableList.contains((deployInfo = this.getDeployInfo(fieldDefine.getKey())).getTableName())) continue;
                fieldListAll.add(fieldDefine.getKey());
                tableList.add(deployInfo.getTableName());
            }
        }
        return fieldListAll;
    }

    public Set<String> queryAllSysTableByTaskKey(String taskKey) throws Exception {
        HashSet<String> tableNameSet = new HashSet<String>();
        try {
            List list = this.iRunTimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (Exception e) {
            logger.error("\u4efb\u52a1" + taskKey + "\u4e0b\u5b58\u50a8\u8868\u4fe1\u606f\u5b58\u5728\u5f02\u5e38\uff01" + e.getMessage());
        }
        return tableNameSet;
    }

    public Set<String> queryAllTableByFormKey(String formKey) throws Exception {
        HashSet<String> tableNameSet = new HashSet<String>();
        List dataLinkDefineList = this.iRunTimeViewController.getAllLinksInForm(formKey);
        ArrayList<String> fieldKeys = new ArrayList<String>();
        for (DataLinkDefine dataLinkDefine : dataLinkDefineList) {
            fieldKeys.add(dataLinkDefine.getLinkExpression());
        }
        if (!fieldKeys.isEmpty()) {
            try {
                List fielDefineList = this.iDataDefinitionRuntimeController.queryFieldDefines(fieldKeys);
                for (FieldDefine fieldDefine : fielDefineList) {
                    DataFieldDeployInfo deployInfo = this.getDeployInfo(fieldDefine.getKey());
                    tableNameSet.add(deployInfo.getTableName());
                }
            }
            catch (Exception e) {
                logger.error("\u901a\u8fc7\u6307\u6807key\u5217\u8868\u83b7\u53d6\u6307\u6807\u4fe1\u606f\u5f02\u5e38\uff01" + e.getMessage());
            }
        }
        return tableNameSet;
    }

    public Set<String> queryTaskEntityTableName(String taskKey) {
        String[] entityArray;
        HashSet<String> taskEntityTableName = new HashSet<String>();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        String entitiesKeys = taskDefine.getMasterEntitiesKey();
        for (String string : entityArray = entitiesKeys.split(";")) {
            taskEntityTableName.add(string);
        }
        return taskEntityTableName;
    }

    public Set<String> querySchemeEntityTableName(FormSchemeDefine formSchemeDefine) {
        String[] formEntityArray;
        HashSet<String> schemeEntityTableName = new HashSet<String>();
        String formEntitiesKeys = formSchemeDefine.getMasterEntitiesKey();
        for (String string : formEntityArray = formEntitiesKeys.split(";")) {
            schemeEntityTableName.add(string);
        }
        return schemeEntityTableName;
    }

    public void changeTable(String formKey, DimensionValueSet dimensionValueSet, Map<String, List<String>> cells, List<List<List<Object>>> data) throws Exception {
        List<List<Object>> dataList = data.get(0);
        Object[] keyList = cells.keySet().toArray();
        ArrayList<Map.Entry<String, List<String>>> cellsEntry = new ArrayList<Map.Entry<String, List<String>>>();
        cellsEntry.addAll(cells.entrySet());
        Map.Entry map = (Map.Entry)cellsEntry.get(0);
        List list = (List)map.getValue();
        for (int i = 0; i < list.size(); ++i) {
            Object value = dataList.get(i).get(1);
            String str = (String)list.get(i);
            List linkDefineList = this.iRunTimeViewController.getAllLinksInRegion(keyList[0].toString());
            for (DataLinkDefine dataLinkDefine : linkDefineList) {
                FieldDefine fieldDefine;
                if (!dataLinkDefine.getKey().equals(str) || (fieldDefine = this.iDataDefinitionRuntimeController.queryFieldDefine(dataLinkDefine.getLinkExpression())) == null) continue;
                this.changReginInfo(fieldDefine, value.toString(), dimensionValueSet);
            }
        }
    }

    public void changReginInfo(FieldDefine fieldDefine, String newData, DimensionValueSet dimensionValueSet) throws Exception {
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery();
        dataQuery.addColumn(fieldDefine);
        dataQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
        IDataUpdator openForUpdate = dataQuery.openForUpdate(executorContext);
        IDataRow iDataRow = openForUpdate.addModifiedRow(dimensionValueSet);
        iDataRow.setValue(fieldDefine, (Object)newData);
        openForUpdate.commitChanges();
    }

    private DataFieldDeployInfo getDeployInfo(String dataFieldKey) {
        IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        List deployInfoByDataFieldKeys = dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataFieldKey});
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
        return deployInfo;
    }
}


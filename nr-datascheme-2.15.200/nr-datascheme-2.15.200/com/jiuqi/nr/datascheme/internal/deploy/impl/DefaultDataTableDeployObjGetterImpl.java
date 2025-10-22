/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.core.DeployResult$Result
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.apache.shiro.util.CollectionUtils
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeDeployErrorEnum;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.IDataTableDeployObjGetter;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.deploy.common.RuntimeDataTableDTO;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AccountTableModelDeployObjBuilderImpl;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.shiro.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class DefaultDataTableDeployObjGetterImpl
implements IDataTableDeployObjGetter {
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    private DesignDataModelService designDataModelService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDataTableDeployObjGetterImpl.class);

    @Override
    public Collection<DataTableDeployObj> doGet(DeployContext context) {
        String dataSchemeKey = context.getDataSchemeKey();
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u5206\u6790\u6570\u636e\u65b9\u6848\u6570\u636e\u8868\u3001\u6307\u6807\u4fe1\u606f\u3002");
        List allDataTables = this.designDataSchemeService.getAllDataTable(dataSchemeKey);
        List allRuntiemDataTables = this.runtimeDataSchemeService.getAllDataTable(dataSchemeKey);
        if (CollectionUtils.isEmpty((Collection)allDataTables) && CollectionUtils.isEmpty((Collection)allRuntiemDataTables)) {
            return Collections.emptyList();
        }
        List allDataFields = this.designDataSchemeService.getAllDataField(dataSchemeKey);
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u5f53\u524d\u6570\u636e\u65b9\u6848\u5171\u5305\u542b\uff1a{}\u4e2a\u6570\u636e\u8868\u8868\uff0c{}\u4e2a\u6307\u6807/\u5b57\u6bb5", (Object)allDataTables.size(), (Object)allDataFields.size());
        if (CollectionUtils.isEmpty((Collection)allRuntiemDataTables)) {
            return this.addAllDataTableDeployInfo(allDataTables, allDataFields);
        }
        List allRuntimeDataFields = this.runtimeDataSchemeService.getAllDataField(dataSchemeKey);
        List<DataFieldDeployInfoDO> allDeployInfo = this.dataFieldDeployInfoDao.getByDataSchemeKey(dataSchemeKey);
        if (CollectionUtils.isEmpty((Collection)allDataTables)) {
            return this.deleteAllDataTableDeployInfo(allRuntiemDataTables, allRuntimeDataFields, allDeployInfo);
        }
        return this.updateAllDataTableDeployInfo(context, allDataTables, allRuntiemDataTables, allDataFields, allRuntimeDataFields, allDeployInfo);
    }

    private void addTableModelKeys(Map<String, Set<String>> tableModelKeyMap, DataFieldDeployInfoDO deployInfo) {
        if (tableModelKeyMap.containsKey(deployInfo.getSourceTableKey())) {
            tableModelKeyMap.get(deployInfo.getSourceTableKey()).add(deployInfo.getTableModelKey());
        } else {
            HashSet<String> tableModelKeySet = new HashSet<String>();
            tableModelKeySet.add(deployInfo.getTableModelKey());
            tableModelKeyMap.put(deployInfo.getSourceTableKey(), tableModelKeySet);
        }
    }

    private Collection<DataTableDeployObj> addAllDataTableDeployInfo(List<DesignDataTable> dataTables, List<DesignDataField> dataFields) {
        HashMap<String, DataTableDeployObj> dataTableInfoMap = new HashMap<String, DataTableDeployObj>();
        for (DesignDataTable dataTable : dataTables) {
            DataTableDeployObj dataTableDeployInfo = new DataTableDeployObj(dataTable, null);
            dataTableInfoMap.put(dataTableDeployInfo.getDataTableKey(), dataTableDeployInfo);
        }
        for (DesignDataField dataField : dataFields) {
            ((DataTableDeployObj)dataTableInfoMap.get(dataField.getDataTableKey())).addDataField(dataField, null, null);
        }
        return dataTableInfoMap.values();
    }

    private Collection<DataTableDeployObj> deleteAllDataTableDeployInfo(List<DataTable> runtiemDataTables, List<DataField> dataFields, List<DataFieldDeployInfoDO> allDeployInfo) {
        HashMap<String, DataTableDeployObj> dataTableInfoMap = new HashMap<String, DataTableDeployObj>();
        for (DataTable dataTable : runtiemDataTables) {
            DataTableDeployObj dataTableDeployInfo = new DataTableDeployObj(null, dataTable);
            dataTableInfoMap.put(dataTableDeployInfo.getDataTableKey(), dataTableDeployInfo);
        }
        HashMap<String, Set<String>> tableModelKeyMap = new HashMap<String, Set<String>>();
        HashMap<String, String> sourceTableMap = new HashMap<String, String>();
        HashMap fieldDeployInfoMap = new HashMap();
        for (DataFieldDeployInfoDO deployInfo : allDeployInfo) {
            sourceTableMap.put(deployInfo.getDataFieldKey(), deployInfo.getSourceTableKey());
            if (fieldDeployInfoMap.containsKey(deployInfo.getDataFieldKey())) {
                ((List)fieldDeployInfoMap.get(deployInfo.getDataFieldKey())).add(deployInfo);
            } else {
                ArrayList<DataFieldDeployInfoDO> deployInfos = new ArrayList<DataFieldDeployInfoDO>();
                deployInfos.add(deployInfo);
                fieldDeployInfoMap.put(deployInfo.getDataFieldKey(), deployInfos);
            }
            this.addTableModelKeys(tableModelKeyMap, deployInfo);
        }
        for (DataField dataField : dataFields) {
            String sourceTableKey = (String)sourceTableMap.get(dataField.getKey());
            List deleteDeployInfos = (List)fieldDeployInfoMap.get(dataField.getKey());
            ((DataTableDeployObj)dataTableInfoMap.get(sourceTableKey)).addDataField(null, dataField, deleteDeployInfos);
        }
        return dataTableInfoMap.values();
    }

    private Collection<DataTableDeployObj> updateAllDataTableDeployInfo(DeployContext context, List<DesignDataTable> allDataTables, List<DataTable> allRuntiemDataTables, List<DesignDataField> allDataFields, List<DataField> allRuntimeDataFields, List<DataFieldDeployInfoDO> allDeployInfo) {
        HashMap<String, DataTableDeployObj> dataTableInfoMap = new HashMap<String, DataTableDeployObj>();
        this.compareDataTables(dataTableInfoMap, allDataTables, allRuntiemDataTables);
        this.compareDataFields(context, dataTableInfoMap, allDataFields, allRuntimeDataFields, allDeployInfo);
        return dataTableInfoMap.values().stream().filter(t -> DeployType.NONE != t.getState()).sorted((t1, t2) -> {
            if (t1.getState() == t2.getState()) {
                String o1 = null == t1.getDataTable().getOrder() ? "" : t1.getDataTable().getOrder();
                String o2 = null == t2.getDataTable().getOrder() ? "" : t2.getDataTable().getOrder();
                return o1.compareTo(o2);
            }
            return t2.getState().getLevel() - t1.getState().getLevel();
        }).collect(Collectors.toList());
    }

    private void compareDataFields(DeployContext context, Map<String, DataTableDeployObj> dataTableInfoMap, List<DesignDataField> allDataFields, List<DataField> allRuntimeDataFields, List<DataFieldDeployInfoDO> allDeployInfo) {
        Map<String, DesignDataField> dataFieldMap = allDataFields.stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        Map<String, DataField> rtDataFieldMap = allRuntimeDataFields.stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        HashMap<String, String> sourceTableMap = new HashMap<String, String>();
        HashMap fieldDeployInfoMap = new HashMap();
        for (DataFieldDeployInfoDO deployInfo : allDeployInfo) {
            sourceTableMap.put(deployInfo.getDataFieldKey(), deployInfo.getSourceTableKey());
            if (fieldDeployInfoMap.containsKey(deployInfo.getDataFieldKey())) {
                ((List)fieldDeployInfoMap.get(deployInfo.getDataFieldKey())).add(deployInfo);
                continue;
            }
            ArrayList<DataFieldDeployInfoDO> deployInfos = new ArrayList<DataFieldDeployInfoDO>();
            deployInfos.add(deployInfo);
            fieldDeployInfoMap.put(deployInfo.getDataFieldKey(), deployInfos);
        }
        HashSet<String> allDataFieldKeys = new HashSet<String>();
        allDataFieldKeys.addAll(dataFieldMap.keySet());
        allDataFieldKeys.addAll(rtDataFieldMap.keySet());
        HashSet<String> missTableKeys = new HashSet<String>();
        for (String dataFieldKey : allDataFieldKeys) {
            DataTableDeployObj dataTableDeployObj;
            String dataTableKey = (String)sourceTableMap.get(dataFieldKey);
            DesignDataField dataField = dataFieldMap.get(dataFieldKey);
            DataField rtDataField = rtDataFieldMap.get(dataFieldKey);
            if (!StringUtils.hasText(dataTableKey)) {
                dataTableKey = dataField.getDataTableKey();
            }
            if (null == (dataTableDeployObj = dataTableInfoMap.get(dataTableKey))) {
                if (missTableKeys.contains(dataTableKey)) continue;
                DesignDataTableDO dataTable = new DesignDataTableDO();
                dataTable.setKey(dataTableKey);
                dataTable.setCode("00MISSTABLE");
                dataTable.setTitle("\u6570\u636e\u8868\u4e22\u5931");
                dataTable.setDataTableType(DataTableType.TABLE);
                missTableKeys.add(dataTableKey);
                context.getDeployResult().addCheckError((DataTable)dataTable, "\u6570\u636e\u8868\u4e22\u5931");
                continue;
            }
            dataTableDeployObj.addDataField(dataField, rtDataField, (List)fieldDeployInfoMap.get(dataFieldKey));
        }
    }

    private void compareDataTables(Map<String, DataTableDeployObj> dataTableInfoMap, List<DesignDataTable> allDataTables, List<DataTable> allRuntiemDataTables) {
        HashSet<String> allDataTableKeys = new HashSet<String>();
        Map<String, DesignDataTable> dataTableMap = allDataTables.stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        Map<String, DataTable> rtDataTableMap = allRuntiemDataTables.stream().collect(Collectors.toMap(Basic::getKey, v -> v));
        allDataTableKeys.addAll(dataTableMap.keySet());
        allDataTableKeys.addAll(rtDataTableMap.keySet());
        for (String dataTableKey : allDataTableKeys) {
            DesignDataTable dataTable = dataTableMap.get(dataTableKey);
            DataTable rtDataTable = rtDataTableMap.get(dataTableKey);
            DataTableDeployObj dataTableDeployObj = new DataTableDeployObj(dataTable, rtDataTable);
            dataTableInfoMap.put(dataTableKey, dataTableDeployObj);
        }
    }

    private void checkDataTableForUpdate(List<String> checkMsg, DataTable designDataTable, DataTable runtimeDataTable) {
        if (DataTableType.TABLE != designDataTable.getDataTableType() && !designDataTable.isRepeatCode() && runtimeDataTable.isRepeatCode() && this.runtimeDataSchemeService.dataTableCheckData(new String[]{designDataTable.getKey()})) {
            checkMsg.add(String.format("\u6570\u636e\u8868[%s]\u5df2\u7ecf\u5f55\u5165\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u7531\u5141\u8bb8\u91cd\u7801\u6539\u4e3a\u4e0d\u5141\u8bb8\u91cd\u7801\uff01", designDataTable.getCode()));
        }
    }

    @Override
    public Collection<DataTableDeployObj> doGet(DeployContext context, Set<String> keys) {
        ArrayList<String> dataTableKeys = new ArrayList<String>(keys);
        List dataTables = this.designDataSchemeService.getDataTables(dataTableKeys);
        List runtimeDataTables = this.runtimeDataSchemeService.getDataTables(dataTableKeys);
        List dataFields = this.designDataSchemeService.getDataFieldByTables(dataTableKeys);
        List runtimeDataFields = this.runtimeDataSchemeService.getDataFieldByTables(dataTableKeys);
        List<DataFieldDeployInfoDO> deployInfos = this.dataFieldDeployInfoDao.getByDataTableKeys(dataTableKeys);
        Optional<DataFieldDeployInfoDO> findAny = deployInfos.stream().filter(Info -> !dataTableKeys.contains(Info.getSourceTableKey())).findAny();
        if (findAny.isPresent()) {
            context.getDeployResult().setResult(DeployResult.Result.CHECK_ERROR);
            context.getDeployResult().setMessage(DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_UNSPT_001.getMessage());
            return null;
        }
        return this.getDataTableDeployInfos(context, dataTables, runtimeDataTables, dataFields, runtimeDataFields, deployInfos);
    }

    private Collection<DataTableDeployObj> getDataTableDeployInfos(DeployContext context, List<DesignDataTable> allDataTables, List<DataTable> allRuntiemDataTables, List<DesignDataField> allDataFields, List<DataField> allRuntimeDataFields, List<DataFieldDeployInfoDO> allDeployInfo) {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u5206\u6790\u6570\u636e\u65b9\u6848\u6570\u636e\u8868\u3001\u6307\u6807\u4fe1\u606f\u3002");
        if (CollectionUtils.isEmpty(allDataTables) && CollectionUtils.isEmpty(allRuntiemDataTables)) {
            return Collections.emptyList();
        }
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u5f53\u524d\u6570\u636e\u65b9\u6848\u5171\u5305\u542b\uff1a{}\u4e2a\u6570\u636e\u8868\u8868\uff0c{}\u4e2a\u6307\u6807/\u5b57\u6bb5", (Object)allDataTables.size(), (Object)allDataFields.size());
        if (CollectionUtils.isEmpty(allRuntiemDataTables)) {
            return this.addAllDataTableDeployInfo(allDataTables, allDataFields);
        }
        if (CollectionUtils.isEmpty(allDataTables)) {
            return this.deleteAllDataTableDeployInfo(allRuntiemDataTables, allRuntimeDataFields, allDeployInfo);
        }
        return this.updateAllDataTableDeployInfo(context, allDataTables, allRuntiemDataTables, allDataFields, allRuntimeDataFields, allDeployInfo);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)
    public List<RuntimeDataTableDTO> getRuntimeDataTable(String dataSchemeKey, int dataTableType) {
        HashMap<String, DataTable> dataTables = new HashMap<String, DataTable>();
        HashMap<String, DataField> dataFields = new HashMap<String, DataField>();
        HashMap<String, String> tableToDataTable = new HashMap<String, String>();
        HashMap<String, String> columnToField = new HashMap<String, String>();
        for (DataTable dataTable : this.runtimeDataSchemeService.getAllDataTable(dataSchemeKey)) {
            dataTables.put(dataTable.getKey(), dataTable);
        }
        for (DataField dataField : this.runtimeDataSchemeService.getAllDataField(dataSchemeKey)) {
            dataFields.put(dataField.getKey(), dataField);
        }
        for (DataFieldDeployInfoDO info : this.dataFieldDeployInfoDao.getByDataSchemeKey(dataSchemeKey)) {
            columnToField.put(info.getColumnModelKey(), info.getDataFieldKey());
            tableToDataTable.put(info.getTableModelKey(), info.getDataTableKey());
        }
        return this.getRuntimeDataTables(dataTables, dataFields, tableToDataTable, columnToField, dataTableType);
    }

    @NotNull
    private ArrayList<RuntimeDataTableDTO> getRuntimeDataTables(Map<String, DataTable> dataTables, Map<String, DataField> dataFields, Map<String, String> tableToDataTable, Map<String, String> columnToField, int dataTableType) {
        HashMap<String, RuntimeDataTableDTO> runtimeDataTables = new HashMap<String, RuntimeDataTableDTO>();
        List tableModelDefines = this.designDataModelService.getTableModelDefines(tableToDataTable.keySet().toArray(new String[0]));
        for (DesignTableModelDefine tableModelDefine : tableModelDefines) {
            String dataTableKey = tableToDataTable.get(tableModelDefine.getID());
            DataTable dataTable = dataTables.get(dataTableKey);
            int value = dataTable.getDataTableType().getValue();
            if (0 != dataTableType && 0 == (dataTableType & value)) continue;
            DataTableModelDTO dataTableModel = new DataTableModelDTO(dataTable, tableModelDefine);
            List indexes = this.designDataModelService.getIndexsByTable(tableModelDefine.getID());
            dataTableModel.addIndexModels(indexes);
            List columns = this.designDataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
            for (DesignColumnModelDefine column : columns) {
                String dataFieldKey = columnToField.get(column.getID());
                DataField dataField = dataFields.get(dataFieldKey);
                dataTableModel.addDataColumnModel(dataField, column);
            }
            if (DataTableType.ACCOUNT == dataTable.getDataTableType() && dataTable.getCode().equals(tableModelDefine.getCode())) {
                DesignTableModelDefine hisTableModel = this.designDataModelService.getTableModelDefineByCode(AccountTableModelDeployObjBuilderImpl.getHisTableName(tableModelDefine.getCode()));
                List hisColumns = this.designDataModelService.getColumnModelDefinesByTable(hisTableModel.getID());
                List hisIndexes = this.designDataModelService.getIndexsByTable(hisTableModel.getID());
                dataTableModel.addExtendTableModel(hisTableModel, hisColumns, hisIndexes);
            }
            RuntimeDataTableDTO runtimeDataTable = runtimeDataTables.computeIfAbsent(dataTableKey, k -> new RuntimeDataTableDTO(dataTable, new ArrayList<DataTableModelDTO>()));
            runtimeDataTable.getDataTableModels().add(dataTableModel);
        }
        return new ArrayList<RuntimeDataTableDTO>(runtimeDataTables.values());
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)
    public List<RuntimeDataTableDTO> getRuntimeDataTable(List<String> dataTableKeys) {
        HashMap<String, DataTable> dataTables = new HashMap<String, DataTable>();
        HashMap<String, DataField> dataFields = new HashMap<String, DataField>();
        HashMap<String, String> tableToDataTable = new HashMap<String, String>();
        HashMap<String, String> columnToField = new HashMap<String, String>();
        for (DataTable dataTable : this.runtimeDataSchemeService.getDataTables(dataTableKeys)) {
            dataTables.put(dataTable.getKey(), dataTable);
            for (DataField dataField : this.runtimeDataSchemeService.getDataFieldByTable(dataTable.getKey())) {
                dataFields.put(dataField.getKey(), dataField);
            }
            for (DataFieldDeployInfo info : this.runtimeDataSchemeService.getDeployInfoByDataTableKey(dataTable.getKey())) {
                tableToDataTable.put(info.getTableModelKey(), info.getDataTableKey());
                columnToField.put(info.getColumnModelKey(), info.getDataFieldKey());
            }
        }
        return this.getRuntimeDataTables(dataTables, dataFields, tableToDataTable, columnToField, 0);
    }
}


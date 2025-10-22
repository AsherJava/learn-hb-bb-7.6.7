/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableType
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeDeployErrorEnum;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployFactory;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper;
import com.jiuqi.nr.datascheme.internal.deploy.ITableModelDeployObjBuilder;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataFieldDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableType;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractTableModelDeployObjBuilder
implements ITableModelDeployObjBuilder {
    @Autowired
    DataModelDeployService dataModelDeployService;
    @Autowired
    IDesignDataSchemeService designDataSchemeService;
    @Autowired
    DesignDataModelService designDataModelService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataFieldDeployInfoDaoImpl dataFieldDeployInfoDao;
    @Autowired
    private DataSchemeDeployFactory dataSchemeDeployFactory;
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTableModelDeployObjBuilder.class);

    @Override
    public List<TableModelDeployObj> doBuild(DeployContext buildContext, DataTableDeployObj dataTableInfo) {
        List<TableModelDeployObj> tableModelInfos = null;
        boolean check = this.checkBeforeBuild(buildContext, dataTableInfo);
        if (!check) {
            return Collections.emptyList();
        }
        DataTable dataTable = dataTableInfo.getDataTable();
        try {
            tableModelInfos = this.build(buildContext, dataTableInfo);
            this.buildExtendTable(buildContext, dataTableInfo, tableModelInfos);
            this.updateCode(buildContext, tableModelInfos, dataTableInfo);
            this.dataSchemeDeployFactory.getIndexBuilder(dataTable.getDataTableType()).doBuild(dataTableInfo, tableModelInfos);
        }
        catch (JQException | ModelValidateException e1) {
            buildContext.getDeployResult().addCheckError(dataTable, e1.getLocalizedMessage());
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u6784\u5efa\u6570\u636e\u8868{}[{}]\u7684\u5b58\u50a8\u6a21\u578b\u5931\u8d25\u3002", dataTable.getTitle(), dataTable.getCode(), e1);
            return tableModelInfos;
        }
        catch (Exception e) {
            buildContext.getDeployResult().addCheckError(dataTable, e.getMessage());
            LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u6784\u5efa\u6570\u636e\u8868{}[{}]\u7684\u5b58\u50a8\u6a21\u578b\u5931\u8d25\u3002", dataTable.getTitle(), dataTable.getCode(), e);
            return tableModelInfos;
        }
        check = this.checkAfterBuild(buildContext, dataTableInfo, tableModelInfos);
        if (!check) {
            return Collections.emptyList();
        }
        return tableModelInfos;
    }

    protected void updateCode(DeployContext buildContext, List<TableModelDeployObj> tableModelDeployObjs, DataTableDeployObj dataTableInfo) {
        if (DeployType.DELETE == dataTableInfo.getState()) {
            this.getTableModelCodes(buildContext);
            for (TableModelDeployObj obj : tableModelDeployObjs) {
                buildContext.getTableModelCodes().remove(obj.getTableModelCode());
            }
        } else if (dataTableInfo.isCodeChanged()) {
            this.updateTableModelCode(buildContext, tableModelDeployObjs, dataTableInfo);
        } else {
            for (TableModelDeployObj tableModelDeployObj : tableModelDeployObjs) {
                if (DeployTableType.ADD != tableModelDeployObj.getState()) continue;
                this.updateTableModelCode(buildContext, tableModelDeployObjs, dataTableInfo);
                break;
            }
        }
    }

    private void updateTableModelCode(DeployContext buildContext, List<TableModelDeployObj> tableModelDeployObjs, DataTableDeployObj dataTableInfo) {
        String code = dataTableInfo.getDataTable().getCode();
        Map<String, String> allTableCodes = this.getTableModelCodes(buildContext);
        HashMap<String, String> newCodeMap = new HashMap<String, String>();
        HashMap<String, TableModelDeployObj> tableMap = new HashMap<String, TableModelDeployObj>();
        for (TableModelDeployObj obj : tableModelDeployObjs) {
            String tableCode = DataSchemeDeployHelper.getUniqueCode(code, c -> {
                String k = (String)allTableCodes.get(c);
                if (StringUtils.hasText(k)) {
                    return obj.getTableModelKey().equals(k);
                }
                return !newCodeMap.containsKey(c);
            });
            newCodeMap.put(tableCode, obj.getTableModelKey());
            tableMap.put(obj.getTableModelCode(), obj);
        }
        buildContext.updateTableModelCodes(newCodeMap);
        ArrayList oldTableCodes = new ArrayList(tableMap.keySet());
        ArrayList newTableCodes = new ArrayList(newCodeMap.keySet());
        oldTableCodes.removeAll(newTableCodes);
        newTableCodes.removeAll(tableMap.keySet());
        oldTableCodes.sort(null);
        newTableCodes.sort(null);
        for (int i = 0; i < oldTableCodes.size(); ++i) {
            TableModelDeployObj tableModelDeployObj = (TableModelDeployObj)tableMap.get(oldTableCodes.get(i));
            tableModelDeployObj.updateTableCode((String)newTableCodes.get(i));
        }
    }

    private Map<String, String> getTableModelCodes(DeployContext buildContext) {
        Map<String, String> allTableCodes = buildContext.getTableModelCodes();
        if (null == allTableCodes) {
            allTableCodes = new HashMap<String, String>();
            List tableModelDefines = this.designDataModelService.getTableModelDefines();
            for (DesignTableModelDefine designTableModelDefine : tableModelDefines) {
                allTableCodes.put(designTableModelDefine.getCode(), designTableModelDefine.getID());
            }
            buildContext.updateTableModelCodes(allTableCodes);
        }
        return allTableCodes;
    }

    protected boolean checkBeforeBuild(DeployContext buildContext, DataTableDeployObj dataTableInfo) {
        return true;
    }

    protected boolean checkAfterBuild(DeployContext buildContext, DataTableDeployObj dataTableInfo, Collection<TableModelDeployObj> tableModelInfos) {
        boolean checkTable = true;
        if (!buildContext.isCheck()) {
            return checkTable;
        }
        DataTable dataTable = dataTableInfo.getDataTable();
        String dataTableCode = dataTable.getCode();
        String dataTableTitle = dataTable.getTitle();
        for (TableModelDeployObj tableModelInfo : tableModelInfos) {
            String tableName = tableModelInfo.getTableModelName();
            try {
                DeployTableType state = tableModelInfo.getState();
                if (DeployTableType.NONE.equals((Object)state)) {
                    state = DeployTableType.UPDATE;
                }
                if (checkTable = this.dataModelDeployService.checkTable(tableModelInfo.getTableModel(), state)) continue;
                buildContext.getDeployResult().addCheckError(dataTable, "\u68c0\u67e5\u672a\u901a\u8fc7\uff0c\u539f\u56e0\u672a\u77e5\u3002");
            }
            catch (Exception e) {
                buildContext.getDeployResult().addCheckError(dataTable, e.getMessage());
                LOGGER.error("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a{}[{}][{}]\u6267\u884c\u53c2\u6570\u68c0\u67e5\u5931\u8d25\u3002", dataTableCode, dataTableTitle, tableName, e);
            }
        }
        return checkTable;
    }

    protected void buildExtendTable(DeployContext buildContext, DataTableDeployObj dataTableInfo, List<TableModelDeployObj> tableModelInfos) throws ModelValidateException {
    }

    private List<TableModelDeployObj> build(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo) throws ModelValidateException, JQException {
        if (DeployType.DELETE.equals((Object)dataTableDeployInfo.getState())) {
            return this.buildAllDeleteTableModels(dataTableDeployInfo);
        }
        if (DeployType.ADD.equals((Object)dataTableDeployInfo.getState())) {
            return this.buildAllAddTableModels(buildContext, dataTableDeployInfo);
        }
        return this.buildAllUpdateTableModels(buildContext, dataTableDeployInfo);
    }

    private List<TableModelDeployObj> buildAllUpdateTableModels(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo) throws JQException, ModelValidateException {
        List<TableModelDeployObj> tableInfos = this.buildUpdateTableModels(buildContext, dataTableDeployInfo);
        this.buildDeleteAndUpdateColumns(buildContext, dataTableDeployInfo, tableInfos);
        this.buildAddColumns(buildContext, dataTableDeployInfo, tableInfos);
        return tableInfos;
    }

    protected void buildAddColumns(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo, List<TableModelDeployObj> tableInfos) throws ModelValidateException, JQException {
        List<DataField> addDataFields = dataTableDeployInfo.getAddDataFields();
        if (null == addDataFields || addDataFields.isEmpty()) {
            return;
        }
        List<DataField> requireDataFields = dataTableDeployInfo.getRequireDataFields();
        for (DataField designDataField : addDataFields) {
            TableModelDeployObj tableModelDeployInfo = this.getNofullTableInfo(buildContext, tableInfos, dataTableDeployInfo);
            tableModelDeployInfo.addColumnModel(buildContext, designDataField);
            if (!requireDataFields.contains(designDataField)) continue;
            for (TableModelDeployObj tableModel : tableInfos) {
                tableModel.addColumnModel(buildContext, designDataField);
            }
        }
    }

    protected void buildDeleteAndUpdateColumns(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo, List<TableModelDeployObj> tableInfos) throws ModelValidateException, JQException {
        Map<String, TableModelDeployObj> tableInfoMap = tableInfos.stream().collect(Collectors.toMap(TableModelDeployObj::getTableModelKey, v -> v));
        List<String> deleteDataFieldKeys = dataTableDeployInfo.getDeleteDataFieldKeys();
        if (null != deleteDataFieldKeys && !deleteDataFieldKeys.isEmpty()) {
            for (String designDataFieldKey : deleteDataFieldKeys) {
                List<DataFieldDeployInfoDO> deployInfos = dataTableDeployInfo.getDeployInfoByFieldKey(designDataFieldKey);
                for (DataFieldDeployInfoDO deployInfo : deployInfos) {
                    TableModelDeployObj tableModelDeployInfo = tableInfoMap.get(deployInfo.getTableModelKey());
                    if (null == tableModelDeployInfo) {
                        throw new JQException((ErrorEnum)DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_PARAERROR);
                    }
                    tableModelDeployInfo.deleteColumnModel(deployInfo);
                }
            }
        }
        List<DataField> updateDataFields = dataTableDeployInfo.getUpdateDataFields();
        List<DataField> requireDataFields = dataTableDeployInfo.getRequireDataFields();
        if (null != updateDataFields && !updateDataFields.isEmpty()) {
            for (DataField dataField : updateDataFields) {
                DataFieldDeployObj dataFieldDeployObj = dataTableDeployInfo.getDataField(dataField.getKey());
                for (DataFieldDeployInfoDO deployInfo : dataFieldDeployObj.getDeployInfos()) {
                    TableModelDeployObj tableModelDeployInfo = tableInfoMap.get(deployInfo.getTableModelKey());
                    if (null == tableModelDeployInfo) {
                        throw new JQException((ErrorEnum)DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_PARAERROR);
                    }
                    tableModelDeployInfo.updateColumnModel(buildContext, dataField, deployInfo, dataFieldDeployObj.isCodeChanged());
                }
                if (!requireDataFields.contains(dataField) || dataFieldDeployObj.getDeployInfos().size() == tableInfos.size()) continue;
                Set existTableModelKeys = dataFieldDeployObj.getDeployInfos().stream().map(DataFieldDeployInfoDO::getTableModelKey).collect(Collectors.toSet());
                for (TableModelDeployObj tableInfo : tableInfos) {
                    if (existTableModelKeys.contains(tableInfo.getTableModelKey())) continue;
                    tableInfo.addColumnModel(buildContext, dataField);
                }
            }
        }
    }

    private List<TableModelDeployObj> buildUpdateTableModels(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo) throws JQException, ModelValidateException {
        ArrayList<TableModelDeployObj> tableInfos = new ArrayList<TableModelDeployObj>();
        DataTable dataTable = dataTableDeployInfo.getDataTable();
        Set<String> tableModelKeys = dataTableDeployInfo.getTableModelKeys();
        TableModelDeployObj tableModelDeployInfo = null;
        for (String tableModelKey : tableModelKeys) {
            DesignTableModel tableModel = this.designDataModelService.getTableModel(tableModelKey);
            if (null == tableModel) {
                tableModelDeployInfo = this.recoveryTableModel(buildContext, dataTableDeployInfo.getDataTable(), tableModelKey);
            } else if (DeployType.UPDATE.equals((Object)dataTableDeployInfo.getState())) {
                tableModelDeployInfo = new TableModelDeployObj(tableModel, DeployTableType.UPDATE);
                tableModelDeployInfo.updateDesignTableModel(buildContext, dataTable, null);
            } else {
                tableModelDeployInfo = new TableModelDeployObj(tableModel, DeployTableType.NONE);
            }
            tableInfos.add(tableModelDeployInfo);
        }
        return tableInfos;
    }

    protected List<TableModelDeployObj> buildAllAddTableModels(DeployContext buildContext, DataTableDeployObj dataTableDeployInfo) throws ModelValidateException, JQException {
        ArrayList<TableModelDeployObj> tableInfos = new ArrayList<TableModelDeployObj>();
        List<DataField> addDataFields = dataTableDeployInfo.getAddDataFields();
        TableModelDeployObj tableModelDeployInfo = null;
        for (DataField dataField : addDataFields) {
            tableModelDeployInfo = this.getNofullTableInfo(buildContext, tableInfos, dataTableDeployInfo);
            tableModelDeployInfo.addColumnModel(buildContext, dataField);
        }
        return tableInfos;
    }

    private List<TableModelDeployObj> buildAllDeleteTableModels(DataTableDeployObj dataTableDeployInfo) throws JQException {
        Set<String> tableModelKeys = dataTableDeployInfo.getTableModelKeys();
        ArrayList<TableModelDeployObj> result = new ArrayList<TableModelDeployObj>(tableModelKeys.size());
        for (String tableModelKey : tableModelKeys) {
            DesignTableModel tableModel = this.designDataModelService.getTableModel(tableModelKey);
            if (null == tableModel) {
                throw new JQException((ErrorEnum)DataSchemeDeployErrorEnum.DATATABLE_DEPLOY_PARAERROR, "\u672a\u627e\u5230\u5b58\u50a8\u8868");
            }
            List<DataFieldDeployInfoDO> deployInfos = dataTableDeployInfo.getDeployInfoByTableModelKey(tableModelKey);
            TableModelDeployObj tableModelDeployInfo = new TableModelDeployObj(tableModel, deployInfos);
            result.add(tableModelDeployInfo);
        }
        return result;
    }

    private TableModelDeployObj getNofullTableInfo(DeployContext buildContext, List<TableModelDeployObj> allTableModelInfos, DataTableDeployObj dataTableDeployInfo) throws ModelValidateException, JQException {
        if (allTableModelInfos.isEmpty()) {
            return this.createTableModel(buildContext, allTableModelInfos, dataTableDeployInfo);
        }
        if (DataTableType.TABLE != dataTableDeployInfo.getDataTable().getDataTableType()) {
            return allTableModelInfos.get(0);
        }
        for (TableModelDeployObj tableInfo : allTableModelInfos) {
            if (tableInfo.isFull()) continue;
            return tableInfo;
        }
        return this.createTableModel(buildContext, allTableModelInfos, dataTableDeployInfo);
    }

    private TableModelDeployObj recoveryTableModel(DeployContext buildContext, DataTable dataTable, String tableModelKey) throws JQException, ModelValidateException {
        List<DataFieldDeployInfoDO> deployInfos = this.dataFieldDeployInfoDao.getByTableModelKey(tableModelKey);
        String tableName = null;
        ArrayList<String> dataFieldKeys = new ArrayList<String>();
        HashMap<String, DataFieldDeployInfoDO> deployInfosMap = new HashMap<String, DataFieldDeployInfoDO>();
        for (DataFieldDeployInfoDO info : deployInfos) {
            if (null == tableName) {
                tableName = info.getTableName();
            }
            dataFieldKeys.add(info.getDataFieldKey());
            deployInfosMap.put(info.getDataFieldKey(), info);
        }
        List dataFields = this.runtimeDataSchemeService.getDataFields(dataFieldKeys);
        TableModelDeployObj tableModelDeployInfo = new TableModelDeployObj().updateDesignTableModel(buildContext, dataTable, tableName);
        for (DataField dataField : dataFields) {
            tableModelDeployInfo.updateColumnModel(buildContext, dataField, (DataFieldDeployInfoDO)deployInfosMap.get(dataField.getKey()), false);
        }
        return tableModelDeployInfo;
    }

    private TableModelDeployObj createTableModel(DeployContext buildContext, List<TableModelDeployObj> allTableModelInfos, DataTableDeployObj dataTableDeployInfo) throws ModelValidateException, JQException {
        TableModelDeployObj tableModelDeployInfo = new TableModelDeployObj();
        DataTable dataTable = dataTableDeployInfo.getDataTable();
        String tableCode = dataTable.getCode();
        if (!CollectionUtils.isEmpty(allTableModelInfos)) {
            List existCodes = allTableModelInfos.stream().map(TableModelDeployObj::getTableModelCode).collect(Collectors.toList());
            tableCode = DataSchemeDeployHelper.getUniqueCode(tableCode, c -> !existCodes.contains(c));
        }
        tableModelDeployInfo.updateDesignTableModel(buildContext, dataTable, tableCode);
        List<DataField> essentialDataFields = dataTableDeployInfo.getRequireDataFields();
        for (DataField essentialDataField : essentialDataFields) {
            tableModelDeployInfo.addColumnModel(buildContext, essentialDataField);
        }
        allTableModelInfos.add(tableModelDeployInfo);
        return tableModelDeployInfo;
    }
}


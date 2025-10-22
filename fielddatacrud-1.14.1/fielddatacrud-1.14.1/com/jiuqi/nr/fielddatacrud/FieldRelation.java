/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.fielddatacrud.FieldMetaData;
import com.jiuqi.nr.fielddatacrud.TableDimSet;
import com.jiuqi.nr.fielddatacrud.impl.DataModelLinkFinder;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FieldRelation
implements ParamRelation {
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private IDataAccessProvider dataAccessProvider;
    private IDataDefinitionRuntimeController runtimeController;
    private IRunTimeViewController runTimeViewController;
    private DataModelService dataModelService;
    private IDataDefinitionRuntimeController dataDefinitionController;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private String dataSchemeKey;
    private String formSchemeKey;
    private String dwDimName;
    private List<IMetaData> metaData;
    private Map<String, IMetaData> metaDataMap;
    private ReportFmlExecEnvironment environment;

    public String getDefaultGroupName() {
        return null;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getTaskKey() {
        return null;
    }

    public String getDwDimName() {
        if (this.dwDimName != null) {
            return this.dwDimName;
        }
        List<TableDimSet> tableDim = this.getTableDim(this.getMetaData());
        this.dwDimName = tableDim.get(0).getDwDimName();
        return this.dwDimName;
    }

    public IFmlExecEnvironment getReportFmlExecEnvironment() {
        if (this.environment != null) {
            return this.environment;
        }
        this.environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, this.getFormSchemeKey());
        if (!StringUtils.hasLength(this.getFormSchemeKey())) {
            DataModelLinkFinder dataModelLinkFinder = new DataModelLinkFinder();
            dataModelLinkFinder.setRuntimeDataSchemeService(this.runtimeDataSchemeService);
            dataModelLinkFinder.setDataModelService(this.dataModelService);
            this.environment.setDataModelLinkFinder((IDataModelLinkFinder)dataModelLinkFinder);
        }
        this.environment.setDataScehmeKey(this.dataSchemeKey);
        return this.environment;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public void setDataAccessProvider(IDataAccessProvider dataAccessProvider) {
        this.dataAccessProvider = dataAccessProvider;
    }

    public void setRuntimeController(IDataDefinitionRuntimeController runtimeController) {
        this.runtimeController = runtimeController;
    }

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public void setDataDefinitionController(IDataDefinitionRuntimeController dataDefinitionController) {
        this.dataDefinitionController = dataDefinitionController;
    }

    public void setEntityViewRunTimeController(IEntityViewRunTimeController entityViewRunTimeController) {
        this.entityViewRunTimeController = entityViewRunTimeController;
    }

    public void setDataModelService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    public List<IMetaData> getMetaData() {
        return this.metaData;
    }

    public List<IMetaData> getMetaDataByTableKey(String dataTableKey) {
        List dataFieldByTable = this.runtimeDataSchemeService.getDataFieldByTable(dataTableKey);
        this.metaData = this.initMetaData(dataFieldByTable.stream().map(Basic::getKey).collect(Collectors.toList()));
        return this.metaData;
    }

    public List<IMetaData> getMetaData(List<String> keys) {
        if (this.metaData != null) {
            return this.metaData;
        }
        this.metaData = new ArrayList<IMetaData>();
        this.metaDataMap = new HashMap<String, IMetaData>();
        List<IMetaData> metaData = this.initMetaData(keys);
        for (IMetaData metaDatum : metaData) {
            this.metaData.add(metaDatum);
            this.metaDataMap.put(metaDatum.getFieldKey(), metaDatum);
        }
        return metaData;
    }

    public List<IMetaData> matchDimMetaData(List<String> fieldCodes) {
        Optional first = this.metaData.stream().findFirst();
        if (!first.isPresent()) {
            return null;
        }
        List dataFieldByTable = this.runtimeDataSchemeService.getDataFieldByTable(((IMetaData)first.get()).getDataField().getDataTableKey());
        HashMap<String, DataField> fieldCodeMap = new HashMap<String, DataField>();
        for (DataField dataField : dataFieldByTable) {
            if (dataField.getDataFieldKind() != DataFieldKind.PUBLIC_FIELD_DIM) continue;
            fieldCodeMap.put(dataField.getCode(), dataField);
        }
        if (fieldCodeMap.size() != fieldCodes.size()) {
            return null;
        }
        ArrayList<IMetaData> list = new ArrayList<IMetaData>();
        for (String fieldCode : fieldCodes) {
            DataField dataField = (DataField)fieldCodeMap.get(fieldCode);
            if (dataField != null) {
                list.add(this.initMetaData(dataField.getKey()));
                continue;
            }
            return null;
        }
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    public List<IMetaData> initFieldRelation(List<String> keys) {
        this.metaData = new ArrayList<IMetaData>();
        this.metaDataMap = new HashMap<String, IMetaData>();
        List<IMetaData> metaData = this.initMetaData(keys);
        for (IMetaData metaDatum : metaData) {
            DataField dataField;
            if (this.dataSchemeKey == null && (dataField = metaDatum.getDataField()) != null) {
                this.dataSchemeKey = dataField.getDataSchemeKey();
            }
            this.metaData.add(metaDatum);
            this.metaDataMap.put(metaDatum.getFieldKey(), metaDatum);
        }
        return metaData;
    }

    public IMetaData initMetaData(String key) {
        List<IMetaData> iMetaData = this.initMetaData(Collections.singletonList(key));
        return iMetaData.stream().findFirst().orElse(null);
    }

    public List<IMetaData> initMetaData(List<String> keys) {
        ArrayList<IMetaData> metaData = new ArrayList<IMetaData>();
        HashMap fieldDeployInfoMap = new HashMap();
        HashMap<String, DataField> keyToFieldMap = new HashMap<String, DataField>();
        List dataFields = this.runtimeDataSchemeService.getDataFields(keys);
        for (DataField field : dataFields) {
            keyToFieldMap.put(field.getKey(), field);
        }
        for (String key : keys) {
            DataField field = (DataField)keyToFieldMap.get(key);
            if (field == null) continue;
            FieldMetaData meta = new FieldMetaData(field);
            metaData.add(meta);
            String dataTableKey = field.getDataTableKey();
            Map fDmap = (Map)fieldDeployInfoMap.get(dataTableKey);
            if (fDmap == null) {
                fDmap = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(dataTableKey).stream().collect(Collectors.groupingBy(DataFieldDeployInfo::getDataFieldKey, Collectors.toList()));
                fieldDeployInfoMap.put(dataTableKey, fDmap);
            }
            meta.setDeployInfos((List)fDmap.get(field.getKey()));
        }
        return metaData;
    }

    public List<IMetaData> getMetaData(Iterator<String> fieldStr) {
        ArrayList<String> keys = new ArrayList<String>();
        while (fieldStr.hasNext()) {
            keys.add(fieldStr.next());
        }
        return this.getMetaData(keys);
    }

    public Set<String> getTableNames(List<IMetaData> metaData) {
        HashSet<String> tables = new HashSet<String>();
        for (IMetaData metaDatum : metaData) {
            for (DataFieldDeployInfo deployInfo : metaDatum.getDeployInfos()) {
                tables.add(deployInfo.getTableName());
            }
        }
        return tables;
    }

    public DataTableType getDataTableType(IMetaData metaData) {
        List deployInfos = metaData.getDeployInfos();
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(((DataFieldDeployInfo)deployInfos.get(0)).getDataTableKey());
        return dataTable.getDataTableType();
    }

    public List<TableDimSet> getTableDim(List<IMetaData> metaData) {
        ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
        IDataAssist iDataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        HashSet<String> dataTables = new HashSet<String>();
        for (IMetaData metaDatum : metaData) {
            String dataTableKey = metaDatum.getDataField().getDataTableKey();
            dataTables.add(dataTableKey);
        }
        ArrayList<TableDimSet> list = new ArrayList<TableDimSet>();
        List tables = this.runtimeDataSchemeService.getDataTables(new ArrayList(dataTables));
        for (DataTable table : tables) {
            TableDimSet tableDimSet = new TableDimSet();
            tableDimSet.setDataTable(table);
            String[] bizKeys = table.getBizKeys();
            List fields = this.runtimeDataSchemeService.getDataFields(Arrays.stream(bizKeys).collect(Collectors.toList()));
            for (DataField field : fields) {
                String dimensionName = iDataAssist.getDimensionName((FieldDefine)((DataFieldDTO)field));
                tableDimSet.getDimName2FieldCode().put(dimensionName, field.getCode());
                tableDimSet.getFieldCode2DimName().put(field.getCode(), dimensionName);
                DataFieldKind dataFieldKind = field.getDataFieldKind();
                if (DataFieldKind.PUBLIC_FIELD_DIM == dataFieldKind) {
                    tableDimSet.getMasterKeyName().add(dimensionName);
                    tableDimSet.getDimField().add(field);
                } else {
                    tableDimSet.getTableKeyName().add(dimensionName);
                    tableDimSet.getTableDimField().add(field);
                }
                if (!field.getCode().equals("MDCODE")) continue;
                tableDimSet.setDwDimName(dimensionName);
            }
            list.add(tableDimSet);
        }
        return list;
    }

    public IMetaData getMetaDataByField(String fieldKey) {
        if (this.metaDataMap == null) {
            return null;
        }
        return this.metaDataMap.get(fieldKey);
    }

    public IMetaData addMetaDataByField(String fieldKey) {
        IMetaData meta = this.getMetaDataByField(fieldKey);
        if (meta == null) {
            if (this.metaData == null) {
                this.metaData = new ArrayList<IMetaData>();
                this.metaDataMap = new HashMap<String, IMetaData>();
            }
            List<IMetaData> metaData = this.initMetaData(Collections.singletonList(fieldKey));
            for (IMetaData metaDatum : metaData) {
                this.metaData.add(metaDatum);
                this.metaDataMap.put(metaDatum.getFieldKey(), metaDatum);
            }
            meta = this.getMetaDataByField(fieldKey);
        }
        return meta;
    }
}


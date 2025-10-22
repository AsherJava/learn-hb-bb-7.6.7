/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class DataFieldUtils {
    public static TableModelDefine getNvwaTableDefineByNrTableKey(String nrTableKey) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(nrTableKey);
        TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByCode(dataTable.getCode());
        return tableModelDefine;
    }

    public static ColumnModelDefine getNvwaColumnDefineByNrFieldKey(String nrFieldKey) {
        Map<String, ColumnModelDefine> fieldKey2NvwaColumnDefineMap = DataFieldUtils.getNrFieldKey2NvwaColumnDefineMapByNrFieldKey(Arrays.asList(nrFieldKey));
        ColumnModelDefine columnModelDefine = fieldKey2NvwaColumnDefineMap.get(nrFieldKey);
        return columnModelDefine;
    }

    public static FieldDefine getFieldDefineByNrFieldKey(String nrFieldKey) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        FieldDefine fieldDefine = RuntimeDefinitionTransfer.toFieldDefine((DataField)runtimeDataSchemeService.getDataField(nrFieldKey));
        return fieldDefine;
    }

    public static DataFieldDeployInfo getDeployInfoByNrFieldKey(String nrFieldKey) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        List deployInfos = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{nrFieldKey});
        Map nrFieldKey2DeployInfoMap = deployInfos.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, Function.identity()));
        DataFieldDeployInfo dataFieldDeployInfo = (DataFieldDeployInfo)nrFieldKey2DeployInfoMap.get(nrFieldKey);
        return dataFieldDeployInfo;
    }

    public static Map<String, DataFieldDeployInfo> getNrFieldKey2DeployInfoMapByNrFieldKeys(Collection<String> nrFieldKeys) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        List deployInfos = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(nrFieldKeys.toArray(new String[0]));
        Map<String, DataFieldDeployInfo> nrFieldKey2DeployInfoMap = deployInfos.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, Function.identity()));
        return nrFieldKey2DeployInfoMap;
    }

    public static Map<String, ColumnModelDefine> getNrFieldKey2NvwaColumnDefineMapByNrFieldKey(Collection<String> nrFieldKeys) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        List dataFields = runtimeDataSchemeService.getDataFields(new ArrayList<String>(nrFieldKeys));
        if (CollectionUtils.isEmpty(dataFields)) {
            return Collections.emptyMap();
        }
        List deployInfo = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(nrFieldKeys.toArray(new String[0]));
        if (CollectionUtils.isEmpty(deployInfo)) {
            return Collections.emptyMap();
        }
        Map<String, String> nrFieldKey2NvwaColumnIdMap = deployInfo.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, DataFieldDeployInfo::getColumnModelKey));
        List columnKeys = deployInfo.stream().map(DataFieldDeployInfo::getColumnModelKey).collect(Collectors.toList());
        Map nvwaColumn2ColumnModelDefineMap = dataModelService.getColumnModelDefinesByIDs(columnKeys).stream().collect(Collectors.toMap(IModelDefineItem::getID, Function.identity()));
        HashMap<String, ColumnModelDefine> nrFieldKey2NvwaColumnDefineMap = new HashMap<String, ColumnModelDefine>();
        nrFieldKey2NvwaColumnIdMap.forEach((nrFieldKey, nvwaColumnId) -> {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)nvwaColumn2ColumnModelDefineMap.get(nvwaColumnId);
            if (columnModelDefine == null) {
                return;
            }
            nrFieldKey2NvwaColumnDefineMap.put((String)nrFieldKey, columnModelDefine);
        });
        return nrFieldKey2NvwaColumnDefineMap;
    }

    public static Map<String, String> getNrFieldKey2NvwaColumnIdMapByNrFieldKey(Set<String> nrFieldKeys) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        List dataFields = runtimeDataSchemeService.getDataFields(new ArrayList<String>(nrFieldKeys));
        if (CollectionUtils.isEmpty(dataFields)) {
            return Collections.emptyMap();
        }
        List deployInfo = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(nrFieldKeys.toArray(new String[0]));
        if (CollectionUtils.isEmpty(deployInfo)) {
            return Collections.emptyMap();
        }
        Map<String, String> nrFieldKey2NvwaColumnIdMap = deployInfo.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, DataFieldDeployInfo::getColumnModelKey));
        return nrFieldKey2NvwaColumnIdMap;
    }

    public static Map<String, FieldDefine> getNrFieldKey2FieldDefineMapByNvwaTableId(String nvwaTableId) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        String nrTableKey = runtimeDataSchemeService.getDataTableByTableModel(nvwaTableId);
        Map<String, FieldDefine> nrFieldKey2FieldDefineMap = DataFieldUtils.getNrFieldKey2FieldDefineMapByNrTableKey(nrTableKey);
        return nrFieldKey2FieldDefineMap;
    }

    public static Map<String, FieldDefine> getNrFieldKey2FieldDefineMapByNrTableKey(String nrTableKey) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        if (ObjectUtils.isEmpty(nrTableKey)) {
            return Collections.emptyMap();
        }
        List dataFields = runtimeDataSchemeService.getDataFieldByTable(nrTableKey);
        if (CollectionUtils.isEmpty(dataFields)) {
            return Collections.emptyMap();
        }
        HashMap<String, FieldDefine> nrFieldKey2FieldDefineMap = new HashMap<String, FieldDefine>();
        dataFields.stream().forEach(dataField -> {
            String key = dataField.getKey();
            FieldDefine fieldDefine = RuntimeDefinitionTransfer.toFieldDefine((DataField)dataField);
            nrFieldKey2FieldDefineMap.put(key, fieldDefine);
        });
        return nrFieldKey2FieldDefineMap;
    }

    public static Map<String, FieldDefine> getNrFieldKey2FieldDefineMapByNrTableName(String tableName) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        if (ObjectUtils.isEmpty(tableName)) {
            return Collections.emptyMap();
        }
        List dataFields = runtimeDataSchemeService.getDataFieldByTableCode(tableName);
        if (CollectionUtils.isEmpty(dataFields)) {
            return Collections.emptyMap();
        }
        HashMap<String, FieldDefine> nrFieldKey2FieldDefineMap = new HashMap<String, FieldDefine>();
        dataFields.stream().forEach(dataField -> {
            String key = dataField.getKey();
            FieldDefine fieldDefine = RuntimeDefinitionTransfer.toFieldDefine((DataField)dataField);
            nrFieldKey2FieldDefineMap.put(key, fieldDefine);
        });
        return nrFieldKey2FieldDefineMap;
    }

    public static boolean isNumber(ColumnModelType type) {
        return ColumnModelType.DOUBLE == type || ColumnModelType.BIGDECIMAL == type || ColumnModelType.INTEGER == type;
    }
}


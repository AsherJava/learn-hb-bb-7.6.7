/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.subdatabase.controller.Impl;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.provider.SubDataBaseCustomTableProvider;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SubDatabaseTableNamesProviderImpl
implements SubDatabaseTableNamesProvider {
    @Autowired
    private SubDataBaseInfoProvider subDataBaseInfoProvider;
    @Autowired
    private SubDataBaseService subDataBaseService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired(required=false)
    private List<SubDataBaseCustomTableProvider> customTableProviders;
    @Autowired
    private RuntimeDataSchemeServiceImpl runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;

    public String getSubDatabaseTableName(String taskKey, String tableName) {
        String otherSubDataBaseCode = this.getOtherSubDataBaseCode();
        if (null != otherSubDataBaseCode) {
            return otherSubDataBaseCode + tableName;
        }
        SubDataBase curDataBase = this.getCurDataBase();
        if (curDataBase != null) {
            if (tableName.equals(curDataBase.getDefaultDBOrgCateGoryName())) {
                return curDataBase.getOrgCateGoryName();
            }
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            String dataSchemeKey = taskDefine.getDataScheme();
            if (dataSchemeKey != null) {
                Set<String> tableNames;
                if (curDataBase.getDataScheme().equals(dataSchemeKey) && (tableNames = this.subDataBaseService.getTablesFromDataScheme(dataSchemeKey)).contains(tableName)) {
                    return curDataBase.getCode() + tableName;
                }
                if (!CollectionUtils.isEmpty(this.customTableProviders)) {
                    for (SubDataBaseCustomTableProvider customTableProvider : this.customTableProviders) {
                        if (!customTableProvider.getCustomTableNames(taskKey).contains(tableName)) continue;
                        return curDataBase.getCode() + tableName;
                    }
                }
            }
        }
        return tableName;
    }

    private String getOtherSubDataBaseCode() {
        ContextExtension nrDtExtension = NpContextHolder.getContext().getExtension("NRDT");
        if (null != nrDtExtension && null != nrDtExtension.get("NRDT.var.newDatabaseCode")) {
            Object otherSubDataBaseCode = nrDtExtension.get("NRDT.var.newDatabaseCode");
            return (String)otherSubDataBaseCode;
        }
        return null;
    }

    private SubDataBase getCurDataBase() {
        return this.subDataBaseInfoProvider.getCurDataBase();
    }

    public List<ColumnModelDefine> getSubDatabaseTableColumns(String schemeKey, List<String> fieldKeys) {
        if (CollectionUtils.isEmpty(fieldKeys)) {
            Collections.emptyList();
        }
        SubDataBase curDataBase = this.getCurDataBase();
        List<String> defaultColumnModelKeys = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(fieldKeys.toArray(new String[fieldKeys.size()])).stream().map(DataFieldDeployInfo::getColumnModelKey).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(defaultColumnModelKeys)) {
            return Collections.emptyList();
        }
        if (curDataBase != null && curDataBase.getDataScheme().equals(schemeKey)) {
            List<String> subDataBaseColumnModelKeys = defaultColumnModelKeys.stream().map(defaultColumnModelKey -> defaultColumnModelKey + curDataBase.getCode()).collect(Collectors.toList());
            return this.getColumnModelDefines(subDataBaseColumnModelKeys);
        }
        return this.getColumnModelDefines(defaultColumnModelKeys);
    }

    private List<ColumnModelDefine> getColumnModelDefines(List<String> columnModelKeys) {
        return this.dataModelService.getColumnModelDefinesByIDs(columnModelKeys);
    }

    public Map<String, List<ColumnModelDefine>> getSubDatabaseFieldKey2Columns(String schemeKey, List<String> fieldKeys) {
        HashMap<String, List<ColumnModelDefine>> resultMap = new HashMap<String, List<ColumnModelDefine>>();
        SubDataBase curDataBase = this.getCurDataBase();
        List<ColumnModelDefine> columnsModels = this.getSubDatabaseTableColumns(schemeKey, fieldKeys);
        if (CollectionUtils.isEmpty(columnsModels)) {
            return resultMap;
        }
        Map<String, ColumnModelDefine> columnMap = columnsModels.stream().collect(Collectors.toMap(IModelDefineItem::getID, v -> v));
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(fieldKeys.toArray(new String[fieldKeys.size()]));
        Map<String, List<DataFieldDeployInfo>> fieldColumnMap = deployInfos.stream().collect(Collectors.groupingBy(DataFieldDeployInfo::getDataFieldKey));
        for (String fieldKey : fieldColumnMap.keySet()) {
            ArrayList<ColumnModelDefine> columnModels = (ArrayList<ColumnModelDefine>)resultMap.get(fieldKey);
            if (CollectionUtils.isEmpty(columnModels)) {
                columnModels = new ArrayList<ColumnModelDefine>();
                columnModels.addAll(this.getColumnModelDefines(curDataBase, schemeKey, fieldColumnMap.get(fieldKey), columnMap));
                resultMap.put(fieldKey, columnModels);
                continue;
            }
            columnModels.addAll(this.getColumnModelDefines(curDataBase, schemeKey, fieldColumnMap.get(fieldKey), columnMap));
        }
        return resultMap;
    }

    private List<ColumnModelDefine> getColumnModelDefines(SubDataBase subDataBase, String schemeKey, List<DataFieldDeployInfo> deployInfos, Map<String, ColumnModelDefine> columnMap) {
        ArrayList<ColumnModelDefine> result = new ArrayList<ColumnModelDefine>();
        for (DataFieldDeployInfo deployInfo : deployInfos) {
            ColumnModelDefine columnModelDefine = columnMap.get(this.getColumnModelKey(subDataBase, schemeKey, deployInfo.getColumnModelKey()));
            result.add(columnModelDefine);
        }
        return result;
    }

    private String getColumnModelKey(SubDataBase subDataBase, String schemeKey, String defaultColumnModelKey) {
        if (subDataBase != null && subDataBase.getDataScheme().equals(schemeKey)) {
            return defaultColumnModelKey + subDataBase.getCode();
        }
        return defaultColumnModelKey;
    }
}


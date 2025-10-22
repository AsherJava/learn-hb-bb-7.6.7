/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataType
 *  com.jiuqi.bi.quickreport.writeback.IWritebackMetaDataProvider
 *  com.jiuqi.bi.quickreport.writeback.SearchBean
 *  com.jiuqi.bi.quickreport.writeback.TableField
 *  com.jiuqi.bi.quickreport.writeback.TableInfo
 *  com.jiuqi.bi.quickreport.writeback.TableModel
 *  com.jiuqi.bi.quickreport.writeback.WritebackException
 *  com.jiuqi.bi.quickreport.writeback.WritebackFolder
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.quickreport.NrWritebackParam;
import com.jiuqi.bi.quickreport.writeback.IWritebackMetaDataProvider;
import com.jiuqi.bi.quickreport.writeback.SearchBean;
import com.jiuqi.bi.quickreport.writeback.TableField;
import com.jiuqi.bi.quickreport.writeback.TableInfo;
import com.jiuqi.bi.quickreport.writeback.TableModel;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.quickreport.writeback.WritebackFolder;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NrWritebackMetaDataProvider
implements IWritebackMetaDataProvider {
    private NrWritebackParam param;

    public NrWritebackMetaDataProvider(NrWritebackParam param) {
        this.param = param;
    }

    public List<WritebackFolder> getTableFolders(String parent) throws WritebackException {
        ArrayList<WritebackFolder> folders = new ArrayList<WritebackFolder>();
        if (StringUtils.isEmpty((String)parent)) {
            WritebackFolder folder = this.createRootFolder();
            folders.add(folder);
        } else {
            List dataSchemes;
            DataScheme dataScheme = this.param.dataSchemeService.getDataScheme(parent);
            if (dataScheme != null) {
                List groups = this.param.dataSchemeService.getDataGroupByScheme(parent);
                if (groups != null) {
                    for (DataGroup group : groups) {
                        WritebackFolder folder = this.dataGroupToWritebackFolder(group);
                        folders.add(folder);
                    }
                }
                return folders;
            }
            DataGroup parentGroup = this.param.dataSchemeService.getDataGroup(parent);
            List groups = null;
            boolean isSchemeGroup = false;
            if (parentGroup == null || parentGroup.getDataGroupKind() == DataGroupKind.SCHEME_GROUP) {
                isSchemeGroup = true;
                groups = this.param.designDataSchemeService.getDataGroupByParent(parent);
            } else {
                groups = this.param.dataSchemeService.getDataGroupByParent(parent);
            }
            if (groups != null) {
                for (DataGroup group : groups) {
                    if (isSchemeGroup && !this.param.dataSchemeAuthService.canReadGroup(group.getKey())) continue;
                    WritebackFolder folder = this.dataGroupToWritebackFolder(group);
                    folders.add(folder);
                }
            }
            if ((dataSchemes = this.param.dataSchemeService.getDataSchemeByParent(parent)) != null) {
                for (DataScheme scheme : dataSchemes) {
                    if (!this.param.dataSchemeAuthService.canReadScheme(scheme.getKey())) continue;
                    WritebackFolder folder = this.dataSchemeToWritebackFolder(scheme);
                    folders.add(folder);
                }
            }
        }
        return folders;
    }

    private WritebackFolder createRootFolder() {
        WritebackFolder folder = new WritebackFolder();
        folder.setId("00000000-0000-0000-0000-000000000000");
        folder.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
        return folder;
    }

    public TableModel getTableModel(String tableCode) throws WritebackException {
        DataTable table = this.param.dataSchemeService.getDataTableByCode(tableCode);
        if (table == null) {
            table = this.param.dataSchemeService.getDataTable(tableCode);
        }
        TableModel tableModel = new TableModel();
        tableModel.setName(table.getKey());
        tableModel.setTitle(table.getTitle());
        List keyFields = this.param.dataSchemeService.getBizDataFieldByTableKey(table.getKey());
        for (DataField keyField : keyFields) {
            if (keyField.getCode().equals("BIZKEYORDER")) continue;
            TableField tableField = this.dataFieldToTableField(keyField);
            tableModel.getDefaultFields().add(tableField);
            tableModel.getKeyFields().add(keyField.getCode());
        }
        return tableModel;
    }

    private TableField dataFieldToTableField(DataField dataField) {
        Integer precision;
        TableField tableField = new TableField();
        tableField.setName(dataField.getCode());
        DataFieldType dataFieldType = dataField.getDataFieldType();
        DataType dataType = DataType.valueOf((int)dataFieldType.getValue());
        if (dataFieldType == DataFieldType.BIGDECIMAL) {
            dataType = DataType.DOUBLE;
        }
        tableField.setDataType(dataType);
        tableField.setTitle(dataField.getTitle());
        Integer decimal = dataField.getDecimal();
        if (decimal != null) {
            tableField.setDecimal(decimal.intValue());
        }
        if ((precision = dataField.getPrecision()) != null) {
            tableField.setPrecision(precision.intValue());
        }
        return tableField;
    }

    public List<TableInfo> getTables(String parent) throws WritebackException {
        ArrayList<TableInfo> tableInfos = new ArrayList<TableInfo>();
        List tables = null;
        DataScheme dataScheme = this.param.dataSchemeService.getDataScheme(parent);
        tables = dataScheme != null ? this.param.dataSchemeService.getDataTableByScheme(parent) : this.param.dataSchemeService.getDataTableByGroup(parent);
        if (tables == null) {
            return tableInfos;
        }
        for (DataTable table : tables) {
            TableInfo tableInfo = this.dataTableToTableInfo(table);
            tableInfos.add(tableInfo);
        }
        return tableInfos;
    }

    private TableInfo dataTableToTableInfo(DataTable table) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setName(table.getCode());
        tableInfo.setTitle(table.getTitle());
        return tableInfo;
    }

    private WritebackFolder dataSchemeToWritebackFolder(DataScheme scheme) {
        WritebackFolder folder = new WritebackFolder();
        folder.setId(scheme.getKey());
        folder.setTitle(scheme.getTitle());
        return folder;
    }

    private WritebackFolder dataGroupToWritebackFolder(DataGroup group) {
        WritebackFolder folder = new WritebackFolder();
        folder.setId(group.getKey());
        folder.setTitle(group.getTitle());
        return folder;
    }

    public List<TableInfo> searchTables(SearchBean searchBean) throws WritebackException {
        String keyword = searchBean.getFilter();
        int type = 0;
        for (DataTableType value : DataTableType.values()) {
            type += value.getValue();
        }
        List dataTables = this.param.dataSchemeService.searchBy(new ArrayList(), keyword, type);
        ArrayList<TableInfo> tableInfos = new ArrayList<TableInfo>();
        for (DataTable table : dataTables) {
            tableInfos.add(this.dataTableToTableInfo(table));
            if (searchBean.getMaxSize() <= 1 || searchBean.getMaxSize() > tableInfos.size()) continue;
            break;
        }
        return tableInfos;
    }

    public List<WritebackFolder> getPaths(String tableCode) throws WritebackException {
        DataTable dataTable = this.param.dataSchemeService.getDataTableByCode(tableCode);
        if (dataTable == null) {
            dataTable = this.param.dataSchemeService.getDataTable(tableCode);
        }
        String groupKey = dataTable.getDataGroupKey();
        ArrayList<WritebackFolder> paths = new ArrayList<WritebackFolder>();
        this.addGroupFolders(groupKey, paths);
        DataScheme dataScheme = this.param.dataSchemeService.getDataScheme(dataTable.getDataSchemeKey());
        paths.add(this.dataSchemeToWritebackFolder(dataScheme));
        groupKey = dataScheme.getDataGroupKey();
        this.addSchemeGroupFolders(groupKey, paths);
        Collections.reverse(paths);
        return paths;
    }

    protected void addSchemeGroupFolders(String groupKey, List<WritebackFolder> paths) throws WritebackException {
        if (StringUtils.isNotEmpty((String)groupKey) && !"00000000-0000-0000-0000-000000000000".equals(groupKey)) {
            DesignDataGroup group = this.param.designDataSchemeService.getDataGroup(groupKey);
            if (group == null) {
                throw new WritebackException("\u6839\u636egroupKey:" + groupKey + "\u6ca1\u6709\u627e\u5230\u6570\u636e\u65b9\u6848\u5206\u7ec4");
            }
            paths.add(this.dataGroupToWritebackFolder((DataGroup)group));
            while (StringUtils.isNotEmpty((String)group.getParentKey())) {
                DesignDataGroup parentGroup = this.param.designDataSchemeService.getDataGroup(group.getParentKey());
                if (parentGroup == null) {
                    if (!"00000000-0000-0000-0000-000000000000".equals(group.getParentKey())) break;
                    WritebackFolder root = this.createRootFolder();
                    paths.add(root);
                    break;
                }
                paths.add(this.dataGroupToWritebackFolder((DataGroup)parentGroup));
                group = parentGroup;
            }
        }
    }

    protected void addGroupFolders(String groupKey, List<WritebackFolder> paths) throws WritebackException {
        if (StringUtils.isNotEmpty((String)groupKey) && !"00000000-0000-0000-0000-000000000000".equals(groupKey)) {
            DataGroup group = this.param.dataSchemeService.getDataGroup(groupKey);
            if (group == null) {
                throw new WritebackException("\u6839\u636egroupKey:" + groupKey + "\u6ca1\u6709\u627e\u5230\u6570\u636e\u5206\u7ec4");
            }
            paths.add(this.dataGroupToWritebackFolder(group));
            while (StringUtils.isNotEmpty((String)group.getParentKey())) {
                group = this.param.dataSchemeService.getDataGroup(group.getParentKey());
                paths.add(this.dataGroupToWritebackFolder(group));
            }
        }
    }
}


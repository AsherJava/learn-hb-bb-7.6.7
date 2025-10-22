/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.model.DSV
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.adhoc.model.FieldType
 *  com.jiuqi.bi.adhoc.model.KeyInfo
 *  com.jiuqi.bi.adhoc.model.RelationInfo
 *  com.jiuqi.bi.adhoc.model.RelationMode
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.adhoc.model.TableType
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.dao.IDataTableMapDao
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO
 *  com.jiuqi.nr.datascheme.internal.service.DataTableRelService
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 */
package com.jiuqi.nr.bql.dsv.adapter;

import com.jiuqi.bi.adhoc.ext.model.DSV;
import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.adhoc.model.FieldType;
import com.jiuqi.bi.adhoc.model.KeyInfo;
import com.jiuqi.bi.adhoc.model.RelationInfo;
import com.jiuqi.bi.adhoc.model.RelationMode;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.adhoc.model.TableType;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.bql.dsv.adapter.DSVAdapter;
import com.jiuqi.nr.bql.dsv.adapter.DSVContext;
import com.jiuqi.nr.bql.dsv.adapter.DimensionTableAdapter;
import com.jiuqi.nr.bql.dsv.adapter.FieldInfoAdapter;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dao.IDataTableMapDao;
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelService;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableInfoAdapter {
    private static final ILogger logger = DSVAdapter.getLogger();
    @Autowired
    private DataTableRelService dataTableRelService;
    @Autowired
    private FieldInfoAdapter fieldInfoAdapter;
    @Autowired
    private IDataTableMapDao tableMapDAO;
    @Autowired
    private DimensionTableAdapter dimensionTableAdapter;

    public List<TableInfo> getAllTableInfos(DSVContext context, String dataSchemeKey, DSV dsv) {
        ArrayList<TableInfo> tableInfos = new ArrayList<TableInfo>();
        List<DataTable> dataTables = context.getAllDataTables();
        HashMap<String, TableInfo> dimensionTableMap = new HashMap<String, TableInfo>();
        ArrayList<TableInfo> subTableInfos = new ArrayList<TableInfo>();
        for (DataTable dataTable : dataTables) {
            try {
                TableInfo tableInfo = this.getTableInfoByDataTable(context, dsv, dataTable);
                if (dataTable.getDataTableType() == DataTableType.SUB_TABLE) {
                    this.buildTableRelation(context, dataTable, tableInfo);
                    subTableInfos.add(tableInfo);
                }
                this.dimensionTableAdapter.bindDimensionTables(context, dsv, tableInfo, dataTable, dimensionTableMap);
                this.setTableKeys(context, dataTable, tableInfo);
                tableInfos.add(tableInfo);
                if (dataTable.getDataTableType() != DataTableType.MD_INFO) continue;
                this.adaptMdInfoTable(context, tableInfo);
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u65b9\u6848\uff1a" + context.getDataScheme().getCode() + "    \u6570\u636e\u8868 \u3010 " + dataTable.getCode() + "\u3011 \u9002\u914d dsv\u6a21\u578b\u51fa\u9519\uff01", (Throwable)e);
            }
        }
        if (subTableInfos.size() > 0) {
            Map tableInfoMap = tableInfos.stream().collect(Collectors.toMap(TableInfo::getName, Function.identity(), (oldValue, newValue) -> newValue));
            for (TableInfo st : subTableInfos) {
                Optional<RelationInfo> parentRelation = st.getRelations().stream().filter(r -> r.getMode() == RelationMode.MORE_TO_ONE).findAny();
                if (!parentRelation.isPresent()) continue;
                TableInfo parentTable = (TableInfo)tableInfoMap.get(parentRelation.get().getTargetTable());
                KeyInfo relKeyInfo = new KeyInfo();
                relKeyInfo.setName(parentTable.getName() + "_relkey");
                relKeyInfo.getFields().addAll(parentRelation.get().getFieldMaps().values());
                parentTable.getKeys().add(relKeyInfo);
            }
        }
        for (TableInfo tableInfo : dimensionTableMap.values()) {
            tableInfos.add(tableInfo);
        }
        return tableInfos;
    }

    private void adaptMdInfoTable(DSVContext context, TableInfo tableInfo) {
        int index = 0;
        int timeKeyIndex = -1;
        for (FieldInfo fieldInfo : tableInfo.getFields()) {
            if (fieldInfo.isTimeKey()) {
                timeKeyIndex = index;
            } else {
                fieldInfo.setFieldType(FieldType.GENERAL_DIM);
                fieldInfo.setKeyField(fieldInfo.getName());
                fieldInfo.setNameField(fieldInfo.getName());
            }
            ++index;
        }
        if (timeKeyIndex >= 0) {
            tableInfo.getFields().remove(timeKeyIndex);
        }
        tableInfo.setType(TableType.DIM);
        context.setMdInfoTable(tableInfo);
        RelationInfo relationInfo = new RelationInfo();
        relationInfo.setMode(RelationMode.ONE_TO_ONE);
        relationInfo.setSrcTable(context.getMdTable().getName());
        relationInfo.setTargetTable(tableInfo.getName());
        relationInfo.getFieldMaps().put(context.getMdTable().getKeyField().getName(), "MDCODE");
        tableInfo.getRelations().clear();
        context.getMdTable().getRelations().add(relationInfo);
        KeyInfo primaryKey = new KeyInfo();
        primaryKey.setName(tableInfo.getName() + "_primaryKey");
        primaryKey.getFields().add("MDCODE");
        tableInfo.getKeys().clear();
        tableInfo.getKeys().add(primaryKey);
    }

    public TableInfo getTableInfoByDataTable(DSVContext context, DSV dsv, DataTable dataTable) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setConnName(dsv.getConnName());
        tableInfo.setDsvName(dsv.getName());
        tableInfo.setGuid(dataTable.getKey());
        tableInfo.setName(dataTable.getCode());
        tableInfo.setTitle(dataTable.getTitle());
        tableInfo.setType(TableType.DATA);
        tableInfo.setTimeStamp(dataTable.getUpdateTime().toEpochMilli());
        tableInfo.setPhysicalSchemaName(dsv.getConnName());
        tableInfo.setPhysicalTableName(dataTable.getCode());
        String dataGroupKey = dataTable.getDataGroupKey();
        if (StringUtils.isNotEmpty((String)dataGroupKey)) {
            tableInfo.getPropMap().put("NR.dataTableGroup", dataGroupKey);
        }
        DataTableMapDO tableMap = null;
        if (context.isQueryDataScheme() && (tableMap = this.tableMapDAO.get(dataTable.getKey())) != null) {
            tableInfo.getPropMap().put("NR.dataTableMap", tableMap);
        }
        if (dataTable.getDesc() != null) {
            tableInfo.getPropMap().put("description", dataTable.getDesc());
        }
        List<FieldInfo> fieldInfos = this.fieldInfoAdapter.getFieldInfosByTable(context, dsv, dataTable, tableMap);
        tableInfo.getFields().addAll(fieldInfos);
        return tableInfo;
    }

    private void setTableKeys(DSVContext context, DataTable dataTable, TableInfo tableInfo) {
        String[] bizKeys = dataTable.getBizKeys();
        if (bizKeys != null) {
            boolean bizKeyOrderIsBizKey = false;
            KeyInfo bizKeyInfo = new KeyInfo();
            bizKeyInfo.setName(tableInfo.getName() + "_bizkey");
            for (String bizKey : bizKeys) {
                DataField keyField = context.getDataField(bizKey);
                String keyFieldName = keyField.getCode();
                if (keyFieldName.equals("DATATIME") && !context.isCustomPeriod()) {
                    keyFieldName = PeriodTableColumn.TIMEKEY.name();
                } else if (keyFieldName.equals("BIZKEYORDER")) {
                    bizKeyOrderIsBizKey = true;
                } else if (keyField.getRefDataEntityKey() != null && context.dimCanIgnroe(keyField.getRefDataEntityKey())) continue;
                bizKeyInfo.getFields().add(keyFieldName);
            }
            if (!bizKeyOrderIsBizKey) {
                tableInfo.getKeys().add(bizKeyInfo);
                FieldInfo bizKeyFieldInfo = tableInfo.findField("BIZKEYORDER");
                if (bizKeyFieldInfo != null) {
                    this.addPrimaryKey(tableInfo);
                }
            } else {
                this.addPrimaryKey(tableInfo);
            }
        }
    }

    private void addPrimaryKey(TableInfo tableInfo) {
        KeyInfo primaryKey = new KeyInfo();
        primaryKey.setName(tableInfo.getName() + "_primaryKey");
        primaryKey.getFields().add("BIZKEYORDER");
        tableInfo.getKeys().add(primaryKey);
    }

    private void buildTableRelation(DSVContext context, DataTable dataTable, TableInfo tableInfo) {
        DataTableRel tableRef = this.dataTableRelService.getBySrcTable(dataTable.getKey());
        if (tableRef != null) {
            RelationInfo relationInfo = new RelationInfo();
            relationInfo.setMode(RelationMode.MORE_TO_ONE);
            relationInfo.setSrcTable(dataTable.getCode());
            DataTable targetTable = context.getDataTable(tableRef.getDesTableKey());
            relationInfo.setTargetTable(targetTable.getCode());
            for (int i = 0; i < tableRef.getSrcFieldKeys().length; ++i) {
                DataField srcField = context.getDataField(tableRef.getSrcFieldKeys()[i]);
                DataField targetField = context.getDataField(tableRef.getDesFieldKeys()[i]);
                relationInfo.getFieldMaps().put(srcField.getCode(), targetField.getCode());
            }
            tableInfo.getRelations().add(relationInfo);
        }
    }
}


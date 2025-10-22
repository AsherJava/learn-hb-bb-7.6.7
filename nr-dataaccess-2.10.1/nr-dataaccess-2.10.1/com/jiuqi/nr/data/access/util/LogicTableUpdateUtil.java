/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.IndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.data.access.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.access.model.ColumnInfo;
import com.jiuqi.nr.data.access.model.IndexInfo;
import com.jiuqi.nr.data.access.model.LogicTableInfo;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.IndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class LogicTableUpdateUtil {
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;

    public List<ColumnInfo> initPrimaryColumns(DesignTaskDefine taskDefine, boolean reportDim) {
        String dataScheme = taskDefine.getDataScheme();
        return this.initPrimaryColumns(dataScheme, reportDim);
    }

    public List<ColumnInfo> initPrimaryColumns(String dataSchemeKey, boolean reportDim) {
        ArrayList<ColumnInfo> primaryColumns = new ArrayList<ColumnInfo>();
        List dimesion = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        List reportDims = this.designDataSchemeService.getReportDimension(dataSchemeKey);
        List reportDimKeys = reportDims.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
        List filterDims = dimesion.stream().filter(e -> e.getDimensionType() != DimensionType.UNIT_SCOPE).collect(Collectors.toList());
        for (DesignDataDimension dim : filterDims) {
            IEntityDefine entity;
            DimensionType dimType = dim.getDimensionType();
            String entityKey = dim.getDimKey();
            String defaultVal = dim.getDefaultValue();
            if (dimType.equals((Object)DimensionType.UNIT)) {
                primaryColumns.add(new ColumnInfo("MDCODE", "MDCODE", ColumnModelType.STRING, 50, this.queryEntityTableBizKeyByEntityID(entityKey), true));
                continue;
            }
            if (dimType.equals((Object)DimensionType.PERIOD)) {
                primaryColumns.add(new ColumnInfo("PERIOD", "PERIOD", ColumnModelType.STRING, 9, this.queryPeriodBizKey(entityKey), true));
                continue;
            }
            if (reportDim) {
                if (!reportDimKeys.contains(entityKey)) continue;
                if ("ADJUST".equals(entityKey)) {
                    primaryColumns.add(new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 50, "", true, defaultVal));
                    continue;
                }
                entity = this.iEntityMetaService.queryEntity(entityKey);
                primaryColumns.add(new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), true));
                continue;
            }
            if ("ADJUST".equals(entityKey)) {
                primaryColumns.add(new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 50, "", true, defaultVal));
                continue;
            }
            entity = this.iEntityMetaService.queryEntity(entityKey);
            primaryColumns.add(new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), true));
        }
        return primaryColumns;
    }

    public List<ColumnInfo> initPrimaryFiterDimColumns(String dataSchemeKey, boolean reportDim) {
        ArrayList<ColumnInfo> primaryColumns = new ArrayList<ColumnInfo>();
        List dimesion = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        List reportDims = this.designDataSchemeService.getReportDimension(dataSchemeKey);
        List reportDimKeys = reportDims.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
        List filterDims = dimesion.stream().filter(e -> e.getDimensionType() != DimensionType.UNIT_SCOPE).collect(Collectors.toList());
        for (DesignDataDimension dim : filterDims) {
            IEntityDefine entity;
            DimensionType dimType = dim.getDimensionType();
            String entityKey = dim.getDimKey();
            if (dimType.equals((Object)DimensionType.UNIT)) {
                primaryColumns.add(new ColumnInfo("MDCODE", "MDCODE", ColumnModelType.STRING, 50, this.queryEntityTableBizKeyByEntityID(entityKey), true));
                continue;
            }
            if (dimType.equals((Object)DimensionType.PERIOD)) {
                primaryColumns.add(new ColumnInfo("PERIOD", "PERIOD", ColumnModelType.STRING, 9, this.queryPeriodBizKey(entityKey), true));
                continue;
            }
            if (reportDim) {
                if (!reportDimKeys.contains(entityKey)) continue;
                if ("ADJUST".equals(entityKey)) {
                    primaryColumns.add(new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 50, "", true));
                    continue;
                }
                entity = this.iEntityMetaService.queryEntity(entityKey);
                primaryColumns.add(new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), true));
                continue;
            }
            if ("ADJUST".equals(entityKey)) {
                primaryColumns.add(new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 50, "", false));
                continue;
            }
            entity = this.iEntityMetaService.queryEntity(entityKey);
            primaryColumns.add(new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), false));
        }
        return primaryColumns;
    }

    public List<String> createAndDeployTable(LogicTableInfo logicTableInfo, boolean noDDL) throws Exception {
        boolean doInsert;
        List<ColumnInfo> columns = logicTableInfo.getColumns();
        if (CollectionUtils.isEmpty(columns)) {
            return Collections.emptyList();
        }
        String tableName = logicTableInfo.getTableName();
        DesignTableModelDefine designTableDefine = this.designDataModelService.getTableModelDefineByCode(tableName);
        Map<Object, Object> oldColumnMap = new HashMap();
        boolean bl = doInsert = designTableDefine == null;
        if (doInsert) {
            designTableDefine = this.initNewTableDefine(tableName, logicTableInfo);
        } else {
            designTableDefine.setBizKeys("");
            designTableDefine.setKeys("");
            designTableDefine.setTitle(logicTableInfo.getTitle());
            designTableDefine.setDesc(logicTableInfo.getDescription());
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(designTableDefine.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity()));
        }
        HashSet deleteCodeSet = new HashSet(oldColumnMap.keySet());
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        for (ColumnInfo columnInfo : columns) {
            deleteCodeSet.remove(columnInfo.getCode());
            this.parseColumn(doInsert, designTableDefine, oldColumnMap, columnInfo, createFieldList, modifyFieldList);
        }
        List<ColumnInfo> primaryColumns = logicTableInfo.getPrimaryColumns();
        for (ColumnInfo column : primaryColumns) {
            deleteCodeSet.remove(column.getCode());
            this.parseColumn(doInsert, designTableDefine, oldColumnMap, column, createFieldList, modifyFieldList);
        }
        List<DesignColumnModelDefine> list = oldColumnMap.entrySet().stream().filter(e -> deleteCodeSet.contains(e.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
        ArrayList<DesignIndexModelDefine> createIndexList = new ArrayList<DesignIndexModelDefine>();
        ArrayList<DesignIndexModelDefine> modifyIndexList = new ArrayList<DesignIndexModelDefine>();
        List<IndexInfo> indexInfos = logicTableInfo.getIndexInfos();
        if (doInsert) {
            this.newIndex(indexInfos, createFieldList, createIndexList);
        } else {
            List oldIndexs = this.designDataModelService.getIndexsByTable(designTableDefine.getID());
            this.updateIndex(columns, oldColumnMap, indexInfos, oldIndexs, createIndexList, modifyIndexList);
        }
        return this.doSaveAndDeploy(designTableDefine, doInsert, createFieldList, modifyFieldList, list, createIndexList, modifyIndexList, noDDL);
    }

    private void newIndex(List<IndexInfo> indexInfos, List<DesignColumnModelDefine> createFieldList, List<DesignIndexModelDefine> createIndexList) {
        for (IndexInfo indexInfo : indexInfos) {
            int[] indexCols = indexInfo.getIndexColumns();
            ArrayList<String> indexColumns = new ArrayList<String>();
            for (int indexCol : indexCols) {
                DesignColumnModelDefine indexColumn = createFieldList.get(indexCol);
                indexColumns.add(indexColumn.getID());
            }
            DesignIndexModelDefine indexModel = this.designDataModelService.createIndexModel();
            indexModel.setName(indexInfo.getIndexName());
            indexModel.setType(indexInfo.getIndexModelType());
            indexModel.setDesc(indexInfo.getDescription());
            indexModel.setFieldIDs(String.join((CharSequence)";", indexColumns));
            createIndexList.add(indexModel);
        }
    }

    private void updateIndex(List<ColumnInfo> columns, Map<String, DesignColumnModelDefine> oldColumnMap, List<IndexInfo> indexInfos, List<DesignIndexModelDefine> oldIndexs, List<DesignIndexModelDefine> createIndexList, List<DesignIndexModelDefine> modifyIndexList) {
        if (CollectionUtils.isEmpty(indexInfos)) {
            return;
        }
        Map oldIndexMap = oldIndexs.stream().collect(Collectors.toMap(IndexModelDefine::getName, Function.identity(), (o, i) -> o));
        for (IndexInfo indexInfo : indexInfos) {
            String indexName = indexInfo.getIndexName();
            DesignIndexModelDefine oldIndex = (DesignIndexModelDefine)oldIndexMap.get(indexName);
            DesignIndexModelDefine newIndex = this.createIndexModel(indexInfo, columns, oldColumnMap);
            if (oldIndex == null) {
                createIndexList.add(newIndex);
                continue;
            }
            if (this.equlasIndex(oldIndex, newIndex)) continue;
            newIndex.setID(oldIndex.getID());
            modifyIndexList.add(newIndex);
        }
    }

    private boolean equlasIndex(DesignIndexModelDefine oldIndex, DesignIndexModelDefine newIndex) {
        Set newFields = Stream.of(newIndex.getFieldIDs().split(";")).collect(Collectors.toSet());
        Set oldFields = Stream.of(oldIndex.getFieldIDs().split(";")).collect(Collectors.toSet());
        return oldIndex.getName().equals(newIndex.getName()) && oldFields.containsAll(newFields) && oldIndex.getType() == newIndex.getType();
    }

    private DesignIndexModelDefine createIndexModel(IndexInfo indexInfo, List<ColumnInfo> columns, Map<String, DesignColumnModelDefine> oldColumnMap) {
        DesignIndexModelDefine indexModel = this.designDataModelService.createIndexModel();
        indexModel.setName(indexInfo.getIndexName());
        indexModel.setDesc(indexInfo.getDescription());
        indexModel.setType(indexInfo.getIndexModelType());
        int[] indexs = indexInfo.getIndexColumns();
        StringBuilder sb = new StringBuilder();
        for (int index : indexs) {
            DesignColumnModelDefine designColumnModelDefine;
            ColumnInfo column = columns.get(index);
            if (!oldColumnMap.containsKey(column.getCode()) || null == (designColumnModelDefine = oldColumnMap.get(column.getCode()))) continue;
            String columnId = designColumnModelDefine.getID();
            sb.append(columnId).append(";");
        }
        indexModel.setFieldIDs(sb.toString());
        return indexModel;
    }

    private DesignTableModelDefine initNewTableDefine(String commentTableCode, LogicTableInfo logicTableInfo) {
        DesignTableModelDefine commentTableDefine = this.designDataModelService.createTableModelDefine();
        commentTableDefine.setCode(commentTableCode);
        commentTableDefine.setName(commentTableCode);
        commentTableDefine.setTitle(logicTableInfo.getTitle());
        commentTableDefine.setDesc(logicTableInfo.getDescription());
        commentTableDefine.setOwner("NR");
        DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
        commentTableDefine.setCatalogID(sysTableGroupByParent.getID());
        return commentTableDefine;
    }

    private void parseColumn(boolean doInsert, DesignTableModelDefine designTable, Map<String, DesignColumnModelDefine> oldColumnMap, ColumnInfo columnInfo, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        DesignColumnModelDefine userColumn = this.initField(designTable.getID(), columnInfo.getReferFieldID(), columnInfo);
        DesignColumnModelDefine designColumnModelDefine = oldColumnMap.get(columnInfo.getCode());
        if (doInsert || designColumnModelDefine == null) {
            createFieldList.add(userColumn);
        } else {
            userColumn.setID(designColumnModelDefine.getID());
            if (!this.checkColumnEquals(userColumn, designColumnModelDefine)) {
                modifyFieldList.add(userColumn);
            }
        }
        if (columnInfo.isKeyField()) {
            String keys = StringUtils.isEmpty((String)designTable.getBizKeys()) ? userColumn.getID() : designTable.getBizKeys() + ";" + userColumn.getID();
            designTable.setBizKeys(keys);
            designTable.setKeys(keys);
        }
    }

    private List<String> doSaveAndDeploy(DesignTableModelDefine tableDefine, boolean doInsert, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, List<DesignColumnModelDefine> deleteFieldList, List<DesignIndexModelDefine> createIndexList, List<DesignIndexModelDefine> modifyIndexList, boolean getSql) throws Exception {
        if (doInsert) {
            this.designDataModelService.insertTableModelDefine(tableDefine);
        } else {
            this.designDataModelService.updateTableModelDefine(tableDefine);
        }
        if (createFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(createFieldList.toArray(new DesignColumnModelDefine[0]));
        }
        if (modifyFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(modifyFieldList.toArray(new DesignColumnModelDefine[0]));
        }
        if (deleteFieldList.size() > 0) {
            this.designDataModelService.deleteColumnModelDefines((String[])deleteFieldList.stream().map(IModelDefineItem::getID).toArray(String[]::new));
        }
        if (createIndexList.size() > 0) {
            this.designDataModelService.updateIndexModelDefines(createIndexList.toArray(new DesignIndexModelDefine[0]));
        }
        if (modifyIndexList.size() > 0) {
            this.designDataModelService.updateIndexModelDefines(modifyIndexList.toArray(new DesignIndexModelDefine[0]));
        }
        if (!getSql) {
            if (createFieldList.size() > 0 || modifyFieldList.size() > 0 || deleteFieldList.size() > 0 || createIndexList.size() > 0 || modifyIndexList.size() > 0) {
                this.dataModelDeployService.deployTableUnCheck(tableDefine.getID());
            }
        } else {
            return this.dataModelDeployService.getDeployTableSqls(tableDefine.getID());
        }
        return Collections.emptyList();
    }

    private boolean checkColumnEquals(DesignColumnModelDefine o1, DesignColumnModelDefine o2) {
        return o1.getCode().equalsIgnoreCase(o2.getCode()) && o1.getColumnType() == o2.getColumnType() && o1.getReferColumnID().equals(o2.getReferColumnID()) && (o1.getPrecision() == o2.getPrecision() || o1.getPrecision() < o2.getPrecision());
    }

    private DesignColumnModelDefine initField(String tableKey, String referField, ColumnInfo columnInfo) {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
        fieldDefine.setCode(columnInfo.getCode());
        fieldDefine.setName(columnInfo.getCode());
        fieldDefine.setColumnType(columnInfo.getType());
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(columnInfo.getSize());
        fieldDefine.setReferColumnID(referField);
        fieldDefine.setDefaultValue(columnInfo.getDefaultValue());
        boolean isKey = columnInfo.isKeyField();
        if (isKey) {
            fieldDefine.setNullAble(false);
        }
        return fieldDefine;
    }

    private String queryEntityTableBizKeyByEntityID(String entityID) {
        IEntityDefine entity;
        if (StringUtils.isNotEmpty((String)entityID) && (entity = this.iEntityMetaService.queryEntity(entityID)) != null) {
            return this.queryEntityBizKeyByEntityId(entity.getId());
        }
        return null;
    }

    private String queryEntityBizKeyByEntityId(String entityId) {
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
        IEntityAttribute referFieldId = entityModel.getBizKeyField();
        if (referFieldId == null) {
            referFieldId = entityModel.getRecordKeyField();
        }
        return referFieldId.getID();
    }

    private String queryPeriodBizKey(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(viewKey);
        return this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
    }

    public void deleteAndDeploy(String tableName) throws Exception {
        DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(tableName);
        this.designDataModelService.deleteColumnModelDefineByTable(tableModelDefine.getID());
        this.designDataModelService.deleteIndexsByTable(tableModelDefine.getID());
        this.designDataModelService.delteTableModel(tableModelDefine.getID());
        this.dataModelDeployService.deployTableUnCheck(tableModelDefine.getID());
    }
}


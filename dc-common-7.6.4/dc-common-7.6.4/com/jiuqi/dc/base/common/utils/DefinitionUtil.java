/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.apache.commons.collections4.MapUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.ShardingBaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.TableDefineConvertHelper;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

public class DefinitionUtil {
    public static List<String> getTableColumn(DefinitionTableV definitionTableV) {
        IDbSqlHandler dbSqlHandler = OuterDataSourceUtils.getJdbcTemplate((String)definitionTableV.getDataSource()).getIDbSqlHandler();
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)definitionTableV.getDataSource());
        return jdbcTemplate.query(dbSqlHandler.getTableColumnSql(definitionTableV.getTableName()), (RowMapper)new StringRowMapper());
    }

    public static Map<String, Set<String>> getTableIndexMap(DefinitionTableV definitionTableV) {
        IDbSqlHandler dbSqlHandler = OuterDataSourceUtils.getJdbcTemplate((String)definitionTableV.getDataSource()).getIDbSqlHandler();
        HashMap resultMap = CollectionUtils.newHashMap();
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)definitionTableV.getDataSource());
        jdbcTemplate.query(dbSqlHandler.getTableIndexSql(definitionTableV.getTableName()), (rs, rowNum) -> {
            if (!resultMap.containsKey(rs.getString(1))) {
                resultMap.put(rs.getString(1), CollectionUtils.newHashSet());
            }
            ((Set)resultMap.get(rs.getString(1))).add(rs.getString(2));
            return null;
        });
        return resultMap;
    }

    public static Map<String, String> getStrTableIndexMap(DefinitionTableV definitionTableV) {
        IDbSqlHandler dbSqlHandler = OuterDataSourceUtils.getJdbcTemplate((String)definitionTableV.getDataSource()).getIDbSqlHandler();
        HashMap resultMap = CollectionUtils.newHashMap();
        GcBizJdbcTemplate jdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)definitionTableV.getDataSource());
        jdbcTemplate.query(dbSqlHandler.getTableIndexSql(definitionTableV.getTableName()), (rs, rowNum) -> {
            String title = rs.getString(1);
            String indexColumn = rs.getString(2);
            resultMap.compute(title, (k, existing) -> {
                if (Objects.isNull(existing)) {
                    return indexColumn;
                }
                return existing + "," + indexColumn;
            });
            return null;
        });
        return resultMap;
    }

    public static Map<String, List<String>> getTableColumnMap() {
        HashMap resultMap = CollectionUtils.newHashMap();
        Map<String, DefinitionTableV> definitionTableMap = DefinitionUtil.getDefinitionTableMap();
        if (MapUtils.isNotEmpty(definitionTableMap)) {
            for (Map.Entry<String, DefinitionTableV> entry : definitionTableMap.entrySet()) {
                resultMap.put(entry.getValue().getTableName(), entry.getValue().getFields().stream().map(DefinitionFieldV::getFieldName).collect(Collectors.toList()));
            }
        }
        return resultMap;
    }

    public static List<String> getTableNameList() {
        return new ArrayList<String>(DefinitionUtil.getDefinitionTableMap().keySet());
    }

    public static Map<String, Map<String, List<String>>> getTableIndexMap() {
        HashMap resultMap = CollectionUtils.newHashMap();
        Map<String, DefinitionTableV> definitionTableMap = DefinitionUtil.getDefinitionTableMap();
        if (MapUtils.isNotEmpty(definitionTableMap)) {
            for (Map.Entry<String, DefinitionTableV> entry : definitionTableMap.entrySet()) {
                DefinitionTableV tableDefine = entry.getValue();
                List indexList = tableDefine.getIndexs();
                if (CollectionUtils.isEmpty((Collection)indexList)) continue;
                resultMap.put(tableDefine.getTableName(), indexList.stream().collect(Collectors.toMap(DefinitionTableIndexV::getTitle, e -> Arrays.asList(e.getColumnsFields()), (k1, k2) -> k2)));
            }
        }
        return resultMap;
    }

    public static Map<String, DefinitionTableV> getDefinitionTableMap() {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        HashMap tableDefineMap = CollectionUtils.newHashMap();
        List entityList = entityTableCollector.getEntitys().stream().filter(e -> e.getClass().getName().startsWith("com.jiuqi.dc") || e.getClass().getName().startsWith("com.jiuqi.bde")).collect(Collectors.toList());
        HashMap tempMap = CollectionUtils.newHashMap();
        for (BaseEntity entity : entityList) {
            DBTable dbTableByType = entityTableCollector.getDbTableByType(entity.getClass());
            if (ShardingBaseEntity.class.isAssignableFrom(entity.getClass())) {
                List shardingList = ((ShardingBaseEntity)entity).getShardingList();
                if (CollectionUtils.isEmpty((Collection)shardingList)) continue;
                for (String sharding : shardingList) {
                    TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity, (DBTable)entityTableCollector.getDbTableByType(entity.getClass()));
                    DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
                    String tableName = (((ShardingBaseEntity)entity).getTableNamePrefix() + "_" + sharding).toUpperCase();
                    if (entity instanceof ITableExtend) {
                        tableDefine.getFields().addAll(((ITableExtend)entity).getExtendFieldList(tableName));
                    }
                    tableDefine.setCode(tableName);
                    tableDefine.setTableName(tableName);
                    List indexList = tableDefine.getIndexs();
                    if (!CollectionUtils.isEmpty((Collection)indexList)) {
                        indexList.forEach(e -> e.setTitle(e.getTitle() + "_" + sharding));
                    }
                    tableDefineMap.put(tableDefine.getTableName(), tableDefine);
                    if (StringUtils.isEmpty((CharSequence)dbTableByType.sourceTable())) continue;
                    tempMap.put(tableDefine.getTableName(), dbTableByType.sourceTable());
                }
                continue;
            }
            TableDefineConvertHelper tableDefineConvertHelper = TableDefineConvertHelper.newInstance((BaseEntity)entity, (DBTable)entityTableCollector.getDbTableByType(entity.getClass()));
            DefinitionTableV tableDefine = tableDefineConvertHelper.convert();
            if (entity instanceof ITableExtend) {
                tableDefine.getFields().addAll(((ITableExtend)entity).getExtendFieldList(tableDefine.getTableName()));
            }
            tableDefineMap.put(tableDefine.getTableName(), tableDefine);
            if (StringUtils.isEmpty((CharSequence)dbTableByType.sourceTable())) continue;
            tempMap.put(tableDefine.getTableName(), dbTableByType.sourceTable());
        }
        tempMap.forEach((k, v) -> {
            BaseEntity baseEntity = entityTableCollector.getEntityByName(v);
            if (Objects.nonNull(baseEntity)) {
                DBTable dbTableByType = entityTableCollector.getDbTableByType(baseEntity.getClass());
                if (!StringUtils.isEmpty((CharSequence)dbTableByType.dataSource())) {
                    ((DefinitionTableV)tableDefineMap.get(k)).setDataSource(OuterDataSourceUtils.getOuterDataSourceCode((String)dbTableByType.dataSource()));
                }
                if (baseEntity instanceof ITableExtend) {
                    ((DefinitionTableV)tableDefineMap.get(k)).getFields().addAll(((ITableExtend)baseEntity).getExtendFieldList(null));
                }
            }
        });
        return tableDefineMap;
    }

    public static DesignTableModelDefine getDesignTableModelDefine(DefinitionTableV definitionTable) {
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringContextUtils.getBean(DesignDataModelService.class);
        return StringUtils.isEmpty((CharSequence)definitionTable.getDataSource()) ? designDataModelService.getTableModelDefineByCode(definitionTable.getTableName()) : designDataModelService.getTableModelDefineByCode(definitionTable.getTableName(), definitionTable.getDataSource());
    }

    public static TableModelDefine getRunningTableModelDefine(DefinitionTableV definitionTable) {
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        return StringUtils.isEmpty((CharSequence)definitionTable.getDataSource()) ? dataModelService.getTableModelDefineByCode(definitionTable.getTableName()) : dataModelService.getTableModelDefineByCode(definitionTable.getTableName(), definitionTable.getDataSource());
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.IndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.DataModelDao
 *  com.jiuqi.nvwa.definition.interval.dao.IndexModelDao
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.apache.commons.collections4.MapUtils
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.dc.base.common.definition.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.data.TableCheckResult;
import com.jiuqi.dc.base.common.definition.service.ITableCheckService;
import com.jiuqi.dc.base.common.enums.TableCheckTypeEnum;
import com.jiuqi.dc.base.common.executor.DcDeployTableProcessor;
import com.jiuqi.dc.base.common.utils.DefinitionUtil;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableIndexV;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.IndexModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.ColumnModelDao;
import com.jiuqi.nvwa.definition.interval.dao.DataModelDao;
import com.jiuqi.nvwa.definition.interval.dao.IndexModelDao;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableCheckServiceImpl
implements ITableCheckService {
    private static final Logger logger = LoggerFactory.getLogger("com.jiuqi.dc.mappingscheme.impl.service.tableCheckAndSchemeUpdate");
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataModelDao dataModelDao;
    @Autowired
    private ColumnModelDao columnModelDao;
    @Autowired
    private IndexModelDao indexModelDao;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    @Autowired
    private DataModelDeployService dataModelDeployService;

    @Override
    public String tableCheck(Set<String> tableList, boolean isRepair) {
        HashSet failTables = CollectionUtils.newHashSet();
        HashSet repairSuccessTables = CollectionUtils.newHashSet();
        HashSet repairFailTables = CollectionUtils.newHashSet();
        Map<String, DefinitionTableV> tableMap = DefinitionUtil.getDefinitionTableMap();
        if (MapUtils.isEmpty(tableMap)) {
            logger.info("\u4e00\u672c\u8d26\u4e0ebde\u8868\u8303\u56f4\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u540e\u91cd\u8bd5!");
            return "\u4e00\u672c\u8d26\u4e0ebde\u8868\u8303\u56f4\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u540e\u91cd\u8bd5!";
        }
        for (DefinitionTableV definitionTable : tableMap.values()) {
            String tableName = definitionTable.getTableName();
            if (!CollectionUtils.isEmpty(tableList) && !tableList.contains(tableName)) continue;
            TableCheckResult dbCheckResult = this.checkDbTable(definitionTable);
            TableCheckResult designCheckResult = this.checkDesignTable(definitionTable);
            TableCheckResult runningCheckResult = this.checkRunningTable(definitionTable);
            if (dbCheckResult.checkPass() && designCheckResult.checkPass() && runningCheckResult.checkPass()) continue;
            if (isRepair) {
                block17: {
                    try {
                        List<String> missingIndexList;
                        List<String> missingColumnList;
                        Object[] ids;
                        DesignTableModelDefine designTableDefine;
                        if (designCheckResult.checkPass()) {
                            designTableDefine = DefinitionUtil.getDesignTableModelDefine(definitionTable);
                        } else {
                            designTableDefine = DcDeployTableProcessor.newInstance(definitionTable).repair();
                            Set<String> extraColumns = designCheckResult.getExtraColumns();
                            if (!CollectionUtils.isEmpty(extraColumns)) {
                                ids = (String[])this.designDataModelService.getColumnModelDefinesByTable(designTableDefine.getID()).stream().filter(e -> extraColumns.contains(e.getName())).map(IModelDefineItem::getID).toArray(String[]::new);
                                this.designDataModelService.deleteColumnModelDefines((String[])ids);
                            }
                        }
                        if (dbCheckResult.getPresent().booleanValue()) {
                            this.dataModelRegisterService.registerTable(designTableDefine.getID());
                        }
                        if (dbCheckResult.checkPass()) break block17;
                        if (!dbCheckResult.getPresent().booleanValue()) {
                            this.columnModelDao.deleteColumnModelDefineByTable(designTableDefine.getID());
                            this.indexModelDao.removeIndexsByTable(designTableDefine.getID());
                            this.dataModelDao.deleteRunTableDefines(new String[]{designTableDefine.getID()});
                        }
                        if (!CollectionUtils.isEmpty(missingColumnList = dbCheckResult.getMissingColumnList())) {
                            ids = this.designDataModelService.getColumnModelDefinesByTable(designTableDefine.getID()).stream().filter(e -> missingColumnList.contains(e.getName())).map(IModelDefineItem::getID).toArray();
                            if (missingColumnList.contains("ID")) {
                                String keys = designTableDefine.getKeys();
                                String bizKeys = designTableDefine.getBizKeys();
                                designTableDefine.setKeys(null);
                                designTableDefine.setBizKeys(null);
                                this.designDataModelService.updateTableModelDefine(designTableDefine);
                                this.dataModelRegisterService.registerTable(designTableDefine.getID());
                                designTableDefine.setKeys(keys);
                                designTableDefine.setBizKeys(bizKeys);
                                this.designDataModelService.updateTableModelDefine(designTableDefine);
                            }
                            this.columnModelDao.delete(ids);
                        }
                        if (!CollectionUtils.isEmpty(missingIndexList = dbCheckResult.getMissingIndexList())) {
                            Object[] ids2 = this.designDataModelService.getIndexsByTable(designTableDefine.getID()).stream().filter(e -> missingIndexList.contains(e.getName())).map(IndexModelDefine::getID).toArray();
                            this.indexModelDao.delete(ids2);
                        }
                        this.dataModelDeployService.deployTable(designTableDefine.getID());
                    }
                    catch (Exception e2) {
                        logger.error("\u3010{}\u3011\u8868\u4fee\u590d\u5931\u8d25", (Object)tableName, (Object)e2);
                        repairFailTables.add(tableName);
                        continue;
                    }
                }
                logger.info(String.format("\u3010%1$s\u3011\u8868\u4fee\u590d\u5b8c\u6bd5\u3002", tableName));
                repairSuccessTables.add(tableName);
                continue;
            }
            failTables.add(tableName);
        }
        if (!CollectionUtils.isEmpty((Collection)failTables)) {
            return String.join((CharSequence)"\u3001", failTables) + "\u8868\u68c0\u67e5\u4e0d\u901a\u8fc7\uff0c\u68c0\u67e5\u4fe1\u606f\u8bf7\u67e5\u770bcheckAndUpdate.log\u65e5\u5fd7\u6587\u4ef6\u3002";
        }
        if (!CollectionUtils.isEmpty((Collection)repairSuccessTables) || !CollectionUtils.isEmpty((Collection)repairFailTables)) {
            StringBuilder sb = new StringBuilder();
            if (!CollectionUtils.isEmpty((Collection)repairSuccessTables)) {
                sb.append(String.join((CharSequence)"\u3001", repairSuccessTables));
                sb.append("\u8868\u4fee\u590d\u6210\u529f\uff0c");
            }
            if (!CollectionUtils.isEmpty((Collection)repairFailTables)) {
                sb.append(String.join((CharSequence)"\u3001", repairFailTables));
                sb.append("\u8868\u4fee\u590d\u5931\u8d25\uff0c");
            }
            sb.append("\u68c0\u67e5\u53ca\u4fee\u590d\u4fe1\u606f\u8bf7\u67e5\u770bcheckAndUpdate.log\u65e5\u5fd7\u6587\u4ef6\u3002");
            return sb.toString();
        }
        return "\u8868\u7ed3\u6784\u68c0\u67e5\u901a\u8fc7";
    }

    private TableCheckResult checkRunningTable(DefinitionTableV definitionTable) {
        TableModelDefine tableModelDefine = DefinitionUtil.getRunningTableModelDefine(definitionTable);
        Set tableColumnSet = CollectionUtils.newHashSet();
        Map<Object, Object> tableIndexMap = CollectionUtils.newHashMap();
        if (Objects.nonNull(tableModelDefine)) {
            tableColumnSet = Optional.ofNullable(this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID())).map(e -> e.stream().map(ColumnModelDefine::getName).collect(Collectors.toSet())).orElse(CollectionUtils.newHashSet());
            tableIndexMap = this.getRunningIndexModelColumn(tableModelDefine.getID());
        }
        TableCheckResult tableCheckResult = this.checkTable(TableCheckTypeEnum.RUNNING_CHECK, definitionTable, tableColumnSet, tableIndexMap);
        this.printCheckLog(tableCheckResult);
        return tableCheckResult;
    }

    private TableCheckResult checkDesignTable(DefinitionTableV definitionTable) {
        DesignTableModelDefine tableModelDefine = DefinitionUtil.getDesignTableModelDefine(definitionTable);
        Set tableColumnSet = CollectionUtils.newHashSet();
        Map<Object, Object> tableIndexMap = CollectionUtils.newHashMap();
        if (Objects.nonNull(tableModelDefine)) {
            tableColumnSet = Optional.ofNullable(this.designDataModelService.getColumnModelDefinesByTable(tableModelDefine.getID())).map(e -> e.stream().map(ColumnModelDefine::getName).collect(Collectors.toSet())).orElse(CollectionUtils.newHashSet());
            tableIndexMap = this.getDesignIndexModelColumn(tableModelDefine.getID());
        }
        TableCheckResult tableCheckResult = this.checkTable(TableCheckTypeEnum.DESIGN_CHECK, definitionTable, tableColumnSet, tableIndexMap);
        this.printCheckLog(tableCheckResult);
        return tableCheckResult;
    }

    private TableCheckResult checkDbTable(DefinitionTableV definitionTable) {
        List<String> tableColumn = DefinitionUtil.getTableColumn(definitionTable);
        HashSet tableColumnSet = CollectionUtils.newHashSet();
        if (!CollectionUtils.isEmpty(tableColumn)) {
            tableColumnSet.addAll(tableColumn);
        }
        Map<String, String> tableIndexMap = DefinitionUtil.getStrTableIndexMap(definitionTable);
        TableCheckResult tableCheckResult = this.checkTable(TableCheckTypeEnum.DB_CHECK, definitionTable, tableColumnSet, tableIndexMap);
        this.printCheckLog(tableCheckResult);
        return tableCheckResult;
    }

    private TableCheckResult checkTable(TableCheckTypeEnum checkType, DefinitionTableV definitionTable, Set<String> tableColumns, Map<String, String> tableIndexMap) {
        List indexList;
        String tableName = definitionTable.getTableName();
        TableCheckResult tableCheckResult = new TableCheckResult(tableName, checkType, false);
        if (CollectionUtils.isEmpty(tableColumns)) {
            return tableCheckResult;
        }
        tableCheckResult.setPresent(Boolean.TRUE);
        HashSet extraColumns = CollectionUtils.newHashSet(tableColumns);
        for (DefinitionFieldV definitionFieldV : definitionTable.getFields()) {
            String columnName = definitionFieldV.getFieldName();
            if (!tableColumns.contains(columnName)) {
                tableCheckResult.getMissingColumnList().add(columnName);
                continue;
            }
            extraColumns.remove(columnName);
        }
        if (!TableCheckTypeEnum.DB_CHECK.equals((Object)checkType)) {
            tableCheckResult.setExtraColumns(extraColumns);
        }
        if (!CollectionUtils.isEmpty((Collection)(indexList = definitionTable.getIndexs()))) {
            Map<String, String> indexMap = indexList.stream().collect(Collectors.toMap(DefinitionTableIndexV::getTitle, e -> String.join((CharSequence)",", e.getColumnsFields()), (k1, k2) -> k2));
            for (Map.Entry<String, String> entry : indexMap.entrySet()) {
                if (tableIndexMap.containsValue(entry.getValue())) continue;
                tableCheckResult.getMissingIndexList().add(entry.getKey());
            }
        }
        return tableCheckResult;
    }

    private void printCheckLog(TableCheckResult checkResult) {
        String log;
        String tableName = checkResult.getTableName();
        String checkType = checkResult.getCheckType().getName();
        if (checkResult.checkPass()) {
            logger.info("\u3010{}\u3011\u8868{}\u901a\u8fc7", (Object)tableName, (Object)checkType);
            return;
        }
        if (!checkResult.getPresent().booleanValue()) {
            String log2 = String.format("\u3010%1$s\u3011\u8868%2$s\u4e0d\u901a\u8fc7\uff1a%3$s", tableName, checkType, TableCheckTypeEnum.DB_CHECK.getCode().contains(checkType) ? "\u7269\u7406\u8868\u7f3a\u5931" : "\u8868\u5b9a\u4e49\u7f3a\u5931");
            logger.info(log2);
            return;
        }
        if (!CollectionUtils.isEmpty(checkResult.getMissingColumnList())) {
            log = String.format("\u3010%1$s\u3011\u8868%2$s\u4e0d\u901a\u8fc7\uff1a\u5b57\u6bb5\u7f3a\u5931\u3010%3$s\u3011", tableName, checkType, String.join((CharSequence)",", checkResult.getMissingColumnList()));
            logger.info(log);
        }
        if (!CollectionUtils.isEmpty(checkResult.getExtraColumns())) {
            log = String.format("\u3010%1$s\u3011\u8868%2$s\u4e0d\u901a\u8fc7\uff1a\u5b58\u5728\u591a\u4f59\u5b57\u6bb5\u3010%3$s\u3011", tableName, checkType, String.join((CharSequence)",", checkResult.getExtraColumns()));
            logger.info(log);
        }
        if (!CollectionUtils.isEmpty(checkResult.getMissingIndexList())) {
            log = String.format("\u3010%1$s\u3011\u8868%2$s\u4e0d\u901a\u8fc7\uff1a\u7d22\u5f15\u7f3a\u5931\u3010%3$s\u3011", tableName, checkType, String.join((CharSequence)",", checkResult.getMissingIndexList()));
            logger.info(log);
        }
    }

    @NotNull
    private Map<String, String> getDesignIndexModelColumn(String tableId) {
        Map<Object, Object> result = CollectionUtils.newHashMap();
        List indexsList = this.designDataModelService.getIndexsByTable(tableId);
        if (CollectionUtils.isEmpty((Collection)indexsList)) {
            return result;
        }
        List designTableColumnList = this.designDataModelService.getColumnModelDefinesByTable(tableId);
        if (CollectionUtils.isEmpty((Collection)designTableColumnList)) {
            return CollectionUtils.newHashMap();
        }
        Map<String, String> columnMap = designTableColumnList.stream().collect(Collectors.toMap(IModelDefineItem::getID, IModelDefineItem::getCode, (k1, k2) -> k2));
        result = indexsList.stream().collect(Collectors.toMap(IndexModelDefine::getName, e -> {
            String[] split;
            StringJoiner sj = new StringJoiner(",");
            for (String s : split = e.getFieldIDs().split(";")) {
                if (!columnMap.containsKey(s)) continue;
                sj.add((CharSequence)columnMap.get(s));
            }
            return sj.toString();
        }, (k1, k2) -> k2));
        return result;
    }

    private Map<String, String> getRunningIndexModelColumn(String tableId) {
        Map<Object, Object> result = CollectionUtils.newHashMap();
        List indexsList = this.indexModelDao.getIndexsByTable(tableId);
        if (CollectionUtils.isEmpty((Collection)indexsList)) {
            return result;
        }
        List runningTableColumnList = this.columnModelDao.getColumnModelDefinesByTable(tableId);
        if (CollectionUtils.isEmpty((Collection)runningTableColumnList)) {
            return result;
        }
        Map<String, String> columnMap = runningTableColumnList.stream().collect(Collectors.toMap(IModelDefineItem::getID, IModelDefineItem::getCode, (k1, k2) -> k2));
        result = indexsList.stream().collect(Collectors.toMap(IndexModelDefine::getName, e -> {
            String[] split;
            StringJoiner sj = new StringJoiner(",");
            for (String s : split = e.getFieldIDs().split(";")) {
                if (!columnMap.containsKey(s)) continue;
                sj.add((CharSequence)columnMap.get(s));
            }
            return sj.toString();
        }, (k1, k2) -> k2));
        return result;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicIndex
 *  com.jiuqi.bi.database.metadata.LogicIndexField
 *  com.jiuqi.bi.database.metadata.LogicPrimaryKey
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.operator.ITableOperation
 *  com.jiuqi.bi.database.operator.ITableRefactor
 */
package com.jiuqi.nr.dataentity_ext.internal.db;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.operator.ITableOperation;
import com.jiuqi.bi.database.operator.ITableRefactor;
import com.jiuqi.nr.dataentity_ext.internal.db.EntityDataTempTable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class TempTableUtil {
    private static final Logger logger = LoggerFactory.getLogger(TempTableUtil.class);

    public static String restructureTempTable(Connection connection, EntityDataTempTable entityDataTempTable) {
        LogicTable logicTable = new LogicTable();
        logicTable.setName(entityDataTempTable.getTableName());
        List<LogicField> fields = entityDataTempTable.getLogicFields();
        List<String> pkFieldNames = entityDataTempTable.getPrimaryKeyFields();
        if (CollectionUtils.isEmpty(pkFieldNames)) {
            throw new RuntimeException("\u672a\u627e\u5230\u4e3b\u952e\u5b9a\u4e49,\u65e0\u6cd5\u751f\u4ea7\u6570\u636e\u8868");
        }
        LogicPrimaryKey primaryKey = TempTableUtil.buildPrimaryKey(logicTable, pkFieldNames);
        List<LogicIndex> logicIndices = TempTableUtil.buildIndexs(logicTable, entityDataTempTable.getIndexes());
        try {
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ITableRefactor tableOperator = database.createTableOperator(connection);
            List restructure = tableOperator.restructure(logicTable, fields, primaryKey, logicIndices, null);
            for (ITableOperation info : restructure) {
                logger.debug("{}:\r\n{}", (Object)info.getOperationName(), (Object)info.getOperationDescription());
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u521b\u5efa\u4e34\u65f6\u8868\u5931\u8d25:" + e.getMessage(), e);
        }
        return logicTable.getName();
    }

    public static LogicPrimaryKey buildPrimaryKey(LogicTable logicTable, List<String> pkFieldNames) {
        LogicPrimaryKey primaryKey = new LogicPrimaryKey();
        primaryKey.setTableName(logicTable.getName());
        primaryKey.setPkName(logicTable.getName() + "_PK");
        primaryKey.setFieldNames(pkFieldNames);
        return primaryKey;
    }

    public static List<LogicIndex> buildIndexs(LogicTable logicTable, List<List<String>> indexFields) {
        if (CollectionUtils.isEmpty(indexFields)) {
            return Collections.emptyList();
        }
        ArrayList<LogicIndex> indexs = new ArrayList<LogicIndex>(indexFields.size());
        int num = 0;
        for (List<String> indexField : indexFields) {
            LogicIndex logicIndex = new LogicIndex();
            logicIndex.setTableName(logicTable.getName());
            logicIndex.setIndexName(logicTable.getName() + "_IDX" + num++);
            ArrayList<LogicIndexField> indexFieldList = new ArrayList<LogicIndexField>(indexField.size());
            for (String field : indexField) {
                LogicIndexField fieldIndex = new LogicIndexField();
                fieldIndex.setFieldName(field);
                fieldIndex.setSortType(1);
                indexFieldList.add(fieldIndex);
            }
            logicIndex.setIndexFields(indexFieldList);
            indexs.add(logicIndex);
        }
        return indexs;
    }
}


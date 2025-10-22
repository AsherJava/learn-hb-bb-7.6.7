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
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.sb.service;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.operator.ITableOperation;
import com.jiuqi.bi.database.operator.ITableRefactor;
import com.jiuqi.nr.io.tz.bean.TempIndex;
import com.jiuqi.nr.io.tz.exception.TzCreateTmpTableException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;

public abstract class BaseTableCreateDao {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(BaseTableCreateDao.class);

    protected abstract LogicTable buildTmpTable();

    protected abstract Set<TempIndex> getIndexFields();

    protected abstract List<String> pkFieldName();

    protected abstract List<LogicField> buildTmpFields();

    protected List<LogicIndex> buildIndex(LogicTable logicTable, Set<TempIndex> indexFieldNames) {
        if (CollectionUtils.isEmpty(indexFieldNames)) {
            return Collections.emptyList();
        }
        ArrayList<LogicIndex> allIndex = new ArrayList<LogicIndex>();
        int num = 0;
        for (TempIndex tempIndex : indexFieldNames) {
            ++num;
            List<String> filedNames = tempIndex.getFiledNames();
            if (CollectionUtils.isEmpty(filedNames)) continue;
            LogicIndex index = new LogicIndex();
            index.setTableName(logicTable.getName());
            index.setIndexName(logicTable.getName() + "_" + num);
            ArrayList<LogicIndexField> indexFields = new ArrayList<LogicIndexField>();
            for (String name : filedNames) {
                LogicIndexField indexField = new LogicIndexField();
                indexField.setFieldName(name);
                indexFields.add(indexField);
            }
            index.setIndexFields(indexFields);
            index.setUnique(tempIndex.isUnique());
            allIndex.add(index);
        }
        return allIndex;
    }

    protected LogicPrimaryKey buildPrimaryKey(LogicTable logicTable, List<String> pkFieldNames) {
        LogicPrimaryKey primaryKey = new LogicPrimaryKey();
        primaryKey.setTableName(logicTable.getName());
        primaryKey.setPkName(logicTable.getName() + "_PK");
        primaryKey.setFieldNames(pkFieldNames);
        return primaryKey;
    }

    public void destroyTempTable(String tempTableName) {
        if (tempTableName == null) {
            return;
        }
        try {
            String sql = "DROP TABLE " + tempTableName;
            logger.info("\u6267\u884cSQL\uff1a{}", (Object)sql);
            this.jdbcTemplate.execute(sql);
        }
        catch (Exception ignored) {
            logger.warn("\u5220\u9664\u4e34\u65f6\u8868\u5931\u8d25\uff0c\u8bf7\u624b\u52a8\u5220\u9664\uff1a{}", (Object)tempTableName);
        }
    }

    public String restructureTempTable() {
        LogicTable logicTable = this.buildTmpTable();
        List<LogicField> fields = this.buildTmpFields();
        List<String> pkFieldNames = this.pkFieldName();
        if (CollectionUtils.isEmpty(pkFieldNames)) {
            throw new TzCreateTmpTableException("\u672a\u627e\u5230\u4e3b\u952e\u5b9a\u4e49,\u65e0\u6cd5\u751f\u4ea7\u6570\u636e\u8868");
        }
        LogicPrimaryKey primaryKey = this.buildPrimaryKey(logicTable, pkFieldNames);
        Set<TempIndex> indexField = this.getIndexFields();
        List<LogicIndex> allIndex = this.buildIndex(logicTable, indexField);
        Connection connection = null;
        try {
            connection = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ITableRefactor tableOperator = database.createTableOperator(connection);
            List restructure = tableOperator.restructure(logicTable, fields, primaryKey, allIndex, null);
            for (ITableOperation info : restructure) {
                logger.info(info.getOperationName() + ":\r\n" + info.getOperationDescription());
            }
        }
        catch (Exception e) {
            throw new TzCreateTmpTableException("\u521b\u5efa\u4e34\u65f6\u8868\u5931\u8d25", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        return logicTable.getName();
    }
}


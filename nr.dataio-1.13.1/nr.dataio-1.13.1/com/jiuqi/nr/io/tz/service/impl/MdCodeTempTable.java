/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicPrimaryKey
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.operator.ITableRefactor
 *  com.jiuqi.bi.database.sql.loader.LoadFieldMap
 *  com.jiuqi.bi.database.sql.loader.TableLoaderException
 *  com.jiuqi.bi.database.sql.model.ISQLField
 *  com.jiuqi.bi.database.sql.model.ISQLTable
 *  com.jiuqi.bi.database.sql.model.fields.EvalField
 *  com.jiuqi.bi.database.sql.model.tables.QueryTable
 *  com.jiuqi.bi.database.sql.model.tables.SimpleTable
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.operator.ITableRefactor;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.nr.io.tz.TzConstants;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.exception.ParamCheckException;
import com.jiuqi.nr.io.tz.service.impl.BaseInsertLoader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class MdCodeTempTable
extends BaseInsertLoader {
    private final TzParams params;

    public MdCodeTempTable(JdbcTemplate jdbcTemplate, TzParams params) {
        super(jdbcTemplate);
        this.params = params;
    }

    @Override
    protected void loadSrc() {
        QueryTable queryTable = new QueryTable((ISQLTable)new SimpleTable(this.params.getSourceData(), "T0"), "T1");
        EvalField md = new EvalField((ISQLTable)queryTable, "DISTINCT(MDCODE)");
        queryTable.addField((ISQLField)md);
        this.loader.setSourceTable((ISQLTable)queryTable);
    }

    @Override
    protected void loadDest() {
        SimpleTable dest = new SimpleTable(this.params.getMdCodeTable());
        dest.addField("MDCODE");
        this.loader.setDestTable(dest);
    }

    @Override
    protected void loadFieldMaps() {
        ISQLTable sourceTable = this.loader.getSourceTable();
        SimpleTable table = this.loader.getDestTable();
        this.loader.getFieldMaps().add(new LoadFieldMap((ISQLField)sourceTable.fields().get(0), (ISQLField)table.fields().get(0)));
    }

    @Override
    public int execute() throws TableLoaderException {
        Connection connection = null;
        try {
            connection = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
            this.database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ITableRefactor tableOperator = this.database.createTableOperator(connection);
            LogicTable logicTable = new LogicTable();
            logicTable.setName(TzConstants.createName());
            this.params.setMdCodeTable(logicTable.getName());
            ArrayList<LogicField> fields = new ArrayList<LogicField>();
            LogicField modeCode = new LogicField();
            modeCode.setDataType(6);
            modeCode.setFieldName("MDCODE");
            modeCode.setSize(50);
            fields.add(modeCode);
            LogicPrimaryKey primaryKey = new LogicPrimaryKey();
            primaryKey.setTableName(logicTable.getName());
            primaryKey.setPkName(logicTable.getName() + "_PK");
            primaryKey.setFieldNames(Collections.singletonList("MDCODE"));
            tableOperator.restructure(logicTable, fields, primaryKey, Collections.emptyList(), null);
        }
        catch (Exception e) {
            throw new ParamCheckException("\u521b\u5efa\u5355\u4f4d\u8303\u56f4\u8868\u5931\u8d25", e);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
        return super.execute();
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.bi.database.temp.ITempTableProvider
 *  com.jiuqi.bi.database.temp.TempTable
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.common.temptable.inner;

import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.ITempTableProvider;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.inner.BaseTempTable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class TempTableProxy
extends BaseTempTable
implements ITempTable {
    private final DataSource dataSource;
    private final Logger LOGGER = LoggerFactory.getLogger(TempTableProxy.class);

    public TempTableProxy(ITempTableProvider tempTableProvider, TempTable tempTable, DataSource dataSource) {
        super(tempTableProvider, tempTable);
        this.dataSource = dataSource;
    }

    @Override
    public void close() throws IOException {
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try {
            this.tempTableProvider.releaseTempTable(this.tempTable, connection);
            if (this.LOGGER.isDebugEnabled()) {
                this.LOGGER.debug("\u91ca\u653e\u4e34\u65f6\u8868 {}", (Object)this.tempTable.getTableName());
            }
        }
        catch (Exception e) {
            this.LOGGER.error("\u91ca\u653e\u4e34\u65f6\u8868\u5931\u8d25 {}", (Object)this.tempTable.getTableName(), (Object)e);
            throw new IOException(e);
        }
        finally {
            this.closed = true;
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    @Override
    public void insertRecords(List<Object[]> records) throws SQLException {
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try {
            this.batchUpdate(connection, this.createInsertSql(), records);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    @Override
    public void insertRecords(Connection connection, List<Object[]> records) throws SQLException {
        this.batchUpdate(connection, this.createInsertSql(), records);
    }

    @Override
    public void close(Connection connection) throws SQLException {
        this.tempTableProvider.releaseTempTable(this.tempTable, connection);
        if (this.LOGGER.isDebugEnabled()) {
            this.LOGGER.info("\u91ca\u653e\u4e34\u65f6\u8868 {}", (Object)this.tempTable.getTableName());
        }
        this.closed = true;
    }

    @Override
    public String getTableName() {
        return this.tempTable.getTableName();
    }

    @Override
    public ITempTableMeta getMeta() {
        return this.tempTable.getMeta();
    }

    @Override
    public String getRealColName(String colName) {
        return colName;
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection connection = DataSourceUtils.getConnection((DataSource)this.dataSource);
        try (PreparedStatement prep = connection.prepareStatement("DELETE FROM " + this.tempTable.getTableName());){
            prep.execute();
        }
        catch (Exception e) {
            throw new SQLException("\u5220\u9664\u6570\u636e\u5931\u8d25" + e.getMessage(), e);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.mappingscheme.impl.util;

import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component(value="AssistDimAdvanceSqlParser")
public class AdvanceSqlParser {
    @Autowired
    private DataSourceService dataSourceService;

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public List<Column> parseSql(String dataSourcCode, String sql) {
        Connection connection = null;
        LinkedList<Column> columnList = new LinkedList<Column>();
        PreparedStatement pst = null;
        try {
            connection = this.dataSourceService.getConnection(dataSourcCode);
            pst = connection.prepareStatement(sql);
            ResultSetMetaData metaData = pst.getMetaData();
            if (metaData == null) {
                throw new CheckRuntimeException("sql\u89e3\u6790\u51fa\u73b0\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5SQL");
            }
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; ++i) {
                columnList.add(this.convert2Column(metaData, i));
            }
            LinkedList<Column> linkedList = columnList;
            return linkedList;
        }
        catch (Exception e) {
            throw new CheckRuntimeException("sql\u89e3\u6790\u51fa\u73b0\u9519\u8bef", (Throwable)e);
        }
        finally {
            this.dataSourceService.releaseResource(dataSourcCode, connection, pst, null);
        }
    }

    private Column convert2Column(ResultSetMetaData metaData, int i) throws SQLException {
        Column column = new Column();
        column.setName(metaData.getColumnLabel(i).toUpperCase());
        column.setTitle(metaData.getColumnLabel(i).toUpperCase());
        return column;
    }
}


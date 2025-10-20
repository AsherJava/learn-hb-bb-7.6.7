/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.grid.GridData
 *  org.apache.ibatis.type.BaseTypeHandler
 *  org.apache.ibatis.type.JdbcType
 *  org.apache.ibatis.type.MappedJdbcTypes
 *  org.apache.ibatis.type.MappedTypes
 */
package com.jiuqi.va.mapper.handler;

import com.jiuqi.np.grid.GridData;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MappedTypes(value={GridData.class})
@MappedJdbcTypes(value={JdbcType.BLOB})
public class BudGridDataTypeHandler
extends BaseTypeHandler<GridData> {
    private static final Logger logger = LoggerFactory.getLogger(BudGridDataTypeHandler.class);

    public void setNonNullParameter(PreparedStatement ps, int i, GridData parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, GridData.gridToBytes((GridData)parameter));
    }

    public GridData getNullableResult(ResultSet rs, String columnName) throws SQLException {
        byte[] bytes = rs.getBytes(columnName);
        if (bytes == null) {
            return null;
        }
        try {
            return GridData.bytesToGrid((byte[])bytes);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public GridData getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        byte[] bytes = rs.getBytes(columnIndex);
        if (bytes == null) {
            return null;
        }
        try {
            return GridData.bytesToGrid((byte[])bytes);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public GridData getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        byte[] bytes = cs.getBytes(columnIndex);
        if (bytes == null) {
            return null;
        }
        try {
            return GridData.bytesToGrid((byte[])bytes);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

